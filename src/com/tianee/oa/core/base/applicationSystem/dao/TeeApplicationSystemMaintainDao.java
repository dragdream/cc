package com.tianee.oa.core.base.applicationSystem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.applicationSystem.bean.TeeApplicationSystemMaintain;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository("applicationSystemMaintainDao")
public class TeeApplicationSystemMaintainDao extends  TeeBaseDao<TeeApplicationSystemMaintain>{

	public List<TeeApplicationSystemMaintain> selectAppSystemList(){
		String hql = "from TeeApplicationSystemMaintain where 1=1 ";
		List<TeeApplicationSystemMaintain> list = executeQuery(hql, null);
		return list;
	}
	
}
