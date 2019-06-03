package com.tianee.oa.subsys.project.service;

import java.io.Serializable;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.fileNetdisk.service.TeeFileNetdiskService;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.project.bean.TeeProject;
import com.tianee.oa.subsys.project.bean.TeeProjectFlow;
import com.tianee.oa.subsys.project.bean.TeeTask;
import com.tianee.oa.subsys.project.bean.TeeTaskProjectTypeFile;
import com.tianee.oa.subsys.project.model.TeeTaskModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
@Service
public class TeeTaskService extends TeeBaseService {

	@Autowired
	private TeeSmsManager smsManager;
	
	@Autowired
	private TeeProjectService projectService;
	
	@Autowired
	private TeeFileNetdiskService fileNetdiskService;
	
	
	
	/**
	 * 根据项目主键 获取项目下除自己以外的其他的任务列表
	 * @param request
	 * @return
	 */
	public TeeJson getOtherTasksByProjectId(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上出来的项目主键  和  任务主键
		String projectId=TeeStringUtil.getString(request.getParameter("projectId"));
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		List<TeeTask> list=simpleDaoSupport.executeQuery(" from TeeTask task where task.project.uuid=? and task.sid <> ? ", new Object[]{projectId,sid});
		List<Map> l=new ArrayList<Map>();
		Map m=null;
		for (TeeTask task : list) {
			m=new HashMap();
			m.put("sid", task.getSid());
			m.put("taskName", task.getTaskName());
			l.add(m);
		}
		json.setRtState(true);
		json.setRtData(l);
		return json;
	}

	
	/**
	 * 新增 /编辑
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeTaskModel model) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
	    //获取任务主键
		int sid=model.getSid();
		if(sid>0){//编辑
			TeeTask task=(TeeTask) simpleDaoSupport.get(TeeTask.class,sid);
			model.setStatus(task.getStatus());
			model.setProgress(task.getProgress());
			BeanUtils.copyProperties(model, task);
			//项目负责人
			if(model.getManagerId()>0){
				TeePerson manager=(TeePerson) simpleDaoSupport.get(TeePerson.class,model.getManagerId());
				task.setManager(manager);
			}
			//上级任务
			if(model.getHigherTaskId()>0){
				TeeTask higher=(TeeTask) simpleDaoSupport.get(TeeTask.class,model.getHigherTaskId());
				task.setHigherTask(higher);
				//higher.setNextTask(task);
				//simpleDaoSupport.update(higher);
			}
			//前置任务
			if(model.getPreTaskId()>0){
				TeeTask pre=(TeeTask) simpleDaoSupport.get(TeeTask.class,model.getPreTaskId());
				task.setPreTask(pre);
			}
			try {
				task.setBeginTime(sdf.parse(model.getBeginTimeStr()));
				task.setEndTime(sdf.parse(model.getEndTimeStr()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			task.setCreater(loginUser);
			//task.setCreateTime(new Date());
			simpleDaoSupport.update(task);
			TeeTask higherTask = task.getHigherTask();
			if(higherTask!=null){
				//上级文件夹
				Map map = simpleDaoSupport.executeNativeUnique("select SID as sid,FILE_FULL_PATH as fileFullPath from FILE_NETDISK where SID = (select FILE_NETDISK_ID as fileId  from task_projectype_file where TASK_ID=?)", new Object[]{higherTask.getSid()});
				//文件夹
				Map map2 = simpleDaoSupport.executeNativeUnique("select FILE_NETDISK_ID as fileId  from task_projectype_file where TASK_ID=?", new Object[]{task.getSid()});
			    //上级文件夹的全路径
				String fileFullPath=TeeStringUtil.getString(map.get("fileFullPath"));
				//当前任务的文件夹id
				int fileId=TeeStringUtil.getInteger(map2.get("fileId"),0);
				//当前任务父上级任务对应的文件夹id
				int parentId=TeeStringUtil.getInteger(map.get("sid"), 0);
				simpleDaoSupport.executeNativeUpdate("update FILE_NETDISK set FILE_FULL_PATH=?,FILE_NAME=?,FILE_PARENT=? where SID=?", new Object[]{fileFullPath+fileId+"/",task.getTaskName(),parentId,fileId});
			}else{
				//根目录文件夹的id
				String fileId2 = task.getProject().getProjectFileNetdiskIds();
				TeeFileNetdisk filedisk = (TeeFileNetdisk)simpleDaoSupport.get(TeeFileNetdisk.class,TeeStringUtil.getInteger(fileId2, 0));
				//获取当前任务对应的文件夹id
				Map map2 = simpleDaoSupport.executeNativeUnique("select FILE_NETDISK_ID as fileId  from task_projectype_file where TASK_ID=?", new Object[]{task.getSid()});
				int fileId=TeeStringUtil.getInteger(map2.get("fileId"),0);
				simpleDaoSupport.executeNativeUpdate("update FILE_NETDISK set FILE_FULL_PATH=?,FILE_NAME=?,FILE_PARENT=? where SID=?", new Object[]{filedisk.getFileFullPath()+fileId+"/",task.getTaskName(),filedisk.getSid(),fileId});
			}

			json.setRtState(true);
			json.setRtMsg("编辑成功！");
		}else{//新增
			TeeTask  task=new TeeTask();
			BeanUtils.copyProperties(model, task);
			//项目
			if(!("").equals(model.getProjectId())){
				TeeProject project=(TeeProject) simpleDaoSupport.get(TeeProject.class,model.getProjectId());
				task.setProject(project);
			}
			//项目负责人
			if(model.getManagerId()>0){
				TeePerson manager=(TeePerson) simpleDaoSupport.get(TeePerson.class,model.getManagerId());
				task.setManager(manager);
			}
			//上级任务
			if(model.getHigherTaskId()>0){
				TeeTask higher=(TeeTask) simpleDaoSupport.get(TeeTask.class,model.getHigherTaskId());
				task.setHigherTask(higher);
			}
			//前置任务
			if(model.getPreTaskId()>0){
				TeeTask pre=(TeeTask) simpleDaoSupport.get(TeeTask.class,model.getPreTaskId());
				task.setPreTask(pre);
			}
			try {
				task.setBeginTime(sdf.parse(model.getBeginTimeStr()));
				task.setEndTime(sdf.parse(model.getEndTimeStr()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			task.setCreater(loginUser);
			task.setStatus(2);//未开始
			task.setProgress(0);
			task.setCreateTime(new Date());
			Serializable newTaskId=simpleDaoSupport.save(task);
			//上级任务
			TeeTask higherTask=task.getHigherTask();
			//此任务对应的文件夹的id
			Serializable netFile=0;
			//判断当前任务是否有上级任务
			if(higherTask!=null){
				//higherTask.setNextTask(task);
				//simpleDaoSupport.update(higherTask);
				Map map = simpleDaoSupport.executeNativeUnique("select FILE_NETDISK_ID as fileId  from task_projectype_file where TASK_ID=?", new Object[]{higherTask.getSid()});
			    //创建文件夹
				netFile = fileNetdiskService.newFileFolderById2(TeeStringUtil.getInteger(map.get("fileId"),0), task.getTaskName(), loginUser);
			}else{
				//根目录文件的id
				int fileId2 = TeeStringUtil.getInteger(task.getProject().getProjectFileNetdiskIds(),0);
				//创建文件夹
				netFile=fileNetdiskService.newFileFolderById2(fileId2, task.getTaskName(), loginUser);
			}
			//文件夹和任务的中间表
			TeeTaskProjectTypeFile pt=new TeeTaskProjectTypeFile();
			//文件夹的id
			pt.setFileId(TeeStringUtil.getInteger(TeeStringUtil.getString(netFile), 0));
			//任务id
			pt.setTaskId(TeeStringUtil.getInteger(TeeStringUtil.getString(newTaskId), 0));
			//项目类型id
			pt.setProjectId(task.getProject().getUuid());
			simpleDaoSupport.save(pt);
			// 发送消息
			Map requestData1 = new HashMap();
			requestData1.put("content", loginUser.getUserName()+"在项目“"+task.getProject().getProjectName()+"”中创建了一条您负责的任务“"+task.getTaskName()+"”，请及时办理。");
			requestData1.put("userListIds", task.getManager().getUuid());
			requestData1.put("moduleNo", "060");
			requestData1.put("remindUrl","/system/subsys/project/taskdetail/index.jsp?sid="+task.getSid());
			smsManager.sendSms(requestData1, loginUser);
			
			
			json.setRtState(true);
			json.setRtMsg("添加成功！");
		}
		return json;
	}

	/**
	 * 根据项目主键  获取任务列表
	 * @param request
	 * @param dm
	 * @return
	 */
	public List getTaskListByProjectId(
			HttpServletRequest request, TeeDataGridModel dm) {
		TeePerson loginUser =(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		//获取页面上传来的项目的主键
		String projectId=TeeStringUtil.getString(request.getParameter("projectId"));
		
		List<TeeTask> taskList = simpleDaoSupport.executeQuery(" from TeeTask t where t.project.uuid=? order by t.higherTask asc, t.taskNo asc ",new Object[]{projectId});
		List<Map> taskMapList = new ArrayList();
		
		//找出所有一级节点
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		for (TeeTask task : taskList) {
			if(task.getHigherTask()==null){
				Map map=new HashMap();
				map.put("sid", task.getSid());
				map.put("taskNo", task.getTaskNo());
				map.put("taskName", task.getTaskName());
				if(task.getBeginTime()!=null){
					map.put("beginTimeStr", sdf.format(task.getBeginTime()));
				}
				if(task.getEndTime()!=null){
					map.put("endTimeStr", sdf.format(task.getEndTime()));
				}
				if(task.getRealBeginTime()!=null){
					map.put("realBeginTimeStr", sdf.format(task.getRealBeginTime()));
				}else{
					map.put("realBeginTimeStr", "未开始");
				}
				
				if(task.getRealEndTime()!=null){
					map.put("realEndTimeStr", sdf.format(task.getRealEndTime()));
				}else{
					map.put("realEndTimeStr", "未结束");
				}
				
				if(task.getStatus()==2){//未开始
					map.put("status", "未开始");
				}else if(task.getStatus()==0){//进行中
				     long diff=new Date().getTime()-task.getEndTime().getTime();
				     if(diff>0){
				    	 map.put("status", "超时未完成（超时"+(diff/1000/60/60/24)+"天）");
				     }else{
				    	 map.put("status", "进行中");
				     }
				}else if(task.getStatus()==1){//已完成
					if(task.getEndTime()!=null&&task.getRealEndTime()!=null){
						long diff=task.getRealEndTime().getTime()-task.getEndTime().getTime();
					     if(diff>0){
					    	 map.put("status", "超时完成（超时"+(diff/1000/60/60/24)+"天）");
					     }else{
					    	 map.put("status", "已完成");
					     }
					}
				     
				}
				
                
				map.put("progress", task.getProgress());
				map.put("days", task.getDays());
				map.put("children", new ArrayList());
				if(task.getManager()!=null){
					map.put("managerName", task.getManager().getUserName());
					
				}
				
				if(task.getCreater()!=null || task.getProject()!=null){
					if(task.getCreater().getUuid()==loginUser.getUuid() || task.getProject().getProjectManageId()==loginUser.getUuid()){//判断当前登录人是否是任务创建人
						map.put("isCreater", 1);
					}else{
						map.put("isCreater", 0);
					}
				}
				taskMapList.add(map);
			}
		}
		//从一级节点开始往下找
		for(Map data:taskMapList){
			setChildInfos(taskList,taskMapList,data,loginUser);
		}
		
		//json.setRows(taskMapList);
		return taskMapList;
	}
	
	private void setChildInfos(List<TeeTask> taskList,List<Map> taskMapList,Map taskMap,TeePerson loginUser){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//先获取该节点下面的所有子节点
		List<Map> childList = new ArrayList();
		//将taskMap的所有子节点加入到childList中
		for(TeeTask task:taskList){
			if(task.getHigherTask()!=null){	
				if(task.getHigherTask().getSid()==Integer.parseInt(taskMap.get("sid")+"")){
					Map map=new HashMap();
					map.put("sid", task.getSid());
					map.put("taskNo", task.getTaskNo());
					map.put("taskName", task.getTaskName());
					if(task.getBeginTime()!=null){
						map.put("beginTimeStr", sdf.format(task.getBeginTime()));
					}
					if(task.getEndTime()!=null){
						map.put("endTimeStr", sdf.format(task.getEndTime()));
					}
					
					if(task.getRealBeginTime()!=null){
						map.put("realBeginTimeStr", sdf.format(task.getRealBeginTime()));
					}else{
						map.put("realBeginTimeStr", "未开始");
					}
					
					if(task.getRealEndTime()!=null){
						map.put("realEndTimeStr", sdf.format(task.getRealEndTime()));
					}else{
						map.put("realEndTimeStr", "未结束");
					}
					if(task.getStatus()==2){//未开始
						map.put("status", "未开始");
					}else if(task.getStatus()==0){//进行中
					     long diff=new Date().getTime()-task.getEndTime().getTime();
					     if(diff>0){
					    	 map.put("status", "超时未完成（超时"+(diff/1000/60/60/24)+"天）");
					     }else{
					    	 map.put("status", "进行中");
					     }
					}else if(task.getStatus()==1){//已完成
						if(task.getRealEndTime()!=null&&task.getEndTime()!=null){
							 long diff=task.getRealEndTime().getTime()-task.getEndTime().getTime();
						     if(diff>0){
						    	 map.put("status", "超时完成（超时"+(diff/1000/60/60/24)+"天）");
						     }else{
						    	 map.put("status", "已完成");
						     }
							
						}
					    
					}
					map.put("progress", task.getProgress());
					map.put("days", task.getDays());
					map.put("children", new ArrayList());
					if(task.getManager()!=null){
						map.put("managerName", task.getManager().getUserName());
					}
					
					if(task.getCreater()!=null || task.getProject()!=null){
						if(task.getCreater().getUuid()==loginUser.getUuid() || task.getProject().getProjectManageId()==loginUser.getUuid()){//判断当前登录人是否是任务创建人
							map.put("isCreater", 1);
						}else{
							map.put("isCreater", 0);
						}
					}
					childList.add(map);
				}
				
			}
			
		}
		((List)taskMap.get("children")).addAll(childList);
		
		for(Map data:childList){
			setChildInfos(taskList,taskMapList,data,loginUser);
		}

	}


	
	/**
	 * 根据主键获取任务详情
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的任务的主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		//根据主键   获取任务对象
		TeeTask task=(TeeTask) simpleDaoSupport.get(TeeTask.class,sid);
		TeeTaskModel model=parseToModel(task);
		
		json.setRtData(model);
		json.setRtState(true);
		json.setRtMsg("数据获取成功！");
		return json;
	}
	
	
	public TeeTaskModel parseToModel(TeeTask task){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeTaskModel model=new TeeTaskModel();
		BeanUtils.copyProperties(task, model);
		//获取流程
		if(task.getFlowTypeIds()!=null && !("").equals(task.getFlowTypeIds())){
			String [] flowTypeIdArray=task.getFlowTypeIds().split(",");
			TeeFlowType flowType=null;
			String flowTypeNames="";
			for (String str : flowTypeIdArray) {
				int sid=Integer.parseInt(str);
				flowType=(TeeFlowType) simpleDaoSupport.get(TeeFlowType.class,sid);
				if(flowType!=null){
					flowTypeNames+=flowType.getFlowName()+",";
				}
			}
			
			if(flowTypeNames.endsWith(",")){
				flowTypeNames=flowTypeNames.substring(0, flowTypeNames.length()-1);
			}
			model.setFlowTypeIds(task.getFlowTypeIds());
			model.setFlowTypeNames(flowTypeNames);
		}
		//获取计划开始   计划结束时间
		if(task.getBeginTime()!=null){
			model.setBeginTimeStr(sdf.format(task.getBeginTime()));
		}
		if(task.getEndTime()!=null){
			model.setEndTimeStr(sdf.format(task.getEndTime()));
		}
		
		//获取实际开始    实际结束时间
		if(task.getRealBeginTime()!=null){
			model.setRealBeginTimeStr(sdf.format(task.getRealBeginTime()));
		}
		if(task.getRealEndTime()!=null){
			model.setRealEndTimeStr(sdf.format(task.getRealEndTime()));
		}
		
		//获取项目负责人
		if(task.getManager()!=null){
			model.setManagerId(task.getManager().getUuid());
			model.setManagerName(task.getManager().getUserName());
		}
		//获取项目创建人
		if(task.getCreater()!=null){
			model.setCreaterId(task.getCreater().getUuid());
			model.setCreaterName(task.getCreater().getUserName());
		}
		//获取创建时间
		if(task.getCreateTime()!=null){
			model.setCreateTimeStr(sdf.format(task.getCreateTime()));
		}
		//获取上级任务
		if(task.getHigherTask()!=null){
			model.setHigherTaskId(task.getHigherTask().getSid());
			model.setHigherTaskName(task.getHigherTask().getTaskName());
		}
		//获取前置任务
		if(task.getPreTask()!=null){
			model.setPreTaskId(task.getPreTask().getSid());
			model.setPreTaskName(task.getPreTask().getTaskName());
		}
		//获取项目相关的东西
		if(task.getProject()!=null){
			model.setProjectName(task.getProject().getProjectName());
			model.setProjectNo(task.getProject().getProjectNum());
			model.setProjectId(task.getProject().getUuid());
		}
		
		return model;
	}


	
	/**
	 * 根据任务主键  删除任务
	 * @param request
	 * @return
	 */
	public TeeJson delBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上 传来的任务的主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
			//删除项目流程表中的数据
			simpleDaoSupport.executeNativeUpdate("delete from project_flow where task_id=? ", new Object[]{sid});
			//删除项目问题表中的数据
			simpleDaoSupport.executeNativeUpdate("delete from project_question where task_id=? ", new Object[]{sid});
			//删除任务汇报中的数据
			simpleDaoSupport.executeNativeUpdate("delete from project_task_report where task_id=? ", new Object[]{sid});
			//删除任务表中的数据
			simpleDaoSupport.executeNativeUpdate("delete from project_task where sid=? ", new Object[]{sid});

			json.setRtState(true);
			json.setRtMsg("删除成功！");
			
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		return json;
	}


