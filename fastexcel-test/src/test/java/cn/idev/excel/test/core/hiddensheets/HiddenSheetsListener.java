package cn.idev.excel.test.core.hiddensheets;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import com.alibaba.fastjson2.JSON;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

@Slf4j
public class HiddenSheetsListener extends AnalysisEventListener<HiddenSheetsData> {
    List<HiddenSheetsData> list = new ArrayList<>();

    @Override
    public void invoke(HiddenSheetsData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("A form is read finished.");
        Assertions.assertEquals("data01", list.get(0).getTitle());
        log.info("All row:{}", JSON.toJSONString(list));
    }

    public List<HiddenSheetsData> getList() {
        return list;
    }

    public void setList(List<HiddenSheetsData> list) {
        this.list = list;
    }
}
