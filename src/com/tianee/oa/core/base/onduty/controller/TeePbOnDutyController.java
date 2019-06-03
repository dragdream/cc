package com.tianee.oa.core.base.onduty.controller;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.attend.bean.TeeAttendHoliday;
import com.tianee.oa.core.base.attend.service.TeeAttendConfigService;
import com.tianee.oa.core.base.exam.model.TeeExamInfoModel;
import com.tianee.oa.core.base.onduty.bean.TeePbOnDuty;
import com.tianee.oa.core.base.onduty.model.TeePbOnDutyModel;
import com.tianee.oa.core.base.onduty.model.TeePbTypeChildModel;
import com.tianee.oa.core.base.onduty.service.TeePbOnDutyService;
import com.tianee.oa.core.base.pm.model.TeePmCustomerFieldModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.file.TeeAOPExcleUtil;
import com.tianee.webframe.util.file.TeeCSVUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("teePbOnDutyController")
public class TeePbOnDutyController {

	private static final String IndexedColors = null;
	@Autowired
	private TeePbOnDutyService teePbOnDutyService;
	
	@Autowired
	private TeeAttendConfigService teeAttendConfigService;
	
	/**
	 * 添加值班
	 * */
	@ResponseBody
	@RequestMapping("/addOrUpdateDuty")
	public TeeJson addOrUpdateDuty(HttpServletRequest request){
		return teePbOnDutyService.addOrUpdateDuty(request);
	}
	
	/**
	 * 获取值班信息
	 * */
	@ResponseBody
	@RequestMapping("/findDutyByDate")
	public TeeJson findDutyByDate(HttpServletRequest request){
		return teePbOnDutyService.findDutyByDate(request);
	}
	
	/**
	 * 个人值班信息
	 * */
	@ResponseBody
	@RequestMapping("/findDutyByDate2")
	public TeeJson findDutyByDate2(HttpServletRequest request){
		return teePbOnDutyService.findDutyByDate2(request);
	}
	
	/**
	 * 获取值班信息
	 * */
	@ResponseBody
	@RequestMapping("/findDutyByDate3")
	public TeeJson findDutyByDate3(HttpServletRequest request){
		return teePbOnDutyService.findDutyByDate3(request);
	}
	
	/**
	 * 获取值班信息
	 * */
	@ResponseBody
	@RequestMapping("/findDutyById")
	public TeeJson findDutyById(HttpServletRequest request){
		return teePbOnDutyService.findDutyById(request);
	}
	
	/**
	 * 删除值班信息
	 * */
	@ResponseBody
	@RequestMapping("/deleteDutyById")
	public TeeJson deleteDutyById(HttpServletRequest request){
		return teePbOnDutyService.deleteDutyById(request);
	}
	
	//批量删除
	@ResponseBody
	@RequestMapping("/batchDelete")
	public TeeJson batchDelete(HttpServletRequest request){
		return teePbOnDutyService.batchDelete(request);
	}
	
	
	/**
	 * 下载值班模板
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/downPdLoadTemplate")
	@ResponseBody
	public void downLoadPdTemplate(HttpServletRequest request,HttpServletResponse response,TeeDataGridModel dm) throws Exception{
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("系统日志");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short)500);
		// 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 12); //字体高度
        font.setColor(HSSFFont.COLOR_NORMAL); //字体颜色
        font.setFontName("宋体"); //字体
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //宽度
        font.setItalic(false); //是否使用斜体
		HSSFCellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 创建一个垂直居中格式
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个水平居中格式
		style.setFont(font);
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("日 期");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("星 期");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("行政值班");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("带班总队领导");
		cell.setCellStyle(style);
		/*cell = row.createCell((short) 7);
		cell.setCellValue("电 话");
		cell.setCellStyle(style);*/
		row = sheet.createRow((int) 1);
		row.setHeight((short)500);
		cell = row.createCell((short) 2);
		cell.setCellValue("白班值");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("白班带");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("夜班值");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("夜班值");
		cell.setCellStyle(style);
		
