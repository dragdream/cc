package com.tianee.oa.core.priv.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.priv.bean.TeeMenuGroup;
import com.tianee.oa.core.priv.bean.TeeSysMenu;
import com.tianee.oa.core.priv.dao.TeeMenuDao;
import com.tianee.oa.core.priv.dao.TeeMenuGroupDao;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * @author syl
 *
 */
@Service
public class TeeSysMenuService extends TeeBaseService {
	@Autowired
	private TeeMenuDao menuDao;
	
	@Autowired
	private TeeMenuGroupDao menuGroupDao;
    
	public void add(TeeSysMenu menu){
		menuDao.addMenu(menu);
	}

	
	/**
	 * 更新
	 * @param TeeSysMenu
	 */
	public void updateMenu(TeeSysMenu TeeSysMenu) {
		
		menuDao.update(TeeSysMenu);	
	}
	
	
	/**
	 * 查询 byId
	 * @param TeeSysMenu
	 */
	public TeeSysMenu selectMenuByUuid(String uuid) {
		
		//TeeSysMenu sysMenu = (TeeSysMenu) menuDao.selectMenuByUuid(uuid);	
		TeeSysMenu sysMenu = (TeeSysMenu) menuDao.selectMenuById(Integer.parseInt(uuid));
		return sysMenu;
	}
	
	
	/**
	 * 删除byId
	 * @param TeeSysMenu
	 */
	public void delMenuByUuid(TeeSysMenu menu) {
		menuDao.delSysMenu(menu);	
	}
	
	/**
	 * 级联清空菜单组
	 * @param TeeSysMenu
	 */
	public void clearMenuGroup(TeeSysMenu menu) {
		
		//先查询，清除菜单组外键关系表,
		List<TeeSysMenu>  list = menuDao.selectMenuAndChildMenuByUuid(menu);
		for (int i = 0; i < list.size(); i++) {
			TeeSysMenu menuObj = list.get(i);
			List<TeeMenuGroup> listG =  menuObj.getMenuGroup();
			for (int j = 0; j < listG.size(); j++) {
				TeeMenuGroup group = listG.get(j);
				List<TeeSysMenu>  listNew = group.getSysMenus();	
				listNew.remove(menuObj);
				group.setSysMenus(listNew);
				//menuGroupDao.updateMenuGroup(group);
			}
			menuObj.setMenuGroup(null);
			//menuDao.updateMenu(menuObj);

		}
		
		
	}
	/**
	 * 删除 包括删除下级菜单
	 * @param menu
	 */
	public void delMenuAndChildMenuByUuid(TeeSysMenu menu) {
		menuDao.delMenuAndChildMenuByUuid(menu);
	}
	

	
	
    /**

	* 更新 下级菜单编号
	* @param oldMenuId : 更改之前编号
	* @param newMenuId: 新编号
	*/
	public void updateMenu(String oldMenuId , String newMenuId) {
		int newMenuIdLength = newMenuId.length();
		String hql = " update TeeSysMenu t set t.menuId = case  when 1=1 then  concat('" +newMenuId + "',substring(t.menuId," + (newMenuIdLength + 1 ) + ")) end   where  t.menuId like ? ";
		Object[] values = { oldMenuId + "%" };
		menuDao.delSysMenu(hql, values);
	}
	/**
	 * 查询 条件查询  ---- 第一级菜单
	 * @param TeeSysMenu
	 */
	public List<TeeSysMenu> selectMenu() {
		List<TeeSysMenu> list = menuDao.selectParentMenu();
		return list;
	}
	
	/**
	 * 查询 条件查询 
	 * @param TeeSysMenu
	 */
	public List<TeeSysMenu> selectMenu(String hql, Object[] values) {
		List<TeeSysMenu> list = menuDao.selectMenu(hql, values);
		return list;
	}
	
   /**
    * 判断是否存在编号的菜单
    * @param status  true-修改 ；false-新增
    * @param menuCode 新菜单编号  
    *  @param oldMenucode:老菜单编号
    * @return
    */
	public boolean isExist(boolean status , TeeSysMenu menu) {
		
		String hql = " from TeeSysMenu t where t.menuId = ?";
		String[] values = {menu.getMenuId() };
		List<TeeSysMenu> list = new ArrayList<TeeSysMenu>();
		if(status){
			hql = " from TeeSysMenu t where t.menuId = ? and t.uuid != ? ";
		   Object[] values2 = {menu.getMenuId() ,menu.getUuid() };
		   list = menuDao.selectMenu(hql, values2);
		}else{
			 list = menuDao.selectMenu(hql, values);
		}
	
		if(list.size() > 0){//存在
			return false;
		}
		return true;
	}
	
