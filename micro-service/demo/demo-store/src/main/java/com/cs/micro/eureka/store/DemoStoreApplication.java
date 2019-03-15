package com.cs.micro.eureka.store;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import com.cs.base.oauth2.EnableOauth2RC;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author wangjiahao
 * @version 1.0
 * @className DemoCustomerApplication
 * @since 2019-02-21 16:30
 */
@RefreshScope
@EnableOauth2RC
@SpringCloudApplication
@EnableDistributedTransaction
@EnableFeignClients(basePackages = "com.cs.micro.eureka.store.api")
public class DemoStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoStoreApplication.class, args);
    }

}
