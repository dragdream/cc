package com.beidasoft.xzzf.inspection.plan.controller;

import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.inspection.plan.bean.InspectionRegion;
import com.beidasoft.xzzf.inspection.plan.bean.InspectionWeight;
import com.beidasoft.xzzf.inspection.plan.service.InspectionOrgService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.file.TeeCSVUtil;

@Controller
@RequestMapping("inspectionOrg")
public class InspectionOrgController {
	@Autowired
	private InspectionOrgService inspectionOrgService;
	/**
	 * 导出被检查企业CSV文件
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportOrgCsv")
	public String exportOrgCsv(HttpServletRequest request,
			HttpServletResponse response,String inspectionId) throws Exception {
		response.setCharacterEncoding("GBK");
		try {
			String fileName = URLEncoder.encode("被检查企业.csv", "UTF-8");
			fileName = fileName.replaceAll("\\+", "%20");
			response.setHeader("Cache-control", "private");
			response.setHeader("Cache-Control", "maxage=3600");
			response.setHeader("Pragma", "public");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ fileName + "\"");
			ArrayList<TeeDataRecord> dbL = inspectionOrgService.exportOrgInfo(inspectionId);
			TeeCSVUtil.CVSWrite(response.getWriter(), dbL);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return null;
	}
	/**
	 * 导入被检查企业
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/importOrgs")
	@ResponseBody
	public TeeJson importOrgs(HttpServletRequest request,HttpServletResponse response,String inspectionId) throws Exception{
		TeeJson json = inspectionOrgService.importOrg(request,inspectionId);
		return json;
	}
	
	/**
	 * 保存所有筛选条件并筛选出企业列表
	 * @param inspectionRegion
	 * @param inspectionWeight
	 * @param conditionJson
	 * @return TeeJson
	 */
	@RequestMapping("saveWeightAndCondition")
	@ResponseBody
	public TeeJson saveWeightAndCondition(HttpServletRequest request,InspectionWeight inspectionWeight,String conditionJson){
		return inspectionOrgService.saveWeightAndCondition(inspectionWeight,conditionJson);
	}
	
	/**
	 * 分页查询被检企业列表
	 * @param inspectionWeight
	 * @param conditionJson
	 * @param areaJson
	 * @return
	 */
	@RequestMapping("orgListByPage")
	@ResponseBody
	public TeeEasyuiDataGridJson orgListByPage(HttpServletRequest request,String inspectionId,TeeDataGridModel dataGridModel){
		return inspectionOrgService.orgListByPage(inspectionId,dataGridModel.getFirstResult(),dataGridModel.getRows());
	}
	/**
	 * 根据检查计划主表id 查询所有筛选条件
	 * @param inspectionId
	 * @return
	 */
	@RequestMapping("findAllCondition")
	@ResponseBody
	public TeeJson findAllCondition(HttpServletRequest request,String inspectionId){
		return inspectionOrgService.findAllCondition(inspectionId);
	}
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	@RequestMapping("delById")
	@ResponseBody
	public TeeJson delById(HttpServletRequest request,String id){
		return inspectionOrgService.delById(id);
	}
	/**
	 * 批量新增
	 * @return
	 */
	@RequestMapping("batchSave")
	@ResponseBody
	public TeeJson batchSave(HttpServletRequest request,String orgIds,String inspectionId){
		return inspectionOrgService.batchSave(orgIds,inspectionId);
	}
}
