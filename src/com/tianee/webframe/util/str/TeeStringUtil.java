package com.tianee.webframe.util.str;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 字符串工具类
 * @author lt
 *
 */
public class TeeStringUtil {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static int getInteger(Object str,int def){
		String s = getString(str);
		int dot = s.indexOf('.');
		if(dot!=-1){
			s = s.substring(0, dot);
		}
		if(!"".equals(s)){
			try{
				return Integer.parseInt(s);
			}catch(Exception e){
				return def;
			}
		}
		return def;
	}
	
	public static String format2ArrayStr(Object objs[]){
		StringBuffer sb = new StringBuffer();
		if(objs!=null){
			for(int i=0;i<objs.length;i++){
				sb.append(TeeStringUtil.getString(objs[i]));
				if(i!=objs.length-1){
					sb.append(",");
				}
			}
		}
		return sb.toString();
	}
	
	public static Date getDate(Object str){
		String s = getString(str);
		if(!"".equals(s)){
			try{
				return sdf.parse(s);
			}catch(Exception e){
				return null;
			}
		}
		return null;
	}
	
	/**
	 * 是否存在某个整型
	 * @param array
	 * @param element
	 * @return
	 */
	public static boolean existInt(int array[],int element){
		if(array==null){
			return false;
		}
		for(int tmp:array){
			if(tmp==element){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否存在某个字符串
	 * @param array
	 * @param element
	 * @return
	 */
	public static boolean existString(String array[],String element){
		if(array==null){
			return false;
		}
		for(String tmp:array){
			if(tmp.equals(element)){
				return true;
			}
		}
		return false;
	}
	
	public static Date getDate(Object str,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String s = getString(str);
		if(!"".equals(s)){
			try{
				return sdf.parse(s);
			}catch(Exception e){
				return null;
			}
		}
		return null;
	}
	
	public static String getString(Object str){
		if(str==null){
			return "";
		}
		return String.valueOf(str);
	}
	
	public static String getString(String str,String str1){
		if(str==null || "".equals(str)){
			return str1;
		}
		return str;
	}
	
	public static String getString(Object obj,String str1){
		if(obj==null || "".equals((String)obj.toString())){
			return str1;
		}
		return (String)obj.toString();
	}
	public static int[] parseIntegerArray(Object str){
		List<Integer> list = new ArrayList<Integer>();
		if(str==null || "".equals(str.toString())){
			return new int[0];
		}
		String sp[]= getString(str).split(",");
		int l[] = new int[0];
		int tmp = 0;
		for(int i=0;i<sp.length;i++){
			tmp = getInteger(sp[i], 0);
			list.add(tmp);
		}
		l = new int[list.size()];
		for(int i=0;i<list.size();i++){
			l[i] = list.get(i);
		}
		return l;
	}
	
	public static String[] parseStringArray(Object str){
		String sp[]= getString(str).split(",");
		return sp;
	}
	
	public static String getString4Null(Object str){
		if(str==null){
			return null;
		}
		return getString(str);
	}
	/**
	 * 过滤空的字符串 如果为空 则返回后面的值
	 * @param str1 要过滤的字符串
	 * @param str2 空串的时候被替换的字符串
	 * @return
	 */
	public static String fileEmptyString(String str1,String str2){
		if(str1==null || "".equals(str1)){
			return str2;
		}
		return str1;
	}
	public static long getLong(Object str,long def){
		String s = getString(str);
		if(!"".equals(s)){
			try{
				return Long.parseLong(s);
			}catch(Exception e){
				return def;
			}
		}
		return def;
	}
	
	public static Float getFloat(Object str,Float def){
		String s = getString(str);
		if(!"".equals(s)){
			try{
				return Float.parseFloat(s);
			}catch(Exception e){
				return def;
			}
		}
		return def;
	}
	
	public static Byte getByte(Object str,Byte def){
		String s = getString(str);
		if(!"".equals(s)){
			try{
				return Byte.parseByte(s);
			}catch(Exception e){
				return def;
			}
		}
		return def;
	}
	
	
	public static double getDouble(Object str,double def){
		String s = getString(str);
		if(!"".equals(s)){
			try{
				return Double.parseDouble(s);
			}catch(Exception e){
				return def;
			}
		}
		return def;
	}
	
	/**
	 * 将字符串转换为新字符串，处理查询sql语句（以逗号分隔，添加单引号）
	 * @param str
	 * @return
	 */
	public static String getSqlStringParse(String str){
		String[] strArray = str.split(",");
		String newStr = "";
		for (int i = 0; i < strArray.length; i++) {
			if(TeeUtility.isNullorEmpty(strArray[i])){
				continue;
			}
			if(newStr.equals("")){
				newStr = "'" + strArray[i]+  "'" ;
			}else{
				
				newStr = newStr + ",'" + strArray[i]  + "'" ;
			}
		}
		return newStr;
	}
	
	public static String getSqlStringParse(List<String> list){
		String newStr = "";
		for (int i = 0; i < list.size(); i++) {
			if(TeeUtility.isNullorEmpty(list.get(i))){
				continue;
			}
			if(newStr.equals("")){
				newStr = "'" + list.get(i) +  "'" ;
			}else{
				newStr = newStr + ",'" + list.get(i) + "'" ;
			}
		}
		return newStr;
	}	
	
	/**
	 * 格式化ID字符串数组
	 * @param ids
	 * @return
	 */
	public static String formatIdsQuote(String ids){
		String arr[] = parseStringArray(ids);
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<arr.length;i++){
			sb.append(arr[i]);
			if(i!=arr.length-1){
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 大字段转换string
	 * @param obj
	 * @return
	 */
	public static String clob2String(Object obj){
		if(obj==null){
			return "";
		}
		if(obj instanceof String){
			return getString(obj);
		}else{
			String reString = "";
	        Reader is = null;
	        BufferedReader br = null;
	        Clob clob = (Clob) obj;
			try {
				is = clob.getCharacterStream();
				br = new BufferedReader(is);
		        String s = null;
		        StringBuffer sb = new StringBuffer();
		        while ((s = br.readLine())!=null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
		            sb.append(s+"\n");
		        }
		        if(sb.toString().endsWith("\n")){
		        	reString = sb.substring(0, sb.length()-1);
		        }else{
		        	reString = sb.toString();
		        }
		        
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(is!=null){
					try {
						is.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(br!=null){
					try {
						br.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return reString;
		}
	}
}
