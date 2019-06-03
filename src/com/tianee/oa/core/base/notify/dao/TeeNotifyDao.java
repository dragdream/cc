package com.tianee.oa.core.base.notify.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.notify.bean.TeeNotify;
import com.tianee.oa.core.base.notify.model.TeeNotifyModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonManagerI;
import com.tianee.oa.oaconst.TeeModelIdConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeManagerPostPersonDataPrivModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
/**
 * 公告通知 
 * @author  zhp
 * @createTime 2013-12-26
 * @desc
 */
@Repository
public class TeeNotifyDao extends TeeBaseDao<TeeNotify>{

	@Autowired
	TeePersonManagerI personManagerI;
	/**
	 * 新增
	 * @author syl
	 * @date 2014-3-11
	 * @param notify
	 */
	public void addNotify(TeeNotify notify){
		save(notify);
	}
	
	
	/**
	 * 新增
	 * @author syl
	 * @date 2014-3-11
	 * @param notify
	 */
	public void updateNotify(TeeNotify notify){
		update(notify);
	}
	
	/**
	 * 根据Id  获取对象
	 * @author syl
	 * @date 2014-3-12
	 * @param sid
	 * @return
	 */
	public TeeNotify getById(int  sid){
		TeeNotify notify  = get(sid);
		return notify;
	}
	
	
	/**
	 * 根据发布范围获取个人公告  --- 查看公告   数量
	 * @author syl
	 * @date 2014-3-13
	 * @param isRed   只读状况  0 -未读  1-只读
	 * @param loginPerson
	 * @return
	 */
	public long getPersonalNoReadCount(int isRed , TeePerson loginPerson,String typeId){
		int uId = loginPerson.getUuid();
		int deptId = 0;
		int userRoleId = 0;
		String deptFullId = null;
		if(loginPerson.getDept() != null){
			deptId= loginPerson.getDept().getUuid();
			deptFullId = loginPerson.getDept().getDeptFullId();
		}
		String deptFullId1=deptFullId.substring(1);
	    String deptFullId2="("+deptFullId1.replace("/", ",")+")";
		if(loginPerson.getUserRole() != null){
			userRoleId= loginPerson.getUserRole().getUuid();
		}
		Calendar cal = Calendar.getInstance();
		TeeUtility.getMinTimeOfDayCalendar(cal);
		Object[]  values = {cal.getTime() , cal.getTime()  };
		//权限
		String hql = "select count(notify.sid) from TeeNotify notify where notify.publish = '1'  and (notify.endDate >= ? or notify.endDate is null) and (notify.beginDate <= ? or notify.beginDate is null) ";
		  //exists (select 1 from n.infos info where info.toUser.uuid = ? and info.isRead = "+isRed+" ) 
		if(!"".equals(typeId)){
			hql+=" and notify.typeId='"+typeId+"'";
		}
		
		hql = hql + " and (exists (select 1 from notify.postDept dept where dept.uuid in "+deptFullId2+")"
				+ " or exists (select 1 from notify.postUser user where user.uuid = " + uId + ")"
				+ " or exists (select 1 from notify.postUserRole role where role.uuid = " + userRoleId + ") or notify.allPriv=1)";
		if(isRed == 1){
			//已读
			hql = hql + " and  exists (select 1 from notify.infos info where info.toUser.uuid = " + uId +")";
		}else{
			//未读
			hql = hql + " and not   exists (select 1 from notify.infos info where info.toUser.uuid = " + uId +")";
		}
		long count = count(hql, values);
		return count;
	}
	
