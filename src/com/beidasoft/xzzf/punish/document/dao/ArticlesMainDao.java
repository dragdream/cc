package com.beidasoft.xzzf.punish.document.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.document.bean.DocArticlesMain;
import com.beidasoft.xzzf.punish.document.model.ArticlesMainModel;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class ArticlesMainDao extends TeeBaseDao<DocArticlesMain>{

	public List<DocArticlesMain> getByBaseId(ArticlesMainModel articlesMainModel) {
		Session session = this.getSession();
		String hql = "from DocArticlesMain where baseId =:baseId and lawLinkId =:lawLinkId";
		
		String baseId = articlesMainModel.getBaseId();
		String lawLinkId = articlesMainModel.getLawLinkId();
		@SuppressWarnings("unchecked")
		List<DocArticlesMain> listArticlesMain = session.createQuery(hql).setString("baseId", baseId).setString("lawLinkId", lawLinkId).list();
		return listArticlesMain;
	}

}
