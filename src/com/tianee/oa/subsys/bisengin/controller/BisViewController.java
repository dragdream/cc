package com.tianee.oa.subsys.bisengin.controller;

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

import com.tianee.oa.subsys.bisengin.model.BisViewListItemModel;
import com.tianee.oa.subsys.bisengin.model.BisViewModel;
import com.tianee.oa.subsys.bisengin.service.BisViewService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/bisView")
public class BisViewController {
	@Autowired
	private BisViewService bisViewService;
	
	@ResponseBody
	@RequestMapping("/createBisView")
	public TeeJson createBisView(HttpServletRequest request){
		TeeJson json = new TeeJson();
		BisViewModel bisViewModel = 
				(BisViewModel) TeeServletUtility.request2Object(request, BisViewModel.class);
		bisViewService.createBisView(bisViewModel);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getBisView")
	public TeeJson getBisView(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String identity = TeeStringUtil.getString(request.getParameter("identity"));
		json.setRtState(true);
		json.setRtData(bisViewService.getBisView(identity));
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/listBisView")
	public TeeEasyuiDataGridJson listBisView(TeeDataGridModel dm,HttpServletRequest request){
		Map requestMap = TeeServletUtility.getParamMap(request);
		return bisViewService.listBisView(dm,requestMap);
	}
	
	
	/**
	 * 获取所有视图（不分页）
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllBisViewList")
	public TeeJson getAllBisViewList(HttpServletRequest request){
		return bisViewService.getAllBisViewList(request);
	}
	
	
	@ResponseBody
	@RequestMapping("/delBisView")
	public TeeJson delBisView(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String identity = TeeStringUtil.getString(request.getParameter("identity"));
		json.setRtState(true);
		bisViewService.delBisView(identity);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/updateBisView")
	public TeeJson updateBisView(HttpServletRequest request){
		TeeJson json = new TeeJson();
		BisViewModel bisViewModel = 
				(BisViewModel) TeeServletUtility.request2Object(request, BisViewModel.class);
		bisViewService.updateBisView(bisViewModel);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/addBisViewListItem")
	public TeeJson addBisViewListItem(HttpServletRequest request){
		TeeJson json = new TeeJson();
		BisViewListItemModel bisViewListItemModel = 
				(BisViewListItemModel) TeeServletUtility.request2Object(request, BisViewListItemModel.class);
		bisViewService.addBisViewListItem(bisViewListItemModel);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/updateBisViewListItem")
	public TeeJson updateBisViewListItem(HttpServletRequest request){
		TeeJson json = new TeeJson();
		BisViewListItemModel bisViewListItemModel = 
				(BisViewListItemModel) TeeServletUtility.request2Object(request, BisViewListItemModel.class);
		bisViewService.updateBisViewListItem(bisViewListItemModel);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/delBisViewListItem")
	public TeeJson delBisViewListItem(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		bisViewService.delBisViewListItem(sid);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getBisViewListItem")
	public TeeJson getBisViewListItem(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(bisViewService.getBisViewListItem(sid));
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/listBisViewListItem")
	public TeeEasyuiDataGridJson listBisViewListItem(HttpServletRequest request){
		String identity = TeeStringUtil.getString(request.getParameter("identity"));
		return bisViewService.listBisViewListItem(identity);
	}
	
	
	/**
	 * 根据视图标识   获取item集合
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBisViewListItemList")
	public TeeJson getBisViewListItemList(HttpServletRequest request){
		String identity = TeeStringUtil.getString(request.getParameter("identity"));
		return bisViewService.getBisViewListItemList(identity);
	}
	
	
	@ResponseBody
	@RequestMapping("/dflist")
	public TeeEasyuiDataGridJson dflist(HttpServletRequest request,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = bisViewService.dflist(dm,TeeServletUtility.getParamMap(request));
		return dataGridJson;
	}
	
	
	 @RequestMapping(value = "/export")
	 public void exportXml(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 String identity = TeeStringUtil.getString(request.getParameter("identity"));
		 BisViewModel model = bisViewService.getBisView(identity);
		 response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("视图_"+model.getName(),"UTF-8")+".xml");
		OutputStream output = response.getOutputStream();
		String sb = bisViewService.exportXml(identity);
		output.write(sb.getBytes("UTF-8"));
	 }
	 
	 
	 @RequestMapping(value = "/import")
	 public void importXml(HttpServletRequest request,HttpServletResponse response) throws Exception {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multiRequest.getFile("file");
			InputStream inputstream = file.getInputStream();
			bisViewService.importXml(inputstream);
			PrintWriter pw = response.getWriter();
			pw.write("<script>parent.uploadSuccess();</script>");
	 }
}
