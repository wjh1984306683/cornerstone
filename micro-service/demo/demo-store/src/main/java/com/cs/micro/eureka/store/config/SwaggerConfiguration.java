package com.cs.micro.eureka.store.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

/**
 * @author wangjiahao
 * @version 1.0
 * @className SwaggerConfiguration
 * @since 2019-02-21 17:09
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${project.version:1.0}")
    private String version;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securitySchemes(Arrays.asList(new ApiKey("1", "Authorization", ApiKeyVehicle.HEADER.getValue())))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cs.micro.eureka.store"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .version(version)
                .title("商店's API")
                .contact(new Contact("wwjjhh", "https://blog.csdn.net/qq_20867219", "1984306683@qq.com"))
                .license("MIT")
                .description("商店在线API样例")
                .build();
    }

}
