package com.cs.base.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author wangjiahao
 * @version 1.0
 * @className UserServiceImpl
 * @since 2019-02-07 14:51
 */
@Slf4j
public class UserServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("临时用户登录");
        return new User("admin", new BCryptPasswordEncoder().encode("123"),
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
