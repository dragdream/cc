package com.tianee.oa.core.base.pm.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.pm.bean.TeeHumanDoc;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository("TeeHumanDocDao")
public class TeeHumanDocDao extends TeeBaseDao<TeeHumanDoc>{
	
	/**
	 * 获取人事档案信息
	 * @param LoginUser
	 * @return
	 */
	public TeeHumanDoc getHumanDocInfo(TeePerson LoginUser){
		TeeHumanDoc info = new TeeHumanDoc();
		List param = new ArrayList();
		String hql =" from TeeHumanDoc info where info.isOaUser='true' and info.oaUser=?";
		param.add(LoginUser);
		List<TeeHumanDoc> list = new ArrayList<TeeHumanDoc>();
		list = executeQueryByList(hql, param);
		if(list.size()>0){
			info = (TeeHumanDoc)list.get(0);
		}
		return info;
	}
//	//根据OA_USER字段   获取人事档案信息
//	public TeeHumanDoc getTeeHumanDocByUserId(int uuid){
//		TeePerson person=new TeePerson();
//		person.setUuid(uuid);
//		TeeHumanDoc info = new TeeHumanDoc();
//		List param = new ArrayList();
//		String hql =" from TeeHumanDoc info where info.isOaUser='true' and info.oaUser=?";
//		param.add(person);
//		List<TeeHumanDoc> list = new ArrayList<TeeHumanDoc>();
//		list = executeQueryByList(hql, param);
//		if(list.size()>0){
//			info = (TeeHumanDoc)list.get(0);
//		}
//		return info;	
//	}
	
	

}
