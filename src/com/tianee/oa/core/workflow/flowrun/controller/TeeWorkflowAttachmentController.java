package com.tianee.oa.core.workflow.flowrun.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/teeWorkflowAttachmentController")
public class TeeWorkflowAttachmentController extends TeeBaseService{

	@Autowired
	private TeeAttachmentService attachService;
	
	@Autowired
	private TeeBaseUpload baseUpload;
	
	/**
	 * 
	 * @author zhp
	 * @createTime 2013-10-19
	 * @editTime 下午12:19:16
	 * @desc
	 */
	@RequestMapping("/getTeeWorkFlowAttachment")
	@ResponseBody
	public TeeJson getTeeWorkFlowAttachment(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		
		List<TeeAttachmentModel>  list = null;
		list = attachService.getAttacheModels(TeeAttachmentModelKeys.workFlow, String.valueOf(runId));
		json.setRtData(list);
		json.setRtMsg("获取工作流附件成功!");
		json.setRtState(true);	
		
		return json;
	}

	/**
	 * 添加工作流附件
	 * @author zhp
	 * @throws IOException 
	 * @createTime 2013-10-20
	 * @editTime 下午05:50:49
	 * @desc
	 */
	@RequestMapping("/addWorkFlowAttachments")
	@ResponseBody
	public TeeJson addWorkFlowAttachments(HttpServletRequest request) throws IOException{
		TeeJson json = new TeeJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		int runId = TeeStringUtil.getInteger(multipartRequest.getParameter("runId"), 0);
		List<TeeAttachmentModel> list = new ArrayList<TeeAttachmentModel>();
		List<TeeAttachment> attaches = baseUpload.manyAttachUpload(multipartRequest, TeeAttachmentModelKeys.workFlow);
		for(TeeAttachment attach:attaches){
			attach.setModelId(String.valueOf(runId));
			attachService.updateAttachment(attach);
			
			TeeAttachmentModel am = new TeeAttachmentModel();
			BeanUtils.copyProperties(attach, am);
			am.setCreateTimeDesc(TeeDateUtil.format(attach.getCreateTime()));
			am.setSizeDesc(TeeFileUtility.getFileSizeDesc(attach.getSize()));
			
			//新上传的附件设置为所有权限
			am.setPriv(1|2|4|8|16|32|64|128);
			list.add(am);
		}
		
		json.setRtMsg("上传附件成功！");
		json.setRtState(true);
		json.setRtData(list);
		return json;
	}
	
	/**
	 * 工作流新建公共附件 
	 * 新建work 等类型附件 
	 * @author zhp
	 * @throws IOException 
	 * @createTime 2013-10-13
	 * @editTime 下午04:33:23
	 * @desc
	 */
	@RequestMapping("/createNewAttach")
	@ResponseBody
	public TeeJson createNewAttach(HttpServletRequest request) throws IOException{
		TeeJson json = new TeeJson();
		
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		String newName = TeeStringUtil.getString(request.getParameter("newName"), "新建文档");
		String newType =request.getParameter("newType"); // TeeStringUtil.getString(request.getParameter("newType"), "doc");
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		if(TeeUtility.isNullorEmpty(newType)){
			json.setRtMsg("新建文档出错，没有选择新建文档类型!");
			json.setRtState(false);
			return json;
		}
		
		TeeAttachment attach = null;
		if(newType.equals("doc")){
			attach = baseUpload.newAttachment(newName, "doc", TeeAttachmentModelKeys.workFlow,person);
		}else if(newType.equals("xls")){
			attach = baseUpload.newAttachment(newName, "xls", TeeAttachmentModelKeys.workFlow,person);
		}else if(newType.equals("ppt")){
			attach = baseUpload.newAttachment(newName, "ppt", TeeAttachmentModelKeys.workFlow,person);
		}
		attach.setModelId(String.valueOf(runId));
		attachService.addAttachment(attach);
		
		int attachId = attach.getSid();
		String attachName = attach.getFileName();
		Map map = new HashMap<String, String>();
		map.put("attachId", attachId);
		map.put("attachName", attachName);
		json.setRtData(map);
		json.setRtMsg("新建附件成功!");
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 删除工作流附件
	 * @author zhp
	 * @createTime 2013-10-19
	 * @editTime 下午06:47:14
	 * @desc
	 */
	@RequestMapping("/deleteWorkFlowAttach")
	@ResponseBody
	public TeeJson deleteWorkFlowAttach(HttpServletRequest request){
		
		TeeJson json = new TeeJson();
		int attachId = TeeStringUtil.getInteger(request.getParameter("attachId"), 0);
		attachService.deleteAttach(attachId);
		json.setRtState(true);
		return json;
	}

	public TeeBaseUpload getBaseUpload() {
		return baseUpload;
	}

	public void setBaseUpload(TeeBaseUpload baseUpload) {
		this.baseUpload = baseUpload;
	}

	public void setAttachService(TeeAttachmentService attachService) {
		this.attachService = attachService;
	}

	public TeeAttachmentService getAttachService() {
		return attachService;
	}
	
	
}
