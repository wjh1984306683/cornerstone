package com.cs.micro.gateway.config;

import com.cs.micro.gateway.support.SwaggerDocProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2核心配置类
 *
 * @author wangjiahao
 * @version 1.0
 * @className SwaggerConfiguration
 * @since 2019-02-21 17:09
 */
@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerDocProperties.class)
public class SwaggerConfiguration {

    /**
     * 表示从配置文件中读取project.version这个变量，如果为空则为1.0
     */

    @Autowired
    private SwaggerDocProperties properties;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .version(properties.getVersion())
                .title(properties.getTitle())
                .contact(new Contact("wwjjhh", "https://blog.csdn.net/qq_20867219", "1984306683@qq.com"))
                .license("MIT")
                .description(properties.getDescription())
                .build();
    }

}
