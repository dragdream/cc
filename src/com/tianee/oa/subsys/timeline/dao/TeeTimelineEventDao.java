package com.tianee.oa.subsys.timeline.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.subsys.timeline.bean.TeeTimelineEvent;
import com.tianee.oa.subsys.timeline.model.TeeTimelineEventModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.str.TeeUtility;


@Repository("TeeTimelineEventDao")
public class TeeTimelineEventDao extends TeeBaseDao<TeeTimelineEvent>{
	/**
	 * @author nieyi
	 * @param timelineEvent
	 */
	public void addTimelineEvent(TeeTimelineEvent timelineEvent) {
		save(timelineEvent);
	}
	
	/**
	 * @author nieyi
	 * @param timelineEvent
	 */
	public void updateTimelineEvent(TeeTimelineEvent timelineEvent) {
		update(timelineEvent);
	}
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeTimelineEvent loadById(int id) {
		TeeTimelineEvent intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeTimelineEvent getById(String id) {
		TeeTimelineEvent intf = get(id);
		return intf;
	}
	
	
	/**
	 * @author nieyi
	 * @param id
	 */
	public void delById(int id) {
		delete(id);
	}
	
	
	/**
	 * @author nieyi
	 * @param ids
	 */
	public void delByIds(String ids){
		
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids= ids.substring(0, ids.length() -1 );
			}
			String hql = "delete from TeeTimelineEvent where uuid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeePerson person = (TeePerson)requestDatas.get("LOGIN_USER");
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String timelineUuid=(String)requestDatas.get("timelineUuid");
		List param = new ArrayList();
		String hql = "from TeeTimelineEvent customer where 1=1";
		
		if(!TeeUtility.isNullorEmpty(timelineUuid) && !"0".equals(timelineUuid)){
			hql+=" and customer.timeline.uuid=?";
			param.add(timelineUuid);
		}
		List<TeeTimelineEvent> infos = super.pageFindByList(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		long total = countByList("select count(*) "+hql, param);
		List<TeeTimelineEventModel> models = new ArrayList<TeeTimelineEventModel>();
		for(TeeTimelineEvent timelineEvent:infos){
			TeeTimelineEventModel m = new TeeTimelineEventModel();
			m=parseModel(timelineEvent);	
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	
	/**
	 * 对象转换
	 * @author nieyi
	 * @param timelineEvent
	 * @return
	 */
	public TeeTimelineEventModel parseModel(TeeTimelineEvent timelineEvent){
		TeeTimelineEventModel model = new TeeTimelineEventModel();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		if(timelineEvent == null){
			return null;
		}
		BeanUtils.copyProperties(timelineEvent, model);
		if(!TeeUtility.isNullorEmpty(timelineEvent.getStartTime())){
			model.setStartTimeDesc(sf.format(timelineEvent.getStartTime().getTime()));
		}
		if(!TeeUtility.isNullorEmpty(timelineEvent.getEndTime())){
			model.setEndTimeDesc(sf.format(timelineEvent.getEndTime().getTime()));
		}
		return model;
	}

	
	public List<TeeTimelineEventModel> getEvent(Map requestDatas) {
		TeePerson person = (TeePerson)requestDatas.get("LOGIN_USER");
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String timelineUuid=(String)requestDatas.get("timelineUuid");
		List param = new ArrayList();
		String hql = "from TeeTimelineEvent customer where 1=1";
		
		if(!TeeUtility.isNullorEmpty(timelineUuid) && !"0".equals(timelineUuid)){
			hql+=" and customer.timeline.uuid=?";
			param.add(timelineUuid);
		}
		List<TeeTimelineEvent> infos = super.executeQueryByList(hql+" order by customer.startTime desc", param);
		List<TeeTimelineEventModel> models = new ArrayList<TeeTimelineEventModel>();
		for(TeeTimelineEvent timelineEvent:infos){
			TeeTimelineEventModel m = new TeeTimelineEventModel();
			m=parseModel(timelineEvent);	
			models.add(m);
		}
		return models;
	}

}