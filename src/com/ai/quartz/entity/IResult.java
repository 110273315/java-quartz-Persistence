/*
 * 文 件 名:  IResult.java
 * 版    权:  Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  yueshanfei
 * 修改时间:  Jan 4, 2013
 */
package com.ai.quartz.entity;

import java.util.List;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author   yueshanfei
 * @version  [版本号, Jan 4, 2013]
 */
public interface IResult extends List<IData>
{
    /**
     * Sort order Constants
     */
    public static final int ORDER_ASCEND = 0;
    
    public static final int ORDER_DESCEND = 1;
    
    /**
     * Key type Constants
     */
    public static final int TYPE_STRING = 2;
    
    public static final int TYPE_INTEGER = 3;
    
    public static final int TYPE_DOUBLE = 4;
    
//    public IResult getChildren();
//    public void setChildren(IResult child);
    /**
     * get object
     * @param index
     * @param name
     * @return Object
     */
    public Object get(int index, String name);
    
    /**
     * get object
     * @param index
     * @param name
     * @param def
     * @return Object
     */
    public Object get(int index, String name, Object def);
    
    /**
     * get names
     * @return String[]
     */
    public String[] getNames();
    
    /**
     * to data
     * @return IData
     */
    public IData toData() throws Exception;
    
    /**
     * get count
     * @return int
     */
    public int count();
    
    public int totalCount();
    public void setAllCount(int allcount);
    /**
     * sort single (default ascend)
     */
    public void sort(String key, int type);
    
    /**
     * sort single
     */
    public void sort(String key, int type, int order);
}
