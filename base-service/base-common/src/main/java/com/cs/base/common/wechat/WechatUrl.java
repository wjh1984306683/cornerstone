package com.cs.base.common.wechat;

/**
 * @author wangjiahao
 * @version 1.0
 * @className WechatUrl
 * @since 2018-11-28 14:21
 */
public interface WechatUrl {

    /**
     * 微信获取权限类型，1为获取openid，2为获取用户个人信息
     */
    String SNSAPI_BASE_TYPE = "snsapi_base";
    String SNSAPI_USERINFO_TYPE = "snsapi_userinfo";

    /**
     * 获取授权的访问路径
     */
    String GRANT_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
            "APP_ID=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";

    /**
     * 获取openID的访问路径
     */
    String OPENID_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
            "APP_ID=%s&secret=%s&code=%s&grant_type=authorization_code";

    /**
     * 获取用户信息的服务路径
     */
    String USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?" +
            "access_token=%s&openid=%s&lang=zh_CN";

    String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    String OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";

    String SERVER_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";


}
