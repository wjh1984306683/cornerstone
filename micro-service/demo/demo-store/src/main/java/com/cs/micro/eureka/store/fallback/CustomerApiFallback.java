package com.cs.micro.eureka.store.fallback;

import com.cs.micro.eureka.store.api.CustomerApi;
import org.springframework.stereotype.Component;

/**
 * {@link CustomerApi}回退的实现方法
 *
 * @author wangjiahao
 * @version 1.0
 * @className EurekaClientDemo2ApiFallBack
 * @since 2019-03-07 10:59
 */
@Component
public class CustomerApiFallback implements CustomerApi {
    @Override
    public String whoIsCustomer() {
        return "nobody knows";
    }
}
