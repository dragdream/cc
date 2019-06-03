package com.tianee.oa.core.org.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserGroup;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.model.TeeUserGroupModel;
import com.tianee.oa.core.org.service.TeeUserGroupService;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/userGroup")
public class TeeUserGroupController {
	@Autowired
	TeeUserGroupService userGroupServ;
	@Autowired
	TeePersonDao personDao;

	
	/**
	 * 新增或者更新参数
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addUpdateUserGroup.action")
	@ResponseBody
	public TeeJson addUpdateUserGroup(HttpServletRequest request, TeeUserGroupModel userGroupModel)
			{
		//boolean isExist = false;
		TeeJson json = new TeeJson();
		try {
			userGroupServ.addUpdateUserGroup(userGroupModel);
			json.setRtState(true);
			json.setRtMsg("保存成功");
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("保存失败");
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 获取分组列表
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUserGroupList.action")
	@ResponseBody
	public TeeJson getUserGroupList(HttpServletRequest request)
			throws Exception {
	    TeeJson json = new TeeJson();
	    //TeeUserGroupModel userGroupModel = new TeeUserGroupModel();
	    List<TeeUserGroupModel> userGroupModelList = new ArrayList<TeeUserGroupModel>();
    	List<TeeUserGroup> userGroup = userGroupServ.getUserGroup();
    	for(int i = 0;i<userGroup.size();i++){
    		TeeUserGroupModel model = new TeeUserGroupModel();
    		TeeUserGroup group = userGroup.get(i);
    		//model = userGroupModelList.get(i);
    		BeanUtils.copyProperties(group, model);
    		model.setUuid(group.getSid()+"");
    		userGroupModelList.add(model);
    	}
    	System.out.println(userGroupModelList.toString());
    	json.setRtData(userGroupModelList);
    	json.setRtState(true);
		json.setRtMsg("查询成功！");
		return json;
	}
	
	

	/**
	 * 获取分组列表  ---个人用户组
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUserGroupByPersonList")
	@ResponseBody
	public TeeJson getUserGroupByPersonList(HttpServletRequest request)
			throws Exception {
	    TeeJson json = new TeeJson();
	    
	    TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	    //TeeUserGroupModel userGroupModel = new TeeUserGroupModel();
	    List<TeeUserGroupModel> userGroupModelList = new ArrayList<TeeUserGroupModel>();
    	List<TeeUserGroup> userGroup = userGroupServ.getUserGroupByPersonId(person.getUuid());
    	for(int i = 0;i<userGroup.size();i++){
    		TeeUserGroupModel model = new TeeUserGroupModel();
    		TeeUserGroup group = userGroup.get(i);
    		//model = userGroupModelList.get(i);
    		BeanUtils.copyProperties(group, model);
    		model.setUuid(group.getSid()+"");
    		userGroupModelList.add(model);
    	}
    	System.out.println(userGroupModelList.toString());
    	json.setRtData(userGroupModelList);
    	json.setRtState(true);
		json.setRtMsg("查询成功！");
		return json;
	}
	
	

	/**
	 * 获取分组列表  ---个人用户组
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUserGroupPersonListInfo")
	@ResponseBody
	public TeeJson getUserGroupPersonListInfo(HttpServletRequest request)
			throws Exception {
	    TeeJson json = new TeeJson();
	    
	    TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	    //TeeUserGroupModel userGroupModel = new TeeUserGroupModel();
	    List<TeeUserGroupModel> userGroupModelList = new ArrayList<TeeUserGroupModel>();
    	List<TeeUserGroup> userGroupList = userGroupServ.getUserGroupByPersonId(person.getUuid());
    	for(int i = 0;i<userGroupList.size();i++){
    		TeeUserGroupModel model = new TeeUserGroupModel();
    		TeeUserGroup userGroup = userGroupList.get(i);
    		//model = userGroupModelList.get(i);
    		BeanUtils.copyProperties(userGroup, model);
    		model.setUuid(userGroup.getSid()+"");
    		List<Map> userList = new ArrayList<Map>();
    		StringBuffer userListIds = new StringBuffer("");
    		StringBuffer userListNames = new StringBuffer("");
    		for(int j = 0;j<userGroup.getUserList().size();j++){
    			TeePerson pTemp = userGroup.getUserList().get(j);
    			Map map = new HashMap();
    			map.put("uuid", pTemp.getUuid());
    			map.put("userId", pTemp.getUserId());
    			map.put("userName", pTemp.getUserName());
    			userList.add(map);
    			if(j ==0 ){
    				userListIds.append(pTemp.getUuid());
    				userListNames.append(pTemp.getUserName());
    			}else{
    				userListIds.append("," + pTemp.getUuid());
    				userListNames.append("," + pTemp.getUserName());
    			}
    		}
    		model.setUuid(userGroup.getSid()+"");
    		model.setUserListInfo(userList);
    		model.setUserListIds(userListIds.toString());
    		model.setUserListNames(userListNames.toString());
    		userGroupModelList.add(model);
    	}
    	System.out.println(userGroupModelList.toString());
    	json.setRtData(userGroupModelList);
    	json.setRtState(true);
		json.setRtMsg("查询成功！");
		return json;
	}
	

	
	
	/**
	 * 获取分组列表 包括 公共和个人
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPublicAndPersonalUserGroup")
	@ResponseBody
	public TeeJson getPublicAndPersonalUserGroup(HttpServletRequest request)
			throws Exception {
	    TeeJson json = new TeeJson();
	    //TeeUserGroupModel userGroupModel = new TeeUserGroupModel();
	    List<TeeUserGroupModel> userGroupModelList = new ArrayList<TeeUserGroupModel>();
	    TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
    	List<TeeUserGroup> userGroup = userGroupServ.getPublicUserGroupAndPerson(person.getUuid());
    	for(int i = 0;i<userGroup.size();i++){
    		TeeUserGroupModel model = new TeeUserGroupModel();
    		TeeUserGroup group = userGroup.get(i);
    		//model = userGroupModelList.get(i);
    		BeanUtils.copyProperties(group, model);
    		model.setUuid(group.getSid()+"");
    		userGroupModelList.add(model);
    	}
    //	System.out.println(userGroupModelList.toString());
    	json.setRtData(userGroupModelList);
    	json.setRtState(true);
		json.setRtMsg("查询成功！");
		return json;
	}
	
	
	
	
	
	/**
	 * 工作流转交     按自定义组
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPublicAndPersonalUserGroupWorkFlow")
	@ResponseBody
	public TeeJson getPublicAndPersonalUserGroupWorkFlow(HttpServletRequest request)
			throws Exception {
	   
		return  userGroupServ.getPublicAndPersonalUserGroupWorkFlow(request);
	}
	
	/**
	 * 查询 byId
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUserGroupById")
	@ResponseBody
	public TeeJson getUserGroupById(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		String uuid = request.getParameter("uuid");
		TeeUserGroupModel model = new TeeUserGroupModel();
	
		if (!TeeUtility.isNullorEmpty(uuid)) {
			TeeUserGroup userGroup = userGroupServ.selectUserGroupByUuid(Integer.parseInt(uuid));
			TeeJsonUtil jsonUtil = new TeeJsonUtil();
			
			BeanUtils.copyProperties(userGroup, model);
			model.setUuid(userGroup.getSid()+"");
			String userListIds = "";
			String userListNames = "";
			for(int i = 0;i<userGroup.getUserList().size();i++){
				userListIds+= userGroup.getUserList().get(i).getUuid()+",";
				userListNames+=userGroup.getUserList().get(i).getUserName()+",";
			}
			//personDao.getPersonNameAndUuidByUuids();
			model.setUserListIds(userListIds);
			model.setUserListNames(userListNames);
		}
		json.setRtData(model);
		json.setRtMsg("获取成功");
		json.setRtState(true);
		return json;

	}
	
	/**
	 * 删除
	 * 
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delUserGroup")
	@ResponseBody
	public TeeJson delUserGroup(HttpServletRequest request) throws Exception {

		TeeJson json = new TeeJson();
		String uuid = request.getParameter("uuid");
		TeeUserGroup userGroup = new TeeUserGroup();
		if(!TeeUtility.isNullorEmpty(uuid)){
			userGroup = userGroupServ.selectUserGroupByUuid(Integer.parseInt(uuid));
			//userGroup.setSid( Integer.parseInt(uuid));
			userGroupServ.delUserGroup(userGroup);
		}
		json.setRtMsg("删除成功");
		json.setRtState(true);
		return json;
	}
	

	
	
	
	
}
