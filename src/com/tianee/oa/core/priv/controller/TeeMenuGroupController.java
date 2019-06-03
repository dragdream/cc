package com.tianee.oa.core.priv.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.applicationSystem.bean.TeeApplicationSystemMaintain;
import com.tianee.oa.core.base.applicationSystem.service.TeeApplicationSystemMaintainService;
import com.tianee.oa.core.general.service.TeeSysParaService;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.priv.bean.TeeMenuButton;
import com.tianee.oa.core.priv.bean.TeeMenuGroup;
import com.tianee.oa.core.priv.bean.TeeSysMenu;
import com.tianee.oa.core.priv.model.TeeMenuGroupModul;
import com.tianee.oa.core.priv.model.TeeSysMenuModel;
import com.tianee.oa.core.priv.service.TeeMenuButtonService;
import com.tianee.oa.core.priv.service.TeeMenuGroupService;
import com.tianee.oa.core.priv.service.TeeSysMenuService;
import com.tianee.oa.oaconst.TeeActionKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.dynamic.TeeClassRunner;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/teeMenuGroup")
public class TeeMenuGroupController {
	@Autowired
	TeeMenuGroupService  menuGroupServ;
	@Autowired
	TeeSysMenuService  sysMenuServ;
	
	@Autowired
	TeeSysParaService sysParaServ;

	@Autowired
	TeePersonService  personServ;

	@Autowired
	TeeDeptService  deptServ;

	@Autowired
	TeeMenuButtonService  menuButtonServ;
		
	@Autowired
	TeeApplicationSystemMaintainService appSystemServ;
	
