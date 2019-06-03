package com.tianee.oa.core.system.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.calendar.dao.TeeCalendarAffairDao;
import com.tianee.oa.core.base.calendar.service.TeeCalAffairService;
import com.tianee.oa.core.base.email.service.TeeEmailService;
import com.tianee.oa.core.base.message.dao.TeeMessageDao;
import com.tianee.oa.core.base.news.dao.TeeNewsDao;
import com.tianee.oa.core.base.notify.dao.TeeNotifyDao;
import com.tianee.oa.core.general.dao.TeeSmsDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.priv.dao.TeeMenuDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeSystemOtherServer extends TeeBaseService {

	@Autowired
	TeePersonDao personDao;
	
	@Autowired
	TeeCalAffairService affairService;
	
	@Autowired
	private TeeMenuDao menuDao;
	
	@Autowired
	private TeeCalendarAffairDao calendarAffairDao;
	
	@Autowired
	private TeeSmsDao smsDao;
	@Autowired
	private TeeEmailService emailService;
	
	@Autowired
	private TeeMessageDao  messageDao;
	
	@Autowired
	private TeeNotifyDao notifyDao;
	
	@Autowired
	private TeeNewsDao newsDao;

	/**
	 * 获取各模块待办记录数
	 * @author syl
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	public TeeJson getModelNoHandCount(HttpServletRequest request) throws ParseException{
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		if(person == null){
			return json;
		}
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

		String stratTime = TeeUtility.getCurDateTimeStr("yyyy-MM-dd");
		long calendarCount = calendarAffairDao.selectCountByTime(person, stratTime, stratTime);//日程
		long emailCount = emailService.getNotReadingCountService(request);
		long smsCount = smsDao.getReceiveNoReadCount(person);
		long messageCount = 0;//messageDao.getComfireNoCount(person.getUserId());
		long newsCount = 0;//未读新闻
		long workFlow = 0;//待办工作
		long docRecWorkFlow = 0;////获取待收公文
		long docReadWorkFlow = 0;//获取待阅公文
		long notifyCount = notifyDao.getPersonalNoReadCount(0, person,"");;//未读公告
				
		//获取未读新闻
		String hql = "select count(n.sid) from TeeNews n where  n.publish = '1'  and   exists (select 1 from n.infos info where info.toUser.uuid = "+person.getUuid()+" and n.newsTime <= ? ";
		hql = hql + "and info.isRead = 0 )";
		Object[] values = {new Date()};
		newsCount = simpleDaoSupport.count(hql, values);
		
		//获取待办工作
		hql = "select count(frp.sid) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.uuid="+person.getUuid()+" and frp.flag in (1,2) and frp.flowRun.endTime is null and frp.delFlag=0 and frp.suspend=0";
		workFlow = simpleDaoSupport.count(hql, null);

		
		//获取待收公文
		hql = "select count(d.uuid) from TeeDocumentDelivery d where d.flag=0 and exists (select 1 from TeeDocumentRecMapping m where m.dept.uuid=d.recDept and exists (select 1 from m.privUsers user where user.uuid="+person.getUuid()+"))";
		docRecWorkFlow = simpleDaoSupport.count(hql, null);
		
		//获取待阅公文
		hql = "select count(v.uuid) from TeeDocumentView v where v.flag=0 and v.recUser="+person.getUuid();
		docReadWorkFlow = simpleDaoSupport.count(hql, null);
				
		Map map = new HashMap();
		map.put("calendarCount", calendarCount);//日程安排
		map.put("emailCount", emailCount);//邮件
		map.put("smsCount", smsCount);//短信
		map.put("workFlow", workFlow);//工作流；---
		map.put("messageCount", messageCount);//消息；---
		map.put("newsCount", newsCount);//新闻；---
		map.put("notifyCount", notifyCount);//公告；---
		
		map.put("docRecWorkFlow", docRecWorkFlow);////获取待收公文
		map.put("docReadWorkFlow", docReadWorkFlow);////获取待阅公文
		Map attaendInfo = new HashMap();
		map.put("attendDutyInfo", attaendInfo);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	
	
}
