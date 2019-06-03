package com.tianee.oa.core.base.pm.controller;

import java.io.OutputStream;
import java.net.URLEncoder;

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

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.base.pm.model.TeeOnDutyModel;
import com.tianee.oa.core.base.pm.service.TeeOnDutyService;
import com.tianee.oa.subsys.crm.core.target.model.TeeCrmDeptTargetModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/onDutyController")
public class TeeOnDutyController {

	@Autowired
	private TeeOnDutyService service;
	
	
	/**
	 * 列表显示
	 * 
	 * @param dm
	 * @param request
	 * @return
	 * @throws java.text.ParseException 
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getDutyList.action")
	@ResponseBody
	public TeeJson getDutyList(HttpServletRequest request) throws java.text.ParseException{
		TeeJson json = new TeeJson();
		int deptUuidd=0;
		int year=Integer.parseInt(request.getParameter("year"));
		int month=Integer.parseInt(request.getParameter("month"));
		String deptId=request.getParameter("deptId");
		if(deptId!=""){
			deptUuidd=Integer.parseInt(deptId);
		}
		String pbType=request.getParameter("pbType");
		json = service.getDutyList(request, year,month,deptUuidd,pbType);
		return json;
	}

	
	
	/**
	 * 当前登录的用户的值班安排列表
	 * @param request
	 * @return
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getPersonalDutyList.action")
	@ResponseBody
	public TeeJson getPersonalDutyList(HttpServletRequest request) throws java.text.ParseException{
		TeeJson json = new TeeJson();
		int year=Integer.parseInt(request.getParameter("year"));
		int month=Integer.parseInt(request.getParameter("month"));
		String pbType=request.getParameter("pbType");
		json = service.getPersonalDutyList(request, year,month,pbType);
		return json;
	}

	
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteDuty")
	@ResponseBody
	public TeeJson deleteDutyById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int uuid = Integer.parseInt(request.getParameter("uuid"));
		json = service.deleteDutyById(uuid);
		return json;
	}
	
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDutyById")
	@ResponseBody
	public TeeJson getDutyInfoById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int sid = Integer.parseInt(request.getParameter("sid"));
		json = service.getDutyInfoById(sid);
		return json;
	}
	
	/**
	 * 新增或者更新
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request) throws ParseException {
		TeeJson json = new TeeJson();
		TeeOnDutyModel model = new TeeOnDutyModel();
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = service.addOrUpdate(request, model);
		return json;
	}
	
	/**
	 * 批量添加
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/addOrUpdateBatch")
	@ResponseBody
	public TeeJson addOrUpdateBatch(HttpServletRequest request) throws ParseException {
		TeeJson json = new TeeJson();
		String userUuidStr=request.getParameter("userUuidStr");
		TeeOnDutyModel model = new TeeOnDutyModel();
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = service.addOrUpdateBatch(request, model,userUuidStr);
		return json;
	}
	
	
	/**
	 * 模板下载
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/downLoadTemplate")
	@ResponseBody
	public void downLoadTemplate(HttpServletRequest request,HttpServletResponse response) throws Exception{
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("系统日志");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 12); //字体高度
        font.setColor(HSSFFont.COLOR_NORMAL); //字体颜色
        font.setFontName("宋体"); //字体
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //宽度
        font.setItalic(false); //是否使用斜体
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		style.setFont(font);
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("值班人员");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("值班开始时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("值班结束时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("排班类型");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("值班类型");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("值班要求");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("备注");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		//设置每一列的宽度
        sheet.setDefaultColumnWidth(10);
		String fileName = "排班模板.xls";
		response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/msexcel;charset=UTF-8");
		OutputStream out=response.getOutputStream();
		wb.write(out);
		out.close();
	}
	
	
	/**
	 * 导入排班安排
	 */
	@RequestMapping("/importDutyInfos")
	@ResponseBody
	public TeeJson importDutyInfos(HttpServletRequest request) throws Exception{
		//System.out.println("controller");
		TeeJson json = service.importDutyInfos(request);
		return json;
	}
	
}
