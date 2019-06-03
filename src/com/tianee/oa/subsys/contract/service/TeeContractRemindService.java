package com.tianee.oa.subsys.contract.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.app.quartz.bean.TeeQuartzTask;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.contract.bean.TeeContract;
import com.tianee.oa.subsys.contract.bean.TeeContractRemind;
import com.tianee.oa.subsys.contract.model.TeeContractRemindModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.quartz.TeeQuartzTypes;
import com.tianee.quartz.util.TeeQuartzUtils;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.socket.MessagePusher;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeContractRemindService extends TeeBaseService{
	public void add(TeeContractRemindModel remindModel){
		TeeContractRemind contractRemind = Model2Entity(remindModel);
		simpleDaoSupport.save(contractRemind);
		
		TeeQuartzTask quartzTask = new TeeQuartzTask();
		quartzTask.setContent("《"+contractRemind.getContract().getContractName()+"》合同提醒："+contractRemind.getRemindContent());
		quartzTask.setExp(TeeQuartzUtils.getOnceQuartzExpression(contractRemind.getRemindTime()));
		quartzTask.setFrom(contractRemind.getCrUser().getUserId());
		quartzTask.setExpDesc("一次性提醒");
		quartzTask.setModelId(String.valueOf(contractRemind.getSid()));
		quartzTask.setModelNo("038");
		quartzTask.setTo(contractRemind.getToUserUids());
		quartzTask.setType(TeeQuartzTypes.ONCE);
		quartzTask.setUrl("");
		quartzTask.setUrl1("");
		MessagePusher.push2Quartz(quartzTask);
	}
	
	public void update(TeeContractRemindModel remindModel){
		TeeContractRemind contractRemind = Model2Entity(remindModel);
		simpleDaoSupport.update(contractRemind);
	}
	
	public void delete(int sid){
		//推送消息中心，取消该定时任务
		TeeQuartzTask task = new TeeQuartzTask();
		task.setModelId(String.valueOf(sid));
		task.setModelNo("038");
		task.setType(0);
		MessagePusher.push2Quartz(task);
		simpleDaoSupport.delete(TeeContractRemind.class, sid);
	}
	
	public TeeContractRemindModel get(int sid){
		
		return null;
	}
	
	public TeeEasyuiDataGridJson datagrid(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		
		StringBuffer hql = new StringBuffer("from TeeContractRemind where 1=1 ");
		int type = TeeStringUtil.getInteger(requestData.get("type"), 0);
		Calendar time1 = Calendar.getInstance();
		Calendar time2 = Calendar.getInstance();
		List params = new ArrayList();
		
		
		if(type==1){//本月
			
		}else if(type==2){//下个月
			time1.add(Calendar.MONTH, 1);
			time2.add(Calendar.MONTH, 1);
		}else{//所有提醒
			
		}
		
		time1.set(Calendar.HOUR_OF_DAY, 0);
		time1.set(Calendar.MINUTE, 0);
		time1.set(Calendar.SECOND, 0);
		time1.set(Calendar.DATE, time2.getActualMinimum(Calendar.DATE));
		
		time2.set(Calendar.HOUR_OF_DAY, 23);
		time2.set(Calendar.MINUTE, 59);
		time2.set(Calendar.SECOND, 59);
		time2.set(Calendar.DATE, time2.getActualMaximum(Calendar.DATE));
		
		
		if(type==1 || type==2){//本月或者下个月
			hql.append(" and (remindTime>? and remindTime<?)");
			params.add(time1);
			params.add(time2);
		}

		
		
		hql.append("and crUser.uuid=? ");
		params.add(loginUser.getUuid());
		
		List<TeeContractRemind> list = simpleDaoSupport.pageFind(hql.toString()+"order by sid desc", dm.getRows()*(dm.getPage()-1), dm.getRows(), params.toArray());
		long total = simpleDaoSupport.count("select count(*) "+hql.toString(), params.toArray());
		List<TeeContractRemindModel> modelList = new ArrayList();
		for(TeeContractRemind remind:list){
			modelList.add(Entity2Model(remind));
		}
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		
		
		return dataGridJson;
	}
	
	public TeeContractRemind Model2Entity(TeeContractRemindModel model){
		TeeContractRemind remind = new TeeContractRemind();
		BeanUtils.copyProperties(model, remind);
		TeeContract contract = 
				(TeeContract) simpleDaoSupport.get(TeeContract.class, model.getContractId());
		remind.setContract(contract);
		TeePerson crUser = 
				(TeePerson) simpleDaoSupport.get(TeePerson.class, model.getCrUserId());
		remind.setCrUser(crUser);
		
		remind.setRemindTime(TeeDateUtil.parseCalendar(model.getRemindTimeDesc()));
		
		String toUserIds = remind.getToUserIds();
		StringBuffer ids = new StringBuffer();
		StringBuffer names = new StringBuffer();
		StringBuffer uids = new StringBuffer();
		//设置Uids
		if(!"".equals(toUserIds) && toUserIds!=null){
			List<TeePerson> personList = simpleDaoSupport.find("from TeePerson where "+TeeDbUtility.IN("uuid", toUserIds)+"", null);
			for(TeePerson p:personList){
				ids.append(p.getUuid()+",");
				names.append(p.getUserName()+",");
				uids.append(p.getUserId()+",");
			}
			
			if(ids.length()!=0){
				ids.deleteCharAt(ids.length()-1);
				names.deleteCharAt(names.length()-1);
				uids.deleteCharAt(uids.length()-1);
			}
			
			remind.setToUserIds(ids.toString());
			remind.setToUserNames(names.toString());
			remind.setToUserUids(uids.toString());
		}
		
		return remind;
	}
	
	public TeeContractRemindModel Entity2Model(TeeContractRemind remind){
		TeeContractRemindModel contractRemindModel = new TeeContractRemindModel();
		BeanUtils.copyProperties(remind, contractRemindModel);
		contractRemindModel.setContractId(remind.getContract().getSid());
		contractRemindModel.setContractName(remind.getContract().getContractName());
		contractRemindModel.setRemindTimeDesc(TeeDateUtil.format(remind.getRemindTime()));
		
		return contractRemindModel;
	}
}
