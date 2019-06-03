package com.tianee.webframe.util.thread;

import java.util.Map;

import com.alibaba.dingtalk.openapi.demo.message.LightAppMessageDelivery;
import com.alibaba.dingtalk.openapi.demo.message.MessageHelper;
import com.tianee.oa.core.base.email.util.WebMailUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeUtility;

/**
 *外部邮箱具体发送处理类
 * @author xsy
 *
 */
public class TeeWebMailSendProcessor implements Runnable{
	//外部邮箱任务的引用实例
		Map<String,String> data=null;
		Map<String,String> sysWebMail=null;
		
		public TeeWebMailSendProcessor(Map<String,String> data ,Map<String,String> sysWebMail){
			this.data = data;
			this.sysWebMail=sysWebMail;
		}
		
		@Override
		public void run() {
			//如果没有设置必要参数的话，直接跳出
			if(TeeUtility.isNullorEmpty(sysWebMail.get("server")) 
					|| TeeUtility.isNullorEmpty(sysWebMail.get("usermail"))
					|| TeeUtility.isNullorEmpty(data.get("to"))
					|| TeeUtility.isNullorEmpty(sysWebMail.get("user"))
					|| TeeUtility.isNullorEmpty(sysWebMail.get("pass"))){
				return;
			}
			WebMailUtil.send(sysWebMail.get("server"), 
					sysWebMail.get("usermail"), 
					data.get("to"), "", data.get("title"), data.get("content"), 
					sysWebMail.get("user"), sysWebMail.get("pass"), null);
		}
}
