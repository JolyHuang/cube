package com.sharingif.cube.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @Description:  [日期工具箱]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年5月12日 下午3:07:08]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年5月12日 下午3:07:08]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */

public class DateUtils {
	
	public static final String DATE_COMPACT_FORMAT = "yyyyMMdd";
	public static final String DATETIME_ISO_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATETIME_MILLI_ISO_FORMAT = "yyyy-MM-dd HH:mm:ss SSS";
	public static final String DATE_ISO_FORMAT = "yyyy-MM-dd";
	public static final String DATETIME_SLASH_FORMAT = "yyyy/MM/dd HH:mm:ss";
	public static final String DATETIME_MILLI_SLASH_FORMAT = "yyyy/MM/dd HH:mm:ss SSS";
	
	public static final String DATETIME_COMPACT_FORMAT = "yyyyMMddHHmmss";
	public static final String DATETIME_MILLI_COMPACT_FORMAT = "yyyyMMddHHmmss SSS";
	public static final String DATE_SLASH_FORMAT = "yyyy/MM/dd";
	
	/**
	 * 获取指定格式的当前日期字符串
	 * @param dateFormat
	 * @return
	 */
	public static String getDate(String dateFormat){
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.format(new Date(System.currentTimeMillis()));
	}
	
	/**
	 * 日期格式化为指定格式字符串
	 * @param date ： 日期
	 * @param dateFormat : 格式
	 * @return
	 */
	public static String getDate(Date date,String dateFormat){
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.format(date);
	}

	/**
	 * 字符串日期按指定格式转换为日期类型
	 * @param date
	 * @param fromFormat
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(String date,String fromFormat) throws ParseException{
		SimpleDateFormat fromSimpleDateFormat = new SimpleDateFormat(fromFormat);
		return fromSimpleDateFormat.parse(date);
	}
	
	/**
	 * 字符串日期格式为指定格式字符串
	 * @param date
	 * @param fromFormat
	 * @param toFormat
	 * @return
	 * @throws ParseException
	 */
	public static String getDate(String date,String fromFormat,String toFormat) throws ParseException{
		Date fromDate=getDate(date, fromFormat);
		return new SimpleDateFormat(toFormat).format(fromDate);
	}

	/**
	 * 添加或减去指定的时间给定日历字段
	 * @param date : 日期
	 * @param field :
	 * @param amount :
	 * @return
	 */
	public static Date add(Date date, int field, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);

		return calendar.getTime();
	}

	/**
	 * 当前时间添加指定分钟
	 * @param amount : 分钟
	 * @return
	 */
	public static Date addCurrentDateMinute(int amount) {
		return add(new Date(), Calendar.MINUTE, amount);
	}

	/**
	 * 比较日期A是否大于日期B
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean greaterThan(Date a, Date b) {
		return a.compareTo(b)>-1;
	}

}
