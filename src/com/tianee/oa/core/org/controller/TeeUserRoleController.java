package com.tianee.oa.core.org.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.model.TeeUserRoleModel;
import com.tianee.oa.core.org.service.TeeOrgService;
import com.tianee.oa.core.org.service.TeeUserRoleService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeePortalModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/userRoleController")
public class TeeUserRoleController {
	
	@Autowired
	private TeeUserRoleService roleService;
	
	@Autowired
	private TeeOrgService orgService;
	
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) {
		
		return roleService.datagrid(TeeServletUtility.getParamMap(request),dm);
	}
	
	/**
	 * 更新角色薪酬
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateRoleSalary")
	@ResponseBody
	public TeeJson updateRoleSalary(TeeUserRoleModel model) {
		TeeJson json = new TeeJson();
		roleService.updateRoleSalary(model);
		return json;
	}

	@RequestMapping(params = "add")
	@ResponseBody
	public TeeJson addRole(TeeUserRoleModel model,HttpServletResponse response) {
		TeeJson json = new TeeJson();
		/*if(roleService.getUserRolelDao().checkRoleNoExist(0, model.getRoleNo())){
			json.setRtState(false);
			json.setRtMsg("角色排序号已存在！");
			json.setRtData(1);
		}else{*/
			roleService.addRoleService(model);
			json.setRtState(true);
			json.setRtMsg("ok");
			json.setRtData(null);
		/*}*/
			
			//生成角色json数据
			orgService.generateRoleJsonData();
		return json;
	}

	@RequestMapping(params = "load")
	@ResponseBody
	public TeeJson loadRoleById(HttpServletRequest request,HttpServletResponse response) {

		String uuid = request.getParameter("uuid");
		uuid = TeeStringUtil.fileEmptyString(uuid, "0");
		TeeUserRoleModel roleModel  = roleService.loadRoleById(Integer.parseInt(uuid));
		
		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtMsg("ok");
		json.setRtData(roleModel);
		return json;
	}
	@RequestMapping(params = "edit")
	@ResponseBody
	public TeeJson editRole(TeeUserRoleModel model,HttpServletResponse response) {
		TeeJson json = new TeeJson();
		/*if(roleService.getUserRolelDao().checkRoleNoExist(model.getUuid(), model.getRoleNo())){
			json.setRtState(false);
			json.setRtMsg("角色排序号已存在!");
			json.setRtData(1);
		}else{*/
			roleService.editRoleService(model);
			json.setRtState(true);
			json.setRtMsg("编辑角色成功!");
			json.setRtData(null);
	/*	}*/
		
			//生成角色json数据
			orgService.generateRoleJsonData();
		return json;
	}
	
	@RequestMapping(params = "del")
	@ResponseBody
	public TeeJson del(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		ids = TeeStringUtil.fileEmptyString(ids, "0");
		TeeJson json = new TeeJson();
		json.setRtState(true);
		List<TeeUserRole> list = roleService.deleteService(ids);
		json.setRtMsg("删除角色成功!");
		json.setRtData(null);
		
		//生成角色json数据
		orgService.generateRoleJsonData();
		return json;
	}
	
	/**
	 * 获取权限---权限控制
	 * @param response
	 * @return
	 */
	@RequestMapping("/selectUserPrivList.action")
	@ResponseBody
	public TeeJson selectUserPrivList(HttpServletRequest request) {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String privOp = request.getParameter("privOp") == null ? "" : request.getParameter("privOp");
		List<TeeUserRole> list = list = roleService.selectUserPrivList(person ,privOp );
		TeeJson json = new TeeJson();
		json.setRtState(true);
		 
		List<TeeUserRoleModel> listM = new ArrayList<TeeUserRoleModel>();
		for (int i = 0; i <list.size(); i++) {
			TeeUserRoleModel modul = new TeeUserRoleModel();
			BeanUtils.copyProperties(list.get(i), modul);
			listM.add(modul);
		}
		json.setRtMsg("删除角色成功!");
		json.setRtData(listM);
		return json;
	}
	
	
	
	@RequestMapping("/selectUserPrivListWorkFlow.action")
	@ResponseBody
	public TeeJson selectUserPrivListWorkFlow(HttpServletRequest request) {
		
		return  roleService.selectUserPrivListWorkFlow(request);
	}
	
	
	
	/**
	 * 上移
	 * @author syl
	 * @date 2013-12-29
	 * @param request
	 * @return
	 */
	@RequestMapping("/toPrev")
	@ResponseBody
	public TeeJson toPrev(HttpServletRequest request) {
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String id = request.getParameter("id");
		TeeJson json = new TeeJson();
		
		return json;
	}
	
	/**
	 * 更新排序号
	 * @author syl
	 * @date 2014-6-1
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateRoleSort")
	@ResponseBody
	public TeeJson updateRoleSort(HttpServletRequest request) {
		TeeJson json = roleService.updateRoleSort(request);
		return json;
	}
	
	/**
	 * 获取所有角色
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllRole")
	@ResponseBody
	public TeeJson getAllRole(HttpServletRequest request) {
		TeeJson json = roleService.getAllRole();
		return json;
	}
	
	/**
	 * 获取 主角色用户/辅助角色用户详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/getStatisticsDetail")
	@ResponseBody
	public TeeJson getStatisticsDetail(HttpServletRequest request) {
		TeeJson json = roleService.getStatisticsDetail(request);
		return json;
	}
	
	public TeeUserRoleService getRoleService() {
		return roleService;
	}


	public void setRoleService(TeeUserRoleService roleService) {
		this.roleService = roleService;
	}
	
	
}