	/**
	 * 根据发布范围获取个人公告  --- 查看公告   
	 * @author syl
	 * @date 2014-3-13
	 * @param isRed   只读状况  0 -未读  1-只读
	 * @param loginPerson
	 * @return
	 */
	public List<TeeNotify> getPersonalNoRead(int isRed , TeePerson loginPerson , TeeDataGridModel dm,String typeId){
		
		int uId = loginPerson.getUuid();
		int deptId = 0;
		String deptFullId = null;
		int userRoleId = 0;
		if(loginPerson.getDept() != null){
			deptId= loginPerson.getDept().getUuid();
			deptFullId = loginPerson.getDept().getDeptFullId();
		}
	    String deptFullId1=deptFullId.substring(1);
	    String deptFullId2="("+deptFullId1.replace("/", ",")+")";
		if(loginPerson.getUserRole() != null){
			userRoleId= loginPerson.getUserRole().getUuid();
		}
		Calendar cal = Calendar.getInstance();
		TeeUtility.getMinTimeOfDayCalendar(cal);
		Object[]  values = {cal.getTime() , cal.getTime()   };
		//权限
		String hql = "select notify  from TeeNotify notify where notify.publish = '1'  and (notify.endDate >= ? or notify.endDate is null) and (notify.beginDate <= ? or notify.beginDate is null) ";
		  //exists (select 1 from n.infos info where info.toUser.uuid = ? and info.isRead = "+isRed+" ) 
		if(!"".equals(typeId)){
			hql+=" and notify.typeId='"+typeId+"'";
		}
		hql = hql + " and (exists (select 1 from notify.postDept dept where dept.uuid in "+deptFullId2+")"
				+ " or exists (select 1 from notify.postUser user where user.uuid = " + uId + ")"
				+ " or exists (select 1 from notify.postUserRole role where role.uuid = " + userRoleId + ") or notify.allPriv=1 )";
		if(isRed == 1){
			//已读
			hql = hql + " and  exists (select 1 from notify.infos info where info.toUser.uuid = " + uId +")";
		}else{
			//未读
			hql = hql + " and not   exists (select 1 from notify.infos info where info.toUser.uuid = " + uId +")";
		}
		if (dm.getSort() != null) {// 设置排序
			if(dm.getSort().equals("fromPersonName")){
				dm.setSort("fromPerson");
			}
			hql += " order by " + dm.getSort() + " " + dm.getOrder() ;
		}else{
			hql += " order by sendTime desc" ;
		}
		
		List<TeeNotify> notifys = pageFind(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(),values);// 查询
		return notifys;
	}
	
	
	
