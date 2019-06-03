package com.tianee.oa.core.base.fixedAssets.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.fixedAssets.bean.TeeFixedAssetsCategory;
import com.tianee.oa.core.base.fixedAssets.model.TeeFixedAssetsCategoryModel;
import com.tianee.oa.core.base.fixedAssets.service.TeeFixedAssetsCategoryService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("TeeFixedAssetsCategoryController")
public class TeeFixedAssetsCategoryController {
	@Autowired
	TeeFixedAssetsCategoryService assetsTypeService;
	
	@RequestMapping("/addAssetsType")
	@ResponseBody
	public TeeJson addAssetsType(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeFixedAssetsCategoryModel assetsTypeModel = new TeeFixedAssetsCategoryModel();
		TeeServletUtility.requestParamsCopyToObject(request, assetsTypeModel);
		assetsTypeService.addAssetsTypeModel(assetsTypeModel);
		json.setRtState(true);
		json.setRtMsg("添加成功");
		return json;
	}
	
	@RequestMapping("/editAssetsType")
	@ResponseBody
	public TeeJson editAssetsType(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeFixedAssetsCategoryModel assetsTypeModel = new TeeFixedAssetsCategoryModel();
		TeeServletUtility.requestParamsCopyToObject(request, assetsTypeModel);
		assetsTypeService.updateAssetsTypeModel(assetsTypeModel);
		json.setRtMsg("更新成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/delAssetsType")
	@ResponseBody
	public TeeJson delAssetsType(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeFixedAssetsCategory assetsType = assetsTypeService.getById(sid);
		assetsTypeService.delAssetsType(assetsType);
		
		json.setRtMsg("删除成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/getAssetsType")
	@ResponseBody
	public TeeJson getAssetsType(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeFixedAssetsCategory assetsType = assetsTypeService.getById(sid);
		TeeFixedAssetsCategoryModel model = new TeeFixedAssetsCategoryModel();
		BeanUtils.copyProperties(assetsType, model);
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return assetsTypeService.datagrid(dm, requestDatas);
	}
	

	
}
