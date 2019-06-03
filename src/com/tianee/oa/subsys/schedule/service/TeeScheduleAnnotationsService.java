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
import com.tianee.oa.subsys.schedule.model.TeeScheduleAnnotationsModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;

@Service
public class TeeScheduleAnnotationsService extends TeeBaseService{

	public void save(TeeScheduleAnnotationsModel annotationsModel){
		TeePerson p = new TeePerson();
		p.setUuid(annotationsModel.getUserId());
		
		TeeScheduleAnnotations annotations = new TeeScheduleAnnotations();
		TeeSchedule schedule = new TeeSchedule();
		schedule.setUuid(annotationsModel.getScheduleId());
		
		annotations.setContent(annotationsModel.getContent());
		annotations.setCrTime(Calendar.getInstance());
		annotations.setUser(p);
		annotations.setSchedule(schedule);
		
		simpleDaoSupport.save(annotations);
	}
	
	public void update(TeeScheduleAnnotationsModel annotationsModel){
		
	}
	
	public void delete(String uuid){
		simpleDaoSupport.delete(TeeScheduleAnnotations.class, uuid);
	}
	
	public TeeScheduleAnnotationsModel get(String uuid){
		return null;
	}
	
	public TeeEasyuiDataGridJson datagrid(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String uuid = (String) requestData.get("uuid");
		List<TeeScheduleAnnotations> list = simpleDaoSupport.find("from TeeScheduleAnnotations where schedule.uuid=? order by crTime asc", new Object[]{uuid});
		List modelList = new ArrayList();
		for(TeeScheduleAnnotations annotations:list){
			modelList.add(Entity2Model(annotations));
		}
		
		dataGridJson.setRows(modelList);
		return dataGridJson;
	}
	
	public static TeeScheduleAnnotationsModel Entity2Model(TeeScheduleAnnotations annotations){
		TeeScheduleAnnotationsModel annotationsModel = new TeeScheduleAnnotationsModel();
		BeanUtils.copyProperties(annotations, annotationsModel);
		
		annotationsModel.setCrTimeDesc(TeeDateUtil.format(annotations.getCrTime()));
		if(annotations.getUser()!=null){
			annotationsModel.setUserId(annotations.getUser().getUuid());
			annotationsModel.setUserName(annotations.getUser().getUserName());
		}
		
		return annotationsModel;
	}
	
	public static TeeScheduleAnnotations Model2Entity(TeeScheduleAnnotationsModel annotationsModel){
		return null;
	}
	
}
