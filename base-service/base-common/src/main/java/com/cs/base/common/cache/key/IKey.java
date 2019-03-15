package com.cs.base.common.cache.key;

/**
 * @author lijingwen
 * @date 2019/2/20 15:56
 */
public interface IKey {
    /**
     * 过期时间
     *
     * @return
     */
    int expireSeconds();

    /**
     * 获取Key前缀
     *
     * @return
     */
    String getPrefix();

    /**
     * 获取Key
     *
     * @return
     */
    String getKey();

    /**
     * 获取Key后缀
     *
     * @return
     */
    String getSuffix();
}
