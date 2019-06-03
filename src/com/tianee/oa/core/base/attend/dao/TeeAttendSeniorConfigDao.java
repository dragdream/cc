package com.tianee.oa.core.base.attend.dao;

import java.io.Serializable;
import java.util.Map;






import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.attend.bean.TeeAttendConfig;
import com.tianee.oa.core.base.attend.bean.TeeAttendConfigRules;
import com.tianee.oa.core.base.attend.bean.TeeAttendDutyTable;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("attendSeniorConfigDao")
public class TeeAttendSeniorConfigDao extends TeeBaseDao{

	/**
	 * 根据用户  和  时间 （yyyy-MM-dd）  获取排班
	 * @param person
	 * @param dateStr
	 * @return
	 */
	public TeeAttendConfig getAttendConfigByUserAndDate(TeePerson person,String dateStr) {
		TeeAttendConfig config=null;
		//2017-12-21  转换成年月   2017-12
		String dateStr1=dateStr.substring(0, 7);
		//先判断该用户在该月份  有没有自由排班
		String hql=" from TeeAttendDutyTable  where user.uuid=? and pbDate=? ";
		
		TeeAttendDutyTable table=(TeeAttendDutyTable) unique(hql,new Object[]{person.getUuid(),dateStr1});
		if(TeeUtility.isNullorEmpty(table)){//没有自由排班   判断固定排班
			 //判断有没有固定排班
		     TeeAttendConfigRules rule =(TeeAttendConfigRules) unique(" from TeeAttendConfigRules r where (exists(select 1 from r.users user where user.uuid=? ) or exists (select 1 from r.depts dept where dept.uuid=?) or exists (select 1 from r.roles role where role.uuid=?) ) ",new Object[]{person.getUuid(),person.getDept()==null?"0":person.getDept().getUuid(),person.getUserRole()==null?"0":person.getUserRole().getUuid()} );
			 if(rule!=null){//有固定排班
				 String configModel=rule.getConfigModel();
				 Map<String,String> configModelMap=TeeJsonUtil.JsonStr2Map(configModel);
				 
				 //转换日期   判断今天是周几
				 int weekday=TeeStringUtil.getInteger(TeeDateUtil.getWeekOfDate(dateStr), 0);
				 
				 //获取排班
				 String attendConfigId=TeeStringUtil.getString(configModelMap.get("week"+weekday));
				
				 if(("0").equals(attendConfigId)){//代表休息
						config=new TeeAttendConfig();
						config.setSid(0);
						return config;
				 }else{
						config=(TeeAttendConfig)get(TeeAttendConfig.class,TeeStringUtil.getInteger(attendConfigId, 0));
					    return config;
				 }	
				  
			 }else{//无固定排班
				 return  null;
			 }
			
		}else{//有自由排班
			//获取当前是几号
			int day=TeeStringUtil.getInteger(dateStr.substring(8, dateStr.length()), 0);
			
		    String jsonData=table.getJsonData();
			Map<String,String> jsonDataMap=TeeJsonUtil.JsonStr2Map(jsonData);
			
			//获取当天的排班
			String attendConfigId=TeeStringUtil.getString(jsonDataMap.get(day+""));
			if(("0").equals(attendConfigId)){//代表休息
				config=new TeeAttendConfig();
				config.setSid(0);
				return config;
			}else if(("").equals(attendConfigId)){//代表无排班
				//判断有没有固定排班
			     TeeAttendConfigRules rule =(TeeAttendConfigRules) unique(" from TeeAttendConfigRules r where (exists(select 1 from r.users user where user.uuid=? ) or exists (select 1 from r.depts dept where dept.uuid=?) or exists (select 1 from r.roles role where role.uuid=?) ) ",new Object[]{person.getUuid(),person.getDept()==null?"0":person.getDept().getUuid(),person.getUserRole()==null?"0":person.getUserRole().getUuid()} );
				 if(rule!=null){//有固定排班
					 String configModel=rule.getConfigModel();
					 Map<String,String> configModelMap=TeeJsonUtil.JsonStr2Map(configModel);
					 
					 //转换日期   判断今天是周几
					 int weekday=TeeStringUtil.getInteger(TeeDateUtil.getWeekOfDate(dateStr), 0);
					 
					 //获取排班
					 String attendConfigId1=TeeStringUtil.getString(configModelMap.get("week"+weekday));
					
					 if(("0").equals(attendConfigId1)){//代表休息
							config=new TeeAttendConfig();
							config.setSid(0);
							return config;
					 }else{
							config=(TeeAttendConfig) get(TeeAttendConfig.class,TeeStringUtil.getInteger(attendConfigId1, 0));
						    return config;
					 }	
					  
				 }else{//无固定排班
					 return  null;
				 }
			}else{
				config=(TeeAttendConfig) get(TeeAttendConfig.class,TeeStringUtil.getInteger(attendConfigId, 0));
			    return config;
			}	
		}
	}
	
	
	public Object unique(String hql,Object params[]){
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		if(params!=null){
			for(int i=0;i<params.length;i++){
				query.setParameter(i, params[i]);
			}
		}
		return query.uniqueResult();
	}
	
	
	public Object get(Class clazz,Serializable id) {
		Session session = this.getSession();
		return session.get(clazz, id);
	}
}
