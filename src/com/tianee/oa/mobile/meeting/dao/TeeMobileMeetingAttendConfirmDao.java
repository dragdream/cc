package com.tianee.oa.mobile.meeting.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.meeting.bean.TeeMeetingAttendConfirm;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository("mobileMeetingAttendConfirmDao")
public class TeeMobileMeetingAttendConfirmDao extends TeeBaseDao<TeeMeetingAttendConfirm>{

	
	
	/**
	 * 根据状态获取参会情况列表
	 * @param meetingId
	 * @param attendFlag
	 * @return
	 */
	public List<TeeMeetingAttendConfirm> getObjList(int meetingId,
			int attendFlag) {
		Object [] obj = {meetingId,attendFlag};
		String hql = " from TeeMeetingAttendConfirm where meetingId =? and attendFlag=? order by sid asc ";
		return executeQuery(hql, obj);
	}
	

}
