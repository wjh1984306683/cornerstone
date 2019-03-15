package com.cs.micro.oauth2.dao.sys;

import com.cs.micro.oauth2.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wangjiahao
 * @version 1.0
 * @className RoleRepository
 * @since 2019-02-27 14:51
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
