package cn.idev.excel.test.temp.large;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.ExcelWriter;
import cn.idev.excel.test.util.TestFileUtil;
import cn.idev.excel.write.metadata.WriteSheet;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 */
@Slf4j
public class TempLargeDataTest {

    private int i = 0;

    private static File fileFill07;

    private static File template07;

    private static File fileCsv;

    private static File fileWrite07;

    private static File fileWriteTemp07;

    private static File fileWritePoi07;

    @BeforeAll
    public static void init() {
        fileFill07 = TestFileUtil.createNewFile("largefill07.xlsx");
        fileWrite07 = TestFileUtil.createNewFile("large" + File.separator + "fileWrite07.xlsx");
        fileWriteTemp07 = TestFileUtil.createNewFile("large" + File.separator + "fileWriteTemp07.xlsx");
        fileWritePoi07 = TestFileUtil.createNewFile("large" + File.separator + "fileWritePoi07.xlsx");
        template07 = TestFileUtil.readFile("large" + File.separator + "fill.xlsx");
        fileCsv = TestFileUtil.createNewFile("largefileCsv.csv");
    }

    @Test
    public void read() throws Exception {
        long start = System.currentTimeMillis();
        EasyExcel.read(
                        Files.newInputStream(Paths.get("src/test/resources/simple/LargeData.xlsx")),
                        LargeData.class,
                        new LargeDataListener())
                .headRowNumber(2)
                .sheet()
                .doRead();
        log.info("Large data total time spent:{}", System.currentTimeMillis() - start);
    }

    @Test
    public void noModelRead() throws Exception {
        ZipSecureFile.setMaxEntrySize(Long.MAX_VALUE);
        long start = System.currentTimeMillis();
        EasyExcel.read("src/test/resources/simple/no_model_10000_rows.xlsx", new NoModelLargeDataListener())
                .sheet()
                .doRead();
        log.info("Large data total time spent:{}", System.currentTimeMillis() - start);
    }

    @Test
    public void t04Write() throws Exception {
        ExcelWriter excelWriter = EasyExcel.write(fileWriteTemp07, cn.idev.excel.test.core.large.LargeData.class)
                .build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        for (int j = 0; j < 2; j++) {
            excelWriter.write(data(), writeSheet);
        }
        excelWriter.finish();

        long start = System.currentTimeMillis();
        excelWriter = EasyExcel.write(fileWrite07, cn.idev.excel.test.core.large.LargeData.class)
                .build();
        writeSheet = EasyExcel.writerSheet().build();
        for (int j = 0; j < 5000; j++) {
            excelWriter.write(data(), writeSheet);
            log.info("{} write success.", j);
        }
        excelWriter.finish();
        long cost = System.currentTimeMillis() - start;
        log.info("write cost:{}", cost);
        start = System.currentTimeMillis();
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileWritePoi07)) {
            SXSSFWorkbook workbook = new SXSSFWorkbook();
            SXSSFSheet sheet = workbook.createSheet("sheet1");
            for (int i = 0; i < 100 * 5000; i++) {
                SXSSFRow row = sheet.createRow(i);
                for (int j = 0; j < 25; j++) {
                    SXSSFCell cell = row.createCell(j);
                    cell.setCellValue("str-" + j + "-" + i);
                }
                if (i % 5000 == 0) {
                    log.info("{} write success.", i);
                }
            }
            workbook.write(fileOutputStream);
            workbook.close();
        }
        long costPoi = System.currentTimeMillis() - start;
        log.info("poi write cost:{}", System.currentTimeMillis() - start);
        log.info("{} vs {}", cost, costPoi);
        Assertions.assertTrue(costPoi * 2 > cost);
    }

    @Test
    public void t04WriteExcel() {
        IntStream.rangeClosed(0, 100).forEach(index -> {
            try (ExcelWriter excelWriter = EasyExcel.write(
                            fileWriteTemp07, cn.idev.excel.test.core.large.LargeData.class)
                    .build()) {
                WriteSheet writeSheet = EasyExcel.writerSheet().build();
                for (int j = 0; j < 5000; j++) {
                    excelWriter.write(data(), writeSheet);
                }
                excelWriter.finish();
            }
            log.info("{} 完成", index);
        });
    }

    @Test
    public void t04WriteExcelNo() throws Exception {
        IntStream.rangeClosed(0, 10000).forEach(index -> {
            try (ExcelWriter excelWriter = EasyExcel.write(
                            fileWriteTemp07, cn.idev.excel.test.core.large.LargeData.class)
                    .build()) {
                WriteSheet writeSheet = EasyExcel.writerSheet().build();
                for (int j = 0; j < 50; j++) {
                    excelWriter.write(data(), writeSheet);
                }
                excelWriter.finish();
            }
            log.info("{} 完成", index);
        });
    }

    @Test
    public void t04WriteExcelPoi() throws Exception {
        IntStream.rangeClosed(0, 10000).forEach(index -> {
            try (FileOutputStream fileOutputStream = new FileOutputStream(fileWritePoi07)) {
                SXSSFWorkbook workbook = new SXSSFWorkbook(500);
                // workbook.setCompressTempFiles(true);
                SXSSFSheet sheet = workbook.createSheet("sheet1");
                for (int i = 0; i < 100 * 50; i++) {
                    SXSSFRow row = sheet.createRow(i);
                    for (int j = 0; j < 25; j++) {
                        String str = "str-" + j + "-" + i;
                        // if (i + 10000 == j) {
                        SXSSFCell cell = row.createCell(j);
                        cell.setCellValue(str);
                        // System.out.println(str);
                        // }
                    }
                    if (i % 5000 == 0) {
                        log.info("{} write success.", i);
                    }
                }
                workbook.write(fileOutputStream);
                workbook.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            log.info("{} 完成", index);
        });
    }

    private List<LargeData> data() {
        List<LargeData> list = new ArrayList<>();

        int size = i + 100;
        for (; i < size; i++) {
            LargeData largeData = new LargeData();
            list.add(largeData);
            largeData.setStr1("str1-" + i);
            largeData.setStr2("str2-" + i);
            largeData.setStr3("str3-" + i);
            largeData.setStr4("str4-" + i);
            largeData.setStr5("str5-" + i);
            largeData.setStr6("str6-" + i);
            largeData.setStr7("str7-" + i);
            largeData.setStr8("str8-" + i);
            largeData.setStr9("str9-" + i);
            largeData.setStr10("str10-" + i);
            largeData.setStr11("str11-" + i);
            largeData.setStr12("str12-" + i);
            largeData.setStr13("str13-" + i);
            largeData.setStr14("str14-" + i);
            largeData.setStr15("str15-" + i);
            largeData.setStr16("str16-" + i);
            largeData.setStr17("str17-" + i);
            largeData.setStr18("str18-" + i);
            largeData.setStr19("str19-" + i);
            largeData.setStr20("str20-" + i);
            largeData.setStr21("str21-" + i);
            largeData.setStr22("str22-" + i);
            largeData.setStr23("str23-" + i);
            largeData.setStr24("str24-" + i);
            largeData.setStr25("str25-" + i);
        }
        return list;
    }
}
