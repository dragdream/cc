package com.tianee.oa.core.workflow.workmanage.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.workflow.workmanage.service.TeeFlowNtkoPrintTemplateServiceInterface;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("ntkoPrintTemplate")
public class TeeFlowNtkoPrintTemplateController {
	
	@Autowired
	private TeeBaseUpload baseUpload;
	
	@Autowired
	private TeeFlowNtkoPrintTemplateServiceInterface printTemplateService;
	
	@RequestMapping("addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request) throws IOException{
		TeeJson json = new TeeJson();
		json.setRtState(true);
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		
		TeeAttachment attach = baseUpload.singleAttachUpload(multipartRequest, TeeAttachmentModelKeys.workFlowNtkoPrintTpl);
		
		Map requestData = TeeServletUtility.getParamMap(request);
		requestData.put("attach", attach);
		printTemplateService.addOrUpdate(requestData);
		return json;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public TeeJson delete(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtState(true);
		printTemplateService.delete(sid);
		return json;
	}
	
	@RequestMapping("list")
	@ResponseBody
	public TeeJson list(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		json.setRtState(true);
		json.setRtData(printTemplateService.list(flowId,runId));
		return json;
	}
}
