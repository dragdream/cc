package com.tianee.oa.core.pending.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.pending.bean.TeeDaiBanEventView;
import com.tianee.oa.core.pending.model.TeeDaiBanEventModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("dbeDao")
public class TeeDaiBanEventViewDao extends TeeBaseDao<TeeDaiBanEventView>{
	
	/*
	 * 返回总数
	 */
	public long  getTotal(TeeDaiBanEventModel querymodel,TeePerson loginUser){
		List list=new ArrayList();
		list.add(loginUser.getUuid());
		String hql="select count(title) from TeeDaiBanEventView where recUserId = ?";
		
		if(!TeeUtility.isNullorEmpty(querymodel.getTitle())){
			hql+="and title like '%"+querymodel.getTitle()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(querymodel.getContent())){
			hql+="and content like '%"+querymodel.getContent()+"%'";
		}
		if(!("0").equals(querymodel.getModel())){
			hql+="and model like '%"+querymodel.getModel()+"%'";
		}
		return super.count(hql, list.toArray());
	}
	
	/*
	 * 分页检索查询
	 */
	public List<TeeDaiBanEventView> list(int firstResult,int rows,TeeDaiBanEventModel querymodel,TeePerson loginUser){
		List list=new ArrayList();
		list.add(loginUser.getUuid());
		
		String hql="from TeeDaiBanEventView where recUserId= ?";
		if(!TeeUtility.isNullorEmpty(querymodel.getTitle())){
			hql+="and title like '%"+querymodel.getTitle()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(querymodel.getContent())){
			hql+="and content like '%"+querymodel.getContent()+"%'";
		}
		if(!("0").equals(querymodel.getModel())){
			hql+="and model like '%"+querymodel.getModel()+"%'";
		}
		hql+=" order by createTime desc";
		return super.pageFind(hql, firstResult, rows, list.toArray());
		
	}
}
