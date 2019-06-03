package com.beidasoft.xzzf.inspection.plan.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.inspection.plan.bean.InspectionStaff;
import com.beidasoft.xzzf.inspection.plan.service.InspectionDeptService;
import com.beidasoft.xzzf.inspection.plan.service.InspectionStaffService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.file.TeeCSVUtil;

@Controller
@RequestMapping("inspectionStaff")
public class InspectionStaffController {
	
	@Autowired
	InspectionStaffService inspectionStaffService;
	@Autowired
	InspectionDeptService inspectionDeptService;
	/**
	 * 导出执法人员CSV文件
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportStaffsCsv")
	public String exportStaffsCsv(HttpServletRequest request,
			HttpServletResponse response,String inspectionId) throws Exception {
		response.setCharacterEncoding("GBK");
		try {
			String fileName = URLEncoder.encode("检查计划执法人员信息.csv", "UTF-8");
			fileName = fileName.replaceAll("\\+", "%20");
			response.setHeader("Cache-control", "private");
			response.setHeader("Cache-Control", "maxage=3600");
			response.setHeader("Pragma", "public");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ fileName + "\"");
			ArrayList<TeeDataRecord> dbL = inspectionStaffService.exportStaffInfo(inspectionId);
			TeeCSVUtil.CVSWrite(response.getWriter(), dbL);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return null;
	}
	/**
	 * 导入检察人员
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/importStaffs")
	@ResponseBody
	public TeeJson importStaffs(HttpServletRequest request,HttpServletResponse response,String inspectionId) throws Exception{
		TeeJson json = inspectionStaffService.importStaff(request,inspectionId);
		return json;
	}
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("deleteStaffs")
	@ResponseBody
	public TeeJson deleteStaffs(HttpServletRequest request,String ids){
		return inspectionStaffService.deleteStaffs(ids);
	}
	
	/**
	 * 保存参与执法检查的人员列表
	 * @param ids
	 * @return
	 */
	@RequestMapping("saveStaff")
	@ResponseBody
	public TeeJson saveStaff(HttpServletRequest request,String ids,String inspectionId){
		return inspectionStaffService.saveStaff(ids,inspectionId);
	}
	/**
	 * 人员列表分页查询
	 * @param dataGridModel
	 * @return
	 */
	@RequestMapping("listByPage")
	@ResponseBody
	public TeeEasyuiDataGridJson listByPage(HttpServletRequest request,TeeDataGridModel dataGridModel,String inspectionId){
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		long total = inspectionStaffService.getTotal(inspectionId);
		List<InspectionStaff> staffList = inspectionStaffService.listByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(),inspectionId);
		json.setRows(staffList);
		json.setTotal(total);
		return json;
	}
	/**
	 * 查询检查计划执行部门信息
	 * @param inspectionId
	 * @return
	 */
	@RequestMapping("getDeptInfo")
	@ResponseBody
	public TeeJson getDeptInfo(HttpServletRequest request,String inspectionId){
		return inspectionDeptService.getDeptInfo(inspectionId);
	}
}
