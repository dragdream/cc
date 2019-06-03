package com.beidasoft.xzzf.punish.document.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.document.bean.DocArticlesDetail;
import com.beidasoft.xzzf.punish.document.dao.ArticlesDetailDao;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class ArticlesDetailService extends TeeBaseService{

	@Autowired
	private ArticlesDetailDao ArticlesDetailDao;
	
	/**
	 * 保存物品清单附表
	 * @param docArticlesDetail
	 */
	public void save(DocArticlesDetail docArticlesDetail) {
		ArticlesDetailDao.saveOrUpdate(docArticlesDetail);
	}

	/**
	 * 获取单个物品清单附表
	 * @param id
	 * @return
	 */
	public List<DocArticlesDetail> getByBaseId(String baseId) {
		return ArticlesDetailDao.getByBaseId(baseId);
	}
	/**
	 * 根据ID查询对象
	 * @param id
	 * @return
	 */
	public DocArticlesDetail getObjById(String id){
		return ArticlesDetailDao.get(id);
	}
	
	/**
	 * 更新 物品清单附表
	 * @param docArticlesDetail
	 */
	public void update(DocArticlesDetail docArticlesDetail) {
		ArticlesDetailDao.update(docArticlesDetail);
	}
	
	public void del(String baseId) {
		ArticlesDetailDao.del(baseId);
	}
}
