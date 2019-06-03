package com.tianee.oa.subsys.contract.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
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

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.contract.model.TeeContractModel;
import com.tianee.oa.subsys.contract.service.TeeContractService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/contract")
public class TeeContractController {
	
	@Autowired
	private TeeContractService contractService;
	
	@ResponseBody
	@RequestMapping("/add")
	public TeeJson add(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeContractModel contractModel = 
				(TeeContractModel) TeeServletUtility.request2Object(request, TeeContractModel.class);
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		contractModel.setOperUserId(loginUser.getUuid());
		contractService.add(contractModel);
		json.setRtMsg("保存成功");
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public TeeJson update(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeContractModel contractModel = 
				(TeeContractModel) TeeServletUtility.request2Object(request, TeeContractModel.class);
		contractService.update(contractModel);
		json.setRtMsg("更新成功");
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public TeeJson delete(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		contractService.delete(sid);
		json.setRtMsg("删除成功");
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/get")
	public TeeJson get(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(contractService.get(sid));
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/datagridByView")
	public TeeEasyuiDataGridJson datagridByView(HttpServletRequest request,TeeDataGridModel dm){
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return contractService.datagridByView(requestData, dm);
	}
	
	@ResponseBody
	@RequestMapping("/datagridByManage")
	public TeeEasyuiDataGridJson datagridByManage(HttpServletRequest request,TeeDataGridModel dm){
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return contractService.datagridByManage(requestData, dm);
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
		HSSFSheet sheet = wb.createSheet("合同模板");
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
		cell.setCellValue("合同名称");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("合同编号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("合同分类");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("合同类型");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("所在部门");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("合同金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("合同开始时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("合同结束时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("合同签订时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("业务员");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("甲方单位");
		cell.setCellStyle(style);
		cell = row.createCell((short) 11);
		cell.setCellValue("乙方单位");
		cell.setCellStyle(style);
		cell = row.createCell((short) 12);
		cell.setCellValue("甲方负责人");
		cell.setCellStyle(style);
		cell = row.createCell((short) 13);
		cell.setCellValue("乙方负责人");
		cell.setCellStyle(style);
		cell = row.createCell((short) 14);
		cell.setCellValue("甲方联系方式");
		cell.setCellStyle(style);
		cell = row.createCell((short) 15);
		cell.setCellValue("乙方联系方式");
		cell.setCellStyle(style);
		cell = row.createCell((short) 16);
		cell.setCellValue("合同主要内容");
		cell.setCellStyle(style);
		cell = row.createCell((short) 17);
		cell.setCellValue("备注");
		cell.setCellStyle(style);
		//设置每一列的宽度
        sheet.setDefaultColumnWidth(20);
		String fileName = "合同模板.xls";
		response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/msexcel;charset=UTF-8");
		OutputStream out=response.getOutputStream();
		wb.write(out);
		out.close();
	}
	
	
	/**
	 * 导入合同
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/importContract")
	public TeeJson importContract(HttpServletRequest request){
		TeeJson json = contractService.importContract(request);
		return json;
	}
}
