package com.tianee.oa.core.base.hr.training.record.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.base.hr.training.record.model.TeeTrainingRecordModel;
import com.tianee.oa.core.base.hr.training.record.service.TeeTrainingRecordService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/trainingRecordController")
public class TeeTrainingRecordController extends BaseController{

	@Autowired
	private TeeTrainingRecordService recordService;
	/**
	 * 新增或者更新
	 * 
	 * @throws ParseException
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , TeeTrainingRecordModel model)
			throws ParseException {
		TeeJson json = recordService.addOrUpdateService(request, model);
		return json;
	}

	/**
	 * 培训计划 list
	 * 
	 * @date 2014-3-18
	 * @author
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getRecordList")
	@ResponseBody
	public TeeEasyuiDataGridJson getRecordList(TeeDataGridModel dm,
			HttpServletRequest request) throws ParseException,
			java.text.ParseException {
		TeeTrainingRecordModel model = new TeeTrainingRecordModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return recordService.getRecordList(dm, request, model);
	}
	
	
	/**
	 * 根据Id  获取培训记录信息
	 * @author syl
	 * @date 2014-6-21
	 * @param request
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request) {
		return recordService.getById(request);
	}
	
	
	/**
	 * 单个删除
	 * @author syl
	 * @date 2014-6-21
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public TeeJson delectById(HttpServletRequest request) {
		return recordService.deleteById(request);
	}
	
	
	/**
	 * 多个删除
	 * @author syl
	 * @date 2014-6-21
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteByIds")
	@ResponseBody
	public TeeJson deleteByIds(HttpServletRequest request) {
		return recordService.deleteByIds(request);
	}
	
}
