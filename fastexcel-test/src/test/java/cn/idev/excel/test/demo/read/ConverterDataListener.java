package cn.idev.excel.test.demo.read;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.read.listener.ReadListener;
import cn.idev.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Template data reading class
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class ConverterDataListener implements ReadListener<ConverterData> {

    /**
     * Save to the database every 5 records. In actual use, you might use 100 records,
     * then clear the list to facilitate memory recycling.
     */
    private static final int BATCH_COUNT = 5;

    private List<ConverterData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    @Override
    public void invoke(ConverterData data, AnalysisContext context) {
        log.info("Parsed a piece of data: {}", JSON.toJSONString(data));
        cachedDataList.add(data);
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
     * Simulate saving data to the database
     */
    private void saveData() {
        log.info("Saving {} records to the database!", cachedDataList.size());
        log.info("Data saved to the database successfully!");
    }
}
