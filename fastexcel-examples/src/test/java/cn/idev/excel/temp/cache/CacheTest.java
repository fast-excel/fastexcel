package cn.idev.excel.temp.cache;

import cn.idev.excel.util.FileUtils;
import com.alibaba.fastjson2.JSON;
import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.junit.jupiter.api.Test;

/**
 *
 **/
@Slf4j
public class CacheTest {

    @Test
    public void cache() throws Exception {

        File readTempFile = FileUtils.createCacheTmpFile();

        File cacheFile = new File(readTempFile.getPath(), UUID.randomUUID().toString());
        PersistentCacheManager persistentCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence(cacheFile))
                .withCache(
                        "cache",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                Integer.class,
                                HashMap.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder().disk(10, MemoryUnit.GB)))
                .build(true);
        Cache<Integer, HashMap> cache = persistentCacheManager.getCache("cache", Integer.class, HashMap.class);

        HashMap<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "test");

        cache.put(1, map);
        log.info("dd1:{}", JSON.toJSONString(cache.get(1)));

        cache.clear();

        log.info("dd2:{}", JSON.toJSONString(cache.get(1)));
    }
}