		CellRangeAddress region1 = new CellRangeAddress((short) 0, (short) 1, (short) 0, (short) 0); //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列 
		sheet.addMergedRegion(region1);
		CellRangeAddress region2 = new CellRangeAddress((short) 0, (short) 1, (short) 1, (short) 1); //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列 
		sheet.addMergedRegion(region2);
		CellRangeAddress region3 = new CellRangeAddress((short) 0, (short) 1, (short) 6, (short) 6); //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列 
		sheet.addMergedRegion(region3);
//		CellRangeAddress region4 = new CellRangeAddress((short) 0, (short) 1, (short) 7, (short) 7); //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列 
//		sheet.addMergedRegion(region4);
		CellRangeAddress region5 = new CellRangeAddress((short) 0, (short) 0, (short) 2, (short) 5); //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列 
		sheet.addMergedRegion(region5);

		//设置每一列的宽度
        sheet.setColumnWidth(0, 5500);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 4000);
        sheet.setColumnWidth(5, 4000);
        sheet.setColumnWidth(6, 5000);
        //sheet.setColumnWidth(7, 5500);
        
		String fileName = "值班排班导入模板.xls";
		response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/msexcel;charset=UTF-8");
		OutputStream out=response.getOutputStream();
		wb.write(out);
		out.close();
	}
	
	/**
	 * 导入值班排班
	 */
	@RequestMapping("/importHumanDocInfo")
	@ResponseBody
	public TeeJson importHumanDocInfo(HttpServletRequest request) throws Exception{
		return teePbOnDutyService.importHumanDocInfo(request);
	}
	
	/**
	 * 导出值班信息
	 */
	/*
	@RequestMapping("/exportHumanDocInfo")
	@ResponseBody
	 public TeeJson exportHumanDocInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		 response.setCharacterEncoding("GBK");
			try {
				String fileName = URLEncoder.encode("值班信息.csv", "UTF-8");
				fileName = fileName.replaceAll("\\+", "%20");
				response.setHeader("Cache-control", "private");
				response.setHeader("Cache-Control", "maxage=3600");
				response.setHeader("Pragma", "public");
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Accept-Ranges", "bytes");
				response.setHeader("Content-disposition", "attachment; filename=\""
						+ fileName + "\"");
				ArrayList<TeeDataRecord> dbL = teePbOnDutyService.exportHumanDocInfo(request, response);
				TeeCSVUtil.CVSWrite(response.getWriter(), dbL);
			} catch (Exception ex) {
				ex.printStackTrace();
				throw ex;
			}
			return null;
	}*/
	
	/**
	 * 导出值班信息
	 */
	@RequestMapping("/exportHumanDocInfo")
	@ResponseBody
	 public TeeJson exportHumanDocInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		TeeJson json = new TeeJson();
		TeePerson person=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		try{
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			String year = request.getParameter("year");
			String month = request.getParameter("month");
			//List<String> dayList = getBetweenDates(year,month);
			String str = year+"-"+month+"-01";
			Calendar cal = Calendar.getInstance();
			Date date = f.parse(str);
			cal.setTime(date);
			int firstDateOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			int totalOfMonthDays = TeeStringUtil.getInteger(request.getParameter("totalOfMonthDays"),0);
			HSSFWorkbook wb = new HSSFWorkbook();
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("系统日志");
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,6));
			HSSFRow rowheader = sheet.createRow(0);
			HSSFCell cellheader = rowheader.createCell(0);
			cellheader.setCellValue("北京市文化市场行政执法总队"+year+"年"+month+"月值班安排表");
			
			
			HSSFFont font2 = wb.createFont();
	        font2.setFontHeightInPoints((short) 25); //字体高度
	        font2.setColor(HSSFFont.COLOR_NORMAL); //字体颜色
	        font2.setFontName("宋体"); //字体
	        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //宽度
	        font2.setItalic(false); //是否使用斜体
			HSSFCellStyle style2 = wb.createCellStyle();
			style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 创建一个垂直居中格式
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个水平居中格式
			style2.setFont(font2);
			cellheader.setCellStyle(style2);
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中 
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			HSSFRow row = sheet.createRow((int) 1);
			row.setHeight((short)500);
			// 第四步，创建单元格，并设置值表头 设置表头居中
	        HSSFFont font = wb.createFont();
	        font.setFontHeightInPoints((short) 12); //字体高度
	        font.setColor(HSSFFont.COLOR_NORMAL); //字体颜色
	        font.setFontName("宋体"); //字体
	        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //宽度
	        font.setItalic(false); //是否使用斜体
			HSSFCellStyle style = wb.createCellStyle();
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 创建一个垂直居中格式
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个水平居中格式
			style.setFont(font);
			//当天颜色 HSSFColor.LIGHT_GREEN：当天
			HSSFCellStyle styleD = wb.createCellStyle();
			styleD.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
			styleD.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);//前景填充色(浅黄色)
			styleD.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 创建一个垂直居中格式
			styleD.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个水平居中格式
			styleD.setFont(font);
			//节假日 颜色 HSSFColor.CORAL.index
			HSSFCellStyle styleJ = wb.createCellStyle();
			styleJ.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
			styleJ.setFillForegroundColor(HSSFColor.CORAL.index);//前景填充色(浅黄色)
			styleJ.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 创建一个垂直居中格式
			styleJ.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个水平居中格式
			styleJ.setFont(font);
			//周六日 颜色   HSSFColor.ROSE.index
			HSSFCellStyle styleZ = wb.createCellStyle();
			styleZ.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
			styleZ.setFillForegroundColor(HSSFColor.ROSE.index);//前景填充色(浅黄色)
			styleZ.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 创建一个垂直居中格式
			styleZ.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个水平居中格式
			styleZ.setFont(font);
			//二、设置边框:

