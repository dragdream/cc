package com.tianee.oa.core.workflow.workmanage.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.general.dao.TeeSmsDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workFlowFrame.creator.TeeFlowCreatorInterface;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowTypeDao;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowTimerTask;
import com.tianee.oa.core.workflow.workmanage.dao.TeeFlowRuleDao;
import com.tianee.oa.core.workflow.workmanage.dao.TeeFlowTimerTaskDao;
import com.tianee.oa.core.workflow.workmanage.model.TeeFlowTimerTaskModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeJsonUtil;

@Service
public class TeeFlowTimerTaskService extends TeeBaseService implements TeeFlowTimerTaskServiceInterface{

	@Autowired
	private TeeFlowTimerTaskDao timerTaskDao;
	@Autowired
	private TeeFlowTypeDao flowTypeDao;
	@Autowired
	private TeeFlowRuleDao flowRuleDao;
	@Autowired
	TeeSmsDao smsDao;
	@Autowired
	TeeSmsManager smsManager;
	@Autowired
	TeeFlowCreatorInterface flowCreator;
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowTimerTaskServiceInterface#getFlowName(int)
	 */
	@Override
	public String getFlowName(int flowTypeId){
		
		TeeFlowType flowType = flowTypeDao.get(flowTypeId);
		
		return flowType.getFlowName();
	}
	
