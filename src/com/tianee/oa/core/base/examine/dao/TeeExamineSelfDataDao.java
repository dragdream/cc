package com.tianee.oa.core.base.examine.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.examine.bean.TeeExamineSelfData;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;
@Repository("TeeExamineSelfDataDao")
public class TeeExamineSelfDataDao extends TeeBaseDao<TeeExamineSelfData>{
	/**
	 * @author syl
	 * 增加
	 * @param TeeExamineSelfData
	 */
	public void add(TeeExamineSelfData obj) {
		save(obj);
	}
	
	/**
	 * @author syl
	 * 更新
	 * @param TeeExamineSelfData
	 */
	public void updateObj(TeeExamineSelfData obj) {
		update(obj);
	}
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeExamineSelfData loadById(int id) {
		TeeExamineSelfData intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeExamineSelfData getById(int id) {
		TeeExamineSelfData intf = get(id);
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
		String hql = "delete from TeeExamineSelfData ";
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
			String hql = "delete from TeeExamineSelfData where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}

	/**
	 * 根据任务  自己自评记录 
	 * @author syl
	 * @date 2014-5-24
	 * @param person
	 * @return
	 */
	public List<TeeExamineSelfData>  getSelftData(int taskId , int userId) {
		Object[] values = { taskId ,userId};
		String hql = "from TeeExamineSelfData  where  task.sid = ? and  participant.uuid = ?";
		List<TeeExamineSelfData> list =  executeQuery(hql, values);
		return list;
	}	
}
