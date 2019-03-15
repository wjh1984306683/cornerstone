package com.cs.base.security.config;

import com.cs.base.security.properties.BaseProperties;
import com.cs.base.security.properties.BaseSecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.sql.DataSource;

/**
 * Security核心配置类
 *
 * @author wangjiahao
 * @version 1.0
 * @className BaseSecurityConfiguration
 * @since 2019-01-30 17:44
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(BaseProperties.class)
public class BaseSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BaseProperties properties;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SessionInformationExpiredStrategy sessionExpiredStrategy;
    @Autowired
    private InvalidSessionStrategy sessionInvalidStrategy;
    @Autowired
    private SimpleUrlAuthenticationFailureHandler failureHandler;
    @Autowired
    private SavedRequestAwareAuthenticationSuccessHandler successHandler;

    /**
     * rememberme功能使用security自带的实现方式
     * 第一次运行时需要开启，生成表之后，必须关闭
     * <code>repository.setCreateTableOnStartup</code>
     */
    @Bean
    @ConditionalOnProperty(value = "base.security.rememberMe.enable", havingValue = "true")
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        repository.setCreateTableOnStartup(properties.getRememberMe().getFirstStartUp());
        return repository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //                登录相关
        http.formLogin()
                .loginPage(BaseSecurityConstants.AUTHENTICATION)
                .loginProcessingUrl(BaseSecurityConstants.AUTHENTICATION_FORM)
                .failureHandler(failureHandler)
                .successHandler(successHandler)
                .and()
//                登出相关
                .logout()
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .logoutSuccessUrl(properties.getHomeUrl())
                .permitAll(false)
                .and()
                .userDetailsService(userDetailsService)
//                禁用csrf防护
                .csrf().disable()
                //分为两部分添加不拦截的地址，1为单独的系统地址
//                拦截地址
                .authorizeRequests()
                .antMatchers(BaseSecurityConstants.AUTHENTICATION,
                        BaseSecurityConstants.AUTHENTICATION_FORM,
                        properties.getLoginUrl(),
                        properties.getHomeUrl())
                .permitAll()
                .and()
                //2为不拦截地址数组
                .authorizeRequests()
                .antMatchers(properties.getPermitUrls().toArray(new String[0]))
                .permitAll()
                .anyRequest().authenticated();

        if (properties.getSession().getEnable()) {
//                session相关
            http.sessionManagement()
                    .invalidSessionStrategy(sessionInvalidStrategy)
                    .maximumSessions(properties.getSession().getMaxNum())
                    .expiredSessionStrategy(sessionExpiredStrategy)
//                阻止后一次重复登录的行为
                    .maxSessionsPreventsLogin(properties.getSession().getPreventsLogin());
        }

        if (properties.getRememberMe().getEnable()) {
//                remember me相关
            http.rememberMe().rememberMeParameter("rememberMe")
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(properties.getRememberMe().getValidTime());
        }
    }

    /**
     * 静态资源拦截设置
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers(properties.getPermitStaticResource().toArray(new String[0]))
                .mvcMatchers(properties.getPermitHtml().toArray(new String[0]));
    }

}
