package com.tianee.oa.subsys.bisengin.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tianee.oa.subsys.bisengin.model.BisCategoryModel;
import com.tianee.oa.subsys.bisengin.service.BisCatService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("bisCat")
public class BisCatController {
	@Autowired
	private BisCatService bisCatService;
	
	/**
	 * 增加业务分类
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addBisCat")
	public TeeJson addBisCat(HttpServletRequest request){
		TeeJson json = new TeeJson();
		BisCategoryModel categoryModel = new BisCategoryModel();
		TeeServletUtility.requestParamsCopyToObject(request, categoryModel);
		bisCatService.addBisCat(categoryModel);
		json.setRtState(true);
		json.setRtMsg("添加成功");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("getModelById")
	public TeeJson getModelById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtState(true);
		json.setRtData(bisCatService.getModelById(sid));
		return json;
	}
	
	/**
	 * 更新业务分类
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateBisCat")
	public TeeJson updateBisCat(HttpServletRequest request){
		TeeJson json = new TeeJson();
		BisCategoryModel categoryModel = new BisCategoryModel();
		TeeServletUtility.requestParamsCopyToObject(request, categoryModel);
		bisCatService.updateBisCat(categoryModel);
		json.setRtState(true);
		json.setRtMsg("编辑成功");
		return json;
	}
	
	/**
	 * 删除业务分类
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteBisCat")
	public TeeJson deleteBisCat(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		bisCatService.deleteBisCat(sid);
		json.setRtMsg("删除成功");
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("datagrid")
	public TeeEasyuiDataGridJson datagrid(HttpServletRequest request,TeeDataGridModel dm){
		BisCategoryModel categoryModel = new BisCategoryModel();
		TeeServletUtility.requestParamsCopyToObject(request, categoryModel);
		return bisCatService.datagrid(categoryModel, dm);
	}

	public void setBisCatService(BisCatService bisCatService) {
		this.bisCatService = bisCatService;
	}
	
}
