package com.tianee.oa.subsys.project.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.project.model.TeeTaskModel;
import com.tianee.oa.subsys.project.service.TeeTaskService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/taskController")
public class TeeTaskController {

	@Autowired
	private TeeTaskService taskService;
	
	
	
	/**
	 * 根据项目主键  获取项目下  除自己以外的其他的任务
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getOtherTasksByProjectId")
	@ResponseBody
	public TeeJson getOtherTasksByProjectId(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return taskService.getOtherTasksByProjectId(request);
	}
	
	
	/**
	 * 新增/编辑
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , HttpServletResponse response,TeeTaskModel model) throws ParseException {	
		return taskService.addOrUpdate(request,model);
	}
	
	
	
	/**
	 * 根据项目主键获取任务列表    (分父任务  子任务)
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getTaskListByProjectId")
	@ResponseBody
	public List getTaskListByProjectId(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return taskService.getTaskListByProjectId(request,dm);
	}
	
	
	
	/**
	 * 单纯的获取项目的所有任务
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getTasksByProjectId")
	@ResponseBody
	public TeeJson getTasksByProjectId(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return taskService.getTasksByProjectId(request,dm);
	}
	
	
	
	
	/**
	 * 根据主键获取任务详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return taskService.getInfoBySid(request);
	}
	
	
	/**
	 * 删除任务
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/delBySid")
	@ResponseBody
	public TeeJson delBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return taskService.delBySid(request);
	}
	
	
	
	
	/**
	 * 根据任务状态  获取我的任务列表  0 进行中  1已完成
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getTaskListByStatus")
	@ResponseBody
	public TeeEasyuiDataGridJson getTaskListByStatus(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return taskService.getTaskListByStatus(request,dm);
	}
	
	
	
	/**
	 * 结束任务
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/end")
	@ResponseBody
	public TeeJson end(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return taskService.end(request);
	}
	
	/**
	 * 根据任务主键  获取与之相关的流程
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getFlowTypeListByTaskId")
	@ResponseBody
	public TeeJson getFlowTypeListByTaskId(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return taskService.getFlowTypeListByTaskId(request);
	}
	
	
	/**
	 * 设置任务开始办理时间
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/begin")
	@ResponseBody
	public TeeJson begin(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return taskService.begin(request);
	}
	
	
	
	/**
	 * 判断当前任务是不是父任务  或者  前置任务
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/isHigherOrPre")
	@ResponseBody
	public TeeJson isHigherOrPre(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return taskService.isHigherOrPre(request);
	}
	
	
	
	/**
	 * 甘特图
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/gantt")
	@ResponseBody
	public List<Map> gantt(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return taskService.gantt(request);
	}
	
	/**
	 * 获取任务的类型
	 * */
	@ResponseBody
	@RequestMapping("/getTaskTypeById")
	public TeeJson getTaskTypeById(HttpServletRequest request){
		return taskService.getTaskTypeById(request);
	}
	
}
