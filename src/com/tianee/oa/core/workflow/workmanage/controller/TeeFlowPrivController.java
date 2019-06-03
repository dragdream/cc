package com.tianee.oa.core.workflow.workmanage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.model.TeePersonModel;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowPriv;
import com.tianee.oa.core.workflow.workmanage.model.TeeFlowPrivModel;
import com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrivServiveInterface;
import com.tianee.oa.oaconst.TeeActionKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeePortalModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/flowPrivManage")
public class TeeFlowPrivController extends BaseController {

	@Autowired
	private TeeFlowPrivServiveInterface flowPrivService;

	@RequestMapping("/addOrUpdate")
	public String addOrUpdate(TeeFlowPrivModel model, HttpServletRequest request)
			throws Exception {
		String sb = "";
		try {

			TeeFlowPriv flowPriv = flowPrivService.addOrUpdatePriv(model);
			sb = "{sid:'" + flowPriv.getSid() + "'}";
			request.setAttribute(TeeActionKeys.RET_STATE, TeeConst.RETURN_OK);
			request.setAttribute(TeeActionKeys.RET_MSRG, "保存成功！");
			request.setAttribute(TeeActionKeys.RET_DATA, sb.toString());
		} catch (Exception ex) {
			request.setAttribute(TeeActionKeys.RET_STATE, TeeConst.RETURN_ERROR);
			request.setAttribute(TeeActionKeys.RET_MSRG, ex.getMessage());
			throw ex;
		}
		return "/inc/rtjson.jsp";
	}

	/**
	 * 获取工作流权限通用个列表
	 * 
	 * @param dm
	 * @param portal
	 * @param response
	 * @return
	 */
	@RequestMapping("/getFlowPrivList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest response) {
		 return flowPrivService.datagrid(dm,response);
	}

	
	/**
	 * 查询BUId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getFlowPrivById.action")
	@ResponseBody
	public TeeJson getFlowPrivById(HttpServletRequest request, TeeFlowPrivModel model)
			throws Exception {
		TeeJson json = new TeeJson();
		flowPrivService.selectPrivById(model);
		if(model != null){
			json.setRtData(model);
			json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("没有找到相应的权限规则！");
		}
		
		return json;
	}
	
	
	/**
	 * 删除 ById   已逗号分隔
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/DelByIds.action")
	@ResponseBody
	public TeeJson DelByIds(HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
		String  ids = request.getParameter("ids");
		if(!TeeUtility.isNullorEmpty(ids)){
			int count = flowPrivService.delByIds(ids);
			json.setRtState(true);
			json.setRtMsg("删除成功");
		}
		return json;
	}
	
	/**
	 * 工作监控--- 获取有监控权限的流程树
	 * @param response
	 * @return
	 */
	@RequestMapping("/getMonitorFlowTypeTree.action")
	@ResponseBody
	public TeeJson getMonitorFlowTypeTree(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		List<TeeZTreeModel>  zmList = flowPrivService.getMonitorFlowTypeTree(request);
		json.setRtData(zmList);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取需要监控工作的工作列表
	 * 
	 * @param dm
	 * @param portal
	 * @param response
	 * @return
	 */
	@RequestMapping("/getMonitorFlowList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getMonitorFlowList(TeeDataGridModel dm, HttpServletRequest request) {
		 return flowPrivService.getMonitorFlowList(dm,request);
	}

	
	/**
	 * 获取有监控权限的流程类型的ids
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/getHasMonitorPrivFlowTypeIds.action")
	@ResponseBody
	public TeeJson getHasMonitorPrivFlowTypeIds(HttpServletRequest request) {
		 return flowPrivService.getHasMonitorPrivFlowTypeIds(request);
	}
	
	
	public void setFlowPrivService(TeeFlowPrivServiveInterface flowPrivService) {
		this.flowPrivService = flowPrivService;
	}

}
