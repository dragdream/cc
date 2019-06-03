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

import com.tianee.oa.core.general.bean.TeeSms;
import com.tianee.oa.core.org.bean.TeePerson;


import com.tianee.thirdparty.newcapec.quartzjob.utils.AuthUtil;
import com.tianee.thirdparty.newcapec.quartzjob.utils.HttpClientFactory;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysCustomerProps;

@Repository
public class TeeNewsStateTimer extends TeeBaseService {
	@Autowired
	private SessionFactory sessionFactory;

	public void newsStateTimer() {
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery("from TeeSms where xkpFlag=1 and remindFlag=1");
			List<TeeSms> list = query.list();
			for (TeeSms sms : list) {
			//	System.out.println(sms.getUuid());
			
				TeePerson tp = (TeePerson) session.get(TeePerson.class,
						sms.getToId());// 根据getToId获得人员对象
				Date txTime=sms.getRemindTime();//得到消息时间
				if (tp != null) {
					Map<String, String> map = reminderServiceRead(Long.parseLong(TeeSysCustomerProps.getString("APP_ID")),
							TeeSysCustomerProps.getString("APP_KEY"),
							sms.getUuid(),
							tp.getUserId(),
							Integer.parseInt(TeeSysCustomerProps.getString("READFLAG")),txTime);
					String kv = map.get("retcode");
					tx = session.beginTransaction();
					if (kv.equals("0")) {
						sms.setXkpFlag(2);// 消息 同步
					}
					tx.commit();
				} else {
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public Map<String, String> reminderServiceRead(long app_id, String app_key,
			String refno, String target_id, int readflag,Date txTime) {
		Map<String, String> map = new HashMap<String, String>();
		String api_url = TeeSysCustomerProps.getString("APP_URL_REMINDER_READ");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String timestamp = sdf.format(txTime);

		String data = app_id + timestamp + refno + target_id + readflag;

		String sign = AuthUtil.HMACSHA1(data, app_key);

		String sign_method = TeeSysCustomerProps.getString("SIGN_METHOD");

		Map<String, String> postData = new HashMap<String, String>();
		postData.put("app_id", String.valueOf(app_id));
		postData.put("timestamp", timestamp);
		postData.put("sign", sign);
		postData.put("sign_method", sign_method);
		postData.put("refno", refno);
		postData.put("target_id", target_id);
		postData.put("readflag", String.valueOf(readflag));

		String response = HttpClientFactory.getInstance().goExePost(api_url,
				postData);
	  //System.out.println("reminderServicePush resp = " + response);
		String sjkey = response.substring(9, 16);
		String sjvalue = response.substring(19, 20);
		map.put(sjkey, sjvalue);
		return map;
	}
}
