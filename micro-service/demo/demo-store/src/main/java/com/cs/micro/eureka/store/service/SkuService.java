package com.cs.micro.eureka.store.service;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.cs.micro.eureka.store.dao.SkuRepository;
import com.cs.micro.eureka.store.entity.SkuEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author wangjiahao
 * @version 1.0
 * @className SkuService
 * @since 2019-03-14 14:22
 */
@Slf4j
@Service
public class SkuService {

    @Autowired
    private SkuRepository repository;

    public List<SkuEntity> findAll() {
        return repository.findAll();
    }

    /**
     * 插入一个
     */
    @Transactional(rollbackFor = Exception.class)
    public SkuEntity saveOne(SkuEntity entity) {
        return repository.save(entity);
    }

    /**
     * 被买走一个
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @LcnTransaction
    public void buyOne(int id) {
        Optional<SkuEntity> optional = repository.findById(id);
        SkuEntity entity = optional.get();
        entity.setNum(entity.getNum() - 1);

        repository.saveAndFlush(entity);
    }

}
