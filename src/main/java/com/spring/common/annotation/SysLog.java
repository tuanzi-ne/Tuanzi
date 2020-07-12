package com.spring.common.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 * @author  团子
 * @date 2018/4/10 17:07
 * @since V1.0
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";
}
