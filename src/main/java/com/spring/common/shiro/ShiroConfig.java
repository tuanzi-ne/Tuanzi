package com.spring.common.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.spring.common.utils.Constants;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置类
 *
 * @author  团子
 * @date 2018/3/5 14:08
 * @since V1.0
 */
@Configuration
public class ShiroConfig {

    @Bean("simpleCookie")
    public SimpleCookie simpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName("sid");
        simpleCookie.setHttpOnly(true);
        // 30天过期
        simpleCookie.setMaxAge(30 * 24 * 60 * 60);
        return simpleCookie;
    }


    /**
     * shiro ehcache cache
     * @return EhCacheManager
     */
    @Bean("shiroEhCacheManager")
    public EhCacheManager ehCacheManager(CacheManager cacheManager) {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }

    /**
     * session ehcache cache
     * @param shiroEhCacheManager ehcache管理
     * @return EnterpriseCacheSessionDAO
     */
    @Bean("ehcacheSessionDao")
    public EnterpriseCacheSessionDAO ehcacheSessionDao(EhCacheManager shiroEhCacheManager) {
        EnterpriseCacheSessionDAO ehcacheSessionDao = new EnterpriseCacheSessionDAO();
        ehcacheSessionDao.setCacheManager(shiroEhCacheManager);
        ehcacheSessionDao.setActiveSessionsCacheName("shiro-session-cache");
        return ehcacheSessionDao;
    }


    /**
     * redisManager jedis pool config
     * @return JedisPoolConfig
     */
    @Bean("jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig(
            @Value("${session.redis.minIdle}") int minIdle,
            @Value("${session.redis.maxIdle}") int maxIdle,
            @Value("${session.redis.maxTotal}") int maxTotal,
            @Value("${session.redis.maxWaitMillis}") int maxWaitMillis,
            @Value("${session.redis.testOnBorrow}") boolean testOnBorrow
    ){
        JedisPoolConfig jpc = new JedisPoolConfig();
        jpc.setMinIdle(minIdle);
        jpc.setMaxIdle(maxIdle);
        jpc.setMaxTotal(maxTotal);
        jpc.setMaxWaitMillis(maxWaitMillis);
        jpc.setTestOnBorrow(testOnBorrow);
        return jpc;
    }

    /**
     * redisManager
     * @return RedisManager
     */
    @Bean("redisManager")
    public RedisManager redisManager(
            @Value("${session.redis.host}") String host,
            @Value("${session.redis.port}") int port,
            @Value("${session.redis.password}") String password,
            @Value("${session.redis.database}") int database,
            @Value("${session.redis.timeout}") int timeout,
            JedisPoolConfig jedisPoolConfig
    ) {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setPassword(password);
        redisManager.setDatabase(database);
        redisManager.setTimeout(timeout);
        redisManager.setJedisPoolConfig(jedisPoolConfig);
        return redisManager;
    }

    /**
     * redis cache
     * @return redisCacheManager
     */
    @Bean("redisCacheManager")
    public RedisCacheManager redisCacheManager(RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        return redisCacheManager;
    }

    /**
     * session redis cache,由第三方实现
     * @param redisManager redis管理
     * @return RedisSessionDAO
     */
    @Bean("redisSessionDAO")
    public RedisSessionDAO redisSessionDAO(RedisManager redisManager) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager);
        return redisSessionDAO;
    }

    @Bean("sessionManager")
    public SessionManager sessionManager(SimpleCookie simpleCookie,
                                         EnterpriseCacheSessionDAO ehcacheSessionDao,
                                         RedisSessionDAO redisSessionDAO,
                                         @Value("${shiro.cache.type}") int cacheType) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //设置session过期时间为1小时(单位：毫秒),默认30分钟
        sessionManager.setGlobalSessionTimeout(60 * 60 * 1000);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        //启用定时器,定期检测会话
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionValidationInterval(60 * 60 * 1000);
        //创建会话Cookie
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(simpleCookie);
        //ehcache存储session,默认存储
        if (Constants.ShiroCacheType.EHCACHE.getValue() == cacheType) {
            sessionManager.setSessionDAO(ehcacheSessionDao);
        }
        // redis存储session
        else if (Constants.ShiroCacheType.REDIS.getValue() == cacheType) {
            sessionManager.setSessionDAO(redisSessionDAO);
        }
        return sessionManager;
    }

    @Bean("securityManager")
    public SecurityManager securityManager(UserRealm userRealm,
                                           SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //缓存userRealm
//        userRealm.setCachingEnabled(true);
//        userRealm.setAuthenticationCachingEnabled(true);
//        userRealm.setAuthenticationCacheName("shiro-userRealm");
//        userRealm.setAuthorizationCachingEnabled(true);
//        userRealm.setAuthorizationCacheName("shiro-userRealm");
        securityManager.setRealm(userRealm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    /**
     * ShiroDialect,thymeleaf shiro标签bean
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setLoginUrl("/login");
        shiroFilter.setSuccessUrl("/admin");
        shiroFilter.setUnauthorizedUrl("/error/403");
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/login", "anon");
        filterMap.put("/css/**", "anon");
        filterMap.put("/fonts/**", "anon");
        filterMap.put("/img/**", "anon");
        filterMap.put("/js/**", "anon");
        filterMap.put("/plugins/**", "anon");
        filterMap.put("/profile/**", "anon");
        filterMap.put("/druid/**", "anon");
        filterMap.put("/captcha", "anon");
        filterMap.put("/upload/**", "anon");
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/**", "authc");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
