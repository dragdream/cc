package com.tianee.oa.core.base.pm.controller;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.pm.model.TeeHumanTrainModel;
import com.tianee.oa.core.base.pm.service.TeeHumanTrainService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("TeeHumanTrainController")
public class TeeHumanTrainController {
	
	@Autowired
	private TeeHumanTrainService trainService;
	
	@RequestMapping("/addHumanTrain")
	@ResponseBody
	public TeeJson addHumanTrain(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanTrainModel humanTrain = new TeeHumanTrainModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanTrain);
		trainService.addHumanTrain(humanTrain);
		json.setRtMsg("添加成功");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return trainService.datagird(dm, requestDatas);
	}
	
	@RequestMapping("/getModelById")
	@ResponseBody
	public TeeJson getModelById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(trainService.getModelById(sid));
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/updateHumanTrain")
	@ResponseBody
	public TeeJson updateHumanTrain(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanTrainModel humanTrainModel = new TeeHumanTrainModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanTrainModel);
		trainService.updateHumanTrain(humanTrainModel);
		json.setRtState(true);
		json.setRtMsg("更新成功");
		return json;
	}
	
	
	@RequestMapping("/delHumanTrain")
	@ResponseBody
	public TeeJson delHumanTrain(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		trainService.delHumanTrain(sid);
		json.setRtState(true);
		json.setRtMsg("删除成功");
		return json;
	}
	
}
