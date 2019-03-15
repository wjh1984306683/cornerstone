package com.cs.base.common.redis;

import com.cs.base.common.SpringBootContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author wangjiahao
 * @version 1.0
 * @className RedisClient
 * @since 2019-03-11 10:46
 */
public class RedisClient {

    private static RedisTemplate<Object, Object> redisTemplate = SpringBootContext.getBean("redisTemplate", RedisTemplate.class);

    /**
     * 普通set方法
     *
     * @param key
     * @param value
     */
    public static void set(Object key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * set方法，设置过期时间
     *
     * @param key
     * @param value
     * @param time
     */
    public static void set(Object key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 获取
     *
     * @param key
     * @return
     */
    public static Object get(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除
     *
     * @param key
     */
    public static void del(Object key) {
        redisTemplate.delete(key);
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param time
     */
    public static void expire(Object key, long time) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

}
