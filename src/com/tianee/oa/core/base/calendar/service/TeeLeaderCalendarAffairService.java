package com.tianee.oa.core.base.calendar.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.calendar.bean.TeeCalendarAffair;
import com.tianee.oa.core.base.calendar.dao.TeeCalendarAffairDao;
import com.tianee.oa.core.base.calendar.model.TeeCalendarAffairModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeePersonManagerI;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.priv.bean.TeeModulePriv;
import com.tianee.oa.core.priv.model.TeeModulePrivModel;
import com.tianee.oa.core.priv.service.TeeModulePrivService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 
 * @author syl
 * 
 */
@Service
public class TeeLeaderCalendarAffairService extends TeeBaseService {
	@Autowired
	private TeeCalendarAffairDao calendarDao;

	@Autowired
	private TeeCalendarAffairService calendarAffairService;
	@Autowired
	private TeeCalAffairService affairService;
	@Autowired
	private TeeModulePrivService modulePrivService;
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private TeePersonDao personDao;

	@Autowired
	private TeeDeptService deptService;
	
	
	
	@Autowired
	private TeeSmsManager smsManager;
	
	
	@Autowired
	private TeePersonManagerI personManagerI;
	/**
	 * 获取管理权限 --- 日程安排查询   日 + 周视图
	 * 
	 * @author syl
	 * @date 2014-1-11
	 * @param request
	 * @return
	 */
	public TeeJson getLeaderPostDept(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);

		int userId = user.getUuid();
		int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
		String fromView = TeeStringUtil.getString(request.getParameter("fromView"), "week");//从那个页面跳转过来的
		boolean isAdminPriv = TeePersonService.checkIsAdminPriv(user);
		boolean isSuperAdmin = TeePersonService.checkIsSuperAdmin(user, "");
		
		TeePerson tempPerson = null;
		List<TeeZTreeModel> deptTree = new ArrayList<TeeZTreeModel>();
		if ( !fromView.equals("month")) {//不是从月视图过来的
			if(deptId == 0){//如果没有部门，则获取本部门
				deptId = user.getDept().getUuid();
			}
			
		}else{
			tempPerson = personDao.get(deptId);//获取人员
			if(tempPerson!= null && tempPerson.getDept() != null){
				deptId = tempPerson.getDept().getUuid();
			}
		}
		String deptIdStr = deptId + "";
		
		List<TeeDepartment> deptList = new ArrayList<TeeDepartment>();
		boolean isJson = false;//如果是json ，就没要转换
		if (isSuperAdmin) {// 超级管理员
			json = deptService.getDeptTreeAll();
			isJson = true;
		} else {
			String moduleId = TeeStringUtil.getString(request.getParameter("moduleId"),"3");// 按模块权限设置
			TeeModulePriv modulePriv = null;
			if (TeeUtility.isInteger(moduleId)) {
				// 如果存在按模块设置
				modulePriv = modulePrivService.getModelPrivDao().selectPrivByUserId(user.getUuid(),Integer.parseInt(moduleId));
			}
			if (modulePriv != null) {// 存在
				String deptPriv = modulePriv.getDeptPriv();
				String roloPriv = modulePriv.getRolePriv();//角色类型
				if(deptPriv.equals("0")){//本部门
					deptList.add(user.getDept());
				}else if(deptPriv.equals("1")){//全体部门
					json = deptService.getDeptTreeAll();
					isJson = true;
				}else if(deptPriv.equals("2")){//指定部门
					deptList.addAll(modulePriv.getDeptIds());
				}else if(deptPriv.equals("3")){//指定人员
					Set<TeePerson> pl =  modulePriv.getUserIds();
					Iterator< TeePerson> it = pl.iterator();
					int currPersonRoleNo = TeeStringUtil.getInteger(user.getUserRole().getRoleNo(),0);//登录用户角色排序号
					String userIds = "";
					String userDatas = "[";
					Set<TeeUserRole>  roloList = modulePriv.getRoleIds();
					while (it.hasNext()) {
						TeePerson p = it.next();
						int personRoleNo = 0;
						if(p.getUserRole() != null){
							personRoleNo = TeeStringUtil.getInteger(p.getUserRole().getRoleNo(),0);
						}else{
							continue;
						}
						if(roloPriv.equals("0") && currPersonRoleNo < personRoleNo){//低角色
							userIds = userIds + p.getUuid() +",";
							userDatas = userDatas + "{uuid:" + p.getUuid() + ",userName:\"" + TeeUtility.encodeSpecial(p.getUserName()) + "\"},";
						}else if(roloPriv.equals("1") && currPersonRoleNo <= personRoleNo){//同角色或者低角色
							userIds = userIds + p.getUuid() +",";
							userDatas = userDatas + "{uuid:" + p.getUuid() + ",userName:\"" + TeeUtility.encodeSpecial(p.getUserName()) + "\"},";
							
						}else if(roloPriv.equals("2")){//所有角色
							userIds = userIds + p.getUuid() +",";
							userDatas = userDatas + "{uuid:" + p.getUuid() + ",userName:\"" + TeeUtility.encodeSpecial(p.getUserName()) + "\"},";	
						}else if(roloPriv.equals("3") && roloList.contains(p.getUserRole())){//指定角色
							userIds = userIds + p.getUuid() +",";
							userDatas = userDatas + "{uuid:" + p.getUuid() + ",userName:\"" + TeeUtility.encodeSpecial(p.getUserName()) + "\"},";
							
						}
					}
					if(userIds.endsWith(",")){
						userIds = userIds.substring(0,userIds.length() - 1);
						userDatas = userDatas.substring(0,userDatas.length() - 1);
					}
					userDatas = userDatas + "]";
					json.setRtData(deptTree);
					json.setRtMsg("{userIds:\"" + userIds + "\",userDatas:" + userDatas + "}" );// 指定人员
					json.setRtState(true);
					return json;
				}
			} else {
				//deptList = personService.getLoginPersonPostDeptBean(user);
				deptList = getLoginPersonPostDept(user);
				
			}

		}
		if(!isJson){
			for (TeeDepartment dept : deptList) {
				String pid = "0";
				if (dept.getDeptParent() != null) {
					pid = dept.getDeptParent().getUuid() + "";
				}
				TeeZTreeModel ztree = new TeeZTreeModel();
				ztree.setId(dept.getUuid() + "");
				ztree.setName(dept.getDeptName());
				ztree.setOpen(true);
				ztree.setpId(pid);
				// ztree.setParent(false);
				ztree.setIconSkin("deptNode");
				deptTree.add(ztree);
			}

			json.setRtData(deptTree);
			
			if(deptTree.size() == 0 ){
				deptIdStr = "";
			}else{
				if(!deptList.contains(user.getDept())){//不包含本部门
					deptIdStr = deptList.get(0).getUuid() + "";
				}
				if(tempPerson != null && deptList.contains(tempPerson.getDept() )){
					deptIdStr = tempPerson.getDept().getUuid() + "";
				}
			}
		}
		json.setRtMsg(deptIdStr);// 选中部门
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 根据系统当前登录人获取管理范围 对象
	 * @param person
	 * @return
	 */
	public List<TeeDepartment> getLoginPersonPostDept(TeePerson person){
		List<TeeDepartment> list = new ArrayList<TeeDepartment>();

		if(person.getDept() != null){
			list.add(person.getDept());
			String level = person.getDept().getUuid() + ",";
			if(!TeeUtility.isNullorEmpty(person.getDept().getDeptParentLevel())){//如果不是第一级部门
				level = person.getDept().getDeptParentLevel();
			}else{//如果是第一级部门，则把uuID当做级别
				level = person.getDept().getGuid();
			}
			List<TeeDepartment> deptChildList = deptService.getDeptDao().getAllChildDeptByLevl(level);
			list.addAll(deptChildList);
		}
		return list;
	} 
	
