package com.ai.quartz;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DateUtil {
	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

	private final static SimpleDateFormat sdfDay = new SimpleDateFormat(
			"yyyy-MM-dd");
	
	private final static SimpleDateFormat sdfDays = new SimpleDateFormat(
	"yyyyMMdd");

	private final static SimpleDateFormat sdfTime = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 获取YYYY格式
	 * 
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * 
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}
	
	/**
	 * 获取YYYYMMDD格式
	 * 
	 * @return
	 */
	public static String getDays(){
		return sdfDays.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * 
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	/**
	* 日期比较，如果s>=e 返回true 否则返回false
	* @param s
	* @param e
	* @return boolean  
	* @throws
	* @author
	 */
	public static boolean compareDate(String s, String e) {
		if(fomatDate(s)==null||fomatDate(e)==null){
			return false;
		}
		return fomatDate(s).getTime() >=fomatDate(e).getTime();
	}

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 校验日期是否合法
	 * 
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}
	public static int getDiffYear(String startTime,String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
//			long aa=0;
			int years=(int) (((fmt.parse(endTime).getTime()-fmt.parse(startTime).getTime())/ (1000 * 60 * 60 * 24))/365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}
	  /**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr,String endDateStr){
        long day=0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate = null;
        java.util.Date endDate = null;
        
            try {
				beginDate = format.parse(beginDateStr);
				endDate= format.parse(endDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
            //System.out.println("相隔的天数="+day);
      
        return day;
    }
    
    /**
     * 获取当前日期相差多少天
     * @param date
     * @return
     */
    public static long getDiffDays(Date date) {
    	return getDiffDays(date, new Date());
    }
    /**
     * 获取两个日期相差多少天
     * @param date
     * @return
     */
    public static long getDiffDays(Date beginDate, Date endDate) {
    	long diff = endDate.getTime() - beginDate.getTime();
    	long days = diff / (1000 * 60 * 60 * 24);
    	return days;
    }
    
    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        
        return dateStr;
    }
    
    
    public static List<String> getAfterWorkingDays(Date beginDate) {
    	List<String> days = new ArrayList<String>();
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	
    	long amount = getDiffDays(beginDate);
    	Calendar tCalendar = Calendar.getInstance();
    	for(int i = 1; i <= amount; i++) {
    		tCalendar.setTime(beginDate);
    		tCalendar.add(Calendar.DATE, i);
    		int dayOfWeek = tCalendar.get(Calendar.DAY_OF_WEEK);
			switch (dayOfWeek) {
			//星期日
			case 1:
				break;
			//星期一
			case 2:
				days.add(format.format(tCalendar.getTime()));
				break;
			//星期二
			case 3:
				days.add(format.format(tCalendar.getTime()));
				break;
			//星期三
			case 4:
				days.add(format.format(tCalendar.getTime()));
				;
				break;
			//星期四
			case 5:
				days.add(format.format(tCalendar.getTime()));
				break;
			//星期五
			case 6:
				days.add(format.format(tCalendar.getTime()));
				break;
			//星期六
			case 7:
				break;
			}
    	}
    	return days;
    }
    /**
     * 得到n天之后是周几
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);
        
        return dateStr;
    }
    
    /**
     * 获取一周的某一天，日期格式为yyyy-MM-dd
     * @param amount 
     * @param value 如：星期五Calendar.FRIDAY
     * @return
     */
    public static String getOneDayInWeek(int amount, int value) {
    	
    	 Calendar tCalendar = Calendar.getInstance();
    	 tCalendar.add(Calendar.DATE, amount);
    	 tCalendar.set(Calendar.DAY_OF_WEEK, value);
    	 String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(tCalendar.getTime());
    	 return dateStr;
    } 
    
    /**
     * 判断日期是否为今天之前
     * @param strDate
     * @return 
     */
    public static boolean isBefore(String strDate) {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	try {
    		Date date = format.parse(strDate);
    		return date.before(new Date());
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return false;
    }
    
    /**
     * get timestamp format
     * @param value
     * @return String
     */
    public static String getTimestampFormat(String value)
    {
        switch (value.length())
        {
            case 4:
                return "yyyy";
            case 6:
                return "yyyyMM";
            case 7:
                return "yyyy-MM";
            case 8:
                return "yyyyMMdd";
            case 10:
                return "yyyy-MM-dd";
            case 13:
                return "yyyy-MM-dd HH";
            case 16:
                return "yyyy-MM-dd HH:mm";
            case 19:
                return "yyyy-MM-dd HH:mm:ss";
            case 21:
                return "yyyy-MM-dd HH:mm:ss.S";
        }
        return null;
    }
    
    
    /**
	 * 某个月按周一到周日列出所有的周
	 * @param year
	 * @param month
	 * @param c
	 * @return 20150901
	 */
	public static List<String> getWeeksByMonth(int year,int month,Calendar c)
	{
		List<String> dayList = new ArrayList<String>();

		c.set(year, month-1, 1);

		int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);

		int dayinweek = c.get(Calendar.DAY_OF_WEEK);

		dayinweek = dayinweek==1?7:dayinweek-1;


		for (int i=1; i<=days; i++) {
			
			String str = "";
			if (i==1 || (i-1+dayinweek)%7==1)
			{
				
				str += DateUtil.formatTime(year, month, i);
			}

			if (i==days || (i-1+dayinweek)%7==0) 
			{
				
				str += DateUtil.formatTime(year, month, i) ;

			}
			
			if(!"".equals(str))
			{
				dayList.add(str);
			}
			
		}
		
		// 只剩最后一个单数,说明是两个日期连在一起
		if(dayList.size()>0 && dayList.size()%2 !=0)
		{
			String str = dayList.get(dayList.size()-1);
			str = str.substring(0, 10);
			dayList.remove(dayList.size()-1);
			dayList.add(str);
			dayList.add(str);
		}
		return dayList;
		
	}
	
	/**
	 * 格式化日期为yy-MM-dd
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String formatTime(int year,int month,int day)
	{
		
		String monthStr = "";
		String dayStr = "";
		if(month<10)
		{
			monthStr = "0"+month;
		}
		else
		{
			monthStr=""+month;
		}
		
		if(day<10)
		{
			dayStr = "0"+day;
		}
		else
		{
			dayStr = ""+day;
		}
		
		return (year+"-"+monthStr +"-"+dayStr);
		
	}
	
	/**
	 *  返回上个月的时间，格式201509
	 * @return
	 */
	public static String getLastMonthStr()
	{
		String str = "";
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		// month是从0开始的，如果是0，那么就是上一年12月
		int month = now.get(Calendar.MONTH);
		if(month == 0)
		{
			year = year-1;
			month = 12;
		}
		
		str = year+""+(month<10?  ("0"+month):month);
		
		return str;
	}
	
	
    public static void main(String[] args) {
//    	System.out.println(getDays());
//    	System.out.println(getAfterDayWeek("3"));
    	try {
    		Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2015-08-21");
    		System.out.println(getAfterWorkingDays(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
