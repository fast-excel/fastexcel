package cn.idev.excel.test.core.template;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import com.alibaba.fastjson2.JSON;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

/**
 *
 */
@Slf4j
public class TemplateDataListener extends AnalysisEventListener<TemplateData> {

    List<TemplateData> list = new ArrayList<TemplateData>();

    @Override
    public void invoke(TemplateData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assertions.assertEquals(list.size(), 2);
        Assertions.assertEquals(list.get(0).getString0(), "字符串0");
        Assertions.assertEquals(list.get(1).getString0(), "字符串1");
        log.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
