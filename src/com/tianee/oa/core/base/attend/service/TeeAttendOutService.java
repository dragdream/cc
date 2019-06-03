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

import com.tianee.oa.core.base.attend.bean.TeeAttendLeave;
import com.tianee.oa.core.base.attend.bean.TeeAttendOut;
import com.tianee.oa.core.base.attend.dao.TeeAttendEvectionDao;
import com.tianee.oa.core.base.attend.dao.TeeAttendLeaveDao;
import com.tianee.oa.core.base.attend.dao.TeeAttendOutDao;
import com.tianee.oa.core.base.attend.dao.TeeAttendOvertimeDao;
import com.tianee.oa.core.base.attend.model.TeeAttendEvectionModel;
import com.tianee.oa.core.base.attend.model.TeeAttendLeaveModel;
import com.tianee.oa.core.base.attend.model.TeeAttendOutModel;
import com.tianee.oa.core.base.attend.model.TeeAttendOvertimeModel;
import com.tianee.oa.core.base.email.bean.TeeMail;
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
 * @author syl
 *
 */
@Service
public class TeeAttendOutService extends TeeBaseService{
	@Autowired
	private TeeAttendOutDao attendOutDao;
	
	@Autowired
	private TeeAttendLeaveDao attendLeaveDao;
	
	@Autowired
	private TeeAttendEvectionDao attendEvectionDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeSmsManager smsManager;
	
