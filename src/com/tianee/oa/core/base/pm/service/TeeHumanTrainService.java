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
import com.tianee.oa.core.base.pm.bean.TeeHumanTrain;
import com.tianee.oa.core.base.pm.dao.TeeHumanDocDao;
import com.tianee.oa.core.base.pm.dao.TeeHumanTrainDao;
import com.tianee.oa.core.base.pm.model.TeeHumanTrainModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeHumanTrainService extends TeeBaseService{
	
	@Autowired
	TeeHumanTrainDao trainDao;
	
	@Autowired
	TeeHumanDocDao humanDocDao;
	
	public TeeHumanTrain addHumanTrain(TeeHumanTrainModel humanTrainModel) throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanTrain humanTrain =new TeeHumanTrain();
		String startTimeDesc = humanTrainModel.getStartTimeDesc();
		String endTimeDesc = humanTrainModel.getEndTimeDesc();
		if(!TeeUtility.isNullorEmpty(startTimeDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(startTimeDesc));
			humanTrain.setStartTime(cl2);
		}
		if(!TeeUtility.isNullorEmpty(endTimeDesc)){
			Calendar cl3 = Calendar.getInstance();
			cl3.setTime(formatter.parse(endTimeDesc));
			humanTrain.setEndTime(cl3);
		}
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(humanTrainModel.getHumanDocSid());
		BeanUtils.copyProperties(humanTrainModel, humanTrain);	
		humanTrain.setHumanDoc(humanDoc);
		trainDao.save(humanTrain);
		return humanTrain;
	}
	
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String sid = (String)requestDatas.get("humanDocSid");
		//TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(Integer.parseInt(sid));
		String hql = "from TeeHumanTrain Train where 1=1 and Train.humanDoc.sid="+sid;
		List<TeeHumanTrain> humanTrains = trainDao.pageFind(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), null);
		long total = trainDao.count("select count(*) "+hql, null);
		List<TeeHumanTrainModel> models = new ArrayList<TeeHumanTrainModel>();
		for(TeeHumanTrain humanTrain:humanTrains){
			TeeHumanTrainModel m = new TeeHumanTrainModel();
			BeanUtils.copyProperties(humanTrain, m);	
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if(humanTrain.getStartTime()!=null){
				m.setStartTimeDesc(formatter.format(humanTrain.getStartTime().getTime()));
			}
			if(humanTrain.getEndTime()!=null){
				m.setEndTimeDesc(formatter.format(humanTrain.getEndTime().getTime()));
			}
			m.setHumanDocSid(Integer.parseInt(sid));
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	public TeeHumanTrainModel getModelById(int sid){
		TeeHumanTrain humanTrain = (TeeHumanTrain)trainDao.get(sid);
		TeeHumanTrainModel model = new TeeHumanTrainModel();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if(humanTrain.getStartTime()!=null){
			model.setStartTimeDesc(formatter.format(humanTrain.getStartTime().getTime()));
		}else{
			model.setStartTimeDesc("");
		}
		if(humanTrain.getEndTime()!=null){
			model.setEndTimeDesc(formatter.format(humanTrain.getEndTime().getTime()));
		}else{
			model.setEndTimeDesc("");
		}
		model.setHumanDocSid(humanTrain.getHumanDoc().getSid());
		BeanUtils.copyProperties(humanTrain,model);
		return model;
	}
	
	
	public void updateHumanTrain(TeeHumanTrainModel humanTrainModel) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanTrain humanTrain = new TeeHumanTrain();
		String startTimeDesc = humanTrainModel.getStartTimeDesc();
		String endTimeDesc = humanTrainModel.getEndTimeDesc();
		if(!TeeUtility.isNullorEmpty(startTimeDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(startTimeDesc));
			humanTrain.setStartTime(cl2);
		}
		if(!TeeUtility.isNullorEmpty(endTimeDesc)){
			Calendar cl3 = Calendar.getInstance();
			cl3.setTime(formatter.parse(endTimeDesc));
			humanTrain.setEndTime(cl3);
		}
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(humanTrainModel.getHumanDocSid());
		BeanUtils.copyProperties(humanTrainModel,humanTrain);
		humanTrain.setHumanDoc(humanDoc);
		trainDao.update(humanTrain);
	}
	
	public void delHumanTrain(int sid){
		trainDao.delete(sid);
	}
}
