package com.tianee.oa.subsys.project.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.project.bean.TeeProjectFlow;
import com.tianee.oa.subsys.project.bean.TeeTask;
import com.tianee.oa.subsys.project.bean.TeeTaskReport;
import com.tianee.oa.subsys.project.model.TeeTaskReportModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeTaskReportService extends TeeBaseService {

	@Autowired
	private TeeSmsManager smsManager;
	
	@Autowired
	private TeeProjectService projectService;
	/**
	 * 根据任务主键  获取任务汇报列表
	 * @param request
	 * @return
	 */
	public TeeJson getReportListByTaskId(HttpServletRequest request) {
		TeeJson json=new TeeJson();
	    //获取页面上传来的任务的主键
		int taskId=TeeStringUtil.getInteger(request.getParameter("taskId"), 0);
		List<TeeTaskReport> reportList=simpleDaoSupport.executeQuery(" from TeeTaskReport where task.sid=?  ", new Object[]{taskId});
		List <TeeTaskReportModel> modelList=new ArrayList<TeeTaskReportModel>();
		TeeTaskReportModel model=null;
		for (TeeTaskReport teeTaskReport : reportList) {
			model=parseToModel(teeTaskReport);
			modelList.add(model);
		}
		json.setRtState(true);
		json.setRtData(modelList);
		json.setRtMsg("数据获取成功！");
		return json;
	}

	
	/**
	 * 将实体类转换成Model
	 * @param teeTaskReport
	 * @return
	 */
	private TeeTaskReportModel parseToModel(TeeTaskReport teeTaskReport) {
		TeeTaskReportModel model=new TeeTaskReportModel();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		BeanUtils.copyProperties(teeTaskReport, model);
		//汇报人信息
		if(teeTaskReport.getReporter()!=null){
			model.setReporterId(teeTaskReport.getReporter().getUuid());
			model.setReporterName(teeTaskReport.getReporter().getUserName());
		}
		//获取创建时间
		if(teeTaskReport.getCreateTime()!=null){
			model.setCreateTimeStr(sdf.format(teeTaskReport.getCreateTime()));
		}
		//获取所属任务信息
		if(teeTaskReport.getTask()!=null){
			model.setTaskId(teeTaskReport.getTask().getSid());
			model.setTaskName(teeTaskReport.getTask().getTaskName());
		}
		return model;
	}


	/**
	 * 添加任务汇报
	 * @param request
	 * @return
	 */
	public TeeJson addReport(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		//获取页面上传来的任务主键
		int taskId=TeeStringUtil.getInteger(request.getParameter("taskId"), 0);
		//获取任务
		TeeTask  task=(TeeTask) simpleDaoSupport.get(TeeTask.class,taskId);
		
		//获取页面上传来的汇报内容
		String  content=TeeStringUtil.getString(request.getParameter("content"));
		//获取页面上传来的任务进度
		int progress=TeeStringUtil.getInteger(request.getParameter("progress"),0);
		TeeTaskReport report=new TeeTaskReport();
		report.setContent(content);
		report.setCreateTime(new Date());
		report.setProgress(progress);
		report.setTask(task);
		report.setReporter(loginUser);
		if(task!=null){
			if(task.getProject()!=null){
				report.setProjectId(task.getProject().getUuid());	
			}
			
			if(progress==100){//修改任务的状态   结束任务
				
//				boolean returnFalg = returnFalg(task);
//				if(returnFalg==false){
//					json.setRtState(false);
//					return json;
//				}
				//判断此任务的前置任务是否完成
				TeeTask preTask=task.getPreTask();
				if(preTask!=null && preTask.getStatus()!=1){
					json.setRtMsg("此任务的前置任务尚未完成！");
					json.setRtState(false);
					return json;
				}
				
				//判断此任务的所有的下级任务是否完成
				long countTask = simpleDaoSupport.count("select count(*) from TeeTask where higherTask.sid=? and status!=1", new Object[]{task.getSid()});
				if(countTask>0){
					json.setRtMsg("此任务还有下级任务尚未完成！");
					json.setRtState(false);
					return json;
				}
				
				//判断此任务下所有的流程是否完成
				String flowTypeIds = task.getFlowTypeIds();
				if(flowTypeIds!=null && !"".equals(flowTypeIds)){
					String[] split = flowTypeIds.split(",");
					for(int i=0;i<split.length;i++){
						int flowTypeId=TeeStringUtil.getInteger(split[i], 0);
						long count=simpleDaoSupport.count("select count(pf.sid) from TeeProjectFlow pf where pf.task.sid=? and pf.flowRun.flowType.sid=?", new Object[]{task.getSid(),flowTypeId});
						long count2=simpleDaoSupport.count("select count(pf.sid) from TeeProjectFlow pf where pf.task.sid=? and pf.flowRun.flowType.sid=? and pf.flowRun.delFlag=0 and pf.flowRun.endTime=null", new Object[]{task.getSid(),flowTypeId});
						if(count<=0 || count2>0){
							json.setRtMsg("此任务下的流程尚未结束！");
							json.setRtState(false);
							return json;
						}
					}
				}
				//自定义字段
				List<Object> param=new ArrayList<Object>(); 
				Map requestData = TeeServletUtility.getParamMap(request);
		    	Map<String,String> customMap=new HashMap<String, String>();//存放自定义字段
		        //获取自定义字段
		    	for(Object obj:requestData.keySet()){
		        	String str=(String)obj;
		        	if(str.startsWith("FIELD_")){
		        		customMap.put(str, (String) requestData.get(str));
		        	}
		        }
		    	//修改自定义字段数据
		        String sql="update project set ";
		        String sql2="";
		        for(String name:customMap.keySet()){
		        	sql2+=name+"=?,";
	 	    		param.add(customMap.get(name));
	 	    	}
		        if(!"".equals(sql2)){
		        	sql2=sql2.substring(0, sql2.length()-1);
		        	sql+=sql2+"  where uuid=?"; 
		        	param.add(task.getProject().getUuid());
		        	simpleDaoSupport.executeNativeUpdate(sql, param.toArray());
		        }
		        
				task.setStatus(1);
				task.setProgress(progress);
				simpleDaoSupport.update(task);
				
				if(task!=null&&task.getProject()!=null){
					//根据项目主键   获取项目创建人  负责人  观察者的id串
					String userListIds=projectService.getCreaterAndManagerAndViewerIds(task.getProject().getUuid());

					// 发送消息
					Map requestData1 = new HashMap();
					requestData1.put("content", loginUser.getUserName()+"在项目“"+task.getProject().getProjectName()+"”中汇报了任务“"+task.getTaskName()+"”，汇报进度："+progress+"%，结束了任务。");
					requestData1.put("userListIds", userListIds);
					requestData1.put("moduleNo", "060");
					requestData1.put("remindUrl","/system/subsys/project/taskdetail/index.jsp?sid="+task.getSid() );
					smsManager.sendSms(requestData1, loginUser);
					
				}
				
				
				
			}else{
				task.setProgress(progress);
				simpleDaoSupport.update(task);
				
				//自定义字段
				List<Object> param=new ArrayList<Object>(); 
				Map requestData = TeeServletUtility.getParamMap(request);
		    	Map<String,String> customMap=new HashMap<String, String>();//存放自定义字段
		        //获取自定义字段
		    	for(Object obj:requestData.keySet()){
		        	String str=(String)obj;
		        	if(str.startsWith("FIELD_")){
		        		customMap.put(str, (String) requestData.get(str));
		        	}
		        }
		    	//修改自定义字段数据
		        String sql="update project set ";
		        String sql2="";
		        for(String name:customMap.keySet()){
		        	sql2+=name+"=?,";
	 	    		param.add(customMap.get(name));
	 	    	}
		        if(!"".equals(sql2)){
		        	sql2=sql2.substring(0, sql2.length()-1);
		        	sql+=sql2+"  where uuid=?"; 
		        	param.add(task.getProject().getUuid());
		        	simpleDaoSupport.executeNativeUpdate(sql, param.toArray());
		        }
				
				if(task!=null&&task.getProject()!=null){
					//根据项目主键   获取项目创建人  负责人  观察者的id串
					String userListIds=projectService.getCreaterAndManagerAndViewerIds(task.getProject().getUuid());

					// 发送消息
					Map requestData1 = new HashMap();
					requestData1.put("content", loginUser.getUserName()+"在项目“"+task.getProject().getProjectName()+"”中汇报了任务“"+task.getTaskName()+"”，汇报进度："+progress+"%。");
					requestData1.put("userListIds", userListIds);
					requestData1.put("moduleNo", "060");
					requestData1.put("remindUrl","/system/subsys/project/taskdetail/taskReportList.jsp?sid="+task.getSid() );
					smsManager.sendSms(requestData1, loginUser);
					
				}
			}
			
		}
		simpleDaoSupport.save(report);

		
		json.setRtState(true);
		json.setRtData("汇报成功！");
		
		return json;
	}


	/**
	 * 根据主键  获取详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeTaskReport  teeTaskReport=(TeeTaskReport) simpleDaoSupport.get(TeeTaskReport.class,sid);
		
		TeeTaskReportModel model=new TeeTaskReportModel();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(teeTaskReport!=null){
			BeanUtils.copyProperties(teeTaskReport, model);
			//汇报人信息
			if(teeTaskReport.getReporter()!=null){
				model.setReporterId(teeTaskReport.getReporter().getUuid());
				model.setReporterName(teeTaskReport.getReporter().getUserName());
			}
			//获取创建时间
			if(teeTaskReport.getCreateTime()!=null){
				model.setCreateTimeStr(sdf.format(teeTaskReport.getCreateTime()));
			}
			//获取所属任务信息
			if(teeTaskReport.getTask()!=null){
				model.setTaskId(teeTaskReport.getTask().getSid());
				model.setTaskName(teeTaskReport.getTask().getTaskName());
			}
		}
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}
	
	public boolean returnFalg(TeeTask teeTask){
		boolean flag1=false;
		boolean flag2=false;
		boolean flag3=false;
		//此任务的前置任务
		TeeTask preTask=teeTask.getPreTask();
		if(preTask==null){
			flag1=true;
		}else{
			if(preTask.getStatus()==1){
				flag1=true;
			}
		}
		//此任务下所有的流程
		List<TeeProjectFlow> list2=simpleDaoSupport.executeQuery("from TeeProjectFlow pf where pf.task.sid=? and pf.flowRun.delFlag=0 and pf.flowRun.endTime=null", new Object[]{teeTask.getSid()});
        if(list2!=null && list2.size()>0){
        	flag2=false;
        }else{
        	flag2=true;
        }
		//此任务的所有的下级任务
		long count = simpleDaoSupport.count("select count(*) from TeeTask where higherTask.sid=? and status!=1", new Object[]{teeTask.getSid()});
		if(count>0){
			flag3=false;
		}else{
			flag3=true;
		}
		if(flag1==true && flag2==true && flag3==true){
			return true;
		}else{
			return false;
		}
	}
	
	

}
