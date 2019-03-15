package com.cs.base.common.wechat;

/**
 * 缓存接口
 *
 * @author root
 */
public interface CacheClient {


    //=============================String============================

    /**
     * 存数据
     *
     * @param key
     * @param value
     */
    void set(String key, Object value);

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    void set(String key, Object value, long time);

    /**
     * 取数据
     *
     * @param key
     * @return
     */
    Object get(String key);


    //=============================通用方法============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    void expire(String key, long time);

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    long getExpire(String key);


    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     * @return
     */
    void delete(String... key);
}
