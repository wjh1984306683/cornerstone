package com.cs.base.common.wechat;

/**
 * 微信配置类接口，需要自己实现
 *
 * @author wangjiahao
 * @version 1.0
 * @className WechatConfig
 * @since 2019-02-14 10:43
 */
public interface WechatConfig {


    /**
     * 获取微信APP_ID
     *
     * @return str
     */
    String getAppId();

    /**
     * 获取微信APP_SECRET
     *
     * @return str
     */
    String getAppSecret();

    /**
     * 自己的授权地址
     *
     * @return str
     */
    String getRedirectUrl();

    /**
     * 这里是自定义的token，需和你提交的token一致
     *
     * @return str
     */
    String getToken();

}
