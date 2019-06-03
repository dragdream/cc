package com.tianee.oa.quartzjob;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.alibaba.dingtalk.openapi.demo.message.LightAppMessageDelivery;
import com.alibaba.dingtalk.openapi.demo.message.MessageHelper;
import com.tianee.oa.core.base.email.util.WebMailUtil;
import com.tianee.oa.subsys.weixin.ParamesAPI.AccessToken;
import com.tianee.oa.subsys.weixin.ParamesAPI.WeixinUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * APP应用（微信和钉钉 推送定时器，定时发送）
 * @author kakalion
 *
 */
@Repository
public class TeeAppPushTimer{

	//钉钉缓存消息队列
	public static List<LightAppMessageDelivery> dingMsgBuffQueue = new LinkedList<LightAppMessageDelivery>();
	//钉钉临时消息队列
	List<LightAppMessageDelivery> dingMessageList = new LinkedList<LightAppMessageDelivery>();
		
	//微信缓存消息队列
	public static List<String> wxMsgBuffQueue = new LinkedList<String>();
	//微信临时消息队列
	List<String> wxMessageList = new LinkedList<String>();
	
	//邮件消息队列
	public static List<Map> webMailMsgBuffQueue = new LinkedList<Map>();
	//邮件临时消息队列
	List<Map> webMailMsgList = new LinkedList<Map>();
	
	public void doTimmer(){
		if(TeeSysProps.getProps()==null){
			return;
		}
		//送微信消息
		//sendWxMsg();
		//发送外部邮件消息
		//sendMailMsg();
	}
	
/*	void sendWxMsg(){
		synchronized (wxMsgBuffQueue) {
			wxMessageList.addAll(wxMsgBuffQueue);
			wxMsgBuffQueue.clear();
		}
		
		String accessToken = AccessToken.getAccessTokenInstance().getToken();////TeeSysProps.getString("WEIXIN_ACCESS_TOKEN");
		String weixinUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+accessToken;
		for(String msg:wxMessageList){
			try{
				WeixinUtil.PostMessage(accessToken, "POST", weixinUrl, msg);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		wxMessageList.clear();
	}*/
	
	/**
	 * 加入外部邮件的方法
	 * @param data
	 *//*
	public static void addInternetMail(Map data){
		if(TeeUtility.isNullorEmpty(data.get("to"))){
			return;
		}
		synchronized (webMailMsgBuffQueue) {
			webMailMsgBuffQueue.add(data);
		}
	}
	
	
	void sendMailMsg(){
		synchronized (webMailMsgBuffQueue) {
			webMailMsgList.addAll(webMailMsgBuffQueue);
			webMailMsgBuffQueue.clear();
		}
		
		Map<String,String> sysWebMail = TeeJsonUtil.JsonStr2Map(TeeSysProps.getString("SYS_WEB_MAIL"));
		
		
		for(Map<String,String> data:webMailMsgList){
//			System.out.println("发送邮件："+data);
//			System.out.println("server："+sysWebMail.get("server")+"   usermail："+sysWebMail.get("usermail")+"   to："+data.get("to")+"   title："+data.get("title")+"   user： "+sysWebMail.get("user")+"    pass："+sysWebMail.get("pass"));
			WebMailUtil.send(sysWebMail.get("server"), 
					sysWebMail.get("usermail"), 
					data.get("to"), "", data.get("title"), data.get("content"), 
					sysWebMail.get("user"), sysWebMail.get("pass"), null);
		}
		webMailMsgList.clear();
	}*/

}
