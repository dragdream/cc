package com.tianee.oa.core.org.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.model.TeePersonModel;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeeOrgService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.priv.bean.TeeMenuGroup;
import com.tianee.oa.oaconst.TeeActionKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeePortalModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.secure.TeePassEncrypt;
import com.tianee.webframe.util.secure.TeePassEncryptMD5;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/personManager")
public class TeePersonController  extends BaseController{

	@Autowired
	private TeeDeptService deptService;
	@Autowired
	private TeeOrgService orgService;
	@Autowired
	private TeePersonService personService;
	
	
	
	@RequestMapping("/add.action")
	public String addPerson(TeePerson person,HttpServletRequest request)
			throws Exception {
		String sb = "";
		try {
			if(StringUtils.isBlank(person.getIsAdmin())) {
				person.setIsAdmin("0");
			}
			personService.add(person);
			sb = "{uuid:'" + person.getUuid() + "'}";
			request.setAttribute(TeeActionKeys.RET_STATE, TeeConst.RETURN_OK);
			request.setAttribute(TeeActionKeys.RET_MSRG, "成功返回结果！");
			request.setAttribute(TeeActionKeys.RET_DATA, sb.toString());
		} catch (Exception ex) {
			request.setAttribute(TeeActionKeys.RET_STATE, TeeConst.RETURN_ERROR);
			request.setAttribute(TeeActionKeys.RET_MSRG, ex.getMessage());
			throw ex;
		}
		return "/inc/rtjson.jsp";
	}
	
	
	/**
	 * 获取人员通用个列表
	 * @param dm
	 * @param portal
	 * @param response
	 * @return
	 */
	@RequestMapping("/getPersonList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, HttpServletRequest response) {
		return personService.datagrid(dm,response);
	}
	
