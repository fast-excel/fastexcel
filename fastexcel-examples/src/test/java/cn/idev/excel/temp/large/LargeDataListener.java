package cn.idev.excel.temp.large;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public class LargeDataListener extends AnalysisEventListener<LargeData> {

    private int count = 0;

    @Override
    public void invoke(LargeData data, AnalysisContext context) {
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
