package com.tianee.oa.core.base.news.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.news.bean.TeeNews;
import com.tianee.oa.core.base.notify.bean.TeeNotify;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 新闻管理 dao
 * @author zhp
 * @createTime 2013-12-26
 * @desc
 */
@Repository
public class TeeNewsDao  extends TeeBaseDao<TeeNews> {

	/**
	 * 删除 byId
	 * @author syl
	 * @date 2014-3-4
	 * @param id
	 * @return
	 */
	public void deleteById(int id){
		 delete(id);
	}
	
	/**
	 * 查询 byId
	 * @author syl
	 * @date 2014-3-4
	 * @param id
	 * @return
	 */
	public TeeNews getById(int id){
		TeeNews news =  get(id);
		return news;
	}
	
	
	public void addCount(int sid , int clickCount){
		String hql = "update TeeNews  set  clickCount = ?   where sid = ?";
		executeUpdate(hql, new Object[]{clickCount,sid});
	}
	
	
	public long getPersonalNoReadCount(int isRed , TeePerson loginPerson){
		int uId = loginPerson.getUuid();
		int deptId = 0;
		int userRoleId = 0;
		if(loginPerson.getDept() != null){
			deptId= loginPerson.getDept().getUuid();
		}
		if(loginPerson.getUserRole() != null){
			userRoleId= loginPerson.getUserRole().getUuid();
		}
		Calendar cal = Calendar.getInstance();
		TeeUtility.getMinTimeOfDayCalendar(cal);
		//Object[]  values = {cal.getTime() , cal.getTime()  };
		//权限
		String hql = "select count(news.sid) from TeeNews news where news.publish = '1'";
		
		  //exists (select 1 from n.infos info where info.toUser.uuid = ? and info.isRead = "+isRed+" ) 
		hql = hql + " and (exists (select 1 from news.postDept dept where dept.uuid = "+deptId+")"
				+ " or exists (select 1 from news.postUser user where user.uuid = " + uId + ")"
				+ " or exists (select 1 from news.postUserRole role where role.uuid = " + userRoleId + ") or news.allPriv=1)";
		if(isRed == 1){
			//已读
			hql = hql + " and  exists (select 1 from news.infos info where info.toUser.uuid = " + uId +")";
		}else if(isRed == 0){
			//未读
			hql = hql + " and not exists (select 1 from news.infos info where info.toUser.uuid = " + uId +")";
		}
		long count = count(hql, null);
		return count;
	}
	
	
	
	public long getPersonalNoReadCount(int isRed , TeePerson loginPerson,String typeId){
		int uId = loginPerson.getUuid();
		int deptId = 0;
		int userRoleId = 0;
		String deptFullId=null;
		if(loginPerson.getDept() != null){
			deptId= loginPerson.getDept().getUuid();
			deptFullId=loginPerson.getDept().getDeptFullId();
		}
		String deptFullId1=deptFullId.substring(1);
	    String deptFullId2="("+deptFullId1.replace("/", ",")+")";
		if(loginPerson.getUserRole() != null){
			userRoleId= loginPerson.getUserRole().getUuid();
		}
		Calendar cal = Calendar.getInstance();
		TeeUtility.getMinTimeOfDayCalendar(cal);
		//Object[]  values = {cal.getTime() , cal.getTime()  };
		//权限
		String hql = "select count(news.sid) from TeeNews news where news.publish = '1'";
		
		if(!TeeUtility.isNullorEmpty(typeId)){
			hql+=" and news.typeId="+typeId;
		}
		//exists (select 1 from n.infos info where info.toUser.uuid = ? and info.isRead = "+isRed+" ) 
		hql = hql + " and (exists (select 1 from news.postDept dept where dept.uuid in "+deptFullId2+")"
				+ " or exists (select 1 from news.postUser user where user.uuid = " + uId + ")"
				+ " or exists (select 1 from news.postUserRole role where role.uuid = " + userRoleId + ") or news.allPriv=1)";
		if(isRed == 1){
			//已读
			hql = hql + " and  exists (select 1 from news.infos info where info.toUser.uuid = " + uId +")";
		}else if(isRed == 0){
			//未读
			hql = hql + " and not exists (select 1 from news.infos info where info.toUser.uuid = " + uId +")";
		}
		long count = count(hql, null);
		return count;
	}
	
