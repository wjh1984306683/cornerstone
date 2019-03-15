package com.cs.micro.eureka.store.web;

import com.cs.base.common.redis.DistributedLocker;
import com.cs.base.common.support.SimpleResult;
import com.cs.base.common.support.SimpleStatusCode;
import com.cs.micro.eureka.store.api.CustomerApi;
import com.cs.micro.eureka.store.entity.SkuEntity;
import com.cs.micro.eureka.store.service.SkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * 商店信息Controller
 *
 * @author wangjiahao
 * @version 1.0
 * @className TestCInfoCtrltrl
 * @since 2019-02-21 16:44
 */
@Slf4j
@Api(tags = "商店信息")
@RestController
public class InfoCtrl {

    @Value("${store-host}")
    private String storeHost;

    @Autowired
    private CustomerApi api;
    @Autowired
    private DistributedLocker locker;
    @Autowired
    private SkuService service;

    @GetMapping("/i/buy")
    @ApiOperation(value = "买走一个商品", authorizations = {@Authorization("1")})
    public void buyOne(int id) {
        log.debug("买走一个商品：{}", id);
        service.buyOne(id);
    }

    @PostMapping("/w/addSku")
    @ApiOperation(value = "添加一个新的商品", authorizations = {@Authorization("1")})
    public SimpleResult<SkuEntity> addSku(@RequestBody SkuEntity entity) {
        SkuEntity one = service.saveOne(entity);
        return Objects.nonNull(one) ? SimpleResult.ok(one) : SimpleResult.build(SimpleStatusCode.FAIL);
    }

    @GetMapping("/w/whose")
    @ApiOperation(value = "商店是谁的", authorizations = {@Authorization("1")})
    public String whose() throws InterruptedException {
        return storeHost;
    }

    @GetMapping("/n/sku")
    @ApiOperation("商店有啥")
    public List<SkuEntity> listSku() {
        return service.findAll();
    }

    @GetMapping("/i/whoIsCustomer")
    @ApiOperation(value = "顾客是谁", authorizations = {@Authorization("1")})
    public String getCustomer() {
        return api.whoIsCustomer();
    }
}
