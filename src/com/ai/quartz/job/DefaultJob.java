package com.ai.quartz.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.quartz.entity.IData;
import com.ai.quartz.entity.ScheduleJob;

/**
 * 默认job
 *
 * Created by liyd on 12/19/14.
 */
public class DefaultJob implements Job
{
    
    /* 日志对象 */
    private static final Logger log = LoggerFactory.getLogger(DefaultJob.class);
    
    public void execute(JobExecutionContext jobExecutionContext)
            throws JobExecutionException
    {
        log.debug("-------------default job.....");
        JobDataMap mergedJobDataMap = jobExecutionContext.getMergedJobDataMap();
        IData scheduleJob = (IData)mergedJobDataMap.get(ScheduleJob.JOB_PARAM_KEY);
        
        System.out.println("jobName:----------" + scheduleJob.get("schedule_job_id")+":"+scheduleJob.get("job_name")
                + " ____________ " + scheduleJob);
    }
}
