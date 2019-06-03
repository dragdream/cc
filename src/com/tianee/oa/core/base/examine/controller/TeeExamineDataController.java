package com.tianee.oa.core.base.examine.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.examine.model.TeeExamineDataModel;
import com.tianee.oa.core.base.examine.model.TeeExamineTaskModel;
import com.tianee.oa.core.base.examine.service.TeeExamineDataService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

/**
 * 
 * @author syl
 *
 */
@Controller
@RequestMapping("/TeeExamineDataManage")
public class TeeExamineDataController {
	@Autowired
	private TeeExamineDataService examineDataService;
	

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
		TeeExamineDataModel model = new TeeExamineDataModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = examineDataService.addOrUpdate(request , model);
		return json;
	}	
	
	/**
	 * 获取有权限的考核任务
	 * @author syl
	 * @date 2014-5-24
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPostExamineTask")
	@ResponseBody
	public TeeEasyuiDataGridJson queryDatagrid(TeeDataGridModel dm  , HttpServletRequest request ) throws ParseException {
		TeeExamineTaskModel model = new TeeExamineTaskModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return examineDataService.getPostExamineTask(dm , request , model);
	}
	
	/**
	 * 获取考核数据
	 * @author syl
	 * @date 2014-5-28
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getSelfData")
	@ResponseBody
	public TeeJson getSelfData(HttpServletRequest request){
		return examineDataService.getSelfData(request );
	}
	
	
	
}
