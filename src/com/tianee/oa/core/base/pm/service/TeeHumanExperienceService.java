package com.tianee.oa.core.base.pm.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.pm.bean.TeeHumanDoc;
import com.tianee.oa.core.base.pm.bean.TeeHumanExperience;
import com.tianee.oa.core.base.pm.dao.TeeHumanDocDao;
import com.tianee.oa.core.base.pm.dao.TeeHumanExperienceDao;
import com.tianee.oa.core.base.pm.model.TeeHumanExperienceModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeHumanExperienceService extends TeeBaseService{
	
	@Autowired
	TeeHumanExperienceDao experienceDao;
	
	@Autowired
	TeeHumanDocDao humanDocDao;
	
	public TeeHumanExperience addHumanExperience(TeeHumanExperienceModel humanExperienceModel) throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanExperience humanExperience =new TeeHumanExperience();
		String startTimeDesc = humanExperienceModel.getStartTimeDesc();
		String endTimeDesc = humanExperienceModel.getEndTimeDesc();
		if(!TeeUtility.isNullorEmpty(startTimeDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(startTimeDesc));
			humanExperience.setStartTime(cl2);
		}
		if(!TeeUtility.isNullorEmpty(endTimeDesc)){
			Calendar cl3 = Calendar.getInstance();
			cl3.setTime(formatter.parse(endTimeDesc));
			humanExperience.setEndTime(cl3);
		}
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(humanExperienceModel.getHumanDocSid());
		BeanUtils.copyProperties(humanExperienceModel, humanExperience);	
		humanExperience.setHumanDoc(humanDoc);
		experienceDao.save(humanExperience);
		return humanExperience;
	}
	
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String sid = (String)requestDatas.get("humanDocSid");
		//TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(Integer.parseInt(sid));
		String hql = "from TeeHumanExperience Experience where 1=1 and Experience.humanDoc.sid="+sid;
		List<TeeHumanExperience> humanExperiences = experienceDao.pageFind(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), null);
		long total = experienceDao.count("select count(*) "+hql, null);
		List<TeeHumanExperienceModel> models = new ArrayList<TeeHumanExperienceModel>();
		for(TeeHumanExperience humanExperience:humanExperiences){
			TeeHumanExperienceModel m = new TeeHumanExperienceModel();
			BeanUtils.copyProperties(humanExperience, m);	
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if(humanExperience.getStartTime()!=null){
				m.setStartTimeDesc(formatter.format(humanExperience.getStartTime().getTime()));
			}
			if(humanExperience.getEndTime()!=null){
				m.setEndTimeDesc(formatter.format(humanExperience.getEndTime().getTime()));
			}
			m.setHumanDocSid(Integer.parseInt(sid));
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	public TeeHumanExperienceModel getModelById(int sid){
		TeeHumanExperience humanExperience = (TeeHumanExperience)experienceDao.get(sid);
		TeeHumanExperienceModel model = new TeeHumanExperienceModel();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if(humanExperience.getStartTime()!=null){
			model.setStartTimeDesc(formatter.format(humanExperience.getStartTime().getTime()));
		}else{
			model.setStartTimeDesc("");
		}
		if(humanExperience.getEndTime()!=null){
			model.setEndTimeDesc(formatter.format(humanExperience.getEndTime().getTime()));
		}else{
			model.setEndTimeDesc("");
		}
		model.setHumanDocSid(humanExperience.getHumanDoc().getSid());
		BeanUtils.copyProperties(humanExperience,model);
		return model;
	}
	
	
	public void updateHumanExperience(TeeHumanExperienceModel humanExperienceModel) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanExperience humanExperience = new TeeHumanExperience();
		String startTimeDesc = humanExperienceModel.getStartTimeDesc();
		String endTimeDesc = humanExperienceModel.getEndTimeDesc();
		if(!TeeUtility.isNullorEmpty(startTimeDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(startTimeDesc));
			humanExperience.setStartTime(cl2);
		}
		if(!TeeUtility.isNullorEmpty(endTimeDesc)){
			Calendar cl3 = Calendar.getInstance();
			cl3.setTime(formatter.parse(endTimeDesc));
			humanExperience.setEndTime(cl3);
		}
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(humanExperienceModel.getHumanDocSid());
		BeanUtils.copyProperties(humanExperienceModel,humanExperience);
		humanExperience.setHumanDoc(humanDoc);
		experienceDao.update(humanExperience);
	}
	
	public void delHumanExperience(int sid){
		experienceDao.delete(sid);
	}
}
