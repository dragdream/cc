package com.tianee.oa.core.ispirit.global;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class TeeIspiritSysProps {
	private static Properties props = null; 
	
	public static void setProps(Properties _props){
		props = _props;
	}
	
	public static Properties getProps(){
		return props;
	}
	
	/**
	 * 获取字符串
	 * @param name - key
	 * @return
	 */
	public static String getString(String name){
		String obj = props.getProperty(name);
		return obj;
	}
	/**
	 * 获取int 
	 * @param name - key
	 * @return
	 */
	public static int getInt(String name){
		String obj = getString(name);
		if(obj!=null){
			return Integer.parseInt(obj);
		}
		return 0;
	}
	/**
	 * 获取双精度
	 * @param name - key
	 * @return
	 */
	public static double getDouble(String name){
		String obj = getString(name);
		if(obj!=null){
			return Double.parseDouble(obj);
		}
		return 0;
	}
	/**
	 * 获取浮点型
	 * @param name - key
	 * @return
	 */
	public static double getFloat(String name){
		String obj = getString(name);
		if(obj!=null){
			return Float.parseFloat(obj);
		}
		return 0;
	}
	/**
	 * 获取字符
	 * @param name - key
	 * @return
	 */
	public static short getChar(String name){
		String obj = getString(name);
		if(obj!=null){
			return Short.parseShort(obj);
		}
		return 0;
	}
	
	/**
	 * 获取long 值
	 * @param name-key
	 * @return
	 */
	public static long getLong(String name){
		String obj = getString(name);
		if(obj!=null){
			return Long.parseLong(obj);
		}
		return 0;
	}
	/**
	 * 判断是否存在
	 * @param name - key
	 * @return
	 */
	public static boolean getBoolean(String name){
		String obj = getString(name);
		if(obj!=null){
			return Boolean.parseBoolean(obj);
		}
		return false;
	}

	
	  /**
	   * 设置系统配置属性

	   * @param props
	   */
	  public static void addProps(Map aProps) {
	    if (aProps == null) {
	      return;
	    }
	    Iterator iKeys = aProps.keySet().iterator();
	    while (iKeys.hasNext()) {
	      String key = (String)iKeys.next();
	      String value = (String)aProps.get(key);
	      props.put(key, value);
	    }
	  }
	  
	  /**
	   * 获取存放 系统默认 附件的根路径 如套红模板
	   * @author zhp
	   * @createTime 2013-10-13
	   * @editTime 下午04:51:24
	   * @desc
	   */
	  public static String getSystemDefaultAttachPath() {
		    return getRootPath()+File.separator+"WEB-INF"+File.separator+"sysAttach";
	  }
	  
	  /**
	   * 获取临时文件夹
	   * @return
	   */
	  public static String getTmpPath(){
		  return props.getProperty("rootPath")+File.separator+".."+File.separator+"attach"+File.separator+"tmp";
	  }
	  
	  public static String getRootPath(){
		  return props.getProperty("rootPath");
	  }
	  
	  /**
	   * 获取站点模板路径
	   * @return
	   */
	  public static String getSiteTemplatePath(){
		  return getRootPath()+File.separator+"cmstpls"+File.separator+"";
	  }
	  
	  /**
	   * 获取默认model数值
	   * @author zhp
	   * @createTime 2013-10-4
	   * @editTime 下午12:43:09
	   * @desc
	   */
	  public static String getDefaultAttachModelPath() {
		    return "other";
	  }
}
