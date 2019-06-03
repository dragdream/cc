package com.tianee.webframe.util.str;

/**
 * Unicode转换工具
 * @author kakalion
 *
 */
public class TeeUnicodeUtil {
	/**
	 * 字符串转换unicode
	 */
	public static String string2Unicode(String string) {
		if(string==null || "".equals(string)){
	    	return "";
	    }
	    StringBuffer unicode = new StringBuffer();
	    String tmp = "";
	    for (int i = 0; i < string.length(); i++) {
	 
	        // 取出每一个字符
	        char c = string.charAt(i);
	        // 转换为unicode
	        tmp = ("000"+Integer.toHexString(c));
	        unicode.append("\\u" + tmp.substring(tmp.length()-4, tmp.length()));
	        
	    }
	 
	    return unicode.toString();
	}
	
	/**
	 * unicode 转字符串
	 */
	public static String unicode2String(String unicode) {
		if(unicode==null || "".equals(unicode)){
	    	return "";
	    }
	    StringBuffer string = new StringBuffer();
	    
	    String[] hex = unicode.split("\\\\u");
	 
	    for (int i = 1; i < hex.length; i++) {
	 
	        // 转换出每一个代码点
	        int data = Integer.parseInt(hex[i], 16);
	        // 追加成string
	        string.append((char) data);
	    }
	 
	    return string.toString();
	}
}
