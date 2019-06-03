package com.tianee.oa.subsys.weixin.ParamesAPI;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信通用接口凭证   采用单例模式
 * @author syl
 * @date 2015.9.16
 */
public  class AccessToken {
	
	//私有构造方法
	private AccessToken(){}
	//已经自行实例化,饱汉模式
	//private static final AccessToken singleAccessToken = new AccessToken();
	private static  AccessToken singleAccessToken ;
	//静态工厂方法
	public static AccessToken getAccessTokenInstance(){
		if(singleAccessToken == null){
			singleAccessToken = new AccessToken();
		}
		return  singleAccessToken;
	}

	// 获取到的凭证
	private static String token;
	
	private static Map<String,String> appToken = new HashMap();
	
	private static Map<String,String> appJsTicket = new HashMap();
	
	// 凭证有效时间，单位：秒
	private static int expiresIn;//微信默认提供7200秒，两个小时

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	public Map<String,String> getAppToken(){
		return appToken;
	}
	
	public Map<String,String> getAppJsTicket(){
		return appJsTicket;
	}
}