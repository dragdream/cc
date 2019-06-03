package com.tianee.oa.core.base.message.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.message.bean.TeeMessage;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("messageDao")
public class TeeMessageDao  extends TeeBaseDao<TeeMessage>{
	
	@Autowired
	private TeePersonDao personDao;
	/**
	 * 增加
	 * @param TeeMessage
	 */
	public void addMessage(TeeMessage message) {
		save(message);
	}
	
	/**
	 * 获取离线消息
	 * @param userId
	 * @return
	 */
	public List<TeeMessage> getOfflineMessages(String userId){
		String hql = "from TeeMessage message where message.toId=? and message.remindFlag=0 order by message.body.sendTime desc";
		List<TeeMessage> list = find(hql, new Object[]{userId});
		executeUpdate("update TeeMessage message set message.remindFlag=1 where message.remindFlag=0 and message.toId=?", new Object[]{userId});
		return list;
	}
	
	/**
	 * byId 查询
	 * @param 
	 */
	public TeeMessage selectById(int id) {
		TeeMessage intf = load(id);
		return intf;
	}
	
	
	/**
	 * 查询所有记录
	 * @param 
	 */
	public  List<TeeMessage> select() {
		Object[] values = null;
		String hql = "from TeeMessage";
		List<TeeMessage> list = (List<TeeMessage>) executeQuery(hql,values);
		return list;
	}
	
	
	/**
	 * 查询 By发信人
	 * @param 
	 */
	public  List<TeeMessage> selectByFromId(String formId) {
		Object[] values = null;
		String hql = "from TeeMessage where formId = '" +formId + "'" ;
		List<TeeMessage> list = (List<TeeMessage>) executeQuery(hql,values);
		return list;
	}
	
	/**获取未确认的记录数
	 * @param toId 收信人
	 * @return
	 */
	public long getComfireNoCount(String  toId){
		String hql = " select count(*) from TeeMessage where   remindFlag != 1 and toId = '"  +  toId  + "' and deleteFlag = 0";
		return count(hql, null);
	}
	
	
	/**获取未确认的记录
	 * @param toId 收信人
	 * @return
	 */
	public List<TeeMessage> getComfireNoList(int firstResult,int pageSize,TeeDataGridModel dm,String  toId){
		String hql = " from TeeMessage where  remindFlag != 1 and toId = '"  +  toId  + "' and deleteFlag = 0";
		
		 hql = hql + " order by body.sendTime asc ";
		return pageFind(hql, firstResult, pageSize, null);
	}
	
	
	/**获取与人员对话的记录数
	 * @param toId 收信人
	 * @param fromId 发信人
	 * @return
	 */
	public long getPersonDialoguCount(String  loginId ,String personId){
		String hql = " select count(*) from TeeMessage where (toId = '"  +  loginId + "' and body.fromId = '" + personId + "' and deleteFlag = 0) or (toId = '"  + personId  + "' and body.fromId = '" + loginId  +"' and body.deleteFlag = 0) ";
		return count(hql, null);
	}
	
	/**获取与人员对话的记录
	 * @param toId 收信人
	 * @param fromId 发信人
	 * @return
	 */
	public List<TeeMessage> getPersonDialoguList(int firstResult,int pageSize,TeeDataGridModel dm,String  loginId ,String personId){
		String hql = " from TeeMessage where   (toId = '"  +  loginId + "' and body.fromId = '" + personId + "' and deleteFlag = 0) or (toId = '"  + personId  + "' and body.fromId = '" + loginId  +"' and body.deleteFlag = 0) ";
		 hql = hql + " order by body.sendTime desc ";
		return pageFind(hql, firstResult, pageSize, null);
	}
	
	
	
