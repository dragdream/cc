package com.beidasoft.xzzf.punish.document.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.document.bean.DocEvidencedescriptionDetail;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class EvidencedescriptionDetailDao extends TeeBaseDao<DocEvidencedescriptionDetail>{

	//根据主表ID 删除所有关联的子表数据
	public void deleteByMainId(String mainId) {
		String hql = "delete from DocEvidencedescriptionDetail where mainId =:mainId";
		Session session = this.getSession();
		session.createQuery(hql).setString("mainId", mainId).executeUpdate();
	}
	
	//根据主表ID  查所有的子表数据
	public List<DocEvidencedescriptionDetail> getListByMainId(String mainId) {
		String hql = "from DocEvidencedescriptionDetail where mainId='" +mainId+"'";
		
		return super.find(hql, null);
	}
}
