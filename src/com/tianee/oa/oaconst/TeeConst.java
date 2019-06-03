package com.tianee.oa.oaconst;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 系统通用常量定义
 * 
 * @author zhp
 * 
 */
public class TeeConst {

	/**
	 * 错误返回
	 */
	public static final boolean RETURN_ERROR = false;
	/**
	 * 正确返回
	 */
	public static final boolean RETURN_OK = true;

	/**
	 * 系统登陆用户session里的关键字
	 */
	public static final String SMS_FLAG = "SMS_FLAG";
	
	public static final String LOGIN_USER = "LOGIN_USER";
	
	public static final String VALIDATE_CODE = "VALIDATE_CODE";
	
	public static final String SESSIONTOKEN = "sessionToken";

	public static final String RETURNSTRING = "/inc/rtjson.jsp";

	/**
	 * 秒
	 */
	public static final long TEE_S = 1000;
	/**
	 * 分
	 */
	public static final long TEE_MINIT = TEE_S * 60;
	/**
	 * 时
	 */
	public static final long TEE_H = TEE_MINIT * 60;
	/**
	 * 天
	 */
	public static final long TEE_D = TEE_H * 24;
	/**
	 * 月
	 */
	public static final long TEE_MONTH = TEE_D * 30;
	/**
	 * 年
	 */
	public static final long TEE_Y = TEE_D * 365;
	/**
	 * 1k
	 */
	public static final int K = 1024;
	/**
	 * 1M
	 */
	public static final int M = K * K;
	/**
	 * 1G
	 */
	public static final int G = M * M;
	/**
	 * 1T
	 */
	public static final int T = G * G;
	/**
	 * 64K
	 */
	public static final int K64 = K * 64;
	/**
	 * 最大一个2字节无符号数字
	 */
	public static final int MAX_2_BYTES = K64 - 1;

	/**
	 * 中文编码名称
	 */
	public static final String CHINA_CODE_NAME = "GBK";
	/**
	 * 缺省编码
	 */
	public static final String DEFAULT_CODE = "UTF-8";
	public static final String FORM_DATA_TABLE_PREFIX = "tee_f_r_d_";
	public static final String FORM_DATA_FIELD_PREFIX = "DATA_";
	public static final String FORM_DATA_FIELD_EXTRA_PREFIX = "EXTRA_";
	public static final String SEAL_PREFIX = "SEAL_";//签章
	public static final String HW_PREFIX = "HW_";//手写
	
	  /**
	   * Request
	   */
	  //public static final String CURR_REQUEST = "currRequest";
	  public static final String CURR_REQUEST_FLAG = "currRequest";
	  public static final String CURR_REQUEST_ADDRESS = "currRequestAddress";
	  
	  //默认界面主题
	  public static final String PERSON_DEFAULT_THEME = "classic";
	 //经典界面 --第一套风格和第三套  默认桌面模块  ---旧的 暂被停用 
	  public static final String PERSON_DEFAULT_DESKTOP = "["
	  		+ "[{'id':'4','rows':'15'},{'id':'13','rows':'15'},{'id':'1','rows':'15'},"
	  		+ "{'id':'7','rows':'15'},{'id':'9','rows':'15'},{'id':'11','rows':'15'}]"
			+ ",[{'id':'2','rows':'15'},{'id':'3','rows':'15'},{'id':'10','rows':'15'},{'id':'12','rows':'15'},"
			+ "{'id':'5','rows':'15'},{'id':'8','rows':'15'},{'id':'6','rows':'15'}]]";
	  //经典界面 --第一套风格(经典主题)、第二套（经典简约）、第四套（现代门户）  默认桌面门户
	  public static final String PERSON_DEFAULT_DESKTOP_NEW = "1";
	  
	  //现代风格 ---第三套 风格（现代风格） 默认 桌面菜单功能 
	  public static final String PERSON_DEFAULT_MENU_PARAM_SET = "[{data:\"25,29,43,33,36,23,52,27\"}]";
	  
	  public static final String EMPTY_PWD = "$1$rPpefogg$d41d8cd98f00b204e9800998ecf8427eIuO6";
	  
	  /**
	   * 默认quartz组名称
	   */
	  public static final String QUARTZ_DEFAULT_GROUP = "QUARTZ_DEFAULT_GROUP";
}