	/**
	 * 获取人员管理范围 ----  按模块授权和管理范围
	 * @author syl
	 * @date 2014-1-22
	 * @param person
	 * @param modulePriv
	 * @return
	 */
	public Map getDeptOrPersonByPostAndModule(TeePerson person  ,String   moduleIdStr){
		Map map = new HashMap();
		boolean isAdminPriv = TeePersonService.checkIsAdminPriv(person);//是否是超级管理员
		boolean isSuperAdmin = TeePersonService.checkIsSuperAdmin(person, "");
		List<TeeDepartment> deptList = new ArrayList<TeeDepartment>();
		TeeModulePriv modulePriv = null;
		TeeModulePrivModel modulePrivModel = null;
		List<TeePerson> personList = new ArrayList<TeePerson>();
		boolean bingPerson = false;//是否绑定人员
		if (isSuperAdmin) {// 超级管理员
			deptList = deptService.getDeptDao().getAllDept();
		} else {
			String moduleId = TeeStringUtil.getString(moduleIdStr,"3");// 按模块权限设置
			if (TeeUtility.isInteger(moduleId)) {
				// 如果存在按模块设置
				modulePriv = modulePrivService.getModelPrivDao().selectPrivByUserId(person.getUuid(),Integer.parseInt(moduleId));
				modulePrivModel = modulePrivService.pasreModelByModulePriv(modulePriv);
			}
			if (modulePriv != null) {// 存在
				String deptPriv = modulePriv.getDeptPriv();
				String roloPriv = modulePriv.getRolePriv();//角色类型
				if(deptPriv.equals("0")){//本部门
					deptList.add(person.getDept());
				}else if(deptPriv.equals("1")){//全体部门
					deptList = deptService.getDeptDao().getAllDept();
				}else if(deptPriv.equals("2")){//指定部门
					deptList.addAll(modulePriv.getDeptIds());
				}else if(deptPriv.equals("3")){//指定人员
					Set<TeePerson> pl =  modulePriv.getUserIds();
					Iterator< TeePerson> it = pl.iterator();
					int currPersonRoleNo = TeeStringUtil.getInteger(person.getUserRole().getRoleNo(),0);//登录用户角色排序号
					String userIds = "";
					String userDatas = "[";
					Set<TeeUserRole>  roloList = modulePriv.getRoleIds();
					while (it.hasNext()) {
						TeePerson p = it.next();
						int personRoleNo = 0;
						if(p.getUserRole() != null){
							personRoleNo = TeeStringUtil.getInteger(p.getUserRole().getRoleNo(),0);
						}else{
							continue;
						}
						if(roloPriv.equals("0") && currPersonRoleNo >= personRoleNo){//低角色
							continue;
						}else if(roloPriv.equals("1") && currPersonRoleNo > personRoleNo){//同角色或者低角色
							continue;	
						}else if(roloPriv.equals("2")){//所有角色
							
						}else if(roloPriv.equals("3") && !roloList.contains(p.getUserRole())){//指定角色
							continue;
						}
						personList.add(p);
					}

					userDatas = userDatas + "]";
					bingPerson = true;
				}
			} else {
				//deptList = personService.getLoginPersonPostDeptBean(person);
				deptList = getLoginPersonPostDept(person);
			}
		}
		
		if(!bingPerson){//不是绑定人员
			personList = selectPersonByDeptsAndPostPriv(isSuperAdmin ,deptList ,person ,modulePrivModel );//personDao.selectPersonByDeptsAndPostPriv(isSuperAdmin ,deptList ,person ,modulePrivModel );//根据部门 + 模块设置查询人员
			
			
			
		}
	
		map.put("deptList", deptList);
		map.put("bingPerson", bingPerson);
		map.put("personList", personList);
		map.put("isSuperAdmin", isSuperAdmin);
		return map;
	}
	
	/**
	 * By部门查询人员,且带条件查询   和权限管理
	 * @param 部门UUID
	 * @param userFilter : 人员id字符串
	 */
	public List<TeePerson> selectPersonByDeptsAndPostPriv(boolean isSuperAdmin , List<TeeDepartment> depts ,TeePerson person ,TeeModulePrivModel model) {
	    @SuppressWarnings("unchecked")
	    String deptIds = "";
	    for (int i = 0; i < depts.size(); i++) {
	    	deptIds = deptIds + depts.get(i).getUuid() + ",";
		}
	    if(!deptIds.equals("")){
	    	deptIds = deptIds.substring(0, deptIds.length() - 1);
	    }
	    Object[] values = null;
	    String hql  = " from TeePerson  where dept.uuid in ("+deptIds+")  and deleteStatus <> '1' "; 
	    if(isSuperAdmin){//是超级管理员
	    	hql = "from TeePerson where deleteStatus <> '1'";
	    	values = null;
	    }else{
	    	if(model == null){
	    		hql = hql + " and userRole.roleNo > " + person.getUserRole().getRoleNo();
	    	}else{
	    		String andHql = personManagerI.getManagerPostPrivHqlStrByPersonPostPrivAndModulePriv(person, model);//按模块或者管理范围
		    	if(andHql.equals("0")){
					return new ArrayList();
				}else {
					if(!TeeUtility.isNullorEmpty(andHql)){
						hql = hql + andHql;
					}
				}
	    	}
	    	
	    }
		
		hql = hql + " order by userRole.roleNo";
	    List<TeePerson> list = (List<TeePerson>) simpleDaoSupport.executeQuery(hql, values);
		return list;
	}

