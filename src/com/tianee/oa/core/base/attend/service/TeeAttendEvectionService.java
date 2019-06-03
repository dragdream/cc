package com.tianee.oa.core.base.attend.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.base.attend.bean.TeeAttendEvection;
import com.tianee.oa.core.base.attend.bean.TeeAttendLeave;
import com.tianee.oa.core.base.attend.bean.TeeAttendOut;
import com.tianee.oa.core.base.attend.dao.TeeAttendEvectionDao;
import com.tianee.oa.core.base.attend.model.TeeAttendEvectionModel;
import com.tianee.oa.core.base.attend.model.TeeAttendLeaveModel;
import com.tianee.oa.core.base.attend.model.TeeAttendOutModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.general.bean.TeeSysLog;
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
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;
/**
 * 
 * @author syl
 *
 */
@Service
public class TeeAttendEvectionService  extends TeeBaseService {
	@Autowired
	private TeeAttendEvectionDao attendEvectionDao;
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeDeptDao deptDao;
	
	@Autowired
	private TeeSmsManager smsManager;
	
	@Autowired
	private TeeDeptService deptService;
	
	private String logRemark ;//日志描述
	
	/**
	 * @author syl
	 * 新增 或者 更新
	 * @param message
	 * @param person 系统当前登录人
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeAttendEvectionModel model) {
		TeeSysLog sysLog = TeeSysLog.newInstance();
		
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		TeeAttendEvection evection = new TeeAttendEvection();
		if(!TeeUtility.isNullorEmpty(model.getLeaderId())){
			TeePerson leader = personDao.get(TeeStringUtil.getInteger(model.getLeaderId(), 0));
			if(leader != null){
				evection.setLeader(leader);
			}
		}
		long startTime = 0;
		long endTime = 0;
		if(model.getStartDate() != null){
			startTime = model.getStartDate().getTime();
		}
		if(model.getEndDate() != null){
			Calendar endCalendar=Calendar.getInstance();
			endCalendar.setTime(model.getEndDate());
			endCalendar.set(Calendar.HOUR_OF_DAY, 23);
			endCalendar.set(Calendar.MINUTE, 59);
			endCalendar.set(Calendar.SECOND, 59);
			
			endTime = endCalendar.getTime().getTime();
		}
		evection.setStartTime(startTime);
		evection.setEndTime(endTime);
		if(model.getSid() > 0){
			TeeAttendEvection attendLeave  = attendEvectionDao.getById(model.getSid());
			if(attendLeave != null){
				BeanUtils.copyProperties(model, attendLeave);
				attendLeave.setLeader(evection.getLeader());
				attendLeave.setStartTime(evection.getStartTime());
				attendLeave.setEndTime(evection.getEndTime());
				attendLeave.setReason("");
				sysLog.setType("023K");
				logRemark = "编辑出差申请,内容为：【地址："+ model.getEvectionAddress() + ",描述:"+ model.getEvectionDesc() + "】";
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关出差信息！");
				return json;
			}
		}else{
			BeanUtils.copyProperties(model, evection);
			evection.setUser(person);
			evection.setStatus(0);
			evection.setAllow(0);
			evection.setCreateTime(new Date().getTime());
			attendEvectionDao.addAttendEvection(evection);
			sysLog.setType("023I");
			logRemark = "新建出差申请,内容为：【地址："+ evection.getEvectionAddress() + ",描述:"+ evection.getEvectionDesc() + "】";
			
		}
		String smsRemind = TeeStringUtil.getString(request.getParameter("smsRemind"), "0");
		if(smsRemind.equals("1")){//发送内部短信
			Map requestData = new HashMap();
			requestData.put("content", "提交出差申请，请批示！");
			String userListIds = evection.getLeader().getUuid() + "";
			requestData.put("userListIds",userListIds );
			//requestData.put("sendTime", );
			requestData.put("moduleNo", "023");
			requestData.put("remindUrl", "/system/core/base/attend/manager/index.jsp?attend=evection");
			smsManager.sendSms(requestData, person);
		}
		
		sysLog.setRemark(logRemark);
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
	}
	
	/**
	 * 外出归来
	 * @author syl
	 * @date 2014-2-6
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson comeback(HttpServletRequest request, TeeAttendEvectionModel model) {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		TeeAttendEvection evection = new TeeAttendEvection();

		long startTime = 0;
		long endTime = 0;
		if(model.getStartDate() != null){
			startTime = model.getStartDate().getTime();
		}
		if(model.getEndDate() != null){
			endTime = model.getEndDate().getTime();
		}
		evection.setStartTime(startTime);
		evection.setEndTime(endTime);
		if(model.getSid() > 0){
			TeeAttendEvection attendLeave  = attendEvectionDao.getById(model.getSid());
			if(attendLeave != null){
				/*attendLeave.setEvectionDesc(model.getEvectionDesc());
				attendLeave.setStartTime(evection.getStartTime());
				attendLeave.setEndTime(evection.getEndTime());*/
				attendLeave.setStatus(1);
				attendEvectionDao.update(attendLeave);
				
