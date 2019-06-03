package com.tianee.oa.core.base.weekActive.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 周活动安排日期工具类
 * 
 * @author wyw
 * 
 */
public class TeeCalendarUtil {
	public static Calendar[] getStartEnd(int year, int weeknum) {
		/*
		 * 参数说明 int year 年分 例如 2005 int weeknum 第几周 例如33 返回一个Calendar数组，长度为2
		 * 分别是开始日期和结束日期
		 */
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.WEEK_OF_YEAR, weeknum);
		int nw = cal.get(Calendar.DAY_OF_WEEK);
		Calendar start = (Calendar) cal.clone();
		Calendar end = (Calendar) cal.clone();
		start.add(Calendar.DATE, 1 - nw + 1);
		end.add(Calendar.DATE, 7 - nw + 1);
		Calendar[] darr = { start, end };
		return darr;
	}
	
	/**
	 * 计算一年的最大周
	 * @function: 
	 * @author: wyw
	 * @data: 2015年6月28日
	 * @param year
	 * @return int
	 */
	public static int getMaxWeekOfYear(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		return cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
	}
	
	
	public static String getFullTimeStr(Calendar d) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(d.getTime());
	}
}
