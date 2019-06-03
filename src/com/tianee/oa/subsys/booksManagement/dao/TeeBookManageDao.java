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
import com.tianee.oa.subsys.booksManagement.bean.TeeBookManage;
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
@Repository("bookManageDao")
public class TeeBookManageDao extends TeeBaseDao<TeeBookManage>{

	public void add(TeeBookManage book){
		save(book);
	}
	
	public void update(TeeBookManage book){
		Session session = this.getSession();
		session.merge(book);
	}
	
	public List<TeeBookManage> bookManagerList(){
		Object[] values = {};
		String hql = "from TeeBookManage book"; 
		List<TeeBookManage> list = executeQuery(hql, values);
		return list;
	}
	
}
