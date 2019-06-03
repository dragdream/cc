package com.tianee.oa.core.base.meeting.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.meeting.bean.TeeMeetingTopic;
import com.tianee.oa.core.base.meeting.model.TeeMeetingTopicModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;
@Repository("meetingTopicDao")
public class TeeMeetingTopicDao  extends TeeBaseDao<TeeMeetingTopic> {
	public List<TeeMeetingTopicModel> getMeetingTopic(int meetingSid) {
		List<TeeMeetingTopicModel> modelList = new ArrayList<TeeMeetingTopicModel>();
		String hql=" from TeeMeetingTopic t where t.meeting.sid = ?";
		List param = new ArrayList();
		param.add(meetingSid);
		List<TeeMeetingTopic> list = super.executeQueryByList(hql, param);
		if(null!=list){
			for(TeeMeetingTopic t:list){
				TeeMeetingTopicModel m = new TeeMeetingTopicModel();
				BeanUtils.copyProperties(t, m);
				if(!TeeUtility.isNullorEmpty(t.getCrTime())){
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					m.setCrTimeDesc(sf.format(t.getCrTime().getTime()));
				}
				if(!TeeUtility.isNullorEmpty(t.getCrUser())){
					m.setCrUserId(t.getCrUser().getUuid());
					m.setCrUserName(t.getCrUser().getUserName());
				}
				modelList.add(m);
			}
		}
		return modelList;
	}

	public void addMeetingTopic(TeeMeetingTopic topic) {
		super.save(topic);
	}	
	
}
