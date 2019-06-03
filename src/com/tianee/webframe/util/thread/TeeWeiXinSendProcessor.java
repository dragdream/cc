package com.tianee.webframe.util.thread;

import com.alibaba.dingtalk.openapi.demo.message.LightAppMessageDelivery;
import com.alibaba.dingtalk.openapi.demo.message.MessageHelper;
import com.tianee.oa.subsys.weixin.ParamesAPI.AccessToken;
import com.tianee.oa.subsys.weixin.ParamesAPI.WeixinUtil;
import com.tianee.webframe.util.global.TeeSysProps;

/**
 * 微信具体发送处理类
 * @author xsy
 *
 */
public  class TeeWeiXinSendProcessor implements Runnable{

	    //微信任务的引用实例
		String msg="";
		String accessToken ="";////TeeSysProps.getString("WEIXIN_ACCESS_TOKEN");
		String weixinUrl ="";
		
		public TeeWeiXinSendProcessor(String msg,String accessToken,String weixinUrl){
			this.msg = msg;
			this.accessToken=accessToken;
			this.weixinUrl=weixinUrl;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
				WeixinUtil.PostMessage(accessToken, "POST", weixinUrl, msg);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
}
