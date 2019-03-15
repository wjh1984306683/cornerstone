package com.cs.cms.config;

import com.cs.base.common.SpringBootContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * common模块中一些工具的引入
 *
 * @author wangjiahao
 * @version 1.0
 * @className CommonAutoComfiguration
 * @since 2019-02-27 16:12
 */
@Configuration
public class CommonAutoComfiguration {

    @Bean
    public SpringBootContext springBootContext() {
        return new SpringBootContext();
    }
}
