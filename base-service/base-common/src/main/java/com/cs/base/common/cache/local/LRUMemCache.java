package com.cs.base.common.cache.local;


import com.cs.base.common.cache.core.CacheException;
import com.cs.base.common.cache.core.ICache;
import com.cs.base.common.cache.core.Resultable;
import com.cs.base.common.cache.core.message.Command;
import org.apache.commons.collections.map.LRUMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author lijingwen
 * @date 2019/2/20 15:42
 */
public class LRUMemCache<K, V> implements ICache<K, V> {

    Logger log = LoggerFactory.getLogger(LRUMemCache.class);

    /**
     * 承载类
     */
    protected static LRUMap CACHE;
    protected static LRUMap LIST_CACHE;
    protected static LRUMap SET_CACHE;
    protected static LRUMap MAP_CACHE;

    public LRUMemCache() {
        init(500, 500, 500, 500);
    }

    public LRUMemCache(int size, int listSize, int setSize, int mapSize) {
        init(size, listSize, setSize, mapSize);
    }

    private void init(int size, int listSize, int setSize, int mapSize) {
        // TODO Auto-generated method stub
        CACHE = new LRUMap(size);
        LIST_CACHE = new LRUMap(listSize);
        SET_CACHE = new LRUMap(setSize);
        MAP_CACHE = new LRUMap(mapSize);
    }

    @Override
    public V get(K key) throws CacheException {
        // TODO Auto-generated method stub
        V ret = (V) CACHE.get(key);
        return ret;
    }

    @Override
    public V get(K key, Class<V> vClass) {
        return null;
    }