	public long getQueryPersonalNoReadCount(int isRed , TeePerson loginPerson,String typeId,String subject,String content){
		int uId = loginPerson.getUuid();
		int deptId = 0;
		int userRoleId = 0;
		String deptFullId=null;
		if(loginPerson.getDept() != null){
			deptId= loginPerson.getDept().getUuid();
			deptFullId=loginPerson.getDept().getDeptFullId();
		}
		String deptFullId1=deptFullId.substring(1);
	    String deptFullId2="("+deptFullId1.replace("/", ",")+")";
		if(loginPerson.getUserRole() != null){
			userRoleId= loginPerson.getUserRole().getUuid();
		}
		Calendar cal = Calendar.getInstance();
		TeeUtility.getMinTimeOfDayCalendar(cal);
		//Object[]  values = {cal.getTime() , cal.getTime()  };
		//权限
		String hql = "select count(news.sid) from TeeNews news where news.publish = '1'";
		
		if(!TeeUtility.isNullorEmpty(typeId)){
			hql+=" and news.typeId="+typeId;
		}
		//exists (select 1 from n.infos info where info.toUser.uuid = ? and info.isRead = "+isRed+" ) 
		hql = hql + " and (exists (select 1 from news.postDept dept where dept.uuid in "+deptFullId2+")"
				+ " or exists (select 1 from news.postUser user where user.uuid = " + uId + ")"
				+ " or exists (select 1 from news.postUserRole role where role.uuid = " + userRoleId + ") or news.allPriv=1)";
		if(isRed == 1){
			//已读
			hql = hql + " and  exists (select 1 from news.infos info where info.toUser.uuid = " + uId +")";
		}else if(isRed == 0){
			//未读
			hql = hql + " and not exists (select 1 from news.infos info where info.toUser.uuid = " + uId +")";
		}
		
		String hql1 = " 1!=1 ";
		if(!TeeUtility.isNullorEmpty(subject)){
			String subject1="%"+subject+"%";
			hql1+=" or news.subject like '"+subject1+"'";
		}
		if(!TeeUtility.isNullorEmpty(content)){
			String content1="%"+content+"%";
			hql1+=" or news.content like '"+content1+"'";
		}
		
		//如果hql1还是初始化的值，则不加入该状态
		if(!" 1!=1 ".equals(hql1)){
			hql+=" and ("+hql1+")";
		}
		
		long count = count(hql, null);
		return count;
	}
	
	public List<TeeNews> getPersonalNoRead(int isRed,TeePerson loginPerson , TeeDataGridModel dm,String typeId,String subject,String content){
		int uId = loginPerson.getUuid();
		int deptId = 0;
		int userRoleId = 0;
		String deptFullId=null;
		if(loginPerson.getDept() != null){
			deptId= loginPerson.getDept().getUuid();
			deptFullId=loginPerson.getDept().getDeptFullId();
		}
		String deptFullId1=deptFullId.substring(1);
	    String deptFullId2="("+deptFullId1.replace("/", ",")+")";
		if(loginPerson.getUserRole() != null){
			userRoleId= loginPerson.getUserRole().getUuid();
		}
		Calendar cal = Calendar.getInstance();
		TeeUtility.getMinTimeOfDayCalendar(cal);
		//Object[]  values = {cal.getTime() , cal.getTime()   };
		//权限
		String hql = "select news  from TeeNews news where news.publish = '1' ";
		  //exists (select 1 from n.infos info where info.toUser.uuid = ? and info.isRead = "+isRed+" ) 
		if(!TeeUtility.isNullorEmpty(typeId)){
			hql+=" and news.typeId="+typeId;
		}
		
		hql = hql + " and (exists (select 1 from news.postDept dept where dept.uuid in  "+deptFullId2+")"
				+ " or exists (select 1 from news.postUser user where user.uuid = " + uId + ")"
				+ " or exists (select 1 from news.postUserRole role where role.uuid = " + userRoleId + ") or news.allPriv=1)";
		if(isRed == 1){
			//已读
			hql = hql + " and  exists (select 1 from news.infos info where info.toUser.uuid = " + uId +")";
		}else if(isRed == 0) {
			//未读
			hql = hql + " and not exists (select 1 from news.infos info where info.toUser.uuid = " + uId +")";
		}
		
		

		String hql1 = " 1!=1 ";
		if(!TeeUtility.isNullorEmpty(subject)){
			String subject1="%"+subject+"%";
			hql1+=" or news.subject like '"+subject1+"'";
		}
		if(!TeeUtility.isNullorEmpty(content)){
			String content1="%"+content+"%";
			hql1+=" or news.content like '"+content1+"'";
		}
		
		//如果hql1还是初始化的值，则不加入该状态
		if(!" 1!=1 ".equals(hql1)){
			hql+=" and ("+hql1+")";
		}
		
		if (dm.getSort() != null) {// 设置排序
			if(dm.getSort().equals("fromPersonName")){
				dm.setSort("fromPerson");
			}
			hql += " order by " + dm.getSort() + " " + dm.getOrder() ;
		}else{
			hql += " order by news.newsTime desc" ;
		}
		
		List<TeeNews> newsList = pageFind(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(),null);// 查询
		
		return newsList;
	}
	
	
	
