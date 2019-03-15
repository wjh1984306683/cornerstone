package com.cs.base.common.cache;

import com.cs.base.common.cache.core.CacheException;
import com.cs.base.common.cache.core.ICache;
import com.cs.base.common.cache.core.ILock;
import com.cs.base.common.cache.core.Resultable;
import com.cs.base.common.cache.core.io.ISerializable;
import com.cs.base.common.cache.core.message.Command;
import com.cs.base.common.cache.core.message.IMessage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * 两级缓存框架的逻辑调用类 配合spring采用单例即可
 *
 * @param <K>
 * @param <V>
 * @author lijingwen
 * @date 2019/2/20 15:15
 */
public class J2LCache<K, V> implements ICache<K, V> {

    Boolean flag = false;

    /**
     * 1级缓存 建议使用快速缓存
     */
    ICache<K, V> L1;

    /**
     * 2级缓存 建议使用集中式、大存储量缓存
     */
    ICache<K, V> L2;

    /**
     * 序列化工具,默认是byte[]
     */
    ISerializable serializer;

    /**
     * 更新的消息
     */
    IMessage msg;

    /**
     * 并发控制的锁,支持粗粒度和细粒度锁
     */
    ILock<K> lock;

    public J2LCache(ICache<K, V> L1, ICache<K, V> L2, ILock<K> lock, IMessage msg) {
        this.L1 = L1;
        this.L2 = L2;
        this.lock = lock;
        this.msg = msg;
        this.msg.setSyncCache(this);
    }

    public J2LCache(ICache<K, V> L1, ICache<K, V> L2, ILock<K> lock, IMessage msg, Boolean flag) {
        this.L1 = L1;
        this.L2 = L2;
        this.lock = lock;
        this.msg = msg;
        this.msg.setSyncCache(this);
        this.flag = flag;
    }

    public J2LCache(ICache<K, V> L1, ICache<K, V> L2) {
        this.L1 = L1;
        this.L2 = L2;
    }


