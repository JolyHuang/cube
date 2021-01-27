package com.sharingif.cube.core.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.sharingif.cube.core.exception.CubeRuntimeException;

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
	public static String getCurrentDate(String dateFormat){
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
	public static Date getDate(String date,String fromFormat) {
		SimpleDateFormat fromSimpleDateFormat = new SimpleDateFormat(fromFormat);
		try {
			return fromSimpleDateFormat.parse(date);
		} catch (ParseException e) {
			throw new CubeRuntimeException("parse date exception", e);
		}
	}
	
	/**
	 * 字符串日期格式为指定格式字符串
	 * @param date
	 * @param fromFormat
	 * @param toFormat
	 * @return
	 * @throws ParseException
	 */
	public static String getDate(String date,String fromFormat,String toFormat){
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
	 * 日期添加指定分钟
	 * @param date : 日期
	 * @param amount : 分钟
	 * @return
	 */
	public static Date addDateMinute(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}

	/**
	 * 日期添加指定天
	 * @param date : 日期
	 * @param day : 天
	 * @return
	 */
	public static Date addDateDay(Date date, int day) {
		return add(date, Calendar.DATE, day);
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
	 * 计算两个日期相差天数
	 * @param startDate ： 开始日期
	 * @param endDate	： 结束日期
	 * @return
	 */
	public static int intervalDay(Date startDate, Date endDate) {
		startDate = getDate(getDate(startDate,DATE_COMPACT_FORMAT),DATE_COMPACT_FORMAT);
		endDate = getDate(getDate(endDate,DATE_COMPACT_FORMAT),DATE_COMPACT_FORMAT);
		long intervalDay = (startDate.getTime() - endDate.getTime())/(24*60*60*1000L);

		return Math.abs((int) intervalDay);
	}

	/**
	 * 获取指定日期开始、结束日期
	 * @param month
	 * @return
	 */
	public static StartEndDate getStartEndDate(Date date) {
		StartEndDate startEndDate = new StartEndDate();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);

		startEndDate.setStartDate(calendar.getTime());

		calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);

		startEndDate.setEndDate(calendar.getTime());

		return startEndDate;
	}

	public static class StartEndDate implements Serializable {

		private Date startDate;
		private Date endDate;

		public Date getStartDate() {
			return startDate;
		}

		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}

		public Date getEndDate() {
			return endDate;
		}

		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder("DateUtils{");
			sb.append("startDate=").append(startDate);
			sb.append(", endDate=").append(endDate);
			sb.append('}');
			return sb.toString();
		}

	}

}
