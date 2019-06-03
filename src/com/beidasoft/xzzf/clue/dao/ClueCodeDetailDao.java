package com.beidasoft.xzzf.clue.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.clue.bean.ClueCodeDetail;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class ClueCodeDetailDao extends TeeBaseDao<ClueCodeDetail>{

	/**
	 * 获取举报形式List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ClueCodeDetail> findFormList() {
		Session session = this.getSession();
		List<ClueCodeDetail> formList = null;
		Query query = session.createQuery("from ClueCodeDetail where mainKey = 'ZF_REPORT_FORM' order by detailKey");
		formList = query.list();
		return formList;
	}

	/**
	 * 获取举报来源List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ClueCodeDetail> findSourceList(String reportForm) {
		Session session = this.getSession();
		List<ClueCodeDetail> sourceList = null;
		String hql="from ClueCodeDetail where detailKey like '"+reportForm+"_%' order by detailKey";
		Query query = session.createQuery(hql);
		sourceList = query.list();
		return sourceList;
	}

	/**
	 * 获取举报类型List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ClueCodeDetail> findTypeList() {
		Session session = this.getSession();
		List<ClueCodeDetail> typeList = null;
		Query query = session.createQuery("from ClueCodeDetail where detailKey = '03_%' and mainKey = 'ZF_REPORT_SOURCE' order by detailKey");
		typeList = query.list();
		return typeList;
	}

	/**
	 * 获取办理单位List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ClueCodeDetail> findDepartList() {
		Session session = this.getSession();
		List<ClueCodeDetail> departList = null;
		Query query = session.createQuery("from ClueCodeDetail where mainKey = 'ZF_CLUE_DEPT' order by detailKey");
		departList = query.list();
		return departList;
	}
}
