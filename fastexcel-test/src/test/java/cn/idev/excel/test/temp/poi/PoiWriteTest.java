package cn.idev.excel.test.temp.poi;

import com.alibaba.fastjson2.JSON;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试poi
 *
 *
 **/
public class PoiWriteTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PoiWriteTest.class);

    @Test
    public void write0(@TempDir Path path) throws IOException {
        try (FileOutputStream fileOutputStream =
                        new FileOutputStream(path.resolve("PoiWriteTest_" + System.currentTimeMillis() + ".xlsx")
                                .toFile());
                SXSSFWorkbook workbook = new SXSSFWorkbook()) {
            SXSSFSheet sheet = workbook.createSheet("t1");
            SXSSFRow row = sheet.createRow(0);
            SXSSFCell cell1 = row.createCell(0);
            cell1.setCellValue(999999999999999L);
            SXSSFCell cell2 = row.createCell(1);
            cell2.setCellValue(1000000000000001L);
            SXSSFCell cell32 = row.createCell(2);
            cell32.setCellValue(300.35f);
            workbook.write(fileOutputStream);
        }
    }

    @Test
    public void write01() throws IOException {
        float ff = 300.35f;
        BigDecimal bd = new BigDecimal(Float.toString(ff));
        System.out.println(bd.doubleValue());
        System.out.println(bd.floatValue());
    }

    @Test
    public void write(@TempDir Path path) throws IOException {
        try (FileOutputStream fileOutputStream =
                        new FileOutputStream(path.resolve("PoiWriteTest_" + System.currentTimeMillis() + ".xlsx")
                                .toFile());
                SXSSFWorkbook workbook = new SXSSFWorkbook()) {
            SXSSFSheet sheet = workbook.createSheet("t1");
            SXSSFRow row = sheet.createRow(0);
            SXSSFCell cell1 = row.createCell(0);
            cell1.setCellValue(Long.toString(999999999999999L));
            SXSSFCell cell2 = row.createCell(1);
            cell2.setCellValue(Long.toString(1000000000000001L));
            workbook.write(fileOutputStream);
        }
    }

    @Test
    public void write1() {
        System.out.println(JSON.toJSONString(long2Bytes(-999999999999999L)));
        System.out.println(JSON.toJSONString(long2Bytes(-9999999999999999L)));
    }

    public static byte[] long2Bytes(long num) {
        byte[] byteNum = new byte[8];
        for (int ix = 0; ix < 8; ++ix) {
            int offset = 64 - (ix + 1) * 8;
            byteNum[ix] = (byte) ((num >> offset) & 0xff);
        }
        return byteNum;
    }
}
