package cn.idev.excel.test.temp;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.support.cglib.beans.BeanMap;
import cn.idev.excel.support.cglib.core.DebuggingClassWriter;
import cn.idev.excel.util.BeanMapUtils;
import com.alibaba.fastjson2.JSON;
import java.nio.file.Path;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * 临时测试
 *
 *
 **/
@Slf4j
public class Xls03Test {

    @TempDir
    Path tempDir;

    @Test
    public void test() {
        List<Object> list = EasyExcel.read("src/test/resources/compatibility/t07.xlsx")
                .sheet()
                .doReadSync();
        for (Object data : list) {
            log.info("返回数据：{}", JSON.toJSONString(data));
        }
    }

    @Test
    public void test2() {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, tempDir.toString());

        CamlData camlData = new CamlData();
        // camlData.setTest("test2");
        // camlData.setAEst("test3");
        // camlData.setTEST("test4");

        BeanMap beanMap = BeanMapUtils.create(camlData);

        log.info("test:{}", beanMap.get("test"));
        log.info("test:{}", beanMap.get("Test"));
        log.info("test:{}", beanMap.get("TEst"));
        log.info("test:{}", beanMap.get("TEST"));
    }
}
