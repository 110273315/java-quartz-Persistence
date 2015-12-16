package com.ai.quartz.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.quartz.entity.DataMap;
import com.ai.quartz.entity.IData;
import com.ai.quartz.entity.IResult;
import com.ai.quartz.entity.ResultData;

/**
 * @author   yueshanfei
 * @date  2015年10月30日
 */
@Repository("jobdao")
public class JobDAO
{
    @Autowired
    private DaoSupport dao;
    
    public IResult queryList()
    {
        try
        {
            Object object = dao.findForList("JobMapper.getList", null);
            return new ResultData(object);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
        
    }

    public IData getObject(String scheduleJobId)
    {
        try
        {
            Object object = dao.findForObject("JobMapper.getObject", scheduleJobId);
            return new DataMap(object);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public Object deleteObject(String scheduleJobId)
    {
        try
        {
          return  dao.delete("JobMapper.deleteObject", scheduleJobId);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public Object insertObject(IData param)
    {
        try
        {
           return dao.save("JobMapper.insertObject", param);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public Object updateObject(IData param)
    {
        try
        {
            return dao.update("JobMapper.updateObject", param);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
}
