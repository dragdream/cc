package com.tianee.oa.core.org.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.meeting.service.TeeMeetingEquipmentService;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserGroup;
import com.tianee.oa.core.org.model.TeePersonModel;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeeOrgSelectService;
import com.tianee.oa.core.org.service.TeePersonManagerI;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.org.service.TeeUserGroupService;
import com.tianee.oa.core.priv.bean.TeeMenuGroup;
import com.tianee.oa.core.priv.model.TeeMenuGroupModul;
import com.tianee.oa.core.priv.model.TeeModulePrivModel;
import com.tianee.oa.core.priv.service.TeeMenuGroupService;
import com.tianee.oa.core.priv.service.TeeModulePrivService;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeManagerPostPersonDataPrivModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/orgSelectManager")
public class TeeOrgSelectController {
	@Autowired
	private TeeDeptService deptService;
	
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private TeeMenuGroupService menuGroupServ;
	

	@Autowired
	private TeeUserGroupService userGroupServ;
	
	

	@Autowired
	private TeeOrgSelectService orgSelectServ;
	
	

	@Autowired
	private TeeMeetingEquipmentService equpimentServ;
	
	@Autowired
	private TeeModulePrivService modulePrivServ;
	
	@Autowired
	private TeePersonManagerI personManagerI;
	
	
	/**
	 * 获取部门  同步记载  ---- 带权限，按模块设置
	 * @author syl
	 * @date 2013-11-21
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSelectDeptTree") 
	@ResponseBody
	public TeeJson getSelectDeptTree(HttpServletRequest request)
			throws Exception {
		/*TeeJson json = new TeeJson();
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
		try {
			json = orgSelectServ.getOrgDeptTree(request);
		} catch (Exception ex) {
			json.setRtState(false);
			json.setRtMsg(ex.getMessage());
			throw ex;
		}
		json.setRtState(true);*/
		String moduleId = TeeStringUtil.getString(request.getParameter("moduleId"), "");
		TeeJson json  = orgSelectServ.getSendPostDept(request, moduleId);
		return json;
	}
	
	
	/**
	 * 获取当前登陆人有管理权限的部门树
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSelectDeptTreePost") 
	@ResponseBody
	public TeeJson getSelectDeptTreePost(HttpServletRequest request)
			throws Exception {
		
		String moduleId = TeeStringUtil.getString(request.getParameter("moduleId"), "");
		TeeJson json  = orgSelectServ.getSelectDeptTreePost(request, moduleId);
		return json;
	}
	
	
	
	/**
	 * 带权限 ---获取角色，按模块设置
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectUserPrivList") 
	@ResponseBody
	public TeeJson selectUserPrivList(HttpServletRequest request)
			throws Exception {
		String moduleId = TeeStringUtil.getString(request.getParameter("moduleId"), "");
		TeeJson json  = orgSelectServ.getSendPostUserRole(request, moduleId);
		return json;
	}
	/**
	 * 带权限 ---获取角色树，按模块设置
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectUserPrivTree") 
	@ResponseBody
	public TeeJson selectUserPrivTree(HttpServletRequest request)
			throws Exception {
		String moduleId = TeeStringUtil.getString(request.getParameter("moduleId"), "");
		TeeJson json  = orgSelectServ.getSendPostUserRoleTree(request, moduleId);
		return json;
	}
	
	/**
	 * 获取部门  异步加载  ---- 获取所有的
	 * @param request
	 * @param org
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAsynSelectDeptTree")
	@ResponseBody
	public TeeJson getAsynSelectDeptTree(HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
		try {
			json = deptService.getSelectDeptTree(id);
		} catch (Exception ex) {
			json.setRtState(false);
			json.setRtMsg(ex.getMessage());
			throw ex;
		}
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取部门  通用选择树右边选择
	 * @param request
	 * @param org
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSelectDept")
	@ResponseBody
	public TeeJson getSelectDept(HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
		String moduleId = TeeStringUtil.getString(request.getParameter("moduleId"), "");
		try {
			//json = deptService.getSelectDept(uuid);
			json = orgSelectServ.getSendPostDeptList(request, moduleId);
		} catch (Exception ex) {
			json.setRtState(false);
			json.setRtMsg(ex.getMessage());
			throw ex;
		}
		json.setRtState(true);
		return json;
		
		/*TeeJson json = orgSelectServ.getSelectDept(request);
		return json;*/
	}
	
	
	
	
	
	/**
	 * 获取部门  一次加载  ---- 获取所有部门
	 * @param request
	 * @param org
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSelectDeptTreeAll.action")
	@ResponseBody
	public TeeJson getSelectDeptTreeAll(HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
		try {
			String deptFilter=TeeStringUtil.getString(request.getParameter("deptFilter"));
			if(!TeeUtility.isNullorEmpty(deptFilter)){//指定部门
				json=deptService.getDeptByIds(deptFilter,request);
			}else{//不指定部门
				json = deptService.getDeptTreeAll();
			}	
		} catch (Exception ex) {
			json.setRtState(false);
			json.setRtMsg(ex.getMessage());
			throw ex;
		}
		json.setRtState(true);
		return json;
	}
	
	
	
	
	/**
	 *  查询部门的人员 ---带权限
	 * @author syl
	 * @date 2013-11-16
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSelectUserByDept.action")
	@ResponseBody
	public TeeJson getSelectUserByDept(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		String deptId = request.getParameter("deptId") == null ? "0" : request.getParameter("deptId") ;
		String isCheckedState = request.getParameter("isCheckedState") == null ? "" : request.getParameter("isCheckedState") ;
		//String userFilter=request.getParameter("userFilter");
		try {
			List<TeePerson> list  =  new ArrayList<TeePerson>();
			if(isCheckedState.equals("0") || isCheckedState.equals("1") ){//0-勾选为false,1-勾选为true
				TeeDepartment dept = deptService.selectDeptByUuid(deptId);
				String deptLevel = dept.getGuid();
				if(!TeeUtility.isNullorEmpty(dept.getDeptParentLevel())){
					deptLevel = dept.getDeptParentLevel() + deptLevel;
				}
				list = orgSelectServ.selectPersonDeptAndChildDeptAndFilter(deptId, deptLevel,request);
			}else{
				list =  orgSelectServ.selectPersonByDeptIdAndFilter(deptId, request);
			}
			 
			List<TeePersonModel> listModel  = new ArrayList<TeePersonModel>();
			String presonIds = "";
			int isHave=0;
			TeePersonModel model = null;
			for (int i = 0; i < list.size(); i++) {
				model = new TeePersonModel();
//				BeanUtils.copyProperties(list.get(i),model );
				model.setUserName(list.get(i).getUserName());
				model.setUuid(list.get(i).getUuid());
				if(list.get(i).getUserRole()!=null){
					model.setUserRoleStrName(list.get(i).getUserRole().getRoleName());
				}
				if(list.get(i).getDept()!=null){
					model.setDeptIdName(list.get(i).getDept().getDeptName());
					model.setDeptId(list.get(i).getDept().getUuid()+"");
				}
				model.setOrgName(deptService.getCurrentPersonOrgName(list.get(i)));
				model.setPerformCode(list.get(i).getPerformCode());
				
				isHave=personService.selectOnlineByUserId(list.get(i).getUuid());
				if(isHave!=0){
					model.setUserOnlineStatus("1");
				}else{
					model.setUserOnlineStatus("0");
				}
				listModel.add(model);
				presonIds = presonIds + model.getUuid() + ",";
			}
			if(presonIds.endsWith(",")){
				presonIds = presonIds.substring(0, presonIds.length() -1);
			}
			json.setRtMsg(presonIds);
			json.setRtData(listModel);
			json.setRtState(true);
		} catch (Exception ex) {
			json.setRtState(false);
			json.setRtMsg(ex.getMessage());
			System.out.println(ex.getMessage());
		}
		return json;
	}
	
	/***
	 * 获取通用数菜单组
	 * @param menugroup
	 * @param response
	 * @return
	 */
	@RequestMapping("/getMenuGroupSelectAll.action")
	@ResponseBody
	public TeeJson getMenuGroupSelectAll(TeeMenuGroup menugroup,HttpServletResponse response) {
		
		TeeJson json = new TeeJson();
		List<TeeMenuGroupModul> list =  menuGroupServ.getMenuGroupSelectAll();
		json.setRtData(list);
		json.setRtState(true);
		json.setRtMsg("查询功能");
		return json;
	}
	
	/***
	 * 获取通用选择部门--本部门人员
	 * @param menugroup
	 * @param response
	 * @return
	 */
	@RequestMapping("/getSelectUserByCurrentDept.action")
	@ResponseBody
	public TeeJson getSelectUserByCurrentDept(HttpServletRequest request) {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String deptId = "";
		String deptName = "";
		if(person.getDept() != null){
			deptId = person.getDept().getUuid() + "";
			deptName = person.getDept().getDeptName();
		}
		TeeJson json = new TeeJson();
		List<Map> pList = new ArrayList<Map>();
		int isHave = 0;
		if(!TeeUtility.isNullorEmpty(deptId)){
			List<TeePerson> list =  orgSelectServ.selectPersonByDeptIdAndFilter( deptId,request);
			
			for (int i = 0; i < list.size(); i++) {
				Map map = new HashMap();
				map.put("uuid", list.get(i).getUuid());
				map.put("userName", list.get(i).getUserName());
				isHave=personService.selectOnlineByUserId(list.get(i).getUuid());
				if(isHave!=0){
					map.put("userOnlineStatus","1");
				}else{
					map.put("userOnlineStatus","0");
				}
				if(list.get(i).getUserRole()!=null){
					map.put("userRoleStrName", list.get(i).getUserRole().getRoleName());
				}
				if(list.get(i).getDept()!=null){
					map.put("deptIdName", list.get(i).getDept().getDeptName());
				}
				map.put("orgName", deptService.getCurrentPersonOrgName(list.get(i)));
				pList.add(map);
			}
		}
		json.setRtData(pList);
		json.setRtState(true);
		json.setRtMsg(deptName);
		return json;
	}
	
	/***
	 * 获取相关人员列表--角色
	 * @param menugroup
	 * @param response
	 * @return
	 */
	@RequestMapping("/getSelectUserByRole.action")
	@ResponseBody
	public TeeJson getSelectUserByRole(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		String roleName = "";
		TeeJson json = new TeeJson();
		List<Map> pList = new ArrayList<Map>();
		int isHave = 0;
		if(!TeeUtility.isNullorEmpty(roleId)){
			List<TeePerson> list =  orgSelectServ.selectPersonByRoleIdAndFilter(roleId,request);
			for (int i = 0; i < list.size(); i++) {
				Map map = new HashMap();
				map.put("uuid", list.get(i).getUuid());
				map.put("userName", list.get(i).getUserName());
				isHave=personService.selectOnlineByUserId(list.get(i).getUuid());
				if(isHave!=0){
					map.put("userOnlineStatus","1");
				}else{
					map.put("userOnlineStatus","0");
				}
				pList.add(map);
			}
		}
		json.setRtData(pList);
		json.setRtState(true);
		json.setRtMsg("");
		return json;
	}
	
	
	
	/**
	 * 流程转交  选人   按角色
	 * @param request
	 * @return
	 */
	@RequestMapping("/getSelectUserByRoleWorkFlow.action")
	@ResponseBody
	public TeeJson getSelectUserByRoleWorkFlow(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		String roleName = "";
		TeeJson json = new TeeJson();
		List<Map> pList = new ArrayList<Map>();
		int isHave = 0;
		if(!TeeUtility.isNullorEmpty(roleId)){
			List<TeePerson> list =  orgSelectServ.selectPersonByRoleIdAndFilterWorkFlow(roleId,request);
			for (int i = 0; i < list.size(); i++) {
				Map map = new HashMap();
				map.put("uuid", list.get(i).getUuid());
				map.put("userName", list.get(i).getUserName());
				isHave=personService.selectOnlineByUserId(list.get(i).getUuid());
				if(isHave!=0){
					map.put("userOnlineStatus","1");
				}else{
					map.put("userOnlineStatus","0");
				}
				pList.add(map);
			}
		}
		json.setRtData(pList);
		json.setRtState(true);
		json.setRtMsg("");
		return json;
	}
	
	
	
	

	/**
	 * 根据自定义组获取人员列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPersonByUserGroup.action")
	@ResponseBody
	public TeeJson getUserGroupById(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		String userGroupId = request.getParameter("userGroupId");
		//是否处理人员Id字符串
		String userFilter = request.getParameter("userFilter") == null ? "0" :  request.getParameter("userFilter");
		String deptFilter = request.getParameter("deptFilter") == null ? "" :  request.getParameter("deptFilter");
		
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String moduleId = request.getParameter("moduleId");
		String privNoFlag = request.getParameter("privNoFlag");
		
		//System.out.println("moduleId："+moduleId);
		//System.out.println("privNoFlag："+privNoFlag);
		
		TeeModulePrivModel model = null;
		if(TeeUtility.isInteger(moduleId)){
			//如果存在按模块设置
			model = modulePrivServ.selectPrivByUserId(loginPerson.getUuid() + "", moduleId);
		}
		String roleName = "";
		List<Map> pList = new ArrayList<Map>();
		if(!TeeUtility.isNullorEmpty(userGroupId) && !TeeUtility.isNullorEmpty(userFilter)){
			TeeUserGroup userGroup  =  userGroupServ.selectUserGroupByUuid(Integer.parseInt(userGroupId));
			
			pList  = orgSelectServ.selectUserGroupByUuidAndPersonPostAndModule(userGroup ,userFilter,deptFilter ,loginPerson, model);
			//List<TeePerson> list = userGroup.getUserList();
			userFilter = userFilter + ",";
			/*for (int i = 0; i < list.size(); i++) {
				if(!userFilter.equals("0,")){//不为零需要处理
					if(userFilter.indexOf(list.get(i).getUuid() + ",") < 0){
						break;
					}
				}
				Map map = new HashMap();
				map.put("uuid", list.get(i).getUuid());
				map.put("userName", list.get(i).getUserName());
				pList.add(map);
			}*/
		}
		json.setRtData(pList);
		json.setRtState(true);
		json.setRtMsg("");
		return json;
	}
	
	
	
	/**
	 * 工作流转交选人  按自定义组
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPersonByUserGroupWorkFlow.action")
	@ResponseBody
	public TeeJson getPersonByUserGroupWorkFlow(HttpServletRequest request) throws Exception {
		
		return orgSelectServ.getPersonByUserGroupWorkFlow(request);
	}
	
	
	
	
	
	
	/**
	 * 根据人员ID或者人员名称，模糊查询人员
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSelectUserByUserIdOrUserName.action")
	@ResponseBody
	public TeeJson getSelectUserByUserIdOrUserName(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		String userInfo = request.getParameter("user");
		List<Map> pList = new ArrayList<Map>();
//		if(!TeeUtility.isNullorEmpty(userInfo)){
//			pList =  orgSelectServ.getSelectUserByUserIdOrUserName(request);
//		}
		pList =  orgSelectServ.getSelectUserByUserIdOrUserName(request);
		json.setRtData(pList);
		json.setRtState(true);
		json.setRtMsg("");
		return json;
	}
	
	
	/**
	 * 根据部门名称，模糊查询部门
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSelectDeptByDeptName.action")
	@ResponseBody
	public TeeJson getSelectDeptByDeptName(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		String deptInfo = request.getParameter("dept");
		List<Map> pList = new ArrayList<Map>();
//		if(!TeeUtility.isNullorEmpty(userInfo)){
//			pList =  orgSelectServ.getSelectUserByUserIdOrUserName(request);
//		}
		pList =  orgSelectServ.getSelectDeptByDeptName(request);
		json.setRtData(pList);
		json.setRtState(true);
		json.setRtMsg("");
		return json;
	}
	
	
	/**
	 * 根据部门名称，模糊查询部门（有管理权限的部门）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectPostDeptByDeptName.action")
	@ResponseBody
	public TeeJson selectPostDeptByDeptName(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		String deptInfo = request.getParameter("dept");
		List<Map> pList = new ArrayList<Map>();
//		if(!TeeUtility.isNullorEmpty(userInfo)){
//			pList =  orgSelectServ.getSelectUserByUserIdOrUserName(request);
//		}
		pList =  orgSelectServ.selectPostDeptByDeptName(request);
		json.setRtData(pList);
		json.setRtState(true);
		json.setRtMsg("");
		return json;
	}
	
	/**
	 * 根据人员ID或者人员名称，模糊查询人员 --- 主界面查询 -不带权限
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryUserByUserIdOrUserName.action")
	@ResponseBody
	public TeeJson queryUserByUserIdOrUserName(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		List<Map> pList =  orgSelectServ.queryUserByUserIdOrUserName(request);
		json.setRtData(pList);
		json.setRtState(true);
		json.setRtMsg("");
		return json;
	}
	
	
	/**
	 * 获取所有部门在线人员
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getOnlineOrgUserTree")
	@ResponseBody
	public TeeJson getOnlineOrgUserTree(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		json = orgSelectServ.selectOrgOnlineUserTree(id,person);
		return json;
	}
	
	
	/**
	 * 获取所有部门人员 (包括在线和离线)
	 * 异步加载
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAllOrgUserTree")
	@ResponseBody
	public TeeJson getAllOrgUserTree(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		json = orgSelectServ.getAllOrgUserTree(id,person);
		return json;
	}
	
	
	
	/**
	 * @author syl
	 * 获取组织机构树  --- 获取带权限的部门树 （按模块权限设置、按管理范围）
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("getLeaderPostOrgTree")
	@ResponseBody
	public TeeJson getLeaderPostOrgTree(HttpServletRequest request ){
		TeeJson json = orgSelectServ.getLeaderPostOrgTree(request);
		return json;
	}
	
	
	
	
	
	
	/**
	 * 获取部门  一次加载  ---- 工作流获取有人员的部门   --- 需要清空没有人的部门
	 * @param request
	 * @param org
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSelectDeptTreeWorkFlow.action")
	@ResponseBody
	public TeeJson getSelectDeptTreeWorkFlow(HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
		try {
			json = orgSelectServ.getSelectDeptTreeWorkFlow(request);
		} catch (Exception ex) {
			json.setRtState(false);
			json.setRtMsg(ex.getMessage());
			throw ex;
		}
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 *  查询部门的人员  工作流选人
	 * @author syl
	 * @date 2013-11-16
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSelectUserByDeptWorkFlow.action")
	@ResponseBody
	public TeeJson getSelectUserByDeptWorkFlow(HttpServletRequest request) throws Exception{
		return orgSelectServ.getSelectUserByDeptWorkFlow(request);
	}
	
	
	/**
	 * 获取工作流有权限办理的 人员列表   不根据部门
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSelectUserWorkFlow.action")
	@ResponseBody
	public TeeJson getSelectUserWorkFlow(HttpServletRequest request) throws Exception{
		return orgSelectServ.getSelectUserWorkFlow(request);
	}
	
	
	/**
	 *  查询部门的人员  工作流选人
	 * @author syl
	 * @date 2013-11-16
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSelectUserByCurrentDeptWorkflow.action")
	@ResponseBody
	public TeeJson getSelectUserByCurrentDeptWorkflow(HttpServletRequest request) throws Exception{
		return orgSelectServ.getSelectUserByCurrentDeptWorkflow(request);
	}
	
	
	/**
	 *  查询部门的人员  工作流选人
	 * @author syl
	 * @date 2013-11-16
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSelectUserByUserIdOrUserNameWorkflow.action")
	@ResponseBody
	public TeeJson getSelectUserByUserIdOrUserNameWorkflow(HttpServletRequest request) throws Exception{
		return orgSelectServ.getSelectUserByUserIdOrUserNameWorkflow(request);
	}
	
	/**
	 * 获取在线人员  --- 工作流
	 * @author syl
	 * @date 2014-2-23
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUserOnlineWorkFlow.action")
	@ResponseBody
	public TeeJson getUserOnlineWorkFlow(HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
		try {
			json = orgSelectServ.getUserOnlineWorkFlow(request);
		} catch (Exception ex) {
			json.setRtState(false);
			json.setRtMsg(ex.getMessage());
			throw ex;
		}
		json.setRtState(true);
		return json;
	}
	
	/**
	 * @param request
	 * @return
	 */
	@RequestMapping("/getSendPostPersonIdsPriv.action")
	@ResponseBody
	public TeeJson getSendPostPersonIdsPriv(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginUser = 
				(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData = TeeServletUtility.getParamMap(request);
		String modelId = TeeStringUtil.getString(request.getParameter("modelId"));
		requestData.put(TeeConst.LOGIN_USER, loginUser);
		TeeManagerPostPersonDataPrivModel model = personManagerI.getSendPostPersonIdsPriv(requestData, modelId, "0");
		json.setRtData(model.getPersonIds());
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 获取系统模块
	 * @param request
	 * @return
	 */
	@RequestMapping("/getSysModule")
	@ResponseBody
	public TeeJson getSysModule(HttpServletRequest request){
		Map requestrMap = TeeServletUtility.getParamMap(request);
		return orgSelectServ.getSysModule(requestrMap);
	}
	
	
	/**
	 * 获取系统模块
	 * @param request
	 * @return
	 */
	@RequestMapping("/getMeetingEquipment")
	@ResponseBody
	public TeeJson getMeetingEquipment(HttpServletRequest request){
		Map requestrMap = TeeServletUtility.getParamMap(request);
		return equpimentServ.getMeetingEquipment(requestrMap);
	}
	
	
	
	
	@RequestMapping("/getSelectUserByDepts.action")
	@ResponseBody
	public TeeJson getSelectUserByDepts(HttpServletRequest request) throws Exception{
		TeeJson json=new TeeJson();
		String deptIds=TeeStringUtil.getString(request.getParameter("deptIds"));
	    List<TeePerson> list= personService.getPersonByDeptIds(deptIds);
		List<TeePersonModel> modelList=new ArrayList<TeePersonModel>();
		TeePersonModel model=null;
	    if(list!=null&&list.size()>0){
			for (TeePerson teePerson : list) {
				model=personService.parseModel(teePerson, true);
				modelList.add(model);
			}
		}
	    json.setRtState(true);
        json.setRtData(modelList);
	    return json;
	}
	
	
	public void setDeptService(TeeDeptService deptService) {
		this.deptService = deptService;
	}
	public void setPersonService(TeePersonService personService) {
		this.personService = personService;
	}
	public void setMenuGroupServ(TeeMenuGroupService menuGroupServ) {
		this.menuGroupServ = menuGroupServ;
	}
	public void setUserGroupServ(TeeUserGroupService userGroupServ) {
		this.userGroupServ = userGroupServ;
	}
	public void setOrgSelectServ(TeeOrgSelectService orgSelectServ) {
		this.orgSelectServ = orgSelectServ;
	}
	public void setModulePrivServ(TeeModulePrivService modulePrivServ) {
		this.modulePrivServ = modulePrivServ;
	}
	
}
