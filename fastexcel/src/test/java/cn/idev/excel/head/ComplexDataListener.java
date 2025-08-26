package cn.idev.excel.head;

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
public class ComplexDataListener extends AnalysisEventListener<ComplexHeadData> {

    List<ComplexHeadData> list = new ArrayList<ComplexHeadData>();

    @Override
    public void invoke(ComplexHeadData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assertions.assertEquals(list.size(), 1);
        ComplexHeadData data = list.get(0);
        Assertions.assertEquals(data.getString4(), "字符串4");
        log.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
