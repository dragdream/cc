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

import com.tianee.oa.core.general.model.TeeSysLogModel;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.customer.model.TeeCrmCustomerInfoModel;
import com.tianee.oa.subsys.crm.core.customer.service.TeeCrmCustomerInfoService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;


@Controller
@RequestMapping("/TeeCrmCustomerInfoController")
public class TeeCrmCustomerInfoController extends BaseController{
	@Autowired
	private TeeCrmCustomerInfoService customerInfoService;
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , TeeCrmCustomerInfoModel model) {
		TeeJson json = new TeeJson();
		json = customerInfoService.addOrUpdate(request , model);
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
	public TeeJson deleteById(HttpServletRequest request , TeeCrmCustomerInfoModel model) {
		TeeJson json = new TeeJson();
		String sids = TeeStringUtil.getString(request.getParameter("sids"), "0");
		json = customerInfoService.deleteByIdService(request ,sids);
		return json;
	}
	
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/collarCustomer")
	@ResponseBody
	public TeeJson collarCustomer(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
		json = customerInfoService.collarCustomer(request,sid);
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
	public TeeJson getById(HttpServletRequest request , TeeCrmCustomerInfoModel model) {
		TeeJson json = new TeeJson();
		json = customerInfoService.getById(request , model);
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
		return customerInfoService.datagird(dm, requestDatas);
	}
	
	
	@RequestMapping("/exportCustomerInfo")
	@ResponseBody
	public void exportCustomerInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		List<TeeCrmCustomerInfoModel> list = customerInfoService.getTotalByConditon(requestDatas);
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("客户信息");
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
		cell.setCellValue("客户名称");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("负责人");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("公司电话");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("移动电话");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("客户类型");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("所属行业");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("公司规模");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("客户来源");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("公司地址");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("公司网址");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("传真");
		cell.setCellStyle(style);
		cell = row.createCell((short) 11);
		cell.setCellValue("邮编");
		cell.setCellStyle(style);
		cell = row.createCell((short) 12);
		cell.setCellValue("邮件");
		cell.setCellStyle(style);
		cell = row.createCell((short) 13);
		cell.setCellValue("QQ");
		cell.setCellStyle(style);
		cell = row.createCell((short) 14);
		cell.setCellValue("关系等级");
		cell.setCellStyle(style);
		cell = row.createCell((short) 15);
		cell.setCellValue("重要程度");
		cell.setCellStyle(style);
		cell = row.createCell((short) 16);
		cell.setCellValue("投资来源");
		cell.setCellStyle(style);
		cell = row.createCell((short) 17);
		cell.setCellValue("信用等级");
		cell.setCellStyle(style);
		cell = row.createCell((short) 18);
		cell.setCellValue("销售市场");
		cell.setCellStyle(style);
		cell = row.createCell((short) 19);
		cell.setCellValue("开票单位");
		cell.setCellStyle(style);
		cell = row.createCell((short) 20);
		cell.setCellValue("开票单位地址");
		cell.setCellStyle(style);
		cell = row.createCell((short) 21);
		cell.setCellValue("银行帐号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 22);
		cell.setCellValue("税号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 23);
		cell.setCellValue("开户银行");
		cell.setCellStyle(style);
		cell = row.createCell((short) 24);
		cell.setCellValue("开票联系电话");
		cell.setCellStyle(style);
		
		for (int i = 0; i < list.size(); i++)
		{
			row = sheet.createRow((int) i + 1);
			TeeCrmCustomerInfoModel model = (TeeCrmCustomerInfoModel) list.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell((short) 0).setCellValue(model.getCustomerName());
			row.createCell((short) 1).setCellValue(model.getManagePersonName());
			row.createCell((short) 2).setCellValue(model.getCompanyPhone());
			cell = row.createCell((short) 3);
			cell.setCellValue(model.getCompanyMobile());
			row.createCell((short) 4).setCellValue(model.getCustomerTypeDesc());
			row.createCell((short) 5).setCellValue(model.getIndustryDesc());
			row.createCell((short) 6).setCellValue(model.getCompanyScaleDesc());
			row.createCell((short) 7).setCellValue(model.getCustomerSourceDesc());
			row.createCell((short) 8).setCellValue(model.getCompanyAddress());
			row.createCell((short) 9).setCellValue(model.getCompanyUrl());
			row.createCell((short) 10).setCellValue(model.getCompanyFax());
			row.createCell((short) 11).setCellValue(model.getCompanyZipCode());
			row.createCell((short) 12).setCellValue(model.getCompanyEmail());
			row.createCell((short) 13).setCellValue(model.getCompanyQQ());
			row.createCell((short) 14).setCellValue(model.getRelationLevelDesc());
			row.createCell((short) 15).setCellValue(model.getImportantLevelDesc());
			row.createCell((short) 16).setCellValue(model.getSourcesOfInvestmentDesc());
			row.createCell((short) 17).setCellValue(model.getTrustLevelDesc());
			row.createCell((short) 18).setCellValue(model.getSalesMarketDesc());
			row.createCell((short) 19).setCellValue(model.getBillUnitName());
			row.createCell((short) 20).setCellValue(model.getBillUnitAddress());
			row.createCell((short) 21).setCellValue(model.getBankAccount());
			row.createCell((short) 22).setCellValue(model.getTaxNo());
			row.createCell((short) 23).setCellValue(model.getBankName());
			row.createCell((short) 24).setCellValue(model.getBillPhone());
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
			String fileName = "客户信息"+times+".xls";
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