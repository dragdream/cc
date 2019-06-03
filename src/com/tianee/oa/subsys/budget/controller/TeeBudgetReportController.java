package com.tianee.oa.subsys.budget.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.budget.service.TeeBudgetReportService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("teeBudgetReportController")
public class TeeBudgetReportController {
	@Autowired
	private TeeBudgetReportService budgetReportService;
	
	
	@RequestMapping("/getPersonalBudget")
	@ResponseBody
	public TeeJson getPersonalBudget(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int year = TeeStringUtil.getInteger(request.getParameter("year"), 0); 
		List<Map> list = budgetReportService.getPersonalBudget(year);
		json.setRtState(true);
		json.setRtData(list);
		return json;
	}
	
	
	@RequestMapping("/getDeptBudget")
	@ResponseBody
	public TeeJson getDeptBudget(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int year = TeeStringUtil.getInteger(request.getParameter("year"), 0); 
		List<Map> list = budgetReportService.getDeptBudget(year);
		json.setRtState(true);
		json.setRtData(list);
		return json;
	}
	
	@RequestMapping("/exportPersonalBudget")
	@ResponseBody
	public void exportPersonalBudget(HttpServletRequest request,HttpServletResponse response) throws Exception{
		int year = TeeStringUtil.getInteger(request.getParameter("year"), 0); 
		List<Map> listMap = budgetReportService.getPersonalBudget(year);
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("公司员工预算执行情况统计");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		
		
	    HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 14); //字体高度
        font.setColor(HSSFFont.COLOR_NORMAL); //字体颜色
        font.setFontName("黑体"); //字体
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //宽度
        font.setItalic(false); //是否使用斜体
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		style.setFont(font);
		
