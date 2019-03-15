package com.cs.base.common.cache.core;


import com.cs.base.common.cache.core.message.Command;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * 缓存框架主要接口
 *
 * @author liyuan
 * @date 2019/2/20 16:02
 */
public interface ICache<K, V> {

    /**
     * Get an item from the cache
     *
     * @param key cache key
     * @return the cached object or null
     */
    V get(K key) throws CacheException;

    V get(K key, Class<V> vClass);

    /**
     * Get an item from the cache,if not exits ,get from loader
     *
     * @param key
     * @param loader
     * @return
     * @throws CacheException
     */
    V get(K key, Callable<V> loader) throws CacheException;

    Map<K, V> gets(Set<K> keys) throws CacheException;

    Map<K, V> gets(Set<K> keys, Resultable<K, V> loader) throws CacheException;

    /**
     * set get
     *
     * @param key
     * @return
     */
    Set<V> sGet(K key);

    Set<V> sGet(K key, Class<V> vClass);

    /**
     * set get
     *
     * @param key
     * @param loader
     * @return
     */
    Set<V> sGet(K key, Callable<Set<V>> loader);

    /**
     * list get
     *
     * @param key
     * @return
     */
    List<V> lGet(K key);

    List<V> lGet(K key, Class<V> vClass);

    List<V> lGet(K key, int start, int end, Class<V> vClass);

    List<V> lGet(K key, int start, int end);

    /**
     * list get
     *
     * @param key
     * @param loader
     * @return
     */
    List<V> lGet(K key, Callable<List<V>> loader);

    /**
     * map get
     *
     * @param key
     * @return
     */
    V hGet(K key, K field);

    V hGet(K key, K filed, Class<V> vClass);

    Map<K, V> hGet(K key);

    Map<K, V> hGet(K key, Class<V> vClass);

    /**
     * map get
     *
     * @param key
     * @param field
     * @param loader
     * @return
     */
    V hGet(K key, K field, Callable<V> loader);

    Map<K, V> hGet(K key, Callable<Map<K, V>> loader);

    Map<K, V> hGet(K key, Resultable<K, V> loader);

    /**
     * Add an item to the cache
     *
     * @param key   cache key
     * @param value cache value
     */
    void set(K key, V value) throws CacheException;

    void setString(K key, String value) throws CacheException;

    /**
     * Add an item to the cache
     * 只保存在二级缓存(redis缓存)
     *
     * @param key
     * @param value
     * @throws CacheException
     */
    void set2(K key, V value) throws CacheException;

    void setString2(K key, String value) throws CacheException;

    /**
     * set add
     *
     * @param key
     * @param values
     * @throws CacheException
     */
    void sAdd(K key, V... values) throws CacheException;

    void sAddString(K key, String... values) throws CacheException;

    /**
     * set add
     * 只保存在二级缓存(redis缓存)
     *
     * @param key
     * @param values
     * @throws CacheException
     */
    void sAdd2(K key, V... values) throws CacheException;

    void sAddString2(K key, String... values) throws CacheException;

    /**
     * list add
     *
     * @param key
     * @param values
     * @throws CacheException
     */
    void lAdd(K key, V... values) throws CacheException;

    void lAddWithIndex(K key, int index, String value) throws CacheException;

    void lAddWithIndexE(K key, int index, V value) throws CacheException;

    void lAddString(K key, String... values) throws CacheException;

    /**
     * list add
     * 只保存在二级缓存(redis缓存)
     *
     * @param key
     * @param values
     * @throws CacheException
     */
    void lAdd2(K key, V... values) throws CacheException;

    void lAddString2(K key, String... values) throws CacheException;

    /**
     * map add
     *
     * @param key
     * @param mKey
     * @param value
     * @throws CacheException
     */
    void hSet(K key, K mKey, V value) throws CacheException;

    void hSetString(K key, K mKey, String value) throws CacheException;

    /**
     * map add
     * 只保存在二级缓存(redis缓存)
     *
     * @param key
     * @param mKey
     * @param value
     * @throws CacheException
     */
    void hSet2(K key, K mKey, V value) throws CacheException;

    void hSetString2(K key, K mKey, String value) throws CacheException;
    //  List<K> keys() throws CacheException;

    void del(K... keys) throws CacheException;

//    void sdel(K... keys) throws CacheException;

    void sdel(K key, K... member) throws CacheException;

    void ldel(K... keys) throws CacheException;

//    void hdel(K... keys) throws CacheException;

    void hdel(K key, K... field) throws CacheException;

    /**
     * Clear the cache
     */
    void clear() throws CacheException;

    /**
     * Clean up
     */
    void destroy() throws CacheException;

    void processMsg(Command cmd);

    void expire(K key, int seconds);

    boolean exist(K key);

    /**
     * Set集合 查询是否有该成员
     *
     * @param key
     * @param value
     * @return
     */
    boolean isMember(K key, V value);

    Long incr(K key);
}
