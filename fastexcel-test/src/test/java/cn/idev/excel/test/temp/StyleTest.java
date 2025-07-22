package cn.idev.excel.test.temp;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.ExcelStyleDateFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 临时测试
 *
 *
 **/
public class StyleTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StyleTest.class);

    @Test
    public void poi07Test() throws Exception {
        InputStream is = Files.newInputStream(Paths.get("src/test/resources/style/styleTest.xlsx"));
        Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel 2003/2007/2010 都是可以处理的
        Sheet sheet = workbook.getSheetAt(0);
        Row hssfRow = sheet.getRow(0);
        Assertions.assertEquals(1.0, hssfRow.getCell(0).getNumericCellValue());
        Assertions.assertEquals(1.0, hssfRow.getCell(1).getNumericCellValue());
        Assertions.assertEquals(1.0, hssfRow.getCell(2).getNumericCellValue());
        Assertions.assertEquals(1.0, hssfRow.getCell(3).getNumericCellValue());
        Assertions.assertEquals(14, hssfRow.getCell(0).getCellStyle().getDataFormat());
        Assertions.assertEquals(0, hssfRow.getCell(1).getCellStyle().getDataFormat());
        Assertions.assertEquals(10, hssfRow.getCell(2).getCellStyle().getDataFormat());
        Assertions.assertEquals(49, hssfRow.getCell(3).getCellStyle().getDataFormat());
        Assertions.assertEquals("m/d/yy", hssfRow.getCell(0).getCellStyle().getDataFormatString());
        Assertions.assertEquals("General", hssfRow.getCell(1).getCellStyle().getDataFormatString());
        Assertions.assertEquals("0.00%", hssfRow.getCell(2).getCellStyle().getDataFormatString());
        Assertions.assertEquals("@", hssfRow.getCell(3).getCellStyle().getDataFormatString());
        Assertions.assertTrue(isDate(hssfRow.getCell(0)));
        Assertions.assertFalse(isDate(hssfRow.getCell(1)));
        Assertions.assertFalse(isDate(hssfRow.getCell(2)));
        Assertions.assertFalse(isDate(hssfRow.getCell(3)));
    }

    @Test
    public void poi03Test() throws Exception {
        InputStream is = Files.newInputStream(Paths.get("src/test/resources/style/styleTest.xls"));
        Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel 2003/2007/2010 都是可以处理的
        Sheet sheet = workbook.getSheetAt(0);
        Row hssfRow = sheet.getRow(0);
        Assertions.assertEquals(1.0, hssfRow.getCell(0).getNumericCellValue());
        Assertions.assertEquals(1.0, hssfRow.getCell(1).getNumericCellValue());
        Assertions.assertEquals(1.0, hssfRow.getCell(2).getNumericCellValue());
        Assertions.assertEquals(1.0, hssfRow.getCell(3).getNumericCellValue());
        Assertions.assertEquals(14, hssfRow.getCell(0).getCellStyle().getDataFormat());
        Assertions.assertEquals(0, hssfRow.getCell(1).getCellStyle().getDataFormat());
        Assertions.assertEquals(10, hssfRow.getCell(2).getCellStyle().getDataFormat());
        Assertions.assertEquals(49, hssfRow.getCell(3).getCellStyle().getDataFormat());
        Assertions.assertEquals("m/d/yy", hssfRow.getCell(0).getCellStyle().getDataFormatString());
        Assertions.assertEquals("General", hssfRow.getCell(1).getCellStyle().getDataFormatString());
        Assertions.assertEquals("0.00%", hssfRow.getCell(2).getCellStyle().getDataFormatString());
        Assertions.assertEquals("@", hssfRow.getCell(3).getCellStyle().getDataFormatString());
        Assertions.assertTrue(isDate(hssfRow.getCell(0)));
        Assertions.assertFalse(isDate(hssfRow.getCell(1)));
        Assertions.assertFalse(isDate(hssfRow.getCell(2)));
        Assertions.assertFalse(isDate(hssfRow.getCell(3)));
    }

    @Test
    public void testFormatter() throws Exception {
        ExcelStyleDateFormatter ff = new ExcelStyleDateFormatter("yyyy年m月d日");

        System.out.println(ff.format(new Date()));
    }

    @Test
    public void testFormatter2() throws Exception {
        StyleData styleData = new StyleData();
        Field field = styleData.getClass().getDeclaredField("byteValue");
        LOGGER.info("field:{}", field.getType().getName());
        field = styleData.getClass().getDeclaredField("byteValue2");
        LOGGER.info("field:{}", field.getType().getName());
        field = styleData.getClass().getDeclaredField("byteValue4");
        LOGGER.info("field:{}", field.getType());
        field = styleData.getClass().getDeclaredField("byteValue3");
        LOGGER.info("field:{}", field.getType());
    }

    @Test
    public void testFormatter3() throws Exception {
        LOGGER.info("field:{}", Byte.class == Byte.class);
    }

    private boolean isDate(Cell cell) {
        return DateUtil.isADateFormat(
                cell.getCellStyle().getDataFormat(), cell.getCellStyle().getDataFormatString());
    }

    @Test
    public void testBuiltinFormats() throws Exception {
        System.out.println(BuiltinFormats.getBuiltinFormat(48));
        System.out.println(BuiltinFormats.getBuiltinFormat(57));
        System.out.println(BuiltinFormats.getBuiltinFormat(28));
    }
}
