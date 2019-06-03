package com.tianee.oa.core.base.pm.controller;

import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.base.pm.bean.TeeHumanDoc;
import com.tianee.oa.core.base.pm.bean.TeePmCustomerField;
import com.tianee.oa.core.base.pm.model.TeeHumanDocModel;
import com.tianee.oa.core.base.pm.model.TeePmCustomerFieldModel;
import com.tianee.oa.core.base.pm.service.TeeHumanDocService;
import com.tianee.oa.core.base.pm.service.TeePmCustomerFieldService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("humanDocController")
public class TeeHumanDocController {
	
	@Autowired
	private TeeHumanDocService humanService;
	
	@Autowired
	private TeePmCustomerFieldService fieldService;
	
	@RequestMapping("/addHumanDoc")
	@ResponseBody
	public TeeJson addHumanDoc(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanDocModel humanModel = new TeeHumanDocModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanModel);
		TeeHumanDoc td =humanService.addHumanDoc(request,humanModel);
		String attachmentSidStr = request.getParameter("attachmentSidStr");
		List<TeeAttachment> attachments = humanService.getAttachmentDao().getAttachmentsByIds(attachmentSidStr);
		if(attachments!= null && attachments.size()>0){
			for(TeeAttachment attach:attachments){
				attach.setModelId(String.valueOf(td.getSid()));
				humanService.getSimpleDaoSupport().update(attach);
			}
		}
		
