package com.tianee.oa.quartzjob;

import org.springframework.stereotype.Repository;

import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.auth.AuthHelper;
import com.alibaba.dingtalk.openapi.demo.utils.HttpHelper;
import com.alibaba.fastjson.JSONObject;
import com.tianee.oa.oaconst.TeeModuleConst;
import com.tianee.oa.subsys.weixin.ParamesAPI.AccessToken;
import com.tianee.oa.subsys.weixin.ParamesAPI.WeixinUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeUtility;

import java.lang.System;
import java.util.Map;
import java.util.Set;

@Repository
public class TeeAccessTokenTimer{

	/**
	 * 手动控制事务，细粒度
	 * @throws OApiException 
	 */
	public void doTimmer(){
		if(TeeSysProps.getProps()==null){
			return;
		}
		
		//钉钉
		String DD_CORPID = TeeSysProps.getString("DD_CORPID");
		String DD_CORPSECRET = TeeSysProps.getString("DD_CORPSECRET");
		String DD_URL = TeeSysProps.getString("DD_URL");
		
		if(!"".equals(DD_CORPID) && !"".equals(DD_CORPSECRET) && !"".equals(DD_URL)){
			//获取钉钉accessToken和票据
			JSONObject jsonData;
			try {
				jsonData = HttpHelper.httpGet("https://oapi.dingtalk.com/gettoken?corpid="+DD_CORPID+"&corpsecret="+DD_CORPSECRET+"");
				if("ok".equals(jsonData.getString("errmsg"))){
					TeeSysProps.getProps().setProperty("DING_ACCESS_TOKEN", jsonData.getString("access_token"));
					String ticket = AuthHelper.getJsapiTicket(jsonData.getString("access_token"));
					TeeSysProps.getProps().setProperty("DING_JSAPI_TICKET", ticket);
				}
			} catch (OApiException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}
		
		//微信获取企业号的全局唯一票据
		String WEIXIN_CORPID = TeeSysProps.getString("WEIXIN_CORPID");
		String WEIXIN_SECRET = TeeSysProps.getString("WEIXIN_SECRET");
		String WEIXIN_URL = TeeSysProps.getString("WEIXIN_URL");
		if(!"".equals(WEIXIN_CORPID) && !"".equals(WEIXIN_SECRET) && !"".equals(WEIXIN_URL)){
			//获取微信accessToken，并存入参数中
			AccessToken accessToken = WeixinUtil.getAccessToken(WEIXIN_CORPID, WEIXIN_SECRET);
			if(accessToken != null){
				TeeSysProps.getProps().setProperty("WEIXIN_ACCESS_TOKEN",accessToken.getToken());
			}
			//获取JSTicket
			String jsTicket = WeixinUtil.getJsTicket(accessToken.getToken());
			if(jsTicket!=null){
				TeeSysProps.getProps().setProperty("WEIXIN_JSAPI_TICKET",jsTicket);
			}
			
			//获取每个应用的appToken
			Map<String,String> appMap = TeeModuleConst.MODULE_SORT_WX_APP_ID;
			Map<String,String> appSecretMap = TeeModuleConst.MODULE_SORT_WX_SECRET;
			Set<String> keys = appMap.keySet();
			for(String key:keys){
				String agentId = appMap.get(key);
				String secret = appSecretMap.get(key);
				if(!TeeUtility.isNullorEmpty(agentId) && !TeeUtility.isNullorEmpty(secret)){
					String token = WeixinUtil.getAppAccessToken(WEIXIN_CORPID, secret);
					accessToken.getAppToken().put(agentId, token);
					
					jsTicket = WeixinUtil.getJsTicket(token);
					accessToken.getAppJsTicket().put(agentId, jsTicket);
				}
			}
			
		}
	}

}
