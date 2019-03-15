package com.cs.base.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目配置类，主要配置此类
 *
 * @author wangjiahao
 * @version 1.0
 * @className BaseProperties
 * @since 2019-01-30 17:57
 */
@Data
@ConfigurationProperties(prefix = "base.security")
public class BaseProperties {

    private LoginType loginType = LoginType.JSON;
    private String loginUrl = "/login.html";
    private String homeUrl = "/home.html";

    private BaseRememberMeProperties rememberMe = new BaseRememberMeProperties();
    /**
     * session
     */
    private BaseSessionProperties session = new BaseSessionProperties();

    /**
     * 不需要权限拦截的url
     */
    private List<String> permitUrls = new ArrayList<>();
    /**
     * 不需要拦截的html页面
     */
    private List<String> permitHtml = new ArrayList<>();

    /**
     * 不需要拦截的静态资源
     */
    private List<String> permitStaticResource = new ArrayList<>();

}
