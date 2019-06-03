package com.tianee.oa.mobile.sms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.general.model.TeeSmsModel;
import com.tianee.oa.core.general.service.TeeSmsService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@RequestMapping("/mobileSms")
public class TeeMobileSmsController {
	@Autowired
	TeeSmsService smsService;
	
	@RequestMapping("/getSmsBoxDatas.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getSmsBoxDatas(TeeDataGridModel dm,HttpServletRequest request){
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//设置短信标记不接收
		request.getSession().setAttribute(TeeConst.SMS_FLAG, 0);
		int toId = person.getUuid();
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put("toId", toId);
		TeeEasyuiDataGridJson dataGridJson = smsService.smsDatas(dm, requestData);
		List<TeeSmsModel> modelList = dataGridJson.getRows();
		List<Map> mapList = new ArrayList();
		Map data = null;
		for(TeeSmsModel smsModel:modelList){
			data = new HashMap();
			data.put("content", smsModel.getContent());
			data.put("from", smsModel.getFromUser());
			data.put("time", smsModel.getSendTimeDesc());
			data.put("url", smsModel.getRemindUrl());
			data.put("url1", smsModel.getRemindUrl1());
			data.put("sid", smsModel.getSmsSid());
			data.put("moduleNo", smsModel.getModuleNo());
			mapList.add(data);
		}
		
		dataGridJson.setRows(mapList);
		return dataGridJson;
	}
}
