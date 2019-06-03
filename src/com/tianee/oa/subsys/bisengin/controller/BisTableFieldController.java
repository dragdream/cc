package com.tianee.oa.subsys.bisengin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.bisengin.bean.BisTableField;
import com.tianee.oa.subsys.bisengin.model.BisTableFieldModel;
import com.tianee.oa.subsys.bisengin.service.BisTableFieldService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("bisTableField")
public class BisTableFieldController {
	@Autowired
	private BisTableFieldService bisTableFieldService;
	
	/**
	 * 检查业务表字段是否存在
	 * @return
	 */
	@ResponseBody
	@RequestMapping("checkFieldIsExist")
	public TeeJson checkFieldIsExist(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int tableId = TeeStringUtil.getInteger(request.getParameter("tableId"), 0);
		String fieldName = TeeStringUtil.getString(request.getParameter("fieldName"));
		json.setRtState(true);
		json.setRtData(bisTableFieldService.checkFieldIsExist(fieldName, tableId));
		return json;
	}
	
	/**
	 * 通过ID查找模型
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getModelById")
	public TeeJson getModelById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(bisTableFieldService.getModelById(sid));
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 增加业务表字段
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addBisTableField")
	public TeeJson addBisTableField(HttpServletRequest request){
		TeeJson json = new TeeJson();
		BisTableFieldModel bisTableFieldModel = new BisTableFieldModel();
		TeeServletUtility.requestParamsCopyToObject(request, bisTableFieldModel);
		//System.out.println("字段控制模型："+bisTableFieldModel.getFieldControlModel());
		bisTableFieldService.addBisTableField(bisTableFieldModel);
		json.setRtState(true);
		json.setRtMsg("添加成功");
		return json;
	}
	
	/**
	 * 更新业务表字段
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateBisTableField")
	public TeeJson updateBisTableField(HttpServletRequest request){
		TeeJson json = new TeeJson();
		BisTableFieldModel bisTableFieldModel = new BisTableFieldModel();
		TeeServletUtility.requestParamsCopyToObject(request, bisTableFieldModel);
		bisTableFieldService.updateBisTableField(bisTableFieldModel);
		json.setRtState(true);
		json.setRtMsg("更新成功");
		return json;
	}
	
	/**
	 * 删除业务表字段
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteBisTableField")
	public TeeJson deleteBisTableField(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		bisTableFieldService.deleteBisTableField(sid);
		json.setRtState(true);
		json.setRtMsg("删除成功");
		return json;
	}
	
	/**
	 * 创建索引
	 * @return
	 */
	@ResponseBody
	@RequestMapping("createIndex")
	public TeeJson createIndex(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		bisTableFieldService.createIndex(sid);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 取消索引
	 * @return
	 */
	@ResponseBody
	@RequestMapping("cancelIndex")
	public TeeJson cancelIndex(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		bisTableFieldService.cancelIndex(sid);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 更改字段
	 * @return
	 */
	@ResponseBody
	@RequestMapping("changeField")
	public TeeJson createField(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		bisTableFieldService.changeField(sid);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("getFieldTypeExt")
	public TeeJson getFieldTypeExt(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String dbType = TeeStringUtil.getString(request.getParameter("dbType"));
		String fieldType = TeeStringUtil.getString(request.getParameter("fieldType"));
		json.setRtState(true);
		json.setRtData(BisTableField.getBasicFieldExt(dbType, fieldType));
		return json;
	}
	
	@ResponseBody
	@RequestMapping("datagrid")
	public TeeEasyuiDataGridJson datagrid(HttpServletRequest request,TeeDataGridModel dm){
		BisTableFieldModel bisTableFieldModel = new BisTableFieldModel();
		TeeServletUtility.requestParamsCopyToObject(request, bisTableFieldModel);
		return bisTableFieldService.datagrid(bisTableFieldModel, dm);
	}
	
	/**
	 * 设置主键
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("setPrimaryKey")
	public TeeJson setPrimaryKey(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		bisTableFieldService.setPrimaryKey(sid);
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 根据表格的id获取字段的列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getFieldsByTableId")
	public TeeJson getFieldsByTableId(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int tableId = TeeStringUtil.getInteger(request.getParameter("tableId"), 0);
		List<BisTableField>fieldList=bisTableFieldService.getFieldsByTableId(tableId);
		json.setRtData(fieldList);
		json.setRtState(true);
		return json;
		
		
	}
}
