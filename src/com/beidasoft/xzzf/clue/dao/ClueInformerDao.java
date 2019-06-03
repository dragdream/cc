package com.beidasoft.xzzf.clue.dao;



import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.clue.bean.ClueInformer;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class ClueInformerDao extends TeeBaseDao<ClueInformer>{
	@SuppressWarnings("unchecked")
	public List<ClueInformer> getByClueId(String clueId) {
		Session session = this.getSession();
		List<ClueInformer> personList = null;
		
		Query query = session.createQuery("from ClueInformer where clueId = ?");
		query.setString(0, clueId);
		personList =query.list();
		
		return personList;
	}

	public List<ClueInformer> findClueInformers() {
		return super.find("from ClueInformer", null);
	}

	public void deleteByClueId(String clueId) {
		Session session = this.getSession();
		
		Query query = session.createQuery("delete from ClueInformer where clueId = ?");
		query.setString(0, clueId);

		query.executeUpdate();
	}
}

