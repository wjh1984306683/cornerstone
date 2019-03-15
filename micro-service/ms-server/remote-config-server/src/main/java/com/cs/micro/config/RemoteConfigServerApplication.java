package com.cs.micro.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author wangjiahao
 * @version 1.0
 * @className RemoteConfigServerApplication
 * @since 2019-03-04 11:19
 */
@SpringBootApplication
@EnableEurekaClient
@EnableConfigServer
public class RemoteConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RemoteConfigServerApplication.class, args);
    }

}
