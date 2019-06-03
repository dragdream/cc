package com.tianee.oa.core.base.examine.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.examine.bean.TeeExamineItem;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;


@Repository("TeeExamineItemDao")
public class TeeExamineItemDao  extends TeeBaseDao<TeeExamineItem>{
	/**
	 * @author syl
	 * 增加
	 * @param TeeExamineItem
	 */
	public void add(TeeExamineItem attendOut) {
		save(attendOut);
	}
	
	/**
	 * @author syl
	 * 更新
	 * @param TeeExamineItem
	 */
	public void updateObj(TeeExamineItem attendOut) {
		update(attendOut);
	}
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeExamineItem loadById(int id) {
		TeeExamineItem intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeExamineItem getById(int id) {
		TeeExamineItem intf = get(id);
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
	 * 删除 所有记录
	 * 
	 * @author syl
	 * @date 2014-1-6
	 * @param ids
	 */
	public void delAll(){
		String hql = "delete from TeeExamineItem ";
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
			String hql = "delete from TeeExamineItem where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/**
	 * 根据明细集 查询 所有记录 
	 * @author syl
	 * @date 2014-5-24
	 * @param person
	 * @return
	 */
	public List<TeeExamineItem>  getAllByGroupId(int groupId) {
		Object[] values = { groupId};
		String hql = "from TeeExamineItem  where  group.sid = ?";
		List<TeeExamineItem> list =  executeQuery(hql, values);
		return list;
	}	
}
