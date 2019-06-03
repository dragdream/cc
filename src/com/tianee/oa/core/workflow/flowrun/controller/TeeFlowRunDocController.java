package com.tianee.oa.core.workflow.flowrun.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunDoc;
import com.tianee.oa.core.workflow.flowrun.model.TeeFlowRunDocModel;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunDocServiceInterface;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/flowRunDocController")
public class TeeFlowRunDocController {
	
	@Autowired
	private TeeFlowRunDocServiceInterface flowRunDocService;
	
	@RequestMapping("/getFlowRunDocByRunId")
	@ResponseBody
	public TeeJson getFlowRunDocByRunId(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		TeeFlowRunDoc doc = flowRunDocService.getFlowRunDocByRunId(runId);
		TeeFlowRunDocModel model = new TeeFlowRunDocModel();
		if(doc!=null){
			flowRunDocService.entityToModel(doc, model);
		}else{
			model = null;
		}
		
		json.setRtData(model);
		return json;
	}
	
	/**
	 * 获取版式文件附件
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFlowRunDocAipByRunId")
	@ResponseBody
	public TeeJson getFlowRunDocAipByRunId(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		TeeAttachment attach = flowRunDocService.getFlowRunDocAipByRunId(runId);
		if(attach==null){
			json.setRtData(null);
		}else{
			json.setRtData(attach.getSid());
		}
		return json;
	}
	
	/**
	 * 创建或更新版式正文
	 * @param request
	 * @return
	 */
	@RequestMapping("/createOrUpdateFlowRunDocAip")
	@ResponseBody
	public TeeJson createOrUpdateFlowRunDocAip(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		MultipartFile file = multipartRequest.getFile("file");
		Map<String,MultipartFile> files = multipartRequest.getFileMap();
		Set<String> keys = files.keySet();
		for(String key:keys){
			int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
			flowRunDocService.createOrUpdateFlowRunDocAip(runId, loginUser,files.get(key));
			break;
		}
		return json;
	}
	
	
	/**
	 * 创建正文
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/createNewOffice")
	@ResponseBody
	public TeeJson createNewOffice(HttpServletRequest request) throws IOException{
		TeeJson json = new TeeJson();
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int frpSid=TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		String docType = TeeStringUtil.getString(request.getParameter("docType"));
		int officePriv = TeeStringUtil.getInteger(request.getParameter("officePriv"), 0);
		if((officePriv&2)!=2){
			throw new TeeOperationException("您没有创建文档的权限！");
		}
		
		TeeFlowRunDoc doc = flowRunDocService.createNewOffice(runId,frpSid, docType, loginUser);
		TeeFlowRunDocModel model = new TeeFlowRunDocModel();
		flowRunDocService.entityToModel(doc, model);
		
		json.setRtData(model);
		return json;
	}
	
	/**
	 * 锁定文档
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/lockDoc")
	@ResponseBody
	public TeeJson lockDoc(HttpServletRequest request) throws IOException{
		TeeJson json = new TeeJson();
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int docId = TeeStringUtil.getInteger(request.getParameter("docId"), 0);
		flowRunDocService.lockDoc(docId);
		json.setRtData(true);
		return json;
	}
	
	/**
	 * 解锁文档
	 */
	@RequestMapping("/unlockDoc")
	@ResponseBody
	public TeeJson unlockDoc(HttpServletRequest request) throws IOException{
		TeeJson json = new TeeJson();
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int docId = TeeStringUtil.getInteger(request.getParameter("docId"), 0);
		flowRunDocService.unlockDoc(docId);
		json.setRtData(true);
		return json;
	}
	
	
	/**
	 * 版本锁校验
	 */
	@RequestMapping("/versionNoValidate")
	@ResponseBody
	public TeeJson versionNoValidate(HttpServletRequest request) throws IOException{
		TeeJson json = new TeeJson();
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int docId = TeeStringUtil.getInteger(request.getParameter("docId"), 0);
		flowRunDocService.unlockDoc(docId);
		json.setRtData(true);
		return json;
	}
	
	/**
	 * 版本锁校验
	 */
	@RequestMapping("/updateDoc")
	@ResponseBody
	public TeeJson updateDoc(HttpServletRequest request) throws IOException{
		TeeJson json = new TeeJson();
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int docId = TeeStringUtil.getInteger(request.getParameter("docId"), 0);
		flowRunDocService.updateDoc(docId, loginUser);
		json.setRtData(true);
		return json;
	}
	
	
	/**
	 * 判断当前步骤正文是否已经生成版本
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/hasGenerateVersion")
	@ResponseBody
	public TeeJson hasGenerateVersion(HttpServletRequest request) throws IOException{
		int runId=TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		int frpSid=TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		return flowRunDocService.hasGenerateVersion(runId,frpSid,loginUser);
	}
	
	
	
	/**
	 * 生成版本
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/generateVersion")
	@ResponseBody
	public TeeJson generateVersion(HttpServletRequest request) throws IOException{
		int runId=TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		int frpSid=TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		return flowRunDocService.generateVersion(runId,frpSid,loginUser);
	}
	
	
	
	
	/**
	 * 根据流程id  获取所有的正文版本
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getAllDocVersionByRunId")
	@ResponseBody
	public TeeJson getAllDocVersionByRunId(HttpServletRequest request) throws IOException{
		int runId=TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		return flowRunDocService.getAllDocVersionByRunId(runId,loginUser);
	}
}
