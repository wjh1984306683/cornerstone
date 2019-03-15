package com.cs.base.common.cache.local;

import com.cs.base.common.cache.core.ILock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lijingwen
 * @date 2019/2/20 15:41
 */
public class JavaReentrantLock<K> implements ILock<K> {

    Lock lock = new ReentrantLock();

    @Override
    public Lock getLock(K key) {
        return lock;
    }
}
