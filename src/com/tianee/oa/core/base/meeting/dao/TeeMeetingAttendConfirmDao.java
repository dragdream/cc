package com.tianee.oa.core.base.meeting.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.meeting.bean.TeeMeetingAttendConfirm;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("attendMeetingConfirmDao")
public class TeeMeetingAttendConfirmDao extends TeeBaseDao<TeeMeetingAttendConfirm> {

	/**
	 * 根据会议Id删除
	 * @function: 
	 * @author: wyw
	 * @data: 2015年10月30日
	 * @param meetingIds void
	 */
	public void deleteObjByMeetingId(String meetingIds) {
		if (!TeeUtility.isNullorEmpty(meetingIds)) {
			if (meetingIds.endsWith(",")) {
				meetingIds = meetingIds.substring(0, meetingIds.length() - 1);
			}
		}
		String hql = "delete from TeeMeetingAttendConfirm where meetingId in (" + meetingIds + ")";
		deleteOrUpdateByQuery(hql, null);
	}

	/**
	 * 根据sId删除
	 * @function: 
	 * @author: wyw
	 * @data: 2015年10月30日
	 * @param sids void
	 */
	public void deleteObjBySid(String sids) {
		if (!TeeUtility.isNullorEmpty(sids)) {
			if (sids.endsWith(",")) {
				sids = sids.substring(0, sids.length() - 1);
			}
		}
		String hql = "delete from TeeMeetingAttendConfirm where sid in (" + sids + ")";
		deleteOrUpdateByQuery(hql, null);
	}
	
	/**
	 * 根据会议Id和人员id获取未确认出席数据
	 * @function: 
	 * @author: wyw
	 * @data: 2015年10月30日
	 * @param meetingId
	 * @param person
	 * @return List<TeeMeetingAttendConfirm>
	 */
	public List<TeeMeetingAttendConfirm> getObjList(int meetingId,TeePerson person){
		Object [] obj = {meetingId,person};
		String hql = " from TeeMeetingAttendConfirm where attendFlag=0 and meetingId =? and createUser=? ";
		return executeQuery(hql, obj);
	}
	/**
	 * 根据会议Id获取数据
	 * @function: 
	 * @author: wyw
	 * @data: 2015年10月30日
	 * @param meetingId
	 * @param person
	 * @return List<TeeMeetingAttendConfirm>
	 */
	public List<TeeMeetingAttendConfirm> getObjList(int meetingId){
		Object [] obj = {meetingId};
		String hql = " from TeeMeetingAttendConfirm where meetingId =? order by sid asc ";
		return executeQuery(hql, obj);
	}
	
	
	/**
	 * 获取参会确认数据
	 * @function: 
	 * @author: wyw
	 * @data: 2015年10月30日
	 * @param meetingId
	 * @param person
	 * @return List<TeeMeetingAttendConfirm>
	 */
	public List<TeeMeetingAttendConfirm> getConfrimObjList(int meetingId,TeePerson person){
		Object [] obj = {meetingId,person};
		String hql = " from TeeMeetingAttendConfirm where attendFlag<>0 and meetingId =? and createUser=? ";
		return executeQuery(hql, obj);
	}
	
	/**
	 * 根据会议Id和人员id获取已签阅数据
	 * @function: 
	 * @author: wyw
	 * @data: 2015年10月30日
	 * @param meetingId
	 * @param person
	 * @return List<TeeMeetingAttendConfirm>
	 */
	public List<TeeMeetingAttendConfirm> getReadObjList(int meetingId,TeePerson person){
		Object [] obj = {meetingId,person};
		String hql = " from TeeMeetingAttendConfirm where readFlag=0 and meetingId =? and createUser=? ";
		return executeQuery(hql, obj);
	}
	
	/**
	 * 获取已签阅数据
	 * @function: 
	 * @author: wyw
	 * @data: 2015年10月30日
	 * @param meetingId
	 * @param person
	 * @return List<TeeMeetingAttendConfirm>
	 */
	public List<TeeMeetingAttendConfirm> isReadObjList(int meetingId,TeePerson person){
		Object [] obj = {meetingId,person};
		String hql = " from TeeMeetingAttendConfirm where readFlag=1 and meetingId =? and createUser=? ";
		return executeQuery(hql, obj);
	}

}
