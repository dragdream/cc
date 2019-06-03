package com.beidasoft.gzpt.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.priv.model.TeeSysMenuModel;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/bdadmin")
public class BdAdminController {

	@RequestMapping("/getAdminMenu.action")
	@ResponseBody
	public TeeJson getAdminMenu(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try {
			TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			
			List<TeeSysMenuModel> list = new ArrayList<TeeSysMenuModel>();
			
			if("1".equals(person.getIsAdmin()) || "9".equals(person.getIsAdmin())) {
				TeeSysMenuModel menu1 = createMenuModel(1,"0.png","","000","系统管理");
				list.add(menu1);
				
				TeeSysMenuModel menu2 = createMenuModel(2,"icon_zzjggl.png","","000001","组织机构管理");
				list.add(menu2);

				TeeSysMenuModel menu3 = createMenuModel(3,"57.png","/system/core/dept/index.jsp","000001001","部门管理");
				list.add(menu3);

				TeeSysMenuModel menu4 = createMenuModel(4,"57.png","/system/core/person/index.jsp","000001002","用户管理");
				list.add(menu4);
				
				TeeSysMenuModel menu5 = createMenuModel(5,"57.png","/system/core/menuGroup/index.jsp","000001003","权限组管理");
				list.add(menu5);

				TeeSysMenuModel menu9 = createMenuModel(9,"57.png","/gzpt/adminUser/index.jsp","000001004","分级管理员");
				list.add(menu9);				
			}

			if("9".equals(person.getIsAdmin())) {
				TeeSysMenuModel menu6 = createMenuModel(6,"icon_zzjggl.png","","000002","平台菜单设置");
				list.add(menu6);
	
				TeeSysMenuModel menu7 = createMenuModel(7,"57.png","/system/core/system/applicationsystem/index.jsp","000002001","应用系统管理");
				list.add(menu7);
				
				TeeSysMenuModel menu8 = createMenuModel(8,"57.png","/system/core/menu/index.jsp","000002001","菜单管理");
				list.add(menu8);
			}

			json.setRtState(true);
			json.setRtMsg("成功返回结果！");
			json.setRtData(list);
		} catch (Exception ex) {
			json.setRtState(false);
			json.setRtMsg("返回结果失败！");
		}
		return json;
	}

	private TeeSysMenuModel createMenuModel(int uuid,String icon,String menuCode,String menuId,String menuName) {
		TeeSysMenuModel menu = new TeeSysMenuModel();
		menu.setUuid(uuid);
		menu.setIcon(icon);
		menu.setMenuCode(menuCode);
		menu.setMenuId(menuId);
		menu.setMenuName(menuName);
		return menu;
	}
	
}
