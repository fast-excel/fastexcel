package cn.idev.excel.test.core.simple;

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
public class SimpleDataSheetNameListener extends AnalysisEventListener<SimpleData> {
    List<SimpleData> list = new ArrayList<SimpleData>();

    @Override
    public void invoke(SimpleData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assertions.assertEquals(list.size(), 1);
        Assertions.assertEquals(list.get(0).getName(), "张三");
        log.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
