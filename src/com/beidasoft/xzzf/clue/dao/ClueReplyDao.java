package com.beidasoft.xzzf.clue.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.clue.bean.ClueReply;
import com.beidasoft.xzzf.clue.model.ClueReplyModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class ClueReplyDao extends TeeBaseDao<ClueReply>{

	public List<ClueReply> findClueReply(ClueReplyModel clueReplyModel, TeePerson person) {
		String hql = "from ClueReply where clueId = '" + clueReplyModel.getClueId() + "'";
		hql = hql + " order by  replyDate desc, replyType desc";
		
		return super.find(hql, null);
	}

	/**
	 * 获取回复列表长度
	 * 
	 * @param id
	 * @return
	 */
	public Long getReplyListSize(String id) {
		String hql = "select count(id) from ClueReply where clueId = '" + id + "'";
		return super.count(hql, null);
	}

	/**
	 * 获取回复list
	 * 
	 * @param id
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<ClueReply> getReplyList(String id, int firstResult, int rows) {
		String hql = " from ClueReply where clueId = '" + id + "'";
		hql += " order by replyType, replyDate desc";
		return super.pageFind(hql, firstResult, rows, null);
	}

	public List<ClueReply> findClueReply(String sourceId) {
		String hql = "from ClueReply where clueId = '" + sourceId + "'";
		hql = hql + " order by replyType, replyDate desc";
		
		return super.find(hql, null);
	}
}
