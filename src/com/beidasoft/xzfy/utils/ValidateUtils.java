package com.beidasoft.xzfy.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.beidasoft.xzfy.base.exception.ValidateException;

/**
 * 公共校验方法
 * @author fyj
 *
 */
public class ValidateUtils 
{

	/**
	 * 校验参数是否为空
	 * @param str
	 */
	public static void validateEmpty(String str){
		
		if(str == null || "".equals(str)){
			throw new ValidateException(ConstCode.VALIDATE_FAIL,"参数为空");
		}
	}
	
	/**
	 * 校验对象是否为空
	 * @param str
	 */
	public static boolean validateEmpty(Object str){
		
		if(str == null || "".equals(str)){
			throw new ValidateException(ConstCode.VALIDATE_FAIL,"参数为空");
		} else {
			return false;
		}
	}
	
	/**
	 * 特殊字符校验
	 * @param str
	 */
	 public static void validateSpecialChar(String str) {

		 //正则表达式
		 String regEx = "[`~!@#$%^&*()+=|{}':;'\\[\\]<>/?~！@#￥%……&*——+|{}【】‘；：”“’。，、？]";
		 Pattern p = Pattern.compile(regEx);
		 Matcher m = p.matcher(str);
		 boolean b = m.find();
		 //存在抛出异常
		 if(b){
			 throw new ValidateException(ConstCode.VALIDATE_FAIL,"参数包含特殊字符");
		 }
	 }

	 /**
	  * 校验字符串长度
	  * @param str 字符串
	  * @param min 最小长度
	  * @param max 最大长度
	  */
	 public static void validateLength(String str,int min,int max){
		 
		 int len = str.length();
		 if( len < min || len > max){
			 throw new ValidateException(ConstCode.VALIDATE_FAIL,"参数长度不在范围中");
		 }
	 }

}
