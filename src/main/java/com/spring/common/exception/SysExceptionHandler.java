package com.spring.common.exception;

import com.spring.common.mvc.AbstractLogging;
import com.spring.common.shiro.ShiroUtils;
import com.spring.common.utils.HttpServletUtils;
import com.spring.common.utils.Result;
import com.spring.sys.pojo.SysExpInfo;
import com.spring.sys.service.SysExpService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
/**  
 * @Title: SysExceptionHandler
 * @Description: 自定义异常处理类
 * @author  团子
 * @date 2018/8/5 11:11
 * @since v1.0
 */
@ControllerAdvice
public class SysExceptionHandler extends AbstractLogging {
    @Autowired
    SysExpService sysExpService;

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(SysException.class)
    @ResponseBody
    public Result handleSysException(SysException e) {
        log.error(e.getMessage(), e);
        saveExp(e);
        return Result.error(e.getMessage());
    }

    /**
     * 处理授权异常
     */
    @ExceptionHandler(AuthorizationException.class)
    public Object handleAuthorizationException(AuthorizationException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        saveExp(e);
        if (HttpServletUtils.jsAjax(request)) {
            return Result.error("未授权");
        }
        return new ModelAndView("error/403");
    }

    /**
     * 处理未知异常
     */
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        saveExp(e);
        if (HttpServletUtils.jsAjax(request)) {
            return Result.error(e.getMessage());
        }
        return new ModelAndView("error");
    }

    /**
     * 保存异常
     */
    private void saveExp(Exception e) {
        SysExpInfo exp = new SysExpInfo();
        exp.setExpection(ExceptionUtils.getFullStackTrace(e));
        exp.setUsername(ShiroUtils.getUserInfo().getUsername());
        exp.setCreateTime(new Date());
        sysExpService.save(exp);
    }

}
