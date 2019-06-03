package com.tianee.oa.core.base.examine.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.examine.model.TeeExamineTaskModel;
import com.tianee.oa.core.base.examine.service.TeeExamineTaskService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.file.TeeCSVUtil;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;



/**
 * 
 * @author syl
 *
 */
@Controller
@RequestMapping("/TeeExamineTaskManage")
public class TeeExamineTaskController {

	@Autowired
	private TeeExamineTaskService  examineGroupService;
	

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
		TeeExamineTaskModel model = new TeeExamineTaskModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = examineGroupService.addOrUpdate(request , model);
		return json;
	}
	
	
	/**
	 *  通用列表
	 * @author syl
	 * @date 2014-5-24
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm  , HttpServletRequest request ) throws ParseException {
		TeeExamineTaskModel model = new TeeExamineTaskModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return examineGroupService.datagrid(dm , request , model);
	}
	
	/**
	 * 查询 
	 * @author syl
	 * @date 2014-5-25
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/queryDatagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson queryDatagrid(TeeDataGridModel dm  , HttpServletRequest request ) throws ParseException {
		TeeExamineTaskModel model = new TeeExamineTaskModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return examineGroupService.queryDatagrid(dm , request , model);
	}
	
	/**
	 * 删除BYId
	 * @author syl
	 * @date 2014-5-24
	 * @param request
	 * @return
	 */
	@RequestMapping("/delById")
	@ResponseBody
	public TeeJson delById(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		TeeExamineTaskModel model = new TeeExamineTaskModel();
		//将request中的对应字段映值射到目标对象的属性中
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = examineGroupService.deleteById( model);
		return json;
	}
	
	/**
	 * 获取 BYId
	 * @author syl
	 * @date 2014-5-24
	 * @param request
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		TeeExamineTaskModel model = new TeeExamineTaskModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = examineGroupService.selectById(request , model);
		return json;
	}
	
	
	/**
	 * 更新状态  ---及开始时间和结束时间
	 * @author syl
	 * @date 2014-5-29
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateState")
	@ResponseBody
	public TeeJson updateState(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		json = examineGroupService.updateState(request );
		return json;
	}
	
	/**
	 * 获取被考核人信息
	 * @author syl
	 * @date 2014-5-29
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectParticipantById")
	@ResponseBody
	public TeeJson selectParticipantById(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		TeeExamineTaskModel model = new TeeExamineTaskModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = examineGroupService.selectParticipantById(request , model);
		return json;
	}
	
	/**
	 * 根据考核任务   查询考核情况
	 * @author syl
	 * @date 2014-5-30
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryExamineInfo")
	
	public String queryExamineInfo(HttpServletRequest request ) {
		TeeExamineTaskModel model = new TeeExamineTaskModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		List<Map> list  = examineGroupService.queryExamineInfo(request , model);
		request.setAttribute("data", list);
		 return "/system/core/base/examine/manage/examineInfo.jsp";
	}
	
	/**
	 * 考核人  -- 查阅
	 * @author syl
	 * @date 2014-5-31
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryExamineDetail")
	@ResponseBody
	public TeeJson queryExamineDetail(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		TeeExamineTaskModel model = new TeeExamineTaskModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map list  = examineGroupService.queryExamineDetail(request , model);
		json.setRtState(true);
		json.setRtData(list);
		return json;
	}
	
	/**
	 * 导出总分
	 * @author syl
	 * @date 2014-5-31
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportTotalToCsv")
	public String exportTotalToCsv(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("GBK");
		try {
			String fileName = URLEncoder.encode("考核总分.csv", "UTF-8");
			fileName = fileName.replaceAll("\\+", "%20");
			response.setHeader("Cache-control", "private");
			response.setHeader("Cache-Control", "maxage=3600");
			response.setHeader("Pragma", "public");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ fileName + "\"");
			ArrayList<TeeDataRecord> dbL = examineGroupService.exportExamineTotal( request);
			TeeCSVUtil.CVSWrite(response.getWriter(), dbL);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return null;
	}
	/**
	 * 导出比分明细
	 * @author syl
	 * @date 2014-5-31
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportDetailToCsv")
	public String exportDetailToCsv(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("GBK");
		try {
			String fileName = URLEncoder.encode("考核总分明细.csv", "UTF-8");
			fileName = fileName.replaceAll("\\+", "%20");
			response.setHeader("Cache-control", "private");
			response.setHeader("Cache-Control", "maxage=3600");
			response.setHeader("Pragma", "public");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ fileName + "\"");
			ArrayList<TeeDataRecord> dbL = examineGroupService.exportDetailToCsv( request);
			TeeCSVUtil.CVSWrite(response.getWriter(), dbL);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return null;
	}
	
	/**
	 * 获取任务考核权重列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getTaskWeightList")
	@ResponseBody
	public TeeJson getTaskWeightList(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int taskId = TeeStringUtil.getInteger(request.getParameter("taskId"), 0);
		json.setRtData(examineGroupService.getTaskWeightList(taskId));
		return json;
	}
	
	/**
	 * 获取任务考核权重列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateTaskWeight")
	@ResponseBody
	public TeeJson updateTaskWeight(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int taskId = TeeStringUtil.getInteger(request.getParameter("taskId"), 0);
		String weightModel = TeeStringUtil.getString(request.getParameter("weightModel"));
		examineGroupService.updateTaskWeightList(taskId, weightModel);
		return json;
	}
	
}
