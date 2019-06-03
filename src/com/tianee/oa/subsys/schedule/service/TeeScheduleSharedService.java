package com.tianee.oa.subsys.schedule.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.schedule.bean.TeeSchedule;
import com.tianee.oa.subsys.schedule.bean.TeeScheduleReported;
import com.tianee.oa.subsys.schedule.bean.TeeScheduleShared;
import com.tianee.oa.subsys.schedule.model.TeeScheduleReportedModel;
import com.tianee.oa.subsys.schedule.model.TeeScheduleSharedModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;

@Service
public class TeeScheduleSharedService extends TeeBaseService{
	
	public void read(String uuid){
		TeeScheduleShared shared = (TeeScheduleShared) simpleDaoSupport.get(TeeScheduleShared.class, uuid);
		if(shared.getReadFlag()==0){
			shared.setReadFlag(1);
			shared.setReadTime(Calendar.getInstance());
			simpleDaoSupport.update(shared);
		}
	}
	
	@Transactional(readOnly=true)
	public TeeEasyuiDataGridJson datagrid(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		StringBuffer sb = new StringBuffer("from TeeScheduleShared r where r.user.uuid=? ");
		List<TeeScheduleShared> list = simpleDaoSupport.pageFind(sb.toString()+"order by r.schedule.crTime desc", dm.getFirstResult(), dm.getRows(), new Object[]{loginUser.getUuid()});
		List modelList = new ArrayList();
		long total = simpleDaoSupport.count("select count(*) "+sb.toString(), new Object[]{loginUser.getUuid()});
		for(TeeScheduleShared shared:list){
			modelList.add(Entity2Model(shared));
		}
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);
		return dataGridJson;
	}
	
	public static TeeScheduleShared Model2Entity(TeeScheduleSharedModel scheduleSharedModel){
		return null;
	}
	
	public static TeeScheduleSharedModel Entity2Model(TeeScheduleShared scheduleShared){
		TeeScheduleSharedModel model = new TeeScheduleSharedModel();
		BeanUtils.copyProperties(scheduleShared, model);
		
		model.setReadTimeDesc(TeeDateUtil.format(scheduleShared.getReadTime()));
		TeeSchedule schedule = scheduleShared.getSchedule();
		model.setScheduleFlag(schedule.getFlag());
		model.setScheduleId(schedule.getUuid());
		model.setScheduleName(schedule.getTitle());
		model.setScheduleRangeType(schedule.getRangeType());
		model.setScheduleType(schedule.getType());
		model.setScheduleTime(TeeScheduleService.getRangeTypeInfo(schedule.getRangeType(), schedule.getCrTime()).getRangeDesc());
		if(schedule.getUser()!=null){
			model.setScheduleUser(schedule.getUser().getUserName());
		}
		if(scheduleShared.getUser()!=null){
			model.setUserId(scheduleShared.getUser().getUuid());
			model.setUserName(scheduleShared.getUser().getUserName());
		}
		return model;
	}

	public List<TeeScheduleSharedModel> getShareSchedules(Map requestData) {
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		StringBuffer sb = new StringBuffer("from TeeScheduleShared r where r.user.uuid=? ");
		List<TeeScheduleShared> list = simpleDaoSupport.executeQuery(sb.toString()+"order by r.schedule.crTime desc", new Object[]{loginUser.getUuid()});
		List<TeeScheduleSharedModel> modelList = new ArrayList<TeeScheduleSharedModel>();
		for(TeeScheduleShared shared:list){
			modelList.add(Entity2Model(shared));
		}
		return modelList;
	}
}
