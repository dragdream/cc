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

import com.incors.plaf.q;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.project.bean.TeeProjectQuestion;
import com.tianee.oa.subsys.project.bean.TeeTask;
import com.tianee.oa.subsys.project.model.TeeProjectQuestionModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
@Service
public class TeeProjectQuestionService extends TeeBaseService{

	@Autowired
	private TeeSmsManager smsManager;
	/**
	 * 创建问题
	 * @param request
	 * @return
	 */
	public TeeJson addQuestion(HttpServletRequest request,TeeProjectQuestionModel model) {
		TeeJson json=new TeeJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取页面上传来的任务的主键
		int taskId=TeeStringUtil.getInteger(request.getParameter("taskId"),0);
		TeeTask task=(TeeTask) simpleDaoSupport.get(TeeTask.class,taskId);
		if(task!=null){
			TeeProjectQuestion question=new TeeProjectQuestion();
			BeanUtils.copyProperties(model, question);
			if(model.getOperatorId()!=0){
				TeePerson operator=(TeePerson) simpleDaoSupport.get(TeePerson.class,model.getOperatorId());
			    question.setOperator(operator);
			}
			question.setTask(task);
			question.setCreater(loginUser);
			question.setCreateTime(new Date());
			question.setStatus(0);
			if(task.getProject()!=null){
				question.setProjectId(task.getProject().getUuid());
			}
			
			simpleDaoSupport.save(question);
			
			
			// 发送消息
			Map requestData1 = new HashMap();
			requestData1.put("content", "项目“"+task.getProject().getProjectName()+"”有新的问题“"+question.getQuestionName()+"”需要您处理，请及时办理。");
			requestData1.put("userListIds", model.getOperatorId());
			requestData1.put("moduleNo", "060");
			requestData1.put("remindUrl","/system/subsys/project/projectquestion/index.jsp");
			smsManager.sendSms(requestData1, loginUser);
			
			
			
			json.setRtState(true);
			json.setRtMsg("创建成功！");
			
		}
		
		return json;
	}

	/**
	 * 根据任务主键 获取问题列表
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getQuestionListByTaskId(
			HttpServletRequest request, TeeDataGridModel dm) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int taskId=TeeStringUtil.getInteger(request.getParameter("taskId"), 0);
		
		long total=simpleDaoSupport.count(" select count(*) from TeeProjectQuestion p where p.task.sid=? ", new Object[]{taskId});
		json.setTotal(total);
		List<TeeProjectQuestion> list=simpleDaoSupport.pageFind(" from TeeProjectQuestion p where p.task.sid=?   ",(dm.getPage() - 1) * dm.getRows(),  dm.getRows(), new Object[]{taskId});
		List<TeeProjectQuestionModel> modelList=new ArrayList<TeeProjectQuestionModel>();
		TeeProjectQuestionModel model=null;
		for (TeeProjectQuestion question : list) {
			model=parseToModel(question);
			modelList.add(model);
		}	
		json.setRows(modelList);
		return json;
	}

	
	/**
	 * 将实体类转换成model
	 * @param question
	 * @return
	 */
	private TeeProjectQuestionModel parseToModel(TeeProjectQuestion question) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeProjectQuestionModel model=new TeeProjectQuestionModel();
		BeanUtils.copyProperties(question, model);
		if(question.getCreateTime()!=null){
			model.setCreateTimeStr(sdf.format(question.getCreateTime()));
		}
		if(question.getHandleTime()!=null){
			model.setHandleTimeStr(sdf.format(question.getHandleTime()));
		}
		
