package com.cs.base.common.cache.core;

/**
 * 缓存异常
 *
 * @author liyuan
 * @date 2019/2/20 15:18
 */
public class CacheException extends RuntimeException {
    private static final long serialVersionUID = -1L;

    public CacheException(String s) {
        super(s);
    }

    public CacheException(String s, Throwable e) {
        super(s, e);
    }

    public CacheException(Throwable e) {
        super(e);
    }
}
