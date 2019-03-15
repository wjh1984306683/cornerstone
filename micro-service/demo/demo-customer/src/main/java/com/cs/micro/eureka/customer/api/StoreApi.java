package com.cs.micro.eureka.customer.api;

import com.cs.micro.eureka.customer.entity.SkuEntity;
import com.cs.micro.eureka.customer.fallback.StoreApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wangjiahao
 * @version 1.0
 * @className EurekaClientDemo2Api
 * @since 2019-03-05 17:44
 */
@Component
@FeignClient(value = "demo-store", fallback = StoreApiFallback.class)
public interface StoreApi {

    /**
     * 商店是谁的
     *
     * @return
     */
    @GetMapping("/w/whose")
    String whose();

    /**
     * 商店有什么东西
     *
     * @return
     */
    @GetMapping("/n/sku")
    List<SkuEntity> listSku();

    /**
     * 买一个东西
     *
     * @param id
     */
    @GetMapping("/i/buy")
    void buyOne(@RequestParam("id") int id);
}
