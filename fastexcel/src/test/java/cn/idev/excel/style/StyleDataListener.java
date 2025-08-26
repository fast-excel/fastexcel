package cn.idev.excel.style;

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
public class StyleDataListener extends AnalysisEventListener<StyleData> {
    List<StyleData> list = new ArrayList<StyleData>();

    @Override
    public void invoke(StyleData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assertions.assertEquals(list.size(), 2);
        Assertions.assertEquals(list.get(0).getString(), "字符串0");
        Assertions.assertEquals(list.get(1).getString(), "字符串1");
        log.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