	@Autowired
	private TeeAttendOvertimeDao attendOvertimeDao;
	
	
	@Autowired
	private TeeDeptService deptService;
	/**
	 * @author syl
	 * 新增 或者 更新
	 * @param message
	 * @param person 系统当前登录人
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeAttendOutModel model) {
		TeeSysLog sysLog = TeeSysLog.newInstance();
		String remark = "";
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		TeeAttendOut out = new TeeAttendOut();
		if(!TeeUtility.isNullorEmpty(model.getLeaderId())){
			TeePerson leader = personDao.get(TeeStringUtil.getInteger(model.getLeaderId(), 0));
			if(leader != null){
				out.setLeader(leader);
			}
		}
		if(!TeeUtility.isNullorEmpty(model.getStartTimeStr())){
			
			String[] startTimeArr  = model.getStartTimeStr().split(":");
			String[] endTimeArr = model.getEndTimeStr().split(":");
			Calendar time = Calendar.getInstance();
			time.setTimeInMillis(model.getSubmitDate().getTime());
			time.set(Calendar.HOUR_OF_DAY, TeeStringUtil.getInteger(startTimeArr[0] , 0));
			time.set(Calendar.MINUTE, TeeStringUtil.getInteger(startTimeArr[1] , 0));
			long startTime = time.getTimeInMillis();
			time.set(Calendar.HOUR_OF_DAY, TeeStringUtil.getInteger(endTimeArr[0] , 0));
			time.set(Calendar.MINUTE, TeeStringUtil.getInteger(endTimeArr[1] , 0));
			long endTime = time.getTimeInMillis();
			out.setStartTime(startTime);
			out.setEndTime(endTime);
			out.setSubmitTime(model.getSubmitDate().getTime());
		}
		if(model.getSid() > 0){
			TeeAttendOut attendOut  = attendOutDao.getById(model.getSid());
			sysLog.setType("023C");
			if(attendOut != null){
				BeanUtils.copyProperties(model, attendOut);
				attendOut.setLeader(out.getLeader());
				attendOut.setStartTime(out.getStartTime());
				attendOut.setEndTime(out.getEndTime());
				attendOut.setSubmitTime(out.getSubmitTime());
				out.setSid(attendOut.getSid());
				
				sysLog.setRemark("更新外出申请,申请原因："+ attendOut.getOutDesc());
			}else{
				sysLog.setRemark("更新外出申请,已被删除");
				json.setRtState(false);
				json.setRtMsg("未查到到相关外出信息！");
				return json;
			}
		}else{
			BeanUtils.copyProperties(model, out);
			out.setUser(person);
			out.setStatus(0);
			out.setAllow(0);
			//创建时间去除秒
			Date crDate=new Date();
			SimpleDateFormat   sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String crDateStr=sdf.format(crDate);
			Date d=null;
			try {
				d=sdf.parse(crDateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.setCreateTime(d.getTime());
			attendOutDao.addAttendOut(out);
			sysLog.setType("023A");
			sysLog.setRemark("新建外出申请,申请原因："+ out.getOutDesc());
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		
		String smsRemind = TeeStringUtil.getString(request.getParameter("smsRemind"), "0");
		if(smsRemind.equals("1")){//发送内部短信
			Map requestData = new HashMap();
			requestData.put("content", "提交外出申请,请批示！内容：" + model.getOutDesc());
			String userListIds = model.getLeaderId() + "";
			requestData.put("userListIds",userListIds );
			//requestData.put("sendTime", );
			requestData.put("moduleNo", "023" );
			requestData.put("remindUrl", "/system/core/base/attend/manager/index.jsp");
			smsManager.sendSms(requestData, person);
		}
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		return json;
	}
	
	/**
	 * 
	 * @author syl
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeEasyuiDataGridJson getOut(HttpServletRequest request, TeeAttendOutModel model,TeeDataGridModel dm) {
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
			hql = "from TeeAttendOut where user = ? and allow = 1 ";
		}else{
			param.add(person);
			param.add( model.getStatus());
			hql = "from TeeAttendOut where user = ? and status = ? ";
		}
		
		// 设置总记录数
		json.setTotal(attendOutDao.countByList("select count(*) " + hql, param));		
		List<TeeAttendOut> list = attendOutDao.pageFindByList(hql+" order by  createTime desc", (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
		
		List<TeeAttendOutModel> listModel = new ArrayList<TeeAttendOutModel> ();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i)));
		}
		
		json.setRows(listModel);
		return json;
	}
	
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getOutEasyui(TeeDataGridModel dm,HttpServletRequest request,TeeAttendOutModel model)throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		int userId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
		if(userId>0){
			loginPerson=personDao.get(userId);
		}
		j.setTotal(attendOutDao.selectPersonalOutCount(loginPerson, model));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		Object parm[] = {};
		List<TeeAttendOut> list = attendOutDao.selectPersonalOutPage(loginPerson,firstIndex,
				dm.getRows(), dm, model);
		List<TeeAttendOutModel> modelList = new ArrayList<TeeAttendOutModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeAttendOutModel modeltemp = parseModel(list.get(i));
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
	public TeeAttendOutModel parseModel(TeeAttendOut out){
		TeeAttendOutModel model = new TeeAttendOutModel();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat HHmm = new SimpleDateFormat("HH:mm");
		SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
		if(out == null){
			return null;
		}
		BeanUtils.copyProperties(out, model);
		if(out.getUser()!=null){
			model.setUserId(out.getUser().getUuid() + "");
			model.setUserName(out.getUser().getUserName() + "");
			model.setDeptId(out.getUser().getDept().getUuid() +"");
			model.setDeptName(out.getUser().getDept().getDeptName());
		}
		if(out.getLeader() != null){
			model.setLeaderId(out.getLeader().getUuid() + "");;
			model.setLeaderName(out.getLeader().getUserName() + "");
		}
		Date date = new Date();
		date.setTime(out.getSubmitTime());
		String submitTimePri = yyyyMMdd.format(date);
		date.setTime(out.getCreateTime());
		//申请时间去除秒
		model.setCreateTimeStr(TeeUtility.getDateTimeStr(date).substring(0,TeeUtility.getDateTimeStr(date).length()-3));
		date.setTime(out.getSubmitTime() + out.getStartTime());
		model.setStartTimeStr(submitTimePri+" "+HHmm.format(out.getStartTime()));
		date.setTime(out.getSubmitTime() + out.getEndTime());
		model.setEndTimeStr(submitTimePri+" "+HHmm.format(out.getEndTime()));
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
	@TeeLoggingAnt(template="删除外出申请， {logModel.outName}",type="023D")
	public TeeJson deleteByIdService(HttpServletRequest request, TeeAttendOutModel model) {
		TeeJson json = new TeeJson();
		String outName = "";
		if(model.getSid() > 0){
			TeeAttendOut out = attendOutDao.getById(model.getSid());
			if(out == null ){
				json.setRtState(false);
				json.setRtMsg("该外出申请未找到！");
				outName = "该外出申请未找到！";
			}else{
				json.setRtState(true);
				json.setRtMsg("删除成功!");
				outName =  "外出原因为：" + out.getOutDesc();
				attendOutDao.delById(model.getSid());
			}
			
		}
		TeeRequestInfoContext.getRequestInfo().getLogModel().put("outName", outName);//添加其他参数
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
	public TeeJson getById(HttpServletRequest request, TeeAttendOutModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeAttendOut out = attendOutDao.getById(model.getSid());
			if(out !=null){
				model = parseModel(out);
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
	
  /* 外出审批管理*/
	
