package com.beidasoft.xzzf.clue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.clue.bean.ClueLeaderOpinion;
import com.beidasoft.xzzf.clue.dao.ClueLeaderOpinionDao;

@Service
public class ClueLeaderOpinionService {
	@Autowired
	private ClueLeaderOpinionDao leaderOpinionDao;
	
	/**
	 * 通过ClueId  获取 所有的领导意见List
	 * @param clueId
	 * @return
	 */
	public List<ClueLeaderOpinion> getListByClueId (String clueId) {
		return leaderOpinionDao.getListByClueId(clueId);
	}
	
	/**
	 * 保存领导意见实体类
	 * 
	 * @param o
	 */
	public void saveLeaderOpinion (ClueLeaderOpinion o) {
		leaderOpinionDao.save(o);
	}
}
