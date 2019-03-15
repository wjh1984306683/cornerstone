package com.cs.cms;

import com.cs.base.security.EnableBaseSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangjiahao
 * @version 1.0
 * @className CmsProjectApplication
 * @since 2019-02-19 09:00
 */
@SpringBootApplication
@EnableBaseSecurity
public class CmsProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsProjectApplication.class, args);
    }

}
