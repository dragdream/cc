package com.tianee.oa.core.base.attend.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.base.attend.bean.TeeAttendOvertime;
import com.tianee.oa.core.base.attend.dao.TeeAttendOvertimeDao;
import com.tianee.oa.core.base.attend.model.TeeAttendOvertimeModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
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
 * @author ny
 *
 */
@Service
public class TeeAttendOvertimeService extends TeeBaseService{
	@Autowired
	private TeeAttendOvertimeDao attendOvertimeDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeSmsManager smsManager;
	
	
	@Autowired
	private TeeDeptService deptService;
	/**
	 * @author ny
	 * 新增 或者 更新
	 * @param message
	 * @param person 系统当前登录人
	 * @return
	 * @throws Exception 
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeAttendOvertimeModel model) throws Exception {
		TeeSysLog sysLog = TeeSysLog.newInstance();
		String remark = "";
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		TeeAttendOvertime overtime = new TeeAttendOvertime();
		if(!TeeUtility.isNullorEmpty(model.getLeaderId())){
			TeePerson leader = personDao.get(TeeStringUtil.getInteger(model.getLeaderId(), 0));
			if(leader != null){
				overtime.setLeader(leader);
			}
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(!TeeUtility.isNullorEmpty(model.getStartTimeDesc())){
			
			overtime.setStartTime(sf.parse(model.getStartTimeDesc()).getTime());
		}
		if(!TeeUtility.isNullorEmpty(model.getEndTimeDesc())){
			
			overtime.setEndTime(sf.parse(model.getEndTimeDesc()).getTime());
		}
		overtime.setSubmitTime(new Date().getTime());
		if(model.getSid() > 0){
			TeeAttendOvertime attendOvertime  = attendOvertimeDao.getById(model.getSid());
			sysLog.setType("023C");
			if(attendOvertime != null){
				BeanUtils.copyProperties(model, attendOvertime);
				attendOvertime.setLeader(overtime.getLeader());
				attendOvertime.setStartTime(overtime.getStartTime());
				attendOvertime.setEndTime(overtime.getEndTime());
				attendOvertime.setSubmitTime(overtime.getSubmitTime());
				overtime.setSid(attendOvertime.getSid());
				
				sysLog.setRemark("更新加班申请,申请原因："+ attendOvertime.getOvertimeDesc());
			}else{
				sysLog.setRemark("更新加班申请,已被删除");
				json.setRtState(false);
				json.setRtMsg("未查到到相关加班信息！");
				return json;
			}
		}else{
			BeanUtils.copyProperties(model, overtime);
			overtime.setUser(person);
			overtime.setStatus(0);
			overtime.setAllow(0);
			overtime.setCreateTime(new Date().getTime());
			attendOvertimeDao.addAttendOvertime(overtime);
			sysLog.setType("023A");
			sysLog.setRemark("新建加班申请,申请原因："+ overtime.getOvertimeDesc());
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		
		String smsRemind = TeeStringUtil.getString(request.getParameter("smsRemind"), "0");
		if(smsRemind.equals("1")){//发送内部短信
			Map requestData = new HashMap();
			requestData.put("content", "提交加班申请,请批示！内容：" + model.getOvertimeDesc());
			String userListIds = model.getLeaderId() + "";
			requestData.put("userListIds",userListIds );
			//requestData.put("sendTime", );
			requestData.put("moduleNo", "023" );
			requestData.put("remindUrl", "/system/core/base/attend/manager/index.jsp?attend=overtime");
			smsManager.sendSms(requestData, person);
		}
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		return json;
	}
	
	/**
	 * 
	 * @author ny
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeEasyuiDataGridJson getOvertime(HttpServletRequest request, TeeAttendOvertimeModel model,TeeDataGridModel dm) {
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
			hql = "from TeeAttendOvertime where user = ? and allow = 1  order by  createTime desc";
		}else{
			param.add(person);
			param.add(model.getStatus());
			hql = "from TeeAttendOvertime where user = ? and status = ?  order by  createTime desc";
		}
		
		// 设置总记录数
		json.setTotal(attendOvertimeDao.countByList("select count(*) " + hql, param));		
		List<TeeAttendOvertime> list = attendOvertimeDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
		
		List<TeeAttendOvertimeModel> listModel = new ArrayList<TeeAttendOvertimeModel> ();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i)));
		}
		
		json.setRows(listModel);
		return json;
	}
	
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getOvertimeEasyui(TeeDataGridModel dm,HttpServletRequest request,TeeAttendOvertimeModel model)throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		int userId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
		if(userId>0){
			loginPerson=personDao.get(userId);
		}
		j.setTotal(attendOvertimeDao.selectPersonalOvertimeCount(loginPerson, model));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		Object parm[] = {};
		List<TeeAttendOvertime> list = attendOvertimeDao.selectPersonalOvertimePage(loginPerson,firstIndex,
				dm.getRows(), dm, model);
		List<TeeAttendOvertimeModel> modelList = new ArrayList<TeeAttendOvertimeModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeAttendOvertimeModel modeltemp = parseModel(list.get(i));
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
		
	}

	/**
	 * 对象转换
	 * @author ny
	 * @date 2014-1-29
	 * @param Overtime
	 * @return
	 */
	public TeeAttendOvertimeModel parseModel(TeeAttendOvertime overtime){
		TeeAttendOvertimeModel model = new TeeAttendOvertimeModel();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(overtime == null){
			return null;
		}
		BeanUtils.copyProperties(overtime, model);
		model.setUserId(overtime.getUser().getUuid());
		model.setUserName(overtime.getUser().getUserName() + "");
		model.setDeptId(overtime.getUser().getDept().getUuid() +"");
		model.setDeptName(overtime.getUser().getDept().getDeptName());
		if(overtime.getLeader() != null){
			model.setLeaderId(overtime.getLeader().getUuid());;
			model.setLeaderName(overtime.getLeader().getUserName() + "");
		}
		model.setCreateTimeDesc(dateFormat.format(overtime.getCreateTime()));
		model.setStartTimeDesc(dateFormat.format(overtime.getStartTime()));
		model.setEndTimeDesc(dateFormat.format(overtime.getEndTime()));
		if(TeeUtility.isNullorEmpty(model.getReason())){
			model.setReason("");
		}
		return model;
	}
	
	
	/**
	 * 
	 * @author ny 删除ById
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	@TeeLoggingAnt(template="删除加班申请， {logModel.OvertimeName}",type="023D")
	public TeeJson deleteByIdService(HttpServletRequest request, TeeAttendOvertimeModel model) {
		TeeJson json = new TeeJson();
		String overtimeName = "";
		if(model.getSid() > 0){
			TeeAttendOvertime overtime = attendOvertimeDao.getById(model.getSid());
			if(overtime == null ){
				json.setRtState(false);
				json.setRtMsg("该加班申请未找到！");
				overtimeName = "该加班申请未找到！";
			}else{
				json.setRtState(true);
				json.setRtMsg("删除成功!");
				overtimeName =  "加班原因为：" + overtime.getOvertimeDesc();
				attendOvertimeDao.delById(model.getSid());
			}
			
		}
		TeeRequestInfoContext.getRequestInfo().getLogModel().put("overtimeName", overtimeName);//添加其他参数
		return json;
	}
	
	/**
	 * 
	 * @author ny 
	 *  查询 ById
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request, TeeAttendOvertimeModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeAttendOvertime overtime = attendOvertimeDao.getById(model.getSid());
			if(overtime !=null){
				model = parseModel(overtime);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关加班记录！");
		return json;
	}
	
  /* 加班审批管理*/
	
