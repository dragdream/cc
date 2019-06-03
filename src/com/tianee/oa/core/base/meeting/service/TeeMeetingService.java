package com.tianee.oa.core.base.meeting.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.app.quartz.bean.TeeQuartzTask;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.calendar.model.TeeCalendarAffairModel;
import com.tianee.oa.core.base.calendar.model.TeeFullCalendarModel;
import com.tianee.oa.core.base.calendar.service.TeeCalendarManagerI;
import com.tianee.oa.core.base.commonword.bean.CommonWord;
import com.tianee.oa.core.base.meeting.bean.TeeMeeting;
import com.tianee.oa.core.base.meeting.bean.TeeMeetingAttendConfirm;
import com.tianee.oa.core.base.meeting.bean.TeeMeetingRoom;
import com.tianee.oa.core.base.meeting.bean.TeeMeetingTopic;
import com.tianee.oa.core.base.meeting.dao.TeeMeetingAttendConfirmDao;
import com.tianee.oa.core.base.meeting.dao.TeeMeetingDao;
import com.tianee.oa.core.base.meeting.dao.TeeMeetingRoomDao;
import com.tianee.oa.core.base.meeting.dao.TeeMeetingTopicDao;
import com.tianee.oa.core.base.meeting.model.TeeMeetingModel;
import com.tianee.oa.core.base.meeting.model.TeeMeetingTopicModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.quartz.TeeQuartzTypes;
import com.tianee.quartz.util.TeeQuartzUtils;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.socket.MessagePusher;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class TeeMeetingService extends TeeBaseService {

	@Autowired
	private TeeMeetingDao meetingDao;

	@Autowired
	private TeePersonDao personDao;

	@Autowired
	private TeeMeetingRoomDao meetingRoomDao;

	@Autowired
	private TeeSmsManager smsManager;

	@Autowired
	private TeeBaseUpload upload;

	@Autowired
	private TeeCalendarManagerI calendarManagerI;

	@Autowired
	private TeeAttachmentDao attachmentDao;
	@Autowired
	private TeeMeetingTopicDao meetingTopicDao;
	private String logRemark = "";// 日志描述

	@Autowired
	private TeeMeetingAttendConfirmDao meetingConfirmDao;

	/**
	 * 新建
	 * 
	 * @function:
	 * @data: 2015年10月30日
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 *             TeeJson
	 */
	public TeeJson addObj(HttpServletRequest request, TeeMeetingModel model)
			throws Exception {
		TeeJson json = new TeeJson();
		TeeMeeting meeting = new TeeMeeting();
		BeanUtils.copyProperties(model, meeting);

		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);

		// 会议室
		if (!TeeUtility.isNullorEmpty(model.getMeetRoomId())) {
			int roomId = TeeStringUtil.getInteger(model.getMeetRoomId(), 0);
			TeeMeetingRoom room = meetingRoomDao.getById(roomId);
			meeting.setMeetRoom(room);
			meeting.setWbMeetRoomName(model.getWbMeetRoomName());

			// 处理申请时间
			meeting.setCreateTime(new Date().getTime());
			Date meetDate = TeeUtility.parseDate("yyyy-MM-dd",
					model.getMeetDateStr());
			String[] startTimeArr = model.getStartTimeStr().split(":");
			String[] endTimeArr = model.getEndTimeStr().split(":");
			long startTime = (TeeStringUtil.getInteger(startTimeArr[0], 0) * 60 * 60 + TeeStringUtil
					.getInteger(startTimeArr[1], 0) * 60) * 1000;
			long endTime = (TeeStringUtil.getInteger(endTimeArr[0], 0) * 60 * 60 + TeeStringUtil
					.getInteger(endTimeArr[1], 0) * 60) * 1000;
			meeting.setStartTime(meetDate.getTime() + startTime);
			meeting.setEndTime(meetDate.getTime() + endTime);

			// 判断会议时间是否重叠
			if (room!=null && meetingDao.checkMeetingTimeIsRepeat(meeting)) {
				json.setRtState(false);
				json.setRtMsg("会议室已被占用,请重新修改时间！");
				return json;
			}

			// 审批人
//			if (!TeeUtility.isNullorEmpty(model.getManagerId())) {
//				TeePerson leader = personDao.get(TeeStringUtil.getInteger(
//						model.getManagerId(), 0));
//				if (leader != null) {
//					meeting.setManager(leader);
//				}
//			}
			// 会议纪要员
			if (!TeeUtility.isNullorEmpty(model.getRecorderId())) {
				TeePerson recorder = personDao.get(TeeStringUtil.getInteger(
						model.getRecorderId(), 0));
				if (recorder != null) {
					meeting.setRecorder(recorder);
				}
			}
			List<TeePerson> personList = new ArrayList();
			// 参会人员
			if (!TeeUtility.isNullorEmpty(model.getAttendee())) {
				String userListIds = model.getAttendee();
				if (userListIds.endsWith(",")) {
					userListIds = userListIds.substring(0,
							userListIds.length() - 1);
				}
				Set<TeePerson> attendees = new HashSet();
				personList = personDao
						.getPersonByUuids(userListIds);

				if (personList != null && personList.size() > 0) {
					for (TeePerson person1 : personList) {
						attendees.add(person1);
					}
				}
				meeting.getAttendeeList().clear();
				meeting.setAttendeeList(attendees);
			}

			
			//出席领导
			List<TeePerson> leaderList = new ArrayList();
			if (!TeeUtility.isNullorEmpty(model.getAttendLeaderIds())) {
				String userListIds = model.getAttendLeaderIds();
				if (userListIds.endsWith(",")) {
					userListIds = userListIds.substring(0,
							userListIds.length() - 1);
				}
				Set<TeePerson> attendLeaderSet = new HashSet();
				leaderList = personDao
						.getPersonByUuids(userListIds);

				if (leaderList != null && leaderList.size() > 0) {
					for (TeePerson person1 : leaderList) {
						attendLeaderSet.add(person1);
					}
				}
				meeting.getAttendLeader().clear();
				meeting.setAttendLeader(attendLeaderSet);
			}
			
			// 申请人
			meeting.setUser(person);
			int userId = TeeStringUtil.getInteger(model.getUserId(), 0);
			if (userId > 0) {// 如果是系统管理员，且设置申请人不为空
				TeePerson applayPerson = personDao.selectPersonById(userId);
				if (applayPerson != null) {
					meeting.setUser(applayPerson);
				}
			}

			meeting.setStatus(1);
			meeting.setIsUpLoadSummary("未上传");
			meetingDao.addAttendOut(meeting);
			//把会议同步到日程中
			String mrName="";
			if(roomId==0){
				mrName=meeting.getWbMeetRoomName();
			}else{
				mrName=meeting.getMeetRoom().getMrName();
			}
			
			String isWriteCalendar = TeeStringUtil.getString(
					request.getParameter("isWriteCalendar"), "");// 是否写入日程安排
			String contentEb = "通知您于" + model.getStartTimeStr() + "在"
					+ mrName + "开会，会议名称："
					+ meeting.getMeetName();
			if ("1".equals(isWriteCalendar)) {
				TeeCalendarAffairModel calModel = new TeeCalendarAffairModel();
				calModel.setStartTime(meeting.getStartTime());
				calModel.setEndTime(meeting.getEndTime());
				calModel.setContent(contentEb);
				calModel.setActorIds(meeting.getAttendee());
				calModel.setPreHours(meeting.getResendHour());
				calModel.setPreMinutes(meeting.getResendMinute());
				calendarManagerI
						.createCalendar(meeting.getUser(), calModel);
			}
			
			/* 附件处理 */
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			List<TeeAttachment> attachments = upload.manyAttachUpload(
					multipartRequest, TeeAttachmentModelKeys.MEETINGATTEND);
			for (TeeAttachment attach : attachments) {
				attach.setModelId(String.valueOf(meeting.getSid()));
				simpleDaoSupport.update(attach);
			}
			
			if (meeting.getStatus() == 1) {
				// 发送内部短信 内部短信
				String smsRemind = meeting.getSmsRemind();
				if (smsRemind.equals("1")) {// 发送内部短信
					String userListIds = model.getAttendee();
					Calendar cl = Calendar.getInstance();
					cl.setTimeInMillis(meeting.getStartTime());

					if (meeting.getResendHour() > 0) {
						cl.add(Calendar.HOUR_OF_DAY,
								-meeting.getResendHour());
					}
					if (meeting.getResendMinute() > 0) {
						cl.add(Calendar.MINUTE,
								-meeting.getResendMinute());
					}
					Date date = new Date();
					// 开始时间
					date.setTime(meeting.getStartTime());
					SimpleDateFormat dateFormat2 = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					String startTimeStr = dateFormat2.format(date);
					if (userListIds.length() > 0) {
						// 会议定时提醒
						TeeQuartzTask task = new TeeQuartzTask();
						task.setContent("请您在" + startTimeStr
								+ "准时参加会议！,其主题为："
								+ meeting.getMeetName());
						task.setModelId(String.valueOf(meeting.getSid()));
						task.setModelNo("031");
						task.setType(TeeQuartzTypes.ONCE);
						task.setUrl("/system/core/base/meeting/personal/meetingdetail.jsp?id="
								+ meeting.getSid());
						task.setUrl1("/system/core/base/meeting/personal/meetingdetail.jsp?id="
								+ meeting.getSid());
						task.setFrom(person.getUserId());
						task.setExp(TeeQuartzUtils
								.getOnceQuartzExpression(cl));
						task.setTo(userListIds);
						MessagePusher.push2Quartz(task);

						// 内部短信
						Map requestData = new HashMap();
						requestData.put("content",
								"请您在" + startTimeStr
								+ "准时参加会议！,其主题为："
								+ meeting.getMeetName());
						requestData.put("userListIds", userListIds);
						requestData.put("moduleNo", "031");
						requestData.put("remindUrl",
								"/system/core/base/meeting/personal/meetingdetail.jsp?id="
										+ meeting.getSid());
						smsManager.sendSms(requestData, person);
					}

					// 创建人员参会信息
					if (personList != null && personList.size() > 0) {
						meetingConfirmDao.deleteObjByMeetingId(String
								.valueOf(meeting.getSid()));
						for (TeePerson person1 : personList) {
							TeeMeetingAttendConfirm obj = new TeeMeetingAttendConfirm();
							obj.setAttendFlag(0);
							obj.setCreateTime(new Date());
							obj.setCreateUser(person1);
							obj.setReadFlag(0);
							obj.setMeetingId(meeting.getSid());
							meetingConfirmDao.save(obj);
						}
					}

				}
			} else if (meeting.getStatus() == 0) {
				Map requestData = new HashMap();
				requestData.put("content", "修改了会议申请,请批示！");
				String userListIds = model.getManagerId();// out.getLeader().getUuid()
															// + "";
				requestData.put("userListIds", userListIds);
				// requestData.put("sendTime", );
				requestData.put("moduleNo", "031");
				requestData.put("remindUrl",
						"/system/core/base/meeting/leader/index.jsp");
				smsManager.sendSms(requestData, person);
			}

			// 发送审批消息
//			Map requestData = new HashMap();
//			requestData.put("content", "提交会议申请,请批示！");
//			String userListIds = model.getManagerId();
//			requestData.put("userListIds", userListIds);
//			// requestData.put("sendTime", );
//			requestData.put("moduleNo", "031");
//			requestData.put("remindUrl",
//					"/system/core/base/meeting/leader/index.jsp");
//			smsManager.sendSms(requestData, person);

			// 创建日志
			TeeSysLog sysLog = TeeSysLog.newInstance();
			sysLog.setType("031A");
			logRemark = "新建会议申请，会议名称：" + meeting.getMeetName();
			sysLog.setRemark(logRemark);
			TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);

		} else {
			json.setRtState(false);
			json.setRtMsg("请选择会议室！");
			return json;
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
	}

	/**
	 * 编辑
	 * 
	 * @function:
	 * @data: 2015年10月30日
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 *             TeeJson
	 */
	public TeeJson updateObj(HttpServletRequest request, TeeMeetingModel model)
			throws Exception {
		//System.out.println(model.getIsUpLoadSummary());
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		String isLeaderOpt = TeeStringUtil.getString(
				request.getParameter("isLeaderOpt"), "");// 是否从会议管理 审批 修改的， ，1-是
															// 其它-不是
		if (model.getSid() > 0) {
			// 会议室
			if (!TeeUtility.isNullorEmpty(model.getMeetRoomId())) {
				int roomId = TeeStringUtil.getInteger(model.getMeetRoomId(), 0);
				TeeMeetingRoom room = meetingRoomDao.getById(roomId);
				if (room != null || roomId==0) {
					TeeMeeting meeting = meetingDao.getById(model.getSid());
					model.setIsUpLoadSummary(meeting.getIsUpLoadSummary());
					BeanUtils.copyProperties(model, meeting);
					meeting.setWbMeetRoomName(model.getWbMeetRoomName());
					meeting.setMeetRoom(room);

					// 处理申请时间
					meeting.setCreateTime(new Date().getTime());
					Date meetDate = TeeUtility.parseDate("yyyy-MM-dd",
							model.getMeetDateStr());
					String[] startTimeArr = model.getStartTimeStr().split(":");
					String[] endTimeArr = model.getEndTimeStr().split(":");
					long startTime = (TeeStringUtil.getInteger(startTimeArr[0],
							0) * 60 * 60 + TeeStringUtil.getInteger(
							startTimeArr[1], 0) * 60) * 1000;
					long endTime = (TeeStringUtil.getInteger(endTimeArr[0], 0) * 60 * 60 + TeeStringUtil
							.getInteger(endTimeArr[1], 0) * 60) * 1000;
					meeting.setStartTime(meetDate.getTime() + startTime);
					meeting.setEndTime(meetDate.getTime() + endTime);

					// 判断会议时间是否重叠
					if (roomId!=0 && meetingDao.checkMeetingTimeIsRepeat(meeting)) {
						json.setRtState(false);
						json.setRtMsg("会议室已被占用,请重新修改时间！");
						return json;
					}

					int oldStatus = 0;
					if (isLeaderOpt.equals("1")) {// 是从会议管理过来的 ，就不修改状态
						oldStatus = meeting.getStatus();
					} else {
						
					}
					String mrName="";
					if(roomId==0){
						mrName=meeting.getWbMeetRoomName();
					}else{
						mrName=meeting.getMeetRoom().getMrName();
					}
					
					String isWriteCalendar = TeeStringUtil.getString(
							request.getParameter("isWriteCalendar"), "");// 是否写入日程安排
					String contentEb = "通知您于" + model.getStartTimeStr() + "在"
							+ mrName + "开会，会议名称："
							+ meeting.getMeetName();
					if ("1".equals(isWriteCalendar)) {
						TeeCalendarAffairModel calModel = new TeeCalendarAffairModel();
						calModel.setStartTime(meeting.getStartTime());
						calModel.setEndTime(meeting.getEndTime());
						calModel.setContent(contentEb);
						calModel.setActorIds(meeting.getAttendee());
						calModel.setPreHours(meeting.getResendHour());
						calModel.setPreMinutes(meeting.getResendMinute());
						calendarManagerI
								.createCalendar(meeting.getUser(), calModel);
					}
					// 审批人
//					if (!TeeUtility.isNullorEmpty(model.getManagerId())) {
//						TeePerson leader = personDao.get(TeeStringUtil
//								.getInteger(model.getManagerId(), 0));
//						if (leader != null) {
//							meeting.setManager(leader);
//						}
//					}
					// 会议纪要员
					if (!TeeUtility.isNullorEmpty(model.getRecorderId())) {
						TeePerson recorder = personDao.get(TeeStringUtil
								.getInteger(model.getRecorderId(), 0));
						if (recorder != null) {
							meeting.setRecorder(recorder);
						}
					}
					// 参会人员
					List<TeePerson> personList = new ArrayList<TeePerson>();
					if (!TeeUtility.isNullorEmpty(model.getAttendee())) {
						String userListIds = model.getAttendee();
						if (userListIds.endsWith(",")) {
							userListIds = userListIds.substring(0,
									userListIds.length() - 1);
						}
						Set<TeePerson> attendees = new HashSet();
						personList = personDao.getPersonByUuids(userListIds);

						if (personList != null && personList.size() > 0) {
							for (TeePerson person1 : personList) {
								attendees.add(person1);
							}
						}
						meeting.getAttendeeList().clear();
						meeting.setAttendeeList(attendees);
					}

					
					
					//出席领导
					List<TeePerson> leaderList = new ArrayList();
					if (!TeeUtility.isNullorEmpty(model.getAttendLeaderIds())) {
						String userListIds = model.getAttendLeaderIds();
						if (userListIds.endsWith(",")) {
							userListIds = userListIds.substring(0,
									userListIds.length() - 1);
						}
						Set<TeePerson> attendLeaderSet = new HashSet();
						leaderList = personDao
								.getPersonByUuids(userListIds);

						if (leaderList != null && leaderList.size() > 0) {
							for (TeePerson person1 : leaderList) {
								attendLeaderSet.add(person1);
							}
						}
						meeting.getAttendLeader().clear();
						meeting.setAttendLeader(attendLeaderSet);
					}
					// 申请人
					/*
					 * meeting.setUser(person); int userId =
					 * TeeStringUtil.getInteger(model.getUserId() , 0);
					 * if(userId > 0){//如果是系统管理员，且设置申请人不为空 TeePerson
					 * applayPerson = personDao.selectPersonById(userId);
					 * if(applayPerson != null){ meeting.setUser(applayPerson);
					 * } }
					 */

					meeting.setStatus(1);

					/* 附件处理 */
					MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
					List<TeeAttachment> attachments = upload.manyAttachUpload(
							multipartRequest, TeeAttachmentModelKeys.MEETINGATTEND);
					for (TeeAttachment attach : attachments) {
						attach.setModelId(String.valueOf(meeting.getSid()));
						simpleDaoSupport.update(attach);
					}

					meetingDao.updateAttendOut(meeting);

					if (meeting.getStatus() == 1) {
						// 发送内部短信 内部短信
						String smsRemind = meeting.getSmsRemind();
						if (smsRemind.equals("1")) {// 发送内部短信
							String userListIds = model.getAttendee();
							Calendar cl = Calendar.getInstance();
							cl.setTimeInMillis(meeting.getStartTime());

							if (meeting.getResendHour() > 0) {
								cl.add(Calendar.HOUR_OF_DAY,
										-meeting.getResendHour());
							}
							if (meeting.getResendMinute() > 0) {
								cl.add(Calendar.MINUTE,
										-meeting.getResendMinute());
							}
							Date date = new Date();
							// 开始时间
							date.setTime(meeting.getStartTime());
							SimpleDateFormat dateFormat2 = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm");
							String startTimeStr = dateFormat2.format(date);
							if (userListIds.length() > 0) {
								// 会议定时提醒
								TeeQuartzTask task = new TeeQuartzTask();
								task.setContent("请您在" + startTimeStr
										+ "准时参加会议！,其主题为："
										+ meeting.getMeetName());
								task.setModelId(String.valueOf(meeting.getSid()));
								task.setModelNo("031");
								task.setType(TeeQuartzTypes.ONCE);
								task.setUrl("/system/core/base/meeting/personal/meetingdetail.jsp?id="
										+ meeting.getSid());
								task.setUrl1("/system/core/base/meeting/personal/meetingdetail.jsp?id="
										+ meeting.getSid());
								task.setFrom(person.getUserId());
								task.setExp(TeeQuartzUtils
										.getOnceQuartzExpression(cl));
								task.setTo(userListIds);
								MessagePusher.push2Quartz(task);

								// 内部短信
								Map requestData = new HashMap();
								requestData.put("content",
										"主题为：" + meeting.getMeetName()
												+ "的会议已变动，请注意参会时间。");
								requestData.put("userListIds", userListIds);
								requestData.put("moduleNo", "031");
								requestData.put("remindUrl",
										"/system/core/base/meeting/personal/meetingdetail.jsp?id="
												+ meeting.getSid());
								smsManager.sendSms(requestData, person);
							}

							// 创建人员参会信息
							if (personList != null && personList.size() > 0) {
								meetingConfirmDao.deleteObjByMeetingId(String
										.valueOf(meeting.getSid()));
								for (TeePerson person1 : personList) {
									TeeMeetingAttendConfirm obj = new TeeMeetingAttendConfirm();
									obj.setAttendFlag(0);
									obj.setCreateTime(new Date());
									obj.setCreateUser(person1);
									obj.setReadFlag(0);
									obj.setMeetingId(meeting.getSid());
									meetingConfirmDao.save(obj);
								}
							}

						}
					} else if (meeting.getStatus() == 0) {
						Map requestData = new HashMap();
						requestData.put("content", "修改了会议申请,请批示！");
						String userListIds = model.getManagerId();// out.getLeader().getUuid()
																	// + "";
						requestData.put("userListIds", userListIds);
						// requestData.put("sendTime", );
						requestData.put("moduleNo", "031");
						requestData.put("remindUrl",
								"/system/core/base/meeting/leader/index.jsp");
						smsManager.sendSms(requestData, person);
					}

					// 创建日志
					TeeSysLog sysLog = TeeSysLog.newInstance();
					sysLog.setType("031B");
					logRemark = "修改会议申请，会议名称：" + meeting.getMeetName();
					sysLog.setRemark(logRemark);
					TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);

				}

			} else {
				// 请选择会议室
				json.setRtState(false);
				json.setRtMsg("未查到到相关会议信息！");
				return json;
			}
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
	}

	/**
	 * @author syl 新增 或者 更新
	 * @param request
	 * @param model
	 *            模型
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeMeetingModel model)
			throws Exception {
		// 日志
		TeeSysLog sysLog = TeeSysLog.newInstance();

		/* 附件处理 */
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<TeeAttachment> attachments = upload.manyAttachUpload(
				multipartRequest, TeeAttachmentModelKeys.MEETING);

		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		String isLeaderOpt = TeeStringUtil.getString(
				request.getParameter("isLeaderOpt"), "");// 是否从会议管理 审批 修改的， ，1-是
															// 其它-不是
		TeeJson json = new TeeJson();
		TeeMeeting meeting = new TeeMeeting();

		BeanUtils.copyProperties(model, meeting);
		// 审批人
		if (!TeeUtility.isNullorEmpty(model.getManagerId())) {
			TeePerson leader = personDao.get(TeeStringUtil.getInteger(
					model.getManagerId(), 0));
			if (leader != null) {
				meeting.setManager(leader);
			}
		}

		if (!TeeUtility.isNullorEmpty(model.getRecorderId())) {
			TeePerson recorder = personDao.get(TeeStringUtil.getInteger(
					model.getRecorderId(), 0));
			if (recorder != null) {
				meeting.setRecorder(recorder);
			}
		}

		if (!TeeUtility.isNullorEmpty(model.getAttendee())) {
			Set<TeePerson> attendees = new HashSet();
			String ids[] = model.getAttendee().split(",");
			for (int i = 0; i < ids.length; i++) {
				TeePerson p = personDao.get(Integer.parseInt(ids[i]));
				attendees.add(p);
			}
			meeting.getAttendeeList().clear();
			meeting.setAttendeeList(attendees);
		}

		// 处理申请时间
		Date dateTemp = new Date();
		meeting.setCreateTime(dateTemp.getTime());
		Date meetDate = TeeUtility.parseDate("yyyy-MM-dd",
				model.getMeetDateStr());
		String[] startTimeArr = model.getStartTimeStr().split(":");
		String[] endTimeArr = model.getEndTimeStr().split(":");
		long startTime = (TeeStringUtil.getInteger(startTimeArr[0], 0) * 60 * 60 + TeeStringUtil
				.getInteger(startTimeArr[1], 0) * 60) * 1000;
		long endTime = (TeeStringUtil.getInteger(endTimeArr[0], 0) * 60 * 60 + TeeStringUtil
				.getInteger(endTimeArr[1], 0) * 60) * 1000;
		meeting.setStartTime(meetDate.getTime() + startTime);
		meeting.setEndTime(meetDate.getTime() + endTime);

		// 会议室
		if (!TeeUtility.isNullorEmpty(model.getMeetRoomId())) {
			int roomId = TeeStringUtil.getInteger(model.getMeetRoomId(), 0);
			TeeMeetingRoom room = meetingRoomDao.getById(roomId);
			meeting.setMeetRoom(room);
		}

		// 申请人
		meeting.setUser(person);
		int userId = TeeStringUtil.getInteger(model.getUserId(), 0);
		if (userId > 0) {// 如果是系统管理员，且设置申请人不为空
			TeePerson applayPerson = personDao.selectPersonById(userId);
			if (applayPerson != null) {
				meeting.setUser(applayPerson);
			}
		}

		// 判断会议时间是否重叠
		if (meetingDao.checkMeetingTimeIsRepeat(meeting)) {
			json.setRtState(false);
			json.setRtMsg("会议室已被占用,请重新修改时间！");
			return json;
		}
		if (model.getSid() > 0) {
			TeeMeeting meetingOld = meetingDao.getById(model.getSid());
			if (meetingOld != null) {
				int oldStatus = 0;
				if (isLeaderOpt.equals("1")) {// 是从会议管理过来的 ，就不修改状态
					oldStatus = meetingOld.getStatus();
				} else {
					String isWriteCalendar = TeeStringUtil.getString(
							request.getParameter("isWriteCalendar"), "");// 是否写入日程安排

				}
				BeanUtils.copyProperties(model, meetingOld);
				meetingOld.setStatus(oldStatus);
				meetingOld.setManager(meeting.getManager());
				meetingOld.setRecorder(meeting.getRecorder());
				meetingOld.setStartTime(meeting.getStartTime());
				meetingOld.setEndTime(meeting.getEndTime());
				meetingOld.setMeetRoom(meeting.getMeetRoom());
				for (TeeAttachment attach : attachments) {
					attach.setModelId(String.valueOf(meetingOld.getSid()));
					simpleDaoSupport.update(attach);
				}
				meetingOld.setUser(meeting.getUser());
				meetingOld.setAttendeeList(meeting.getAttendeeList());
				meetingDao.updateAttendOut(meetingOld);
				sysLog.setType("031B");
				logRemark = "修改会议申请，会议名称：" + meetingOld.getMeetName();

				SimpleDateFormat dateFormat2 = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm");
				if (meetingOld.getStatus() == 1) {
					// 发送内部短信 内部短信
					String smsRemind = meeting.getSmsRemind();
					if (smsRemind.equals("1")) {// 发送内部短信
						String userListIds = model.getAttendee();
						Calendar cl = Calendar.getInstance();
						cl.setTimeInMillis(meeting.getStartTime());

						if (meeting.getResendHour() > 0) {
							cl.add(Calendar.HOUR_OF_DAY,
									-meeting.getResendHour());
						}
						if (meeting.getResendMinute() > 0) {
							cl.add(Calendar.MINUTE, -meeting.getResendMinute());
						}
						Date date = new Date();
						// 开始时间
						date.setTime(meeting.getStartTime());
						String startTimeStr = dateFormat2.format(date);
						if (userListIds.length() > 0) {
							// 会议定时提醒
							TeeQuartzTask task = new TeeQuartzTask();
							task.setContent("请您在" + startTimeStr
									+ "准时参加会议！,其主题为：" + meeting.getMeetName());
							task.setModelId(String.valueOf(meeting.getSid()));
							task.setModelNo("031");
							task.setType(TeeQuartzTypes.ONCE);
							task.setUrl("/system/core/base/meeting/personal/meetingdetail.jsp?id="
									+ meeting.getSid());
							task.setUrl1("/system/core/base/meeting/personal/meetingdetail.jsp?id="
									+ meeting.getSid());
							task.setFrom(person.getUserId());
							task.setExp(TeeQuartzUtils
									.getOnceQuartzExpression(cl));
							task.setTo(userListIds);
							MessagePusher.push2Quartz(task);

							Map requestData = new HashMap();
							requestData.put("content",
									"主题为：" + meeting.getMeetName()
											+ "的会议已变动，请注意参会时间。");
							requestData.put("userListIds", userListIds);
							requestData.put("moduleNo", "031");
							requestData.put("remindUrl",
									"/system/core/base/meeting/personal/meetingdetail.jsp?id="
											+ meeting.getSid());
							smsManager.sendSms(requestData, person);
						}

					}
				} else if (meetingOld.getStatus() == 0) {
					Map requestData = new HashMap();
					requestData.put("content", "提交会议申请,请批示！");
					String userListIds = model.getManagerId();// out.getLeader().getUuid()
																// + "";
					requestData.put("userListIds", userListIds);
					// requestData.put("sendTime", );
					requestData.put("moduleNo", "031");
					requestData.put("remindUrl",
							"/system/core/base/meeting/leader/index.jsp");
					smsManager.sendSms(requestData, person);
				}
			} else {
				json.setRtState(false);
				json.setRtMsg("未查到到相关会议信息！");
				return json;
			}
		} else {
			meeting.setStatus(0);
			meetingDao.addAttendOut(meeting);

			for (TeeAttachment attach : attachments) {
				attach.setModelId(String.valueOf(meeting.getSid()));
				simpleDaoSupport.update(attach);
			}

			sysLog.setType("031A");
			logRemark = "新建会议申请，会议名称：" + meeting.getMeetName();

			Map requestData = new HashMap();
			requestData.put("content", "提交会议申请,请批示！");
			String userListIds = model.getManagerId();// out.getLeader().getUuid()
														// + "";
			requestData.put("userListIds", userListIds);
			// requestData.put("sendTime", );
			requestData.put("moduleNo", "031");
			requestData.put("remindUrl",
					"/system/core/base/meeting/leader/index.jsp");
			smsManager.sendSms(requestData, person);
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");

		// 创建日志
		sysLog.setRemark(logRemark);
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		return json;
	}

	public TeeJson addSummary(HttpServletRequest request, TeeMeetingModel model)
			throws Exception {
		/* 附件处理 */
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<TeeAttachment> attachments = upload.manyAttachUpload(
				multipartRequest, TeeAttachmentModelKeys.MEETINGRECORD);
		TeeJson json = new TeeJson();
		TeeMeeting meetingOld = meetingDao.getById(model.getSid());
		if (meetingOld != null) {
			meetingOld.setSubmary(model.getSubmary());
			meetingOld.setAttendeeNotIds(model.getAttendeeNotIds());
			meetingOld.setAttendeeNotNames(model.getAttendeeNotNames());
			
			if (!TeeUtility.isNullorEmpty(model.getReadPeopleIds())) {
				Set<TeePerson> readPeoples = new HashSet();
				String ids[] = model.getReadPeopleIds().split(",");
				for (int i = 0; i < ids.length; i++) {
					TeePerson person = personDao.get(Integer.parseInt(ids[i]));
					readPeoples.add(person);
				}
				meetingOld.setReadPeoples(readPeoples);
			}
			for (TeeAttachment attach : attachments) {
				attach.setModelId(String.valueOf(meetingOld.getSid()));
				simpleDaoSupport.update(attach);
			}
			if(meetingOld.getSubmary()!=null||meetingOld.getReadPeoples()!=null||String.valueOf(meetingOld.getSid())!=null){
				meetingOld.setIsUpLoadSummary("已上传");
			}
			meetingDao.updateAttendOut(meetingOld);
		} else {
			json.setRtState(false);
			json.setRtMsg("未查到到相关会议信息！");
			return json;
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
	}

	/**
	 * 检查会议申请 时间是否重叠
	 * 
	 * @author syl
	 * @date 2014-4-27
	 * @param meeting
	 * @return
	 */
	public boolean checkMeetingTimeIsRepeat(TeeMeeting meeting) {
		return meetingDao.checkMeetingTimeIsRepeat(meeting);
	}

	/**
	 * 拖拉更改会议日期
	 * 
	 * @author syl
	 * @date 2014-2-15
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson updateChangeMeet(HttpServletRequest request,
			TeeMeetingModel model) {
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		// 处理申请时间

		if (model.getSid() > 0) {
			TeeMeeting meetingOld = meetingDao.getById(model.getSid());
			TeeMeeting meeting = new TeeMeeting();
			if (meetingOld != null) {
				meeting.setSid(meetingOld.getSid());
				meeting.setMeetRoom(meetingOld.getMeetRoom());
				meeting.setStartTime(model.getStartTime());
				meeting.setEndTime(model.getEndTime());
				// 判断会议时间是否重叠
				if (meetingDao.checkMeetingTimeIsRepeat(meeting)) {
					json.setRtState(false);
					json.setRtMsg("会议室已被占用,请重新修改时间！");
					return json;
				}
				meetingOld.setStartTime(model.getStartTime());
				meetingOld.setEndTime(model.getEndTime());
				meetingDao.updateAttendOut(meetingOld);
			} else {
				json.setRtState(false);
				json.setRtMsg("未查到到相关会议信息！");
				return json;
			}
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");

		String smsRemind = TeeStringUtil.getString(
				request.getParameter("smsRemind"), "0");
		if (smsRemind.equals("1")) {// 发送内部短信
			Map requestData = new HashMap();
			requestData.put("content", "提交会议申请,请批示！");
			String userListIds = "";// out.getLeader().getUuid() + "";
			requestData.put("userListIds", userListIds);
			// requestData.put("sendTime", );
			requestData.put("moduleNo", "031");
			requestData.put("remindUrl",
					"/system/core/base/meeting/leader/index.jsp");
			smsManager.sendSms(requestData, person);
		}
		return json;
	}

	/**
	 * 获取当前登录人会议申请记录 BY status 状态
	 * 
	 * @author syl
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getPersonalMeetByStatus(HttpServletRequest request,
			TeeMeetingModel model) {
		TeeJson json = new TeeJson();
		// 自动执行更改状态
		autoUpdateMeetStatus(request, model);
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		List<TeeMeeting> list = meetingDao.getPersonalMeetByStatus(person,
				model);
		List<TeeMeetingModel> listModel = new ArrayList<TeeMeetingModel>();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i), true));
		}
		json.setRtData(listModel);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 通用列表
	 * 
	 * @param dm
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getPersonalMeetByStatusEasyui(TeeDataGridModel dm,
			HttpServletRequest request, TeeMeetingModel model)
			throws ParseException {
		//获取会议室类型
		int roomType=TeeStringUtil.getInteger(request.getParameter("type"), 1);
		
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		j.setTotal(meetingDao.getQueryCountByStatus(loginPerson, model,roomType));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		Object parm[] = {};
		List<TeeMeeting> list = meetingDao.getMeetPageFindByStatus(loginPerson,firstIndex,
				dm.getRows(), dm, model,roomType);// 查
		List<TeeMeetingModel> modelList = new ArrayList<TeeMeetingModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeMeetingModel modeltemp = parseModel(list.get(i), true);
				
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * 获取当前登录人会议申请记录 BY status 状态
	 * 
	 * @author syl
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getLeaderMeetByStatus(HttpServletRequest request,
			TeeMeetingModel model) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		// 自动执行更改状态
		autoUpdateMeetStatus(request, model);
		List<TeeMeeting> list = meetingDao.getLeaderMeetByStatus(person, model);
		List<TeeMeetingModel> listModel = new ArrayList<TeeMeetingModel>();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i), true));
		}
		json.setRtData(listModel);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 通用列表
	 * 
	 * @param dm
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getLeaderMeetByStatusEasyui(TeeDataGridModel dm,
			HttpServletRequest request, TeeMeetingModel model)
			throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		j.setTotal(meetingDao.getLeaderQueryCountByStatus(loginPerson, model));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		Object parm[] = {};
		List<TeeMeeting> list = meetingDao.getLeaderMeetPageFindByStatus(loginPerson,firstIndex,
				dm.getRows(), dm, model);// 查
		List<TeeMeetingModel> modelList = new ArrayList<TeeMeetingModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeMeetingModel modeltemp = parseModel(list.get(i), true);
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * 获取系统当前登录人 所有申请会议记录
	 * 
	 * @author syl
	 * @date 2013-12-24
	 * @param request
	 * @param person
	 *            系统当前登录人
	 * @return
	 * @throws ParseException
	 */
	public TeeJson getPersonalAllMeet(HttpServletRequest request,
			TeeMeetingModel model) throws ParseException {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				"LOGIN_USER");
		// 自动执行更改状态
		autoUpdateMeetStatus(request, model);

		String startTimeStr = model.getStartTimeStr();
		String endTimeStr = model.getEndTimeStr();
		Date endDate = TeeUtility.parseDate(endTimeStr + " 00:00:00");
		Date startDate = TeeUtility.parseDate(startTimeStr + " 00:00:00");
		String view = TeeStringUtil.getString(request.getParameter("view"),
				"week");// 默认为周视图

		Date currTime = new Date();
		// 日程model
		List<TeeFullCalendarModel> listModel = new ArrayList<TeeFullCalendarModel>();

		/*
		 * Date fromDate = TeeUtility.parseDate("yyyy-MM-dd", startTimeStr);
		 * Date toDate = TeeUtility.parseDate("yyyy-MM-dd", endTimeStr);
		 * Calendar[] dateList = TeeDateUtil.getDaySpanCalendar(fromDate,
		 * toDate);
		 */// 周期性
		List<TeeMeeting> list = meetingDao.selectPersonalByTime(person,
				startDate, endDate, model);
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseFullCalendarModel(list.get(i), person));
		}
		json.setRtState(true);
		json.setRtData(listModel);
		return json;
	}

	/**
	 * 获取所有会议 byTime
	 * 
	 * @author syl
	 * @date 2014-2-17
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	public TeeJson getAllMeetByTime(HttpServletRequest request,
			TeeMeetingModel model) throws ParseException {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				"LOGIN_USER");
		// 自动执行更改状态
		autoUpdateMeetStatus(request, model);

		String startTimeStr = model.getStartTimeStr();
		String endTimeStr = model.getEndTimeStr();
		Date startDate = TeeUtility.parseDate(startTimeStr + " 00:00:00");
		Date endDate = TeeUtility.parseDate(startTimeStr + " 23:59:59");
		// 日程model
		List<TeeMeetingModel> listModel = new ArrayList<TeeMeetingModel>();
		List<TeeMeeting> list = meetingDao.selectAllByTime(person, startDate,
				endDate, model);
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i), true));
		}
		json.setRtState(true);
		json.setRtData(listModel);
		return json;
	}

	/***
	 * 将日程转换为 fullCalendar对象
	 * 
	 * @author syl
	 * @date 2014-1-5
	 * @param cal
	 * @return
	 */
	public TeeFullCalendarModel parseFullCalendarModel(TeeMeeting meeting,
			TeePerson loginUser) {
		TeeFullCalendarModel calModel = new TeeFullCalendarModel();
		calModel.setId(meeting.getSid());
		calModel.setTitle(meeting.getMeetName());
		calModel.setAllDay(false);
		calModel.setClassName("fc-event-color");
		Date date = new Date();
		date.setTime(meeting.getStartTime());// 设置开始时间
		calModel.setStart(TeeDateUtil.format(date));
		calModel.setStartTime(calModel.getStart());

		date.setTime(meeting.getEndTime());// 设置结束时间时间
		calModel.setEnd(TeeDateUtil.format(date));
		calModel.setEndTime(calModel.getEnd());

		calModel.setEditable(false);
		calModel.setDeleteable(false);

		if (meeting.getStatus() == 0 || meeting.getStatus() == 3) {// 待批准和未批准
			calModel.setEditable(true);
			calModel.setDeleteable(true);
		}
		if (meeting.getUser().getUuid() != loginUser.getUuid()) {// 不是自己申请的
			calModel.setEditable(false);
			calModel.setDeleteable(false);
		}

		if (meeting.getStatus() == 1) {
			calModel.setClassName("fc-event-color3");
		} else if (meeting.getStatus() == 2) {
			calModel.setClassName("fc-event-color1");
		} else if (meeting.getStatus() == 3) {
			calModel.setClassName("fc-event-color4");
		} else if (meeting.getStatus() == 4) {
			calModel.setClassName("fc-event-color6");
		}

		return calModel;
	}

	/**
	 * 对象转换
	 * 
	 * @author syl
	 * @date 2014-1-29
	 * @param out
	 * @param isSimp
	 * @return
	 */
	public TeeMeetingModel parseModel(TeeMeeting out, boolean isSimp) {
		TeeMeetingModel model = new TeeMeetingModel();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (out == null) {
			return null;
		}
		BeanUtils.copyProperties(out, model);
		model.setUserId(out.getUser().getUuid() + "");
		model.setUserName(out.getUser().getUserName());
		String managerId = "";
		String managerName = "";
		int recorderId = 0;
		String recorderName = "";
		if (out.getManager() != null) {
			managerId = out.getManager().getUuid() + "";
			managerName = out.getManager().getUserName();
		}
		if (out.getRecorder() != null) {
			recorderId = out.getRecorder().getUuid();
			recorderName = out.getRecorder().getUserName();
		}
		if (out.getReadPeoples() != null) {
			String readPeopleIds = "";
			String readPeopleNames = "";
			for (Iterator it = out.getReadPeoples().iterator(); it.hasNext();) {
				TeePerson person = (TeePerson) it.next();
				readPeopleIds = readPeopleIds + "," + person.getUuid();
				readPeopleNames = readPeopleNames + "," + person.getUserName();
			}
			if (readPeopleIds.startsWith(",")) {
				readPeopleIds = readPeopleIds.substring(1);
			}
			if (readPeopleNames.startsWith(",")) {
				readPeopleNames = readPeopleNames.substring(1);
			}
			model.setReadPeopleIds(readPeopleIds);
			model.setReadPeopleNames(readPeopleNames);
		}
		model.setManagerId(managerId);
		model.setManagerName(managerName);
		model.setRecorderId(recorderId);
		model.setRecorderName(recorderName);
		Date date = new Date();
		date.setTime(out.getCreateTime());
		model.setCreateTimeStr(dateFormat.format(date));
		// 开始时间
		date.setTime(out.getStartTime());
		String startTimeStr = dateFormat2.format(date);
		// model.setStartTimeStr(startTimeStr.substring(11,
		// startTimeStr.length()));
		model.setStartTimeStr(startTimeStr);
		// 结束时间
		date.setTime(out.getEndTime());
		String endTimeStr = dateFormat2.format(date);
		// model.setEndTimeStr(endTimeStr.substring(11, endTimeStr.length()));
		model.setEndTimeStr(endTimeStr);
		model.setMeetDateStr(startTimeStr.substring(0, 10));

		// 内部出席人
		if (!TeeUtility.isNullorEmpty(out.getAttendee())) {
			String[] userInfo = personDao.getPersonNameAndUuidByUuids(out
					.getAttendee());
			model.setAttendee(userInfo[0]);
			model.setAttendeeName(userInfo[1]);
		} else {
			model.setAttendee("");
			model.setAttendeeName("");
		}
		if (!TeeUtility.isNullorEmpty(out.getEquipmentIds())) {
			model.setEquipmentIds(out.getEquipmentIds());
		} else {
			model.setEquipmentIds("");
			model.setEquipmentNames("");
		}
		// 内部出席人
		if (!TeeUtility.isNullorEmpty(out.getAttendee())) {
			String[] userInfo = personDao.getPersonNameAndUuidByUuids(out
					.getAttendee());
			model.setAttendee(userInfo[0]);
			model.setAttendeeName(userInfo[1]);
		} else {
			model.setAttendee("");
			model.setAttendeeName("");
		}

		//出席领导
		if (!TeeUtility.isNullorEmpty(out.getAttendLeaderIds())) {
			String[] userInfo = personDao.getPersonNameAndUuidByUuids(out.getAttendLeaderIds());
			model.setAttendLeaderIds(userInfo[0]);
			model.setAttendLeaderNames(userInfo[1]);
		} else {
			model.setAttendLeaderIds("");
			model.setAttendLeaderNames("");
		}

		
		if (!TeeUtility.isNullorEmpty(out.getAttendeeNotIds())) {
			String[] userInfo = personDao.getPersonNameAndUuidByUuids(out
					.getAttendeeNotIds());
			model.setAttendeeNotIds(userInfo[0]);
			model.setAttendeeNotNames(userInfo[1]);
		} else {
			model.setAttendeeNotIds("");
			model.setAttendeeNotNames("");
		}

		if (!TeeUtility.isNullorEmpty(out.getSubmary())) {
			model.setSubmary(out.getSubmary());
		} else {
			model.setSubmary("");
		}
		// 会议室
		TeeMeetingRoom room = out.getMeetRoom();
		String meetRoomId = "";
		String meetRoomName = "";
		if (room != null) {
			meetRoomId = room.getSid() + "";
			meetRoomName = room.getMrName();
		}else{
			meetRoomId="0";
			meetRoomName=out.getWbMeetRoomName()+"(外部)";
		}
		model.setMeetRoomId(meetRoomId);
		model.setMeetRoomName(meetRoomName);

		// 附件
		if (!isSimp) {
			List<TeeAttachment> attaches = attachmentDao.getAttaches(
					TeeAttachmentModelKeys.MEETINGATTEND,
					String.valueOf(out.getSid()));
			List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
			for (TeeAttachment attach : attaches) {
				TeeAttachmentModel m = new TeeAttachmentModel();
				BeanUtils.copyProperties(attach, m);
				m.setUserId(attach.getUser().getUuid() + "");
				m.setUserName(attach.getUser().getUserName());
				m.setPriv(1 + 2 + 4 + 8 + 16 + 32);// 一共五个权限好像
													// 1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
				attachmodels.add(m);
			}
			model.setAttacheModels(attachmodels);
		}

		return model;
	}

	/**
	 * 
	 * @author syl 删除ById
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	@TeeLoggingAnt(template = "删除会议名称为：{logModel.meetName}", type = "031C")
	public TeeJson deleteByIdService(HttpServletRequest request,
			TeeMeetingModel model) {
		TeeJson json = new TeeJson();
		String meetName = "";
		if (model.getSid() > 0) {
			TeeMeeting meeting = meetingDao.getById(model.getSid());
			if (meeting == null) {
				json.setRtState(false);
				json.setRtMsg("该会议已被删除！");
				return json;
			}
			meetName = meeting.getMeetName();
			meetingConfirmDao.deleteObjByMeetingId(String.valueOf(meeting
					.getSid()));
			meetingDao.deleteByObj(meeting);
		}
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		TeeRequestInfoContext.getRequestInfo().getLogModel()
				.put("meetName", meetName);// 添加其他参数
		return json;
	}

	/**
	 * 
	 * @author syl 查询 ById
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request, TeeMeetingModel model) {
		
		//获取当前登录人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		String type = TeeStringUtil.getString(request.getParameter("type"), "0");
		TeeJson json = new TeeJson();
		if (model.getSid() > 0) {
			TeeMeeting out = meetingDao.getById(model.getSid());
			
			if (out != null) {
				model = parseModel(out, false);
				if (type.equals("0")) {
					
					List<TeeAttachment> attaches = attachmentDao.getAttaches(
							TeeAttachmentModelKeys.MEETINGATTEND,
							String.valueOf(out.getSid()));
					
					List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
					for (TeeAttachment attach : attaches) {
						
						TeeAttachmentModel m = new TeeAttachmentModel();
						BeanUtils.copyProperties(attach, m);
						m.setSizeDesc(TeeFileUtility.getFileSizeDesc(attach.getSize()));
						
						m.setUserId(attach.getUser().getUuid() + "");
						m.setUserName(attach.getUser().getUserName());
						m.setPriv(1 + 2);// 一共五个权限好像
											// 1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
						attachmodels.add(m);
					}
					model.setAttacheModels(attachmodels);
				}
				
				//判断当前登录的人是不是参会人  有没有确认参会
				if(!TeeUtility.isNullorEmpty(model.getAttendee())){
				    String [] array=model.getAttendee().split(",");
					for (int k = 0; k < array.length; k++) {
						if(Integer.parseInt(array[k])==loginUser.getUuid()){//当前登录人是参会人
							model.setIsAttend(1);
							break;
						}
						model.setIsAttend(0);//不是参会人	
					}
					
				}else{//不是参会人员
					model.setIsAttend(0);
				}
				
				
				//判断是否已经确认参会了
				List<TeeMeetingAttendConfirm> list1 = meetingConfirmDao.getConfrimObjList(model.getSid(), loginUser);
				int confirmFlag = 0;
				if(list1 != null && list1.size()>0){
					confirmFlag = 1;
				}
				model.setIsConfirm(confirmFlag);	
				model.setWbMeetRoomName(out.getWbMeetRoomName());
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关会议记录！");
		return json;
	}
	
	/**
	 * 查询会议纪要
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getById2(HttpServletRequest request, TeeMeetingModel model) {
		
		//获取当前登录人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		String type = TeeStringUtil.getString(request.getParameter("type"), "0");
		TeeJson json = new TeeJson();
		if (model.getSid() > 0) {
			TeeMeeting out = meetingDao.getById(model.getSid());
			if (out != null) {
				model = parseModel(out, false);
				if (type.equals("0")) {
					
					List<TeeAttachment> attaches = attachmentDao.getAttaches(
							TeeAttachmentModelKeys.MEETINGRECORD,
							String.valueOf(out.getSid()));
					
					List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
					for (TeeAttachment attach : attaches) {
						
						TeeAttachmentModel m = new TeeAttachmentModel();
						BeanUtils.copyProperties(attach, m);
						m.setSizeDesc(TeeFileUtility.getFileSizeDesc(attach.getSize()));
						
						m.setUserId(attach.getUser().getUuid() + "");
						m.setUserName(attach.getUser().getUserName());
						m.setPriv(1 + 2);// 一共五个权限好像
											// 1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
						attachmodels.add(m);
					}
					model.setAttacheModels(attachmodels);
				}
				
				//判断当前登录的人是不是参会人  有没有确认参会
				if(!TeeUtility.isNullorEmpty(model.getAttendee())){
				    String [] array=model.getAttendee().split(",");
					for (int k = 0; k < array.length; k++) {
						if(Integer.parseInt(array[k])==loginUser.getUuid()){//当前登录人是参会人
							model.setIsAttend(1);
							break;
						}
						model.setIsAttend(0);//不是参会人	
					}
					
				}else{//不是参会人员
					model.setIsAttend(0);
				}
				
				
				//判断是否已经确认参会了
				List<TeeMeetingAttendConfirm> list1 = meetingConfirmDao.getConfrimObjList(model.getSid(), loginUser);
				int confirmFlag = 0;
				if(list1 != null && list1.size()>0){
					confirmFlag = 1;
				}
				model.setIsConfirm(confirmFlag);	
				
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关会议记录！");
		return json;
	}

	/**
	 * 审批
	 * 
	 * @author syl
	 * @date 2014-2-5
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson approve(HttpServletRequest request, TeeMeetingModel model) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		TeeMeeting meeting = meetingDao.getById(model.getSid());
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		json.setRtState(true);
		if (meeting != null) {
			Map requestData = new HashMap();
			// requestData.put("sendTime", );
			requestData.put("userListIds", model.getUserId());
			requestData.put("moduleNo", "031");
			requestData.put("remindUrl",
					"/system/core/base/meeting/personal/index.jsp");
			if (model.getStatus() == 1) {// 批准

				String userListIds = TeeUtility.null2Empty(meeting
						.getAttendee());// out.getLeader().getUuid() + "";
				if (userListIds.endsWith(",")) {
					userListIds = userListIds.substring(0,
							userListIds.length() - 1);
				}

				// 判断会议时间是否重叠
				if (meetingDao.checkMeetingTimeIsRepeat(meeting)) {
					json.setRtState(false);
					json.setRtMsg("会议室已被占用,请重新修改时间！");
					return json;
				}
				requestData.put("content", "你的会议申请已批准,请查看!");

				Date date = new Date();
				// 开始时间
				date.setTime(meeting.getStartTime());
				String startTimeStr = dateFormat2.format(date);
				// model.setStartTimeStr(startTimeStr.substring(11,
				// startTimeStr.length()));
				model.setStartTimeStr(startTimeStr);
				// 结束时间
				date.setTime(meeting.getEndTime());
				String endTimeStr = dateFormat2.format(date);
				// model.setEndTimeStr(endTimeStr.substring(11,
				// endTimeStr.length()));
				model.setEndTimeStr(endTimeStr);
				// 写入日程安排
				int isWriteCalendar = meeting.getIsWriteCalendar();
				String contentEb = "通知您于" + model.getStartTimeStr() + "在"
						+ meeting.getMeetRoom().getMrName() + "开会，会议名称："
						+ meeting.getMeetName();

				if (isWriteCalendar == 1) {
					TeeCalendarAffairModel calModel = new TeeCalendarAffairModel();
					calModel.setStartTime(meeting.getStartTime());
					calModel.setEndTime(meeting.getEndTime());
					calModel.setContent(contentEb);
					calModel.setActorIds(meeting.getAttendee());
					calModel.setPreHours(meeting.getResendHour());
					calModel.setPreMinutes(meeting.getResendMinute());
					calendarManagerI
							.createCalendar(meeting.getUser(), calModel);
				}

				// 发送内部短信 内部短信
				String smsRemind = meeting.getSmsRemind();
				if (smsRemind.equals("1")) {// 发送内部短信

					Map requestData2 = new HashMap();
					requestData2.put("content", contentEb);

					requestData2.put("userListIds", userListIds);
					Calendar cl = Calendar.getInstance();
					cl.setTimeInMillis(meeting.getStartTime());

					if (meeting.getResendHour() > 0) {
						cl.add(Calendar.HOUR_OF_DAY, -meeting.getResendHour());
					}
					if (meeting.getResendMinute() > 0) {
						cl.add(Calendar.MINUTE, -meeting.getResendMinute());
					}

					/*
					 * String smsRemindTime =dateFormat2.format(cl.getTime());
					 * 
					 * //long smsRemindTime = (meeting.getResendHour() * 60 * 60
					 * + meeting.getResendMinute() * 60 ) * 1000;
					 * requestData2.put("sendTime", smsRemindTime+":00");
					 * requestData2.put("moduleNo", "031" );
					 * requestData2.put("remindUrl",
					 * "/system/core/base/meeting/personal/meetingdetail.jsp?id="
					 * + meeting.getSid()); smsManager.sendSms(requestData2,
					 * person);
					 */
					requestData2.put("moduleNo", "031");
					requestData2.put("remindUrl",
							"/system/core/base/meeting/personal/meetingdetail.jsp?id="
									+ meeting.getSid());
					smsManager.sendSms(requestData2, person);

					if (!TeeUtility.isNullorEmpty(userListIds)
							&& userListIds.length() > 0) {
						String[] ids = userListIds.split(",");
						StringBuffer sb = new StringBuffer();
						for (int i = 0; i < ids.length; i++) {
							TeePerson p = personDao.get(Integer
									.parseInt(ids[i]));
							if (p != null) {
								sb.append(p.getUserId() + ",");
							}
						}
						sb.deleteCharAt(sb.length() - 1);
						// 会议定时提醒
						TeeQuartzTask task = new TeeQuartzTask();
						task.setContent("您" + startTimeStr + "有一个会议需要参加，其主题为："
								+ meeting.getMeetName());
						task.setModelId(String.valueOf(meeting.getSid()));
						task.setModelNo("031");
						task.setType(TeeQuartzTypes.ONCE);
						task.setUrl("/system/core/base/meeting/personal/meetingdetail.jsp?id="
								+ meeting.getSid());
						task.setUrl1("/system/core/base/meeting/personal/meetingdetail.jsp?id="
								+ meeting.getSid());
						task.setFrom(person.getUserId());
						task.setExp(TeeQuartzUtils.getOnceQuartzExpression(cl));
						task.setTo(sb.toString());
						MessagePusher.push2Quartz(task);
					}

				}

				// 创建人员参会信息
				if (!TeeUtility.isNullorEmpty(userListIds)
						&& userListIds.length() > 0) {
					List<TeePerson> personList = personDao
							.getPersonByUuids(userListIds);
					if (personList != null && personList.size() > 0) {
						for (TeePerson person1 : personList) {
							TeeMeetingAttendConfirm obj = new TeeMeetingAttendConfirm();
							obj.setAttendFlag(0);
							obj.setCreateTime(new Date());
							obj.setCreateUser(person1);
							obj.setReadFlag(0);
							obj.setMeetingId(meeting.getSid());
							meetingConfirmDao.save(obj);
						}
					}
				}

			} else if (model.getStatus() == 3) {// 不批准
				requestData.put("content", "你的会议申请未批准,请查看!");
			} else if (model.getStatus() == 4) {// 结束
				requestData.put("content", "你的会议已结束，请查看!");

			}

			meetingDao.updateStatus(person, model);
			model = parseModel(meeting, true);
			smsManager.sendSms(requestData, person);

		} else {
			json.setRtState(false);
			json.setRtMsg("该会议可能已被删除！");
		}
		return json;
	}

	/**
	 * 自动更改 状态 “已审批准” 改成 “进行中” 。“进行中” 改成 “结束”
	 * 
	 * @author syl
	 * @date 2014-2-15
	 * @param request
	 * @param model
	 */
	public void autoUpdateMeetStatus(HttpServletRequest request,
			TeeMeetingModel model) {
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		Date currDate = new Date();
		long currTime = currDate.getTime();
		/* “已批准” 和 “进行中” */
		List<TeeMeeting> list = meetingDao.getAutoLeaderMeetByStatus(person,
				model);
		for (int i = 0; i < list.size(); i++) {
			TeeMeeting meet = list.get(i);
			if (meet.getStatus() == 1) {// 已批准
				if (currTime > meet.getEndTime()) {// 如果当前时间比结束时间还大，直接更改成结束
					model.setStatus(4);
					model.setSid(meet.getSid());
					meetingDao.autoUpdateStatus(person, model);
					continue;
				}
				if (currTime > meet.getStartTime()) {
					model.setStatus(2);
					model.setSid(meet.getSid());
					meetingDao.autoUpdateStatus(person, model);
				}
			} else if (meet.getStatus() == 2) {// 进行中
				if (currTime > meet.getEndTime()) {
					model.setStatus(4);
					model.setSid(meet.getSid());
					meetingDao.autoUpdateStatus(person, model);
				}
			}
		}
	}

	/**
	 * 获取各种状态审批数量
	 * 
	 * @author syl
	 * @date 2014-2-15
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getLeaderApproveCount(HttpServletRequest request,
			TeeMeetingModel model) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);

		// 自动执行更改状态
		autoUpdateMeetStatus(request, model);

		// 待审批
		model.setStatus(0);
		long applyCount = meetingDao.getLeaderApproveCount(person, model);

		// 已批准
		model.setStatus(1);
		long approveCount = meetingDao.getLeaderApproveCount(person, model);

		// 进行中
		model.setStatus(2);
		long IngCount = meetingDao.getLeaderApproveCount(person, model);

		// 未批准
		model.setStatus(3);
		long noApproveCount = meetingDao.getLeaderApproveCount(person, model);

		// 已结束
		model.setStatus(4);
		long endCount = meetingDao.getLeaderApproveCount(person, model);
		Map map = new HashMap();
		map.put("meetCount0", applyCount);
		map.put("meetCount1", approveCount);
		map.put("meetCount2", IngCount);
		map.put("meetCount3", noApproveCount);
		map.put("meetCount4", endCount);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}

	/**
	 * 通用列表
	 * 
	 * @param dm
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,
			HttpServletRequest request, TeeMeetingModel model)
			throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		j.setTotal(meetingDao.getQueryCount(loginPerson, model));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		Object parm[] = {};
		List<TeeMeeting> list = meetingDao.getMeetPageFind(firstIndex,
				dm.getRows(), dm, model);// 查
		List<TeeMeetingModel> modelList = new ArrayList<TeeMeetingModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeMeetingModel modeltemp = parseModel(list.get(i), true);
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	public void setMeetingDao(TeeMeetingDao meetingDao) {
		this.meetingDao = meetingDao;
	}

	public void setSmsManager(TeeSmsManager smsManager) {
		this.smsManager = smsManager;
	}

//	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getMyMeeting(TeeDataGridModel dm,
			HttpServletRequest request, TeeMeetingModel model) throws Exception {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER,loginPerson);

		// 自动执行更改状态
		autoUpdateMeetStatus(request, model);

		j.setTotal(meetingDao.getMyMeetingTotal(requestDatas));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		Object parm[] = {};
		List<TeeMeeting> list = meetingDao.getMyMeeting(firstIndex,
				dm.getRows(), dm, requestDatas);// 查
		List<TeeMeetingModel> modelList = new ArrayList<TeeMeetingModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeMeetingModel modeltemp = parseModel(list.get(i), true);
				if(!TeeUtility.isNullorEmpty(modeltemp.getAttendee())){
				    String [] array=modeltemp.getAttendee().split(",");
					for (int k = 0; k < array.length; k++) {
						if(Integer.parseInt(array[k])==loginPerson.getUuid()){//当前登录人是参会人
							modeltemp.setIsAttend(1);
							break;
						}
						modeltemp.setIsAttend(0);//不是参会人	
					}
					
				}else{//不是参会人员
					modeltemp.setIsAttend(0);
				}
				
				
				//判断是否已经确认参会了
				List<TeeMeetingAttendConfirm> list1 = meetingConfirmDao.getConfrimObjList(modeltemp.getSid(), loginPerson);
				int confirmFlag = 0;
				if(list1 != null && list1.size()>0){
					confirmFlag = 1;
				}
				modeltemp.setIsConfirm(confirmFlag);	
				
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getSummary(TeeDataGridModel dm,
			HttpServletRequest request, TeeMeetingModel model) throws Exception {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession()
				.getAttribute(TeeConst.LOGIN_USER));
		j.setTotal(meetingDao.getSummaryTotal(requestDatas));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		Object parm[] = {};
		List<TeeMeeting> list = meetingDao.getSummary(firstIndex, dm.getRows(),
				dm, requestDatas);// 查
		List<TeeMeetingModel> modelList = new ArrayList<TeeMeetingModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeMeetingModel modeltemp = parseModel(list.get(i), true);
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	public TeeJson getMeetingTopic(HttpServletRequest request, int meetingSid) {
		TeeJson json = new TeeJson();
		List<TeeMeetingTopicModel> topicList = meetingTopicDao
				.getMeetingTopic(meetingSid);
		if (null != topicList) {
			json.setRtData(topicList);
			json.setRtState(true);
			json.setRtMsg("查询成功!");
		} else {
			json.setRtState(false);
			json.setRtMsg("查询失败!");
		}

		return json;
	}

	public TeeJson addMeetingTopic(HttpServletRequest request, int meetingSid,
			String topicContent, TeePerson loginPerson) {
		TeeMeetingTopic topic = new TeeMeetingTopic();
		TeeJson json = new TeeJson();
		TeeMeeting meeting = meetingDao.getById(meetingSid);
		topic.setContent(topicContent);
		topic.setCrUser(loginPerson);
		topic.setMeeting(meeting);
		Calendar cl = Calendar.getInstance();
		topic.setCrTime(cl);
		meetingTopicDao.addMeetingTopic(topic);
		json.setRtState(true);
		json.setRtMsg("添加成功!");
		return json;
	}

	public List<TeeMeetingModel> getMeetingByRoomId(int roomId) {
		List<TeeMeetingModel> modelList = new ArrayList<TeeMeetingModel>();
		List<TeeMeeting> list = meetingDao.getMeetingByRoomId(roomId);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeMeetingModel modeltemp = parseModel(list.get(i), true);
				modelList.add(modeltemp);
			}
		}
		return modelList;
	}

	/**
	 * leiqisheng
	 * 
	 * @param request
	 * @param meetingSid
	 * @return
	 */

	public TeeMeetingModel selectMeeting(int meetingSid) {
		String hql = "from TeeMeeting tm  where tm.sid=" + meetingSid;
		TeeMeeting tm = (TeeMeeting) simpleDaoSupport.unique(hql, null); //返回 对象 Unique();
		// List<TeeMeetingModel> modelList = new ArrayList();
		TeeMeetingModel tmm = new TeeMeetingModel();
		tmm = parseModel(tm, true);
		String userListIds = tm.getAttendee();
		List<TeePerson> personList = personDao.getPersonByUuids(userListIds);
		String ids = "";
		String names = "";
		for (TeePerson lists : personList) {
			ids += lists.getUuid() + ",";
			names += lists.getUserName() + ",";
		}
		tmm.setAttendee(ids);
		tmm.setAttendeeName(names);
		return tmm;

	}

	/**
	 * 撤销已审批的会议
	 * 
	 * @param meetingSid
	 * @return
	 */
	public TeeJson cancel(HttpServletRequest request, int meetingSid,String reason) {
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		if (meetingSid > 0) {
			// 根据会议的主键获取会议对象
			TeeMeeting meet = meetingDao.get(meetingSid);
			// 获取会议的内部参会人员id字符串
			String attendee = meet.getAttendee();
			// 获取审批人员的主键
			int managerId = meet.getManager().getUuid();
			// 给参会人员和审批人员发送撤销提醒
			String userListIds = attendee +","+ managerId;
			String smsRemind = meet.getSmsRemind();

			Map requestData = new HashMap();
			requestData.put("content", "会议：" + meet.getMeetName() + "已被撤销！撤销原因："+reason);
			requestData.put("userListIds", userListIds);
			requestData.put("moduleNo", "031");
			smsManager.sendSms(requestData, person);

			// 删除会议
			meetingDao.deleteByObj(meet);
			json.setRtState(true);
			json.setRtMsg("撤销成功！");
		}
		return json;
	}
	
	/**
	 * 获取会议室房间的审批人
	 * @param roomId
	 * @return
	 */
	public List<Map<String,String>> getMeetroomAuditors(int roomId){
		List<Map<String,String>> list = new ArrayList();
		
		String meetingManager = TeeSysProps.getString("MEETING_MANAGER_TYPE");
		List<TeePerson> persons = personDao.getPersonByUuids(meetingManager);
		for(TeePerson p:persons){
			Map data = new HashMap();
			data.put("id", p.getUuid());
			data.put("name", p.getUserName());
			list.add(data);
		}
		
		return list;
	}

	/**
	 * 结束会议室的使用
	 * */
	public TeeJson jieShuMeeting(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		int sbStatus=TeeStringUtil.getInteger(request.getParameter("sbStatus"),0);
		TeeMeeting meeting = meetingDao.get(sid);
		if(meeting!=null){
			Date date=new Date();
			long time = date.getTime();
			if(time<meeting.getEndTime()){
				meeting.setEndTime(time);
			}
			TeeMeetingRoom meetRoom = meeting.getMeetRoom();
			if(meetRoom!=null){
				meetRoom.setSbStatus(sbStatus);
				meetingRoomDao.update(meetRoom);//修改会议室中的设备状态
			}
			//meeting.setSbStatus(sbStatus);
			meeting.setStatus(4);
			meetingDao.update(meeting);
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		return json;
	}
}