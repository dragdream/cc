package com.tianee.oa.subsys.cowork.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.beetl.ext.fn.ParseInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.calendar.model.TeeFullCalendarModel;
import com.tianee.oa.core.base.vehicle.bean.TeeVehicleUsage;
import com.tianee.oa.core.base.vehicle.model.TeeVehicleUsageModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.cowork.bean.TeeCoWorkTask;
import com.tianee.oa.subsys.cowork.model.TeeCoWorkOperModel;
import com.tianee.oa.subsys.cowork.model.TeeCoWorkTaskDocModel;
import com.tianee.oa.subsys.cowork.model.TeeCoWorkTaskModel;
import com.tianee.oa.subsys.cowork.model.TeenumModel;
import com.tianee.oa.subsys.cowork.service.TeeCoWorkService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeConfigLoader;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/coWork")
public class TeeCoWorkController {
	
	@Autowired
	private TeeCoWorkService coWorkService;
	
	@Autowired
	private TeeSmsManager smsManager;
	
	@ResponseBody
	@RequestMapping("/addTask")
	public TeeJson addTask(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeCoWorkTaskModel coWorkTaskModel = (TeeCoWorkTaskModel) TeeServletUtility.request2Object(request, TeeCoWorkTaskModel.class);
		/*//获取父级得开始时间及完成时间
		int parentTaskId=TeeStringUtil.getInteger(request.getParameter("parentTaskId"), 0);
		String startPDate=null;
		String endPDate=null;
		if(parentTaskId>0){
			TeeCoWorkOperModel operModel = new TeeCoWorkOperModel();
			operModel.setTaskId(parentTaskId);
			//System.out.println(operModel.getTaskId());
			TeeCoWorkTaskModel work=coWorkService.getTaskInfo(operModel);
			startPDate=work.getStartTimeDesc();
			endPDate=work.getEndTimeDesc();
			
		}
		//处理开始时间及完成时间
		
		String startDate=request.getParameter("startTimeDesc");
		String endDate=request.getParameter("endTimeDesc");
		*/
		
		
		coWorkTaskModel.setCreateUserId(loginPerson.getUuid());
		TeeCoWorkTask coWorkTask = coWorkService.addTaskService(coWorkTaskModel);
		json.setRtMsg("布置任务成功");
		json.setRtState(true);
		json.setRtData(coWorkTask.getSid());
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/editTask")
	public TeeJson editTask(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		TeeCoWorkTaskModel coWorkTaskModel = (TeeCoWorkTaskModel) TeeServletUtility.request2Object(request, TeeCoWorkTaskModel.class);
		coWorkTaskModel.setCreateUserId(loginPerson.getUuid());
		TeeCoWorkTask coWorkTask = coWorkService.editTaskService(coWorkTaskModel);
		
		json.setRtMsg("修改任务成功");
		json.setRtState(true);
		json.setRtData(coWorkTask.getSid());
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping("/getTaskInfo")
	public TeeJson getTaskInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		//System.out.println(request.getParameter("taskId"));
		TeeCoWorkOperModel operModel = (TeeCoWorkOperModel) TeeServletUtility.request2Object(request, TeeCoWorkOperModel.class);
		json.setRtState(true);
		json.setRtData(coWorkService.getTaskInfo(operModel));
		json.setRtState(true);
		json.setRtMsg("查询失败");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/deleteTask")
	public TeeJson deleteTask(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeCoWorkOperModel operModel = (TeeCoWorkOperModel) TeeServletUtility.request2Object(request, TeeCoWorkOperModel.class);
		json.setRtState(true);
		coWorkService.deleteTaskService(operModel.getTaskId());
		return json;
	}
	
	/**
	 * 撤销
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/redo")
	public TeeJson redo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeCoWorkOperModel operModel = (TeeCoWorkOperModel) TeeServletUtility.request2Object(request, TeeCoWorkOperModel.class);
		json.setRtState(true);
		coWorkService.redo(operModel);
		return json;
	}
	
	/**
	 * 列表
	 * @param request
	 * @return
	 */ 
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(HttpServletRequest request,TeeDataGridModel dataGridModel,Integer personid){
		
		TeeEasyuiDataGridJson easyuiDataGridJson = new TeeEasyuiDataGridJson();
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		if(personid==null){
			personid=0;
		}
		easyuiDataGridJson = coWorkService.datagrid(dataGridModel, requestData,personid);
		return easyuiDataGridJson;
	}
	/**
	 * 图标
	 * @param request
	 * @return
	 */ 
	@ResponseBody
	@RequestMapping("/num")
	public  TeeJson getNum(String paras,Integer personid,HttpServletRequest request){
		TeeJson json=new TeeJson();
		Map requestData=new HashMap();
		JSONObject js = JSONObject.fromObject(paras);
		if(js.size() >0){
			String taskTitle =String.valueOf(js.get("taskTitle"));
			String createUserId =String.valueOf(js.get("createUserId"));
			String createUserName = String.valueOf(js.get("createUserName"));
			String chargerId  =String.valueOf(js.get("chargerId"));
			String chargerName = String.valueOf(js.get("chargerName"));
			String status = String.valueOf(js.get("status"));
					
			requestData.put("taskTitle",taskTitle);
			requestData.put("createUserId",createUserId);
			requestData.put("createUserName",createUserName);
			requestData.put("chargerId",chargerId);
			requestData.put("chargerName",chargerName);
			requestData.put("status",status);
		}
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		
		if(personid==null){
			personid=0;
		}
		TeenumModel model=coWorkService.num1(personid, requestData);
		json.setRtData(model);
		
		return json;
	}
	/**
	 * 督办
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/urge")
	public TeeJson urge(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeCoWorkOperModel operModel = (TeeCoWorkOperModel) TeeServletUtility.request2Object(request, TeeCoWorkOperModel.class);
		json.setRtState(true);
		coWorkService.urge(operModel);
		return json;
	}
	
	/**
	 * 延期
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delay")
	public TeeJson delay(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeCoWorkOperModel operModel = (TeeCoWorkOperModel) TeeServletUtility.request2Object(request, TeeCoWorkOperModel.class);
		json.setRtState(true);
		coWorkService.delay(operModel);
		return json;
	}
	
	/**
	 * 申请失败
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/failed")
	public TeeJson failed(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeCoWorkOperModel operModel = (TeeCoWorkOperModel) TeeServletUtility.request2Object(request, TeeCoWorkOperModel.class);
		json.setRtState(true);
		coWorkService.failed(operModel);
		return json;
	}
	
	/**
	 * 申请完成
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/finish")
	public TeeJson finish(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeCoWorkOperModel operModel = (TeeCoWorkOperModel) TeeServletUtility.request2Object(request, TeeCoWorkOperModel.class);
		json.setRtState(true);
		coWorkService.finish(operModel);
		return json;
	}
	
	/**
	 * 接收任务
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/receive")
	public TeeJson receive(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeCoWorkOperModel operModel = (TeeCoWorkOperModel) TeeServletUtility.request2Object(request, TeeCoWorkOperModel.class);
		json.setRtState(true);
		coWorkService.receive(operModel);
		return json;
	}
	
	/**
	 * 拒绝接收
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/noReceive")
	public TeeJson noReceive(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeCoWorkOperModel operModel = (TeeCoWorkOperModel) TeeServletUtility.request2Object(request, TeeCoWorkOperModel.class);
		json.setRtState(true);
		coWorkService.noReceive(operModel);
		return json;
	}
	
	/**
	 * 通过审批
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pass")
	public TeeJson pass(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeCoWorkOperModel operModel = (TeeCoWorkOperModel) TeeServletUtility.request2Object(request, TeeCoWorkOperModel.class);
		json.setRtState(true);
		coWorkService.pass(operModel);
		return json;
	}
	
	/**
	 * 审批不通过
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/noPass")
	public TeeJson noPass(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeCoWorkOperModel operModel = (TeeCoWorkOperModel) TeeServletUtility.request2Object(request, TeeCoWorkOperModel.class);
		json.setRtState(true);
		coWorkService.noPass(operModel);
		return json;
	}
	
	/**
	 * 通过审核
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pass1")
	public TeeJson pass1(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeCoWorkOperModel operModel = (TeeCoWorkOperModel) TeeServletUtility.request2Object(request, TeeCoWorkOperModel.class);
		json.setRtState(true);
		coWorkService.pass1(operModel);
		return json;
	}
	
	/**
	 * 不通过审核
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/noPass1")
	public TeeJson noPass1(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeCoWorkOperModel operModel = (TeeCoWorkOperModel) TeeServletUtility.request2Object(request, TeeCoWorkOperModel.class);
		json.setRtState(true);
		coWorkService.noPass1(operModel);
		return json;
	}
	
	/**
	 * 汇报
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/report")
	public TeeJson report(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeCoWorkOperModel operModel = (TeeCoWorkOperModel) TeeServletUtility.request2Object(request, TeeCoWorkOperModel.class);
		json.setRtState(true);
		coWorkService.report(operModel);
		return json;
	}
	
	/**
	 * 任务文档
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listDocs")
	public TeeJson listDocs(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeCoWorkOperModel operModel = (TeeCoWorkOperModel) TeeServletUtility.request2Object(request, TeeCoWorkOperModel.class);
		json.setRtState(true);
		json.setRtData(coWorkService.listDocs(operModel));
		return json;
	}
	
	/**
	 * 任务事件
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listEvents")
	public TeeJson listEvents(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeCoWorkOperModel operModel = (TeeCoWorkOperModel) TeeServletUtility.request2Object(request, TeeCoWorkOperModel.class);
		json.setRtState(true);
		json.setRtData(coWorkService.listEvents(operModel));
		return json;
	}
	
	/**
	 * 添加文档
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addDoc")
	public TeeJson addDoc(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeCoWorkTaskDocModel docModel = (TeeCoWorkTaskDocModel) TeeServletUtility.request2Object(request, TeeCoWorkTaskDocModel.class);
		json.setRtState(true);
		coWorkService.addDoc(docModel);
		return json;
	}
	
	/**
	 * 是否存在未完成的子任务
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/hasExistsUnfinishedChildTask")
	public TeeJson hasExistsUnfinishedChildTask(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int taskId = TeeStringUtil.getInteger(request.getParameter("taskId"), 0);
		json.setRtState(true);
		json.setRtData(coWorkService.hasExistsUnfinishedChildTask(taskId));
		return json;
	}
	
	/**
	 * 删除文档
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteDoc")
	public TeeJson deleteDoc(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeCoWorkTaskDocModel docModel = (TeeCoWorkTaskDocModel) TeeServletUtility.request2Object(request, TeeCoWorkTaskDocModel.class);
		json.setRtState(true);
		coWorkService.deleteDoc(docModel);
		return json;
	}
	
	/**
	 * 任务汇总数据
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showTasksReport")
	public TeeJson showTasksReport(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeCoWorkTaskDocModel docModel = (TeeCoWorkTaskDocModel) TeeServletUtility.request2Object(request, TeeCoWorkTaskDocModel.class);
		json.setRtState(true);
		json.setRtData(coWorkService.showTasksReport());
		return json;
	}
	
	/**
	 * 任务树状图展示
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showTasksGraphics")
	public TeeJson showTasksGraphics(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeCoWorkTaskModel taskModel = (TeeCoWorkTaskModel) TeeServletUtility.request2Object(request, TeeCoWorkTaskModel.class);
		json.setRtState(true);
		json.setRtData(coWorkService.showTasksGraphics(taskModel));
		return json;
	}
	
	/**
	 * 获取与计划相关联的任务通过时间
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getTaskRelations")
	public TeeJson getTaskRelations(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String scheduleId = TeeStringUtil.getString(request.getParameter("scheduleId"));
		json.setRtState(true);
		json.setRtData(coWorkService.getTaskRelations(scheduleId));
		return json;
	} 
	
	/**
	 * 查询所有的任务
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping("/getListBytime")
	public TeeJson getshowListBy(HttpServletRequest request,TeeCoWorkOperModel model, TeePerson person) throws ParseException{
		TeeJson json = new TeeJson();
		 person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        //将request中的对应字段映值射到目标对象的属性中
		//TeeCoWorkOperModel models = new TeeCoWorkOperModel();
        //TeeServletUtility.requestParamsCopyToObject(request, model);
		 List<TeeFullCalendarModel> cc=coWorkService.getshowListByid(model,person);
		json.setRtData(cc);
		//json.setRtState(true);
        return json;
	} 
	/**
	 * 查询所有的任务
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping("/getList")
	public TeeJson SelctshowList(HttpServletRequest request,TeeCoWorkOperModel model, TeePerson person) throws ParseException{
		TeeJson json = new TeeJson();
		 person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        //将request中的对应字段映值射到目标对象的属性中
		//TeeCoWorkOperModel models = new TeeCoWorkOperModel();
        //TeeServletUtility.requestParamsCopyToObject(request, model);
		List<TeeCoWorkTaskModel>cc=coWorkService.SelctshowList();
		//System.out.println("************"+cc.size());
		json.setRtData(cc);
		json.setRtState(true);
        return json;
	}
	/**
	 * 列表
	 * @param request
	 * @return
	 */ 
	@ResponseBody
	@RequestMapping("/datagridPhone")
	public TeeEasyuiDataGridJson datagridPhone(HttpServletRequest request,TeeDataGridModel dataGridModel,Integer personid,String param){
		
		TeeEasyuiDataGridJson easyuiDataGridJson = new TeeEasyuiDataGridJson();
		//Map requestData = TeeServletUtility.getParamMap(request);
		Map requestData=new HashMap();
		JSONObject js = JSONObject.fromObject(param);
		if(js.size() >0){
			String taskTitle =String.valueOf(js.get("taskTitle"));
			String createUserId =String.valueOf(js.get("createUserId"));
			String createUserName = String.valueOf(js.get("createUserName"));
			String chargerId  =String.valueOf(js.get("chargerId"));
			String chargerName = String.valueOf(js.get("chargerName"));
			String status = String.valueOf(js.get("status"));
			String personId = String.valueOf(js.get("person"));
					
			requestData.put("taskTitle",taskTitle);
			requestData.put("createUserId",createUserId);
			requestData.put("createUserName",createUserName);
			requestData.put("chargerId",chargerId);
			requestData.put("chargerName",chargerName);
			requestData.put("status",status);
			requestData.put("personId",personId);
		}
		
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		if(personid==null){
			personid=0;
		}
		easyuiDataGridJson = coWorkService.datagridPhone(dataGridModel, requestData,personid);
		return easyuiDataGridJson;
	}
	
	/**
	 * 图标
	 * @param request
	 * @return
	 */ 
	@ResponseBody
	@RequestMapping("/num1")
	public  TeeJson getNum1(String param,Integer personid,HttpServletRequest request){
		TeeJson json=new TeeJson();
		Map requestData=new HashMap();
		JSONObject js = JSONObject.fromObject(param);
		if(js.size() >0){
			String taskTitle =String.valueOf(js.get("taskTitle"));
			String createUserId =String.valueOf(js.get("createUserId"));
			String createUserName = String.valueOf(js.get("createUserName"));
			String chargerId  =String.valueOf(js.get("chargerId"));
			String chargerName = String.valueOf(js.get("chargerName"));
			String status = String.valueOf(js.get("status"));
			String personId = String.valueOf(js.get("person"));
					
			requestData.put("taskTitle",taskTitle);
			requestData.put("createUserId",createUserId);
			requestData.put("createUserName",createUserName);
			requestData.put("chargerId",chargerId);
			requestData.put("chargerName",chargerName);
			requestData.put("status",status);
			requestData.put("personId",personId);
		}
		requestData.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		
		if(personid==null){
			personid=0;
		}
		TeenumModel model=coWorkService.num(personid, requestData);
		json.setRtData(model);
		
		return json;
	}
	
	
}
