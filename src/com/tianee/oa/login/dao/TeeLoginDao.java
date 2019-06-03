package com.tianee.oa.login.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class TeeLoginDao extends TeeBaseDao<TeePerson>{

	public List<TeePerson> verifyCertUniqueId(String certUniqueId){
		Object [] obj = {certUniqueId};
		String hql = " from TeePerson where certUniqueId=?";
		return executeQuery(hql, obj);
	}
	
}
