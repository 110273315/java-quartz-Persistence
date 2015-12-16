package com.ai.quartz.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ai.quartz.AjaxUtil;
import com.ai.quartz.entity.DataMap;
import com.ai.quartz.entity.IData;
import com.ai.quartz.entity.IResult;
import com.ai.quartz.entity.ScheduleJob;
import com.ai.quartz.service.ScheduleJobService;
import com.alibaba.fastjson.JSON;

/**
 * Created by liyd on 2015/4/3.
 */
@Controller
@RequestMapping("/job")
public class JobController
{
    
    /** job service */
    @Autowired
    private ScheduleJobService scheduleJobService;
    
    /**
     * 删除任务
     *
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String remove(HttpServletRequest request, HttpServletResponse response)
    {
        
        String jobid = request.getParameter("job_id");
        Object object = scheduleJobService.delete(jobid);
        System.out.println((Integer)object > 0 ? "成功" : "失败");
        IData ret = new DataMap();
        ret.put("success", true);
        return AjaxUtil.ajaxJson(response, JSON.toJSONString(ret));
    }
    
    /**
     * 运行一次
     *
     * @return
     */
    @RequestMapping(value = "runOnce", method = RequestMethod.POST)
    public String runOnce(HttpServletRequest request, HttpServletResponse response)
    {
        String jobid = request.getParameter("job_id");
        String jobName = request.getParameter("job_name");
        
        scheduleJobService.runOnce(jobName,jobid);
        
        IData ret = new DataMap();
        ret.put("success", true);
        return AjaxUtil.ajaxJson(response, JSON.toJSONString(ret));
    }
    
    /**
     * 暂停
     *
     * @return
     */
    @RequestMapping(value = "pause", method = RequestMethod.POST)
    public String pauseScheduleJob(HttpServletRequest request, HttpServletResponse response)
    { 
        String jobid = request.getParameter("job_id");
        String jobName = request.getParameter("job_name");
    
        scheduleJobService.pauseJob(jobName,jobid);
        IData ret = new DataMap();
        ret.put("success", true);
        return AjaxUtil.ajaxJson(response, JSON.toJSONString(ret));
    }
    
    /**
     * 恢复
     *
     * @return
     */
    @RequestMapping(value = "resume", method = RequestMethod.POST)
    public String resume(HttpServletRequest request, HttpServletResponse response)
    {
        String jobid = request.getParameter("job_id");
        String jobName = request.getParameter("job_name");
        
        scheduleJobService.resumeJob(jobName, jobid);
        IData ret = new DataMap();
        ret.put("success", true);
        return AjaxUtil.ajaxJson(response, JSON.toJSONString(ret));
    }
    
    /**
     * 保存任务
     *
     * @param scheduleJobVo
     * @return
     */
    @RequestMapping(value = "submitNewJob", method = RequestMethod.POST)
    public String submitNewJob(HttpServletRequest request,
            HttpServletResponse response, ModelMap model)
    {
        
        IData param = new DataMap();
        String jobid = request.getParameter("jobId");
        param.put("schedule_job_id", jobid);
        param.put("job_name", request.getParameter("jobName"));
        param.put("job_group", request.getParameter("jobGroup"));
        param.put("cron_expression", request.getParameter("cronExpression"));
        param.put("job_trigger", request.getParameter("jobTrigger"));
        param.put("desc", request.getParameter("desc"));
        
        Object obj = null;
        if (jobid == null || "".equals(jobid))
        {
            obj = scheduleJobService.insert(param);
        }
        else
        {
            obj = scheduleJobService.update(param);
        }
        System.out.println((Integer)obj > 0 ? "成功" : "失败");
        IData ret = new DataMap();
        ret.put("success", true);
        return AjaxUtil.ajaxJson(response, JSON.toJSONString(ret));
    }
    
    /**
     * 任务列表页
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public String list(HttpServletRequest request,
            HttpServletResponse response, ScheduleJob scheduleJobVo)
    {
        
        IResult jobs = scheduleJobService.queryList(scheduleJobVo);
        
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("data", jobs);
        return AjaxUtil.ajaxJson(response, JSON.toJSONString(ret));
    }
    
    //    @ResponseBody
    //    @RequestMapping(value = "list", method = RequestMethod.GET)
    //    public InvokeResult listget(ScheduleJob  scheduleJobVo) {
    //        
    //        IResult jobs = scheduleJobService.queryList(scheduleJobVo);
    //        
    //        Map<String, Object> ret = new HashMap<String, Object>();
    //        ret.put("data", jobs);
    //        return InvokeResult.success(ret);
    //    }
    
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(ScheduleJob scheduleJobVo)
    {
        return "job/list";
    }
    
    @RequestMapping(value = "input", method = RequestMethod.GET)
    public String input(ScheduleJob scheduleJobVo)
    {
        return "job/input";
    }
    
}