		json.setRtMsg("添加成功");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return humanService.datagird(dm, requestDatas);
	}
	
	@RequestMapping("/datagrid2")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid2(TeeDataGridModel dm,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return humanService.datagrid2(dm, requestDatas);
	}
	

	@RequestMapping("/getModelById")
	@ResponseBody
	public TeeJson getModelById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		String type = TeeStringUtil.getString(request.getParameter("type"), "0");
		json.setRtData(humanService.getModelById(sid,type));
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取个人档案详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/getInfoById")
	@ResponseBody
	public TeeJson getInfoById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json =humanService.getInfoById(request);
		return json;
	}
	
	/**
	 * 根据档案id获取档案详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json =humanService.getById(request);
		return json;
	}
	
	@RequestMapping("/delHumanDoc")
	@ResponseBody
	public TeeJson delHumanDoc(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		humanService.delHumanDoc(sid);
		json.setRtState(true);
		json.setRtMsg("删除成功");
		return json;
	}
	
	@RequestMapping("/updateHumanDoc")
	@ResponseBody
	public TeeJson updateHumanDoc(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeHumanDocModel humanModel = new TeeHumanDocModel();
		TeeServletUtility.requestParamsCopyToObject(request, humanModel);
		String attachmentSidStr = request.getParameter("attachmentSidStr");
		List<TeeAttachment> attachments = humanService.getAttachmentDao().getAttachmentsByIds(attachmentSidStr);
		if(attachments!= null && attachments.size()>0){
			for(TeeAttachment attach:attachments){
				attach.setModelId(String.valueOf(humanModel.getSid()));
				humanService.getSimpleDaoSupport().update(attach);
			}
		}
		humanService.updateHumanDoc(request,humanModel);
		json.setRtState(true);
		json.setRtMsg("更新成功");
		return json;
	}
	
	@RequestMapping("/importPersonInfo")
	@ResponseBody
	public TeeJson importPersonInfo(HttpServletRequest request) throws Exception{
		TeeJson json = humanService.importPersonInfo(request);
		return json;
	}
	
	/**
	 * 根据身份证号信息查询其他相关身份信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findExtraInfoByIdCard")
	@ResponseBody
	public TeeJson findExtraInfoByIdCard(HttpServletRequest request){
		TeeJson json = humanService.findExtraInfoByIdCard(request);
		return json;
	}
	
	
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
		cell.setCellValue("人员姓名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("性别");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("籍贯");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("民族");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("所在岗位");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("在职状态");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("编号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("工号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("英文名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("身份证号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("出生日期");
		cell.setCellStyle(style);
		cell = row.createCell((short) 11);
		cell.setCellValue("婚姻状况");
		cell.setCellStyle(style);
		cell = row.createCell((short) 12);
		cell.setCellValue("健康状况");
		cell.setCellStyle(style);
		cell = row.createCell((short) 13);
		cell.setCellValue("政治面貌");
		cell.setCellStyle(style);
		cell = row.createCell((short) 14);
		cell.setCellValue("入党（团）时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 15);
		cell.setCellValue("户口类型");
		cell.setCellStyle(style);
		cell = row.createCell((short) 16);
		cell.setCellValue("户口所在地");
		cell.setCellStyle(style);
		cell = row.createCell((short) 17);
		cell.setCellValue("员工类型");
		cell.setCellStyle(style);
		cell = row.createCell((short) 18);
		cell.setCellValue("职务");
		cell.setCellStyle(style);
		cell = row.createCell((short) 19);
		cell.setCellValue("入职时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 20);
		cell.setCellValue("工龄");
		cell.setCellStyle(style);
		cell = row.createCell((short) 21);
		cell.setCellValue("总工龄");
		cell.setCellStyle(style);
		cell = row.createCell((short) 22);
		cell.setCellValue("手机号码");
		cell.setCellStyle(style);
		cell = row.createCell((short) 23);
		cell.setCellValue("电话号码");
		cell.setCellStyle(style);
		cell = row.createCell((short) 24);
		cell.setCellValue("电子邮件");
		cell.setCellStyle(style);
		cell = row.createCell((short) 25);
		cell.setCellValue("QQ号码");
		cell.setCellStyle(style);
		cell = row.createCell((short) 26);
		cell.setCellValue("MSN");
		cell.setCellStyle(style);
		cell = row.createCell((short) 27);
		cell.setCellValue("家庭地址");
		cell.setCellStyle(style);
		cell = row.createCell((short) 28);
		cell.setCellValue("其他联系地址");
		cell.setCellStyle(style);
		cell = row.createCell((short) 29);
		cell.setCellValue("学历");
		cell.setCellStyle(style);
		cell = row.createCell((short) 30);
		cell.setCellValue("学位");
		cell.setCellStyle(style);
		cell = row.createCell((short) 31);
		cell.setCellValue("毕业学校");
		cell.setCellStyle(style);
		cell = row.createCell((short) 32);
		cell.setCellValue("毕业时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 33);
		cell.setCellValue("专业");
		cell.setCellStyle(style);
		cell = row.createCell((short) 34);
		cell.setCellValue("计算机水平");
		cell.setCellStyle(style);
		cell = row.createCell((short) 35);
		cell.setCellValue("语种1");
		cell.setCellStyle(style);
		cell = row.createCell((short) 36);
		cell.setCellValue("语种2");
		cell.setCellStyle(style);
		cell = row.createCell((short) 37);
		cell.setCellValue("语种3");
		cell.setCellStyle(style);
		cell = row.createCell((short) 38);
		cell.setCellValue("特长");
		cell.setCellStyle(style);
		cell = row.createCell((short) 39);
		cell.setCellValue("职务情况");
		cell.setCellStyle(style);
		cell = row.createCell((short) 40);
		cell.setCellValue("社保缴纳情况");
		cell.setCellStyle(style);
		cell = row.createCell((short) 41);
		cell.setCellValue("体检记录");
		cell.setCellStyle(style);
		cell = row.createCell((short) 42);
		cell.setCellValue("备注");
		cell.setCellStyle(style);
		//设置每一列的宽度
        sheet.setDefaultColumnWidth(10);
		String fileName = "人事档案导入模板.xls";
		response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/msexcel;charset=UTF-8");
		OutputStream out=response.getOutputStream();
		wb.write(out);
		out.close();
	}
	
	
	
	
	
	@RequestMapping("/exportHumanDoc")
	@ResponseBody
	public void exportHumanDoc(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map requestDatas = TeeServletUtility.getParamMap(request);
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
		cell.setCellValue("人员姓名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("性别");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("籍贯");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("民族");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("所在岗位");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("在职状态");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("编号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("工号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("英文名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("身份证号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("出生日期");
		cell.setCellStyle(style);
		cell = row.createCell((short) 11);
		cell.setCellValue("婚姻状况");
		cell.setCellStyle(style);
		cell = row.createCell((short) 12);
		cell.setCellValue("健康状况");
		cell.setCellStyle(style);
		cell = row.createCell((short) 13);
		cell.setCellValue("政治面貌");
		cell.setCellStyle(style);
		cell = row.createCell((short) 14);
		cell.setCellValue("入党（团）时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 15);
		cell.setCellValue("户口类型");
		cell.setCellStyle(style);
		cell = row.createCell((short) 16);
		cell.setCellValue("户口所在地");
		cell.setCellStyle(style);
		cell = row.createCell((short) 17);
		cell.setCellValue("员工类型");
		cell.setCellStyle(style);
		cell = row.createCell((short) 18);
		cell.setCellValue("职务");
		cell.setCellStyle(style);
		cell = row.createCell((short) 19);
		cell.setCellValue("入职时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 20);
		cell.setCellValue("工龄");
		cell.setCellStyle(style);
		cell = row.createCell((short) 21);
		cell.setCellValue("总工龄");
		cell.setCellStyle(style);
		cell = row.createCell((short) 22);
		cell.setCellValue("手机号码");
		cell.setCellStyle(style);
		cell = row.createCell((short) 23);
		cell.setCellValue("电话号码");
		cell.setCellStyle(style);
		cell = row.createCell((short) 24);
		cell.setCellValue("电子邮件");
		cell.setCellStyle(style);
		cell = row.createCell((short) 25);
		cell.setCellValue("QQ号码");
		cell.setCellStyle(style);
		cell = row.createCell((short) 26);
		cell.setCellValue("MSN");
		cell.setCellStyle(style);
		cell = row.createCell((short) 27);
		cell.setCellValue("家庭地址");
		cell.setCellStyle(style);
		cell = row.createCell((short) 28);
		cell.setCellValue("其他联系地址");
		cell.setCellStyle(style);
		cell = row.createCell((short) 29);
		cell.setCellValue("学历");
		cell.setCellStyle(style);
		cell = row.createCell((short) 30);
		cell.setCellValue("学位");
		cell.setCellStyle(style);
		cell = row.createCell((short) 31);
		cell.setCellValue("毕业学校");
		cell.setCellStyle(style);
		cell = row.createCell((short) 32);
		cell.setCellValue("毕业时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 33);
		cell.setCellValue("专业");
		cell.setCellStyle(style);
		cell = row.createCell((short) 34);
		cell.setCellValue("计算机水平");
		cell.setCellStyle(style);
		cell = row.createCell((short) 35);
		cell.setCellValue("语种1");
		cell.setCellStyle(style);
		cell = row.createCell((short) 36);
		cell.setCellValue("语种2");
		cell.setCellStyle(style);
		cell = row.createCell((short) 37);
		cell.setCellValue("语种3");
		cell.setCellStyle(style);
		cell = row.createCell((short) 38);
		cell.setCellValue("特长");
		cell.setCellStyle(style);
		cell = row.createCell((short) 39);
		cell.setCellValue("职务情况");
		cell.setCellStyle(style);
		cell = row.createCell((short) 40);
		cell.setCellValue("社保缴纳情况");
		cell.setCellStyle(style);
		cell = row.createCell((short) 41);
		cell.setCellValue("体检记录");
		cell.setCellStyle(style);
		cell = row.createCell((short) 42);
		cell.setCellValue("备注");
		cell.setCellStyle(style);
		
		List<TeeHumanDocModel> list = humanService.getDocListByConditon(requestDatas);
		for (int i = 0; i < list.size(); i++)
		{
			row = sheet.createRow((int) i + 1);
			TeeHumanDocModel model = (TeeHumanDocModel) list.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell((short) 0).setCellValue(model.getPersonName());
			row.createCell((short) 1).setCellValue(model.getGender());
			row.createCell((short) 2).setCellValue(model.getNativePlace());
			row.createCell((short) 3).setCellValue(model.getEthnicity());
			row.createCell((short) 4).setCellValue(model.getDeptIdName());
			row.createCell((short) 5).setCellValue(model.getStatusTypeDesc());
			row.createCell((short) 6).setCellValue(model.getCodeNumber());
			row.createCell((short) 7).setCellValue(model.getWorkNumber());
			row.createCell((short) 8).setCellValue(model.getEnglishName());
			row.createCell((short) 9).setCellValue(model.getIdCard());
			row.createCell((short) 10).setCellValue(model.getBirthdayDesc());
			row.createCell((short) 11).setCellValue(model.getMarriageDesc());
			row.createCell((short) 12).setCellValue(model.getHealth());
			row.createCell((short) 13).setCellValue(model.getPoliticsDesc());
			row.createCell((short) 14).setCellValue(model.getJoinPartyDateDesc());
			row.createCell((short) 15).setCellValue(model.getHouseholdDesc());
			row.createCell((short) 16).setCellValue(model.getHouseholdPlace());
			row.createCell((short) 17).setCellValue(model.getEmployeeTypeDesc());
			row.createCell((short) 18).setCellValue(model.getPostState());
			row.createCell((short) 19).setCellValue(model.getJoinDateDesc());
			row.createCell((short) 20).setCellValue(model.getWorkYears());
			row.createCell((short) 21).setCellValue(model.getTotalYears());
			row.createCell((short) 22).setCellValue(model.getMobileNo());
			row.createCell((short) 23).setCellValue(model.getTelNo());
			row.createCell((short) 24).setCellValue(model.getEmail());
			row.createCell((short) 25).setCellValue(model.getQqNo());
			row.createCell((short) 26).setCellValue(model.getMsn());
			row.createCell((short) 27).setCellValue(model.getAddress());
			row.createCell((short) 28).setCellValue(model.getOtherAddress());
			row.createCell((short) 29).setCellValue(model.getEducationDegreeDesc());
			row.createCell((short) 30).setCellValue(model.getDegreeDesc());
			row.createCell((short) 31).setCellValue(model.getGraduateSchool());
			row.createCell((short) 32).setCellValue(model.getGraduateDateDesc());
			row.createCell((short) 33).setCellValue(model.getMajor());
			row.createCell((short) 34).setCellValue(model.getComputerLevel());
			row.createCell((short) 35).setCellValue(model.getLanguage1());
			row.createCell((short) 36).setCellValue(model.getLanguage2());
			row.createCell((short) 37).setCellValue(model.getLanguage3());
			row.createCell((short) 38).setCellValue(model.getSkill());
			row.createCell((short) 39).setCellValue(model.getPostDesc());
			row.createCell((short) 40).setCellValue(model.getShebaoDesc());
			row.createCell((short) 41).setCellValue(model.getHealthDesc());
			row.createCell((short) 42).setCellValue(model.getRemark());
		}
		//设置每一列的宽度
        sheet.setDefaultColumnWidth(10);
		String fileName = "人事档案信息.xls";
		response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/msexcel;charset=UTF-8");
		OutputStream out=response.getOutputStream();
		wb.write(out);
		out.close();
	}
	
	
	
	/**
	 * 下载人事档案模板
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/downPdLoadTemplate")
	@ResponseBody
	public void downLoadPdTemplate(HttpServletRequest request,HttpServletResponse response,TeeDataGridModel dm) throws Exception{
		TeeEasyuiDataGridJson datagrid = fieldService.datagrid2();
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
		cell.setCellValue("姓名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("用户名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("部门");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("角色");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("身份证号码");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("档案编号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("籍贯");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("工号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("员工状态");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("员工类型");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("英文名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 11);
		cell.setCellValue("性别");
		cell.setCellStyle(style);
		cell = row.createCell((short) 12);
		cell.setCellValue("出生日期");
		cell.setCellStyle(style);
		cell = row.createCell((short) 13);
		cell.setCellValue("民族");
		cell.setCellStyle(style);
		cell = row.createCell((short) 14);
		cell.setCellValue("职务");
		cell.setCellStyle(style);
		cell = row.createCell((short) 15);
		cell.setCellValue("婚姻状况");
		cell.setCellStyle(style);
		cell = row.createCell((short) 16);
		cell.setCellValue("毕业院校");
		cell.setCellStyle(style);
		cell = row.createCell((short) 17);
		cell.setCellValue("户口类型");
		cell.setCellStyle(style);
		cell = row.createCell((short) 18);
		cell.setCellValue("健康状况");
		cell.setCellStyle(style);
		cell = row.createCell((short) 19);
		cell.setCellValue("户口所在地");
		cell.setCellStyle(style);
		cell = row.createCell((short) 20);
		cell.setCellValue("入职时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 21);
		cell.setCellValue("政治面貌");
		cell.setCellStyle(style);
		cell = row.createCell((short) 22);
		cell.setCellValue("入党（团）时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 23);
		cell.setCellValue("专业");
		cell.setCellStyle(style);
		cell = row.createCell((short) 24);
		cell.setCellValue("毕业时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 25);
		cell.setCellValue("学历");
		cell.setCellStyle(style);
		cell = row.createCell((short) 26);
		cell.setCellValue("学位");
		cell.setCellStyle(style);
		cell = row.createCell((short) 27);
		cell.setCellValue("手机号码");
		cell.setCellStyle(style);
		cell = row.createCell((short) 28);
		cell.setCellValue("电话号码");
		cell.setCellStyle(style);
		cell = row.createCell((short) 29);
		cell.setCellValue("电子邮件");
		cell.setCellStyle(style);
		cell = row.createCell((short) 30);
		cell.setCellValue("QQ号码");
		cell.setCellStyle(style);
		cell = row.createCell((short) 31);
		cell.setCellValue("MSN");
		cell.setCellStyle(style);
		cell = row.createCell((short) 32);
		cell.setCellValue("家庭地址");
		cell.setCellStyle(style);
		cell = row.createCell((short) 33);
		cell.setCellValue("其他联系地址");
		cell.setCellStyle(style);
		cell = row.createCell((short) 34);
		cell.setCellValue("保险系数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 35);
		List<TeePmCustomerFieldModel> rows = datagrid.getRows();
		int i=36;
		if(rows!=null && rows.size()>0){
			for(TeePmCustomerFieldModel f:rows){
				cell.setCellValue(f.getExtendFiledName());
				cell.setCellStyle(style);
				cell = row.createCell((short) i);
				i++;
			}
		}
		
		//设置每一列的宽度
        sheet.setDefaultColumnWidth(10);
		String fileName = "人事档案导入模板.xls";
		response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/msexcel;charset=UTF-8");
		OutputStream out=response.getOutputStream();
		wb.write(out);
		out.close();
	}
	
	
	/**
	 * 导入人事档案
	 */
	@RequestMapping("/importHumanDocInfo")
	@ResponseBody
	public TeeJson importHumanDocInfo(HttpServletRequest request) throws Exception{
		//System.out.println("controller");
		TeeEasyuiDataGridJson datagrid = fieldService.datagrid2();
		List<TeePmCustomerFieldModel> rows = datagrid.getRows();
		TeeJson json = humanService.importHumanDocInfo(request,rows);
		return json;
	}
	
	/**
	 * 获取自定义字段
	 * @param request
	 * @return
	 */
	@RequestMapping("/getListFieldByHuman")
	@ResponseBody
	private TeeJson getListFieldByHuman(HttpServletRequest request){
		TeeJson json = new TeeJson();
		List<TeePmCustomerField> list = new ArrayList<TeePmCustomerField>();
		list = fieldService.getListFieldByHuman(request);
		json.setRtData(list);
		json.setRtMsg("查询成功！");
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取自定义字段作为查询的字段
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getQueryFieldListById")
	public TeeJson getQueryFieldListById(HttpServletRequest request){
		return fieldService.getQueryFieldListById(request);
	}
	
	/**
	 * 获取自定义字段作为显示的字段
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getShowFieldListById")
	public TeeJson getShowFieldListById(HttpServletRequest request){
		return fieldService.getShowFieldListById(request);
	}
	
	 /**
	 * 导出数据会议
	 * */

	@SuppressWarnings("deprecation")
	@RequestMapping("/exportExcel")
	public String exportExcel2(String params, HttpServletRequest request,
			HttpServletResponse response) {
		TeeEasyuiDataGridJson datagrid = fieldService.datagrid2();
		List<TeePmCustomerFieldModel> rows = datagrid.getRows();
		List<Map<String,String>> model=humanService.findHmanAll(request,rows);
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("档案信息");
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
		
		// 第三行样式
		HSSFFont font3 = wb.createFont();
	    font3.setFontHeightInPoints((short) 10); // 字体高度
		font3.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色
		font3.setFontName("宋体"); // 字体
		font3.setItalic(false); // 是否使用斜体
		HSSFCellStyle style3 = wb.createCellStyle();
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		style3.setFont(font3);
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("姓名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("用户名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("部门");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("角色");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("身份证号码");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("档案编号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("籍贯");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("工号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("员工状态");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("员工类型");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("英文名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 11);
		cell.setCellValue("性别");
		cell.setCellStyle(style);
		cell = row.createCell((short) 12);
		cell.setCellValue("出生日期");
		cell.setCellStyle(style);
		cell = row.createCell((short) 13);
		cell.setCellValue("民族");
		cell.setCellStyle(style);
		cell = row.createCell((short) 14);
		cell.setCellValue("职务");
		cell.setCellStyle(style);
		cell = row.createCell((short) 15);
		cell.setCellValue("婚姻状况");
		cell.setCellStyle(style);
		cell = row.createCell((short) 16);
		cell.setCellValue("毕业院校");
		cell.setCellStyle(style);
		cell = row.createCell((short) 17);
		cell.setCellValue("户口类型");
		cell.setCellStyle(style);
		cell = row.createCell((short) 18);
		cell.setCellValue("健康状况");
		cell.setCellStyle(style);
		cell = row.createCell((short) 19);
		cell.setCellValue("户口所在地");
		cell.setCellStyle(style);
		cell = row.createCell((short) 20);
		cell.setCellValue("入职时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 21);
		cell.setCellValue("政治面貌");
		cell.setCellStyle(style);
		cell = row.createCell((short) 22);
		cell.setCellValue("入党（团）时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 23);
		cell.setCellValue("专业");
		cell.setCellStyle(style);
		cell = row.createCell((short) 24);
		cell.setCellValue("毕业时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 25);
		cell.setCellValue("学历");
		cell.setCellStyle(style);
		cell = row.createCell((short) 26);
		cell.setCellValue("学位");
		cell.setCellStyle(style);
		cell = row.createCell((short) 27);
		cell.setCellValue("手机号码");
		cell.setCellStyle(style);
		cell = row.createCell((short) 28);
		cell.setCellValue("电话号码");
		cell.setCellStyle(style);
		cell = row.createCell((short) 29);
		cell.setCellValue("电子邮件");
		cell.setCellStyle(style);
		cell = row.createCell((short) 30);
		cell.setCellValue("QQ号码");
		cell.setCellStyle(style);
		cell = row.createCell((short) 31);
		cell.setCellValue("MSN");
		cell.setCellStyle(style);
		cell = row.createCell((short) 32);
		cell.setCellValue("家庭地址");
		cell.setCellStyle(style);
		cell = row.createCell((short) 33);
		cell.setCellValue("其他联系地址");
		cell.setCellStyle(style);
		cell = row.createCell((short) 34);
		cell.setCellValue("保险系数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 35);
		int i=36;
		if(rows!=null && rows.size()>0){
			for(TeePmCustomerFieldModel f:rows){
				cell.setCellValue(f.getExtendFiledName());
				cell.setCellStyle(style);
				cell = row.createCell((short) i);
				i++;
			}
		}
		
	    int j=1;
		for (int m = 0; m < model.size(); m++) {
			row = sheet.createRow((int) j);
			 Map<String, String> map = model.get(m);
			// 第四步，创建单元格，并设置值
			//序号
//			HSSFCell createCell = row.createCell((short) 0);
//			createCell.setCellValue(m + 1);
//			createCell.setCellStyle(style);
			String userName = map.get("userName");
			if (userName != null) {//
				HSSFCell createCell2 = row.createCell((short) 0);
				createCell2.setCellValue(userName);
				createCell2.setCellStyle(style3);
			}
			if (userName != null) {//
				HSSFCell createCell2 = row.createCell((short) 1);
				createCell2.setCellValue(userName);
				createCell2.setCellStyle(style3);
			}
			String deptIdName = map.get("deptIdName");
			if (deptIdName != null) {//
				HSSFCell createCell2 = row.createCell((short) 2);
				createCell2.setCellValue(deptIdName);
				createCell2.setCellStyle(style3);
			}
			String roleName = map.get("roleName");
			if (roleName != null) {//角色
				HSSFCell createCell2 = row.createCell((short) 3);
				createCell2.setCellValue(roleName);
				createCell2.setCellStyle(style3);
			}
			String idCard = map.get("idCard");
			if (idCard != null) {//身份证号
				HSSFCell createCell2 = row.createCell((short) 4);
				createCell2.setCellValue(idCard);
				createCell2.setCellStyle(style3);
			}
			String codeNumber = map.get("codeNumber");
			if (idCard != null) {//档案编号
				HSSFCell createCell2 = row.createCell((short) 5);
				createCell2.setCellValue(codeNumber);
				createCell2.setCellStyle(style3);
			}
			String nativePlace = map.get("nativePlace");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 6);
				createCell2.setCellValue(nativePlace);
				createCell2.setCellStyle(style3);
			}
			
			//"工号");
			String workNumber = map.get("workNumber");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 7);
				createCell2.setCellValue(workNumber);
				createCell2.setCellStyle(style3);
			}
			//"员工状态");
			String statusTypeDesc = map.get("statusTypeDesc");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 8);
				createCell2.setCellValue(statusTypeDesc);
				createCell2.setCellStyle(style3);
			}
			//"员工类型");
			String employeeTypeDesc = map.get("employeeTypeDesc");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 9);
				createCell2.setCellValue(employeeTypeDesc);
				createCell2.setCellStyle(style3);
			}
			//"英文名");
			String englishName = map.get("englishName");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 10);
				createCell2.setCellValue(englishName);
				createCell2.setCellStyle(style3);
			}
			//"性别");
			String gender = map.get("gender");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 11);
				createCell2.setCellValue(gender);
				createCell2.setCellStyle(style3);
			}
			//"出生日期");
			String birthdayDesc = map.get("birthdayDesc");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 12);
				createCell2.setCellValue(birthdayDesc);
				createCell2.setCellStyle(style3);
			}
			//"民族");
			String ethnicity = map.get("ethnicity");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 13);
				createCell2.setCellValue(ethnicity);
				createCell2.setCellStyle(style3);
			}
			//"职务");
			String postState = map.get("postState");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 14);
				createCell2.setCellValue(postState);
				createCell2.setCellStyle(style3);
			}
			//"婚姻状况");
			String marriageDesc = map.get("marriageDesc");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 15);
				createCell2.setCellValue(marriageDesc);
				createCell2.setCellStyle(style3);
			}
			//"毕业院校");
			String graduateSchool = map.get("graduateSchool");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 16);
				createCell2.setCellValue(graduateSchool);
				createCell2.setCellStyle(style3);
			}
			//"户口类型");
			String householdDesc = map.get("householdDesc");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 17);
				createCell2.setCellValue(householdDesc);
				createCell2.setCellStyle(style3);
			}
			//"健康状况");
			String health = map.get("health");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 18);
				createCell2.setCellValue(health);
				createCell2.setCellStyle(style3);
			}
			//"户口所在地");
			String householdPlace = map.get("householdPlace");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 19);
				createCell2.setCellValue(householdPlace);
				createCell2.setCellStyle(style3);
			}
			//"入职时间");
			String joinDateDesc = map.get("joinDateDesc");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 20);
				createCell2.setCellValue(joinDateDesc);
				createCell2.setCellStyle(style3);
			}
			//"政治面貌");
			String politicsDesc = map.get("politicsDesc");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 21);
				createCell2.setCellValue(politicsDesc);
				createCell2.setCellStyle(style3);
			}
			//"入党（团）时间");
			String joinPartyDateDesc = map.get("joinPartyDateDesc");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 22);
				createCell2.setCellValue(joinPartyDateDesc);
				createCell2.setCellStyle(style3);
			}
			//"专业");
			String major = map.get("major");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 23);
				createCell2.setCellValue(major);
				createCell2.setCellStyle(style3);
			}
			//"毕业时间");
			String graduateDateDesc = map.get("graduateDateDesc");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 24);
				createCell2.setCellValue(graduateDateDesc);
				createCell2.setCellStyle(style3);
			}
			//"学历");
			String educationDegreeDesc = map.get("educationDegreeDesc");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 25);
				createCell2.setCellValue(educationDegreeDesc);
				createCell2.setCellStyle(style3);
			}
			//"学位");
			String degreeDesc = map.get("degreeDesc");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 26);
				createCell2.setCellValue(degreeDesc);
				createCell2.setCellStyle(style3);
			}
			//"手机号码");
			String mobileNo = map.get("mobileNo");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 27);
				createCell2.setCellValue(mobileNo);
				createCell2.setCellStyle(style3);
			}
			//"电话号码");
			String telNo = map.get("telNo");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 28);
				createCell2.setCellValue(telNo);
				createCell2.setCellStyle(style3);
			}
			//"电子邮件");
			String email = map.get("email");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 29);
				createCell2.setCellValue(email);
				createCell2.setCellStyle(style3);
			}
			
			//"QQ号码");
			String qqNo = map.get("qqNo");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 30);
				createCell2.setCellValue(qqNo);
				createCell2.setCellStyle(style3);
			}
			//"MSN");
			String msn = map.get("msn");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 31);
				createCell2.setCellValue(msn);
				createCell2.setCellStyle(style3);
			}
			//"家庭地址");
			String address = map.get("address");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 32);
				createCell2.setCellValue(address);
				createCell2.setCellStyle(style3);
			}
			//"其他联系地址");
			String otherAddress = map.get("otherAddress");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 33);
				createCell2.setCellValue(otherAddress);
				createCell2.setCellStyle(style3);
			}
			//"保险系数");
			String insuranceName = map.get("insuranceName");
			if (idCard != null) {//"籍贯");
				HSSFCell createCell2 = row.createCell((short) 34);
				createCell2.setCellValue(insuranceName);
				createCell2.setCellStyle(style3);
			}
			int n=34;
			if(rows!=null && rows.size()>0){
				for(TeePmCustomerFieldModel f:rows){
					n++;
					String extra = map.get("EXTRA_"+f.getSid());
					if (idCard != null) {//"籍贯");
						HSSFCell createCell2 = row.createCell((short) n);
						createCell2.setCellValue(extra);
						createCell2.setCellStyle(style3);
					}
				}
			}
			
			j++;
		}
		// 设置每一列的宽度
		sheet.setDefaultColumnWidth(20);
		//第一列的宽度
		sheet.setColumnWidth(0, 2500);
		try {
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			String months = "";
			if (month < 10) {
				months = "0" + month;
			} else {
				months = "" + month;
			}
			String times = "" + year + "" + months;
			String fileName = "档案信息" + times + ".xls";
			response.setHeader("Content-disposition", "attachment;filename="
					+ URLEncoder.encode(fileName, "UTF-8"));
			response.setContentType("application/msexcel;charset=UTF-8");
			OutputStream out = response.getOutputStream();
			wb.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//批量设置保险账套
	@ResponseBody
	@RequestMapping("/getInsuranceAll")
	public TeeJson getInsuranceAll(HttpServletRequest request){
		return humanService.getInsuranceAll(request);
	}
}
