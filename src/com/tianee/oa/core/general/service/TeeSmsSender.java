package com.tianee.oa.core.general.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sun.misc.BASE64Encoder;

import com.alibaba.dingtalk.openapi.demo.message.LightAppMessageDelivery;
import com.alibaba.dingtalk.openapi.demo.message.OAMessage;
import com.alibaba.dingtalk.openapi.demo.message.OAMessage.Body;
import com.alibaba.dingtalk.openapi.demo.message.OAMessage.Head;
import com.tianee.oa.core.general.bean.TeeSms;
import com.tianee.oa.core.general.bean.TeeSmsBody;
import com.tianee.oa.core.general.dao.TeeSmsBodyDao;
import com.tianee.oa.core.general.dao.TeeSmsDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeModuleConst;
import com.tianee.oa.subsys.weixin.ParamesAPI.AccessToken;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.socket.MessagePusher;
import com.tianee.webframe.util.cache.RedisClient;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeDingDingSendProcessor;
import com.tianee.webframe.util.thread.TeeDingDingThreadPool;
import com.tianee.webframe.util.thread.TeeWeiXinSendProcessor;

/**
 * 短信管理
 * @author CXT
 *
 */
@Service
@Transactional(readOnly=false,rollbackFor=Exception.class)
public class TeeSmsSender extends TeeBaseService{
	@Autowired
	private TeeSmsDao smsDao;
	@Autowired
	private TeeSmsBodyDao smsBodyDao;
	@Autowired
	private TeePersonDao personDao;
	
