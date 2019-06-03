package com.tianee.oa.core.general.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.general.bean.TeeArchives;
import com.tianee.oa.core.general.bean.TeeMysqlBackup;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("TeeMysqlBackupDao")
public class TeeMysqlBackupDao extends TeeBaseDao<TeeMysqlBackup>{
	
	/**
	   * quarzJob
	   * @throws SQLException
	   */
	  public void exectSql(String sql) throws Exception{
	    Session session = getSession();
	    SQLQuery query = session.createSQLQuery(sql);
	    query.executeUpdate();
	    
	  }

	  /**
	   * 获取备份list
	   * @param dm
	   * @param requestDatas
	   * @return
	   */
	public List<TeeMysqlBackup> getBackupList(TeeDataGridModel dm, Map requestDatas) {
		List list = new ArrayList();
		String hql = "from TeeMysqlBackup back where 1=1 ";
		hql+=" order by back.time desc";
		List<TeeMysqlBackup> curList =new ArrayList<TeeMysqlBackup>();
		curList = pageFindByList(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		return curList;
	}

	/**
	 * 获取备份总数
	 * @param requestDatas
	 * @return
	 */
	public long getTotal(Map requestDatas) {
		String hql = "from TeeMysqlBackup back where 1=1 ";
		long count = countByList("select count(*)"+hql, null);
		return count;
	}
	
	
}
