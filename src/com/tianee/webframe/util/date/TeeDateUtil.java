package com.tianee.webframe.util.date;


import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 日期工具类
 * 
 * 
 */
public class TeeDateUtil {
	public static final int FILTER_SUNDAY = 1;
	public static final int FILTER_MONDAY = 2;
	public static final int FILTER_TUESDAY = 3;
	public static final int FILTER_WEDNESDAY = 4;
	public static final int FILTER_THURSDAY = 5;
	public static final int FILTER_FRIDAY = 6;
	public static final int FILTER_SATURDAY = 7;

	
	 private static DecimalFormat numFormat = new DecimalFormat("#0.00");
	 
	  private static DecimalFormat numFormatG = new DecimalFormat("#,##0.00");
	  private static SimpleDateFormat dateFormat =
	      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  private static SimpleDateFormat dateFormatHHmm =
		      new SimpleDateFormat("yyyy-MM-dd HH:mm");
	  private static SimpleDateFormat dateFormatS =
	    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	  public static String DATE_FORMAT_NOSPLIT = "yyyyMMddHHmmssSSS";
	  private static SimpleDateFormat dateFormatEn =
	    new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
	  private static SimpleDateFormat dateFormatCn =
	    new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINESE);
	  private static SimpleDateFormat dateFormatYMEn =
	    new SimpleDateFormat("MMM-yyyy", Locale.ENGLISH);
	  private static SimpleDateFormat dateFormatYMCn =
	    new SimpleDateFormat("yyyy年MM月", Locale.CHINESE);
	  private static SimpleDateFormat dateFormatMDEn =
	    new SimpleDateFormat("dd-MMM", Locale.ENGLISH);
	  private static SimpleDateFormat dateFormatMDCn =
	    new SimpleDateFormat("MM-dd", Locale.CHINESE);

	/**
	 * 将Date类型转换为字符串
	 * 
	 * @param date
	 *            日期类型
	 * @return 日期字符串
	 */
	public static String format(Date date) {
		if(date == null){
			return "";
		}
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String format(Calendar date) {
		if(date == null){
			return "";
		}
		return format(date.getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将Date类型转换为字符串
	 * 
	 * @param date
	 *            日期类型
	 * @param pattern
	 *            字符串格式
	 * @return 日期字符串
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return "null";
		}
		if (pattern == null || pattern.equals("") || pattern.equals("null")) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		return new java.text.SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 将字符串转换为Date类型
	 * 
	 * @param date
	 *            字符串类型
	 * @return 日期类型
	 */
	public static Date format(String date) {
		return format(date, null);
	}

	/**
	 * 将字符串转换为Date类型
	 * 
	 * @param date
	 *            字符串类型
	 * @param pattern
	 *            格式
	 * @return 日期类型
	 */
	public static Date format(String date, String pattern) {
		if (pattern == null || pattern.equals("") || pattern.equals("null")) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		if (date == null || date.equals("") || date.equals("null")) {
			return new Date();
		}
		Date d = null;
		try {
			d = new java.text.SimpleDateFormat(pattern).parse(date);
		} catch (ParseException pe) {
		}
		return d;
	}

	public static String getCurrDate() {
		return format(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 格局传入的日期 解析出 该用何种SimpleDateFormat
	 * @param dateStr
	 * @return
	 */
	public static SimpleDateFormat  getSimpleDateFormatPattern(String dateStr) {
		 SimpleDateFormat format = null;
		    if (Pattern.matches("\\d{4}-\\d{1,2}-\\d{1,2}", dateStr)) {
		      format = new SimpleDateFormat("yyyy-MM-dd");
		      //System.out.println("用的yyyy-MM-dd");
		    }else if (Pattern.matches("\\d{4}\\d{2}\\d{2}", dateStr)) {
		      format = new SimpleDateFormat("yyyyMMdd");
		    }else if (Pattern.matches("\\d{4}年\\d{2}月\\d{2}日", dateStr)) {
		      format = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
		     // System.out.println("用的yyyy年MM月dd日");
		    }else if (Pattern.matches("\\d{4}年\\d{1,2}月\\d{1,2}日", dateStr)) {
		      format = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
		    }else if (Pattern.matches("\\d{1,2}\\w{3}\\d{4}", dateStr)) {
		      format = new SimpleDateFormat("dMMMyyyy", Locale.ENGLISH);
		    }else if (Pattern.matches("\\d{1,2}-\\w{3}-\\d{4}", dateStr)) {
		      format = new SimpleDateFormat("d-MMM-yyyy", Locale.ENGLISH);
		    }else if(Pattern.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}", dateStr)) {
		    	format = dateFormatHHmm;
		    }
		    else if (dateStr.length() > 20 ) {
		    	format = dateFormat;
		    }else {
		    	format = dateFormat;
		    }
		  return format;
	}
	
	
	/**
	 * 格局传入的日期 解析出 该用何种SimpleDateFormat
	 * @param dateStr
	 * @return
	 * @throws ParseException 
	 */
	public static Date parseDateByPattern(String dateStr){
		 SimpleDateFormat format = null;
		 try{
			 	if (Pattern.matches("\\d{4}-\\d{2}-\\d{2}", dateStr)) {
			        format = new SimpleDateFormat("yyyy-MM-dd");
			        return format.parse(dateStr);
			    }else if (Pattern.matches("\\d{4}-\\d{1,2}-\\d{1,2}", dateStr)) {
			        format = new SimpleDateFormat("yyyy-MM-dd");
			        return format.parse(dateStr);
			    }else if (Pattern.matches("\\d{4}/\\d{2}/\\d{2}", dateStr)) {
				    format = new SimpleDateFormat("yyyy/MM/dd");
				    return format.parse(dateStr);
				}else if (Pattern.matches("\\d{4}/\\d{1,2}/\\d{1,2}", dateStr)) {
				    format = new SimpleDateFormat("yyyy/MM/dd");
				    return format.parse(dateStr);
				}else if (Pattern.matches("\\d{4}\\d{2}\\d{2}", dateStr)) {
			        format = new SimpleDateFormat("yyyyMMdd");
			        return format.parse(dateStr);
			    }else if (Pattern.matches("\\d{4}年\\d{2}月\\d{2}日", dateStr)) {
			        format = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
			        return format.parse(dateStr);
			    }else if (Pattern.matches("\\d{4}年\\d{1,2}月\\d{1,2}日", dateStr)) {
			        format = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
			        return format.parse(dateStr);
			    }else if (Pattern.matches("\\d{4}/\\d{1,2}/\\d{1,2} \\d{2}:\\d{2}", dateStr)) {
			        format = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.CHINA);
			        return format.parse(dateStr);
			    }else if (Pattern.matches("\\d{4}\\.\\d{2}\\.\\d{2}", dateStr)) {
			        format = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
			        return format.parse(dateStr);
			    }else if (Pattern.matches("\\d{4}\\.\\d{1,2}\\.\\d{1,2}", dateStr)) {
			        format = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
			        return format.parse(dateStr);
			    }else if (Pattern.matches("\\d{4}年\\d{2}月", dateStr)) {
			        format = new SimpleDateFormat("yyyy年MM月", Locale.CHINA);
			        return format.parse(dateStr);
			    }else if (Pattern.matches("\\d{4}年\\d{1,2}月", dateStr)) {
			        format = new SimpleDateFormat("yyyy年MM月", Locale.CHINA);
			        return format.parse(dateStr);
			    }else if (Pattern.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", dateStr)) {
			        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
			        return format.parse(dateStr);
			    }else if (Pattern.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}", dateStr)) {
			        format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
			        return format.parse(dateStr);
			    }else if (Pattern.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}", dateStr)) {
			        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA);
			        return format.parse(dateStr);
			    }else if (Pattern.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{2}", dateStr)) {
			        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS", Locale.CHINA);
			        return format.parse(dateStr);
			    }else if (Pattern.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{1}", dateStr)) {
			        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.CHINA);
			        return format.parse(dateStr);
			    }else if (Pattern.matches("\\d{2}:\\d{2}", dateStr)) {
			        format = new SimpleDateFormat("HH:mm", Locale.CHINA);
			        return format.parse(dateStr);
			    }
		 }catch(Exception e){
			 
		 }
		  return null;
	}
	
	/**
	 * 
	 * @param start
	 * @param end
	 * @param filter
	 * @return
	 */
	public static long getMillisecondsRanges(Calendar start,Calendar end,Object filters[]){
		Calendar tmp = (Calendar) start.clone();
		long mills = 0;//叠加秒数
		int weekIndex=0;//星期几
		
		while(true){
			weekIndex = tmp.get(Calendar.DAY_OF_WEEK);//取出星期
			
			if(filters!=null){
				boolean ctn = false;
				for(Object filter:filters){
					if(filter instanceof Integer){
						if(TeeStringUtil.getInteger(filter, 0)==weekIndex){
							tmp.set(Calendar.HOUR, 0);
							tmp.set(Calendar.MINUTE, 0);
							tmp.set(Calendar.SECOND, 0);
							tmp.set(Calendar.MILLISECOND, 0);
							tmp.add(Calendar.DATE, 1);
							ctn = true;
							break;
						}
					}else if(filter instanceof Calendar){
						Calendar t = (Calendar)filter;
						int year = t.get(Calendar.YEAR);
						int month = t.get(Calendar.MONTH);
						int date = t.get(Calendar.DATE);
						int _year = tmp.get(Calendar.YEAR);
						int _month = tmp.get(Calendar.MONTH);
						int _date = tmp.get(Calendar.DATE);
						if(year==_year && month==_month && date==_date){
							tmp.set(Calendar.HOUR, 0);
							tmp.set(Calendar.MINUTE, 0);
							tmp.set(Calendar.SECOND, 0);
							tmp.set(Calendar.MILLISECOND, 0);
							tmp.add(Calendar.DATE, 1);
							ctn = true;
							break;
						}
					}
				}
				if(ctn){
					continue;
				}
			}
			
			//如果当前时间节点还在结束节点之前，并且不在忽略日期范围内的话，则将转到第二天
			if(tmp.before(end)){
				Calendar c = (Calendar) tmp.clone();
				c.set(Calendar.HOUR, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				c.set(Calendar.MILLISECOND, 0);
				c.add(Calendar.DATE, 1);
				
				if(c.before(end)){//如果当前节点继续在结束节点范围之前，则叠加秒数
					mills+=c.getTime().getTime()-tmp.getTime().getTime();
					tmp = (Calendar) c.clone();
				}else{
					mills+=(end.getTime().getTime()-tmp.getTime().getTime());
					break;
				}
			}
		}
		return mills;
	}
	
	/**
	 * 获取逝去的时间描述
	 * @param start
	 * @param end
	 * @return
	 */
	public static String getPassedTimeDesc(Calendar start,Calendar end){
		if(start==null){
			return "";
		}
		if(end==null){
			end = Calendar.getInstance();
		}
		long milliSeconds = end.getTimeInMillis()-start.getTimeInMillis();
		return getTimeMilisecondDesc(milliSeconds);
	}
	
	/**
	 * 获取时间日期
	 * @param milliSeconds
	 * @return
	 */
	public static String getTimeMilisecondDesc(long milliSeconds){
		long days = milliSeconds/(1000*60*60*24);
		milliSeconds = milliSeconds-(days*24*60*60*1000);
		long hours = milliSeconds/(1000*60*60);
		milliSeconds = milliSeconds-(hours*60*60*1000);
		long minutes = milliSeconds/(1000*60);
		milliSeconds = milliSeconds-(minutes*60*1000);
		long seconds = milliSeconds/(1000);
		
		StringBuffer sb = new StringBuffer();
		if(days!=0){
			sb.append(days+"天");
		}
		if(hours!=0){
			sb.append(hours+"小时");
		}
		if(minutes!=0){
			sb.append(minutes+"分钟");
		}
		if(seconds!=0){
			sb.append(seconds+"秒");
		}
		return sb.toString();
	}
	
	/**
	   * 取得当前日期所在周的第一天

	   * @param date
	   * @return
	   */
	  public static Date getFirstDayOfWeek(Date date) { 
	    Calendar c = clearDate(date, 4);
	    c.setFirstDayOfWeek(Calendar.MONDAY);
	    c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
	    return c.getTime ();
	  }
	  /**
	   * 取得当前日期所在周的最后一天

	   * @param date
	   * @return
	   */
	  public static Date getLastDayOfWeek(Date date) {
	    Calendar c = clearDate(date, 4);
	    c.setFirstDayOfWeek(Calendar.MONDAY);
	    c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
	    c.set(Calendar.HOUR_OF_DAY, 23);
	    c.set(Calendar.MINUTE, 59);
	    c.set(Calendar.SECOND, 59);
	    c.set(Calendar.MILLISECOND, 000);
	    return c.getTime();
	  }
	  
	  /**
	   * 取得指定日期的当周的起始时间
	   * @param date
	   * @return
	   */
	  public static Date[] getWeekLimit(Date date) throws Exception {
	    Date date1 = getFirstDayOfWeek(date);
	    Date date2 = getLastDayOfWeek(date);
	    return new Date[]{date1, date2};
	  }
	  
	  /**
	   * 取得指定日期的当月起始时间

	   * @param date
	   * @return
	   */
	  public static Date[] getMonthLimit(Date date) throws Exception {
	    Calendar cal = clearDate(date, 5);
	    cal.set(Calendar.DATE, 1);
	    Date date1 = cal.getTime();
	    
	    cal.add(Calendar.MONTH, 1);
	    cal.add(Calendar.SECOND, -1);
	    Date date2 = cal.getTime();
	    
	    return new Date[]{date1, date2};
	  }

	  /**
	   * 取得指定日期的当年起始时间

	   * @param date
	   * @return
	   */
	  public static Date[] getYearLimit(Date date) throws Exception {
	    Calendar cal = clearDate(date, 6);    
	    cal.set(Calendar.MONTH, 0);
	    cal.set(Calendar.DATE, 1);
	    Date date1 = cal.getTime();
	    
	    cal.add(Calendar.YEAR, 1);
	    cal.add(Calendar.SECOND, -1);
	    Date date2 = cal.getTime();
	    
	    return new Date[]{date1, date2};
	  }
	  /**
	   * 取得指定日期当月的起始时间串
	   * @param date
	   * @return
	   */
	  public static String[] getMonthLimitStr(Date date) throws Exception {
	    Date[] rtDateArray = getMonthLimit(date);
	    return new String[]{getDateTimeStr(rtDateArray[0]), getDateTimeStr(rtDateArray[1])};
	  }
	  /**
	   * 取得指定日期当年的起始时间串
	   * @param date
	   * @return
	   */
	  public static String[] getYearLimitStr(Date date) throws Exception {
	    Date[] rtDateArray = getYearLimit(date);
	    return new String[]{getDateTimeStr(rtDateArray[0]), getDateTimeStr(rtDateArray[1])};
	  }
	  /**
	   * 取得天数间隔
	   * @param fromDate
	   * @param toDate
	   * @return
	   */
	  public static int getDaySpan(String toDateStr) throws Exception {
	    return (int)((parseDate(toDateStr).getTime() - new Date().getTime()) / TeeConst.TEE_D);
	  }
	  
	  /**
	   * 取得天数间隔
	   * @param fromDate
	   * @param toDate
	   * @return
	   */
	  public static int getDaySpan(Date toDate) {
	    return (int)((toDate.getTime() - new Date().getTime()) / TeeConst.TEE_D);
	  }
	  
	  /**
	   * 取得天数间隔
	   * @param fromDate
	   * @param toDate
	   * @return
	   */
	  public static int getDaySpan(Date fromDate, Date toDate) {
	    return (int)((toDate.getTime() - fromDate.getTime()) / TeeConst.TEE_D);
	  }
	  
	  /**
	   * 取得天数间隔 毫秒
	   * @param fromDate
	   * @param toDate
	   * @return
	   */
	  public static int getDaySpanTimeInMillis(long fromDate, long toDate) {
		  return (int)((fromDate - toDate) / TeeConst.TEE_D);
	  }
	  
	  /**
	   * 取得前一天的时间
	   * @param dateStr
	   * @return
	   */
	  public static Date getDayBefore(String dateStr, int dayCnt) throws Exception {
	    return getDayBefore(parseDate(dateStr), dayCnt);
	  }
	  
	  /**
	   * 取得前一天的时间
	   * @param date
	   * @return
	   */
	  public static Date getDayBefore(Date date, int dayCnt) {
	    GregorianCalendar cal = new GregorianCalendar();
	    cal.setTime(date);
	    cal.add(Calendar.DATE, 0 - dayCnt);
	    return cal.getTime();
	  }
	  
	  /**
	   * 取得后一天的时间字

	   * @param dateStr
	   * @return
	   */
	  public static Date getDayAfter(String dateStr, int dayCnt) throws Exception {
	    return getDayAfter(parseDate(dateStr), dayCnt);
	  }
	  
	  /**
	   * 取得后一天的时间
	   * @param date
	   * @return
	   */
	  public static Date getDayAfter(Date date, int dayCnt) {
	    GregorianCalendar cal = new GregorianCalendar();
	    cal.setTime(date);
	    cal.add(Calendar.DATE, dayCnt);
	    return cal.getTime();
	  }
	  
	  
	  /**
	   * 取得后N天的时间的所有日期list
	   * @param date
	   * @return
	   */
	  public static List<Date> getDayListAfter(Date date, int dayCnt) {
		 List<Date> list = new ArrayList<Date>();
	    GregorianCalendar cal = new GregorianCalendar();
	    for (int i = 1; i <=dayCnt; i++) {
	    	cal.setTime(date);
	 	    cal.add(Calendar.DATE, i);
	 	    list.add(cal.getTime());
		}
	    return list;
	  }

	  /**
	   * 取得指定天数差的时间字

	   * @param dateStr
	   * @return
	   */
	  public static Date getDayDiff(String dateStr, int dayCnt) throws Exception {
	    return getDayDiff(parseDate(dateStr), dayCnt);
	  }
	  
	  /**
	   * 取得指定天数差的时间
	   * @param date
	   * @return
	   */
	  public static Date getDayDiff(Date date, int dayCnt) {
	    GregorianCalendar cal = new GregorianCalendar();
	    cal.setTime(date);
	    cal.add(Calendar.DATE, dayCnt);
	    return cal.getTime();
	  }
	  
	  /**
	   * 取得前一天的时间字

	   * @param dateStr
	   * @return
	   */
	  public static Date getYestday(String dateStr) throws Exception {
	    return getYestday(parseDate(dateStr));
	  }
	  
	  /**
	   * 取得前一天的时间
	   * @param date
	   * @return
	   */
	  public static Date getYestday(Date date) {
	    GregorianCalendar cal = new GregorianCalendar();
	    cal.setTime(date);
	    cal.add(Calendar.DATE, -1);
	    return cal.getTime();
	  }
	  
	  /**
	   * 取得前一天的时间字符串

	   * @param dateStr
	   * @return
	   */
	  public static String getYestdayStr(String dateStr) throws Exception {
	    return getYestdayStr(parseDate(dateStr));
	  }
	  
	  /**
	   * 取得前一天的时间字符串

	   * @param date
	   * @return
	   */
	  public static String getYestdayStr(Date date) {
	    GregorianCalendar cal = new GregorianCalendar();
	    cal.setTime(date);
	    cal.add(Calendar.DATE, -1);
	    return getDateTimeStr(cal.getTime());
	  }
	  
	  
	  /**
	   * Date清零
	   * @param date 
	   * @param clearNum 1=毫秒, 2=秒, 3=分钟, 4=小时, 5=天, 6=月份
	   * @return
	   */
	  private static Calendar clearDate(Date date, int clearNum) {
	    Calendar cal = new GregorianCalendar();
	    cal.setTime(date);
	    //毫秒
	    if (clearNum > 0) {
	      cal.set(Calendar.MILLISECOND, 0);
	    }
	    //秒

	    if (clearNum > 1) {
	      cal.set(Calendar.SECOND, 0);
	    }
	    //分钟
	    if (clearNum > 2) {
	      cal.set(Calendar.MINUTE, 0);
	    }
	    //小时
	    if (clearNum > 3) {
	      cal.set(Calendar.HOUR_OF_DAY, 0);
	    }
	    //天

	    if (clearNum > 4) {
	      cal.set(Calendar.DATE, 0);
	    }
	    //月份
	    if (clearNum > 5) {
	      cal.set(Calendar.MONTH, 0);
	    }
	    return cal;
	  }
	  
	  /**
	   * 把字符串转化为Date
	   * @param dateStr
	   * @return
	   */
	  public static Date parseDate(String formatStr, String dateStr) throws ParseException {
	    SimpleDateFormat format = new SimpleDateFormat(formatStr);
	    return format.parse(dateStr);
	  }
	  
	  public static Calendar parseCalendar(String formatStr, String dateStr){
		    Calendar c = Calendar.getInstance();
		    try {
				c.setTime(parseDate(formatStr,dateStr));
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				return null;
			}
		    return c;
	  }
	  
	  public static Calendar parseCalendar(String dateStr){
		    Calendar c = Calendar.getInstance();
		    try {
				c.setTime(parseDateByPattern(dateStr));
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				return null;
			}
		    return c;
	  }
	  
	  /**
	   * 把字符串转化为Date
	   * @param dateStr
	   * @return
	   */
	  public static Date parseDate(String dateStr) {
	    if (dateStr == null || "".equals(dateStr)) {
	      return null;
	    }
	    
//	    SimpleDateFormat format = null;
//	    if (Pattern.matches("\\d{4}-\\d{1,2}-\\d{1,2}", dateStr)) {
//	      format = new SimpleDateFormat("yyyy-MM-dd");
//	    }else if (Pattern.matches("\\d{4}\\d{2}\\d{2}", dateStr)) {
//	      format = new SimpleDateFormat("yyyyMMdd");
//	    }else if (Pattern.matches("\\d{4}年\\d{2}月\\d{2}日", dateStr)) {
//	      format = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
//	    }else if (Pattern.matches("\\d{4}年\\d{1,2}月\\d{1,2}日", dateStr)) {
//	      format = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
//	    }else if (Pattern.matches("\\d{1,2}\\w{3}\\d{4}", dateStr)) {
//	      format = new SimpleDateFormat("dMMMyyyy", Locale.CHINA);
//	    }else if (Pattern.matches("\\d{1,2}-\\w{3}-\\d{4}", dateStr)) {
//	      format = new SimpleDateFormat("d-MMM-yyyy", Locale.CHINA);
//	    }else if (dateStr.length() > 20 ) {
//	      format = dateFormatS;
//	    }else {
//	      format = dateFormat;
//	    }
//	    
//	    try {
//			return format.parse(dateStr);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	    return parseDateByPattern(dateStr);
	  }
	  
	  
	  /**
	   * 取得的时间串，格式为 yyyy-MM-dd HH:mm:ss 
	   * @return
	   */
	  public static String getDateTimeStr(Date date) {
	    if (date == null) {
	      return getCurDateTimeStr();
	    }
	    return dateFormat.format(date);
	  }
	  

	  /**
	   * 取得当前的时间，格式为 yyyy-MM-dd HH:mm:ss 
	   * @return
	   */
	  public static String getCurDateTimeStr() {
	    return dateFormat.format(new Date());
	  }
	  
	  
	  /**
	   * @return计算一年的最大周
	   * @author syl
	   * @date 2014-1-11
	   * @param year
	   * @return
	   */
	  public static int getMaxWeekOfYear(int year) {
	      Calendar c = new GregorianCalendar();
	      c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
	      return getWeekOfYear(c.getTime());
	  }
	  /**
	   * 获取日期当前周
	   * @author syl
	   * @date 2014-1-11
	   * @param date
	   * @return
	   */
	  public static int getWeekOfYear(Date date) {
	      Calendar c = new GregorianCalendar();
	      c.setFirstDayOfWeek(Calendar.MONDAY);
	      c.setMinimalDaysInFirstWeek(7);
	      c.setTime(date);
	      return c.get(Calendar.WEEK_OF_YEAR);
	  }
	  
	  /**
	   *  参数说明  返回一个Calendar数组，长度7
	   * 获取一周的所有日期  7议案
	   * @author syl
	   * @date 2014-1-11
	   * @param year 年分 例如 2014 i
	   * @param weeknum 第几周 例如33 
	   * @return
	   */
	  public static Calendar[] getStartToEndDate(int year, int weeknum) {

	      Calendar cal = Calendar.getInstance();
	      cal.set(Calendar.YEAR, year);
	      cal.set(Calendar.WEEK_OF_YEAR, weeknum);
	      int nw = cal.get(Calendar.DAY_OF_WEEK);
	      Calendar start = (Calendar) cal.clone();
	      Calendar date2 = (Calendar) cal.clone();
	      Calendar date3 = (Calendar) cal.clone();
	      Calendar date4 = (Calendar) cal.clone();
	      Calendar date5 = (Calendar) cal.clone();
	      Calendar date6 = (Calendar) cal.clone();
	      Calendar end = (Calendar) cal.clone();
	      
	      start.add(Calendar.DATE, 1 - nw + 1);
	      TeeUtility.getMinTimeOfDayCalendar(start);
	      date2.add(Calendar.DATE, 1 - nw + 2);
	      TeeUtility.getMinTimeOfDayCalendar(date2);
	      date3.add(Calendar.DATE, 1 - nw + 3);
	      TeeUtility.getMinTimeOfDayCalendar(date3);
	      date4.add(Calendar.DATE, 1 - nw + 4);
	      TeeUtility.getMinTimeOfDayCalendar(date4);
	      date5.add(Calendar.DATE, 1 - nw + 5);
	      TeeUtility.getMinTimeOfDayCalendar(date5);
	      date6.add(Calendar.DATE, 1 - nw + 6);
	      TeeUtility.getMinTimeOfDayCalendar(date6);
	      end.add(Calendar.DATE, 7 - nw + 1);
	      TeeUtility.getMinTimeOfDayCalendar(end);
	      Calendar[] darr = { start, date2 ,date3,date4,date5,date6 ,end };
	      return darr;
	  }
	  
	  /**
	   *  参数说明  返回一个Calendar数组，长度为2
	   * 分别是开始日期和结束日期
	   * @author syl
	   * @date 2014-1-11
	   * @param year 年分 例如 2014 i
	   * @param weeknum 第几周 例如33 
	   * @return
	   */
	  public static Calendar[] getStartAndEndDate(int year, int weeknum) {

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
	   * 获取当期日期的一周 开始可结束日期 返回一个Calendar数组，长度为2  分别是开始日期和结束日期
	   * @author syl
	   * @date 2014-1-11
	   * @return
	   */
	  public static Calendar[] getCurrStartEndDate() {
	      Calendar cal = Calendar.getInstance();
	      // 向后推一天（从星期一到周末）
	      cal.add(Calendar.DATE, -1);
	      int nw = cal.get(Calendar.DAY_OF_WEEK);
	      Calendar start = (Calendar) cal.clone();
	      Calendar end = (Calendar) cal.clone();
	      start.add(Calendar.DATE, 1 - nw + 1);
	      end.add(Calendar.DATE, 7 - nw + 1);
	      Calendar[] darr = { start, end };
	      return darr;
	  }
	  
	  /**
	   * 获取当期日期的一周 开始至结束日期 返回一个Calendar数组，长度为7  
	   * @author syl
	   * @date 2014-1-11
	   * @return
	   */
	  public static Calendar[] getCurrStartAndEndOfWeek() {
	      Calendar cal = Calendar.getInstance();
	      // 向后推一天（从星期一到周末）
	      cal.add(Calendar.DATE, -1);
	      int nw = cal.get(Calendar.DAY_OF_WEEK);
	      Calendar start = (Calendar) cal.clone();
	      start.add(Calendar.DATE, 1 - nw + 1);
	      Calendar tuesday = (Calendar) cal.clone();//周二
	      tuesday.add(Calendar.DATE, 2 - nw + 1);
	      Calendar wednesday = (Calendar) cal.clone();//周三
	      wednesday.add(Calendar.DATE, 3 - nw + 1);
	      Calendar thursday = (Calendar) cal.clone();//周四
	      thursday.add(Calendar.DATE, 4- nw + 1);
	      Calendar friday = (Calendar) cal.clone();//周五
	      friday.add(Calendar.DATE, 5 - nw + 1);
	      Calendar saturday = (Calendar) cal.clone();//周六
	      saturday.add(Calendar.DATE, 6 - nw + 1);
	      Calendar end = (Calendar) cal.clone();
	      end.add(Calendar.DATE, 7 - nw + 1);
	      Calendar[] darr = { start, tuesday, wednesday , thursday , friday , saturday , end };
	      return darr;
	  }
	  
	  
	  /**
	   * 获取当期日期的一周 开始至结束日期 返回一个Calendar数组，长度为7  
	   * @author syl
	   * @date 2014-1-11
	   * @return
	   */
	  public static Calendar[] getStartAndEndOfWeekByDate(Date date) {
	      Calendar cal = Calendar.getInstance();
	      cal.setTime(date);
	      // 向后推一天（从星期一到周末）
	      cal.add(Calendar.DATE, -1);
	      int nw = cal.get(Calendar.DAY_OF_WEEK);
	      Calendar start = (Calendar) cal.clone();
	      start.add(Calendar.DATE, 1 - nw + 1);
	      Calendar tuesday = (Calendar) cal.clone();//周二
	      tuesday.add(Calendar.DATE, 2 - nw + 1);
	      Calendar wednesday = (Calendar) cal.clone();//周三
	      wednesday.add(Calendar.DATE, 3 - nw + 1);
	      Calendar thursday = (Calendar) cal.clone();//周四
	      thursday.add(Calendar.DATE, 4- nw + 1);
	      Calendar friday = (Calendar) cal.clone();//周五
	      friday.add(Calendar.DATE, 5 - nw + 1);
	      Calendar saturday = (Calendar) cal.clone();//周六
	      saturday.add(Calendar.DATE, 6 - nw + 1);
	      Calendar end = (Calendar) cal.clone();
	      end.add(Calendar.DATE, 7 - nw + 1);
	      Calendar[] darr = { start, tuesday, wednesday , thursday , friday , saturday , end };
	      return darr;
	  }
	  
		/**
		 *  取两个日程的之间所有天 ---数组
		 * @author syl
		 * @date 2014-1-21
		 * @param fromDate
		 * @param toDate
		 * @return
		 */
		public static Calendar[] getDaySpanCalendar(Date fromDate, Date toDate) {
			int dayDay = TeeUtility.getDaySpan(fromDate, toDate);
			Calendar[] calDate = new Calendar[dayDay];
			Calendar cal = Calendar.getInstance();
			cal.setTime(fromDate);
			TeeUtility.getMinTimeOfDayCalendar(cal);
			calDate[0] =  cal;
			for (int i = 1; i < dayDay; i++) {
				Calendar calTemp = Calendar.getInstance();
				calTemp.setTime(fromDate);
				calTemp.add(Calendar.DATE, + i);
				calDate[i] = calTemp;
			}
			return calDate;
		}
		
		
		/**
		 * 获取当前日期是周几    返回 1,2,3,4,5,6,7
		 * @param dt
		 * @return
		 */
	    public static String getWeekOfDate(String dateStr) {
	    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    	Date dt=null;
			try {
				dt = sdf.parse(dateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(dt);
	        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
	        if (w < 0)
	            w = 0;
	        return weekDays[w];
	    }
		
		
		public static void main(String[] args) {
			//System.out.println(parseDateByPattern("2014.2.2"));
			System.out.println(getWeekOfDate("2017-12-28"));
		}
		
		
		
}
