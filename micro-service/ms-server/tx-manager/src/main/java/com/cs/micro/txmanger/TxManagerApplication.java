package com.cs.micro.txmanger;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author wangjiahao
 * @version 1.0
 * @className TxManagerApplication
 * @since 2019-03-14 15:19
 */
@RefreshScope
@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagerServer
public class TxManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxManagerApplication.class, args);
    }

}
