package com.cs.base.common.cache.key;

import org.apache.commons.lang3.StringUtils;

/**
 * @author lijingwen
 * @date 2019/2/27 16:13
 */
public class BaseKey implements IKey {

    private int expireSeconds;

    private String prefix;

    private String key;

    private String suffix;

    public BaseKey() {
    }

    public BaseKey(String prefix) {
        this.expireSeconds = 0;
        this.prefix = prefix;
    }

    // 0代表永不过期
    public BaseKey(String prefix, String suffix, String key) {
        this(0, prefix, suffix, key);
    }

    public BaseKey(String prefix, String key) {
        this(0, prefix, key);
    }

    public BaseKey(int expireSeconds, String prefix, String key) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
        this.key = key;
    }

    public BaseKey(int expireSeconds, String prefix, String suffix, String key) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
        this.key = key;
        this.suffix = suffix;
    }


    @Override
    public int expireSeconds() {//默认0代表永不过期
        return this.expireSeconds;
    }

    @Override
    public String getPrefix() {
        if (StringUtils.isNotEmpty(this.prefix)) {
            return this.prefix + ":";
        }
        return "";
    }

    @Override
    public String getKey() {
        if (StringUtils.isNotEmpty(this.key)) {
            return this.key;
        }
        return "";
    }

    @Override
    public String getSuffix() {
        if (StringUtils.isNotEmpty(this.suffix)) {
            return this.suffix;
        }
        return "";
    }


    @Override
    public String toString() {
        return this.getPrefix() + this.getKey() + this.getSuffix();
    }
}
