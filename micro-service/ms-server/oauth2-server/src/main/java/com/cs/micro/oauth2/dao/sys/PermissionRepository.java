package com.cs.micro.oauth2.dao.sys;

import com.cs.micro.oauth2.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wangjiahao
 * @version 1.0
 * @className PermissionRepository
 * @since 2019-02-27 14:51
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
}
