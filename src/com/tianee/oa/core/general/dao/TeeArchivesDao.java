package com.tianee.oa.core.general.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.general.bean.TeeArchives;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository("archivesDao")
public class TeeArchivesDao extends TeeBaseDao<TeeArchives>{
	/**
	   * quarzJob
	   * @throws SQLException
	   */
	  public void exectSql(String sql) throws Exception{
	    Session session = getSession();
	    SQLQuery query = session.createSQLQuery(sql);
	    query.executeUpdate();
	    
	  }
	
	public Object get(Class clazz,Serializable id) {
		Session session = this.getSession();
		return session.get(clazz, id);
	}
	
	public List getSysLogTable(){
		List<TeeArchives> tableList = new ArrayList<TeeArchives>();
		try{
			String hql = "from TeeArchives where moduleNo is null";
			tableList=super.executeQuery(hql, null);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return tableList;
	}

}
