package cn.idev.excel.test.core.multiplesheets;

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
public class MultipleSheetsListener extends AnalysisEventListener<MultipleSheetsData> {

    List<MultipleSheetsData> list = new ArrayList<MultipleSheetsData>();

    @Override
    public void invoke(MultipleSheetsData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.debug("A form is read finished.");
        Assertions.assertEquals(list.get(0).getTitle(), "表1数据");
        log.debug("All row:{}", JSON.toJSONString(list));
    }

    public List<MultipleSheetsData> getList() {
        return list;
    }

    public void setList(List<MultipleSheetsData> list) {
        this.list = list;
    }
}
