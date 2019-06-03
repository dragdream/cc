package com.tianee.oa.subsys.schedule.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.schedule.bean.TeeSchedule;
import com.tianee.oa.subsys.schedule.model.TeeScheduleModel;
import com.tianee.oa.subsys.schedule.service.TeeScheduleService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/schedule")
public class TeeScheduleController {
	
	@Autowired
	private TeeScheduleService scheduleService;
	
	@ResponseBody
	@RequestMapping("/save")
	public TeeJson save(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeScheduleModel scheduleModel = 
				(TeeScheduleModel) TeeServletUtility.request2Object(request, TeeScheduleModel.class);
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		scheduleModel.setUserId(loginUser.getUuid());
		scheduleModel.setDeptId(loginUser.getDept().getUuid());
		TeeSchedule schedule = scheduleService.save(scheduleModel);
		
		String attachmentSidStr = request.getParameter("attachmentSidStr");
		List<TeeAttachment> attachments = scheduleService.getAttachmentDao().getAttachmentsByIds(attachmentSidStr);
		if(attachments!= null && attachments.size()>0){
			for(TeeAttachment attach:attachments){
				attach.setModelId(String.valueOf(schedule.getUuid()));
				scheduleService.getSimpleDaoSupport().update(attach);
			}
		}
		
		json.setRtState(true);
		json.setRtMsg("添加成功");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public TeeJson update(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String attachmentSidStr = request.getParameter("attachmentSidStr");
		TeeScheduleModel scheduleModel = 
				(TeeScheduleModel) TeeServletUtility.request2Object(request, TeeScheduleModel.class);
		List<TeeAttachment> attachments = scheduleService.getAttachmentDao().getAttachmentsByIds(attachmentSidStr);
		if(attachments!= null && attachments.size()>0){
			for(TeeAttachment attach:attachments){
				attach.setModelId(String.valueOf(scheduleModel.getUuid()));
				scheduleService.getSimpleDaoSupport().update(attach);
			}
		}
		scheduleService.update(scheduleModel);
		json.setRtState(true);
		json.setRtMsg("更新成功");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public TeeJson delete(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
		scheduleService.delete(uuid);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/get")
	public TeeJson get(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"),"");
		String type = TeeStringUtil.getString(request.getParameter("type"),"");
		
		if(!TeeUtility.isNullorEmpty(uuid) && !uuid.equals("null")){
			TeeScheduleModel model = scheduleService.get(uuid);
			List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
			List<TeeAttachment> attaches =scheduleService.getAttachmentDao().getAttaches(TeeAttachmentModelKeys.schedule, String.valueOf(uuid));
			for (TeeAttachment attach : attaches) {
				TeeAttachmentModel attachmentModel = new TeeAttachmentModel();
				BeanUtils.copyProperties(attach, attachmentModel);
				attachmentModel.setUserId(attach.getUser().getUuid() + "");
				attachmentModel.setUserName(attach.getUser().getUserName());
				if(type.equals("0")){
					attachmentModel.setPriv(1+2+4+8+16+32);// 一共五个权限好像
				}else{
					attachmentModel.setPriv(1+2);// 一共五个权限好像
				}
				// 1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
				attachmodels.add(attachmentModel);
			}
			model.setAttachMentModel(attachmodels);
			json.setRtData(model);
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getPriv")
	public TeeJson getPriv(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String scheduleId = TeeStringUtil.getString(request.getParameter("scheduleId"));
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json.setRtData(scheduleService.getPriv(loginUser.getUuid(), scheduleId));
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/finish")
	public TeeJson finish(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String summerize = TeeStringUtil.getString(request.getParameter("summerize"));
		String scheduleId = TeeStringUtil.getString(request.getParameter("scheduleId"));
		scheduleService.finish(summerize, scheduleId);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(HttpServletRequest request,TeeDataGridModel dm){
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER,request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return scheduleService.datagrid(requestData, dm);
	}
	
	@ResponseBody
	@RequestMapping("/getRangeTypeInfo")
	public TeeJson getRangeTypeInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String time = TeeStringUtil.getString(request.getParameter("time"));
		int rangeType = TeeStringUtil.getInteger(request.getParameter("rangeType"), 0);
		Calendar c = TeeDateUtil.parseCalendar("yyyy-MM-dd", time);
		json.setRtData(scheduleService.getRangeTypeInfo(rangeType, c));
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getMySchedules")
	public TeeJson getMySchedules(HttpServletRequest request){
		TeeJson json = new TeeJson();
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put(TeeConst.LOGIN_USER,request.getSession().getAttribute(TeeConst.LOGIN_USER));
		json.setRtData(scheduleService.getMySchedules(requestData));
		json.setRtState(true);
		return json;
	}
}
