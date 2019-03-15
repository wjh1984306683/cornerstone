package com.cs.base.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Objects;

/**
 * SpringBootContext，实现ApplicationContextAware
 * 用于获取springboot项目中的bean
 *
 * @author wangjh
 * @version 1.0
 * @className SpringBootContext
 * @see ApplicationContextAware
 * @since 2018/11/14 11:14
 */
@Slf4j
public class SpringBootContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    public SpringBootContext() {

    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
        if (applicationContext != null) {
            log.debug("applicationContext load completed");
        } else {
            throw new RuntimeException("loaded applicationContext error");
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        ApplicationContext context = getApplicationContext();
        return Objects.nonNull(context) ? context.getBean(name) : null;
    }

    public static <T> T getBean(Class<T> clazz) {
        ApplicationContext context = getApplicationContext();
        return Objects.nonNull(context) ? context.getBean(clazz) : null;
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        ApplicationContext context = getApplicationContext();
        return Objects.nonNull(context) ? context.getBean(name, clazz) : null;
    }
}
