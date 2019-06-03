package com.tianee.oa.core.base.exam.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.exam.bean.TeeExamStore;
import com.tianee.oa.core.base.exam.model.TeeExamStoreModel;
import com.tianee.oa.core.base.exam.service.TeeExamStoreService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("TeeExamStoreController")
public class TeeExamStoreController {
	@Autowired
	TeeExamStoreService examStoreService;
	
	@RequestMapping("/addExamStore")
	@ResponseBody
	public TeeJson addExamStore(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeExamStoreModel examStoreModel = new TeeExamStoreModel();
		TeeServletUtility.requestParamsCopyToObject(request, examStoreModel);
		examStoreService.addExamStoreModel(examStoreModel);
		json.setRtState(true);
		json.setRtMsg("添加成功");
		return json;
	}
	
	@RequestMapping("/editExamStore")
	@ResponseBody
	public TeeJson editExamStore(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeExamStoreModel examStoreModel = new TeeExamStoreModel();
		TeeServletUtility.requestParamsCopyToObject(request, examStoreModel);
		examStoreService.updateExamStoreModel(examStoreModel);
		json.setRtMsg("更新成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/delExamStore")
	@ResponseBody
	public TeeJson delExamStore(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeExamStore examStore = examStoreService.getById(sid);
		examStoreService.deleteExamStore(examStore);
		
		json.setRtMsg("删除成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/getExamStore")
	@ResponseBody
	public TeeJson getExamStore(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeExamStore examStore = examStoreService.getById(sid);
		TeeExamStoreModel model = new TeeExamStoreModel();
		BeanUtils.copyProperties(examStore, model);
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return examStoreService.datagrid(dm, requestDatas);
	}
	
	
	
	/**
	 * 获取所有的题库信息 
	 * 不分页
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllExamStore")
	@ResponseBody
	public TeeJson getAllExamStore(HttpServletRequest request){	
		return examStoreService.getAllExamStore(request);
	}
	
}
