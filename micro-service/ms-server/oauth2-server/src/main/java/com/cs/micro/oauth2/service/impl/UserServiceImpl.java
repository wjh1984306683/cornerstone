package com.cs.micro.oauth2.service.impl;

import com.cs.micro.oauth2.dao.sys.UserRepository;
import com.cs.micro.oauth2.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author wangjiahao
 * @version 1.0
 * @className UserServiceImpl
 * @since 2019-02-27 14:53
 */
@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = repository.findByUsername(s);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("查询不到此用户！");
        }
        return user.get();
    }
}
