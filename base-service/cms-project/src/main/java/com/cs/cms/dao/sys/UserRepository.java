package com.cs.cms.dao.sys;

import com.cs.cms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author wangjiahao
 * @version 1.0
 * @className UserRepository
 * @since 2019-02-27 14:49
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * 按账号名查询一个用户
     *
     * @param username 账号名
     * @return Optional
     */
    Optional<User> findByUsername(String username);

}
