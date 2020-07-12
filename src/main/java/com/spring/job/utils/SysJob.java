package com.spring.job.utils;

import com.spring.common.exception.SysException;
import com.spring.common.utils.Constants;
import com.spring.common.utils.SpringUtils;
import com.spring.job.pojo.SysJobInfo;
import com.spring.job.pojo.SysJobLogInfo;
import com.spring.job.service.SysJobLogService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * quartz集成Spring
 * 定时Job
 *
 * @author  团子
 * @date 2018/4/18 15:07
 * @since V1.0
 */
public class SysJob extends QuartzJobBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        //获取Job运行时具体信息
        SysJobInfo sysJob = (SysJobInfo) context.getMergedJobDataMap()
                .get(SysJobInfo.JOB_KEY);

        //获取spring bean
        SysJobLogService sysJobLogService = (SysJobLogService) SpringUtils.getBean("sysJobLogService");

        Object jobClass;
        Method method;
        String params;
        try {
            jobClass = SpringUtils.getBean(sysJob.getBeanName());
            params = sysJob.getParams();
            if (StringUtils.isNotBlank(params)) {
                method = jobClass.getClass().getDeclaredMethod(sysJob.getMethodName(), String.class);
            } else {
                method = jobClass.getClass().getDeclaredMethod(sysJob.getMethodName());
            }
            ReflectionUtils.makeAccessible(method);
        } catch (NoSuchMethodException e) {
            throw new SysException("定时Job反射找不到具体方法!", e);
        }

        //保存执行记录
        SysJobLogInfo log = new SysJobLogInfo();
        log.setJobId(sysJob.getId());
        log.setBeanName(sysJob.getBeanName());
        log.setMethodName(sysJob.getMethodName());
        log.setParams(sysJob.getParams());
        log.setCronExpression(sysJob.getCronExpression());
        log.setCreateTime(new Date());

        //Job开始时间
        long startTime = System.currentTimeMillis();
        try {
            //利用反射执行Job
            logger.info("Job准备执行，JobID：" + sysJob.getId());
            if (StringUtils.isNotBlank(params)) {
                method.invoke(jobClass, params);
            } else {
                method.invoke(jobClass);
            }
            //Job执行总时长
            long times = System.currentTimeMillis() - startTime;
            log.setRuntime(times);
            //Job状态
            log.setStatus(Constants.JobStatus.NORMAL.getValue());
            logger.info("Job执行完毕，JobID：" + sysJob.getId() + "  总共耗时：" + times + "毫秒");
        } catch (Exception e) {
            logger.error("Job执行失败，JobID：" + sysJob.getId(), e);
            //Job执行总时长
            long times = System.currentTimeMillis() - startTime;
            log.setRuntime(times);
            log.setStatus(Constants.JobStatus.FAILED.getValue());
            log.setMsg(ExceptionUtils.getFullStackTrace(e));
        } finally {
            sysJobLogService.save(log);
        }
    }
}
