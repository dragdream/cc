package com.tianee.oa.subsys.bisengin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.bisengin.bean.BusinessCat;
import com.tianee.oa.subsys.bisengin.service.BusinessCatService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("businessCatController")
public class BusinessCatController {

	@Autowired
	private BusinessCatService businessCatService;
	
	/**
	 * 获取业务分类列表
	 * @param request
	 * @param dm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("datagrid")
	public TeeEasyuiDataGridJson datagrid(HttpServletRequest request,TeeDataGridModel dm){
		BusinessCat cat = new BusinessCat();
		TeeServletUtility.requestParamsCopyToObject(request, cat);
		return businessCatService.datagrid(cat, dm);
	}
	
	
	/**
	 * 删除业务类别
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public TeeJson deleteBusinessCatById(HttpServletRequest request){
		TeeJson json=new TeeJson();
		BusinessCat cat = new BusinessCat();
		TeeServletUtility.requestParamsCopyToObject(request, cat);
		businessCatService.deleteBusinessCatById(cat);
		json.setRtMsg("删除成功");
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据主键获取业务类别详情
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getBusinessCatById")
	public TeeJson getBusinessCatById(HttpServletRequest request){
		TeeJson json=new TeeJson();
		int sid=Integer.parseInt(request.getParameter("sid"));
		BusinessCat cat=businessCatService.getBusinessCatById(sid);
		json.setRtData(cat);
		json.setRtMsg("删除成功");
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 新建或者更新业务分类
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addOrUpdate")
	public TeeJson addOrUpdate(HttpServletRequest request){
		BusinessCat cat = new BusinessCat();
		TeeServletUtility.requestParamsCopyToObject(request, cat);
		TeeJson json=businessCatService.addOrUpdate(cat);
		return json;
	}
	
	/**
	 * 获取业务分类的列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getBusinessCatList")
	public TeeJson getBusinessCatList(HttpServletRequest request){
		TeeJson json=new TeeJson();
		List<BusinessCat> list=businessCatService.getBusinessCatList();
		json.setRtData(list);
		json.setRtState(true);
		return json;
	}
	
}