	/**
	 * 获取外出审批记录
	 * @author syl
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeEasyuiDataGridJson getManagerOut(HttpServletRequest request, TeeAttendOutModel model,TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);

		String hql="from TeeAttendOut where leader = ? and status = ? and allow = ? order by  createTime desc";
		List<Object> param = new ArrayList<Object>();
		param.add(person);
		param.add(model.getStatus());
		param.add(model.getAllow());
		// 设置总记录数
		json.setTotal(attendOutDao.countByList("select count(*) " + hql, param));		
		List<TeeAttendOut> list = attendOutDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
		
		List<TeeAttendOutModel> listModel = new ArrayList<TeeAttendOutModel> ();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i)));
		}
		
		json.setRows(listModel);
		return json;
	}
	
	/**
	 * 获取外出已审批记录
	 * @author syl
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeEasyuiDataGridJson getManagerApprovOut(HttpServletRequest request, TeeAttendOutModel model,TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		
		String hql = "from TeeAttendOut where leader = ? and allow <> ? order by  createTime desc";
		List<Object> param = new ArrayList<Object>();
		param.add(person);
		param.add(model.getAllow());
		// 设置总记录数
		json.setTotal(attendOutDao.countByList("select count(*) " + hql, param));		
		List<TeeAttendOut> list = attendOutDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
		
		List<TeeAttendOutModel> listModel = new ArrayList<TeeAttendOutModel> ();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i)));
		}
		
		json.setRows(listModel);
		return json;
	}
	
	/**
	 * 审批
	 * @author syl
	 * @date 2014-2-5
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson approve(HttpServletRequest request, TeeAttendOutModel model) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		attendOutDao.approve(person, model);
		Map requestData = new HashMap();
		requestData.put("userListIds",model.getUserId() );
		requestData.put("moduleNo","023");
		requestData.put("remindUrl", "/system/core/base/attend/out/detail.jsp?id="+model.getSid());
		//requestData.put("sendTime", );
		if(model.getAllow() == 1){//批准	
			requestData.put("content", "您的外出申请已批准,请查看!");
		}else if(model.getAllow() == 2){//不批准
			requestData.put("content", "您的外出申请未批准,请查看!");
		}
		smsManager.sendSms(requestData, person);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 外出归来
	 * @author syl
	 * @date 2014-2-5
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson comeBack(HttpServletRequest request, TeeAttendOutModel model) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		TeeAttendOut attendOut  = attendOutDao.getById(model.getSid());
		if(attendOut != null){
			/*String[] startTimeArr  = model.getStartTimeStr().split(":");
			String[] endTimeArr = model.getEndTimeStr().split(":");
			long startTime = (TeeStringUtil.getInteger(startTimeArr[0] , 0) * 60 * 60 + TeeStringUtil.getInteger(startTimeArr[1] , 0) * 60 ) * 1000;
			long endTime = (TeeStringUtil.getInteger(endTimeArr[0] , 0) * 60 * 60 + TeeStringUtil.getInteger(endTimeArr[1] , 0) * 60 ) * 1000;
			attendOut.setStartTime(startTime);
			attendOut.setEndTime(endTime);*/
			attendOut.setStatus(1);
			attendOutDao.update(attendOut);
			Map requestData = new HashMap();
			
