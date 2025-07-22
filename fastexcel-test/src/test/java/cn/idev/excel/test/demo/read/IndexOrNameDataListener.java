package cn.idev.excel.test.demo.read;

import java.util.List;

import cn.idev.excel.event.AnalysisEventListener;
import cn.idev.excel.util.ListUtils;
import cn.idev.excel.context.AnalysisContext;
import com.alibaba.fastjson2.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * Template reading class
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class IndexOrNameDataListener extends AnalysisEventListener<IndexOrNameData> {

    /**
     * Store data in the database every 5 records. In actual use, it can be 100 records,
     * and then clear the list to facilitate memory recycling.
     */
    private static final int BATCH_COUNT = 5;

    private List<IndexOrNameData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    @Override
    public void invoke(IndexOrNameData data, AnalysisContext context) {
        log.info("Parsed one row of data: {}", JSON.toJSONString(data));
        cachedDataList.add(data);
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        log.info("All data has been parsed!");
    }

    /**
     * Store data in the database
     */
    private void saveData() {
        log.info("{} records are being stored in the database!", cachedDataList.size());
        log.info("Data has been successfully stored in the database!");
    }
}
