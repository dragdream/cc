package com.tianee.oa.core.base.attend.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

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

import com.tianee.oa.core.base.attend.model.TeeAttendEvectionModel;
import com.tianee.oa.core.base.attend.model.TeeAttendLeaveModel;
import com.tianee.oa.core.base.attend.model.TeeAttendOvertimeModel;
import com.tianee.oa.core.base.attend.service.TeeAttendOvertimeService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 
 * @author ny
 *
 */
@Controller
@RequestMapping("/attendOvertimeManage")
public class TeeAttendOvertimeController  extends BaseController{
	@Autowired
	private TeeAttendOvertimeService attendOvertimeService;
	/**
	 * @author ny
	 * 新增或者更新
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception 
	 * @throws ParseException 
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , TeeAttendOvertimeModel model) throws Exception {
		TeeJson json = new TeeJson();
		json = attendOvertimeService.addOrUpdate(request , model);
		return json;
	}
	
	/**
	 * 获取加班管理
	 * @author ny
	 * @date 2014-5-23
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getOvertime")
	@ResponseBody
	public TeeEasyuiDataGridJson getOvertime(HttpServletRequest request , TeeAttendOvertimeModel model,TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		json = attendOvertimeService.getOvertime(request , model,dm);
		return json;
	}
	
	@RequestMapping("/getOvertimeEasyui")
	@ResponseBody
	public TeeEasyuiDataGridJson getOvertimeEasyui(TeeDataGridModel dm ,HttpServletRequest request) throws ParseException {
		TeeAttendOvertimeModel model = new TeeAttendOvertimeModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
	    return attendOvertimeService.getOvertimeEasyui(dm,request , model);
	}
	
	/**
	 * 删除  byId
	 * @author ny
	 * @date 2014-5-23
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public TeeJson deleteById(HttpServletRequest request , TeeAttendOvertimeModel model) {
		TeeJson json = new TeeJson();
		json = attendOvertimeService.deleteByIdService(request , model);
		return json;
	}
	

	/**
	 * 查询 byId
	 * @author ny
	 * @date 2014-5-23
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request , TeeAttendOvertimeModel model) {
		TeeJson json = new TeeJson();
		json = attendOvertimeService.getById(request , model);
		return json;
	}
	
	/**
	 * 获取加班审批管理
	 * @author ny
	 * @date 2014-5-23
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getManagerOvertime")
	@ResponseBody
	public TeeEasyuiDataGridJson getManagerOvertime(HttpServletRequest request , TeeAttendOvertimeModel model,TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		json = attendOvertimeService.getManagerOvertime(request , model,dm);
		return json;
	}
	
	
	/**
	 * 获取加班已审批管理
	 * @author ny
	 * @date 2014-5-23
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getManagerApprovOvertime")
	@ResponseBody
	public TeeEasyuiDataGridJson getManagerApprovOvertime(HttpServletRequest request , TeeAttendOvertimeModel model,TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		json = attendOvertimeService.getManagerApprovOvertime(request , model,dm);
		return json;
	}
	
	
	/**
	 * 审批
	 * @author ny
	 * @date 2014-5-23
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/approve")
	@ResponseBody
	public TeeJson approve(HttpServletRequest request , TeeAttendOvertimeModel model) {
		TeeJson json = new TeeJson();
		json = attendOvertimeService.approve(request , model);
		return json;
	}
	
	
	/**
	 * 桌面模块
	 * @author ny
	 * @date 2014-5-23
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/getDesktop")
	@ResponseBody
	public TeeJson getDesktop(HttpServletRequest request ) throws ParseException {
		TeeJson json = new TeeJson();
		json = attendOvertimeService.getDesktop(request );
		return json;
	}
	
	
	@RequestMapping("/getOvertimeByCondition")
	@ResponseBody
	public TeeJson getOvertimeByCondition(HttpServletRequest request , TeeAttendOvertimeModel model) throws Exception {
		TeeJson json = new TeeJson();
		json = attendOvertimeService.getOvertimeByCondition(request);
		return json;
	}
	
	@RequestMapping("/getOvertimeByConditionEasyui")
	@ResponseBody
	public TeeEasyuiDataGridJson getOvertimeByConditionEasyui(TeeDataGridModel dm ,HttpServletRequest request) throws ParseException {
		TeeAttendOvertimeModel model = new TeeAttendOvertimeModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
	    return attendOvertimeService.getOvertimeByConditionEasyui(dm,request , model);
	}
	
	@RequestMapping("/exportOvertime")
	@ResponseBody
	public void exportOvertime(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
		String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
		List<TeeAttendOvertimeModel> listMap=attendOvertimeService.getOvertimesByCondition(request);
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(startDateDesc+"至"+endDateDesc+"员工加班统计结果");
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
		cell.setCellValue("申请时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("审批人员");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("开始时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("结束时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("加班原因");
		cell.setCellStyle(style);
		
		
		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		for (int i = 0; i < listMap.size(); i++)
		{
			row = sheet.createRow((int) i + 1);
			TeeAttendOvertimeModel map = listMap.get(i);
			// 第四步，创建单元格，并设置值
			cell = row.createCell((short) 0);
			cell.setCellValue((String)map.getDeptName());
			cell.setCellStyle(style1);
			
			cell = row.createCell((short) 1);
			cell.setCellValue((String)map.getUserName());
			cell.setCellStyle(style1);
			
			cell = row.createCell((short) 2);
			cell.setCellValue((String)map.getCreateTimeDesc());
			cell.setCellStyle(style1);
			
			cell = row.createCell((short) 3);
			cell.setCellValue((String)map.getLeaderName());
			cell.setCellStyle(style1);
			
			cell = row.createCell((short) 4);
			cell.setCellValue((String)map.getStartTimeDesc());
			cell.setCellStyle(style1);
			
			cell = row.createCell((short) 5);
			cell.setCellValue((String)map.getEndTimeDesc());
			cell.setCellStyle(style1);
			
			cell = row.createCell((short) 6);
			cell.setCellValue((String)map.getOvertimeDesc());
			cell.setCellStyle(style1);
			
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
			String fileName = "员工加班统计结果"+times+".xls";
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