		if(question.getOperator()!=null){
			model.setOperatorId(question.getOperator().getUuid());
			model.setOperatorName(question.getOperator().getUserName());
		}
		if(question.getTask()!=null){
			model.setTaskId(question.getTask().getSid());
			model.setTaskName(question.getTask().getTaskName());
		}
		if(question.getCreater()!=null){
			model.setCreaterId(question.getCreater().getUuid());
			model.setCreaterName(question.getCreater().getUserName());
		}
		if(!("").equals(question.getProjectId())){
			Map  m=simpleDaoSupport.executeNativeUnique(" select * from project where uuid=? ", new Object[]{question.getProjectId()});
		    model.setProjectName(TeeStringUtil.getString(m.get("PROJECT_NAME")));
		 }
		return model;
	}

	
	/**
	 * 根据主键获取问题详情
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的 问题的主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
		    TeeProjectQuestion question=(TeeProjectQuestion) simpleDaoSupport.get(TeeProjectQuestion.class,sid);
			TeeProjectQuestionModel model=parseToModel(question);
			json.setRtData(model);
		    json.setRtState(true);
			json.setRtMsg("数据获取成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		
		return json;
	}

	
	/**
	 * 获取当前登陆人待解决   已解决的问题列表
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getQuestionListByStatus(
			HttpServletRequest request, TeeDataGridModel dm) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//获取页面上传来的问题的状态
		int status=TeeStringUtil.getInteger(request.getParameter("status"),0);
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		long total=simpleDaoSupport.count(" select count(*) from TeeProjectQuestion p where p.operator.uuid=? and p.status=? ", new Object[]{loginUser.getUuid(),status});
		json.setTotal(total);
		List<TeeProjectQuestion> list=simpleDaoSupport.pageFind(" from TeeProjectQuestion p where p.operator.uuid=? and p.status=?   ",(dm.getPage() - 1) * dm.getRows(),  dm.getRows(), new Object[]{loginUser.getUuid(),status});
		List<TeeProjectQuestionModel> modelList=new ArrayList<TeeProjectQuestionModel>();
		TeeProjectQuestionModel model=null;
		for (TeeProjectQuestion question : list) {
			model=parseToModel(question);
			modelList.add(model);
		}	
		json.setRows(modelList);
		return json;
	}

	/**
	 * 问题办理
	 * @param request
	 * @return
	 */
	public TeeJson handle(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		//获取页面上传来的问题的主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		//获取页面上传来的汇报结果
		String result=TeeStringUtil.getString(request.getParameter("result"));
		TeeProjectQuestion q=(TeeProjectQuestion) simpleDaoSupport.get(TeeProjectQuestion.class,sid);
		q.setHandleTime(new Date());
		q.setStatus(1);
		q.setResult(result);
		simpleDaoSupport.update(q);
		
		
		// 发送消息      给任务负责人
		Map requestData1 = new HashMap();
		requestData1.put("content", loginUser.getUserName()+"处理了您提出的问题“"+q.getQuestionName()+"”，请查看详情。");
		requestData1.put("userListIds", q.getTask().getManager().getUuid());
		requestData1.put("moduleNo", "060");
		requestData1.put("remindUrl","/system/subsys/project/projectquestion/questionInfo.jsp?sid="+q.getSid());
		smsManager.sendSms(requestData1, loginUser);
		
		
		
		json.setRtState(true);
		json.setRtMsg("办理成功！");
		return json;
	}

	
	/**
	 * 根据项目主键 获取项目的所有问题列表
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getQuestionListByProjectId(
			HttpServletRequest request, TeeDataGridModel dm) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//获取页面上传来的项目主键
		String projectId=TeeStringUtil.getString(request.getParameter("projectId"));
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		long total=simpleDaoSupport.count(" select count(*) from TeeProjectQuestion p where p.projectId=? ", new Object[]{projectId});
		json.setTotal(total);
		List<TeeProjectQuestion> list=simpleDaoSupport.executeQuery(" from TeeProjectQuestion p where p.projectId=?   ", new Object[]{projectId});
		List<TeeProjectQuestionModel> modelList=new ArrayList<TeeProjectQuestionModel>();
		TeeProjectQuestionModel model=null;
		for (TeeProjectQuestion question : list) {
			model=parseToModel(question);
			modelList.add(model);
		}	
		json.setRows(modelList);
		return json;
	}
    
	
	/**
	 * 根据项目主键  获取问题列表
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeJson getQuestionsByProjectId(HttpServletRequest request,
			TeeDataGridModel dm) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//获取页面上传来的项目主键
		String projectId=TeeStringUtil.getString(request.getParameter("projectId"));
		TeeJson json=new TeeJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	
		List<TeeProjectQuestion> list=simpleDaoSupport.executeQuery(" from TeeProjectQuestion p where p.projectId=?   ", new Object[]{projectId});
		List<TeeProjectQuestionModel> modelList=new ArrayList<TeeProjectQuestionModel>();
		TeeProjectQuestionModel model=null;
		for (TeeProjectQuestion question : list) {
			model=parseToModel(question);
			modelList.add(model);
		}	
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}

}
