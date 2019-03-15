package com.cs.base.common.wechat;

import lombok.Data;

/**
 * @author wangjiahao
 * @version 1.0
 * @className WechatOpenIdResponse
 * @since 2018-12-17 16:04
 */
@Data
@SuppressWarnings("all")
public class WechatOpenIdResponse {

    /**
     * access_token : ACCESS_TOKEN
     * expires_in : 7200
     * refresh_token : REFRESH_TOKEN
     * openid : OPENID
     * scope : SCOPE
     * errcode : 40029
     * errmsg : invalid code
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String errcode;
    private String errmsg;
}
