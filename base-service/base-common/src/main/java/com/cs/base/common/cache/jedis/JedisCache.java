package com.cs.base.common.cache.jedis;

import com.cs.base.common.cache.core.CacheException;
import com.cs.base.common.cache.core.ICache;
import com.cs.base.common.cache.core.Resultable;
import com.cs.base.common.cache.core.message.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;


/**
 * @author lijingwen
 * @date 2019/2/20 19:18
 */
public class JedisCache<K, V> implements ICache<K, V> {

    private Logger log = LoggerFactory.getLogger(JedisCache.class);


    /**
     * 缓存穿透
     *
     * @param ret
     * @param loader
     * @return
     */
    private V cachePenetrate(K key, V ret, Callable<V> loader) {
        // 处理可能的回调填充数据，没有处理回调的并发
        if (ret == null) {
            if (loader != null) {
                try {
                    ret = loader.call();
                    if (ret != null) {
                        // 回写
                        set(key, ret);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    private String parseKey(K key) {
        // TODO Auto-generated method stub
        String k = null;
        if (key instanceof String) {
            k = (String) key;
        } else if (key instanceof byte[]) {
            k = new String((byte[]) key);
        } else {
            k = key.toString();
        }
        return k;
    }

    @Override
    public V get(K key) throws CacheException {
        return null;
    }

    @Override
    public V get(K key, Class<V> vClass) {
        return null;
    }

    @Override
    public V get(K key, Callable<V> loader) throws CacheException {
        return null;
    }

    @Override
    public Map<K, V> gets(Set<K> keys) throws CacheException {
        return null;
    }

    @Override
    public Map<K, V> gets(Set<K> keys, Resultable<K, V> loader) throws CacheException {
        return null;
    }

    @Override
    public Set<V> sGet(K key) {
        return null;
    }

    @Override
    public Set<V> sGet(K key, Class<V> vClass) {
        return null;
    }

    @Override
    public Set<V> sGet(K key, Callable<Set<V>> loader) {
        return null;
    }

    @Override
    public List<V> lGet(K key) {
        return null;
    }

    @Override
    public List<V> lGet(K key, Class<V> vClass) {
        return null;
    }

    @Override
    public List<V> lGet(K key, int start, int end, Class<V> vClass) {
        return null;
    }

    @Override
    public List<V> lGet(K key, int start, int end) {
        return null;
    }

    @Override
    public List<V> lGet(K key, Callable<List<V>> loader) {
        return null;
    }

    @Override
    public V hGet(K key, K field) {
        return null;
    }

    @Override
    public V hGet(K key, K filed, Class<V> vClass) {
        return null;
    }

    @Override
    public Map<K, V> hGet(K key) {
        return null;
    }

    @Override
    public Map<K, V> hGet(K key, Class<V> vClass) {
        return null;
    }

    @Override
    public V hGet(K key, K field, Callable<V> loader) {
        return null;
    }

    @Override
    public Map<K, V> hGet(K key, Callable<Map<K, V>> loader) {
        return null;
    }

    @Override
    public Map<K, V> hGet(K key, Resultable<K, V> loader) {
        return null;
    }

    @Override
    public void set(K key, V value) throws CacheException {

    }

    @Override
    public void setString(K key, String value) throws CacheException {

    }

    @Override
    public void set2(K key, V value) throws CacheException {

    }

    @Override
    public void setString2(K key, String value) throws CacheException {

    }

    @Override
    public void sAdd(K key, V... values) throws CacheException {

    }

    @Override
    public void sAddString(K key, String... values) throws CacheException {

    }

    @Override
    public void sAdd2(K key, V... values) throws CacheException {

    }

    @Override
    public void sAddString2(K key, String... values) throws CacheException {

    }

    @Override
    public void lAdd(K key, V... values) throws CacheException {

    }

    @Override
    public void lAddWithIndex(K key, int index, String value) throws CacheException {

    }

    @Override
    public void lAddWithIndexE(K key, int index, V value) throws CacheException {

    }

    @Override
    public void lAddString(K key, String... values) throws CacheException {

    }

    @Override
    public void lAdd2(K key, V... values) throws CacheException {

    }

    @Override
    public void lAddString2(K key, String... values) throws CacheException {

    }

    @Override
    public void hSet(K key, K mKey, V value) throws CacheException {

    }

    @Override
    public void hSetString(K key, K mKey, String value) throws CacheException {

    }

    @Override
    public void hSet2(K key, K mKey, V value) throws CacheException {

    }

    @Override
    public void hSetString2(K key, K mKey, String value) throws CacheException {

    }

    @Override
    public void del(K... keys) throws CacheException {

    }

    @Override
    public void sdel(K key, K... member) throws CacheException {

    }

    @Override
    public void ldel(K... keys) throws CacheException {

    }

    @Override
    public void hdel(K key, K... field) throws CacheException {

    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public void destroy() throws CacheException {

    }

    @Override
    public void processMsg(Command cmd) {

    }

    @Override
    public void expire(K key, int seconds) {

    }

    @Override
    public boolean exist(K key) {
        return false;
    }

    @Override
    public boolean isMember(K key, V value) {
        return false;
    }

    @Override
    public Long incr(K key) {
        return null;
    }
}
