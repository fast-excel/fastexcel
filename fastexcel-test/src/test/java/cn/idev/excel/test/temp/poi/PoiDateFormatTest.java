package cn.idev.excel.test.temp.poi;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DateUtil;
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
public class PoiDateFormatTest {


    @Test
    public void read() throws IOException {
        String file = "src/test/resources/dataformat/dataformat.xlsx";
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        log.info("一共行数:{}", xssfSheet.getLastRowNum());
        XSSFRow row = xssfSheet.getRow(7);
        XSSFCell cell = row.getCell(0);
        log.info("dd{}", cell.getDateCellValue());
        log.info("dd{}", cell.getNumericCellValue());

        log.info("dd{}", DateUtil.isCellDateFormatted(cell));
    }
}
