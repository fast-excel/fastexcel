package cn.idev.excel.sort;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

/**
 *
 */
@Slf4j
public class SortDataListener extends AnalysisEventListener<SortData> {

    List<SortData> list = new ArrayList<SortData>();

    @Override
    public void invoke(SortData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assertions.assertEquals(list.size(), 1);
        SortData sortData = list.get(0);
        Assertions.assertEquals("column1", sortData.getColumn1());
        Assertions.assertEquals("column2", sortData.getColumn2());
        Assertions.assertEquals("column3", sortData.getColumn3());
        Assertions.assertEquals("column4", sortData.getColumn4());
        Assertions.assertEquals("column5", sortData.getColumn5());
        Assertions.assertEquals("column6", sortData.getColumn6());
    }
}
