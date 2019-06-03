package com.tianee.oa.core.base.vote.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.vote.bean.TeeVote;
import com.tianee.oa.core.base.vote.bean.TeeVoteItem;
import com.tianee.oa.core.base.vote.bean.TeeVoteItemPerson;
import com.tianee.oa.core.base.vote.model.TeeVoteItemModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;


/**
 * 
 * @author syl
 *
 */
@Repository("voteItemPersonDao")
public class TeeVoteItemPersonDao extends TeeBaseDao<TeeVoteItemPerson>{

	/**
	 * @author syl   
	 * 根据 投票  获取投票项目
	 * @param 
	 */
	public  List<TeeVoteItemPerson> getVoteData(int voteItemId) {
		Object[] values = {voteItemId};
		String hql = "from TeeVoteItemPerson where voteItem.sid = ?  ";
		List<TeeVoteItemPerson> list = (List<TeeVoteItemPerson>) executeQuery(hql,values);
		return list;
	}
	
}
