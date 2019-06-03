package com.tianee.oa.subsys.topic.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.topic.bean.TeeTopicReply;
import com.tianee.oa.subsys.topic.model.TeeTopicReplyModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("teeTopicReplyDao")
public class TeeTopicReplyDao extends TeeBaseDao<TeeTopicReply> {

	/**
	 * @function: 删除数据
	 * @author: wyw
	 * @data: 2014年8月30日
	 * @param ids
	 *            void
	 */
	public void delByIds(String ids) {
		if (!TeeUtility.isNullorEmpty(ids)) {
			if (ids.endsWith(",")) {
				ids = ids.substring(0, ids.length() - 1);
			}
			String hql = "delete from TeeTopicReply where uuid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	/**
	 * 根据topicId删除回复内容
	 * @function: 
	 * @author: wyw
	 * @data: 2015年1月27日
	 * @param ids void
	 */
	public void delByTopicIds(String ids) {
		if (!TeeUtility.isNullorEmpty(ids)) {
			if (ids.endsWith(",")) {
				ids = ids.substring(0, ids.length() - 1);
			}
			String hql = "delete from TeeTopicReply where topic.uuid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}

	/**
	 * 根据话题获取回复内容数据
	 * @function: 
	 * @author: wyw
	 * @data: 2015年1月7日
	 * @param model
	 * @param person
	 * @return List<TeeTopicReply>
	 */
	public List<TeeTopicReply> getTopicReplyListById(TeePerson person,TeeTopicReplyModel model){
		Object values[]={model.getTopicId()};
		String hql = " from TeeTopicReply obj where obj.topic.uuid=? order by obj.crTime asc";
		List<TeeTopicReply> list = executeQuery(hql, values);
		
		return list;
	}
	
	
	
}
