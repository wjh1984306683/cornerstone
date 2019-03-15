package com.cs.micro.gateway.support;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjiahao
 * @version 1.0
 * @className SwaggerDocProperties
 * @since 2019-03-07 14:48
 */
@Data
@ConfigurationProperties(prefix = "swagger.doc")
public class SwaggerDocProperties {

    private String version = "1.0-SNAPSHOT";
    private String title = "Swagger's Api";
    private String description = "Swagger's Online Api Info";
    private List<String> name = new ArrayList<>();

}
