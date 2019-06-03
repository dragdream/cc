package com.tianee.oa.core.base.hr.recruit.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.base.hr.recruit.model.TeeHrFilterItemModel;
import com.tianee.oa.core.base.hr.recruit.model.TeeHrFilterModel;
import com.tianee.oa.core.base.hr.recruit.service.TeeHrFilterItemService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
@Controller
@RequestMapping("/hrFilterItemController")
public class TeeHrFilerItemController extends BaseController {
	@Autowired 
	private TeeHrFilterItemService filterItemService;

	/**
	 * 新增或者更新
	 * @throws ParseException
	 * @throws IOException 
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , TeeHrFilterItemModel model)
			throws ParseException, IOException {
		TeeJson json = new TeeJson();
		json = filterItemService.addOrUpdate(request, model);
		return json;
	}

	
}