    @Override
    public V get(final K key) throws CacheException {
        V ret = null;
        if (flag) {
            ret = L1.get(key);
            // 处理回调L2，防止并发
            if (ret == null) {
                try {
                    if (lock.getLock(key) != null) {
                    }
                    ret = L1.get(key, new Callable<V>() {
                        @Override
                        public V call() throws Exception {
                            return L2.get(key);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (lock.getLock(key) != null) {
                        lock.getLock(key).unlock();
                    }
                }
            }

        }
        return ret;
    }

    @Override
    public V get(K key, Class<V> vClass) {
        if (flag) {
            V ret = L1.get(key);
            if (ret == null) {
                ret = L2.get(key, vClass);
            }
            return ret;
        }
        return null;
    }

    @Override
    public V get(final K key, final Callable<V> loader) throws CacheException {
        V ret = null;
        if (flag) {
            ret = L1.get(key, new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return L2.get(key);
                }
            });

            // 处理回调，防止并发
            if (ret == null) {
                try {
                    if (lock.getLock(key) != null) {
                        lock.getLock(key).lock();
                    }
                    ret = L1.get(key, new Callable<V>() {
                        @Override
                        public V call() throws Exception {
                            return L2.get(key, loader);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (lock.getLock(key) != null) {
                        lock.getLock(key).unlock();
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public Map<K, V> gets(Set<K> keys) throws CacheException {
        Map<K, V> ret = null;
        if (flag) {
            // 有待优化
            ret = L1.gets(keys, new Resultable<K, V>() {
                @Override
                public Map<K, V> result(Map<K, V> foundV, Set<K> notFoundK) {
                    // TODO Auto-generated method stub
                    return L2.gets(notFoundK);
                }
            });
        }
        return ret;
    }

    @Override
    public Map<K, V> gets(Set<K> keys, Resultable<K, V> loader) throws CacheException {
        Map<K, V> ret = null;
        if (flag) {
            // 可能为空
            Map<K, V> foundV = gets(keys);

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

                    // 回写到两级缓存里
                    for (Map.Entry<K, V> entry : notFoundV.entrySet()) {
                        set(entry.getKey(), entry.getValue());
                    }
                }

            }
        }
        return ret;
    }

    @Override
    public Set<V> sGet(final K key) {
        Set<V> ret = null;
        if (flag) {
            ret = L1.sGet(key);

            // 处理回调L2，防止并发
            if (ret == null) {
                try {
                    if (lock.getLock(key) != null) {
                        lock.getLock(key).lock();
                    }
                    ret = L1.sGet(key, new Callable<Set<V>>() {
                        @Override
                        public Set<V> call() throws Exception {
                            return L2.sGet(key);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (lock.getLock(key) != null) {
                        lock.getLock(key).unlock();
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public Set<V> sGet(K key, Class<V> vClass) {
        if (flag) {
            Set<V> ret = L1.sGet(key);
            if (ret == null) {
                ret = L2.sGet(key, vClass);
            }
            return ret;
        }
        return null;
    }

    @Override
    public Set<V> sGet(final K key, final Callable<Set<V>> loader) {
        Set<V> ret = null;
        if (flag) {
            ret = L1.sGet(key, new Callable<Set<V>>() {
                @Override
                public Set<V> call() throws Exception {
                    return L2.sGet(key);
                }
            });

            // 处理回调，防止并发
            if (ret == null) {
                try {
                    if (lock.getLock(key) != null) {
                        lock.getLock(key).lock();
                    }
                    ret = L1.sGet(key, new Callable<Set<V>>() {
                        @Override
                        public Set<V> call() throws Exception {
                            return L2.sGet(key, loader);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (lock.getLock(key) != null) {
                        lock.getLock(key).unlock();
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public List<V> lGet(final K key) {
        List<V> ret = null;
        if (flag) {
            ret = L1.lGet(key);

            // 处理回调L2，防止并发
            if (ret == null) {
                try {
                    if (lock.getLock(key) != null) {
                        lock.getLock(key).lock();
                    }
                    ret = L1.lGet(key, new Callable<List<V>>() {
                        @Override
                        public List<V> call() throws Exception {
                            return L2.lGet(key);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (lock.getLock(key) != null) {
                        lock.getLock(key).unlock();
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public List<V> lGet(K key, Class<V> vClass) {
        if (flag) {
            List<V> ret = L1.lGet(key);
            if (ret == null) {
                ret = L2.lGet(key, vClass);
            }
            return ret;
        }
        return null;
    }

    @Override
    public List<V> lGet(K key, int start, int end, Class<V> vClass) {
        if (flag) {
            List<V> ret = L1.lGet(key, start, end);
            if (ret == null) {
                ret = L2.lGet(key, start, end, vClass);
            }
            return ret;
        }
        return null;
    }

    @Override
    public List<V> lGet(K key, int start, int end) {
        if (flag) {
            List<V> ret = L1.lGet(key, start, end);
            if (ret == null) {
                ret = L2.lGet(key, start, end);
            }
            return ret;
        }
        return null;
    }

    @Override
    public List<V> lGet(final K key, final Callable<List<V>> loader) {
        List<V> ret = null;
        if (flag) {
            ret = L1.lGet(key, new Callable<List<V>>() {
                @Override
                public List<V> call() throws Exception {
                    return L2.lGet(key);
                }
            });

            // 处理回调，防止并发
            if (ret == null) {
                try {
                    if (lock.getLock(key) != null) {
                        lock.getLock(key).lock();
                    }
                    ret = L1.lGet(key, new Callable<List<V>>() {
                        @Override
                        public List<V> call() throws Exception {
                            return L2.lGet(key, loader);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (lock.getLock(key) != null) {
                        lock.getLock(key).unlock();
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public V hGet(K key, K field) {
        V ret = null;
        if (flag) {
            ret = L1.hGet(key, field);
            if (ret == null) {
                ret = L2.hGet(key, field);
            }
        }
        return ret;
    }

    @Override
    public V hGet(K key, K filed, Class<V> vClass) {
        if (flag) {
            V ret = L1.hGet(key, filed);
            if (ret == null) {
                ret = L2.hGet(key, filed, vClass);
            }
            return ret;
        }
        return null;
    }

    @Override
    public V hGet(K key, K field, Callable<V> loader) {
        V ret = null;
        if (flag) {

        }
        return ret;
    }

    @Override
    public Map<K, V> hGet(K key, Resultable<K, V> loader) {
        Map<K, V> ret = null;
        if (flag) {

        }
        return ret;
    }

    @Override
    public Map<K, V> hGet(final K key) {
        Map<K, V> ret = null;
        if (flag) {
            ret = L1.hGet(key);

            // 处理回调L2，防止并发
            if (ret == null) {
                try {
                    if (lock.getLock(key) != null) {
                        lock.getLock(key).lock();
                    }
                    ret = (Map<K, V>) L1.hGet(key, new Callable<Map<K, V>>() {
                        @Override
                        public Map<K, V> call() throws Exception {
                            return L2.hGet(key);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (lock.getLock(key) != null) {
                        lock.getLock(key).unlock();
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public Map<K, V> hGet(K key, Class<V> vClass) {
        if (flag) {
            Map<K, V> ret = L1.hGet(key);
            if (ret == null) {
                ret = L2.hGet(key, vClass);
            }
            return ret;
        }
        return null;
    }

    @Override
    public Map<K, V> hGet(final K key, final Callable<Map<K, V>> loader) {
        Map<K, V> ret = null;
        if (flag) {
            ret = L1.hGet(key, new Callable<Map<K, V>>() {
                @Override
                public Map<K, V> call() throws Exception {
                    return L2.hGet(key);
                }
            });

            // 处理回调，防止并发
            if (ret == null) {
                try {
                    if (lock.getLock(key) != null) {
                        lock.getLock(key).lock();
                    }
                    ret = L1.hGet(key, new Callable<Map<K, V>>() {
                        @Override
                        public Map<K, V> call() throws Exception {
                            return L2.hGet(key, loader);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (lock.getLock(key) != null) {
                        lock.getLock(key).unlock();
                    }
                }
            }
        }

        return ret;
    }

    @Override
    public void set(K key, V value) throws CacheException {
        if (flag) {
            L2.set(key, value);
            L1.set(key, value);
        }
    }

    @Override
    public void setString(K key, String value) throws CacheException {
        if (flag) {
            L2.setString(key, value);
            L1.setString(key, value);
        }
    }

    @Override
    public void set2(K key, V value) throws CacheException {
        if (flag) {
            L2.set2(key, value);
        }
    }

    @Override
    public void setString2(K key, String value) throws CacheException {
        if (flag) {
            L2.setString2(key, value);
        }
    }

    @Override
    public void sAdd(K key, V... values) throws CacheException {
        if (flag) {
            L2.sAdd(key, values);
            L1.sAdd(key, values);
        }
    }

    @Override
    public void sAddString(K key, String... values) throws CacheException {
        if (flag) {
            L2.sAddString(key, values);
            L1.sAddString(key, values);
        }
    }

    @Override
    public void sAdd2(K key, V... values) throws CacheException {
        if (flag) {
            L2.sAdd2(key, values);
        }
    }

    @Override
    public void sAddString2(K key, String... values) throws CacheException {
        if (flag) {
            L2.sAddString(key, values);
        }
    }

    @Override
    public void lAdd(K key, V... values) throws CacheException {
        if (flag) {
            L2.lAdd(key, values);
            L1.lAdd(key, values);
        }
    }

    @Override
    public void lAddWithIndex(K key, int index, String value) throws CacheException {
        if (flag) {
            L2.lAddWithIndex(key, index, value);
            L1.lAddWithIndex(key, index, value);
        }
    }

    @Override
    public void lAddWithIndexE(K key, int index, V value) throws CacheException {
        if (flag) {
            L2.lAddWithIndexE(key, index, value);
            L1.lAddWithIndexE(key, index, value);
        }
    }

    @Override
    public void lAddString(K key, String... values) throws CacheException {
        if (flag) {
            L2.lAddString(key, values);
            L1.lAddString(key, values);
        }
    }

    @Override
    public void lAdd2(K key, V... values) throws CacheException {
        if (flag) {
            L2.lAdd2(key, values);
        }
    }

    @Override
    public void lAddString2(K key, String... values) throws CacheException {
        if (flag) {
            L2.lAddString(key, values);
        }
    }

    @Override
    public void hSet(K key, K mKey, V value) throws CacheException {
        if (flag) {
            L2.hSet(key, mKey, value);
            L1.hSet(key, mKey, value);
        }
    }

    @Override
    public void hSetString(K key, K mKey, String value) throws CacheException {
        if (flag) {
            L2.hSetString(key, mKey, value);
            L1.hSetString(key, mKey, value);
        }
    }

    @Override
    public void hSet2(K key, K mKey, V value) throws CacheException {
        if (flag) {
            L2.hSet2(key, mKey, value);
        }
    }

    @Override
    public void hSetString2(K key, K mKey, String value) throws CacheException {
        if (flag) {
            L2.hSetString(key, mKey, value);
        }
    }

    @Override
    public void del(K... keys) throws CacheException {
        if (flag) {
            L2.del(keys);
            L1.del(keys);
        }
    }

    @Override
    public void sdel(K key, K... member) throws CacheException {
        if (flag) {
            L1.sdel(key, member);
            L2.sdel(key, member);
        }
    }

    @Override
    public void ldel(K... keys) throws CacheException {
        if (flag) {
            L2.ldel(keys);
            L1.ldel(keys);
        }
    }

//    @Override
//    public void hdel(K... keys) throws CacheException {
//       if (flag){
//           L2.hdel(keys);
//           L1.hdel(keys);
//       }
//
//    }

    @Override
    public void hdel(K key, K... field) throws CacheException {
        if (flag) {
            L1.hdel(key, field);
            L2.hdel(key, field);
        }
    }

    @Override
    public void clear() throws CacheException {
        // TODO Auto-generated method stub
//		L2.clear();
//		L1.clear();
    }

    @Override
    public void destroy() throws CacheException {
        // TODO Auto-generated method stub
//		L2.destroy();
//		L1.destroy();
    }

    @Override
    public void processMsg(Command cmd) {
        // TODO Auto-generated method stub

    }

    @Override
    public void expire(K key, int seconds) {
        if (flag) {
            L2.expire(key, seconds);
        }
    }

    @Override
    public boolean exist(K key) {
        if (flag) {
            return L2.exist(key);
        }
        return false;
    }

    @Override
    public boolean isMember(K key, V value) {
        boolean exist = false;
        if (flag) {
            if (L1.isMember(key, value)) {
                exist = true;
            } else {
                exist = L2.isMember(key, value);
                if (exist) {
                    L1.sAdd(key, value);
                }
            }
        }
        return exist;
    }

    @Override
    public Long incr(K key) {
        if (flag) {
            return L2.incr(key);
        }
        return null;
    }

}
