package cn.idev.excel.test.core.extra;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.enums.CellExtraTypeEnum;
import cn.idev.excel.metadata.CellExtra;
import cn.idev.excel.read.listener.ReadListener;
import cn.idev.excel.test.util.TestFileUtil;
import com.alibaba.fastjson2.JSON;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 */
@Slf4j
public class ExtraDataTest {
    private static File file03;
    private static File file07;

    private static File extraRelationships;

    @BeforeAll
    public static void init() {
        file03 = TestFileUtil.readFile("extra" + File.separator + "extra.xls");
        file07 = TestFileUtil.readFile("extra" + File.separator + "extra.xlsx");
        extraRelationships = TestFileUtil.readFile("extra" + File.separator + "extraRelationships.xlsx");
    }

    @Test
    public void t01Read07() {
        read(file07);
    }

    @Test
    public void t02Read03() {
        read(file03);
    }

    @Test
    public void t03Read() {
        EasyExcel.read(extraRelationships, ExtraData.class, new ReadListener() {
                    @Override
                    public void invoke(Object data, AnalysisContext context) {}

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {}

                    @Override
                    public void extra(CellExtra extra, AnalysisContext context) {
                        log.info("extra data:{}", JSON.toJSONString(extra));
                        switch (extra.getType()) {
                            case HYPERLINK:
                                if ("222222222".equals(extra.getText())) {
                                    Assertions.assertEquals(1, (int) extra.getRowIndex());
                                    Assertions.assertEquals(0, (int) extra.getColumnIndex());
                                } else if ("333333333333".equals(extra.getText())) {
                                    Assertions.assertEquals(1, (int) extra.getRowIndex());
                                    Assertions.assertEquals(1, (int) extra.getColumnIndex());
                                } else {
                                    Assertions.fail("Unknown hyperlink!");
                                }
                                break;
                            default:
                        }
                    }
                })
                .extraRead(CellExtraTypeEnum.HYPERLINK)
                .sheet()
                .doRead();
    }

    private void read(File file) {
        EasyExcel.read(file, ExtraData.class, new ExtraDataListener())
                .extraRead(CellExtraTypeEnum.COMMENT)
                .extraRead(CellExtraTypeEnum.HYPERLINK)
                .extraRead(CellExtraTypeEnum.MERGE)
                .sheet()
                .doRead();
    }
}
