package com.tianee.oa.subsys.bisengin.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.subsys.bisengin.model.BisTableModel;
import com.tianee.oa.subsys.bisengin.model.BisViewModel;
import com.tianee.oa.subsys.bisengin.service.BisTableService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("bisTable")
public class BisTableController {
	@Autowired
	private BisTableService bisTableService;
	
	/**
	 * 增加业务表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addBisTable")
	public TeeJson addBisTable(HttpServletRequest request){
		TeeJson json = new TeeJson();
		BisTableModel bisTableModel = new BisTableModel();
		TeeServletUtility.requestParamsCopyToObject(request, bisTableModel);
		bisTableService.addBisTable(bisTableModel);
		json.setRtState(true);
		json.setRtMsg("添加成功");
		return json;
	}
	
	/**
	 * 更新业务表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateBisTable")
	public TeeJson updateBisTable(HttpServletRequest request){
		TeeJson json = new TeeJson();
		BisTableModel bisTableModel = new BisTableModel();
		TeeServletUtility.requestParamsCopyToObject(request, bisTableModel);
		bisTableService.updateBisTable(bisTableModel);
		json.setRtState(true);
		json.setRtMsg("修改成功");
		return json;
	}
	
	/**
	 * 更新业务表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateBisTableModel")
	public TeeJson updateBisTableModel(HttpServletRequest request){
		TeeJson json = new TeeJson();
		BisTableModel bisTableModel = new BisTableModel();
		TeeServletUtility.requestParamsCopyToObject(request, bisTableModel);
		bisTableService.updateBisTableModel(bisTableModel);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 删除业务表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteBisTable")
	public TeeJson deleteBisTable(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
		try{
			bisTableService.dropBisTable(sid);
		}catch(Exception e){
			e.printStackTrace();
		}
		bisTableService.deleteBisTable(sid);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("getModelById")
	public TeeJson getModelById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
		json.setRtData(bisTableService.getModelById(sid));
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("datagrid")
	public TeeEasyuiDataGridJson datagrid(HttpServletRequest request,TeeDataGridModel dm){
		BisTableModel bisTableModel = new BisTableModel();
		TeeServletUtility.requestParamsCopyToObject(request, bisTableModel);
		return bisTableService.datagrid(bisTableModel, dm);
	}
	
	@ResponseBody
	@RequestMapping("generate")
	public TeeJson generate(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int tableId = TeeStringUtil.getInteger(request.getParameter("tableId"), 0);
		bisTableService.generateBisTable(tableId);
		json.setRtMsg("已生成业务数据表结构");
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("findTableByDatasource")
	public TeeEasyuiDataGridJson findTableByDatasource(HttpServletRequest request){
		int dataSource = TeeStringUtil.getInteger(request.getParameter("dataSource"), 0);
		return bisTableService.findTableByDatasource(dataSource);
	}
	
	@RequestMapping(value = "/export")
	 public void exportXml(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
		 BisTableModel model = bisTableService.getModelById(sid);
		 response.setCharacterEncoding("UTF-8");
		 response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("业务表_"+model.getTableDesc(),"UTF-8")+".xml");
		 OutputStream output = response.getOutputStream();
		 String sb = bisTableService.exportXml(sid);
		 output.write(sb.getBytes("UTF-8"));
	 }
	 
	 
	 @RequestMapping(value = "/import")
	 public void importXml(HttpServletRequest request,HttpServletResponse response) throws Exception {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			int catId = TeeStringUtil.getInteger(multiRequest.getParameter("catId"), 0);
			MultipartFile file = multiRequest.getFile("file");
			InputStream inputstream = file.getInputStream();
			bisTableService.importXml(inputstream,catId);
			PrintWriter pw = response.getWriter();
			pw.write("<script>parent.uploadSuccess();</script>");
	 }
	 

}
