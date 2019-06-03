package com.beidasoft.xzzf.lawCheck.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.lawCheck.bean.LawCheckItem;
import com.beidasoft.xzzf.lawCheck.model.CheckItemModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;
@Repository
public class LawCheckItemDao extends TeeBaseDao<LawCheckItem>{

	public List<LawCheckItem> listByPage(int firstResult, int rows,
			CheckItemModel queryModel) {
        String hql ="from LawCheckItem where 1=1 ";
        if(!TeeUtility.isNullorEmpty(queryModel.getModuleId())){
			hql+="and moduleId ='"+queryModel.getModuleId()+"'";
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getItemName())){
			hql+="and itemName like '%"+queryModel.getItemName()+"%'";
		}
		
		return super.pageFind(hql, firstResult, rows, null);
	}
	

	public long getTotal(CheckItemModel queryModel) {
		String hql ="select count(id) from LawCheckItem where 1 = 1 ";
		if(!TeeUtility.isNullorEmpty(queryModel.getModuleId())){
			hql+="and moduleId ='"+queryModel.getModuleId()+"'";
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getItemName())){
			
			hql+="and itemName like '%"+queryModel.getItemName()+"%'";
		}
		return super.count(hql, null);
	}


	public List<LawCheckItem> getListById(String sid) {
		String hql ="from LawCheckItem where 1=1 ";
		hql+="and moduleId='"+sid+"'";
		return super.find(hql, null);
	}
	
}
