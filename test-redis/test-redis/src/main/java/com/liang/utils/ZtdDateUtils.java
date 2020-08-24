package com.liang.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ZtdDateUtils {

	public final static String DATE_SIMPLE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public final static String DATE_PATTERN_STR = "yyyyMMddHHmmsss";
	
	public final static String DATE_PATTERN_DATE = "yyyy-MM-dd";
	
	public final static String DATE_PATTERN_CN = "yyyy年MM月dd日 HH时mm分ss秒";
	
	public final static String WEEK_PATTERN = "EEEE";
	
	public final static String SUN_OF_WEEK = "星期日";
	
	public final static String MON_OF_WEEK = "星期一";
	
	public final static String TUE_OF_WEEK = "星期二";
	
	public final static String WED_OF_WEEK = "星期三";
	
	public final static String THU_OF_WEEK = "星期四";
	
	public final static String FRI_OF_WEEK = "星期五";
	
	public final static String SAT_OF_WEEK = "星期六";
	
	/**
	 * 获取执行格式的时间字符串
	 * 
	 * @param type
	 *            字符串类型
	 * @return 相应时间字符串
	 */
	public static String getDateStrByPattern(String type) {
		SimpleDateFormat df = new SimpleDateFormat(type);
		return df.format(new Date());
	}

	/**
	 * 获取单签时间对象
	 * 
	 * @return 时间对象
	 */
	public static Date getDate() {
		return new Date();
	}
	
	/**
	 * 获取当前时间字符串
	 * @return 时间字符串
	 */
	public static String getDateStr() {
		return getDateStr(getDate(), DATE_SIMPLE_PATTERN);
	};
	
	/**
	 * 获取当前日期字符串
	 * @return 日期字符串
	 */
	public static String getDayStr() {
		return getDateStr(getDate(), DATE_PATTERN_DATE);
	};
	
	/**
	 * 获取时间字符串
	 * 
	 * @param date
	 *            时间
	 * @return 时间字符串
	 */
	public static String getDateStr(Date date) {
		return getDateStr(date, DATE_SIMPLE_PATTERN);
	};
	
	/**
	 * 获取中文时间字符串
	 * 
	 * @param date
	 *            时间
	 * @return 时间字符串
	 */
	public static String getDateCNStr(Date date) {
		return getDateStr(date, DATE_PATTERN_CN);
	};

	/**
	 * 获取指定时间格式的时间字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getDateStr(Date date, String pattern) {
		if(date != null)
		{
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return "";
	}
	/**
	 * 获取指定时间格式的时间字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getDateStr(String pattern) {
		return getDateStr(getDate(), pattern);
	}
	
	/**
	 * 根据日期获取日期的星期数
	 * @param date 日期
	 * @return 对应的星期数
	 */
	public static String getDayOfWeek(Date date)
	{
		if(date == null)
		{
			return "星期日";
		}
		SimpleDateFormat dateFm = new SimpleDateFormat(WEEK_PATTERN);
		return dateFm.format(date);
	}
	/**
	 * 根据当前日期获取日期的星期数
	 * @param date 日期
	 * @return 对应的星期数
	 */
	public static String getCurrentDayOfWeek()
	{
		return getDayOfWeek(getDate());
	}
	
	/**
	  * 获取制定日期前几天的日期
	  * @param date 时间
	  * @param day 天数
	  * @return 指定天数之前的日期
	  */	 
	public static Date getDateBefore(Date date, int days) {
	  Calendar now = Calendar.getInstance();
	  now.setTime(date);
	  now.set(Calendar.DATE, now.get(Calendar.DATE) - days);
	  return now.getTime();
	 }
	
	/**
	  * 获取指定分钟数的日期
	  * @param date 时间
	  * @param day 分钟数， 负数表示之前，整数表示之后
	  * @return 指定分钟数据的日期
	  */	 
	public static Date getDateByMinute(Date date, int minutes) {
	  Calendar now = Calendar.getInstance();
	  now.setTime(date);
	  now.set(Calendar.MINUTE,minutes);
	  return now.getTime();
	 }

	 /**
	  * 获取制定日期后几天的日期
	  * @param date 时间
	  * @param day 天数
	  * @return 指定天数后的日期
	  */
	 public static Date getDateAfter(Date date, int days) {
	  Calendar now = Calendar.getInstance();
	  now.setTime(date);
	  now.set(Calendar.DATE, now.get(Calendar.DATE) + days);
	  return now.getTime();
	 }
	 /**
	  * 获取当前时间的前一天
	  * @return
	  */
	 public static Date getPreDay()
	 {
		 return getDateBefore(getDate(), 1);
	 }
	 /**
	  * 获取当前时间后一天
	  * @return
	  */
	 public static Date getNextDay()
	 {
		 return getDateAfter(getDate(), 1);
	 }
	
	/**
	 * 获取两个日志类型对象间隔的天数
	 * @param fDate 开始日期
	 * @param tDate 结束日期
	 * @return 间隔天数
	 */
	public static int getDays(Date fDate, Date tDate) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(fDate);
		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
		aCalendar.setTime(tDate);
		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
		return day2 - day1;
	}
	/**
	 * 获取日期字符串的日期对象
	 * @param dateStr 日期字符串 eg 2017-03-30
	 * @return 日期对象
	 * @throws ParseException
	 */
	public static Date getDateFromStr(String dateStr) throws ParseException
	{
		return getDateFromStr(dateStr,DATE_SIMPLE_PATTERN);
	}
	/**
	 * 获取指定日期格式日期对象
	 * @param dateStr 日期字符串 eg 2017-03-30
	 * @return 日期对象
	 * @throws ParseException
	 */
	public static Date getDateFromStr(String dateStr,String pattern) throws ParseException
	{
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.parse(dateStr);
	}
	public static void main(String[] args) throws Exception {
		String s = "2017-03-01";
		String e = "2017-03-26";
		SimpleDateFormat df = new SimpleDateFormat("yyy-MM-dd");
		Date d1 = df.parse(s);
		Date d2 = df.parse(e);
		int days = getDays(d1, d2);
		Date p = null;
		for(int i = 0; i <= days; i++)
		{
			p = getDateAfter(d1, i);
			System.out.println(df.format(p));
		}
		/*long begin = System.currentTimeMillis();
		try {
			//休眠两秒再查询
			Thread.currentThread().sleep(60 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println((System.currentTimeMillis() - begin)/ (1000 * 60));*/
		System.out.println(getDayOfWeek(getDate()));
		System.out.println(getDayOfWeek(null));
		System.out.println(getDateAfter(getDate(), 1));
		System.out.println(getPreDay());
		System.out.println(getNextDay());
	}
}