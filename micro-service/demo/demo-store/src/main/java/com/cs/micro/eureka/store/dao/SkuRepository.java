package com.cs.micro.eureka.store.dao;

import com.cs.micro.eureka.store.entity.SkuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wangjiahao
 * @version 1.0
 * @className SkuRepository
 * @since 2019-03-14 14:20
 */
@Repository
public interface SkuRepository extends JpaRepository<SkuEntity, Integer> {
}
