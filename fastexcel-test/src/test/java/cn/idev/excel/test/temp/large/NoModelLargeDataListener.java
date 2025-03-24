package cn.idev.excel.test.temp.large;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import com.alibaba.fastjson2.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author Jiaju Zhuang
 */
public class NoModelLargeDataListener extends AnalysisEventListener<Map<Integer, String>> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(NoModelLargeDataListener.class);
    
    private int count = 0;
    
    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
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
