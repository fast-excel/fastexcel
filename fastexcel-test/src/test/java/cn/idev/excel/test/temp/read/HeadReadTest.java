package cn.idev.excel.test.temp.read;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.cache.Ehcache;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 临时测试
 *
 *
 **/
@Slf4j
public class HeadReadTest {

    @Test
    public void test() throws Exception {
        File file = new File("src/test/resources/cache/t2.xlsx");
        EasyExcel.read(file, HeadReadData.class, new HeadListener())
                .ignoreEmptyRow(false)
                .sheet(0)
                .doRead();
    }

    @Test
    public void testCache() throws Exception {
        File file = new File("src/test/resources/cache/headt1.xls");
        EasyExcel.read(file, HeadReadData.class, new HDListener())
                .readCache(new Ehcache(20))
                .sheet(0)
                .doRead();

        log.info("------------------");
        EasyExcel.read(file, HeadReadData.class, new HDListener())
                .readCache(new Ehcache(20))
                .sheet(0)
                .doRead();
        log.info("------------------");
        EasyExcel.read(file, HeadReadData.class, new HDListener())
                .readCache(new Ehcache(20))
                .sheet(0)
                .doRead();
    }
}
