package com.tianee.oa.core.base.examine.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.examine.bean.TeeExamineData;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;
@Repository("TeeExamineDataDao")
public class TeeExamineDataDao extends TeeBaseDao<TeeExamineData>{
	/**
	 * @author syl
	 * 增加
	 * @param TeeExamineData
	 */
	public void add(TeeExamineData obj) {
		save(obj);
	}
	
	/**
	 * @author syl
	 * 更新
	 * @param TeeExamineData
	 */
	public void updateObj(TeeExamineData obj) {
		update(obj);
	}
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeExamineData loadById(int id) {
		TeeExamineData intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeExamineData getById(int id) {
		TeeExamineData intf = get(id);
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
	 * 根据任务  查询自己考评记录 
	 * @author syl
	 * @date 2014-5-28
	 * @param taskId
	 * @param rankmanId  考核人
	 * @param participant  被考核人
	 * @return
	 */
	public List<TeeExamineData>  getExamineData(int taskId , int rankmanId , int  participant) {
		List list = new ArrayList();
		list.add(taskId);
		String hql = "from TeeExamineData  where  task.sid = ?  ";
		
		if(rankmanId > 0){
			list.add(rankmanId);
			hql = hql +  " and   rankman.uuid= ? ";
		}
		if(participant > 0){
			list.add(participant);
			hql = hql +  " and  participant.uuid = ?";
		}
		hql = hql + " order by participant";
		List<TeeExamineData> listData =  executeQueryByList(hql, list);
		return listData;
	}	
}
