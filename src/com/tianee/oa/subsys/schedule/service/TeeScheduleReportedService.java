package com.tianee.oa.subsys.schedule.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.schedule.bean.TeeSchedule;
import com.tianee.oa.subsys.schedule.bean.TeeScheduleReported;
import com.tianee.oa.subsys.schedule.model.TeeScheduleReportedModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;

@Service
public class TeeScheduleReportedService  extends TeeBaseService{
	
	public void read(String uuid){
		TeeScheduleReported reported = (TeeScheduleReported) simpleDaoSupport.get(TeeScheduleReported.class, uuid);
		if(reported.getReadFlag()==0){
			reported.setReadFlag(1);
			reported.setReadTime(Calendar.getInstance());
			simpleDaoSupport.update(reported);
		}
	}
	
	@Transactional(readOnly=true)
	public TeeEasyuiDataGridJson datagrid(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		StringBuffer sb = new StringBuffer("from TeeScheduleReported r where r.user.uuid=? ");
		List<TeeScheduleReported> list = simpleDaoSupport.pageFind(sb.toString()+"order by r.schedule.crTime desc", dm.getFirstResult(), dm.getRows(), new Object[]{loginUser.getUuid()});
		List modelList = new ArrayList();
		long total = simpleDaoSupport.count("select count(*) "+sb.toString(), new Object[]{loginUser.getUuid()});
		for(TeeScheduleReported reported:list){
			modelList.add(Entity2Model(reported));
		}
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);
		return dataGridJson;
	}
	
	public static TeeScheduleReportedModel Entity2Model(TeeScheduleReported reported){
		TeeScheduleReportedModel model = new TeeScheduleReportedModel();
		BeanUtils.copyProperties(reported, model);
		
		model.setReadTimeDesc(TeeDateUtil.format(reported.getReadTime()));
		TeeSchedule schedule = reported.getSchedule();
		model.setScheduleFlag(schedule.getFlag());
		model.setScheduleId(schedule.getUuid());
		model.setScheduleName(schedule.getTitle());
		model.setScheduleRangeType(schedule.getRangeType());
		model.setScheduleType(schedule.getType());
		model.setScheduleTime(TeeScheduleService.getRangeTypeInfo(schedule.getRangeType(), schedule.getCrTime()).getRangeDesc());
		if(schedule.getUser()!=null){
			model.setScheduleUser(schedule.getUser().getUserName());
		}
		if(reported.getUser()!=null){
			model.setUserId(reported.getUser().getUuid());
			model.setUserName(reported.getUser().getUserName());
		}
		return model;
	}
	
	public static TeeScheduleReported Model2Entity(TeeScheduleReportedModel model){
		return null;
	}
	
	
	@Transactional(readOnly=true)
	public List<TeeScheduleReportedModel> getReportSchedules(Map requestData){
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		StringBuffer sb = new StringBuffer("from TeeScheduleReported r where r.user.uuid=? ");
		List<TeeScheduleReported> list = simpleDaoSupport.executeQuery(sb.toString()+"order by r.schedule.crTime desc",new Object[]{loginUser.getUuid()});
		List<TeeScheduleReportedModel> modelList = new ArrayList<TeeScheduleReportedModel>();
		for(TeeScheduleReported reported:list){
			modelList.add(Entity2Model(reported));
		}
		return modelList;
	}
}
