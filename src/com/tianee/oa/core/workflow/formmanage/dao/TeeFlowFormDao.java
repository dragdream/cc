package com.tianee.oa.core.workflow.formmanage.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class TeeFlowFormDao extends TeeBaseDao<TeeForm>{
	
	/**
	 * 获取指定表单的历史表单版本
	 * @param form
	 * @return
	 */
	public List<TeeForm> listHistoryVersion(TeeForm form){
		if(form.getFormGroup()==0){
			return new ArrayList<TeeForm>();
		}
		Session session = this.getSession();
		Criteria c = session.createCriteria(TeeForm.class,"form");
		c.add(Restrictions.eq("form.formGroup", form.getFormGroup()));
		c.addOrder(Order.asc("form.versionNo"));
		List<TeeForm> list = c.list();
		return list;
	}
	
	/**
	 * 获取指定版本的表单
	 * @param formGroup
	 * @param versionNo
	 * @return
	 */
	public TeeForm getVersionForm(int formGroup,int versionNo){
		Session session = this.getSession();
		Criteria c = session.createCriteria(TeeForm.class,"form");
		c.add(Restrictions.eq("form.formGroup", formGroup));
		c.add(Restrictions.eq("form.versionNo", versionNo));
		return (TeeForm) c.uniqueResult();
	}
	
	/**
	 * 获取最新的表单版本
	 * @param form
	 * @return
	 */
	public TeeForm getLatestVersion(TeeForm form){
		if(form==null){
			return null;
		}
		if(form.getFormGroup()==0){
			return form;
		}
		Session session = this.getSession();
		Criteria c = session.createCriteria(TeeForm.class,"form");
		c.add(Restrictions.eq("form.formGroup", form.getFormGroup()));
		c.addOrder(Order.desc("form.versionNo"));
		c.setMaxResults(1);
		c.setFirstResult(0);
		return (TeeForm) c.uniqueResult();
	}
	
	/**
	 * 获取原始表单版本
	 * @param form
	 * @return
	 */
	public TeeForm getFirstVersion(TeeForm form){
		if(form==null){
			return null;
		}
		if(form.getFormGroup()==0){
			return form;
		}
		
		Session session = this.getSession();
		Criteria c = session.createCriteria(TeeForm.class,"form");
		c.add(Restrictions.eq("form.sid", form.getFormGroup()));
		c.add(Restrictions.eq("form.versionNo", 1));
		form = (TeeForm) c.uniqueResult();
		return form;
	}
	
	/**
	 * 通过表单分类获取表单实体集合，只获取版本号为1的原始表单实体
	 * @param sortId
	 * @return
	 */
	public List<TeeForm> getFlowFormBySort1(int sortId,TeePerson loginUser,boolean isAdmin){
		if(isAdmin){//系统管理员
			if(sortId>0){
				return super.find("from TeeForm form where form.formSort.sid=? and form.versionNo=1 order by form.sid asc", new Object[]{sortId});
			}else{
				return super.find("from TeeForm form where form.formSort is null and form.versionNo=1 order by form.sid asc", new Object[]{});
			}
			
		}else{
			if(sortId>0){
				return super.find("from TeeForm form where form.formSort.sid=? and form.versionNo=1 and (form.dept is null or form.dept.uuid=? ) order by form.sid asc", new Object[]{sortId,loginUser.getDept().getUuid()});
			}else{
				return super.find("from TeeForm form where form.formSort is null and form.versionNo=1 and (form.dept is null or form.dept.uuid=? ) order by form.sid asc", new Object[]{loginUser.getDept().getUuid()});
			}
			
		}
		
	}
	
	
	public List<TeeForm> getFlowFormBySort(int sortId){
		
			if(sortId>0){
				return super.find("from TeeForm form where form.formSort.sid=? and form.versionNo=1 order by form.sid asc", new Object[]{sortId});
			}else{
				return super.find("from TeeForm form where form.formSort is null and form.versionNo=1 order by form.sid asc", new Object[]{});
			}
	}
	
	public List<TeeForm> getFlowFormBySort(TeePerson loginUser,boolean isAdmin,int sortId,int firstResult,int pageSize){
		if(isAdmin){//当前登录用户是系统管理员
			if(sortId==-1){
				return super.pageFind("from TeeForm form where form.formSort is null and form.versionNo=1 order by form.sid asc",firstResult,pageSize,null);
			}else{
				return super.pageFind("from TeeForm form where form.formSort.sid=? and form.versionNo=1 order by form.sid asc",firstResult,pageSize, new Object[]{sortId});
			}
			
		}else{//当前登录的用户不是系统管理员
			if(sortId==-1){
				return super.pageFind("from TeeForm form where form.formSort is null and form.versionNo=1 and (form.dept.uuid=? or form.dept is null) order by form.sid asc",firstResult,pageSize,new Object[]{loginUser.getDept().getUuid()});
			}else{
				return super.pageFind("from TeeForm form where form.formSort.sid=? and form.versionNo=1 and (form.dept.uuid=? or form.dept is null) order by form.sid asc",firstResult,pageSize, new Object[]{sortId,loginUser.getDept().getUuid()});
			}	
		}
		
		
	}
	
	
	
	
	public long getFlowFormCountBySort(int sortId,boolean isAdmin,TeePerson loginUser){
		if(isAdmin){//是系统管理员
			if(sortId>0){
				return super.count("select count(*) from TeeForm form where form.formSort.sid=? and form.versionNo=1 ", new Object[]{sortId});
			}else{
				return super.count("select count(*) from TeeForm form where form.formSort is null and form.versionNo=1 ", new Object[]{});
			}
		}else{
			if(sortId>0){
				return super.count("select count(*) from TeeForm form where form.formSort.sid=? and form.versionNo=1 and (form.dept.uuid=? or form.dept is null) ", new Object[]{sortId,loginUser.getDept().getUuid()});
			}else{
				return super.count("select count(*) from TeeForm form where form.formSort is null and form.versionNo=1 and (form.dept.uuid=? or form.dept is null) ", new Object[]{loginUser.getDept().getUuid()});
			}
			
			
		}
		
	}
	
	public List<TeeForm> list(){
		return super.find("from TeeForm form where form.versionNo=1 order by form.sid asc", null);
	}
	
	/**
	 * 根据表单获取所绑定流程的数量
	 * @param formId
	 * @return
	 */
	public long getCountOfBundledFlowType(int formId){
		long c = count("select count(*) from TeeFlowType where form.sid=?", new Object[]{formId});
		return formId;
	}
	
	/**
	 * 根据表单获取所绑定流程集合
	 * @param formId
	 * @return
	 */
	public List<TeeFlowType> getBundledFlowTypes(int formId){
		Session session = getSession();
		Query query = session.createQuery("from TeeFlowType where form.sid="+formId);
		return query.list();
	}
}
