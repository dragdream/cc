package com.tianee.oa.subsys.topic.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.topic.bean.TeeTopicSection;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("topicSectionDao")
public class TeeTopicSectionDao extends TeeBaseDao<TeeTopicSection> {

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
			String hql = "delete from TeeTopicSection where uuid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}

	/**
	 * 根据版块可见人员权限获取版块
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年1月7日
	 * @param model
	 * @param person
	 * @return List<TeeTopicSection>
	 */
	public List<TeeTopicSection> getTopicSectionListByViewPriv(TeePerson person) {
		Object values[] = { person.getUuid() };
		String hql = " from TeeTopicSection obj where viewPrivAllFlag=1 or exists (select 1 from obj.viewPriv viewPriv where viewPriv.uuid=?) order by orderNo asc,createTime desc";
		List<TeeTopicSection> list = executeQuery(hql, values);

		return list;
	}
	
	/**
	 * 根据版块uuid判断版块可见权限
	 * @function: 
	 * @author: wyw
	 * @data: 2015年6月29日
	 * @param person
	 * @param topicSectionId
	 * @return boolean
	 */
	public boolean isHaveViewPriv(TeePerson person,String topicSectionId) {
		Object values[] = { topicSectionId,person.getUuid() };
		String hql = " from TeeTopicSection obj where uuid=? viewPrivAllFlag=1 or exists (select 1 from obj.viewPriv viewPriv where viewPriv.uuid=?) order by orderNo asc,createTime desc";
		List<TeeTopicSection> list = executeQuery(hql, values);
		if(list != null && list.size()>0){
			return true;
		}
		return false;
	}
	
	

}
