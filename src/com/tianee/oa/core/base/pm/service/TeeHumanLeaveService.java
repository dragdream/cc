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
import com.tianee.oa.core.base.pm.bean.TeeHumanLeave;
import com.tianee.oa.core.base.pm.dao.TeeHumanDocDao;
import com.tianee.oa.core.base.pm.dao.TeeHumanLeaveDao;
import com.tianee.oa.core.base.pm.model.TeeHumanLeaveModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeHumanLeaveService extends TeeBaseService{
	
	@Autowired
	TeeHumanLeaveDao leaveDao;
	
	@Autowired
	TeePersonDao personDao;
	
	@Autowired
	TeeHumanDocDao humanDocDao;
	public TeeHumanLeave addHumanLeave(TeeHumanLeaveModel humanLeaveModel,int isDel) throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanLeave humanLeave =new TeeHumanLeave();
		String payTimeDesc=humanLeaveModel.getPayTimeDesc();
		String planTimeDesc = humanLeaveModel.getPlanTimeDesc();
		String realTimeDesc = humanLeaveModel.getRealTimeDesc();
		String regTimeDesc = humanLeaveModel.getRegTimeDesc();
		if(!TeeUtility.isNullorEmpty(payTimeDesc)){
			Calendar cl = Calendar.getInstance();
			cl.setTime(formatter.parse(payTimeDesc));
			humanLeave.setPayTime(cl);
		}
		if(!TeeUtility.isNullorEmpty(planTimeDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(planTimeDesc));
			humanLeave.setPlanTime(cl2);
			
		}
		if(!TeeUtility.isNullorEmpty(realTimeDesc)){
			Calendar cl3 = Calendar.getInstance();
			cl3.setTime(formatter.parse(realTimeDesc));
			humanLeave.setRealTime(cl3);
		}
		if(!TeeUtility.isNullorEmpty(regTimeDesc)){
			Calendar cl4 = Calendar.getInstance();
			cl4.setTime(formatter.parse(regTimeDesc));
			humanLeave.setRegTime(cl4);
		}
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(humanLeaveModel.getHumanDocSid());
		BeanUtils.copyProperties(humanLeaveModel, humanLeave);	
		humanLeave.setHumanDoc(humanDoc);
		leaveDao.save(humanLeave);
		if(humanDoc.getIsOaUser().equals("true") && humanDoc.getOaUser()!=null && isDel==1){
			TeePerson oaUser = humanDoc.getOaUser();
			oaUser.setNotLogin("1");
			personDao.update(oaUser);
		}
		return humanLeave;
	}
	
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String sid = (String)requestDatas.get("humanDocSid");
		//TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(Integer.parseInt(sid));
		String hql = "from TeeHumanLeave Leave where 1=1 and Leave.humanDoc.sid="+sid;
		List<TeeHumanLeave> humanLeaves = leaveDao.pageFind(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), null);
		long total = leaveDao.count("select count(*) "+hql, null);
		List<TeeHumanLeaveModel> models = new ArrayList<TeeHumanLeaveModel>();
		for(TeeHumanLeave humanLeave:humanLeaves){
			TeeHumanLeaveModel m = new TeeHumanLeaveModel();
			BeanUtils.copyProperties(humanLeave, m);	
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if(humanLeave.getPayTime()!=null){
				m.setPayTimeDesc(formatter.format(humanLeave.getPayTime().getTime()));
			}
			if(humanLeave.getPlanTime()!=null){
				m.setPlanTimeDesc(formatter.format(humanLeave.getPlanTime().getTime()));
			}
			if(humanLeave.getRealTime()!=null){
				m.setRealTimeDesc(formatter.format(humanLeave.getRealTime().getTime()));
			}
			if(humanLeave.getRegTime()!=null){
				m.setRegTimeDesc(formatter.format(humanLeave.getRegTime().getTime()));
			}
			m.setHumanDocSid(Integer.parseInt(sid));
			String leaveTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_LEAVE_TYPE", humanLeave.getLeaveType());
			m.setLeaveTypeDesc(leaveTypeDesc);
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	public TeeHumanLeaveModel getModelById(int sid){
		TeeHumanLeave humanLeave = (TeeHumanLeave)leaveDao.get(sid);
		TeeHumanLeaveModel model = new TeeHumanLeaveModel();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if(humanLeave.getPayTime()!=null){
			model.setPayTimeDesc(formatter.format(humanLeave.getPayTime().getTime()));
		}else{
			model.setPayTimeDesc("");
		}
		if(humanLeave.getPlanTime()!=null){
			model.setPlanTimeDesc(formatter.format(humanLeave.getPlanTime().getTime()));
		}else{
			model.setPlanTimeDesc("");
		}
		if(humanLeave.getRealTime()!=null){
			model.setRealTimeDesc(formatter.format(humanLeave.getRealTime().getTime()));
		}else{
			model.setRealTimeDesc("");
		}
		if(humanLeave.getRegTime()!=null){
			model.setRegTimeDesc(formatter.format(humanLeave.getRegTime().getTime()));
		}else{
			model.setRegTimeDesc("");
		}
		model.setHumanDocSid(humanLeave.getHumanDoc().getSid());
		BeanUtils.copyProperties(humanLeave,model);
		String leaveTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_LEAVE_TYPE", humanLeave.getLeaveType());
		model.setLeaveTypeDesc(leaveTypeDesc);
		return model;
	}
	
	
	public void updateHumanLeave(TeeHumanLeaveModel humanLeaveModel,int isDel) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanLeave humanLeave = new TeeHumanLeave();
		String payTimeDesc=humanLeaveModel.getPayTimeDesc();
		String planTimeDesc = humanLeaveModel.getPlanTimeDesc();
		String realTimeDesc = humanLeaveModel.getRealTimeDesc();
		String regTimeDesc = humanLeaveModel.getRegTimeDesc();
		if(!TeeUtility.isNullorEmpty(payTimeDesc)){
			Calendar cl = Calendar.getInstance();
			cl.setTime(formatter.parse(payTimeDesc));
			humanLeave.setPayTime(cl);
		}
		if(!TeeUtility.isNullorEmpty(planTimeDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(planTimeDesc));
			humanLeave.setPlanTime(cl2);
			
		}
		if(!TeeUtility.isNullorEmpty(realTimeDesc)){
			Calendar cl3 = Calendar.getInstance();
			cl3.setTime(formatter.parse(realTimeDesc));
			humanLeave.setRealTime(cl3);
		}
		if(!TeeUtility.isNullorEmpty(regTimeDesc)){
			Calendar cl4 = Calendar.getInstance();
			cl4.setTime(formatter.parse(regTimeDesc));
			humanLeave.setRegTime(cl4);
		}
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(humanLeaveModel.getHumanDocSid());
		BeanUtils.copyProperties(humanLeaveModel,humanLeave);
		humanLeave.setHumanDoc(humanDoc);
		leaveDao.update(humanLeave);
		
		if(humanDoc.getIsOaUser().equals("true") && humanDoc.getOaUser()!=null && isDel==1){
			TeePerson oaUser = humanDoc.getOaUser();
			oaUser.setNotLogin("1");
			personDao.update(oaUser);
		}
	}
	
	public void delHumanLeave(int sid){
		leaveDao.delete(sid);
	}
}
