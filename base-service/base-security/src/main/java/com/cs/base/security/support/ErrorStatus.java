package com.cs.base.security.support;

/**
 * @author wangjiahao
 * @version 1.0
 * @className ErrorStatus
 * @since 2019-02-04 18:07
 */
public enum ErrorStatus {

    /**
     * 成功
     */
    SUCCSESS("000", "success"),
    /**
     * 需要登录
     */
    LOGIN_REQUIRED("996", "请登录"),
    /**
     * session过期
     */
    SESSION_EXPIRE("997", "session已过期"),
    /**
     * session无效
     */
    SESSION_INVALID("998", "session无效"),
    /**
     * 失败
     */
    FAIL("999", "fail");

    private String code;
    private String msg;

    ErrorStatus(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

}
