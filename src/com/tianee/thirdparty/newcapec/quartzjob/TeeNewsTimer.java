package com.tianee.thirdparty.newcapec.quartzjob;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import com.tianee.oa.core.general.bean.TeeSms;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.thirdparty.newcapec.quartzjob.utils.AuthUtil;
import com.tianee.thirdparty.newcapec.quartzjob.utils.HttpClientFactory;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysCustomerProps;
import com.tianee.webframe.util.secure.TeePassBase64;

@Repository
public class TeeNewsTimer {
	// TeeModuleSort 模块
	// TeeSms 消息
	// TeeSmsBody 消息内容
//改了 msgid
	@Autowired
	private SessionFactory sessionFactory;

	public void newsTimer() {
		    Session session = null;
			Transaction tx = null;
			int sj;//参数
			String apptypecode ="";
			String content="";//内容
			try{
			session = sessionFactory.openSession();
			Query query =session.createQuery("from TeeSms where xkpFlag=0");
            List<TeeSms> list= query.list();
			for (TeeSms sms : list) {
				TeePerson tp= (TeePerson)session.get(TeePerson.class,sms.getToId());//根据getToId获得人员对象是
				if (sms.getSmsBody()!=null&&tp != null) {
					int appid = Integer.parseInt(TeeSysCustomerProps.getString("XKP_APPID"));// 对应新开普系统编号 ID
					String appkey = TeeSysCustomerProps.getString("XKP_APPKEY");// 新开普系统密钥
					if(sms.getSmsBody().getModuleNo()=="006"){
					      apptypecode = TeeSysCustomerProps.getString("XKP_GZL");// 新开普类型编码    2002是oa系统所有消息   2001oa工流
					}else{
				          apptypecode = TeeSysCustomerProps.getString("XKP_OA");
					}
					String msgid =sms.getUuid();// 消息ID唯一的 自动生成
					
					if(sms.getSmsBody().getRemindUrl()!=null&&sms.getSmsBody().getRemindUrl()!=""){//判断SmsBody表里的RemindUrl属性是不是“”
						sj=1;
					}else{
						sj=0;
					}
					if(sj==1){
					  content = "<a target='_blank' href='"
							+ TeeSysCustomerProps.getString("QZDZ")
							+ TeePassBase64.encodeStr(TeeSysCustomerProps.getString("CSDZ")+sms.getSmsBody().getRemindUrl()) + "'>"
							+ sms.getSmsBody().getContent() + "</a>";
					}else{
					  content = sms.getSmsBody().getContent() ;
					}
				      Date txTime=sms.getRemindTime();  //消息时间
					Map<String, String> map = reminderServicePush(appid,
							appkey, msgid, apptypecode, "stuempno",
							tp.getUserId(), content, "", 1,txTime);
					String kv = map.get("retcode");
					tx = session.beginTransaction();
					if (kv.equals("0")) {
						sms.setXkpFlag(1);// 消息发送成功
						
					} else {
						sms.setXkpFlag(0); // 消息没发送成功
					}
					tx.commit();
				} else {
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
	 }

	// 新开普消息方法
	public Map<String, String> reminderServicePush(long app_id, String app_key,
			String refno, String reminder_type_code, String target_type,
			String target_ids, String content, String url, int impflag,Date txTime) {

		Map<String, String> map = new HashMap<String, String>();
		String api_url = TeeSysCustomerProps.getString("XKP_TSJK");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String timestamp = sdf.format(txTime);
		
	  //System.out.println("localhost timestamp = " + timestamp);

		String data = app_id + timestamp + refno + reminder_type_code
				+ target_type + target_ids + content + url + impflag;
		
	  //System.out.println(data);
		
		String sign = AuthUtil.HMACSHA1(data, app_key);
		
	//	System.out.println(sign);

		String sign_method = "HMAC";

		Map<String, String> postData = new HashMap<String, String>();
		postData.put("app_id", String.valueOf(app_id));
		postData.put("reminder_type_code", reminder_type_code);
		postData.put("timestamp", timestamp);
		postData.put("sign", sign);
		postData.put("sign_method", sign_method);
		postData.put("refno", refno);
		postData.put("target_type", target_type);
		postData.put("target_ids", target_ids);
		postData.put("content", content);
		postData.put("url", url);
		postData.put("impflag", String.valueOf(impflag));

		String response = HttpClientFactory.getInstance().goExePost(api_url,
				postData);
		
		//System.out.println("response = " + response);
		
		String sjkey = response.substring(9, 16);
		String sjvalue = response.substring(19, 20);
		map.put(sjkey, sjvalue);
		return map;
	}



}
