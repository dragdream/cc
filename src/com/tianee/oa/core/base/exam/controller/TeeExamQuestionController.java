package com.tianee.oa.core.base.exam.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.exam.bean.TeeExamQuestion;
import com.tianee.oa.core.base.exam.model.TeeExamQuestionModel;
import com.tianee.oa.core.base.exam.service.TeeExamQuestionService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("TeeExamQuestionController")
public class TeeExamQuestionController {
	@Autowired
	TeeExamQuestionService examQuestionService;
	@Autowired
	@Qualifier("teeBaseUpload")
	private TeeBaseUpload upload;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	public TeeExamQuestionService getExamQuestionService() {
		return examQuestionService;
	}

	public void setExamQuestionService(TeeExamQuestionService examQuestionService) {
		this.examQuestionService = examQuestionService;
	}

	public TeeBaseUpload getUpload() {
		return upload;
	}

	public void setUpload(TeeBaseUpload upload) {
		this.upload = upload;
	}

	@RequestMapping("/addExamQuestion")
	@ResponseBody
	public TeeJson addExamQuestion(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List attachments = upload.manyAttachUpload(multipartRequest, TeeAttachmentModelKeys.exam);
		TeeExamQuestionModel examQuestionModel = new TeeExamQuestionModel();
		TeeServletUtility.requestParamsCopyToObject(request, examQuestionModel);
		examQuestionModel.setAttacheModels(attachments);
		examQuestionService.addExamQuestionModel(examQuestionModel);
		json.setRtData(null);
		json.setRtState(true);
		json.setRtMsg("添加成功");
		return json;
	}
	
	@RequestMapping("/editExamQuestion")
	@ResponseBody
	public TeeJson editExamQuestion(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List attachments = upload.manyAttachUpload(multipartRequest, TeeAttachmentModelKeys.exam);
		TeeExamQuestionModel examQuestionModel = new TeeExamQuestionModel();
		TeeServletUtility.requestParamsCopyToObject(request, examQuestionModel);
		examQuestionModel.setAttacheModels(attachments);
		examQuestionService.updateExamQuestionModel(examQuestionModel);
		json.setRtMsg("更新成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/delExamQuestion")
	@ResponseBody
	public TeeJson delExamQuestion(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeExamQuestion examQuestion = examQuestionService.getById(sid);
		examQuestionService.deleteExamQuestion(examQuestion);
		
		json.setRtMsg("删除成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/getExamQuestion")
	@ResponseBody
	public TeeJson getExamQuestion(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeExamQuestion examQuestion = examQuestionService.getById(sid);
		TeeExamQuestionModel model = new TeeExamQuestionModel();
		BeanUtils.copyProperties(examQuestion, model);
		model.setStoreId(examQuestion.getExamStore().getSid());
		//获取附件
		List<TeeAttachment> attaches = attachmentService.getAttaches(TeeAttachmentModelKeys.exam, examQuestion.getSid()+"");
		List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
		for(TeeAttachment attach:attaches){
			TeeAttachmentModel m = new TeeAttachmentModel();
			BeanUtils.copyProperties(attach, m);
			m.setUserId(attach.getUser().getUuid()+"");
			m.setUserName(attach.getUser().getUserName());
			m.setPriv(1+2+4+8+16+32);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
			attachmodels.add(m);
		}
		model.setAttacheModels(attachmodels);
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return examQuestionService.datagrid(dm, requestDatas);
	}
	

	
}
