package com.tianee.oa.subsys.crm.core.contract.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.crm.core.contract.model.TeeCrmContractProductItemModel;
import com.tianee.oa.subsys.crm.core.contract.service.TeeCrmContractProductItemService;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/crmContractProductItemController")
public class TeeCrmContractProductItemController extends BaseController {
	@Autowired
	TeeCrmContractProductItemService contractProductItemService;
	/**
	 * 新增或者更新
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addUpdateSysCode(HttpServletRequest request, TeeCrmContractProductItemModel object){
		TeeJson json = contractProductItemService.addOrUpdate( request ,object);
		return json;
	}
	
	
	
}
