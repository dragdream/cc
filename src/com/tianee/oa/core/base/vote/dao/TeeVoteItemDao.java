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
@Repository("voteItemDao")
public class TeeVoteItemDao extends TeeBaseDao<TeeVoteItem>{

	/**
	 * @author syl
	 * 增加
	 * @param TeeVoteItem
	 */
	public void add(TeeVoteItem obj) {
		save(obj);
	}
	
	/**
	 * @author syl
	 * 更新
	 * @param TeeVoteItem
	 */
	public void updateObj(TeeVoteItem obj) {
		update(obj);
	}
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeVoteItem loadById(int id) {
		TeeVoteItem intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeVoteItem getById(int id) {
		TeeVoteItem intf = get(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 删除
	 * @param 
	 */
	public void delById(int id) {
		delete(id);
	}
	
	
	/**
	 * 删除 所有会议室
	 * 
	 * @author syl
	 * @date 2014-1-6
	 * @param ids
	 */
	public void delAll(){
		String hql = "delete from TeeVoteItem ";
		deleteOrUpdateByQuery(hql, null);
	}
	/**
	 * 删除 by Ids
	 * 
	 * @author syl
	 * @date 2014-1-6
	 * @param ids
	 */
	public void delByIds(String ids){
		
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids= ids.substring(0, ids.length() -1 );
			}
			String hql = "delete from TeeVoteItem where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/**
	 * @author syl   
	 * 根据 投票  获取投票项目
	 * @param 
	 */
	public  List<TeeVoteItem> getVoteItemByVote(TeePerson person , TeeVoteItemModel model) {
		Object[] values = { model.getVoteId()};
		String hql = "from TeeVoteItem where vote.sid = ?  ";
		List<TeeVoteItem> list = (List<TeeVoteItem>) executeQuery(hql,values);
		return list;
	}	
	
	/**
	 * @author CXT
	 * 增加/更新
	 * @param TeeVoteItem
	 */
	public void saveObj(TeeVoteItem obj) {
		save(obj);
	}
	
	/**
	 * @author CXT   
	 * 根据符id查找vote
	 * @param 
	 */
	public  List<TeeVoteItem> getVoteItemListBySid(int id) {
		Object[] values = { };
		String hql = "select item from TeeVoteItem item where item.vote.sid ="+id+" order by item.voteNo asc";
		List<TeeVoteItem> list = (List<TeeVoteItem>) executeQuery(hql,values);
		return list;
	}
	
	/**
	 * @author CXT   
	 * 根据上级vote的父id查找vote
	 * @param 
	 */
	public  List<TeeVoteItem> voteItemListBySid(int id) {
		Object[] values = { };
		String hql = "select item from TeeVoteItem item where item.vote.parentVote.sid ="+id+" order by item.voteNo asc";
		List<TeeVoteItem> list = (List<TeeVoteItem>) executeQuery(hql,values);
		return list;
	}
	
	/**
	 * @author syl
	 * 更新
	 * @param TeeVoteItem
	 */
	public void updateItem(TeeVoteItem obj) {
		
		Session session = this.getSession();
		session.merge(obj);
	}
	
	/**
	 * @author CXT   
	 * 根据符id查找voteItem
	 * @param 
	 */
	public  List<TeeVoteItem> getItemListByVoteId(int id) {
		Object[] values = { };
		String hql = "select item from TeeVoteItem item where item.vote.sid ="+id+" order by item.voteNo asc";
		List<TeeVoteItem> list = (List<TeeVoteItem>) executeQuery(hql,values);
		return list;
	}	
	

}
