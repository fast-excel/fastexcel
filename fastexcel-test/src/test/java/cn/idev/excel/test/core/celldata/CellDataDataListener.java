package cn.idev.excel.test.core.celldata;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import cn.idev.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson2.JSON;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

/**
 *
 */
@Slf4j
public class CellDataDataListener extends AnalysisEventListener<CellDataReadData> {

    List<CellDataReadData> list = new ArrayList<>();

    @Override
    public void invoke(CellDataReadData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assertions.assertEquals(list.size(), 1);
        CellDataReadData cellDataData = list.get(0);

        Assertions.assertEquals("2020年01月01日", cellDataData.getDate().getData());
        Assertions.assertEquals((long) cellDataData.getInteger1().getData(), 2L);
        Assertions.assertEquals((long) cellDataData.getInteger2(), 2L);
        if (context.readWorkbookHolder().getExcelType() != ExcelTypeEnum.CSV) {
            Assertions.assertEquals(
                    cellDataData.getFormulaValue().getFormulaData().getFormulaValue(), "B2+C2");
        } else {
            Assertions.assertNull(cellDataData.getFormulaValue().getData());
        }
        log.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
