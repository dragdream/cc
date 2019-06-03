package com.tianee.oa.core.base.vehicle.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.vehicle.model.TeeVehicleModel;
import com.tianee.oa.core.base.vehicle.service.TeeVehicleService;
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
@RequestMapping("/vehicleManage")
public class TeeVehicleController {
	@Autowired
	private TeeVehicleService vehicleService;
	

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
		TeeVehicleModel model = new TeeVehicleModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = vehicleService.addOrUpdate(request , model);
		return json;
	}
	
	/**
	 * 获取所有会车辆
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getAllVehicle")
	@ResponseBody
	public TeeEasyuiDataGridJson getAllRoom(HttpServletRequest request,TeeDataGridModel dm ) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		TeeVehicleModel model = new TeeVehicleModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = vehicleService.getAllVehicle(request , model,dm);
		return json;
	}
	
	
	
	/**
	 * 获取所有会车辆
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getAllVehicles")
	@ResponseBody
	public TeeJson getAllRoom(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeeVehicleModel model = new TeeVehicleModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = vehicleService.getAllVehicles(request , model);
		return json;
	}
	
	/**
	 * 获取所有会车辆--- 根据申请权限
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/selectPostVehicle")
	@ResponseBody
	public TeeJson selectPostMeetRoom(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		TeeVehicleModel model = new TeeVehicleModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = vehicleService.selectPostVehicle(request , model);
		return json;
	}
	
	
	/**
	 * 删除  byId
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public TeeJson deleteById(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		TeeVehicleModel model = new TeeVehicleModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = vehicleService.deleteById(request , model);
		return json;
	}
	
	
	/**
	 * 删除  所有会议室
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteAll")
	@ResponseBody
	public TeeJson deleteAll(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		TeeVehicleModel model = new TeeVehicleModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = vehicleService.deleteAll(request , model);
		return json;
	}
	
	
	

	/**
	 * 查询 byId
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		TeeVehicleModel model = new TeeVehicleModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = vehicleService.getById(request , model);
		return json;
	}
	
	
	/**
	 * 校验名称的唯一性
	 * @date 2014年7月20日
	 * @author 
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkName")
	@ResponseBody
	public TeeJson checkName(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = vehicleService.checkName(request);
		return json;
	}
	
	/**
	 * 获取车辆调度员
	 * @date 2014年7月20日
	 * @author 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getOperators")
	@ResponseBody
	public TeeJson getOperators(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtData(vehicleService.getOperators(request));
		return json;
	}
	
	/**
	 * 查看指定日期指定车辆的占用情况
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getVehicleUseage")
	@ResponseBody
	public TeeJson getVehicleUseage(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		int vehicleId = TeeStringUtil.getInteger(request.getParameter("vehicleId"), 0);
		String date = TeeStringUtil.getString(request.getParameter("date"));
		json.setRtData(vehicleService.getVehicleUseage(vehicleId,date));
		return json;
	}
}


	