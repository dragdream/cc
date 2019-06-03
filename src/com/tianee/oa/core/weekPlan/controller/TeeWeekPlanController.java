package com.tianee.oa.core.weekPlan.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.weekPlan.model.TeeWeekPlanModel;
import com.tianee.oa.core.weekPlan.service.TeeWeekPlanService;
import com.tianee.oa.core.xt.model.TeeXTRunModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("teeWeekPlanController")
public class TeeWeekPlanController {
	
	@Autowired
	private TeeWeekPlanService weekPlanService;
	
	/**
	 * 新增或者编辑
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request,TeeWeekPlanModel model){
		TeeJson json=new TeeJson();
		json=weekPlanService.addOrUpdate(request,model);
		return json;
	}
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request){
		TeeJson json=new TeeJson();
		json=weekPlanService.getInfoBySid(request);
		return json;
	}
	
	/**
	 * 根据状态获取列表   0==提交
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getTiJiaoList")
	@ResponseBody
	public TeeEasyuiDataGridJson getTiJiaoList(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return weekPlanService.getTiJiaoList(request,dm);
	}
	
	/**
	 * 获取已发列表  1=发布
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getFaBuList")
	@ResponseBody
	public TeeEasyuiDataGridJson getFaBuList(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return weekPlanService.getFaBuList(request,dm);
	}
	
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public TeeJson delete(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return weekPlanService.delete(request);
	}
	
	
	/**
	 * 查询所有
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/findAll")
	@ResponseBody
	public TeeEasyuiDataGridJson findAll(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return weekPlanService.findAll(request,dm);
	}
	
	
	/**
	 * 领导批注
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/addPiZhu")
	@ResponseBody
	public TeeJson addPiZhu(HttpServletRequest request,TeeWeekPlanModel model){
		TeeJson json=new TeeJson();
		json = weekPlanService.addPiZhu(request,model);
		return json;
	}
	
	
	/**
	 * 发布
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/publishWeekPlan")
	@ResponseBody
	public TeeJson  publishWeekPlan(HttpServletRequest request,TeeWeekPlanModel model){
		TeeJson json=new TeeJson();
		json = weekPlanService.publishWeekPlan(request, model);
		
		return json;
	}
	
	/**
	 * 查询所有部门
	 * @return
	 */
	@RequestMapping("/findAllDept")
	@ResponseBody
	public TeeJson findAllDept() {
		
		TeeJson json=new TeeJson();
		
		return weekPlanService.findAllDept();
		
	}
	
	@RequestMapping("/getTiJiaoListAll")
	@ResponseBody
	public TeeEasyuiDataGridJson getTiJiaoListAll(HttpServletRequest request,TeeDataGridModel dm) {
		return weekPlanService.getTiJiaoListAll(request,dm);
		
	}

}
