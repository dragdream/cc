package com.tianee.oa.subsys.supervise.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.supervise.model.TeeSupervisionTypeModel;
import com.tianee.oa.subsys.supervise.service.TeeSupervisionTypeService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/supTypeController")
public class TeeSupervisionTypeController {

	
	@Autowired
	private TeeSupervisionTypeService supTypeService;
	
	
	/**
	 * 根据分类主键   获取分类详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return supTypeService.getInfoBySid(request);
	}
	
	
	/**
	 * 增加/编辑
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request,HttpServletResponse response,TeeSupervisionTypeModel model) throws ParseException {	
		return supTypeService.addOrUpdate(request,model);
	}
	
	
	
	
	/**
	 * 根据当前登录的用户    获取有权限的分类列表
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getSupTypeList")
	@ResponseBody
	public List getSupTypeList(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return supTypeService.getSupTypeList(request);
	}
	
	
	/**
	 * 获取除自己以外的其他分类列表
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getOtherSupTypeList")
	@ResponseBody
	public List getOtherSupTypeList(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return supTypeService.getOtherSupTypeList(request,dm);
	}
	
	
	/**
	 * 获取所有的督办类型列表
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getAllSupTypeList")
	@ResponseBody
	public List getAllSupTypeList(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return supTypeService.getAllSupTypeList(request,dm);
	}
	
	
	/**
	 * 删除督办任务分类
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/delBySid")
	@ResponseBody
	public TeeJson delBySid(HttpServletRequest request,HttpServletResponse response,TeeSupervisionTypeModel model) throws ParseException {	
		return supTypeService.delBySid(request,model);
	}

	/**
	 * 获取督办任务树（带权限）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getSupTypeTree")
	@ResponseBody
	public TeeJson getSupTypeTree(HttpServletRequest request,HttpServletResponse response){
		
		return supTypeService.getSupTypeTree(request);
	}
}
