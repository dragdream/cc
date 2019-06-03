package com.tianee.oa.core.general.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.bean.TeePortalTemplate;
import com.tianee.oa.core.general.bean.TeePortalTemplateUserData;
import com.tianee.oa.core.general.dao.TeePortalTemplateDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeePortalTemplateService extends TeeBaseService{
	@Autowired
	TeePortalTemplateDao templateDao;
	@Autowired
	TeeDeptDao deptDao;
	@Autowired
	TeePersonDao personDao;
	@Autowired
	TeeUserRoleDao userRoleDao;
	
	public TeePortalTemplate addTemplate(TeePortalTemplate template){
		templateDao.save(template);
		return template;
	}
	
	public TeePortalTemplate addPortalTemplate(TeePortalTemplate template) throws Exception{
		if(!TeeUtility.isNullorEmpty(template.getDeptPriv())){
			String deptPriv = template.getDeptPriv();
			if(deptPriv.endsWith(",")){
				deptPriv = deptPriv.substring(0,deptPriv.length()-1);
			}
			String[] tmp = deptPriv.split(",");
			String priv="";
			for(String t:tmp){
				priv+=t+".d/";
			}
			if(priv.endsWith("/")){
				priv = priv.substring(0,priv.length()-1);
			}
			template.setDeptPriv(priv);
		}
		if(!TeeUtility.isNullorEmpty(template.getUserPriv())){
			String userPriv = template.getUserPriv();
			if(userPriv.endsWith(",")){
				userPriv = userPriv.substring(0,userPriv.length()-1);
			}
			String[] tmp = userPriv.split(",");
			String priv="";
			for(String t:tmp){
				priv+=t+".u/";
			}
			if(priv.endsWith("/")){
				priv = priv.substring(0,priv.length()-1);
			}
			template.setUserPriv(priv);
		}
		
		if(!TeeUtility.isNullorEmpty(template.getRolePriv())){
			String rolePriv = template.getRolePriv();
			if(rolePriv.endsWith(",")){
				rolePriv = rolePriv.substring(0,rolePriv.length()-1);
			}
			String[] tmp = rolePriv.split(",");
			String priv="";
			for(String t:tmp){
				priv+=t+".r/";
			}
			if(priv.endsWith("/")){
				priv = priv.substring(0,priv.length()-1);
			}
			template.setRolePriv(priv);
		}
		addTemplate(template);
		return template;
	}
	
	public void delPortalTemplate(TeePortalTemplate model){
		TeePortalTemplate template = new TeePortalTemplate();
		template.setSid(model.getSid());
		templateDao.deleteByObj(template);
	}
	
	public void updateTemplate(TeePortalTemplate template){
		templateDao.update(template);
	}
	
	public void editPortalTemplate(TeePortalTemplate template) throws Exception{
		if(!TeeUtility.isNullorEmpty(template.getDeptPriv())){
			String deptPriv = template.getDeptPriv();
			if(deptPriv.endsWith(",")){
				deptPriv = deptPriv.substring(0,deptPriv.length()-1);
			}
			String[] tmp = deptPriv.split(",");
			String priv="";
			for(String t:tmp){
				priv+=t+".d/";
			}
			if(priv.endsWith("/")){
				priv = priv.substring(0,priv.length()-1);
			}
			template.setDeptPriv(priv);
		}
		if(!TeeUtility.isNullorEmpty(template.getUserPriv())){
			String userPriv = template.getUserPriv();
			if(userPriv.endsWith(",")){
				userPriv = userPriv.substring(0,userPriv.length()-1);
			}
			String[] tmp = userPriv.split(",");
			String priv="";
			for(String t:tmp){
				priv+=t+".u/";
			}
			if(priv.endsWith("/")){
				priv = priv.substring(0,priv.length()-1);
			}
			template.setUserPriv(priv);
		}
		
		if(!TeeUtility.isNullorEmpty(template.getRolePriv())){
			String rolePriv = template.getRolePriv();
			if(rolePriv.endsWith(",")){
				rolePriv = rolePriv.substring(0,rolePriv.length()-1);
			}
			String[] tmp = rolePriv.split(",");
			String priv="";
			for(String t:tmp){
				priv+=t+".r/";
			}
			if(priv.endsWith("/")){
				priv = priv.substring(0,priv.length()-1);
			}
			template.setRolePriv(priv);
		}
		
		//清空所有用户桌面数据
		simpleDaoSupport.executeUpdate("delete from TeePortalTemplateUserData where portalTemplate.sid="+template.getSid(), null);
		
		updateTemplate(template);
	}
	
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,Map requestDatas,int type){
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		datagird = templateDao.getTemplateList(dm, requestDatas,type);
		return datagird;
	}
	
	
	public TeePortalTemplate getById(int sid){
		TeePortalTemplate template = (TeePortalTemplate) templateDao.get(sid);
		String userPriv = null;
		String deptPriv = null;
		String rolePriv = null;
		
		if(template.getUserPriv()!=null){
			userPriv = template.getUserPriv().replaceAll(".u/", ",").replace(".u", "");
		}else{
			userPriv = "";
		}
		if(template.getDeptPriv()!=null){
			deptPriv = template.getDeptPriv().replaceAll(".d/", ",").replace(".d", "");
		}else{
			deptPriv = "";
		}
		if(template.getRolePriv()!=null){
			rolePriv = template.getRolePriv().replaceAll(".r/", ",").replace(".r", "");
		}else{
			rolePriv = "";
		}
		
		TeePortalTemplate portal = new TeePortalTemplate();
		portal.setTemplateName(template.getTemplateName());
		portal.setSortNo(template.getSortNo());
		portal.setUserPriv(userPriv);
		portal.setDeptPriv(deptPriv);
		portal.setRolePriv(rolePriv);
		portal.setUserPrivDesc(template.getUserPrivDesc());
		portal.setDeptPrivDesc(template.getDeptPrivDesc());
		portal.setRolePrivDesc(template.getRolePrivDesc());
		portal.setCols(template.getCols());
		portal.setPortalModel(template.getPortalModel());
		
		return portal;
	}

	/**
	 * 获取当前登录人有权限的模板
	 * @param person
	 * @return
	 */
	public List<TeePortalTemplate> getTemplateList(TeePerson person,int deptId) {
		
		return templateDao.getTemplateList(person,deptId);
	}

	
}
