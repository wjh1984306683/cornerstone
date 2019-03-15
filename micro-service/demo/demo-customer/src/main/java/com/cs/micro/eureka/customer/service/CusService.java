package com.cs.micro.eureka.customer.service;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.cs.base.common.support.SimpleResult;
import com.cs.micro.eureka.customer.api.StoreApi;
import com.cs.micro.eureka.customer.dao.CusRepository;
import com.cs.micro.eureka.customer.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author wangjiahao
 * @version 1.0
 * @className CusService
 * @since 2019-03-14 15:34
 */
@Service
public class CusService {

    @Autowired
    private StoreApi api;
    @Autowired
    private CusRepository repository;

    public Customer findOne() {
        return repository.findById(1).orElse(null);
    }

    /**
     * 购买操作，会减少顾客的总金额，会减少商品总数
     *
     * @param id 商品ID
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @LcnTransaction
    public SimpleResult buyOne(int id) {

        Optional<Customer> optional = repository.findById(1);
        Customer customer = optional.get();
        customer.setMoney(customer.getMoney().subtract(new BigDecimal(5)));

        repository.saveAndFlush(customer);

        api.buyOne(id);
//出现异常，使其回滚
//        int i = 1 / 0;
        return SimpleResult.ok();
    }

}
