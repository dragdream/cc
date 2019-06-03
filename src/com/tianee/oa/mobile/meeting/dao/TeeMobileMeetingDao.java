package com.tianee.oa.mobile.meeting.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.meeting.bean.TeeMeeting;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("mobileMeetingDao")
public class TeeMobileMeetingDao extends TeeBaseDao<TeeMeeting>{

	public Long getMyMeetingTotalByStatus(Map requestDatas) {
		int status = TeeStringUtil.getInteger(requestDatas.get("status"), 0);
		TeePerson loginUser = (TeePerson)requestDatas.get("LOGIN_USER");
		String hql = "from TeeMeeting meeting where  1 = 1 and status=? ";
		List list = new ArrayList();
		list.add(status);
		hql+=" and (user.uuid = "+loginUser.getUuid()+" or recorder.uuid = "+loginUser.getUuid()+" or exists (select 1 from meeting.attendeeList attendee where attendee.uuid = "+loginUser.getUuid()+"))";
		long count = countByList("select count(*) "+hql, list);
		return count;
	}

	
	
	public List<TeeMeeting> getMyMeetingByStatus(int firstIndex, int rows,
			TeeDataGridModel dm, Map requestDatas) {
		int status = TeeStringUtil.getInteger(requestDatas.get("status"), 0);
		TeePerson loginUser = (TeePerson)requestDatas.get("LOGIN_USER");
		String hql = "from TeeMeeting meeting where  1 = 1  and status=?";
		List list = new ArrayList();
		list.add(status);
		

		if(TeeUtility.isNullorEmpty(dm.getSort())){
			dm.setSort("startTime");
			dm.setOrder("desc");
		}
		hql+=" and (user.uuid = "+loginUser.getUuid()+" or recorder.uuid = "+loginUser.getUuid()+" or exists (select 1 from meeting.attendeeList attendee where attendee.uuid = "+loginUser.getUuid()+"))";
		
		hql = hql + " order by " + dm.getSort() + " " + dm.getOrder();
		return pageFindByList(hql, firstIndex, rows, list);
	}

}
