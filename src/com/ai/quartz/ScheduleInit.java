package com.ai.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.quartz.service.ScheduleJobService;

/**
 * 定时任务初始化
 *
 * Created by liyd on 12/19/14.
 */
@Component
public class ScheduleInit{

    /** 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleInit.class);

    /** 定时任务service */
    @Autowired
    private ScheduleJobService  scheduleJobService;

    /**
     * 项目启动时初始化
     */
    public void init() {

        if (LOG.isInfoEnabled()) {
            LOG.info("init");
        }

        scheduleJobService.initScheduleJob();

        if (LOG.isInfoEnabled()) {
            LOG.info("end");
        }
    }

}