    @Override
    public V get(K key, Callable<V> loader) throws CacheException {
        // TODO Auto-generated method stub
        V ret = get(key);

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

    @Override
    public Map<K, V> gets(Set<K> keys) throws CacheException {
        // TODO Auto-generated method stub
        Map<K, V> ret = null;
        if (keys != null && keys.size() > 0) {
            ret = new HashMap<>(keys.size());
            V v = null;
            for (K k : keys) {
                v = get(k);
                // 不为空才放到返回值里
                if (v != null) {
                    ret.put(k, v);
                }
            }
        }
        return ret;
    }

    @Override
    public Map<K, V> gets(Set<K> keys, Resultable<K, V> loader) throws CacheException {
        // TODO Auto-generated method stub
        // 可能为空
        Map<K, V> foundV = gets(keys);
        // 返回
        Map<K, V> ret = null;
        // 找到了所有的
        if (foundV != null && foundV.size() == keys.size()) {
            ret = foundV;
        } else {
            Set<K> notFoundK = new HashSet<>(keys);

            if (foundV != null) {
                // 找到的部分先加入
                ret = new HashMap<>(foundV);

                // 处理去调用loader
                Set<K> foundK = foundV.keySet();
                notFoundK.removeAll(foundK);
            }

            // 查到的返回value，查不到的返回key
            Map<K, V> notFoundV = loader.result(foundV, notFoundK);
            if (notFoundV != null && notFoundV.size() > 0) {
                if (ret == null) {
                    ret = new HashMap<>();
                }
                ret.putAll(notFoundV);

                // 回写从回调里拿到的数据
                for (Map.Entry<K, V> entry : notFoundV.entrySet()) {
                    set(entry.getKey(), entry.getValue());
                }
            }
        }

        return ret;
    }

    @Override
    public Set<V> sGet(K key) {
        // TODO Auto-generated method stub
        Set<V> ret = (Set<V>) SET_CACHE.get(key);
        return ret;
    }

    @Override
    public Set<V> sGet(K key, Class<V> vClass) {
        return null;
    }

    @Override
    public Set<V> sGet(K key, Callable<Set<V>> loader) {
        // TODO Auto-generated method stub
        Set<V> ret = (Set<V>) SET_CACHE.get(key);

        // 处理可能的回调填充数据，没有处理回调的并发
        if (ret == null) {
            if (loader != null) {
                try {
                    ret = loader.call();
                    if (ret != null) {
                        // 回写
                        sAdd(key, (V[]) ret.toArray());
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return ret;
    }

    @Override
    public List<V> lGet(K key) {
        // TODO Auto-generated method stub
        List<V> ret = (List<V>) LIST_CACHE.get(key);
        return ret;
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
        // TODO Auto-generated method stub
        List<V> ret = (List<V>) LIST_CACHE.get(key);

        // 处理可能的回调填充数据，没有处理回调的并发
        if (ret == null) {
            if (loader != null) {
                try {
                    ret = loader.call();
                    if (ret != null) {
                        // 回写
                        lAdd(key, (V[]) ret.toArray());
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return ret;
    }

    @Override
    public V hGet(K key, K field) {
        Map<K, V> map = hGet(key);
        V ret = null;
        if (map != null) {
            ret = map.get(field);
        }

        return ret;
    }

    @Override
    public V hGet(K key, K filed, Class<V> vClass) {
        return null;
    }

    @Override
    public Map<K, V> hGet(K key) {
        // TODO Auto-generated method stub
        Map<K, V> ret = (Map<K, V>) MAP_CACHE.get(key);
        return ret;
    }

    @Override
    public Map<K, V> hGet(K key, Class<V> vClass) {
        return null;
    }

    @Override
    public V hGet(K key, K field, Callable<V> loader) {
        V ret = hGet(key, field);
        if (ret == null) {
            if (loader != null) {
                try {
                    ret = loader.call();
                    if (ret != null) {
                        hSet(key, field, ret);
                    }
                } catch (Exception e) {
                    log.warn("回调出现异常：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    @Override
    public Map<K, V> hGet(K key, Callable<Map<K, V>> loader) {
        // TODO Auto-generated method stub
        Map<K, V> ret = (Map<K, V>) MAP_CACHE.get(key);

        // 处理可能的回调填充数据，没有处理回调的并发
        if (ret == null) {
            if (loader != null) {
                try {
                    ret = loader.call();
                    if (ret != null) {
                        // 回写
                        for (K k : ret.keySet()) {
                            hSet(key, k, ret.get(k));
                        }
                    }
                } catch (Exception e) {
                    log.warn("回调出现异常：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        return ret;
    }

    @Override
    public Map<K, V> hGet(K key, Resultable<K, V> loader) {
        return null;
    }

    @Override
    public void set(K key, V value) throws CacheException {
        // TODO Auto-generated method stub
        CACHE.put(key, value);
    }

    @Override
    public void setString(K key, String value) throws CacheException {
        CACHE.put(key, value);
    }

    @Override
    public void set2(K key, V value) throws CacheException {
        set(key, value);
    }

    @Override
    public void setString2(K key, String value) throws CacheException {
        setString(key, value);
    }

    @Override
    public void sAdd(K key, V... values) throws CacheException {
        // TODO Auto-generated method stub
        Object o = SET_CACHE.get(key);
        ConcurrentHashSet<V> set = null;
        if (o != null) {
            set = (ConcurrentHashSet<V>) o;
        } else {
            set = new ConcurrentHashSet<>();
        }

        for (V v : values) {
            set.add(v);
        }
        SET_CACHE.put(key, set);
    }

    @Override
    public void sAddString(K key, String... values) throws CacheException {
        Object o = SET_CACHE.get(key);
        ConcurrentHashSet<String> set = null;
        if (o != null) {
            set = (ConcurrentHashSet<String>) o;
        } else {
            set = new ConcurrentHashSet<>();
        }

        for (String v : values) {
            set.add(v);
        }
        SET_CACHE.put(key, set);
    }

    @Override
    public void sAdd2(K key, V... values) throws CacheException {
        sAdd(key, values);
    }

    @Override
    public void sAddString2(K key, String... values) throws CacheException {
        sAddString(key, values);
    }

    @Override
    public void lAdd(K key, V... values) throws CacheException {
        // TODO Auto-generated method stub
        Object o = LIST_CACHE.get(key);
        CopyOnWriteArrayList<V> list = null;
        if (o != null) {
            list = (CopyOnWriteArrayList<V>) o;
        } else {
            list = new CopyOnWriteArrayList<>();
        }

        for (V v : values) {
            list.add(v);
        }

        LIST_CACHE.put(key, list);
    }

    @Override
    public void lAddWithIndex(K key, int index, String value) throws CacheException {

    }

    @Override
    public void lAddWithIndexE(K key, int index, V value) throws CacheException {

    }

    @Override
    public void lAddString(K key, String... values) throws CacheException {
        Object o = LIST_CACHE.get(key);
        CopyOnWriteArrayList<String> list = null;
        if (o != null) {
            list = (CopyOnWriteArrayList<String>) o;
        } else {
            list = new CopyOnWriteArrayList<>();
        }

        for (String v : values) {
            list.add(v);
        }

        LIST_CACHE.put(key, list);
    }

    @Override
    public void lAdd2(K key, V... values) throws CacheException {
        lAdd(key, values);
    }

    @Override
    public void lAddString2(K key, String... values) throws CacheException {
        lAddString(key, values);
    }

    @Override
    public void hSet(K key, K mKey, V value) throws CacheException {
        // TODO Auto-generated method stub
        Object o = MAP_CACHE.get(key);
        ConcurrentHashMap<K, V> map = null;
        if (o != null) {
            map = (ConcurrentHashMap<K, V>) o;
        } else {
            map = new ConcurrentHashMap<>();
        }
        map.put(mKey, value);
        MAP_CACHE.put(key, map);
    }

    @Override
    public void hSetString(K key, K mKey, String value) throws CacheException {
        Object o = MAP_CACHE.get(key);
        ConcurrentHashMap<K, String> map = null;
        if (o != null) {
            map = (ConcurrentHashMap<K, String>) o;
        } else {
            map = new ConcurrentHashMap<>();
        }
        map.put(mKey, value);
        MAP_CACHE.put(key, map);
    }

    @Override
    public void hSet2(K key, K mKey, V value) throws CacheException {
        hSet(key, mKey, value);
    }

    @Override
    public void hSetString2(K key, K mKey, String value) throws CacheException {
        hSetString(key, mKey, value);
    }

    @Override
    public void del(K... keys) throws CacheException {
        // TODO Auto-generated method stub
        for (K k : keys) {
            CACHE.remove(k);
        }
    }

    @Override
    public void sdel(K key, K... member) throws CacheException {
        ConcurrentHashSet<V> kSet = (ConcurrentHashSet<V>) SET_CACHE.get(key);
        kSet.removeAll(Arrays.asList(member));
        V[] values = null;
        if (kSet.size() > 0) {
            int i = 0;
            while (kSet.iterator().hasNext()) {
                values[i] = kSet.iterator().next();
                i++;
            }
        }
        sAdd(key, values);
    }

    @Override
    public void ldel(K... keys) throws CacheException {
        // TODO Auto-generated method stub
        for (K k : keys) {
            LIST_CACHE.remove(k);
        }
    }

    @Override
    public void hdel(K key, K... field) throws CacheException {
        Map<K, V> ret = hGet(key);
        for (K k : field) {
            ret.remove(k);
        }
        ConcurrentHashMap<K, V> map = new ConcurrentHashMap<K, V>();
        for (Map.Entry entry : ret.entrySet()) {
            map.put((K) entry.getKey(), (V) entry.getValue());
        }
        MAP_CACHE.put(key, map);
    }

    @Override
    public void clear() throws CacheException {
        // TODO Auto-generated method stub
        CACHE.clear();
        LIST_CACHE.clear();
        SET_CACHE.clear();
        MAP_CACHE.clear();
    }

    @Override
    public void destroy() throws CacheException {
        // TODO Auto-generated method stub
        CACHE = null;
        LIST_CACHE = null;
        SET_CACHE = null;
        MAP_CACHE = null;
    }

    @Override
    public void processMsg(Command cmd) {
        // TODO Auto-generated method stub

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
        Set<V> set = sGet(key);
        if (set != null) {
            return set.contains(String.valueOf(value));
        }
        return false;
    }

    @Override
    public Long incr(K key) {
        return null;
    }

}
