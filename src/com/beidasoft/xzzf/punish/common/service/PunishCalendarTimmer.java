package com.beidasoft.xzzf.punish.common.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishCalendar;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.phoneSms.bean.TeeSmsSendPhone;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysProps;

@Service
public class PunishCalendarTimmer extends TeeBaseService{
	@Autowired
	PunishCalendarService calendarService;
	@Autowired
	private TeeSmsManager smsManager;
	@Autowired
	private TeeSimpleDaoSupport simpleDaoSupport;
	public void doTimmer(){
		try {
			if(TeeSysProps.getProps()==null){
				return;
			}
			List<PunishCalendar> pCalendars = calendarService.getInExecution();
			for (int i = 0; i < pCalendars.size(); i++) {
				if (pCalendars.get(i).getCountDown()==0) {
					pCalendars.get(i).setStatus("300");
					calendarService.updateObj(pCalendars.get(i));
					if ("100".equals(pCalendars.get(i).getRemindType())) {
						sendSmsManager(pCalendars.get(i),"，已到期！请即使处理！");
					}else if ("200".equals(pCalendars.get(i).getRemindType())) {
						sendSmsSendPhone(pCalendars.get(i),"，已到期！请尽快处理！");
					}else if ("300".equals(pCalendars.get(i).getRemindType())) {
						sendSmsManager(pCalendars.get(i),"，已到期！请尽快处理！");
						sendSmsSendPhone(pCalendars.get(i),"，已到期！请尽快处理！");
					}
				} else if(pCalendars.get(i).getCountDown()<0){
					pCalendars.get(i).setStatus("300");
					calendarService.updateObj(pCalendars.get(i));
					if ("100".equals(pCalendars.get(i).getRemindType())) {
						sendSmsManager(pCalendars.get(i),"，已超时！请即使处理！");
					}else if ("200".equals(pCalendars.get(i).getRemindType())) {
						sendSmsSendPhone(pCalendars.get(i),"，已超时！请尽快处理！");
					}else if ("300".equals(pCalendars.get(i).getRemindType())) {
						sendSmsManager(pCalendars.get(i),"，已超时！请尽快处理！");
						sendSmsSendPhone(pCalendars.get(i),"，已超时！请尽快处理！");
					}
				} else {
					if (pCalendars.get(i).getCountDown()<=pCalendars.get(i).getRemindDaily()&&"100".equals(pCalendars.get(i).getRemindType())) {
						sendSmsManager(pCalendars.get(i),"，即将到期！请尽快处理！");
					}else if (pCalendars.get(i).getCountDown()<=pCalendars.get(i).getRemindDaily()&&"200".equals(pCalendars.get(i).getRemindType())) {
						sendSmsSendPhone(pCalendars.get(i),"，即将到期！请尽快处理！");
					}else if (pCalendars.get(i).getCountDown()<=pCalendars.get(i).getRemindDaily()&&"300".equals(pCalendars.get(i).getRemindType())) {
						sendSmsManager(pCalendars.get(i),"，即将到期！请尽快处理！");
						sendSmsSendPhone(pCalendars.get(i),"，即将到期！请尽快处理！");
					}
					pCalendars.get(i).setCountDown(pCalendars.get(i).getCountDown()-1);
					calendarService.updateObj(pCalendars.get(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//发送站内信
	private void sendSmsManager(PunishCalendar pCalendar,String templet){
		Map requestData = new HashMap();
		requestData.put("content",pCalendar.getRemindTemplet()+templet);
		requestData.put("userListIds",pCalendar.getUser().getUuid());
		requestData.put("moduleNo","104");
		requestData.put("remindUrl",pCalendar.getRemindUrl());
		Calendar cl= Calendar.getInstance();
		smsManager.sendSms(requestData, null);
	}
	//发送短信
	private void sendSmsSendPhone(PunishCalendar pCalendar,String templet){
		TeeSmsSendPhone sms = new TeeSmsSendPhone();
		sms.setContent(pCalendar.getRemindTemplet()+templet);
	    sms.setFromId(4);
	    sms.setFromName("系统管理员");
	    sms.setPhone(pCalendar.getUser().getMobilNo());
	    sms.setSendFlag(0);
	    sms.setSendNumber(1);
	    sms.setSendTime(Calendar.getInstance());
	    sms.setToId(pCalendar.getUser().getUuid());
	    sms.setToName(pCalendar.getUser().getUserName());
	    simpleDaoSupport.save(sms);
	}
}
