package com.tianee.oa.core.base.dam.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.dam.model.TeeFileAttchModel;
import com.tianee.oa.core.base.dam.service.TeeFileAttchService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/TeeFileAttchController")
public class TeeFileAttchController {
	@Autowired
	TeeFileAttchService fileAttchService;
	
	
	/**
	 * 新建/编辑
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request,TeeFileAttchModel model){
		return fileAttchService.addOrUpdate(request,model);
	}
	
	
	/**
	 * 根据档案id   获取该档案相关联的档案附件信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFileAttchListByFileId")
	@ResponseBody
	public TeeJson getFileAttchListByFileId(HttpServletRequest request){
		return fileAttchService.getFileAttchListByFileId(request);
	}
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request){
		return fileAttchService.getInfoBySid(request);
	}
	
	
	
	/**
	 * 根据主键删除档案附件信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/delBySid")
	@ResponseBody
	public TeeJson delBySid(HttpServletRequest request){
		return fileAttchService.delBySid(request);
	}
}
