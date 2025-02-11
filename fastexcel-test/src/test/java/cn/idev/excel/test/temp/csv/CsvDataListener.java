package cn.idev.excel.test.temp.csv;

import cn.idev.excel.event.AnalysisEventListener;
import cn.idev.excel.context.AnalysisContext;
import com.alibaba.fastjson2.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CsvDataListener extends AnalysisEventListener<CsvData> {
    @Override
    public void invoke(CsvData data, AnalysisContext context) {
        log.info("data:{}", JSON.toJSONString(data));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
