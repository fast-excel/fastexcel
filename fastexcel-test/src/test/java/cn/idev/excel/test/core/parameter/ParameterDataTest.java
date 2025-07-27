package cn.idev.excel.test.core.parameter;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.ExcelReader;
import cn.idev.excel.ExcelWriter;
import cn.idev.excel.cache.MapCache;
import cn.idev.excel.converters.string.StringStringConverter;
import cn.idev.excel.read.metadata.ReadSheet;
import cn.idev.excel.support.ExcelTypeEnum;
import cn.idev.excel.test.util.TestFileUtil;
import cn.idev.excel.write.metadata.WriteSheet;
import cn.idev.excel.write.metadata.WriteTable;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 *
 */
@Slf4j
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ParameterDataTest {

    private static File file03;
    private static File file07;
    private static File fileCsv;

    @BeforeAll
    public static void init() {
        file03 = TestFileUtil.createNewFile("parameter03.xls");
        file07 = TestFileUtil.createNewFile("parameter07.xlsx");
        fileCsv = TestFileUtil.createNewFile("parameterCsv.csv");
    }

    @Test
    public void t01ReadAndWrite() throws Exception {
        readAndWrite1(file07, ExcelTypeEnum.XLSX);
        readAndWrite2(file07, ExcelTypeEnum.XLSX);
        readAndWrite3(file07, ExcelTypeEnum.XLSX);
        readAndWrite4(file07, ExcelTypeEnum.XLSX);
        readAndWrite5(file07, ExcelTypeEnum.XLSX);
        readAndWrite6(file07, ExcelTypeEnum.XLSX);
        readAndWrite7(file07, ExcelTypeEnum.XLSX);
    }

    @Test
    public void t02ReadAndWrite() throws Exception {
        readAndWrite1(fileCsv, ExcelTypeEnum.CSV);
        readAndWrite2(fileCsv, ExcelTypeEnum.CSV);
        readAndWrite3(fileCsv, ExcelTypeEnum.CSV);
        readAndWrite4(fileCsv, ExcelTypeEnum.CSV);
        readAndWrite5(fileCsv, ExcelTypeEnum.CSV);
        readAndWrite6(fileCsv, ExcelTypeEnum.CSV);
        readAndWrite7(fileCsv, ExcelTypeEnum.CSV);
    }

    private void readAndWrite1(File file, ExcelTypeEnum type) {
        EasyExcel.write(file.getPath()).head(ParameterData.class).sheet().doWrite(data());
        EasyExcel.read(file.getPath())
                .head(ParameterData.class)
                .registerReadListener(new ParameterDataListener())
                .sheet()
                .doRead();
    }

    private void readAndWrite2(File file, ExcelTypeEnum type) {
        EasyExcel.write(file.getPath(), ParameterData.class).sheet().doWrite(data());
        EasyExcel.read(file.getPath(), ParameterData.class, new ParameterDataListener())
                .sheet()
                .doRead();
    }

    private void readAndWrite3(File file, ExcelTypeEnum type) throws Exception {
        EasyExcel.write(new FileOutputStream(file))
                .excelType(type)
                .head(ParameterData.class)
                .sheet()
                .doWrite(data());
        EasyExcel.read(file.getPath())
                .head(ParameterData.class)
                .registerReadListener(new ParameterDataListener())
                .sheet()
                .doRead();
    }

    private void readAndWrite4(File file, ExcelTypeEnum type) throws Exception {
        EasyExcel.write(new FileOutputStream(file), ParameterData.class)
                .excelType(type)
                .sheet()
                .doWrite(data());
        EasyExcel.read(file.getPath(), new ParameterDataListener())
                .head(ParameterData.class)
                .sheet()
                .doRead();
    }

    private void readAndWrite5(File file, ExcelTypeEnum type) throws Exception {
        ExcelWriter excelWriter = EasyExcel.write(new FileOutputStream(file))
                .excelType(type)
                .head(ParameterData.class)
                .relativeHeadRowIndex(0)
                .build();
        WriteSheet writeSheet = EasyExcel.writerSheet(0)
                .relativeHeadRowIndex(0)
                .needHead(Boolean.FALSE)
                .build();
        WriteTable writeTable = EasyExcel.writerTable(0)
                .relativeHeadRowIndex(0)
                .needHead(Boolean.TRUE)
                .build();
        excelWriter.write(data(), writeSheet, writeTable);
        excelWriter.finish();

        ExcelReader excelReader = EasyExcel.read(file.getPath(), new ParameterDataListener())
                .head(ParameterData.class)
                .mandatoryUseInputStream(Boolean.FALSE)
                .autoCloseStream(Boolean.TRUE)
                .readCache(new MapCache())
                .build();
        ReadSheet readSheet = EasyExcel.readSheet()
                .head(ParameterData.class)
                .use1904windowing(Boolean.FALSE)
                .headRowNumber(1)
                .sheetNo(0)
                .sheetName("0")
                .build();
        excelReader.read(readSheet);
        excelReader.finish();

        excelReader = EasyExcel.read(file.getPath(), new ParameterDataListener())
                .head(ParameterData.class)
                .mandatoryUseInputStream(Boolean.FALSE)
                .autoCloseStream(Boolean.TRUE)
                .readCache(new MapCache())
                .build();
        excelReader.read();
        excelReader.finish();
    }

    private void readAndWrite6(File file, ExcelTypeEnum type) throws Exception {
        ExcelWriter excelWriter = EasyExcel.write(new FileOutputStream(file))
                .excelType(type)
                .head(ParameterData.class)
                .relativeHeadRowIndex(0)
                .build();
        WriteSheet writeSheet = EasyExcel.writerSheet(0)
                .relativeHeadRowIndex(0)
                .needHead(Boolean.FALSE)
                .build();
        WriteTable writeTable = EasyExcel.writerTable(0)
                .registerConverter(new StringStringConverter())
                .relativeHeadRowIndex(0)
                .needHead(Boolean.TRUE)
                .build();
        excelWriter.write(data(), writeSheet, writeTable);
        excelWriter.finish();

        ExcelReader excelReader = EasyExcel.read(file.getPath(), new ParameterDataListener())
                .head(ParameterData.class)
                .mandatoryUseInputStream(Boolean.FALSE)
                .autoCloseStream(Boolean.TRUE)
                .readCache(new MapCache())
                .build();
        ReadSheet readSheet = EasyExcel.readSheet("0")
                .head(ParameterData.class)
                .use1904windowing(Boolean.FALSE)
                .headRowNumber(1)
                .sheetNo(0)
                .build();
        excelReader.read(readSheet);
        excelReader.finish();

        excelReader = EasyExcel.read(file.getPath(), new ParameterDataListener())
                .head(ParameterData.class)
                .mandatoryUseInputStream(Boolean.FALSE)
                .autoCloseStream(Boolean.TRUE)
                .readCache(new MapCache())
                .build();
        excelReader.read();
        excelReader.finish();
    }

    private void readAndWrite7(File file, ExcelTypeEnum type) {
        EasyExcel.write(file, ParameterData.class)
                .registerConverter(new StringStringConverter())
                .sheet()
                .registerConverter(new StringStringConverter())
                .needHead(Boolean.FALSE)
                .table(0)
                .needHead(Boolean.TRUE)
                .doWrite(data());
        EasyExcel.read(file.getPath())
                .head(ParameterData.class)
                .registerReadListener(new ParameterDataListener())
                .sheet()
                .registerConverter(new StringStringConverter())
                .doRead();
    }

    @Test
    void readAndWriteDate1904Test() {
        readAndWriteDate1904(file03, ExcelTypeEnum.XLS, null);
        readAndWriteDate1904(file03, ExcelTypeEnum.XLS, Boolean.TRUE);
        readAndWriteDate1904(file03, ExcelTypeEnum.XLS, Boolean.FALSE);
        readAndWriteDate1904(file07, ExcelTypeEnum.XLSX, null);
        readAndWriteDate1904(file07, ExcelTypeEnum.XLSX, Boolean.TRUE);
        readAndWriteDate1904(file07, ExcelTypeEnum.XLSX, Boolean.FALSE);
        readAndWriteDate1904(fileCsv, ExcelTypeEnum.CSV, null);
        readAndWriteDate1904(fileCsv, ExcelTypeEnum.CSV, Boolean.TRUE);
        readAndWriteDate1904(fileCsv, ExcelTypeEnum.CSV, Boolean.FALSE);
    }

    private void readAndWriteDate1904(File file, ExcelTypeEnum type, Boolean use1904windowing) {
        EasyExcel.write(file)
                .excelType(type)
                .use1904windowing(use1904windowing)
                .head(ParameterData.class)
                .sheet()
                .doWrite(data());

        ExcelReader excelReader;
        if (ExcelTypeEnum.XLSX == type) {
            // for the xlsx files, automatically obtained from the file
            excelReader = EasyExcel.read(file, new ParameterUse1904Listener(use1904windowing))
                    .excelType(type)
                    .head(ParameterData.class)
                    .build();
        } else {
            excelReader = EasyExcel.read(file, new ParameterUse1904Listener(use1904windowing))
                    .use1904windowing(use1904windowing) // user-defined settings
                    .excelType(type)
                    .head(ParameterData.class)
                    .build();
        }
        excelReader.readAll();
        excelReader.finish();
    }

    private List<ParameterData> data() {
        List<ParameterData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ParameterData simpleData = new ParameterData();
            simpleData.setName("姓名" + i);
            simpleData.setDate(new Date());
            list.add(simpleData);
        }
        return list;
    }
}