				Map requestData = new HashMap();
				requestData.put("content", "提交出差归来，请查看");
				String userListIds = attendLeave.getLeader().getUuid() + "";
				requestData.put("userListIds",userListIds );
				//requestData.put("sendTime", );
				requestData.put("moduleNo", "023");
				requestData.put("remindUrl", "/system/core/base/attend/evection/detail.jsp?id=" + model.getSid());
				smsManager.sendSms(requestData, person);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关出差信息！");
				return json;
			}
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
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
	public TeeEasyuiDataGridJson getEvection(HttpServletRequest request, TeeAttendEvectionModel model,TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int userId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
		if(userId>0){
			person=personDao.get(userId);
		}
		String hql="";
		List<Object> param = new ArrayList<Object>();		
		if(model.getStatus()==9){
			param.add(person);
			hql = "from TeeAttendEvection where user = ? and allow = 1  order by  createTime desc";
		}else{
			param.add(person);
			param.add(model.getStatus());
			hql = "from TeeAttendEvection where user = ? and status = ?  order by  createTime desc";
		}
		
		// 设置总记录数
		json.setTotal(attendEvectionDao.countByList("select count(*) " + hql, param));		
		List<TeeAttendEvection> list = attendEvectionDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
		
		List<TeeAttendEvectionModel> listModel = new ArrayList<TeeAttendEvectionModel> ();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i)));
		}
		
		json.setRows(listModel);
		return json;
	}
	
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getEvectionEasyui(TeeDataGridModel dm,HttpServletRequest request,TeeAttendEvectionModel model)throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		int userId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
		if(userId>0){
			loginPerson=personDao.get(userId);
		}
		j.setTotal(attendEvectionDao.selectPersonalEvectionCount(loginPerson, model));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		Object parm[] = {};
		List<TeeAttendEvection> list = attendEvectionDao.selectPersonalEvectionPage(loginPerson,firstIndex,
				dm.getRows(), dm, model);
		List<TeeAttendEvectionModel> modelList = new ArrayList<TeeAttendEvectionModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeAttendEvectionModel modeltemp = parseModel(list.get(i));
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
	public TeeAttendEvectionModel parseModel(TeeAttendEvection leave){
		TeeAttendEvectionModel model = new TeeAttendEvectionModel();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		if(leave == null){
			return null;
		}
		BeanUtils.copyProperties(leave, model);
		if(leave.getUser()!=null){
			model.setUserId(leave.getUser().getUuid() + "");
			model.setUserName(leave.getUser().getUserName() + "");
			model.setDeptId(leave.getUser().getDept().getUuid() +"");
			model.setDeptName(leave.getUser().getDept().getDeptName());
		}
		if(leave.getLeader() != null){
			model.setLeaderId(leave.getLeader().getUuid() + "");;
			model.setLeaderName(leave.getLeader().getUserName() + "");
		}
		Date date = new Date();
		date.setTime(leave.getCreateTime());
		model.setCreateTimeStr(dateFormat.format(date));
		
		date.setTime(leave.getStartTime());
		model.setStartTimeStr(dateFormat2.format(date));
		
		date.setTime(leave.getEndTime());
		model.setEndTimeStr(dateFormat2.format(date));
		
		if(TeeUtility.isNullorEmpty(model.getReason())){
			model.setReason("");
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
	
	@TeeLoggingAnt(template="删除出差申请，  {logModel.evectionName}",type="023L")
	public TeeJson deleteByIdService(HttpServletRequest request, TeeAttendEvectionModel model) {
		String evectionName = "";
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeAttendEvection evection = attendEvectionDao.getById(model.getSid());
			if(evection == null){
				json.setRtState(false);
				json.setRtMsg("出差申请已被删除!");
				evectionName = "出差申请已被删除!";
			}else{
				json.setRtState(true);
				json.setRtMsg("删除成功!");
				evectionName = "出差内容为：【地址："+ evection.getEvectionAddress() + ",描述:"+ evection.getEvectionDesc() + "】";
				attendEvectionDao.deleteByObj(evection);
			}
		}
		TeeRequestInfoContext.getRequestInfo().getLogModel().put("evectionName", evectionName);//添加其他参数
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
	public TeeJson getById(HttpServletRequest request, TeeAttendEvectionModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeAttendEvection leave = attendEvectionDao.getById(model.getSid());
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
	
	
	
	 /* 请假审批管理*/
	
		/**
		 * 获取请假审批记录
		 * @author syl
		 * @date 2014-1-29
		 * @param request
		 * @param model
		 * @return
		 */
		public TeeEasyuiDataGridJson getLeaderEvection(HttpServletRequest request, TeeAttendEvectionModel model,TeeDataGridModel dm) {
			TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
			TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			
			String hql = "from TeeAttendEvection where leader = ? and status = ?  and allow = ? order by  createTime desc";
			List<Object> param = new ArrayList<Object>();		
			param.add(person);
			param.add(model.getStatus());
			param.add(model.getAllow());
			
			// 设置总记录数
			json.setTotal(attendEvectionDao.countByList("select count(*) " + hql, param));		
			List<TeeAttendEvection> list = attendEvectionDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
			
			List<TeeAttendEvectionModel> listModel = new ArrayList<TeeAttendEvectionModel> ();
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
		public TeeEasyuiDataGridJson getLeaderApprovEvection(HttpServletRequest request, TeeAttendEvectionModel model,TeeDataGridModel dm) {
			TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
			TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			String hql = "from TeeAttendEvection where leader = ? and allow <> ? order by  createTime desc";
			List<Object> param = new ArrayList<Object>();		
			param.add(person);
			param.add(model.getAllow());
			
			// 设置总记录数
			json.setTotal(attendEvectionDao.countByList("select count(*) " + hql, param));		
			List<TeeAttendEvection> list = attendEvectionDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
			
			List<TeeAttendEvectionModel> listModel = new ArrayList<TeeAttendEvectionModel> ();
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
		public TeeJson approve(HttpServletRequest request, TeeAttendEvectionModel model) {
			TeeJson json = new TeeJson();
			TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			attendEvectionDao.approve(person, model);
			Map requestData = new HashMap();
			requestData.put("userListIds",model.getUserId() );
			requestData.put("moduleNo", "23");
			requestData.put("remindUrl", "oa/core/base/attend/evection/detail.jsp?id="+model.getSid());
			//requestData.put("sendTime", );
			if(model.getAllow() == 1){//批准	
				requestData.put("content", "您的出差已批准,请查看!");
			}else if(model.getAllow() == 2){//不批准
				requestData.put("content", "您的出差申请未批准,请查看!");
			}
			smsManager.sendSms(requestData, person);
			json.setRtState(true);
			return json;
		}

		public TeeJson getEvectionByCondition(HttpServletRequest request,
				TeeAttendEvectionModel model) throws Exception {
			TeeJson json = new TeeJson();
			int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
			String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
			String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
			List<TeeAttendEvection> list = attendEvectionDao.getEvectionByCondition(deptId+"",startDateDesc,endDateDesc);
			List<TeeAttendEvectionModel> listModel = new ArrayList<TeeAttendEvectionModel> ();
			for (int i = 0; i < list.size(); i++) {
				listModel.add(parseModel(list.get(i)));
			}
			json.setRtData(listModel);
			json.setRtState(true);
			return json;
		}
		
		@Transactional(readOnly = true)
		public TeeEasyuiDataGridJson getEvectionByConditionEasyui(TeeDataGridModel dm,HttpServletRequest request,TeeAttendEvectionModel model)throws ParseException {
			TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
			//int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
			String deptIds=TeeStringUtil.getString(request.getParameter("deptIds"));
			String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
			String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
			List<TeeAttendEvection> list =null;
			if(!TeeUtility.isNullorEmpty(deptIds)){//指定部门
				j.setTotal(attendEvectionDao.getEvectionByConditionCount(deptIds,startDateDesc,endDateDesc));// 设置总记录数
				int firstIndex = 0;
				firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
				Object parm[] = {};
				list = attendEvectionDao.getEvectionByConditionPage(deptIds,startDateDesc,endDateDesc,firstIndex,
						dm.getRows(), dm);
			}else{
				List <TeeDepartment>listDept = deptService.getDeptsByLoginUser(loginUser);
				j.setTotal(attendEvectionDao.getViewPrivEvectionByConditionCount(listDept,startDateDesc,endDateDesc));// 设置总记录数
				int firstIndex = 0;
				firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
				Object parm[] = {};
				list = attendEvectionDao.getViewPrivEvectionByConditionPage(listDept,startDateDesc,endDateDesc,firstIndex,
						dm.getRows(), dm);
			}
			
			
			List<TeeAttendEvectionModel> modelList = new ArrayList<TeeAttendEvectionModel>();
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					TeeAttendEvectionModel modeltemp = parseModel(list.get(i));
					modelList.add(modeltemp);
				}
			}
			j.setRows(modelList);// 设置返回的行
			return j;
			
		}
		
		
		public List<TeeAttendEvectionModel> getEvectionByCondition(HttpServletRequest request) throws Exception {
			String deptIds = TeeStringUtil.getString(request.getParameter("deptIds"));
			String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
			String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
			List<TeeAttendEvection> list = attendEvectionDao.getEvectionByCondition(deptIds,startDateDesc,endDateDesc);
			List<TeeAttendEvectionModel> listModel = new ArrayList<TeeAttendEvectionModel> ();
			for (int i = 0; i < list.size(); i++) {
				listModel.add(parseModel(list.get(i)));
			}
			return listModel;
		}
		
		

}



	