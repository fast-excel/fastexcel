package cn.idev.excel.test.core.head;

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
public class NoHeadDataListener extends AnalysisEventListener<NoHeadData> {

    List<NoHeadData> list = new ArrayList<NoHeadData>();

    @Override
    public void invoke(NoHeadData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assertions.assertEquals(list.size(), 1);
        NoHeadData data = list.get(0);
        Assertions.assertEquals(data.getString(), "字符串0");
        log.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
