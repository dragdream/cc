package com.tianee.thirdparty.newcapec.quartzjob.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class AuthUtil {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		//String abc = new String("我们".getBytes(), "GBK");
		//System.out.println(abc);
		
		//System.out.println(AuthUtil.HMACSHA1("我们", "UNIPORTAL"));
		
		// b19d653219c91d2fed2b4743658a7e3df1d02f4b
	
		//System.out.println(AuthUtil.HMACSHA1(abc, "UNIPORTAL", "GBK"));
		
		// b19d653219c91d2fed2b4743658a7e3df1d02f4b
		
		String a = "10201407231549569313jwxt1000610006stuempno2011118317,2011112018,2011118310,2011118347,20111183442014-2015学年1学期2014年“优贤一夏”MBA暑假集训班于2014/07/21开始选课，<a href=\"http://10.2.29.55:8180/eams-shuju/stdOtherExamSignUp.action\">点击进入。</a>1";
		//		    10201407231549579313jwxt1000610006stuempno2011118317,2011112018,2011118310,2011118347,2011118344
		System.out.println(AuthUtil.HMACSHA1(a, "201EEDE0D2BA4244A94F1C8A2B6D2AD1"));
	}
	
	/**
	 * 加 密
	 * @param data 明文
	 * @param key 密钥
	 * @return 密文
	 */
	public static String HMACSHA1(String data, String key) {
		byte[] byteHMAC = null;
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
			SecretKeySpec spec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
			mac.init(spec);
			
			byteHMAC = mac.doFinal(data.getBytes("UTF-8"));
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException ignore) {
			ignore.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (byteHMAC != null) {
			try {
				String hexMac = getHexString(byteHMAC);
				return hexMac;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		return null;
	}
	
	/**
	 * byte2HexString
	 * @param b
	 * @return 十六进制字符串
	 * @throws Exception
	 */
	public static String getHexString(byte[] b) throws Exception {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}
	
	/**
	 * 时间差
	 * @param timestamp
	 * @param seconds
	 * @return 时间戳与当前时间差在 seconds 秒内 返回true，否则返回false
	 */
	public static boolean checkIntervalTime(String timestamp, long seconds){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			Calendar c = Calendar.getInstance();
			Date curTime = c.getTime();
			Date time = sdf.parse(timestamp);
			long interval = (time.getTime() - curTime.getTime()) / 1000;
			if(interval < seconds && interval > -seconds){
				return true;
			}else{
				return false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 时间格式转化
	 * @param time
	 * @return
	 */
	public static String timeFormat(String time){
		String year = time.substring(0, 4);
		String mon = time.substring(4, 6);
		String day = time.substring(6, 8);
		String hour = time.substring(8, 10);
		String min = time.substring(10, 12);
		String sec = time.substring(12, 14);
		return year+"-"+mon+"-"+day+" "+hour+":"+min+":"+sec;
	}
}
