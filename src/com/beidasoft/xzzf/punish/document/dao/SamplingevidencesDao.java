package com.beidasoft.xzzf.punish.document.dao;

import java.util.List;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.beidasoft.xzzf.punish.document.bean.DocSamplingevidences;
import com.beidasoft.xzzf.punish.document.model.SamplingevidencesModel;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class SamplingevidencesDao extends TeeBaseDao<DocSamplingevidences>{

	/**
	 * 通过baseId和lawLinkId 获取物品清单信息
	 * @param samplingevidencesModel
	 * @return
	 */
	public List<DocSamplingevidences> getByBaseId(SamplingevidencesModel samplingevidencesModel) {
		Session session = this.getSession();
		String hql = "from DocSamplingevidences where baseId =:baseId and lawLinkId =:lawLinkId";
		
		String baseId = samplingevidencesModel.getBaseId();
		String lawLinkId = samplingevidencesModel.getLawLinkId();
		@SuppressWarnings("unchecked")
		List<DocSamplingevidences> listArticlesMain = session.createQuery(hql).setString("baseId", baseId).setString("lawLinkId", lawLinkId).list();
		return listArticlesMain;
	}
	
}
