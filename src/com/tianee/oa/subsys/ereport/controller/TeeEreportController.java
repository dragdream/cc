package com.tianee.oa.subsys.ereport.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.bisengin.model.BisViewModel;
import com.tianee.oa.subsys.ereport.bean.TeeEreport;
import com.tianee.oa.subsys.ereport.model.TeeEreportModel;
import com.tianee.oa.subsys.ereport.service.TeeEreportService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/teeEreportController")
public class TeeEreportController {

	@Autowired
	private TeeEreportService teeEreportService;
	
	/**
	 * 获取我创建的报表 （系统管理员  可以查看全部报表）
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getEreportList")
	@ResponseBody
	public TeeEasyuiDataGridJson getEreportList(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return teeEreportService.getEreportList(request,dm);
	}
	
	
	
	
	/**
	 * 创建报表
	 * @param request
	 * @return
	 */
	@RequestMapping("/create")
	@ResponseBody
	public TeeJson create(HttpServletRequest request) {
		return teeEreportService.create(request);
	}
	
	
	
	/**
	 * 删除报表
	 * @param request
	 * @return
	 */
	@RequestMapping("/delBySid")
	@ResponseBody
	public TeeJson delBySid(HttpServletRequest request) {
		return teeEreportService.delBySid(request);
	}
	
	
	/**
	 * 权限设置
	 * @param request
	 * @return
	 */
	@RequestMapping("/privSetting")
	@ResponseBody
	public TeeJson privSetting(HttpServletRequest request) {
		return teeEreportService.privSetting(request);
	}
	
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request) {
		return teeEreportService.getInfoBySid(request);
	}
	
	
	
	/**
	 * 修改操作
	 * @param request
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public TeeJson update(HttpServletRequest request,TeeEreportModel model) {
		return teeEreportService.update(request,model);
	}
	
	
	
	/**
	 * 渲染图表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/renderGraphics")
	@ResponseBody
	public TeeJson renderGraphics(HttpServletRequest request,TeeEreportModel model) {
		return teeEreportService.renderGraphics(request,model);
	}
	
	
	
	/**
	 * 报表中心  渲染图表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/renderGraph")
	@ResponseBody
	public TeeJson renderGraphics(HttpServletRequest request) {
		return teeEreportService.renderGraph(request);
	}
	
	
	/**
	 * 报表中心  根据分类   权限   获取当前登陆人可以查看的报表
	 * @param request
	 * @param dm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/datagridViews")
	public TeeEasyuiDataGridJson datagridViews(HttpServletRequest request,TeeDataGridModel dm){
		return teeEreportService.datagridViews(request, dm);
	}
	
	/**
	 * 导出
	 * */
	@RequestMapping(value = "/export")
	 public void exportXml(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 String sid = TeeStringUtil.getString(request.getParameter("sid"));
		 TeeJson infoBySid = teeEreportService.getInfoBySid(request);
		 TeeEreportModel rtData = (TeeEreportModel)infoBySid.getRtData();
		 response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("报表_"+rtData.getTitle(),"UTF-8")+".xml");
		OutputStream output = response.getOutputStream();
		String sb = teeEreportService.exportXml(sid);
		output.write(sb.getBytes("UTF-8"));
	 }
	
	/**
	 * 导入
	 * */
	 @RequestMapping(value = "/import")
	 public void importXml(HttpServletRequest request,HttpServletResponse response) throws Exception {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multiRequest.getFile("file");
			InputStream inputstream = file.getInputStream();
			teeEreportService.importXml(inputstream);
			PrintWriter pw = response.getWriter();
			pw.write("<script>parent.uploadSuccess();</script>");
	 }
}
