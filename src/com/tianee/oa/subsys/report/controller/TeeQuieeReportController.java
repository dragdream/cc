package com.tianee.oa.subsys.report.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.report.model.TeeQuieeReportModel;
import com.tianee.oa.subsys.report.service.TeeQuieeReportService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/quieeReportController")
public class TeeQuieeReportController {

	@Autowired
	private TeeQuieeReportService quieeReportService;
	
	
	/**
	 * 获取报表目录树
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年7月20日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/getReportFolderTree")
	@ResponseBody
	public TeeJson getReportFolderTree(HttpServletRequest request) {

		TeeQuieeReportModel model = new TeeQuieeReportModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		requestMap.put(TeeConst.LOGIN_USER, person);
		TeeJson json = new TeeJson();
		
		json = quieeReportService.getReportFolderTree(requestMap, model);
		return json;
	}
	
	
	/**
	 * 判断当前登录的人  对文件夹是查看权限  还是管理权限
	 * @param request
	 * @return
	 */
    @RequestMapping("/getPriv")
    @ResponseBody
    public TeeJson getPriv(HttpServletRequest request){
    	//获取页面上传来的文件夹的主键  
    	int folderSid=TeeStringUtil.getInteger(request.getParameter("folderSid"), 0);
    	//获取当前登录的用户对象
    	TeePerson person=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        
    	return quieeReportService.getPriv(folderSid, person);
    }
    
    
    /**
     * 新建文件夹
     * @param request
     * @return
     */
    @RequestMapping("/addFolder")
    @ResponseBody
    public TeeJson addFolder(HttpServletRequest request){
    	TeeJson json=new TeeJson();
    	Map requestMap=TeeServletUtility.getParamMap(request);
    	
    	return quieeReportService.addFolder(requestMap);
    }
    
    
  
    /**
     * 获取父文件夹下的  文件夹 和文件列表
     * @param dm
     * @param request
     * @return
     */
    @RequestMapping("/getReportPage")
    @ResponseBody
    public TeeEasyuiDataGridJson getReportPage(TeeDataGridModel dm, HttpServletRequest request){
    	return quieeReportService.getReportPage(dm,request);
    }
    
   
    /**
     * 批量删除
     * @param request
     * @return
     */
    @RequestMapping("/deleteReportsBySid")
    @ResponseBody
    public TeeJson deleteReportsBySid(HttpServletRequest request){
    	return quieeReportService.deleteReportsBySid(request);
    }
    
    
    /**
     * 根据主键  获取详情
     */
    @RequestMapping("/getBySid")
    @ResponseBody
    public TeeJson getBySid(HttpServletRequest request){
    	return quieeReportService.getBySid(request);
    }
    
    
    /**
     * 编辑文件夹
     */
    @RequestMapping("/editFolder")
    @ResponseBody
    public TeeJson editFolder(HttpServletRequest request){
    	TeeQuieeReportModel model=new TeeQuieeReportModel();
    	TeeServletUtility.requestParamsCopyToObject(request,model);
    	return quieeReportService.editFolder(model);
    }
    
   
    /**
     * 上传报表
     * @param request
     * @return
     * @throws IOException 
     */
    @RequestMapping("/addReport")
    @ResponseBody
    public TeeJson addReport(HttpServletRequest request) throws IOException{
    	MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
    	return quieeReportService.addReport(multiRequest);
    }
    
    
    /**
     * 编辑报表
     * @param request
     * @return
     * @throws IOException 
     */
    @RequestMapping("/editReport")
    @ResponseBody
    public TeeJson editReport(HttpServletRequest request) throws IOException{
    	MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
    	return quieeReportService.editReport(multiRequest);
    }
    
    
    
    
    /**
     * 获取文件夹   一级目录列表
     * @param dm
     * @param request
     * @return
     */
    @RequestMapping("/getFolderList")
    @ResponseBody
    public TeeEasyuiDataGridJson getFolderList(TeeDataGridModel dm, HttpServletRequest request){
    	return quieeReportService.getFolderList(dm,request);
    }
}