			if(attendOut.getLeader()!=null){
				requestData.put("content", "外出归来,请查看！内容：" + model.getOutDesc());
				String userListIds = attendOut.getLeader().getUuid() + "";
				requestData.put("userListIds",userListIds );
				//requestData.put("sendTime", );
				requestData.put("moduleNo", "023");
				requestData.put("remindUrl", "/system/core/base/attend/out/detail.jsp?id=" + attendOut.getSid());
				smsManager.sendSms(requestData, person);
			}
			
		}
	
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取考勤  待审批数量  ----  外出、请假、出差
	 * @author syl
	 * @date 2014-2-6
	 * @param request
	 * @return
	 */
	public TeeJson getLeaderCount(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map map = new HashMap();
		/*外出需要审批数量*/
		TeeAttendOutModel outModel = new TeeAttendOutModel();
		outModel.setStatus(0);
		outModel.setAllow(0);
		long leaderOutCount = attendOutDao.getLeaderCount(person, outModel);;
		map.put("attendOutCount", leaderOutCount);
		
		/*请假需要审批数量*/
		TeeAttendLeaveModel leaveModel = new TeeAttendLeaveModel();
		leaveModel.setStatus(0);
		leaveModel.setAllow(0);
		long leaderLeaveCount = attendLeaveDao.getLeaderCount(person, leaveModel);
		map.put("attendLeaveCount", leaderLeaveCount);
		
		/*出差需要审批的数量*/
		TeeAttendEvectionModel evectionModel = new TeeAttendEvectionModel();
		evectionModel.setStatus(0);
		evectionModel.setAllow(0);
		long leaderEvectionCount = attendEvectionDao.getEvectionCount(person, evectionModel);
		map.put("attendEvectionCount", leaderEvectionCount);
		
		/*出差需要审批的数量*/
		TeeAttendOvertimeModel overtimeModel = new TeeAttendOvertimeModel();
		evectionModel.setStatus(0);
		evectionModel.setAllow(0);
		long leaderOvertimeCount = attendOvertimeDao.getLeaderCount(person, overtimeModel);
		map.put("attendOvertimeCount", leaderOvertimeCount);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	
	
	
	/**
	 * 个人桌面 模块--- 
	 * @author syl
	 * @date 2014-2-12
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	public TeeJson getDesktop(HttpServletRequest request) throws ParseException{
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	    TeeAttendOutModel model = new TeeAttendOutModel();
	    model.setStatus(0);
	    model.setAllow(1);
	    List<TeeAttendOutModel> listModel = new ArrayList<TeeAttendOutModel>();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    int userId = person.getUuid();
	    Date dateCur = new Date();  
	    String curDate = TeeUtility.getCurDateTimeStr("yyyy-MM-dd");
	    Date startTimeDate = TeeUtility.parseDate("yyyy-MM-dd", curDate);
		Date endTimeDate = TeeUtility.parseDate("yyyy-MM-dd HH:mm:ss", curDate + " 23:59:59");
	    long beginTime = startTimeDate.getTime();////T9DBUtility.getDateFilter("BEGIN_TIME", curDate + " 23:59:59", "<=");
	    long endTime = endTimeDate.getTime();//T9DBUtility.getDateFilter("END_TIME", curDate , ">=");
	    List<TeeAttendOut> list = attendOutDao.getDeskTop(person, model  , beginTime ,endTime);
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

	public TeeJson getOutByCondition(HttpServletRequest request,TeeAttendOutModel model) throws Exception {
		TeeJson json = new TeeJson();
		int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
		String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
		String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
		List<TeeAttendOut> list = attendOutDao.getOutByCondition(deptId+"",startDateDesc,endDateDesc);
		List<TeeAttendOutModel> listModel = new ArrayList<TeeAttendOutModel> ();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i)));
		}
		json.setRtData(listModel);
		json.setRtState(true);
		return json;
	}
	
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getOutByConditionEasyui(TeeDataGridModel dm,HttpServletRequest request,TeeAttendOutModel model)throws ParseException {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		//int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
		String deptIds=TeeStringUtil.getString(request.getParameter("deptIds"));
		String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
		String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
		List<TeeAttendOut> list=null;
		if(!TeeUtility.isNullorEmpty(deptIds)){//指定部门
			j.setTotal(attendOutDao.getOutByConditionCount(deptIds,startDateDesc,endDateDesc));// 设置总记录数
			int firstIndex = 0;
			firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
			Object parm[] = {};
			list = attendOutDao.getOutByConditionPage(deptIds,startDateDesc,endDateDesc,firstIndex,
					dm.getRows(), dm);
		}else{//有可见权限的所有部门
			List <TeeDepartment>listDept = deptService.getDeptsByLoginUser(loginUser);
			
			j.setTotal(attendOutDao.getViewPrivOutByConditionCount(listDept,startDateDesc,endDateDesc));// 设置总记录数
			int firstIndex = 0;
			firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
			Object parm[] = {};
			list = attendOutDao.getViewPrivOutByConditionPage(listDept,startDateDesc,endDateDesc,firstIndex,
					dm.getRows(), dm);
		}
		
		
		
		List<TeeAttendOutModel> modelList = new ArrayList<TeeAttendOutModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeAttendOutModel modeltemp = parseModel(list.get(i));
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
		
	}
	
	public List<TeeAttendOutModel> getOutByCondition(HttpServletRequest request) throws Exception {
		//int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
		String deptIds=TeeStringUtil.getString(request.getParameter("deptIds"));
		String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
		String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
		List<TeeAttendOut> list = attendOutDao.getOutByCondition(deptIds,startDateDesc,endDateDesc);
		List<TeeAttendOutModel> listModel = new ArrayList<TeeAttendOutModel> ();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i)));
		}
		return listModel;
	}
	
	
	
	
	
}