		HSSFFont deptFont = wb.createFont();
		deptFont.setFontHeightInPoints((short) 12); //字体高度
		//deptFont.setColor(HSSFFont.COLOR_NORMAL); //字体颜色
		//deptFont.setFontName("黑体"); //字体
		//deptFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //宽度
		deptFont.setItalic(false); //是否使用斜体
		HSSFCellStyle deptStyle = wb.createCellStyle();
		deptStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		deptStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		deptStyle.setFont(deptFont);
		
		
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCell incomeCell = row.createCell((short) 0);
		incomeCell.setCellValue("公司员工"+year+"年预算执行情况统计");
		incomeCell.setCellStyle(style);
		sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 4));
		//创建收入表头
		row = sheet.createRow((int) 1);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("员工");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("年份");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("预算");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("支出");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("剩余");
		cell.setCellStyle(style);
		
		int k = 1;
		double budgetAmount=0;
		double costAmount=0;
		for (int i = 0; i < listMap.size(); i++)
		{
			double surplus=0;
			Map map = listMap.get(i);
			k++;
			row = sheet.createRow((int) k);
			cell = row.createCell((short) 0);
			cell.setCellValue((String)map.get("USERNAME"));
			cell.setCellStyle(deptStyle);
			row.createCell((short) 1).setCellValue((String)map.get("YEAR"));
			cell = row.createCell((short) 2);
			cell.setCellValue((Double)map.get("AMOUNT"));
			row.createCell((short) 3).setCellValue((Double)map.get("COSTAMOUNT"));
			surplus = (Double)map.get("AMOUNT") - (Double)map.get("COSTAMOUNT");
			row.createCell((short) 4).setCellValue(surplus);
			budgetAmount = budgetAmount+(Double)map.get("AMOUNT");
			costAmount = costAmount+ (Double)map.get("COSTAMOUNT");
		}
		
		row = sheet.createRow((int) k+1);
		cell = row.createCell((short) 0);
		cell.setCellValue("合计");
		cell.setCellStyle(deptStyle);
		cell = row.createCell((short) 1);
		cell.setCellValue("合计");
		cell = row.createCell((short) 2);
		cell.setCellValue(budgetAmount);
		cell = row.createCell((short) 3);
		cell.setCellValue(costAmount);
		cell = row.createCell((short) 4);
		cell.setCellValue(budgetAmount-costAmount);
		sheet.addMergedRegion(new Region(k+1, (short) 0, k+1, (short) 1));
		
		sheet.setDefaultColumnWidth(20);
        
		try
		{
			String fileName ="公司员工"+year+"预算执行情况统计结果.xls";
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType("application/msexcel;charset=UTF-8");
			OutputStream out=response.getOutputStream();
			wb.write(out);
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/exportDeptBudget")
	@ResponseBody
	public void exportDeptBudget(HttpServletRequest request,HttpServletResponse response) throws Exception{
		int year = TeeStringUtil.getInteger(request.getParameter("year"), 0); 
		List<Map> listMap = budgetReportService.getDeptBudget(year);
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("公司各部门预算执行情况统计");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		
		
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 14); //字体高度
		font.setColor(HSSFFont.COLOR_NORMAL); //字体颜色
		font.setFontName("黑体"); //字体
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //宽度
		font.setItalic(false); //是否使用斜体
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		style.setFont(font);
		
		HSSFFont deptFont = wb.createFont();
		deptFont.setFontHeightInPoints((short) 12); //字体高度
		//deptFont.setColor(HSSFFont.COLOR_NORMAL); //字体颜色
		//deptFont.setFontName("黑体"); //字体
		//deptFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //宽度
		deptFont.setItalic(false); //是否使用斜体
		HSSFCellStyle deptStyle = wb.createCellStyle();
		deptStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		deptStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		deptStyle.setFont(deptFont);
		
		
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCell incomeCell = row.createCell((short) 0);
		incomeCell.setCellValue("公司各部门"+year+"年预算执行情况统计");
		incomeCell.setCellStyle(style);
		sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 4));
		//创建收入表头
		row = sheet.createRow((int) 1);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("部门");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("年份");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("预算");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("支出");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("剩余");
		cell.setCellStyle(style);
		
		int k = 1;
		double budgetAmount=0;
		double costAmount=0;
		for (int i = 0; i < listMap.size(); i++)
		{
			double surplus=0;
			Map map = listMap.get(i);
			k++;
			row = sheet.createRow((int) k);
			cell = row.createCell((short) 0);
			cell.setCellValue((String)map.get("DEPTNAME"));
			cell.setCellStyle(deptStyle);
			row.createCell((short) 1).setCellValue((String)map.get("YEAR"));
			cell = row.createCell((short) 2);
			cell.setCellValue((Double)map.get("AMOUNT"));
			row.createCell((short) 3).setCellValue((Double)map.get("COSTAMOUNT"));
			surplus = (Double)map.get("AMOUNT") - (Double)map.get("COSTAMOUNT");
			row.createCell((short) 4).setCellValue(surplus);
			budgetAmount = budgetAmount+(Double)map.get("AMOUNT");
			costAmount = costAmount+ (Double)map.get("COSTAMOUNT");
		}
		
		row = sheet.createRow((int) k+1);
		cell = row.createCell((short) 0);
		cell.setCellValue("合计");
		cell.setCellStyle(deptStyle);
		cell = row.createCell((short) 1);
		cell.setCellValue("合计");
		cell = row.createCell((short) 2);
		cell.setCellValue(budgetAmount);
		cell = row.createCell((short) 3);
		cell.setCellValue(costAmount);
		cell = row.createCell((short) 4);
		cell.setCellValue(budgetAmount-costAmount);
		sheet.addMergedRegion(new Region(k+1, (short) 0, k+1, (short) 1));
		
		sheet.setDefaultColumnWidth(20);
		
		try
		{
			String fileName ="公司各部门"+year+"预算执行情况统计结果.xls";
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			response.setContentType("application/msexcel;charset=UTF-8");
			OutputStream out=response.getOutputStream();
			wb.write(out);
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
