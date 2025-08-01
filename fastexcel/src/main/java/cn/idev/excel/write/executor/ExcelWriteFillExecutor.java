package cn.idev.excel.write.executor;

import cn.idev.excel.context.WriteContext;
import cn.idev.excel.enums.CellDataTypeEnum;
import cn.idev.excel.enums.TemplateStringPartType;
import cn.idev.excel.enums.WriteDirectionEnum;
import cn.idev.excel.enums.WriteTemplateAnalysisCellTypeEnum;
import cn.idev.excel.exception.ExcelGenerateException;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;
import cn.idev.excel.util.BeanMapUtils;
import cn.idev.excel.util.ClassUtils;
import cn.idev.excel.util.FieldUtils;
import cn.idev.excel.util.ListUtils;
import cn.idev.excel.util.MapUtils;
import cn.idev.excel.util.PoiUtils;
import cn.idev.excel.util.StringUtils;
import cn.idev.excel.util.WriteHandlerUtils;
import cn.idev.excel.write.handler.TemplateStringParseHandler;
import cn.idev.excel.write.handler.context.CellWriteHandlerContext;
import cn.idev.excel.write.handler.context.RowWriteHandlerContext;
import cn.idev.excel.write.metadata.fill.AnalysisCell;
import cn.idev.excel.write.metadata.fill.FillConfig;
import cn.idev.excel.write.metadata.fill.FillWrapper;
import cn.idev.excel.write.metadata.fill.TemplateStringPart;
import cn.idev.excel.write.metadata.holder.WriteSheetHolder;
import java.util.*;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Fill the data into excel
 */
public class ExcelWriteFillExecutor extends AbstractExcelWriteExecutor {
    /**
     * Fields to replace in the template
     */
    private final Map<UniqueDataFlagKey, List<AnalysisCell>> templateAnalysisCache = MapUtils.newHashMap();
    /**
     * Collection fields to replace in the template
     */
    private final Map<UniqueDataFlagKey, List<AnalysisCell>> templateCollectionAnalysisCache = MapUtils.newHashMap();
    /**
     * Style cache for collection fields
     */
    private final Map<UniqueDataFlagKey, Map<AnalysisCell, CellStyle>> collectionFieldStyleCache =
            MapUtils.newHashMap();
    /**
     * Row height cache for collection
     */
    private final Map<UniqueDataFlagKey, Short> collectionRowHeightCache = MapUtils.newHashMap();
    /**
     * Last index cache for collection fields
     */
    private final Map<UniqueDataFlagKey, Map<AnalysisCell, Integer>> collectionLastIndexCache = MapUtils.newHashMap();

    private final Map<UniqueDataFlagKey, Integer> relativeRowIndexMap = MapUtils.newHashMap();
    /**
     * The unique data encoding for this fill
     */
    private UniqueDataFlagKey currentUniqueDataFlag;

    /**
     * The template string parse handler for this fill
     */
    private TemplateStringParseHandler currentTemplateStringParseHandler;

    public ExcelWriteFillExecutor(WriteContext writeContext) {
        super(writeContext);
    }

    public void fill(Object data, FillConfig fillConfig) {
        if (data == null) {
            data = new HashMap<String, Object>(16);
        }
        if (fillConfig == null) {
            fillConfig = FillConfig.builder().build();
        }
        fillConfig.init();

        Object realData;
        // The data prefix that is populated this time
        String currentDataPrefix;

        if (data instanceof FillWrapper) {
            FillWrapper fillWrapper = (FillWrapper) data;
            currentDataPrefix = fillWrapper.getName();
            realData = fillWrapper.getCollectionData();
        } else {
            realData = data;
            currentDataPrefix = null;
        }
        currentUniqueDataFlag = uniqueDataFlag(writeContext.writeSheetHolder(), currentDataPrefix);
        currentTemplateStringParseHandler = fillConfig.getTemplateStringParseHandler();

        // processing data
        if (realData instanceof Collection) {
            List<AnalysisCell> analysisCellList = readTemplateData(templateCollectionAnalysisCache);
            Collection<?> collectionData = (Collection<?>) realData;
            if (CollectionUtils.isEmpty(collectionData)) {
                return;
            }
            Iterator<?> iterator = collectionData.iterator();
            if (WriteDirectionEnum.VERTICAL.equals(fillConfig.getDirection()) && fillConfig.getForceNewRow()) {
                shiftRows(collectionData.size(), analysisCellList);
            }
            while (iterator.hasNext()) {
                doFill(analysisCellList, iterator.next(), fillConfig, getRelativeRowIndex());
            }
        } else {
            doFill(readTemplateData(templateAnalysisCache), realData, fillConfig, null);
        }
    }

