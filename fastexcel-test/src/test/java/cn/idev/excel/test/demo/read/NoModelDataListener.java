package cn.idev.excel.test.demo.read;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import cn.idev.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * Directly receive data using a map.
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class NoModelDataListener extends AnalysisEventListener<Map<Integer, String>> {

    /**
     * Save to the database every 5 records. In actual use, it can be set to 100 records, and then clear the list to facilitate memory recycling.
     */
    private static final int BATCH_COUNT = 5;

    private List<Map<Integer, String>> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        log.info("Parsed a data row: {}", JSON.toJSONString(data));
        cachedDataList.add(data);
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
