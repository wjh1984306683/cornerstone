package com.cs.base.common.wechat;

import lombok.Data;

import java.util.List;

/**
 * @author wangjiahao
 * @version 1.0
 * @className WechatUserInfoResponse
 * @since 2019-01-23 15:09
 */
@Data
public class WechatUserInfoResponse {

    /**
     * openid :  OPENID
     * nickname : NICKNAME
     * sex : 1
     * province : PROVINCE
     * city : CITY
     * country : COUNTRY
     * headimgurl : http://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46
     * privilege : ["PRIVILEGE1","PRIVILEGE2"]
     * unionid : o6_bmasdasdsad6_2sgVt7hMZOPfL
     */

    private String openid;
    private String nickname;
    private String sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private String unionid;
    private String language;
    private List<String> privilege;
    /**
     * errcode : 40003
     * errmsg :  invalid openid
     */

    private String errcode;
    private String errmsg;

}
