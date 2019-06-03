package com.beidasoft.xzzf.clue.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.clue.bean.ClueReply;
import com.beidasoft.xzzf.clue.dao.ClueReplyDao;
import com.beidasoft.xzzf.clue.model.ClueReplyModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class ClueReplyService extends TeeBaseService{
	@Autowired
	private ClueReplyDao clueReplyDao;
	
	public List<ClueReply> findClueReply(ClueReplyModel clueReplyModel, TeePerson person) {
		return clueReplyDao.findClueReply(clueReplyModel, person);
	}
	
	public List<ClueReply> getReplyList(String id, int firstResult, int rows) {
		return clueReplyDao.getReplyList(id, firstResult, rows);
	}

	public Long getReplyListSize(String id) {
		return clueReplyDao.getReplyListSize(id);
	}

	public void saveReply(ClueReply reply) {
		clueReplyDao.save(reply);
	}

	public List<ClueReply> findClueReply(String sourceId) {
		return clueReplyDao.findClueReply(sourceId);
	}

	public ClueReply getById(String id) {
		return clueReplyDao.get(id);
	}

	public void update(ClueReply clueReply) {
		clueReplyDao.update(clueReply);
	}
}
