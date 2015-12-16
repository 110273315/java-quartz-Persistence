package com.ai.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.quartz.entity.IData;
import com.ai.quartz.entity.ScheduleJob;
import com.ai.quartz.job.DefaultJob;

/**
 * 定时任务辅助类
 * 
 * Created by liyd on 12/19/14.
 */
public class ScheduleUtils
{
    
    /** 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleUtils.class);
    
    /**
     * 获取触发器key
     * 
     * @param jobName
     * @param jobGroup
     * @return
     */
    public static TriggerKey getTriggerKey(String jobName, String jobGroup)
    {
        
        return TriggerKey.triggerKey(jobName, jobGroup);
    }
    
    /**
     * 获取表达式触发器
     *
     * @param scheduler the scheduler
     * @param jobName the job name
     * @param jobGroup the job group
     * @return cron trigger
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler,
            String jobName, String jobGroup)
    {
        
        try
        {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            return (CronTrigger)scheduler.getTrigger(triggerKey);
        }
        catch (SchedulerException e)
        {
            LOG.error("获取定时任务CronTrigger出现异常", e);
            throw new ScheduleException("获取定时任务CronTrigger出现异常");
        }
    }
    
    /**
     * 创建任务
     *
     * @param scheduler the scheduler
     * @param job the schedule job
     */
    public static void createScheduleJob(Scheduler scheduler,
            IData job)
    {
        try
        {
            createScheduleJob(scheduler,
                    job.getString("job_name"),
                    job.getString("schedule_job_id"),
                    job.getString("cron_expression"),
                    job.getString("job_trigger"),
                    job);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 创建定时任务
     *
     * @param scheduler the scheduler
     * @param jobName the job name
     * @param jobGroup the job group
     * @param cronExpression the cron expression
     * @param param the param
     * @throws ClassNotFoundException 
     */
    @SuppressWarnings("unchecked")
    public static void createScheduleJob(Scheduler scheduler, String jobName,
            String jobGroup, String cronExpression,String jobTrigger, IData param)
            throws ClassNotFoundException
    {
        //同步或异步
        Class<? extends Job> jobClass =(null == jobTrigger|| "".equals(jobTrigger)) ? DefaultJob.class
                : (Class<? extends Job>)Class.forName(jobTrigger);
        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobName, jobGroup)
                .build();
        
        //放入参数，运行时的方法可以获取
        jobDetail.getJobDataMap().put(ScheduleJob.JOB_PARAM_KEY, param);
        
        //表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        
        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, jobGroup)
                .withSchedule(scheduleBuilder)
                .build();
        
        try
        {
            scheduler.scheduleJob(jobDetail, trigger);
        }
        catch (SchedulerException e)
        {
            LOG.error("创建定时任务失败", e);
            throw new ScheduleException("创建定时任务失败");
        }
    }
    
    /**
     * 运行一次任务
     * 
     * @param scheduler
     * @param jobName
     * @param jobGroup
     */
    public static void runOnce(Scheduler scheduler, String jobName,
            String jobGroup)
    {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try
        {
            scheduler.triggerJob(jobKey);
        }
        catch (SchedulerException e)
        {
            LOG.error("运行一次定时任务失败", e);
            throw new ScheduleException("运行一次定时任务失败");
        }
    }
    
    /**
     * 暂停任务
     * 
     * @param scheduler
     * @param jobName
     * @param jobGroup
     */
    public static void pauseJob(Scheduler scheduler, String jobName,
            String jobGroup)
    {
        
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try
        {
            scheduler.pauseJob(jobKey);
        }
        catch (SchedulerException e)
        {
            LOG.error("暂停定时任务失败", e);
            throw new ScheduleException("暂停定时任务失败");
        }
    }
    
    /**
     * 恢复任务
     *
     * @param scheduler
     * @param jobName
     * @param jobGroup
     */
    public static void resumeJob(Scheduler scheduler, String jobName,
            String jobGroup)
    {
        
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try
        {
            scheduler.resumeJob(jobKey);
        }
        catch (SchedulerException e)
        {
            LOG.error("暂停定时任务失败", e);
            throw new ScheduleException("暂停定时任务失败");
        }
    }
    
    /**
     * 获取jobKey
     *
     * @param jobName the job name
     * @param jobGroup the job group
     * @return the job key
     */
    public static JobKey getJobKey(String jobName, String jobGroup)
    {
        
        return JobKey.jobKey(jobName, jobGroup);
    }
    
    /**
     * 更新定时任务
     *
     * @param scheduler the scheduler
     * @param scheduleJob the schedule job
     */
    public static void updateScheduleJob(Scheduler scheduler,
            IData job)
    {
        updateScheduleJob(scheduler,
                job.getString("job_name"),
                job.getString("schedule_job_id"),
                job.getString("cron_expression"),
                job);
    }
    
    /**
     * 更新定时任务
     *
     * @param scheduler the scheduler
     * @param jobName the job name
     * @param jobGroup the job group
     * @param cronExpression the cron expression
     * @param isSync the is sync
     * @param param the param
     */
    public static void updateScheduleJob(Scheduler scheduler, String jobName,
            String jobGroup, String cronExpression,  Object param)
    {
        try
        {
            JobDetail jobDetail = scheduler.getJobDetail(getJobKey(jobName,
                    jobGroup));
            
            //            jobDetail = jobDetail.getJobBuilder().ofType(jobClass).build();
            
            //更新参数 实际测试中发现无法更新
            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            jobDataMap.put(ScheduleJob.JOB_PARAM_KEY, param);
            jobDetail.getJobBuilder().usingJobData(jobDataMap);
            
            TriggerKey triggerKey = ScheduleUtils.getTriggerKey(jobName,
                    jobGroup);
            
            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            
            CronTrigger trigger = (CronTrigger)scheduler.getTrigger(triggerKey);
            
            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder()
                    .withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder)
                    .build();
            
            //按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }
        catch (SchedulerException e)
        {
            LOG.error("更新定时任务失败", e);
            throw new ScheduleException("更新定时任务失败");
        }
    }
    
    /**
     * 删除定时任务
     *
     * @param scheduler
     * @param jobName
     * @param jobGroup
     */
    public static void deleteScheduleJob(Scheduler scheduler, String jobName,
            String jobGroup)
    {
        try
        {
            scheduler.deleteJob(getJobKey(jobName, jobGroup));
        }
        catch (SchedulerException e)
        {
            LOG.error("删除定时任务失败", e);
            throw new ScheduleException("删除定时任务失败");
        }
    }
}
