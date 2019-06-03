package com.tianee.oa.core.base.officeProducts.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeStock;
import com.tianee.oa.core.base.officeProducts.model.TeeOfficeStockModel;
import com.tianee.oa.core.base.officeProducts.service.TeeOfficeStockService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/officeStockController")
public class TeeOfficeStockController {
	@Autowired
	private TeeOfficeStockService officeStockService;
	
	@RequestMapping("/addStockInfo")
	@ResponseBody
	public TeeJson addStockInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtState(true);
		
		TeeOfficeStockModel model = new TeeOfficeStockModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		model.setRegUserId(loginUser.getUuid());
		model.setRegUserName(loginUser.getUserName());
		officeStockService.saveStockModel(model);
		json.setRtMsg("操作成功");
		return json;
	}
	
}
