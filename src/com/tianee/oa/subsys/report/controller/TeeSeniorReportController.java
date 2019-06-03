package com.tianee.oa.subsys.report.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.report.model.TeeSeniorReportTemplateModel;
import com.tianee.oa.subsys.report.service.TeeSeniorReportService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/seniorReport")
public class TeeSeniorReportController {
	
	@Autowired
	private TeeSeniorReportService seniorReportService;
	
	@ResponseBody
	@RequestMapping("/addReport")
	public TeeJson addReport(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeSeniorReportTemplateModel seniorReportTemplateModel = 
				(TeeSeniorReportTemplateModel) TeeServletUtility.request2Object(request, TeeSeniorReportTemplateModel.class);
		
		seniorReportService.addReport(seniorReportTemplateModel);
		json.setRtState(true);
		json.setRtData(seniorReportTemplateModel.getUuid());
		json.setRtMsg("保存成功");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/updateReport")
	public TeeJson updateReport(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeSeniorReportTemplateModel seniorReportTemplate = 
				(TeeSeniorReportTemplateModel) TeeServletUtility.request2Object(request, TeeSeniorReportTemplateModel.class);
		
		seniorReportService.updateReport(seniorReportTemplate);
		json.setRtState(true);
		json.setRtMsg("保存成功");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/delReport")
	public TeeJson delReport(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
		seniorReportService.delReport(uuid);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getReport")
	public TeeJson getReport(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
		json.setRtData(seniorReportService.getReport(uuid));
		return json;
	}
	
	/**
	 * 批量设置
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/batchSettings")
	public TeeJson batchSettings(HttpServletRequest request){
		TeeJson json  = new TeeJson();
		String ids = TeeStringUtil.getString(request.getParameter("ids"));
		String userPrivs = TeeStringUtil.getString(request.getParameter("userPrivs"));
		String deptPrivs = TeeStringUtil.getString(request.getParameter("deptPrivs"));
		seniorReportService.batchSettings(ids, userPrivs, deptPrivs);
		json.setRtState(true);
		json.setRtMsg("设置成功");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(HttpServletRequest request,TeeDataGridModel dm){
		Map param = TeeServletUtility.getParamMap(request);
		return seniorReportService.datagrid(param, dm);
	}
	
	@ResponseBody
	@RequestMapping("/datagridViews")
	public TeeEasyuiDataGridJson datagridViews(HttpServletRequest request,TeeDataGridModel dm){
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map param = TeeServletUtility.getParamMap(request);
		param.put(TeeConst.LOGIN_USER,loginUser);
		return seniorReportService.datagridViews(param, dm);
	}
	
	@ResponseBody
	@RequestMapping("/reportDatas")
	public TeeJson reportDatas(HttpServletRequest request,TeeDataGridModel dm){
		TeeJson json = new TeeJson();
		Map param = TeeServletUtility.getParamMap(request);
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		param.put(TeeConst.LOGIN_USER,loginUser);
		json.setRtData(seniorReportService.reportDatas(param,dm));
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/changeStatus")
	public TeeJson changeStatus(HttpServletRequest request){
		TeeJson json = new TeeJson();
		Map param = TeeServletUtility.getParamMap(request);
		String ids = TeeStringUtil.getString(request.getParameter("ids"));
		int status = TeeStringUtil.getInteger(request.getParameter("status"), 0);
		seniorReportService.changeStatus(ids, status);
		json.setRtState(true);
		return json;
	}
	
	
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response){
		String tplName = request.getParameter("tplName");
		String group = request.getParameter("group");
		String reverse = request.getParameter("reverse");
		
		//获取表头序列
		String headerSeq = request.getParameter("headerSeq");
		JSONArray headerSeqArray = JSONArray.fromObject(headerSeq);
		
		//获取分组列序列
		String columnSeq = request.getParameter("columnSeq");
		JSONArray columnSeqArray = JSONArray.fromObject(columnSeq);
		
		//获取body数据体
		String bodyData = request.getParameter("bodyData");
		JSONArray bodyDataArray = JSONArray.fromObject(bodyData);
		
		//获取footer
		String footerSeq = request.getParameter("footerSeq");
		JSONArray footerSeqArray = JSONArray.fromObject(footerSeq);
		
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("报表统计");
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
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue(group);//分组名称
		cell.setCellStyle(style);
		
		for(int i=0;i<headerSeqArray.size();i++){
			cell = row.createCell((short) (i+1));
			cell.setCellValue(headerSeqArray.getString(i));
			cell.setCellStyle(style);
			sheet.setColumnWidth(i+1, headerSeqArray.getString(i).getBytes().length*2*256);
		}

		
		//创建每一行数据
		JSONArray doubleDataArray = null;
		for(int i=0;i<bodyDataArray.size();i++){
			doubleDataArray = bodyDataArray.getJSONArray(i);
			row = sheet.createRow((int) i+1);
			cell = row.createCell((short) 0);
			cell.setCellValue(columnSeqArray.getString(i));
			for(int j=0;j<doubleDataArray.size();j++){
				cell = row.createCell((short) (j+1));
				cell.setCellValue(doubleDataArray.getString(j));
			}
		}
		
		row = sheet.createRow(1+bodyDataArray.size());
		
		
		//获取数据脚统计
		for(int i=0;i<footerSeqArray.size();i++){
			if("0".equals(reverse)){//
				cell = row.createCell((short) (i+1));
			}else{
				//获取
				row = sheet.getRow(i+1);
				cell = row.createCell((short) (headerSeqArray.size()+2));
			}
			cell.setCellValue(footerSeqArray.getString(i));
		}
		
//		for (int i = 0; i < list.size(); i++)
//		{
//			row = sheet.createRow((int) i + 1);
//			TeeSysLogModel model = (TeeSysLogModel) list.get(i);
//			// 第四步，创建单元格，并设置值
//			row.createCell((short) 0).setCellValue(model.getUserName());
//			row.createCell((short) 1).setCellValue(model.getIp());
//			row.createCell((short) 2).setCellValue(model.getType());
//			cell = row.createCell((short) 3);
//			cell.setCellValue(model.getTimeDesc());
//			row.createCell((short) 4).setCellValue(model.getRemark());
//		}
		
		
	//设置每一列的宽度
//        sheet.setDefaultColumnWidth(20);
 /*       sheet.autoSizeColumn((short)0);
        sheet.autoSizeColumn((short)1); //调整第一列宽度
        sheet.autoSizeColumn((short)2); //调整第二列宽度
        sheet.autoSizeColumn((short)3); //调整第三列宽度
        sheet.autoSizeColumn((short)4); //调整第四列宽度       
*/		// 第六步，将文件存到指定位置
		try
		{
//			Calendar cal = Calendar.getInstance();
//			int year=cal.get(Calendar.YEAR);
//			int month=cal.get(Calendar.MONTH)+1;
//			int day=cal.get(Calendar.DAY_OF_MONTH);
//			String days ="";
//			String months="";
//			if(month<10){
//				months="0"+month;
//			}else{
//				months=""+month;
//			}
//			if(day<10){
//				days="0"+day;
//			}else{
//				days=""+day;
//			}
//			String times = ""+year+""+months+""+days;
//			String fileName = "sysLog"+times+".xls";
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(tplName+".xls", "UTF-8"));
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
	
	
	
	
	@RequestMapping(value = "/export")
	 public void exportXml(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 String uuid = request.getParameter("uuid");
		 TeeSeniorReportTemplateModel report = seniorReportService.getReport(uuid);
		 response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("报表_"+report.getTplName(),"UTF-8")+".xml");
		OutputStream output = response.getOutputStream();
		String sb = seniorReportService.exportXml(uuid);
		output.write(sb.getBytes("UTF-8"));
	 }
	 
	 @RequestMapping(value = "/import")
	 public void importXml(HttpServletRequest request,HttpServletResponse response) throws Exception {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multiRequest.getFile("file");
			InputStream inputstream = file.getInputStream();
			seniorReportService.importXml(inputstream);
			PrintWriter pw = response.getWriter();
			pw.write("<script>parent.uploadSuccess();</script>");
	 }
}
