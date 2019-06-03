package com.tianee.oa.subsys.schedule.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.schedule.model.TeeScheduleAnnotationsModel;
import com.tianee.oa.subsys.schedule.model.TeeScheduleCommentModel;
import com.tianee.oa.subsys.schedule.service.TeeScheduleCommentService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/scheduleComment")
public class TeeScheduleCommentController {
	
	@Autowired
	private TeeScheduleCommentService commentService;
	@ResponseBody
	@RequestMapping("/save")
	public TeeJson save(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeScheduleCommentModel commentModel = 
				(TeeScheduleCommentModel) TeeServletUtility.request2Object(request, TeeScheduleCommentModel.class);
		commentModel.setUserId(loginUser.getUuid());
		commentService.save(commentModel);
		json.setRtMsg("");
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public TeeJson update(HttpServletRequest request){
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public TeeJson delete(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
		commentService.delete(uuid);
		json.setRtMsg("");
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/get")
	public TeeJson get(HttpServletRequest request){
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(HttpServletRequest request,TeeDataGridModel dm){
		Map requestData = TeeServletUtility.getParamMap(request);
		return commentService.datagrid(requestData, dm);
	}
}