    private void shiftRows(int size, List<AnalysisCell> analysisCellList) {
        if (CollectionUtils.isEmpty(analysisCellList)) {
            return;
        }
        int maxRowIndex = 0;
        Map<AnalysisCell, Integer> collectionLastIndexMap = collectionLastIndexCache.get(currentUniqueDataFlag);
        for (AnalysisCell analysisCell : analysisCellList) {
            if (collectionLastIndexMap != null) {
                Integer lastRowIndex = collectionLastIndexMap.get(analysisCell);
                if (lastRowIndex != null) {
                    if (lastRowIndex > maxRowIndex) {
                        maxRowIndex = lastRowIndex;
                    }
                    continue;
                }
            }
            if (analysisCell.getRowIndex() > maxRowIndex) {
                maxRowIndex = analysisCell.getRowIndex();
            }
        }
        Sheet cachedSheet = writeContext.writeSheetHolder().getCachedSheet();
        int lastRowIndex = cachedSheet.getLastRowNum();
        if (maxRowIndex >= lastRowIndex) {
            return;
        }
        Sheet sheet = writeContext.writeSheetHolder().getCachedSheet();
        int number = size;
        if (collectionLastIndexMap == null) {
            number--;
        }
        if (number <= 0) {
            return;
        }
        sheet.shiftRows(maxRowIndex + 1, lastRowIndex, number, true, false);

        // The current data is greater than unity rowindex increase
        increaseRowIndex(templateAnalysisCache, number, maxRowIndex);
        increaseRowIndex(templateCollectionAnalysisCache, number, maxRowIndex);
    }

    private void increaseRowIndex(
            Map<UniqueDataFlagKey, List<AnalysisCell>> templateAnalysisCache, int number, int maxRowIndex) {
        for (Map.Entry<UniqueDataFlagKey, List<AnalysisCell>> entry : templateAnalysisCache.entrySet()) {
            UniqueDataFlagKey uniqueDataFlagKey = entry.getKey();
            if (!Objects.equals(currentUniqueDataFlag.getSheetNo(), uniqueDataFlagKey.getSheetNo())
                    || !Objects.equals(currentUniqueDataFlag.getSheetName(), uniqueDataFlagKey.getSheetName())) {
                continue;
            }
            for (AnalysisCell analysisCell : entry.getValue()) {
                if (analysisCell.getRowIndex() > maxRowIndex) {
                    analysisCell.setRowIndex(analysisCell.getRowIndex() + number);
                }
            }
        }
    }

