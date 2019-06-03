package com.tianee.oa.core.general.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tianee.oa.core.general.model.TeeShortcutMenuModel;
import com.tianee.oa.core.general.service.TeeShortcutMenuService;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/shortcutMenuController")
public class TeeShortcutMenuController extends BaseController{
	@Autowired
	private TeeShortcutMenuService shortcutMenuService;


	/**
	 * 新增或者更新
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addUpdate(HttpServletRequest request, TeeShortcutMenuModel model)throws Exception {
		TeeJson json = shortcutMenuService.addOrUpdateShortcutMenu(request, model);
		return json;
	}
	
	/**
	 * 根据人员获取 有权限的 常用菜单信息
	 * @author syl
	 * @date 2014-3-17
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getMenuInfoByPerson")
	@ResponseBody
	public TeeJson getMenuInfoByPerson(HttpServletRequest request)throws Exception {
		TeeJson json = shortcutMenuService.getMenuInfoByPerson(request);
		return json;
	}
	
	
	
	
}
