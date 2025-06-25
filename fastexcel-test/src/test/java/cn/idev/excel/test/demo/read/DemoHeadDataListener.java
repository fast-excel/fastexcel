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
 * Reading headers
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class DemoHeadDataListener implements ReadListener<DemoData> {

    /**
     * Save to the database every 5 records. In actual use, you might use 100 records,
     * then clear the list to facilitate memory recycling.
     */
    private static final int BATCH_COUNT = 5;

    private List<ExceptionDemoData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * This method is called when a conversion exception or other exceptions occur.
     * Throwing an exception will stop the reading process. If no exception is thrown here,
     * the reading will continue to the next row.
     *
     * @param exception The exception that occurred.
     * @param context   The analysis context.
     * @throws Exception If an exception is thrown to stop reading.
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("Parsing failed, but continue parsing the next row: {}", exception.getMessage());
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            log.error("Row {}, Column {} parsing exception, data is: {}", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex(), excelDataConvertException.getCellData());
        }
    }

    /**
     * This method is called for each header row.
     *
     * @param headMap The header data as a map.
     * @param context The analysis context.
     */
    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        log.info("Parsed a header row: {}", JSON.toJSONString(headMap));
        // If you want to convert it to a Map<Integer, String>:
        // Solution 1: Do not implement ReadListener, but extend AnalysisEventListener.
        // Solution 2: Call ConverterUtils.convertToStringMap(headMap, context) to convert automatically.
    }

    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        log.info("Parsed a piece of data: {}", JSON.toJSONString(data));
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        log.info("All data has been parsed and processed!");
    }

    /**
     * Simulate saving data to the database.
     */
    private void saveData() {
        log.info("Saving {} records to the database!", cachedDataList.size());
        log.info("Data saved to the database successfully!");
    }
}