    private void doFill(
            List<AnalysisCell> analysisCellList, Object oneRowData, FillConfig fillConfig, Integer relativeRowIndex) {
        if (CollectionUtils.isEmpty(analysisCellList) || oneRowData == null) {
            return;
        }
        Map dataMap;
        if (oneRowData instanceof Map) {
            dataMap = (Map) oneRowData;
        } else {
            dataMap = BeanMapUtils.create(oneRowData);
        }
        Set<String> dataKeySet = new HashSet<>(dataMap.keySet());

        RowWriteHandlerContext rowWriteHandlerContext =
                WriteHandlerUtils.createRowWriteHandlerContext(writeContext, null, relativeRowIndex, Boolean.FALSE);

        for (AnalysisCell analysisCell : analysisCellList) {
            CellWriteHandlerContext cellWriteHandlerContext = WriteHandlerUtils.createCellWriteHandlerContext(
                    writeContext,
                    null,
                    analysisCell.getRowIndex(),
                    null,
                    analysisCell.getColumnIndex(),
                    relativeRowIndex,
                    Boolean.FALSE,
                    ExcelContentProperty.EMPTY);

            if (analysisCell.getOnlyOneVariable()) {
                String variable = analysisCell.getVariableList().get(0);
                Object value = null;
                if (dataKeySet.contains(variable)) {
                    value = dataMap.get(variable);
                }
                ExcelContentProperty excelContentProperty = ClassUtils.declaredExcelContentProperty(
                        dataMap,
                        writeContext
                                .currentWriteHolder()
                                .excelWriteHeadProperty()
                                .getHeadClazz(),
                        variable,
                        writeContext.currentWriteHolder());
                cellWriteHandlerContext.setExcelContentProperty(excelContentProperty);

                createCell(analysisCell, fillConfig, cellWriteHandlerContext, rowWriteHandlerContext);
                cellWriteHandlerContext.setOriginalValue(value);
                cellWriteHandlerContext.setOriginalFieldClass(FieldUtils.getFieldClass(dataMap, variable, value));

                converterAndSet(cellWriteHandlerContext);
                WriteCellData<?> cellData = cellWriteHandlerContext.getFirstCellData();

                // Restyle
                if (fillConfig.getAutoStyle()) {
                    Optional.ofNullable(collectionFieldStyleCache.get(currentUniqueDataFlag))
                            .map(collectionFieldStyleMap -> collectionFieldStyleMap.get(analysisCell))
                            .ifPresent(cellData::setOriginCellStyle);
                }
            } else {
                StringBuilder cellValueBuild = new StringBuilder();
                int index = 0;
                List<WriteCellData<?>> cellDataList = new ArrayList<>();

                cellWriteHandlerContext.setExcelContentProperty(ExcelContentProperty.EMPTY);
                cellWriteHandlerContext.setIgnoreFillStyle(Boolean.TRUE);

                createCell(analysisCell, fillConfig, cellWriteHandlerContext, rowWriteHandlerContext);
                Cell cell = cellWriteHandlerContext.getCell();

                for (String variable : analysisCell.getVariableList()) {
                    cellValueBuild.append(analysisCell.getPrepareDataList().get(index++));
                    Object value = null;
                    if (dataKeySet.contains(variable)) {
                        value = dataMap.get(variable);
                    }
                    ExcelContentProperty excelContentProperty = ClassUtils.declaredExcelContentProperty(
                            dataMap,
                            writeContext
                                    .currentWriteHolder()
                                    .excelWriteHeadProperty()
                                    .getHeadClazz(),
                            variable,
                            writeContext.currentWriteHolder());
                    cellWriteHandlerContext.setOriginalValue(value);
                    cellWriteHandlerContext.setOriginalFieldClass(FieldUtils.getFieldClass(dataMap, variable, value));
                    cellWriteHandlerContext.setExcelContentProperty(excelContentProperty);
                    cellWriteHandlerContext.setTargetCellDataType(CellDataTypeEnum.STRING);

                    WriteCellData<?> cellData = convert(cellWriteHandlerContext);
                    cellDataList.add(cellData);

                    CellDataTypeEnum type = cellData.getType();
                    if (type != null) {
                        switch (type) {
                            case STRING:
                                cellValueBuild.append(cellData.getStringValue());
                                break;
                            case BOOLEAN:
                                cellValueBuild.append(cellData.getBooleanValue());
                                break;
                            case NUMBER:
                                cellValueBuild.append(cellData.getNumberValue());
                                break;
                            default:
                                break;
                        }
                    }
                }
                cellValueBuild.append(analysisCell.getPrepareDataList().get(index));
                cell.setCellValue(cellValueBuild.toString());
                cellWriteHandlerContext.setCellDataList(cellDataList);
                if (CollectionUtils.isNotEmpty(cellDataList)) {
                    cellWriteHandlerContext.setFirstCellData(cellDataList.get(0));
                }

                // Restyle
                if (fillConfig.getAutoStyle()) {
                    Optional.ofNullable(collectionFieldStyleCache.get(currentUniqueDataFlag))
                            .map(collectionFieldStyleMap -> collectionFieldStyleMap.get(analysisCell))
                            .ifPresent(cell::setCellStyle);
                }
            }
            WriteHandlerUtils.afterCellDispose(cellWriteHandlerContext);
        }

        // In the case of the fill line may be called many times
        if (rowWriteHandlerContext.getRow() != null) {
            WriteHandlerUtils.afterRowDispose(rowWriteHandlerContext);
        }
    }

