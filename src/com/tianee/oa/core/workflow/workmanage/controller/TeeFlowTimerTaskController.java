package com.tianee.oa.core.workflow.workmanage.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowTimerTask;
import com.tianee.oa.core.workflow.workmanage.model.TeeFlowTimerTaskModel;
import com.tianee.oa.core.workflow.workmanage.service.TeeFlowTimerTaskServiceInterface;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("flowTimerTask")
public class TeeFlowTimerTaskController {
	@Autowired
	TeeFlowTimerTaskServiceInterface timerTaskService;
	
	@Autowired
	TeePersonService personService;
	
	@Autowired
	TeeFlowTypeServiceInterface flowTypeService;
	
	/**
	 * 获取流程名称
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getFlowName.action")
	@ResponseBody
	public TeeJson getFlowTree(HttpServletRequest request){
		
		TeeJson json = new TeeJson();
		String flowTypeId = request.getParameter("flowTypeId");
		String flowName = "";
		flowName = timerTaskService.getFlowName(Integer.parseInt(flowTypeId));
		System.out.println(flowName);
		json.setRtData(flowName);
		json.setRtMsg("发送成功");
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据ID获取定时任务
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getFlowTimerTaskById.action")
	@ResponseBody
	public TeeJson getFlowTimerTaskById(HttpServletRequest request){
		
		TeeJson json = new TeeJson();
		String sid = request.getParameter("sid");
		TeeFlowTimerTaskModel model = timerTaskService.getFlowTimerTaskById(Integer.parseInt(sid));
		json.setRtData(model);
		json.setRtMsg("读取成功");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/deleteById.action")
	@ResponseBody
	public TeeJson deleteById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		timerTaskService.deleteById(sid);
		json.setRtMsg("删除成功");
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 新增和更新定时任务
	 * @param request
	 * @param para
	 * @return
	 * @throws ParseException 
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdateTimerTask.action")
	@ResponseBody
	public TeeJson addOrUpdateTimerTask(HttpServletRequest request) throws ParseException{
		TeeJson json = new TeeJson();
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//当前时间
		Date date = new Date();
		Date curDate = new Date();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowTypeId"), 0);
		int users[] = TeeStringUtil.parseIntegerArray(request.getParameter("users"));
		int type = TeeStringUtil.getInteger(request.getParameter("type"), 0);
		//一次性
		String remindTime1 = TeeStringUtil.getString(request.getParameter("remindTime1"));
		//按日
		String remindTime2 = TeeStringUtil.getString(request.getParameter("remindTime2"));
		//按周
		String remindDate3 = TeeStringUtil.getString(request.getParameter("remindDate3"));
		String remindTime3 = TeeStringUtil.getString(request.getParameter("remindTime3"));
		//按月
		String remindDate4 = TeeStringUtil.getString(request.getParameter("remindDate4"));
		String remindTime4 = TeeStringUtil.getString(request.getParameter("remindTime4"));
		//按年
		String remindDate5Mon = TeeStringUtil.getString(request.getParameter("remindDate5Mon"));
		String remindDate5Day = TeeStringUtil.getString(request.getParameter("remindDate5Day"));
		String remindTime5 = TeeStringUtil.getString(request.getParameter("remindTime5"));
		
		//计时器
		Calendar calendar = Calendar.getInstance();
		
		//提醒模型
		Map model = new HashMap();
		
		switch (type) {
		case 1:
			calendar.setTime(sdf1.parse(remindTime1));
			break;
		case 2:
			date = sdf.parse(remindTime2);
			calendar.set(Calendar.HOUR_OF_DAY, date.getHours());
			calendar.set(Calendar.MINUTE, date.getMinutes());
			calendar.set(Calendar.SECOND, date.getSeconds());
			if(calendar.getTimeInMillis()<curDate.getTime()){
				calendar.add(Calendar.DATE, 1);
			}
			break;
		case 3:
			calendar.set(Calendar.DAY_OF_WEEK, Integer.parseInt(remindDate3));
			date = sdf.parse(remindTime3);
			calendar.set(Calendar.HOUR_OF_DAY, date.getHours());
			calendar.set(Calendar.MINUTE, date.getMinutes());
			calendar.set(Calendar.SECOND, date.getSeconds());
			if(calendar.getTimeInMillis()<curDate.getTime()){
				calendar.add(Calendar.DAY_OF_WEEK, 7);
			}
			break;
		case 4:
			calendar.set(Calendar.DATE, Integer.parseInt(remindDate4));
			date = sdf.parse(remindTime4);
			calendar.set(Calendar.HOUR_OF_DAY, date.getHours());
			calendar.set(Calendar.MINUTE, date.getMinutes());
			calendar.set(Calendar.SECOND, date.getSeconds());
			if(calendar.getTimeInMillis()<curDate.getTime()){
				calendar.add(Calendar.MONTH, 1);
			}
			break;
		case 5:
			calendar.set(Calendar.MONTH, Integer.parseInt(remindDate5Mon)-1);
			calendar.set(Calendar.DATE, Integer.parseInt(remindDate5Day));
			date = sdf.parse(remindTime5);
			calendar.set(Calendar.HOUR_OF_DAY, date.getHours());
			calendar.set(Calendar.MINUTE, date.getMinutes());
			calendar.set(Calendar.SECOND, date.getSeconds());
			if(calendar.getTimeInMillis()<curDate.getTime()){
				calendar.add(Calendar.YEAR, 1);
			}
			break;
		}
		
		model.put("year", calendar.get(Calendar.YEAR));
		model.put("month", calendar.get(Calendar.MONTH)+1);
		model.put("date", calendar.get(Calendar.DATE));
		model.put("hours", calendar.get(Calendar.HOUR_OF_DAY));
		model.put("minutes", calendar.get(Calendar.MINUTE));
		model.put("seconds", calendar.get(Calendar.SECOND));
		model.put("week", calendar.get(Calendar.DAY_OF_WEEK));
		
		
		TeeFlowTimerTask task = null;
		
		if(sid!=0){
			task = timerTaskService.get(sid);
			task.setLastTime(null);
		}else{
			task = new TeeFlowTimerTask();
		}
		
		for(int userId:users){
			if(userId==0){
				continue;
			}
			task.getUsers().add(personService.selectByUuid(""+userId));
		}
		task.setFlowType(flowTypeService.load(flowId));
		task.setRemindStamp(calendar.getTimeInMillis());
		task.setType(type);
		task.setRemindModel(jsonUtil.toJson(model));
		
		
		if(sid!=0){
			timerTaskService.update(task);
			json.setRtMsg("更新成功");
		}else{
			timerTaskService.add(task);
			json.setRtMsg("添加成功");
		}
		
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取定时任务列表
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getTimerTaskList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getTimerTaskList(TeeDataGridModel dm,HttpServletRequest request){
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int fromId = person.getUuid();
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		return timerTaskService.datagrid(dm,fromId,flowId);
	}

}
