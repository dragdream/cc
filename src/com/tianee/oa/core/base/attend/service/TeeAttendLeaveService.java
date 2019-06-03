package com.tianee.oa.core.base.attend.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.base.attend.bean.TeeAttendLeave;
import com.tianee.oa.core.base.attend.bean.TeeAttendOut;
import com.tianee.oa.core.base.attend.dao.TeeAttendLeaveDao;
import com.tianee.oa.core.base.attend.model.TeeAttendLeaveModel;
import com.tianee.oa.core.base.attend.model.TeeAttendOvertimeModel;
import com.tianee.oa.core.base.attend.model.TeeAttendOutModel;
import com.tianee.oa.core.base.hr.settings.bean.TeeAnnualLeave;
import com.tianee.oa.core.base.hr.settings.dao.TeeAnnualLeaveDao;
import com.tianee.oa.core.base.meeting.model.TeeMeetingModel;
import com.tianee.oa.core.base.pm.bean.TeeHumanDoc;
import com.tianee.oa.core.base.pm.dao.TeeHumanDocDao;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.general.dao.TeeSysParaDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;
/**
 * 
 * @author syl
 *
 */
@Service
public class TeeAttendLeaveService  extends TeeBaseService {
	@Autowired
	private TeeAttendLeaveDao attendLeaveDao;
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeDeptDao deptDao;
	
	@Autowired
	private TeeSmsManager smsManager;
	
	@Autowired
	private TeeHumanDocDao humanDao;
	
	@Autowired
	private TeeSysParaDao sysParaDao;
	
	@Autowired
	private TeeAnnualLeaveDao leaveDao;
	
