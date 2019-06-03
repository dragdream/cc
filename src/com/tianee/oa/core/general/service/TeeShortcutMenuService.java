package com.tianee.oa.core.general.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.bean.TeeShortcutMenu;
import com.tianee.oa.core.general.dao.TeeShortcutMenuDao;
import com.tianee.oa.core.general.model.TeeShortcutMenuModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.priv.bean.TeeMenuGroup;
import com.tianee.oa.core.priv.bean.TeeSysMenu;
import com.tianee.oa.core.priv.dao.TeeMenuDao;
import com.tianee.oa.core.priv.model.TeeSysMenuModel;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeShortcutMenuService extends TeeBaseService {
	@Autowired
	private TeeShortcutMenuDao shortcutMenuDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeMenuDao menuDao;
	
	/**
	 * 新增
	 * @author syl
	 * @date 2014-3-17
	 * @param intf
	 */
	public TeeJson addOrUpdateShortcutMenu(HttpServletRequest request , TeeShortcutMenuModel model) {
		TeeJson json = new TeeJson();
		TeeShortcutMenu menu = new TeeShortcutMenu();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String selectShortcutMenuDesc = TeeStringUtil.getString(request.getParameter("selectShortcutMenuDesc"));//选中
		//String noSelectShortcutMenuDesc = TeeStringUtil.getString(request.getParameter("noSelectShortcutMenuDesc"));//未选中的
		menu.setUser(person);
		menu.setMenuIds(selectShortcutMenuDesc);
		
		List<TeeShortcutMenu> list = shortcutMenuDao.selectByPersonId(person);
		if(list.size() > 0){
			TeeShortcutMenu old = list.get(0);
			int sid = old.getSid();
			if(old != null){
				BeanUtils.copyProperties(menu, old);
				old.setSid(sid);
				shortcutMenuDao.updateShortcutMenu(old);
			}else{
				shortcutMenuDao.addShortcutMenu(menu);	
			}
		}else{
			
			shortcutMenuDao.addShortcutMenu(menu);	
		}
		
		json.setRtState(true);
		json.setRtMsg("保存成功！");
		return json;
	}

	   
	/**
	 * 根据人员查询
	 * @param 
	 */
	public  TeeJson selectByPersonId(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<TeeShortcutMenu> list = shortcutMenuDao.selectByPersonId(person);
		if(list.size() > 0){
			TeeShortcutMenuModel model = parseModel(list.get(0));
			json.setRtData(model);
		}
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 转换模型
	 * @author syl
	 * @date 2014-3-17
	 * @param shortcutMenu
	 * @return
	 */
	public TeeShortcutMenuModel parseModel(TeeShortcutMenu shortcutMenu){
		TeeShortcutMenuModel model = new TeeShortcutMenuModel();
		if(shortcutMenu == null){
			return model;
		}
		BeanUtils.copyProperties(shortcutMenu, model);
		model.setUserId(shortcutMenu.getUser().getUuid() + "");
		model.setUserName(shortcutMenu.getUser().getUserName());
		
		return model;
	}
	
	
	/**
	 * 获取当前登录人 有权限的菜单
	 * @author syl
	 * @date 2014-3-17
	 * @param request
	 * @return
	 */
	public TeeJson getMenuInfoByPerson(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<TeeSysMenu>  list = new ArrayList<TeeSysMenu>(); 
		person = personDao.selectPersonById(person.getUuid());//重新从数据库取依稀
		List<TeeMenuGroup> groupList = person.getMenuGroups();
		String groupIds = "";
		for (int i = 0; i < groupList.size(); i++) {
			TeeMenuGroup group = groupList.get(i);
			groupIds = groupIds  + group.getUuid() + ",";
		}
		if(TeeUtility.isNullorEmpty(groupIds)){
			json.setRtState(true);
			json.setRtData("");
			return json;
		}
		/*获取系统登录人 有权限的所有叶子菜单 （URL 不为空）*/
	   list = menuDao.getChildSysMenuListByMenuGroupUuids(groupIds);
	   
	   //获取个人常用菜单组记录
	   List<TeeShortcutMenu> shortcutMenulist = shortcutMenuDao.selectByPersonId(person);
	   String shortcutMenuIds = "";//选中菜单Ids
	   if(shortcutMenulist.size() > 0){
		   TeeShortcutMenu shortcutMenu =  shortcutMenulist.get(0);
		   shortcutMenuIds = TeeStringUtil.getString(shortcutMenu.getMenuIds());
	   }
		
	   List<TeeSysMenuModel> selectSysMenuModelList = new ArrayList<TeeSysMenuModel>();//个人选中的
	   List<TeeSysMenuModel> notSelectSysMenuModelList = new ArrayList<TeeSysMenuModel>();//个人未选中的
	   Map map = new HashMap();
	   shortcutMenuIds = ","+ shortcutMenuIds + ",";
	   for (int i = 0; i < list.size(); i++) {
		   TeeSysMenu sysMenu = list.get(i);
		   TeeSysMenuModel SysMenuModul  = new TeeSysMenuModel();
		   BeanUtils.copyProperties(sysMenu, SysMenuModul);
		   if(shortcutMenuIds.indexOf("," + sysMenu.getUuid()+ "," ) >= 0){
			   selectSysMenuModelList.add(SysMenuModul);//包含
		   }else{
			   notSelectSysMenuModelList.add(SysMenuModul);//个人未选中的
		   }
	   }
	   map.put("selectSysMenuModelList", selectSysMenuModelList);
	   map.put("notSelectSysMenuModelList", notSelectSysMenuModelList);
	   json.setRtData(map);
	   json.setRtState(true);
	   return json;
	}
}