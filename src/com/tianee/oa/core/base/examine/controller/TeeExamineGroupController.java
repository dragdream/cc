package com.tianee.oa.core.base.examine.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.examine.model.TeeExamineGroupModel;
import com.tianee.oa.core.base.examine.service.TeeExamineGroupService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;


/**
 * 
 * @author syl
 *
 */
@Controller
@RequestMapping("/TeeExamineGroupManage")
public class TeeExamineGroupController {

	@Autowired
	private TeeExamineGroupService examineGroupService;
	

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
		TeeExamineGroupModel model = new TeeExamineGroupModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		
		String examineReferDiary = TeeStringUtil.getString(request.getParameter("examineReferDiary"),"0");
		String examineReferCalendar = TeeStringUtil.getString(request.getParameter("examineReferCalendar"),"0");
		model.setExamineRefer(examineReferDiary + "," + examineReferCalendar);
		json = examineGroupService.addOrUpdate(request , model);
		return json;
	}
	
	/**
	 *  获取所有记录
	 * @author syl
	 * @date 2014-5-24
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson selectPostVoteManager(TeeDataGridModel dm  , HttpServletRequest request ) throws ParseException {
		TeeExamineGroupModel model = new TeeExamineGroupModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return examineGroupService.datagrid(dm , request , model);
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
		TeeExamineGroupModel model = new TeeExamineGroupModel();
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
		TeeExamineGroupModel model = new TeeExamineGroupModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = examineGroupService.selectById(request , model);
		return json;
	}
	
	/**
	 * 获取登录人有权限申请的 考核指标集
	 * @author syl
	 * @date 2014-5-25
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPostExamine")
	@ResponseBody
	public TeeJson getPostExamine(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		TeeExamineGroupModel model = new TeeExamineGroupModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = examineGroupService.getPostExamine(request , model);
		return json;
	}
	
}
