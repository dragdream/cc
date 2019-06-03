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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.general.bean.TeeArchives;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.general.bean.TeeSysPara;
import com.tianee.oa.core.general.service.TeeSysParaService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("sysLogDao")
public class TeeSysLogDao extends TeeBaseDao<TeeSysLog>{
	@Autowired
	TeeArchivesDao archivesDao;
	
	@Autowired
	TeeSysParaService sysParaService;
	/**
	 * 增加日志
	 * @param TeeSysLog
	 */
	public void addSysLog(TeeSysLog sysLog) {
		save(sysLog);	
	}
	
	/**
	 * 更新
	 * @param TeeSysLog
	 */
	public void updateSysLog(TeeSysLog sysLog) {
		update(sysLog);	
	}
	
	/**
	 * 删除
	 * @param TeeSysLog
	 */
	public void delSysLog(TeeSysLog sysLog) {
		deleteByObj(sysLog);	
	}
	
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
	 * 作者  syl
	 * 获取当前登录人 最后N条记录
	 * @param personId
	 * @param count  去多条少记录
	 * @param type 日志类型
	 * @return
	 * @throws Exception
	 */
	  public List<TeeSysLog> getLogByLoginPerson(TeePerson person , int count , String type){
		  Session session = getSession();
		  String hql = "from TeeSysLog where (userId = '" + person.getUserId() + "' or personId=" + person.getUuid() +")" ;
		  if(!type.equals("")){
			  hql = hql + " and type ='" + type + "'";
		  }
		  
		  hql +="  order by time desc";
		  Query query = session.createQuery(hql);  
		  query.setFirstResult(0);
		  query.setMaxResults(count);
		  return query.list();
	  }

	public Map getSummaryLogInfo() {
		Map map = new HashMap();
		Session session = getSession();
		Calendar cl = Calendar.getInstance();
		/**
		 * 总日志条数
		 */
		String aHql = "from TeeArchives where 1=1";
		List<TeeArchives> archivesList = archivesDao.executeQuery(aHql, null);
		long totalSum=0;
		if(archivesList!=null && archivesList.size()>0){
			for(TeeArchives archives:archivesList){
				String allSql="select count(*) from "+archives.getTableName();
				try{
					totalSum+=super.countSQLByList(allSql, null);
				}catch(Exception ex){
					continue;
				}
			}
		}
		String hql = "from TeeSysLog where 1=1" ;
		long total = super.count("select count(*) "+hql, null);
		/**
		 * 今日日志条数
		 */
		int day = cl.get(Calendar.DAY_OF_MONTH);
		int month = cl.get(Calendar.MONTH)+1;
		int year = cl.get(Calendar.YEAR);
		String dhql = "from TeeSysLog log where 1=1 and day(log.time)="+day+" and month(log.time)="+month+" and year(log.time)="+year+" ";
		long dayTotal = super.count("select count(*) "+dhql, null);
		/**
		 * 本月日志条数
		 */
		String mhql = "from TeeSysLog log where 1=1 and month(log.time)="+month+" and year(log.time)="+year+" ";
		long monthTotal = super.count("select count(*) "+mhql, null);
		/**
		 * 今年日志条数
		 */
		long yearSum=0;
		SimpleDateFormat sf=new SimpleDateFormat("yyyy");
		Connection dbConn = null;
		try{
			dbConn = TeeDbUtility.getConnection();
			DbUtils dbUtils = new DbUtils(dbConn);
			for(int i=1;i<month;i++){
				String mon="";
				if(i<=9){
					mon="0"+i;
				}else{
					mon=i+"";
				}
				try{
					String ysql="select count(*) as C from sys_log_"+sf.format(cl.getTime())+mon;
					yearSum+=TeeStringUtil.getLong(dbUtils.queryToMap(ysql).get("C"), 0);
				}catch(Exception ex){
					continue;
				}
			}
		}catch(Exception e){
			
		}finally{
			TeeDbUtility.closeConn(dbConn);
		}
		String yhql = "from TeeSysLog log where 1=1 and year(log.time)="+year+" ";
		long yearTotal = super.count("select count(*) "+yhql, null);
		map.put("total", total+totalSum);
		map.put("dayTotal", dayTotal);
		map.put("monthTotal",monthTotal);
		map.put("yearTotal", yearTotal+yearSum);
		return map;
	}

	
	/**
	 * 获取最近十条日志信息
	 * @author ny
	 * @return
	 */
	public List<TeeSysLog> getLast10LogInfo() {
		  Session session = getSession();
		  String hql = "from TeeSysLog where 1=1 order by time desc";
		  Query query = session.createQuery(hql);  
		  query.setFirstResult(0);
		  query.setMaxResults(10);
		  return query.list();
	}

	
	public List getLogInfoByYear(String year) {
		List list = new ArrayList();
		NumberFormat nt = NumberFormat.getPercentInstance();
		   //设置百分数精确度2即保留两位小数
		nt.setMinimumFractionDigits(2);
		/**
		 * 
		 */
		long yearSum=0;
		for(int i=1;i<=12;i++){
			String mon="";
			if(i<9){
				mon="0"+i;
			}else{
				mon=i+"";
			}
			try{
				String ysql="select count(*) from sys_log_"+year+mon;
				yearSum+=super.countSQLByList(ysql, null);
			}catch(Exception ex){
				continue;
			}
		}
		String yhql = "from TeeSysLog log where 1=1 and year(log.time)="+year+" ";
		long yearTotal = super.count("select count(*) "+yhql, null)+yearSum;
		Calendar cl = Calendar.getInstance();
		for(int i=1;i<=12;i++){
			Map map = new HashMap();
			String mon="";
			if(i<=9){
				mon="0"+i;
			}else{
				mon=i+"";
			}
			try{
				long monthTotal=0;
				if(cl.get(Calendar.MONTH)+1==i && cl.get(Calendar.YEAR)==Integer.parseInt(year)){
					String mhql = "from TeeSysLog log where month(log.time)="+i+" and year(log.time)="+Integer.parseInt(year)+" ";
					monthTotal = super.count("select count(*) "+mhql, null);//月总条数
				}else{
					try{
						String msql="select count(*) from sys_log_"+year+mon;
						monthTotal = super.countSQLByList(msql, null);	
					}catch(Exception ex){
						monthTotal=0;
					}
				}
				float per =0;
				if(yearTotal>0){
					per =(float) monthTotal/yearTotal;
				}
				map.put("monthTotal", monthTotal);
				map.put("monthPer", nt.format(per));
				list.add(map);
			}catch(Exception ex){
				map.put("monthTotal", 0);
				map.put("monthPer", nt.format(0));
				list.add(map);
				continue;
			}
		}
		return list;
	}

