package com.tianee.oa.core.base.notify.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.notify.bean.TeeNotify;
import com.tianee.oa.core.base.notify.bean.TeeNotifyInfo;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;
/**
 * 
 * @author syl
 */
@Repository
public class TeeNotifyInfoDao extends TeeBaseDao{

	/**
	 * 新增
	 * @author syl
	 * @date 2014-3-11
	 * @param notify
	 */
	public void addNotify(TeeNotifyInfo notify){
		save(notify);
	}
	
	
	/**
	 * 更新
	 * @author syl
	 * @date 2014-3-11
	 * @param notify
	 */
	public void updateNotify(TeeNotifyInfo notify){
		update(notify);
	}

	
	/**
	 * 检查是否存在阅读人员
	 * @author syl
	 * @date 2014-3-13
	 * @param person
	 * @param notify
	 * @return
	 */
	public boolean checkExistsInfo(TeePerson person , TeeNotify notify){
		Object[] values = {person.getUuid() , notify.getSid()};
		String hql  = "select count(sid) from TeeNotifyInfo  where toUser.uuid= ? and notify.sid = ?";
		long count = count(hql, values);
		if(count > 0){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 根据公告删除   阅读公告信息
	 * @author syl
	 * @date 2014-3-13
	 * @param notify
	 */
	public void deleteByNotify(int sid){
		Object[] values = {sid};
		String hql  = "delete from TeeNotifyInfo  where  notify.sid = ?";
		deleteOrUpdateByQuery(hql, values);
	}
}
