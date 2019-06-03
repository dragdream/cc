package com.tianee.oa.subsys.bisengin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.bisengin.bean.BusinessModel;
import com.tianee.oa.subsys.bisengin.model.BusModel;
import com.tianee.oa.subsys.bisengin.service.BusinessModelService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/businessModelController")
public class BusinessModelController {

	@Autowired
	private BusinessModelService businessModelService;

	/**
	 * 获取业务建模的列表
	 * 
	 * @param request
	 * @param dm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("datagrid")
	public TeeEasyuiDataGridJson datagrid(HttpServletRequest request,
			TeeDataGridModel dm) {
		BusModel model = new BusModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return businessModelService.datagrid(model, dm);
	}

	/**
	 * 删除业务建模
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public TeeJson deleteBusinessCatById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		BusinessModel model = new BusinessModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		businessModelService.deleteBusinessModelById(model);
		json.setRtMsg("删除成功");
		json.setRtState(true);
		return json;
	}

	/**
	 * 检查业务编号是否已经存在
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("checkSidIsExist")
	public TeeJson checkSidIsExist(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String bisKey = request.getParameter("bisKey");
		BusinessModel model = businessModelService.getBusinessModelById(bisKey);
		if (model != null && model.getBisKey() != "") {
			json.setRtState(false);
		} else {
			json.setRtState(true);
		}
		return json;
	}

	/**
	 * 根据主键获取model
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getBusinessModelByBisKey")
	public TeeJson getBusinessModelByBisKey(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String bisKey = request.getParameter("bisKey");
		BusModel model = businessModelService.getBusModelByBisKey(bisKey);
		json.setRtData(model);
		json.setRtMsg("成功获取");
		json.setRtState(true);
		return json;
	}

	/**
	 * 添加或者更新
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addOrUpdate")
	public TeeJson addOrUpdate(HttpServletRequest request) {
		String type = request.getParameter("type");
		BusModel model = new BusModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		TeeJson json = businessModelService.addOrUpdate(model, type);
		return json;
	}

	/**
	 * 显示表格
	 * 
	 * @param request
	 * @param dm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getTable")
	public TeeEasyuiDataGridJson getTable(HttpServletRequest request,
			TeeDataGridModel dm) {

		Map requestData = TeeServletUtility.getParamMap(request);
		
		// 传入一个bisKey
		String bisKey = requestData.get("bisKey").toString();
		// 传入searchModel
		// {birthDay_start:'xxxxx',birthDay_end:'xxxxx',name:'xxx'}
		String searchModel = requestData.get("searchModel").toString();
		// 调用service
		return businessModelService.getTable(bisKey, searchModel, dm, request);
	}

	/**
	 * 获取业务建模的shortContent 替换其中的控件 动态渲染页面
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getModelContent")
	public TeeJson getModelContent(HttpServletRequest request) {
		String bisKey = request.getParameter("bisKey");
		String operate = request.getParameter("operate");
		return businessModelService.getModelContent(bisKey, operate, request);

	}

	/**
	 * 根据bisKey获取表格的主键
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getPkIdByBisKey")
	public TeeJson getPkIdByBisKey(HttpServletRequest request) {
		String bisKey = request.getParameter("bisKey");
		return businessModelService.getPkIdByBisKey(bisKey);

	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getRecordByPkId")
	public TeeJson getRecordByPkId(HttpServletRequest request) {
		String bisKey = request.getParameter("bisKey");
		int pkId = TeeStringUtil.getInteger(request.getParameter("pkId"), 0);
		return businessModelService.getRecordByPkId(pkId, bisKey);
	}

	/**
	 * 添加或者编辑表格中的数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("addOrUpdateRecord")
	public TeeJson addOrUpdateRecord(HttpServletRequest request) {
		String bisKey = request.getParameter("bisKey");
		String operate = request.getParameter("operate");
		String param = request.getParameter("param");
		int pkId = TeeStringUtil.getInteger(request.getParameter("pkId"), 0);
		return businessModelService.addOrUpdateRecord(bisKey, operate, param,
				pkId);
	}

	/**
	 * 根据字段主键 字段对象
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getFieldById")
	public TeeJson getFieldById(HttpServletRequest request) {
		int fieldId = Integer.parseInt(request.getParameter("fieldId"));
		return businessModelService.getFieldById(fieldId);
	}

	/**
	 * 获取主表的列表数据
	 * 
	 * @param request
	 * @param dm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getMainTableRecordsList")
	public TeeEasyuiDataGridJson getMainTableRecordsList(
			HttpServletRequest request, TeeDataGridModel dm) {
		int tableId = Integer.parseInt(request.getParameter("mianTableId"));
		String sqlFilter=request.getParameter("sqlFilter");
		return businessModelService.getMainTableRecordsList(tableId,sqlFilter, dm,request);
	}

	/**
	 * 查看
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("view")
	public TeeJson view(HttpServletRequest request) {
		String bisKey = request.getParameter("bisKey");
		int pkId = Integer.parseInt(request.getParameter("pkId"));
		return businessModelService.view(bisKey, pkId);
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("del")
	public TeeJson del(HttpServletRequest request) {
		String bisKey = request.getParameter("bisKey");
		int pkId = Integer.parseInt(request.getParameter("pkId"));
		return businessModelService.del(bisKey, pkId);
	}

	/**
	 * 导出
	 * 
	 * @param request
	 */
	@RequestMapping("/export")
	public void export(HttpServletRequest request, HttpServletResponse response) {
		String bisKey = request.getParameter("bisKey");
		String searchModel=request.getParameter("searchModel");
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		businessModelService.export(bisKey,searchModel, pageNumber, pageSize, response,request);
	}

	/**
	 * 根据bisKey获取表格的字段名称 和 是否必填 的map集合
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getFieldRequiredMap")
	public TeeJson getFieldRequiredMap(HttpServletRequest request) {
		String bisKey = request.getParameter("bisKey");
		return businessModelService.getFieldRequiredMap(bisKey);
	}
	
	
	/**
	 * 根据附件id  获取附件集合
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getAttachList")
	public TeeJson getAttachList(HttpServletRequest request) {
		String attIds = request.getParameter("attIds");
		return businessModelService.getAttachList(attIds);
	}
}
