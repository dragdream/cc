package com.tianee.oa.core.base.address.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tianee.oa.core.base.address.bean.TeeAddressGroup;
import com.tianee.oa.core.base.address.service.TeeAddressGroupService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/teeAddressGroupController")
public class TeeAddressGroupController {

	@Autowired
	private TeeAddressGroupService addressGroupService;
	
	
	
	@RequestMapping("/addAddressGroup")
	@ResponseBody
	public TeeJson addAddressGroup(HttpServletRequest request){
		int orderNo =TeeStringUtil.getInteger(request.getParameter("orderNo"), 0);
		String groupName = TeeStringUtil.getString(request.getParameter("groupName"), "");
		
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		TeeAddressGroup group = new TeeAddressGroup();
		group.setGroupName(groupName);
		group.setOrderNo(orderNo);
		group.setUserId(loginPerson.getUuid());
		group.setUserId(loginPerson.getUuid());
		TeeJson json = new TeeJson();
		try {
			addressGroupService.addAddressGroupService( request, group);
			json.setRtState(true);
			json.setRtMsg("添加群组成功");
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("添加群组失败");
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 添加公共通讯簿组
	 * @author zhp
	 * @createTime 2014-1-4
	 * @editTime 上午08:18:29
	 * @desc
	 */
	@RequestMapping("/addPublicAddressGroup")
	@ResponseBody
	public TeeJson addAddPublicAddressGroup(HttpServletRequest request){
		int orderNo = TeeStringUtil.getInteger(request.getParameter("orderNo"), 0);
		String groupName = TeeStringUtil.getString(request.getParameter("groupName"), "");
		
		String toId = TeeStringUtil.getString(request.getParameter("toId"),null);
		String privId = TeeStringUtil.getString(request.getParameter("privId"),null);
		String userId = TeeStringUtil.getString(request.getParameter("userId"),null);//  
		String toName = TeeStringUtil.getString(request.getParameter("toName"),null);
		String privName = TeeStringUtil.getString(request.getParameter("privName"),null);
		String userName = TeeStringUtil.getString(request.getParameter("userName"),null);
		
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//这样做 方便 合理 用like 匹配   ",1, like  ,1,2,3,"  
		if(!TeeUtility.isNullorEmpty(toId)){
			toId = TeeUtility.decorationCommaUtil(toId);
		}
		if(!TeeUtility.isNullorEmpty(privId)){
			privId = TeeUtility.decorationCommaUtil(privId);
		}
		if(!TeeUtility.isNullorEmpty(userId)){
			userId = TeeUtility.decorationCommaUtil(userId);
		}
		TeeAddressGroup group = new TeeAddressGroup();
		group.setGroupName(groupName);
		group.setOrderNo(orderNo);
		group.setToDeptIds(toId);
		group.setToDeptNames(toName);
		group.setToUserIds(userId);
		group.setToUserNames(userName);
		group.setToRolesIds(privId);
		group.setToRolesNames(privName);
		
		TeeJson json = new TeeJson();
		try {
			addressGroupService.addPublicAddressGroupService( request, group);
			json.setRtState(true);
			json.setRtMsg("添加公共通讯簿群组成功");
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("添加公共通讯簿群组失败");
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 更新个人通讯簿组
	 * @author zhp
	 * @createTime 2014-1-4
	 * @editTime 上午08:19:18
	 * @desc
	 */
	@RequestMapping("/updateAddressGroup")
	@ResponseBody
	public TeeJson updateAddressGroup(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int aid =TeeStringUtil.getInteger(request.getParameter("id"), 0);
		int orderNo =TeeStringUtil.getInteger(request.getParameter("orderNo"), 0);
		String groupName = TeeStringUtil.getString(request.getParameter("groupName"), "");
		
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		TeeAddressGroup group = null;
		group = addressGroupService.getAddressGroupById(aid);
		if(group != null){
			group.setGroupName(groupName);
			group.setOrderNo(orderNo);
			group.setUserId(loginPerson.getUuid());
		}else{
			json.setRtState(false);
			json.setRtMsg("更新群组失败");
		}
		try {
			addressGroupService.updateAddressGroup(group);
			json.setRtState(true);
			json.setRtMsg("更新群组成功");
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("添加群组失败");
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 更新公共通讯簿组
	 * @author zhp
	 * @createTime 2014-1-4
	 * @editTime 上午08:19:41
	 * @desc
	 */
	@RequestMapping("/updatePublicAddressGroup")
	@ResponseBody
	public TeeJson updatePublicAddressGroup(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int aid =TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		int orderNo =TeeStringUtil.getInteger(request.getParameter("orderNo"), 0);
		String groupName = TeeStringUtil.getString(request.getParameter("groupName"), "");

		String toId = TeeStringUtil.getString(request.getParameter("toId"),null);
		String privId = TeeStringUtil.getString(request.getParameter("privId"),null);
		String userId = TeeStringUtil.getString(request.getParameter("userId"),null);//  
		String toName = TeeStringUtil.getString(request.getParameter("toName"),null);
		String privName = TeeStringUtil.getString(request.getParameter("privName"),null);
		String userName = TeeStringUtil.getString(request.getParameter("userName"),null);
		
		//这样做 方便 合理 用like 匹配   ",1, like  ,1,2,3,"  
		if(!TeeUtility.isNullorEmpty(toId)){
			toId = TeeUtility.decorationCommaUtil(toId);
		}
		if(!TeeUtility.isNullorEmpty(privId)){
			privId = TeeUtility.decorationCommaUtil(privId);
		}
		if(!TeeUtility.isNullorEmpty(userId)){
			userId = TeeUtility.decorationCommaUtil(userId);
		}
		
		TeeAddressGroup group = null;
		group = addressGroupService.getAddressGroupById(aid);
		try {
			if(group != null){
				group.setGroupName(groupName);
				group.setOrderNo(orderNo);
				group.setToDeptIds(toId);
				group.setToDeptNames(toName);
				group.setToUserIds(userId);
				group.setToUserNames(userName);
				group.setToRolesIds(privId);
				group.setToRolesNames(privName);
				addressGroupService.updatePublicAddressGroupService(request , group);
				json.setRtState(true);
				json.setRtMsg("更新群组成功");
			}else{
				json.setRtState(false);
				json.setRtMsg("更新群组失败");
			}
		
			
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("添加群组失败");
			e.printStackTrace();
		}
		return json;
	}

	
	/**
	 * 获取个人通讯簿组  以及个人有权限的 公共通讯薄组
	 * @author zhp
	 * @createTime 2014-1-4
	 * @editTime 上午07:52:38
	 * @desc
	 */
	@RequestMapping("/getAddressGroups")
	@ResponseBody
	public TeeJson getAddressGroups(HttpServletRequest request){
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		List list = new ArrayList();
		TeeAddressGroup g = new TeeAddressGroup();
		g.setGroupName("默认");
		g.setSeqId(0);
		g.setOrderNo(0);
		list.add(g);
		List aList = addressGroupService.getAddressGroups(loginPerson);
		list.addAll(aList);
		json.setRtState(true);
		json.setRtMsg("获取组列表成功");
		json.setRtData(list);
		return json;
	}
	
	
	
	/**
	 * 获取个人有权限的 公共通讯薄组
	 * @author zhp
	 * @createTime 2014-1-4
	 * @editTime 上午07:52:38
	 * @desc
	 */
	@RequestMapping("/getPubAddressGroups")
	@ResponseBody
	public TeeJson getPubAddressGroups(HttpServletRequest request){
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		List list = new ArrayList();
		TeeAddressGroup g = new TeeAddressGroup();
		g.setGroupName("默认");
		g.setSeqId(0);
		g.setOrderNo(0);
		list.add(g);
		List aList = addressGroupService.getPubAddressGroups(loginPerson);
		list.addAll(aList);
		json.setRtState(true);
		json.setRtMsg("获取组列表成功");
		json.setRtData(list);
		return json;
	}
	/**
	 * 获取公共通讯簿组 （全部）
	 * @author zhp
	 * @createTime 2014-1-4
	 * @editTime 上午07:52:20
	 * @desc
	 */
	@RequestMapping("/getPublicAddressGroups")
	@ResponseBody
	public TeeJson getPublicAddressGroups(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List list = new ArrayList();
		TeeAddressGroup g = new TeeAddressGroup();
		g.setGroupName("默认");
		g.setSeqId(0);
		g.setOrderNo(0);
		g.setToDeptNames("全体部门");
		g.setToUserNames("全体人员");
		g.setToRolesNames("全体角色");
		list.add(g);
		List aList = addressGroupService.getPublicAddressGroups();
		list.addAll(aList);
		json.setRtState(true);
		json.setRtMsg("获取组列表成功");
		json.setRtData(list);
		return json;
	}
	
	/**
	 * 获取公共通讯簿组 to 对于有权限的人（仅仅是当前登录人 有权限的 公共通讯簿组）
	 * @author zhp
	 * @createTime 2014-1-4
	 * @editTime 上午07:52:20
	 * @desc
	 */
	@RequestMapping("/getPublicAddressGroups2Priv")
	@ResponseBody
	public TeeJson getPublicAddressGroups2Priv(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List list = new ArrayList();
		TeeAddressGroup g = new TeeAddressGroup();
		g.setGroupName("默认");
		g.setSeqId(0);
		g.setOrderNo(0);
		g.setToDeptNames("全体部门");
		g.setToUserNames("全体人员");
		g.setToRolesNames("全体角色");
		list.add(g);
		List aList = addressGroupService.getPublicAddressGroups2Priv(loginPerson);
		list.addAll(aList);
		json.setRtState(true);
		json.setRtMsg("获取组列表成功");
		json.setRtData(list);
		return json;
	}
	/**
	 * 清空 通讯薄 组 
	 * @author zhp
	 * @createTime 2014-1-4
	 * @editTime 上午08:20:33
	 * @desc
	 */
	@RequestMapping("/emptyAddressGroups")
	@ResponseBody
	public TeeJson emptyAddressGroups(HttpServletRequest request){
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		int groupId =TeeStringUtil.getInteger(request.getParameter("groupId"), -1);
		String isPub = TeeStringUtil.getString(request.getParameter("isPub"),"0");//0 不是公共通讯薄 1 是公共通讯薄
		boolean pub = false;
		try {
			if("1".equals(isPub)){//公共分组清空
				pub = true;
				addressGroupService.emptyPupublicAddressGroupsService(pub, groupId);
			}else{//个人分组清空
				addressGroupService.emptyAddressGroups(pub,groupId,loginUser);
			}
			json.setRtState(true);
			json.setRtMsg("清空列表成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.setRtState(false);
			json.setRtMsg("清空列表失败");
		}
		return json;
	}
	
	

	/**
	 * 获取个人 以及 公共通讯簿组 
	 * @author zhp
	 * @createTime 2014-1-4
	 * @editTime 上午08:20:52
	 * @desc
	 */
	@RequestMapping("/getAddressGroupById")
	@ResponseBody
	public TeeJson getAddressGroupById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int aid =TeeStringUtil.getInteger(request.getParameter("id"), 0);
		TeeAddressGroup group = null;
		
		group = addressGroupService.getAddressGroupById(aid);
		String userIds = TeeStringUtil.getString(group.getToUserIds());//人员
		if(userIds.endsWith(",")){
			userIds = userIds.substring(0, userIds.length() - 1);
		}
		if(userIds.startsWith(",")){
			userIds = userIds.substring(1, userIds.length() );
		}
		
		String deptIds = TeeStringUtil.getString(group.getToDeptIds());//部门
		if(deptIds.endsWith(",")){
			deptIds = deptIds.substring(0, deptIds.length() - 1);
		}
		if(deptIds.startsWith(",")){
			deptIds = deptIds.substring(1, deptIds.length() );
		}
		
		String roleIds = TeeStringUtil.getString(group.getToRolesIds());//角色
		if(roleIds.endsWith(",")){
			roleIds = roleIds.substring(0, roleIds.length() - 1);
		}
		if(roleIds.startsWith(",")){
			roleIds = roleIds.substring(1, roleIds.length() );
		}
		
		group.setToUserIds(userIds);
		group.setToRolesIds(roleIds);
		group.setToDeptIds(deptIds);
		json.setRtState(true);
		json.setRtMsg("获取组列表成功");
		json.setRtData(group);
		return json;
	}
	
	/**
	 * 删除分组
	 * @author syl
	 * @date 2014-4-26
	 * @param request
	 * @return
	 */
	@RequestMapping("/delAddressGroups")
	@ResponseBody
	public TeeJson delAddressGroups(HttpServletRequest request){
		int sid =TeeStringUtil.getInteger(request.getParameter("groupId"), 0);
		String isPub = TeeStringUtil.getString(request.getParameter("isPub") , "0");//0-个人  1- 公共
		TeeJson json = new TeeJson();
		try {
//			if(isPub.equals("1")){
//				addressGroupService.delAddressGroupService(sid);
//			}else{
//				addressGroupService.delAddressByGroupId(sid);
//			}
			addressGroupService.delAddressGroupService(sid);
			
			json.setRtState(true);
			json.setRtMsg("删除成功");
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("删除失败");
			e.printStackTrace();
		}
		return json;
	}
	
	public TeeAddressGroupService getAddressGroupService() {
		return addressGroupService;
	}

	public void setAddressGroupService(TeeAddressGroupService addressGroupService) {
		this.addressGroupService = addressGroupService;
	}
	
}
