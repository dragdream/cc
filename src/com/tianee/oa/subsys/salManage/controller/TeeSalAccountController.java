package com.tianee.oa.subsys.salManage.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.subsys.crm.core.base.model.TeeCrmAfterSaleServModel;
import com.tianee.oa.subsys.salManage.model.TeeSalAccountModel;
import com.tianee.oa.subsys.salManage.model.TeeSalAccountPersonModel;
import com.tianee.oa.subsys.salManage.service.TeeSalAccountPersonService;
import com.tianee.oa.subsys.salManage.service.TeeSalAccountService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

/**
 * 账套管理
 * @author think
 *
 */
@Controller
@RequestMapping("/teeSalAccountController")
public class TeeSalAccountController  extends BaseController{
	
	@Autowired
	private TeeSalAccountService accountService;
	
	@Autowired
	private TeeSalAccountPersonService accountPersonService;
	
	
	/**新建或者更新
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , TeeSalAccountModel model) {
		TeeJson json = new TeeJson();
		Map map = TeeServletUtility.getParamMap(request);
		json = accountService.addOrUpdate(map  , model);
		return json;
	}
	
	
	/**获取账套  by Id
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request , TeeSalAccountModel model) {
		TeeJson json = new TeeJson();
		json = accountService.getById(model);
		return json;
	}
	
	/**
	 * 获取所有账套
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllAccount")
	@ResponseBody
	public TeeJson getAllAccount(HttpServletRequest request ){
		TeeJson json = accountService.getAllAccount();
		return json;
	}
	
	
	/**
	 * 删除ById
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public TeeJson deleteById(HttpServletRequest request,TeeSalAccountModel model ){
		TeeJson json = accountService.deleteById(model);
		return json;
	}
	
	
	/**
	 * 获取账套人员通用列表
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getManageAccountPersonList")
	@ResponseBody
	public TeeEasyuiDataGridJson getManageAccountPersonList(TeeDataGridModel dm, HttpServletRequest request , TeeSalAccountPersonModel model) {
		return accountPersonService.getManageAccountPersonList(dm, request, model);
	}

	
	/**
	 * 添加账套人员
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/addPersonByAccount")
	@ResponseBody
	public TeeJson addPersonByAccount( HttpServletRequest request , TeeSalAccountPersonModel model){
		Map map = TeeServletUtility.getParamMap(request);
		TeeJson json = accountPersonService.addPersonByAccount(map, model);
		return json;
	}
	
	/**
	 * 移除账套人员
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/deletePersonByIdsAndAccountId")
	@ResponseBody
	public TeeJson deleteByIdsAndAccountId( HttpServletRequest request , TeeSalAccountPersonModel model){
		Map map = TeeServletUtility.getParamMap(request);
		TeeJson json = accountPersonService.deleteByIdsAndAccountId(map, model);
		return json;
	}
	
}
