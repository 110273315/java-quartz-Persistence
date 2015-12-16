/*
 * 文 件 名:  ResultData.java
 * 版    权:  Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  yueshanfei
 * 修改时间:  Jan 4, 2013
 */
package com.ai.quartz.entity;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 列表结果集
 * 
 * @author   yueshanfei
 * @version  [版本号, Jan 4, 2013]
 * @see      [相关类/方法]
 * @since    [产品/模块版本]
 */
public class ResultData extends ArrayList<IData> implements IResult
{
    /** 注释内容 */
    private static final long serialVersionUID = 1L;
    
    private List<String> names = new ArrayList<String>();
    
//    private IResult child;
    
    private int totalCount = 0;
    
    public ResultData()
    {
    }
    
    public ResultData(List<Object> list)
    {
        for (Object object : list)
        {
           add(new DataMap(object));
        }
    }
    @SuppressWarnings("unchecked")
    public ResultData(Object object)
    {
        if (object instanceof List)
        {
            List<Object> list = (List<Object>) object;
            for (Object obj : list)
            {
                add(new DataMap(obj));
            }
        }
        
    }
    /**
     * construct function
     * @param rs
     * @throws Exception
     */
    public ResultData(ResultSet rs)
    {
        try
        {
            while (rs.next())
            {
                ResultSetMetaData rsmd = rs.getMetaData();
                
                IData data = new DataMap();
                for (int i = 1; i <= rsmd.getColumnCount(); i++)
                {
                    String name = rsmd.getColumnLabel(i).toUpperCase();
                    data.put(name, rs.getObject(i));
                    
                    if (rs.isFirst())
                        names.add(name);
                }
                add(data);
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public ResultData(IData data, IData param) throws Exception
    {
        for (String name : param.getNames())
        {
            String value = param.getString(name);
            StringTokenizer st = new StringTokenizer(value, "|");
            while (st.hasMoreElements())
            {
                String id = (String)st.nextElement();
                IData d = new DataMap();
                d.putAll(data);
                d.add(name, id);
                this.add(d);
            }
        }
    }
    
    
    public Object get(int index, String name)
    {
        IData data = get(index);
        return data == null ? null : data.get(name);
    }
    
    public Object get(int index, String name, Object def)
    {
        Object value = get(index, name);
        return value == null ? def : value;
    }
    
    public String[] getNames()
    {
        return (String[])names.toArray(new String[0]);
    }
    
    /**
     * to data
     * @return IData
     */
    public IData toData() throws Exception
    {
        IData data = new DataMap();
        
        Iterator<IData> it = iterator();
        while (it.hasNext())
        {
            IData element = it.next();
            Iterator<String> iterator = element.keySet().iterator();
            while (iterator.hasNext())
            {
                String key = (String)iterator.next();
                if (data.containsKey(key))
                {
                    @SuppressWarnings("unchecked")
                    List<Object> list = (List<Object>)data.get(key);
                    list.add(element.get(key));
                }
                else
                {
                    List<Object> list = new ArrayList<Object>();
                    list.add(element.get(key));
                    data.put(key, list);
                }
            }
        }
        
        return data;
    }
    
    public int count()
    {
        return size();
    }
    
    public int totalCount()
    {
        return totalCount;
    }
    
    public void setAllCount(int allcount)
    {
        totalCount = allcount;
    }
    
    public void sort(String key, int type)
    {
        sort(key, type, IResult.ORDER_ASCEND);
        
    }
    
    public void sort(String key, int type, int order)
    {
        IData[] datas = (IData[])this.toArray();
        DataComparator c = new DataComparator(key, type, order);
        Arrays.sort(datas, c);
        
        List<IData> list = Arrays.asList(datas);
        
        this.clear();
        this.addAll(list);
        
    }
    
    /**
     * clear
     */
    public void clear()
    {
        super.clear();
        names = new ArrayList<String>();
    }
    
//    public IResult getChildren()
//    {
//        return child;
//    }
//    
//    public void setChildren(IResult child)
//    {
//        if (this.child == null)
//        {
//            this.child = new ResultData();
//        }
//        this.child.addAll(child);
//        
//    }

    
    
}