	/**
	 * 根据发布范围获取个人公告  --- 查询
	 * @author syl
	 * @date 2014-3-13
	 * @param loginPerson
	 * @param dm
	 * @param model  
	 * @param queryType  0-查询总记录数  1- 按分页查询
	 * @return
	 */
	public Map getPersonalQuery( TeePerson loginPerson , TeeDataGridModel dm , TeeNotifyModel model , String queryType,HttpServletRequest request){
		Map map = new HashMap();
		List<TeeNotify> notifys = new ArrayList<TeeNotify>();
		long count = 0;
		int uId = loginPerson.getUuid();
		int deptId = 0;
		int userRoleId = 0;
		String deptFullId = null;
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
		
		List list = new ArrayList();
		list.add(cal.getTime());
		list.add(cal.getTime());

		
		//String content = TeeStringUtil.getString(model.getContent(), "");
		String typeId = TeeStringUtil.getString(model.getTypeId(), "");//公告类型
		String keyWord=TeeStringUtil.getString(request.getParameter("keyWord"));//关键字
		int departmentId=TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
		String endDateStr=TeeStringUtil.getString(request.getParameter("endDateStr"));
		String beginDateStr=TeeStringUtil.getString(request.getParameter("beginDateStr"));
		
		
		//String subject = TeeStringUtil.getString(model.getSubject(), "");
		//权限
		String hql = " from TeeNotify notify where notify.publish = '1'  and (notify.endDate >= ? or notify.endDate is null) and (notify.beginDate <= ? or notify.beginDate is null) ";
		 
//		if(!TeeUtility.isNullorEmpty(content)){
//			hql = hql + " and notify.content like ?";
//			list.add("%" + content +  "%");
//		}
//		
//		if(!TeeUtility.isNullorEmpty(subject)){
//			hql = hql + " and notify.subject like  ?";
//			list.add("%" + subject +  "%");
//		}
		
		String hql1 = " 1!=1 ";
		//如果hql1还是初始化的值，则不加入该状态
		if(!" 1!=1 ".equals(hql1)){
			hql+=" and ("+hql1+")";
		}
		//关键字
		if(!TeeUtility.isNullorEmpty(keyWord)){
			hql = hql + " and (notify.subject like  ? or notify.content like ?) ";
			list.add("%" + keyWord +  "%");
			list.add("%" + keyWord +  "%");
		}
		
		//发布部门
		if(departmentId!=0){
			hql = hql + " and notify.fromPerson.dept.uuid = ?";
			list.add(departmentId);
		}
		
		//公告类型
		if(!TeeUtility.isNullorEmpty(typeId)){
			hql = hql + " and notify.typeId = ?";
			list.add(typeId);
		}
		
		
		//发布时间
		if(!TeeUtility.isNullorEmpty(beginDateStr)){	
			Calendar c=TeeDateUtil.parseCalendar(beginDateStr+" 00:00:00");
			hql = hql + " and notify.sendTime >= ?  ";
			list.add(c.getTime());
		}
		
		//发布时间
		if(!TeeUtility.isNullorEmpty(endDateStr)){	
			Calendar c1=TeeDateUtil.parseCalendar(endDateStr+" 23:59:59");
			hql = hql + " and notify.sendTime <= ?  ";
			list.add(c1.getTime());
		}
		
		hql = hql + " and (exists (select 1 from notify.postDept dept where dept.uuid in "+deptFullId2+")"
				+ " or exists (select 1 from notify.postUser user where user.uuid = " + uId + ")"
				+ " or exists (select 1 from notify.postUserRole role where role.uuid = " + userRoleId + ")  or notify.allPriv=1)";
		if(model.getReadType() == 1){
			//已读
			hql = hql + " and  exists (select 1 from notify.infos info where info.toUser.uuid = " + uId +")";
		}else if(model.getReadType() == 0){
			//未读
			hql = hql + " and not   exists (select 1 from notify.infos info where info.toUser.uuid = " + uId +")";
		}
		
		if(queryType.equals("0")){//获取数量
			hql = "select count(notify) " + hql;
			count = countByList(hql, list);
			map.put("notifyCount", count);
		}else{
			hql = "select notify " + hql;
			if (dm.getSort() != null) {// 设置排序
				if(dm.getSort().equals("fromPersonName")){
					dm.setSort("fromPerson");
				}
				hql += " order by " + dm.getSort() + " " + dm.getOrder() ;
			}else{
				hql += " order by sendTime desc";
			}
			notifys = pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(),list);// 查询
			map.put("notifyList", notifys);
		}
		return map;
	}
	
	
	
	
	
	/**
	 * 根据公告管理  --- 查询
	 * @author syl
	 * @date 2014-3-13
	 * @param loginPerson
	 * @param dm
	 * @param model  
	 * @param queryType  0-查询总记录数  1- 按分页查询
	 * @return
	 */
	public Map managerQuery( TeePerson loginPerson , TeeDataGridModel dm , TeeNotifyModel model , String queryType , TeeManagerPostPersonDataPrivModel dataPrivModel){
		Map map = new HashMap();
		List<TeeNotify> notifys = new ArrayList<TeeNotify>();
		long count = 0;
		int uId = loginPerson.getUuid();
		
		List list = new ArrayList();
	
		
		String content = TeeStringUtil.getString(model.getContent(), "");
		String typeId = TeeStringUtil.getString(model.getTypeId(), "");
		String subject = TeeStringUtil.getString(model.getSubject(), "");
		//权限
		String hql = "from TeeNotify notify where 1=1 ";
		 
		//数据管理权限
		
		if(dataPrivModel.getPrivType().equals("0")){//空
			list.add(loginPerson.getUuid());
			hql = hql + " and notify.fromPerson.uuid = ?";//加上自己创建的
		}else if(dataPrivModel.getPrivType().equals("ALL")){//所有
			// hql = "from TeeNews n where  1 = 1";
		}else{
			List<Integer> pIdList = dataPrivModel.getPersonIds();//获取权限
			pIdList.add(loginPerson.getUuid());
			if(pIdList.size() > 0){
				String personIdsSql =  TeeDbUtility.IN("notify.fromPerson.uuid ", pIdList);
				hql = hql + " and " + personIdsSql;
			}else{
				hql = hql + " and 1=2";
			}
		}
		
		if(!TeeUtility.isNullorEmpty(content)){
			hql = hql + " and notify.content like ?";
			list.add("%" + content +  "%");
		}
		
		if(!TeeUtility.isNullorEmpty(subject)){
			hql = hql + " and notify.subject like  ?";
			list.add("%" + subject +  "%");
		}
		
		if(!TeeUtility.isNullorEmpty(typeId)){
			hql = hql + " and notify.typeId = ?";
			list.add(typeId);
		}
		
		
		if(queryType.equals("0")){//获取数量
			hql = "select count(notify) " + hql;
			count = countByList(hql, list);
			map.put("notifyCount", count);
		}else{
			hql = "select notify " + hql;
			if (dm.getSort() != null) {// 设置排序
				if(dm.getSort().equals("fromPersonName")){
					dm.setSort("fromPerson");
				}
				hql += " order by " + dm.getSort() + " " + dm.getOrder() ;
			}else{
				hql += " order by sendTime desc";
			}
			notifys = pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(),list);// 查询
			map.put("notifyList", notifys);
		}
		return map;
	}
	
	/**
	 * 根据发布范围获取公告  ---  用于桌面模块  和 主界面查询
	 * @author syl
	 * @date 2014-3-13
	 * @param loginPerson
	 * @param dm
	 * @param model  
	 * @param queryType  0-查询总记录数  1- 按分页查询
	 * @return
	 */
	public List<TeeNotify>  selectNotifyToDiskAndMainQuery( TeePerson loginPerson , TeeNotifyModel model , int count , int state){
		Map map = new HashMap();
		List<TeeNotify> notifys = new ArrayList<TeeNotify>();

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
		
		List list = new ArrayList();
		list.add(cal.getTime());
		list.add(cal.getTime());

		
		String content = TeeStringUtil.getString(model.getContent(), "");
		String typeId = TeeStringUtil.getString(model.getTypeId(), "");
		String subject = TeeStringUtil.getString(model.getSubject(), "");
		//权限
		String hql = " from TeeNotify notify where notify.publish = '1'  and (notify.endDate >= ? or notify.endDate is null) and (notify.beginDate <= ? or notify.beginDate is null) ";
		 
		if(!TeeUtility.isNullorEmpty(content)){
			hql = hql + " and notify.content like ?";
			list.add("%" + content +  "%");
		}
		
		if(!TeeUtility.isNullorEmpty(subject)){
			hql = hql + " and notify.subject like  ?";
			list.add("%" + subject +  "%");
		}
		
		if(!TeeUtility.isNullorEmpty(typeId)){
			hql = hql + " and notify.typeId = ?";
			list.add(typeId);
		}
		hql = hql + " and (exists (select 1 from notify.postDept dept where dept.uuid = "+deptId+")"
				+ " or exists (select 1 from notify.postUser user where user.uuid = " + uId + ")"
				+ " or exists (select 1 from notify.postUserRole role where role.uuid = " + userRoleId + "))";
		if(state == 1){
			//已读
			hql = hql + " and  exists (select 1 from notify.infos info where info.toUser.uuid = " + uId +")";
		}else if(state == 0){
			//未读
			hql = hql + " and not   exists (select 1 from notify.infos info where info.toUser.uuid = " + uId +")";
		}

		if(count > 0 ){
			notifys = pageFindByList(hql,0,count,list);// 查询
		}else{
			notifys = executeQueryByList(hql, list);
		}	
		return notifys;
	}
	
	
	
