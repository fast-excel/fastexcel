package cn.idev.excel.analysis.csv;

import cn.idev.excel.analysis.ExcelReadExecutor;
import cn.idev.excel.context.csv.CsvReadContext;
import cn.idev.excel.enums.ByteOrderMarkEnum;
import cn.idev.excel.enums.CellDataTypeEnum;
import cn.idev.excel.enums.RowTypeEnum;
import cn.idev.excel.exception.ExcelAnalysisException;
import cn.idev.excel.exception.ExcelAnalysisStopSheetException;
import cn.idev.excel.metadata.Cell;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.read.metadata.ReadSheet;
import cn.idev.excel.read.metadata.holder.ReadRowHolder;
import cn.idev.excel.read.metadata.holder.csv.CsvReadWorkbookHolder;
import cn.idev.excel.util.SheetUtils;
import cn.idev.excel.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * CSV Excel Read Executor, responsible for reading and processing CSV files.
 *
 * @author zhuangjiaju
 */
@Slf4j
public class CsvExcelReadExecutor implements ExcelReadExecutor {
    
    // List of sheets to be read
    private final List<ReadSheet> sheetList;
    
    // Context for CSV reading operation
    private final CsvReadContext csvReadContext;
    
    public CsvExcelReadExecutor(CsvReadContext csvReadContext) {
        this.csvReadContext = csvReadContext;
        sheetList = new ArrayList<>();
        ReadSheet readSheet = new ReadSheet();
        sheetList.add(readSheet);
        readSheet.setSheetNo(0);
    }
    
    @Override
    public List<ReadSheet> sheetList() {
        return sheetList;
    }
    
    /**
     * Overrides the execute method to parse and process CSV files. This method first attempts to create a CSV parser,
     * then iterates through each sheet, and processes each record in the CSV file.
     */
    @Override
    public void execute() {
        CSVParser csvParser;
        try {
            // Create a CSV parser instance
            csvParser = csvParser();
            // Store the CSV parser instance in the context for subsequent processing
            csvReadContext.csvReadWorkbookHolder().setCsvParser(csvParser);
        } catch (IOException e) {
            throw new ExcelAnalysisException(e);
        }
        // Iterate through each sheet in the sheet list
        for (ReadSheet readSheet : sheetList) {
            // Match and update the readSheet object
            readSheet = SheetUtils.match(readSheet, csvReadContext);
            // If the match result is null, skip the current sheet
            if (readSheet == null) {
                continue;
            }
            try {
                // Set the current sheet being processed in the context
                csvReadContext.currentSheet(readSheet);
                
                // Initialize the row index
                int rowIndex = 0;
                
                for (CSVRecord record : csvParser) {
                    // Process the current record, incrementing the row index after each processing
                    dealRecord(record, rowIndex++);
                }
            } catch (ExcelAnalysisStopSheetException e) {
                if (log.isDebugEnabled()) {
                    log.debug("Custom stop!", e);
                }
            }
            
            // The last sheet is read
            csvReadContext.analysisEventProcessor().endSheet(csvReadContext);
        }
    }
    
    /**
     * Initializes and returns a CSVParser instance based on the configuration provided in the CsvReadContext. This
     * method determines the appropriate input stream and character set to create the CSV parser.
     *
     * @return A CSVParser instance for parsing CSV files.
     * @throws IOException If an I/O error occurs while accessing the input stream or file.
     */
    private CSVParser csvParser() throws IOException {
        // Retrieve the CsvReadWorkbookHolder instance from the CsvReadContext.
        CsvReadWorkbookHolder csvReadWorkbookHolder = csvReadContext.csvReadWorkbookHolder();
        // Get the CSV format configuration from the CsvReadWorkbookHolder.
        CSVFormat csvFormat = csvReadWorkbookHolder.getCsvFormat();
        // Determine the ByteOrderMarkEnum based on the character set name.
        ByteOrderMarkEnum byteOrderMark = ByteOrderMarkEnum.valueOfByCharsetName(
                csvReadContext.csvReadWorkbookHolder().getCharset().name());
        
        // If the configuration mandates the use of an input stream, build the CSV parser using the input stream.
        if (csvReadWorkbookHolder.getMandatoryUseInputStream()) {
            return buildCsvParser(csvFormat, csvReadWorkbookHolder.getInputStream(), byteOrderMark);
        }
        
        // If a file is provided in the configuration, build the CSV parser using the file's input stream.
        if (csvReadWorkbookHolder.getFile() != null) {
            return buildCsvParser(csvFormat, Files.newInputStream(csvReadWorkbookHolder.getFile().toPath()),
                    byteOrderMark);
        }
        
        // As a fallback, build the CSV parser using the input stream.
        return buildCsvParser(csvFormat, csvReadWorkbookHolder.getInputStream(), byteOrderMark);
    }
    
