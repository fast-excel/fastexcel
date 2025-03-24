package cn.idev.excel.test.temp.read;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.cache.Ehcache;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 临时测试
 *
 * @author Jiaju Zhuang
 **/

public class HeadReadTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(HeadReadTest.class);
    
    @Test
    public void test() throws Exception {
        File file = new File("src/test/resources/cache/t2.xlsx");
        EasyExcel.read(file, HeadReadData.class, new HeadListener()).ignoreEmptyRow(false).sheet(0).doRead();
        
    }
    
    @Test
    public void testCache() throws Exception {
        File file = new File("src/test/resources/cache/headt1.xls");
        EasyExcel.read(file, HeadReadData.class, new HDListener()).readCache(new Ehcache(20)).sheet(0).doRead();
        
        LOGGER.info("------------------");
        EasyExcel.read(file, HeadReadData.class, new HDListener()).readCache(new Ehcache(20)).sheet(0).doRead();
        LOGGER.info("------------------");
        EasyExcel.read(file, HeadReadData.class, new HDListener()).readCache(new Ehcache(20)).sheet(0).doRead();
    }
    
}