	/**
	 * 获取管理权限 --- 日程安排查询   月视图
	 * 
	 * @author syl
	 * @date 2014-1-11
	 * @param request
	 * @return
	 */
	public TeeJson getLeaderMonthPostDept(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);

		int userId = user.getUuid();
		int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
		String moduleIdStr = request.getParameter("moduleId");
		boolean isAdminPriv = TeePersonService.checkIsAdminPriv(user);
		boolean isSuperAdmin = TeePersonService.checkIsSuperAdmin(user, "");
		List<TeeZTreeModel> deptTree = new ArrayList<TeeZTreeModel>();
		if (deptId == 0) {
			deptId = user.getDept().getUuid();
		}
		String deptIdStr = deptId + "";
		
		List<TeeDepartment> deptList = new ArrayList<TeeDepartment>();
		TeeModulePriv modulePriv = null;
		TeeModulePrivModel modulePrivModel = null;
		List<TeePerson> personList = new ArrayList<TeePerson>();
		boolean bingPerson = false;//是否绑定人员
		Map map = getDeptOrPersonByPostAndModule(user, moduleIdStr);
		bingPerson = (Boolean) map.get("bingPerson");
		deptList = (List<TeeDepartment>)map.get("deptList");
		personList = (List<TeePerson>)map.get("personList");
		if(!bingPerson){//不是绑定人员
			for (TeeDepartment dept : deptList) {
				String pid = "0";
				if (dept.getDeptParent() != null) {
					pid = dept.getDeptParent().getUuid() + ";dept";
				}
				TeeZTreeModel ztree = new TeeZTreeModel();
				ztree.setId(dept.getUuid() + ";dept");
				ztree.setName(dept.getDeptName());
				ztree.setOpen(true);
				ztree.setpId(pid);
				// ztree.setParent(false);
				ztree.setIconSkin("deptNode");
				deptTree.add(ztree);
			}
			json.setRtData(deptTree);
			//personList = selectPersonByDeptsAndPostPriv(isSuperAdmin ,deptList ,user ,modulePrivModel );
			//personList = personDao.selectPersonByDeptsAndPostPriv(isSuperAdmin ,deptList ,user ,modulePrivModel );//根据部门 + 模块设置查询人员
		}
		
