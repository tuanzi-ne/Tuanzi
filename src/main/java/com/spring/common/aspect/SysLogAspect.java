package com.spring.common.aspect;

import com.alibaba.fastjson.JSON;
import com.spring.common.annotation.SysLog;
import com.spring.common.shiro.ShiroUtils;
import com.spring.common.utils.IPUtils;
import com.spring.sys.pojo.SysLogInfo;
import com.spring.sys.pojo.SysUserInfo;
import com.spring.sys.service.SysLogService;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 系统日志切面类
 *
 * @author  团子
 * @date 2018/4/10 17:38
 * @since V1.0
 */
@Aspect
@Component
public class SysLogAspect {
    @Autowired
    private SysLogService sysLogService;

    @Autowired(required = false)
    private HttpServletRequest request;

    @Pointcut("@annotation(com.spring.common.annotation.SysLog)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时间(毫秒)
        long time = System.currentTimeMillis() - startTime;
        //保存日志
        saveLog(point, time);
        return result;
    }

    private void saveLog(ProceedingJoinPoint point, long time) {
        SysUserInfo user = ShiroUtils.getUserInfo();
        if (user == null)
            return;
        String username = user.getUsername();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        SysLogInfo info = new SysLogInfo();
        SysLog syslog = method.getAnnotation(SysLog.class);
        if (syslog != null) {
            info.setDesc(syslog.value());
        }
        info.setIp(IPUtils.getIpAddr(request));
        info.setUrl(request.getRequestURI());
        info.setParam(filterParams(request));
        info.setMethod(request.getMethod());
        info.setUsername(username);
        info.setRuntime(time);
        info.setCreateTime(new Date());
        //开启线程保存日志
        new Thread(new SaveLog(info)).start();
    }

    private String filterParams(HttpServletRequest request) {
        Map<String, String> pMap = new HashMap<>();
        Map<String, String[]> map = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("password")) {
                pMap.put(entry.getKey(), "***");
            } else {
                pMap.put(entry.getKey(), StringUtils.join(entry.getValue(), ","));
            }
        }
        return JSON.toJSONString(pMap);
    }

    /**
     * 异步保存日志
     */
    private class SaveLog implements Runnable {
        private SysLogInfo info;

        private SaveLog(SysLogInfo sysLogInfo) {
            this.info = sysLogInfo;
        }

        @Override
        public void run() {
            sysLogService.save(info);
        }
    }

}
