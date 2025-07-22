package cn.idev.excel.test.temp.simple;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.ExcelReader;
import cn.idev.excel.read.metadata.ReadSheet;
import cn.idev.excel.test.temp.LockData;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

/**
 * 测试poi
 *
 *
 **/
public class RepeatTest {

    @Test
    public void xlsTest1() throws IOException {
        try (ExcelReader reader = EasyExcel.read(
                        Files.newInputStream(Paths.get("src/test/resources/repeat/repeat.xls")),
                        LockData.class,
                        new RepeatListener())
                .headRowNumber(0)
                .build()) {
            ReadSheet r1 = EasyExcel.readSheet(0).build();
            ReadSheet r2 = EasyExcel.readSheet(2).build();
            reader.read(r1);
            reader.read(r2);
            reader.finish();
        }
    }

    @Test
    public void xlsTest2() throws IOException {
        try (ExcelReader reader = EasyExcel.read(
                        Files.newInputStream(Paths.get("src/test/resources/repeat/repeat.xls")),
                        LockData.class,
                        new RepeatListener())
                .headRowNumber(0)
                .build()) {
            ReadSheet r2 = EasyExcel.readSheet(1).build();
            reader.read(r2);
            reader.finish();
        }
    }

    @Test
    public void xlsTest3() throws IOException {
        try (ExcelReader reader = EasyExcel.read(
                        Files.newInputStream(Paths.get("src/test/resources/repeat/repeat.xls")),
                        LockData.class,
                        new RepeatListener())
                .headRowNumber(0)
                .build()) {
            ReadSheet r2 = EasyExcel.readSheet(0).build();
            reader.read(r2);
            reader.finish();
        }
    }

    @Test
    public void xlsxTest1() throws IOException {
        try (ExcelReader reader = EasyExcel.read(
                        Files.newInputStream(Paths.get("src/test/resources/repeat/repeat.xlsx")),
                        LockData.class,
                        new RepeatListener())
                .headRowNumber(0)
                .build()) {
            ReadSheet r1 = EasyExcel.readSheet(0).build();
            ReadSheet r2 = EasyExcel.readSheet(2).build();
            reader.read(r1);
            reader.read(r2);
            reader.finish();
        }
    }

    @Test
    public void xlsxTest2() throws IOException {
        try (ExcelReader reader = EasyExcel.read(
                        Files.newInputStream(Paths.get("src/test/resources/repeat/repeat.xlsx")),
                        LockData.class,
                        new RepeatListener())
                .headRowNumber(0)
                .build()) {
            ReadSheet r2 = EasyExcel.readSheet(1).build();
            reader.read(r2);
            reader.finish();
        }
    }

    @Test
    public void xlsxTest3() throws IOException {
        try (ExcelReader reader = EasyExcel.read(
                        Files.newInputStream(Paths.get("src/test/resources/repeat/repeat.xlsx")),
                        LockData.class,
                        new RepeatListener())
                .headRowNumber(0)
                .build()) {
            ReadSheet r2 = EasyExcel.readSheet(0).build();
            reader.read(r2);
            reader.finish();
        }
    }
}
