package com.tianee.oa.subsys.crm.core.base.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.crm.core.base.bean.TeeCrmCompetitor;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("teeCrmCompetitorDao")
public class TeeCrmCompetitorDao extends TeeBaseDao<TeeCrmCompetitor> {
	/**
	 * 获取所有竞争对手
	 * @author syl
	 * @date 2014-3-1
	 * @return
	 */
	public List<TeeCrmCompetitor> getCrmContract() {
	    String hql = " from TeeCrmCompetitor ";
		List<TeeCrmCompetitor> list = (List<TeeCrmCompetitor>) executeQuery(hql,null);
		return list;
	}
	
	/**
	 * 删除 byIds
	 * @return
	 */
	public int deleteById (String sid ){
		int count = 0;
		sid = TeeStringUtil.getString(sid);
		if(TeeUtility.isNullorEmpty(sid)){
			return count;
		}
		if(sid.endsWith(",")){
			sid = sid.substring(0, sid.length() - 1);
		}
		String hql = "delete from TeeCrmCompetitor where sid in(" + sid + ")";
		count = deleteOrUpdateByQuery(hql, null);
		return count;
	}
			
}
