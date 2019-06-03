package com.tianee.oa.subsys.crm.setting.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.subsys.crm.setting.model.TeeChinaCityModel;
import com.tianee.oa.subsys.crm.setting.service.TeeChinaCityService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/chinaCityController")
public class TeeChinaCityController extends BaseController {
	@Autowired
	private TeeChinaCityService service;

	/**
	 * 新增或者更新
	 * 
	 * @throws ParseException
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request) throws ParseException {
		TeeJson json = new TeeJson();
		TeeChinaCityModel model = new TeeChinaCityModel();

		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = service.addOrUpdateService(request, model);
		return json;
	}

	/**
	 * 省-列表查询
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getChinaCityList")
	@ResponseBody
	public TeeEasyuiDataGridJson getChinaCityList(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeChinaCityModel model = new TeeChinaCityModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return service.getChinaCityList(dm, request, model);
	}

	/**
	 * 根据sid查看详情
	 * 
	 * @date 2014-3-8
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/getInfoById")
	@ResponseBody
	public TeeJson getInfoById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeeChinaCityModel model = new TeeChinaCityModel();
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = service.getInfoByIdService(request, model);
		return json;
	}

	/**
	 * 删除省
	 * 
	 * @date 2014年5月27日
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteObjById")
	@ResponseBody
	public TeeJson deleteObjById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sidStr = request.getParameter("sids");
		json = service.deleteObjByIdService(sidStr);
		return json;
	}
	
	/**
	 * 删除市
	 * 
	 * @date 2014年5月27日
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteCityObjById")
	@ResponseBody
	public TeeJson deleteCityObjById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sidStr = request.getParameter("sids");
		json = service.deleteCityObjById(sidStr);
		return json;
	}
	/**
	 * 删除县
	 * 
	 * @date 2014年5月27日
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteCountyObjById")
	@ResponseBody
	public TeeJson deleteCountyObjById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sidStr = request.getParameter("sids");
		json = service.deleteCountyObjById(sidStr);
		return json;
	}


	/**
	 * 审批查询
	 * 
	 * @date 2014-3-18
	 * @author
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getPlanApprovalList")
	@ResponseBody
	public TeeEasyuiDataGridJson getPlanApprovalList(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeChinaCityModel model = new TeeChinaCityModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return service.getPlanApprovalListService(dm, request, model);
	}
	
	

	
	/**
	 * 获取所有的省和直辖市
	 * @param request
	 * @return
	 * 2014年8月24日
	 * TeeJson
	 *
	 */
	@RequestMapping("/getProvinceList")
	@ResponseBody
	public TeeJson getProvinceList(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		json = service.getProvinceList();
		return json;
	}


	
	/**
	 * @function: 获取所有的省和直辖市
	 * @author: 
	 * @data: 2014年8月24日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/getCityListByCode")
	@ResponseBody
	public TeeJson getCityListByCityCode(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String cityCode = request.getParameter("cityCode");
		json = service.getCityListByCode(cityCode);
		return json;
	}
	
	/**
	 * @function: 获取所有县级地区
	 * @author: 
	 * @data: 2014年8月24日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/getCountyListByCode")
	@ResponseBody
	public TeeJson getCountyListByCode(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String cityCode = request.getParameter("cityCode");
		json = service.getCountyListByCode(cityCode);
		return json;
	}


	/**
	 * @function: 校验城市编号
	 * @author: 
	 * @data: 2014年8月24日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/checkCityCode")
	@ResponseBody
	public TeeJson checkCityCode(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeeChinaCityModel model = new TeeChinaCityModel();

		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = service.checkCityCode(model);
		return json;
	}
	
	
	/**
	 * 市-列表查询
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getCityListByProvinceCode")
	@ResponseBody
	public TeeEasyuiDataGridJson getCityListByCode(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeChinaCityModel model = new TeeChinaCityModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return service.getCityListByCode(dm, request, model);
	}
	/**
	 * @function: 县-列表查询
	 * @author: 
	 * @data: 2014年8月25日
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException TeeEasyuiDataGridJson
	 */
	@RequestMapping("/getCountyByCityCode")
	@ResponseBody
	public TeeEasyuiDataGridJson getCountyByCityCode(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeChinaCityModel model = new TeeChinaCityModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return service.getCountyByCityCode(dm, request, model);
	}
	
	/**
	 * @function: 获取省份树
	 * @author: nieyi
	 * @data: 2014年9月3日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/getProvinceTree")
	@ResponseBody
	public TeeJson getProvinceTree(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String provinceNo = TeeStringUtil.getString(request.getParameter("id"), "");
		json = service.getProvinceTree(provinceNo);
		return json;
	}
	
	/**
	 * @function: 根据城市编号获取数据对象
	 * @author: wyw
	 * @data: 2014年9月3日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/getInfoByCityCode")
	@ResponseBody
	public TeeJson getInfoByCityCode(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeeChinaCityModel model = new TeeChinaCityModel();
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = service.getInfoByCityCode(request, model);
		return json;
	}
	
	
	
	/**
	 * @function: 根据城市编号自动获取下一个
	 * @author: wyw
	 * @data: 2014年9月20日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/getAutoNumberByCityCode")
	@ResponseBody
	public TeeJson getAutoNumberByCityCode(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeeChinaCityModel model = new TeeChinaCityModel();
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		json = service.getAutoNumberByCityCode(requestMap, model);
		return json;
	}
	
	

}
