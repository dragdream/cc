package com.tianee.oa.subsys.booksManagement.dao;

import java.text.ParseException;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.meeting.model.TeeMeetingModel;
import com.tianee.oa.core.base.vote.bean.TeeVote;
import com.tianee.oa.core.base.vote.bean.TeeVoteItem;
import com.tianee.oa.core.base.vote.model.TeeVoteModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.booksManagement.bean.TeeBookInfo;
import com.tianee.oa.subsys.booksManagement.bean.TeeBookManager;
import com.tianee.oa.subsys.booksManagement.bean.TeeBookType;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;


/**
 * 
 * @author CXT
 *
 */
@Repository("bookManagerDao")
public class TeeBookManagerDao extends TeeBaseDao<TeeBookManager>{

	public void add(TeeBookManager book){
		save(book);
	}
	
	public void update(TeeBookManager book){
		Session session = this.getSession();
		session.merge(book);
	}
	
	public List<TeeBookManager> bookManagerList(){
		Object[] values = {};
		String hql = "from TeeBookManager book"; 
		List<TeeBookManager> list = executeQuery(hql, values);
		return list;
	}
	
}
