package com.tianee.oa.subsys.project.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.project.bean.TeeProjectFlow;
import com.tianee.oa.subsys.project.bean.TeeTask;
import com.tianee.oa.subsys.project.model.TeeProjectFlowModel;
import com.tianee.oa.subsys.project.model.TeeTaskModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
@Service
public class TeeProjectFlowService extends TeeBaseService{

	
	@Autowired
	private TeeFlowProcessServiceInterface flowProcessService;
	
	/**
	 * 新增
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson add(HttpServletRequest request, TeeTaskModel model) {
		TeeJson json=new TeeJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//獲取頁面传来的任务主键
		int taskId=TeeStringUtil.getInteger(request.getParameter("taskId"),0);
		//获取页面上传来的runId
		int runId=TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		TeeTask task=(TeeTask) simpleDaoSupport.get(TeeTask.class,taskId);
		TeeFlowRun flowRun=(TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class,runId);
		TeeProjectFlow pf=new TeeProjectFlow();
		pf.setCreater(loginUser);
		pf.setFlowRun(flowRun);
		pf.setTask(task);
		if(task!=null&&task.getProject()!=null){
			pf.setProjectId(task.getProject().getUuid());
		}
		pf.setCreateTime(new Date());
		simpleDaoSupport.save(pf);
        json.setRtState(true);
        json.setRtMsg("保存成功！");
		return json;
	}

	
	/**
	 * 根据任务主键  获取与任务相关的流程数据
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getFlowRunData(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的任务的主键
		int taskId=TeeStringUtil.getInteger(request.getParameter("taskId"),0);
		
		List<TeeProjectFlow> list=simpleDaoSupport.executeQuery(" from TeeProjectFlow pf where pf.task.sid=? and pf.flowRun.delFlag=0", new Object[]{taskId});
		List<TeeProjectFlowModel> modelList=new ArrayList<TeeProjectFlowModel>();
		TeeProjectFlowModel model=null;
		for (TeeProjectFlow pf : list) {
			model=parseToModel(pf);
			modelList.add(model);	
		}
        json.setRtState(true);
        json.setRtData(modelList);
		return json;
	}


	
	/**
	 * 将实体类转换成model对象
	 * @param pf
	 * @return
	 */
	private TeeProjectFlowModel parseToModel(TeeProjectFlow pf) {
		TeeProjectFlowModel model=new TeeProjectFlowModel();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		BeanUtils.copyProperties(pf, model);
		if(pf.getCreater()!=null){
			model.setCreaterId(pf.getCreater().getUuid());
			model.setCreaterName(pf.getCreater().getUserName());
		}
		if(pf.getTask()!=null){
			model.setTaskId(pf.getTask().getSid());
			model.setTaskName(pf.getTask().getTaskName());
		}
		
		if(pf.getFlowRun()!=null){
			model.setRunName(pf.getFlowRun().getRunName());
			model.setRunId(pf.getFlowRun().getRunId());
			if(pf.getFlowRun().getFlowType()!=null){
				model.setFlowTypeName(pf.getFlowRun().getFlowType().getFlowName());
			}
			
		}
		if(pf.getCreateTime()!=null){
			model.setCreateTimeStr(sdf.format(pf.getCreateTime()));
		}
		//获取流程当前步骤
		List<Map> list=simpleDaoSupport.executeNativeQuery(" select * from flow_run_prcs where run_id=? and flag in (1,2) order by sid desc", new Object[]{pf.getFlowRun().getRunId()}, 0, Integer.MAX_VALUE);
        TeeFlowProcess fp=null;
		if(list.size()>0){
			if (TeeStringUtil.getInteger(list.get(0).get("FLOW_PRCS"), 0) != 0) {
				fp = flowProcessService.load(TeeStringUtil.getInteger(
						list.get(0).get("FLOW_PRCS"), 0));
				model.setCurrStep("第" + list.get(0).get("PRCS_ID") + "步：" + fp.getPrcsName());
				
			} else {
				model.setCurrStep("第" + list.get(0).get("PRCS_ID") + "步");
				
			}
			
		}else{
			model.setCurrStep("<span style='color:red;'>已完结</span>");
		}
		
		return model;
	}

}
