package com.spring.job.utils;

import com.spring.common.exception.SysException;
import com.spring.common.utils.Constants;
import com.spring.job.pojo.SysJobInfo;
import org.quartz.*;

/**
 * Quartz定时Job工具类
 *
 * @author  团子
 * @date 2018/4/18 14:24
 * @since V1.0
 */
public class QuartzUtils {

    private final static String JOB_NAME_PREFIX = "JOB_";
    private final static String JOB_GROUP_NAME = "MY_JOB_GROUP_NAME";
    private final static String TRIGGER_GROUP_NAME = "MY_TRIGGER_GROUP";

    /**
     * 获取trigger key
     */
    private static TriggerKey getTriggerKey(Integer jobId) {
        return TriggerKey.triggerKey(JOB_NAME_PREFIX + jobId, TRIGGER_GROUP_NAME);
    }

    /**
     * 获取job Key
     */
    private static JobKey getJobKey(Integer jobId) {
        return JobKey.jobKey(JOB_NAME_PREFIX + jobId, JOB_GROUP_NAME);
    }

    /**
     * 获取CronTrigger
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, Integer jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            throw new SysException("获取CronTrigger异常!", e);
        }
    }

    /**
     * 添加定时Job
     */
    public static void addJob(Scheduler scheduler, SysJobInfo sysJob) {
        try {
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(SysJob.class)
                    .withIdentity(getJobKey(sysJob.getId()))
                    .build();

            //添加具体Job信息
            jobDetail.getJobDataMap().put(SysJobInfo.JOB_KEY, sysJob);

            //Cron表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(sysJob.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();

            //构建trigger
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(getTriggerKey(sysJob.getId()))
                    .withSchedule(scheduleBuilder)
                    .build();

            //交给scheduler调度
            scheduler.scheduleJob(jobDetail, trigger);

            //暂停JOB
            if (sysJob.getStatus() == Constants.JobStatus.PAUSE.getValue()) {
                pauseJob(scheduler, sysJob.getId());
            }

        } catch (SchedulerException e) {
            throw new SysException("添加定时Job失败!", e);
        }
    }

    /**
     * 更新定时Job
     */
    public static void updateJob(Scheduler scheduler, SysJobInfo sysJob) {
        try {
            TriggerKey triggerKey = getTriggerKey(sysJob.getId());

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(sysJob.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();

            CronTrigger trigger = getCronTrigger(scheduler, sysJob.getId());

            //Cron表达式调度构建器
            trigger = trigger.getTriggerBuilder()
                    .withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder)
                    .build();

            //添加具体Job信息
            trigger.getJobDataMap().put(SysJobInfo.JOB_KEY, sysJob);

            //重置job
            scheduler.rescheduleJob(triggerKey, trigger);

            //暂停Job
            if (sysJob.getStatus() == Constants.JobStatus.PAUSE.getValue()) {
                pauseJob(scheduler, sysJob.getId());
            }

        } catch (SchedulerException e) {
            throw new SysException("更新定时Job失败!", e);
        }
    }

    /**
     * 立即执行Job
     */
    public static void runJob(Scheduler scheduler, SysJobInfo sysJob) {
        try {
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(SysJobInfo.JOB_KEY, sysJob);
            scheduler.triggerJob(getJobKey(sysJob.getId()), dataMap);
        } catch (SchedulerException e) {
            throw new SysException("立即执行定时Job失败!", e);
        }
    }

    /**
     * 暂停Job
     */
    public static void pauseJob(Scheduler scheduler, Integer jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new SysException("暂停定时Job失败!", e);
        }
    }

    /**
     * 恢复Job
     */
    public static void resumeJob(Scheduler scheduler, Integer jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new SysException("恢复Job失败!", e);
        }
    }

    /**
     * 删除定时Job
     */
    public static void deleteJob(Scheduler scheduler, Integer jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new SysException("删除定时Job失败!", e);
        }
    }
}
