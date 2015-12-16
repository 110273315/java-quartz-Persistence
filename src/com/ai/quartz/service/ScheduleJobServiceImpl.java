package com.ai.quartz.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.CronTrigger;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.quartz.ScheduleUtils;
import com.ai.quartz.dao.JobDAO;
import com.ai.quartz.entity.IData;
import com.ai.quartz.entity.IResult;
import com.ai.quartz.entity.ScheduleJob;

/**
 * 定时任务
 *
 * Created by liyd on 12/19/14.
 */
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService
{
    
    /** 调度工厂Bean */
    @Autowired
    private Scheduler scheduler;
    
    /** 通用dao */
    @Autowired
    private JobDAO jdbcDao;
    
    public void initScheduleJob()
    {
        IResult list = jdbcDao.queryList();
        if (CollectionUtils.isEmpty(list))
        {
            return;
        }
        for (IData data : list)
        {
            
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler,
                    data.getString("job_name"),
                    data.getString("schedule_job_id"));
            
            //不存在，创建一个
            if (cronTrigger == null)
            {
                ScheduleUtils.createScheduleJob(scheduler, data);
            }
            else
            {
                //已存在，那么更新相应的定时设置
                ScheduleUtils.updateScheduleJob(scheduler, data);
            }
        }
    }
    
    @Override
    public Object insert(IData param)
    {
        Object object = jdbcDao.insertObject(param);
        
        ScheduleUtils.createScheduleJob(scheduler, param);
        return object;
    }
    
    @Override
    public Object update(IData param)
    {
        
        ScheduleUtils.updateScheduleJob(scheduler, param);
        return jdbcDao.updateObject(param);
    }
    
    @Override
    public void delUpdate(ScheduleJob ScheduleJob)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public Object delete(String scheduleJobId)
    {
        IData scheduleJob = jdbcDao.getObject(scheduleJobId);
        //删除运行的任务
        ScheduleUtils.deleteScheduleJob(scheduler,
                scheduleJob.getString("job_name"),
                scheduleJob.getString("schedule_job_id"));
        //删除数据
        return jdbcDao.deleteObject(scheduleJobId);
        
    }
    
    @Override
    public void runOnce(String jobid, String jobGroup)
    {
        ScheduleUtils.runOnce(scheduler, jobid, jobGroup);
    }
    
    @Override
    public void pauseJob(String jobid, String jobGroup)
    {
        ScheduleUtils.pauseJob(scheduler, jobid, jobGroup);
        
    }
    
    @Override
    public void resumeJob(String jobid, String jobGroup)
    {
        ScheduleUtils.resumeJob(scheduler, jobid, jobGroup);
    }
    
    @Override
    public ScheduleJob get(Long scheduleJobId)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public IResult queryList(ScheduleJob scheduleJob)
    {
        IResult result = jdbcDao.queryList();
        try
        {
            for (IData idata : result)
            {
                JobKey jobKey = ScheduleUtils.getJobKey(idata.getString("job_name"),
                        idata.getString("schedule_job_id"));
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                if (CollectionUtils.isEmpty(triggers))
                {
                    continue;
                }
                //这里一个任务可以有多个触发器， 但是我们一个任务对应一个触发器，所以只取第一个即可，清晰明了
                Trigger trigger = triggers.iterator().next();
                scheduleJob.setJobTrigger(trigger.getKey().getName());
                
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                idata.put("status", triggerState.name());
                
                if (trigger instanceof CronTrigger)
                {
                    CronTrigger cronTrigger = (CronTrigger)trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    idata.put("cron_expression", cronExpression);
                }
                
            }
        }
        catch (SchedulerException e)
        {
            //演示用，就不处理了
        }
        return result;
    }
    
    @Override
    public List<ScheduleJob> queryExecutingJobList()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    //    
    //    public void delUpdate(ScheduleJob ScheduleJob)
    //    {
    //        ScheduleJob scheduleJob = ScheduleJob.getTargetObject(ScheduleJob.class);
    //        //先删除
    //        ScheduleUtils.deleteScheduleJob(scheduler,
    //                scheduleJob.getJobName(),
    //                scheduleJob.getJobGroup());
    //        //再创建
    //        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
    //        //数据库直接更新即可
    //        jdbcDao.update(scheduleJob);
    //    }
    //    
    //    
    //    public ScheduleJob get(Long scheduleJobId)
    //    {
    //        ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
    //        return scheduleJob.getTargetObject(ScheduleJob.class);
    //    }
    //    
    //    public IResult queryList(ScheduleJob  ScheduleJob)
    //    {
    //        
    //        IResult scheduleJobs = jdbcDao.queryList(ScheduleJob.getTargetObject(ScheduleJob.class));
    //        
    //        List<ScheduleJob> ScheduleJobList = BeanConverter.convert(ScheduleJob.class,
    //                scheduleJobs);
    //        try
    //        {
    //            for (ScheduleJob vo : ScheduleJobList)
    //            {
    //                
    //                JobKey jobKey = ScheduleUtils.getJobKey(vo.getJobName(),
    //                        vo.getJobGroup());
    //                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
    //                if (CollectionUtils.isEmpty(triggers))
    //                {
    //                    continue;
    //                }
    //                
    //                //这里一个任务可以有多个触发器， 但是我们一个任务对应一个触发器，所以只取第一个即可，清晰明了
    //                Trigger trigger = triggers.iterator().next();
    //                ScheduleJob.setJobTrigger(trigger.getKey().getName());
    //                
    //                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
    //                vo.setStatus(triggerState.name());
    //                
    //                if (trigger instanceof CronTrigger)
    //                {
    //                    CronTrigger cronTrigger = (CronTrigger)trigger;
    //                    String cronExpression = cronTrigger.getCronExpression();
    //                    vo.setCronExpression(cronExpression);
    //                }
    //            }
    //        }
    //        catch (SchedulerException e)
    //        {
    //            //演示用，就不处理了
    //        }
    //        return ScheduleJobList;
    //    }
    //    
    //    public List<ScheduleJob> queryExecutingJobList()
    //    {
    //        try
    //        {
    //            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
    //            List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(
    //                    executingJobs.size());
    //            for (JobExecutionContext executingJob : executingJobs)
    //            {
    //                ScheduleJob job = new ScheduleJob();
    //                JobDetail jobDetail = executingJob.getJobDetail();
    //                JobKey jobKey = jobDetail.getKey();
    //                Trigger trigger = executingJob.getTrigger();
    //                ScheduleJob object = (ScheduleJob)executingJob.getMergedJobDataMap()
    //                        .get(ScheduleJob.JOB_PARAM_KEY);
    //                job.setAliasName(object.getAliasName());
    //                job.setJobName(jobKey.getName());
    //                job.setJobGroup(jobKey.getGroup());
    //                job.setJobTrigger(executingJob.getJobInstance()
    //                        .getClass()
    //                        .toString());
    //                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
    //                job.setStatus(triggerState.name());
    //                if (trigger instanceof CronTrigger)
    //                {
    //                    CronTrigger cronTrigger = (CronTrigger)trigger;
    //                    String cronExpression = cronTrigger.getCronExpression();
    //                    job.setCronExpression(cronExpression);
    //                }
    //                jobList.add(job);
    //            }
    //            return jobList;
    //        }
    //        catch (SchedulerException e)
    //        {
    //            //演示用，就不处理了
    //            return null;
    //        }
    //        
    //    }
}
