package com.tianee.oa.subsys.crm.core.base.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.subsys.crm.core.base.model.TeeCrmAfterSaleServModel;
import com.tianee.oa.subsys.crm.core.base.service.TeeCrmAfterSaleServService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/crmAfterSaleServController")
public class TeeCrmAfterSaleServController {

	@Autowired
	private TeeCrmAfterSaleServService service;

	/**
	 * 新增或者更新
	 * 
	 * @throws ParseException
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request) throws ParseException {
		TeeJson json = new TeeJson();
		TeeCrmAfterSaleServModel model = new TeeCrmAfterSaleServModel();

		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = service.addOrUpdateService(request, model);
		return json;
	}

	/**
	 * @function: 产品管理列表
	 * @author:
	 * @data: 2014年8月29日
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 *             TeeEasyuiDataGridJson
	 */
	@RequestMapping("/getManageInfoList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getRecruitList(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeCrmAfterSaleServModel model = new TeeCrmAfterSaleServModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return service.getManageInfoList(dm, request, model);
	}

	/**
	 * @function: 根据sid查看详情
	 * @author: wyw
	 * @data: 2014年8月29日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/getInfoById")
	@ResponseBody
	public TeeJson getInfoById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeeCrmAfterSaleServModel model = new TeeCrmAfterSaleServModel();
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = service.getInfoById(request, model);
		return json;
	}

	/**
	 * @function: 删除
	 * @author: wyw
	 * @data: 2014年8月30日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/deleteObjById")
	@ResponseBody
	public TeeJson deleteObjById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sidStr = request.getParameter("sids");
		json = service.deleteObjById(sidStr);
		return json;
	}

}
