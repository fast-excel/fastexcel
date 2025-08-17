package cn.idev.excel.test.core.writesheet;

import cn.idev.excel.ExcelWriter;
import cn.idev.excel.FastExcel;
import cn.idev.excel.support.ExcelTypeEnum;
import cn.idev.excel.test.util.TestFileUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WriteSheetTest {

    @Test
    public void testSheetOrder03() {
        // normal
        testSheetOrderInternal(ExcelTypeEnum.XLS, Arrays.asList(0));
        testSheetOrderInternal(ExcelTypeEnum.XLS, Arrays.asList(0, 1, 2));
        // sheetNo is bigger than the size of workbook sheet num
        testSheetOrderInternal(ExcelTypeEnum.XLS, Arrays.asList(2));
        testSheetOrderInternal(ExcelTypeEnum.XLS, Arrays.asList(10, 6, 8));
        // negative numbers
        testSheetOrderInternal(ExcelTypeEnum.XLS, Arrays.asList(-1));
        testSheetOrderInternal(ExcelTypeEnum.XLS, Arrays.asList(-8, -10, -6));
        testSheetOrderInternal(ExcelTypeEnum.XLS, Arrays.asList(-8, 6));
    }

    @Test
    public void testSheetOrder07() {
        // normal
        testSheetOrderInternal(ExcelTypeEnum.XLSX, Arrays.asList(0));
        testSheetOrderInternal(ExcelTypeEnum.XLSX, Arrays.asList(0, 1, 2));
        // sheetNo is bigger than the size of workbook sheet num
        testSheetOrderInternal(ExcelTypeEnum.XLSX, Arrays.asList(2));
        testSheetOrderInternal(ExcelTypeEnum.XLSX, Arrays.asList(10, 6, 8));
        // negative numbers
        testSheetOrderInternal(ExcelTypeEnum.XLSX, Arrays.asList(-1));
        testSheetOrderInternal(ExcelTypeEnum.XLSX, Arrays.asList(-8, -10, -6));
        testSheetOrderInternal(ExcelTypeEnum.XLSX, Arrays.asList(-8, 6));
    }

    private void testSheetOrderInternal(ExcelTypeEnum excelTypeEnum, List<Integer> sheetNoList) {
        Map<Integer, Integer> dataMap = initSheetDataSizeList(sheetNoList);

        File testFile = TestFileUtil.createNewFile("writesheet/write-sheet-order" + excelTypeEnum.getValue());
        // write a file in the order of sheetNoList.
        try (ExcelWriter excelWriter = FastExcel.write(testFile, WriteSheetData.class)
                .excelType(excelTypeEnum)
                .build()) {
            for (Integer sheetNo : sheetNoList) {
                excelWriter.write(
                        dataList(dataMap.get(sheetNo)),
                        FastExcel.writerSheet(sheetNo).build());
            }
        }

        for (int i = 0; i < sheetNoList.size(); i++) {
            List<WriteSheetData> sheetDataList = FastExcel.read(testFile)
                    .excelType(excelTypeEnum)
                    .head(WriteSheetData.class)
                    .sheet(i)
                    .doReadSync();
            Assertions.assertEquals(dataMap.get(sheetNoList.get(i)), sheetDataList.size());
        }
    }

    private Map<Integer, Integer> initSheetDataSizeList(List<Integer> sheetNoList) {
        // sort by sheetNo
        Collections.sort(sheetNoList);
        // key: sheetNo
        // value: data size
        Map<Integer, Integer> dataMap = new HashMap<>();
        for (int i = 0; i < sheetNoList.size(); i++) {
            dataMap.put(sheetNoList.get(i), i + 1);
        }
        return dataMap;
    }

    private static List<WriteSheetData> dataList(int size) {
        List<WriteSheetData> dataList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            WriteSheetData data = new WriteSheetData();
            data.setString("String" + i);
            dataList.add(data);
        }
        return dataList;
    }
}
