package com.tianee.oa.subsys.salManage.controller;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.calendar.model.TeeCalendarAffairModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.salManage.bean.TeeSalItem;
import com.tianee.oa.subsys.salManage.service.TeeSalDataPersonService;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.file.TeeAOPExcleUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;

/**
 * 工资导入导出
 * @author think
 *
 */
@Controller
@RequestMapping("/teeSalImportExportController")
public class TeeSalImportExportController {
	@Autowired
	private TeeSalDataPersonService dataPersonService;
	
	/**
	 * 导出模板
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportSalModule")
	public String export(HttpServletRequest request ,TeeSalItem model, HttpServletResponse response ) throws Exception {
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);	
		response.setCharacterEncoding("GBK");
		try {
			String fileName = URLEncoder.encode("工资数据模板.xls", "UTF-8");
			fileName = fileName.replaceAll("\\+", "%20");
			response.setHeader("Cache-control", "private");
			response.setHeader("Cache-Control", "maxage=3600");
			response.setHeader("Pragma", "public");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ fileName + "\"");
			ArrayList<TeeDataRecord> dbL = dataPersonService.exportSalModule(request);
			//OutputStream out=response.getOutputStream();
			//wb.write(out);
			TeeAOPExcleUtil.writeExc(response.getOutputStream(), dbL);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return null;
	}
	
	/**
	 * 导入
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/importSal")
	public void importSal(HttpServletRequest request , HttpServletResponse response ) throws Exception {
		PrintWriter out = response.getWriter();
		TeeJson json = dataPersonService.importSal(request);
		out.write("<script>\n\r");
		out.write("var data = "+TeeJsonUtil.toJson(json)+";\n\r");
		out.write("parent.importInfo(data.rtData,data.rtMsg);\n\r;");
		out.write("</script>\n\r");
//		System.out.println(TeeJsonUtil.toJson(json));
	}
	
}