	@RequestMapping("/getMenuGroupList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getMenuGroupList(TeeDataGridModel dm, HttpServletRequest request) {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);

		return menuGroupServ.datagrid(dm,person);
	}

	
	/**
	 * 获取所有的权限组   不分页
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllMenuGroupList.action")
	@ResponseBody
	public TeeJson getAllMenuGroupList(HttpServletResponse response,HttpServletRequest request) {
		return menuGroupServ.getAllMenuGroupList(request);
	}
	
	/**
	 * 编辑和新增菜单组
	 * @author syl
	 * @date 2014-4-23
	 * @param menugroup
	 * @param response
	 * @return
	 */
	@RequestMapping("/addOrUpdateMenuGroup.action")
	@ResponseBody
	public TeeJson addOrUpdateMenuGroup(TeeMenuGroup menugroup,HttpServletRequest request) {
		
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeDepartment dept = person.getDept();
		menugroup.setDept(dept);
				
		TeeJson json = new TeeJson();
		if(menugroup.getUuid() <=0 ){//如果UUId为空则新增
			menuGroupServ.addService(menugroup);
			json.setRtMsg("ok");

		}else{
			TeeMenuGroup menugroupNew = menuGroupServ.selectByUuid(menugroup.getUuid());
			menugroupNew.setMenuGroupName(menugroup.getMenuGroupName());
			menugroupNew.setMenuGroupNo(menugroup.getMenuGroupNo());
			menugroupNew.setMenuGroupType(menugroup.getMenuGroupType());
			menuGroupServ.updateMenuService(menugroupNew);
			json.setRtMsg("编辑菜单权限组成功!");
		}
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/getMenuGroup.action")
	@ResponseBody
	public TeeJson getMenuGroup(HttpServletRequest request) {
		String uuid  = request.getParameter("uuid") == null ? "" :  request.getParameter("uuid") ;
		TeeJson json = new TeeJson();
		if(!TeeUtility.isNullorEmpty(uuid)){
			TeeMenuGroupModul modul = new TeeMenuGroupModul();
			TeeMenuGroup group = menuGroupServ.selectByUuid(Integer.parseInt(uuid));
			BeanUtils.copyProperties(group, modul);
			json.setRtData(modul);
		}else{
			json.setRtState(false);
		}
		json.setRtState(true);
		return json;
	}
	@RequestMapping("/delMenuGroup.action")
	@ResponseBody
	public TeeJson delMenuGroup(HttpServletRequest request) {
		
		TeeJson json = new TeeJson();
		String uuids = request.getParameter("uuids");
		if(!TeeUtility.isNullorEmpty(uuids)){//如果UUId为空则新增
			//先清空菜单组的菜单项和人员关系
			menuGroupServ.clearMenuAndPreson(uuids);
		
			//删除菜单
			int count =  menuGroupServ.delSysMenuGroupByUUids(uuids);
	
			json.setRtState(true);
			json.setRtMsg("删除成功!");
		}else{
			json.setRtState(false);
			json.setRtMsg("删除失败!");
		}
		return json;
	}
	
	/**
	 * 获取菜单组树
	 * 一次加载
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getMenuGroupPriv.action")
	public String getMenuGroupPriv(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
		String uuid = request.getParameter("uuid");//菜单组
		String data  = "[";
		try {

				Object[] values = {};//'%'||:name||'%' /% escape '/' 
				String hql = " from TeeSysMenu t    order by t.menuId";
				TeeMenuGroup group = menuGroupServ.selectByUuid(Integer.parseInt(uuid));
				
				if(group != null ){
					List<TeeSysMenu> list = sysMenuServ.selectMenu(hql,values);
					List<TeeSysMenu> list2 =  group.getSysMenus();
					boolean hasPriv = false;
					for (int i = 0; i < list.size(); i++) {
						TeeSysMenu menu = list.get(i);
						
                        boolean checked  = false;
						if(list2.contains(menu)){
							checked = true;
						}
						//判断是否有权限,如果没有权限，则不显示对应菜单
						hasPriv = (Boolean) TeeClassRunner.exec("com.tianee.webframe.util.auth.TeeFunctionControl", "hasPriv", new Object[]{menu.getMenuCode()});
						if(!hasPriv){
							continue;
						}
						if(menu.getMenuId().length() == 3){//父级及诶单
							data = data + "{ id:'" +menu.getMenuId()+ "', pId:0, name:'" + menu.getMenuName()+ "',checked: " + checked+",title:'"+menu.getMenuName()+"'},";
						}else if(menu.getMenuId().length() == 6){//二级父级及诶单
							data = data + "{ id:'" +menu.getMenuId()+ "', pId:\"" +menu.getMenuId().substring(0,menu.getMenuId().length()-3)+ "\", name:'" + menu.getMenuName()+ "',checked: " + checked+",title:'"+menu.getMenuName()+"'},";
						}else{
							data = data + "{ id:'" +menu.getMenuId()+ "', pId:\"" +menu.getMenuId().substring(0,menu.getMenuId().length()-3)+ "\", name:'" + menu.getMenuName()+ "', iconSkin:'icon03',checked: " + checked+",title:'"+menu.getMenuName()+"'},";

						}
					}
					if(list.size() > 0){
						data = data.substring(0, data.length() - 1);
					}
				}
				
	
			data = data +  "]";
			request.setAttribute(TeeActionKeys.RET_STATE, TeeConst.RETURN_OK);
			request.setAttribute(TeeActionKeys.RET_MSRG, "成功返回结果！");
			request.setAttribute(TeeActionKeys.RET_DATA, data);
		} catch (Exception ex) {
			request.setAttribute(TeeActionKeys.RET_STATE, TeeConst.RETURN_ERROR);
			request.setAttribute(TeeActionKeys.RET_MSRG, ex.getMessage());
			throw ex;
		}
		return "/inc/rtjson.jsp";
	}
	
	
	/**
	 * 获取所有菜单组树
	 * 获取所有菜单组
	 * 一次加载
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getMenuGroupAllPriv.action")
	public String getMenuGroupAllPriv(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
		String data  = "";
		try {

			Object[] values = {};//'%'||:name||'%' /% escape '/' 
			String hql = " from TeeSysMenu t    order by t.menuId";
		   	//所有菜单
			List<TeeSysMenu> list = sysMenuServ.selectMenu(hql, values);
			/* List<TeeSysMenu> list2 = group.getSysMenus(); */
			String SysMenuData = "[";

			for (int i = 0; i < list.size(); i++) {
				TeeSysMenu menu = list.get(i);
				if (menu.getMenuId().length() == 3) {// 父级及诶单
					SysMenuData = SysMenuData + "{ id:'" + menu.getMenuId()
							+ "', pId:0, name:'" + menu.getMenuName() + "'},";
				} else {
					SysMenuData = SysMenuData
							+ "{ id:'"
							+ menu.getMenuId()
							+ "', pId:\""
							+ menu.getMenuId().substring(0,
									menu.getMenuId().length() - 3)
							+ "\", name:'" + TeeUtility.encodeSpecial(menu.getMenuName())
							+ "', iconSkin:'icon03'},";
				}
			}
			if (list.size() > 0) {
				SysMenuData = SysMenuData.substring(0, SysMenuData.length() - 1);
			}
		
	
			SysMenuData = SysMenuData +  "]";
			
			//data = "{menuGroupData:" + menuGroupData + ",SysMenuData:" + SysMenuData + "}";
			data = SysMenuData ;
			request.setAttribute(TeeActionKeys.RET_STATE, TeeConst.RETURN_OK);
			request.setAttribute(TeeActionKeys.RET_MSRG, "成功返回结果！");
			request.setAttribute(TeeActionKeys.RET_DATA, data);
		} catch (Exception ex) {
			request.setAttribute(TeeActionKeys.RET_STATE, TeeConst.RETURN_ERROR);
			request.setAttribute(TeeActionKeys.RET_MSRG, ex.getMessage());
			throw ex;
		}
		return "/inc/rtjson.jsp";
	}
	
	
	
	
	
	@RequestMapping("/getMenuGroupAll.action")
	@ResponseBody
	public TeeJson getMenuGroupAll(TeeMenuGroup menugroup,HttpServletResponse response) {
		
		TeeJson json = new TeeJson();
		String hqlGroup =  " from TeeMenuGroup   order by MENU_GROUP_NO";
		//所有菜单组
		String menuGroupData = "[";
        List<TeeMenuGroup> groupList = menuGroupServ.selectGroupMenu(hqlGroup, null);
    	for (int i = 0; i < groupList.size(); i++) {
			TeeMenuGroupModul um = new TeeMenuGroupModul();
			BeanUtils.copyProperties(groupList.get(i), um);
			TeeJsonUtil d = new TeeJsonUtil();
			menuGroupData = menuGroupData + d.toJson(um) + ",";
		}
		if (groupList.size() > 0) {
			menuGroupData = menuGroupData.substring(0, menuGroupData.length() - 1);
		}
		menuGroupData = menuGroupData + "]";
		json.setRtState(true);
		json.setRtMsg("查询功能");
		json.setRtData(menuGroupData);
		return json;
	}
	
	
	/**
	 * 获取菜单组树
	 * 一次加载
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveMenuGroupPriv.action")
	public String saveMenuGroupPriv(HttpServletRequest request) throws Exception {
		
		String uuid = request.getParameter("uuid");//菜单组UUID
		String menuIds = request.getParameter("menuIds");//菜单IDs
		String data  = "[";
		try {

			if(!TeeUtility.isNullorEmpty(uuid)){
				TeeMenuGroup group = menuGroupServ.selectByUuid(Integer.parseInt(uuid));
				String[] menuIdArray = menuIds.split(",");
				List list  = new ArrayList();
				if(!TeeUtility.isNullorEmpty(menuIds)){
					String hql = " from TeeSysMenu where menuId in (" + TeeStringUtil.getSqlStringParse(menuIds) + ")";
					 list = sysMenuServ.selectMenu(hql, null);
				}
				
				group.setSysMenus(list);
				menuGroupServ.updateMenuService(group);
			}
			request.setAttribute(TeeActionKeys.RET_STATE, TeeConst.RETURN_OK);
			request.setAttribute(TeeActionKeys.RET_MSRG, "成功返回结果！");
			request.setAttribute(TeeActionKeys.RET_DATA, "{}");
		} catch (Exception ex) {
			request.setAttribute(TeeActionKeys.RET_STATE, TeeConst.RETURN_ERROR);
			request.setAttribute(TeeActionKeys.RET_MSRG, ex.getMessage());
			throw ex;
		}
		return "/inc/rtjson.jsp";
	}
	
	
	
	/**
	 * 批量设置菜单组
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/setMuiltMenuGroupPriv")
	public String setMuiltMenuGroupPriv(HttpServletRequest request) throws Exception {
		
		String uuids = request.getParameter("uuids");//菜单组UUID字符串已逗号分隔
		String menuIds = request.getParameter("menuIds");//菜单IDs
		String menuGroupOptType = request.getParameter("menuGroupOptType"); //操作类型
		String opt = request.getParameter("opt") == null ? "0" : request.getParameter("opt");
		String data  = "[";
		try {

			if(!TeeUtility.isNullorEmpty(uuids)){
				if(uuids.endsWith(",")){
					uuids = uuids.substring(0, uuids.length() -1);
				}
			
				String grouphql = " from TeeMenuGroup where uuid in ("+ uuids + ")";
				List<TeeMenuGroup> groupList = menuGroupServ.selectGroupMenu(grouphql,null);
				String sysMenuhql = " from TeeSysMenu where menuId in ("+TeeStringUtil.getSqlStringParse(menuIds)+ ")";
				List<TeeSysMenu> menuList = sysMenuServ.selectMenu(sysMenuhql, null);
				
				for (int i = 0; i < groupList.size(); i++) {
					TeeMenuGroup group = groupList.get(i);
					List<TeeSysMenu> list = group.getSysMenus();
					list = menuGroupServ.reGetMenuList(list, opt, menuList);
					group.setSysMenus(list);
					menuGroupServ.updateMenuService(group);
				}
				
			}
			request.setAttribute(TeeActionKeys.RET_STATE, TeeConst.RETURN_OK);
			request.setAttribute(TeeActionKeys.RET_MSRG, "成功返回结果！");
			request.setAttribute(TeeActionKeys.RET_DATA, "{}");
		} catch (Exception ex) {
			request.setAttribute(TeeActionKeys.RET_STATE, TeeConst.RETURN_ERROR);
			request.setAttribute(TeeActionKeys.RET_MSRG, ex.getMessage());
			throw ex;
		}
		return "/inc/rtjson.jsp";
	}
	
	
	
	/**
	 * 获取带权限的菜单组
	 * 
	 * @param request
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPrivSysMenu")
	@ResponseBody
	public TeeJson getPrivSysMenu(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		try {
			 List<TeeSysMenuModel> list = menuGroupServ.getPrivSysMenu(person);
			 String MENU_EXPAND_SINGLE =  sysParaServ.getSysParaValue("MENU_EXPAND_SINGLE");
			 if(TeeUtility.isNullorEmpty(MENU_EXPAND_SINGLE)){
				 MENU_EXPAND_SINGLE = "0";//同时可以打开多个菜单
			 }
			 
			 Iterator<TeeSysMenuModel> it = list.iterator();
			 TeeSysMenuModel m = null;
			 boolean hasPriv = false;
			 while(it.hasNext()){
				 m = it.next();
				 hasPriv = (Boolean) TeeClassRunner.exec("com.tianee.webframe.util.auth.TeeFunctionControl", "hasPriv", new Object[]{m.getMenuCode()});
				 if(!hasPriv){
					 it.remove();
				 }
			 }
			 json.setRtMsg(MENU_EXPAND_SINGLE);
			 json.setRtData(list);
			 json.setRtState(true);
		} catch (Exception ex) {
			json.setRtMsg(ex.getMessage());
			json.setRtState(false);
		}
		return json;
	}
	
	/**
	 * 获取带权限的菜单组
	 * 
	 * @param request
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPrivSysMenu2")
	@ResponseBody
	public TeeJson getPrivSysMenu2(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取当前的应用系统的id
		int sysId=TeeStringUtil.getInteger(request.getParameter("sysId"),0);
		try {
			 List<TeeSysMenuModel> list = menuGroupServ.getPrivSysMenu2(person,sysId);
			 String MENU_EXPAND_SINGLE =  sysParaServ.getSysParaValue("MENU_EXPAND_SINGLE");
			 if(TeeUtility.isNullorEmpty(MENU_EXPAND_SINGLE)){
				 MENU_EXPAND_SINGLE = "0";//同时可以打开多个菜单
			 }
			 
			 Iterator<TeeSysMenuModel> it = list.iterator();
			 TeeSysMenuModel m = null;
			 boolean hasPriv = false;
			 while(it.hasNext()){
				 m = it.next();
//				 hasPriv = (Boolean) TeeClassRunner.exec("com.tianee.webframe.util.auth.TeeFunctionControl", "hasPriv", new Object[]{m.getMenuCode()});
//				 if(!hasPriv){
//					 it.remove();
//				 }
			 }
			 Map<String,List<TeeSysMenuModel>> map=new HashMap<String,List<TeeSysMenuModel>>();
			 List<TeeSysMenuModel> list1=new ArrayList<TeeSysMenuModel>();
			 List<TeeSysMenuModel> list2=new ArrayList<TeeSysMenuModel>();
			 List<TeeSysMenuModel> list3=new ArrayList<TeeSysMenuModel>();
			 for(TeeSysMenuModel model:list){
				 String menuId = model.getMenuId();
				 if(menuId.length()==9){
					 list3.add(model);
				 }
			  }
		  for(TeeSysMenuModel model:list){
				 String menuId = model.getMenuId();
				 if(menuId.length()==6){
					 two:
					 for(TeeSysMenuModel m3:list3){
						 String menuIdParent=m3.getMenuId().substring(0, 6);
						 if(menuId.equals(menuIdParent)){
							 model.setCaiDanFalg(true);
							 break two;
						 }
					 }
					 list2.add(model);
				 }
			  }
		  
		  for(TeeSysMenuModel model:list){
			  String menuId = model.getMenuId();
			  if(menuId.length()==3){
				  one:
				  for(TeeSysMenuModel m2:list2){
					  String menuIdParent = m2.getMenuId().substring(0, 3);
					  if(menuId.equals(menuIdParent)){
						  model.setCaiDanFalg(true);
						  break one; 
					  }
				  }
				  list1.add(model);
			  }
		  }
			 map.put("one", list1);
			 map.put("two", list2);
			 map.put("three", list3);
			 json.setRtMsg(MENU_EXPAND_SINGLE);
			 json.setRtData(map);
			 json.setRtState(true);
		} catch (Exception ex) {
			json.setRtMsg(ex.getMessage());
			json.setRtState(false);
		}
		return json;
	}
	
	
	/**
	 * 获取登录人有权限的第一级菜单
	 * @param request
	 * @return
	 */
	@RequestMapping("/getSysMenuByLoginPerson.action")
	@ResponseBody
	public TeeJson getSysMenuByLoginPerson(HttpServletRequest request) {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		List<TeeSysMenuModel> sysMenuList = new ArrayList<TeeSysMenuModel>();
		try {
			sysMenuList = menuGroupServ.getPrivFirstSysMenu(person);
			 json.setRtMsg("");
			 json.setRtData(sysMenuList);
			 json.setRtState(true);
		} catch (Exception ex) {
			json.setRtMsg(ex.getMessage());
			json.setRtState(false);
		}
		json.setRtState(true);
		return json;
	}
	
	
	
	
	
	/**
	 * 批量设置人员菜单组 ----
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/setMuiltPersonMenuGroupPriv")
	@ResponseBody
	public TeeJson setMuiltPersonMenuGroupPriv(HttpServletRequest request) throws Exception {
		TeeJson json = menuGroupServ.setMuiltPersonMenuGroupPriv(request);
		return json;
	}
	
	
	/**
	 * 访问带url的菜单  需要写入实时访问日志
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/visitPirv")
	@ResponseBody
	public TeeJson visitPirv(HttpServletRequest request,HttpServletResponse response) throws Exception {
		TeeJson json = menuGroupServ.visitPirv(request,response);
		return json;
	}
	
	
	
	/**
	 * 拥有权限组用户数
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getStatisticsDetail")
	@ResponseBody
	public TeeJson getStatisticsDetail(HttpServletRequest request,HttpServletResponse response) throws Exception {
		TeeJson json = menuGroupServ.getStatisticsDetail(request,response);
		return json;
	}
	
	public TeeMenuGroupService getMenuGroupServ() {
		return menuGroupServ;
	}

	public void setMenuGroupServ(TeeMenuGroupService menuGroupServ) {
		this.menuGroupServ = menuGroupServ;
	}

	public void setSysMenuServ(TeeSysMenuService sysMenuServ) {
		this.sysMenuServ = sysMenuServ;
	}

	public void setSysParaServ(TeeSysParaService sysParaServ) {
		this.sysParaServ = sysParaServ;
	}
	
	@RequestMapping("/getOrgTreeByLoginPerson.action")
	@ResponseBody
	public TeeJson getOrgTreeByLoginPerson(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		try {
			//获取group对应机构
			String groupId = request.getParameter("groupId");//groupId
			TeeMenuGroup group = this.menuGroupServ.selectByUuid(Integer.valueOf(groupId));
			List<TeeDepartment> checkedDeptList = group.getDepts();
			Map<Integer,TeeDepartment> checkedDeptMap = new HashMap<Integer,TeeDepartment>();
			if(checkedDeptList != null) {
				for(TeeDepartment dept :checkedDeptList) {
					checkedDeptMap.put(dept.getUuid(), dept);
				}
			}
			
			//获取机构范围
			TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			
			TeePerson person = this.personServ.selectByUuid(loginPerson.getUuid());
			TeeDepartment personDept = person.getDept();
			String deptFullId = personDept.getDeptFullId();
			
			String hql = " from TeeDepartment t where t.deptFullId like ? order by t.deptFullId ";
			Object[] para = {deptFullId + "%"};
			
			List<TeeDepartment> deptList = deptServ.selectDept(hql, para);
			List<TeeZTreeModel> nodeList = new ArrayList<TeeZTreeModel>();
				
			for(TeeDepartment dept:deptList) {
				
				String pid = null;
				if (dept.getDeptParent() != null) {
					pid = String.valueOf(dept.getDeptParent().getUuid());
				}
				
				boolean isChecked = false;
				if(checkedDeptMap.get(dept.getUuid())!=null) {
					isChecked = true;
				}

				String iconSkin = "dept";
				if(dept.getDeptType() == 2) {
					iconSkin = "unit";
				}
				
				TeeZTreeModel node = new TeeZTreeModel();
				node.setId(String.valueOf(dept.getUuid()));
				node.setpId(pid);
				node.setName(dept.getDeptName());
				node.setIconSkin(iconSkin);
				node.setNocheck(false);
				node.setChecked(isChecked);
				node.setOpen(true);
				
				nodeList.add(node);
			}
			json.setRtData(nodeList);
			json.setRtState(true);
		} catch (Exception ex) {
			json.setRtMsg(ex.getMessage());
			json.setRtState(false);
		}
		return json;
	}	
	
	@RequestMapping("/setDeptPriv.action")
	@ResponseBody
	public TeeJson setDeptPriv(HttpServletRequest request) throws Exception {
		
		String groupId = request.getParameter("groupId");//菜单组UUID
		String deptsIdStr = request.getParameter("deptsIdStr");//部门 IDs

		TeeJson json = new TeeJson();
		try {			
			if(!TeeUtility.isNullorEmpty(groupId)){
				List<TeeDepartment> deptList = this.deptServ.getDeptByUuids(deptsIdStr) ;
				TeeMenuGroup group = menuGroupServ.selectByUuid(Integer.parseInt(groupId));
				group.setDepts(deptList);
				menuGroupServ.updateMenuService(group);
				json.setRtState(true);
			}
		} catch (Exception ex) {
			json.setRtState(false);
			json.setRtMsg(ex.getMessage());
			throw ex;
		}
		return json;
	}

	
	//根据本人所属的机构
	@RequestMapping("/getSysMenuBtnPriv.action")
	@ResponseBody
	public TeeJson getSysMenuBtnPriv(int groupId,HttpServletRequest request) {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);

		//可选资源范围
		List<TeeApplicationSystemMaintain> systemList = null;
		List<TeeSysMenu> sysMenuList = null;
		List<TeeMenuButton> buttonList = null;
		
		if(TeePersonService.checkIsAdminPriv(person)) {
			systemList = this.appSystemServ.selectAppSystemList();
			
			String Hql = "from TeeSysMenu t order by t.menuId";
			sysMenuList = this.sysMenuServ.selectMenu(Hql,new Object[] {});
			
			buttonList = this.menuButtonServ.getAll();
		}else {
			TeeDepartment dept = deptServ.get(person.getDept().getUuid());
			List<TeeMenuGroup> groupList = this.menuGroupServ.getGroupListByDept(dept);
			
			Map<Integer,TeeApplicationSystemMaintain> systemMap = new HashMap<Integer,TeeApplicationSystemMaintain>();
			Map<String,TeeSysMenu> menuMap = new TreeMap<String,TeeSysMenu>();
			Map<Integer,TeeMenuButton> buttonMap = new HashMap<Integer,TeeMenuButton>();
			if (groupList!=null) {
				for(TeeMenuGroup group : groupList) {
					List<TeeApplicationSystemMaintain> sysems = group.getAppSystems();
					if(sysems!=null) {
						for(TeeApplicationSystemMaintain sys: sysems) {
							systemMap.put(sys.getSid(), sys);
						}
					}
					//
					List<TeeSysMenu> menus = group.getSysMenus();
					if(menus!=null) {
						for(TeeSysMenu menu: menus) {
							menuMap.put(menu.getMenuId(), menu);
						}
					}
					//
					List<TeeMenuButton> btns = group.getMenuButtons();
					if(menus!=null) {
						for(TeeMenuButton btn: btns) {
							buttonMap.put(btn.getId(), btn);
						}
					}
				}				
			}

			systemList = new ArrayList<TeeApplicationSystemMaintain>();
			sysMenuList = new ArrayList<TeeSysMenu> ();
			buttonList = new ArrayList<TeeMenuButton>();
			
			for (Map.Entry<Integer, TeeApplicationSystemMaintain> entry : systemMap.entrySet()) { 
				systemList.add(entry.getValue());
			}

			for (Map.Entry<String, TeeSysMenu> entry : menuMap.entrySet()) { 
				sysMenuList.add(entry.getValue());
			}

			for (Map.Entry<Integer, TeeMenuButton> entry : buttonMap.entrySet()) { 
				buttonList.add(entry.getValue());
			}

		}

		//当前用户所有权限范围
		TeeMenuGroup menuGroup = menuGroupServ.selectByUuid(groupId);
		List<TeeApplicationSystemMaintain> groupSystemList = menuGroup.getAppSystems();
		Map<Integer,TeeApplicationSystemMaintain> groupSystemMap = new HashMap<Integer,TeeApplicationSystemMaintain>();
		for(TeeApplicationSystemMaintain system :groupSystemList) {
			groupSystemMap.put(system.getSid(), system);
		}
		
		List<TeeSysMenu> groupMenuList = menuGroup.getSysMenus();
		Map<Integer,TeeSysMenu> groupMenuMap = new HashMap<Integer,TeeSysMenu>();
		for(TeeSysMenu menu :groupMenuList) {
			groupMenuMap.put(menu.getUuid(), menu);
		}
		
		List<TeeMenuButton> groupButtonList = menuGroup.getMenuButtons();
		Map<Integer,TeeMenuButton> groupButtonMap = new HashMap<Integer,TeeMenuButton>();
		for(TeeMenuButton button :groupButtonList) {
			groupButtonMap.put(button.getId(), button);
		}		
		
		//
		Map<String,List<TeeZTreeModel>> systemMap = new LinkedHashMap<String,List<TeeZTreeModel>>();
		//system
		for(TeeApplicationSystemMaintain system:systemList) {
			int id = system.getSid();
			List<TeeZTreeModel> treeList = systemMap.get(id);
			if(treeList == null) {
				treeList = new ArrayList<TeeZTreeModel>();
				systemMap.put(String.valueOf(id), treeList);
			}
			
			boolean isChecked = false;
			if(groupSystemMap.get(id) != null) {
				isChecked = true;
			}
			
			TeeZTreeModel node = new TeeZTreeModel();
			node.setId("S" + String.valueOf(system.getSid()));
			node.setpId("0");
			node.setName(system.getSystemName());
			node.setIconSkin("sys");
			node.setNocheck(false);
			node.setChecked(isChecked);
			node.setOpen(true);
			//node.setObj(system);
			treeList.add(node);
		}
		
		//menu
		Map<String,Integer> menuMap = new HashMap<String,Integer>();   //["000"(menuId),1(uuid) ]
		Map<String,String> menuMap2 = new HashMap<String,String>();   //["1"(uuid),"000"(menuId)]
		Map<String,Integer> menuSysMap = new HashMap<String,Integer>();//["000"(menuId),1(sysId)]
		for(TeeSysMenu menu: sysMenuList) {
			int menuUuid = menu.getUuid();
			String menuId = menu.getMenuId();
			menuMap.put(menuId, menuUuid);
			menuMap2.put(String.valueOf(menuUuid), menuId);
			
			int len = menuId.length();
			String pId = null;
			if(len == 3) {
				TeeApplicationSystemMaintain system = menu.getSys();
				if(system != null) {
					pId = "S" + String.valueOf(menu.getSys().getSid());
					menuSysMap.put(menuId, menu.getSys().getSid());					
				}
			}else if(len == 6){
				String pMenuId = menuId.substring(0, 3);
				Integer id = menuMap.get(pMenuId);
				if(id != null) {
					pId = "M" + String.valueOf(id);						
				}
			}else if(len == 9) {
				String pMenuId = menuId.substring(0, 6);
				Integer id = menuMap.get(pMenuId);
				if(id != null) {
					pId = "M" + String.valueOf(id);						
				}
			}
			
			if(pId != null) {
				
				boolean isChecked = false;
				if(groupMenuMap.get(menuUuid) != null) {
					isChecked = true;
				}
				
				String iconSkin = "fld";
				if(StringUtils.isNotBlank(menu.getMenuCode())) {
					iconSkin = "pag";
				}
 				
				TeeZTreeModel node = new TeeZTreeModel();
				node.setId("M" + String.valueOf(menuUuid));
				node.setpId(pId);
				node.setName(menu.getMenuName());
				node.setIconSkin(iconSkin);					
				node.setNocheck(false);
				node.setChecked(isChecked);
				node.setOpen(true);
				//node.setObj(menu);
				
				String level1 = menuId.substring(0, 3);
				Integer sysId =  menuSysMap.get(level1);
				if(sysId != null) {
					List<TeeZTreeModel> treeList = systemMap.get(sysId.toString());
					if(treeList != null) {
						treeList.add(node);					
					}
				}
			}
			
		}
		
		//button
		for(TeeMenuButton button:buttonList) {
			int id = button.getId();
			int menuUuid = button.getMenuId();
			
			String menuId = menuMap2.get(String.valueOf(menuUuid));
			if(menuId !=null || menuId.length()>=3) {
				
				boolean isChecked = false;
				if(groupButtonMap.get(id) != null) {
					isChecked = true;
				}
				
				TeeZTreeModel node = new TeeZTreeModel();
				node.setId("B" + String.valueOf(id));
				node.setpId("M" + String.valueOf(menuUuid));
				node.setName(button.getButtonName());
				node.setIconSkin("btn");
				node.setNocheck(false);
				node.setChecked(isChecked);
				node.setOpen(true);
				//node.setObj(button);

				String level1 = menuId.substring(0, 3);
				Integer sysId =  menuSysMap.get(level1);
				if(sysId != null) {
					List<TeeZTreeModel> treeList = systemMap.get(sysId.toString());
					if(treeList == null) {
						treeList = new ArrayList<TeeZTreeModel>();
						systemMap.put(String.valueOf(sysId), treeList);
					}
					treeList.add(node);
				}
			}			
		}

		List<List<TeeZTreeModel>> treesList = new ArrayList<List<TeeZTreeModel>>();
		for (String treeKey : systemMap.keySet()) {
			List<TeeZTreeModel> treeNodeList = systemMap.get(treeKey);
			treesList.add(treeNodeList);
		}
		
		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtMsg("ok");
		json.setRtData(treesList);
		return json;
	}

	@RequestMapping("/saveSysMenuBtnPriv.action")
	@ResponseBody
	public TeeJson saveSysMenuBtnPriv(HttpServletRequest request) throws Exception {
		
		String groupId = request.getParameter("groupId");//菜单组UUID
		String checkedIds = request.getParameter("checkedIds");//系统、菜单、按钮 IDs

		TeeJson json = new TeeJson();
		try {			
			if(!TeeUtility.isNullorEmpty(groupId)){
				String[] checkedIdArray = checkedIds.split(",");

				List<String> systemIdList = new ArrayList<String>();
				List<String> menuIdList = new ArrayList<String>();
				List<String> buttonIdList = new ArrayList<String>();
				for(String checkedId: checkedIdArray) {
					if(checkedId.length() > 1) {
						String type = checkedId.substring(0,1);
						if("S".equals(type)) {
							systemIdList.add(checkedId.substring(1));
						}else if("M".equals(type)) {
							menuIdList.add(checkedId.substring(1));					
						}else if("B".equals(type)) {
							buttonIdList.add(checkedId.substring(1));
						}						
					}
				}
			
				List<TeeApplicationSystemMaintain> systemList  = new ArrayList<TeeApplicationSystemMaintain>();
				if(systemIdList.size()>0){
					String hql = " from TeeApplicationSystemMaintain where sid in (" + TeeStringUtil.getSqlStringParse(systemIdList) + ")";
					systemList = appSystemServ.selectSystem(hql, null);
				}
				
				List<TeeSysMenu> menuList  = new ArrayList<TeeSysMenu>();
				if(menuIdList.size()>0){
					String hql = " from TeeSysMenu where uuid in (" + TeeStringUtil.getSqlStringParse(menuIdList) + ")";
					menuList = sysMenuServ.selectMenu(hql, null);
				}
				
				List<TeeMenuButton> buttonList  = new ArrayList<TeeMenuButton>();
				if(buttonIdList.size()>0){
					String hql = " from TeeMenuButton where id in (" + TeeStringUtil.getSqlStringParse(buttonIdList) + ")";
					buttonList = menuButtonServ.selectButton(hql, null);
				}
				
				TeeMenuGroup group = menuGroupServ.selectByUuid(Integer.parseInt(groupId));
				group.setAppSystems(systemList);
				group.setSysMenus(menuList);
				group.setMenuButtons(buttonList);
				menuGroupServ.updateMenuService(group);
				
			}
			json.setRtState(true);
			json.setRtMsg("成功返回结果！");
			json.setRtData("");
		} catch (Exception ex) {
			json.setRtState(false);
			json.setRtMsg(ex.getMessage());
			throw ex;
		}
		return json;
	}
	
	/***
	 * 获取菜单权限组
	 * @param menugroup
	 * @param response
	 * @return
	 */
	@RequestMapping("/getMenuGroupByDeptUuid.action")
	@ResponseBody
	public TeeJson getMenuGroupByDeptUuid(int deptUuid) {
		TeeJson json = new TeeJson();
		
		try {
			//TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			TeeDepartment dept = this.deptServ.get(deptUuid);
						
			List<TeeMenuGroup> groupList1 = menuGroupServ.getGroupListByDept(dept);
			List<TeeMenuGroup> groupList2 = menuGroupServ.getGroupListByDeptUuid(deptUuid);
			
			Map<Integer,TeeMenuGroup> groupMap = new HashMap<Integer,TeeMenuGroup>();
			
			for(TeeMenuGroup group: groupList1) {
				groupMap.put(group.getUuid(), group);
			}
			for(TeeMenuGroup group: groupList2) {
				groupMap.put(group.getUuid(), group);
			}
			
			List<TeeMenuGroupModul> groupList = new  ArrayList<TeeMenuGroupModul>();
			for(Map.Entry<Integer,TeeMenuGroup> entry: groupMap.entrySet()) {
				TeeMenuGroupModul groupModel = new TeeMenuGroupModul();
				BeanUtils.copyProperties(entry.getValue(), groupModel);
				groupList.add(groupModel);
			}
			
			json.setRtData(groupList);
			json.setRtState(true);
			json.setRtMsg("查询功能");
		}catch(Exception e) {
			json.setRtState(false);
			json.setRtMsg("查询报错");			
		}
		return json;
	}

	
}
