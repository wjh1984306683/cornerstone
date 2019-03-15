package com.cs.base.common.cache.core;

import java.util.concurrent.locks.Lock;

/**
 * @author liyuan
 * @date 2019/2/20 15:40
 */
public interface ILock<K> {
    /**
     * 根据key获得锁
     */
    Lock getLock(K key);
}
