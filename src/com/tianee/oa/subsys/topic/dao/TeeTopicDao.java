package com.tianee.oa.subsys.topic.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.topic.bean.TeeTopic;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("teeTopicDao")
public class TeeTopicDao extends TeeBaseDao<TeeTopic> {

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
			String hql = "delete from TeeTopic where uuid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}

	/**
	 * 根据版块可见人员权限获取版块
	 * @function: 
	 * @author: wyw
	 * @data: 2015年1月7日
	 * @param model
	 * @param person
	 * @return List<TeeTopic>
	 */
	public List<TeeTopic> getTopicListByViewPriv(TeePerson person){
		Object values[]={person.getUuid()};
		String hql = " from TeeTopic obj where viewPrivAllFlag=1 or exists (select 1 from obj.viewPriv viewPriv where viewPriv.uuid=?) order by orderNo asc,createTime desc";
		List<TeeTopic> list = executeQuery(hql, values);
		return list;
	}
	
	
	/**
	 * 根据版块分组
	 * @function: 
	 * @author: wyw
	 * @data: 2015年1月19日
	 * @return List<Map>
	 */
	public List<Map> getTopicListCounter(){
		String sql = "SELECT SECTION_ID as sectionId,count(*) as topicCount FROM topic group by SECTION_ID";
		return executeNativeQuery(sql, new Object[] {}, 0, Integer.MAX_VALUE);
	}
	
	/**
	 * 获取文章数
	 * @function: 
	 * @author: wyw
	 * @data: 2015年1月19日
	 * @return List<Map>
	 */
	public List<Map> getTopicReplyCount(){
		String sql = "select s.uuid as SECTIONID,((select count(*) from topic_reply r where r.section_id=s.uuid) +(select count(*) from topic  t where t.section_id=s.uuid  )) as TOPICREPLYCOUNT   from topic_section s";
		return executeNativeQuery(sql, new Object[] {}, 0, Integer.MAX_VALUE);
	}
	
	
	
	
	/**
	 * 根据版块id获取话题列表数据
	 * @function: 
	 * @author: wyw
	 * @data: 2015年1月7日
	 * @param model
	 * @param person
	 * @return List<TeeTopicReply>
	 */
	public List<TeeTopic> getTopicListById(String sectionUuid){
		Object values[]={sectionUuid};
		String hql = " from TeeTopic obj where obj.section.uuid=? order by lastReplyTime desc";
		List<TeeTopic> list = executeQuery(hql, values);
		
		return list;
	}
	
	
	
}
