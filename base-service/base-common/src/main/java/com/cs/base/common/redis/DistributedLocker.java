package com.cs.base.common.redis;

/**
 * redisson锁接口
 *
 * @author wangjiahao
 * @version 1.0
 * @className Locker
 * @since 2019-03-12 10:43
 */

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

public interface DistributedLocker {

    /**
     * 获取锁
     * @param lockKey
     * @return
     */
    RLock getLock(String lockKey);

    /**
     * 上锁
     * @param lockKey
     * @return
     */
    RLock lock(String lockKey);

    /**
     * 上锁，并设置超时时间，单位秒
     * @param lockKey
     * @param timeout
     * @return
     */
    RLock lock(String lockKey, int timeout);

    /**
     * 上锁，设置超时时间，单位自定义
     * @param lockKey
     * @param unit
     * @param timeout
     * @return
     */
    RLock lock(String lockKey, TimeUnit unit, int timeout);

    /**
     * 尝试获取锁
     * @param lockKey 锁key
     * @param unit 时间单位
     * @param waitTime 等待时间
     * @param leaseTime 离开时间
     * @return bool值，true为已经获取锁，false为没有获取到锁
     */
    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

    /**
     * 释放锁
     * @param lockKey
     */
    void unlock(String lockKey);

    /**
     * 释放锁
     * @param lock
     */
    void unlock(RLock lock);

}

