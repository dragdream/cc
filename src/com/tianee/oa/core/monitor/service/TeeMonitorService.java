package com.tianee.oa.core.monitor.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonManagerI;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeModelIdConst;
import com.tianee.oa.subsys.schedule.service.TeeScheduleService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeManagerPostPersonDataPrivModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeMonitorService extends TeeBaseService{
	
	@Autowired
	private TeePersonManagerI personManagerI;
	
	@Autowired
	private TeePersonDao personDao;
	
	/**
	 * 监控列表
	 * @param requestData
	 * @param dm
	 * @return
	 * @throws ParseException 
	 */
	public TeeEasyuiDataGridJson monitorList(Map requestData,TeeDataGridModel dm) throws ParseException{
		String createTimeStrMin = (String) requestData.get("createTimeStrMin");
		String createTimeStrMax = (String) requestData.get("createTimeStrMax");
		TeePerson loginUser = (TeePerson)requestData.get(TeeConst.LOGIN_USER);
		loginUser = (TeePerson) simpleDaoSupport.get(TeePerson.class,loginUser.getUuid());
		
		//计划
		String scheduleQuery = "";
		if (!TeeUtility.isNullorEmpty(createTimeStrMin)) {
			scheduleQuery += " and schedule.crTime >= '" + createTimeStrMin + " 00:00:00'";
		}
		if (!TeeUtility.isNullorEmpty(createTimeStrMax)) {
			scheduleQuery += " and schedule.crTime <= '" + createTimeStrMax + " 23:59:59'";
		}
		//任务
		String taskQuery = "";
		if (!TeeUtility.isNullorEmpty(createTimeStrMin)) {
			taskQuery += " and task.createTime >= '" + createTimeStrMin + " 00:00:00'";
		}
		if (!TeeUtility.isNullorEmpty(createTimeStrMax)) {
			taskQuery += " and task.createTime <= '" + createTimeStrMax + " 23:59:59'";
		}
		
		//客户
		String customerQuery = "";
		if (!TeeUtility.isNullorEmpty(createTimeStrMin)) {
			customerQuery += " and customer.addTime >= '" + createTimeStrMin + " 00:00:00'";
		}
		if (!TeeUtility.isNullorEmpty(createTimeStrMax)) {
			customerQuery += " and customer.addTime <= '" + createTimeStrMax + " 23:59:59'";
		}
		//工作
		String frQuery = "";
		if (!TeeUtility.isNullorEmpty(createTimeStrMin)) {
			frQuery += " and fr.beginTime >= '" + createTimeStrMin + " 00:00:00'";
		}
		if (!TeeUtility.isNullorEmpty(createTimeStrMax)) {
			frQuery += " and fr.beginTime <= '" + createTimeStrMax + " 23:59:59'";
		}
		//日志
		String diaryQuery = "";
		if (!TeeUtility.isNullorEmpty(createTimeStrMin)) {
			diaryQuery += " and diary.createTime >= '" + createTimeStrMin + " 00:00:00'";
		}
		if (!TeeUtility.isNullorEmpty(createTimeStrMax)) {
			diaryQuery += " and diary.createTime <= '" + createTimeStrMax + " 23:59:59'";
		}
		//邮件
		String mailQuery = "";
		if (!TeeUtility.isNullorEmpty(createTimeStrMin)) {
			mailQuery += " and mail.sendTime >= '" + createTimeStrMin + " 00:00:00'";
		}
		if (!TeeUtility.isNullorEmpty(createTimeStrMax)) {
			mailQuery += " and mail.sendTime <= '" + createTimeStrMax + " 23:59:59'";
		}
		
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List modelList = new ArrayList();
		//数据管理权限
//		TeeManagerPostPersonDataPrivModel dataPrivModel = personManagerI.getManagerPostPersonIdsPriv(requestData, TeeModelIdConst.MONITOR_PRIV, "0");
		String select = "select "
				+ "p.uuid as userId,"
				+ "p.userName as userName,"
				+ "p.dept.deptName as deptName,"
				+ "(select count(schedule.uuid) from TeeSchedule schedule where schedule.user.uuid=p.uuid " + scheduleQuery + ") as scheduleCount,"
				+ "(select count(task.sid) from TeeCoWorkTask task where task.createUser.uuid=p.uuid " + taskQuery + " ) as taskCount,"
				+ "(select count(customer.sid) from TeeCrmCustomerInfo customer where customer.managePerson.uuid=p.uuid " + customerQuery + ") as customerCount,"
				+ "(select count(fr.runId) from TeeFlowRun fr where fr.beginPerson.uuid=p.uuid " + frQuery + ") as runCount,"
				+ "(select count(diary.sid) from TeeDiary diary where diary.createUser.uuid=p.uuid " + diaryQuery + " ) as diaryCount,"
				+ "(select count(mail.sid) from TeeMailBody mail where mail.fromuser.uuid=p.uuid " + mailQuery + ") as mailCount";
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from TeePerson p where 1=1 ");
		String findInSet = "";
		
		//获取直属下级
		List<TeePerson> underlines = personDao.getUnderlines(loginUser.getUuid());
		StringBuffer userFilter = new StringBuffer();
		if(underlines.size()==0){
			userFilter.append("0");
		}else{
			for(int i=0;i<underlines.size();i++){
				userFilter.append(underlines.get(i).getUuid());
				if(i!=underlines.size()-1){
					userFilter.append(",");
				}
			}
		}
		
//		if(dataPrivModel.getPrivType().equals("0")){//空，无管理权限
//			dataGridJson.setTotal(0L);
//			dataGridJson.setRows(modelList);// 设置返回的行
//			return dataGridJson;
//		}else if(dataPrivModel.getPrivType().equals("ALL")){//可管理所有的
//			
//		}else{
//			List<Integer> pIdList = dataPrivModel.getPersonIds();//获取权限
//			if(pIdList.size() > 0){
				findInSet = TeeDbUtility.IN("p.uuid", userFilter.toString());
//			}else{
//				dataGridJson.setTotal(0L);
//				dataGridJson.setRows(modelList);// 设置返回的行
//				return dataGridJson;
//			}
//		}
		
		if(!"".equals(findInSet)){
			hql.append(" and "+findInSet);
		}
		
		modelList = simpleDaoSupport.getMaps(select+hql.toString()+" order by p.userNo asc",null,dm.getFirstResult(),dm.getRows());
		long total = simpleDaoSupport.count("select count(p.uuid) "+hql.toString(), null);
		
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	
	public TeeEasyuiDataGridJson scheduleList(Map requestData,TeeDataGridModel dm){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		int userId = TeeStringUtil.getInteger(requestData.get("userId"), 0);
		String createTimeStrMin = (String) requestData.get("createTimeStrMin");
		String createTimeStrMax = (String) requestData.get("createTimeStrMax");
		
		//计划
		String scheduleQuery = "";
		if (!TeeUtility.isNullorEmpty(createTimeStrMin)) {
			scheduleQuery += " and schedule.crTime >= '" + createTimeStrMin + " 00:00'";
		}
		if (!TeeUtility.isNullorEmpty(createTimeStrMax)) {
			scheduleQuery += " and schedule.crTime <= '" + createTimeStrMax + " 23:59'";
		}
		
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List<Map> modelList = new ArrayList();
		
		String select = "select "
				+ "schedule.uuid as uuid,"
				+ "schedule.title as title,"
				+ "schedule.type as type,"
				+ "schedule.rangeType as rangeType,"
				+ "schedule.finishTime as finishTime,"
				+ "schedule.crTime as crTime,"
				+ "schedule.flag as flag ";
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from TeeSchedule schedule where schedule.user.uuid=? " + scheduleQuery);
		
		modelList = simpleDaoSupport.getMaps(select+hql.toString()+" order by schedule.crTime desc",new Object[]{userId},dm.getFirstResult(),dm.getRows());
		for(Map data:modelList){
			data.put("timeRangeDesc", TeeScheduleService.getRangeTypeInfo(Integer.parseInt(data.get("rangeType").toString()), (Calendar)data.get("crTime")).getRangeDesc());
			Calendar crTime =  (Calendar) data.get("crTime");
			
			data.put("crTime", sdf.format(crTime.getTime()));
		}
		
		long total = simpleDaoSupport.count("select count(schedule.uuid) "+hql.toString(), new Object[]{userId});
		
		
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	public TeeEasyuiDataGridJson taskList(Map requestData,TeeDataGridModel dm){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		int userId = TeeStringUtil.getInteger(requestData.get("userId"), 0);
		String createTimeStrMin = (String) requestData.get("createTimeStrMin");
		String createTimeStrMax = (String) requestData.get("createTimeStrMax");
		
		//任务
		String taskQuery = "";
		if (!TeeUtility.isNullorEmpty(createTimeStrMin)) {
			taskQuery += " and task.createTime >= '" + createTimeStrMin + " 00:00'";
		}
		if (!TeeUtility.isNullorEmpty(createTimeStrMax)) {
			taskQuery += " and task.createTime <= '" + createTimeStrMax + " 23:59'";
		}
		
		
		
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List<Map> modelList = new ArrayList();
		
		String select = "select "
				+ "task.sid as sid,"
				+ "task.taskTitle as taskTitle,"
				+ "task.rangeTimes as rangeTimes,"
				+ "task.relTimes as relTimes,"
				+ "task.status as status,"
				+ "task.progress as progress,"
				+ "task.score as score, "
				+ "task.createTime as createTime "
				+ "";
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from TeeCoWorkTask task where task.createUser.uuid=?" + taskQuery);
		
		modelList = simpleDaoSupport.getMaps(select+hql.toString()+" order by task.createTime desc",new Object[]{userId},dm.getFirstResult(),dm.getRows());
		for(Map map:modelList){
			Calendar createTime = (Calendar) map.get("createTime");
			map.put("createTime", sdf.format(createTime.getTime()));
		}
		
		
		long total = simpleDaoSupport.count("select count(task.sid) "+hql.toString(), new Object[]{userId});
		
		
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	public TeeEasyuiDataGridJson customerList(Map requestData,TeeDataGridModel dm){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		int userId = TeeStringUtil.getInteger(requestData.get("userId"), 0);
		String createTimeStrMin = (String) requestData.get("createTimeStrMin");
		String createTimeStrMax = (String) requestData.get("createTimeStrMax");
		
		//客户
		String customerQuery = "";
		if (!TeeUtility.isNullorEmpty(createTimeStrMin)) {
			customerQuery += " and customer.addTime >= '" + createTimeStrMin + " 00:00'";
		}
		if (!TeeUtility.isNullorEmpty(createTimeStrMax)) {
			customerQuery += " and customer.addTime <= '" + createTimeStrMax + " 23:59'";
		}
		
		
		
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List<Map> modelList = new ArrayList();
		
		String select = "select "
				+ "customer.sid as sid,"
				+ "customer.customerName as customerName,"
				+ "customer.companyAddress as companyAddress,"
				+ "customer.companyFax as companyFax,"
				+ "customer.companyUrl as companyUrl,"
				+ "customer.companyPhone as companyPhone,"
				+ "customer.companyMobile as companyMobile,"
				+ "customer.companyEmail as companyEmail,"
				+ "customer.companyQQ as companyQQ, "
				+ "customer.addTime as addTime "
				+ "";
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from TeeCrmCustomerInfo customer where customer.managePerson.uuid=?" + customerQuery);
		
		modelList = simpleDaoSupport.getMaps(select+hql.toString()+" order by customer.sid desc",new Object[]{userId},dm.getFirstResult(),dm.getRows());
		
		for(Map map:modelList){
			Calendar createTime = (Calendar) map.get("addTime");
			map.put("addTime",sdf.format(createTime.getTime()));
		}
		
		
		long total = simpleDaoSupport.count("select count(customer.sid) "+hql.toString(), new Object[]{userId});
		
		
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	public TeeEasyuiDataGridJson runList(Map requestData,TeeDataGridModel dm){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		int userId = TeeStringUtil.getInteger(requestData.get("userId"), 0);
		String createTimeStrMin = (String) requestData.get("createTimeStrMin");
		String createTimeStrMax = (String) requestData.get("createTimeStrMax");
		
		//工作
		String frQuery = "";
		if (!TeeUtility.isNullorEmpty(createTimeStrMin)) {
			frQuery += " and fr.beginTime >= '" + createTimeStrMin + " 00:00'";
		}
		if (!TeeUtility.isNullorEmpty(createTimeStrMax)) {
			frQuery += " and fr.beginTime <= '" + createTimeStrMax + " 23:59'";
		}
		
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List<Map> modelList = new ArrayList();
		
		String select = "select "
				+ "fr.runId as runId,"
				+ "fr.runName as runName,"
				+ "fr.beginTime as beginTime,"
				+ "fr.endTime as endTime "
				+ "";
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from TeeFlowRun fr where fr.beginPerson.uuid=?" + frQuery);
		
		modelList = simpleDaoSupport.getMaps(select+hql.toString()+" order by fr.runId desc",new Object[]{userId},dm.getFirstResult(),dm.getRows());
		long total = simpleDaoSupport.count("select count(fr.runId) "+hql.toString(), new Object[]{userId});
		
		
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	public TeeEasyuiDataGridJson diaryList(Map requestData,TeeDataGridModel dm){
		int userId = TeeStringUtil.getInteger(requestData.get("userId"), 0);
		String createTimeStrMin = (String) requestData.get("createTimeStrMin");
		String createTimeStrMax = (String) requestData.get("createTimeStrMax");
		
		//日志
		String diaryQuery = "";
		if (!TeeUtility.isNullorEmpty(createTimeStrMin)) {
			diaryQuery += " and diary.createTime >= '" + createTimeStrMin + " 00:00'";
		}
		if (!TeeUtility.isNullorEmpty(createTimeStrMax)) {
			diaryQuery += " and diary.createTime <= '" + createTimeStrMax + " 23:59'";
		}
		
		
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List<Map> modelList = new ArrayList();
		
		String select = "select "
				+ "diary.sid as sid,"
				+ "diary.title as title,"
				+ "diary.writeTime as time,"
				+ "diary.type as type, "
				+ "diary.createTime as createTime "
				+ "";
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from TeeDiary diary where diary.createUser.uuid=?" + diaryQuery);
		
		modelList = simpleDaoSupport.getMaps(select+hql.toString()+" order by diary.sid desc",new Object[]{userId},dm.getFirstResult(),dm.getRows());
		long total = simpleDaoSupport.count("select count(diary.sid) "+hql.toString(), new Object[]{userId});
		
		
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	public TeeEasyuiDataGridJson mailList(Map requestData,TeeDataGridModel dm){
		int userId = TeeStringUtil.getInteger(requestData.get("userId"), 0);
		String createTimeStrMin = (String) requestData.get("createTimeStrMin");
		String createTimeStrMax = (String) requestData.get("createTimeStrMax");
		//邮件
		String mailQuery = "";
		if (!TeeUtility.isNullorEmpty(createTimeStrMin)) {
			mailQuery += " and mail.sendTime >= '" + createTimeStrMin + " 00:00'";
		}
		if (!TeeUtility.isNullorEmpty(createTimeStrMax)) {
			mailQuery += " and mail.sendTime <= '" + createTimeStrMax + " 23:59'";
		}
		
		
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List<Map> modelList = new ArrayList();
		
		String select = "select "
				+ "mail.sid as sid,"
				+ "mail.sendTime as time,"
				+ "mail.subject as subject"
				+ "";
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from TeeMailBody mail where mail.fromuser.uuid=?" + mailQuery);
		
		modelList = simpleDaoSupport.getMaps(select+hql.toString()+" order by mail.sid desc",new Object[]{userId},dm.getFirstResult(),dm.getRows());
		long total = simpleDaoSupport.count("select count(mail.sid) "+hql.toString(), new Object[]{userId});
		
		List<String> usersList = null;
		for(Map data:modelList){
			usersList = simpleDaoSupport.find("select toUser.userName as userName from TeeMail where mailBody.sid="+data.get("sid"), null);
			if(usersList.size()>5){
				usersList = usersList.subList(0, 5);
				usersList.add("……");
			}
			data.put("toUsers", usersList);
		}
		
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
}
