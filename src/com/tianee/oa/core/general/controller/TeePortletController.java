package com.tianee.oa.core.general.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.general.bean.TeePortlet;
import com.tianee.oa.core.general.service.TeePersonalPortletService;
import com.tianee.oa.core.general.service.TeePortletService;
import com.tianee.oa.core.general.service.TeeSmsService;
import com.tianee.oa.core.general.service.TeeSysParaService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;


@Controller
@RequestMapping("portlet")
public class TeePortletController {
	 @Autowired
	 TeeSmsService smsService;
	 @Autowired
	 TeePersonalPortletService portletPersonalService;
	 @Autowired
	 TeePortletService portletService;
	 @Autowired
	 TeeSysParaService paraService;
	 @Autowired
	 TeePersonService personService;
	 
	 @RequestMapping(value = "/addOrUpdatePortlet")
	 @ResponseBody
	 public TeeJson addOrUpdatePortlet(HttpServletRequest request) {
		  TeeJson json = new TeeJson();
		  TeePortlet portlet =
				  (TeePortlet) TeeServletUtility.request2Object(request, TeePortlet.class);
		  portletService.addOrUpdatePortlet(portlet);
		  json.setRtState(true);
		  return json;
	 }
	 
	 @RequestMapping(value = "/getPortlet")
	 @ResponseBody
	 public TeeJson getPortlet(HttpServletRequest request) {
		  TeeJson json = new TeeJson();
		  int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		  json.setRtData(portletService.getPortlet(sid));
		  json.setRtState(true);
		  return json;
	 }
	 
	 @RequestMapping(value = "/datagrid")
	 @ResponseBody
	 public TeeEasyuiDataGridJson datagrid(HttpServletRequest request) {
		  return portletService.datagrid();
	 }
	 
	 @RequestMapping(value = "/updateStatus")
	 @ResponseBody
	 public TeeJson updateStatus(HttpServletRequest request) {
		 TeeJson json = new TeeJson();
		  int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		  int flag = TeeStringUtil.getInteger(request.getParameter("flag"), 0);
		  portletService.updateStatus(sid, flag);
		  json.setRtState(true);
		  return json;
	 }
	 
	 @RequestMapping(value = "/delete")
	 @ResponseBody
	 public TeeJson delete(HttpServletRequest request) {
		 TeeJson json = new TeeJson();
		  int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		  portletService.delete(sid);
		  json.setRtState(true);
		  return json;
	 }
	 
	 @RequestMapping(value = "/renderPortlet")
	 @ResponseBody
	 public Map renderPortlet(HttpServletRequest request) {
		  Map requestDatas = TeeServletUtility.getParamMap(request);
		  return portletService.renderPortlet(requestDatas);
	 }
	 
	 @RequestMapping(value = "/getPersonalDesktop")
	 @ResponseBody
	 public String getPersonalDesktop(HttpServletRequest request) {
		  return portletService.getPersonalDesktop();
	 }
	 
	 /**
	  * 获取可使用的桌面模块信息
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/getUseablePortlet")
	 @ResponseBody
	 public TeeJson getUseablePortlet(HttpServletRequest request){
		 TeeJson json = new TeeJson();
		 json.setRtState(true);
		 json.setRtData(portletService.getUseablePortlet());
		 return json;
	 }
	 
	 @RequestMapping(value = "/updatePersonDesktop")
	 @ResponseBody
	 public TeeJson updatePersonDesktop(HttpServletRequest request) {
		 String desktop = TeeStringUtil.getString(request.getParameter("desktop"));
		 portletService.updatePersonDesktop(desktop);
		 return null;
	 }
	 
	 @RequestMapping(value = "/export")
	 public void exportXml(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		 TeePortlet portlet = portletService.getPortlet(sid);
		 response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("桌面模块_"+portlet.getModelTitle(),"UTF-8")+".xml");
		OutputStream output = response.getOutputStream();
		String sb = portletService.exportXml(sid);
		output.write(sb.getBytes("UTF-8"));
	 }
	 
	 @RequestMapping(value = "/import")
	 public void importXml(HttpServletRequest request,HttpServletResponse response) throws Exception {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multiRequest.getFile("file");
			InputStream inputstream = file.getInputStream();
			portletService.importXml(inputstream);
			PrintWriter pw = response.getWriter();
			pw.write("<script>parent.uploadSuccess();</script>");
	 }
}
