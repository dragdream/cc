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
import com.tianee.oa.core.base.pm.bean.TeeHumanSkill;
import com.tianee.oa.core.base.pm.dao.TeeHumanDocDao;
import com.tianee.oa.core.base.pm.dao.TeeHumanSkillDao;
import com.tianee.oa.core.base.pm.model.TeeHumanSkillModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeHumanSkillService extends TeeBaseService{
	
	@Autowired
	TeeHumanSkillDao skillDao;
	
	@Autowired
	TeeHumanDocDao humanDocDao;
	
	public TeeHumanSkill addHumanSkill(TeeHumanSkillModel humanSkillModel) throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanSkill humanSkill =new TeeHumanSkill();
		String startTimeDesc=humanSkillModel.getStartTimeDesc();
		String endTimeDesc = humanSkillModel.getEndTimeDesc();
		if(!TeeUtility.isNullorEmpty(startTimeDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(startTimeDesc));
			humanSkill.setStartTime(cl2);
		}
		if(!TeeUtility.isNullorEmpty(endTimeDesc)){
			Calendar cl3 = Calendar.getInstance();
			cl3.setTime(formatter.parse(endTimeDesc));
			humanSkill.setEndTime(cl3);
		}
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(humanSkillModel.getHumanDocSid());
		BeanUtils.copyProperties(humanSkillModel, humanSkill);	
		humanSkill.setHumanDoc(humanDoc);
		skillDao.save(humanSkill);
		return humanSkill;
	}
	
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String sid = (String)requestDatas.get("humanDocSid");
		//TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(Integer.parseInt(sid));
		String hql = "from TeeHumanSkill Skill where 1=1 and Skill.humanDoc.sid="+sid;
		List<TeeHumanSkill> humanSkills = skillDao.pageFind(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), null);
		long total = skillDao.count("select count(*) "+hql, null);
		List<TeeHumanSkillModel> models = new ArrayList<TeeHumanSkillModel>();
		for(TeeHumanSkill humanSkill:humanSkills){
			TeeHumanSkillModel m = new TeeHumanSkillModel();
			BeanUtils.copyProperties(humanSkill, m);	
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if(humanSkill.getStartTime()!=null){
				m.setStartTimeDesc(formatter.format(humanSkill.getStartTime().getTime()));
			}
			if(humanSkill.getEndTime()!=null){
				m.setEndTimeDesc(formatter.format(humanSkill.getEndTime().getTime()));
			}
			m.setHumanDocSid(Integer.parseInt(sid));
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	public TeeHumanSkillModel getModelById(int sid){
		TeeHumanSkill humanSkill = (TeeHumanSkill)skillDao.get(sid);
		TeeHumanSkillModel model = new TeeHumanSkillModel();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if(humanSkill.getStartTime()!=null){
			model.setStartTimeDesc(formatter.format(humanSkill.getStartTime().getTime()));
		}else{
			model.setStartTimeDesc("");
		}
		if(humanSkill.getEndTime()!=null){
			model.setEndTimeDesc(formatter.format(humanSkill.getEndTime().getTime()));
		}else{
			model.setEndTimeDesc("");
		}
		model.setHumanDocSid(humanSkill.getHumanDoc().getSid());
		BeanUtils.copyProperties(humanSkill,model);
		return model;
	}
	
	
	public void updateHumanSkill(TeeHumanSkillModel humanSkillModel) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanSkill humanSkill = new TeeHumanSkill();
		String startTimeDesc=humanSkillModel.getStartTimeDesc();
		String endTimeDesc = humanSkillModel.getEndTimeDesc();
		if(!TeeUtility.isNullorEmpty(startTimeDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(startTimeDesc));
			humanSkill.setStartTime(cl2);
		}
		if(!TeeUtility.isNullorEmpty(endTimeDesc)){
			Calendar cl3 = Calendar.getInstance();
			cl3.setTime(formatter.parse(endTimeDesc));
			humanSkill.setEndTime(cl3);
		}
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(humanSkillModel.getHumanDocSid());
		BeanUtils.copyProperties(humanSkillModel,humanSkill);
		humanSkill.setHumanDoc(humanDoc);
		skillDao.update(humanSkill);
	}
	
	public void delHumanSkill(int sid){
		skillDao.delete(sid);
	}
}
