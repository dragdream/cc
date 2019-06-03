package com.tianee.oa.core.base.news.dao;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.news.bean.TeeNewsInfo;
import com.tianee.oa.core.base.notify.bean.TeeNotifyInfo;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class TeeNewsInfoDao extends TeeBaseDao<TeeNewsInfo> {

	public TeeNewsInfoDao() {
		// TODO Auto-generated constructor stub
	}
		
	/**
	 * ny
	 * @param news
	 */
	public void addNewsInfo(TeeNewsInfo news){
		save(news);
	}
	
	
	public void updateNewsInfo(TeeNewsInfo info){
		update(info);
	}

	/**
	 * 删除 byId
	 * 
	 * @author syl
	 * @date 2014-3-4
	 * @param id
	 * @return
	 */
	public void deleteById(int id) {
		delete(id);
	}

	
	/**
	 * 删除 byId
	 * 
	 * @author syl
	 * @date 2014-3-4
	 * @param id
	 * @return
	 */
	public void deleteByInfo(TeeNewsInfo info) {
		deleteByObj(info);
	}
	
	/**
	 * 删除所有人员读取状态记录
	 * @param newsId
	 */
	public void delectByNewsId(int newsId){
		String hql = "delete from TeeNewsInfo n where exists (select 1 from n.news news  where news.sid in ("+newsId+") )" ;
	//	Object[] obj = {newsId};
		deleteOrUpdateByQuery(hql,null) ;
	}
}
