package com.tianee.oa.core.base.pm.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.hr.TeeHrCodeManager;
import com.tianee.oa.core.base.pm.bean.TeeHumanDoc;
import com.tianee.oa.core.base.pm.bean.TeeHumanSanction;
import com.tianee.oa.core.base.pm.dao.TeeHumanDocDao;
import com.tianee.oa.core.base.pm.dao.TeeHumanSanctionDao;
import com.tianee.oa.core.base.pm.model.TeeHumanSanctionModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeHumanSanctionService extends TeeBaseService{
	
	@Autowired
	TeeHumanSanctionDao sanctionDao;
	
	@Autowired
	TeeHumanDocDao humanDocDao;
	
	public TeeHumanSanction addHumanSanction(TeeHumanSanctionModel humanSanctionModel) throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanSanction humanSanction =new TeeHumanSanction();
		String sanDate=humanSanctionModel.getSanDateDesc();
		String validDateDesc = humanSanctionModel.getValidDateDesc();
		if(!TeeUtility.isNullorEmpty(sanDate)){
			Calendar cl = Calendar.getInstance();
			cl.setTime(formatter.parse(sanDate));
			humanSanction.setSanDate(cl);
		}
		if(!TeeUtility.isNullorEmpty(validDateDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(validDateDesc));
			humanSanction.setValidDate(cl2);
		}
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(humanSanctionModel.getHumanDocSid());
		BeanUtils.copyProperties(humanSanctionModel, humanSanction);	
		humanSanction.setHumanDoc(humanDoc);
		sanctionDao.save(humanSanction);
		return humanSanction;
	}
	
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas) throws Exception{
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String sid = (String)requestDatas.get("humanDocSid");
		String sanType = (String)requestDatas.get("sanType");
		String startTime = (String)requestDatas.get("startTime");
		String endTime = (String)requestDatas.get("endTime");
		String from = (String)requestDatas.get("from");
		String to = (String)requestDatas.get("to");
		List values = new ArrayList();
		String hql = "from TeeHumanSanction Sanction  where 1=1 and Sanction.humanDoc.sid="+sid;
		if(!TeeUtility.isNullorEmpty(sanType) && !"全部".equals(sanType)){
			hql+=" and Sanction.sanType='"+sanType+"'";
		}
		if(!TeeUtility.isNullorEmpty(startTime) && !TeeUtility.isNullorEmpty(endTime)){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Calendar sl=Calendar.getInstance();
			Calendar el = Calendar.getInstance();
			sl.setTime(formatter.parse(startTime));
			el.setTime(formatter.parse(endTime));
			hql+=" and Sanction.sanDate>? and Sanction.sanDate<?";
		    values.add(sl);
			values.add(el);
			
		}
		if(!TeeUtility.isNullorEmpty(from) && !TeeUtility.isNullorEmpty(to)){
			hql+=" and Sanction.pays>="+Integer.parseInt(from)+" and Sanction.pays<="+Integer.parseInt(to);
		}
		List<TeeHumanSanction> humanSanctions = sanctionDao.pageFindByList(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), values);
		long total = sanctionDao.countByList("select count(*) "+hql, values);
		List<TeeHumanSanctionModel> models = new ArrayList<TeeHumanSanctionModel>();
		for(TeeHumanSanction humanSanction:humanSanctions){
			TeeHumanSanctionModel m = new TeeHumanSanctionModel();
			BeanUtils.copyProperties(humanSanction, m);	
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if(humanSanction.getSanDate()!=null){
				m.setSanDateDesc(formatter.format(humanSanction.getSanDate().getTime()));
			}
			if(humanSanction.getSanDate()!=null){
				m.setValidDateDesc(formatter.format(humanSanction.getValidDate().getTime()));
			}
			String sanTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_SANCTION_TYPE", humanSanction.getSanType());
			m.setSanTypeDesc(sanTypeDesc);
			m.setHumanDocSid(Integer.parseInt(sid));
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	public TeeHumanSanctionModel getModelById(int sid){
		TeeHumanSanction humanSanction = (TeeHumanSanction)sanctionDao.get(sid);
		TeeHumanSanctionModel model = new TeeHumanSanctionModel();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if(humanSanction.getSanDate()!=null){
			model.setSanDateDesc(formatter.format(humanSanction.getSanDate().getTime()));
		}else{
			model.setSanDateDesc("");
		}
		if(humanSanction.getSanDate()!=null){
			model.setValidDateDesc(formatter.format(humanSanction.getValidDate().getTime()));
		}else{
			model.setValidDateDesc("");
		}
		model.setHumanDocSid(humanSanction.getHumanDoc().getSid());
		BeanUtils.copyProperties(humanSanction,model);
		String sanTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_SANCTION_TYPE", humanSanction.getSanType());
		model.setSanTypeDesc(sanTypeDesc);
		return model;
	}
	
	
	public void updateHumanSanction(TeeHumanSanctionModel humanSanctionModel) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanSanction humanSanction = new TeeHumanSanction();
		String sanDate=humanSanctionModel.getSanDateDesc();
		String validDateDesc = humanSanctionModel.getValidDateDesc();
		if(!TeeUtility.isNullorEmpty(sanDate)){
			Calendar cl = Calendar.getInstance();
			cl.setTime(formatter.parse(sanDate));
			humanSanction.setSanDate(cl);
		}
		if(!TeeUtility.isNullorEmpty(validDateDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(validDateDesc));
			humanSanction.setValidDate(cl2);
		}
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(humanSanctionModel.getHumanDocSid());
		BeanUtils.copyProperties(humanSanctionModel,humanSanction);
		humanSanction.setHumanDoc(humanDoc);
		sanctionDao.update(humanSanction);
	}
	
	public void delHumanSanction(int sid){
		sanctionDao.delete(sid);
	}
}
