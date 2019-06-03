package com.tianee.oa.core.base.attend.controller;


import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.attend.bean.TeeAttendDutyRecordModel;
import com.tianee.oa.core.base.attend.model.TeeAttendOvertimeModel;
import com.tianee.oa.core.base.attend.service.TeeAttendDutyRecordService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;


@Controller
@RequestMapping("/TeeAttendDutyRecordController")
public class TeeAttendDutyRecordController {

	@Autowired
	private TeeAttendDutyRecordService dutyRecordService;
	
	
	/**
	 * 考情统计
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRegisterRecordInfo")
	@ResponseBody
	public TeeJson getRegisterRecordInfo(HttpServletRequest request , TeeAttendOvertimeModel model) throws Exception {
		
		return dutyRecordService.getRegisterRecordInfo(request);
	}
	
	@RequestMapping("/exportRegister")
	@ResponseBody
	public void exportRegister(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
		String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
		TeeJson json =dutyRecordService.getRegisterRecordInfo(request);
		List<TeeAttendDutyRecordModel> modelList = (List<TeeAttendDutyRecordModel>)json.getRtData();
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(startDateDesc+"至"+endDateDesc+"员工上下班登记统计结果");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 14); //字体高度
        font.setColor(HSSFFont.COLOR_NORMAL); //字体颜色
        font.setFontName("黑体"); //字体
       // font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //宽度
        font.setItalic(false); //是否使用斜体
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		style.setFont(font);
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("所属部门");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("姓名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("全勤天");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("上班时长");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("上班未登记");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("迟到");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("下班未登记");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("早退");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("请假天数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("外出天数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("出差天数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 11);
		cell.setCellValue("加班时长");
		cell.setCellStyle(style);
		cell = row.createCell((short) 12);
		cell.setCellValue("外勤天数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 13);
		cell.setCellValue("外勤次数");
		cell.setCellStyle(style);
		
		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		TeeAttendDutyRecordModel model=null;
		if(modelList!=null&&modelList.size()>0){
			for (int i = 0; i < modelList.size(); i++){
				row = sheet.createRow((int) i + 1);
				model = modelList.get(i);
				// 第四步，创建单元格，并设置值
				cell = row.createCell((short) 0);
				cell.setCellValue(model.getDeptName());
				cell.setCellStyle(style1);
				
				cell = row.createCell((short) 1);
				cell.setCellValue(model.getUserName());
				cell.setCellStyle(style1);
				
				cell = row.createCell((short) 2);
				cell.setCellValue(model.getPerfectCount());
				cell.setCellStyle(style1);
					
				cell = row.createCell((short) 3);
				cell.setCellValue(model.getHours());
				cell.setCellStyle(style1);
				
				cell = row.createCell((short) 4);
				cell.setCellValue(model.getWorkOnNoRegisters());
				cell.setCellStyle(style1);
				
				cell = row.createCell((short) 5);
				cell.setCellValue(model.getLateNums());
				cell.setCellStyle(style1);
				
				cell = row.createCell((short) 6);
				cell.setCellValue(model.getWorkOutNoRegisters());
				cell.setCellStyle(style1);
				
				cell = row.createCell((short) 7);
				cell.setCellValue(model.getLeaveEarlyNums());
				cell.setCellStyle(style1);
				
				cell = row.createCell((short) 8);
				cell.setCellValue(model.getLeaveDays());
				cell.setCellStyle(style1);
				
				cell = row.createCell((short) 9);
				cell.setCellValue(model.getOutDays());
				cell.setCellStyle(style1);
				
				cell = row.createCell((short) 10);
				cell.setCellValue(model.getEvectionDays());
				cell.setCellStyle(style1);
				
				cell = row.createCell((short) 11);
				cell.setCellValue(model.getOverHours());
				cell.setCellStyle(style1);
				
				cell = row.createCell((short) 12);
				cell.setCellValue(model.getAttendAssignDays());
				cell.setCellStyle(style1);
				
				cell = row.createCell((short) 13);
				cell.setCellValue(model.getAttendAssignNums());
				cell.setCellStyle(style1);
			}
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
			String fileName = "员工上下班登记统计结果"+times+".xls";
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
