package com.tianee.oa.core.priv.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.core.priv.bean.TeeModulePriv;
import com.tianee.oa.core.priv.dao.TeeModulePrivDao;
import com.tianee.oa.core.priv.model.TeeModulePrivModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Service
public class TeeModulePrivService extends TeeBaseService {

	@Autowired
	private TeeModulePrivDao modelPrivDao;
	
	@Autowired
	private TeePersonDao  personDao;
	
	@Autowired
	private TeeDeptDao  deptDao;
	
	@Autowired
	private TeeUserRoleDao  roleDao;
	
	private TeeJson addOrUpdateBatch(TeeModulePrivModel privModel,TeeJson json){
		
		TeeModulePriv priv = modelPrivDao.selectPrivByUserId(Integer.parseInt(privModel.getPersonId()),privModel.getModuleId());
		if(priv==null){
			priv = new TeeModulePriv();
		}
		
		BeanUtils.copyProperties(privModel, priv,new String[]{"sid"});
		if(!TeeUtility.isInteger(privModel.getPersonId())){
			json.setRtState(false);
			json.setRtMsg("没有设置人员");
			return json;
		}
		//设置人员
		TeePerson person = personDao.selectPersonById(Integer.parseInt(privModel.getPersonId()));
		priv.setUserId(person);
		if(privModel.getDeptPriv().equals("2")){//指定部门
			String deptIds = privModel.getDeptIdStr();
			if(!TeeUtility.isNullorEmpty(deptIds)){
				List<TeeDepartment> deptList = deptDao.getDeptListByUuids(deptIds);
				priv.setDeptIds(new HashSet(deptList));
			}
		}
		
		if(privModel.getDeptPriv().equals("3")){//指定人员
			String userIds = privModel.getUserIdStr();
			if(!TeeUtility.isNullorEmpty(userIds)){
				List<TeePerson> userList = personDao.getPersonByUuids(userIds);
				priv.setUserIds(new HashSet(userList));
			}
		}
		
		
		if(privModel.getRolePriv().equals("3")){//指定角色
			String roleIds = privModel.getRoleIdStr();
			if(!TeeUtility.isNullorEmpty(roleIds)){
				List<TeeUserRole> roleList = roleDao.getPrivListByUuids(roleIds);
				priv.setRoleIds(new HashSet(roleList));
			}
		}
		if(priv.getSid() > 0){
			if(TeeUtility.isNullorEmpty(privModel.getDeptPriv()) &&  TeeUtility.isNullorEmpty(privModel.getRolePriv())){
				//如果指定部门和角色都为空，则删除
				priv.setDeptIds(null);
				priv.setRoleIds(null);
				priv.setUserIds(null);
				priv.setUserId(null);
				modelPrivDao.delById(priv);
			}else{
//				BeanUtils.copyProperties(priv, privOld);
				modelPrivDao.updateModulePriv(priv);
				json.setRtData( priv.getSid() + "");
			}
			json.setRtState(true);
			json.setRtMsg("保存成功");
		}else{
		
			if(!TeeUtility.isNullorEmpty(privModel.getDeptPriv()) &&  !TeeUtility.isNullorEmpty(privModel.getRolePriv())){
				modelPrivDao.addModulePriv(priv);
				json.setRtData( priv.getSid() + "");
			}else{
				json.setRtData( "0");
			}
			
			json.setRtState(true);
			json.setRtMsg("新建成功");
		}
		return json;
	}
	
    /**
     * 更新或者新增
     * @param privModel
     */
	public TeeJson addOrUpdate(TeeModulePrivModel privModel,TeeJson json){
		if(TeeUtility.isNullorEmpty(privModel.getUserIds1())){
			this.addOrUpdateBatch(privModel, json);
		}else{
			this.addOrUpdateBatch(privModel, json);
			List<TeePerson> personList =personDao.getPersonByUuids(privModel.getUserIds1());
	        for (TeePerson teePerson : personList) {
	       // 	BeanUtils.copyProperties(teePerson, privModel);
	        	privModel.setPersonId(String.valueOf(teePerson.getUuid()));
	        	this.addOrUpdateBatch(privModel, json);
			}
		}
		//personDao.getPersonByUuids("1,2,3,4,5")
		
        json.setRtState(true);
		json.setRtMsg("保存成功");
		return json;
	}

