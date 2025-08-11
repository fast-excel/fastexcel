package cn.idev.excel.test.temp;

import cn.idev.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 临时测试
 *
 *
 **/
@Slf4j
public class LockTest {

    @Test
    public void test() throws Exception {
        List<Object> list = EasyExcel.read(new FileInputStream("src/test/resources/simple/simple07.xlsx"))
                .useDefaultListener(false)
                .doReadAllSync();
        for (Object data : list) {
            log.info("返回数据：{}", JSON.toJSONString(data));
        }
    }

    @Test
    public void test2() throws Exception {
        List<Object> list = EasyExcel.read(new FileInputStream("src/test/resources/simple/simple07.xlsx"))
                .sheet()
                .headRowNumber(0)
                .doReadSync();
        for (Object data : list) {
            log.info("返回数据：{}", ((Map) data).size());
            log.info("返回数据：{}", JSON.toJSONString(data));
        }
    }
}
