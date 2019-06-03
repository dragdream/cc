package com.beidasoft.xzfy.caseTrial.template.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzfy.caseTrial.template.bean.Template;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class TemplateDao extends TeeBaseDao<Template>{

	/**
	 * Description:查询所有
	 * @author ZCK
	 * @param hql
	 * @param firstResult
	 * @param pageSize
	 * @param list
	 * @return
	 * List<Template>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Template> getAll(final String hql, int firstResult,int pageSize, List list) {
		Session session = this.getSession();
		List<Template> result = null;
		Query query = session.createQuery(hql);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				query.setParameter(i, list.get(i));
			}
		}
		query.setFirstResult(firstResult);
		query.setMaxResults(pageSize);
		result = query.list();
		return result;
	}
	
	/**
	 * Description:查询
	 * @author ZCK
	 * @param list
	 * @return
	 * List<Template>
	 */
	@SuppressWarnings("unchecked")
	public List<Template> getByMcAndCodeAndOrganId(List<String> list){
		Session session = this.getSession();
		List<Template> result = null;
		String hql = " from Template where isDelete = 0 and documentName = ? and typesOfCode = ? ";
		if(StringUtils.isNotEmpty(list.get(2))) hql+="and fyOrganId = ?";
		Query query = session.createQuery(hql);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if(StringUtils.isNotEmpty(list.get(i))) query.setParameter(i, list.get(i));
			}
		}
		System.out.println(hql);
		result = query.list();
		return result;
	}
}
