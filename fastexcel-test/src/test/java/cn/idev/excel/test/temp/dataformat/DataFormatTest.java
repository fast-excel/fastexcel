package cn.idev.excel.test.temp.dataformat;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.metadata.data.FormulaData;
import cn.idev.excel.test.core.dataformat.DateFormatData;
import cn.idev.excel.test.util.TestFileUtil;
import com.alibaba.fastjson2.JSON;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

/**
 * 格式测试
 *
 *
 **/
@Slf4j
public class DataFormatTest {

    @Test
    public void test() throws Exception {

        File file = TestFileUtil.readFile("dataformat" + File.separator + "dataformat.xlsx");

        List<DataFormatData> list = EasyExcel.read(file, DataFormatData.class, null)
                .sheet()
                .headRowNumber(1)
                .doReadSync();
        log.info("数据：{}", list.size());
        for (DataFormatData data : list) {
            cn.idev.excel.metadata.data.DataFormatData dataFormat =
                    data.getDate().getDataFormatData();

            FormulaData dataFormatString = data.getDate().getFormulaData();

            if (dataFormat == null || dataFormatString == null) {

            } else {
                log.info(
                        "格式化：{};{}：{}",
                        dataFormat.getIndex(),
                        dataFormatString.getFormulaValue(),
                        DateUtil.isADateFormat(dataFormat.getIndex(), dataFormatString.getFormulaValue()));
            }

            log.info("返回数据：{}", JSON.toJSONString(data));
        }
    }

    @Test
    public void testxls() throws Exception {
        File file = TestFileUtil.readFile("dataformat" + File.separator + "dataformat.xls");

        List<DataFormatData> list = EasyExcel.read(file, DataFormatData.class, null)
                .sheet()
                .headRowNumber(1)
                .doReadSync();
        log.info("数据：{}", list.size());
        for (DataFormatData data : list) {
            cn.idev.excel.metadata.data.DataFormatData dataFormat =
                    data.getDate().getDataFormatData();

            FormulaData dataFormatString = data.getDate().getFormulaData();

            if (dataFormat == null || dataFormatString == null) {

            } else {
                log.info(
                        "格式化：{};{}：{}",
                        dataFormat.getIndex(),
                        dataFormatString.getFormulaValue(),
                        DateUtil.isADateFormat(dataFormat.getIndex(), dataFormatString.getFormulaValue()));
            }

            log.info("返回数据：{}", JSON.toJSONString(data));
        }
    }

    @Test
    public void test3() throws IOException {

        File file = TestFileUtil.readFile("dataformat" + File.separator + "dataformat.xlsx");
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getAbsoluteFile().getAbsolutePath());
        Sheet xssfSheet = xssfWorkbook.getSheetAt(0);
        Cell cell = xssfSheet.getRow(0).getCell(0);
        DataFormatter d = new DataFormatter();
        System.out.println(d.formatCellValue(cell));
    }

    @Test
    public void test31() throws IOException {
        System.out.println(DateUtil.isADateFormat(181, "[DBNum1][$-404]m\"\u6708\"d\"\u65e5\";@"));
    }

    @Test
    public void test43() throws IOException {
        SimpleDateFormat s = new SimpleDateFormat("yyyy'年'm'月'd'日' h'点'mm'哈哈哈m'");
        System.out.println(s.format(new Date()));
    }

    @Test
    public void test463() throws IOException {
        SimpleDateFormat s = new SimpleDateFormat("[$-804]yyyy年m月");
        System.out.println(s.format(new Date()));
    }

    @Test
    public void test1() throws Exception {
        System.out.println(DateUtil.isADateFormat(181, "yyyy\"年啊\"m\"月\"d\"日\"\\ h"));
        System.out.println(DateUtil.isADateFormat(180, "yyyy\"年\"m\"月\"d\"日\"\\ h\"点\""));
    }

    @Test
    public void test2() throws Exception {
        List<String> list1 = new ArrayList<String>(3000);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            list1.clear();
        }
        System.out.println("end:" + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            list1 = new ArrayList<String>(3000);
        }
        System.out.println("end:" + (System.currentTimeMillis() - start));
    }

    @Test
    public void tests() throws IOException, InvalidFormatException {
        SimpleDateFormat s1 = new SimpleDateFormat("yyyy\"5E74\"m\"6708\"d\"65E5\"");
        System.out.println(s1.format(new Date()));
        s1 = new SimpleDateFormat("yyyy年m月d日");
        System.out.println(s1.format(new Date()));
    }

    @Test
    public void tests1() throws IOException, InvalidFormatException {

        File file = TestFileUtil.readFile("dataformat" + File.separator + "dataformat.xlsx");
        List<DateFormatData> list =
                EasyExcel.read(file, DateFormatData.class, null).sheet().doReadSync();
        for (DateFormatData data : list) {
            log.info("返回:{}", JSON.toJSONString(data));
        }
    }

    @Test
    public void tests3() throws IOException, InvalidFormatException {
        SimpleDateFormat s1 = new SimpleDateFormat("ah\"时\"mm\"分\"");
        System.out.println(s1.format(new Date()));
    }

    private static final Pattern date_ptrn6 = Pattern.compile("^.*(年|月|日|时|分|秒)+.*$");

    @Test
    public void tests34() throws IOException, InvalidFormatException {
        System.out.println(date_ptrn6.matcher("2017但是").matches());
    }
}
