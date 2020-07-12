package com.spring.common.shiro;

import com.spring.sys.pojo.SysUserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 *
 * @author  团子
 * @date 2018/3/5 13:44
 * @since V1.0
 */
public class ShiroUtils {
    /**
     * 加密算法
     */
    public final static String SHA256_ALGORITHM = "SHA-256";
    /**
     * 循环次数
     */
    public final static int HASH_ITERATIONS = 16;

    public static String sha256(String password, String salt) {
        return new SimpleHash(SHA256_ALGORITHM, password, salt, HASH_ITERATIONS).toString();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Session getSession() {
        return getSubject().getSession();
    }

    public static SysUserInfo getUserInfo() {
        return (SysUserInfo) getSubject().getPrincipal();
    }

    public static Integer getUserId() {
        return getUserInfo().getId();
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return getSubject().getPrincipal() != null;
    }

    public static void logout() {
        Subject subject = getSubject();
        if (subject != null && subject.isAuthenticated()) {
            subject.logout();
        }
    }

    public static String getKaptcha(String key) {
        Object kaptcha = getSessionAttribute(key);
        if (kaptcha == null) return "";
        getSession().removeAttribute(key);
        return kaptcha.toString();
    }
}
