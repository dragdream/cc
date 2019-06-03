package com.tianee.oa.subsys.crm.core.clue.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.clue.model.TeeCrmClueModel;
import com.tianee.oa.subsys.crm.core.clue.service.TeeCrmClueService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/TeeCrmClueController")
public class TeeCrmClueController extends BaseController {
	
	@Autowired
	private TeeCrmClueService clueService;
	
	/**
	 * 添加销售线索
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addOrUpdate")
	public TeeJson addOrUpdate(HttpServletRequest request,TeeCrmClueModel model){
		TeeJson json = new TeeJson();
		json = clueService.addOrUpdate(request,model);
		return json;
	}
	
	/**
	 * 显示线索列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request){
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return clueService.datagird(dm, requestDatas);
	}
	
	/**
	 * 获取线索详情
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getInfoBySid")
	public TeeJson getInfoBySid(HttpServletRequest request,TeeCrmClueModel model){
		TeeJson json = new TeeJson();
		json = clueService.getInfoBySid(request,model);
		return json;
	}
	
	/**
	 * 更换负责人
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/changeManage")
	public TeeJson changeManage(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = clueService.changeManage(request);
		return json;
	}
	
	/**
	 * 删除线索
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delById")
	public TeeJson delById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = clueService.delById(request);
		return json;
		
	}
	
	/**
	 * 跟进线索
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/followUpClue")
	public TeeJson followUpClue(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = clueService.followUpClue(request);
		return json;
	}
	
	/**
	 * 线索转换
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/transfromClue")
	public TeeJson transfromClue(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = clueService.transfromClue(request);
		return json;
	}
	
	/**
	 * 线索无效
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/invalidClue")
	public TeeJson invalidClue(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = clueService.invalidClue(request);
		return json;
	}
}
