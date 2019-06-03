package com.tianee.oa.core.base.examine.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.base.examine.bean.TeeExamineGroup;
import com.tianee.oa.core.base.examine.bean.TeeExamineTask;
import com.tianee.oa.core.base.examine.dao.TeeExamineGroupDao;
import com.tianee.oa.core.base.examine.model.TeeExamineGroupModel;
import com.tianee.oa.core.base.examine.model.TeeExamineTaskModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 
 * @author syl
 */
@Service
public class TeeExamineGroupService extends TeeBaseService {
	@Autowired
	private TeeExamineGroupDao examineGroupDao;
	
	@Autowired
	private TeePersonDao personDao;

	@Autowired
	private TeeDeptDao deptDao;
	
	@Autowired 
	private TeeUserRoleDao userRoleDao;
	
	@Autowired
	private TeeExamineTaskService examineTaskService;


	/**
	 * @author syl
	 * 新增 或者 更新
	 * @param message
	 * @param person 系统当前登录人
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeExamineGroupModel model) throws IOException, ParseException {
	
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		
		TeeExamineGroup group = new TeeExamineGroup();
		BeanUtils.copyProperties(model, group);
		if(!TeeUtility.isNullorEmpty(model.getPostDeptIds())){//申请权限 ---部门
			List<TeeDepartment> listDept = deptDao.getDeptListByUuids(model.getPostDeptIds());
			group.setPostDept(listDept);
		}
		if(!TeeUtility.isNullorEmpty(model.getPostUserIds())){//申请权限-- 人员
			List<TeePerson> listDept = personDao.getPersonByUuids(model.getPostUserIds());
			group.setPostUser(listDept);
		}
	
		if(!TeeUtility.isNullorEmpty(model.getPostUserRoleIds())){//申请权限-- 角色
			if(!TeeUtility.isNullorEmpty(model.getPostUserRoleIds())){//发布权限 -- 角色
				List<TeeUserRole> listRole = userRoleDao.getPrivListByUuids(model.getPostUserRoleIds());
				group.setPostUserRole(listRole);
			}				
		}
		
		if(model.getSid() > 0){
			TeeExamineGroup meetRoom  = examineGroupDao.getById(model.getSid());
			if(meetRoom != null){
				int sid = meetRoom.getSid();
				BeanUtils.copyProperties(group, meetRoom);
				examineGroupDao.updateObj(meetRoom);
			}else{
				json.setRtState(false);
				json.setRtMsg("该指标集已被删除！");
				return json;
			}
		}else{
			examineGroupDao.add(group);
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		
		return json;
	}
	
	/**
	 * 获取所有记录
	 * @date 2014-3-9
	 * @author 
	 * @param request
	 * @param model
	 * @return
	 */
	
	
	/**
	 * 通用列表  ----   
	 * @param dm
	 * @return
	 * @throws ParseException 
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request , TeeExamineGroupModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		Map mapCount = examineGroupDao.getAll(loginPerson, false , 0 , 0 ,dm);
		j.setTotal(TeeStringUtil.getLong(mapCount.get("count") , 0));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage()-1) * dm.getRows() ;//获取开始索引位置
		Object parm[] = {};
		
		Map mapData = examineGroupDao.getAll(loginPerson, true , firstIndex ,dm.getRows() ,dm);
		List<TeeExamineGroup> list = (List<TeeExamineGroup>)mapData.get("data");
		List<TeeExamineGroupModel> modelList = new ArrayList<TeeExamineGroupModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeExamineGroupModel modeltemp = parseModel(list.get(i), false);
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	

	

	/**
	 * 对象转换
	 * @author syl
	 * @date 2014-1-29
	 * @param out
	 * @return
	 */
	public TeeExamineGroupModel parseModel(TeeExamineGroup room , boolean isSimple){
		TeeExamineGroupModel model = new TeeExamineGroupModel();
		if(room == null){
			return model;
		}
		BeanUtils.copyProperties(room, model);
		List<TeeDepartment> listDept = room.getPostDept();
		List<TeePerson> userList = room.getPostUser();
		List<TeeUserRole> userRoleList = room.getPostUserRole();
		String postDeptIds = "";
		String postDeptNames = "";
		String postUserIds = "";
		String postUserNames = "";
		String postUserRoleIds = "";
		String postUserRoleNames = "";
		
		if(!isSimple){
			if(listDept != null){
		    	for (int i = 0; i < listDept.size(); i++) {
		    		postDeptIds = postDeptIds + listDept.get(i).getUuid() + ",";
		    		postDeptNames = postDeptNames + listDept.get(i).getDeptName() + ",";
				}
		    }
		    
		    if(userRoleList != null){
		    	for (int i = 0; i < userRoleList.size(); i++) {
		    		postUserRoleIds = postUserRoleIds + userRoleList.get(i).getUuid() + ",";
		    		postUserRoleNames = postUserRoleNames + userRoleList.get(i).getRoleName() + ",";
				}
		    }
		    
		    if(userList != null){
		    	for (int i = 0; i < userList.size(); i++) {
		    		postUserIds = postUserIds + userList.get(i).getUuid() + ",";
		    		postUserNames = postUserNames + userList.get(i).getUserName() + ",";
				}
		    }
		}
	    
	    model.setPostDeptIds(postDeptIds);
	    model.setPostDeptNames(postDeptNames);
	    
	    model.setPostUserNames(postUserNames);
	    model.setPostUserIds(postUserIds);
	    model.setPostUserRoleIds(postUserRoleIds);
	    model.setPostUserRoleNames(postUserRoleNames);
	 
		return model;
	}
	
	
	/**
	 * 
	 * @author syl 删除ById
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson deleteById(HttpServletRequest request, TeeExamineGroupModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			//删除指标项
			simpleDaoSupport.executeUpdate("delete from TeeExamineItem where group.sid=?", new Object[]{model.getSid()});
			//删除相关所有任务
			List<TeeExamineTask> tasks = simpleDaoSupport.find("from TeeExamineTask where group.sid=?", new Object[]{model.getSid()});
			for(TeeExamineTask task:tasks){
				TeeExamineTaskModel m = new TeeExamineTaskModel();
				m.setSid(task.getSid());
				examineTaskService.deleteById(m);
			}
			examineGroupDao.delById(model.getSid());
		}
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}
	
	/**
	 * 查询 ById  
	 * @author syl
	 * @date 2014-5-24
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson selectById(HttpServletRequest request, TeeExamineGroupModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeExamineGroup group = examineGroupDao.getById(model.getSid());
			if(group != null){
				json.setRtData(parseModel(group, false));
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("该记录已被删除!");
	
		return json;
	}
	
	/**
	 * 根据当前登录人获取有权限申请的  考核指标集
	 * @author syl
	 * @date 2014-5-25
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getPostExamine(HttpServletRequest request, TeeExamineGroupModel model) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map map = examineGroupDao.selectPostExamineGroup(person, model, true);
		List<TeeExamineGroup> groupList = (List<TeeExamineGroup> )map.get("data");
		List<TeeExamineGroupModel> modelList = new ArrayList<TeeExamineGroupModel>();
		if (groupList != null) {
			for (int i = 0; i < groupList.size(); i++) {
				TeeExamineGroupModel modeltemp = parseModel(groupList.get(i), false);
				modelList.add(modeltemp);
			}
		}
		json.setRtData(modelList);
		json.setRtState(true);
		json.setRtMsg("查询陈宫");
	
		return json;
	}
		
}