	/**
	 * 获取加班审批记录
	 * @author ny
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeEasyuiDataGridJson getManagerOvertime(HttpServletRequest request, TeeAttendOvertimeModel model,TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		String hql = "from TeeAttendOvertime where leader = ? and status = ? and allow = ? order by  createTime desc";
		List<Object> param = new ArrayList<Object>();		
		param.add(person);
		param.add(model.getStatus());
		param.add(model.getAllow());
		
		// 设置总记录数
		json.setTotal(attendOvertimeDao.countByList("select count(*) " + hql, param));		
		List<TeeAttendOvertime> list = attendOvertimeDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
		
		List<TeeAttendOvertimeModel> listModel = new ArrayList<TeeAttendOvertimeModel> ();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i)));
		}
		
		json.setRows(listModel);
		return json;
	}
	
	/**
	 * 获取加班已审批记录
	 * @author ny
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeEasyuiDataGridJson getManagerApprovOvertime(HttpServletRequest request, TeeAttendOvertimeModel model,TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		String hql = "from TeeAttendOvertime where leader = ? and allow <> ? order by  createTime desc";
		List<Object> param = new ArrayList<Object>();		
		param.add(person);
		param.add(model.getAllow());
		
		// 设置总记录数
		json.setTotal(attendOvertimeDao.countByList("select count(*) " + hql, param));		
		List<TeeAttendOvertime> list = attendOvertimeDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
		
		List<TeeAttendOvertimeModel> listModel = new ArrayList<TeeAttendOvertimeModel> ();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i)));
		}	
		json.setRows(listModel);
		return json;
	}
	
	/**
	 * 审批
	 * @author ny
	 * @date 2014-5-23
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson approve(HttpServletRequest request, TeeAttendOvertimeModel model) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		attendOvertimeDao.approve(person, model);
		Map requestData = new HashMap();
		requestData.put("userListIds",model.getUserId() );
		requestData.put("moduleNo","023");
		requestData.put("remindUrl", "/system/core/base/attend/overtime/detail.jsp?id="+model.getSid());
		if(model.getAllow() == 1){//批准	
			requestData.put("content", "您的加班申请已批准,请查看!");
		}else if(model.getAllow() == 2){//不批准
			requestData.put("content", "您的加班申请未批准,请查看!");
		}
		smsManager.sendSms(requestData, person);
		json.setRtState(true);
		return json;
	}
	
	
	
	/**
	 * 个人桌面 模块--- 
	 * @author ny
	 * @date 2014-5-23
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	public TeeJson getDesktop(HttpServletRequest request) throws ParseException{
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	    TeeAttendOvertimeModel model = new TeeAttendOvertimeModel();
	    model.setStatus(0);
	    model.setAllow(1);
	    List<TeeAttendOvertimeModel> listModel = new ArrayList<TeeAttendOvertimeModel>();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    int userId = person.getUuid();
	    Date dateCur = new Date();  
	    String curDate = TeeUtility.getCurDateTimeStr("yyyy-MM-dd");
	    Date startTimeDate = TeeUtility.parseDate("yyyy-MM-dd", curDate);
		Date endTimeDate = TeeUtility.parseDate("yyyy-MM-dd HH:mm:ss", curDate + " 23:59:59");
	    long beginTime = startTimeDate.getTime();////T9DBUtility.getDateFilter("BEGIN_TIME", curDate + " 23:59:59", "<=");
	    long endTime = endTimeDate.getTime();//T9DBUtility.getDateFilter("END_TIME", curDate , ">=");
	    List<TeeAttendOvertime> list = attendOvertimeDao.getDeskTop(person, model  , beginTime ,endTime);
		for (int i = 0; i < list.size(); i++) {
			 model = parseModel(list.get(i));
			 listModel.add(model);
		}
		
		/*Map map = new HashMap();
		map.put("today", listModel);//今天
		map.put("round", listRoundModel);//近日10天内
*/		json.setRtState(true);
		json.setRtData(listModel);
		return json;
		
	}
	
	
	/**
	 * 根据条件统计加班记录
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public TeeJson getOvertimeByCondition(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
		String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
		String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
		List<TeeAttendOvertime> list = attendOvertimeDao.getOvertimeByCondition(deptId+"",startDateDesc,endDateDesc);
		List<TeeAttendOvertimeModel> listModel = new ArrayList<TeeAttendOvertimeModel> ();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i)));
		}
		json.setRtData(listModel);
		json.setRtState(true);
		return json;
	}
	
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getOvertimeByConditionEasyui(TeeDataGridModel dm,HttpServletRequest request,TeeAttendOvertimeModel model)throws ParseException {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		//int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
		String deptIds=TeeStringUtil.getString(request.getParameter("deptIds"));
		String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
		String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
		List<TeeAttendOvertime> list =null;
		if(!TeeUtility.isNullorEmpty(deptIds)){//指定部门
			j.setTotal(attendOvertimeDao.getOvertimeByConditionCount(deptIds,startDateDesc,endDateDesc));// 设置总记录数
			int firstIndex = 0;
			firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
			Object parm[] = {};
			list = attendOvertimeDao.getOvertimeByConditionPage(deptIds,startDateDesc,endDateDesc,firstIndex,
					dm.getRows(), dm);
		}else{//有可见权限的部门
			List <TeeDepartment>listDept = deptService.getDeptsByLoginUser(loginUser);
			j.setTotal(attendOvertimeDao.getViewPrivOvertimeByConditionCount(listDept,startDateDesc,endDateDesc));// 设置总记录数
			int firstIndex = 0;
			firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
			Object parm[] = {};
			list = attendOvertimeDao.getViewPrivOvertimeByConditionPage(listDept,startDateDesc,endDateDesc,firstIndex,
					dm.getRows(), dm);
		}
		
		
		List<TeeAttendOvertimeModel> modelList = new ArrayList<TeeAttendOvertimeModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeAttendOvertimeModel modeltemp = parseModel(list.get(i));
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
		
	}
	/**
	 * 根据条件统计加班记录
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public List<TeeAttendOvertimeModel>  getOvertimesByCondition(HttpServletRequest request) throws Exception {
		String deptIds = TeeStringUtil.getString(request.getParameter("deptIds"));
		String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
		String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
		List<TeeAttendOvertime> list = attendOvertimeDao.getOvertimeByCondition(deptIds,startDateDesc,endDateDesc);
		List<TeeAttendOvertimeModel> listModel = new ArrayList<TeeAttendOvertimeModel> ();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i)));
		}
		return listModel;
	}
	
	
}