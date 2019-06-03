package com.tianee.oa.core.phoneSms.sender;

import java.util.Date;

import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.vosms.impl.VOSmsImpl;

public class VoSmsSender implements SmsSender{
	
	private static Object lock = new Object();
	
	@Override
	public String send(String fromUser, String phone, String content) {
		// TODO Auto-generated method stub
		synchronized (lock) {
			String tpl = "";
			boolean result=false;
			String res="";
			try {
				tpl = TeeSysProps.getString("SMS_TEMPLATE");
				tpl = tpl.replace("${CONTENT}", content);
				tpl = tpl.replace("${DATE}", TeeDateUtil.format(new Date(), "yyyy-MM-dd hh:mm"));
				tpl = tpl.replace("${SENDER}", fromUser);
				
				VOSmsImpl vosms = new VOSmsImpl();
				result = vosms.sendSms(phone, content);
				
				if(result){
					res = "success";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return res;
		}
		
		
	}
	
}
