package com.tianee.oa.subsys.crm.core.customer.controller;

import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.Calendar;
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

import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.customer.model.TeeCrmContactUserModel;
import com.tianee.oa.subsys.crm.core.customer.service.TeeCrmContactUserService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;


@Controller
@RequestMapping("/TeeCrmContactUserController")
public class TeeCrmContactUserController extends BaseController{
	@Autowired
	private TeeCrmContactUserService contactUserService;
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , TeeCrmContactUserModel model) {
		TeeJson json = new TeeJson();
		json = contactUserService.addOrUpdate(request , model);
		return json;
	}
	
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public TeeJson deleteById(HttpServletRequest request , TeeCrmContactUserModel model) {
		TeeJson json = new TeeJson();
		String sids = TeeStringUtil.getString(request.getParameter("sids"), "0");
		json = contactUserService.deleteByIdService(request , sids);
		return json;
	}
	

	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request , TeeCrmContactUserModel model) {
		TeeJson json = new TeeJson();
		json = contactUserService.getById(request , model);
		return json;
	}
	
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getContactUserList")
	@ResponseBody
	public TeeJson getContactUserList(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int customerId = TeeStringUtil.getInteger(request.getParameter("customerId"),0);
		json = contactUserService.getContactUserList(customerId);
		return json;
	}
	
	/**
	 * @author ny
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return contactUserService.datagird(dm, requestDatas);
	}
	
	@RequestMapping("/exportContactUser")
	@ResponseBody
	public void exportContactUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		List<TeeCrmContactUserModel> list = contactUserService.getTotalByConditon(requestDatas);
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("联系人信息");
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
		cell.setCellValue("所属客户");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("联系人姓名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("性别");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("所属部门");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("重要程度");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("出生日期");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("状态");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("简介");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("联系电话");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("分机电话");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("移动电话");
		cell.setCellStyle(style);
		cell = row.createCell((short) 11);
		cell.setCellValue("传真");
		cell.setCellStyle(style);
		cell = row.createCell((short) 12);
		cell.setCellValue("邮件");
		cell.setCellStyle(style);
		cell = row.createCell((short) 13);
		cell.setCellValue("QQ");
		cell.setCellStyle(style);
		
		for (int i = 0; i < list.size(); i++)
		{
			row = sheet.createRow((int) i + 1);
			TeeCrmContactUserModel model = (TeeCrmContactUserModel) list.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell((short) 0).setCellValue(model.getCustomerName());
			row.createCell((short) 1).setCellValue(model.getName());
			row.createCell((short) 2).setCellValue(model.getGenderDesc());
			cell = row.createCell((short) 3);
			cell.setCellValue(model.getDepartment());
			row.createCell((short) 4).setCellValue(model.getImportantDesc());
			row.createCell((short) 5).setCellValue(model.getBirthdayDesc());
			row.createCell((short) 6).setCellValue(model.getPosDesc());
			row.createCell((short) 7).setCellValue(model.getBrief());
			row.createCell((short) 8).setCellValue(model.getTelephone());
			row.createCell((short) 9).setCellValue(model.getTelephone1());
			row.createCell((short) 10).setCellValue(model.getMobilePhone());
			row.createCell((short) 11).setCellValue(model.getFax());
			row.createCell((short) 12).setCellValue(model.getEmail());
			row.createCell((short) 13).setCellValue(model.getQq());
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
			String fileName = "客户联系人信息"+times+".xls";
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