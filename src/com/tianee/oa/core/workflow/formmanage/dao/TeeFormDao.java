package com.tianee.oa.core.workflow.formmanage.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormSort;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class TeeFormDao extends TeeBaseDao<TeeForm>{
	
	public List<TeeForm> findByFormSort(TeeFormSort formSort){
		if(formSort==null){
			throw new HibernateException("传入的表单分类为空");
		}
		return findByFormSort(formSort.getSid());
	}
	
	public List<TeeForm> findByFormSort(int formSortId){
		return this.find("from TeeForm form where form.formSort.sid=? order by form.sid asc", new Object[]{formSortId});
	}
}
