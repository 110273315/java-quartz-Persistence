package com.ai.quartz.job;

import java.text.SimpleDateFormat;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ai.quartz.entity.IData;
import com.ai.quartz.entity.ScheduleJob;

/**
 * @author   yueshanfei
 * @date  2015年10月29日
 */
public class Testjob implements Job
{
    
    /**
     * @param arg0
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException
    {
        JobDataMap mergedJobDataMap = arg0.getMergedJobDataMap();
        IData scheduleJob = (IData)mergedJobDataMap.get(ScheduleJob.JOB_PARAM_KEY);
        
        System.out.println("---------------"
                + scheduleJob.get("schedule_job_id")+" "+scheduleJob.get("job_name")
                + "---------"
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(System.currentTimeMillis()));
        
    }
    
}
