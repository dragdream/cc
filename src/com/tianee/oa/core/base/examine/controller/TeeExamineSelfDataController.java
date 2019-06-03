package com.tianee.oa.core.base.examine.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.examine.model.TeeExamineSelfDataModel;
import com.tianee.oa.core.base.examine.service.TeeExamineSelfDataService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

/**
 * 
 * @author syl
 *
 */
@Controller
@RequestMapping("/TeeExamineSelfDataManage")
public class TeeExamineSelfDataController {
	@Autowired
	private TeeExamineSelfDataService examineSelfDataService;
	

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
		TeeExamineSelfDataModel model = new TeeExamineSelfDataModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = examineSelfDataService.addOrUpdate(request , model);
		return json;
	}

	/**
	 * 根据Id 获取自评数据
	 * @author syl
	 * @date 2014-5-24
	 * @param request
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		TeeExamineSelfDataModel model = new TeeExamineSelfDataModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = examineSelfDataService.getById(request , model);
		return json;
	}
	
	/**
	 * 根据任务Id 获取自评信息
	 * @author syl
	 * @date 2014-5-24
	 * @param request
	 * @return
	 */
	@RequestMapping("/getSelfDataByTask")
	@ResponseBody
	public TeeJson getSelfDataByTask(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		TeeExamineSelfDataModel model = new TeeExamineSelfDataModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = examineSelfDataService.getSelfDataByTask(request , model);
		return json;
	}
	
	
	/**
	 * 获取自评的考核
	 * @author syl
	 * @date 2014-5-24
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCurrUserSelf")
	@ResponseBody
	public TeeJson getNoSelf(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		json = examineSelfDataService.getCurrUserSelf(request );
		return json;
	}
}
