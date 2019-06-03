package com.tianee.oa.core.tpl.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.tpl.bean.TeeWordModel;
import com.tianee.oa.core.tpl.model.TeeWordModelModel;
import com.tianee.oa.core.tpl.service.TeeWordModelService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("wordModel")
public class TeeWordModelController {
	@Autowired
	private TeeWordModelService modelService;
	
	@Autowired
	private TeeBaseUpload baseUpload;
	
	@ResponseBody
	@RequestMapping("addModel")
	public TeeJson addModel(HttpServletRequest request) throws IOException{
		TeeJson json = new TeeJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		TeeAttachment attach = baseUpload.singleAttachUpload(multipartRequest, TeeAttachmentModelKeys.WORD_MODEL);
		
		TeeWordModelModel model = new TeeWordModelModel();
		TeeServletUtility.requestParamsCopyToObject(multipartRequest, model);
		model.setAttachId(attach.getSid());
		
		modelService.saveWordModel(model);
		
		json.setRtState(true);
		json.setRtMsg("添加套红模板成功");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("updateModel")
	public TeeJson updateModel(HttpServletRequest request) throws IOException{
		//获取新上传的文件名称
		String newFileName=request.getParameter("file");
		
		TeeJson json = new TeeJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		TeeAttachment attach =null;
		if(newFileName!=null&&!("").equals(newFileName)){//重新上传了附件
			attach = baseUpload.singleAttachUpload(multipartRequest, TeeAttachmentModelKeys.WORD_MODEL);
		}
		
		
		TeeWordModelModel model = new TeeWordModelModel();
		TeeServletUtility.requestParamsCopyToObject(multipartRequest, model);
		
		
		
		if(attach!=null){
			model.setAttachId(attach.getSid());
		}
		
		modelService.update(model);
		json.setRtState(true);
		json.setRtMsg("修改模板信息成功");
		
		return json;
	}
	
	@ResponseBody
	@RequestMapping("deleteModel")
	public TeeJson deleteModel(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		modelService.deleteWordModel(sid);
		json.setRtState(true);
		json.setRtMsg("删除成功");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("getModel")
	public TeeJson getModel(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(modelService.getWordModelModel(sid));
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("getWordModelListByPriv")
	public TeeJson getWordModelListByPriv(HttpServletRequest request){
		TeeJson json = new TeeJson();
		//获取套红类型
		String wordModelType=request.getParameter("wordModelType");
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json.setRtData(modelService.getWordModelListByPriv(loginPerson.getUuid(),wordModelType));
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("datagrid")
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request){
		return modelService.datagrid(dm, null);
	}

	public void setModelService(TeeWordModelService modelService) {
		this.modelService = modelService;
	}

	public void setBaseUpload(TeeBaseUpload baseUpload) {
		this.baseUpload = baseUpload;
	}
	
}
