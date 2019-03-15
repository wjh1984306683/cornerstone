package com.cs.micro.eureka.customer.dao;

import com.cs.micro.eureka.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wangjiahao
 * @version 1.0
 * @className CusRepository
 * @since 2019-03-14 15:37
 */
@Repository
public interface CusRepository extends JpaRepository<Customer, Integer> {
}