	/**条件查询获取记录数
	 * @param 
	 * @return
	 */
	public long getQueryCount(HttpServletRequest request ) {
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		String content = TeeStringUtil.getString(request.getParameter("content"), "");
		String messageTypeStr = request.getParameter("messageType");
		int messageType =  TeeStringUtil.getInteger(messageTypeStr, 0);
		int fromId =  TeeStringUtil.getInteger(request.getParameter("fromId"), 0);
		
		String beginTime =  TeeStringUtil.getString(request.getParameter("beginTime"), "");
		String endTime =  TeeStringUtil.getString(request.getParameter("endTime"), "");
		List list = new ArrayList();
		String hql = " select count(*) from TeeMessage where  ((toId = '"  +  person.getUserId() + "' and deleteFlag =0 )  or ( body.fromId = '" +  person.getUserId()  + "' and body.deleteFlag = 0) ) ";
		if(!TeeUtility.isNullorEmpty(content)){
			hql = hql + " and body.content like ? ";
			list.add( "%" + content + "%");
		}
		
		if(!TeeUtility.isNullorEmpty(messageTypeStr)){
			hql = hql + " and body.messageType = ? ";
			list.add(messageType);
		}
		
		if(fromId > 0 ){
			TeePerson p = personDao.get(fromId);
			hql = hql + " and body.fromId = ? ";
			list.add(p.getUserId());
		}
		
		
		if(!TeeUtility.isNullorEmpty(beginTime)){
			hql = hql + " and body.sendTime >= ? ";
			Calendar ca = Calendar.getInstance();
			Date date = TeeDateUtil.format(beginTime);
			ca.setTime(date);
			list.add(ca);
		}	
		
		if(!TeeUtility.isNullorEmpty(endTime)){
			hql = hql + " and body.sendTime <= ? ";
			Calendar ca = Calendar.getInstance();
			Date date = TeeDateUtil.format(endTime);
			ca.setTime(date);
			list.add(ca);
		}	
		return countByList(hql, list);
	}
	
	
	/**条件查询获取记录
	 * @param 
	 * @param 
	 * @return
	 */
	public List<TeeMessage> getQuery(int firstResult,int pageSize,TeeDataGridModel dm ,HttpServletRequest request ) {
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		String content = TeeStringUtil.getString(request.getParameter("content"), "");
		String messageTypeStr = request.getParameter("messageType");
		int messageType =  TeeStringUtil.getInteger(messageTypeStr, 0);
		int fromId =  TeeStringUtil.getInteger(request.getParameter("fromId") , 0);
		String beginTime =  TeeStringUtil.getString(request.getParameter("beginTime"), "");
		String endTime =  TeeStringUtil.getString(request.getParameter("endTime"), "");
		List list = new ArrayList();
		String hql = " from TeeMessage where  ((toId = '"  +  person.getUserId() + "' and deleteFlag =0 )  or ( body.fromId = '" +  person.getUserId()  + "' and body.deleteFlag = 0) ) ";
		if(!TeeUtility.isNullorEmpty(content)){
			hql = hql + " and body.content like ? ";
			list.add( "%" + content + "%");
		}
		
		if(!TeeUtility.isNullorEmpty(messageTypeStr)){
			hql = hql + " and body.messageType = ? ";
			list.add(messageType);
		}	
		if(fromId > 0 ){
			TeePerson p = personDao.get(fromId);
			hql = hql + " and body.fromId = ? ";
			list.add(p.getUserId());
		}
		
		
		if(!TeeUtility.isNullorEmpty(beginTime)){
			hql = hql + " and body.sendTime >= ? ";
			Calendar ca = Calendar.getInstance();
			Date date = TeeDateUtil.format(beginTime);
			ca.setTime(date);
			list.add(ca);
		}	
		
		if(!TeeUtility.isNullorEmpty(endTime)){
			hql = hql + " and body.sendTime <= ? ";
			Calendar ca = Calendar.getInstance();
			Date date = TeeDateUtil.format(endTime);
			ca.setTime(date);
			list.add(ca);
		}	
		return pageFindByList(hql, firstResult, pageSize,  list);
	}

	
	/**删除ByIds
	 * @param  ids
	 * @return
	 */
	public long deleteByIds(String  ids){
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length() -1 );
		}
		String hql = " delete from	TeeMessage where sid in(" + ids + ")";
		return deleteOrUpdateByQuery(hql, null);
	}
	
	
	
	/**更改收信人已读
	 * @param  ids
	 * @param  remindFlag
	 * @return
	 */
	public long updateRemindFlag(String  ids , int remindFlag){
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length() -1 );
		}
		if(ids.equals("")){
			return 0l;
		}
//		TeeDbUtility.i
//		String hql = " update  TeeMessage set remindFlag = " + remindFlag + " where uuid in(" + ids + ")";
		return 2;
	}
	
	
	
	/** 修改 收件人删除 状态
	 * @param  ids
	 * @param  loginPersonId  登录人Id
	 * @return
	 */
	public long updateToDeleteFlag(String  ids , String loginPersonId){
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length() -1 );
		}
		if(ids.equals("")){
			return 0l;
		}
		//String hql = " update  TeeMessage set remindFlag = " + remindFlag + " where sid in(" + ids + ")";
		
		String hql = "update TeeMessage  set toDeleteFlag = 1  where  toId ='" +  loginPersonId  + "' and sid in (" + ids + ")";
		return deleteOrUpdateByQuery(hql, null);
	}
	
	/** 修改 发件人删除 状态
	 * @param  ids
	 * @param  loginPersonId  登录人Id
	 * @return
	 */
	public long updateFromDeleteFlag(String  ids , String loginPersonId){
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length() -1 );
		}
		if(ids.equals("")){
			return 0l;
		}
		//String hql = " update  TeeMessage set remindFlag = " + remindFlag + " where sid in(" + ids + ")";
		
		String hql = "update TeeMessage  set fromDeleteFlag = 1 , " +
		        " toDeleteFlag = case  when remindFlag = 0  then 1 end  "
				+ " where  fromId ='" +  loginPersonId  + "' and sid in (" + ids + ")";
		return deleteOrUpdateByQuery(hql, null);
	}

}