package cn.idev.excel.read.listener;


import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.exception.ExcelDataConvertException;
import cn.idev.excel.metadata.Head;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.read.metadata.ValidateError;
import cn.idev.excel.read.metadata.holder.ValidateErrorHolder;
import cn.idev.excel.read.metadata.property.ExcelReadHeadProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

/**
 * validate read listener
 *
 * @author wangmeng
 */
public class ValidateReadListener<T> implements ReadListener<T>, ValidateErrorHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateReadListener.class);

    private boolean existValidate = true;

    private final Map<Field, String> fieldMap = new HashMap<>();

    private final Map<Integer, List<ValidateError>> errorMap = new TreeMap<>();


    private final static String CHECK_EMPTY_TEXT = "%s cannot be null or empty";
    private final static String CONVERSION_FAIL_TEXT = "the '%s' field type conversion failed, please enter the correct content";


    private File sourceFile = null;
    private Integer sheetNo;


    /**
     * Record {@link ExcelDataConvertException}
     *
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException convertException = ((ExcelDataConvertException) exception);
            Field field = convertException.getExcelContentProperty().getField();
            String headName = fieldMap.get(field);
            ValidateError error = new ValidateError(context.readRowHolder().getRowIndex(), headName, String.format(CONVERSION_FAIL_TEXT, headName));
            // mark conversion failure
            error.setConvertError(true);
            addError(error);
        }
    }


    /**
     * Initialize all fields that require validation
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        ExcelReadHeadProperty excelReadHeadProperty = context.currentReadHolder().excelReadHeadProperty();
        for (Head head : excelReadHeadProperty.getHeadMap().values()) {
            Field field = head.getField();
            String headName = String.join("-", head.getHeadNameList());
            field.setAccessible(true);
            fieldMap.put(field, headName);

            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if (excelProperty != null && excelProperty.notNull()) {

            }
        }
        if (fieldMap.isEmpty()) {
            existValidate = false;
        }
        if (sourceFile == null) {
            sourceFile = context.readWorkbookHolder().getFile();
        }
        sheetNo = context.readSheetHolder().getSheetNo();
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        if (existValidate) {
            checkNotNull(data, context);
        }
    }


    private void checkNotNull(T data, AnalysisContext context) {
        Integer rowIndex = context.readRowHolder().getRowIndex();
        fieldMap.forEach((field, headName) -> {
            try {
                Object attribute = field.get(data);
                if (attribute == null ||
                        (field.getType().equals(String.class) && ((String) attribute).isEmpty())) {
                    addError(rowIndex, String.format(CHECK_EMPTY_TEXT, headName));
                }
            } catch (IllegalAccessException e) {
                LOGGER.warn("failed to retrieve field properties through reflection");
            }
        });
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
    }

    @Override
    public Map<Integer, List<ValidateError>> getError() {
        return errorMap;
    }

    @Override
    public void addError(Integer rowNum, String message) {
        ValidateError error = new ValidateError(rowNum, message);
        errorMap.computeIfAbsent(rowNum, key -> new ArrayList<>());
        errorMap.get(rowNum).add(error);
    }


    public void addError(ValidateError error) {
        errorMap.computeIfAbsent(error.getRowNum(), key -> new ArrayList<>());
        errorMap.get(error.getRowNum()).add(error);
    }


    @Override
    public File getSourceFile() {
        return sourceFile;
    }

    @Override
    public Integer getSheetNo() {
        return sheetNo;
    }
}
