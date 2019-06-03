package com.beidasoft.xzzf.clue.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.clue.bean.ClueLeaderOpinion;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class ClueLeaderOpinionDao extends TeeBaseDao<ClueLeaderOpinion>{
	
	String strStart = " '";
	String strFina = "' ";
	
	/**
	 * 通过ClueId  获取 所有的领导意见List
	 * @param clueId
	 * @return
	 */
	public List<ClueLeaderOpinion> getListByClueId(String clueId) {
		
		String hql = "from ClueLeaderOpinion where clueId = " + strStart + clueId + strFina ;
		hql += "order by createTime desc ";
		
		return super.executeQuery(hql, null);
	}
}