	/**
	 * 更新菜单模块权限
	 * @author syl
	 * @date 2014-2-14
	 * @param request
	 * @param menu
	 * @return
	 */
	public TeeJson updateMenuModuleNo(HttpServletRequest request , TeeSysMenu menu){
		TeeJson json = new TeeJson();
		menuDao.updateMenuModuleNo(menu);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取所有菜单树  ---通用选择
	 * @author syl
	 * @date 2014-2-25
	 * @param request
	 * @return
	 */
	public TeeJson getSysMenuTree(HttpServletRequest request ){
		TeeJson json = new TeeJson();
	   	//所有菜单
		List<TeeSysMenu> list = menuDao.getSysMenuTree();
		String checkIds = "," + TeeStringUtil.getString(request.getParameter("checkIds")) + ",";
		List<TeeZTreeModel> modelList= new ArrayList<TeeZTreeModel>();
		for (int i = 0; i < list.size(); i++) {
			TeeSysMenu menu = list.get(i);
			TeeZTreeModel ztreeModel = new TeeZTreeModel();
			
			ztreeModel.setId( menu.getMenuId());
			ztreeModel.setName(menu.getMenuName());
			ztreeModel.setExtend1(menu.getUuid() + "");
			if (menu.getMenuId().length() == 3) {// 父级及诶单
				ztreeModel.setpId("0");
			} else {
				ztreeModel.setpId(menu.getMenuId().substring(0,
						menu.getMenuId().length() - 3));
				
			}
			if(checkIds.indexOf("," + menu.getUuid() + ",") >=0){//存在
				ztreeModel.setChecked(true);
			}
			modelList.add(ztreeModel);
		}
	
		Map map = new HashMap();
		map.put("ztreeData", modelList);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	
	
	/**
	 * 增加 或者更新菜单
	 * @param TeeSysMenu
	 */
	public void addOrUpdateMenu(TeeSysMenu TeeSysMenu) {
		menuDao.saveOrUpdate(TeeSysMenu);	
	}
	
	
	public TeeMenuDao getMenuDao() {
		return menuDao;
	}

	public void setMenuDao(TeeMenuDao menuDao) {
		this.menuDao = menuDao;
	}


	public void setMenuGroupDao(TeeMenuGroupDao menuGroupDao) {
		this.menuGroupDao = menuGroupDao;
	}


	
	/**
	 * 流程菜单定义指南
	 * @param request
	 * @return
	 */
	public TeeJson addFlowMenu(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//上级菜单
		int pMenuId=TeeStringUtil.getInteger(request.getParameter("pMenuId"), 0);
		
		String menus=TeeStringUtil.getString(request.getParameter("menus"));
		List<TeeSysMenu>  menuList=TeeJsonUtil.JsonStr2ObjectList(menus, TeeSysMenu.class);
		
		
		String isExistCode="0";
		String hasExistCode="";
		if(menuList!=null&&menuList.size()>0){
			for (TeeSysMenu teeSysMenu : menuList) {
				//先判断是否存在编号
				boolean isExist = isExist(false,  teeSysMenu);
				if(!isExist){//已存在
					hasExistCode+=teeSysMenu.getMenuId().substring(teeSysMenu.getMenuId().length()-3, teeSysMenu.getMenuId().length())+",";
					isExistCode="1";
				}
			}
			
			if(hasExistCode.endsWith(",")){
				hasExistCode=hasExistCode.substring(0, hasExistCode.length()-1);
			}
			
			if(("1").equals(isExistCode)){//说明有的编号已经存在
				json.setRtState(false);
				json.setRtMsg("子菜单编号："+hasExistCode+"已存在！");
				return json;
			}else{
				for (TeeSysMenu teeSysMenu : menuList) {
					simpleDaoSupport.save(teeSysMenu);
				}
				
				json.setRtState(true);
			}	
		}
		return json;
	}
	
	public List<TeeSysMenu> getSysMenuListBySysId(String sysId){
		return menuDao.getSysMenuListBySysId(sysId);		
	}

}
