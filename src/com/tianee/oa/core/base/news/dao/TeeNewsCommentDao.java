package com.tianee.oa.core.base.news.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.news.bean.TeeNewsComment;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class TeeNewsCommentDao extends TeeBaseDao<TeeNewsComment> {

	/**
	 * 获取全部 新闻回复
	 * @author zhp
	 * @createTime 2014-2-25
	 * @editTime 下午11:16:29
	 * @desc
	 */
	public List getNewsAllComments(int newsId,int count,int state,int userId){
		List list = null;
		String hql = "from TeeNewsComment c where c.newsId = "+newsId;
		if(state  == 0){//自己
			hql = hql +" and c.userId = "+userId;
		}
		if(state  == 1){
			hql = hql +" and c.userId  != "+userId;
		}
		hql = hql +" order by c.reTime desc";
		if(count > -1){
			list = pageFind(hql, 0, count, null);
		}else{
			list = executeQuery(hql, null);
			
		}
		return list;
	}
}
