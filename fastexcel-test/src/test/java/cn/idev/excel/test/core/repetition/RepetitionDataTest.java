package cn.idev.excel.test.core.repetition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.idev.excel.read.metadata.ReadSheet;
import cn.idev.excel.test.util.TestFileUtil;
import cn.idev.excel.EasyExcel;
import cn.idev.excel.ExcelReader;
import cn.idev.excel.ExcelWriter;
import cn.idev.excel.write.metadata.WriteSheet;
import cn.idev.excel.write.metadata.WriteTable;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * @author Jiaju Zhuang
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
public class RepetitionDataTest {

    private static File file07;
    private static File file03;
    private static File fileCsv;
    private static File fileTable07;
    private static File fileTable03;
    private static File fileTableCsv;

    @BeforeAll
    public static void init() {
        file07 = TestFileUtil.createNewFile("repetition07.xlsx");
        file03 = TestFileUtil.createNewFile("repetition03.xls");
        fileCsv = TestFileUtil.createNewFile("repetitionCsv.csv");
        fileTable07 = TestFileUtil.createNewFile("repetitionTable07.xlsx");
        fileTable03 = TestFileUtil.createNewFile("repetitionTable03.xls");
        fileTableCsv = TestFileUtil.createNewFile("repetitionTableCsv.csv");
    }

    @Test
    public void t01ReadAndWrite07() {
        readAndWrite(file07);
    }

    @Test
    public void t02ReadAndWrite03() {
        readAndWrite(file03);
    }

    @Test
    public void t03ReadAndWriteCsv() {
        readAndWrite(fileCsv);
    }

    private void readAndWrite(File file) {
        try (ExcelWriter excelWriter = EasyExcel.write(file, RepetitionData.class).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet(0).build();
            excelWriter.write(data(), writeSheet).write(data(), writeSheet);
        }
        try (ExcelReader excelReader = EasyExcel.read(file, RepetitionData.class, new RepetitionDataListener())
            .build()) {
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
        }
    }

    @Test
    public void t11ReadAndWriteTable07() {
        readAndWriteTable(fileTable07);
    }

    @Test
    public void t12ReadAndWriteTable03() {
        readAndWriteTable(fileTable03);
    }

    @Test
    public void t13ReadAndWriteTableCsv() {
        readAndWriteTable(fileTableCsv);
    }

    private void readAndWriteTable(File file) {
        try (ExcelWriter excelWriter = EasyExcel.write(file, RepetitionData.class).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet(0).build();
            WriteTable writeTable = EasyExcel.writerTable(0).relativeHeadRowIndex(0).build();
            excelWriter.write(data(), writeSheet, writeTable).write(data(), writeSheet, writeTable);
        }
        try (ExcelReader excelReader = EasyExcel.read(file, RepetitionData.class, new RepetitionDataListener())
            .build()) {
            ReadSheet readSheet = EasyExcel.readSheet(0).headRowNumber(2).build();
            excelReader.read(readSheet);
        }
    }

    private List<RepetitionData> data() {
        List<RepetitionData> list = new ArrayList<RepetitionData>();
        RepetitionData data = new RepetitionData();
        data.setString("字符串0");
        list.add(data);
        return list;
    }
}
