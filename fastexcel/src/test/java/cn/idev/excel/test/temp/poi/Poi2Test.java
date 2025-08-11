package cn.idev.excel.test.temp.poi;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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
public class Poi2Test {

    @Test
    public void test() throws IOException {
        String file = "src/test/resources/poi/last_row_number_test.xlsx";
        SXSSFWorkbook xssfWorkbook = new SXSSFWorkbook(new XSSFWorkbook(file));
        SXSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        log.info("一共行数:{}", xssfSheet.getLastRowNum());
        SXSSFRow row = xssfSheet.getRow(0);
        log.info("第一行数据:{}", row);
    }

    @Test
    public void lastRowNumXSSF() throws IOException {
        String file = "src/test/resources/poi/last_row_number_xssf_test.xlsx";
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        log.info("一共:{}个sheet", xssfWorkbook.getNumberOfSheets());
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        log.info("一共行数:{}", xssfSheet.getLastRowNum());
        XSSFRow row = xssfSheet.getRow(0);
        log.info("第一行数据:{}", row);
        xssfSheet.createRow(20);
        log.info("一共行数:{}", xssfSheet.getLastRowNum());
    }
}
