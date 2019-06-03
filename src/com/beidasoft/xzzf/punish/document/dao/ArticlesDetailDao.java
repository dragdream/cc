package com.beidasoft.xzzf.punish.document.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.document.bean.DocArticlesDetail;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class ArticlesDetailDao extends TeeBaseDao<DocArticlesDetail>{
	
	/**
	 * 根据mainId（案件baseId）遍历物品子表
	 * @param baseId
	 * @return
	 */
	public List<DocArticlesDetail> getByBaseId(String baseId){
		Session session = this.getSession();
		 String hql = "from DocArticlesDetail where mainId =:mainId order by goodsCode";
		@SuppressWarnings("unchecked")
		List<DocArticlesDetail> listDocArticlesDetail = session.createQuery(hql).setString("mainId", baseId).list();
		return listDocArticlesDetail;
	}
	
	/**
	 * 删除子表数据
	 * @param baseId
	 */
	public void del(String baseId){
		Session session = this.getSession();
		 String hql = "Delete DocArticlesDetail Where mainId =:mainId";
		 session.createQuery(hql).setString("mainId", baseId).executeUpdate();
	}

}
