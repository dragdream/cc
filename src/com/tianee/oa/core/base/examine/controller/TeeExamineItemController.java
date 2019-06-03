package com.tianee.oa.core.base.examine.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.examine.model.TeeExamineItemModel;
import com.tianee.oa.core.base.examine.service.TeeExamineItemService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;



/**
 * 
 * @author syl
 *
 */
@Controller
@RequestMapping("/TeeExamineItemManage")
public class TeeExamineItemController {

	@Autowired
	private TeeExamineItemService  examineGroupService;
	

	/**
	 * @author syl
	 * 新增或者更新
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		TeeExamineItemModel model = new TeeExamineItemModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = examineGroupService.addOrUpdate(request , model);
		return json;
	}
	/**
	 * 删除BYId
	 * @author syl
	 * @date 2014-5-24
	 * @param request
	 * @return
	 */
	@RequestMapping("/delById")
	@ResponseBody
	public TeeJson delById(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		TeeExamineItemModel model = new TeeExamineItemModel();
		//将request中的对应字段映值射到目标对象的属性中
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = examineGroupService.deleteById(request , model);
		return json;
	}
	
	/**
	 * 获取 BYId
	 * @author syl
	 * @date 2014-5-24
	 * @param request
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		TeeExamineItemModel model = new TeeExamineItemModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = examineGroupService.selectById(request , model);
		return json;
	}
	
	
	/**
	 * 根据指标集 获取项目明细
	 * @author syl
	 * @date 2014-5-24
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllByGroupId")
	@ResponseBody
	public TeeJson getAllByGroupId(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		json = examineGroupService.getAllByGroupId(request );
		return json;
	}
	
}