//			style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
//			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
//			style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
//			style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			HSSFCell cell = row.createCell((short) 0);
			cell.setCellValue("日 期");
			cell.setCellStyle(style);
			cell = row.createCell((short) 1);
			cell.setCellValue("星 期");
			cell.setCellStyle(style);
			cell = row.createCell((short) 2);
			cell.setCellValue("行政值班");
			cell.setCellStyle(style);
			cell = row.createCell((short) 6);
			cell.setCellValue("带班总队领导");
			cell.setCellStyle(style);
//			cell = row.createCell((short) 7);
//			cell.setCellValue("电 话");
//			cell.setCellStyle(style);
			
			row = sheet.createRow((int) 2);
			row.setHeight((short)500);
			
			cell = row.createCell((short) 2);
			cell.setCellValue("白班值");
			cell.setCellStyle(style);
			
			cell = row.createCell((short) 3);
			cell.setCellValue("白班带");
			cell.setCellStyle(style);
			
			cell = row.createCell((short) 4);
			cell.setCellValue("夜班值");
			cell.setCellStyle(style);
			
			cell = row.createCell((short) 5);
			cell.setCellValue("夜班待");
			cell.setCellStyle(style);
			
			CellRangeAddress region1 = new CellRangeAddress((short) 1, (short) 2, (short) 0, (short) 0); //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列 
			sheet.addMergedRegion(region1);
			CellRangeAddress region2 = new CellRangeAddress((short) 1, (short) 2, (short) 1, (short) 1); //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列 
			sheet.addMergedRegion(region2);
			CellRangeAddress region3 = new CellRangeAddress((short) 1, (short) 2, (short) 6, (short) 6); //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列 
			sheet.addMergedRegion(region3);
