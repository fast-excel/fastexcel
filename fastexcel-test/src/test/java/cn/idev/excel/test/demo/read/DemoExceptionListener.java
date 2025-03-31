package cn.idev.excel.test.demo.read;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.exception.ExcelDataConvertException;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.read.listener.ReadListener;
import cn.idev.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * Read and convert exceptions.
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class DemoExceptionListener implements ReadListener<ExceptionDemoData> {

    /**
     * Save to the database every 5 records. In actual use, it can be set to 100 records, and then clear the list to facilitate memory recycling.
     */
    private static final int BATCH_COUNT = 5;

    private List<ExceptionDemoData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * This interface will be called in case of conversion exceptions or other exceptions. If an exception is thrown here, reading will be stopped. If no exception is thrown, the next line will continue to be read.
     *
     * @param exception The exception that occurred.
     * @param context The analysis context.
     * @throws Exception If an exception needs to be propagated.
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("Parsing failed, but continue parsing the next line: {}", exception.getMessage());
        // If it is a cell conversion exception, the specific row number can be obtained.
        // If the header information is needed, use it in conjunction with invokeHeadMap.
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            log.error("Parsing exception in row {}, column {}, data: {}", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex(), excelDataConvertException.getCellData());
        }
    }

    /**
     * This method will return the header row line by line.
     *
     * @param headMap The header map containing column index and cell data.
     * @param context The analysis context.
     */
    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        log.info("Parsed a header row: {}", JSON.toJSONString(headMap));
    }

    @Override
    public void invoke(ExceptionDemoData data, AnalysisContext context) {
        log.info("Parsed a data row: {}", JSON.toJSONString(data));
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        log.info("All data parsing completed!");
    }

    /**
     * Save data to the database.
     */
    private void saveData() {
        log.info("{} records, starting to save to the database!", cachedDataList.size());
        log.info("Data saved to the database successfully!");
    }
}
