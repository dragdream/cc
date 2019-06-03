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

import com.tianee.oa.core.base.hr.TeeHrCodeManager;
import com.tianee.oa.core.base.pm.bean.TeeHumanDoc;
import com.tianee.oa.core.base.pm.bean.TeeHumanRehab;
import com.tianee.oa.core.base.pm.dao.TeeHumanDocDao;
import com.tianee.oa.core.base.pm.dao.TeeHumanRehabDao;
import com.tianee.oa.core.base.pm.model.TeeHumanRehabModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeHumanRehabService extends TeeBaseService{
	
	@Autowired
	TeeHumanRehabDao rehabDao;
	
	@Autowired
	TeeHumanDocDao humanDocDao;
	
	@Autowired
	TeePersonDao personDao;
	public TeeHumanRehab addHumanRehab(TeeHumanRehabModel humanRehabModel,int isDel) throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanRehab humanRehab =new TeeHumanRehab();
		String payTimeDesc=humanRehabModel.getPayTimeDesc();
		String planTimeDesc = humanRehabModel.getPlanTimeDesc();
		String realTimeDesc = humanRehabModel.getRealTimeDesc();
		String regTimeDesc = humanRehabModel.getRegTimeDesc();
		if(!TeeUtility.isNullorEmpty(payTimeDesc)){
			Calendar cl = Calendar.getInstance();
			cl.setTime(formatter.parse(payTimeDesc));
			humanRehab.setPayTime(cl);
		}
		if(!TeeUtility.isNullorEmpty(planTimeDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(planTimeDesc));
			humanRehab.setPlanTime(cl2);
		}
		if(!TeeUtility.isNullorEmpty(realTimeDesc)){
			Calendar cl3 = Calendar.getInstance();
			cl3.setTime(formatter.parse(realTimeDesc));
			humanRehab.setRealTime(cl3);
		}
		if(!TeeUtility.isNullorEmpty(regTimeDesc)){
			Calendar cl4 = Calendar.getInstance();
			cl4.setTime(formatter.parse(regTimeDesc));
			humanRehab.setRegTime(cl4);
		}
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(humanRehabModel.getHumanDocSid());
		BeanUtils.copyProperties(humanRehabModel, humanRehab);	
		humanRehab.setHumanDoc(humanDoc);
		rehabDao.save(humanRehab);
		if(humanDoc.getIsOaUser().equals("true") && humanDoc.getOaUser()!=null && isDel==1){
			TeePerson oaUser = humanDoc.getOaUser();
			oaUser.setNotLogin("0");
			personDao.update(oaUser);
		}
		return humanRehab;
	}
	
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String sid = (String)requestDatas.get("humanDocSid");
		//TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(Integer.parseInt(sid));
		String hql = "from TeeHumanRehab Rehab where 1=1 and Rehab.humanDoc.sid="+sid;
		List<TeeHumanRehab> humanRehabs = rehabDao.pageFind(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), null);
		long total = rehabDao.count("select count(*) "+hql, null);
		List<TeeHumanRehabModel> models = new ArrayList<TeeHumanRehabModel>();
		for(TeeHumanRehab humanRehab:humanRehabs){
			TeeHumanRehabModel m = new TeeHumanRehabModel();
			BeanUtils.copyProperties(humanRehab, m);	
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if(humanRehab.getPayTime()!=null){
				m.setPayTimeDesc(formatter.format(humanRehab.getPayTime().getTime()));
			}
			if(humanRehab.getPlanTime()!=null){
				m.setPlanTimeDesc(formatter.format(humanRehab.getPlanTime().getTime()));
			}
			if(humanRehab.getRealTime()!=null){
				m.setRealTimeDesc(formatter.format(humanRehab.getRealTime().getTime()));
			}
			if(humanRehab.getRegTime()!=null){
				m.setRegTimeDesc(formatter.format(humanRehab.getRegTime().getTime()));
			}
			m.setHumanDocSid(Integer.parseInt(sid));
			String rehabTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_REHAB_TYPE", humanRehab.getRehabType());
			m.setRehabTypeDesc(rehabTypeDesc);
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	public TeeHumanRehabModel getModelById(int sid){
		TeeHumanRehab rehab = (TeeHumanRehab)rehabDao.get(sid);
		TeeHumanRehabModel model = new TeeHumanRehabModel();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if(rehab.getPayTime()!=null){
			model.setPayTimeDesc(formatter.format(rehab.getPayTime().getTime()));
		}else{
			model.setPayTimeDesc("");
		}
		if(rehab.getPlanTime()!=null){
			model.setPlanTimeDesc(formatter.format(rehab.getPlanTime().getTime()));
		}else{
			model.setPlanTimeDesc("");
		}
		if(rehab.getRealTime()!=null){
			model.setRealTimeDesc(formatter.format(rehab.getRealTime().getTime()));
		}else{
			model.setRealTimeDesc("");
		}
		if(rehab.getRegTime()!=null){
			model.setRegTimeDesc(formatter.format(rehab.getRegTime().getTime()));
		}else{
			model.setRegTimeDesc("");
		}
		model.setHumanDocSid(rehab.getHumanDoc().getSid());
		BeanUtils.copyProperties(rehab,model);
		String rehabTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_REHAB_TYPE", rehab.getRehabType());
		model.setRehabTypeDesc(rehabTypeDesc);
		return model;
	}
	
	
	public void updateHumanRehab(TeeHumanRehabModel humanRehabModel,int isDel) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanRehab humanRehab = new TeeHumanRehab();
		String payTimeDesc=humanRehabModel.getPayTimeDesc();
		String planTimeDesc = humanRehabModel.getPlanTimeDesc();
		String realTimeDesc = humanRehabModel.getRealTimeDesc();
		String regTimeDesc = humanRehabModel.getRegTimeDesc();
		if(!TeeUtility.isNullorEmpty(payTimeDesc)){
			Calendar cl = Calendar.getInstance();
			cl.setTime(formatter.parse(payTimeDesc));
			humanRehab.setPayTime(cl);
		}
		if(!TeeUtility.isNullorEmpty(planTimeDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(planTimeDesc));
			humanRehab.setPlanTime(cl2);
		}
		if(!TeeUtility.isNullorEmpty(realTimeDesc)){
			Calendar cl3 = Calendar.getInstance();
			cl3.setTime(formatter.parse(realTimeDesc));
			humanRehab.setRealTime(cl3);
		}
		if(!TeeUtility.isNullorEmpty(regTimeDesc)){
			Calendar cl4 = Calendar.getInstance();
			cl4.setTime(formatter.parse(regTimeDesc));
			humanRehab.setRegTime(cl4);
		}
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(humanRehabModel.getHumanDocSid());
		BeanUtils.copyProperties(humanRehabModel,humanRehab);
		humanRehab.setHumanDoc(humanDoc);
		rehabDao.update(humanRehab);
		if(humanDoc.getIsOaUser().equals("true") && humanDoc.getOaUser()!=null && isDel==1){
			TeePerson oaUser = humanDoc.getOaUser();
			oaUser.setNotLogin("0");
			personDao.update(oaUser);
		}
	}
	
	public void delHumanRehab(int sid){
		rehabDao.delete(sid);
	}
}
