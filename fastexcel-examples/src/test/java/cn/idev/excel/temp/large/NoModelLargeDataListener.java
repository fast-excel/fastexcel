package cn.idev.excel.temp.large;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import com.alibaba.fastjson2.JSON;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public class NoModelLargeDataListener extends AnalysisEventListener<Map<Integer, String>> {

    private int count = 0;

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        if (count == 0) {
            log.info("First row:{}", JSON.toJSONString(data));
        }
        count++;
        if (count % 100000 == 0) {
            log.info("Already read:{}", count);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("Large row count:{}", count);
    }
}
