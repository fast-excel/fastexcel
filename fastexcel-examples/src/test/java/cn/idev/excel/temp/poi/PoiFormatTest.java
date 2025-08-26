package cn.idev.excel.temp.poi;

import java.io.IOException;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

/**
 * 测试poi
 *
 *
 **/
@Slf4j
public class PoiFormatTest {

    @Test
    public void lastRowNum() throws IOException {
        String file = "src/test/resources/poi/last_row_number_test.xlsx";
        SXSSFWorkbook xssfWorkbook = new SXSSFWorkbook(new XSSFWorkbook(file));
        SXSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        log.info("一共行数:{}", xssfSheet.getLastRowNum());
        SXSSFRow row = xssfSheet.getRow(0);
        log.info("第一行数据:{}", row);
        xssfSheet.createRow(20);
        log.info("一共行数:{}", xssfSheet.getLastRowNum());
    }

    @Test
    public void lastRowNumXSSF() throws IOException {
        String file = "src/test/resources/poi/last_row_number_xssf_test.xlsx";
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        log.info("一共:{}个sheet", xssfWorkbook.getNumberOfSheets());
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        log.info("一共行数:{}", xssfSheet.getLastRowNum());
        XSSFRow row = xssfSheet.getRow(1);
        XSSFCell xssfCell = row.getCell(0);
        DataFormatter d = new DataFormatter(Locale.CHINA);
        log.info("fo:{}", d.formatCellValue(xssfCell));
    }
}
