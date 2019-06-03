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
import com.tianee.oa.core.base.pm.bean.TeeHumanShift;
import com.tianee.oa.core.base.pm.dao.TeeHumanDocDao;
import com.tianee.oa.core.base.pm.dao.TeeHumanShiftDao;
import com.tianee.oa.core.base.pm.model.TeeHumanShiftModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeHumanShiftService extends TeeBaseService{
	
	@Autowired
	TeeHumanShiftDao shiftDao;
	
	@Autowired
	TeeHumanDocDao humanDocDao;
	
	public TeeHumanShift addHumanShift(TeeHumanShiftModel humanShiftModel) throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanShift humanShift =new TeeHumanShift();
		String time1=humanShiftModel.getsTime1Desc();
		String time2 = humanShiftModel.getsTime2Desc();
		if(!TeeUtility.isNullorEmpty(time1)){
			Calendar cl = Calendar.getInstance();
			cl.setTime(formatter.parse(time1));
			humanShift.setSTime1(cl);
		}
		if(!TeeUtility.isNullorEmpty(time2)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(time2));
			humanShift.setSTime2(cl2);
		}
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(humanShiftModel.getHumanDocSid());
		BeanUtils.copyProperties(humanShiftModel, humanShift);	
		humanShift.setHumanDoc(humanDoc);
		shiftDao.save(humanShift);
		return humanShift;
	}
	
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String sid = (String)requestDatas.get("humanDocSid");
		//TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(Integer.parseInt(sid));
		String hql = "from TeeHumanShift Shift where 1=1 and Shift.humanDoc.sid="+sid;
		List<TeeHumanShift> humanShifts = shiftDao.pageFind(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), null);
		long total = shiftDao.count("select count(*) "+hql, null);
		List<TeeHumanShiftModel> models = new ArrayList<TeeHumanShiftModel>();
		for(TeeHumanShift humanShift:humanShifts){
			TeeHumanShiftModel m = new TeeHumanShiftModel();
			BeanUtils.copyProperties(humanShift, m);	
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if(humanShift.getSTime1()!=null){
				m.setsTime1Desc(formatter.format(humanShift.getSTime1().getTime()));
			}
			if(humanShift.getSTime2()!=null){
				m.setsTime2Desc(formatter.format(humanShift.getSTime2().getTime()));
			}
			m.setHumanDocSid(Integer.parseInt(sid));
			String shiftTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_SHIFT_TYPE",humanShift.getSType());
			m.setsTypeDesc(shiftTypeDesc);
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	public TeeHumanShiftModel getModelById(int sid){
		TeeHumanShift humanShift = (TeeHumanShift)shiftDao.get(sid);
		TeeHumanShiftModel model = new TeeHumanShiftModel();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if(humanShift.getSTime1()!=null){
			model.setsTime1Desc(formatter.format(humanShift.getSTime1().getTime()));
		}else{
			model.setsTime1Desc(""); 
		}
		if(humanShift.getSTime2()!=null){
			model.setsTime2Desc(formatter.format(humanShift.getSTime2().getTime()));
		}else{
			model.setsTime2Desc("");
		}
		model.setHumanDocSid(humanShift.getHumanDoc().getSid());
		BeanUtils.copyProperties(humanShift,model);
		String shiftTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_SHIFT_TYPE",humanShift.getSType());
		model.setsTypeDesc(shiftTypeDesc);
		return model;
	}
	
	
	public void updateHumanShift(TeeHumanShiftModel humanShiftModel) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanShift humanShift = new TeeHumanShift();
		String time1=humanShiftModel.getsTime1Desc();
		String time2 = humanShiftModel.getsTime2Desc();
		if(!TeeUtility.isNullorEmpty(time1)){
			Calendar cl = Calendar.getInstance();
			cl.setTime(formatter.parse(time1));
			humanShift.setSTime1(cl);
		}
		if(!TeeUtility.isNullorEmpty(time2)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(time2));
			humanShift.setSTime2(cl2);
		}
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(humanShiftModel.getHumanDocSid());
		BeanUtils.copyProperties(humanShiftModel,humanShift);
		humanShift.setHumanDoc(humanDoc);
		shiftDao.update(humanShift);
	}
	
	public void delHumanShift(int sid){
		shiftDao.delete(sid);
	}
}
