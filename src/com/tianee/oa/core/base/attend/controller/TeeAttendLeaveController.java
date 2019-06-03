package com.tianee.oa.core.base.attend.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
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

import com.tianee.oa.core.base.attend.model.TeeAttendLeaveModel;
import com.tianee.oa.core.base.attend.model.TeeAttendOutModel;
import com.tianee.oa.core.base.attend.model.TeeAttendOvertimeModel;
import com.tianee.oa.core.base.attend.service.TeeAttendLeaveService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.customer.model.TeeCrmContactUserModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 
 * @author syl
 *
 */
@Controller
@RequestMapping("/attendLeaveManage")
public class TeeAttendLeaveController extends BaseController{
	@Autowired
	private TeeAttendLeaveService attendLeaveService;
	
	/**
	 * @author syl
	 * 新增或者更新
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , TeeAttendLeaveModel model) {
		TeeJson json = new TeeJson();
		json = attendLeaveService.addOrUpdate(request , model);
		return json;
	}
	
	/**
	 * 获取外出管理
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getLeave")
	@ResponseBody
	public TeeEasyuiDataGridJson getLeave(HttpServletRequest request , TeeAttendLeaveModel model,TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		json = attendLeaveService.getLeave(request,model,dm);
		return json;
	}
	
	@RequestMapping("/getLeaveEasyui")
	@ResponseBody
	public TeeEasyuiDataGridJson getLeaveEasyui(TeeDataGridModel dm ,HttpServletRequest request) throws ParseException {
		TeeAttendLeaveModel model = new TeeAttendLeaveModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
	    return attendLeaveService.getLeaveEasyui(dm,request , model);
	}
	
	/**
	 * 删除  byId
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public TeeJson deleteById(HttpServletRequest request , TeeAttendLeaveModel model) {
		TeeJson json = new TeeJson();
		json = attendLeaveService.deleteByIdService(request , model);
		return json;
	}
	

	/**
	 * 查询 byId
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request , TeeAttendLeaveModel model) {
		TeeJson json = new TeeJson();
		json = attendLeaveService.getById(request , model);
		return json;
	}
	
	/**
	 * 获取外出审批管理
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getLeaderLeave")
	@ResponseBody
	public TeeEasyuiDataGridJson   getManagerOut(HttpServletRequest request,TeeAttendLeaveModel model,TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		json = attendLeaveService.getLeaderLeave(request , model,dm);
		return json;
	}

	/**
	 * 申请销毁
	 * @author syl
	 * @date 2014-2-5
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/destroyApply")
	@ResponseBody
	public TeeJson destroyApply(HttpServletRequest request , TeeAttendLeaveModel model) {
		TeeJson json = new TeeJson();
		json = attendLeaveService.destroyApply(request , model);
		return json;
	}
	
	
	
	/**
	 * 获取外出已审批管理
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getLeaderApprovLeave")
	@ResponseBody
	public TeeEasyuiDataGridJson getLeaderApprovLeave(HttpServletRequest request , TeeAttendLeaveModel model,TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		json = attendLeaveService.getLeaderApprovLeave(request,model,dm);
		return json;
	}
	
	
	/**
	 * 审批
	 * @author syl
	 * @date 2014-2-5
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/approve")
	@ResponseBody
	public TeeJson approve(HttpServletRequest request , TeeAttendLeaveModel model) {
		TeeJson json = new TeeJson();
		json = attendLeaveService.approve(request , model);
		return json;
	}
	
	/**
	 * @author syl
	 * 新增或者更新
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/destroyLeave")
	@ResponseBody
	public TeeJson destroyLeave(HttpServletRequest request , TeeAttendLeaveModel model) {
		TeeJson json = new TeeJson();
		json = attendLeaveService.destroyLeave(request , model);
		return json;
	}
	
	@RequestMapping("/getLeaveByCondition")
	@ResponseBody
	public TeeJson getLeaveByCondition(HttpServletRequest request , TeeAttendLeaveModel model) throws Exception {
		TeeJson json = new TeeJson();
		json = attendLeaveService.getLeaveByCondition(request , model);
		return json;
	}
	
	@RequestMapping("/getLeaveByConditionEasyui")
	@ResponseBody
	public TeeEasyuiDataGridJson getLeaveByConditionEasyui(TeeDataGridModel dm ,HttpServletRequest request) throws ParseException {
		TeeAttendLeaveModel model = new TeeAttendLeaveModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
	    return attendLeaveService.getLeaveByConditionEasyui(dm,request , model);
	}
	
	/**
	 * 获取年假信息
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAnnualLeaveInfo")
	@ResponseBody
	public TeeJson getAnnualLeaveInfo(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		json = attendLeaveService.getAnnualLeaveInfo(request);
		return json;
	}
	
	
	
	/**
	 * 获取年假信息
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUsedAnnualLeaveDays")
	@ResponseBody
	public TeeJson getUsedAnnualLeaveDays(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		json = attendLeaveService.getUsedAnnualLeaveDays(request);
		return json;
	}
	
	/**
	 * 根据条件统计年假使用情况
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAnnualLeaveByCondition")
	@ResponseBody
	public TeeJson getAnnualLeaveByCondition(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		json = attendLeaveService.getAnnualLeaveByCondition(request);
		return json;
	}
	
	@RequestMapping("/getAnnualLeaveByConditionEasyui")
	@ResponseBody
	public TeeEasyuiDataGridJson getAnnualLeaveByConditionEasyui(TeeDataGridModel dm ,HttpServletRequest request) throws ParseException {
	    return attendLeaveService.getAnnualLeaveByConditionEasyui(dm,request);
	}
	
	@RequestMapping("/exprotAnnualLeave")
	@ResponseBody
	public void exprotAnnualLeave(HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<Map> listMap=attendLeaveService.getAnnualLeaveListByCondition(request);
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("年假统计信息");
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
		cell.setCellValue("总年假天数（今年）");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("剩余年假天数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("已用年假天数");
		cell.setCellStyle(style);
		
		
		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		for (int i = 0; i < listMap.size(); i++)
		{
			row = sheet.createRow((int) i + 1);
			Map map=new HashMap();
			map = listMap.get(i);
			// 第四步，创建单元格，并设置值
			cell = row.createCell((short) 0);
			cell.setCellValue((String)map.get("deptName"));
			cell.setCellStyle(style1);
			
			cell = row.createCell((short) 1);
			cell.setCellValue((String)map.get("userName"));
			cell.setCellStyle(style1);
			
			cell = row.createCell((short) 2);
			cell.setCellValue((Double)map.get("totalAnnualLeaveDays"));
			cell.setCellStyle(style1);
			
			cell = row.createCell((short) 3);
			cell.setCellValue((Double)map.get("usedAnnualLeaveDays"));
			cell.setCellStyle(style1);
			
			cell = row.createCell((short) 4);
			cell.setCellValue((Double)map.get("remainAnnualLeaveDays"));
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
			String fileName = "年假统计信息"+times+".xls";
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
	
	
	@RequestMapping("/exportLeave")
	@ResponseBody
	public void exportLeave(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
		String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
		List<TeeAttendLeaveModel> listMap=attendLeaveService.getLeaveByCondition(request);
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(startDateDesc+"至"+endDateDesc+"员工请假统计结果");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 14); //字体高度
        font.setColor(HSSFFont.COLOR_NORMAL); //字体颜色
        font.setFontName("黑体"); //字体
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //宽度
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
		cell.setCellValue("请假类型");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("请假原因");
		cell.setCellStyle(style);
		
		
		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		for (int i = 0; i < listMap.size(); i++){
			row = sheet.createRow((int) i + 1);
			TeeAttendLeaveModel map = listMap.get(i);
			// 第四步，创建单元格，并设置值
			cell = row.createCell((short) 0);
			cell.setCellValue((String)map.getDeptName());
			cell.setCellStyle(style1);
			
			cell = row.createCell((short) 1);
			cell.setCellValue((String)map.getUserName());
			cell.setCellStyle(style1);
			
			cell = row.createCell((short) 2);
			cell.setCellValue((String)map.getCreateTimeStr());
			cell.setCellStyle(style1);
			
			cell = row.createCell((short) 3);
			cell.setCellValue((String)map.getLeaderName());
			cell.setCellStyle(style1);
			
			cell = row.createCell((short) 4);
			cell.setCellValue((String)map.getStartTimeStr());
			cell.setCellStyle(style1);
			
			cell = row.createCell((short) 5);
			cell.setCellValue((String)map.getEndTimeStr());
			cell.setCellStyle(style1);
			
			cell = row.createCell((short) 6);
			cell.setCellValue((String)map.getLeaveTypeDesc());
			cell.setCellStyle(style1);
			
			cell = row.createCell((short) 7);
			cell.setCellValue((String)map.getLeaveDesc());
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
			String fileName = "员工请假统计结果"+times+".xls";
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
