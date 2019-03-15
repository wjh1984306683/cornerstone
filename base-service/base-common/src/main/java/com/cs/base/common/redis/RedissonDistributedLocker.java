package com.cs.base.common.redis;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * redisson分布式锁
 *
 * @author wangjiahao
 * @version 1.0
 * @className RedissonDistributedLocker
 * @since 2019-03-12 10:44
 */
public class RedissonDistributedLocker implements DistributedLocker {

    private RedissonClient redissonClient;

    /**
     * 获取锁
     *
     * @param lockKey
     * @return
     */
    @Override
    public RLock getLock(String lockKey) {
        return redissonClient.getLock(lockKey);
    }

    /**
     * 加锁
     *
     * @param lockKey
     * @return
     */
    @Override
    public RLock lock(String lockKey) {
        RLock lock = getLock(lockKey);
        lock.lock();
        return lock;
    }

    /**
     * 加锁并加上超时时间--单位秒
     *
     * @param lockKey
     * @param leaseTime
     * @return
     */
    @Override
    public RLock lock(String lockKey, int leaseTime) {
        RLock lock = getLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock;
    }

    /**
     * 加锁并加上超时时间--单位自定
     *
     * @return
     */
    @Override
    public RLock lock(String lockKey, TimeUnit unit, int timeout) {
        RLock lock = getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    /**
     * 尝试获取锁
     *
     * @param lockKey
     * @param unit
     * @param waitTime
     * @param leaseTime
     * @return
     */
    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        RLock lock = getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 释放锁
     *
     * @param lockKey
     */
    @Override
    public void unlock(String lockKey) {
        RLock lock = getLock(lockKey);
        lock.unlock();
    }

    @Override
    public void unlock(RLock lock) {
        lock.unlock();
    }

    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }
}
