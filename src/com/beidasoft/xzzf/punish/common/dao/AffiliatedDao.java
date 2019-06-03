package com.beidasoft.xzzf.punish.common.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.common.bean.AffiliatedPerson;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class AffiliatedDao extends TeeBaseDao<AffiliatedPerson>{

	/**
	 * 通过baseId 获取参与人 List
	 * @param baseId
	 * @return
	 */
	public List<AffiliatedPerson> getListByBaseId(String baseId) {
		
		String hql = "from AffiliatedPerson where baseId = '" + baseId + "' ";
		
		return super.find(hql, null);
	}

	/**
	 * 通过baseId 删除参与人
	 * 
	 * @param baseId
	 */
	public void deletePersonByBaseId(String baseId) {
		if (StringUtils.isNotBlank(baseId)) {
			String hql = "delete from AffiliatedPerson where baseId = '" + baseId + "' ";
			Session session = this.getSession();
			Query query = session.createQuery(hql);
			query.executeUpdate();
		}
	}


}
