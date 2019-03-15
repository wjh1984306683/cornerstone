package com.cs.micro.eureka.store.api;

import com.cs.micro.eureka.store.fallback.CustomerApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author wangjiahao
 * @version 1.0
 * @className EurekaClientDemo2Api
 * @since 2019-03-05 17:44
 */
@Component
@FeignClient(value = "demo-customer", fallback = CustomerApiFallback.class)
public interface CustomerApi {

    /**
     * 谁是顾客
     *
     * @return
     */
    @GetMapping("/i/whoAmI")
    String whoIsCustomer();

}
