package com.cs.base.security.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一回复对象
 *
 * @author wangjiahao
 * @version 1.0
 * @className GenericResult
 * @since 2019-02-04 18:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResult<T> {

    private String code;
    private String msg;
    private T data;

    public static <T> GenericResult<T> ok(T data) {
        return build(ErrorStatus.SUCCSESS, data);
    }

    public static GenericResult ok() {
        return build(ErrorStatus.SUCCSESS, null);
    }

    public static GenericResult build(ErrorStatus status) {
        return build(status.getCode(), status.getMsg(), null);
    }

    public static <T> GenericResult<T> build(ErrorStatus status, T data) {
        return build(status.getCode(), status.getMsg(), data);
    }

    public static <T> GenericResult<T> build(String code, String msg, T data) {
        return new GenericResult<>(code, msg, data);
    }
}
