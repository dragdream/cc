package com.tianee.oa.mobile.meeting.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.base.meeting.bean.TeeMeeting;
import com.tianee.oa.core.base.meeting.bean.TeeMeetingAttendConfirm;
import com.tianee.oa.core.base.meeting.bean.TeeMeetingRoom;
import com.tianee.oa.core.base.meeting.dao.TeeMeetingAttendConfirmDao;
import com.tianee.oa.core.base.meeting.dao.TeeMeetingDao;
import com.tianee.oa.core.base.meeting.model.TeeMeetingModel;
import com.tianee.oa.core.base.vehicle.bean.TeeVehicleUsage;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.mobile.meeting.dao.TeeMobileMeetingDao;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeMobileMeetingService extends TeeBaseService{

	 @Autowired
	 private TeeMobileMeetingDao mobileMeetingDao;
	 @Autowired
	 private TeeAttachmentDao attachmentDao;
	 @Autowired
	 private TeePersonDao personDao; 
	 @Autowired
	 private TeeMeetingDao meetingDao;
	 @Autowired
	 private TeeMeetingAttendConfirmDao meetingConfirmDao;
	/**
	 * 获取有当前登陆人审批的所有的会议
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeEasyuiDataGridJson getLeaderMeetAllStatus(TeeDataGridModel dm,
			HttpServletRequest request, TeeMeetingModel model) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		
		String hql="from TeeMeeting where manager = ?   order by  createTime desc";
		List param = new ArrayList();
		param.add(loginPerson);
		j.setTotal(mobileMeetingDao.countByList("select count(*) "+hql, param));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeMeeting> list = mobileMeetingDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
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
		}
		model.setMeetRoomId(meetRoomId);
		model.setMeetRoomName(meetRoomName);

		// 附件
		if (!isSimp) {
			List<TeeAttachment> attaches = attachmentDao.getAttaches(
					TeeAttachmentModelKeys.MEETING,
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
	 * 获取所有由当前登陆人申请的或者参与的会议（状态为：已批准   进行中   已结束）
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeEasyuiDataGridJson getMyMeetByStatus(TeeDataGridModel dm,
			HttpServletRequest request, TeeMeetingModel model) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER,loginPerson);

		// 自动执行更改状态
		autoUpdateMeetStatus(request, model);

		j.setTotal(mobileMeetingDao.getMyMeetingTotalByStatus(requestDatas));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		Object parm[] = {};
		List<TeeMeeting> list = mobileMeetingDao.getMyMeetingByStatus(firstIndex,
				dm.getRows(), dm, requestDatas);// 查
		List<TeeMeetingModel> modelList = new ArrayList<TeeMeetingModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Map map=new HashMap();
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

}
