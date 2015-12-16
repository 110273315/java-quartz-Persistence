package com.ai.quartz.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

public interface IData extends Map<String, Object>, Serializable
{
    
    public void setParent(IData parent);
    
    public IData getParent();
    
    /**
     * add object
     * @param name
     * @param def
     * @return Object
     */
    public void add(String name, Object def);
    
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param name
     * @return
     * @see [类、类#方法、类#成员]
     */
    public boolean hasValue(String name);
    
    /**
     * get object
     * @param name
     * @param def
     * @return Object
     */
    public Object get(String name, Object def);
    
    /**
     * get names
     * @return String[]
     */
    public String[] getNames();
    
    /**
     * get string
     * @param name
     * @return String
     */
    public String getString(String name);
    
    public String getEncodeString(String name);
    
    public String[] getStringAry(String name);
    
    /**
     * get string
     * @param name
     * @param defaultValue
     * @return String
     */
    public String getString(String name, String defaultValue);
    
    /**
     * get int
     * @param name
     * @return int
     */
    public int getInt(String name);
    
    /**
     * get int
     * @param name
     * @param defaultValue
     * @return int
     */
    public int getInt(String name, int defaultValue);
    
    /**
     * get double
     * @param name
     * @return double
     */
    public double getDouble(String name);
    
    /**
     * get double
     * @param name
     * @param defaultValue
     * @return double
     */
    public double getDouble(String name, double defaultValue);
    
    /**
     * get boolean
     * @param name
     * @return boolean
     */
    public boolean getBoolean(String name);
    
    /**
     * get boolean
     * @param name
     * @param defaultValue
     * @return boolean
     */
    public boolean getBoolean(String name, boolean defaultValue);
    
    /**
     * get Timestamp
     * @param name
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Timestamp getTimestamp(String name);
    
    /**
     * 把key value  映射成URL格式
     * @return
     * @see [类、类#方法、类#成员]
     */
    public String toURL();
    
}
