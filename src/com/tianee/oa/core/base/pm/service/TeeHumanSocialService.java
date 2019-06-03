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
import com.tianee.oa.core.base.pm.bean.TeeHumanSocial;
import com.tianee.oa.core.base.pm.dao.TeeHumanDocDao;
import com.tianee.oa.core.base.pm.dao.TeeHumanSocialDao;
import com.tianee.oa.core.base.pm.model.TeeHumanSocialModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeHumanSocialService extends TeeBaseService{
	
	@Autowired
	TeeHumanSocialDao socialDao;
	
	@Autowired
	TeeHumanDocDao humanDocDao;
	
	public TeeHumanSocial addHumanSocial(TeeHumanSocialModel humanSocialModel) throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-d");
		TeeHumanSocial humanSocial =new TeeHumanSocial();
		String birthDesc=humanSocialModel.getBirthdayDesc();
		if(!TeeUtility.isNullorEmpty(birthDesc)){
			Calendar cl = Calendar.getInstance();
			cl.setTime(formatter.parse(birthDesc));
			humanSocial.setBirthday(cl);
		}
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(humanSocialModel.getHumanDocSid());
		BeanUtils.copyProperties(humanSocialModel, humanSocial);	
		humanSocial.setHumanDoc(humanDoc);
		socialDao.save(humanSocial);
		return humanSocial;
	}
	
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String sid = (String)requestDatas.get("humanDocSid");
		//TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(Integer.parseInt(sid));
		String hql = "from TeeHumanSocial Social where 1=1 and Social.humanDoc.sid="+sid;
		List<TeeHumanSocial> humanSocials = socialDao.pageFind(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), null);
		long total = socialDao.count("select count(*) "+hql, null);
		List<TeeHumanSocialModel> models = new ArrayList<TeeHumanSocialModel>();
		for(TeeHumanSocial humanSocial:humanSocials){
			TeeHumanSocialModel m = new TeeHumanSocialModel();
			BeanUtils.copyProperties(humanSocial, m);	
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if(humanSocial.getBirthday()!=null){
				m.setBirthdayDesc(formatter.format(humanSocial.getBirthday().getTime()));
			}
			m.setHumanDocSid(Integer.parseInt(sid));
			String relationDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_RELATION",humanSocial.getRelation());
			m.setRelationDesc(relationDesc);
			String policyDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_POLITICS",humanSocial.getPolicy());
			m.setPolicyDesc(policyDesc);
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	public TeeHumanSocialModel getModelById(int sid){
		TeeHumanSocial humanSocial = (TeeHumanSocial)socialDao.get(sid);
		TeeHumanSocialModel model = new TeeHumanSocialModel();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if(humanSocial.getBirthday()!=null){
			model.setBirthdayDesc(formatter.format(humanSocial.getBirthday().getTime()));
		}else{
			model.setBirthdayDesc("");
		}
		model.setHumanDocSid(humanSocial.getHumanDoc().getSid());
		BeanUtils.copyProperties(humanSocial,model);
		String relationDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_RELATION",humanSocial.getRelation());
		model.setRelationDesc(relationDesc);
		String policyDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_POLITICS",humanSocial.getPolicy());
		model.setPolicyDesc(policyDesc);
		return model;
	}
	
	
	public void updateHumanSocial(TeeHumanSocialModel humanSocialModel) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanSocial humanSocial = new TeeHumanSocial();
		String birthDesc=humanSocialModel.getBirthdayDesc();
		if(!TeeUtility.isNullorEmpty(birthDesc)){
			Calendar cl = Calendar.getInstance();
			cl.setTime(formatter.parse(birthDesc));
			humanSocial.setBirthday(cl);
		}
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(humanSocialModel.getHumanDocSid());
		BeanUtils.copyProperties(humanSocialModel,humanSocial);
		humanSocial.setHumanDoc(humanDoc);
		socialDao.update(humanSocial);
	}
	
	public void delHumanSocial(int sid){
		socialDao.delete(sid);
	}
}