	/**
	 * 查询 byId
	 * @param TeeSysMenu
	 */
	public TeeModulePriv selectById(String sid) {
		TeeModulePriv sysMenu =  modelPrivDao.selectById(Integer.parseInt(sid));
		return sysMenu;
	}
	/**
	 * 根据人员和模块查询
	 * @param personId 人员Id
 	 * @param modelId 模块
	 * @return
	 */
	public TeeModulePrivModel  selectPrivByUserId(String personId, String modelId) {
		TeeModulePriv priv = modelPrivDao.selectPrivByUserId(Integer.parseInt(personId),Integer.parseInt(modelId));
		TeeModulePrivModel model = pasreModelByModulePriv(priv);
		return model;
	}
	
	/**
	 * 根据人员和模块查询
	 * @param personId 人员Id
 	 * @param modelId 模块
	 * @return
	 */
	public TeeModulePriv  selectPrivEntityByUserId(String personId, String modelId) {
		TeeModulePriv priv = modelPrivDao.selectPrivByUserId(Integer.parseInt(personId),TeeStringUtil.getInteger(modelId, 0));
		return priv;
	}
	
	/**
	 * 将模块权限设置转为 模型
	 * @author syl
	 * @date 2014-1-15
	 * @param priv
	 * @return
	 */
	public TeeModulePrivModel pasreModelByModulePriv(TeeModulePriv priv){
		TeeModulePrivModel model = new TeeModulePrivModel();
		if(priv == null ){
			return model;
		}
		BeanUtils.copyProperties(priv, model);
		if(priv.getUserId() != null ){
			model.setPersonId(priv.getUserId().getUuid() + "");
		}
		if(priv.getRoleIds() != null ){//指定角色
			Iterator<TeeUserRole> it = priv.getRoleIds().iterator();
			String roleIds = "";
			String roleNames = "";
			while(it.hasNext()){
				TeeUserRole role =  it.next();
				roleIds = roleIds + role.getUuid() + ",";
				roleNames = roleNames + role.getRoleName() + ",";
			}
			model.setRoleIdsName(roleNames);
			model.setRoleIdStr(roleIds);
		}
		
		
		if(priv.getDeptIds() != null ){//指定部门
			Iterator<TeeDepartment> it = priv.getDeptIds().iterator();
			String roleIds = "";
			String roleNames = "";
			while(it.hasNext()){
				TeeDepartment role =  it.next();
				roleIds = roleIds + role.getUuid() + ",";
				roleNames = roleNames + role.getDeptName() + ",";
			}
			model.setDeptIdsName(roleNames);
			model.setDeptIdStr(roleIds);
		}

		

		if(priv.getUserIds() != null ){//指定人员
			Iterator<TeePerson> it = priv.getUserIds().iterator();
			String roleIds = "";
			String roleNames = "";
			while(it.hasNext()){
				TeePerson role =  it.next();
				roleIds = roleIds + role.getUuid() + ",";
				roleNames = roleNames + role.getUserName() + ",";
			}
			model.setUserIdsName(roleNames);
			model.setUserIdStr(roleIds);
		}
		return model;
	}
	
	public void setModelPrivDao(TeeModulePrivDao modelPrivDao) {
		this.modelPrivDao = modelPrivDao;
	}

	public void setPersonDao(TeePersonDao personDao) {
		this.personDao = personDao;
	}

	public void setDeptDao(TeeDeptDao deptDao) {
		this.deptDao = deptDao;
	}

	public void setRoleDao(TeeUserRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public TeeModulePrivDao getModelPrivDao() {
		return modelPrivDao;
	}

	public TeePersonDao getPersonDao() {
		return personDao;
	}

	public TeeDeptDao getDeptDao() {
		return deptDao;
	}

	public TeeUserRoleDao getRoleDao() {
		return roleDao;
	}

		
}
