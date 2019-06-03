package com.tianee.oa.core.base.meeting.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.base.meeting.bean.TeeMeeting;
import com.tianee.oa.core.base.meeting.bean.TeeMeetingRoom;
import com.tianee.oa.core.base.meeting.dao.TeeMeetingDao;
import com.tianee.oa.core.base.meeting.dao.TeeMeetingRoomDao;
import com.tianee.oa.core.base.meeting.model.TeeMeetingModel;
import com.tianee.oa.core.base.meeting.model.TeeMeetingRoomModel;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class TeeMeetingRoomService  extends TeeBaseService{
	@Autowired
	private TeeMeetingRoomDao meetingDao;
	
	@Autowired
	private TeePersonDao personDao;

	@Autowired
	private TeeDeptDao deptDao;
	
	@Autowired
	private TeeMeetingService meetDao;
	
	@Autowired
	private TeeMeetingDao mDao;
	/**
	 * @author syl
	 * 新增 或者 更新
	 * @param message
	 * @param person 系统当前登录人
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeMeetingRoomModel model) {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		TeeSysLog sysLog = TeeSysLog.newInstance();
	  
	       
		TeeMeetingRoom room = new TeeMeetingRoom();
		BeanUtils.copyProperties(model, room);
		if(!TeeUtility.isNullorEmpty(model.getPostDeptIds())){//申请权限会议室  ---部门
			List<TeeDepartment> listDept = deptDao.getDeptListByUuids(model.getPostDeptIds());
			room.setPostDept(listDept);
		}
		if(!TeeUtility.isNullorEmpty(model.getPostUserIds())){//申请权限会议室 -- 人员
			List<TeePerson> listDept = personDao.getPersonByUuids(model.getPostUserIds());
			room.setPostUser(listDept);
		}
		if(model.getSid() > 0){
			TeeMeetingRoom meetRoom  = meetingDao.getById(model.getSid());
			String OldMrName = meetRoom.getMrName();
			if(meetRoom != null){
				BeanUtils.copyProperties(room, meetRoom);
				
				meetingDao.updateAttendOut(meetRoom);
				sysLog.setType("031F");
			  	sysLog.setRemark("更新会议室将【 "+OldMrName+" 改为 " + meetRoom.getMrName() + "】");
			}else{
				json.setRtState(false);
				json.setRtMsg("该会议室可能已被删除！");
				return json;
			}
		}else{
			sysLog.setType("031E");
			sysLog.setRemark("新建会议室名称为 " + room.getMrName());
			meetingDao.addAttendOut(room);
		}
		//创建日志
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		
		return json;
	}
	
	/**
	 * 获取所有会议室
	 * @author syl
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getAllRoom(HttpServletRequest request, TeeMeetingRoomModel model) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<TeeMeetingRoom> list = meetingDao.getAllRoom(person, model);
		List<TeeMeetingRoomModel> listModel = new ArrayList<TeeMeetingRoomModel> ();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i)));
		}
		json.setRtData(listModel);
		json.setRtState(true);
		return json;
	}
	
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getAllRoomEasyui(TeeDataGridModel dm,
			HttpServletRequest request, TeeMeetingRoomModel model)
			throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		j.setTotal(meetingDao.getCountAllRoom(loginPerson, model));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		Object parm[] = {};
		List<TeeMeetingRoom> list = meetingDao.getAllRoomPageFind(loginPerson,firstIndex,
				dm.getRows(), dm, model);// 查
		List<TeeMeetingRoomModel> modelList = new ArrayList<TeeMeetingRoomModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeMeetingRoomModel modeltemp = parseModel(list.get(i));
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	
	
	/**
	 * 获取有权限的会议室
	 * @author syl
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson selectPostMeetRoom(HttpServletRequest request, TeeMeetingRoomModel model) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取会议室类型
		int type=TeeStringUtil.getInteger(request.getParameter("type"),0);
		List<TeeMeetingRoom> list = meetingDao.selectPostMeetRoom(person, model,type);
		List<TeeMeetingRoomModel> listModel = new ArrayList<TeeMeetingRoomModel> ();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseSimpleModel(list.get(i)));
		}
		json.setRtData(listModel);
		json.setRtState(true);
		return json;
	}
	

	/**
	 * 对象转换
	 * @author syl
	 * @date 2014-1-29
	 * @param out
	 * @return
	 */
	public TeeMeetingRoomModel parseModel(TeeMeetingRoom room){
		TeeMeetingRoomModel model = new TeeMeetingRoomModel();
		if(room == null){
			return model;
		}
		BeanUtils.copyProperties(room, model);
		List<TeeDepartment> listDept = room.getPostDept();
		List<TeePerson> userList = room.getPostUser();
		String postDeptIds = "";
		String postDeptNames = "";
		String postUserIds = "";
		String postUserNames = "";
	    if(listDept != null){
	    	for (int i = 0; i < listDept.size(); i++) {
	    		postDeptIds = postDeptIds + listDept.get(i).getUuid() + ",";
	    		postDeptNames = postDeptNames + listDept.get(i).getDeptName() + ",";
			}
	    }
	    
	    if(userList != null){
	    	for (int i = 0; i < userList.size(); i++) {
	    		postUserIds = postUserIds + userList.get(i).getUuid() + ",";
	    		postUserNames = postUserNames + userList.get(i).getUserName() + ",";
			}
	    }
	    
	    
	    if(room.getType()==1){
	    	model.setTypeDesc("会议室");
	    }else if(room.getType()==2){
	    	model.setTypeDesc("询问室");
	    }
	    if(room.getSbStatus()==0){
	    	model.setSbStatusName("正常");
	    }else{
	    	model.setSbStatusName("异常");
	    }
	    model.setPostDeptIds(postDeptIds);
	    model.setPostDeptNames(postDeptNames);
	    model.setPostUserIds(postUserIds);
	    model.setPostUserNames(postUserNames);
	    String[] personInfo = personDao.getPersonNameAndUuidByUuids(room.getManagerIds());
	    model.setManagerIds(personInfo[0]);
	    model.setManagerNames(personInfo[1]);
		return model;
	}
	
	
	
	/**
	 * 对象转换
	 * @author syl
	 * @date 2014-1-29
	 * @param out
	 * @return
	 */
	public TeeMeetingRoomModel parseSimpleModel(TeeMeetingRoom room){
		TeeMeetingRoomModel model = new TeeMeetingRoomModel();
		if(room == null){
			return model;
		}
		BeanUtils.copyProperties(room, model);
		List<TeeDepartment> listDept = room.getPostDept();
		List<TeePerson> userList = room.getPostUser();
		String postDeptIds = "";
		String postDeptNames = "";
		String postUserIds = "";
		String postUserNames = "";
	    if(listDept != null){
	    	for (int i = 0; i < listDept.size(); i++) {
	    		postDeptIds = postDeptIds + listDept.get(i).getUuid() + ",";
	    		postDeptNames = postDeptNames + listDept.get(i).getDeptName() + ",";
			}
	    }
	    
	    if(userList != null){
	    	for (int i = 0; i < userList.size(); i++) {
	    		postUserIds = postUserIds + userList.get(i).getUuid() + ",";
	    		postUserNames = postUserNames + userList.get(i).getUserName() + ",";
			}
	    }
	    model.setPostDeptIds(postDeptIds);
	    model.setPostDeptNames(postDeptNames);
	    model.setPostUserIds(postUserIds);
	    model.setPostUserNames(postUserNames);
	   
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
	@TeeLoggingAnt(template="删除会议室名称为：{logModel.mrName}",type="031G")
	public TeeJson deleteByIdService(HttpServletRequest request, TeeMeetingRoomModel model) {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		String  mrName = "";
		if(model.getSid() > 0){
			TeeMeetingRoom room = meetingDao.getById(model.getSid());
			if(room == null){
				json.setRtState(false);
				json.setRtMsg("该会议室已被删除!");
				return json;
			}
			mrName = room.getMrName();
			
			//删除该会议室下面的所有申请
			simpleDaoSupport.executeUpdate("delete from TeeMeeting where meetRoom.sid="+model.getSid(), null);
			
			meetingDao.deleteByObj(room);
		}
		TeeRequestInfoContext.getRequestInfo().getLogModel().put("mrName", mrName);//添加其他参数
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}
	
	/**
	 * 
	 * @author syl 删除所有会议室
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	@TeeLoggingAnt(template="删除所有会议室",type="031G")
	public TeeJson deleteAllService(HttpServletRequest request, TeeMeetingRoomModel model) {
		TeeJson json = new TeeJson();
		meetingDao.delAll();
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}
	
	
	/**
	 * 
	 * @author syl 
	 *  查询 ById
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request, TeeMeetingRoomModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeMeetingRoom out = meetingDao.getById(model.getSid());
			if(out !=null){
				model = parseModel(out);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("该会议室可能已被删除！");
		return json;
	}

	public TeeJson getRecordById(int roomId) {
		TeeJson json = new TeeJson();
		List<TeeMeetingModel> list = meetDao.getMeetingByRoomId(roomId);
		if(null!=list && list.size()>0){
			json.setRtState(true);
			json.setRtData(list);
			json.setRtMsg("成功获取会议记录！");
		}else{
			json.setRtState(false);
			json.setRtMsg("没有找到相关会议记录！");
		}
		return json;
	}

	
	/**
	 * 判断会议室下是否存在正在进行或者有预定的会议。
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson isExistUnfinishedMeeting(int sid) {
		TeeJson json=new TeeJson();
		if(sid>0){
			//判断该会议室下是否存在正在申请(待审批，已通过 审批)，正在进行中的会议
			List<TeeMeeting> list=mDao.getUnfinishedMeeting(sid);
			if(null!=list && list.size()>0){
				json.setRtState(false);
			}else{
				json.setRtState(true);
			}
		}
		return json;
	}
	
	/**
	 * 获取占用情况
	 * @param roomId
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public List<double[]> getMeetroomUseage(int roomId,String date) throws Exception {
		List<double[]> list = new ArrayList();
		
		Date start = TeeDateUtil.parseDate(date+" 00:00:00");
		Date end = TeeDateUtil.parseDate(date+" 23:59:59");
		
		//获取指定日期下的会议室占用情况
		List<TeeMeeting> meetingInfo = simpleDaoSupport.find("from TeeMeeting where ?<=startTime and startTime<=? and meetRoom.sid="+roomId, new Object[]{start.getTime(),end.getTime()});
		for(TeeMeeting meeting:meetingInfo){
			Calendar s = Calendar.getInstance();
			s.setTimeInMillis(meeting.getStartTime());
			s.set(Calendar.SECOND, 0);
			if(s.get(Calendar.MINUTE)<30){
				s.set(Calendar.MINUTE, 0);
			}else{
				s.set(Calendar.MINUTE, 30);
			}
			
			Calendar e = Calendar.getInstance();
			e.setTimeInMillis(meeting.getEndTime());
			e.set(Calendar.SECOND, 0);
			if(e.get(Calendar.MINUTE)<30){
				e.set(Calendar.MINUTE, 0);
			}else{
				e.set(Calendar.MINUTE, 30);
			}
			double arr[] = new double[2];
			int minute = s.get(Calendar.MINUTE);
			if(minute==30){
				arr[0] = s.get(Calendar.HOUR_OF_DAY)+0.5;
			}else{
				arr[0] = s.get(Calendar.HOUR_OF_DAY)+0.0;
			}
			
			minute = e.get(Calendar.MINUTE);
			if(minute==30){
				arr[1] = e.get(Calendar.HOUR_OF_DAY)+0.5;
			}else{
				arr[1] = e.get(Calendar.HOUR_OF_DAY)+0.0;
			}
			list.add(arr);
			
//			for(Calendar tmp=s;tmp.getTimeInMillis()<=e.getTimeInMillis();tmp.add(Calendar.MINUTE, 30)){
//				int minute = tmp.get(Calendar.MINUTE);
//				if(minute==30){
//					list.add(tmp.get(Calendar.HOUR_OF_DAY)+0.5);
//				}else{
//					list.add(tmp.get(Calendar.HOUR_OF_DAY)+0.0);
//				}
//			}
			
		}
		
		return list;
	}
	
}