	@Autowired
	private TeeDeptService  deptService;
	/**
	 * @author syl
	 * 新增 或者 更新
	 * @param message
	 * @param person 系统当前登录人
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeAttendLeaveModel model) {
		TeeSysLog sysLog = TeeSysLog.newInstance();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		TeeAttendLeave leave = new TeeAttendLeave();
		if(!TeeUtility.isNullorEmpty(model.getLeaderId())){
			TeePerson leader = personDao.get(TeeStringUtil.getInteger(model.getLeaderId(), 0));
			if(leader != null){
				leave.setLeader(leader);
			}
		}
		long startTime = 0;
		long endTime = 0;
		if(model.getStartDate() != null){
			startTime = model.getStartDate().getTime();
		}
		if(model.getEndDate() != null){
			endTime = model.getEndDate().getTime();
		}
		leave.setStartTime(startTime);
		leave.setEndTime(endTime);
		if(model.getSid() > 0){
			TeeAttendLeave attendLeave  = attendLeaveDao.getById(model.getSid());
			if(attendLeave != null){
				BeanUtils.copyProperties(model, attendLeave);
				attendLeave.setLeader(leave.getLeader());
				attendLeave.setStartTime(leave.getStartTime());
				attendLeave.setEndTime(leave.getEndTime());
				leave.setSid(attendLeave.getSid());
				sysLog.setType("023G");
				sysLog.setRemark("修改请假申请,请假原因：" + attendLeave.getLeaveDesc());
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关请假信息！");
				return json;
			}
		}else{
			BeanUtils.copyProperties(model, leave);
			leave.setUser(person);
			leave.setStatus(0);
			leave.setAllow(0);
			leave.setCreateTime(new Date().getTime());
			attendLeaveDao.addAttendLeave(leave);
			sysLog.setType("023E");
			sysLog.setRemark("新建请假申请,请假原因：" + leave.getLeaveDesc());
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		String smsRemind = TeeStringUtil.getString(request.getParameter("smsRemind"), "0");
		if(smsRemind.equals("1")){//发送内部短信
			Map requestData = new HashMap();
			requestData.put("content", "提交请假申请，请批示！内容：" + model.getLeaveDesc());
			String userListIds = leave.getLeader().getUuid() + "";
			requestData.put("userListIds",userListIds );
			//requestData.put("sendTime", );
			requestData.put("moduleNo", "023");
			requestData.put("remindUrl", "/system/core/base/attend/manager/index.jsp?attend=leave");
			smsManager.sendSms(requestData, person);
		}
		//创建日志
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		return json;
	}
	
	/**
	 * 个人考勤  
	 * @author syl
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeEasyuiDataGridJson getLeave(HttpServletRequest request, TeeAttendLeaveModel model,TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int userId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
		if(userId>0){
			person=personDao.get(userId);
		}
		
		
		List<Object> param = new ArrayList<Object>();	
		String hql = "from TeeAttendLeave where user = ? and status = ?  order by  createTime desc";
		if( model.getStatus() == 0){//个人考勤---待审批
			hql = "from TeeAttendLeave where user = ? and (status = ? or status = 1)   order by  createTime desc";
		}
		if(model.getStatus()==9){
			param.add(person);
			hql = "from TeeAttendLeave where user = ? and allow = 1  order by  createTime desc";
		}else{
			param.add(person);
			param.add(model.getStatus());
		}
		
		// 设置总记录数
		json.setTotal(attendLeaveDao.countByList("select count(*) " + hql, param));		
		List<TeeAttendLeave> list = attendLeaveDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
		
		List<TeeAttendLeaveModel> listModel = new ArrayList<TeeAttendLeaveModel> ();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i)));
		}
		
		json.setRows(listModel);
		return json;
	}
	
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getLeaveEasyui(TeeDataGridModel dm,HttpServletRequest request,TeeAttendLeaveModel model)throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		int userId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
		if(userId>0){
			loginPerson=personDao.get(userId);
		}
		j.setTotal(attendLeaveDao.selectPersonalLeaveCount(loginPerson, model));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		Object parm[] = {};
		List<TeeAttendLeave> list = attendLeaveDao.selectPersonalLeavePage(loginPerson,firstIndex,
				dm.getRows(), dm, model);
		List<TeeAttendLeaveModel> modelList = new ArrayList<TeeAttendLeaveModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeAttendLeaveModel modeltemp = parseModel(list.get(i));
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
		
	}
	/**
	 * 对象转换
	 * @author syl
	 * @date 2014-1-29
	 * @param out
	 * @return
	 */
	public TeeAttendLeaveModel parseModel(TeeAttendLeave leave){
		TeeAttendLeaveModel model = new TeeAttendLeaveModel();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(leave == null){
			return null;
		}
		BeanUtils.copyProperties(leave, model);
		model.setUserId(leave.getUser().getUuid() + "");
		model.setUserName(leave.getUser().getUserName() + "");
		model.setDeptId(leave.getUser().getDept().getUuid() +"");
		model.setDeptName(leave.getUser().getDept().getDeptName());
		if(leave.getLeader() != null){
			model.setLeaderId(leave.getLeader().getUuid() + "");;
			model.setLeaderName(leave.getLeader().getUserName() + "");
		}
		Date date = new Date();
		date.setTime(leave.getCreateTime());
		model.setCreateTimeStr(dateFormat.format(date));
		
		date.setTime(leave.getStartTime());
		model.setStartTimeStr(dateFormat.format(date));
		
		date.setTime(leave.getEndTime());
		model.setEndTimeStr(dateFormat.format(date));
		
		if(leave.getDestroyTime() > 0){
			date.setTime(leave.getDestroyTime());
			model.setDestroyTimeStr(dateFormat.format(date));
		}else{
			model.setDestroyTimeStr("");
		}
		
		if(TeeUtility.isNullorEmpty(model.getReason())){
			model.setReason("");
		}
		if(!TeeUtility.isNullorEmpty(model.getLeaveType())){
			if(model.getLeaveType()==1){
				model.setLeaveTypeDesc("事假");
			}else if(model.getLeaveType()==2){
				model.setLeaveTypeDesc("病假");
			}else if(model.getLeaveType()==3){
				model.setLeaveTypeDesc("年假");
			}else if(model.getLeaveType()==4){
				model.setLeaveTypeDesc("其他");
			}else if(model.getLeaveType()==5){
				model.setLeaveTypeDesc("工伤假");
			}else if(model.getLeaveType()==6){
				model.setLeaveTypeDesc("婚假");
			}else if(model.getLeaveType()==7){
				model.setLeaveTypeDesc("丧假");
			}else if(model.getLeaveType()==8){
				model.setLeaveTypeDesc("产假");
			}else if(model.getLeaveType()==9){
				model.setLeaveTypeDesc("探亲假");
			}else if(model.getLeaveType()==10){
				model.setLeaveTypeDesc("公假");
			}
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
	@TeeLoggingAnt(template="删除请假申请， {logModel.leaveName}",type="023H")
	public TeeJson deleteByIdService(HttpServletRequest request, TeeAttendLeaveModel model) {
		TeeJson json = new TeeJson();
		String leaveName = "";
		if(model.getSid() > 0){
			TeeAttendLeave leave  = attendLeaveDao.getById(model.getSid());
			if(leave == null){
				json.setRtState(false);
				json.setRtMsg("该请假申请未找到!");
				leaveName = "该请假申请未找到！";
			}else{
				attendLeaveDao.deleteByObj(leave);
				json.setRtState(true);
				json.setRtMsg("删除成功!");
				leaveName = "请假原因为：" + leave.getLeaveDesc() ;
			}
			
		}
		TeeRequestInfoContext.getRequestInfo().getLogModel().put("leaveName", leaveName);
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
	public TeeJson getById(HttpServletRequest request, TeeAttendLeaveModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeAttendLeave leave = attendLeaveDao.getById(model.getSid());
			if(leave !=null){
				model = parseModel(leave);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关外出记录！");
		return json;
	}
	
	/**
	 * 
	 * @author syl 
	 *  申请销毁
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson destroyApply(HttpServletRequest request, TeeAttendLeaveModel model) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		model.setStatus(1);
		model.setDestroyDate(new Date());
		attendLeaveDao.destroyApply(person ,model);
		Map requestData = new HashMap();
		requestData.put("content", "提交请假销假申请，请批示！");
		String userListIds = model.getLeaderId();
		requestData.put("userListIds",userListIds );
		//requestData.put("sendTime", );
		requestData.put("moduleNo", "023");
		requestData.put("remindUrl", "/system/core/base/attend/manager/index.jsp?attend=leave");
		smsManager.sendSms(requestData, person);
		
		json.setRtState(true);
		return json;
	}
	
	
	
	
	 /* 请假审批管理*/
	
		/**
		 * 获取出差待审批记录
		 * @author syl
		 * @date 2014-1-29
		 * @param request
		 * @param model
		 * @return
		 */
		public TeeEasyuiDataGridJson getLeaderLeave(HttpServletRequest request, TeeAttendLeaveModel model,TeeDataGridModel dm) {
			TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
			TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			
			List<Object> param = new ArrayList<Object>();	
			String hql = "from TeeAttendLeave where leader = ? and ((status = 0 and  allow = 0) or status = 1 ) order by  createTime desc";
			param.add(person);
			
			// 设置总记录数
			json.setTotal(attendLeaveDao.countByList("select count(*) " + hql, param));		
			List<TeeAttendLeave> list = attendLeaveDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
			
			List<TeeAttendLeaveModel> listModel = new ArrayList<TeeAttendLeaveModel> ();
			for (int i = 0; i < list.size(); i++) {
				listModel.add(parseModel(list.get(i)));
			}
			json.setRows(listModel);
			return json;
		}
		
		
		/**
		 * 获取请假审批记录
		 * @author syl
		 * @date 2014-1-29
		 * @param request
		 * @param model
		 * @return
		 */
		public TeeEasyuiDataGridJson getLeaderApprovLeave(HttpServletRequest request, TeeAttendLeaveModel model,TeeDataGridModel dm) {
			TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
			TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			
			
			List<Object> param = new ArrayList<Object>();	
			String hql = "from TeeAttendLeave where leader = ? and allow <> ? order by  createTime desc";
			param.add(person);
			param.add(model.getAllow());
			// 设置总记录数
			json.setTotal(attendLeaveDao.countByList("select count(*) " + hql, param));		
			List<TeeAttendLeave> list = attendLeaveDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
			
			List<TeeAttendLeaveModel> listModel = new ArrayList<TeeAttendLeaveModel> ();
			for (int i = 0; i < list.size(); i++) {
				listModel.add(parseModel(list.get(i)));
			}
			json.setRows(listModel);
			
			return json;
		}
		
		
		/**
		 * 审批  批准或者不批准
		 * @author syl
		 * @date 2014-2-5
		 * @param request
		 * @param model
		 * @return
		 */
		public TeeJson approve(HttpServletRequest request, TeeAttendLeaveModel model) {
			TeeJson json = new TeeJson();
			TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			attendLeaveDao.approve(person, model);
			
			Map requestData = new HashMap();
			requestData.put("userListIds",model.getUserId() );
			requestData.put("moduleNo", "023");
			requestData.put("remindUrl", "/system/core/base/attend/leave/detail.jsp?id="+model.getSid());
			//requestData.put("sendTime", );
			if(model.getAllow() == 1){//批准	
				requestData.put("content", "您的请假已批准,请查看!");
			}else if(model.getAllow() == 2){//不批准
				requestData.put("content", "您的请假申请未批准,请查看!");
			}
			smsManager.sendSms(requestData, person);
			json.setRtState(true);
			return json;
		}
		
		/**
		 * 审批   销假
		 * @author syl
		 * @date 2014-2-5
		 * @param request
		 * @param model
		 * @return
		 */
		public TeeJson destroyLeave(HttpServletRequest request, TeeAttendLeaveModel model) {
			TeeJson json = new TeeJson();
			TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			attendLeaveDao.destroyLeave(person, model);
			Map requestData = new HashMap();
			requestData.put("content", "同意请假销假，请查收！");
			String userListIds = model.getUserId();
			requestData.put("userListIds",userListIds );
			//requestData.put("sendTime", );
			requestData.put("moduleNo", "023");
			requestData.put("remindUrl", "/system/core/base/attend/leave/detail.jsp?id=" + model.getSid());
			smsManager.sendSms(requestData, person);
			json.setRtState(true);
			return json;
		}

		public TeeJson getLeaveByCondition(HttpServletRequest request,
				TeeAttendLeaveModel model) throws Exception {
			TeeJson json = new TeeJson();
			int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
			String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
			String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
			List<TeeAttendLeave> list = attendLeaveDao.getLeaveByCondition(deptId+"",startDateDesc,endDateDesc);
			List<TeeAttendLeaveModel> listModel = new ArrayList<TeeAttendLeaveModel> ();
			for (int i = 0; i < list.size(); i++) {
				listModel.add(parseModel(list.get(i)));
			}
			json.setRtData(listModel);
			json.setRtState(true);
			return json;
		}
		
		@Transactional(readOnly = true)
		public TeeEasyuiDataGridJson getLeaveByConditionEasyui(TeeDataGridModel dm,HttpServletRequest request,TeeAttendLeaveModel model)throws ParseException {
			TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
			//int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
			String deptIds=TeeStringUtil.getString(request.getParameter("deptIds"));
			String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
			String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
			List<TeeAttendLeave> list =null;
			if(!TeeUtility.isNullorEmpty(deptIds)){//指定部门
				j.setTotal(attendLeaveDao.getLeaveByConditionCount(deptIds,startDateDesc,endDateDesc));// 设置总记录数
				int firstIndex = 0;
				firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
				Object parm[] = {};
				list = attendLeaveDao.getLeaveByConditionPage(deptIds,startDateDesc,endDateDesc,firstIndex,
						dm.getRows(), dm);
			}else{//所有有可见权限的部门
				List <TeeDepartment>listDept = deptService.getDeptsByLoginUser(loginUser);
				j.setTotal(attendLeaveDao.getViewPrivLeaveByConditionCount(listDept,startDateDesc,endDateDesc));// 设置总记录数
				int firstIndex = 0;
				firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
				Object parm[] = {};
				list = attendLeaveDao.getViewPrivLeaveByConditionPage(listDept,startDateDesc,endDateDesc,firstIndex,
						dm.getRows(), dm);
				
			}

			List<TeeAttendLeaveModel> modelList = new ArrayList<TeeAttendLeaveModel>();
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					TeeAttendLeaveModel modeltemp = parseModel(list.get(i));
					modelList.add(modeltemp);
				}
			}
			j.setRows(modelList);// 设置返回的行
			return j;
			
		}

		
		/**
		 * @author nieyi
		 * 获取年假信息
		 * @param request
		 * @return
		 */
		public TeeJson getAnnualLeaveInfo(HttpServletRequest request) {
			TeeJson json = new TeeJson();
			Map map = new HashMap();
			TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			//获取当前登录人的入职时间
			TeeHumanDoc humanInfo = humanDao.getHumanDocInfo(person);
			double totalAnnualLeaveDays = 0;//总年假天数
			double remainLeaveDays=0;//剩余年假天数
			double usedDays = 0;
			if(!TeeUtility.isNullorEmpty(humanInfo)){
				//判断是否启用了年假规则，启用就动态判断其总年假天数，否则获取其默认年假天数
				int  isOpen = TeeStringUtil.getInteger(sysParaDao.getSysPara("HR_ANNUAL_LEAVE_RULE_OPEN").getParaValue(), 0);
				if(isOpen==1){
					int joinYears = 0;
					Calendar curDate = Calendar.getInstance();
					if(!TeeUtility.isNullorEmpty(humanInfo.getJoinDate())){
						Calendar joinDate = humanInfo.getJoinDate();
						//获取入职年限 
						long years = ((curDate.getTimeInMillis()-joinDate.getTimeInMillis())/(1000*3600*24))/365;
						joinYears = Integer.parseInt(String.valueOf(years));//入职年限为0 代表其入职不到一年
					}
					List<TeeAnnualLeave> ruleList =leaveDao.getObjList();
					for(TeeAnnualLeave leave :ruleList){
						if(joinYears==0 && leave.getYearCount()==1){
							totalAnnualLeaveDays=leave.getVacationDays();
							break;
						}else{
							if(joinYears <= leave.getYearCount()){
								totalAnnualLeaveDays=leave.getVacationDays();
								break;
							}
						}
					}
				}else{
					double defaultAnnualLeaveDays = humanInfo.getDefaultAnnualLeaveDays();
					totalAnnualLeaveDays = defaultAnnualLeaveDays;
				}
				//获取人事档案中，当前人设置的默认年假天数
				//获取当前用户已经用了多少天年假
				if(!TeeUtility.isNullorEmpty(humanInfo.getJoinDate())){
					usedDays = attendLeaveDao.getUsedAnnualLeaveDays(person,humanInfo.getJoinDate());
				}else{
					usedDays = attendLeaveDao.getUsedAnnualLeaveDays(person,Calendar.getInstance());
				}
				
			}else{
				totalAnnualLeaveDays=0;
				usedDays = 0;
			}
			remainLeaveDays = totalAnnualLeaveDays - usedDays;
			remainLeaveDays=remainLeaveDays<0?0:remainLeaveDays;
			map.put("totalAnnualLeaveDays",totalAnnualLeaveDays);
			map.put("remainLeaveDays",remainLeaveDays);
			json.setRtData(map);
			json.setRtState(true);
			json.setRtMsg("数据获取成功！");
			return json;
		}
		
		/**
		 * 根据person查看用了几天年假
		 * @param request
		 * @return
		 */
		public TeeJson getUsedAnnualLeaveDays(HttpServletRequest request) {
			TeeJson json = new TeeJson();
			Map map = new HashMap();
			int userId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
			TeePerson person = (TeePerson)personDao.get(userId);
			//获取当前登录人的入职时间
			TeeHumanDoc humanInfo = humanDao.getHumanDocInfo(person);
			double totalAnnualLeaveDays = 0;//总年假天数
			double remainLeaveDays=0;//剩余年假天数
			double usedDays = 0;
			if(!TeeUtility.isNullorEmpty(humanInfo)){
				//判断是否启用了年假规则，启用就动态判断其总年假天数，否则获取其默认年假天数
				int  isOpen = TeeStringUtil.getInteger(sysParaDao.getSysPara("HR_ANNUAL_LEAVE_RULE_OPEN").getParaValue(), 0);
				if(isOpen==1){
					int joinYears = 0;
					Calendar curDate = Calendar.getInstance();
					if(!TeeUtility.isNullorEmpty(humanInfo.getJoinDate())){
						Calendar joinDate = humanInfo.getJoinDate();
						//获取入职年限 
						long years = ((curDate.getTimeInMillis()-joinDate.getTimeInMillis())/(1000*3600*24))/365;
						joinYears = Integer.parseInt(String.valueOf(years));//入职年限为0 代表其入职不到一年
					}
					List<TeeAnnualLeave> ruleList =leaveDao.getObjList();
					for(TeeAnnualLeave leave :ruleList){
						if(joinYears==0 && leave.getYearCount()==1){
							totalAnnualLeaveDays=leave.getVacationDays();
							break;
						}else{
							if(joinYears <= leave.getYearCount()){
								totalAnnualLeaveDays=leave.getVacationDays();
								break;
							}
						}
					}
				}else{
					double defaultAnnualLeaveDays = humanInfo.getDefaultAnnualLeaveDays();
					totalAnnualLeaveDays = defaultAnnualLeaveDays;
				}
				//获取人事档案中，当前人设置的默认年假天数
				//获取当前用户已经用了多少天年假
				if(!TeeUtility.isNullorEmpty(humanInfo.getJoinDate())){
					usedDays = attendLeaveDao.getUsedAnnualLeaveDays(person,humanInfo.getJoinDate());
				}
				
			}else{
				totalAnnualLeaveDays=0;
				usedDays = 0;
			}
			remainLeaveDays = totalAnnualLeaveDays - usedDays;
			map.put("totalAnnualLeaveDays",totalAnnualLeaveDays);
			map.put("remainLeaveDays",remainLeaveDays);
			json.setRtData(map);
			json.setRtState(true);
			json.setRtMsg("数据获取成功！");
			return json;
		}
		
		/**
		 * 根据条件查询出年假使用情况
		 * @author nieyi
		 * @param request
		 * @return
		 */
		public TeeJson getAnnualLeaveByCondition(HttpServletRequest request) {
			TeeJson json = new TeeJson();
			int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
			String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
			String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
			List<Map> mapList = new ArrayList<Map>();
			List<TeePerson> personList = new ArrayList<TeePerson>();
			if(deptId>0){
				TeeDepartment dept = (TeeDepartment)deptDao.get(deptId);
				String  deptLevel = dept.getDeptParentLevel();
				if(TeeUtility.isNullorEmpty(deptLevel)){//如果是第一级部门
					deptLevel = dept.getGuid();
				}
			    personList = personDao.selectDeptAndChildDeptPerson(deptId,deptLevel);
			}else{
				personList = personDao.getAllUserNoDelete();
			}
			if(personList.size()>0){
				for(TeePerson person:personList){
					Map map = new HashMap();
					map = getUsedAnnualLeaveDays(person);
					mapList.add(map);
				}
			}
			json.setRtData(mapList);
			json.setRtState(true);
			return json;
		}
		
		@Transactional(readOnly = true)
		public TeeEasyuiDataGridJson getAnnualLeaveByConditionEasyui(TeeDataGridModel dm,HttpServletRequest request)throws ParseException {
			TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
			//int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
			String deptIds=TeeStringUtil.getString(request.getParameter("deptIds"));
			String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
			String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
			List<Map> mapList = new ArrayList<Map>();
			List<TeePerson> personList = new ArrayList<TeePerson>();
			int firstIndex = 0;
			firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
			if(!TeeUtility.isNullorEmpty(deptIds)){
				String[] deptArray=deptIds.split(",");
				TeeDepartment dept =null;
				long total=0l;
				for (String id : deptArray) {
					int deptId=TeeStringUtil.getInteger(id, 0);
					dept = (TeeDepartment)deptDao.get(deptId);
					String  deptLevel = dept.getDeptParentLevel();
					if(TeeUtility.isNullorEmpty(deptLevel)){//如果是第一级部门
						deptLevel = dept.getGuid();
					}
					total+=personDao.selectDeptAndChildDeptPersonCount(deptId,deptLevel);
					personList.addAll(personDao.selectDeptAndChildDeptPersonPage(deptId,deptLevel,firstIndex,dm.getRows(), dm));
				}
				
				Collections.sort(personList,new Comparator<TeePerson>() {

					@Override
					public int compare(TeePerson arg0, TeePerson arg1) {
						TeeDepartment dept0 = arg0.getDept();
						TeeDepartment dept1 = arg1.getDept();
						int userNo0 = TeeStringUtil.getInteger(arg0.getUserNo(), 0);
						int userNo1 =TeeStringUtil.getInteger(arg1.getUserNo(), 0);
						
						if(dept0.getDeptNo()==dept1.getDeptNo()){
							if(userNo0==userNo1){
								return 0;
							}else if(userNo0>userNo1){
								return 1;
							}else{
								return -1;
							}
						}else if(dept0.getDeptNo()>dept1.getDeptNo()){
							return 1;
						}else{
							return -1;
						}
					}
					
				});
				j.setTotal(total);// 设置总记录数
			    
			}else{
				//获取当前登陆者有查看权限的部门
				List <TeeDepartment>listDept = deptService.getDeptsByLoginUser(loginUser);
				
				j.setTotal(personDao.getViewPrivAllUserNoDeleteCount(loginUser,listDept));// 设置总记录数
				personList = personDao.getViewPrivAllUserNoDeletePage(firstIndex,dm.getRows(),dm,loginUser,listDept);
			}
			if(personList.size()>0){
				for(TeePerson person:personList){
					Map map = new HashMap();
					map = getUsedAnnualLeaveDays(person);
					mapList.add(map);
				}
			}
			j.setRows(mapList);
			return j;
			
		}
		
		/**
		 * 根据person查看用了几天年假
		 * @param request
		 * @return
		 */
		public Map getUsedAnnualLeaveDays(TeePerson person) {
			Map map = new HashMap();
			//获取当前登录人的入职时间
			TeeHumanDoc humanInfo = humanDao.getHumanDocInfo(person);
			double totalAnnualLeaveDays = 0;//总年假天数
			double remainLeaveDays=0;//剩余年假天数
			double usedDays = 0;
			if(!TeeUtility.isNullorEmpty(humanInfo)){
				//判断是否启用了年假规则，启用就动态判断其总年假天数，否则获取其默认年假天数
				int  isOpen = TeeStringUtil.getInteger(sysParaDao.getSysPara("HR_ANNUAL_LEAVE_RULE_OPEN").getParaValue(), 0);
				if(isOpen==1){
					Calendar curDate = Calendar.getInstance();
					int joinYears = 0;
					if(!TeeUtility.isNullorEmpty(humanInfo.getJoinDate())){
						Calendar joinDate = humanInfo.getJoinDate();
						//获取入职年限 
						long years = ((curDate.getTimeInMillis()-joinDate.getTimeInMillis())/(1000*3600*24))/365;
						joinYears = Integer.parseInt(String.valueOf(years));//入职年限为0 代表其入职不到一年
					}
					List<TeeAnnualLeave> ruleList =leaveDao.getObjList();
					for(TeeAnnualLeave leave :ruleList){
						if(joinYears==0 && leave.getYearCount()==1){
							totalAnnualLeaveDays=leave.getVacationDays();
							break;
						}else{
							if(joinYears <= leave.getYearCount()){
								totalAnnualLeaveDays=leave.getVacationDays();
								break;
							}
						}
					}
				}else{
					double defaultAnnualLeaveDays = humanInfo.getDefaultAnnualLeaveDays();
					totalAnnualLeaveDays = defaultAnnualLeaveDays;
				}
				//获取人事档案中，当前人设置的默认年假天数
				//获取当前用户已经用了多少天年假
				if(!TeeUtility.isNullorEmpty(humanInfo.getJoinDate())){
					usedDays = attendLeaveDao.getUsedAnnualLeaveDays(person,humanInfo.getJoinDate());
				}
				
			}else{
				totalAnnualLeaveDays=0;
				usedDays = 0;
			}
			remainLeaveDays = totalAnnualLeaveDays - usedDays;
			map.put("deptName", person.getDept().getDeptName());
			map.put("userName", person.getUserName());
			map.put("totalAnnualLeaveDays",totalAnnualLeaveDays);
			map.put("usedAnnualLeaveDays",usedDays);
			map.put("remainAnnualLeaveDays",remainLeaveDays);
			return map;
		}

		/**
		 * 根据条件查询出年假使用情况
		 * @author nieyi
		 * @param request
		 * @return
		 */
		public List<Map> getAnnualLeaveListByCondition(HttpServletRequest request) {
			TeeJson json = new TeeJson();
			//int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
			String deptIds=TeeStringUtil.getString(request.getParameter("deptIds"));
			List<Map> mapList = new ArrayList<Map>();
			List<TeePerson> personList = new ArrayList<TeePerson>();
			if(!TeeUtility.isNullorEmpty(deptIds)){
				String[] deptArray=deptIds.split(",");
				TeeDepartment dept =null;
				for (String id : deptArray) {
					int deptId=TeeStringUtil.getInteger(id, 0);
					dept= (TeeDepartment)deptDao.get(deptId);
					String  deptLevel = dept.getDeptParentLevel();
					if(TeeUtility.isNullorEmpty(deptLevel)){//如果是第一级部门
						deptLevel = dept.getGuid();
					}
					personList.addAll(personDao.selectDeptAndChildDeptPerson(deptId,deptLevel)) ;
				}
				
				Collections.sort(personList,new Comparator<TeePerson>() {

					@Override
					public int compare(TeePerson arg0, TeePerson arg1) {
						if(arg0.getDept().getDeptNo()==arg1.getDept().getDeptNo()){
							if(TeeStringUtil.getInteger(arg0.getUserNo(), 0)==TeeStringUtil.getInteger(arg1.getUserNo(), 0)){
								return 0;
							}else if(TeeStringUtil.getInteger(arg0.getUserNo(), 0)>TeeStringUtil.getInteger(arg1.getUserNo(), 0)){
								return 1;
							}else{
								return -1;
							}
						}else if(arg0.getDept().getDeptNo()>arg1.getDept().getDeptNo()){
							return 1;
						}else{
							return -1;
						}
					}
					
				});
			    
			}else{
				personList = personDao.getAllUserNoDelete();
			}
			if(personList.size()>0){
				for(TeePerson person:personList){
					Map map = new HashMap();
					map = getUsedAnnualLeaveDays(person);
					mapList.add(map);
				}
			}
			return mapList;
		}
		
		
		public List<TeeAttendLeaveModel> getLeaveByCondition(HttpServletRequest request) throws Exception {
			//int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
			String deptIds=TeeStringUtil.getString(request.getParameter("deptIds"));
			String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
			String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
			List<TeeAttendLeave> list = attendLeaveDao.getLeaveByCondition(deptIds,startDateDesc,endDateDesc);
			List<TeeAttendLeaveModel> listModel = new ArrayList<TeeAttendLeaveModel> ();
			for (int i = 0; i < list.size(); i++) {
				listModel.add(parseModel(list.get(i)));
			}
			return listModel;
		}
}



	