package com.cs.base.security.config;

import com.cs.base.security.properties.BaseProperties;
import com.cs.base.security.service.UserServiceImpl;
import com.cs.base.security.support.authentication.BaseAuthenticationFailureHandler;
import com.cs.base.security.support.authentication.BaseAuthenticationSuccessHandler;
import com.cs.base.security.support.session.BaseSessionExpiredStrategy;
import com.cs.base.security.support.session.BaseSessionInvalidStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * 默认加载配置
 * 1.登录成功、失败处理器
 * 2.session策略方式
 * 3.UserDetailsService
 * 4.密码加密策略
 *
 * @author wangjiahao
 * @version 1.0
 * @className BaseBeanAssembleConfig
 * @since 2019-02-12 14:23
 */
@Configuration
public class BaseBeanAssembleConfig {

    @Autowired
    private BaseProperties properties;

    @Bean
    @ConditionalOnMissingBean(value = PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 登录失败处理器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SimpleUrlAuthenticationFailureHandler.class)
    public SimpleUrlAuthenticationFailureHandler baseAuthenticationFailureHandler() {
        return new BaseAuthenticationFailureHandler(properties);
    }

    /**
     * 登录成功处理器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SavedRequestAwareAuthenticationSuccessHandler.class)
    public SavedRequestAwareAuthenticationSuccessHandler baseAuthenticationSuccessHandler() {
        return new BaseAuthenticationSuccessHandler(properties);
    }

    /**
     * session过期策略
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy baseSessionExpiredStrategy() {
        return new BaseSessionExpiredStrategy(properties);
    }

    /**
     * session无效策略
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy baseInvalidSessionStrategy() {
        return new BaseSessionInvalidStrategy(properties);
    }

    /**
     * UserDetailsService实现类
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        return new UserServiceImpl();
    }
}
