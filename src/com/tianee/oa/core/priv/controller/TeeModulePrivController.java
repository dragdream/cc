package com.tianee.oa.core.priv.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.priv.bean.TeeModulePriv;
import com.tianee.oa.core.priv.model.TeeModulePrivModel;
import com.tianee.oa.core.priv.service.TeeModulePrivService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/modulePrivManager")
public class TeeModulePrivController {

	@Autowired
	TeeModulePrivService  modulPrivServ;


	/***
	 * 新增或者修改
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOrUpdate.action")
	@ResponseBody
	public TeeJson addOrUpdateMenuGroup(TeeModulePrivModel model,HttpServletRequest request) {		
		TeeJson json = new TeeJson();
		json = modulPrivServ.addOrUpdate(model,json);
		return json;
	}
	
	/**
	 * byId
	 * @param request
	 * @return
	 */
	@RequestMapping("/getById.action")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request) {	
		String sid = request.getParameter("sid");
		TeeJson json = new TeeJson();
		TeeModulePriv priv  =modulPrivServ.selectById(sid);
		TeeModulePrivModel model = new TeeModulePrivModel();
		BeanUtils.copyProperties(priv, model);
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}
	/**
	 * 根据人员获取模块权限设置
	 * @param request
	 * @return
	 */
	@RequestMapping("/getByUserId.action")
	@ResponseBody
	public TeeJson getByUserId(HttpServletRequest request) {	
		String personId = request.getParameter("personId");
		String module = request.getParameter("model");
		TeeJson json = new TeeJson();
		TeeModulePrivModel model  =modulPrivServ.selectPrivByUserId(personId,module);

		json.setRtData(model);
		json.setRtState(true);
		return json;
	}

	public void setModulPrivServ(TeeModulePrivService modulPrivServ) {
		this.modulPrivServ = modulPrivServ;
	}
	
	
}
