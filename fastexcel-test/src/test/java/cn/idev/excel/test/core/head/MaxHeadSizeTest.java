package cn.idev.excel.test.core.head;

import cn.idev.excel.FastExcel;
import cn.idev.excel.support.ExcelTypeEnum;
import cn.idev.excel.test.util.TestFileUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Map;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class MaxHeadSizeTest {
    private static final Logger log = LoggerFactory.getLogger(MaxHeadSizeTest.class);

    private static String headFile01;
    private static String headFile02;
    private static String headFile03;

    @BeforeAll
    public static void init() {
        headFile01 = TestFileUtil.getPath() + "temp/issue220" + File.separator + "test01.xlsx";
        headFile02 = TestFileUtil.getPath() + "temp/issue220" + File.separator + "test02.xlsx";
        headFile03 = TestFileUtil.getPath() + "temp/issue220" + File.separator + "test03.xlsx";
    }

    @Test
    public void t01ReadTest() throws Exception {
        // issue example
        readFileWithMap(headFile01, 6);
        readFileWithPOJO(headFile01);
    }

    @Test
    public void t02ReadTest() throws Exception {
        // 表头有空列
        readFileWithMap(headFile02, 8);
        readFileWithPOJO(headFile02);
    }

    @Test
    public void t03ReadTest() throws Exception {
        // 表头列数比实际数据行的列少
        readFileWithMap(headFile03, 4);
        readFileWithPOJO(headFile03);
    }

    private void readFileWithMap(String file, int expectHeadSize) {
        List<Map<Integer, String>> dataList;
        // default
        dataList = FastExcel.read(file)
            .excelType(ExcelTypeEnum.XLSX)
            .sheet()
            .doReadSync();
        dataList.forEach(d -> {
            log.info(JSON.toJSONString(d, JSONWriter.Feature.WriteMapNullValue));
            Assertions.assertTrue(d.size() >= expectHeadSize);
        });

        // custom listener
        dataList = FastExcel.read(file, new MaxHeadReadListener(expectHeadSize))
            .excelType(ExcelTypeEnum.XLSX)
            .sheet()
            .doReadSync();
        dataList.forEach(d -> {
            log.info(JSON.toJSONString(d, JSONWriter.Feature.WriteMapNullValue));
            Assertions.assertTrue(d.size() >= expectHeadSize);
        });
    }

    private void readFileWithPOJO(String file) {
        List<MaxHeadSizeData> dataList = FastExcel.read(file).head(MaxHeadSizeData.class).excelType(ExcelTypeEnum.XLSX)
            .sheet()
            .doReadSync();
        dataList.forEach(d -> {
            log.info(JSON.toJSONString(d, JSONWriter.Feature.WriteMapNullValue));
        });
    }
}