    /**
     * Builds and returns a CSVParser instance based on the provided CSVFormat, InputStream, and ByteOrderMarkEnum.
     *
     * @param csvFormat     The format configuration for parsing the CSV file.
     * @param inputStream   The input stream from which the CSV data will be read.
     * @param byteOrderMark The enumeration representing the Byte Order Mark (BOM) of the file's character set.
     * @return A CSVParser instance configured to parse the CSV data.
     * @throws IOException If an I/O error occurs while creating the parser or reading from the input stream.
     *                     <p>
     *                     This method checks if the byteOrderMark is null. If it is null, it creates a CSVParser using
     *                     the provided input stream and charset. Otherwise, it wraps the input stream with a
     *                     BOMInputStream to handle files with a Byte Order Mark, ensuring proper decoding of the file
     *                     content.
     */
    private CSVParser buildCsvParser(CSVFormat csvFormat, InputStream inputStream, ByteOrderMarkEnum byteOrderMark)
            throws IOException {
        if (byteOrderMark == null) {
            return csvFormat.parse(
                    new InputStreamReader(inputStream, csvReadContext.csvReadWorkbookHolder().getCharset()));
        }
        return csvFormat.parse(new InputStreamReader(new BOMInputStream(inputStream, byteOrderMark.getByteOrderMark()),
                csvReadContext.csvReadWorkbookHolder().getCharset()));
    }
    
    /**
     * Processes a single CSV record and maps its content to a structured format for further analysis.
     *
     * @param record   The CSV record to be processed.
     * @param rowIndex The index of the current row being processed. This method performs the following steps: 1.
     *                 Initializes a `LinkedHashMap` to store cell data, ensuring the order of columns is preserved. 2.
     *                 Iterates through each cell in the CSV record using an iterator. 3. For each cell, creates a
     *                 `ReadCellData` object and sets its metadata (row index, column index, type, and value). - If the
     *                 cell is not blank, it is treated as a string and optionally trimmed based on the `autoTrim`
     *                 configuration. - If the cell is blank, it is marked as empty. 4. Adds the processed cell data to
     *                 the `cellMap`. 5. Determines the row type: if the `cellMap` is empty, the row is marked as
     *                 `EMPTY`; otherwise, it is marked as `DATA`. 6. Creates a `ReadRowHolder` object with the row's
     *                 metadata and cell map, and stores it in the context. 7. Updates the context's sheet holder with
     *                 the cell map and row index. 8. Notifies the analysis event processor that the row processing has
     *                 ended.
     */
    private void dealRecord(CSVRecord record, int rowIndex) {
        Map<Integer, Cell> cellMap = new LinkedHashMap<>();
        Iterator<String> cellIterator = record.iterator();
        int columnIndex = 0;
        Boolean autoTrim = csvReadContext.currentReadHolder().globalConfiguration().getAutoTrim();
        while (cellIterator.hasNext()) {
            String cellString = cellIterator.next();
            ReadCellData<String> readCellData = new ReadCellData<>();
            readCellData.setRowIndex(rowIndex);
            readCellData.setColumnIndex(columnIndex);
            
            // csv is an empty string of whether <code>,,</code> is read or <code>,"",</code>
            if (StringUtils.isNotBlank(cellString)) {
                readCellData.setType(CellDataTypeEnum.STRING);
                readCellData.setStringValue(autoTrim ? cellString.trim() : cellString);
            } else {
                readCellData.setType(CellDataTypeEnum.EMPTY);
            }
            cellMap.put(columnIndex++, readCellData);
        }
        
        RowTypeEnum rowType = MapUtils.isEmpty(cellMap) ? RowTypeEnum.EMPTY : RowTypeEnum.DATA;
        ReadRowHolder readRowHolder = new ReadRowHolder(rowIndex, rowType,
                csvReadContext.readWorkbookHolder().getGlobalConfiguration(), cellMap);
        csvReadContext.readRowHolder(readRowHolder);
        
        csvReadContext.csvReadSheetHolder().setCellMap(cellMap);
        csvReadContext.csvReadSheetHolder().setRowIndex(rowIndex);
        csvReadContext.analysisEventProcessor().endRow(csvReadContext);
    }
}
