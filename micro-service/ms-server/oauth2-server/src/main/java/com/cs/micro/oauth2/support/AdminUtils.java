package com.cs.micro.oauth2.support;

import com.cs.base.common.SpringBootContext;
import com.cs.micro.oauth2.dao.sys.UserRepository;
import com.cs.micro.oauth2.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * 当前权限工具
 *
 * @author wangjiahao
 * @version 1.0
 * @className AdminUtils
 * @since 2019-02-27 14:45
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class AdminUtils {

    private static UserRepository userRepository = SpringBootContext.getBean(UserRepository.class);

    /**
     * 获取当前用户信息
     *
     * @return
     */
    public static User getCurrentUser() {
        Object o = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if (Objects.isNull(o) || o instanceof String) {
            return null;
        }
        User user = (User) o;
        Optional<User> optional = userRepository.findById(user.getId());
        return optional.orElse(null);
    }
}
