package com.cs.base.common.cache.core;

import java.util.Properties;

/**
 * @author liyuan
 * @date 2019/2/20 16:05
 */
public interface ICacheProvider {

    /**
     * 缓存的标识名称
     *
     * @return return cache provider name
     */
    String name();

    /**
     * Configure the cache
     *
     * @param regionName the name of the cache region
     * @param autoCreate autoCreate settings
     * @param listener   listener for expired elements
     * @return return cache instance
     * @throws CacheException cache exception
     */
    ICache buildCache(String regionName, boolean autoCreate, CacheExpiredListener listener)
            throws CacheException;

    /**
     * Callback to perform any necessary initialization of the underlying cache
     * implementation during SessionFactory construction.
     *
     * @param props current configuration settings.
     */
    void start(Properties props) throws CacheException;

    /**
     * Callback to perform any necessary cleanup of the underlying cache
     * implementation during SessionFactory.close().
     */
    void stop();
}