	/**
	 * 图片新闻
	 * @param isRed
	 * @param loginPerson
	 * @param dm
	 * @param typeId
	 * @return
	 */
	public List<TeeNews> getPersonalNoReadPic(int isRed , TeePerson loginPerson , TeeDataGridModel dm,String typeId){
		int uId = loginPerson.getUuid();
		int deptId = 0;
		int userRoleId = 0;
		String deptFullId=null;
		if(loginPerson.getDept() != null){
			deptId= loginPerson.getDept().getUuid();
			deptFullId=loginPerson.getDept().getDeptFullId();
		}
		String deptFullId1=deptFullId.substring(1);
	    String deptFullId2="("+deptFullId1.replace("/", ",")+")";
		if(loginPerson.getUserRole() != null){
			userRoleId= loginPerson.getUserRole().getUuid();
		}
		Calendar cal = Calendar.getInstance();
		TeeUtility.getMinTimeOfDayCalendar(cal);
		//Object[]  values = {cal.getTime() , cal.getTime()   };
		//权限
		String hql = "select news  from TeeNews news where news.publish = '1' and thumbnail is not null ";
		  //exists (select 1 from n.infos info where info.toUser.uuid = ? and info.isRead = "+isRed+" ) 
		if(!TeeUtility.isNullorEmpty(typeId)){
			hql+=" and news.typeId="+typeId;
		}
		
		hql = hql + " and (exists (select 1 from news.postDept dept where dept.uuid in  "+deptFullId2+")"
				+ " or exists (select 1 from news.postUser user where user.uuid = " + uId + ")"
				+ " or exists (select 1 from news.postUserRole role where role.uuid = " + userRoleId + ") or news.allPriv=1)";
		if(isRed == 1){
			//已读
			hql = hql + " and  exists (select 1 from news.infos info where info.toUser.uuid = " + uId +")";
		}else if(isRed == 0) {
			//未读
			hql = hql + " and not exists (select 1 from news.infos info where info.toUser.uuid = " + uId +")";
		}
		if (dm.getSort() != null) {// 设置排序
			if(dm.getSort().equals("fromPersonName")){
				dm.setSort("fromPerson");
			}
			hql += " order by " + dm.getSort() + " " + dm.getOrder() ;
		}else{
			hql += " order by news.newsTime desc" ;
		}
		
		List<TeeNews> newsList = pageFind(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(),null);// 查询
		return newsList;
	}

	
	/**
	 * 获取未阅读的新闻列表
	 * @param state
	 * @param person
	 * @param typeId
	 * @return
	 */
	public List<TeeNews> getNoReadNewsList(int state, TeePerson loginPerson,
			String typeId) {
		int uId = loginPerson.getUuid();
		int deptId = 0;
		int userRoleId = 0;
		String deptFullId=null;
		if(loginPerson.getDept() != null){
			deptId= loginPerson.getDept().getUuid();
			deptFullId=loginPerson.getDept().getDeptFullId();
		}
		String deptFullId1=deptFullId.substring(1);
	    String deptFullId2="("+deptFullId1.replace("/", ",")+")";
		if(loginPerson.getUserRole() != null){
			userRoleId= loginPerson.getUserRole().getUuid();
		}
		Calendar cal = Calendar.getInstance();
		TeeUtility.getMinTimeOfDayCalendar(cal);
		//Object[]  values = {cal.getTime() , cal.getTime()   };
		//权限
		String hql = "select news  from TeeNews news where news.publish = '1' ";
		  //exists (select 1 from n.infos info where info.toUser.uuid = ? and info.isRead = "+isRed+" ) 
		if(!TeeUtility.isNullorEmpty(typeId)){
			hql+=" and news.typeId="+typeId;
		}
		
		hql = hql + " and (exists (select 1 from news.postDept dept where dept.uuid in  "+deptFullId2+")"
				+ " or exists (select 1 from news.postUser user where user.uuid = " + uId + ")"
				+ " or exists (select 1 from news.postUserRole role where role.uuid = " + userRoleId + ") or news.allPriv=1)";
		if(state == 1){
			//已读
			hql = hql + " and  exists (select 1 from news.infos info where info.toUser.uuid = " + uId +")";
		}else if(state == 0) {
			//未读
			hql = hql + " and not exists (select 1 from news.infos info where info.toUser.uuid = " + uId +")";
		}
			hql += " order by news.newsTime desc" ;
		
		
		List<TeeNews> newsList =executeQuery(hql, null);// 查询
		return newsList;
	}

	
	/*public boolean checkExistsInfo(TeePerson person, TeeNews news) {
		Object[] values = {person.getUuid() , news.getSid()};
		String hql  = "select count(sid) from TeeNewsInfo  where toUser.uuid= ? and news.sid = ?";
		long count = count(hql, values);
		if(count > 0){
			return true;
		}
		return false;
	}*/
}
