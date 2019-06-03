package com.tianee.oa.core.general.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


import com.tianee.oa.core.general.bean.TeePortlet;
import com.tianee.oa.core.general.bean.TeePortletPersonal;
import com.tianee.oa.core.general.bean.TeeSms;
import com.tianee.oa.core.general.bean.TeeSmsBody;
import com.tianee.oa.core.general.model.TeeSmsModel;

import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Repository
public class TeePersonalPortletDao extends TeeBaseDao<TeePortletPersonal>{
	
	public List<TeePortletPersonal> getPortletList(int uuid){
		
		List<TeePortletPersonal> list = new ArrayList<TeePortletPersonal>();
		List<Object> values = new ArrayList<Object>();
		String hql = "select portlet from TeePortletPersonal portlet left join portlet.portletId p where p.viewType = 1 and portlet.userId ="+uuid+"  order by portlet.sortNo ";
		List<Object> objList = getList(hql, values);
	    for(Object obj : objList){
	    	TeePortletPersonal p = (TeePortletPersonal)obj;
			list.add(p);
	    }
		return list;
		
	}
	
	
	public List<TeePortletPersonal> getPortletByPortletId(int id){
		
		List<TeePortletPersonal> list = new ArrayList<TeePortletPersonal>();
		List<Object> values = new ArrayList<Object>();
		String hql = "select portlet from TeePortletPersonal portlet left join portlet.portletId p where p.viewType = 1 and p.sid ="+id+" order by portlet.sortNo ";
		list = executeQueryByList(hql, values);

		return list;
		
	}
	
	 /**
	 * page TeeDataGridModel.page 当前页
	 * rows TeeDataGridModel.rows 每页显示记录数
	 * @param hql
	 * @param page
	 * @param rows
	 * @param param
	 * @return
	 */
	public List<Object> getList(String hql, List<Object> param) {
		Query q = getSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		//List<Object> list = new ArrayList<Object>();
		return q.list();
	}
	
	public boolean checkRepeat(int portletId,int personId){
		boolean repeat = false;
		List<TeePortletPersonal> list = find("from TeePortletPersonal portlet where portlet.userId = "+personId+" and portlet.portletId.sid="+portletId, null);
		if(list.size()>0){
			repeat = true;
		}
		return repeat;
	}

}
