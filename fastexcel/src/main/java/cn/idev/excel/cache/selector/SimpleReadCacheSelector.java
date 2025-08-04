package cn.idev.excel.cache.selector;

import cn.idev.excel.cache.Ehcache;
import cn.idev.excel.cache.MapCache;
import cn.idev.excel.cache.ReadCache;
import java.io.IOException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.opc.PackagePart;

/**
 * Simple cache selector
 *
 *
 **/
@Getter
@Setter
@EqualsAndHashCode
@Slf4j
public class SimpleReadCacheSelector implements ReadCacheSelector {

    /**
     * Convert bytes to megabytes
     */
    private static final long B2M = 1000 * 1000L;
    /**
     * If it's less than 5M, use map cache, or use ehcache.unit MB.
     */
    private static final long DEFAULT_MAX_USE_MAP_CACHE_SIZE = 5;

    /**
     * Maximum batch of `SharedStrings` stored in memory.
     * The batch size is 100.{@link Ehcache#BATCH_COUNT}
     */
    private static final int DEFAULT_MAX_EHCACHE_ACTIVATE_BATCH_COUNT = 20;

    /**
     * Shared strings exceeding this value will use {@link Ehcache},or use {@link MapCache}.unit MB.
     */
    private Long maxUseMapCacheSize;

    /**
     * Maximum size of cache activation.unit MB.
     *
     * @deprecated Please use maxCacheActivateBatchCount to control the size of the occupied memory
     */
    @Deprecated
    private Integer maxCacheActivateSize;

    /**
     * Maximum batch of `SharedStrings` stored in memory.
     * The batch size is 100.{@link Ehcache#BATCH_COUNT}
     */
    private Integer maxCacheActivateBatchCount;

    public SimpleReadCacheSelector() {}

    /**
     * Parameter maxCacheActivateSize has already been abandoned
     *
     * @param maxUseMapCacheSize
     * @param maxCacheActivateSize
     */
    @Deprecated
    public SimpleReadCacheSelector(Long maxUseMapCacheSize, Integer maxCacheActivateSize) {
        this.maxUseMapCacheSize = maxUseMapCacheSize;
        this.maxCacheActivateSize = maxCacheActivateSize;
    }

    @Override
    public ReadCache readCache(PackagePart sharedStringsTablePackagePart) {
        long size = sharedStringsTablePackagePart.getSize();
        if (size < 0) {
            try {
                size = sharedStringsTablePackagePart.getInputStream().available();
            } catch (IOException e) {
                log.warn("Unable to get file size, default used MapCache");
                return new MapCache();
            }
        }
        if (maxUseMapCacheSize == null) {
            maxUseMapCacheSize = DEFAULT_MAX_USE_MAP_CACHE_SIZE;
        }
        if (size < maxUseMapCacheSize * B2M) {
            if (log.isDebugEnabled()) {
                log.debug("Use map cache.size:{}", size);
            }
            return new MapCache();
        }
        if (log.isDebugEnabled()) {
            log.debug("Use ehcache.size:{}", size);
        }

        // In order to be compatible with the code
        // If the user set up `maxCacheActivateSize`, then continue using it
        if (maxCacheActivateSize != null) {
            return new Ehcache(maxCacheActivateSize, maxCacheActivateBatchCount);
        } else {
            if (maxCacheActivateBatchCount == null) {
                maxCacheActivateBatchCount = DEFAULT_MAX_EHCACHE_ACTIVATE_BATCH_COUNT;
            }
            return new Ehcache(maxCacheActivateSize, maxCacheActivateBatchCount);
        }
    }
}
