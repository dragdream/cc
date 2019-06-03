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
import org.apache.poi.hssf.util.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.fabric.Response;
import com.tianee.oa.core.base.attend.model.TeeAttendConfigModel;
import com.tianee.oa.core.base.attend.service.TeeAttendDutyService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/TeeAttendDutyController")
public class TeeAttendDutyController  extends BaseController{
	@Autowired
	private TeeAttendDutyService dutyService;

	@RequestMapping("/addDuty")
	@ResponseBody
	public TeeJson addDuty(HttpServletRequest request , TeeAttendConfigModel model) throws Exception {
		TeeJson json = new TeeJson();
		json = dutyService.addDuty(request);
		return json;
	}
	
	/**
	 * 根据条件查询上下班登记记录(人事管理   --考勤管理 ---考勤记录   )
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDutyByCondition")
	@ResponseBody
	public TeeJson getDutyByCondition(HttpServletRequest request , TeeAttendConfigModel model) throws Exception {
		TeeJson json = new TeeJson();
		json = dutyService.getDutyByCondition(request);
		return json;
	}
	
	
	/**
	 * 根据条件查询上下班登记记录(上下班记录  )
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPersonalDutyByCondition")
	@ResponseBody
	public TeeJson getPersonalDutyByCondition(HttpServletRequest request , TeeAttendConfigModel model) throws Exception {
		TeeJson json = new TeeJson();
		json = dutyService.getPersonalDutyByCondition(request);
		return json;
	}
	
	
	@RequestMapping("/delAttendData")
	@ResponseBody
	public TeeJson delAttendData(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		json = dutyService.delAttendData(request);
		return json;
	}
	
	
	@RequestMapping("/getRegisterRecord")
	@ResponseBody
	public TeeJson getRegisterRecord(HttpServletRequest request , TeeAttendConfigModel model) throws Exception {
		TeeJson json = new TeeJson();
		json = dutyService.getRegisterRecord(request);
		return json;
	}
	
	/**
	 * 考勤 ---- 统计
	 * @author syl
	 * @date 2014-6-23
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRegisterRecordInfo")
	@ResponseBody
	public TeeJson getRegisterRecordInfo(HttpServletRequest request , TeeAttendConfigModel model) throws Exception {
		TeeJson json = new TeeJson();
		json = dutyService.getRegisterRecordInfo(request);
		return json;
	}
	
	

	/**
	 *获取当前用户当天已经签到的次数（从1至6）
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRegisterStatue")
	@ResponseBody
	public TeeJson getRegisterStatue(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		json= dutyService.getRegisterStatue(request);
		return json;
	}
	
	@RequestMapping("/exportRegister")
	@ResponseBody
	public void exportRegister(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String startDateDesc = TeeStringUtil.getString(request.getParameter("startDateDesc"), "");
		String endDateDesc = TeeStringUtil.getString(request.getParameter("endDateDesc"), "");
		TeeJson json =dutyService.getRegisterRecordInfo(request);
		List<Map> listMap = (List<Map>)json.getRtData();
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
		
		
		
		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		if(listMap!=null&&listMap.size()>0){
			for (int i = 0; i < listMap.size(); i++){
				row = sheet.createRow((int) i + 1);
				Map map = new HashMap();
				map = listMap.get(i);
				// 第四步，创建单元格，并设置值
				cell = row.createCell((short) 0);
				cell.setCellValue((String)map.get("deptName"));
				cell.setCellStyle(style1);
				
				cell = row.createCell((short) 1);
				cell.setCellValue((String)map.get("userName"));
				cell.setCellStyle(style1);
				
				cell = row.createCell((short) 2);
				cell.setCellValue((Integer)map.get("perfectCount"));
				cell.setCellStyle(style1);
					
				cell = row.createCell((short) 3);
				cell.setCellValue((String)map.get("hours"));
				cell.setCellStyle(style1);
				
				cell = row.createCell((short) 4);
				cell.setCellValue((Integer)map.get("workOnNoRegisters"));
				cell.setCellStyle(style1);
				
				cell = row.createCell((short) 5);
				cell.setCellValue((Integer)map.get("lateNums"));
				cell.setCellStyle(style1);
				
				cell = row.createCell((short) 6);
				cell.setCellValue((Integer)map.get("workOutNoRegisters"));
				cell.setCellStyle(style1);
				
				cell = row.createCell((short) 7);
				cell.setCellValue((Integer)map.get("leaveEarlyNums"));
				cell.setCellStyle(style1);
				
				cell = row.createCell((short) 8);
				cell.setCellValue((Double)map.get("leaveDays"));
				cell.setCellStyle(style1);
				
				cell = row.createCell((short) 9);
				cell.setCellValue((Double)map.get("outDays"));
				cell.setCellStyle(style1);
				
				cell = row.createCell((short) 10);
				cell.setCellValue((Double)map.get("evectionDays"));
				cell.setCellStyle(style1);
				
				cell = row.createCell((short) 11);
				cell.setCellValue((Double)map.get("overHours"));
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
	
	/**
	 * 获取指定天数的星期
	 * @param day
	 * @return
	 */
	@RequestMapping("/startWeek")
	@ResponseBody
	public String startWeek(int day){
		String startWeeks = "";
		if(day==0){
			startWeeks="日";
		}
		else if(day==1){
			startWeeks="一";
		}
		else if(day==2){
			startWeeks="二";
		}
	   else if(day==3){
			startWeeks="三";
		}
	   else if(day==4){
			startWeeks="四";
		}
	   else if(day==5){
			startWeeks="五";
		}
	   else if(day==6){
			startWeeks="六";
		}
        return startWeeks;
	}
	
	
	/**
	 * 考勤同步
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/attendSync")
	@ResponseBody
	public TeeJson attendSync(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		json = dutyService.attendSync(request);
		return json;
	}
	
	
	
	/**
	 * 根据排班类型  获取两个时间段   之间的 时间差（只计算工作时间    排除节假日  周六日）
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getTimeDiffByDutyConfig")
	@ResponseBody
	public TeeJson getTimeDiffByDutyConfig(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		String startTimeStr=TeeStringUtil.getString(request.getParameter("startTimeStr"));
		String endTimeStr=TeeStringUtil.getString(request.getParameter("endTimeStr"));
		TeePerson person=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json = dutyService.getTimeDiffByDutyConfig(startTimeStr,endTimeStr,person);
		return json;
	}
	
	
	/**
	 * 数据刷新
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/refreshData")
	public void refreshData(HttpServletRequest request , HttpServletResponse response) throws Exception {
		dutyService.refreshData(request,response);
	}
}