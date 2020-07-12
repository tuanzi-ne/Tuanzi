package com.spring.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.LocaleResolver;
import java.util.Locale;

/**
 * @author  团子
 * @date 2018/4/17 16:44
 * @since V1.0
 */
@Component
@Lazy(false)
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    public static Object getBean(String name) {
        Assert.hasText(name,"Bean名称不能为空!");
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> type) {
        Assert.hasText(name,"Bean名称不能为空!");
        Assert.notNull(type,"Bean类型不能为NULL!");
        return applicationContext.getBean(name, type);
    }

    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    public static boolean isSingleton(String name) {
        return applicationContext.isSingleton(name);
    }

    public static Class<?> getType(String name) {
        return applicationContext.getType(name);
    }

    public static String getMessage(String code) {
        return getMessage("localeResolver",code,null);
    }

    public static String getMessage(String code, Object[] args) {
        return getMessage("localeResolver",code,args);
    }

    public static String getMessage(String localStr, String code, Object[] args) {
        LocaleResolver localeResolver =  getBean(localStr,
                LocaleResolver.class);
        Locale locale = localeResolver.resolveLocale(null);
        return applicationContext.getMessage(code, args, locale);
    }

}