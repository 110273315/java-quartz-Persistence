/*
 * 文 件 名:  DataComparator.java
 * 版    权:  Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  yueshanfei
 * 修改时间:  Jan 4, 2013
 */
package com.ai.quartz.entity;

import java.util.Comparator;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author   yueshanfei
 * @version  [版本号, Jan 4, 2013]
 * @see      [相关类/方法]
 * @since    [产品/模块版本]
 */
public class DataComparator implements Comparator<IData>
{
    private String key;
    
    private int keyType;
    
    private int order;
    
    public DataComparator(String key, int keyType, int order)
    {
        this.key = key;
        this.keyType = keyType;
        this.order = order;
    }
    
    public int compare(IData data1, IData data2)
    {
        
        if (order == IResult.ORDER_ASCEND)
        {
            if (keyType == IResult.TYPE_STRING)
            {
                String value1 = data1.getString(key);
                String value2 = data2.getString(key);
                return value1.compareTo(value2);
            }
            else if (keyType == IResult.TYPE_INTEGER)
            {
                int value1 = data1.getInt(key);
                int value2 = data2.getInt(key);
                return value1 < value2 ? -1 : (value1 == value2 ? 0 : 1);
            }
            else if (keyType == IResult.TYPE_DOUBLE)
            {
                double value1 = data1.getDouble(key);
                double value2 = data2.getDouble(key);
                return value1 < value2 ? -1 : (value1 == value2 ? 0 : 1);
            }
        }
        else
        {
            if (keyType == IResult.TYPE_STRING)
            {
                String value1 = data1.getString(key);
                String value2 = data2.getString(key);
                return value2.compareTo(value1);
            }
            else if (keyType == IResult.TYPE_INTEGER)
            {
                int value1 = data1.getInt(key);
                int value2 = data2.getInt(key);
                return value1 > value2 ? -1 : (value1 == value2 ? 0 : 1);
            }
            else if (keyType == IResult.TYPE_DOUBLE)
            {
                double value1 = data1.getDouble(key);
                double value2 = data2.getDouble(key);
                return value1 > value2 ? -1 : (value1 == value2 ? 0 : 1);
            }
        }
        return 0;
    }
}