	/**
	 * 发送短信
	 * @param requestData
	 * @param loginPerson
	 * @return
	 */
	public void sendSms(Map requestData,TeePerson loginPerson){

		try{
			
			int fromId = loginPerson==null?0:loginPerson.getUuid();
			String userId = loginPerson==null?"":loginPerson.getUserId();
			String userName = loginPerson==null?"":loginPerson.getUserName();
//			String userName = loginPerson==null?"系统消息":loginPerson.getUserName();
			String remindUrl = TeeStringUtil.getString(requestData.get("remindUrl"));//提醒url
			String remindUrl1=TeeStringUtil.getString(requestData.get("remindUrl1"));//手机端事务提醒
			String sendTimeStr = TeeStringUtil.getString(requestData.get("sendTime"));
//			String moduleId = TeeStringUtil.getString(requestData.get("moduleId"));
			Calendar sendTime = Calendar.getInstance();//发送时间串
			if(sendTimeStr!=null && !"".equals(sendTimeStr)){
				sendTime.setTime(TeeDateUtil.parseDate(sendTimeStr));
			}
			String moduleNo = TeeStringUtil.getString(requestData.get("moduleNo"));//模块编码
			String content = TeeStringUtil.getString(requestData.get("content"));//内容
			String userListIds = TeeStringUtil.getString(requestData.get("userListIds"));//用户Ids串
			List<TeePerson> userList = (List<TeePerson>) requestData.get("userList");
			Set<TeePerson> userSet = (Set<TeePerson>) requestData.get("userSet");

			//存储smsBody
			TeeSmsBody smsBody = new TeeSmsBody();
	    	smsBody.setContent(content);
	    	smsBody.setFromId(fromId);
	    	smsBody.setRemindUrl(remindUrl);
	    	smsBody.setRemindUrl1(remindUrl1);
	    	smsBody.setSendTime(sendTime);
	    	smsBody.setModuleNo(moduleNo);
	    	//如果发送时间大于当前时间，则延时发送
	    	if(sendTime.getTime().getTime()>new Date().getTime()){
	    		smsBody.setSendFlag(0);
	    	}else{
	    		smsBody.setSendFlag(1);
	    	}
	    	smsBodyDao.save(smsBody);
			
			
			if(!"".equals(userListIds)){
				if(userListIds.endsWith(",")){
		    		userListIds = userListIds.substring(0,userListIds.length()-1);
		    	}
				String[] uuids = userListIds.split(",");
				TeePerson p = null;
				StringBuffer sb = new StringBuffer("");
				
				List persons = new ArrayList();//人员
				
				for(String uuid : uuids){
					TeeSms sms = new TeeSms();
					if(TeeStringUtil.getInteger(uuid, 0)==0){
						continue;
					}
					sms.setToId(TeeStringUtil.getInteger(uuid, 0));
					sms.setSmsBody(smsBody);
					sms.setDeleteFlag(0);
					sms.setRemindTime(sendTime.getTime());
					smsDao.save(sms);
					
					p = personDao.get(TeeStringUtil.getInteger(uuid, 0));
					persons.add(p);
					sb.append(p.getUserId()+",");
					
					//设置用户消息标记
					RedisClient.getInstance().set("USER_SMS_FLAG_"+p.getUserId().hashCode(), "1");
					
//					if(smsBody.getSendFlag()==1){//如果是即时发送，则发送短消息socket
//						//发送socket消息
//						remind(userId,p.getUserId(),content,remindUrl,moduleNo);
//					}else{//非即时发送，推送到任务中心
						//创建任务实例
//						TeeQuartzTask quartzTask = new TeeQuartzTask();
//						quartzTask.setContent(content);
//						quartzTask.setExpDesc("一次性定时发送");
//						quartzTask.setType(TeeQuartzTypes.ONCE);
//						quartzTask.setFrom(userId);
//						quartzTask.setTo(targetPerson.getUserId());
//						quartzTask.setModelId(moduleId);
//						quartzTask.setModelNo(moduleNo);
//						quartzTask.setUrl(remindUrl);
//						quartzTask.setExp(TeeQuartzUtils.getOnceQuartzExpression(sendTime));
//						MessagePusher.pushQuartz(quartzTask);
//					}
				}
				
				if(sb.length()!=0 && sb.lastIndexOf(",")==sb.length()-1){
					sb.deleteCharAt(sb.length()-1);
				}
				
				remind(userId,sb.toString(),content,remindUrl,remindUrl1,moduleNo,userName);
				
				sendDingMsg(persons.toArray(), moduleNo, remindUrl,remindUrl1,content);
				sendWeiXinMsg(persons.toArray(), moduleNo, remindUrl,remindUrl1,content);
			}else if(userList!=null&&userList.size()>0){
				StringBuffer sb = new StringBuffer("");
				
				for(TeePerson user : userList){
					TeeSms sms = new TeeSms();
					sms.setToId(TeeStringUtil.getInteger(user.getUuid(), 0));
					sms.setSmsBody(smsBody);
					sms.setDeleteFlag(0);
					sms.setRemindTime(sendTime.getTime());
					smsDao.save(sms);
					
					
					sb.append(user.getUserId()+",");
					
					//设置用户消息标记
					RedisClient.getInstance().set("USER_SMS_FLAG_"+user.getUserId().hashCode(), "1");
					
//					if(smsBody.getSendFlag()==1){//如果是即时发送，则发送短消息socket
						//发送socket消息
//						remind(userId,user.getUserId(),content,remindUrl,moduleNo);
//					}else{//非即时发送，推送到任务中心
//						TeeQuartzTask quartzTask = new TeeQuartzTask();
//						quartzTask.setContent(content);
//						quartzTask.setExpDesc("一次性定时发送");
//						quartzTask.setType(TeeQuartzTypes.ONCE);
//						quartzTask.setFrom(userId);
//						quartzTask.setTo(user.getUserId());
//						quartzTask.setModelId(moduleId);
//						quartzTask.setModelNo(moduleNo);
//						quartzTask.setUrl(remindUrl);
//						quartzTask.setExp(TeeQuartzUtils.getOnceQuartzExpression(sendTime));
//						MessagePusher.pushQuartz(quartzTask);
//					}
				}
				
				if(sb.lastIndexOf(",")==sb.length()-1){
					sb.deleteCharAt(sb.length()-1);
				}
				
				remind(userId,sb.toString(),content,remindUrl,remindUrl1,moduleNo,userName);
				
				sendDingMsg(userList.toArray(), moduleNo, remindUrl,remindUrl1,content);
				sendWeiXinMsg(userList.toArray(), moduleNo, remindUrl,remindUrl1,content);
			}else if(userSet!=null&&userSet.size()>0){
				
				StringBuffer sb = new StringBuffer("");
				
				for(TeePerson user : userSet){
					TeeSms sms = new TeeSms();
					sms.setToId(TeeStringUtil.getInteger(user.getUuid(), 0));
					sms.setSmsBody(smsBody);
					sms.setDeleteFlag(0);
					sms.setRemindTime(sendTime.getTime());
					smsDao.save(sms);
					
					sb.append(user.getUserId()+",");
					
					//设置用户消息标记
					RedisClient.getInstance().set("USER_SMS_FLAG_"+user.getUserId().hashCode(), "1");
					
//					if(smsBody.getSendFlag()==1){//如果是即时发送，则发送短消息socket
						//发送socket消息
//						remind(userId,user.getUserId(),content,remindUrl,moduleNo);
//					}else{//非即时发送，推送到任务中心
//						TeeQuartzTask quartzTask = new TeeQuartzTask();
//						quartzTask.setContent(content);
//						quartzTask.setExpDesc("一次性定时发送");
//						quartzTask.setType(TeeQuartzTypes.ONCE);
//						quartzTask.setFrom(userId);
//						quartzTask.setTo(user.getUserId());
//						quartzTask.setModelId(moduleId);
//						quartzTask.setModelNo(moduleNo);
//						quartzTask.setUrl(remindUrl);
//						quartzTask.setExp(TeeQuartzUtils.getOnceQuartzExpression(sendTime));
//						MessagePusher.pushQuartz(quartzTask);
//					}
				}
				
				if(sb.lastIndexOf(",")==sb.length()-1){
					sb.deleteCharAt(sb.length()-1);
				}
				
				remind(userId,sb.toString(),content,remindUrl,remindUrl1,moduleNo,userName);
				
				sendDingMsg(userSet.toArray(), moduleNo, remindUrl,remindUrl1,content);
				sendWeiXinMsg(userSet.toArray(), moduleNo, remindUrl,remindUrl1,content);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}
	}
	
	/**
	 * 提醒接口
	 * @param uuid
	 */
	public void remind(String userId,String toUserId,String content,String url,String url1,String moduleNo,String userName){
		SmsSender sender = new SmsSender();
		sender.userId = userId;
		sender.toUserId = toUserId;
		sender.content = content;
		sender.url = url;
		sender.url1 = url1;
		sender.no =moduleNo;
		sender.userName =userName;
		sender.run();
	}
	
	/**
	 * 发送钉钉消息
	 * @param users
	 * @param moduleNo
	 * @param url
	 */
	private void sendDingMsg(Object users[],String moduleNo,String url,String url1,String content){
		String agentId = null;
		String sp[] = url.split("\\?");
		String dingUrl = TeeSysProps.getString("DD_URL");
		String color = "";
		if(TeeUtility.isNullorEmpty(dingUrl)){
			return;
		}
		
		agentId = TeeModuleConst.MODULE_SORT_DD_APP_ID.get(moduleNo);
		if("019".equals(moduleNo)){//邮件
			url = dingUrl+"/system/mobile/phone/email/emailInfo.jsp?"+sp[1];
			color = "FF009ebc";
		}else if("021".equals(moduleNo)){//通知公告
			if(url.indexOf("readNotify.jsp")!=-1){//公告查看
				url = dingUrl+"/system/mobile/phone/notify/notifyInfo.jsp?"+sp[1];
			}
			color = "FF1bbcee";
		}else if("020".equals(moduleNo)){//新闻
			url = dingUrl+"/system/mobile/phone/news/newsInfo.jsp?"+sp[1];
			color = "FF578ae5";
		}else if("006".equals(moduleNo)){//工作流
			if(url.indexOf("/prcs/index.jsp")!=-1){//工作流办理
				url = dingUrl+"/system/mobile/phone/workflow/prcs/form.jsp?"+sp[1];
			}else if(url.indexOf("/print/index.jsp")!=-1){//工作流预览
				url = dingUrl+"/system/core/workflow/flowrun/print/print.jsp?"+sp[1];
			}
			color = "FFfe654b";
		}else if("018".equals(moduleNo)){//工作日志
			url = dingUrl+"/system/mobile/phone/diary/diaryInfo.jsp?"+sp[1];
			color = "FFfd8c34";
		}else if("043".equals(moduleNo)){//计划管理
			url = dingUrl+"/system/mobile/phone/schedule/detail.jsp?"+sp[1];
			color = "FFe16ccc";
		}else if("035".equals(moduleNo)){//任务中心
			url = dingUrl+"/system/mobile/phone/task/detail.jsp?"+sp[1];
			color = "FFc9adf8";
		}else if("050".equals(moduleNo)){//讨论区
			url = dingUrl+"/system/mobile/phone/topic/detail.jsp?"+sp[1];
			color = "FF53ba53";
		}else{
			url = dingUrl+url1;
			color = TeeStringUtil.getString(TeeModuleConst.MODULE_SORT_COLOR.get(moduleNo),"FF53ba53");
		}
		
		if(agentId!=null && !"".equals(agentId)  && !TeeUtility.isNullorEmpty(dingUrl)){
			StringBuffer tousers = new StringBuffer();
			for(int i=0;i<users.length;i++){
				if(TeeStringUtil.getString(((TeePerson)users[i]).getGsbUserId()).equals("")){
					continue;
				}
				tousers.append(((TeePerson)users[i]).getGsbUserId());
				if(i!=users.length-1){
					tousers.append("|");
				}
			}
			
			
			LightAppMessageDelivery delivery = new LightAppMessageDelivery(tousers.toString(), "", agentId);
			OAMessage msg = new OAMessage();
			msg.head = new Head();
			msg.head.bgcolor = color;
			msg.head.text = TeeModuleConst.MODULE_SORT_TYPE.get(moduleNo);
			msg.body = new Body();
			msg.body.content = content;
			BASE64Encoder base64Encoder = new BASE64Encoder();
			msg.message_url = TeeSysProps.getString("DD_URL")+"/dingding/sso.action?url="+base64Encoder.encode(url.getBytes()).replace("\r\n", "");
			delivery.withMessage(msg);
			
			//创建钉钉发送实例
			Runnable dingDingSendProcessor = new TeeDingDingSendProcessor(delivery);
			//获取钉钉线程池，并且该任务放入线程池执行
			TeeDingDingThreadPool.getInstance().execute(dingDingSendProcessor);
		}
		
	}
	
	
	/**
	 * 发送微信消息，暂时不带url
	 * @param users
	 * @param moduleNo
	 * @param url
	 */
	private void sendWeiXinMsg(Object users[],String moduleNo,String url,String url1,String content){
		String agentId = null;
		String sp[] = url.split("\\?");
		String appId = TeeModuleConst.MODULE_SORT_WX_APP_ID.get(moduleNo);
		String accessToken = AccessToken.getAccessTokenInstance().getAppToken().get(appId);
		String weixinUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+accessToken;
		String dingUrl = TeeSysProps.getString("WEIXIN_URL");
		String color = "";
		if(TeeUtility.isNullorEmpty(dingUrl)){
			return;
		}
		
		agentId = TeeModuleConst.MODULE_SORT_WX_APP_ID.get(moduleNo);
		agentId = TeeStringUtil.getString(agentId).trim();
		if("019".equals(moduleNo)){//邮件
			
			url = dingUrl+"/system/mobile/phone/email/emailInfo.jsp?"+sp[1];
			color = "FF009ebc";
		}else if("021".equals(moduleNo)){//通知公告
			if(url.indexOf("readNotify.jsp")!=-1){//公告查看
				url = dingUrl+"/system/mobile/phone/notify/notifyInfo.jsp?"+sp[1];
			}
			color = "FF1bbcee";
		}else if("020".equals(moduleNo)){//新闻
			url = dingUrl+"/system/mobile/phone/news/newsInfo.jsp?"+sp[1];
			color = "FF578ae5";
		}else if("006".equals(moduleNo)){//工作流
			if(url.indexOf("/prcs/index.jsp")!=-1){//工作流办理
				url = dingUrl+"/system/mobile/phone/workflow/prcs/form.jsp?"+sp[1];
			}else if(url.indexOf("/print/index.jsp")!=-1){//工作流预览
				url = dingUrl+"/system/core/workflow/flowrun/print/print.jsp?"+sp[1];
			}
			color = "FFfe654b";
		}else if("018".equals(moduleNo)){//工作日志
			url = dingUrl+"/system/mobile/phone/diary/diaryInfo.jsp?"+sp[1];
			color = "FFfd8c34";
		}else if("043".equals(moduleNo)){//计划管理
			url = dingUrl+"/system/mobile/phone/schedule/detail.jsp?"+sp[1];
			color = "FFe16ccc";
		}else if("035".equals(moduleNo)){//任务中心
			url = dingUrl+"/system/mobile/phone/task/detail.jsp?"+sp[1];
			color = "FFc9adf8";
		}else if("050".equals(moduleNo)){//讨论区
			url = dingUrl+"/system/mobile/phone/topic/detail.jsp?"+sp[1];
			color = "FF53ba53";
		}else{
			url = dingUrl+url1;
			color = "FF53ba53";
		}
		
		if(agentId!=null && !"".equals(agentId)  && !TeeUtility.isNullorEmpty(accessToken)){
			StringBuffer tousers = new StringBuffer();
			for(int i=0;i<users.length;i++){
				if(TeeStringUtil.getString(((TeePerson)users[i]).getGsbPassword()).equals("")){
					continue;
				}
				tousers.append(((TeePerson)users[i]).getGsbPassword());
				if(i!=users.length-1){
					tousers.append("|");
				}
			}
			BASE64Encoder base64Encoder = new BASE64Encoder();
			String redirectUrl = dingUrl+"/weixin/dosso.action?url="+base64Encoder.encode(url.getBytes()).replace("\r\n", "")+"&agent_id="+agentId;
			
			String outstr = "{\"touser\": \""+tousers.toString()+"\",\"msgtype\": \"news\",\"agentid\": \""+agentId+"\",\"news\":{\"articles\": [{\"title\": \""+content+"\",\"description\": \""+content+"\",\"url\": \""+redirectUrl+"\"}]},\"safe\":\"0\"}";
			/*
			synchronized (TeeAppPushTimer.wxMsgBuffQueue) {
				TeeAppPushTimer.wxMsgBuffQueue.add(outstr);
			}*/
			
			//创建微信发送实例
			Runnable weiXinSendProcessor = new TeeWeiXinSendProcessor(outstr,accessToken,weixinUrl);
			//获取微信线程池，并且该任务放入线程池执行
			TeeDingDingThreadPool.getInstance().execute(weiXinSendProcessor);
		}
	}
	
	
	public class SmsSender extends Thread{
		String userId;
		String toUserId;
		String content;
		String url;
		String url1;
		String no;
		String userName;
		@Override
		public void run(){
			Map map = new HashMap();
			map.put("content", content);
			map.put("url", url);
			map.put("url1", url1);
			map.put("t", "50");
			map.put("time", TeeDateUtil.format(new Date()));
			map.put("no", no);
			map.put("to", toUserId);
			map.put("from", userId);
			map.put("userName", userName);
			MessagePusher.push2Im(map);
		}
	}

}
