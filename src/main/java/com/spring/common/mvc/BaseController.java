package com.spring.common.mvc;

import com.spring.sys.pojo.SysUserInfo;
import org.apache.shiro.SecurityUtils;

/**
 * controller基础类
 *
 * @author  团子
 * @since: V1.0
 * @date 2018/3/2 9:57
 */
public abstract class BaseController extends AbstractLogging {

    protected static SysUserInfo getUser() {
        return (SysUserInfo) SecurityUtils.getSubject().getPrincipal();
    }

    protected static Integer getUserId() {
        return getUser().getId();
    }
    protected static String getUsername() {
        return getUser().getUsername();
    }

    protected static Integer getDeptId() {
        return getUser().getDeptId();
    }
}
