package com.tianee.oa.core.priv.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.applicationSystem.bean.TeeApplicationSystemMaintain;
import com.tianee.oa.core.base.applicationSystem.service.TeeApplicationSystemMaintainService;
import com.tianee.oa.core.priv.bean.TeeSysMenu;
import com.tianee.oa.core.priv.model.TeeSysMenuModel;
import com.tianee.oa.core.priv.service.TeeSysMenuService;
import com.tianee.oa.oaconst.TeeActionKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;


@Controller
@RequestMapping("/sysMenu")
public class TeeMenuController {
	@Autowired
	TeeSysMenuService sysMenuServ;

	@Autowired
	TeeApplicationSystemMaintainService applicationSystemMaintainService;
	
	
	@RequestMapping("/toIndex.action")
	public String toIndex() {
		return "/system/common/menu/add.jsp";
	}

	@RequestMapping("/addMenu.action")
	public String addMenu(HttpServletRequest request, TeeSysMenu menu)
			throws Exception {
		/*
		 * menu.setMenuId("001"); menu.setMenuName("系统管理");
		 * menu.setMenuCode("/test/index.jsp"); menu.setIcon("11");
		 * menu.setOpenFlag("dd");
		 */
		String sb = "";
		try {
			sysMenuServ.add(menu);
			sb = "{uuid:'" + menu.getUuid() + "'}";
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
	 * 新增或添加
	 * @param request
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdateMenu.action")
	public String addOrUpdateMenu(HttpServletRequest request, TeeSysMenu menu)
			throws Exception {
		String sb = "";
		String isExistCode = "0";
		String oldMenuId = request.getParameter("oldMenuId") == null ? "" :  request.getParameter("oldMenuId");//原来菜单Id
		String pMenuId = request.getParameter("pMenuId") == null ? "" : request.getParameter("pMenuId");//上级菜单menuId
		menu.setMenuId(pMenuId + menu.getMenuId());
		
		//获取页面上传来的所属应用系统
		int sysId=TeeStringUtil.getInteger(request.getParameter("sysId"),0);
		if(sysId>0){
			TeeApplicationSystemMaintain sys=applicationSystemMaintainService.getById(sysId);
			menu.setSys(sys);
		}
		
		try {
			if(menu.getUuid() <= 0){
				//先判断是否存在编号
				boolean isExist = sysMenuServ.isExist(false,  menu);
				if(isExist){
					sysMenuServ.add(menu);	
				}else{
					isExistCode = "1";//已存在
				}
				
			}else{//更新
				boolean isExist = sysMenuServ.isExist(true, menu);
				if(isExist){
					sysMenuServ.updateMenu(menu);//
					if(!menu.getMenuId().equals(oldMenuId)){//如果更新过菜单编号
						sysMenuServ.updateMenu(oldMenuId, menu.getMenuId());
					}
					
				}else{
					isExistCode = "1";//已存在
				}
				
			}
			//sysMenuServ.addOrUpdateMenu(menu);
			sb = "{uuid:'" + menu.getUuid() + "',isExistCode:" +  isExistCode + "}";
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
	

	@RequestMapping("/getSysMenu.action")
	public String getSysMenu(HttpServletRequest request) throws Exception {

		try {
			String[] values = {};
			String data = "[";
			List<TeeSysMenu> list = sysMenuServ.selectMenu();
			TeeJsonUtil jsonUtil = new TeeJsonUtil();
			for (int i = 0; i < list.size(); i++) {
				TeeSysMenu menu = list.get(i);
				TeeSysMenuModel modul = new TeeSysMenuModel();
				BeanUtils.copyProperties(menu, modul);
				data = data + jsonUtil.obj2Json(modul).toString() + ",";
			}
			if (list.size() > 0) {
				data = data.substring(0, data.length() - 1);
			}
			data = data + "]";
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
	 * 查询 byId
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSysMenuById")
	public String getSysMenuById(HttpServletRequest request) throws Exception {
		String uuid = request.getParameter("uuid");
		String menuId = request.getParameter("menuId") == null ? "" : request.getParameter("menuId");
		String data = "";
		try {
			if (TeeUtility.isInteger(uuid)) {
				TeeSysMenu menu = sysMenuServ.selectMenuByUuid(uuid);
	
				
				TeeJsonUtil jsonUtil = new TeeJsonUtil();
				TeeSysMenuModel modul = new TeeSysMenuModel();
				BeanUtils.copyProperties(menu, modul);
				//设置应用系统相关信息
				if(menu.getSys()!=null){
					modul.setSysId(menu.getSys().getSid());
				}else{
					modul.setSysId(0);
				}
				
				data = data + jsonUtil.obj2Json(modul).toString() ;
				//data = jsonUtil.obj2Json(menu).toString();
				if(!TeeUtility.isNullorEmpty(menuId)){
					String parentMenuId = menuId.substring(0, menuId.length()-3);
					Object values[] = {parentMenuId};
					String hql = "from TeeSysMenu t where t.menuId = ?";
					List<TeeSysMenu> list = sysMenuServ.selectMenu(hql, values);
					if(list.size() > 0){
						data = data.substring(0, data.length() -1) 
								+ ",pMenuId:\"" + parentMenuId+ "\""
								
								+ ",pMenuIdDesc:\"" + TeeUtility.encodeSpecial(list.get(0).getMenuName()) + "\"}";
					}
				}
				
			}

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
	 * 更新
	 * 
	 * @param request
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toUpdateMenu.action")
	public String toUpdateMenu(HttpServletRequest request) throws Exception {
		return "/system/common/menu/update.jsp";
	}

	@RequestMapping("/updateMenu.action")
	public String updateMenu(HttpServletRequest request, TeeSysMenu menu)
			throws Exception {

		try {
			sysMenuServ.updateMenu(menu);
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
	 * 删除
	 * 
	 * @param request
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delMenu.action")
	public String delMenu(HttpServletRequest request) throws Exception {

		try {
			String uuid = request.getParameter("uuid");
			String menuId = request.getParameter("menuId");
			TeeSysMenu menu = new TeeSysMenu();
			if(TeeUtility.isInteger(uuid) && !TeeUtility.isNullorEmpty(menuId)){
				menu.setUuid(Integer.parseInt(uuid));
				menu.setMenuId(menuId);
				//先清除菜单组
				//sysMenuServ.clearMenuGroup(menu);
				
				sysMenuServ.delMenuAndChildMenuByUuid(menu);
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
	 * 获取下一级菜单列表 
	 * 
	 * @param request
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSysMenuByParent.action")
	public String getSysMenuByParent(HttpServletRequest request) throws Exception {

		try {
			String menuId = request.getParameter("menuId");
			String[] values = {"" + menuId + "%", menuId};//'%'||:name||'%' /% escape '/' 


			String data = "[";
			String hql = " from TeeSysMenu t where t.menuId like ? and  t.menuId != ? order by t.menuId ";
			List<TeeSysMenu> list = sysMenuServ.selectMenu(hql,values);
			TeeJsonUtil jsonUtil = new TeeJsonUtil();
			for (int i = 0; i < list.size(); i++) {
				TeeSysMenu menu = list.get(i);
				TeeSysMenuModel modul = new TeeSysMenuModel();
				BeanUtils.copyProperties(menu, modul);
				data = data + jsonUtil.obj2Json(modul).toString() + ",";
				//data = data + jsonUtil.obj2Json(menu).toString() + ",";
			}
			if (list.size() > 0) {
				data = data.substring(0, data.length() - 1);
			}
			data = data + "]";
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
	 * 获取所有菜单列表 
	 * 
	 * @param request
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAllSysMenu.action")
	public String getAllSysMenu(HttpServletRequest request) throws Exception {

		try {
			String[] values = null;


			String data = "[";
			String hql = " from TeeSysMenu t  order by t.menuId ";
			List<TeeSysMenu> list = sysMenuServ.selectMenu(hql,values);
			TeeJsonUtil jsonUtil = new TeeJsonUtil();
			for (int i = 0; i < list.size(); i++) {
				TeeSysMenu menu = list.get(i);
				TeeSysMenuModel modul = new TeeSysMenuModel();
				BeanUtils.copyProperties(menu, modul);
				data = data + jsonUtil.obj2Json(modul).toString() + ",";
				//data = data + jsonUtil.obj2Json(menu).toString() + ",";
			}
			if (list.size() > 0) {
				data = data.substring(0, data.length() - 1);
			}
			data = data + "]";
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
	 * 获取菜单树
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getMenuTree.action")
	public String getMenuTree(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
		int sid  = TeeStringUtil.getInteger(request.getParameter("sid") , 0);//修改时  ，过滤本身菜单（二级）
		String name = request.getParameter("name");
		String lengthStr = request.getParameter("length") == null ? "3" : request.getParameter("length") ;
		String data  = "[";
	
		try {
			int length =  Integer.parseInt(lengthStr);
			long lengthL = Long.parseLong(lengthStr);
			if(!TeeUtility.isNullorEmpty(id)){
				length = length + id.length();
				lengthL = lengthL + id.length();
				Object[] values = {"" + id + "%",length , sid};//'%'||:name||'%' /% escape '/' 
				if(TeeSysProps.getDialect().equals("kingbase")){//人大金仓数据库
					Object[] values2 = {"" + id + "%",lengthL , sid};
					values = values2;
				}
				String hql = " from TeeSysMenu t where t.menuId like ? and  length(t.menuId)= ?  and t.uuid <> ?  order by t.menuId";
				List<TeeSysMenu> list = sysMenuServ.selectMenu(hql,values);
				for (int i = 0; i < list.size(); i++) {
					TeeSysMenu menu = list.get(i);
					//判断是否存在下级菜单
					String subid = menu.getMenuId(); 
					String subhql = " from TeeSysMenu t where t.menuId like ? and  length(t.menuId)= ?   order by t.menuId";
					Object[] subvalues = {"" + subid + "%",length + 3};//'%'||:name||'%' /% escape '/' 
					boolean isParent = false;
					/*List<TeeSysMenu> sublist = sysMenuServ.selectMenu(subhql,subvalues);
					
					if(sublist.size() > 0){
						isParent = true;
					}*/
					data = data + "{ id:'" +menu.getMenuId()+ "', pId:0, name:'" + menu.getMenuName()+ "', iconSkin:'icon03',isParent:" + isParent + "},";
				}
				if(list.size() > 0){
					data = data.substring(0, data.length() - 1);
				}
				
			}else{//初始化
				Object[] values = {"" + id + "%",length};//'%'||:name||'%' /% escape '/' 
				if(TeeSysProps.getDialect().equals("kingbase")){//人大金仓数据库
					Object[] values2 = {"" + id + "%",lengthL};//'%'||:name||'%' /% escape '/' 
					values = values2;
				}
				String hql = " from TeeSysMenu t where t.menuId like ? and  length(t.menuId)= ?   order by t.menuId";
				List<TeeSysMenu> list = sysMenuServ.selectMenu(hql,values);
				for (int i = 0; i < list.size(); i++) {
					TeeSysMenu menu = list.get(i);
					data = data + "{ id:'" +menu.getMenuId()+ "', pId:0, name:'" + menu.getMenuName()+ "', iconSkin:'pIcon01',isParent:true},";
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
	 * 获取带权限的菜单组
	 * 
	 * @param request
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPrivSysMenu.action")
	public String getPrivSysMenu(HttpServletRequest request) throws Exception {

		try {
			String menuId = request.getParameter("menuId");
			Object[] values = {};//'%'||:name||'%' /% escape '/' 


			String data = "[";
			String hql = " from TeeSysMenu t order by t.menuId ";
			List<TeeSysMenu> list = sysMenuServ.selectMenu(hql,values);
			TeeJsonUtil jsonUtil = new TeeJsonUtil();
			for (int i = 0; i < list.size(); i++) {
				TeeSysMenu menu = list.get(i);
				//menu.setMenuGroup(null);
				TeeSysMenuModel um = new TeeSysMenuModel();
				BeanUtils.copyProperties(menu, um);
				data = data + jsonUtil.obj2Json(um).toString() + ",";
			}
			if (list.size() > 0) {
				data = data.substring(0, data.length() - 1);
			}
			data = data + "]";
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
	 * 更新菜单模块权限编号
	 * @param request
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateMenuModuleNo.action")
	@ResponseBody
	public TeeJson updateMenuModuleNo(HttpServletRequest request, TeeSysMenu menu){
		TeeJson json = new TeeJson();
		json = sysMenuServ.updateMenuModuleNo(request, menu);
		return json;
	}
	

	/**
	 * 获取所有菜单组树
	 * 获取所有菜单组
	 * 一次加载
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSysMenuTree.action")
	@ResponseBody
	public TeeJson getSysMenuTree(HttpServletRequest request) throws Exception {
		TeeJson json  = sysMenuServ.getSysMenuTree(request);
		return json;
	}
	
	
	
	/**
	 * 流程菜单定义指南
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addFlowMenu.action")
	@ResponseBody
	public TeeJson addFlowMenu(HttpServletRequest request){
		TeeJson json  = sysMenuServ.addFlowMenu(request);
		return json;
	}
	
	public TeeSysMenuService getSysMenuServ() {
		return sysMenuServ;
	}

	public void setSysMenuServ(TeeSysMenuService sysMenuServ) {
		this.sysMenuServ = sysMenuServ;
	}

	//根据系统获取菜单
	@RequestMapping("/getSysMenuBySysId.action")
	@ResponseBody
	public TeeJson getSysMenuBySysId(HttpServletRequest request) {
		String sysId = request.getParameter("sysId") == null ? "" : request.getParameter("sysId");
		TeeJson json = new TeeJson();
		List<TeeSysMenu> list = sysMenuServ.getSysMenuListBySysId(sysId);

		List<TeeSysMenuModel> listM = new ArrayList<TeeSysMenuModel>();
		for (int i = 0; i <list.size(); i++) {
			TeeSysMenuModel model = new TeeSysMenuModel();
			BeanUtils.copyProperties(list.get(i), model);
			listM.add(model);
		}		
		
		json.setRtState(true);
		json.setRtMsg("ok");
		json.setRtData(listM);
		return json;
	}
		
}
