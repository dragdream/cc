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
import com.tianee.oa.subsys.booksManagement.bean.TeeBookType;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;


/**
 * 
 * @author CXT
 *
 */
@Repository("bookTypeDao")
public class TeeBookTypeDao extends TeeBaseDao<TeeBookType>{

	public void add(TeeBookType book){
		save(book);
	}
	
	public void update(TeeBookType book){
		Session session = this.getSession();
		session.merge(book);
	}
	
	public List<TeeBookType> bookTypeList(){
		Object[] values = {};
		String hql = "from TeeBookType bookType"; 
		List<TeeBookType> list = executeQuery(hql, values);
		return list;
	}
	
}
