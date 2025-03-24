package cn.idev.excel.test.demo.read;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Listener to read headers with compatibility for both Chinese and English.
 */
@Slf4j
public class DemoCompatibleHeaderDataListener extends AnalysisEventListener<DemoCompatibleHeaderData> {
    
    /**
     * Store data in batches of 100. In practice, you can adjust this number based on your needs. After storing, clear
     * the list to facilitate memory recovery.
     */
    private static final int BATCH_COUNT = 100;
    
    /**
     * Map various header names to their corresponding annotation header information.
     */
    private final Map<String, String> headerMapping = new HashMap<>(8);
    
    /**
     * Cache data in a list.
     */
    private List<DemoCompatibleHeaderData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    
    {
        // Initialize the header mapping with examples.
        headerMapping.put("字符串标题", "String");
        headerMapping.put("日期标题", "Date");
        headerMapping.put("数字标题", "DoubleData");
    }
    
    /**
     * This method will be called for each row of headers.
     *
     * @param headMap A map containing the header information.
     * @param context The analysis context.
     */
    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        log.info("Parsed one header row:{}", JSON.toJSONString(headMap));
        headMap.forEach((key, value) -> {
            // Here, a header mapping relationship is established. You can customize this logic as needed,
            // such as case conversion, suffix removal, space deletion, etc.
            String stringValue = value.getStringValue();
            value.setStringValue(headerMapping.getOrDefault(stringValue, stringValue));
        });
    }
    
    /**
     * This method is called for each parsed data row.
     *
     * @param data    One row of data. It is the same as {@link AnalysisContext#readRowHolder()}.
     * @param context The analysis context.
     */
    @Override
    public void invoke(DemoCompatibleHeaderData data, AnalysisContext context) {
        log.info("Parsed one data row:{}", JSON.toJSONString(data));
        cachedDataList.add(data);
        // When the cached data reaches BATCH_COUNT, store it to prevent OOM issues with large datasets.
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // Clear the list after storage.
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }
    
    /**
     * Called when all data has been analyzed.
     *
     * @param context The analysis context.
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        log.info("All data parsing completed!");
    }
    
    /**
     * Simulates saving data to a database.
     */
    private void saveData() {
        log.info("{} rows of data, starting to save to the database!", cachedDataList.size());
        log.info("Data saved successfully to the database!");
    }
    
}
