package cn.idev.excel.test.demo.write;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.test.util.TestFileUtil;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Class for testing colors
 *
 */
public class WriteWithColorTest {


    @Test
    public void write() {
        String fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, ColorDemoData.class)
            .sheet("模板")
            .doWrite(this::data);
        System.out.println(fileName);
    }

    private List<ColorDemoData> data() {
        List<ColorDemoData> list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new ColorDemoData("name" + i, i, "男"));
        }
        return list;
    }

}