	public Map getGraphDataByYearAndMonth(String year,String month){
		Map map = new HashMap();
	/*	String data="<graph caption='"+year+"年系统日志月度统计（单位：条）' showNames='1'>";
		for(int i=1;i<=12;i++){
			String mhql = "from TeeSysLog log where month(log.time)="+i+" and year(log.time)="+Integer.parseInt(year)+" order by log.time desc";
			long monthTotal = super.count("select count(*) "+mhql, null);//月总条数
			data+="<set name='"+i+"月' value='"+monthTotal+"'/>";
		}
		data+="</graph>";*/
		Calendar cl = Calendar.getInstance();
		StringBuffer sb = new StringBuffer("{");
		sb.append("\"chart\": {\"yaxisname\": \"total numbers\", \"caption\": \""+year+"年系统日志月度统计（单位：条）\",\"numberprefix\": \"\",\"useroundedges\": \"1\",\"bgcolor\": \"FFFFFF,FFFFFF\",\"showborder\": \"0\"},");
		sb.append("\"data\":[");
		for(int i=1;i<=12;i++){
			String mon="";
			if(i<=9){
				mon="0"+i;
			}else{
				mon=i+"";
			}
			long monthTotal=0;
			try{
				if(cl.get(Calendar.MONTH)+1==i && cl.get(Calendar.YEAR)==Integer.parseInt(year)){
					String mhql = "from TeeSysLog log where month(log.time)="+i+" and year(log.time)="+Integer.parseInt(year)+" ";
					monthTotal = super.count("select count(*) "+mhql, null);//月总条数
				}else{
					String msql="select count(*) from sys_log_"+year+mon;
				    monthTotal = super.countSQLByList(msql, null);	
				}
				sb.append("{\"label\":\""+i+"月\",\"value\":\""+monthTotal+"\"},");
			}catch(Exception ex){
				monthTotal=0;
				sb.append("{\"label\":\""+i+"月\",\"value\":\""+monthTotal+"\"},");
				continue;
			}
		}
		if(sb.length()>2){
			sb=sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]}");
		map.put("graphData", sb.toString());
		return map;
	}
	
	
	public Map getEverydayByYearAndMonth(String year,String month){
		Map map = new HashMap();
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.YEAR,Integer.parseInt(year)); 
		cl.set(Calendar.MONTH, Integer.parseInt(month)-1);
		int days = cl.getActualMaximum(Calendar.DATE);
	/*	String data="<graph caption='"+month+"月系统日志统计（单位：条）' showNames='1'>";
		for(int i=1;i<=days;i++){
			String mhql = "from TeeSysLog log where day(log.time)="+i+" and month(log.time)="+Integer.parseInt(month)+" and year(log.time)="+Integer.parseInt(year)+" order by log.time desc";
			long dayTotal = super.count("select count(*) "+mhql, null);//月总条数
			data+="<set name='"+i+"天' value='"+dayTotal+"'/>";
		}
		data+="</graph>";*/
		Calendar cur=Calendar.getInstance();
		StringBuffer sb =new  StringBuffer("{");
		sb.append("\"chart\": {\"yaxisname\": \"total numbers\", \"caption\": \""+month+"月系统日志统计（单位：条）\",\"numberprefix\": \"\",\"useroundedges\": \"1\",\"bgcolor\": \"FFFFFF,FFFFFF\",\"showborder\": \"0\"},");
		sb.append("\"data\":[");
		for(int i=1;i<=days;i++){
			if(cur.get(Calendar.MONTH)+1==Integer.parseInt(month) && cur.get(Calendar.YEAR)==Integer.parseInt(year)){
				String mhql = "from TeeSysLog log where day(log.time)="+i+" and month(log.time)="+Integer.parseInt(month)+" and year(log.time)="+Integer.parseInt(year)+" ";
				long dayTotal = super.count("select count(*) "+mhql, null);//月总条数
				sb.append("{\"label\":\""+i+"天\",\"value\":\""+dayTotal+"\"},");
			}else{
				String curMonth=month;
				if(Integer.parseInt(month)<=9){
					curMonth="0"+month;
				}
				try{
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Calendar cl2 = Calendar.getInstance();
					cl2.set(Calendar.YEAR,Integer.parseInt(year)); 
					cl2.set(Calendar.MONTH, Integer.parseInt(month)-1);
					cl2.set(Calendar.DAY_OF_MONTH, i);
					String min=sf.format(cl2.getTime())+" 00:00:00";
					String max = sf.format(cl2.getTime())+" 23:59:59";
					Date minDate = sdf.parse(min);
					Date maxDate = sdf.parse(max);
					try{
						String msql="select count(*) from sys_log_"+year+curMonth+" log where log.time>="+minDate+" and log.time<="+maxDate+" order by log.time";
						long dayTotal = super.countSQLByList(msql, null);
						sb.append("{\"label\":\""+i+"天\",\"value\":\""+dayTotal+"\"},");
					}catch(Exception ex){
						sb.append("{\"label\":\""+i+"天\",\"value\":\""+0+"\"},");
					}
				}catch(Exception ex){
					sb.append("{\"label\":\""+i+"天\",\"value\":\""+0+"\"},");
				}
			}
		}
		if(sb.length()>2){
			sb=sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]}");
		map.put("graphData", sb.toString());
		return map;
	}
	
	public List getLogInfoByHour() {
		List list = new ArrayList();
		NumberFormat nt = NumberFormat.getPercentInstance();
		   //设置百分数精确度2即保留两位小数
		nt.setMinimumFractionDigits(2);
		String hql = "from TeeSysLog where 1=1" ;
		long total = super.count("select count(*) "+hql, null);
		for(int i=0;i<=23;i++){
			Map map = new HashMap();
			String hhql = "from TeeSysLog log where hour(log.time) ="+i+" ";
			long timesTotal = super.count("select count(*) "+hhql, null);//月总条数
			float per =0;
			if(total>0){
				 per =(float) timesTotal/total;
			}
			map.put("timesTotal", timesTotal);
			map.put("timesPer", nt.format(per));
			list.add(map);
		}
		return list;
	}
	
	
	public List getLogInfoByYearAndMonth(String year,String month) {
		List list = new ArrayList();
		Calendar cur=Calendar.getInstance();
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.YEAR,Integer.parseInt(year)); 
		cl.set(Calendar.MONTH, Integer.parseInt(month)-1);
		int days = cl.getActualMaximum(Calendar.DATE);
		NumberFormat nt = NumberFormat.getPercentInstance();
		//设置百分数精确度2即保留两位小数
		nt.setMinimumFractionDigits(2);
		long monthTotal=0;
		if(cur.get(Calendar.MONTH)+1==Integer.parseInt(month) && cur.get(Calendar.YEAR)==Integer.parseInt(year)){
			String mhql = "from TeeSysLog log where 1=1 and month(log.time)="+month+" and year(log.time)="+year+" ";
		    monthTotal = super.count("select count(*) "+mhql, null);
		}else{
			if(Integer.parseInt(month)<=9){
				month="0"+month;
			}
			try{
				String msql="select count(*) from sys_log_"+year+month;
				monthTotal = super.countSQLByList(msql, null);
			}catch(Exception ex){
				monthTotal=0;
			}
		}
		for(int i=1;i<=days;i++){
			Map map = new HashMap();
			long daysTotal=0;
			if(cur.get(Calendar.MONTH)+1==Integer.parseInt(month) && cur.get(Calendar.YEAR)==Integer.parseInt(year)){
				String hhql = "from TeeSysLog log where day(log.time)="+i+" and month(log.time)="+Integer.parseInt(month)+" and year(log.time)="+Integer.parseInt(year)+" ";
			    daysTotal = super.count("select count(*) "+hhql, null);//总条数
			}else{
				String curMonth=month;
				if(Integer.parseInt(month)<=9){
					curMonth="0"+month;
				}
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar cl2 = Calendar.getInstance();
				cl2.set(Calendar.YEAR,Integer.parseInt(year)); 
				cl2.set(Calendar.MONTH, Integer.parseInt(month)-1);
				cl2.set(Calendar.DAY_OF_MONTH, i);
				String min=sf.format(cl2.getTime())+" 00:00:00";
				String max = sf.format(cl2.getTime())+" 23:59:59";
				try{
					Date minDate = sdf.parse(min);
					Date maxDate = sdf.parse(max);
					String msql="select count(*) from sys_log_"+year+curMonth+" log where log.time>="+minDate+" and log.time<="+maxDate+" order by log.time";
					daysTotal = super.countSQLByList(msql, null);
				}catch(Exception ex){
					daysTotal=0;
				}
			}
			float per =0;
			if(monthTotal>0){
				per =(float) daysTotal/monthTotal;
			}
			map.put("daysTotal", daysTotal);
			map.put("daysPer", nt.format(per));
			list.add(map);
		}
		return list;
	}
	
	public Map getGraphDataByTimes(){
		Map map = new HashMap();
		String data="<graph caption='系统日志当月按时段统计（单位：条）' showNames='1'>";
		for(int i=0;i<=23;i++){
			String hhql = "from TeeSysLog log where hour(log.time) ="+i+" ";
			long timesTotal = super.count("select count(*) "+hhql, null);//月总条数
			data+="<set name='"+i+"时' value='"+timesTotal+"'/>";
		}
		data+="</graph>";
		map.put("graphData", data);
		return map;
	}

	
	/**
	 * 根据条件查询出满足条件的系统日志信息
	 * @param requestDatas
	 * @return
	 * @throws ParseException
	 */
	public List<TeeSysLog> getLogInfoByCondition(TeeDataGridModel dm,Map requestDatas) throws Exception {
		List list = new ArrayList();
		String hql = "from TeeSysLog log where 1=1 ";
		//如果不是管理员的话，则进行权限校验
		String logType=(String)requestDatas.get("logType");
		String userIds=(String)requestDatas.get("userIds");
		String startTimeDesc=(String)requestDatas.get("startTimeDesc");
		String endTimeDesc=(String)requestDatas.get("endTimeDesc");
		String ip=(String)requestDatas.get("ip");
		String remark=(String)requestDatas.get("remark");
		String sysLogTable = TeeStringUtil.getString(requestDatas.get("sysLogTable"),"0");
		String queryStr="";
		if(userIds.endsWith(",")){
			userIds=userIds.substring(0,userIds.length()-1);
		}
		if(!TeeUtility.isNullorEmpty(logType)){
			hql+=" and log.type like ?";
			queryStr+=" and log.type like ?";
			list.add(logType+"%");
		}
		if(!TeeUtility.isNullorEmpty(userIds)){
			hql+=" and log.userId in (?)";
			queryStr+=" and log.user_id in (?)";
			list.add(userIds);
		}
		if(!TeeUtility.isNullorEmpty(startTimeDesc)){
		    Date date = TeeUtility.parseDate("yyyy-MM-dd HH:mm:ss", startTimeDesc);
		    Calendar scl=Calendar.getInstance();
		    scl.setTime(date);
		    hql+=" and log.time>=?";
		    queryStr+=" and log.time>=?";
			list.add(scl);
		}
		if(!TeeUtility.isNullorEmpty(endTimeDesc)){
			Date date = TeeUtility.parseDate("yyyy-MM-dd HH:mm:ss", endTimeDesc);
			Calendar ecl = Calendar.getInstance();
			ecl.setTime(date);
			hql+=" and log.time<=?";
			queryStr+=" and log.time<=?";
			list.add(ecl);
		}
		if(!TeeUtility.isNullorEmpty(ip)){
			hql+=" and log.ip=?";
			queryStr+=" and log.ip=?";
			list.add(ip);
		}
		if(!TeeUtility.isNullorEmpty(remark)){
			hql+=" and log.remark like ?";
			queryStr+=" and log.remark like ?";
			list.add("%"+remark+"%");
		}
		hql+=" order by log.time desc";
		queryStr+=" order by log.time desc";
		List<TeeSysLog> curList =new ArrayList<TeeSysLog>();
		List<Map> sList=new ArrayList<Map>();
		if(sysLogTable.equals("0")){
			 curList = pageFindByList(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), list);
		}else{
			String sql = "select * from "+sysLogTable+" log  where 1=1 "+queryStr;
			 Object[] param=new Object[list.size()];
			for(int i=0;i<list.size();i++){
				param[i]=list.get(i);
			}
			sList = executeNativeQuery(sql, param, dm.getRows()*(dm.getPage()-1), dm.getRows());
			if(sList.size()>0){
				//{TIME=2014-06-19 10:32:19.0, USER_ID=4, USER_NAME=系统管理员, PERSON_ID=0, IP=127.0.0.1, UUID=4028b88146b1f7240146b1f7b7300000, REMARK=登录系统, ERROR_LOG=null, ERROR_FLAG=0, TYPE=003E}
				for(Map map:sList){
					TeeSysLog log = new TeeSysLog();
					Timestamp time=(Timestamp)map.get("TIME");
					Calendar tcl=Calendar.getInstance();
					tcl.setTime(TeeUtility.parseDate("yyyy-MM-dd HH:mm:ss", time.toString()));
					log.setTime(tcl);
					log.setUserId((String)map.get("USER_ID"));
					log.setUserName((String)map.get("USER_NAME"));
					log.setPersonId((Integer)map.get("PERSON_ID"));
					log.setIp((String)map.get("IP"));
					log.setUuid((String)map.get("UUID"));
					log.setRemark((String)map.get("REMARK"));
					log.setErrorLog((String)map.get("ERROR_LOG"));
					log.setType((String)map.get("TYPE"));
					//log.setErrorFlag((Integer)map.get("ERROR_FLAG"));
					curList.add(log);
				}
			}
		}
		return curList;
	}
	
	
	/**
	 * 根据条件查询出满足条件的系统日志信息条数
	 * @param requestDatas
	 * @return
	 * @throws ParseException
	 */
    public  long getTotalByConditon(Map requestDatas) throws Exception {
		List list = new ArrayList();
		String hql = "from TeeSysLog log where 1=1 ";
		//如果不是管理员的话，则进行权限校验
		String logType=(String)requestDatas.get("logType");
		String userIds=(String)requestDatas.get("userIds");
		String startTimeDesc=(String)requestDatas.get("startTimeDesc");
		String endTimeDesc=(String)requestDatas.get("endTimeDesc");
		String ip=(String)requestDatas.get("ip");
		String remark=(String)requestDatas.get("remark");
		String sysLogTable = TeeStringUtil.getString(requestDatas.get("sysLogTable"),"0");
		String queryStr="";
		if(userIds.endsWith(",")){
			userIds=userIds.substring(0,userIds.length()-1);
		}
		if(!TeeUtility.isNullorEmpty(logType)){
			hql+=" and log.type like ?";
			queryStr+=" and log.type like ?";
			list.add(logType+"%");
		}
		if(!TeeUtility.isNullorEmpty(userIds)){
			hql+=" and log.userId in (?)";
			queryStr+=" and log.user_id in (?)";
			list.add(userIds);
		}
		if(!TeeUtility.isNullorEmpty(startTimeDesc)){
		    Date date = TeeUtility.parseDate("yyyy-MM-dd HH:mm:ss", startTimeDesc);
		    Calendar scl=Calendar.getInstance();
		    scl.setTime(date);
		    hql+=" and log.time>=?";
		    queryStr+=" and log.time>=?";
			list.add(scl);
		}
		if(!TeeUtility.isNullorEmpty(endTimeDesc)){
			Date date = TeeUtility.parseDate("yyyy-MM-dd HH:mm:ss", endTimeDesc);
			Calendar ecl = Calendar.getInstance();
			ecl.setTime(date);
			hql+=" and log.time<=?";
			queryStr+=" and log.time<=?";
			list.add(ecl);
		}
		if(!TeeUtility.isNullorEmpty(ip)){
			hql+=" and log.ip=?";
			queryStr+=" and log.ip=?";
			list.add(ip);
		}
		if(!TeeUtility.isNullorEmpty(remark)){
			hql+=" and log.remark like ?";
			queryStr+=" and log.remark like ?";
			list.add("%"+remark+"%");
		}
		long count=0;
		if(sysLogTable.equals("0")){
			  count = countByList("select count(*)"+hql, list);
		}else{
			String sql = "select count(*) from "+sysLogTable+" log  where 1=1 "+queryStr;
			 Object[] param=new Object[list.size()];
			for(int i=0;i<list.size();i++){
				param[i]=list.get(i);
			}
			count = countSQLByList(sql, list);
		}
        return count;
    } 
	
	public Object get(Class clazz,Serializable id) {
		Session session = this.getSession();
		return session.get(clazz, id);
	}
	
	
	/**
	 * 查询出所有满足条件的数据
	 * @param requestDatas
	 * @return
	 * @throws Exception
	 */
	public List<TeeSysLog> getLogInfoByCondition(Map requestDatas) throws Exception {
		List list = new ArrayList();
		String hql = "from TeeSysLog log where 1=1 ";
		//如果不是管理员的话，则进行权限校验
		String logType=(String)requestDatas.get("logType");
		String userIds=(String)requestDatas.get("userIds");
		String startTimeDesc=(String)requestDatas.get("startTimeDesc");
		String endTimeDesc=(String)requestDatas.get("endTimeDesc");
		String ip=(String)requestDatas.get("ip");
		String remark=(String)requestDatas.get("remark");
		String sysLogTable = TeeStringUtil.getString(requestDatas.get("sysLogTable"),"0");
		String queryStr="";
		String queryStrs="";
		if(userIds.endsWith(",")){
			userIds=userIds.substring(0,userIds.length()-1);
		}
		if(!TeeUtility.isNullorEmpty(logType)){
			hql+=" and log.type like ?";
			queryStr+=" and log.type like ?";
			list.add(logType+"%");
		}
		if(!TeeUtility.isNullorEmpty(userIds)){
			hql+=" and log.userId in (?)";
			queryStr+=" and log.user_id in (?)";
			list.add(userIds);
		}
		if(!TeeUtility.isNullorEmpty(startTimeDesc)){
		    Date date = TeeUtility.parseDate("yyyy-MM-dd HH:mm:ss", startTimeDesc);
		    Calendar scl=Calendar.getInstance();
		    scl.setTime(date);
		    hql+=" and log.time>=?";
		    queryStr+=" and log.time>=?";
			list.add(scl);
		}
		if(!TeeUtility.isNullorEmpty(endTimeDesc)){
			Date date = TeeUtility.parseDate("yyyy-MM-dd HH:mm:ss", endTimeDesc);
			Calendar ecl = Calendar.getInstance();
			ecl.setTime(date);
			hql+=" and log.time<=?";
			queryStr+=" and log.time<=?";
			list.add(ecl);
		}
		if(!TeeUtility.isNullorEmpty(ip)){
			hql+=" and log.ip=?";
			queryStr+=" and log.ip=?";
			list.add(ip);
		}
		if(!TeeUtility.isNullorEmpty(remark)){
			hql+=" and log.remark like ?";
			queryStr+=" and log.remark like ?";
			list.add("%"+remark+"%");
		}
		hql+=" order by log.time desc";
		queryStrs = queryStr;
		queryStr+=" order by log.time desc";
		List<TeeSysLog> logList = new ArrayList<TeeSysLog>();
		List<Map> sList=new ArrayList<Map>();
		if(sysLogTable.equals("0")){
			 logList =executeQueryByList(hql,list); 
		}else{
			String sql = "select * from "+sysLogTable+" log  where 1=1 "+queryStr;
			String cql = "select count(*) from "+sysLogTable+" log  where 1=1 "+queryStrs;
			
			 Object[] param=new Object[list.size()];
			for(int i=0;i<list.size();i++){
				param[i]=list.get(i);
			}
			int count =countSQLByList(cql, list).intValue();
			sList =super.executeNativeQuery(sql, param, 0, count);
			if(sList.size()>0){
				//{TIME=2014-06-19 10:32:19.0, USER_ID=4, USER_NAME=系统管理员, PERSON_ID=0, IP=127.0.0.1, UUID=4028b88146b1f7240146b1f7b7300000, REMARK=登录系统, ERROR_LOG=null, ERROR_FLAG=0, TYPE=003E}
				for(Map map:sList){
					TeeSysLog log = new TeeSysLog();
					Timestamp time=(Timestamp)map.get("TIME");
					Calendar tcl=Calendar.getInstance();
					tcl.setTime(TeeUtility.parseDate("yyyy-MM-dd HH:mm:ss", time.toString()));
					log.setTime(tcl);
					log.setUserId((String)map.get("USER_ID"));
					log.setUserName((String)map.get("USER_NAME"));
					log.setPersonId((Integer)map.get("PERSON_ID"));
					log.setIp((String)map.get("IP"));
					log.setUuid((String)map.get("UUID"));
					log.setRemark((String)map.get("REMARK"));
					log.setErrorLog((String)map.get("ERROR_LOG"));
					log.setType((String)map.get("TYPE"));
					//log.setErrorFlag((Integer)map.get("ERROR_FLAG"));
					logList.add(log);
				}
			}
		}
		return logList;
	}

	public TeeSysLog getSysLogByUuid(String uuid) {
		String hql = "from TeeSysLog log where log.uuid='"+uuid+"'";
		List<TeeSysLog> list = find(hql,null);
		TeeSysLog log = null;
		if(list.size()>0){
			log = list.get(0);
		}
		return log;
	}

	
	
	/**
	 * 三员日志管理
	 * @param dm
	 * @param requestDatas
	 * @return
	 * @throws ParseException 
	 */
	public List<TeeSysLog> getThreePartSysLogs(TeeDataGridModel dm,
			Map requestDatas) throws ParseException {
		Set s=new HashSet();
		
		TeeSysPara adminPriv=sysParaService.getSysPara("ADMIN_PRIV");
		TeeSysPara saferPriv=sysParaService.getSysPara("SAFER_PRIV");
		TeeSysPara auditorPriv=sysParaService.getSysPara("AUDITOR_PRIV");
		
		String adminPrivStr="";
		String saferPrivStr="";
		String auditorPrivStr="";
		if(adminPriv!=null){
			adminPrivStr=adminPriv.getParaValue();
			if(!TeeUtility.isNullorEmpty(adminPrivStr)){
			    for (String str : adminPrivStr.split(",")) {
					s.add(str);
				}
			}
		}
		if(saferPriv!=null){
			saferPrivStr=saferPriv.getParaValue();
			if(!TeeUtility.isNullorEmpty(saferPrivStr)){
			    for (String str : saferPrivStr.split(",")) {
					s.add(str);
				}
			}
		}
		if(auditorPriv!=null){
			auditorPrivStr=auditorPriv.getParaValue();
			if(!TeeUtility.isNullorEmpty(auditorPrivStr)){
			    for (String str : auditorPrivStr.split(",")) {
					s.add(str);
				}
			}
		}
		List list = new ArrayList();	
		//1=登录访问日志   2=访问实时日志   3=异常日志  4=管理员访问日志  5=管理员登录日志  6=管理员操作日志
		int  type=TeeStringUtil.getInteger(requestDatas.get("type"),1);
		String sysLogTable = TeeStringUtil.getString(requestDatas.get("sysLogTable"),"0");
		String queryStr="";
		
		if(type==1){//1=登录访问日志
			queryStr+=" and (log.type=? or log.type=? ) ";
			list.add("003E");
			list.add("003F");
		}else if(type==2){// 2=访问实时日志 
			queryStr+=" and log.type=? ";
			list.add("VISIT");
		}else if(type==3){// 3=异常日志 
			queryStr+=" and log.error_flag = ?  ";
			list.add(1);
		}else if(type==4){//  4=管理员访问日志
			queryStr+=" and log.type=? ";
			list.add("VISIT");
			if(s.size()>0){
				queryStr+=" and log.person_id in ("+ StringUtils.join(s.toArray(), ",")+") ";
			}else{
				//queryStr+=" and log.person_id in (0) ";
				return new ArrayList<TeeSysLog>();
			}
		}else if(type==5){// 5=管理员登录日志
			queryStr+=" and (log.type=? or log.type=? ) ";
			list.add("003E");
			list.add("003F");
			if(s.size()>0){
				queryStr+=" and log.person_id in ("+ StringUtils.join(s.toArray(), ",")+") ";
			}else{
				//queryStr+=" and log.person_id in (0) ";
				return new ArrayList<TeeSysLog>();
			}
			
		}else if(type==6){// 6=管理员操作日志
			queryStr+=" and log.type!=? and log.type!=? and log.type!=? and log.error_flag != ? ";
			list.add("003E");
			list.add("003F");
			list.add("VISIT");
			list.add(1);
			if(s.size()>0){
				queryStr+=" and log.person_id in ("+ StringUtils.join(s.toArray(), ",")+") ";
			}else{
				//queryStr+=" and log.person_id in (0) ";
				return new ArrayList<TeeSysLog>();
			}
			
		}
		
		queryStr+=" order by log.time desc";
		List<TeeSysLog> curList =new ArrayList<TeeSysLog>();
		List<Map> sList=new ArrayList<Map>();
		String sql="";
		if(sysLogTable.equals("0")){//当前月份
			sql = "select * from sys_log log  where 1=1 ";
		}else{
			 sql = "select * from "+sysLogTable+" log  where 1=1 ";
		}
		
		
	    Object[] param=new Object[list.size()];
		for(int i=0;i<list.size();i++){
			param[i]=list.get(i);
		}
		
		
		sList = executeNativeQuery(sql+queryStr, param, dm.getRows()*(dm.getPage()-1), dm.getRows());
		if(sList.size()>0){
			//{TIME=2014-06-19 10:32:19.0, USER_ID=4, USER_NAME=系统管理员, PERSON_ID=0, IP=127.0.0.1, UUID=4028b88146b1f7240146b1f7b7300000, REMARK=登录系统, ERROR_LOG=null, ERROR_FLAG=0, TYPE=003E}
			for(Map map:sList){
				TeeSysLog log = new TeeSysLog();
				Timestamp time=(Timestamp)map.get("TIME");
				Calendar tcl=Calendar.getInstance();
				tcl.setTime(TeeUtility.parseDate("yyyy-MM-dd HH:mm:ss", time.toString()));
				log.setTime(tcl);
				log.setUserId((String)map.get("USER_ID"));
				log.setUserName((String)map.get("USER_NAME"));
				log.setPersonId((Integer)map.get("PERSON_ID"));
				log.setIp((String)map.get("IP"));
				log.setUuid((String)map.get("UUID"));
				log.setRemark((String)map.get("REMARK"));
				log.setErrorLog((String)map.get("ERROR_LOG"));
				log.setType((String)map.get("TYPE"));
				log.setErrorFlag((Integer)map.get("ERROR_FLAG"));
				curList.add(log);
			}
		}
		return curList;
	}

	
	//三员管理  获取总数
	public long getThreePartSysLogsTotal(Map requestDatas) {
		
        Set s=new HashSet();
		
		TeeSysPara adminPriv=sysParaService.getSysPara("ADMIN_PRIV");
		TeeSysPara saferPriv=sysParaService.getSysPara("SAFER_PRIV");
		TeeSysPara auditorPriv=sysParaService.getSysPara("AUDITOR_PRIV");
		
		String adminPrivStr="";
		String saferPrivStr="";
		String auditorPrivStr="";
		if(adminPriv!=null){
			adminPrivStr=adminPriv.getParaValue();
			if(!TeeUtility.isNullorEmpty(adminPrivStr)){
			    for (String str : adminPrivStr.split(",")) {
					s.add(str);
				}
			}
		}
		if(saferPriv!=null){
			saferPrivStr=saferPriv.getParaValue();
			if(!TeeUtility.isNullorEmpty(saferPrivStr)){
			    for (String str : saferPrivStr.split(",")) {
					s.add(str);
				}
			}
		}
		if(auditorPriv!=null){
			auditorPrivStr=auditorPriv.getParaValue();
			if(!TeeUtility.isNullorEmpty(auditorPrivStr)){
			    for (String str : auditorPrivStr.split(",")) {
					s.add(str);
				}
			}
		}
		
		List list = new ArrayList();
		//1=登录访问日志   2=访问实时日志   3=异常日志  4=管理员访问日志  5=管理员登录日志  6=管理员操作日志
		int  type=TeeStringUtil.getInteger(requestDatas.get("type"),1);
		String sysLogTable = TeeStringUtil.getString(requestDatas.get("sysLogTable"),"0");
		
		String sql="";
		String queryStr="";
		if(type==1){
			queryStr+=" and (log.type=? or log.type=? ) ";
			list.add("003E");
			list.add("003F");
		}else if(type==2){
			queryStr+=" and log.type=? ";
			list.add("VISIT");
		}else if(type==3){
			queryStr+=" and log.error_flag = ?  ";
			list.add(1);
		}else if(type==4){
			queryStr+=" and log.type=? ";
			list.add("VISIT");
			if(s.size()>0){
				queryStr+=" and log.person_id in ("+StringUtils.join(s.toArray(), ",")+") ";
			}else{
				//queryStr+=" and log.person_id in (0) ";
				return 0;
			}
		}else if(type==5){
			queryStr+=" and (log.type=? or log.type=? ) ";
			list.add("003E");
			list.add("003F");
			if(s.size()>0){
				queryStr+=" and log.person_id in ("+StringUtils.join(s.toArray(), ",")+") ";
			}else{
				//queryStr+=" and log.person_id in (0) ";
				return 0;
			}
		}else if(type==6){
			queryStr+=" and log.type!=? and log.type!=? and log.type!=? and log.error_flag != ? ";
			list.add("003E");
			list.add("003F");
			list.add("VISIT");
			list.add(1);
			if(s.size()>0){
				queryStr+=" and log.person_id in ("+StringUtils.join(s.toArray(), ",")+") ";
			}else{
				//queryStr+=" and log.person_id in (0) ";
				return 0;
			}
		}
		long count=0;
		if(sysLogTable.equals("0")){
			sql = "select count(*)  from sys_log log  where 1=1 ";
		}else{
			sql = "select count(*) from "+sysLogTable+" log  where 1=1 ";
			
		}
		count = countSQLByList(sql+queryStr, list);
        return count;
	}
}
