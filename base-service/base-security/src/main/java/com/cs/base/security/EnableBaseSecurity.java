package com.cs.base.security;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * baseSecurity的开关
 *
 * @author wangjiahao
 * @version 1.0
 * @className EnableBaseSecurity
 * @since 2019-02-19 08:57
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(SecurityProjectImportSelector.class)
public @interface EnableBaseSecurity {
}
