package com.tianee.oa.subsys.weixin.service;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tianee.oa.oaconst.TeeModuleConst;
import com.tianee.oa.subsys.weixin.WeiXinConst;
import com.tianee.oa.subsys.weixin.ParamesAPI.AccessToken;
import com.tianee.oa.subsys.weixin.ParamesAPI.WeixinUtil;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeWeixinService extends TeeBaseService{
	
	/**
	 * 获取微信基础信息
	 */
	public Map getBasicParam(){
		Map data = new HashMap();
		data.put("WEIXIN_CORPID", TeeSysProps.getString("WEIXIN_CORPID"));//企业CORPID
		data.put("WEIXIN_SECRET", TeeSysProps.getString("WEIXIN_SECRET"));//企业Secret
		data.put("WEIXIN_URL", TeeSysProps.getString("WEIXIN_URL"));//外网地址
		data.put("WE_CHAT_CORPID", TeeSysProps.getString("WE_CHAT_CORPID"));//企业CORPID
		data.put("WE_CHAT_SECRET", TeeSysProps.getString("WE_CHAT_SECRET"));//企业Secret
		
		data.put("WEIXIN_EMAIL_APPID", TeeSysProps.getString("WEIXIN_EMAIL_APPID"));
		data.put("WEIXIN_NOTIFY_APPID", TeeSysProps.getString("WEIXIN_NOTIFY_APPID"));
		data.put("WEIXIN_NEWS_APPID", TeeSysProps.getString("WEIXIN_NEWS_APPID"));
		data.put("WEIXIN_WORKFLOW_APPID", TeeSysProps.getString("WEIXIN_WORKFLOW_APPID"));
		data.put("WEIXIN_CALENDAR_APPID", TeeSysProps.getString("WEIXIN_CALENDAR_APPID"));
		data.put("WEIXIN_DIARY_APPID", TeeSysProps.getString("WEIXIN_DIARY_APPID"));
		data.put("WEIXIN_SCHEDULE_APPID", TeeSysProps.getString("WEIXIN_SCHEDULE_APPID"));
		data.put("WEIXIN_TASK_APPID", TeeSysProps.getString("WEIXIN_TASK_APPID"));
		data.put("WEIXIN_CUSTOMER_APPID", TeeSysProps.getString("WEIXIN_CUSTOMER_APPID"));
		data.put("WEIXIN_TOPIC_APPID", TeeSysProps.getString("WEIXIN_TOPIC_APPID"));
		return data;
	}
	
	/**
	 * 保存基础信息
	 */
	public void saveBasicParam(Map<String,String> requestData){
		String type = requestData.get("wechat");
		if("1".equals(type)){
			String WE_CHAT_SECRET = requestData.get("WE_CHAT_SECRET");
			String WE_CHAT_CORPID = requestData.get("WE_CHAT_CORPID");
			TeeSysProps.getProps().setProperty("WE_CHAT_SECRET", WE_CHAT_SECRET);
			TeeSysProps.getProps().setProperty("WE_CHAT_CORPID", WE_CHAT_CORPID);
			simpleDaoSupport.executeUpdate("update TeeSysPara set paraValue=? where paraName=?", new Object[]{WE_CHAT_SECRET,"WE_CHAT_SECRET"});
			simpleDaoSupport.executeUpdate("update TeeSysPara set paraValue=? where paraName=?", new Object[]{WE_CHAT_CORPID,"WE_CHAT_CORPID"});
		}else{
		String WEIXIN_URL = requestData.get("WEIXIN_URL");
		String WEIXIN_SECRET = requestData.get("WEIXIN_SECRET");
		String WEIXIN_CORPID = requestData.get("WEIXIN_CORPID");
		
		
		TeeSysProps.getProps().setProperty("WEIXIN_URL", WEIXIN_URL);
		TeeSysProps.getProps().setProperty("WEIXIN_SECRET", WEIXIN_SECRET);
		TeeSysProps.getProps().setProperty("WEIXIN_CORPID", WEIXIN_CORPID);
		
		simpleDaoSupport.executeUpdate("update TeeSysPara set paraValue=? where paraName=?", new Object[]{WEIXIN_URL,"WEIXIN_URL"});
		simpleDaoSupport.executeUpdate("update TeeSysPara set paraValue=? where paraName=?", new Object[]{WEIXIN_SECRET,"WEIXIN_SECRET"});
		simpleDaoSupport.executeUpdate("update TeeSysPara set paraValue=? where paraName=?", new Object[]{WEIXIN_CORPID,"WEIXIN_CORPID"});
		//成功保存后，马上去更新AccessToken
		WeixinUtil.getAccessToken(WEIXIN_CORPID, WEIXIN_SECRET);
		}
	}
	
	/**
	 * 保存应用ID
	 */
	public void saveAppParam(Map<String,String> requestData){
		String modelType = TeeStringUtil.getString(requestData.get("modelType"));//模块类型
		String WEIXIN_APPID = requestData.get("WEIXIN_APPID");
		String WEIXIN_APPSECRET = requestData.get("WEIXIN_APPSECRET");
//		String WEIXIN_TOKEN =  requestData.get("WEIXIN_TOKEN");
//		String WEIXIN_ENCODING_AESKEY = requestData.get("WEIXIN_ENCODING_AESKEY");
//		String WEIXIN_APPID_NAME = "";
//		String WEIXIN_TOKEN_NAME = "";
//		String WEIXIN_ENCODING_AESKEY_NAME = "";
//		String temp[] = getWeixinAppInfo(modelType, WEIXIN_APPID_NAME, WEIXIN_TOKEN_NAME, WEIXIN_ENCODING_AESKEY_NAME);
//		TeeSysProps.getProps().setProperty(temp[0] , WEIXIN_APPID);
//		TeeSysProps.getProps().setProperty(temp[1] , WEIXIN_TOKEN);
//		TeeSysProps.getProps().setProperty(temp[2], WEIXIN_ENCODING_AESKEY);
		simpleDaoSupport.executeUpdate("update TeeModuleSort set WX_APP_ID=?,WX_SECRET=? where KEY_=?", new Object[]{WEIXIN_APPID,WEIXIN_APPSECRET,modelType});
		TeeModuleConst.MODULE_SORT_WX_APP_ID.put(modelType, WEIXIN_APPID);
		TeeModuleConst.MODULE_SORT_WX_SECRET.put(modelType, WEIXIN_APPSECRET);
		
		//获取应用的AccessToken
		String appToken = WeixinUtil.getAppAccessToken(TeeSysProps.getString("WEIXIN_CORPID"), WEIXIN_APPSECRET);
		AccessToken accessToken = AccessToken.getAccessTokenInstance();
		accessToken.getAppToken().put(WEIXIN_APPID, appToken);
		
		//获取应用的jsticket
		String jsTicket = WeixinUtil.getJsTicket(appToken);
		accessToken.getAppJsTicket().put(WEIXIN_APPID, jsTicket);
		
	}
	
	/**
	 * 获取微信应用信息
	 * @param modelType 模块应用类型 如;email、calendar、notify等
	 * @param WEIXIN_APPID_NAME
	 * @param WEIXIN_EMAIL_TOKEN_NAME
	 * @param WEIXIN_ENCODING_AESKEY_NAME
	 */
	public String[] getWeixinAppInfo(String modelType , String WEIXIN_APPID_NAME , String WEIXIN_EMAIL_TOKEN_NAME , String WEIXIN_ENCODING_AESKEY_NAME ){
		String[] temp = new String[3];
		if(modelType.equals("email")){
			WEIXIN_APPID_NAME = "WEIXIN_EMAIL_APPID";
			WEIXIN_EMAIL_TOKEN_NAME = "WEIXIN_EMAIL_TOKEN";
			WEIXIN_ENCODING_AESKEY_NAME = "WEIXIN_EMAIL_ENCODING_AESKEY";
		}else if(modelType.equals("notify")){
			WEIXIN_APPID_NAME = "WEIXIN_NOTIFY_APPID";
			WEIXIN_EMAIL_TOKEN_NAME = "WEIXIN_NOTIFY_TOKEN";
			WEIXIN_ENCODING_AESKEY_NAME = "WEIXIN_NOTIFY_ENCODING_AESKEY";
		}else if(modelType.equals("news")){
			WEIXIN_APPID_NAME = "WEIXIN_NEWS_APPID";
			WEIXIN_EMAIL_TOKEN_NAME = "WEIXIN_NEWS_TOKEN";
			WEIXIN_ENCODING_AESKEY_NAME = "WEIXIN_NEWS_ENCODING_AESKEY";
		}else if(modelType.equals("workflow")){
			WEIXIN_APPID_NAME = "WEIXIN_WORKFLOW_APPID";
			WEIXIN_EMAIL_TOKEN_NAME = "WEIXIN_WORKFLOW_TOKEN";
			WEIXIN_ENCODING_AESKEY_NAME = "WEIXIN_WORKFLOW_ENCODING_AESKEY";
		}else if(modelType.equals("calendar")){
			WEIXIN_APPID_NAME = "WEIXIN_CALENDAR_APPID";
			WEIXIN_EMAIL_TOKEN_NAME = "WEIXIN_CALENDAR_TOKEN";
			WEIXIN_ENCODING_AESKEY_NAME = "WEIXIN_CALENDAR_ENCODING_AESKEY";
		}else if(modelType.equals("diary")){
			WEIXIN_APPID_NAME = "WEIXIN_DIARY_APPID";
			WEIXIN_EMAIL_TOKEN_NAME = "WEIXIN_DIARY_TOKEN";
			WEIXIN_ENCODING_AESKEY_NAME = "WEIXIN_DIARY_ENCODING_AESKEY";
		}else if(modelType.equals("schedule")){
			WEIXIN_APPID_NAME = "WEIXIN_SCHEDULE_APPID";
			WEIXIN_EMAIL_TOKEN_NAME = "WEIXIN_SCHEDULE_TOKEN";
			WEIXIN_ENCODING_AESKEY_NAME = "WEIXIN_SCHEDULE_ENCODING_AESKEY";
		}else if(modelType.equals("task")){
			WEIXIN_APPID_NAME = "WEIXIN_TASK_APPID";
			WEIXIN_EMAIL_TOKEN_NAME = "WEIXIN_TASK_TOKEN";
			WEIXIN_ENCODING_AESKEY_NAME = "WEIXIN_TASK_ENCODING_AESKEY";
		}else if(modelType.equals("customer")){
			WEIXIN_APPID_NAME = "WEIXIN_CUSTOMER_APPID";
			WEIXIN_EMAIL_TOKEN_NAME = "WEIXIN_CUSTOMER_TOKEN";
			WEIXIN_ENCODING_AESKEY_NAME = "WEIXIN_CUSTOMER_ENCODING_AESKEY";
		}else if(modelType.equals("topic")){
			WEIXIN_APPID_NAME = "WEIXIN_TOPIC_APPID";
			WEIXIN_EMAIL_TOKEN_NAME = "WEIXIN_TOPIC_TOKEN";
			WEIXIN_ENCODING_AESKEY_NAME = "WEIXIN_TOPIC_ENCODING_AESKEY";
		}else if(modelType.equals("pubdisk")){
			WEIXIN_APPID_NAME = "WEIXIN_PUBDISK_APPID";
			WEIXIN_EMAIL_TOKEN_NAME = "WEIXIN_PUBDISK_TOKEN";
			WEIXIN_ENCODING_AESKEY_NAME = "WEIXIN_PUBDISK_ENCODING_AESKEY";
		}else if(modelType.equals("persondisk")){
			WEIXIN_APPID_NAME = "WEIXIN_PERSONDISK_APPID";
			WEIXIN_EMAIL_TOKEN_NAME = "WEIXIN_PERSONDISK_TOKEN";
			WEIXIN_ENCODING_AESKEY_NAME = "WEIXIN_PERSONDISK_ENCODING_AESKEY";
		}
		temp[0] = WEIXIN_APPID_NAME;
		temp[1] = WEIXIN_EMAIL_TOKEN_NAME;
		temp[2] = WEIXIN_ENCODING_AESKEY_NAME;
		return temp;
	}
	
}
