package com.cs.base.security.properties;

import lombok.Data;

/**
 * @author wangjiahao
 * @version 1.0
 * @className BaseRememberMeProperties
 * @since 2019-02-12 13:51
 */
@Data
public class BaseRememberMeProperties {

    private Boolean enable = false;
    /**
     * 记住我有效时间，单位秒
     */
    private Integer validTime = 60 * 60 * 24;
    /**
     * 是否为第一次启动
     */
    private Boolean firstStartUp = false;

}
