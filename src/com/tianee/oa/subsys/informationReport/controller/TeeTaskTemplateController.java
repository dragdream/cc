package com.tianee.oa.subsys.informationReport.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.base.email.model.TeeEmailModel;
import com.tianee.oa.subsys.informationReport.bean.TeeTaskTemplate;
import com.tianee.oa.subsys.informationReport.model.TeeQueryModel;
import com.tianee.oa.subsys.informationReport.service.TeeTaskTemplateService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/TeeTaskTemplateController")
public class TeeTaskTemplateController {
	@Autowired
	private TeeTaskTemplateService  taskTemplateService;
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request){	
		return taskTemplateService.getInfoBySid(request);
	}
	
	
	/**
	 * 新建/编辑
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request){	
		return taskTemplateService.addOrUpdate(request);
	}
	
	/**
	 * 获取我创建的任务
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/getMyPubTask")
	@ResponseBody
	public TeeEasyuiDataGridJson getMyPubTask(TeeDataGridModel dm, HttpServletRequest request){
		return taskTemplateService.getMyPubTask(dm, request);
	}
	
	
	
	/**
	 * 获取我可以查看的任务列表  已经发布的任务模板
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/getMyViewTask")
	@ResponseBody
	public TeeEasyuiDataGridJson getMyViewTaskss(TeeDataGridModel dm, HttpServletRequest request,TeeQueryModel models){
		return taskTemplateService.getMyViewTask1(dm, request,models);
	}
	
	
	/**
	 * 任务发布
	 * @param request
	 * @return
	 */
	@RequestMapping("/pubTask")
	@ResponseBody
	public TeeJson pubTask(HttpServletRequest request){	
		return taskTemplateService.pubTask(request);
	}
	
	
	
	/**
	 * 取消发布
	 * @param request
	 * @return
	 */
	@RequestMapping("/cancelPub")
	@ResponseBody
	public TeeJson cancelPub(HttpServletRequest request){	
		return taskTemplateService.cancelPub(request);
	}
	
	
	
	
	
	/**
	 * 删除任务
	 * @param request
	 * @return
	 */
	@RequestMapping("/delBySid")
	@ResponseBody
	public TeeJson delBySid(HttpServletRequest request){	
		return taskTemplateService.delBySid(request);
	}
	
	/*@RequestMapping("/getMyPubTask1")
	@ResponseBody
	public TeeEasyuiDataGridJson getMyPubTask11(TeeDataGridModel gridModel,HttpServletRequest request,TeeQueryModel querymodel){
		TeeEasyuiDataGridJson gridJson=new TeeEasyuiDataGridJson();
		
		List<TeeQueryModel> teeUserInfoModel=new ArrayList<TeeQueryModel>();
		long total=taskTemplateService.getTotal(querymodel);
		List<TeeTaskTemplate> user=taskTemplateService.lists(gridModel.getFirstResult(), gridModel.getRows(),querymodel);
		for(TeeTaskTemplate  users: user ){
			TeeQueryModel userInfoModel=new TeeQueryModel();
			BeanUtils.copyProperties(users,userInfoModel);
			
			
			teeUserInfoModel.add(userInfoModel);
		}
		
		gridJson.setTotal(total);
		gridJson.setRows(teeUserInfoModel);
		return gridJson;
		
		
	}*/
}
