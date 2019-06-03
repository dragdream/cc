package com.tianee.oa.core.workflow.flowrun.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRelatedResource;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.model.TeeFlowRelatedResourceModel;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.cowork.bean.TeeCoWorkTask;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomer;
import com.tianee.oa.subsys.project.bean.TeeProject;
import com.tianee.oa.subsys.project.bean.TeeProjectType;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeFlowRelatedResourceService extends TeeBaseService implements TeeFlowRelatedResourceServiceInterface{

	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRelatedResourceServiceInterface#relatedFlowRunList(com.tianee.oa.webframe.httpModel.TeeDataGridModel, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeEasyuiDataGridJson relatedFlowRunList(TeeDataGridModel dm,
			HttpServletRequest request) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//获取当前登陆人
		TeePerson loginUser =(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取前台页面传来的参数
		//所属流程类型
		int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		//流程标题
		String runName=TeeStringUtil.getString(request.getParameter("runName"));
		//流程发起人
		int beginUserId=TeeStringUtil.getInteger(request.getParameter("beginUserId"), 0);
		//流水号
		int runId=TeeStringUtil.getInteger(request.getParameter("runId"),0);
		//start1  start2
		String start1=TeeStringUtil.getString(request.getParameter("start1"));
		String start2=TeeStringUtil.getString(request.getParameter("start2"));
		
        TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List param=new ArrayList();
		String hql = "from TeeFlowRun fr where fr.delFlag=0 and fr.isSave=1 and (fr.beginPerson.uuid=? or (exists (select 1 from TeeFlowRunPrcs frp where frp.prcsUser.uuid=? and frp.flowRun.runId=fr.runId))) ";
		param.add(loginUser.getUuid());
		param.add(loginUser.getUuid());
		
		if(flowId!=0){
			hql+=" and fr.flowType.sid=? ";
			param.add(flowId);
		}
		
		if(!TeeUtility.isNullorEmpty(runName)){
			hql+=" and fr.runName like ? ";
			param.add("%"+runName+"%");
		}
		
		if(beginUserId!=0){
			hql+=" and fr.beginPerson.uuid=? ";
			param.add(beginUserId);
		}
		
		if(runId!=0){
			hql+=" and fr.runId=? ";
			param.add(runId);
		}
		
		if(!TeeUtility.isNullorEmpty(start1)){
			String s=start1+" 00:00:00";	
			Calendar c1 = Calendar. getInstance(); 
			Date sDate=null;
			try {
				sDate = sdf1.parse(s);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c1.setTime(sDate);
			
			
			hql+=" and fr.beginTime >= ?  ";
			param.add(c1);
		}
		
		
		if(!TeeUtility.isNullorEmpty(start2)){
			String e=start2+" 23:59:59";	
			Calendar c2 = Calendar. getInstance(); 
			Date eDate=null;
			try {
				eDate = sdf1.parse(e);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			c2.setTime(eDate);
			
			
			hql+=" and fr.beginTime <= ?  ";
			param.add(c2);
		}
		
		List<TeeFlowRun> list = simpleDaoSupport.pageFind("select fr "+hql+"order by fr.runId desc", dm.getRows()*(dm.getPage()-1), dm.getRows(), param.toArray());
		long total = simpleDaoSupport.count("select count(fr) "+hql, param.toArray());
		List<Map> models = new ArrayList<Map>();
		Map map = null;
		for(TeeFlowRun run:list){
			map=new HashMap();
			map.put("runId",run.getRunId());
			map.put("runName", run.getRunName());
			if(run.getBeginPerson()!=null){
				map.put("beginUser",run.getBeginPerson().getUserName());
			}else{
				map.put("beginUser","");
			}
			
			if(run.getBeginTime()!=null){
				map.put("beginTime",sdf.format(run.getBeginTime().getTime()));
			}else{
				map.put("beginTime","");
			}	
			
			models.add(map);
		}
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(models);
		
		return dataGridJson;
	}

	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRelatedResourceServiceInterface#add(com.tianee.oa.webframe.httpModel.TeeDataGridModel, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson add(TeeDataGridModel dm, HttpServletRequest request) {
		
		TeeJson json=new TeeJson();
		//流程
		int runId=TeeStringUtil.getInteger(request.getParameter("runId"),0);
		TeeFlowRun flowRun=(TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class,runId);
		//获取当前登陆人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取类型
		int type=TeeStringUtil.getInteger(request.getParameter("type"),0);
		String relatedIds=TeeStringUtil.getString(request.getParameter("relatedIds"));
		String [] relatedIdArray=relatedIds.split(",");
		TeeFlowRelatedResource resource=null;
		if(relatedIdArray!=null&&relatedIdArray.length>0){
			for (String relatedId : relatedIdArray) {
				//先判断  是否已经关联过
				long tatal=simpleDaoSupport.count(" select count(*) from TeeFlowRelatedResource where relatedId=? and type=? and flowRun.runId=? ", new Object[]{relatedId,type,runId});
				if(tatal==0){
					resource=new TeeFlowRelatedResource();
					resource.setCreateTime(Calendar.getInstance());
					resource.setCreateUser(loginUser);
					resource.setFlowRun(flowRun);
					resource.setRelatedId(relatedId);
					resource.setType(type);
					simpleDaoSupport.save(resource);
				}	
			}
		}
		
		json.setRtState(true);
        json.setRtMsg("添加成功！");		
		return json;
	}



	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRelatedResourceServiceInterface#getRelatedResourceByRunId(com.tianee.oa.webframe.httpModel.TeeDataGridModel, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson getRelatedResourceByRunId(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取流程主键
		int runId=TeeStringUtil.getInteger(request.getParameter("runId"),0);
		//获取type
		int type=TeeStringUtil.getInteger(request.getParameter("type"),0);
		
		List<TeeFlowRelatedResource> list=simpleDaoSupport.executeQuery(" from TeeFlowRelatedResource where type=? and flowRun.runId=? ", new Object[]{type,runId});
		
		List<TeeFlowRelatedResourceModel> modelList=new ArrayList();
		if(list!=null&&list.size()>0){
			TeeFlowRelatedResourceModel model=null;
			for (TeeFlowRelatedResource r : list) {
				model=parseToModel(r);
				modelList.add(model);
			}
		}
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}



	/**
	 * 实体类转换成model
	 * @param r
	 * @return
	 */
	private TeeFlowRelatedResourceModel parseToModel(TeeFlowRelatedResource r) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeFlowRelatedResourceModel model=new TeeFlowRelatedResourceModel();
		BeanUtils.copyProperties(r, model);
		if(r.getCreateTime()!=null){
			model.setCreateTimeStr(sdf.format(r.getCreateTime().getTime()));
		}
		if(r.getCreateUser()!=null){
			model.setCreateUserId(r.getCreateUser().getUuid());
			model.setCreateUserName(r.getCreateUser().getUserName());
		}
		if(r.getFlowRun()!=null){
			model.setRunId(r.getFlowRun().getRunId());
			model.setRunName(r.getFlowRun().getRunName());
		}
		
		if(r.getType()==1){//相关流程
			TeeFlowRun flowRun=(TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class,TeeStringUtil.getInteger(r.getRelatedId(), 0));
		    if(flowRun!=null){
		    	model.setRelatedId(flowRun.getRunId()+"");
		    	model.setRelatedName(flowRun.getRunName());
		    }else{
		    	model.setRelatedId("0");
		    	model.setRelatedName("");
		    }
		}else if(r.getType()==2){//相关任务
			TeeCoWorkTask task=(TeeCoWorkTask) simpleDaoSupport.get(TeeCoWorkTask.class,TeeStringUtil.getInteger(r.getRelatedId(), 0));
			if(task!=null){
				model.setRelatedId(task.getSid()+"");
				model.setRelatedName(task.getTaskTitle());
			}else{
				model.setRelatedId("0");
		    	model.setRelatedName("");
			}
		}else if(r.getType()==3){//相关客户
			TeeCrmCustomer customer=(TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class,TeeStringUtil.getInteger(r.getRelatedId(), 0));
		    if(customer!=null){
		    	model.setRelatedId(customer.getSid()+"");
				model.setRelatedName(customer.getCustomerName());
		    }else{
		    	model.setRelatedId("0");
		    	model.setRelatedName("");
		    }
		}else if(r.getType()==4){//相关项目
			TeeProject project=(TeeProject) simpleDaoSupport.get(TeeProject.class,TeeStringUtil.getString(r.getRelatedId()));
			if(project!=null){
				model.setRelatedId(project.getUuid());
				model.setRelatedName(project.getProjectName());
			}else{
				model.setRelatedId("0");
		    	model.setRelatedName("");
			}
		}
		return model;
	}



	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRelatedResourceServiceInterface#relatedTaskList(com.tianee.oa.webframe.httpModel.TeeDataGridModel, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeEasyuiDataGridJson relatedTaskList(TeeDataGridModel dm,
			HttpServletRequest request) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//获取当前登陆人
		TeePerson loginUser =(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
        TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
        //获取前台传来的参数
        String taskTitle=TeeStringUtil.getString(request.getParameter("taskTitle"));
        int createUserId=TeeStringUtil.getInteger(request.getParameter("createUserId"), 0);
        String start1=TeeStringUtil.getString(request.getParameter("start1"));
		String start2=TeeStringUtil.getString(request.getParameter("start2"));
        
        List param=new ArrayList();
		
		String hql = "from TeeCoWorkTask t where (createUser.uuid=? or charger.uuid=? or exists(select 1 from t.joiners j where  j.uuid=? )) ";
		param.add(loginUser.getUuid());
		param.add(loginUser.getUuid());
		param.add(loginUser.getUuid());
		
		
		if(!TeeUtility.isNullorEmpty(taskTitle)){
			hql+=" and t.taskTitle like ? ";
			param.add("%"+taskTitle+"%");
		}
		

		if(createUserId!=0){
			hql+=" and t.createUser.uuid=?  ";
			param.add(createUserId);
		}
		
		if(!TeeUtility.isNullorEmpty(start1)){
			String s=start1+" 00:00:00";	
			Calendar c1 = Calendar. getInstance(); 
			Date sDate=null;
			try {
				sDate = sdf1.parse(s);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c1.setTime(sDate);
			
			
			hql+=" and t.createTime >= ?  ";
			param.add(c1);
		}
		
		
		if(!TeeUtility.isNullorEmpty(start2)){
			String e=start2+" 23:59:59";	
			Calendar c2 = Calendar. getInstance(); 
			Date eDate=null;
			try {
				eDate = sdf1.parse(e);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			c2.setTime(eDate);
			
			
			hql+=" and t.createTime <= ?  ";
			param.add(c2);
		}
		
		
		List<TeeCoWorkTask> list = simpleDaoSupport.pageFind("select t "+hql+"order by t.sid desc", dm.getRows()*(dm.getPage()-1), dm.getRows(), param.toArray());
		long total = simpleDaoSupport.count("select count(t) "+hql, param.toArray());
		List<Map> models = new ArrayList<Map>();
		Map map = null;
		for (TeeCoWorkTask cWork : list) {
			map=new HashMap();
			map.put("taskTitle", cWork.getTaskTitle());
			map.put("sid", cWork.getSid());
			if(cWork.getCreateUser()!=null){
				map.put("createUserName", cWork.getCreateUser().getUserName());
			}else{
				map.put("createUserName","");
			}
			
			if(cWork.getCreateTime()!=null){
				map.put("createTime",sdf.format(cWork.getCreateTime().getTime()) );
			}else{
				map.put("createTime","");
			}
			
			models.add(map);
		}
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(models);
		
		return dataGridJson;
	}



	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRelatedResourceServiceInterface#relatedCustomerList(com.tianee.oa.webframe.httpModel.TeeDataGridModel, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeEasyuiDataGridJson relatedCustomerList(TeeDataGridModel dm,
			HttpServletRequest request) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//获取当前登陆人
		TeePerson loginUser =(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		//获取页面上传来的参数
        String customerName=TeeStringUtil.getString(request.getParameter("customerName"));
        String customerNum=TeeStringUtil.getString(request.getParameter("customerNum"));
        int type=TeeStringUtil.getInteger(request.getParameter("type"), 0);
        int  managerPersonId=TeeStringUtil.getInteger(request.getParameter("managerPersonId"), 0);
        String province=TeeStringUtil.getString(request.getParameter("province"));
        String district=TeeStringUtil.getString(request.getParameter("district"));
        String city=TeeStringUtil.getString(request.getParameter("city"));
        List param=new ArrayList();
		
		String hql = "from TeeCrmCustomer t where (t.managePerson.uuid=? or t.managePerson.leader.uuid=? or t.managePerson is null ) ";
		param.add(loginUser.getUuid());
		param.add(loginUser.getUuid());
		
		
		if(!TeeUtility.isNullorEmpty(customerName)){
			hql+=" and t.customerName like ? ";
			param.add("%"+customerName+"%");
		}
		
		if(!TeeUtility.isNullorEmpty(customerNum)){
			hql+=" and t.customerNum like ? ";
			param.add("%"+customerNum+"%");
		}
		
		if(type!=0){
			hql+=" and t.type= ?  ";
			param.add(type);
		}
		
		if(managerPersonId!=0){
			hql+=" and t.managePerson.uuid=? ";
			param.add(managerPersonId);
		}
		
        if(!TeeUtility.isNullorEmpty(province)){
        	hql+=" and t.province=? ";
			param.add(province);
        }
        
        if(!TeeUtility.isNullorEmpty(district)&&!("null").equals(district)){
        	hql+=" and t.district=? ";
			param.add(district);
        }
        
        if(!TeeUtility.isNullorEmpty(city)){
        	hql+=" and t.city=? ";
			param.add(city);
        }
		
		List<TeeCrmCustomer> list = simpleDaoSupport.pageFind("select t "+hql+"order by t.sid desc", dm.getRows()*(dm.getPage()-1), dm.getRows(), param.toArray());
		long total = simpleDaoSupport.count("select count(t) "+hql, param.toArray());
		List<Map> models = new ArrayList<Map>();
		Map map = null;
		for (TeeCrmCustomer c : list) {
			map=new HashMap();
			map.put("customerName",c.getCustomerName() );
			map.put("customerNum", c.getCustomerNum());
			if(c.getType()==1){
				map.put("typeDesc","客户" );
			}else{
				map.put("typeDesc","供应商" );
			}
			
			map.put("sid", c.getSid());
			if(c.getManagePerson()!=null){
				map.put("managerPerson", c.getManagePerson().getUserName());
			}else{
				map.put("managerPerson","");
			}
			
			map.put("province", c.getProvince());
			map.put("city", c.getCity());
			map.put("district", c.getDistrict());
			
			models.add(map);
		}
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(models);
		
		return dataGridJson;
	}



	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRelatedResourceServiceInterface#relatedProjectList(com.tianee.oa.webframe.httpModel.TeeDataGridModel, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeEasyuiDataGridJson relatedProjectList(TeeDataGridModel dm,
			HttpServletRequest request) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//获取当前登陆人
		TeePerson loginUser =(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
        
        //获取前台页面传来的参数
        String projectName=TeeStringUtil.getString(request.getParameter("projectName"));
        int projectTypeId=TeeStringUtil.getInteger(request.getParameter("projectTypeId"), 0);
        String projectLevel=TeeStringUtil.getString(request.getParameter("projectLevel"));
        int managerPersonId=TeeStringUtil.getInteger(request.getParameter("managerPersonId"),0);
        
        List param=new ArrayList();
		
		
		String sql=" from project p where (p.project_creater_id=? or p.project_manager_id=? or (exists(select 1 from project_member m where m.project_id=p.uuid and m.member_id=?)) or (exists(select 1 from project_view v where v.project_id=p.uuid and v.view_id=?)) or (exists(select 1 from project_share s where s.project_id=p.uuid and s.share_id=?)) ) ";
		param.add(loginUser.getUuid());
		param.add(loginUser.getUuid());
		param.add(loginUser.getUuid());
		param.add(loginUser.getUuid());
		param.add(loginUser.getUuid());
		
		
		if(!TeeUtility.isNullorEmpty(projectName)){
			sql+=" and p.project_name like ?   ";
			param.add("%"+projectName+"%");
		}
		if(!TeeUtility.isNullorEmpty(projectLevel)){
			sql+=" and p.project_level = ?   ";
			param.add(projectLevel);
		}
		
		if(projectTypeId!=0){
			sql+=" and p.project_type_id = ?   ";
			param.add(projectTypeId);
		}
		
		
		if(managerPersonId!=0){
			sql+=" and p.project_manager_id = ?   ";
			param.add(managerPersonId);
		}
		List<Map> list = simpleDaoSupport.executeNativeQuery("select p.* "+sql+"order by p.uuid desc", param.toArray() ,dm.getRows()*(dm.getPage()-1), dm.getRows());
		long total = simpleDaoSupport.countSQLByList("select count(p.uuid) "+sql, param);
		List<Map> models = new ArrayList<Map>();
		Map map = null;
		TeePerson manager=null;
		TeeProjectType type=null;
		for (Map m : list) {
			map=new HashMap();
			map.put("projectName", m.get("PROJECT_NAME"));
			map.put("projectLevel", m.get("PROJECT_LEVEL") );
			
			int managerId=TeeStringUtil.getInteger(m.get("PROJECT_MANAGER_ID"), 0) ;
			manager=(TeePerson) simpleDaoSupport.get(TeePerson.class,managerId);
			if(manager!=null){
				map.put("managerName", manager.getUserName());
			}else{
				map.put("managerName", "");
			}
			
			int typeId=TeeStringUtil.getInteger(m.get("PROJECT_TYPE_ID"), 0);
			type=(TeeProjectType) simpleDaoSupport.get(TeeProjectType.class,typeId);
			if(type!=null){
				map.put("typeName", type.getTypeName());
			}else{
				map.put("typeName", "");
			}
			
			
			map.put("uuid", m.get("UUID"));
			models.add(map);
		}
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(models);
		
		return dataGridJson;
	}



	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRelatedResourceServiceInterface#delBySid(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson delBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取前台页面传来的主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeFlowRelatedResource r=(TeeFlowRelatedResource) simpleDaoSupport.get(TeeFlowRelatedResource.class,sid);
		if(r!=null){
			simpleDaoSupport.deleteByObj(r);
			json.setRtState(true);
			json.setRtMsg("删除成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("信息获取失败！");
		}
		return json;
	}

	
}
