package com.alibaba.dingtalk.openapi.demo.auth;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.OApiResultException;
import com.alibaba.dingtalk.openapi.demo.utils.HttpHelper;
import com.alibaba.fastjson.JSONObject;

public class AuthHelper {

	public static String getAccessToken(String corpId,String corpSecret) throws OApiException {
		String url = Env.OAPI_HOST + "/gettoken?" + 
				"corpid=" +corpId + "&corpsecret=" + corpSecret;
		JSONObject response = HttpHelper.httpGet(url);
		if (response.containsKey("access_token")) {
			return response.getString("access_token");
		}
		else {
			throw new OApiResultException("access_token");
		}
	}
	
	public static String getJsapiTicket(String accessToken) throws OApiException {
		String url = Env.OAPI_HOST + "/get_jsapi_ticket?" + 
				"type=jsapi" + "&access_token=" + accessToken;
		JSONObject response = HttpHelper.httpGet(url);
		if (response.containsKey("ticket")) {
			return response.getString("ticket");
		}
		else {
			throw new OApiResultException("ticket");
		}
	}
	
	public static String sign(String ticket, String nonceStr, long timeStamp, String url) 
			throws OApiException {
		String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr +
	            "&timestamp=" + String.valueOf(timeStamp) + "&url=" + url;
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			sha1.reset();
			sha1.update(plain.getBytes("UTF-8"));
			return bytesToHex(sha1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new OApiResultException(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			throw new OApiResultException(e.getMessage());
		}
	}
	
	private static String bytesToHex(byte[] hash) {
		Formatter formatter = new Formatter();
        for (byte b : hash){
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
	}
	
public static String getConfig(String urlString, String queryString){
		
		String queryStringEncode = null;
		String url;
		if(queryString != null){
			queryStringEncode = URLDecoder.decode(queryString);
			url = urlString + "?" + queryStringEncode;
		}
		else{
			url = urlString;
		}
		System.out.println(url);
		String nonceStr = "abcdefg";
		long timeStamp = System.currentTimeMillis()/1000;
		String signedUrl = url;
		String accessToken = null;
		String ticket = null;
		String signature = null;
		try {
			accessToken = "";
			ticket = AuthHelper.getJsapiTicket(accessToken);
			signature = AuthHelper.sign(ticket, nonceStr, timeStamp, signedUrl);
		} catch (OApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{signature:'"+signature+"',nonceStr:'"+nonceStr+"',timeStamp:'"+timeStamp+"',corpId:'"+Env.CORP_ID+"'}";
	}
}
