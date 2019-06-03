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
import com.tianee.oa.core.base.pm.bean.TeeHumanEducation;
import com.tianee.oa.core.base.pm.dao.TeeHumanDocDao;
import com.tianee.oa.core.base.pm.dao.TeeHumanEducationDao;
import com.tianee.oa.core.base.pm.model.TeeHumanEducationModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeHumanEducationService extends TeeBaseService{
	
	@Autowired
	TeeHumanEducationDao educationDao;
	
	@Autowired
	TeeHumanDocDao humanDocDao;
	
	public TeeHumanEducation addHumanEducation(TeeHumanEducationModel humanEducationModel) throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanEducation humanEducation =new TeeHumanEducation();
		String startTimeDesc = humanEducationModel.getStartTimeDesc();
		String endTimeDesc = humanEducationModel.getEndTimeDesc();
		if(!TeeUtility.isNullorEmpty(startTimeDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(startTimeDesc));
			humanEducation.setStartTime(cl2);
		}
		if(!TeeUtility.isNullorEmpty(endTimeDesc)){
			Calendar cl3 = Calendar.getInstance();
			cl3.setTime(formatter.parse(endTimeDesc));
			humanEducation.setEndTime(cl3);
		}
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(humanEducationModel.getHumanDocSid());
		BeanUtils.copyProperties(humanEducationModel, humanEducation);	
		humanEducation.setHumanDoc(humanDoc);
		educationDao.save(humanEducation);
		return humanEducation;
	}
	
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String sid = (String)requestDatas.get("humanDocSid");
		//TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(Integer.parseInt(sid));
		String hql = "from TeeHumanEducation Education where 1=1 and Education.humanDoc.sid="+sid;
		List<TeeHumanEducation> humanEducations = educationDao.pageFind(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), null);
		long total = educationDao.count("select count(*) "+hql, null);
		List<TeeHumanEducationModel> models = new ArrayList<TeeHumanEducationModel>();
		for(TeeHumanEducation humanEducation:humanEducations){
			TeeHumanEducationModel m = new TeeHumanEducationModel();
			BeanUtils.copyProperties(humanEducation, m);	
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if(humanEducation.getStartTime()!=null){
				m.setStartTimeDesc(formatter.format(humanEducation.getStartTime().getTime()));
			}
			if(humanEducation.getEndTime()!=null){
				m.setEndTimeDesc(formatter.format(humanEducation.getEndTime().getTime()));
			}
			m.setHumanDocSid(Integer.parseInt(sid));
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	public TeeHumanEducationModel getModelById(int sid){
		TeeHumanEducation humanEducation = (TeeHumanEducation)educationDao.get(sid);
		TeeHumanEducationModel model = new TeeHumanEducationModel();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if(humanEducation.getStartTime()!=null){
			model.setStartTimeDesc(formatter.format(humanEducation.getStartTime().getTime()));
		}else{
			model.setStartTimeDesc("");
		}
		if(humanEducation.getEndTime()!=null){
			model.setEndTimeDesc(formatter.format(humanEducation.getEndTime().getTime()));
		}else{
			model.setEndTimeDesc("");
		}
		model.setHumanDocSid(humanEducation.getHumanDoc().getSid());
		BeanUtils.copyProperties(humanEducation,model);
		return model;
	}
	
	
	public void updateHumanEducation(TeeHumanEducationModel humanEducationModel) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanEducation humanEducation = new TeeHumanEducation();
		String startTimeDesc=humanEducationModel.getStartTimeDesc();
		String endTimeDesc = humanEducationModel.getEndTimeDesc();
		if(!TeeUtility.isNullorEmpty(startTimeDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(startTimeDesc));
			humanEducation.setStartTime(cl2);
		}
		if(!TeeUtility.isNullorEmpty(endTimeDesc)){
			Calendar cl3 = Calendar.getInstance();
			cl3.setTime(formatter.parse(endTimeDesc));
			humanEducation.setEndTime(cl3);
		}
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(humanEducationModel.getHumanDocSid());
		BeanUtils.copyProperties(humanEducationModel,humanEducation);
		humanEducation.setHumanDoc(humanDoc);
		educationDao.update(humanEducation);
	}
	
	public void delHumanEducation(int sid){
		educationDao.delete(sid);
	}
}