    private Integer getRelativeRowIndex() {
        Integer relativeRowIndex = relativeRowIndexMap.get(currentUniqueDataFlag);
        if (relativeRowIndex == null) {
            relativeRowIndex = 0;
        } else {
            relativeRowIndex++;
        }
        relativeRowIndexMap.put(currentUniqueDataFlag, relativeRowIndex);
        return relativeRowIndex;
    }

    private void createCell(
            AnalysisCell analysisCell,
            FillConfig fillConfig,
            CellWriteHandlerContext cellWriteHandlerContext,
            RowWriteHandlerContext rowWriteHandlerContext) {
        Sheet cachedSheet = writeContext.writeSheetHolder().getCachedSheet();
        if (WriteTemplateAnalysisCellTypeEnum.COMMON.equals(analysisCell.getCellType())) {
            Row row = cachedSheet.getRow(analysisCell.getRowIndex());
            cellWriteHandlerContext.setRow(row);
            Cell cell = row.getCell(analysisCell.getColumnIndex());
            cellWriteHandlerContext.setCell(cell);
            rowWriteHandlerContext.setRow(row);
            rowWriteHandlerContext.setRowIndex(analysisCell.getRowIndex());
            return;
        }
        Sheet sheet = writeContext.writeSheetHolder().getSheet();

        Map<AnalysisCell, Integer> collectionLastIndexMap =
                collectionLastIndexCache.computeIfAbsent(currentUniqueDataFlag, key -> MapUtils.newHashMap());

        boolean isOriginalCell = false;
        Integer lastRowIndex;
        Integer lastColumnIndex;
        switch (fillConfig.getDirection()) {
            case VERTICAL:
                lastRowIndex = collectionLastIndexMap.get(analysisCell);
                if (lastRowIndex == null) {
                    lastRowIndex = analysisCell.getRowIndex();
                    collectionLastIndexMap.put(analysisCell, lastRowIndex);
                    isOriginalCell = true;
                } else {
                    collectionLastIndexMap.put(analysisCell, ++lastRowIndex);
                }
                lastColumnIndex = analysisCell.getColumnIndex();
                break;
            case HORIZONTAL:
                lastRowIndex = analysisCell.getRowIndex();
                lastColumnIndex = collectionLastIndexMap.get(analysisCell);
                if (lastColumnIndex == null) {
                    lastColumnIndex = analysisCell.getColumnIndex();
                    collectionLastIndexMap.put(analysisCell, lastColumnIndex);
                    isOriginalCell = true;
                } else {
                    collectionLastIndexMap.put(analysisCell, ++lastColumnIndex);
                }
                break;
            default:
                throw new ExcelGenerateException("The wrong direction.");
        }

        Row row = createRowIfNecessary(
                sheet, cachedSheet, lastRowIndex, fillConfig, analysisCell, isOriginalCell, rowWriteHandlerContext);
        cellWriteHandlerContext.setRow(row);

        cellWriteHandlerContext.setRowIndex(lastRowIndex);
        cellWriteHandlerContext.setColumnIndex(lastColumnIndex);
        Cell cell = createCellIfNecessary(row, lastColumnIndex, cellWriteHandlerContext);
        cellWriteHandlerContext.setCell(cell);

        if (isOriginalCell) {
            Map<AnalysisCell, CellStyle> collectionFieldStyleMap =
                    collectionFieldStyleCache.computeIfAbsent(currentUniqueDataFlag, key -> MapUtils.newHashMap());
            collectionFieldStyleMap.put(analysisCell, cell.getCellStyle());
        }
    }

