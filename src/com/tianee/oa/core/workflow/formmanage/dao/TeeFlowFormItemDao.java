package com.tianee.oa.core.workflow.formmanage.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class TeeFlowFormItemDao extends TeeBaseDao<TeeFormItem>{
	
	/**
	 * 获取指定表单中的表单字段集合
	 * @param form
	 * @return
	 */
	public List<TeeFormItem> listFormItems(TeeForm form){
		Session session = this.getSession();
		Criteria c = session.createCriteria(TeeFormItem.class,"formItem");
		c.add(Restrictions.eq("formItem.form.sid", form.getSid()));
		c.addOrder(Order.asc("formItem.itemId"));
		List<TeeFormItem> list = c.list();
		return list;
	}
	
	/**
	 * 获取指定表单中的表单字段集合
	 * @param form
	 * @return
	 */
	public List<TeeFormItem> listFormItems(int formId){
		Session session = this.getSession();
		Criteria c = session.createCriteria(TeeFormItem.class,"formItem");
		c.add(Restrictions.eq("formItem.form.sid", formId));
		c.addOrder(Order.asc("formItem.itemId"));
		List<TeeFormItem> list = c.list();
		return list;
	}
	
	/**
	 * 将延迟加载的表单项集合排序
	 * @param items
	 * @return
	 */
	public List<TeeFormItem> listSortedFormItems(List<TeeFormItem> items){
		Session session = this.getSession();
		Query query = session.createFilter(items, "order by this.sortNo asc");
		List<TeeFormItem> items0 = new ArrayList<TeeFormItem>();
		Iterator<TeeFormItem> it = query.iterate();
		while(it!=null && it.hasNext()){
			items0.add(it.next());
		}
		return items0;
	}
	
}