/**
 * 获取未阅读的公告集合
 * @param isRed
 * @param loginPerson
 * @param dm
 * @param typeId
 * @return
 */
	public List<TeeNotify> getNoReadNotifyList(int isRed , TeePerson loginPerson ,String typeId){
		
		int uId = loginPerson.getUuid();
		int deptId = 0;
		String deptFullId = null;
		int userRoleId = 0;
		if(loginPerson.getDept() != null){
			deptId= loginPerson.getDept().getUuid();
			deptFullId = loginPerson.getDept().getDeptFullId();
		}
	    String deptFullId1=deptFullId.substring(1);
	    String deptFullId2="("+deptFullId1.replace("/", ",")+")";
		if(loginPerson.getUserRole() != null){
			userRoleId= loginPerson.getUserRole().getUuid();
		}
		Calendar cal = Calendar.getInstance();
		TeeUtility.getMinTimeOfDayCalendar(cal);
		Object[]  values = {cal.getTime() , cal.getTime()   };
		//权限
		String hql = "select notify  from TeeNotify notify where notify.publish = '1'  and (notify.endDate >= ? or notify.endDate is null) and (notify.beginDate <= ? or notify.beginDate is null) ";
		  //exists (select 1 from n.infos info where info.toUser.uuid = ? and info.isRead = "+isRed+" ) 
		if(!"".equals(typeId)){
			hql+=" and notify.typeId='"+typeId+"'";
		}
		hql = hql + " and (exists (select 1 from notify.postDept dept where dept.uuid in "+deptFullId2+")"
				+ " or exists (select 1 from notify.postUser user where user.uuid = " + uId + ")"
				+ " or exists (select 1 from notify.postUserRole role where role.uuid = " + userRoleId + ") or notify.allPriv=1 )";
		if(isRed == 1){
			//已读
			hql = hql + " and  exists (select 1 from notify.infos info where info.toUser.uuid = " + uId +")";
		}else{
			//未读
			hql = hql + " and not   exists (select 1 from notify.infos info where info.toUser.uuid = " + uId +")";
		}
		
			hql += " order by sendTime desc" ;
		
		List<TeeNotify> notifys = find(hql, values);
		return notifys;
	}
	
}
