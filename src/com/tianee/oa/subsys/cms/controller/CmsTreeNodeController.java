package com.tianee.oa.subsys.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.cms.service.CmsTreeNodeService;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/cmsTreeNode")
public class CmsTreeNodeController {
	
	@Autowired
	private CmsTreeNodeService cmsTreeNodeService;
	
	@ResponseBody
	@RequestMapping("/treeNode")
	public TeeJson treeNode(HttpServletRequest request){
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		json.setRtState(true);
		String id = request.getParameter("id");
		String siteIds = request.getParameter("siteIds");
		String chnls = request.getParameter("chnls");//过滤被选中的栏目
		json.setRtData(cmsTreeNodeService.treeNode(id,siteIds,chnls,loginUser));
		return json;
	}
	
	
	/**
	 * 获取当前登陆人审批的栏目树
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkTreeNode")
	public TeeJson checkTreeNode(HttpServletRequest request){
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		json.setRtState(true);
		String id = request.getParameter("id");
		String siteIds = request.getParameter("siteIds");
		String chnls = request.getParameter("chnls");//过滤被选中的栏目
		json.setRtData(cmsTreeNodeService.checkTreeNode(id,siteIds,chnls,loginUser));
		return json;
	}
}
