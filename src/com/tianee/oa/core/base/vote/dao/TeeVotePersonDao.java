package com.tianee.oa.core.base.vote.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.vote.bean.TeeVotePerson;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;


/**
 * 
 * @author syl
 *
 */
@Repository("TeeVotePersonDao")
public class TeeVotePersonDao extends TeeBaseDao<TeeVotePerson>{
	
	public List<TeeVotePerson> getVotePersonList(TeePerson person,int voteId){
		
		List<TeeVotePerson> list= new ArrayList<TeeVotePerson>();
		
		Object[] values = { };
		String hql = "select vote from TeeVotePerson vote where vote.vote.sid ="+voteId+" and vote.user.uuid="+person.getUuid();
		list = (List<TeeVotePerson>) executeQuery(hql,values);
		return list;
	}
	
	
	/**
	 * 根据vote Sid获取已投票数据
	 * @function: 
	 * @author: wyw
	 * @data: 2015年6月26日
	 * @param voteId
	 * @return List<TeeVotePerson>
	 */
	public List<TeeVotePerson> getVotePersonListByVoteId(int voteId){
		List<TeeVotePerson> list= new ArrayList<TeeVotePerson>();
		Object[] values = { };
		String hql = " from TeeVotePerson where vote.sid ="+voteId ;
		list = (List<TeeVotePerson>) executeQuery(hql,values);
		return list;
	}

	
}
