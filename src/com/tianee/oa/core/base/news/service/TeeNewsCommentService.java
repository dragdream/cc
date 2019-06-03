package com.tianee.oa.core.base.news.service;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.news.bean.TeeNewsComment;
import com.tianee.oa.core.base.news.dao.TeeNewsCommentDao;
import com.tianee.oa.core.base.news.model.TeeNewsCommentModel;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeNewsCommentService extends TeeBaseService {

	@Autowired
	private TeeNewsCommentDao commentDao;

	/**
	 * 添加评论 
	 * @author zhp
	 * @createTime 2014-2-25
	 * @editTime 下午09:49:51
	 * @desc
	 */
	public void addNewsComment(TeeNewsComment comment){
		commentDao.save(comment);
	}
	
	/**
	 * 更新 评论
	 * @author zhp
	 * @createTime 2014-2-25
	 * @editTime 下午09:49:42
	 * @desc
	 */
	public void updateNewsComment(TeeNewsComment comment){
		commentDao.update(comment);
	}
	
	/**
	 * 删除评论
	 * @author zhp
	 * @createTime 2014-2-25
	 * @editTime 下午09:49:33
	 * @desc
	 */
	public void deleteNewsComment(int sid){
		String hql = "delete from TeeNewsComment c where c.sid = "+sid;
		commentDao.deleteOrUpdateByQuery(hql, null);
	}
	
	/**
	 * 删除新闻所有评论
	 * @author zhp
	 * @createTime 2014-2-27
	 * @editTime 上午12:45:28
	 * @desc
	 */
	public void deleteNewsAllComment(int newsId){
		String hql = "delete from TeeNewsComment c where c.newsId = "+newsId;
		commentDao.deleteOrUpdateByQuery(hql, null);
	}
	
	/**
	 * 获取新闻 全部评论
	 * @author zhp
	 * @createTime 2014-2-25
	 * @editTime 下午10:01:08
	 * @desc
	 */
	public List getNewsAllComments(int newsId,int count,int state,int userId){
		List<TeeNewsCommentModel> mList = new ArrayList<TeeNewsCommentModel>();
		List comList = commentDao.getNewsAllComments(newsId,count,state,userId);
		for(int i=0;comList!= null && i< comList.size();i++){
			TeeNewsComment comment = (TeeNewsComment)comList.get(i);
			TeeNewsCommentModel model = new TeeNewsCommentModel();
			BeanUtils.copyProperties(comment, model);
			if(null != comment.getReTime()){
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String strReTime = sf.format(comment.getReTime());
				model.setStrReTime(strReTime);
				//model.setSortId(i+1);
				model.setSortId(comList.size() - i);
			}
			
			mList.add(model);
		}
		return mList;
	}
	/**
	 * 获取 新闻评论
	 * @author zhp
	 * @createTime 2014-2-25
	 * @editTime 下午09:51:47
	 * @desc
	 */
	public TeeNewsComment getNewsComment(int sid){
		String hql = "from TeeNewsComment c where c.sid = "+sid;
		return commentDao.loadSingleObject(hql, null);
	}
	
	public TeeNewsCommentDao getCommentDao() {
		return commentDao;
	}

	public void setCommentDao(TeeNewsCommentDao commentDao) {
		this.commentDao = commentDao;
	}
	
}
