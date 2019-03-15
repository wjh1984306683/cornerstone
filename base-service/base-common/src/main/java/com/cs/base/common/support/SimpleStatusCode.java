package com.cs.base.common.support;

/**
 * @author wangjiahao
 * @version 1.0
 * @className SimpleStatusCode
 * @since 2019-03-14 14:30
 */
public enum SimpleStatusCode implements BaseStatusCode {

    /**
     * 成功
     */
    SUCCESS("000", "成功"),
    /**
     * 失败
     */
    FAIL("999", "失败"),

    /**
     * 自定义提示语
     */
    CUSTOM_FAIL("998", "%s");

    private String code;
    private String msg;

    SimpleStatusCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String msg() {
        return this.msg;
    }
}
