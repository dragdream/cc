package com.tianee.oa.subsys.informationReport.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.informationReport.bean.TeeTaskTemplateType;
import com.tianee.oa.subsys.informationReport.model.TeeTaskTemplateTypeModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;
@Repository("typeDao")
public class TeeTaskTemplateTypeDao extends TeeBaseDao<TeeTaskTemplateType>{
	
	public List<TeeTaskTemplateType> taskTemplateTypeList(){
		
		return find("from TeeTaskTemplateType", null);
	}
	/*
	 * 分页检索查询
	 */
	public List<TeeTaskTemplateType> list(int firstResult,int rows,TeeTaskTemplateTypeModel querymodel){
		String hql="from TeeTaskTemplateType where 1=1";
		if(!TeeUtility.isNullorEmpty(querymodel.getTypeName())){
			hql+="and typeName like '%"+querymodel.getTypeName()+"%'";
		}
		return super.pageFind(hql, firstResult, rows, null);
		
	}
	
	/*
	 * 返回总数
	 */
	public long  getTotal(TeeTaskTemplateTypeModel querymodel){
		
		String hql="select count(sid) from TeeTaskTemplateType where 1=1";
		if(!TeeUtility.isNullorEmpty(querymodel.getTypeName())){
			hql+="and typeName like '%"+querymodel.getTypeName()+"%'";
		}
		
		return super.count(hql, null);
	}
}
