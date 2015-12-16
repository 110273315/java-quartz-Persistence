package com.ai.quartz.service;

import java.util.List;

import com.ai.quartz.entity.IData;
import com.ai.quartz.entity.IResult;
import com.ai.quartz.entity.ScheduleJob;

/**
 * 定时任务service
 *
 * Created by liyd on 12/19/14.
 */
public interface ScheduleJobService {

    /**
     * 初始化定时任务
     */
    public void initScheduleJob();

    /**
     * 新增
     * 
     * @param param
     * @return
     */
    public Object insert(IData param);

    /**
     * 直接修改 只能修改运行的时间，参数、同异步等无法修改
     * 
     * @param ScheduleJob
     */
    public Object update(IData param);

    /**
     * 删除重新创建方式
     * 
     * @param ScheduleJob
     */
    public void delUpdate(ScheduleJob ScheduleJob);

    /**
     * 删除
     * 
     * @param scheduleJobId
     */
    public Object delete(String scheduleJobId);

    /**
     * 运行一次任务
     *
     * @param jobid the schedule job name
     * @param jobGroup the schedule job id
     * @return
     */
    public void runOnce(String jobid, String jobGroup);

    /**
     * 暂停任务
     *
     * @param jobid the schedule job name
     * @param jobGroup the schedule job id
     * @return
     */
    public void pauseJob(String jobid, String jobGroup);

    /**
     * 恢复任务
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
    public void resumeJob(String jobid, String jobGroup);

    /**
     * 获取任务对象
     * 
     * @param scheduleJobId
     * @return
     */
    public ScheduleJob get(Long scheduleJobId);

    /**
     * 查询任务列表
     * 
     * @param ScheduleJob
     * @return
     */
    public IResult queryList(ScheduleJob ScheduleJob);

    /**
     * 获取运行中的任务列表
     *
     * @return
     */
    public List<ScheduleJob> queryExecutingJobList();

}
