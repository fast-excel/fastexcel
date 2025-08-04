package cn.idev.excel.test.core.extra;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import cn.idev.excel.metadata.CellExtra;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

/**
 *
 */
@Slf4j
public class ExtraDataListener extends AnalysisEventListener<ExtraData> {

    @Override
    public void invoke(ExtraData data, AnalysisContext context) {}

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {}

    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        log.info("extra data:{}", JSON.toJSONString(extra));
        switch (extra.getType()) {
            case COMMENT:
                Assertions.assertEquals("批注的内容", extra.getText());
                Assertions.assertEquals(4, (int) extra.getRowIndex());
                Assertions.assertEquals(0, (int) extra.getColumnIndex());
                break;
            case HYPERLINK:
                if ("Sheet1!A1".equals(extra.getText())) {
                    Assertions.assertEquals(1, (int) extra.getRowIndex());
                    Assertions.assertEquals(0, (int) extra.getColumnIndex());
                } else if ("Sheet2!A1".equals(extra.getText())) {
                    Assertions.assertEquals(2, (int) extra.getFirstRowIndex());
                    Assertions.assertEquals(0, (int) extra.getFirstColumnIndex());
                    Assertions.assertEquals(3, (int) extra.getLastRowIndex());
                    Assertions.assertEquals(1, (int) extra.getLastColumnIndex());
                } else {
                    Assertions.fail("Unknown hyperlink!");
                }
                break;
            case MERGE:
                Assertions.assertEquals(5, (int) extra.getFirstRowIndex());
                Assertions.assertEquals(0, (int) extra.getFirstColumnIndex());
                Assertions.assertEquals(6, (int) extra.getLastRowIndex());
                Assertions.assertEquals(1, (int) extra.getLastColumnIndex());
                break;
            default:
        }
    }
}
