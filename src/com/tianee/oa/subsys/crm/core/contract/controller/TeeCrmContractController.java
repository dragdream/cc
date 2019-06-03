package com.tianee.oa.subsys.crm.core.contract.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.contract.model.TeeCrmContractModel;
import com.tianee.oa.subsys.crm.core.contract.service.TeeCrmContractService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/teeCrmContractController")
public class TeeCrmContractController  extends BaseController{	
	@Autowired
	TeeCrmContractService crmContractService;
	/**
	 * 新增或者更新
	 * @param request
	 * @param para
	 * @return
	 * @throws ParseException 
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addUpdateSysCode(HttpServletRequest request ,TeeCrmContractModel model ) throws ParseException{
		TeeJson json = crmContractService.addOrUpdate( request , model );
		return json;
	}
	
	
	/**
	 * 合同管理  --通用分页
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/manager")
	@ResponseBody
	public TeeEasyuiDataGridJson manager(TeeDataGridModel dm,HttpServletRequest request) {
		Map map = new HashMap<String, String>();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		return crmContractService.manager(dm, request, loginPerson);
	}
	
	/**
	 * 删除 byIds
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteByIds")
	@ResponseBody
	public TeeJson deleteByIds(HttpServletRequest request){
		String ids = TeeStringUtil.getString(request.getParameter("sid") );
		TeeJson json = crmContractService.deleteByIds(request , ids);
		return json;
	}
	
	/**
	 * 获取 byIds
	 * @param request
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getByIds(HttpServletRequest request){
		TeeJson json = crmContractService.getById(request);
		return json;
	}
	

	
	
	
	
	/*汇总统计*/
	/**
	 * 合同年汇总报表
	 * @param request
	 * @return
	 */
	public TeeJson statisticsYearCollect(HttpServletRequest request){
		Map map = TeeServletUtility.getParamMap(request);
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = crmContractService.statisticsYearCollect(map, person);
		return json;
	}
}
