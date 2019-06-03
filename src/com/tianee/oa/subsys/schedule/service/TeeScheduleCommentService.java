package com.tianee.oa.subsys.schedule.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.schedule.bean.TeeSchedule;
import com.tianee.oa.subsys.schedule.bean.TeeScheduleAnnotations;
import com.tianee.oa.subsys.schedule.bean.TeeScheduleComment;
import com.tianee.oa.subsys.schedule.model.TeeScheduleAnnotationsModel;
import com.tianee.oa.subsys.schedule.model.TeeScheduleCommentModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;

@Service
public class TeeScheduleCommentService  extends TeeBaseService{
	public void save(TeeScheduleCommentModel commentModel){
		TeePerson p = new TeePerson();
		p.setUuid(commentModel.getUserId());
		
		TeeScheduleComment comment = new TeeScheduleComment();
		TeeSchedule schedule = new TeeSchedule();
		schedule.setUuid(commentModel.getScheduleId());
		
		comment.setContent(commentModel.getContent());
		comment.setCrTime(Calendar.getInstance());
		comment.setUser(p);
		comment.setSchedule(schedule);
		
		simpleDaoSupport.save(comment);
	}
	
	public void update(TeeScheduleComment comment){
		
	}
	
	public void delete(String uuid){
		simpleDaoSupport.delete(TeeScheduleComment.class, uuid);
	}
	
	public TeeEasyuiDataGridJson datagrid(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String uuid = (String) requestData.get("uuid");
		List<TeeScheduleComment> list = simpleDaoSupport.find("from TeeScheduleComment where schedule.uuid=? order by crTime asc", new Object[]{uuid});
		List modelList = new ArrayList();
		for(TeeScheduleComment comment:list){
			modelList.add(Entity2Model(comment));
		}
		
		dataGridJson.setRows(modelList);
		return dataGridJson;
	}
	
	public static TeeScheduleCommentModel Entity2Model(TeeScheduleComment comment){
		TeeScheduleCommentModel commentModel = new TeeScheduleCommentModel();
		BeanUtils.copyProperties(comment, commentModel);
		
		commentModel.setCrTimeDesc(TeeDateUtil.format(comment.getCrTime()));
		if(comment.getUser()!=null){
			commentModel.setUserId(comment.getUser().getUuid());
			commentModel.setUserName(comment.getUser().getUserName());
		}
		
		return commentModel;
	}
	
	public static TeeScheduleComment Model2Entity(TeeScheduleCommentModel commentModel){
		return null;
	}
}