	/**
	 * 根据任务状态  获取我的任务的列表  0进行中  1已完成  2未开始
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getTaskListByStatus(
			HttpServletRequest request, TeeDataGridModel dm) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);
		String hql="";
		List param=new ArrayList();
		
		Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, 1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        String preMonth = dft.format(cal.getTime());
		
		if(status==0){
			hql=" from TeeTask task where task.manager=? and task.status in (0,2) and task.beginTime<'"+preMonth+"' and task.project.status=3 ";
			param.add(loginUser);
		}else if(status==1){
			hql=" from TeeTask task where task.manager=? and task.status=? and task.project.status=3 ";
			param.add(loginUser);
			param.add(status);
		}else{
			hql=" from TeeTask task where task.manager=? and task.status in (0,2) and task.beginTime>='"+preMonth+"' and task.project.status=3 ";
			param.add(loginUser);
		}
		long total=simpleDaoSupport.count(" select count(*) "+hql, param.toArray());
		json.setTotal(total);
		List<TeeTask> list=simpleDaoSupport.pageFind(hql,(dm.getPage() - 1) * dm.getRows(),  dm.getRows(), param.toArray());
		List<TeeTaskModel> modelList=new ArrayList<TeeTaskModel>();
		TeeTaskModel model=null;
		for (TeeTask teeTask : list) {
			model=parseToModel(teeTask);
			boolean returnFalg = returnFalg(teeTask);
			model.setFlag(returnFalg);
			modelList.add(model);
		}	
		json.setRows(modelList);
		return json;
	}

	
	
	public boolean returnFalg(TeeTask teeTask){
		boolean flag1=true;
		boolean flag2=true;
		boolean flag3=true;
		//此任务的前置任务
		TeeTask preTask=teeTask.getPreTask();
		if(preTask!=null && preTask.getStatus()!=1){
			flag1=false;
		}
		//此任务下所有的流程
//		List<TeeProjectFlow> list2=simpleDaoSupport.executeQuery("from TeeProjectFlow pf where pf.task.sid=? and pf.flowRun.delFlag=0 and pf.flowRun.endTime=null", new Object[]{teeTask.getSid()});
//        if(list2!=null && list2.size()>0){
//        	flag2=false;
//        }else{
//        	flag2=true;
//        }
		
		
		//判断此任务下所有的流程是否完成
		String flowTypeIds = teeTask.getFlowTypeIds();
		if(flowTypeIds!=null && !"".equals(flowTypeIds)){
			String[] split = flowTypeIds.split(",");
			for(int i=0;i<split.length;i++){
				int flowTypeId=TeeStringUtil.getInteger(split[i], 0);
				long count=simpleDaoSupport.count("select count(pf.sid) from TeeProjectFlow pf where pf.task.sid=? and pf.flowRun.flowType.sid=?", new Object[]{teeTask.getSid(),flowTypeId});
				long count2=simpleDaoSupport.count("select count(pf.sid) from TeeProjectFlow pf where pf.task.sid=? and pf.flowRun.flowType.sid=? and pf.flowRun.delFlag=0 and pf.flowRun.endTime=null", new Object[]{teeTask.getSid(),flowTypeId});
				if(count<=0 || count2>0){
					flag2=false;
					break;
				}
			}
		}
		
		//此任务的所有的下级任务
		long count = simpleDaoSupport.count("select count(*) from TeeTask where higherTask.sid=? and status!=1", new Object[]{teeTask.getSid()});
		if(count>0){
			flag3=false;
		}
		if(flag1==true && flag2==true && flag3==true){
			return true;
		}else{
			return false;
		}
	}
	

	/**
	 * 结束任务
	 * @param request
	 * @return
	 */
	public TeeJson end(HttpServletRequest request) {
		//获取当前登陆人 
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeJson  json=new TeeJson();
		//获取页面上传来的任务的主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		//根据任务主键  获取任务对象
		TeeTask  task=(TeeTask) simpleDaoSupport.get(TeeTask.class,sid);
		//设置任务的状态 和  进度
		task.setStatus(1);
		task.setProgress(100);
		String realEndTimeStr=sdf.format(new Date());
		Date realEndTime=null;
		try {
			realEndTime = sdf.parse(realEndTimeStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		task.setRealEndTime(realEndTime);
		simpleDaoSupport.update(task);
		
		if(task.getProject()!=null){
			
			String userListIds=projectService.getCreaterAndManagerAndViewerIds(task.getProject().getUuid());
			
			// 发送消息
			Map requestData1 = new HashMap();
			requestData1.put("content", loginUser.getUserName()+"完成了项目“"+task.getProject().getProjectName()+"”中所负责的任务“"+task.getTaskName()+"”，请查看详情。");
			requestData1.put("userListIds", userListIds);
			requestData1.put("moduleNo", "060");
			requestData1.put("remindUrl","/system/subsys/project/taskdetail/index.jsp?sid="+task.getSid());
			smsManager.sendSms(requestData1, loginUser);
			
		}
		
		
		
		
		
		
		
		json.setRtState(true);
		json.setRtMsg("已结束！");
		return json;
	}


	/**
	 * 根据任务主键  获取与之相关的 流程
	 * @param request
	 * @return
	 */
	public TeeJson getFlowTypeListByTaskId(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的任务的主键
	    int  sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	    List<Map> list=new ArrayList<Map>();
	    if(sid>0){
	    	TeeTask task=(TeeTask) simpleDaoSupport.get(TeeTask.class,sid);
	    	if(task!=null){
	    		String flowTypeIds=task.getFlowTypeIds();
	    		if(!("").equals(flowTypeIds)){
	    			String []flowTypeIdArray=flowTypeIds.split(",");
	    			Map m=null;
	    			for (String str : flowTypeIdArray) {
	    				m=new HashMap();
						int flowTypeId=Integer.parseInt(str);
						TeeFlowType flowType=(TeeFlowType) simpleDaoSupport.get(TeeFlowType.class,flowTypeId);
						m.put("flowName", flowType.getFlowName());
						m.put("sid",flowType.getSid());
						list.add(m);
					}	
	    		}
	    	}
	    	json.setRtState(true);
	    	json.setRtData(list);
            json.setRtMsg("数据获取成功！");	    	
	    }else{
	    	json.setRtState(false);
	    	json.setRtMsg("数据获取失败！");
	    }
		return json;
	}


	/**
	 * 设置任务的开始办理时间
	 * @param request
	 * @return
	 */
	public TeeJson begin(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//获取页面上传 来的任务主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeeTask task=(TeeTask) simpleDaoSupport.get(TeeTask.class,sid);
			if(task!=null){
				if(task.getRealBeginTime()==null){
					String dateStr=sdf.format(new Date());
					Date date=null;
					try {
						date = sdf.parse(dateStr);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					task.setRealBeginTime(date);
					task.setStatus(0);
					simpleDaoSupport.update(task);
				}
			}
		}
		json.setRtState(true);
		return json;
	}


	
	/**
	 * 根据项目主键  获取项目下的任务(不分父子关系)
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeJson getTasksByProjectId(HttpServletRequest request,
			TeeDataGridModel dm) {
		TeeJson json=new TeeJson();
		//获取页面上传来的项目的主键
		String projectId=TeeStringUtil.getString(request.getParameter("projectId"));
				
		List<TeeTask> taskList = simpleDaoSupport.executeQuery(" from TeeTask t where t.project.uuid=? order by t.taskNo asc ",new Object[]{projectId});
		List<Map> taskMapList = new ArrayList();
				
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    for (TeeTask task : taskList) {
			Map map=new HashMap();
			map.put("sid", task.getSid());
			map.put("taskNo", task.getTaskNo());
			map.put("taskName", task.getTaskName());
			map.put("beginTimeStr", sdf.format(task.getBeginTime()));
			map.put("endTimeStr", sdf.format(task.getEndTime()));
			map.put("taskLevel", task.getTaskLevel());
			if(task.getRealBeginTime()!=null){
				map.put("realBeginTimeStr", sdf.format(task.getRealBeginTime()));
			}else{
				map.put("realBeginTimeStr", "未开始");
			}
						
			if(task.getRealEndTime()!=null){
				map.put("realEndTimeStr", sdf.format(task.getRealEndTime()));
			}else{
				map.put("realEndTimeStr", "未结束");
			}
						
			if(task.getStatus()==2){//未开始
				map.put("status", "未开始");
			}else if(task.getStatus()==0){//进行中
			   long diff=new Date().getTime()-task.getEndTime().getTime();
			   if(diff>0){
				   map.put("status", "超时未完成（超时"+(diff/1000/60/60/24)+"天）");
			    }else{
				   map.put("status", "进行中");
			    }
			}else if(task.getStatus()==1){//已完成
				long diff=task.getRealEndTime().getTime()-task.getEndTime().getTime();
				 if(diff>0){
				    map.put("status", "超时完成（超时"+(diff/1000/60/60/24)+"天）");
				 }else{
					 map.put("status", "已完成");
				 }
			}
						
		                
			map.put("progress", task.getProgress());
			map.put("days", task.getDays());
			map.put("children", new ArrayList());
			if(task.getManager()!=null){
				map.put("managerName", task.getManager().getUserName());
							
			}
			
			 taskMapList.add(map);
			}
        json.setRtState(true);
        json.setRtData(taskMapList);
		return json;
	}


	
	/**
	 * 判断当前任务是不是父任务  或者   前置任务
	 * @param request
	 * @return
	 */
	public TeeJson isHigherOrPre(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的任务的主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
			List <TeeTask> taskList=simpleDaoSupport.executeQuery(" from TeeTask task where task.higherTask.sid=? or task.preTask.sid=? ", new Object[]{sid,sid});
			if(taskList!=null&&taskList.size()>0){
				json.setRtData(1);
				json.setRtState(true);
				json.setRtMsg("该任务存在子任务或者后置任务！");
			}else{
				json.setRtData(0);
				json.setRtState(true);
				json.setRtMsg("该任务不存在子任务或者后置任务！");
			}
		}else{
			json.setRtState(false);
		}
		return json;
	}


	
	/**
	 * 甘特图
	 * @param request
	 * @return
	 */
	public List<Map> gantt(HttpServletRequest request) {
		//获取页面上传来的项目的主键
		String projectId=TeeStringUtil.getString(request.getParameter("projectId"));
		List<Map> mapList=new ArrayList<Map>();
		
		List<TeeTask> taskList = simpleDaoSupport.executeQuery(" from TeeTask t where t.project.uuid=? order by t.taskNo asc ",new Object[]{projectId});
		Map m=null;
		Map m1=null;
		List<Map> list=null;
		for (TeeTask task : taskList) {
			m=new HashMap();
			m1=new HashMap();
			list=new ArrayList<Map>();
			
			m.put("name",task.getTaskNo());
			m.put("desc",task.getTaskName());
			if(task.getBeginTime()!=null){
				m1.put("from",task.getBeginTime().getTime());
			}
			if(task.getEndTime()!=null){
				m1.put("to",task.getEndTime().getTime());
			}
			m1.put("label",task.getTaskName()+"     "+task.getProgress()+"%");
			if(task.getStatus()==2){//未开始
				m1.put("customClass", "ganttBlue");
			}else if(task.getStatus()==1){//已完成
				m1.put("customClass", "ganttRed");
			}else if(task.getStatus()==0){//进行中
				m1.put("customClass", "ganttGreen");
			}
			m1.put("dataObj", task.getSid());
			list.add(m1);
			m.put("values", list);

			mapList.add(m);
		}
		return mapList;
	}

/**
 * 获取任务类型
 * */
	public TeeJson getTaskTypeById(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int taskId=TeeStringUtil.getInteger(request.getParameter("taskId"),0);
		Map map = simpleDaoSupport.executeNativeUnique("select project_type_id as typeId from task_projectype_file where task_id=?", new Object[]{taskId});
		if(map!=null){
			json.setRtData(map.get("typeId"));
		}
		return json;
	}
	
}


