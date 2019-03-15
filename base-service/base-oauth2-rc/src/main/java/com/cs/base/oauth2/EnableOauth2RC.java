package com.cs.base.oauth2;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangjiahao
 * @version 1.0
 * @className EnableOauth2RC
 * @since 2019-03-06 17:12
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(Oauth2RCImportSelector.class)
public @interface EnableOauth2RC {
}
