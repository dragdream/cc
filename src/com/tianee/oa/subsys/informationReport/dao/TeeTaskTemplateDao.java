package com.tianee.oa.subsys.informationReport.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.informationReport.bean.TeeTaskTemplate;
import com.tianee.oa.subsys.informationReport.model.TeeQueryModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;
@Repository("taskTemplateDao")
public class TeeTaskTemplateDao extends TeeBaseDao<TeeTaskTemplate>{
	/*
	 * 分页检索查询
	 */
	public List<TeeTaskTemplate> list(int firstResult,int rows,TeeQueryModel querymodel){
		String hql="from TeeUserInfo where 1=1";
		
		if(querymodel.getRepType()!=-1){
			hql+="and gender="+querymodel.getRepType();
		}
		return super.pageFind(hql, firstResult, rows, null);
		
	}
	
	/*
	 * 返回总数
	 */
	public long  getTotal(TeeQueryModel querymodel){
		
		String hql="select count(sid) from TeeUserInfo where 1=1";
		if(querymodel.getRepType()!=-1){
			hql+="and gender="+querymodel.getRepType();
		}
		
		return super.count(hql, null);
	}
}
