package cn.idev.excel.test.core.hiddensheets;

import cn.idev.excel.ExcelReader;
import cn.idev.excel.FastExcel;
import cn.idev.excel.read.metadata.ReadSheet;
import cn.idev.excel.test.util.TestFileUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.util.List;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class HiddenSheetsTest {

    private static File file07;
    private static File file03;

    @BeforeAll
    public static void init() {
        file07 = TestFileUtil.readFile("hiddensheets" + File.separator + "hiddensheets.xlsx");
        file03 = TestFileUtil.readFile("hiddensheets" + File.separator + "hiddensheets.xls");
    }

    @Test
    public void t01Read07() {
        read(file07, Boolean.FALSE);
        read(file07, Boolean.TRUE);
    }

    @Test
    public void t02Read03() {
        read(file03, Boolean.FALSE);
        read(file03, Boolean.TRUE);
    }

    @Test
    public void t03Read07All() {
        readAll(file07);
    }

    @Test
    public void t04Read03All() {
        readAll(file03);
    }

    private void read(File file, Boolean ignoreHidden) {
        try (ExcelReader excelReader = FastExcel.read(file, HiddenSheetsData.class, new HiddenSheetsListener())
            .ignoreHiddenSheet(ignoreHidden).build()) {
            List<ReadSheet> sheets = excelReader.excelExecutor().sheetList();
            if (ignoreHidden) {
                Assertions.assertEquals(3, sheets.size());
            } else {
                Assertions.assertEquals(6, sheets.size());
            }
        }
    }

    private void readAll(File file) {
        FastExcel.read(file, HiddenSheetsData.class, new HiddenSheetsListener())
            .ignoreHiddenSheet(Boolean.TRUE)
            .doReadAll();
    }

}
