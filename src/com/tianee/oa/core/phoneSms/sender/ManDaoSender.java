package com.tianee.oa.core.phoneSms.sender;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeSysProps;

public class ManDaoSender implements SmsSender{

	@Override
	public String send(String fromUser, String phone, String content) {
		// TODO Auto-generated method stub
		String tpl;
		String result = "";
		try {
			tpl = TeeSysProps.getString("SMS_TEMPLATE");
			tpl = tpl.replace("${CONTENT}", content);
			tpl = tpl.replace("${DATE}", TeeDateUtil.format(new Date(), "yyyy-MM-dd hh:mm"));
			tpl = tpl.replace("${SENDER}", fromUser);
			
			String sn = TeeSysProps.getString("SMS_APIKEY");
			String pwd = TeeSysProps.getString("SMS_PASSWORD");
			ManDaoClient client=new ManDaoClient(sn,pwd);
			result = client.mdsmssend(phone, URLEncoder.encode(tpl, "utf8"), "", "", "", "");
			if(result.indexOf("-")==-1){
				result = "success";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

}
