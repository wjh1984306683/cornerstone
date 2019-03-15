package com.cs.base.security.support.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

/**
 * 权限评估实现类
 * 用于鉴定hasPermission的表达式
 * 只需要自行实现PermissionEvaluator{@link PermissionEvaluator},并向spring注册
 *
 * @author wangjh
 * @version 1.0
 * @className BasePermissionEvaluator
 * @see PermissionEvaluator
 * @since 2019-02-12
 */
@Slf4j
public class BasePermissionEvaluator implements PermissionEvaluator {

    /**
     * 权限过滤--根据url和所需权限过滤
     *
     * @param authentication 用于获取当前登录的信息
     * @param targetUrl      目标url --- 实际为检查的对象
     * @param permission     需要的权限
     * @return
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object permission) {
        String url = (String) targetUrl;
        String pms = (String) permission;
        UserDetails user = (UserDetails) authentication.getPrincipal();
        log.error(url);
        log.error(pms);

//        TODO:需要具体实现校验部分

        return false;
    }

    /**
     * 不需要实现
     *
     * @param authentication
     * @param serializable
     * @param s
     * @param o
     * @return
     */
    @Override
    @Deprecated
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }

}
