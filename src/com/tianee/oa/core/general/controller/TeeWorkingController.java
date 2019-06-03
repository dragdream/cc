package com.tianee.oa.core.general.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

@Controller
@RequestMapping("/working")
public class TeeWorkingController {
	
	@RequestMapping("/taskList")
	public TeeEasyuiDataGridJson taskList(TeeDataGridModel dm,HttpServletRequest request){
		return null;
	}
	
	@RequestMapping("/productList")
	public TeeEasyuiDataGridJson productList(TeeDataGridModel dm,HttpServletRequest request){
		return null;
	}
	
	@RequestMapping("/vehicleList")
	public TeeEasyuiDataGridJson vehicleList(TeeDataGridModel dm,HttpServletRequest request){
		return null;
	}
	
	@RequestMapping("/attendDutyList")
	public TeeEasyuiDataGridJson attendDutyList(TeeDataGridModel dm,HttpServletRequest request){
		return null;
	}
	
	@RequestMapping("/meetingList")
	public TeeEasyuiDataGridJson meetingList(TeeDataGridModel dm,HttpServletRequest request){
		return null;
	}
	
	@RequestMapping("/emailList")
	public TeeEasyuiDataGridJson emailList(TeeDataGridModel dm,HttpServletRequest request){
		return null;
	}
	
	@RequestMapping("/workFlowList")
	public TeeEasyuiDataGridJson workFlowList(TeeDataGridModel dm,HttpServletRequest request){
		return null;
	}
	
	
	
}
