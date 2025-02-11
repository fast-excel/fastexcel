package cn.idev.excel.test.temp.large;

import cn.idev.excel.event.AnalysisEventListener;
import cn.idev.excel.context.AnalysisContext;
import com.alibaba.fastjson2.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jiaju Zhuang
 */
public class LargeDataListener extends AnalysisEventListener<LargeData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LargeDataListener.class);
    private int count = 0;

    @Override
    public void invoke(LargeData data, AnalysisContext context) {
        if (count == 0) {
            LOGGER.info("First row:{}", JSON.toJSONString(data));
        }
        count++;
        if (count % 100000 == 0) {
            LOGGER.info("Already read:{}", count);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        LOGGER.info("Large row count:{}", count);
    }
}
