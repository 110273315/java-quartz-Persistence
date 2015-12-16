/*
 * 文 件 名:  DataMap.java
 * 版    权:  Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  yueshanfei
 * 修改时间:  2012-12-14
 */
package com.ai.quartz.entity;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ai.quartz.DateUtil;

/**
 * 对象结果集
 * 
 * @author   yueshanfei
 * @version  [版本号, 2012-12-14]
 * @see      [相关类/方法]
 * @since    [产品/模块版本]
 */
public class DataMap extends HashMap<String, Object> implements IData
{
    
    /** 注释内容 */
    private static final long serialVersionUID = 1L;
    
    private IData parent;
    
    public DataMap()
    {
        
    }
    
    public DataMap(ResultSet rs)
    {
        try
        {
            if (rs.next())
            {
                ResultSetMetaData rsmd = rs.getMetaData();
                
                for (int i = 1; i <= rsmd.getColumnCount(); i++)
                {
                    String name = rsmd.getColumnLabel(i).toUpperCase();
                    put(name, rs.getObject(i));
                    
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    public DataMap(Object object)
    {
        if (object instanceof DataMap)
        {
            putAll((DataMap)object);
        }
        else if (object instanceof Map)
        {
            putAll((Map)object);
        }
    }
    
    /**
     * get object
     * @param name
     * @param def
     * @return Object
     */
    public Object get(String name, Object def)
    {
        Object value = super.get(name);
        return value == null ? def : value;
    }
    
    /**
     * get names
     * @return String[]
     */
    public String[] getNames()
    {
        String[] names = (String[])keySet().toArray(new String[0]);
        Arrays.sort(names);
        return names;
    }
    
    /**
     * get string
     * @param name
     * @return String
     */
    public String getString(String name)
    {
        Object value = super.get(name);
        if (value instanceof String[])
        {
            return ((String[])value)[0];
        }
        return value == null ? null : value.toString();
    }
    
    public String[] getStringObject(String name)
    {
        Object value = super.get(name);
        return value == null ? null : (String[])value;
    }
    
    public Object get(String key)
    {
        if (null == key)
            return null;
        return super.get(key.toString().toUpperCase());
    }
    
    /**
     * get string
     * @param name
     * @return String
     */
    public String getString(String name, String defaultValue)
    {
        return get(name, defaultValue).toString();
    }
    
    public String[] getStringAry(String name)
    {
        Object value = super.get(name);
        return value == null ? null : (String[])value;
    }
    
    /**
     * get int
     * @param name
     * @return int
     */
    public int getInt(String name)
    {
        return getInt(name, 0);
    }
    
    /**
     * get int
     * @param name
     * @param defaultValue
     * @return int
     */
    public int getInt(String name, int defaultValue)
    {
        String value = getString(name, "");
        return "".equals(value) ? defaultValue : Integer.parseInt(value);
    }
    
    /**
     * get double
     * @param name
     * @return double
     */
    public double getDouble(String name)
    {
        return getDouble(name, 0);
    }
    
    /**
     * get double
     * @param name
     * @param defaultValue
     * @return double
     */
    public double getDouble(String name, double defaultValue)
    {
        String value = getString(name, "");
        return "".equals(value) ? defaultValue : Double.parseDouble(value);
    }
    
    /**
     * get boolean
     * @param name
     * @return boolean
     */
    public boolean getBoolean(String name)
    {
        return getBoolean(name, false);
    }
    
    /**
     * get boolean
     * @param name
     * @param defaultValue
     * @return boolean
     */
    public boolean getBoolean(String name, boolean defaultValue)
    {
        String value = getString(name, "");
        return "".equals(value) ? defaultValue : Boolean.valueOf(value).booleanValue();
    }
    
    public void add(String key, Object def)
    {
        
        if (def != null && !"".equals(def))
        {
            put(key, def);
        }
    }
    
    public boolean hasValue(String name)
    {
        String string = getString(name);
        if (null == string || "".equals(string))
        {
            return false;
        }
        return true;
    }
    
    public Timestamp getTimestamp(String name)
    {
        Object object = get(name);
        if (object == null)
            return null;
        if (object instanceof Date)
        {
            Date date = (Date)object;
            return new Timestamp(date.getTime());
        }
        else if (object instanceof Timestamp)
        {
            Timestamp date = (Timestamp)object;
            return date;
        }
        else if (object instanceof String)
        {
            String date = (String)object;
            SimpleDateFormat format = new SimpleDateFormat(DateUtil.getTimestampFormat(date));
            try
            {
                Date parse = format.parse(date);
                return new Timestamp(parse.getTime());
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            
        }
        return null;
    }
    
    public String toURL()
    {
        StringBuffer sb = new StringBuffer();
        Set<java.util.Map.Entry<String, Object>> entrySet2 = this.entrySet();
        for (Entry<String, Object> entry : entrySet2)
        {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        
        return sb.toString().substring(0, sb.toString().length() - 1);
    }
    
    public String getEncodeString(String name)
    {
        try
        {
            String string = getString(name);
            if (null == string)
            {
                return null;
            }
            return new String(string.getBytes("ISO8859-1"), "utf-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public void putAll(Map<? extends String, ? extends Object> m)
    {
        super.putAll(m);
        //        if (null == m || m.isEmpty())
        //            return;
        //        for (String key : m.keySet())
        //        {
        //            Object object = m.get(key);
        //            if (object instanceof String[])
        //            {
        //                Object value = ((String[])object)[0];
        //                put(key, value);
        //            }
        //            else
        //            {
        //                put(key, object);
        //            }
        //        }
    }
    
    public void setParent(IData parent)
    {
        this.parent = parent;
    }
    
    public IData getParent()
    {
        return parent;
    }
    
}
