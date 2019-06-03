package com.tianee.oa.subsys.project.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.project.service.TeeProjectFileService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/projectFileController")
public class TeeProjectFileController {
    @Autowired
    private TeeProjectFileService projectFileService;
    
    
    
    /**
     * 根据项目主键  获取文档
     * @param request
     * @param response
     * @return
     * @throws ParseException
     */
	@RequestMapping("/getFileData")
	@ResponseBody
	public TeeJson getFileData(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectFileService.getFileData(request);
	}
	
	
	
	 /**
     * 上传文档
     * @param request
     * @param response
     * @return
     * @throws ParseException
     */
	@RequestMapping("/upload")
	@ResponseBody
	public TeeJson upload(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectFileService.upload(request);
	}
	
	
	/**
	 * 根据主键 删除文档
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/delBySid")
	@ResponseBody
	public TeeJson delBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectFileService.delBySid(request);
	}
	
	
	
	/**
	 * 根据diskId获取文档list
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getFileListByDiskId")
	@ResponseBody
	public TeeEasyuiDataGridJson getFileListByDiskId(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectFileService.getFileListByDiskId(request,dm);
	}
	
	
	
	/**
	 * 上传文档成功之后通知   项目创建人  负责人  观察者   成员
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/sendMessage")
	@ResponseBody
	public TeeJson sendMessage(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return projectFileService.sendMessage(request);
	}
	
	
}
