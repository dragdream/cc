package com.tianee.oa.core.org.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.util.JSONUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dingtalk.openapi.demo.department.Department;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.model.TeePersonModel;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.file.TeeCSVUtil;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;


@Controller
@RequestMapping("/orgImportExport")
public class TeeOrgImportAndExportController extends BaseController{

	@Autowired
	private TeeDeptService deptService;
	
	@Autowired
	private TeePersonService personService;
	
	/**
	 *导入部门
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/importDept")
	@ResponseBody
	public TeeJson importDept(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/*response.setCharacterEncoding("GBK");*/
		TeeJson json = deptService.importDept(request);
		return json;
	}
	
	
	/**
	 * 导出部门CSV文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportDept.action")
	public String exportDeptCsv(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("GBK");
		try {
			String fileName = URLEncoder.encode("OA部门.csv", "UTF-8");
			fileName = fileName.replaceAll("\\+", "%20");
			response.setHeader("Cache-control", "private");
			response.setHeader("Cache-Control", "maxage=3600");
			response.setHeader("Pragma", "public");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ fileName + "\"");
			ArrayList<TeeDataRecord> dbL = deptService.exportAllDeptInfo();
			TeeCSVUtil.CVSWrite(response.getWriter(), dbL);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return null;
	}
	
	
	/**
	 *导入用户
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/importUser")
	@ResponseBody
	public TeeJson importUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//response.setCharacterEncoding("GBK");
		TeeJson json = personService.importUser(request);
		return json;
	}
	
	
	/**
	 * 按条件 导出人员
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportToCsv")
	public String exportToCsv(HttpServletRequest request , TeePersonModel model , HttpServletResponse response ) throws Exception {
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);	
		response.setCharacterEncoding("GBK");
		try {
			String fileName = URLEncoder.encode("OA人员.csv", "UTF-8");
			fileName = fileName.replaceAll("\\+", "%20");
			response.setHeader("Cache-control", "private");
			response.setHeader("Cache-Control", "maxage=3600");
			response.setHeader("Pragma", "public");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ fileName + "\"");
			
			
			Map params=TeeJsonUtil.JsonStr2Map(request.getParameter("param"));
			if(!TeeUtility.isNullorEmpty(params.get("userId"))){
				model.setUserId(TeeStringUtil.getString(params.get("userId")));
			}
			if(!TeeUtility.isNullorEmpty(params.get("userName"))){
				model.setUserName(TeeStringUtil.getString(params.get("userName")));
			}
			if(!TeeUtility.isNullorEmpty(params.get("byname"))){
				model.setByname(TeeStringUtil.getString(params.get("byname")));
			}
			if(!TeeUtility.isNullorEmpty(params.get("sex"))){
				model.setSex(TeeStringUtil.getString(params.get("sex")));
			}
			if(!TeeUtility.isNullorEmpty(params.get("deptId"))){
				model.setDeptId(TeeStringUtil.getString(params.get("deptId")));
			}
			if(!TeeUtility.isNullorEmpty(params.get("userRoleStr"))){
				model.setUserRoleStr(TeeStringUtil.getString(params.get("userRoleStr")));
			}
			if(!TeeUtility.isNullorEmpty(params.get("postDeptStr"))){
				model.setPostDeptStr(TeeStringUtil.getString(params.get("postDeptStr")));
			}
			if(!TeeUtility.isNullorEmpty(params.get("notLogin"))){
				model.setNotLogin(TeeStringUtil.getString(params.get("notLogin")));
			}
			if(!TeeUtility.isNullorEmpty(params.get("notViewUser"))){
				model.setNotViewUser(TeeStringUtil.getString(params.get("notViewUser")));
			}
			if(!TeeUtility.isNullorEmpty(params.get("notViewTable"))){
				model.setNotViewTable(TeeStringUtil.getString(params.get("notViewTable")));
			}
			ArrayList<TeeDataRecord> dbL = personService.exportToCsv(model, loginPerson);
			TeeCSVUtil.CVSWrite(response.getWriter(), dbL);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return null;
	}


	public void setDeptService(TeeDeptService deptService) {
		this.deptService = deptService;
	}


	public void setPersonService(TeePersonService personService) {
		this.personService = personService;
	}
	
	
	
	

}
