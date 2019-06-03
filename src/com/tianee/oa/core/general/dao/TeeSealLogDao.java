package com.tianee.oa.core.general.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.general.bean.TeeSealLog;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("sealLogDao")
public class TeeSealLogDao extends TeeBaseDao<TeeSealLog> {
	
	
	public void addSealLog(TeePerson person , int sealId , String sealName , int logType ,String ipAdd ,String macAdd , String result){
		TeeSealLog sealLog = new TeeSealLog();
		sealLog.setIpAdd(ipAdd);
		sealLog.setLogTime(Calendar.getInstance());
		sealLog.setLogType(logType);
		sealLog.setResult(result);
		sealLog.setUserId(person.getUuid());
		sealLog.setUserName(person.getUserName());
		sealLog.setSealId(sealId);
		sealLog.setSealName(sealName);
		Add(sealLog);
	}
	/**
	 * 新增
	 * @param seal
	 */
	public void Add(TeeSealLog sealLog){
		save(sealLog);
	}
	


	
	/**
	 * 用户管理 --- 按部门查询  管理范围 和 角色
	 * @param firstResult
	 * @param pageSize
	 * @param dm
	 * @param deptId
	 * @param person
	 * @return
	 */
	public  List getLogPageFind(int firstResult,int pageSize,TeeDataGridModel dm ,HttpServletRequest request) { 
		String sealName = TeeStringUtil.getString(request.getParameter("sealName"), "");
		String logType =  TeeStringUtil.getString(request.getParameter("logType"), "");
		String opUser =  TeeStringUtil.getString(request.getParameter("opUser"), "");
		String beginTime =  TeeStringUtil.getString(request.getParameter("beginTime"), "");
		String endTime =  TeeStringUtil.getString(request.getParameter("endTime"), "");
		
		String hql = "select log.sid , log.LOG_TYPE , log.SEAL_ID , se.SEAL_NAME , log.LOG_TIME , log.IP_ADD , log.MAC_ADD , log.USER_ID , log.USER_NAME,log.RESULT , log.SEAL_NAME as sn " +
				"  from SEAL_LOG log left join SEAL se on log.SEAL_ID = se.sid where 1=1 ";
		List list = new ArrayList();
		if(!TeeUtility.isNullorEmpty(sealName)){
			hql = hql + " and se.SEAL_NAME like ? ";
			list.add( "%" + sealName + "%");
		}
		
		if(!TeeUtility.isNullorEmpty(logType)){
			hql = hql + " and log.LOG_TYPE = ? ";
			list.add(logType);
		}
	
		if(!TeeUtility.isNullorEmpty(opUser)){
			hql = hql + " and log.USER_ID = ? ";
			list.add(opUser);
		}
		if(!TeeUtility.isNullorEmpty(beginTime)){
			hql = hql + " and log.LOG_TIME >= ? ";
			Timestamp ts = new Timestamp(System.currentTimeMillis());    
			ts = Timestamp.valueOf(beginTime); 
			list.add(ts);
		}	
		
		if(!TeeUtility.isNullorEmpty(endTime)){
			hql = hql + " and log.LOG_TIME <= ? ";
			Timestamp ts = new Timestamp(System.currentTimeMillis());    
			ts = Timestamp.valueOf(endTime); 
			list.add(ts);
		}	
		
		if(TeeUtility.isNullorEmpty(dm.getOrder()) ){
			dm.setOrder("desc");
		}
		
	    hql = hql + " order by log.LOG_TIME "  +  dm.getOrder();
	    
	    Session session = this.getSession();
	    Query query = session.createSQLQuery(hql);  
		   if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					query.setParameter(i, list.get(i));
				}
			}
		   query.setFirstResult(firstResult);
		   query.setMaxResults(pageSize);
		   List result = query.list();
		   return result;
		//return pageFindSQLByList(hql, firstResult, pageSize, list);
	 }
	/**
	 * 查询所有记录数
	 * @param 
	 */
	public long selectAllCount(HttpServletRequest request) {
		String sealName = TeeStringUtil.getString(request.getParameter("sealName"), "");
		String logType =  TeeStringUtil.getString(request.getParameter("logType"), "");
		String opUser =  TeeStringUtil.getString(request.getParameter("opUser"), "");
		String beginTime =  TeeStringUtil.getString(request.getParameter("beginTime"), "");
		String endTime =  TeeStringUtil.getString(request.getParameter("endTime"), "");
		
		
		String hql = "select count(*) from SEAL_LOG log left join SEAL se on log.SEAL_ID = se.SID where 1=1 ";
		List list = new ArrayList();
		if(!TeeUtility.isNullorEmpty(sealName)){
			hql = hql + " and se.SEAL_NAME like ? ";
			list.add( "%" + sealName + "%");
		}
		
		if(!TeeUtility.isNullorEmpty(logType)){
			hql = hql + " and log.LOG_TYPE = ? ";
			list.add(logType);
		}
	
		if(!TeeUtility.isNullorEmpty(opUser)){
			hql = hql + " and log.USER_ID = ? ";
			list.add(opUser);
		}	
		

		if(!TeeUtility.isNullorEmpty(beginTime)){
			hql = hql + " and log.LOG_TIME >= ? ";
			Timestamp ts = new Timestamp(System.currentTimeMillis());    
			ts = Timestamp.valueOf(beginTime); 
			list.add(ts);
		}	
		
		if(!TeeUtility.isNullorEmpty(endTime)){
			hql = hql + " and log.LOG_TIME <= ? ";
			Timestamp ts = new Timestamp(System.currentTimeMillis());    
			ts = Timestamp.valueOf(endTime); 
			list.add(ts);
		}	

		Long count =  countSQLByList(hql, list);
		return count;
	}

	

	
	/**
	 * byId  del
	 * @param 
	 */
	public void delBySids(String  sids) {
		if(TeeUtility.isNullorEmpty(sids)){
			return;
		}
		if(sids.endsWith(",")){
			sids = sids.substring(0, sids.length() - 1);
		}
		String hql = "delete from TeeSeal where sid in (" + sids + ")";
		deleteOrUpdateByQuery(hql, null);
	}
	

	
}


