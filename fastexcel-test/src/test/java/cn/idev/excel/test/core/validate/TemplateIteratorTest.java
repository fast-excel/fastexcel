package cn.idev.excel.test.core.validate;

import cn.idev.excel.FastExcel;
import cn.idev.excel.write.metadata.fill.FillConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author wangmeng
 * @since 2025/2/20
 */
public class TemplateIteratorTest {

    public static void main(String[] args) throws IOException {
        write();
    }

    public static void write() {
        String fileName = "D:\\tmp\\" + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        FastExcel.write(fileName)
                .withTemplate("D:\\tmp\\template.xlsx")
                .sheet()
                .doFill(data(), FillConfig.builder().forceNewRow(true).build());
        System.out.println(fileName);
    }

    private static List<Map<String, String>> data() {
        List<Map<String, String>> list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name", "name- " + i);
            map.put("number", String.valueOf(i));
            list.add(map);
        }
        return list;
    }


}
