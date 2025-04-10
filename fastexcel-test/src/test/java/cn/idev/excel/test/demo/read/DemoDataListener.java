package cn.idev.excel.test.demo.read;

import java.util.List;

import cn.idev.excel.read.listener.ReadListener;
import cn.idev.excel.util.ListUtils;
import cn.idev.excel.context.AnalysisContext;
import com.alibaba.fastjson2.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * Template reading class
 *
 * @author Jiaju Zhuang
 */
// An important point is that DemoDataListener should not be managed by Spring.
// It needs to be newly created each time an Excel file is read.
// If Spring is used inside, it can be passed through the constructor.
@Slf4j
public class DemoDataListener implements ReadListener<DemoData> {

    /**
     * Store data in the database every 5 records. In actual use, it can be 100 records,
     * and then clear the list to facilitate memory recycling.
     */
    private static final int BATCH_COUNT = 100;
    /**
     * Cached data
     */
    private List<DemoData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    /**
     * Assume this is a DAO. Of course, if there is business logic, this can also be a service.
     * If the data does not need to be stored, this object is useless.
     */
    private DemoDAO demoDAO;

    public DemoDataListener() {
        // This is a demo, so a new instance is created here. In actual use with Spring,
        // please use the constructor with parameters below.
        demoDAO = new DemoDAO();
    }

    /**
     * If Spring is used, please use this constructor. When creating a Listener, the class managed by Spring needs to be passed in.
     *
     * @param demoDAO
     */
    public DemoDataListener(DemoDAO demoDAO) {
        this.demoDAO = demoDAO;
    }

    /**
     * This method will be called for each data parsed.
     *
     * @param data    one row value. It is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        log.info("Parsed one row of data: {}", JSON.toJSONString(data));
        cachedDataList.add(data);
        // When the number of records reaches BATCH_COUNT, the data needs to be stored in the database to prevent tens of
        // thousands of records from being held in memory, which can easily cause OutOfMemoryError (OOM).
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // Clear the list after storage
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * This method will be called after all data has been parsed.
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // Data needs to be saved here to ensure that any remaining data is stored in the database.
        saveData();
        log.info("All data has been parsed!");
    }

    /**
     * Store data in the database
     */
    private void saveData() {
        log.info("{} records are being stored in the database!", cachedDataList.size());
        demoDAO.save(cachedDataList);
        log.info("Data has been successfully stored in the database!");
    }
}
