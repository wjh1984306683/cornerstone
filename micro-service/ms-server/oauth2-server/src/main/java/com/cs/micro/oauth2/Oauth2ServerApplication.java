package com.cs.micro.oauth2;

import com.cs.base.common.SpringBootContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author wangjiahao
 * @version 1.0
 * @className Oauth2ServerApplication
 * @since 2019-02-28 11:14
 */
@RefreshScope
@EnableResourceServer
@EnableDiscoveryClient
@SpringBootApplication
public class Oauth2ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2ServerApplication.class, args);
    }

    @Bean
    public SpringBootContext springBootContext() {
        return new SpringBootContext();
    }
}
