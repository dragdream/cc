package com.tianee.oa.core.base.email.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.email.bean.TeeMail;
import com.tianee.oa.core.base.email.bean.TeeMailBody;
import com.tianee.oa.core.base.email.bean.TeeMailBox;
import com.tianee.oa.core.base.email.bean.TeeMailColor;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;
@Repository
public class TeeMailColorDao extends TeeBaseDao<TeeMailColor>{

	public List<TeeMailColor> checkColor(TeePerson person,String name){
		List<TeeMailColor> list = new ArrayList<TeeMailColor>();
		String hql = "select mailColor from TeeMailColor mailColor where mailColor.user.uuid = '"+person.getUuid()+"' and mailColor.modularName = '"+name+"'";
		list = find(hql, null);
		return list;
	}
	
	public void updateMailColor(TeeMailColor c){
		Session session = this.getSession();
		session.merge(c);
	}
}
