package com.tianee.oa.core.general.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.exam.bean.TeeExamInfo;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.general.model.TeeSysLogModel;
import com.tianee.oa.core.general.service.TeeSysLogService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;


















import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


@RequestMapping("/sysLogManage")
public class TeeSysLogController {
	@Autowired
	TeeSysLogService logService;
	
	
	/**
	 * 根据系统当前登录人相关获取日志
	 * @param request
	 * @return
	 */
	@RequestMapping("/getLogByLoginPerson")
	@ResponseBody
	public TeeJson getLogByLoginPerson(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		try {
			int count = TeeStringUtil.getInteger(request.getParameter("count"),10);
			String type = TeeStringUtil.getString(request.getParameter("type"),"");
			List<TeeSysLog> list = logService.getLogByLoginPerson(person,count, type);
			json.setRtData(list);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			json.setRtMsg("查询日志失败");
		}
		return json;
	}


	public void setLogService(TeeSysLogService logService) {
		this.logService = logService;
	}
	
	
	
	
	
	
	@RequestMapping("/getSummaryLogInfo")
	@ResponseBody
	public TeeJson getSummaryLogInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		Map map = logService.getSummaryLogInfo();
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	@RequestMapping("/getLast10LogInfo")
	@ResponseBody
	public TeeJson getLast10LogInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		List<TeeSysLogModel> list = logService.getLast10LogInfo();
		json.setRtData(list);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/getLogInfoByYear")
	@ResponseBody
	public TeeJson getLogInfoByYear(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String year = TeeStringUtil.getString(request.getParameter("year"),"0");
		List map = logService.getLogInfoByYear(year);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	
	@RequestMapping("/getGraphDataByYearAndMonth")
	@ResponseBody
	public TeeJson getGraphDataByYearAndMonth(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String year = TeeStringUtil.getString(request.getParameter("year"),"0");
		String month = TeeStringUtil.getString(request.getParameter("month"),"0");
		Map map = logService.getGraphDataByYearAndMonth(year,month);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/getEverydayByYearAndMonth")
	@ResponseBody
	public TeeJson getEverydayByYearAndMonth(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String year = TeeStringUtil.getString(request.getParameter("year"),"0");
		String month = TeeStringUtil.getString(request.getParameter("month"),"0");
		Map map = logService.getEverydayByYearAndMonth(year,month);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	
	@RequestMapping("/getGraphDataByTimes")
	@ResponseBody
	public TeeJson getGraphDataByTimes(HttpServletRequest request){
		TeeJson json = new TeeJson();
		Map map = logService.getGraphDataByTimes();
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/getLogInfoByHour")
	@ResponseBody
	public TeeJson getLogInfoByHour(HttpServletRequest request){
		TeeJson json = new TeeJson();
		List map = logService.getLogInfoByHour();
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	
	@RequestMapping("/getLogInfoByYearAndMonth")
	@ResponseBody
	public TeeJson getLogInfoByYearAndMonth(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String year = TeeStringUtil.getString(request.getParameter("year"),"0");
		String month = TeeStringUtil.getString(request.getParameter("month"),"0");
		List map = logService.getLogInfoByYearAndMonth(year,month);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) throws Exception {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return logService.datagrid(dm, requestDatas);
	}
	
	
	
	/**
	 * 三员日志管理
	 * @param dm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getThreePartSysLogs")
	@ResponseBody
	public TeeEasyuiDataGridJson getThreePartSysLogs(TeeDataGridModel dm,HttpServletRequest request) throws Exception {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		return logService.getThreePartSysLogs(dm, requestDatas);
	}
	
	
	
	@RequestMapping("/delLogInfo")
	@ResponseBody
	public TeeJson delLogInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String sid = TeeStringUtil.getString(request.getParameter("sid"), "");
		logService.delLogInfo(sid);
		json.setRtMsg("删除成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/exportLogInfo")
	@ResponseBody
	public void exportLogInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		List<TeeSysLogModel> list = logService.getTotalByConditon(requestDatas);
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("系统日志");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 16); //字体高度
        font.setColor(HSSFFont.COLOR_NORMAL); //字体颜色
        font.setFontName("黑体"); //字体
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //宽度
        font.setItalic(false); //是否使用斜体
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		style.setFont(font);
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("用户名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("ip");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("日志类型");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("日期");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("描述");
		cell.setCellStyle(style);
		
		for (int i = 0; i < list.size(); i++)
		{
			row = sheet.createRow((int) i + 1);
			TeeSysLogModel model = (TeeSysLogModel) list.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell((short) 0).setCellValue(model.getUserName());
			row.createCell((short) 1).setCellValue(model.getIp());
			row.createCell((short) 2).setCellValue(model.getType());
			cell = row.createCell((short) 3);
			cell.setCellValue(model.getTimeDesc());
			row.createCell((short) 4).setCellValue(model.getRemark());
		}
		
		
	//设置每一列的宽度
        sheet.setDefaultColumnWidth(20);
 /*       sheet.autoSizeColumn((short)0);
        sheet.autoSizeColumn((short)1); //调整第一列宽度
        sheet.autoSizeColumn((short)2); //调整第二列宽度
        sheet.autoSizeColumn((short)3); //调整第三列宽度
        sheet.autoSizeColumn((short)4); //调整第四列宽度       
*/		// 第六步，将文件存到指定位置
		try
		{
			Calendar cal = Calendar.getInstance();
			int year=cal.get(Calendar.YEAR);
			int month=cal.get(Calendar.MONTH)+1;
			int day=cal.get(Calendar.DAY_OF_MONTH);
			String days ="";
			String months="";
			if(month<10){
				months="0"+month;
			}else{
				months=""+month;
			}
			if(day<10){
				days="0"+day;
			}else{
				days=""+day;
			}
			String times = ""+year+""+months+""+days;
			String fileName = "sysLog"+times+".xls";
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
	
	
	@RequestMapping("/getSysLogTable")
	@ResponseBody
	public TeeJson getSysLogTable(HttpServletRequest request){
		TeeJson json = new TeeJson();
		List map = logService.getSysLogTable();
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 导出tomcat日志文件
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/doExportTomcat")
	@ResponseBody
	public void doExportTomcat(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
	}
	
	
}