    private Cell createCellIfNecessary(
            Row row, Integer lastColumnIndex, CellWriteHandlerContext cellWriteHandlerContext) {
        Cell cell = row.getCell(lastColumnIndex);
        if (cell != null) {
            return cell;
        }
        WriteHandlerUtils.beforeCellCreate(cellWriteHandlerContext);
        cell = row.createCell(lastColumnIndex);
        cellWriteHandlerContext.setCell(cell);

        WriteHandlerUtils.afterCellCreate(cellWriteHandlerContext);
        return cell;
    }

    private Row createRowIfNecessary(
            Sheet sheet,
            Sheet cachedSheet,
            Integer lastRowIndex,
            FillConfig fillConfig,
            AnalysisCell analysisCell,
            boolean isOriginalCell,
            RowWriteHandlerContext rowWriteHandlerContext) {
        rowWriteHandlerContext.setRowIndex(lastRowIndex);
        Row row = sheet.getRow(lastRowIndex);
        if (row != null) {
            checkRowHeight(analysisCell, fillConfig, isOriginalCell, row);
            rowWriteHandlerContext.setRow(row);
            return row;
        }
        row = cachedSheet.getRow(lastRowIndex);
        if (row == null) {
            rowWriteHandlerContext.setRowIndex(lastRowIndex);
            WriteHandlerUtils.beforeRowCreate(rowWriteHandlerContext);

            if (fillConfig.getForceNewRow()) {
                row = cachedSheet.createRow(lastRowIndex);
            } else {
                // The last row of the middle disk inside empty rows, resulting in cachedSheet can not get inside.
                // Will throw Attempting to write a row[" + rownum + "] " + "in the range [0," + this._sh
                // .getLastRowNum() + "] that is already written to disk.
                try {
                    row = sheet.createRow(lastRowIndex);
                } catch (IllegalArgumentException ignore) {
                    row = cachedSheet.createRow(lastRowIndex);
                }
            }
            rowWriteHandlerContext.setRow(row);
            checkRowHeight(analysisCell, fillConfig, isOriginalCell, row);

            WriteHandlerUtils.afterRowCreate(rowWriteHandlerContext);
        } else {
            checkRowHeight(analysisCell, fillConfig, isOriginalCell, row);
            rowWriteHandlerContext.setRow(row);
        }
        return row;
    }

    private void checkRowHeight(AnalysisCell analysisCell, FillConfig fillConfig, boolean isOriginalCell, Row row) {
        if (!analysisCell.getFirstRow() || !WriteDirectionEnum.VERTICAL.equals(fillConfig.getDirection())) {
            return;
        }
        // fix https://github.com/fast-excel/fastexcel/issues/1869
        if (isOriginalCell && PoiUtils.customHeight(row)) {
            collectionRowHeightCache.put(currentUniqueDataFlag, row.getHeight());
            return;
        }
        if (fillConfig.getAutoStyle()) {
            Short rowHeight = collectionRowHeightCache.get(currentUniqueDataFlag);
            if (rowHeight != null) {
                row.setHeight(rowHeight);
            }
        }
    }

