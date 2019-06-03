package com.tianee.oa.subsys.supervise.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.email.model.TeeEmailModel;
import com.tianee.oa.subsys.supervise.model.TeeSupervisionModel;
import com.tianee.oa.subsys.supervise.service.TeeSupervisionService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/supervisionController")
public class TeeSupervisionController {

	@Autowired
	private TeeSupervisionService supService;
	
	
	/**
	 * 新建/编辑
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 * @throws IOException 
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException {	
		return supService.addOrUpdate(request);
	}
	
	
	/**
	 * 手机端新建/编辑
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 * @throws IOException 
	 */
	@RequestMapping("/mobileAddOrUpdate")
	@ResponseBody
	public TeeJson mobileAddOrUpdate(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException {	
		return supService.mobileAddOrUpdate(request);
	}
	
	
	/**
	 * 根据分类主键  获取分类下的督办任务列表
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getSupervisionListByTypeId")
	@ResponseBody
	public TeeEasyuiDataGridJson getSupervisionListByTypeId(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {
		return supService.getSupervisionListByTypeId(dm,request);
	}
	
	
	/**
	 * 
	 * 根据主键进行删除操作
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/delBySid")
	@ResponseBody
	public TeeJson delBySid(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException {	
		return supService.delBySid(request);
	}
	
	
	/**
	 * 发布督办任务
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/publish")
	@ResponseBody
	public TeeJson publish(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException {	
		return supService.publish(request);
	}
	
	
	/**
	 * 根据主键  获取督办任务详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException {	
		return supService.getInfoBySid(request);
	}
	
	
	/**
	 * 根据任务主键  获取任务状态 和当前登陆人在该任务中的角色
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/getStatusAndRole")
	@ResponseBody
	public TeeJson getStatusAndRole(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException {	
		return supService.getStatusAndRole(request);
	}
	
	
	
	/**
	 * 签收任务
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/receive")
	@ResponseBody
	public TeeJson receive(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException {	
		return supService.receive(request);
	}
	
	
	/**
	 * 根据状态   获取我的督办任务
	 * @param dm
	 * @param request
	 * @return
	 * @throws java.text.ParseException
	 * @throws Exception 
	 */
	@RequestMapping("/getMySupListByStatus")
	@ResponseBody
	public TeeEasyuiDataGridJson getMySupListByStatus(TeeDataGridModel dm, HttpServletRequest request) throws Exception {
		return supService.getMySupListByStatus(dm,request);
	}
	
	
	
	/**
	 * 督办任务统计  -------按部门
	 * @param dm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSupCountByDept")
	@ResponseBody
	public TeeEasyuiDataGridJson getSupCountByDept(TeeDataGridModel dm, HttpServletRequest request) throws Exception {
		return supService.getSupCountByDept(dm,request);
	}
	
	
	
	/**
	 * 督办任务统计-------- 按类别
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/getSupCountByType")
	@ResponseBody
	public List getSupCountByType(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws Exception {	
		return supService.getSupCountByType(request);
	}
	
	
	
	/**
	 * 督办任务统计  -------按状态
	 * @param dm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSupCountByStatus")
	@ResponseBody
	public TeeEasyuiDataGridJson getSupCountByStatus(TeeDataGridModel dm, HttpServletRequest request) throws Exception {
		return supService.getSupCountByStatus(dm,request);
	}
}
