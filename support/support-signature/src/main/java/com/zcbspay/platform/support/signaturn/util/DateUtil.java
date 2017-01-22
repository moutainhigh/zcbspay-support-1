/* 
 * DateUtil.java  
 * 
 * version TODO
 *
 * 2015年8月26日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zcbspay.platform.support.signaturn.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Class Description
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年8月26日 下午12:26:17
 * @since
 */
public class DateUtil {
	public final static String DEFAULT_DATE_FROMAT = "yyyyMMddHHmmss";
	public final static String SIMPLE_DATE_FROMAT = "yyyyMMdd";
	public final static String SIMPLE_TIME_FROMAT = "HHmmss";
	public final static String DEFAULT_TIME_STAMP_FROMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 给指定日期加上指定分钟 @例：【2015-08-26 12:26:27】，【60】返回【2015-08-26 13:26:27】
	 * 
	 * @param date
	 * @param min
	 * @return Date = 指定日期+指定分钟
	 */
	public static Date addMin(Date date, long min) {
		Date current = new Date();
		Date rtnDate = new Date(current.getTime() + min * 60 * 1000);
		return rtnDate;
	}

	/**
	 * 根据日期字符串返回相应的DATE类型
	 * 
	 * @param yyyymmdd
	 * @return Date
	 * @throws ParseException
	 */
	public static Date convertToDate(String strDate, String format) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.parse(strDate);
	}

	/**
	 * 获取当前日期（yyyyMMdd）
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		return formatDateTime(SIMPLE_DATE_FROMAT, new Date());
	}

	/**
	 * 获取当前时间（HHmmss）
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		return formatDateTime(SIMPLE_TIME_FROMAT, new Date());
	}

	/**
	 * 获取当前日期时间（yyyyMMddHHmmss）
	 * 
	 * @return
	 */
	public static String getCurrentDateTime() {
		return formatDateTime(DEFAULT_DATE_FROMAT, new Date());
	}

	public static String getTimeStamp() {
		return formatDateTime(DEFAULT_TIME_STAMP_FROMAT, new Date());
	}

	/**
	 * 格式化日期 default format yyyy-MM-dd HH:mm:ss formatDateTime
	 * 
	 * @param date
	 * @return String
	 * @exception @since
	 *                1.0.0
	 */
	public static String formatDateTime(java.util.Date date) {
		return formatDateTime(DEFAULT_DATE_FROMAT, date);
	}

	/**
	 * 以指定格式返回指定日期的字符串
	 * 
	 * @param pattern
	 *            - 日期显示格式
	 * @param date
	 *            - 需要格式 化的时间
	 * @return the formatted date-time string
	 * @see java.text.SimpleDateFormat
	 */
	public static String formatDateTime(String pattern, java.util.Date date) {
		String strDate = null;
		String strFormat = pattern;
		SimpleDateFormat dateFormat = null;

		if (date == null)
			return "";

		dateFormat = new SimpleDateFormat(strFormat);
		strDate = dateFormat.format(date);

		return strDate;
	}

	/**
	 * 给指定(d)的日期添加指定(skipDay)的天数
	 * 
	 * @param d
	 * @param skipDay
	 *            需要添加的天数，可以为正数或负数
	 * @return
	 */
	public static Date skipDateTime(Date d, int skipDay) {
		if (d == null)
			return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DATE, skipDay);
		return calendar.getTime();
	}

	public static String getSettleDate(int cycle) {
		return formatDateTime(SIMPLE_DATE_FROMAT, skipDateTime(new Date(), cycle));
	}

	public static String getBeforeDate(int skipday) {
		return formatDateTime(SIMPLE_DATE_FROMAT, skipDateTime(new Date(), skipday));
	}
}
