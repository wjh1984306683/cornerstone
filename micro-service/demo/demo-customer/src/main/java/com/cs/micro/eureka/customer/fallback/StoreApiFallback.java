package com.cs.micro.eureka.customer.fallback;

import com.cs.micro.eureka.customer.api.StoreApi;
import com.cs.micro.eureka.customer.entity.SkuEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangjiahao
 * @version 1.0
 * @className StoreApiFallback
 * @since 2019-03-07 14:27
 */
@Component
@Slf4j
public class StoreApiFallback implements StoreApi {

    @Override
    public String whose() {
        return "nobody";
    }

    @Override
    public List<SkuEntity> listSku() {
        return null;
    }

    @Override
    public void buyOne(int id) {
        log.error("购买失败！");
    }
}