		for (TeePerson person : personList) {
			
			TeeZTreeModel ztree = new TeeZTreeModel();
			ztree.setId(person.getUuid() + "");
			ztree.setName(person.getUserName());
			ztree.setOpen(true);
			String pid = "0";
			if(person.getDept() != null){
				pid = person.getDept().getUuid() +";dept";
			}
			ztree.setpId(pid);
			// ztree.setParent(false);
			ztree.setIconSkin("person_online_node");
			deptTree.add(ztree);
		
		}
		if(personList.size() == 0 ){
			deptIdStr = "";
		}else{
			deptIdStr = personList.get(0).getUuid() + "";
		}
		json.setRtData(deptTree);
		json.setRtMsg(deptIdStr);// 选中部门
		json.setRtState(true);
		//json.setRtMsg("{userIds:\"" + userIds + "\",userDatas:" + userDatas + "}" );// 指定人员
		return json;
		
	}
	
	
	
	
	
	
	/**
	 * @author syl
	 * 新增 
	 * @param model
	 * @param userIds   用户Id串 以逗号分隔
	 * @param person 系统当前登录人
	 * @return
	 * @throws ParseException 
	 */
	public TeeJson addCalAff(HttpServletRequest request , TeeCalendarAffairModel model ,TeePerson person , String userIds) throws ParseException {
		TeeJson json = new TeeJson();
		String remindTime = request.getParameter("remindTime");
		String isWeekend = request.getParameter("isWeekend");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar currSimpCal = TeeUtility.getMinTimeOfDayCalendar(null);//当前一天日程  去掉时分秒
		long maxTime = (23*60*60 + 59*60 + 59)*1000;//一天最大毫秒
	      //System.out.println(type);
	      //判断今天是否可以为提醒时间

		Calendar calendar = Calendar.getInstance();
		int week = calendar.get(Calendar.DAY_OF_WEEK);// 周
		int day = calendar.get(Calendar.DATE);// 日
		int month = calendar.get(Calendar.MONTH);// 月
		if (week == 0) {
			week = 7;
		} else {
			week = week - 1;
		}
		month = month + 1;
		Date curDate = new Date();
		String curDateStr = dateFormat.format(curDate);
		if(!TeeUtility.isNullorEmpty(userIds)){
			List<TeePerson> pl = personDao.getPersonByUuids(userIds);
			for (int i = 0; i < pl.size() ; i++) {
				TeeCalendarAffair cal = new TeeCalendarAffair();
				BeanUtils.copyProperties(model, cal);
				if(model.getCalAffType() == 1){//周期性事务
					if (isWeekend != null) {// 是否包含周末 1-排除
						cal.setIsWeekend(1);
					}
					String remindDate = "";
					if (model.getRemindType() == 3) {// 周
						remindDate = request.getParameter("remindDate3");
						remindTime = request.getParameter("remindTime3");
					}
					if (model.getRemindType() == 4) {// 周
						remindDate = request.getParameter("remindDate4");
						remindTime = request.getParameter("remindTime4");
					}
					if (model.getRemindType() == 5) {// 周
						remindDate = request.getParameter("remindDate5Mon") + "-"
								+ request.getParameter("remindDate5Day");
						remindTime = request.getParameter("remindTime5");
					}
					if (TeeUtility.isNullorEmpty(remindTime)) {
						cal.setRemindTime(maxTime);
					} else {
						String currTimeStr = TeeUtility.getCurDateTimeStr("yyyy-MM-dd");
						Date data1 = TeeUtility.parseDate(currTimeStr);
						currTimeStr = currTimeStr + " " + remindTime;
						Date data2 = TeeUtility.parseDate(currTimeStr);
						calendar.setTime(data2);// 设置带时分秒
						long time2 = calendar.getTimeInMillis();
						calendar.setTime(data1);// 设置不带时分秒
						long time1 = calendar.getTimeInMillis();
						long time = time2 - time1;
						cal.setRemindTime(time);
					}
					cal.setRemindDate(remindDate);
					cal.setRemindType(model.getRemindType());
				}
				cal.setStartTime(model.getStartDate().getTimeInMillis());
				if (model.getEndDate() != null) {// 结束时间
					cal.setEndTime(model.getEndDate().getTimeInMillis());
				}
				cal.setUser(pl.get(i));
				cal.setManager(person);
				cal.setOverStatus(0);
				if(!TeeUtility.isNullorEmpty(model.getActorIds())){//参与者
					List<TeePerson> list = personDao.getPersonByUuids(model.getActorIds());
					cal.setActor(list);
				}
				calendarDao.addCalendar(cal);
				
				String smsRemind = TeeStringUtil.getString(request.getParameter("smsRemind"), "0");
				//if(smsRemind.equals("1") && model.getCalAffType() == 0){//发送内部短信 , 日程
				if( model.getCalAffType() == 0){//发送内部短信 , 日程
					Map requestData = new HashMap();
					requestData.put("content", person.getUserName() + "为你安排新的工作,内容：" + cal.getContent());
					String userListIds = pl.get(i).getUuid() + "";
					if(!TeeUtility.isNullorEmpty(model.getActorIds())){
						userListIds = userListIds + "," + model.getActorIds() ;
					}
					requestData.put("userListIds",userListIds );
					//requestData.put("sendTime", );
					requestData.put("moduleNo", "022");
					requestData.put("remindUrl", "/system/core/base/calendar/detail.jsp?id=" + cal.getSid());
					smsManager.sendSms(requestData, person);
				}
			}
			json.setRtMsg("保存成功");
			json.setRtState(true);
		}else{
			json.setRtMsg("创建失败！");
			json.setRtState(false);
		}
		
		return json;
	}

	/**
	 * 周视图-- 查询
	 * 
	 * @author syl
	 * @date 2014-1-12
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public TeeJson getCalByWeekAndUserIds(HttpServletRequest request)
			throws Exception {
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Calendar cal = Calendar.getInstance();
		int year = TeeStringUtil.getInteger(request.getParameter("year"),cal.get(Calendar.YEAR));
		int weekth = TeeStringUtil.getInteger(request.getParameter("weekth"),cal.get(Calendar.DAY_OF_WEEK_IN_MONTH));
		//System.out.println(request.getAttribute("weekth"));
		TeeJson json = new TeeJson();

		Date currDate = new Date();
		long currTime = currDate.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormatday = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy年-MM月-w周-dd日");
		SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat4 = new SimpleDateFormat("MM/dd E");
		SimpleDateFormat dateFormat5 = new SimpleDateFormat("E");
		String status = request.getParameter("status");
		String userIds = request.getParameter("userIds");
		Calendar[] startAndEndCalendar = TeeDateUtil.getStartToEndDate(year,weekth);
		Calendar startCal = startAndEndCalendar[0];
		TeeUtility.getMinTimeOfDayCalendar(startCal);
		Calendar endCal = startAndEndCalendar[6];
		TeeUtility.getMaxTimeOfDayCalendar(endCal);

		// 获取时间
		List<Map> dateList = new ArrayList<Map>();
		for (int i = 0; i < startAndEndCalendar.length; i++) {
			Map map = new HashMap();
			map.put("simpDate",dateFormat3.format(startAndEndCalendar[i].getTime()));
			dateFormat4.format(startAndEndCalendar[i].getTime());
			map.put("fullDate",dateFormat4.format(startAndEndCalendar[i].getTime()));
			map.put("week",dateFormat5.format(startAndEndCalendar[i].getTime()));
			
			dateList.add(map);
		}
		List<TeeCalendarAffairModel> listCalendar = new ArrayList<TeeCalendarAffairModel>();
		List<TeeCalendarAffairModel> listAffair = new ArrayList<TeeCalendarAffairModel>();
		if (!TeeUtility.isNullorEmpty(userIds)) {
			List<TeeCalendarAffair> calList = calendarDao.selectLeaderCalendar(userIds, startCal.getTimeInMillis(),endCal.getTimeInMillis(), status);
			for (int i = 0; i < calList.size(); i++) {
				TeeCalendarAffairModel calendarModel = new TeeCalendarAffairModel();
				TeeCalendarAffair calendar = calList.get(i);
				if(calendar.getCalAffType() == 1){// 周期性事务
					/*calendarModel= affairService.parseAffairModel(calendar);
					listAffair.add(calendarModel);*/
					List<TeeCalendarAffairModel> affairModelList = getIsUseAffair(calendar, startAndEndCalendar);
					listAffair.addAll(affairModelList);
				}else{//日程
					calendarModel = parseWeekCalendarmodel(calendar, startCal, endCal);
					listCalendar.add(calendarModel);
				}
			}
		}
		Map dataMap = new HashMap();
		dataMap.put("dateList", dateList);
		dataMap.put("calendarList", listCalendar);
		dataMap.put("affairList", listAffair);
		json.setRtData(dataMap);
		json.setRtState(true);
		return json;
	}
	
	
	
	/**
	 * 日视图 -- 查询
	 * 
	 * @author syl
	 * @date 2014-1-12
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public TeeJson getCalOfDayByUserIds(HttpServletRequest request)
			throws Exception {
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Calendar cal = Calendar.getInstance();
		//获取日期
		String dateStr = TeeStringUtil.getString(request.getParameter("dateStr"), TeeUtility.getCurDateTimeStr("yyyy-MM-dd"));
		
		Date startTimeDate = TeeUtility.parseDate("yyyy-MM-dd", dateStr);
		Date endTimeDate = TeeUtility.parseDate("yyyy-MM-dd HH:mm:ss", dateStr + " 23:59:59");
		TeeJson json = new TeeJson();

		Date currDate = new Date();
		long currTime = currDate.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormatday = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy年-MM月-w周-dd日");
		SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat4 = new SimpleDateFormat("MM/dd E");
		SimpleDateFormat dateFormat5 = new SimpleDateFormat("E");
		String status = request.getParameter("status");
		String userIds = request.getParameter("userIds");


		// 获取时间
		List<Map> dateList = new ArrayList<Map>();
		Calendar[] startAndEndCalendar = new Calendar[1];
		Calendar calendarDate = Calendar.getInstance();
		calendarDate.setTime(startTimeDate);
		startAndEndCalendar[0] = calendarDate;
		
		Map map = new HashMap();
		map.put("simpDate",dateFormat3.format(startTimeDate));
		map.put("fullDate",dateFormat4.format(startTimeDate));
		map.put("week",dateFormat5.format(startTimeDate));
		dateList.add(map);
		
		List<TeeCalendarAffairModel> listCalendar = new ArrayList<TeeCalendarAffairModel>();
		List<TeeCalendarAffairModel> listAffair = new ArrayList<TeeCalendarAffairModel>();
		if (!TeeUtility.isNullorEmpty(userIds)) {
			List<TeeCalendarAffair> calList = calendarDao.selectLeaderCalendar(userIds, startTimeDate.getTime(),endTimeDate.getTime(), status);
			for (int i = 0; i < calList.size(); i++) {
				TeeCalendarAffairModel calendarModel = new TeeCalendarAffairModel();
				TeeCalendarAffair calendar = calList.get(i);
				if(calendar.getCalAffType() == 1){// 周期性事务
					//calendarModel= affairService.parseAffairModel(calendar);
					List<TeeCalendarAffairModel> affairModelList = getIsUseAffair(calendar, startAndEndCalendar);
					listAffair.addAll(affairModelList);
				}else{//日程
					Calendar startCal = Calendar.getInstance();
					startCal.setTime(startTimeDate);
					Calendar endCal = Calendar.getInstance();
					endCal.setTime(endTimeDate);
					calendarModel = parseWeekCalendarmodel(calendar, startCal, endCal);
					listCalendar.add(calendarModel);
				}
			}
		}
		Map dataMap = new HashMap();
		dataMap.put("dateList", dateList);
		dataMap.put("calendarList", listCalendar);
		dataMap.put("affairList", listAffair);
		json.setRtData(dataMap);
		json.setRtState(true);
		return json;
	}

	
	
	/**
	 * 月视图 -- 查询
	 * 
	 * @author syl
	 * @date 2014-1-12
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public TeeJson getCalOfMonthByUserId(HttpServletRequest request)
			throws Exception {
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Calendar cal = Calendar.getInstance();
		
		int year = TeeStringUtil.getInteger(request.getParameter("year"),cal.get(Calendar.YEAR));
		int month = TeeStringUtil.getInteger(request.getParameter("month"),cal.get(Calendar.MONTH));
		//System.out.println(month);
		cal.set(year, month -1, 1);
		int maxDay=cal.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数 
		//获取日期
		String monthStr = "01";
		if(month < 10){
			monthStr = "0" + month; 
		}
		String dateStr = year + "-" + monthStr ;
		
		Date startTimeDate = TeeUtility.parseDate("yyyy-MM-dd", dateStr + "-01");
		Date endTimeDate = TeeUtility.parseDate("yyyy-MM-dd HH:mm:ss", dateStr + "-" + maxDay + " 23:59:59");
		TeeJson json = new TeeJson();

		Date currDate = new Date();
		long currTime = currDate.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormatday = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy年-MM月-w周-dd日");
		SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat4 = new SimpleDateFormat("MM/dd E");
		SimpleDateFormat dateFormat5 = new SimpleDateFormat("E");
		String status = request.getParameter("status");
		String userIds = request.getParameter("userIds");


		// 获取时间
		List<Calendar> listCalendarDate = new ArrayList<Calendar>();
		List<Map> dateList = getMonthDateList(year, month , listCalendarDate);
		List<TeeCalendarAffairModel> listCalendar = new ArrayList<TeeCalendarAffairModel>();
		List<TeeCalendarAffairModel> listAffair = new ArrayList<TeeCalendarAffairModel>();
		if (!TeeUtility.isNullorEmpty(userIds)) {
			List<TeeCalendarAffair> calList = calendarDao.selectLeaderCalendar(userIds, startTimeDate.getTime(),endTimeDate.getTime(), status);
			for (int i = 0; i < calList.size(); i++) {
				TeeCalendarAffairModel calendarModel = new TeeCalendarAffairModel();
				TeeCalendarAffair calendar = calList.get(i);
				if(calendar.getCalAffType() == 1){// 周期性事务
					/*calendarModel= affairService.parseAffairModel(calendar);
				
					listAffair.add(calendarModel);*/
					//System.out.println(listCalendarDate);
					Calendar[] calArray = new Calendar[listCalendarDate.size()];
					 listCalendarDate.toArray(calArray);
					List<TeeCalendarAffairModel> affairModelList = getIsUseAffair(calendar,calArray );
					listAffair.addAll(affairModelList);
				}else{//日程
					Calendar startCal = Calendar.getInstance();
					startCal.setTime(startTimeDate);
					Calendar endCal = Calendar.getInstance();
					endCal.setTime(endTimeDate);
					calendarModel = parseWeekCalendarmodel(calendar, startCal, endCal);
					listCalendar.add(calendarModel);
				}
			}
		}
		Map dataMap = new HashMap();
		dataMap.put("dateList", dateList);
		dataMap.put("calendarList", listCalendar);
		dataMap.put("affairList", listAffair);
		json.setRtData(dataMap);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 将周期性事务转换为有效的事务，如果周 为多个 ，如果是月，也是多
	 * @author syl
	 * @date 2014-1-18
	 * @param affair 周期性事务对象
	 * @param dateList  日期数组  日、周、月
	 * @return
	 */
	public List<TeeCalendarAffairModel> getIsUseAffair(TeeCalendarAffair affair ,Calendar[] dateList ){
		List<TeeCalendarAffairModel> affList = new ArrayList<TeeCalendarAffairModel>();
	    int type = affair.getRemindType();//提醒类型
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");

		for (int i = 0; i < dateList.length; i++) {
			Calendar cal = dateList[i];
			TeeCalendarAffairModel model = new TeeCalendarAffairModel();
			int weekInt = cal.get(Calendar.DAY_OF_WEEK);// 周几
			//if(i == 0){
				System.out.println(cal.getTimeInMillis());
			//}
			if(affair.getStartTime() > (cal.getTimeInMillis() + 24*60*60*1000 -1000)){
				continue;
			}
			// 判断日提醒
			if (type == 2) {
				int isWeekend = affair.getIsWeekend();
				if (affair.getEndTime() > 0) {// 结束时间为空
					if (affair.getEndTime() >= cal.getTimeInMillis()) {// 结束时间比当前时间要大
						if (isWeekend == 1 ) {// 排除周末
							if( weekInt != 1 && weekInt != 7){
								model = affairService.parseAffairModel(affair);
								model.setStartTimeTempStr(TeeUtility.getDateTimeStr(cal.getTime()));
								affList.add(model);//
							}
							
						} else {
							model = affairService.parseAffairModel(affair);
							model.setStartTimeTempStr(TeeUtility.getDateTimeStr(cal.getTime()));
							affList.add(model);//
						}
					}
				} else {
					if (isWeekend == 1 && weekInt != 1 && weekInt != 7) {// 排除周末
						model = affairService.parseAffairModel(affair);
						model.setStartTimeTempStr(TeeUtility.getDateTimeStr(cal.getTime()));
						affList.add(model);//
					} else {
						model = affairService.parseAffairModel(affair);
						model.setStartTimeTempStr(TeeUtility.getDateTimeStr(cal.getTime()));
						
						affList.add(model);//
					}
				}
				// 判断周提醒
			} else if (type == 3) {
				if (affair.getEndTime() > 0) {// 结束时间不为空
					if (affair.getEndTime() >= cal.getTimeInMillis()) {// 结束时间比当前时间要大

					} else {
						continue;
					}
				}

				String week = affair.getRemindDate();
				int today = cal.get(Calendar.DAY_OF_WEEK);// 获取星期几
				if (today == 1) {
					today = 7;
				} else {
					today = today - 1;
				}
				if (String.valueOf(today).equals(week)) {// 相等
					model = affairService.parseAffairModel(affair);
					model.setStartTimeTempStr(TeeUtility.getDateTimeStr(cal.getTime()));
					affList.add(model);//
				}
				// 判断月提醒

			} else if (type == 4) {
				if (affair.getEndTime() > 0) {
					if (affair.getEndTime() >= cal.getTimeInMillis()) {// 结束时间比当前时间要大
					} else {
						continue;
					}
				}
				String d = TeeStringUtil.getString(affair.getRemindDate(), "");
				int day = cal.get(Calendar.DAY_OF_MONTH);
				if (d.equals(day + "")) {
					model = affairService.parseAffairModel(affair);
					model.setStartTimeTempStr(TeeUtility.getDateTimeStr(cal.getTime()));
					affList.add(model);//
				}
				// 按年提醒
			} else if (type == 5) {
				if (affair.getEndTime() > 0) {
					if (affair.getEndTime() >= cal.getTimeInMillis()) {// 结束时间比当前时间要大
					} else {
						continue;
					}
				}
				String monthday = affair.getRemindDate();
				String m = monthday.split("-")[0];
				String d = monthday.split("-")[1];
				if (m.length() == 1) {
					m = "0" + m;
				}
				if (d.length() == 1) {
					d = "0" + d;
				}

				if (dateFormat.format(cal.getTime()).equals(m + d)) {
					model = affairService.parseAffairModel(affair);
					model.setStartTimeTempStr(TeeUtility.getDateTimeStr(cal.getTime()));
					affList.add(model);//
				}
			}
		}
		return affList;    
	}
	public static void main(String[] args) throws ParseException {
		Calendar ca = Calendar.getInstance();
		SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat3.parse("2014-01-17");
		//System.out.println(date.getTime());
		ca.setTime(date);
		//System.out.println(ca.getTimeInMillis());
	}
	/**
	 * 获取当天的所有天数  ----  月视图
	 * @author syl
	 * @date 2014-1-14
	 * @param year
	 * @param month
	 * @param calendarList
	 * @return
	 */
	public List<Map> getMonthDateList(int year , int month , List<Calendar> calendarList){
		SimpleDateFormat simDateFor = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormatFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormatDayWeek = new SimpleDateFormat("MM/dd E");
		SimpleDateFormat dateFormatWeek = new SimpleDateFormat("E");
		List<Map> dateList = new ArrayList<Map>();
		Calendar c = Calendar.getInstance();
		// 判断月初是第几周
		c.set(year, month - 1, 1);
		int beginWeekth = c.get(Calendar.WEEK_OF_YEAR);
		// 判断这个月1号是星期几
		int beginWeek = c.get(Calendar.DAY_OF_WEEK);//1、2、3、4、5、6、7 （日、一、二、三、四、五、六）
		int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);// 本月份的天数
		// 判断这个月最后一天是星期几
		c.set(year, month - 1, maxDay);
		int endWeek = c.get(Calendar.DAY_OF_WEEK);
		// 判断这个月最后一天是第几周
		int endWeekth = c.get(Calendar.WEEK_OF_YEAR);
		// 如果这个月的最后一天是星期天的话，那么最未周-1
		if (endWeek == 1) {
			endWeekth = endWeekth - 1;
		}
		int maxWeekTh = TeeDateUtil.getMaxWeekOfYear(year);//  一年最大周
		//System.out.println(maxDay);
		if(endWeekth < maxWeekTh && month == 12 ){//考虑12月份
			endWeekth = 53;
		}
		// 如果这个月的第一天是星期天的话，那么起试周-1；
		if (beginWeek == 1) {
			beginWeekth = beginWeekth - 1;
		}
		if(beginWeek==1){//周日
			beginWeek = 7;  
		}else{
			beginWeek = beginWeek - 1;
		}
		if(endWeek==1){//周日
			endWeek = 7;  
		}else{
			endWeek = endWeek - 1;
		}
		for (int i = beginWeekth; i <= endWeekth; i++) {
			Calendar[] calList = TeeDateUtil.getStartToEndDate(year, i);
			Map weekthMap = new HashMap();
			weekthMap.put("weekth", i);
			List<Map> dateMap = new ArrayList<Map>();
			for (int j = 0; j < calList.length; j++) {
				Map map = new HashMap();
				boolean isCurrMonth = true;
				if(i == beginWeekth && (j + 1) < beginWeek ){//如果是第一周，且是从星期一开始
					isCurrMonth = false;
				}
				if(i == endWeekth && (j + 1) > endWeek ){//如果是第一周，且是从星期一开始
					isCurrMonth = false;
				}
				map.put("simpDate", simDateFor.format(calList[j].getTime()));
				map.put("fullDate", dateFormatFull.format(calList[j].getTime()));
				map.put("weekDate", dateFormatDayWeek.format(calList[j].getTime()));
				map.put("weekStr", dateFormatWeek.format(calList[j].getTime()));
				map.put("day", calList[j].get(Calendar.DAY_OF_MONTH));
				map.put("isCurrMonth", isCurrMonth);
				dateMap.add(map);
				if(isCurrMonth){
					Calendar cal = TeeUtility.getMinTimeOfDayCalendar(calList[j]);
					calendarList.add(cal);
				}
			}
			weekthMap.put("dateList", dateMap);
			dateList.add(weekthMap);
		}
		return dateList;
	}


	/***
	 * 将对象转换模版对襄樊
	 * @author syl
	 * @date 2014-1-12
	 * @param cal
	 * @return
	 */
	public 	TeeCalendarAffairModel parseWeekCalendarmodel(TeeCalendarAffair cal , Calendar startWeekDate  , Calendar endWeekDate){
		TeeCalendarAffairModel model =  new TeeCalendarAffairModel();
		SimpleDateFormat dateFormatday = new SimpleDateFormat("yyyy-MM-dd");
		BeanUtils.copyProperties(cal, model);
		if(cal.getStartTime() > 0){
			Date startDate = new Date();
			startDate.setTime(cal.getStartTime());
			model.setStartTimeStr(TeeDateUtil.format(startDate, "yyyy-MM-dd HH:mm"));
		}
		if(cal.getEndTime() > 0){
			Date startDate = new Date();
			startDate.setTime(cal.getEndTime());
			model.setEndTimeStr(TeeDateUtil.format(startDate, "yyyy-MM-dd HH:mm"));
		}
		if(cal.getManager() != null){//安排人
			model.setManagerId(cal.getManager().getUuid() + "");
			model.setManagerName(cal.getManager().getUserName());
		}
		String actorIds = "";
		String actorNames = "";
		if(cal.getActor() != null && cal.getActor().size()>0 ){//安排人
			for (int i = 0; i < cal.getActor().size() ; i++) {
				actorIds = actorIds + cal.getActor().get(i).getUuid() + ",";
				actorNames = actorNames + cal.getActor().get(i).getUserName() + ",";
			}
		}
		model.setActorIds(actorIds);
		model.setActorNames(actorNames);
		model.setUserId(cal.getUser().getUuid() + "");
		model.setUserName(cal.getUser().getUserName());
		Date currTime = new Date();
		

		long begin = 0;
		long end = 0;
		int statusTemp = 0;// 进行中 判断判断状态

		// System.out.println(calendar.getCalTime());
		begin = cal.getStartTime();
		end = cal.getEndTime();
		String status = "0";//完成状态
		String overStatus = cal.getOverStatus() + "";
		status = isTimeOut(cal.getStartTime(), cal.getEndTime(), currTime, overStatus);
		if(TeeUtility.isNullorEmpty(cal.getCalLevel())){
			model.setCalLevel("0");
		}
		model.setStatus(status);
		
		int  dayStatus = 0;
		//跨天状态
		String dateStr1 = model.getStartTimeStr().substring(0, 10); 
		String dateStr2 = "";
		if(!TeeUtility.isNullorEmpty(model.getEndTimeStr())){
			dateStr2=  model.getEndTimeStr().substring(0, 10); 
			 if(!dateStr1.equals(dateStr2)){
		          if(dateFormatday.format(startWeekDate.getTime()).compareTo(dateStr1)>0
		        		  && dateFormatday.format(endWeekDate.getTime()).compareTo(dateStr2)>=0){
		            dayStatus = 1;//过期跨周
		          }else if(dateFormatday.format(startWeekDate.getTime()).compareTo(dateStr1)<=0
		        		  && dateFormatday.format(endWeekDate.getTime()).compareTo(dateStr2)<0){
		            dayStatus = 2;//未过跨周
		          }else if(dateFormatday.format(startWeekDate.getTime()).compareTo(dateStr1)>0
		        		  &&dateFormatday.format(endWeekDate.getTime()).compareTo(dateStr2)<0){
		            dayStatus = 3;//跨周
		          }else{
		            dayStatus = 4;//本周跨天
		          }
		      }
		}else{//跨周
			 dayStatus = 3;//跨周
		}
		model.setAllDayStatus(dayStatus);
		if(cal.getRemindTime() > 0){
			Calendar currSimpCal = TeeUtility.getMinTimeOfDayCalendar(null);//当前一天日程  去掉时分秒
			long test = currSimpCal.getTimeInMillis() + cal.getRemindTime() ;
			currSimpCal.setTimeInMillis(test);
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			Date ss = currSimpCal.getTime();
			String remindTmeStr = TeeUtility.getDateStrByFormat(ss, format);
			model.setRemindTimeStr(remindTmeStr);
		}
		return model;
	}
	
	
	/**
	 * 判断日程进行状态
	 * @author syl
	 * @date 2014-1-5
	 * @param startTime
	 * @param endTime
	 * @param currTime
	 * @param overStatus 状态
	 * @return
	 */
	public  String isTimeOut(long startTime  , long endTime , Date currTime , String overStatus1){
		//boolean isTimeOut = false;
		String status = "0";//未完成
	    if ((overStatus1 == null || overStatus1.equals("0") || overStatus1.trim().equals("")) ) {
	        if (currTime.getTime() > endTime && endTime > 0) {
	            status = "2";// 超时
	        }else  if(currTime.getTime() < startTime){
	        	status = "3";//未开始
	        }else{
	        	status = "4";//进行中
	        }
	    }else{
	    	status = overStatus1;//已完成
	    }
		
        return status;
	}

	
	/**
	 * 日程查询
	 * @author syl
	 * @date 2014-1-3
	 * @param request
	 * @return
	 */
	public List<Map<String, String>> queryCal(HttpServletRequest request ,TeeCalendarAffairModel model){
		 TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		 List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
	      String sendTimeMin = request.getParameter("sendTimeMin");
	      String sendTimeMax = request.getParameter("sendTimeMax");
	      String calLevel = request.getParameter("calLevel");
	      String calType = request.getParameter("calType");
	      // System.out.println(calType);
	      String overStatus = request.getParameter("overStatus");
	      String content = request.getParameter("content");
	      String deptId = request.getParameter("deptId");//有可能是部门查询，也有可能是按人员查询，已逗号分隔的是部门 例如：12;dept

	      Map postPrivMap = getDeptOrPersonByPostAndModule(user, "3");
	      List<TeeCalendarAffair> calendarList  = calendarDao.leaderQueryCal(user , deptId, model , postPrivMap);
	      
	      Date date = new Date();
	      long dateTime = date.getTime();
	      long begin = 0;
	      long end = 0;
	      for (int i = 0; i < calendarList.size(); i++) {
	        String status = "0";// 进行中 判断判断状态

	        Map<String, String> map = new HashMap<String, String>();
	        TeeCalendarAffair calendar = calendarList.get(i);
	        map.put("seqId", String.valueOf(calendar.getSid()));
	        map.put("userId", calendar.getUser().getUuid() + "");
	        map.put("userName",  calendar.getUser().getUserName());
	        map.put("calLevel", calendar.getCalLevel());
	        map.put("calType", calendar.getCalType() + "");
	        map.put("content", calendar.getContent());
	        map.put("isDelete", "0");
	        if(calendar.getUser().getUuid() == user.getUuid() ){
	        	 map.put("isDelete", "1");//能删除
	        }
	        if (calendar.getStartTime() >0) {
	        	Date sdate = new Date();
	        	sdate.setTime(calendar.getStartTime());
	        	map.put("calTime", TeeDateUtil.getDateTimeStr(sdate));
	        } else {
	          map.put("calTime", "");
	        }
	        if (calendar.getEndTime() > 0) {
	        	Date edate = new Date();
	        	edate.setTime(calendar.getEndTime());
	         	map.put("endTime", TeeDateUtil.getDateTimeStr(edate));
	        } else {
	          map.put("endTime", "");
	        }

	        map.put("overStatus", calendar.getOverStatus() + "");
	        // System.out.println(calendar.getManagerId());
	        if (calendar.getManager() != null) {
	        	map.put("managerName", calendar.getManager().getUserName());
	        	if(calendar.getManager().getUuid()  == user.getUuid()){
	        		map.put("isDelete", "1");//能删除
	        	}
	        } else {
	        	map.put("managerName", "");
	        }
	        String overStatus1 = calendar.getOverStatus() + "";
	        if (overStatus1 == null || overStatus1.equals("0")
	            || overStatus1.trim().equals("")) {
	          begin = calendar.getStartTime();
	          end = calendar.getEndTime();
	          if (dateTime < begin) {
	            status = "1";// 未开始

	          }
	          if (dateTime > end) {
	            status = "2";// 超时
	          }
	        }
	        map.put("status", status);
	        list.add(map);
	      }
		return list;
	}
	
	
	
	/**
	 * 导出日程安排数据
	 * @author syl
	 * @date 2014-1-22
	 * @param model
	 * @param loginPerson
	 * @return
	 */
	public ArrayList<TeeDataRecord> exportLeaderCalendarToCsv(HttpServletRequest request ,TeeCalendarAffairModel model){
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		ArrayList<TeeDataRecord> list = new ArrayList<TeeDataRecord>();
		String sendTimeMin = request.getParameter("sendTimeMin");
		String sendTimeMax = request.getParameter("sendTimeMax");
		String calLevel = request.getParameter("calLevel");
		String calType = request.getParameter("calType");
		      // System.out.println(calType);
		String overStatus = request.getParameter("overStatus");
	    String content = request.getParameter("content");
		String deptId = request.getParameter("deptId");//有可能是部门查询，也有可能是按人员查询，已逗号分隔的是部门 例如：12;dept

		Map postPrivMap = getDeptOrPersonByPostAndModule(user, "3");
		List<TeeCalendarAffair> calendarList  = calendarDao.leaderQueryCal(user , deptId, model , postPrivMap);
		 
		for (int i = 0; i <calendarList.size(); i++) {
			TeeCalendarAffair calendar = calendarList.get(i);
			TeePerson person = calendar.getUser();
			TeeDataRecord dr = new TeeDataRecord();
			
			String deptName = "";//部门名称
	
			if(person.getDept() != null){
				deptName = person.getDept().getDeptName();
			}
			dr.addField("部门", deptName);
			dr.addField("姓名", person.getUserName());
			String startTime = "";
			String endTime = "";
			Date date = new Date();
			if(calendar.getStartTime() > 0){
				date.setTime(calendar.getStartTime());
				startTime = format.format(date);
			}
			
			if(calendar.getEndTime() > 0){
				date.setTime(calendar.getEndTime());
				endTime = format.format(date);
			}
			
			dr.addField("开始时间", startTime);
			dr.addField("结束时间", endTime);
			
			String calTypeStr = "工作事务";
			if(calendar.getCalType() == 2){
				calTypeStr = "个人事务";
			}
			dr.addField("事务类型", calTypeStr);
			dr.addField("事务内容", calendar.getContent());
			String managerName = "";
			if(calendar.getManager() != null){
				managerName = calendar.getManager().getUserName();
			}
			dr.addField("安排人", managerName);
			list.add(dr);
		}
		return list;
	}

	public TeeCalendarAffairDao getCalendarDao() {
		return calendarDao;
	}

	public void setCalendarDao(TeeCalendarAffairDao calendarDao) {
		this.calendarDao = calendarDao;
	}

	public TeeCalendarAffairService getCalendarAffairService() {
		return calendarAffairService;
	}

	public void setCalendarAffairService(
			TeeCalendarAffairService calendarAffairService) {
		this.calendarAffairService = calendarAffairService;
	}

	public TeeCalAffairService getAffairService() {
		return affairService;
	}

	public void setAffairService(TeeCalAffairService affairService) {
		this.affairService = affairService;
	}

	public TeeModulePrivService getModulePrivService() {
		return modulePrivService;
	}

	public void setModulePrivService(TeeModulePrivService modulePrivService) {
		this.modulePrivService = modulePrivService;
	}

	public TeePersonService getPersonService() {
		return personService;
	}

	public void setPersonService(TeePersonService personService) {
		this.personService = personService;
	}

	public TeePersonDao getPersonDao() {
		return personDao;
	}
	
	public void setPersonDao(TeePersonDao personDao) {
		this.personDao = personDao;
	}

	public TeeDeptService getDeptService() {
		return deptService;
	}

	public void setDeptService(TeeDeptService deptService) {
		this.deptService = deptService;
	}


}
