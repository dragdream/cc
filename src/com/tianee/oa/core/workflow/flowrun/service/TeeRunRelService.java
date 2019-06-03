package com.tianee.oa.core.workflow.flowrun.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeRunCustomerRel;
import com.tianee.oa.core.workflow.flowrun.bean.TeeRunRel;
import com.tianee.oa.core.workflow.flowrun.bean.TeeRunScheduleRel;
import com.tianee.oa.core.workflow.flowrun.bean.TeeRunTaskRel;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.cowork.bean.TeeCoWorkTask;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomerInfo;
import com.tianee.oa.subsys.schedule.bean.TeeSchedule;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class TeeRunRelService extends TeeBaseService implements TeeRunRelServiceInterface{
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRunRelServiceInterface#listSchedule(java.util.Map, com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	public TeeEasyuiDataGridJson listSchedule(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String title = TeeStringUtil.getString(requestData.get("title"));
		
		TeePerson loginUser = (TeePerson)requestData.get(TeeConst.LOGIN_USER);
		
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer("from TeeSchedule s where 1=1 and s.user.uuid=? ");
		params.add(loginUser.getUuid());
		
		if(!"".equals(title)){
			params.add("%"+title+"%");
			sb.append(" and s.title like ?");
		}
		
		
		List<TeeSchedule> list = simpleDaoSupport.pageFind(sb.toString()+" order by s.crTime desc", dm.getFirstResult(), dm.getRows(), params.toArray());
		long total = simpleDaoSupport.count("select count(*) "+sb.toString(), params.toArray());
		List modelList = new ArrayList();
		Map data = null;
		for(TeeSchedule rel:list){
			data = new HashMap();
			if(rel!=null){
				data.put("title", rel.getTitle());
				data.put("uuid", rel.getUuid());
			}
			modelList.add(data);
		}
		
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRunRelServiceInterface#listTask(java.util.Map, com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	public TeeEasyuiDataGridJson listTask(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String title = TeeStringUtil.getString(requestData.get("title"));
		
		TeePerson loginUser = (TeePerson)requestData.get(TeeConst.LOGIN_USER);
		
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer("from TeeCoWorkTask t where 1=1 and t.createUser.uuid=? ");
		params.add(loginUser.getUuid());
		
		if(!"".equals(title)){
			params.add("%"+title+"%");
			sb.append(" and t.taskTitle like ?");
		}
		
		
		List<TeeCoWorkTask> list = simpleDaoSupport.pageFind(sb.toString()+" order by t.sid desc", dm.getFirstResult(), dm.getRows(), params.toArray());
		long total = simpleDaoSupport.count("select count(*) "+sb.toString(), params.toArray());
		List modelList = new ArrayList();
		Map data = null;
		for(TeeCoWorkTask rel:list){
			data = new HashMap();
			if(rel!=null){
				data.put("title", rel.getTaskTitle());
				data.put("sid", rel.getSid());
			}
			modelList.add(data);
		}
		
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRunRelServiceInterface#listCustomer(java.util.Map, com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	public TeeEasyuiDataGridJson listCustomer(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String title = TeeStringUtil.getString(requestData.get("title"));
		
		TeePerson loginUser = (TeePerson)requestData.get(TeeConst.LOGIN_USER);
		
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer("from TeeCrmCustomerInfo c where 1=1 and (c.managePerson.uuid=? or c.managePerson is null)");
		params.add(loginUser.getUuid());
		
		if(!"".equals(title)){
			params.add("%"+title+"%");
			sb.append(" and c.customerName like ?");
		}
		
		
		List<TeeCrmCustomerInfo> list = simpleDaoSupport.pageFind(sb.toString()+" order by c.sid desc", dm.getFirstResult(), dm.getRows(), params.toArray());
		long total = simpleDaoSupport.count("select count(*) "+sb.toString(), params.toArray());
		List modelList = new ArrayList();
		Map data = null;
		for(TeeCrmCustomerInfo rel:list){
			data = new HashMap();
			if(rel!=null){
				data.put("title", rel.getCustomerName());
				data.put("sid", rel.getSid());
			}
			modelList.add(data);
		}
		
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRunRelServiceInterface#listFlowRun(java.util.Map, com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	public TeeEasyuiDataGridJson listFlowRun(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String title = TeeStringUtil.getString(requestData.get("title"));
		
		TeePerson loginUser = (TeePerson)requestData.get(TeeConst.LOGIN_USER);
		
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer("from TeeFlowRun fr where 1=1 and fr.beginPerson.uuid=?");
		params.add(loginUser.getUuid());
		
		if(!"".equals(title)){
			params.add("%"+title+"%");
			sb.append(" and fr.runName like ?");
		}
		
		
		List<TeeFlowRun> list = simpleDaoSupport.pageFind(sb.toString()+" order by fr.runId desc", dm.getFirstResult(), dm.getRows(), params.toArray());
		long total = simpleDaoSupport.count("select count(*) "+sb.toString(), params.toArray());
		List modelList = new ArrayList();
		Map data = null;
		for(TeeFlowRun rel:list){
			data = new HashMap();
			if(rel!=null){
				data.put("title", rel.getRunName());
				data.put("runId", rel.getRunId());
			}
			modelList.add(data);
		}
		
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRunRelServiceInterface#addScheduleRel(java.lang.String, int)
	 */
	@Override
	public void addScheduleRel(String scheduleId,int targetRunId){
		TeeRunScheduleRel rel = new TeeRunScheduleRel();
		rel.setTime(Calendar.getInstance());
		
		TeeFlowRun flowRun = new TeeFlowRun();
		flowRun.setRunId(targetRunId);
		rel.setFlowRun(flowRun);
		
		TeeSchedule schedule = new TeeSchedule();
		schedule.setUuid(scheduleId);
		
		rel.setSchedule(schedule);
		rel.setUserId(TeeRequestInfoContext.getRequestInfo().getUserSid());
		simpleDaoSupport.save(rel);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRunRelServiceInterface#delScheduleRel(java.lang.String)
	 */
	@Override
	public void delScheduleRel(String relUuid){
		simpleDaoSupport.delete(TeeRunScheduleRel.class, relUuid);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRunRelServiceInterface#listScheduleRel(java.util.Map, com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	public TeeEasyuiDataGridJson listScheduleRel(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer("from TeeRunScheduleRel sr where 1=1 and sr.flowRun.runId=? ");
		params.add(TeeStringUtil.getInteger(requestData.get("runId"), 0));
//		sb.append(" order by s.crTime desc");
//		System.out.println(requestData.get("runId"));
		List<TeeRunScheduleRel> list = simpleDaoSupport.pageFind(sb.toString()+"order by sr.time asc", dm.getFirstResult(), dm.getRows(), params.toArray());
		long total = simpleDaoSupport.count("select count(*) "+sb.toString(), params.toArray());
		List modelList = new ArrayList();
		Map data = null;
		for(TeeRunScheduleRel rel:list){
			data = new HashMap();
			data.put("uuid", rel.getUuid());
			data.put("title", rel.getSchedule().getTitle());
			data.put("scheduleId", rel.getSchedule().getUuid());
			data.put("userId", rel.getUserId());
			modelList.add(data);
		}
		
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRunRelServiceInterface#addTaskRel(int, int)
	 */
	@Override
	public void addTaskRel(int taskId,int targetRunId){
		TeeRunTaskRel rel = new TeeRunTaskRel();
		rel.setTime(Calendar.getInstance());
		
		TeeFlowRun flowRun = new TeeFlowRun();
		flowRun.setRunId(targetRunId);
		rel.setFlowRun(flowRun);
		
		TeeCoWorkTask task = new TeeCoWorkTask();
		task.setSid(taskId);
		
		rel.setTask(task);
		rel.setUserId(TeeRequestInfoContext.getRequestInfo().getUserSid());
		simpleDaoSupport.save(rel);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRunRelServiceInterface#delTaskRel(java.lang.String)
	 */
	@Override
	public void delTaskRel(String relUuid){
		simpleDaoSupport.delete(TeeRunTaskRel.class, relUuid);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRunRelServiceInterface#listTaskRel(java.util.Map, com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	public TeeEasyuiDataGridJson listTaskRel(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer("from TeeRunTaskRel tr where 1=1 and tr.flowRun.runId=? ");
		params.add(TeeStringUtil.getInteger(requestData.get("runId"), 0));
		List<TeeRunTaskRel> list = simpleDaoSupport.pageFind(sb.toString()+"order by tr.time asc", dm.getFirstResult(), dm.getRows(), params.toArray());
		long total = simpleDaoSupport.count("select count(*) "+sb.toString(), params.toArray());
		List modelList = new ArrayList();
		Map data = null;
		for(TeeRunTaskRel rel:list){
			data = new HashMap();
			data.put("uuid", rel.getUuid());
			data.put("title", rel.getTask().getTaskTitle());
			data.put("taskId", rel.getTask().getSid());
			data.put("userId", rel.getUserId());
			modelList.add(data);
		}
		
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRunRelServiceInterface#addCustomerRel(int, int)
	 */
	@Override
	public void addCustomerRel(int customerId,int targetRunId){
		TeeRunCustomerRel rel = new TeeRunCustomerRel();
		rel.setTime(Calendar.getInstance());
		
		TeeFlowRun flowRun = new TeeFlowRun();
		flowRun.setRunId(targetRunId);
		rel.setFlowRun(flowRun);
		
		TeeCrmCustomerInfo customerInfo = new TeeCrmCustomerInfo();
		customerInfo.setSid(customerId);
		
		rel.setCustomerInfo(customerInfo);
		rel.setUserId(TeeRequestInfoContext.getRequestInfo().getUserSid());
		simpleDaoSupport.save(rel);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRunRelServiceInterface#delCustomerRel(java.lang.String)
	 */
	@Override
	public void delCustomerRel(String relUuid){
		simpleDaoSupport.delete(TeeRunCustomerRel.class, relUuid);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRunRelServiceInterface#listCustomerRel(java.util.Map, com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	public TeeEasyuiDataGridJson listCustomerRel(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer("from TeeRunCustomerRel cr where 1=1 and cr.flowRun.runId=? ");
		params.add(TeeStringUtil.getInteger(requestData.get("runId"), 0));
		List<TeeRunCustomerRel> list = simpleDaoSupport.pageFind(sb.toString()+"order by cr.time asc", dm.getFirstResult(), dm.getRows(), params.toArray());
		long total = simpleDaoSupport.count("select count(*) "+sb.toString(), params.toArray());
		List modelList = new ArrayList();
		Map data = null;
		for(TeeRunCustomerRel rel:list){
			data = new HashMap();
			data.put("uuid", rel.getUuid());
			data.put("title", rel.getCustomerInfo().getCustomerName());
			data.put("customerId", rel.getCustomerInfo().getSid());
			data.put("userId", rel.getUserId());
			modelList.add(data);
		}
		
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRunRelServiceInterface#addFlowRunRel(int, int)
	 */
	@Override
	public void addFlowRunRel(int runId,int targetRunId){
		TeeRunRel rel = new TeeRunRel();
		rel.setTime(Calendar.getInstance());
		
		TeeFlowRun flowRun = new TeeFlowRun();
		flowRun.setRunId(targetRunId);
		rel.setFlowRun(flowRun);
		
		TeeFlowRun flowRun1 = new TeeFlowRun();
		flowRun1.setRunId(runId);
		
		rel.setFlowRun1(flowRun1);
		rel.setUserId(TeeRequestInfoContext.getRequestInfo().getUserSid());
		simpleDaoSupport.save(rel);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRunRelServiceInterface#delFlowRunRel(java.lang.String)
	 */
	@Override
	public void delFlowRunRel(String relUuid){
		simpleDaoSupport.delete(TeeRunRel.class, relUuid);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRunRelServiceInterface#listFlowRunRel(java.util.Map, com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	public TeeEasyuiDataGridJson listFlowRunRel(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer("from TeeRunRel cr where 1=1 and cr.flowRun.runId=? ");
		params.add(TeeStringUtil.getInteger(requestData.get("runId"), 0));
		List<TeeRunRel> list = simpleDaoSupport.pageFind(sb.toString()+"order by cr.time asc", dm.getFirstResult(), dm.getRows(), params.toArray());
		long total = simpleDaoSupport.count("select count(*) "+sb.toString(), params.toArray());
		List modelList = new ArrayList();
		Map data = null;
		for(TeeRunRel rel:list){
			data = new HashMap();
			data.put("uuid", rel.getUuid());
			data.put("title", rel.getFlowRun1().getRunName());
			data.put("runId", rel.getFlowRun1().getRunId());
			data.put("userId", rel.getUserId());
			modelList.add(data);
		}
		
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeRunRelServiceInterface#getDataCounts(int)
	 */
	@Override
	public Map getDataCounts(int runId){
		Map data = new HashMap();
		long scheduleCount = simpleDaoSupport.count("select count(*) from TeeRunScheduleRel where flowRun.runId=?", new Object[]{runId});
		long taskCount = simpleDaoSupport.count("select count(*) from TeeRunTaskRel where flowRun.runId=?", new Object[]{runId});
		long customerCount = simpleDaoSupport.count("select count(*) from TeeRunCustomerRel where flowRun.runId=?", new Object[]{runId});
		long runCount = simpleDaoSupport.count("select count(*) from TeeRunRel where flowRun.runId=?", new Object[]{runId});
		
		data.put("scheduleCount", scheduleCount);
		data.put("taskCount", taskCount);
		data.put("customerCount", customerCount);
		data.put("runCount", runCount);
		
		return data;
	}
}
