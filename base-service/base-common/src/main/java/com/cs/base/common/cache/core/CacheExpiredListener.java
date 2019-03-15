package com.cs.base.common.cache.core;

/**
 * @author liyuan
 * @date 2019/2/20 16:01
 */
public interface CacheExpiredListener {
    /**
     * 当缓存中的某个对象超时被清除的时候触发
     *
     * @param region: Cache region name
     * @param key:    cache key
     */
    void notifyElementExpired(String region, Object key);
}
