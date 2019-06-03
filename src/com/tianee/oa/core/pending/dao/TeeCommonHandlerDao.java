package com.tianee.oa.core.pending.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.pending.bean.TeeCommonHandler;
import com.tianee.oa.core.pending.model.TeeCommonHandlerModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;
@Repository("cHandlerDao")
public class TeeCommonHandlerDao extends TeeBaseDao<TeeCommonHandler>{
	
	/*
	 * 分页检索查询
	 */
	public List<TeeCommonHandler> list(int firstResult,int rows,TeeCommonHandlerModel querymodel){
		String hql="from TeeCommonHandler where 1=1";
		if(!TeeUtility.isNullorEmpty(querymodel.getPendingTitle())){
			hql+="and pendingTitle like '%"+querymodel.getPendingTitle()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(querymodel.getPendingContent())){
			hql+="and pendingContent like '%"+querymodel.getPendingContent()+"%'";
		}
		if(!("0").equals(querymodel.getModel())){
			hql+="and model like '%"+querymodel.getModel()+"%'";
		}
		return super.pageFind(hql, firstResult, rows, null);
		
	}
	
	/*
	 * 返回总数
	 */
	public long  getTotal(TeeCommonHandlerModel querymodel){
		
		String hql="select count(uid) from TeeCommonHandler where 1=1";
		
		if(!TeeUtility.isNullorEmpty(querymodel.getPendingTitle())){
			hql+="and pendingTitle like '%"+querymodel.getPendingTitle()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(querymodel.getPendingContent())){
			hql+="and pendingContent like '%"+querymodel.getPendingContent()+"%'";
		}
		if(!("0").equals(querymodel.getModel())){
			hql+="and model like '%"+querymodel.getModel()+"%'";
		}
		return super.count(hql, null);
	}
}
