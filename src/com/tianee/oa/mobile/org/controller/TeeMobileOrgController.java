package com.tianee.oa.mobile.org.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeeOrgSelectService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.org.service.TeeUserOnlineService;
import com.tianee.oa.mobile.org.service.TeeMobileOrgService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("mobileOrgController")
public class TeeMobileOrgController {
	
	@Autowired
	TeePersonService personService;
	
	@Autowired
	private TeeOrgSelectService orgSelectServ;
	
	@Autowired
	private TeeMobileOrgService mobileOrgService;
	
	@Autowired
	TeeUserOnlineService onlineService;
	
	/**
	 * 选择人员树
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getSelectUserTree")
	public TeeJson getOrgTreeSelectUser(HttpServletRequest request){
		String id = request.getParameter("id") == null ? "0" : request.getParameter("id")+";dept";
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		json = personService.selectOrgTreeAll(id,person);
		List<TeeZTreeModel> orgDeptList = (List<TeeZTreeModel>) json.getRtData();
		List finalList = new ArrayList();
		String sp [] = null;
		for(TeeZTreeModel ztree:orgDeptList){
			Map data = new HashMap();
			sp = ztree.getId().split(";");
			data.put("id", sp[0]);
			data.put("name", ztree.getName());
			data.put("userId", ztree.getParams().get("userId"));
			data.put("type", sp[1].contains("dept")?"dept":"user");
			data.put("pid", "0".equals(ztree.getpId())?"0":ztree.getpId().split(";")[0]);
			data.put("hasChilds", ztree.isParent());
			if(!sp[1].contains("dept")){
				data.put("online", onlineService.checkIsOnline(Integer.parseInt(sp[0])));
			}
			data.put("ico", ztree.getParams().get("ico"));
			
			finalList.add(data);
		}
		
		json.setRtData(finalList);
		return json;
	}
	
	
	/**
	 * 选择人员树
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getSelectDeptTree")
	public TeeJson getSelectDeptTree(HttpServletRequest request){
		String id = request.getParameter("id") == null ? "0" : request.getParameter("id")+";dept";
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		json = orgSelectServ.selectOrgTreeAll(id,person);
		List<TeeZTreeModel> orgDeptList = (List<TeeZTreeModel>) json.getRtData();
		List finalList = new ArrayList();
		String sp [] = null;
		for(TeeZTreeModel ztree:orgDeptList){
			Map data = new HashMap();
			sp = ztree.getId().split(";");
			data.put("id", sp[0]);
			data.put("name", ztree.getName());
			data.put("type", sp[1].contains("dept")?"dept":"user");
			data.put("pid", "0".equals(ztree.getpId())?"0":ztree.getpId().split(";")[0]);
			data.put("hasChilds", ztree.isParent());
			if(!sp[1].contains("dept")){
				data.put("online", onlineService.checkIsOnline(Integer.parseInt(sp[0])));
			}
			data.put("ico", ztree.getParams().get("ico"));
			
			finalList.add(data);
		}
		
		json.setRtData(finalList);
		return json;
	}
	
	
	
	/**
	 * 选择角色树
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getSelectRoleTree")
	public TeeJson getSelectRoleTree(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = orgSelectServ.getOrgRoleTree4Async(request);
		List<TeeZTreeModel> orgDeptList = (List<TeeZTreeModel>) json.getRtData();
		List finalList = new ArrayList();
		String sp [] = null;
		for(TeeZTreeModel ztree:orgDeptList){
			Map data = new HashMap();
			sp = ztree.getId().split("_");
			data.put("id", sp[1]);
			data.put("name", ztree.getName());
			data.put("type", sp[0].contains("dept")?"dept":"role");
			data.put("pid", ztree.getpId());
			data.put("hasChilds", ztree.isParent());
			if(!sp[0].contains("dept")){
				data.put("online", onlineService.checkIsOnline(Integer.parseInt(sp[1])));
			}
			
			finalList.add(data);
		}
		
		json.setRtData(finalList);
		return json;
	}
	
	
	/**
	 * 查询人员
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("queryPersons")
	public TeeEasyuiDataGridJson queryPersons(HttpServletRequest request,TeeDataGridModel dm){
		String keyWords = TeeStringUtil.getString(request.getParameter("keyWords"));
		return mobileOrgService.queryPersons(keyWords, dm);
	}
}