    private List<AnalysisCell> readTemplateData(Map<UniqueDataFlagKey, List<AnalysisCell>> analysisCache) {
        List<AnalysisCell> analysisCellList = analysisCache.get(currentUniqueDataFlag);
        if (analysisCellList != null) {
            return analysisCellList;
        }
        Sheet sheet = writeContext.writeSheetHolder().getCachedSheet();
        Map<UniqueDataFlagKey, Set<Integer>> firstRowCache = MapUtils.newHashMapWithExpectedSize(8);
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                if (cell == null) {
                    continue;
                }
                String preparedData = prepareData(cell, i, j, firstRowCache);
                // Prevent empty data from not being replaced
                if (preparedData != null) {
                    cell.setCellValue(preparedData);
                }
            }
        }
        return analysisCache.get(currentUniqueDataFlag);
    }

    /**
     * To prepare data
     *
     * @param cell          cell
     * @param rowIndex      row index
     * @param columnIndex   column index
     * @param firstRowCache first row cache
     * @return Returns the data that the cell needs to replace
     */
    private String prepareData(
            Cell cell, int rowIndex, int columnIndex, Map<UniqueDataFlagKey, Set<Integer>> firstRowCache) {
        if (!CellType.STRING.equals(cell.getCellType())) {
            return null;
        }
        String value = cell.getStringCellValue();
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        Collection<TemplateStringPart> templateStringParts = currentTemplateStringParseHandler.parse(value);
        if (CollectionUtils.isEmpty(templateStringParts)) {
            return null;
        }
        AnalysisCell analysisCell = analysisByTemplateStringParts(value, rowIndex, columnIndex, templateStringParts);
        if (analysisCell != null) {
            List<String> variableList = analysisCell.getVariableList(),
                    prepareDataList = analysisCell.getPrepareDataList();
            // fix https://github.com/fast-excel/fastexcel/issues/1552
            // When read template, XLSX data may be in `is` labels, and set the time set in `v` label, lead to can't set
            // up successfully, so all data format to empty first.
            if (CollectionUtils.isNotEmpty(variableList)) {
                cell.setBlank();
            } else if (CollectionUtils.isNotEmpty(prepareDataList)) {
                return String.join(StringUtils.EMPTY, analysisCell.getPrepareDataList());
            }
        }
        return dealAnalysisCell(analysisCell, rowIndex, firstRowCache);
    }

    private AnalysisCell analysisByTemplateStringParts(
            String value, Integer rowIndex, Integer columnIndex, Collection<TemplateStringPart> templateStringParts) {
        List<TemplateStringPart> orderedTemplateStringParts = templateStringParts.stream()
                .filter(part -> part != null && part.getType() != null)
                .sorted(Comparator.comparing(TemplateStringPart::getOrder, Comparator.nullsLast(Integer::compareTo)))
                .collect(Collectors.toList());
        AnalysisCell analysisCell = null;
        StringBuilder textAppender = new StringBuilder();
        int partsSize = orderedTemplateStringParts.size();
        for (int i = 0; i < partsSize; i++) {
            TemplateStringPart templateStringPart = orderedTemplateStringParts.get(i);
            TemplateStringPartType currentPartType = templateStringPart.getType();
            boolean tail = i == partsSize - 1;
            if (TemplateStringPartType.TEXT == currentPartType) {
                textAppender.append(templateStringPart.getText());
                if (!tail) {
                    continue;
                }
                String trailingText = textAppender.toString();
                if (analysisCell == null) {
                    if (!trailingText.equals(value)) {
                        AnalysisCell cell = new AnalysisCell();
                        cell.setPrepareDataList(Collections.singletonList(trailingText));
                        return cell;
                    }
                    continue;
                }
                analysisCell.getPrepareDataList().add(trailingText);
                if (Boolean.TRUE.equals(analysisCell.getOnlyOneVariable()) && !StringUtils.isEmpty(trailingText)) {
                    analysisCell.setOnlyOneVariable(Boolean.FALSE);
                }
                continue;
            }
            if (analysisCell == null) {
                analysisCell = initAnalysisCell(rowIndex, columnIndex);
            }
            String previousText = textAppender.toString();
            analysisCell.getPrepareDataList().add(previousText);
            if (textAppender.length() > 0) {
                textAppender.setLength(0);
            }
            if (tail) {
                analysisCell.getPrepareDataList().add(textAppender.toString());
            }
            List<String> variableList = analysisCell.getVariableList();
            if (Boolean.TRUE.equals(analysisCell.getOnlyOneVariable())
                    && (!variableList.isEmpty() || !StringUtils.isEmpty(previousText))) {
                analysisCell.setOnlyOneVariable(Boolean.FALSE);
            }
            if (TemplateStringPartType.COMMON_VARIABLE.equals(currentPartType)) {
                variableList.add(templateStringPart.getVariableName());
                analysisCell.setCellType(WriteTemplateAnalysisCellTypeEnum.COMMON);
                continue;
            }
            variableList.add(templateStringPart.getVariableName());
            analysisCell.setCellType(WriteTemplateAnalysisCellTypeEnum.COLLECTION);
            String collectionName = templateStringPart.getCollectionName();
            analysisCell.setPrefix(StringUtils.isEmpty(collectionName) ? null : collectionName);
        }
        return analysisCell;
    }

    private String dealAnalysisCell(
            AnalysisCell analysisCell, int rowIndex, Map<UniqueDataFlagKey, Set<Integer>> firstRowCache) {
        if (analysisCell != null) {
            UniqueDataFlagKey uniqueDataFlag =
                    uniqueDataFlag(writeContext.writeSheetHolder(), analysisCell.getPrefix());
            if (WriteTemplateAnalysisCellTypeEnum.COMMON.equals(analysisCell.getCellType())) {
                List<AnalysisCell> analysisCellList =
                        templateAnalysisCache.computeIfAbsent(uniqueDataFlag, key -> ListUtils.newArrayList());
                analysisCellList.add(analysisCell);
            } else {
                Set<Integer> uniqueFirstRowCache =
                        firstRowCache.computeIfAbsent(uniqueDataFlag, key -> new HashSet<>());

                if (!uniqueFirstRowCache.contains(rowIndex)) {
                    analysisCell.setFirstRow(Boolean.TRUE);
                    uniqueFirstRowCache.add(rowIndex);
                }

                List<AnalysisCell> collectionAnalysisCellList = templateCollectionAnalysisCache.computeIfAbsent(
                        uniqueDataFlag, key -> ListUtils.newArrayList());

                collectionAnalysisCellList.add(analysisCell);
            }
            List<String> prepareDataList = analysisCell.getPrepareDataList();
            return String.join(
                    StringUtils.EMPTY,
                    prepareDataList.size() > 1
                            ? prepareDataList.subList(0, prepareDataList.size() - 1)
                            : prepareDataList);
        }
        return null;
    }

    private AnalysisCell initAnalysisCell(Integer rowIndex, Integer columnIndex) {
        AnalysisCell analysisCell = new AnalysisCell();
        analysisCell.setRowIndex(rowIndex);
        analysisCell.setColumnIndex(columnIndex);
        analysisCell.setOnlyOneVariable(Boolean.TRUE);
        List<String> variableList = ListUtils.newArrayList();
        analysisCell.setVariableList(variableList);
        List<String> prepareDataList = ListUtils.newArrayList();
        analysisCell.setPrepareDataList(prepareDataList);
        analysisCell.setCellType(WriteTemplateAnalysisCellTypeEnum.COMMON);
        analysisCell.setFirstRow(Boolean.FALSE);
        return analysisCell;
    }

    private UniqueDataFlagKey uniqueDataFlag(WriteSheetHolder writeSheetHolder, String wrapperName) {
        return new UniqueDataFlagKey(writeSheetHolder.getSheetNo(), writeSheetHolder.getSheetName(), wrapperName);
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class UniqueDataFlagKey {
        private Integer sheetNo;
        private String sheetName;
        private String wrapperName;
    }
}
