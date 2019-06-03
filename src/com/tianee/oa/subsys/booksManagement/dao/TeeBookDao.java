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
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;


/**
 * 
 * @author CXT
 *
 */
@Repository("bookDao")
public class TeeBookDao extends TeeBaseDao<TeeBookInfo>{

	public void add(TeeBookInfo book){
		save(book);
	}
	
	public List<TeeBookInfo> bookListByBookNo(String bookNo){
		Object[] values = {bookNo};
		String hql = "from TeeBookInfo book where book.bookNo = ?"; 
		List<TeeBookInfo> list = executeQuery(hql, values);
		return list;
	}
	
	public void updateBook(TeeBookInfo book){
		Session session = this.getSession();
		session.merge(book);
	}
	
	public List<TeeBookInfo> bookList(){
		Object[] values = null;
		String hql = "from TeeBookInfo book "; 
		List<TeeBookInfo> list = executeQuery(hql, values);
		return list;
	}
	
}
