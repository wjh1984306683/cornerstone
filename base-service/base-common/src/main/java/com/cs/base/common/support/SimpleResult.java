package com.cs.base.common.support;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 统一返回对象
 *
 * @author wangjiahao
 * @version 1.0
 * @className SimpleResult
 * @since 2019-03-14 14:26
 */
@Setter
@Getter
public class SimpleResult<T> implements Serializable {

    private String code;
    private String msg;
    private T data;

    /**
     * 其他
     *
     * @param msg
     * @return
     */
    public static SimpleResult build(String msg) {
        return new SimpleResult(SimpleStatusCode.CUSTOM_FAIL.code(), msg, null);
    }

    public static SimpleResult build(BaseStatusCode statusCode) {
        return new SimpleResult(statusCode.code(), statusCode.msg(), null);
    }

    public static <T> SimpleResult<T> build(String msg, T data) {
        return new SimpleResult<>(SimpleStatusCode.CUSTOM_FAIL.code(), msg, data);
    }

    public static <T> SimpleResult<T> build(BaseStatusCode statusCode, T data) {
        return new SimpleResult<>(statusCode.code(), statusCode.msg(), data);
    }

    public static <T> SimpleResult<T> build(String code, String msg, T data) {
        return new SimpleResult<>(code, msg, data);
    }

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> SimpleResult<T> ok(T data) {
        return new SimpleResult<>(SimpleStatusCode.SUCCESS, data);
    }

    public static SimpleResult ok() {
        return new SimpleResult(SimpleStatusCode.SUCCESS, null);
    }

    public SimpleResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public SimpleResult(BaseStatusCode statusCode, T data) {
        this.code = statusCode.code();
        this.msg = statusCode.msg();
        this.data = data;
    }

}
