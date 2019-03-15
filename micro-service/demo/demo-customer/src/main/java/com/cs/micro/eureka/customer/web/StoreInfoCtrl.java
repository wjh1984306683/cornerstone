package com.cs.micro.eureka.customer.web;

import com.cs.base.common.support.SimpleResult;
import com.cs.micro.eureka.customer.api.StoreApi;
import com.cs.micro.eureka.customer.entity.Customer;
import com.cs.micro.eureka.customer.entity.SkuEntity;
import com.cs.micro.eureka.customer.service.CusService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试Controller
 *
 * @author wangjiahao
 * @version 1.0
 * @className TestCtrl
 * @since 2019-02-21 16:44
 */
@Api(tags = "商店信息")
@RefreshScope
@RestController
public class StoreInfoCtrl {

    @Autowired
    private CusService service;
    @Autowired
    private StoreApi api;

    @GetMapping("/w/askStore")
    @ApiOperation(value = "问问商店是谁的", authorizations = {@Authorization("1")})
    public String askStore() {
        return "This store belong to " + api.whose();
    }

    @GetMapping("/n/hasWhat")
    @ApiOperation("都有啥啊")
    public List<SkuEntity> hasWhat() {
        return api.listSku();
    }

    @GetMapping("/i/whoAmI")
    @ApiOperation("我是...")
    public Customer getCustomer() throws JsonProcessingException {
        return service.findOne();
    }

    @GetMapping("/w/buy")
    @ApiOperation("买一个商品")
    public SimpleResult buyOne(int id) {
        SimpleResult one = service.buyOne(id);
        return one;
    }
}