	/**
	 * 新增或添加
	 * @param requestqueryPerson
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdate.action")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request, TeePersonModel personModel)
			throws Exception {
		if(StringUtils.isBlank(personModel.getIsAdmin())) {
			personModel.setIsAdmin("0");
		}		
		TeeJson json = personService.addOrUpdate(personModel,request);
		
		//生成人员json数据
//		orgService.generatePersonJsonData();
		return json;
	}
	
	/**
	 * 查询 byId
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPersonById.action")
	@ResponseBody
	public TeeJson getPersonById(HttpServletRequest request) throws Exception {
		String uuid = request.getParameter("uuid");
		String data = "";
		TeeJson json = new TeeJson();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (!TeeUtility.isNullorEmpty(uuid)) {
				TeePerson person = personService.selectByUuid(uuid);
				if(person != null ){
					TeePersonModel model = new TeePersonModel();
					if(person.getBirthday() != null){
						model.setBirthdayStr(TeeUtility.getDateStrByFormat(person.getBirthday(), dateFormat));
					}
					BeanUtils.copyProperties(person, model);
					if(person.getDept() != null ){//部门
						model.setDeptIdName(person.getDept().getDeptName()); 
						model.setDeptId(person.getDept().getUuid() + "");
					}
					if(person.getUserRole() != null ){//角色
						model.setUserRoleStrName(person.getUserRole().getRoleName()); 
						model.setUserRoleStr(person.getUserRole().getUuid() + "");
					}
					String userRoleOtherIds = "";
					String userRoleOtherNames = "";
					List<TeeUserRole> list = person.getUserRoleOther();
					if(list != null  && list.size() > 0){//辅助角色
						for (int i = 0; i < list.size(); i++) {
							userRoleOtherIds = userRoleOtherIds + list.get(i).getUuid() +",";
							userRoleOtherNames = userRoleOtherNames + list.get(i).getRoleName() +",";
						}
						
					}
					model.setUserRoleOtherName(userRoleOtherNames);
					model.setUserRoleOtherId(userRoleOtherIds);
					
					//辅助部门
					String deptOtherIds = "";
					String deptOtherNames = "";
					List<TeeDepartment> deptOtherList = person.getDeptIdOther();
					if(list != null  && deptOtherList.size() > 0){//辅助部门
						for (int i = 0; i < deptOtherList.size(); i++) {
							deptOtherIds = deptOtherIds + deptOtherList.get(i).getUuid() +",";
							deptOtherNames = deptOtherNames + deptOtherList.get(i).getDeptName() +",";
						}
						
					}
					model.setDeptIdOtherStr(deptOtherIds);
					model.setDeptIdOtherStrName(deptOtherNames);
					TeeAttachment attach = person.getAttach();
					if(attach!=null){
						model.setAttachId(attach.getSid());
						model.setAttachName(attach.getFileName());
					}
					//获取直属上级
					TeePerson leader = person.getLeader();
					StringBuffer ids = new StringBuffer();
					StringBuffer names = new StringBuffer();
					if(leader!=null){
						ids.append(leader.getUuid()+"");
						names.append(leader.getUserName());
					}
					
					model.setLeaderIds(ids.toString());
					model.setLeaderNames(names.toString());
					
					ids.delete(0, ids.length());
					names.delete(0,names.length());
					
					//获取直属下级
					List<TeePerson> underlings = personService.getUnderlines(person.getUuid());
					for(int i=0;i<underlings.size();i++){
						ids.append(underlings.get(i).getUuid()+"");
						names.append(underlings.get(i).getUserName());
						if(i!=underlings.size()-1){
							ids.append(",");
							names.append(",");
						}
					}
					model.setUnderlingIds(ids.toString());
					model.setUnderlingNames(names.toString());
					
					//管理范围
					
					String postDeptIds = "";
					String postDeptNames = "";
					List<TeeDepartment> postDeptList = person.getPostDept();
					if(list != null  && postDeptList.size() > 0){//管理范围
						for (int i = 0; i < postDeptList.size(); i++) {
							postDeptIds = postDeptIds + postDeptList.get(i).getUuid() +",";
							postDeptNames = postDeptNames + postDeptList.get(i).getDeptName() +",";
						}
						
					}
					model.setPostDeptStr(postDeptIds);
					model.setPostDeptStrName(postDeptNames);
					
					//可见范围
					String viewDeptIds = "";
					String viewDeptNames = "";
					if(person.getViewPriv()==2 && !TeeUtility.isNullorEmpty(person.getViewDept())){//获取指定部门
						List<TeeDepartment> viewDeptList = deptService.selectDept("from TeeDepartment where uuid in ("+person.getViewDept()+")", null);
						for(TeeDepartment d:viewDeptList){
							viewDeptIds+=d.getUuid()+",";
							viewDeptNames += d.getDeptName()+",";
						}
						if(!"".equals(viewDeptIds)){
							viewDeptIds = viewDeptIds.substring(0,viewDeptIds.length()-1);
							viewDeptNames = viewDeptNames.substring(0,viewDeptNames.length()-1);
						}
					}
					model.setViewDeptIds(viewDeptIds);
					model.setViewDeptNames(viewDeptNames);
					
					
					
					//菜单组
					String menuGroupStrs = "";
					String menuGroupStrNames = "";
					List<TeeMenuGroup> menuGroupList = person.getMenuGroups();
					if(list != null  && menuGroupList.size() > 0){//辅助角色
						for (int i = 0; i < menuGroupList.size(); i++) {
							menuGroupStrs = menuGroupStrs + menuGroupList.get(i).getUuid() +",";
							menuGroupStrNames = menuGroupStrNames + menuGroupList.get(i).getMenuGroupName() +",";
						}
	
					}
					model.setMenuGroupsStr(menuGroupStrs);
					model.setMenuGroupsStrName(menuGroupStrNames);
			
					if(person.getDynamicInfo() != null){
						model.setOnline(person.getDynamicInfo().getOnline());
					}
					TeeJsonUtil jsonUtil = new TeeJsonUtil();
					//data = jsonUtil.obj2Json(model).toString();
					json.setRtData(model);
				}else{
					json.setRtState(true);
					json.setRtMsg("没有相关人员");
				}
			
			}
			json.setRtState(true);
			
		} catch (Exception ex) {
			json.setRtState(true);
			json.setRtMsg(ex.getMessage());
			System.out.println(ex.getMessage());
		}
		return json;
	}

	/**
	 * 查询 byId  --简单，不带辅助部门、角色等等
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSimplePersonById.action")
	@ResponseBody
	public TeeJson getSimplePersonById(HttpServletRequest request) throws Exception {
		String uuid = request.getParameter("uuid");
		String data = "";
		TeeJson json = new TeeJson();
		try {
			if (!TeeUtility.isNullorEmpty(uuid)) {
				TeePerson person = personService.selectByUuid(uuid);
				if(person != null ){
					TeePersonModel model = new TeePersonModel();
					BeanUtils.copyProperties(person, model);
					json.setRtData(model);
				}else{
					json.setRtState(true);
					json.setRtMsg("没有相关人员");
				}
			
			}
			json.setRtState(true);
			
		} catch (Exception ex) {
			json.setRtState(true);
			json.setRtMsg(ex.getMessage());
			System.out.println(ex.getMessage());
		}
		return json;
	}
	
	/**
	 * 根据UUID 获取人员 信息 ---- 适用于sysUtil。js 通用方法
	 * @author syl
	 * @date 2014-2-14
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPersonNameAndUuidByUuids")
	@ResponseBody
	public TeeJson getPersonNameAndUuidByUuids(HttpServletRequest request) throws Exception {
		String uuid = request.getParameter("uuid");
		TeeJson json = new TeeJson();
		String uuids = "";
		String userNames = "";
		try {
			if (!TeeUtility.isNullorEmpty(uuid)) {
				String[] userInfos = personService.getPersonNameAndUuidByUuids(uuid);
				uuids = userInfos[0];
				userNames = userInfos[1];
			}
			json.setRtState(true);
			
		} catch (Exception ex) {
			json.setRtState(false);
			json.setRtMsg(ex.getMessage());
			System.out.println(ex.getMessage());
		}
		Map map = new HashMap();
		map.put("sid", uuids);
		map.put("userName", userNames);
		json.setRtData(map);
		return json;
	}
	
	
	/**
	 * 根据UUID 获取人员数组 信息 ---- 适用于sysUtil。js 通用方法
	 * @author syl
	 * @date 2014-2-14
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPersonListByUuids")
	@ResponseBody
	public TeeJson getPersonListByUuids(HttpServletRequest request) throws Exception {
		String uuid = request.getParameter("uuid");
		TeeJson json = new TeeJson();
		List<TeePersonModel> list = new ArrayList<TeePersonModel>();
		try {
			list = personService.getPersonListByUuids(uuid);
			
		} catch (Exception ex) {
			json.setRtState(false);
			json.setRtMsg(ex.getMessage());
		}
	
		json.setRtData(list);
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 获取部门人员树 ---无权限 syl
	 * 异步加载
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getOrgTreeAll")
	@ResponseBody
	public TeeJson getOrgTreeAll(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		json = personService.selectOrgTreeAll(id,person);
		return json;
	}

	/**
	 * 获取部门人员树 ---带权限 syl
	 * 异步加载
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getOrgTree.action")
	@ResponseBody
	public TeeJson getOrgDeptTree(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		json = personService.selectOrgTree(id,person,"0");
		return json;
	}
	
	/**
	 * 获取部门人员树 ---根据可见范围
	 * 异步加载
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getOrgTreeByViewPriv.action")
	@ResponseBody
	public TeeJson getOrgTreeByViewPriv(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
		String isAdmin = request.getParameter("isAdmin") == null ? "" : request.getParameter("isAdmin");
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		json = personService.getOrgTreeByViewPriv(id,person,isAdmin);
		return json;
	}
	/**
	 * 获取当前登录人的管理范围，部门ID串
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPostDeptIds.action")
	@ResponseBody
	public TeeJson getPostDeptIds(HttpServletRequest request) throws Exception {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		json.setRtData(personService.getLoginPersonPostDept(person));
		return json;
	}
	
	/**
	 * 检查用户名是否存在 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkUserExist.action")
	@ResponseBody
	public TeeJson checkUserExist(HttpServletRequest request) throws Exception {
		String uuid = request.getParameter("uuid") == null ? "" : request.getParameter("uuid");
		String userId = request.getParameter("userId") == null ? "" : request.getParameter("userId");
		TeeJson json = new TeeJson();
		boolean isUser = personService.checkUserExist(uuid, userId);
		if(isUser){
			json.setRtState(false);
			json.setRtMsg("用户已存在！");
		}else{
			json.setRtState(true);
		}
		return json;
	}
	
	
	/**
	 * 按条件查询----
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryPerson")
	@ResponseBody
	public TeeJson queryPerson(HttpServletRequest request , TeePersonModel model) throws Exception {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		try {
			json = personService.queryPerson(model, json,loginPerson);
			json.setRtState(true);
			json.setRtMsg("查询成功");
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			json.setRtMsg(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	
	
	
	/**
	 * 清空密码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/clearPassword.action")
	@ResponseBody
	public TeeJson clearPassword(HttpServletRequest request ) throws Exception {
		String uuids = request.getParameter("uuids");//人员UUid字符串，以逗号分隔
		TeeJson json = new TeeJson();
		try {
			if(!TeeUtility.isNullorEmpty(uuids)){
				json = personService.clearPassword(uuids);
			}
			json.setRtState(true);
			json.setRtMsg("清空密码成功");
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			json.setRtMsg(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 更新删除状态
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateDelPersonByUuids.action")
	@ResponseBody
	public TeeJson updateDelPersonByUuids(HttpServletRequest request ) throws Exception {
		String uuids = request.getParameter("uuids");//人员UUid字符串，以逗号分隔
		String  status = "1";
		TeeJson json = new TeeJson();
		try {
			if(!TeeUtility.isNullorEmpty(uuids)){
			   personService.updateDelPersonByUuids(uuids, status,request);
			}
			json.setRtState(true);
			json.setRtMsg("删除成功");
			//生成人员json数据
//			orgService.generatePersonJsonData();
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			json.setRtMsg(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	
	
	/**
	 * 批量设置人员
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mulitSetPersonByTerm.action")
	@ResponseBody
	public TeeJson mulitSetPersonByTerm(HttpServletRequest request,TeePersonModel model) throws Exception {
	
		TeeJson json = new TeeJson();
		try {
			personService.mulitSetPersonByTermService(request,model);
			json.setRtState(true);
			json.setRtMsg("设置成功");
			
			//生成人员json数据
			orgService.generatePersonJsonData();
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			json.setRtMsg(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	
	
	
	



	/**
	 * 控制面板 -- 
	 * updatePersonType 0-界面主题  1-菜单快捷组 2-桌面自定义 3-收藏夹    4-个人资料   5 - 昵称头像 6-印章密码 7-用户自定义 8-我的账户 9-修改密码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updatePersonDeskTopType")
	@ResponseBody
	public TeeJson updateDeskTop(HttpServletRequest request,TeePersonModel model) throws Exception {
	    String updatePersonDeskTopType = request.getParameter("updatePersonDeskTopType") == null ? "" : request.getParameter("updatePersonDeskTopType");
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		try {
			boolean updateStatus = false;
			if(updatePersonDeskTopType.equals("0")){//桌面主题
				updateStatus = personService.updateDeskTop(model, person);
				person.setAutoPopSms(model.getAutoPopSms());
			
			}else if(updatePersonDeskTopType.equals("1")){//菜单快捷组
				
			}else if(updatePersonDeskTopType.equals("2")){//桌面自定义
				
			}else if(updatePersonDeskTopType.equals("3")){//收藏夹  
				
			}else if(updatePersonDeskTopType.equals("4")){//个人资料
				updateStatus = personService.updateDeskTopInfo(model, person);
			}else if(updatePersonDeskTopType.equals("5")){//昵称头像
				updateStatus = personService.updateDeskTopAvatar(model, person);
			}else if(updatePersonDeskTopType.equals("6")){//印章密码
				
			}else if(updatePersonDeskTopType.equals("7")){//个人用户组
				updateStatus = personService.updateDeskTopMypriv(model, person);
			}
			else if(updatePersonDeskTopType.equals("8")){//我的账户
				updateStatus = personService.updateDeskTopMypriv(model, person);
			}else if(updatePersonDeskTopType.equals("9")){//修改密码
				person = personService.selectByUuid(person.getUuid() + "");
				String oldpassword = request.getParameter("oldPassword") == null ?  "" : request.getParameter("oldPassword");
				if (!TeePassEncryptMD5.checkCryptDynamic(oldpassword, person.getPassword().trim())){//密码不对
					json.setRtState(false);
					json.setRtMsg("原密码不正确！");
					return json;
			    }
				String newPassword = request.getParameter("newPassword") == null ?  "" : request.getParameter("newPassword");
				
				personService.updatePassword(person.getUuid() + "", newPassword);
				updateStatus = true;
				
			}else if(updatePersonDeskTopType.equals("10")){//日志
				
			}
					
			if(updateStatus){
				json.setRtState(true);
				json.setRtMsg("设置成功");
			}else{
				json.setRtState(false);
				json.setRtMsg("没有找到相关人员！");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			json.setRtMsg(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 密码或者修改密码
	 * @param request
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("/updatePassword")
	@ResponseBody

	public TeeJson updatePassword(HttpServletRequest request) throws Exception {
		return personService.updatePersonPasswordService( request);
	}
	
	
	/**
	 * 更新高速波账号或者密码
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateCloudAccountAndPwd")
	@ResponseBody
	public TeeJson updateCloudAccountAndPwd(HttpServletRequest request) throws Exception {
		return personService.updateGsbUserIdAndPassword( request);
	}
	/**
	 * 更新高速波账号或者密码
	 * @param request
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("/getCloudAccountAndPwd")
	@ResponseBody
	public TeeJson getCloudAccountAndPwd(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		 TeePerson  person =  personService.selectByUuid(loginPerson.getUuid());
		 Map map = new HashMap();
		 map.put("id", person.getUuid());
		 map.put("accountId", person.getGsbUserId());
		 map.put("accountPwd", person.getGsbPassword());
		 json.setRtData(map);
		 json.setRtState(true);
		 return json;
	}
	/**
	 * 保存人员昵称与头像
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAvatar")
	@ResponseBody
	public TeeJson updateAvatar(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//response.setCharacterEncoding("GBK");
		TeeJson json = personService.updateAvatar(request);
		return json;
	}
	
	

	/**
	 * 快捷展示区 --- 栏目设置
	 * updatePersonPortlet 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updatePersonPortlet")
	@ResponseBody
	public TeeJson updatePersonPortlet(HttpServletRequest request,TeePersonModel model) throws Exception {
	  	TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		try {
			int count = personService.updatePersonPortlet(model, person.getUuid());
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			json.setRtMsg(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}

	
	
	/**
	 * 获取在线人数信息
	 * @author syl
	 * @date 2013-12-2
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryOnlineUserCount")
	@ResponseBody
	public TeeJson queryOnlineUserCount(HttpServletRequest request,TeePersonModel model) throws Exception {
	  	TeeJson json = new TeeJson();
		try {
			long count = personService.queryOnlineCount();
			Map map = new HashMap();
			map.put("onlineUserCount" , count);
			json.setRtData(map);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			json.setRtMsg(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 更新人员桌面设置----第二套风格
	 * @author syl
	 * @date 2014-3-20
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updatePersonMenuParamSet")
	@ResponseBody
	public TeeJson updatePersonMenuParamSet(HttpServletRequest request,TeePersonModel model) throws Exception {
	  	TeeJson json = personService.updatePersonMenuParamSet(request);
		return json;
	}
	
	/**
	 * 查询 byId  --简单，不带辅助部门、角色等等
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPsersonInfoByUserId.action")
	@ResponseBody
	public TeeJson getPsersonInfoByUserId(HttpServletRequest request) throws Exception {
		String userId = request.getParameter("userId");
		String data = "";
		TeeJson json = new TeeJson();
		try {
			if (!TeeUtility.isNullorEmpty(userId)) {
				TeePerson person = personService.getPersonByUserName(userId);
				if(person != null ){
					TeePersonModel model = new TeePersonModel();
					model.setDeptIdName(person.getDept().getDeptName());
					model.setUserRoleStrName(person.getUserRole().getRoleName());
					BeanUtils.copyProperties(person, model);
//					if(person.getDynamicInfo()!=null){
//						model.setLastVisitTime(person.getDynamicInfo().getLastVisitTime());
//					}
//					model.setLastVisitTime(new Date());
					json.setRtData(model);
				}else{
					json.setRtState(true);
					json.setRtMsg("没有相关人员");
				}
			
			}
			json.setRtState(true);
			
		} catch (Exception ex) {
			json.setRtState(true);
			json.setRtMsg(ex.getMessage());
			System.out.println(ex.getMessage());
		}
		return json;
	}
	
	/**
	 * 获取已删除的人员  和 外部人员及 部门为空的人员
	 * @author syl
	 * @date 2014-6-1
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryDeletePerson.action")
	@ResponseBody
	public TeeEasyuiDataGridJson queryDeletePerson(HttpServletRequest request,TeeDataGridModel dm){
		return personService.queryDeletePerson(request,dm);
	}
	
	
	/**
	 *  物理删除人员信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delPerson")
	@ResponseBody
	public TeeJson delPerson(HttpServletRequest request) throws Exception {
		int uuid = TeeStringUtil.getInteger(request.getParameter("uuid"), 0);
		TeeJson json = new TeeJson();
		if(uuid>0){
			personService.delPerson(uuid);
			json.setRtState(true);
			json.setRtMsg("用户删除成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("用户删除失败！");
		}
		return json;
	}
	
	
	
	
	
	
	/**
	 * 根据部门id查询 人员
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPersonByDept")
	@ResponseBody
	public TeeJson getPersonByDept(HttpServletRequest request , TeePersonModel model) throws Exception {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		try {
			List<TeePerson> persons = personService.selectPersonByDeptId(model.getDeptId());
			List<Map> personModelList = new ArrayList();
			for(TeePerson p:persons){
				Map pModel = new HashMap();
				pModel.put("id", p.getUuid());
				pModel.put("name", p.getUserName());
				personModelList.add(pModel);
			}
			json.setRtData(personModelList);
			json.setRtState(true);
			json.setRtMsg("查询成功");
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			json.setRtMsg(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	
	
	
	
/**
 * 还原离职人员
 * @param request
 * @return
 * @throws Exception
 */
	@RequestMapping("/reductionPerson")
	@ResponseBody
	public TeeJson reductionPerson(HttpServletRequest request) throws Exception {
		int uuid = TeeStringUtil.getInteger(request.getParameter("uuid"), 0);
		TeeJson json = new TeeJson();
		if(uuid>0){
			personService.reductionPerson(uuid,request);
			json.setRtState(true);
			json.setRtMsg("用户还原成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("用户还原失败！");
		}
		return json;
	}
	
	
	/**
	 * 判读昂当前的Ukey是否被其他人占用
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkKeySNIsExist")
	@ResponseBody
	public TeeJson checkKeySNIsExist(HttpServletRequest request) throws Exception {
		return personService.checkKeySNIsExist(request);
	}
	
	
	
	/**
	 * 判读即将被绑定的人员是否已经与其他设备绑定了
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkUserIsBound")
	@ResponseBody
	public TeeJson checkUserIsBound(HttpServletRequest request) throws Exception {
		return personService.checkUserIsBound(request);
	}
	
	
	/**
	 * 进行UKey绑定
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/boundUkey")
	@ResponseBody
	public TeeJson boundUkey(HttpServletRequest request) throws Exception {
		return personService.boundUkey(request);
	}
	
	
	/**
	 * 判断用户和UKEY设备是否匹配
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkUserAndUkeyIsMatch")
	@ResponseBody
	public TeeJson checkUserAndUkeyIsMatch(HttpServletRequest request) throws Exception {
		return personService.checkUserAndUkeyIsMatch(request);
	}
	
	
	
	
	/**
	 * 解绑ukey
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/unBoundUkey")
	@ResponseBody
	public TeeJson unBoundUkey(HttpServletRequest request) throws Exception {
		return personService.unBoundUkey(request);
	}
	

	
	/**
	 * 根据用户主键   获取头像主键
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAvatarId")
	@ResponseBody
	public TeeJson getAvatarId(HttpServletRequest request) throws Exception {
		return personService.getAvatarId(request);
	}
	
	/**
	 * 获取辅助角色为领导活动的人员
	 * */
	@ResponseBody
	@RequestMapping("/getHongdongLd")
	public TeeJson getHongdongLd(){
		return personService.getHongdongLd();
	}
	
	
	/**
	 * 获取当前登陆人的快捷菜单ids
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getQuickMenuIds")
	public TeeJson getQuickMenuIds(HttpServletRequest request){
		return personService.getQuickMenuIds(request);
	}
	
	/**
	 * 判断当前的菜单是不是该用户的快捷菜单
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/isQuickMenu")
	public TeeJson isQuickMenu(HttpServletRequest request){
		return personService.isQuickMenu(request);
	}
	
	
	/**
	 * 添加快捷菜单
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addQuickMenu")
	public TeeJson addQuickMenu(HttpServletRequest request){
		return personService.addQuickMenu(request);
	}
	
	
	
	/**
	 * 移除快捷菜单ids
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/removeQuickMenu")
	public TeeJson removeQuickMenu(HttpServletRequest request){
		return personService.removeQuickMenu(request);
	}
	
	/**
	 * 获取当前登录人有权限的快捷菜单集合
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/getQuickMenus")
	public TeeJson getQuickMenus(HttpServletRequest request) throws Exception{
		return personService.getQuickMenus(request);
	}
	
	
	@RequestMapping("/getAddressListByDeptId.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getAddressListByDeptId(TeeDataGridModel dm, HttpServletRequest response) {
		return personService.getAddressListByDeptId(dm,response);
	}
	
	/**
	 * 获取当前登陆人的基本信息  用户名  部门名称  头像
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getLoginUserInfo")
	public TeeJson getLoginUserInfo(HttpServletRequest request) throws Exception{
		return personService.getLoginUserInfo(request);
	}
	
	
	/**
	 * 修改用户登录授权   （禁用  启用登录权限）
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updatePersonLoginPermission")
	public TeeJson updatePersonLoginPermission(HttpServletRequest request) throws Exception{
		return personService.updatePersonLoginPermission(request);
	}
	
	
	
	
	/**
	 * 根据用户姓名和部门名称模糊查询用户列表
	 * @param dm
	 * @param response
	 * @return
	 */
	@RequestMapping("/getUserListByUserNameAndDeptName.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getUserListByUserNameAndDeptName(TeeDataGridModel dm, HttpServletRequest request) {
		return personService.getUserListByUserNameAndDeptName(dm,request);
	}
	
	
	/**
	 * 渲染领导桌面   我的事项  待办工作  已办工作   消息提醒数量
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getLeaderDesktopNum")
	public TeeJson getLeaderDesktopNum(HttpServletRequest request) throws Exception{
		return personService.getLeaderDesktopNum(request);
	}
	
	
	public void setDeptService(TeeDeptService deptService) {
		this.deptService = deptService;
	}

	public void setOrgService(TeeOrgService orgService) {
		this.orgService = orgService;
	}

	public void setPersonService(TeePersonService personService) {
		this.personService = personService;
	}
	
}
