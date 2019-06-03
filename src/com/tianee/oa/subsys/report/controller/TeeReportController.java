package com.tianee.oa.subsys.report.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.report.model.TeeConditionItemModel;
import com.tianee.oa.subsys.report.model.TeeReportConditionModel;
import com.tianee.oa.subsys.report.model.TeeReportTemplateModel;
import com.tianee.oa.subsys.report.model.TeeTemplateItemModel;
import com.tianee.oa.subsys.report.service.TeeReportService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/report")
public class TeeReportController {
	
	@Autowired
	private TeeReportService reportService;
	
	@ResponseBody
	@RequestMapping("/datagridCondition")
	public TeeEasyuiDataGridJson datagridCondition(HttpServletRequest request,TeeDataGridModel dm){
		return reportService.datagridCondition(TeeStringUtil.getInteger(request.getParameter("flowId"), 0), dm);
	}
	
	@ResponseBody
	@RequestMapping("/saveCondition")
	public TeeJson saveCondition(HttpServletRequest request){
		TeeJson json  = new TeeJson();
		TeeReportConditionModel conditionModel = 
				(TeeReportConditionModel) TeeServletUtility.request2Object(request, TeeReportConditionModel.class);
		
		String listJson = request.getParameter("listJson");
		List<Map<String,String>> jsons = TeeJsonUtil.JsonStr2MapList(listJson);
		List<TeeConditionItemModel> conditionItemModels = new ArrayList<TeeConditionItemModel>();
		for(Map<String,String> data:jsons){
			TeeConditionItemModel m = new TeeConditionItemModel();
			m.setItemId(TeeStringUtil.getInteger(data.get("itemId"), 0));
			m.setOper(data.get("oper"));
			m.setVal(data.get("val"));
			conditionItemModels.add(m);
		}
		reportService.saveCondition(conditionModel, conditionItemModels);
		json.setRtData(conditionModel.getSid());
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/delCondition")
	public TeeJson delCondition(HttpServletRequest request){
		TeeJson json  = new TeeJson();
		int conditionId = TeeStringUtil.getInteger(request.getParameter("conditionId"), 0);
		reportService.delCondition(conditionId);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/updateCondition")
	public TeeJson updateCondition(HttpServletRequest request){
		TeeJson json  = new TeeJson();
		TeeReportConditionModel conditionModel = 
				(TeeReportConditionModel) TeeServletUtility.request2Object(request, TeeReportConditionModel.class);
		conditionModel.setSid(TeeStringUtil.getInteger(request.getParameter("conditionId"), 0));
		String listJson = request.getParameter("listJson");
		List<Map<String,String>> jsons = TeeJsonUtil.JsonStr2MapList(listJson);
		List<TeeConditionItemModel> conditionItemModels = new ArrayList<TeeConditionItemModel>();
		for(Map<String,String> data:jsons){
			TeeConditionItemModel m = new TeeConditionItemModel();
			m.setItemId(TeeStringUtil.getInteger(data.get("itemId"), 0));
			m.setOper(data.get("oper"));
			m.setVal(data.get("val"));
			conditionItemModels.add(m);
		}
		reportService.updateCondition(conditionModel, conditionItemModels);
		json.setRtData(conditionModel.getSid());
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getCondition")
	public TeeJson getCondition(HttpServletRequest request){
		TeeJson json  = new TeeJson();
		int conditionId = TeeStringUtil.getInteger(request.getParameter("conditionId"), 0);
		json.setRtData(reportService.getCondition(conditionId));
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getConditionItems")
	public TeeJson getConditionItems(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int conditionId = TeeStringUtil.getInteger(request.getParameter("conditionId"), 0);
		json.setRtData(reportService.getConditionItems(conditionId));
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/datagridTemplate")
	public TeeEasyuiDataGridJson datagridTemplate(HttpServletRequest request,TeeDataGridModel dm){
		return reportService.datagridTemplate(TeeStringUtil.getInteger(request.getParameter("flowId"), 0), dm);
	}
	
	@ResponseBody
	@RequestMapping("/saveTemplate")
	public TeeJson saveTemplate(HttpServletRequest request){
		TeeJson json  = new TeeJson();
		TeeReportTemplateModel templateModel = 
				(TeeReportTemplateModel) TeeServletUtility.request2Object(request, TeeReportTemplateModel.class);
		
		String listJson = request.getParameter("listJson");
		List<Map<String,String>> jsons = TeeJsonUtil.JsonStr2MapList(listJson);
		List<TeeTemplateItemModel> templateItemModels = new ArrayList<TeeTemplateItemModel>();
		for(Map<String,String> data:jsons){
			TeeTemplateItemModel m = new TeeTemplateItemModel();
			m.setColModel(data.get("colModel"));
			m.setDefName(data.get("defName"));
			m.setItem(data.get("item"));
			m.setWidth(TeeStringUtil.getInteger(data.get("width"), 0));
			templateItemModels.add(m);
		}
		reportService.saveTemplate(templateModel, templateItemModels);
		json.setRtData(templateModel.getSid());
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/updateTemplate")
	public TeeJson updateTemplate(HttpServletRequest request){
		TeeJson json  = new TeeJson();
		TeeReportTemplateModel templateModel = 
				(TeeReportTemplateModel) TeeServletUtility.request2Object(request, TeeReportTemplateModel.class);
		templateModel.setSid(TeeStringUtil.getInteger(request.getParameter("templateId"), 0));
		
		String listJson = request.getParameter("listJson");
		List<Map<String,String>> jsons = TeeJsonUtil.JsonStr2MapList(listJson);
		List<TeeTemplateItemModel> templateItemModels = new ArrayList<TeeTemplateItemModel>();
		for(Map<String,String> data:jsons){
			TeeTemplateItemModel m = new TeeTemplateItemModel();
			m.setColModel(data.get("colModel"));
			m.setDefName(data.get("defName"));
			m.setItem(data.get("item"));
			m.setWidth(TeeStringUtil.getInteger(data.get("width"), 0));
			templateItemModels.add(m);
		}
		reportService.updateTemplate(templateModel, templateItemModels);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getTemplate")
	public TeeJson getTemplate(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int templateId = TeeStringUtil.getInteger(request.getParameter("templateId"), 0);
		json.setRtData(reportService.getTemplate(templateId));
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/delTemplate")
	public TeeJson delTemplate(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int templateId = TeeStringUtil.getInteger(request.getParameter("templateId"), 0);
		reportService.delTemplate(templateId);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getTemplateItems")
	public TeeJson getTemplateItems(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int templateId = TeeStringUtil.getInteger(request.getParameter("templateId"), 0);
		json.setRtData(reportService.getTemplateItems(templateId));
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 列出有查看权限的报表
	 * @param request
	 * @param dm
	 * @return
	 */
	/**
	 * @param request
	 * @param dm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/viewableReports")
	public TeeEasyuiDataGridJson viewableReports(HttpServletRequest request,TeeDataGridModel dm){
		TeePerson loginUser =
				(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData = TeeServletUtility.getParamMap(request);
		return reportService.viewableReports(requestData,dm,loginUser);
	}
	
	/**
	 * 报表数据结果
	 * @param request
	 * @param dm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reportDatas")
	public TeeEasyuiDataGridJson reportDatas(HttpServletRequest request,TeeDataGridModel dm){
		TeePerson loginUser =
				(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData = TeeServletUtility.getParamMap(request);
		
		return reportService.reportDatas(requestData,dm,loginUser);
	}
	
	
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response,TeeDataGridModel dm){
		
		TeePerson loginUser =
				(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData = TeeServletUtility.getParamMap(request);
		
		String titleFields = request.getParameter("titleFields");//标题头字段名
		String titleDescFields = request.getParameter("titleDescFields");//标题头中文字段名
		String reportName = request.getParameter("reportName");//报表名称
		
		TeeEasyuiDataGridJson dataGridJson = reportService.reportDatas(requestData,dm,loginUser);
		List<Map> datas = dataGridJson.getRows();
		
		String titleFieldsSp[] = titleFields.split(",");
		String titleDescFieldsSp[] = titleDescFields.split(",");
		
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("流程报表");
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(1, true);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 12); //字体高度
        font.setColor(HSSFFont.COLOR_NORMAL); //字体颜色
        font.setFontName("黑体"); //字体
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //宽度
        font.setItalic(false); //是否使用斜体
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		style.setFont(font);
		style.setWrapText(false);
		
		//设置表头
		HSSFCell cell = null;
		
		for(int i=0;i<titleDescFieldsSp.length;i++){
			cell = row.createCell((short) (i));
			cell.setCellValue(titleDescFieldsSp[i]);
			cell.setCellStyle(style);
			sheet.setColumnWidth(i, titleDescFieldsSp[i].getBytes().length*2*256);
		}

		
		//创建每一行数据,遍历结果集
		Map data = null;
		for(int i=0;i<datas.size();i++){
			data = datas.get(i);
			row = sheet.createRow((int) i+1);
			for(int j=0;j<titleFieldsSp.length;j++){
				cell = row.createCell((short) (j));
				cell.setCellValue(TeeStringUtil.getString(data.get(titleFieldsSp[j])));
			}
		}
		
		try
		{
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(reportName+".xls", "UTF-8"));
            response.setContentType("application/msexcel;charset=UTF-8");
			OutputStream out=response.getOutputStream();
			wb.write(out);
			out.close();
		}
		catch (Exception e)
		{
			//e.printStackTrace();
		}
	}
}