	  /* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowTimerTaskServiceInterface#datagrid(com.tianee.oa.webframe.httpModel.TeeDataGridModel, int, int)
	 */
	@Override
	@Transactional(readOnly = true)
	   	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,int userId,int flowId) {
	   		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
	   		List<TeeFlowTimerTask> taskList = new ArrayList<TeeFlowTimerTask>();
	   		List<TeeFlowTimerTaskModel> modelList = new ArrayList<TeeFlowTimerTaskModel>();
	   		TeeJsonUtil jsonUtil = new TeeJsonUtil();
	   		String hql = "from TeeFlowTimerTask ftt where ftt.flowType.sid="+flowId+" ";
	   		long total = timerTaskDao.count("select count(*) "+hql, null);
	   		try {
	   			j.setTotal(total);// 设置总记录数
	   			taskList = timerTaskDao.pageFind(hql+"order by ftt.sid desc", (dm.getPage()-1)*dm.getRows(), dm.getRows(), null);
	   			for(TeeFlowTimerTask task : taskList){
	   				TeeFlowTimerTaskModel model = new TeeFlowTimerTaskModel();
	   				BeanUtils.copyProperties(task, model);
	   				String str = "";
	   				for(TeePerson person : task.getUsers()){
	   					String name = person.getUserName();
	   					str += name+",";
	   				}
	   				model.setUserNames(str);
	   				Map<String,String> remindModel = jsonUtil.JsonStr2Map(task.getRemindModel());
	   				switch (task.getType()) {
					case 1:
						model.setTimerType("仅此一次");
						model.setRemindModelDesc(formatDateElement(remindModel.get("year"))+"-"+formatDateElement(remindModel.get("month"))+"-"+formatDateElement(remindModel.get("date"))
								+" "+formatDateElement(remindModel.get("hours"))+":"+formatDateElement(remindModel.get("minutes"))+":"+formatDateElement(remindModel.get("seconds")));
						break;
					case 2:
						model.setTimerType("每日提醒");
						model.setRemindModelDesc(formatDateElement(remindModel.get("hours"))+":"+formatDateElement(remindModel.get("minutes"))+":"+formatDateElement(remindModel.get("seconds")));
						break;
					case 3:
						model.setTimerType("每周提醒");
						if(remindModel.get("week").equals("2")){
							model.setRemindModelDesc("星期一");
						}else if(remindModel.get("week").equals("3")){
							model.setRemindModelDesc("星期二");
						}else if(remindModel.get("week").equals("4")){
							model.setRemindModelDesc("星期三");
						}else if(remindModel.get("week").equals("5")){
							model.setRemindModelDesc("星期四");
						}else if(remindModel.get("week").equals("6")){
							model.setRemindModelDesc("星期五");
						}else if(remindModel.get("week").equals("7")){
							model.setRemindModelDesc("星期六");
						}else if(remindModel.get("week").equals("1")){
							model.setRemindModelDesc("星期日");
						}
						break;
					case 4:
						model.setTimerType("每月提醒");
						model.setRemindModelDesc(formatDateElement(remindModel.get("date"))+"日 "+formatDateElement(remindModel.get("hours"))+":"+formatDateElement(remindModel.get("minutes"))+":"+formatDateElement(remindModel.get("seconds")));
						break;
					case 5:
						model.setTimerType("每年提醒");
						model.setRemindModelDesc(formatDateElement(remindModel.get("month"))+"月"+formatDateElement(remindModel.get("date"))+"日 "+formatDateElement(remindModel.get("hours"))+":"+formatDateElement(remindModel.get("minutes"))+":"+formatDateElement(remindModel.get("seconds")));
						break;
					}
	   				modelList.add(model);
	   			}
	   		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   		
	   		j.setRows(modelList);// 设置返回的行
	   		return j;
	   	}
	  
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowTimerTaskServiceInterface#getFlowTimerTaskById(int)
	 */
	@Override
	public TeeFlowTimerTaskModel getFlowTimerTaskById(int sid){
		
		TeeFlowTimerTaskModel model = new TeeFlowTimerTaskModel();
		TeeFlowTimerTask task  = timerTaskDao.get(sid);
		BeanUtils.copyProperties(task, model);
		String userIds = "";
		String userNames = "";
		Set<TeePerson> persons = task.getUsers();
		for(TeePerson person : persons){
			userNames+=person.getUserName()+",";
			userIds+=person.getUserId()+",";
		}
		model.setUsersIds(userIds);
		model.setUserNames(userNames);
		return model;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowTimerTaskServiceInterface#listExecutableTask()
	 */
	@Override
	public List<TeeFlowTimerTask> listExecutableTask(){
		return timerTaskDao.listExecutableTask();
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowTimerTaskServiceInterface#autoProcessingExecutableTask()
	 */
	@Override
	public void autoProcessingExecutableTask(){
		List<TeeFlowTimerTask> list = listExecutableTask();
		long currentTime = new Date().getTime();
		Date date = new Date();
		Map params = new HashMap();
		Set<TeePerson> persons = null;;
		TeeFlowRunPrcs frp = null;
		Calendar calendar = Calendar.getInstance();
		for(TeeFlowTimerTask task:list){
			params.clear();
			task.setLastTime(date);
			calendar.setTimeInMillis(task.getRemindStamp());
			switch(task.getType()){
			case 1:
				break;
			case 2:
				calendar.add(Calendar.DATE, 1);
				break;
			case 3:
				calendar.add(Calendar.DATE, 7);
				break;
			case 4:
				calendar.add(Calendar.MONTH, 1);
				break;
			case 5:
				calendar.add(Calendar.YEAR, 1);
				break;
			}
			task.setRemindStamp(calendar.getTimeInMillis());
			persons = task.getUsers();
			for(TeePerson person:persons){
				params.clear();
				frp = flowCreator.CreateNewWork(task.getFlowType(), person,null,false);
				params.put("content", "您有一条流程定时任务已发起，请进行办理，流程标题："+frp.getFlowRun().getRunName());
				params.put("userListIds", person.getUuid()+"");
				params.put("moduleNo", "006");
				params.put("remindUrl", "/system/core/workflow/flowrun/prcs/index.jsp?" +
						"runId="+frp.getFlowRun().getRunId()+
						"&flowId="+frp.getFlowRun().getFlowType().getSid()+
						"&frpSid="+frp.getSid());
				
				smsManager.sendSms(params, null);
			}
			timerTaskDao.update(task);
		}
	}
	
	private String formatDateElement(String element){
		int i = Integer.parseInt(element);
		if(i<10){
			return "0"+i;
		}else{
			return i+"";
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowTimerTaskServiceInterface#update(com.tianee.oa.core.workflow.workmanage.bean.TeeFlowTimerTask)
	 */
	@Override
	public void update(TeeFlowTimerTask task){
		
		timerTaskDao.update(task);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowTimerTaskServiceInterface#get(int)
	 */
	@Override
	public TeeFlowTimerTask get(int sid){
		
		return timerTaskDao.get(sid);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowTimerTaskServiceInterface#add(com.tianee.oa.core.workflow.workmanage.bean.TeeFlowTimerTask)
	 */
	@Override
	public void add(TeeFlowTimerTask task){
		
		timerTaskDao.save(task);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowTimerTaskServiceInterface#deleteById(int)
	 */
	@Override
	public void deleteById(int sid){
		timerTaskDao.delete(sid);
	}
	
}
