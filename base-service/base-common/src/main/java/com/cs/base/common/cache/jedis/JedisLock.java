package com.cs.base.common.cache.jedis;


import com.cs.base.common.cache.core.ILock;

import java.util.concurrent.locks.Lock;

/**
 * @author lijingwen
 * @date 2019/2/26 10:25
 */
public class JedisLock<K> implements ILock<K> {


//    private KooJedisClient redisClient;
//
//    public JedisLock(KooJedisClient redisClient){
//        this.redisClient = redisClient;
//    }

    @Override
    public Lock getLock(K key) {
        return null;
    }
}
