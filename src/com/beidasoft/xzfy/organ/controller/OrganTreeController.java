package com.beidasoft.xzfy.organ.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzfy.base.controller.FyBaseController;
import com.beidasoft.xzfy.organ.service.OrganTreeService;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeJson;

import common.Logger;

/**
 * 组织机构树
 * @author fyj
 *
 */
@Controller
@RequestMapping("/xzfy/organTree")
public class OrganTreeController extends FyBaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger(OrganTreeController.class);
	
	@Autowired
	private OrganTreeService treeService;
	
	/**
	 * 选择复议组织机构树
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/selectFyTree")
	@ResponseBody
	public TeeJson selectFyTree(){
		
		log.info("[xzfy - OrganTreeController - selectFyTree] enter controller.");

		TeeJson json = new TeeJson();

		try{
			//选择复议组织机构树
			List<TeeZTreeModel> nodeList = treeService.selectFyTree(getRequest());
			
			json.setRtData(nodeList);
			json.setRtState(true);
		}
		catch(Exception e){
			
			log.info("[xzfy - OrganTreeController - selectFyTree] error="+e);
			json.setRtState(true);
			json.setRtMsg("请求失败");
		}
		finally{
			
			log.info("[xzfy - OrganTreeController - selectFyTree] controller end.");
		}
		return json;
	}
	
	
	
	/**
	 * 获取复议的组织机构树
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFyTree")
	@ResponseBody
	public TeeJson getFyTree(HttpServletRequest request){
		
		log.info("[xzfy - OrganTreeController - getFyTree] enter controller.");

		TeeJson json = new TeeJson();

		try{
			//获取部门ID
			String deptId = request.getParameter("id") == null ? "" : request.getParameter("id");
			
			//获取所有部门
			List<TeeZTreeModel> rtData = treeService.getFyTree(request,deptId);
			
			json.setRtState(true);
			json.setRtMsg("请求成功");
			json.setRtData(rtData);
		}
		catch(Exception e){
			
			log.info("[xzfy - OrganTreeController - getFyTree] error="+e);
			json.setRtState(true);
			json.setRtMsg("请求失败");
		}
		finally{
			
			log.info("[xzfy - OrganTreeController - getFyTree] controller end.");
		}
		return json;
	}
}
