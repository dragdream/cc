package com.tianee.oa.subsys.cowork.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.base.calendar.model.TeeFullCalendarModel;
import com.tianee.oa.core.base.email.dao.TeeMailDao;
import com.tianee.oa.core.base.vehicle.bean.TeeVehicleUsage;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.cowork.bean.TeeCoWorkTask;
import com.tianee.oa.subsys.cowork.bean.TeeCoWorkTaskDoc;
import com.tianee.oa.subsys.cowork.bean.TeeCoWorkTaskEvent;
import com.tianee.oa.subsys.cowork.bean.TeeTaskSchedule;
import com.tianee.oa.subsys.cowork.model.TeeCoWorkOperModel;
import com.tianee.oa.subsys.cowork.model.TeeCoWorkTaskDocModel;
import com.tianee.oa.subsys.cowork.model.TeeCoWorkTaskModel;
import com.tianee.oa.subsys.cowork.model.TeeTaskEventModel;
import com.tianee.oa.subsys.cowork.model.TeenumModel;
import com.tianee.oa.subsys.schedule.bean.TeeSchedule;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfo;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class TeeCoWorkService extends TeeBaseService{
	
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private TeeSmsManager smsManager;
	@Autowired
	private TeeMailDao mailDao;
	/**
	 * 布置任务
	 * @param coWorkTaskModel
	 * @return
	 */
	public TeeCoWorkTask addTaskService(TeeCoWorkTaskModel coWorkTaskModel){
		TeeCoWorkTask coWorkTask = new TeeCoWorkTask();
		Calendar start=TeeDateUtil.parseCalendar(coWorkTaskModel.getStartTimeDesc());
		Calendar end=TeeDateUtil.parseCalendar(coWorkTaskModel.getEndTimeDesc());
		
		taskModelToEntity(coWorkTaskModel,coWorkTask);
		coWorkTask.setStartTime(start);
		coWorkTask.setEndTime(end);
		coWorkTask.setRangeTimes(coWorkTaskModel.getRangeTimes());
		TeePerson loginPerson = (TeePerson) 
				simpleDaoSupport.get(TeePerson.class, TeeRequestInfoContext.getRequestInfo().getUserSid());
		
		if(coWorkTask.getAuditor()!=null){//需要进行审批
			coWorkTask.setStatus(1);//等待审批
		}else{//不用审批的话
			coWorkTask.setStatus(0);//等待接收
		}
		
		simpleDaoSupport.save(coWorkTask);
		
		//设置路径
		TeeCoWorkTask parentTask = (TeeCoWorkTask) simpleDaoSupport.get(TeeCoWorkTask.class,coWorkTaskModel.getParentTaskId());
		if(parentTask!=null){
			coWorkTask.setTaskPath(parentTask.getTaskPath()+"/"+coWorkTask.getSid()+".path");
		}else{
			coWorkTask.setTaskPath("/"+coWorkTask.getSid()+".path");
		}
		
		simpleDaoSupport.update(coWorkTask);
		
		TeePerson loginUser = personService.selectByUuid(coWorkTaskModel.getCreateUserId());
		
		//短消息处理
		Map requestData = new HashMap();
		requestData.put("moduleNo", "035");
		if(coWorkTask.getAuditor()!=null){//需要进行审批
			requestData.put("content", "您有需要审批的任务["+coWorkTask.getTaskTitle()+"]，您是任务的审批人，请及时处理。");
			requestData.put("userListIds", ""+coWorkTask.getAuditor().getUuid());
			requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+coWorkTask.getSid());
			//手机端事务提醒
			requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+coWorkTask.getSid());
			smsManager.sendSms(requestData, loginPerson);
			
		}else{//不用审批的话，通知负责人领取
//			//通知参与人
//			requestData.put("content", loginPerson.getUserName()+"发起一个协同任务["+coWorkTask.getTaskTitle()+"]，您是任务的“参与人”，请及时办理。");
//			requestData.put("userSet", coWorkTask.getJoiners());
//			requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+coWorkTask.getSid());
//			smsManager.sendSms(requestData, loginPerson);
			
			//通知负责人
			requestData.put("userListIds", coWorkTask.getCharger().getUuid());
			requestData.put("content", loginPerson.getUserName()+"发起一个协同任务["+coWorkTask.getTaskTitle()+"]，您是任务的“负责人”，请及时办理。");
			requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+coWorkTask.getSid());
			//手机事务提醒
			requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+coWorkTask.getSid());
			smsManager.sendSms(requestData, loginPerson);
			
			//同时通知参与人
			if(!TeeUtility.isNullorEmpty(coWorkTaskModel.getJoinerIds())){
				requestData.put("content", loginPerson.getUserName()+"发起一个协同任务["+coWorkTask.getTaskTitle()+"]，您是任务的“参与人”，请及时办理。");
				requestData.put("userListIds",coWorkTaskModel.getJoinerIds());
				requestData.put("moduleNo", "035");
				requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+coWorkTask.getSid());
				//手机端事务提醒
				requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+coWorkTask.getSid());
				smsManager.sendSms(requestData, loginPerson);
			}
			
		}
		
		//保存计划与任务的关系
		if(coWorkTaskModel.getScheduleId()!=null && !"".equals(coWorkTaskModel.getScheduleId())){
			TeeSchedule schedule = (TeeSchedule) simpleDaoSupport.get(TeeSchedule.class, coWorkTaskModel.getScheduleId());
			TeeTaskSchedule taskSchedule = new TeeTaskSchedule();
			taskSchedule.setSchedule(schedule);
			taskSchedule.setTask(coWorkTask);
			simpleDaoSupport.save(taskSchedule);
		}
		
		
		//记录日志
		TeeCoWorkTaskEvent event = new TeeCoWorkTaskEvent();
		event.setTitle("布置了一个任务");
		event.setCreateTime(TeeDateUtil.format(new Date()));
		event.setCreateUserId(loginUser.getUuid());
		event.setCreateUserName(loginUser.getUserName());
		event.setTask(coWorkTask);
		event.setType(1);
		event.setTypeDesc("布置");
		simpleDaoSupport.save(event);
		
		return coWorkTask;
	}
	
	/**
	 * 修改任务
	 * @param coWorkTaskModel
	 * @return
	 */
	public TeeCoWorkTask editTaskService(TeeCoWorkTaskModel coWorkTaskModel){
		TeeCoWorkTask origin = (TeeCoWorkTask) simpleDaoSupport.get(TeeCoWorkTask.class, coWorkTaskModel.getSid());
		Calendar crTime = origin.getCreateTime();
		taskModelToEntity(coWorkTaskModel,origin);
		
		if(origin.getAuditor()!=null){//需要进行审批
			origin.setStatus(1);//等待审批
		}else{//不用审批的话
			origin.setStatus(0);//等待接收
		}
		origin.setCreateTime(crTime);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Calendar start=null;
		Calendar end=null;
		try {
			Date date1=sdf.parse(coWorkTaskModel.getStartTimeDesc());
			Date date2=sdf.parse(coWorkTaskModel.getEndTimeDesc());
			start=TeeDateUtil.parseCalendar(sdf.format(date1));
			end=TeeDateUtil.parseCalendar(sdf.format(date2));
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		origin.setStartTime(start);
		origin.setEndTime(end);
		//获取原始任务数据
		simpleDaoSupport.update(origin);
		
		TeePerson loginUser = personService.selectByUuid(coWorkTaskModel.getCreateUserId());
		
		//短消息处理
		Map requestData = new HashMap();
		requestData.put("moduleNo", "035");
		if(origin.getAuditor()!=null){//需要进行审批
			requestData.put("content", "您有需要审批的任务["+origin.getTaskTitle()+"]，您是任务的审批人，请及时处理。");
			requestData.put("userListIds", ""+origin.getAuditor().getUuid());
			requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+origin.getSid());
			//手机端事务提醒
			requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+origin.getSid());
			smsManager.sendSms(requestData, loginUser);
			
		}else{//不用审批的话，通知负责人领取
//					//通知参与人
//					requestData.put("content", loginPerson.getUserName()+"发起一个协同任务["+coWorkTask.getTaskTitle()+"]，您是任务的“参与人”，请及时办理。");
//					requestData.put("userSet", coWorkTask.getJoiners());
//					requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+coWorkTask.getSid());
//					smsManager.sendSms(requestData, loginPerson);
			
			//通知负责人
			requestData.put("userListIds", origin.getCharger().getUuid());
			requestData.put("content", loginUser.getUserName()+"发起一个协同任务["+origin.getTaskTitle()+"]，您是任务的“负责人”，请及时办理。");
			requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+origin.getSid());
			//手机端事务提醒
			requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+origin.getSid());
			smsManager.sendSms(requestData, loginUser);
			
		}
		
		
		//记录日志
		TeeCoWorkTaskEvent event = new TeeCoWorkTaskEvent();
		event.setTitle("修改任务信息");
		event.setCreateTime(TeeDateUtil.format(new Date()));
		event.setCreateUserId(loginUser.getUuid());
		event.setCreateUserName(loginUser.getUserName());
		event.setTask(origin);
		event.setType(12);
		event.setTypeDesc("修改");
		simpleDaoSupport.save(event);
		
		return origin;
	}
	
	/**
	 * 获取任务信息
	 * @param coWorkTaskModel
	 * @return
	 */
	public TeeCoWorkTaskModel getTaskInfo(TeeCoWorkOperModel operModel){
		TeeCoWorkTaskModel coWorkTask = new TeeCoWorkTaskModel();
		//System.out.println(operModel.getTaskId());
		TeeCoWorkTask task = (TeeCoWorkTask) simpleDaoSupport.get(TeeCoWorkTask.class, operModel.getTaskId());
		
		taskEntityToModel(task,coWorkTask);
		//处理时间
		Calendar calendat1 = task.getStartTime();
		Calendar calendat2 = task.getEndTime();
		
		Calendar calendat3 = task.getCreateTime();
		Calendar calendat4= task.getRelStartTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		//coWorkTask.setStartTimeDesc(sdf.format(task.getStartTime().getTime()));
		//System.out.println(coWorkTask.getSid());
		coWorkTask.setStartTimeDesc(sdf.format(calendat1.getTime()));
		coWorkTask.setEndTimeDesc(sdf.format(calendat2.getTime()));
		coWorkTask.setCreateTimeDesc(sdf.format(calendat3.getTime()));
		if(task.getRelStartTime()!=null){
			coWorkTask.setRelStartTimeDesc(sdf.format(calendat4.getTime()));
		};
		
		coWorkTask.setRangeTimes(task.getRangeTimes());
		//System.out.println(coWorkTask.getRangeTimes());
		
		return coWorkTask;
	}
	
	/**
	 * 删除任务
	 * @param taskId
	 * @return
	 */
	public TeeCoWorkTask deleteTaskService(int taskId){
		TeeCoWorkTask coWorkTask = (TeeCoWorkTask) simpleDaoSupport.get(TeeCoWorkTask.class, taskId);
		//先获取路径下所有任务
		List<TeeCoWorkTask> list = simpleDaoSupport.executeQuery("from TeeCoWorkTask where taskPath like '"+coWorkTask.getTaskPath()+"%'", null);
		for(TeeCoWorkTask task:list){
			task.getJoiners().clear();
			simpleDaoSupport.executeUpdate("delete from TeeRunTaskRel where task.sid=?", new Object[]{task.getSid()});
			simpleDaoSupport.executeUpdate("delete from TeeCoWorkTaskEvent where task.sid=?", new Object[]{task.getSid()});
			simpleDaoSupport.executeUpdate("delete from TeeCoWorkTaskDoc where task.sid=?", new Object[]{task.getSid()});
			simpleDaoSupport.deleteByObj(task);
		}
		return coWorkTask;
	}
	
	/**
	 * 撤销
	 * @param operModel
	 */
	public void redo(TeeCoWorkOperModel operModel){
		//获取原始任务数据
		TeeCoWorkTask origin = (TeeCoWorkTask) simpleDaoSupport.get(TeeCoWorkTask.class, operModel.getTaskId());
		origin.setStatus(7);
		simpleDaoSupport.update(origin);
		
		TeePerson loginPerson = 
				(TeePerson) simpleDaoSupport.get(TeePerson.class, TeeRequestInfoContext.getRequestInfo().getUserSid());
		
		//短消息处理
		Map requestData = new HashMap();
		requestData.put("moduleNo", "035");
		requestData.put("content", loginPerson.getUserName()+"撤销了您委派的任务["+origin.getTaskTitle()+"]，撤销原因："+operModel.getRemark());
		requestData.put("userListIds", ""+origin.getCreateUser().getUuid());
		requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+origin.getSid());
		
		//手机端事务提醒
		requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+origin.getSid());
		smsManager.sendSms(requestData, loginPerson);
		
		//发送给审批人
		if(origin.getAuditor()!=null){
			requestData.clear();
			requestData.put("moduleNo", "035");
			requestData.put("content", loginPerson.getUserName()+"撤销了您审批过的任务["+origin.getTaskTitle()+"]，撤销原因："+operModel.getRemark());
			requestData.put("userListIds", ""+origin.getAuditor().getUuid());
			requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+origin.getSid());
			//手机端进行事务提醒
			requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+origin.getSid());
			smsManager.sendSms(requestData, loginPerson);
		}
		
		
		//记录日志
		TeeCoWorkTaskEvent event = new TeeCoWorkTaskEvent();
		event.setTitle("撤销任务");
		event.setContent(operModel.getRemark());
		event.setCreateTime(TeeDateUtil.format(new Date()));
		event.setCreateUserId(origin.getCreateUser().getUuid());
		event.setCreateUserName(origin.getCreateUser().getUserName());
		event.setTask(origin);
		event.setType(5);
		event.setTypeDesc("撤销");
		simpleDaoSupport.save(event);
	}
	
	/**
	 * 督办
	 * @param operModel
	 */
	public void urge(TeeCoWorkOperModel operModel){
		//获取原始任务数据
		TeeCoWorkTask origin = (TeeCoWorkTask) simpleDaoSupport.get(TeeCoWorkTask.class, operModel.getTaskId());
		
		TeePerson loginPerson = 
				(TeePerson) simpleDaoSupport.get(TeePerson.class, TeeRequestInfoContext.getRequestInfo().getUserSid());
		//短消息处理
		Map requestData = new HashMap();
		requestData.put("content", loginPerson.getUserName()+"给您发送了一条督办请求，相关任务["+origin.getTaskTitle()+"]，督办内容："+operModel.getRemark());
		requestData.put("userListIds", ""+origin.getCharger().getUuid());
		requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+origin.getSid());
		//手机端事务提醒
		requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+origin.getSid());
		requestData.put("moduleNo", "035");
		smsManager.sendSms(requestData, loginPerson);
		
		//记录日志
		TeeCoWorkTaskEvent event = new TeeCoWorkTaskEvent();
		event.setTitle("督办任务");
		event.setContent(operModel.getRemark());
		event.setCreateTime(TeeDateUtil.format(new Date()));
		event.setCreateUserId(origin.getCreateUser().getUuid());
		event.setCreateUserName(origin.getCreateUser().getUserName());
		event.setTask(origin);
		event.setType(6);
		event.setTypeDesc("督办");
		simpleDaoSupport.save(event);
	}
	
	/**
	 * 延期
	 * @param operModel
	 */
	public void delay(TeeCoWorkOperModel operModel){
		//获取原始任务数据
		TeeCoWorkTask origin = (TeeCoWorkTask) simpleDaoSupport.get(TeeCoWorkTask.class, operModel.getTaskId());
		Calendar c = TeeDateUtil.parseCalendar(operModel.getDelayTime());
		origin.setEndTime(c);
		simpleDaoSupport.update(origin);
		
		TeePerson loginPerson = 
				(TeePerson) simpleDaoSupport.get(TeePerson.class, TeeRequestInfoContext.getRequestInfo().getUserSid());
		//给创建人发
		Map requestData = new HashMap();
		requestData.put("moduleNo", "035");
		requestData.put("content", "您发起的协同任务["+origin.getTaskTitle()+"]被延期，延期原因："+operModel.getRemark());
		requestData.put("userListIds", ""+origin.getCreateUser().getUuid());
		requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+origin.getSid());
		//手机端事务提醒
		requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+origin.getSid());
		smsManager.sendSms(requestData, loginPerson);
		
		//记录日志
		TeeCoWorkTaskEvent event = new TeeCoWorkTaskEvent();
		event.setTitle("任务延期");
		event.setContent(operModel.getRemark());
		event.setCreateTime(TeeDateUtil.format(new Date()));
		event.setCreateUserId(origin.getCreateUser().getUuid());
		event.setCreateUserName(origin.getCreateUser().getUserName());
		event.setTask(origin);
		event.setType(7);
		event.setTypeDesc("延期");
		simpleDaoSupport.save(event);
	}
	
	/**
	 * 申请失败
	 * @param operModel
	 */
	public void failed(TeeCoWorkOperModel operModel){
		//获取原始任务数据
		TeeCoWorkTask origin = (TeeCoWorkTask) simpleDaoSupport.get(TeeCoWorkTask.class, operModel.getTaskId());
		
		TeePerson loginPerson = 
				(TeePerson) simpleDaoSupport.get(TeePerson.class, TeeRequestInfoContext.getRequestInfo().getUserSid());
		
		//短消息处理
		Map requestData = new HashMap();
		
		//将所有关联任务置为失败
		List<TeeCoWorkTask> coWorkTask = simpleDaoSupport.find("from TeeCoWorkTask where taskPath like ?", new Object[]{origin.getTaskPath()+"%"});
		for(TeeCoWorkTask task:coWorkTask){
			task.setStatus(9);
			simpleDaoSupport.update(task);
			
			requestData.clear();
			requestData.put("moduleNo", "035");
			requestData.put("content", loginPerson.getUserName()+"将您委派的任务["+task.getTaskTitle()+"]置为失败，失败原因："+operModel.getRemark());
			requestData.put("userListIds", ""+task.getCreateUser().getUuid());
			requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+task.getSid());
			//手机端事务提醒
			requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+task.getSid());
			smsManager.sendSms(requestData, loginPerson);
			
			//发送给审批人
			if(task.getAuditor()!=null){
				requestData.clear();
				requestData.put("moduleNo", "035");
				requestData.put("content", "您审批的协同任务["+task.getTaskTitle()+"]已经失败，失败原因："+operModel.getRemark());
				requestData.put("userListIds", ""+task.getAuditor().getUuid());
				requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+task.getSid());
				//手机端事务提醒
				requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+task.getSid());
				smsManager.sendSms(requestData, loginPerson);
			}
			
			//记录日志
			TeeCoWorkTaskEvent event = new TeeCoWorkTaskEvent();
			event.setTitle("任务失败");
			event.setContent(operModel.getRemark());
			event.setCreateTime(TeeDateUtil.format(new Date()));
			event.setCreateUserId(loginPerson.getUuid());
			event.setCreateUserName(loginPerson.getUserName());
			event.setTask(task);
			event.setType(9);
			event.setTypeDesc("失败");
			simpleDaoSupport.save(event);
		}
		
	}
	
	/**
	 * 接收该任务
	 * @param operModel
	 */
	public void receive(TeeCoWorkOperModel operModel){
		//获取原始任务数据
		TeeCoWorkTask origin = (TeeCoWorkTask) simpleDaoSupport.get(TeeCoWorkTask.class, operModel.getTaskId());
		origin.setStatus(4);
		origin.setRelStartTime(Calendar.getInstance());
		simpleDaoSupport.update(origin);
		
		TeePerson loginPerson = 
				(TeePerson) simpleDaoSupport.get(TeePerson.class, TeeRequestInfoContext.getRequestInfo().getUserSid());
		
		//短消息处理
		Map requestData = new HashMap();
		requestData.put("moduleNo", "035");
		requestData.put("content", loginPerson.getUserName()+"已确认接收您委派的任务["+origin.getTaskTitle()+"]。");
		requestData.put("userListIds", ""+origin.getCreateUser().getUuid());
		requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+origin.getSid());
		//手机端事务提醒
		requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+origin.getSid());
		smsManager.sendSms(requestData, loginPerson);
		
		
		//记录日志
		TeeCoWorkTaskEvent event = new TeeCoWorkTaskEvent();
		event.setTitle("接收任务");
		event.setCreateTime(TeeDateUtil.format(new Date()));
		event.setCreateUserId(origin.getCharger().getUuid());
		event.setCreateUserName(origin.getCharger().getUserName());
		event.setTask(origin);
		event.setType(2);
		event.setTypeDesc("接收");
		simpleDaoSupport.save(event);
	}
	
	/**
	 * 拒绝接收该任务
	 * @param operModel
	 */
	public void noReceive(TeeCoWorkOperModel operModel){
		//获取原始任务数据
		TeeCoWorkTask origin = (TeeCoWorkTask) simpleDaoSupport.get(TeeCoWorkTask.class, operModel.getTaskId());
		origin.setStatus(3);
		simpleDaoSupport.update(origin);
		
		TeePerson loginPerson = 
				(TeePerson) simpleDaoSupport.get(TeePerson.class, TeeRequestInfoContext.getRequestInfo().getUserSid());
		
		//短消息处理
		Map requestData = new HashMap();
		requestData.put("moduleNo", "035");
		requestData.put("content", loginPerson.getUserName()+"拒绝接收您委派的任务["+origin.getTaskTitle()+"]");
		requestData.put("userListIds", ""+origin.getCreateUser().getUuid());
		requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+origin.getSid());
		//手机端事务提醒
		requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+origin.getSid());
		smsManager.sendSms(requestData, loginPerson);
		
		
		//记录日志
		TeeCoWorkTaskEvent event = new TeeCoWorkTaskEvent();
		event.setTitle("拒绝接收该任务");
		event.setCreateTime(TeeDateUtil.format(new Date()));
		event.setCreateUserId(origin.getCharger().getUuid());
		event.setCreateUserName(origin.getCharger().getUserName());
		event.setTask(origin);
		event.setType(2);
		event.setTypeDesc("接收");
		simpleDaoSupport.save(event);
	}
	
	/**
	 * 通过审批
	 * @param operModel
	 */
	public void pass(TeeCoWorkOperModel operModel){
		//获取原始任务数据
		TeeCoWorkTask origin = (TeeCoWorkTask) simpleDaoSupport.get(TeeCoWorkTask.class, operModel.getTaskId());
		origin.setStatus(4);
		simpleDaoSupport.update(origin);
		
		TeePerson loginPerson = 
				(TeePerson) simpleDaoSupport.get(TeePerson.class, TeeRequestInfoContext.getRequestInfo().getUserSid());
		
		//短消息处理
		Map requestData = new HashMap();
		requestData.put("moduleNo", "035");
		requestData.put("content", "您发起的协同任务["+origin.getTaskTitle()+"]已通过审批");
		requestData.put("userListIds", ""+origin.getCreateUser().getUuid());
		requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+origin.getSid());
		//手机端事务提醒
		requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+origin.getSid());
		smsManager.sendSms(requestData, loginPerson);
		
		
		//记录日志
		TeeCoWorkTaskEvent event = new TeeCoWorkTaskEvent();
		event.setTitle("审批通过");
		event.setCreateTime(TeeDateUtil.format(new Date()));
		event.setCreateUserId(origin.getAuditor().getUuid());
		event.setCreateUserName(origin.getAuditor().getUserName());
		event.setTask(origin);
		event.setType(3);
		event.setTypeDesc("审批");
		simpleDaoSupport.save(event);
	}
	
	/**
	 * 不通过审批
	 * @param operModel
	 */
	public void noPass(TeeCoWorkOperModel operModel){
		//获取原始任务数据
		TeeCoWorkTask origin = (TeeCoWorkTask) simpleDaoSupport.get(TeeCoWorkTask.class, operModel.getTaskId());
		origin.setStatus(2);
		simpleDaoSupport.update(origin);
		
		TeePerson loginPerson = 
				(TeePerson) simpleDaoSupport.get(TeePerson.class, TeeRequestInfoContext.getRequestInfo().getUserSid());
		
		//短消息处理
		Map requestData = new HashMap();
		requestData.put("moduleNo", "035");
		requestData.put("content", "您发起的协同任务["+origin.getTaskTitle()+"]未通过审批，拒绝原因："+operModel.getRemark());
		requestData.put("userListIds", ""+origin.getCreateUser().getUuid());
		requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+origin.getSid());
		//手机端事务提醒
		requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+origin.getSid());
		smsManager.sendSms(requestData, loginPerson);
		
		//记录日志
		TeeCoWorkTaskEvent event = new TeeCoWorkTaskEvent();
		event.setTitle("拒绝审批通过");
		event.setContent(operModel.getRemark());
		event.setCreateTime(TeeDateUtil.format(new Date()));
		event.setCreateUserId(origin.getAuditor().getUuid());
		event.setCreateUserName(origin.getAuditor().getUserName());
		event.setTask(origin);
		event.setType(3);
		event.setTypeDesc("审批");
		simpleDaoSupport.save(event);
	}
	
	/**
	 * 通过审核
	 * @param operModel
	 */
	public void pass1(TeeCoWorkOperModel operModel){
		//获取原始任务数据
		TeeCoWorkTask origin = (TeeCoWorkTask) simpleDaoSupport.get(TeeCoWorkTask.class, operModel.getTaskId());
		origin.setStatus(8);
		origin.setRelEndTime(Calendar.getInstance());
		//origin.setScore(operModel.getScore());
		//origin.setRelTimes(operModel.getRelTimes());
		origin.setProgress(100);
		simpleDaoSupport.update(origin);
		
		TeePerson loginPerson = 
				(TeePerson) simpleDaoSupport.get(TeePerson.class, TeeRequestInfoContext.getRequestInfo().getUserSid());
		
		//短消息处理
		Map requestData = new HashMap();
		
		//将所有子任务置为成功
		List<TeeCoWorkTask> coWorkTask = simpleDaoSupport.find("from TeeCoWorkTask where taskPath like ? and sid!=?", new Object[]{origin.getTaskPath()+"%",origin.getSid()});
		for(TeeCoWorkTask task:coWorkTask){
			task.setStatus(8);
			task.setRelEndTime(Calendar.getInstance());
			task.setRelTimes(operModel.getRelTimes());
			task.setProgress(100);
			simpleDaoSupport.update(task);
			
			requestData.clear();
			//给负责人发
			requestData.put("moduleNo", "035");
			requestData.put("content", "父级任务["+origin.getTaskTitle()+"]已完成");
			requestData.put("userListIds", ""+task.getCharger().getUuid());
			requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+task.getSid());
			//手机端事务提醒
			requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+task.getSid());
			smsManager.sendSms(requestData, loginPerson);
			
			//发送给审批人
			if(task.getAuditor()!=null){
				requestData.clear();
				requestData.put("moduleNo", "035");
				requestData.put("content", "父级任务["+origin.getTaskTitle()+"]已完成");
				requestData.put("userListIds", ""+task.getAuditor().getUuid());
				requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+task.getSid());
				//手机端事务提醒
				requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+task.getSid());
				smsManager.sendSms(requestData, loginPerson);
			}
					
			//记录日志
			TeeCoWorkTaskEvent event = new TeeCoWorkTaskEvent();
			event.setTitle("父级任务已完成");
			event.setCreateTime(TeeDateUtil.format(new Date()));
			event.setCreateUserId(task.getCreateUser().getUuid());
			event.setCreateUserName(task.getCreateUser().getUserName());
			event.setTask(task);
			event.setType(4);
			event.setTypeDesc("审核");
			simpleDaoSupport.save(event);
		}
		
		
		//给负责人发
		requestData.put("moduleNo", "035");
		requestData.put("content", "您完成的协同任务["+origin.getTaskTitle()+"]已通过审核。");
		requestData.put("userListIds", ""+origin.getCharger().getUuid());
		requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+origin.getSid());
		//手机端事务提醒
		requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+origin.getSid());
		smsManager.sendSms(requestData, loginPerson);
		
		//发送给审批人
		if(origin.getAuditor()!=null){
			requestData.clear();
			requestData.put("moduleNo", "035");
			requestData.put("content", "您审批的协同任务["+origin.getTaskTitle()+"]已通过审核。");
			requestData.put("userListIds", ""+origin.getAuditor().getUuid());
			requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+origin.getSid());
			//手机端事务提醒
			requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+origin.getSid());
			smsManager.sendSms(requestData, loginPerson);
		}
				
		//记录日志
		TeeCoWorkTaskEvent event = new TeeCoWorkTaskEvent();
		event.setTitle("审核通过，该任务已完成");
		event.setCreateTime(TeeDateUtil.format(new Date()));
		event.setCreateUserId(origin.getCreateUser().getUuid());
		event.setCreateUserName(origin.getCreateUser().getUserName());
		event.setTask(origin);
		event.setType(4);
		event.setTypeDesc("审核");
		simpleDaoSupport.save(event);
	}
	
	/**
	 * 不通过审核
	 * @param operModel
	 */
	public void noPass1(TeeCoWorkOperModel operModel){
		//获取原始任务数据
		TeeCoWorkTask origin = (TeeCoWorkTask) simpleDaoSupport.get(TeeCoWorkTask.class, operModel.getTaskId());
		origin.setStatus(6);
		simpleDaoSupport.update(origin);
		
		TeePerson loginPerson = 
				(TeePerson) simpleDaoSupport.get(TeePerson.class, TeeRequestInfoContext.getRequestInfo().getUserSid());
		
		//给负责人发
		Map requestData = new HashMap();
		requestData.put("moduleNo", "035");
		requestData.put("content", "您完成的协同任务["+origin.getTaskTitle()+"]未通过审核，请联系任务布置人。");
		requestData.put("userListIds", ""+origin.getCharger().getUuid());
		requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+origin.getSid());
		//手机端事务提醒
		requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+origin.getSid());
		smsManager.sendSms(requestData, loginPerson);
		
		//发送给审批人
		if(origin.getAuditor()!=null){
			requestData.clear();
			requestData.put("moduleNo", "035");
			requestData.put("content", "您审批的协同任务["+origin.getTaskTitle()+"]未通过审核，请联系任务布置人。");
			requestData.put("userListIds", ""+origin.getAuditor().getUuid());
			requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+origin.getSid());
			//手机端事务提醒
			requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+origin.getSid());
			smsManager.sendSms(requestData, loginPerson);
		}
		
		//记录日志
		TeeCoWorkTaskEvent event = new TeeCoWorkTaskEvent();
		event.setTitle("审核不通过");
		event.setCreateTime(TeeDateUtil.format(new Date()));
		event.setCreateUserId(origin.getCreateUser().getUuid());
		event.setCreateUserName(origin.getCreateUser().getUserName());
		event.setTask(origin);
		event.setType(4);
		event.setTypeDesc("审核");
		simpleDaoSupport.save(event);
	}
	
	/**
	 * 完成任务
	 * @param operModel
	 */
	public void finish(TeeCoWorkOperModel operModel){
		//获取原始任务数据
		TeeCoWorkTask origin = (TeeCoWorkTask) simpleDaoSupport.get(TeeCoWorkTask.class, operModel.getTaskId());
		origin.setStatus(5);
		origin.setScore(operModel.getScore());
		origin.setRelTimes(operModel.getRelTimes());
		simpleDaoSupport.update(origin);
		
		
		TeePerson loginPerson = 
				(TeePerson) simpleDaoSupport.get(TeePerson.class, TeeRequestInfoContext.getRequestInfo().getUserSid());
		
		//短消息处理
		Map requestData = new HashMap();
		requestData.put("moduleNo", "035");
		requestData.put("content", loginPerson.getUserName()+" 申请完成您委派的协同任务["+origin.getTaskTitle()+"] 评分："+operModel.getScore()+"，确认工时："+operModel.getRelTimes());
		requestData.put("userListIds", ""+origin.getCreateUser().getUuid());
		requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+origin.getSid());
		//手机端事务提醒
		requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+origin.getSid());
		smsManager.sendSms(requestData, loginPerson);
		
		//记录日志
		TeeCoWorkTaskEvent event = new TeeCoWorkTaskEvent();
		event.setTitle("完成任务（申请审核）");
		event.setContent(operModel.getRemark());
		event.setCreateTime(TeeDateUtil.format(new Date()));
		event.setCreateUserId(loginPerson.getUuid());
		event.setCreateUserName(loginPerson.getUserName());
		event.setTask(origin);
		event.setType(10);
		event.setTypeDesc("完成");
		simpleDaoSupport.save(event);
	}
	
	/**
	 * 任务汇报
	 * @param operModel
	 */
	public void report(TeeCoWorkOperModel operModel){
		//获取原始任务数据
		TeeCoWorkTask origin = (TeeCoWorkTask) simpleDaoSupport.get(TeeCoWorkTask.class, operModel.getTaskId());
		origin.setProgress(operModel.getProgress());
		simpleDaoSupport.update(origin);
		
		TeePerson loginPerson = 
				(TeePerson) simpleDaoSupport.get(TeePerson.class, TeeRequestInfoContext.getRequestInfo().getUserSid());
		
		//短消息处理
		Map requestData = new HashMap();
		requestData.put("moduleNo", "035");
		requestData.put("content", loginPerson.getUserName()+" 向您汇报任务进度["+origin.getTaskTitle()+"] 进度："+operModel.getProgress()+"%，汇报内容："+operModel.getRemark());
		requestData.put("userListIds", ""+origin.getCreateUser().getUuid());
		requestData.put("remindUrl", "/system/subsys/cowork/detail.jsp?taskId="+origin.getSid());
		//手机端事务提醒
		requestData.put("remindUrl1", "/system/mobile/phone/task/detail.jsp?taskId="+origin.getSid());
		smsManager.sendSms(requestData, loginPerson);
		
		//记录日志
		TeeCoWorkTaskEvent event = new TeeCoWorkTaskEvent();
		event.setTitle("汇报任务（进度："+operModel.getProgress()+"%）");
		event.setContent(operModel.getRemark());
		event.setCreateTime(TeeDateUtil.format(new Date()));
		event.setCreateUserId(origin.getCharger().getUuid());
		event.setCreateUserName(origin.getCharger().getUserName());
		event.setTask(origin);
		event.setType(9);
		event.setTypeDesc("汇报");
		simpleDaoSupport.save(event);
	}
	
	/**
	 * 任务文档
	 * @param operModel
	 */
	public List<TeeCoWorkTaskDocModel> listDocs(TeeCoWorkOperModel operModel){
		String hql = "from TeeCoWorkTaskDoc doc where doc.task.sid="+operModel.getTaskId()+" order by doc.sid desc";
		List<TeeCoWorkTaskDocModel> list = new ArrayList<TeeCoWorkTaskDocModel>();
		List<TeeCoWorkTaskDoc> docs = simpleDaoSupport.find(hql, null);
		for(TeeCoWorkTaskDoc doc:docs){
			TeeCoWorkTaskDocModel model = new TeeCoWorkTaskDocModel();
			BeanUtils.copyProperties(doc, model);
			model.setCreateUserId(doc.getCreateUser().getUuid());
			model.setCreateUserName(doc.getCreateUser().getUserName());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			Calendar calendat1 = doc.getCreateTime();
			model.setCreateTimeDesc(sdf.format(calendat1.getTime()));
			
			List<TeeAttachment> attaches = attachmentService.getAttaches(TeeAttachmentModelKeys.coWork, doc.getSid()+"");
			List<TeeAttachmentModel> attachModels = new ArrayList<TeeAttachmentModel>();
			for(TeeAttachment attach:attaches){
				TeeAttachmentModel attachModel = new TeeAttachmentModel();
				attachmentService.entityToModel(attach, attachModel);
				attachModels.add(attachModel);
			}
			model.setAttaches(attachModels);
			list.add(model);
		}
		
		return list;
	}
	
	/**
	 * 任务事件
	 * @param operModel
	 */
	@Transactional(readOnly = true)
	public List<TeeTaskEventModel> listEvents(TeeCoWorkOperModel operModel){
		String hql = "from TeeCoWorkTaskEvent event where event.task.sid="+operModel.getTaskId()+" order by event.sid desc";
		List<TeeCoWorkTaskEvent> list = simpleDaoSupport.find(hql, null);
		List<TeeTaskEventModel> lists = new ArrayList<TeeTaskEventModel>();
		for(TeeCoWorkTaskEvent event:list){
			TeeTaskEventModel model=new  TeeTaskEventModel();
			
			model.setContent(event.getContent());
			model.setCreateUserId(model.getCreateUserId());
			model.setCreateUserName(event.getCreateUserName());
			model.setSid(event.getSid());
			int tack=event.getTask().getSid();
			model.setTask(tack);
			model.setTitle(event.getTitle());
			model.setType(event.getType());
			model.setTypeDesc(event.getTypeDesc());
			model.setContent(event.getContent());
			
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date=null;
			try {
				date = sdf1.parse(event.getCreateTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			
			model.setCreateTime(sdf.format(date));
			
			lists.add(model);
		}
		
		return lists;
	}
	
	/**
	 * 添加文档
	 * @param operModel
	 */
	public void addDoc(TeeCoWorkTaskDocModel docModel){
		TeeRequestInfo info = TeeRequestInfoContext.getRequestInfo();
		TeePerson loginUser = (TeePerson) simpleDaoSupport.get(TeePerson.class, info.getUserSid());
		
		TeeCoWorkTask task = (TeeCoWorkTask) simpleDaoSupport.get(TeeCoWorkTask.class, docModel.getTaskId());
		
		int attachIds [] = TeeStringUtil.parseIntegerArray(docModel.getAttachIds());
		TeeCoWorkTaskDoc coWorkTaskDoc = new TeeCoWorkTaskDoc();
		coWorkTaskDoc.setCreateTime(Calendar.getInstance());
		coWorkTaskDoc.setCreateUser(loginUser);
		coWorkTaskDoc.setRemark(docModel.getRemark());
		coWorkTaskDoc.setTask(task);
		
		simpleDaoSupport.save(coWorkTaskDoc);
		
		for(int attachId:attachIds){
			TeeAttachment attach = (TeeAttachment) simpleDaoSupport.get(TeeAttachment.class, attachId);
			attach.setModelId(coWorkTaskDoc.getSid()+"");
			attachmentService.updateAttachment(attach);
		}
		
		//记录日志
		TeeCoWorkTaskEvent event = new TeeCoWorkTaskEvent();
		event.setTitle("提交文档");
		event.setCreateTime(TeeDateUtil.format(new Date()));
		event.setCreateUserId(loginUser.getUuid());
		event.setCreateUserName(loginUser.getUserName());
		event.setTask(task);
		event.setType(11);
		event.setTypeDesc("文档");
		simpleDaoSupport.save(event);
		
	}
	
	/**
	 * 删除文档
	 * @param operModel
	 */
	public void deleteDoc(TeeCoWorkTaskDocModel docModel){
		TeeRequestInfo info = TeeRequestInfoContext.getRequestInfo();
		TeePerson loginUser = (TeePerson) simpleDaoSupport.get(TeePerson.class, info.getUserSid());
		
		TeeCoWorkTaskDoc doc = (TeeCoWorkTaskDoc) simpleDaoSupport.get(TeeCoWorkTaskDoc.class, docModel.getSid());
		List<TeeAttachment> attaches = attachmentService.getAttaches(TeeAttachmentModelKeys.coWork, String.valueOf(doc.getSid()));
		for(TeeAttachment attach:attaches){
			attachmentService.deleteAttach(attach);
		}
		simpleDaoSupport.deleteByObj(doc);
		
		//记录日志
		TeeCoWorkTaskEvent event = new TeeCoWorkTaskEvent();
		event.setTitle("删除文档");
		event.setCreateTime(TeeDateUtil.format(new Date()));
		event.setCreateUserId(loginUser.getUuid());
		event.setCreateUserName(loginUser.getUserName());
		event.setTask(doc.getTask());
		event.setType(11);
		event.setTypeDesc("文档");
		simpleDaoSupport.save(event);
	}
	
	/**
	 * 判断该任务是否存在未完成的子任务
	 * @param taskId
	 * @return
	 */
	public boolean hasExistsUnfinishedChildTask(int taskId){
		long count = simpleDaoSupport.count("select count(*) from TeeCoWorkTask task where task.parentTask.sid="+taskId+" and task.status not in (8,9)", null);
		return count!=0;
	}
	
	/**
	 * 列表
	 * @param dataGridModel
	 * @param requestData
	 * @return
	 */
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dataGridModel,Map requestData, Integer personid){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		
		List param = new ArrayList();
		int parentTaskId = TeeStringUtil.getInteger(requestData.get("parentTaskId"), 0);
		String taskTitle = TeeStringUtil.getString(requestData.get("taskTitle"));
		int status = TeeStringUtil.getInteger((requestData.get("status")),-2);
		String createUserId = TeeStringUtil.getString(requestData.get("createUserId"));
		String chargerId = TeeStringUtil.getString(requestData.get("chargerId"));
		
		
		/**
		 * 查询条件
		 * 1：创建人
		 * 2：等待审批 并且 审批人
		 * 3：审批通过 并且 负责人
		 * 4：审批通过 并且 参与人
		 */
		String hql = "from TeeCoWorkTask task where task.parentTask.sid="+parentTaskId;
		
		
		if(parentTaskId==0){
			
			hql = "from TeeCoWorkTask task where "+(parentTaskId==0?"":"(task.parentTask.sid="+parentTaskId+" and task.parentTask is not null) and ")+"(task.createUser.uuid="+loginUser.getUuid()+" "
					+ " or (task.auditor.uuid="+loginUser.getUuid()+" and task.status=1) "
					+ " or (task.charger.uuid="+loginUser.getUuid()+" and task.status !=1) "
					+ " or (exists (select 1 from task.joiners j where j.uuid in ("+loginUser.getUuid()+"))  and task.status !=1)) ";
			
			if(!"".equals(taskTitle)){
				hql+=" and task.taskTitle like '%"+TeeDbUtility.formatString(taskTitle)+"%'";
			}
			
			if(!"".equals(createUserId)){
				hql+=" and task.createUser.uuid = "+createUserId;
			}
			
			if(!"".equals(chargerId)){
				hql+=" and task.charger.uuid = "+chargerId;
			}
			
			if(personid==1 || personid==0){
				hql+=" and task.charger.uuid ="+loginUser.getUuid()+"";
			}
			
			if(personid==2){
				hql+=" and (exists (select 1 from task.joiners j where j.uuid in ("+loginUser.getUuid()+")) and task.status !=1)";
			}
			
			if(personid==3){
				hql+=" and task.createUser.uuid ="+loginUser.getUuid()+"";
			}
			Calendar c1=Calendar.getInstance();
			Date now=c1.getTime();
			if("-2".equals(status) || "".equals(status) || status == -2){
				hql+=" and task.status BETWEEN 0 AND 9";
			}else if("-1".equals(status) || "".equals(status) || status == -1){
				hql+=" and task.status BETWEEN 0 AND 9";
			}else if("0".equals(status)  || status == 0){
					hql+=" and task.status BETWEEN 0 AND 3 ";
			}else if("4".equals(status)  || status == 4){
				    hql+=" and task.status BETWEEN 4 AND 6 ";
			}else if("8".equals(status)  || status == 8){
			    hql+=" and task.status = "+status;
			}else if("7".equals(status)  || status == 7){
			    hql+=" and task.status = "+status;
			}else if("9".equals(status)  || status == 9){
				hql+=" and task.status BETWEEN 4 AND 6 and endTime < ?";
				param.add(c1);
			}
		}
		

		StringBuffer order = new StringBuffer();
		order.append(" order by ");
		
		if("".equals(dataGridModel.getSort()) || dataGridModel.getSort()==null){
			order.append(" task.sid desc");
		}else{
			order.append(" "+dataGridModel.getSort()+" "+dataGridModel.getOrder());
		}
		
		List<TeeCoWorkTask> list = simpleDaoSupport.pageFindByList(hql+order.toString(), dataGridModel.getRows()*(dataGridModel.getPage()-1), dataGridModel.getRows(), param);
		long total = simpleDaoSupport.count("select count(sid) "+hql, param.toArray());
		
	
		List<TeeCoWorkTaskModel> models = new ArrayList<TeeCoWorkTaskModel>();
		List<TeenumModel> model1 = new ArrayList<TeenumModel>();
		for(TeeCoWorkTask task:list){
			TeeCoWorkTaskModel model = new TeeCoWorkTaskModel();
			taskEntityToModel(task, model);
			models.add(model);
		}
	
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	public TeenumModel num1(Integer personid, Map requestData){
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		int status=TeeStringUtil.getInteger(requestData.get("status"),0);
		int parentTaskId = TeeStringUtil.getInteger(requestData.get("parentTaskId"), 0);
		String hql1 = "from TeeCoWorkTask task where "+(parentTaskId==0?"":"(task.parentTask.sid="+parentTaskId+" and task.parentTask is not null) and ");
		if(parentTaskId ==1 ){
			
		}else if(parentTaskId ==2 ){
			
		}else if(parentTaskId ==3 ){
			
		}
		hql1=hql1+"(task.createUser.uuid="+loginUser.getUuid()+" "
				+ " or (task.auditor.uuid="+loginUser.getUuid()+" and task.status=1) "
				+ " or (task.charger.uuid="+loginUser.getUuid()+" and task.status !=1) "
				+ " or (exists (select 1 from task.joiners j where j.uuid in ("+loginUser.getUuid()+"))  and task.status !=1)) ";

		long total = simpleDaoSupport.count("select count(sid) "+hql1, null);
		
				TeenumModel model = new TeenumModel();
				long startTime1 = System.currentTimeMillis();
				long cha = mailDao.getListCount1(status, personid,requestData,total);
				long quanbuCount = mailDao.getListCount1(-1, personid,requestData,total);
				long weikaishiCount = mailDao.getListCount1(0, personid,requestData,total);
				long jinxingzhongCount = mailDao.getListCount1(4, personid,requestData,total);
				long yichaoqiCount = mailDao.getListCount1(9, personid,requestData,total);
				long yiwanchengCount = mailDao.getListCount1(8, personid,requestData,total);
				long yiquxiaoCount = mailDao.getListCount1(7, personid,requestData,total);
				long wofuzedeCount = mailDao.getListCount1(-1, 1,requestData,total);
				long wocanyudeCount = mailDao.getListCount1(-1, 2,requestData,total);
				long wochangjiandeCount = mailDao.getListCount1(-1, 3,requestData,total);
				
				
				model.setQuanbuCount(quanbuCount);
				model.setWeikaishiCount(weikaishiCount);
				model.setJinxingzhongCount(jinxingzhongCount);
				model.setYichaoqiCount(yichaoqiCount);
				model.setYiwanchengCount(yiwanchengCount);
				model.setYiquxiaoCount(yiquxiaoCount);
				model.setWocanyudeCount(wocanyudeCount);
				model.setWochangjiandeCount(wochangjiandeCount);
				model.setWofuzedeCount(wofuzedeCount);
				model.setNumber(cha);
				model.setStatus(status);
		return model;
	}
	/**
	 * 任务汇总
	 * @param operModel
	 */
	public List<Map> showTasksReport(){
		List<Map> list = new ArrayList();
		TeeRequestInfo info = TeeRequestInfoContext.getRequestInfo();
		
		String hql = "from TeeCoWorkTask task where task.createUser.uuid="+info.getUserSid()+" or task.charger.uuid="+info.getUserSid()+" order by task.status asc,task.sid desc";
		List<TeeCoWorkTask> taskList =  simpleDaoSupport.find(hql, null);
		for(TeeCoWorkTask task:taskList){
			Map param = new HashMap();
			param.put("taskTitle", task.getTaskTitle());
			param.put("sid", task.getSid());
			param.put("chargerName", task.getCharger().getUserName());
			param.put("startTime", TeeDateUtil.format(task.getStartTime()));
			param.put("endTime", TeeDateUtil.format(task.getEndTime()));
			param.put("progress", task.getProgress());
			param.put("overtime", task.getEndTime().before(Calendar.getInstance()));
			param.put("finish", task.getStatus()==8);//完成标记
			list.add(param);
		}
		
		return list;
	}
	
	/**
	 * 任务图形化
	 * @param operModel
	 */
	public List<Map> showTasksGraphics(TeeCoWorkTaskModel taskModel){
		List<Map> list = new ArrayList();
		TeeRequestInfo info = TeeRequestInfoContext.getRequestInfo();
		
		//获取当前任务路径
		TeeCoWorkTask cTask = (TeeCoWorkTask) simpleDaoSupport.get(TeeCoWorkTask.class, taskModel.getSid());
		
		String hql = "from TeeCoWorkTask task where task.taskPath like '"+cTask.getTaskPath()+"%'";
		List<TeeCoWorkTask> taskList =  simpleDaoSupport.find(hql, null);
		for(TeeCoWorkTask task:taskList){
			Map param = new HashMap();
			param.put("taskTitle", task.getTaskTitle());
			param.put("chargerName", task.getCharger().getUserName());
			param.put("startTime", TeeDateUtil.format(task.getStartTime()));
			param.put("endTime", TeeDateUtil.format(task.getEndTime()));
			param.put("progress", task.getProgress());
			param.put("sid", task.getSid());
			param.put("status", task.getStatus());
			param.put("pid", task.getParentTask()==null?-1:task.getParentTask().getSid());
			list.add(param);
		}
		
		return list;
	}
	
	public void taskModelToEntity(TeeCoWorkTaskModel coWorkTaskModel,TeeCoWorkTask coWorkTask){
		BeanUtils.copyProperties(coWorkTaskModel, coWorkTask);
		coWorkTask.setCreateTime(Calendar.getInstance());
		coWorkTask.setStartTime(TeeDateUtil.parseCalendar("yyyy-MM-dd hh:mm:ss", coWorkTaskModel.getStartTimeDesc()));
		coWorkTask.setEndTime(TeeDateUtil.parseCalendar("yyyy-MM-dd hh:mm:ss", coWorkTaskModel.getEndTimeDesc()));
		coWorkTask.setRelStartTime(TeeDateUtil.parseCalendar("yyyy-MM-dd hh:mm:ss", coWorkTaskModel.getRelStartTimeDesc()));
		coWorkTask.setRelEndTime(TeeDateUtil.parseCalendar("yyyy-MM-dd hh:mm:ss", coWorkTaskModel.getRelEndTimeDesc()));
		if(coWorkTaskModel.getCreateUserId()!=0){
			coWorkTask.setCreateUser(personService.selectByUuid(coWorkTaskModel.getCreateUserId()));
		}
		if(coWorkTaskModel.getAuditorId()!=0){
			coWorkTask.setAuditor(personService.selectByUuid(coWorkTaskModel.getAuditorId()));
		}
		if(coWorkTaskModel.getChargerId()!=0){
			coWorkTask.setCharger(personService.selectByUuid(coWorkTaskModel.getChargerId()));
		}
		
		int sp[] = TeeStringUtil.parseIntegerArray(coWorkTaskModel.getJoinerIds());
		for(int id:sp){
			coWorkTask.getJoiners().add(personService.selectByUuid(id));
		}
		
		TeeCoWorkTask parentTask = (TeeCoWorkTask) simpleDaoSupport.get(TeeCoWorkTask.class,coWorkTaskModel.getParentTaskId());
		coWorkTask.setParentTask(parentTask);
	}

	public void taskEntityToModel(TeeCoWorkTask coWorkTask,TeeCoWorkTaskModel coWorkTaskModel){
		BeanUtils.copyProperties(coWorkTask, coWorkTaskModel);
		TeePerson tmp = coWorkTask.getCreateUser();
		if(tmp!=null){
			coWorkTaskModel.setCreateUserId(tmp.getUuid());
			coWorkTaskModel.setCreateUserName(tmp.getUserName());
		}
		
		tmp = coWorkTask.getAuditor();
		if(tmp!=null){
			coWorkTaskModel.setAuditorId(tmp.getUuid());
			coWorkTaskModel.setAuditorName(tmp.getUserName());
		}else{
			coWorkTaskModel.setAuditorId(0);
			coWorkTaskModel.setAuditorName("");
		}
		
		tmp = coWorkTask.getCharger();
		if(tmp!=null){
			coWorkTaskModel.setChargerId(tmp.getUuid());
			coWorkTaskModel.setChargerName(tmp.getUserName());
		}else{
			coWorkTaskModel.setChargerId(0);
			coWorkTaskModel.setChargerName("");
		}
		
		Set<TeePerson> joiners = coWorkTask.getJoiners();
		Iterator<TeePerson> it = joiners.iterator();
		StringBuffer ids = new StringBuffer();
		StringBuffer names = new StringBuffer();
		if(it!=null){
			while(it.hasNext()){
				tmp = it.next();
				ids.append(tmp.getUuid()+"");
				names.append(tmp.getUserName()+"");
				if(it.hasNext()){
					ids.append(",");
					names.append(",");
				}
			}
		}
		coWorkTaskModel.setJoinerIds(ids.toString());
		coWorkTaskModel.setJoinerNames(names.toString());
		
		
		coWorkTaskModel.setCreateTimeDesc(TeeDateUtil.format(coWorkTask.getCreateTime()));
		coWorkTaskModel.setStartTimeDesc(TeeDateUtil.format(coWorkTask.getStartTime()));
		coWorkTaskModel.setEndTimeDesc(TeeDateUtil.format(coWorkTask.getEndTime()));
		coWorkTaskModel.setRelStartTimeDesc(TeeDateUtil.format(coWorkTask.getRelStartTime()));
		coWorkTaskModel.setRelEndTimeDesc(TeeDateUtil.format(coWorkTask.getRelEndTime()));
		if(coWorkTask.getRelTimes()==null){
			coWorkTaskModel.setRelTimes("");
		}
		TeeCoWorkTask parentTask = coWorkTask.getParentTask();
		if(parentTask!=null){
			coWorkTaskModel.setParentTaskId(parentTask.getSid());
			coWorkTaskModel.setParentTaskName(parentTask.getTaskTitle());
		}
		
	}
	
	/**
	 * 通过scheduleId获取相关任务
	 * @param scheduleId
	 * @return
	 */
	public List<TeeCoWorkTaskModel> getTaskRelations(String scheduleId){
		List<TeeCoWorkTask> tasks = simpleDaoSupport.find("from TeeCoWorkTask task where exists (select 1 from task.taskSchedule taskSchedule where taskSchedule.schedule.uuid=?)", new Object[]{scheduleId});
		List modelList = new ArrayList();
		for(TeeCoWorkTask task:tasks){
			TeeCoWorkTaskModel model = new TeeCoWorkTaskModel();
			taskEntityToModel(task, model);
			modelList.add(model);
		}
		return modelList;
	}
	
	/*public List<TeeCoWorkTaskModel> getshowList(){
		List<TeeCoWorkTask> tasks = simpleDaoSupport.find("from TeeCoWorkTask task",null);
		List modelList = new ArrayList();
		for(TeeCoWorkTask task:tasks){
			TeeCoWorkTaskModel model = new TeeCoWorkTaskModel();
			taskEntityToModel(task, model);
			modelList.add(model);
		}
		return modelList;
	}*/
	
	public List<TeeFullCalendarModel> getshowListByid(TeeCoWorkOperModel model, TeePerson person) throws ParseException{
		
        
       
        String startTimeStr = model.getStartTimes();
        String endTimeStr = model.getEndTimes();
        Calendar startTime=TeeDateUtil.parseCalendar("yyyy-MM-dd hh:mm:ss", startTimeStr);
        Calendar endTime=TeeDateUtil.parseCalendar("yyyy-MM-dd hh:mm:ss", endTimeStr);
        /*Calendar startTime=TeeDateUtil.parseCalendar("yyyy-MM-dd hh:mm:ss", "2018-08-17 17:03:34");
        Calendar endTime=TeeDateUtil.parseCalendar("yyyy-MM-dd hh:mm:ss", "2018-08-17 17:03:38");*/
       int vehicleSid  = TeeStringUtil.getInteger(model.getTaskId(), 0);
        Object[] values = {startTime,endTime,startTime,endTime,startTime,endTime};
        List<TeeCoWorkTask> list =simpleDaoSupport.find("from TeeCoWorkTask c where "
        		+ "( c.endTime BETWEEN ? AND ?)"
                +"OR (c.startTime BETWEEN ? AND ? )"
        		+"OR ( ? BETWEEN c.startTime and c.endTime)"
                +"OR (? BETWEEN c.startTime and c.endTime)",values) ;

      //日程model
        List<TeeFullCalendarModel> listModel = new ArrayList<TeeFullCalendarModel> ();
        for (int i = 0; i < list.size(); i++) {
        	 listModel.add(parseFullCalendarModel( list.get(i))); 
        	// System.out.println(list.get(i).getTaskTitle());
        }
        //System.out.println(listModel.size());
		return listModel;

	}
	public List<TeeCoWorkTaskModel> SelctshowList(){
		List<TeeCoWorkTask> tasks = simpleDaoSupport.find("from TeeCoWorkTask task",null);
		List modelList = new ArrayList();
		for(TeeCoWorkTask task:tasks){
			TeeCoWorkTaskModel model = new TeeCoWorkTaskModel();
			taskEntityToModel(task, model);
			modelList.add(model);
		}
		return modelList;
	}
	
	/***
     * 将日程转换为 fullCalendar对象
     * @author syl
     * @date 2014-1-5
     * @param cal
     * @return
     */
	
	public TeeFullCalendarModel parseFullCalendarModel(TeeCoWorkTask vehicleUsage ){
        TeeFullCalendarModel calModel = new TeeFullCalendarModel();
        calModel.setId(vehicleUsage.getSid());
        calModel.setTitle(vehicleUsage.getCreateUser().getUserName());
        Calendar cal1=vehicleUsage.getStartTime();
    	Date date1=cal1.getTime();
    	 Calendar cal2=vehicleUsage.getEndTime();
     	Date date2=cal2.getTime();
        int day = TeeUtility.getDaySpan(date1,date2 );
        if (day>0) {
            calModel.setAllDay(true);
        }
        
        calModel.setClassName("fc-event-color");
        Date date = new Date();
        date.setTime(date1.getTime());//设置开始时间
        calModel.setStart(TeeDateUtil.format(date));
        
        date.setTime(date2.getTime());//设置结束时间时间
        calModel.setEnd(TeeDateUtil.format(date));
        
        calModel.setEditable(false);
        calModel.setDeleteable(false);
        
        if(vehicleUsage.getStatus() == 0){
        	 calModel.setClassName("fc-event-color");
          }else if(vehicleUsage.getStatus() == 1){
        	  calModel.setClassName("fc-event-color1");
           }else if(vehicleUsage.getStatus() == 2){
        	   calModel.setClassName("fc-event-color2");
           }else if(vehicleUsage.getStatus() == 3){
        	   calModel.setClassName("fc-event-color3");
           }else if(vehicleUsage.getStatus() == 4){
        	   calModel.setClassName("fc-event-color4");
        }else if(vehicleUsage.getStatus() == 5){
        	 calModel.setClassName("fc-event-color5");
        }else if(vehicleUsage.getStatus() == 6){
        	 calModel.setClassName("fc-event-color6");
        }else if(vehicleUsage.getStatus() == 7){
        	 calModel.setClassName("fc-event-color7");
        }else if(vehicleUsage.getStatus() == 8){
        	 calModel.setClassName("fc-event-color8");
        }else if(vehicleUsage.getStatus() == 9){
        	 calModel.setClassName("fc-event-color9");
        }
        
        if(vehicleUsage.getStatus() == 0){
       	 calModel.setTitle("任务名称："+vehicleUsage.getTaskTitle()+
       	 		"\r\n负责人："+vehicleUsage.getCharger().getUserName()+
            		"\r\n布置人："+vehicleUsage.getCreateUser().getUserName()
            		+"\r\n状态："+"等待接受");
            
       }else if(vehicleUsage.getStatus() == 1){
        	 calModel.setTitle("任务名称："+vehicleUsage.getTaskTitle()+
            	 		"\r\n负责人："+vehicleUsage.getCharger().getUserName()+
             		"\r\n布置人："+vehicleUsage.getCreateUser().getUserName()
             		+"\r\n状态："+"等待审批");
             
        }else if(vehicleUsage.getStatus() == 2){
        	 calModel.setTitle("任务名称："+vehicleUsage.getTaskTitle()+
            	 		"\r\n负责人："+vehicleUsage.getCharger().getUserName()+
             		"\r\n布置人："+vehicleUsage.getCreateUser().getUserName()
             		+"\r\n状态："+"审批不通过");
        }else if(vehicleUsage.getStatus() == 3){
        	 calModel.setTitle("任务名称："+vehicleUsage.getTaskTitle()+
            	 		"\r\n负责人："+vehicleUsage.getCharger().getUserName()+
              		"\r\n布置人："+vehicleUsage.getCreateUser().getUserName()
              		+"\r\n状态："+"拒绝接收");
        }else if(vehicleUsage.getStatus() == 4){
       	 calModel.setTitle("任务名称："+vehicleUsage.getTaskTitle()+
        	 		"\r\n负责人："+vehicleUsage.getCharger().getUserName()+
           		"\r\n布置人："+vehicleUsage.getCreateUser().getUserName()
           		+"\r\n状态："+"进行中");
     }else if(vehicleUsage.getStatus() == 5){
    	 calModel.setTitle("任务名称："+vehicleUsage.getTaskTitle()+
        	 		"\r\n负责人："+vehicleUsage.getCharger().getUserName()+
           		"\r\n布置人："+vehicleUsage.getCreateUser().getUserName()
           		+"\r\n状态："+"等待审核");
     }else if(vehicleUsage.getStatus() == 6){
    	 calModel.setTitle("任务名称："+vehicleUsage.getTaskTitle()+
        	 		"\r\n负责人："+vehicleUsage.getCharger().getUserName()+
           		"\r\n布置人："+vehicleUsage.getCreateUser().getUserName()
           		+"\r\n状态："+"审核不通过");
     }else if(vehicleUsage.getStatus() == 7){
    	 calModel.setTitle("任务名称："+vehicleUsage.getTaskTitle()+
        	 		"\r\n负责人："+vehicleUsage.getCharger().getUserName()+
           		"\r\n布置人："+vehicleUsage.getCreateUser().getUserName()
           		+"\r\n状态："+"任务撤销");
     }else if(vehicleUsage.getStatus() == 8){
    	 calModel.setTitle("任务名称："+vehicleUsage.getTaskTitle()+
        	 		"\r\n负责人："+vehicleUsage.getCharger().getUserName()+
           		"\r\n布置人："+vehicleUsage.getCreateUser().getUserName()
           		+"\r\n状态："+"已完成");
     }else if(vehicleUsage.getStatus() == 9){
    	 calModel.setTitle("任务名称："+vehicleUsage.getTaskTitle()+
        	 		"\r\n负责人："+vehicleUsage.getCharger().getUserName()+
           		"\r\n布置人："+vehicleUsage.getCreateUser().getUserName()
           		+"\r\n状态："+"任务失败");
     }
       
        return calModel;
    }
	
	
	/**
	 * 移动列表
	 * @param dataGridModel
	 * @param requestData
	 * @return
	 */
	public TeeEasyuiDataGridJson datagridPhone(TeeDataGridModel dataGridModel,Map requestData, Integer personid){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		
		List param = new ArrayList();
		int parentTaskId = TeeStringUtil.getInteger(requestData.get("parentTaskId"), 0);
		String taskTitle = TeeStringUtil.getString(requestData.get("taskTitle"));
		int status = TeeStringUtil.getInteger((requestData.get("status")),-2);
		String createUserId = TeeStringUtil.getString(requestData.get("createUserId"));
		String chargerId = TeeStringUtil.getString(requestData.get("chargerId"));
		int  personId = TeeStringUtil.getInteger((requestData.get("personId")),0);
		
		/**
		 * 查询条件
		 * 1：创建人
		 * 2：等待审批 并且 审批人
		 * 3：审批通过 并且 负责人
		 * 4：审批通过 并且 参与人
		 */
		/*String hql1 = "from TeeCoWorkTask task where "+(parentTaskId==0?"":"(task.parentTask.sid="+parentTaskId+" and task.parentTask is not null) and ")+"(task.createUser.uuid="+loginUser.getUuid()+" "
				+ " or (task.auditor.uuid="+loginUser.getUuid()+" and task.status=1) "
				+ " or (task.charger.uuid="+loginUser.getUuid()+" and task.status !=1) "
				+ " or (exists (select 1 from task.joiners j where j.uuid in ("+loginUser.getUuid()+"))  and task.status !=1)) ";
		List<TeeCoWorkTask> lists= simpleDaoSupport.executeQuery(hql1, null);*/
		String hql = "from TeeCoWorkTask task where "+(parentTaskId==0?"":"(task.parentTask.sid="+parentTaskId+" and task.parentTask is not null) and ")+"(task.createUser.uuid="+loginUser.getUuid()+" "
				+ " or (task.auditor.uuid="+loginUser.getUuid()+" and task.status=1) "
				+ " or (task.charger.uuid="+loginUser.getUuid()+" and task.status !=1) "
				+ " or (exists (select 1 from task.joiners j where j.uuid in ("+loginUser.getUuid()+"))  and task.status !=1)) ";
		
		if(!TeeUtility.isNullorEmpty(taskTitle) && !"null".equals(taskTitle) && taskTitle !=null){
			hql+=" and task.taskTitle like '%"+TeeDbUtility.formatString(taskTitle)+"%'";
//			param.add("%"+taskTitle+"%");
		}
		
		/*if(!"-1".equals(status) && !"".equals(status)){
			hql+=" and task.status = "+status;
		}*/
		
		if(!TeeUtility.isNullorEmpty(createUserId) && !"null".equals(createUserId) && createUserId !=null){
			hql+=" and task.createUser.uuid = "+createUserId;
		}
		
		if(!TeeUtility.isNullorEmpty(chargerId) && !"null".equals(chargerId) && createUserId !=null){
			hql+=" and task.charger.uuid = "+chargerId;
		}
		
			if(personId==1 || personId==0){
				hql+=" and task.charger.uuid ="+loginUser.getUuid()+"";
			}
			
			if(personId==2){
				hql+=" and (exists (select 1 from task.joiners j where j.uuid in ("+loginUser.getUuid()+")) and task.status !=1)";
			}
			
			if(personId==3){
				hql+=" and task.createUser.uuid ="+loginUser.getUuid()+"";
			}
			Calendar c1=Calendar.getInstance();
			Date now=c1.getTime();
				if("-2".equals(status) || "".equals(status) || status == -2){
					hql+=" and task.status BETWEEN 0 AND 9";
				}else if("0".equals(status)  || status == 0){
						hql+=" and task.status BETWEEN 0 AND 3 ";
				}else if("4".equals(status)  || status == 4){
					    hql+=" and task.status BETWEEN 4 AND 6 ";
				}else if("8".equals(status)  || status == 8){
				    hql+=" and task.status = "+status;
				}else if("7".equals(status)  || status == 7){
				    hql+=" and task.status = "+status;
				}else if("9".equals(status)  || status == 9){
					hql+=" and task.status BETWEEN 4 AND 6 and endTime < ?";
					param.add(c1);
				}

		StringBuffer order = new StringBuffer();
		order.append(" order by ");
		
		if("".equals(dataGridModel.getSort()) || dataGridModel.getSort()==null){
			order.append(" task.sid desc");
		}else{
			order.append(" "+dataGridModel.getSort()+" "+dataGridModel.getOrder());
		}
		List<TeeCoWorkTask> list = simpleDaoSupport.pageFindByList(hql+order.toString(), dataGridModel.getRows()*(dataGridModel.getPage()-1), dataGridModel.getRows(), param);

		//System.out.println(list.get(0).getStartTime());
		long total = simpleDaoSupport.count("select count(sid) "+hql, param.toArray());
		
		List<TeeCoWorkTaskModel> models = new ArrayList<TeeCoWorkTaskModel>();
		List<TeenumModel> model1 = new ArrayList<TeenumModel>();
			for(TeeCoWorkTask task:list){
				
				TeeCoWorkTaskModel model = new TeeCoWorkTaskModel();
				taskEntityToModel(task, model);
				models.add(model);
				
			}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	public TeenumModel num(Integer personid, Map requestData){
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		int status=TeeStringUtil.getInteger(requestData.get("status"),-2);
		int parentTaskId = TeeStringUtil.getInteger(requestData.get("parentTaskId"), 0);
		int  personId = TeeStringUtil.getInteger((requestData.get("personId")),0);
		String hql1 = "from TeeCoWorkTask task where "+(parentTaskId==0?"":"(task.parentTask.sid="+parentTaskId+" and task.parentTask is not null) and ")+"(task.createUser.uuid="+loginUser.getUuid()+" "
				+ " or (task.auditor.uuid="+loginUser.getUuid()+" and task.status=1) "
				+ " or (task.charger.uuid="+loginUser.getUuid()+" and task.status !=1) "
				+ " or (exists (select 1 from task.joiners j where j.uuid in ("+loginUser.getUuid()+"))  and task.status !=1)) ";
		long total = simpleDaoSupport.count("select count(sid) "+hql1, null);
		
		
				TeenumModel model = new TeenumModel();
				long cha = mailDao.getListCount1(status, personId,requestData,total);
				long quanbuCount = mailDao.getListCount1(-2, personId,requestData,total);
				long weikaishiCount = mailDao.getListCount1(0, personId,requestData,total);
				long jinxingzhongCount = mailDao.getListCount1(4, personId,requestData,total);
				long yichaoqiCount = mailDao.getListCount1(7, personId,requestData,total);
				long yiwanchengCount = mailDao.getListCount1(8, personId,requestData,total);
				long yiquxiaoCount = mailDao.getListCount1(9, personId,requestData,total);
				long wofuzedeCount = mailDao.getListCount1(-2, 1,requestData,total);
				long wocanyudeCount = mailDao.getListCount1(-2, 2,requestData,total);
				long wochangjiandeCount = mailDao.getListCount1(-2, 3,requestData,total);
				
				model.setQuanbuCount(quanbuCount);
				model.setWeikaishiCount(weikaishiCount);
				model.setJinxingzhongCount(jinxingzhongCount);
				model.setYichaoqiCount(yichaoqiCount);
				model.setYiwanchengCount(yiwanchengCount);
				model.setYiquxiaoCount(yiquxiaoCount);
				model.setWocanyudeCount(wocanyudeCount);
				model.setWochangjiandeCount(wochangjiandeCount);
				model.setWofuzedeCount(wofuzedeCount);
				model.setNumber(cha);
				model.setStatus(status);
				
		return model;
	}
}
