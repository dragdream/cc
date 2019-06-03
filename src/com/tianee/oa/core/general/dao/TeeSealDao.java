package com.tianee.oa.core.general.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.general.bean.TeeSeal;
import com.tianee.oa.core.general.model.TeeSealModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("sealDao")
public class TeeSealDao extends TeeBaseDao<TeeSeal> {
	/**
	 * 新增
	 * @param seal
	 */
	public void Add(TeeSeal seal){
		save(seal);
	}
	
	/**
	 * 更新
	 * @param seal
	 */
	public void updateSeal(TeeSeal seal){
		update(seal);
	}
	   
	/**
	 * 查询所有记录
	 * @param 
	 */
	public List<TeeSeal> selectAll() {
		String hql = "from TeeSeal";
		List<TeeSeal> list = (List<TeeSeal>) executeQuery(hql,null);
		return list;
	}
	
	
	/**
	 * 用户管理 --- 按部门查询  管理范围 和 角色
	 * @param firstResult
	 * @param pageSize
	 * @param dm
	 * @param deptId
	 * @param person
	 * @return
	 */
	public  List<TeeSeal> getSealPageFind(int firstResult,int pageSize,TeeDataGridModel dm,TeeSealModel model,int deptId,boolean  isSuperAdmin ) { 
		
		//Object[] param = {};
		List list = new ArrayList();
		String hql = " from TeeSeal  where  1=1 ";
		if(!TeeUtility.isNullorEmpty(model.getSealId())){
			hql = hql + " and sealId like ?" ; 
			list.add( model.getSealId() + "%");
		}
		
		if(!TeeUtility.isNullorEmpty(model.getSealName())){
			hql = hql + " and sealName like ?" ; 
			list.add( model.getSealName() + "%");
		}
		if(TeeUtility.isNullorEmpty(dm.getOrder()) ){
			dm.setOrder("desc");
		}
	    hql = hql + " order by createTime "  +  dm.getOrder();
	    
		return pageFindByList(hql, firstResult, pageSize, list);
	 }
	/**
	 * 查询所有记录数
	 * @param 
	 */
	public long selectAllCount() {
		String hql = "select count(*) from TeeSeal";
		long count = count(hql, null);
		return count;
	}

	/**
	 * byId  查询
	 * @param 
	 */
	public TeeSeal loadById(int sid) {
		TeeSeal seal = load(sid);
		return seal;
	}
	
	/**
	 * byId  查询
	 * @param 
	 */
	public TeeSeal getById(int sid) {
		TeeSeal seal = get(sid);
		return seal;
	}
	
	/**
	 * byId  del
	 * @param 
	 */
	public void delById(int sid) {
		delete(sid);
	}
	
	/**
	 * byId  删除
	 * @param 
	 */
	public void delByObj(TeeSeal seal) {
		deleteByObj(seal);
	}
	
	/**
	 * 获取sealId下一个编号
	 * @param sealIdPre
	 * @return
	 */
	public String getNextSealId(String sealIdPre){
		Object[] values = {sealIdPre + "%"};
		String hql = " select max(sealId) from TeeSeal where sealId like ? ";
		 Query query = this.getSession().createQuery(hql);  
	      for (int i = 0; values != null && i < values.length; i++) {  
	          query.setParameter(i, values[i]);  
	      }  
	     String maxSealId = (String)query.uniqueResult();
	     maxSealId =  TeeStringUtil.getString(maxSealId, "");
	     if(maxSealId.equals("")){
	    	 maxSealId =  "0001";
	     }else{
	    	 String result = "";
	    	 int maxNum =TeeStringUtil.getInteger(maxSealId.substring(sealIdPre.length(), maxSealId.length()),0) + 1 ;
	    	 String maxStr = String.valueOf(maxNum);
	          if(maxStr.length() == 1){
	            result = "000" + maxStr;
	          }else if(maxStr.length() == 2){
	            result = "00" + maxStr;
	          }else if(maxStr.length() == 3){
	            result = "0" + maxStr;
	          }else if(maxStr.length() == 4){
	            result = maxStr;
	          }
	    	 maxSealId =  result;
	     }
	     return maxSealId;   
	}
	
	/**
	 * byId  del
	 * @param 
	 */
	public void delBySids(String  sids) {
		if(TeeUtility.isNullorEmpty(sids)){
			return;
		}
		if(sids.endsWith(",")){
			sids = sids.substring(0, sids.length() - 1);
		}
		String hql = "delete from TeeSeal where sid in (" + sids + ")";
		deleteOrUpdateByQuery(hql, null);
	}
	
	
	/**
	 * 启用或者停止印章
	 * @param sids
	 * @param isflag
	 * @return
	 */
	
	public int openOrstopSeal(String sids , String isflag) {
		if(TeeUtility.isNullorEmpty(sids)){
			return 0 ;
		}
		if(sids.endsWith(",")){
			sids = sids.substring(0, sids.length() - 1);
		}
		String hql = "update  TeeSeal  set isFlag = " + isflag +  " where sid in (" + sids + ")";
		int count  = deleteOrUpdateByQuery(hql, null);
		return count;
	}
	
	/**
	 * 启用或者停止印章
	 * @param sid 
	 * @param userStr  人员Id字符串
	 * @return
	 */
	
	public int setSealPriv(String sid , String userStr) {
		if(TeeUtility.isNullorEmpty(sid)){
			return 0 ;
		}
		if(userStr.endsWith(",")){
			userStr = userStr.substring(0, userStr.length() - 1);
		}
		String hql = "update  TeeSeal  set userStr = '" + userStr +  "' where sid = " + sid ;
		int count  = deleteOrUpdateByQuery(hql, null);
		return count;
	}
	
	
	
	/**
	 * 获取印章名称
	 * @param 
	 */
	public List selectSealName(String sids) {
		if(sids.endsWith(",")){
			sids = sids.substring(0, sids.length() - 1);
		}
		String hql = "select sealName from TeeSeal where sid in (" + sids + ")";
		List list = executeQuery(hql, null);
		return list;
	}
	
	/**
	 * 获取登录人有权限的印章列表
	 * @param 
	 */
	public List<TeeSeal> getHavePrivSeal(int loginPersonId) {
		Object[] values = {"%," + loginPersonId + ",%"};
		String hql = "from TeeSeal  where concat(',' , userStr) || ','  like ? ";
		List<TeeSeal> list = executeQuery(hql, values);
		return list;
	}
	
	
	
}


