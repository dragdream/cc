package com.beidasoft.xzzf.transferred.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.hql.internal.ast.HqlASTFactory;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.lawCheck.bean.LawCheckItem;
import com.beidasoft.xzzf.transferred.bean.DocManagements;
import com.beidasoft.xzzf.transferred.model.ManagementsModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class ManagementsDao extends TeeBaseDao<DocManagements>{



	public DocManagements get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 通过baseId 
	 * @param managementModel
	 * @return
	 */
	public List<DocManagements> getByBaseId(String baseId) {
		Session session = this.getSession();
		String hql = "from DocManagements where baseId =:baseId order by manageId";
		@SuppressWarnings("unchecked")
		List<DocManagements> listArticlesMain = session.createQuery(hql).setString("baseId", baseId).list();
		return listArticlesMain;
	}

	public List<LawCheckItem> getBylawList(String id,
			TeeDataGridModel dataGridModel) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DocManagements> listByPage(int firstResult, int rows,
			ManagementsModel managementsModel) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delById(String baseId) {
		Session session = this.getSession();
		 String hql = "Delete DocManagements Where baseId =:baseId";
		 session.createQuery(hql).setString("baseId", baseId).executeUpdate();
	}
	
}