//			CellRangeAddress region4 = new CellRangeAddress((short) 1, (short) 2, (short) 7, (short) 7); //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列 
//			sheet.addMergedRegion(region4);
			CellRangeAddress region5 = new CellRangeAddress((short) 1, (short) 1, (short) 2, (short) 5); //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列 
			sheet.addMergedRegion(region5);
	
			//设置每一列的宽度
	        sheet.setColumnWidth(0, 5500);
	        sheet.setColumnWidth(1, 3000);
	        sheet.setColumnWidth(2, 4000);
	        sheet.setColumnWidth(3, 4000);
	        sheet.setColumnWidth(4, 4000);
	        sheet.setColumnWidth(5, 4000);
	        sheet.setColumnWidth(6, 5000);
	        //sheet.setColumnWidth(7, 5500);
	        
			String fileName = year+"年"+month+"月值班排班安排表.xls";
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
	        response.setContentType("application/msexcel;charset=UTF-8");
			OutputStream out=response.getOutputStream();
			List<TeePbOnDuty> list = teePbOnDutyService.exportHumanDocInfo(request, response);
			JSONObject object=new JSONObject();
			object.put("year", year);
			object.put("month", month);
			//获取节假日
			String allow = isAllow(object.toString());
			//String allow="{\"msg\": \"成功\",\"list\":[{\"date\":\"2018-12-01\",\"holidayType\":\"2\"},{\"date\":\"2018-12-02\",\"holidayType\":\"2\"},{\"date\":\"2018-12-30\",\"holidayType\":\"1\"},{\"date\":\"2018-12-31\",\"holidayType\":\"1\"}]}";
			List<String> listJ=new ArrayList<String>();
			List<String> listZ=new ArrayList<String>();
			JSONObject jsonObject = JSONObject.fromObject(allow);//返回的是数值	
			List<Map> listMap = (List<Map>)JSONArray.fromObject(jsonObject.getString("list"));
			if(listMap!=null && listMap.size()>0){
				for(int i=0;i<listMap.size();i++){
					Map map = listMap.get(i);
					String data=TeeStringUtil.getString(map.get("date"));
					String holidayType=TeeStringUtil.getString(map.get("holidayType"));
					if("1".equals(holidayType)){//节假日
						listJ.add(data);
					}else{//双休日
						listZ.add(data);
					}
				}
			}
			for (int i=0;i<totalOfMonthDays;i++) {
				HSSFRow tRow = sheet.createRow(i + 3); // 第4行是数据
				tRow.setHeight((short)500);
				
				HSSFCell cell0 = tRow.createCell(0);
				HSSFCell cell1 = tRow.createCell(1);
				HSSFCell cell2 = tRow.createCell(2);
				HSSFCell cell3 = tRow.createCell(3);
				HSSFCell cell4 = tRow.createCell(4);
				HSSFCell cell5 = tRow.createCell(5);
				HSSFCell cell6 = tRow.createCell(6);
				//HSSFCell cell7 = tRow.createCell(7);
				String dayStr="";
				int i2=i+1;
				if(i2<10){
					dayStr="0"+i2;
				}else{
					dayStr=i2+"";
				}
				int monthInt = TeeStringUtil.getInteger(month, 0);
				String monthStr=month;
				if(monthInt<10){
					monthStr="0"+monthStr;
				}
				String format = TeeDateUtil.format(new Date(), "dd");
//				if(dayStr.equals(format)){
//					cell0.setCellStyle(styleD);
//					cell1.setCellStyle(styleD);
//					cell2.setCellStyle(styleD);
//					cell3.setCellStyle(styleD);
//					cell4.setCellStyle(styleD);
//					cell5.setCellStyle(styleD);
//					cell6.setCellStyle(styleD);
//					//cell7.setCellStyle(styleD);
//				}else 
				if(listZ.contains(year+"-"+monthStr+"-"+dayStr)){//周六日
					cell0.setCellStyle(styleZ);
					cell1.setCellStyle(styleZ);
					cell2.setCellStyle(styleZ);
					cell3.setCellStyle(styleZ);
					cell4.setCellStyle(styleZ);
					cell5.setCellStyle(styleZ);
					cell6.setCellStyle(styleZ);
					//cell7.setCellStyle(styleJ);
				}else if(listJ.contains(year+"-"+monthStr+"-"+dayStr)){//节假日
					cell0.setCellStyle(styleJ);
					cell1.setCellStyle(styleJ);
					cell2.setCellStyle(styleJ);
					cell3.setCellStyle(styleJ);
					cell4.setCellStyle(styleJ);
					cell5.setCellStyle(styleJ);
					cell6.setCellStyle(styleJ);
					//cell7.setCellStyle(styleZ);
				}else{
					cell0.setCellStyle(style);
					cell1.setCellStyle(style);
					cell2.setCellStyle(style);
					cell3.setCellStyle(style);
					cell4.setCellStyle(style);
					cell5.setCellStyle(style);
					cell6.setCellStyle(style);
					//cell7.setCellStyle(style);
				}
				String str1 = year+"年"+month+"月"+(i+1)+"日";
				cell0.setCellValue(str1);
				String str2 = "";
				if((i+firstDateOfWeek)% 7==1){
					str2 = "星期日";
				}
				if((i+firstDateOfWeek)% 7==2){
					str2 = "星期一";
				}
				if((i+firstDateOfWeek)% 7==3){
					str2 = "星期二";
				}
				if((i+firstDateOfWeek)% 7==4){
					str2 = "星期三";
				}
				if((i+firstDateOfWeek)% 7==5){
					str2 = "星期四";
				}
				if((i+firstDateOfWeek)% 7==6){
					str2 = "星期五";
				}
				if((i+firstDateOfWeek)% 7==0){
					str2 = "星期六";
				}
				cell1.setCellValue(str2);
				
				String str3 = year+"-"+month+"-"+(i+1);
				String str4 = TeeDateUtil.format(TeeStringUtil.getDate(str3,"yyyy-MM-dd"),"yyyy-MM-dd");
				for(int j=0;j<list.size();j++){
					String str5 = TeeDateUtil.format(list.get(j).getCreTime(),"yyyy-MM-dd");
					if(str4.equals(str5)){
						if(list.get(j).getBzUser()!=null){
							String BzUser = TeeStringUtil.getString(list.get(j).getBzUser().getUserName());
							if("王辉".equals(BzUser)){
								if("执法二队".equals(TeeStringUtil.getString(list.get(j).getBzUser().getDept().getDeptName()))){
									BzUser=BzUser+"(二队)";
								}else{
									BzUser=BzUser+"(法制)";
								}
							}
							cell2.setCellValue(BzUser);
							if(person.getUuid()==list.get(j).getBzUser().getUuid()){
								cell2.setCellStyle(styleD);
							}
						}
						if(list.get(j).getBdUser()!=null){
							String BdUser = TeeStringUtil.getString(list.get(j).getBdUser().getUserName());
							if("王辉".equals(BdUser)){
								if("执法二队".equals(TeeStringUtil.getString(list.get(j).getBdUser().getDept().getDeptName()))){
									BdUser=BdUser+"(二队)";
								}else{
									BdUser=BdUser+"(法制)";
								}
							}
							cell3.setCellValue(BdUser);
							if(person.getUuid()==list.get(j).getBdUser().getUuid()){
								cell3.setCellStyle(styleD);
							}
						}
						if(list.get(j).getYzUser()!=null){
							String YzUser = TeeStringUtil.getString(list.get(j).getYzUser().getUserName());
							if("王辉".equals(YzUser)){
								if("执法二队".equals(TeeStringUtil.getString(list.get(j).getYzUser().getDept().getDeptName()))){
									YzUser=YzUser+"(二队)";
								}else{
									YzUser=YzUser+"(法制)";
								}
							}
							cell4.setCellValue(YzUser);
							if(person.getUuid()==list.get(j).getYzUser().getUuid()){
								cell4.setCellStyle(styleD);
							}
						}
						if(list.get(j).getYdUser()!=null){
							String YdUser = TeeStringUtil.getString(list.get(j).getYdUser().getUserName());
							if("王辉".equals(YdUser)){
								if("执法二队".equals(TeeStringUtil.getString(list.get(j).getYdUser().getDept().getDeptName()))){
									YdUser=YdUser+"(二队)";
								}else{
									YdUser=YdUser+"(法制)";
								}
							}
							cell5.setCellValue(YdUser);
							if(person.getUuid()==list.get(j).getYdUser().getUuid()){
								cell5.setCellStyle(styleD);
							}
						}
						if(list.get(j).getZdUser()!=null){
							cell6.setCellValue(TeeStringUtil.getString(list.get(j).getZdUser().getUserName()));
						    if(person.getUuid()==list.get(j).getZdUser().getUuid()){
						    	cell6.setCellStyle(styleD);
						    }
						}
						//cell7.setCellValue(TeeStringUtil.getString(list.get(j).getMobileNo()));
					}
				}
				
			}
			
			wb.write(out);
			out.close();
			json.setRtState(true); 
			json.setRtMsg("导出成功");
		}catch(Exception ex){
			ex.printStackTrace();
			json.setRtMsg("导出失败");
			json.setRtState(false);
		}
		return json;
	}
	/**
	 * 获取某个月的节假日
	 * */
	@ResponseBody
	@RequestMapping("/getJieJiaRi")
	public TeeJson getJieJiaRi(HttpServletRequest request){
		TeeJson json=new TeeJson();
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		Map listJAndZ = getListJAndZ(year,month);
		json.setRtData(listJAndZ);
		json.setRtState(true);
		return json;
	}
	
	//获取节假日和双休日
	public Map getListJAndZ(String year,String month){
		//List<String> dates = getBetweenDates(year,month);
		JSONObject object=new JSONObject();
		object.put("year", year);
		object.put("month", month);
		//String str="{'year':'"+year+"','month':'"+month+"'}";
		String allow = isAllow(object.toString());
		//String allow="{\"msg\": \"成功\",\"list\":[{\"data\":\"2019-01-27\",\"holidayType\":\"2\"},{\"data\":\"2019-01-28\",\"holidayType\":\"2\"},{\"data\":\"2019-01-29\",\"holidayType\":\"1\"}]}";
		List<String> listJ=new ArrayList<String>();
		List<String> listZ=new ArrayList<String>();
		JSONObject jsonObject = JSONObject.fromObject(allow);//返回的是数值	
		List<Map> listMap = (List<Map>)JSONArray.fromObject(jsonObject.getString("list"));
		if(listMap!=null && listMap.size()>0){
			for(int i=0;i<listMap.size();i++){
				Map map = listMap.get(i);
				String data=TeeStringUtil.getString(map.get("date"));
				String holidayType=TeeStringUtil.getString(map.get("holidayType"));
				if("1".equals(holidayType)){//节假日
					listJ.add(data);
				}else{//双休日
					listZ.add(data);
				}
			}
		}
		Map m=new HashMap();
		m.put("listJ", listJ.toArray());
		m.put("listZ", listZ.toArray());
		return m;
	}
	//获取两个日期之间的日期
	private List<String> getBetweenDates(String year,String month) {
		List<String> listStr=new ArrayList<String>();
		List<TeeAttendHoliday> findDayList = teeAttendConfigService.findDayList(year,month);
		if(findDayList!=null && findDayList.size()>0){
	    	for(TeeAttendHoliday d:findDayList){
	    		Calendar start=d.getStartTime();
	    		Calendar end=d.getEndTime();
	    		GetDates(TeeDateUtil.format(start),TeeDateUtil.format(end),listStr);
	    	}
	    }
		
	    return listStr;
	}
	
	public void GetDates(String start1, String end1,List<String> listStr){
		Calendar start = getCalendar(start1);
		Calendar end = getCalendar(end1);
		//start.add(Calendar.DAY_OF_YEAR, 1);
 	    //listStr.add(TeeDateUtil.format(start.getTime(), "yyyy-MM-dd"));
 	    while (start.before(end)) {
 	    	listStr.add(TeeDateUtil.format(start.getTime(), "yyyy-MM-dd"));
 	        start.add(Calendar.DAY_OF_YEAR, 1);
 	    }
 	    listStr.add(TeeDateUtil.format(end.getTime(), "yyyy-MM-dd"));
	}
	//字符串变
		public Calendar getCalendar(String time){
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date;
			Calendar calendar=null;
			try {
				date = sdf.parse(time);
				calendar= Calendar.getInstance();
				calendar.setTime(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return calendar;
		}
		
		
		public String isAllow(String data) {
			String xml = data.toString();
			// 声明返回值
			String result = "";
			// 省院调用高检的url
			// 声明接口地址
			String personAddress = TeeSysProps.getProps().getProperty("PERSON_ADDRESS");
			String ENDPOINT = "http://"+personAddress+"/QjxxHolidaysService/getHolidays?wsdl";
			// 声明方法名
			String method = "getHolidays";
			// 声明命名空间
			String SOAPACTION = "http://whzf.inf.web.bop.com/";
			// 2.进行接口调用
			// 声明调用对象
			Call call;
			try {
				// 获得调用对象
				call = (Call) new Service().createCall();
				// 设置超时时间
				call.setTimeout(new Integer(6000000));
				// 设置调用地址
				call.setTargetEndpointAddress(new URL(ENDPOINT));
				// 设置命名空间与调用方法
				call.setOperationName(new QName(SOAPACTION, method));
				// 设置输入参数类型
				call.addParameter(new QName(SOAPACTION, "arg0"),
						XMLType.XSD_STRING, ParameterMode.IN);
				// 设置返回值类型
				call.setReturnType(XMLType.XSD_STRING);
				// 声明参数数组并封装xml数据
				Object[] argArr = new Object[] { xml };
				// 调用接口并获得返回值
				// result = (String) call.invoke(argArr);
				// 将返回值进行解析
				
				result = (String) call.invoke(argArr);
				// 将返回值返回
			} catch (Exception e) {
			}
		return result;
		}
}
