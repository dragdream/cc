package com.tianee.oa.subsys.project.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.project.service.TeeProjectService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/projectController")
public class TeeProjectController {

	@Autowired
	private TeeProjectService projectService;
	
	/**
	 * 新增/编辑项目
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.addOrUpdate(request);
	}
	
	
	/**
	 * 判断 项目编号是否已经存在
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/isExistPNum")
	@ResponseBody
	public TeeJson isExistPNum(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.isExistPNum(request);
	}
	
	
	
	/**
	 * 根据项目状态  获取  立项中  审批中  挂起中 我创建的项目列表
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getProjectListByStatus")
	@ResponseBody
	public TeeEasyuiDataGridJson getProjectListByStatus(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.getProjectListByStatus(request,dm);
	}
	
	
	/**
	 * 获取挂起中的项目    我的项目---挂起中  包含：我属于创建人或者是负责人
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getHangProject")
	@ResponseBody
	public TeeEasyuiDataGridJson getHangProject(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.getHangProject(request,dm);
	}
	
	
	/**
	 * 根据项目状态  获取  办理中   已办结的与我相关的项目列表
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getMyProjectListByStatus")
	@ResponseBody
	public TeeEasyuiDataGridJson getMyProjectListByStatus(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.getMyProjectListByStatus(request,dm);
	}
	/**
	 * 改变项目的状态  例如：撤回
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/changeStatus")
	@ResponseBody
	public TeeJson changeStatus(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.changeStatus(request);
	}
	
	/**
	 * 挂起项目
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/hang")
	@ResponseBody
	public TeeJson hang(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.hang(request);
	}
	
	
	
	/**
	 * 项目审批
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/approveProject")
	@ResponseBody
	public TeeJson approveProject(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.approveProject(request);
	}
	
	
	
	
	
	
	
	/**
	 * 提交项目
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/submitProject")
	@ResponseBody
	public TeeJson submitProject(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.submitProject(request);
	}
	
	
	
	/**
	 *根据项目主键删除项目
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/delByUUid")
	@ResponseBody
	public TeeJson delByUUid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.delByUUid(request);
	}
	
	
	/**
	 *根据项目主键获取详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getInfoByUuid")
	@ResponseBody
	public TeeJson getInfoByUuid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.getInfoByUuid(request);
	}
	
	/**
	 * 获取自定义字段
	 * */
	@RequestMapping("/getZiDingInfoByUuid")
	@ResponseBody
	public TeeJson getZiDingInfoByUuid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.getZiDingInfoByUuid(request);
	}
	
	/**
	 * 完成项目
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/finish")
	@ResponseBody
	public TeeJson finish(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.finish(request);
	}
	
	
	
	/**
	 * 根据项目类型获取项目列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getProjectListByTypeId")
	@ResponseBody
	public TeeJson getProjectListByTypeId(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.getProjectListByTypeId(request);
	}
	
	
	/**
	 * 项目延期
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/delay")
	@ResponseBody
	public TeeJson delay(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.delay(request);
	}
	
	
	/**
	 * 项目汇报
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/report")
	@ResponseBody
	public TeeJson report(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.report(request);
	}
	
	
	/**
	 * 获取已经审批的项目列表
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getHaveApprovedProjectList")
	@ResponseBody
	public TeeEasyuiDataGridJson getHaveApprovedProjectList(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.getHaveApprovedProjectList(request,dm);
	}
	
	
	
	
	
	/**
	 * 获取未审批的项目列表
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getNoApprovedProjectList")
	@ResponseBody
	public TeeEasyuiDataGridJson getNoApprovedProjectList(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.getNoApprovedProjectList(request,dm);
	}
	
	
	/**
	 * 项目查询
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/query")
	@ResponseBody
	public TeeEasyuiDataGridJson query(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.query(request,dm);
	}
	
	
	/**
	 * 判断当前登陆人是不是项目创建者  或者  是项目负责人
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/isCreaterOrManager")
	@ResponseBody
	public TeeJson isCreaterOrManager(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.isCreaterOrManager(request);
	}
	
	
	
	/**
	 * 判断当前用户是不是负责人  创建人   或者项目成员
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/isCreaterOrManagerOrMember")
	@ResponseBody
	public TeeJson isCreaterOrManagerOrMember(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.isCreaterOrManagerOrMember(request);
	}
	
	
	
	/**
	 * 根据项目主键  获取项目的基本信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getBasicInfoBySid")
	@ResponseBody
	public TeeJson getBasicInfoBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.getBasicInfoBySid(request);
	}
	
	
	
	/**
	 * 根据项目主键  获取项目的负责人和项目成员
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getManagerOrMember")
	@ResponseBody
	public TeeJson getManagerOrMember(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.getManagerOrMember(request);
	}
	
	
	/**
	 * 根据项目主键  判断当前登陆人是不是项目的观察者
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/isViewer")
	@ResponseBody
	public TeeJson isViewer(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.isViewer(request);
	}
	
	
	
	/**
	 * 项目文档   获取项目树
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getProjectTree")
	@ResponseBody
	public TeeJson getProjectTree(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.getProjectTree(request);
	}
	
	
	
	/**
	 * 判断当前登录人是不是项目负责人  或者   项目成员
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/isManagerOrMember")
	@ResponseBody
	public TeeJson isManagerOrMember(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.isManagerOrMember(request);
	}
	
	
	
	
	/**
	 * 项目变更
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/projectChange")
	@ResponseBody
	public TeeJson projectChange(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.projectChange(request);
	}
	
	
	

	/**
	 * 恢复挂起
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/restoreHang")
	@ResponseBody
	public TeeJson restoreHang(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectService.restoreHang(request);
	}
	
	@RequestMapping("/getFileNetdisk")
	@ResponseBody
	public TeeJson getFileNetdisk(HttpServletRequest request){	
		return projectService.getFileNetdisk(request);
	}
	
	/**
	 * 获取项目下面的所有子任务
	 * projectController/getTaskAll.action?sid=de30f64f044c493cb4c42d23ef3ad488
	 * */
	@ResponseBody
	@RequestMapping("/getTaskAll")
	public TeeJson getTaskAll(HttpServletRequest request){
		return projectService.getTaskAll(request);
	}
	/**
	 * 获取当前登录人的主管领导
	 * */
	@ResponseBody
	@RequestMapping("/getZhuGuanLingDao")
	public TeeJson getZhuGuanLingDao(HttpServletRequest request){
		return projectService.getZhuGuanLingDao(request);
	}
	
	//获取附件对象
	@ResponseBody
	@RequestMapping("/getFuJianByTaskId")
	public TeeJson getFuJianByTaskId(HttpServletRequest request){
		return projectService.getFuJianByTaskId(request);
	}
}
