package com.tianee.oa.core.workflow.flowrun.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.filters.StringInputStream;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import sun.misc.BASE64Encoder;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.bean.TeeBaseStream;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseDownloadService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.dam.service.TeeDamFilesService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workFlowFrame.creator.TeeFlowCreatorInterface;
import com.tianee.oa.core.workFlowFrame.dataloader.TeeSimpleDataLoaderInterface;
import com.tianee.oa.core.workFlowFrame.dataloader.TeeWorkFlowDataFactoryInterface;
import com.tianee.oa.core.workFlowFrame.runner.TeeFreeFlowRunnerInterface;
import com.tianee.oa.core.workFlowFrame.runner.TeeWorkFlowRunnerFactoryInterface;
import com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunDoc;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.model.TeeBisRunModel;
import com.tianee.oa.core.workflow.flowrun.model.TeeFlowRunModel;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunViewServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.util.workflow.TeeWorkflowHelperInterface;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.thirdparty.newcapec.supwisdom.LoginUser;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.file.TeeAOPExcleUtil;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.file.TeeZipUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.secure.TeePassEncryptMD5;
import com.tianee.webframe.util.servlet.HttpClientUtil;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.str.expReplace.TeeExpFetcher;
import com.tianee.webframe.util.str.expReplace.TeeHTMLInputTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLTag;
import com.tianee.webframe.util.str.expReplace.TeeRegexpAnalyser;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
@RequestMapping("/flowRun")
public class TeeWorkFlowRunController {
	@Autowired
	private TeeFlowRunPrcsServiceInterface flowRunPrcsService;
	@Autowired
	private TeeFlowCreatorInterface flowCreator;

	@Autowired
	private TeeWorkFlowServiceContextInterface flowServiceContext;
	
	@Autowired
	private TeeWorkflowHandlerInterface workflowHandler;
	
	@Autowired
	private TeeWorkflowHelperInterface workflowHelper;

	@Autowired
	private TeeWorkFlowDataFactoryInterface flowDataFactory;
	
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private TeeWorkFlowRunnerFactoryInterface flowRunnerFactory;

	@Autowired
	private TeeFreeFlowRunnerInterface freeFlowRunner;
	
	@Autowired
	private TeeSimpleDataLoaderInterface simpleDataLoader;
	
	@Autowired
	private TeeFlowRunViewServiceInterface flowRunViewService;
	
	@Autowired
	private TeeWorkflowHelperInterface helper;
	
	@Autowired
	private TeeBaseDownloadService baseDownloadService;
	
	@Autowired
	private TeeBaseUpload baseUpload;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private TeeFlowFormServiceInterface flowFormService;
	
	
	@Autowired
	private TeeDamFilesService damFilesService;
	
	@Autowired
	private TeeFlowTypeServiceInterface flowTypeService;
	
	@Autowired
	private TeeFlowRunServiceInterface flowRunService;
	
	
	@Autowired
	private  TeeWorkflowServiceInterface workflowService;
	
	@RequestMapping(value="/updateRunName")
	@ResponseBody
	public TeeJson updateRunName(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		String runName = TeeStringUtil.getString(request.getParameter("runName"));
		workflowHandler.updateRunName(runId, runName);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取flowRun详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getFlowIdByRunId")
	@ResponseBody
	public TeeJson get(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		json.setRtData(workflowHandler.getFlowIdByRunId(runId));
		json.setRtState(true);
		return json;
	}
	
	
	@RequestMapping(value="/updateRunLevel")
	@ResponseBody
	public TeeJson updateRunLevel(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		int level = TeeStringUtil.getInteger(request.getParameter("level"),0);
		workflowHandler.updateRunLevel(runId, level);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping(value="/createNewWork")
	@ResponseBody
	public TeeJson createNewWork(HttpServletRequest request) throws Exception {
		
		String sType = request.getParameter("fType");
				sType = TeeStringUtil.fileEmptyString(sType, "0");//流程类型
		TeeJson json = new TeeJson();
		
		//第一步办理人id
		int beginUserSid=TeeStringUtil.getInteger(request.getParameter("beginUserSid"),0);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeePerson person=null;
		if(beginUserSid!=0){
			person = personService.selectByUuid(beginUserSid+"");
		}else{
			person = personService.selectByUuid(loginPerson.getUuid()+"");
		}
		
		TeeFlowTypeServiceInterface flowTypeService = flowServiceContext.getFlowTypeService();
		TeeFlowType ft	= flowTypeService.get(Integer.parseInt(sType));
		int validation = TeeStringUtil.getInteger(request.getParameter("validation"), 0);
		if(ft == null){
			throw new TeeOperationException("流程类型获取为空");
		}
		
		//处理接收流程发起数据相关
		TeeBisRunModel bisRunModel = new TeeBisRunModel();
		bisRunModel.setRunName(request.getParameter("runName"));
		bisRunModel.setPreName(request.getParameter("preName"));
		bisRunModel.setSufName(request.getParameter("sufName"));
		String bisKey = request.getParameter("bisKey");//获取业务键
		String bisTable = request.getParameter("bisTable");//获取业务表
		//用来区分是不是由业务建模跳转到流程的
		bisRunModel.setBusinessKey(TeeStringUtil.getString(request.getParameter("businessKey")));
		
		Map<String,String> initDatas = new HashMap();
		Map<String,String> initVars = new HashMap();
		
		Enumeration _enum = request.getParameterNames();
		String key = null;
		String val = null;
		while(_enum.hasMoreElements()){
			key = (String)_enum.nextElement();
			if(key.indexOf("DATA_")!=-1 || key.indexOf("EXT_")!=-1){//
				initDatas.put(key, request.getParameter(key));
			}else if(key.indexOf("VAR_")!=-1){
				initVars.put(key, request.getParameter(key));
			}else if(key.indexOf("DOC_ID")!=-1){//正文
				bisRunModel.setDocId(TeeStringUtil.getInteger(request.getParameter(key), 0));
			}else if(key.indexOf("ATTACH_IDS")!=-1){//附件
				bisRunModel.setAttachIds(TeeStringUtil.getString(request.getParameter(key)));
			}else if(key.indexOf("DOC_AIP_ID")!=-1){//版式正文
				bisRunModel.setDocAipId(TeeStringUtil.getInteger(request.getParameter(key), 0));
			}
		}
		bisRunModel.setBisKey(bisKey);
		bisRunModel.setBisTable(bisTable);
		bisRunModel.setRunDatas(initDatas);
		bisRunModel.setRunVars(initVars);
		
		
		TeeFlowRunPrcs flowRunPrcs = null;
		try {
			flowRunPrcs = flowCreator.CreateNewWork(ft, person,bisRunModel,validation==1);
			flowRunPrcs.getFlowRun().setIsSave(1);
			flowRunService.update(flowRunPrcs.getFlowRun());
		} catch (Exception e) {
			e.printStackTrace();
			throw new TeeOperationException(e);
		}
		
		
		//处理初始化流程变量值
		Connection conn = null;
		try{
			conn = TeeDbUtility.getConnection();
			DbUtils dbUtils = new DbUtils(conn);
			Set<String> keys = initVars.keySet();
			for(String key0 : keys){
				String tmpKey = key0.substring(4,key0.length());
				dbUtils.executeUpdate("update FLOW_RUN_VARS set VAR_VALUE='"+initVars.get(key0)+"' where VAR_KEY='"+tmpKey+"' and RUN_ID="+flowRunPrcs.getFlowRun().getRunId());
			}
			conn.commit();
		}catch(Exception ex){
			conn.rollback();
			ex.printStackTrace();
		}finally{
			TeeDbUtility.closeConn(conn);
		}
		
		
		json.setRtState(true);
		Map params = new HashMap();
		params.put("runId", flowRunPrcs.getFlowRun().getRunId());
		params.put("flowId", flowRunPrcs.getFlowType().getSid());
		params.put("frpSid", flowRunPrcs.getSid());
		json.setRtData(params);
		return json;
	}
	
	
	/**
	 * 对外流程发起接口 http协议
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/createNewWork4Outer")
	public void createNewWork4Outer(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String sType = request.getParameter("FLOW_ID");
				sType = TeeStringUtil.fileEmptyString(sType, "0");//流程类型
		TeeJson json = new TeeJson();
		//创建人
		String createUser = TeeStringUtil.getString(request.getParameter("CREATE_USER"));
		TeePerson person = personService.getPersonByUserId(createUser);
		if(person==null){
			throw new TeeOperationException("流程发起人为空，无法发起流程");
		}
		
		TeeFlowTypeServiceInterface flowTypeService = flowServiceContext.getFlowTypeService();
		TeeFlowType ft	= flowTypeService.get(Integer.parseInt(sType));
		int validation = TeeStringUtil.getInteger(request.getParameter("VALIDATION"), 0);
		if(ft == null){
			throw new TeeOperationException("流程类型获取为空");
		}
		
		//处理接收流程发起数据相关
		TeeBisRunModel bisRunModel = new TeeBisRunModel();
		bisRunModel.setRunName(request.getParameter("RUN_NAME"));
		bisRunModel.setPreName(request.getParameter("PRE_NAME"));
		bisRunModel.setSufName(request.getParameter("SUF_NAME"));
		String bisKey = request.getParameter("bisKey");//获取业务键
		String bisTable = request.getParameter("bisTable");//获取业务表
		
		Map<String,String> initDatas = new HashMap();
		Map<String,String> initVars = new HashMap();
		
		Enumeration _enum = request.getParameterNames();
		String key = null;
		String val = null;
		while(_enum.hasMoreElements()){
			key = (String)_enum.nextElement();
			if(key.indexOf("DATA_")!=-1 || key.indexOf("EXT_")!=-1){//
				initDatas.put(key, request.getParameter(key));
			}else if(key.indexOf("VAR_")!=-1){
				initVars.put(key, request.getParameter(key));
			}else if(key.indexOf("DOC_ID")!=-1){
				bisRunModel.setDocId(TeeStringUtil.getInteger(request.getParameter(key), 0));
			}else if(key.indexOf("ATTACH_IDS")!=-1){
				bisRunModel.setAttachIds(TeeStringUtil.getString(request.getParameter(key)));
			}
		}
		bisRunModel.setBisKey(bisKey);
		bisRunModel.setBisTable(bisTable);
		bisRunModel.setRunDatas(initDatas);
		bisRunModel.setRunVars(initVars);
		
		
		TeeFlowRunPrcs flowRunPrcs = null;
		try {
			flowRunPrcs = flowCreator.CreateNewWork(ft, person,bisRunModel,validation==1);
			
			//遍历所上传的附件集合，将该集合里面的所有附件设置为该流程的公共附件
			List<TeeAttachment> attachList = new ArrayList();
			//判断是否是多媒体的request实例，然后获取文件
			if(request instanceof MultipartHttpServletRequest){
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
				Iterator<String> fileNamesIt = multipartRequest.getFileNames();
				while(fileNamesIt.hasNext()){
					String fileName = fileNamesIt.next();
					MultipartFile file = multipartRequest.getFile(fileName);
					if(file.getSize()==0){
						continue;
					}
					TeeAttachment attachment = baseUpload.singleAttachUpload(file.getInputStream(), file.getSize(), file.getOriginalFilename(), "", TeeAttachmentModelKeys.workFlow, String.valueOf(flowRunPrcs.getFlowRun().getRunId()), person);
					attachList.add(attachment);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new TeeOperationException(e);
		}
		
		json.setRtState(true);
		Map params = new HashMap();
		params.put("runId", flowRunPrcs.getFlowRun().getRunId());
		params.put("frpSid", flowRunPrcs.getSid());
		json.setRtData(params);
		
		
		//单点登录用
		BASE64Encoder encoder = new BASE64Encoder();
		String uid = person.getUserId();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		String expire = TeeDateUtil.format(c.getTime(), "yyyy-MM-dd HH:mm:ss");
		String token = TeePassEncryptMD5.crypt(uid+"ztoa"+expire);
		
		
		String redirect_url = TeeStringUtil.getString(request.getParameter("redirect_url"));
		if("WORKFLOW".equals(redirect_url)){//直接跳转到工作流办理页面
			redirect_url = "/system/core/workflow/flowrun/prcs/index.jsp?runId="+flowRunPrcs.getFlowRun().getRunId()+"&frpSid="+flowRunPrcs.getSid()+"&flowId="+flowRunPrcs.getFlowType().getSid()+"&view=1";
			response.sendRedirect("/sso/login.action?uid="+uid+"&expire="+encoder.encodeBuffer(expire.getBytes()).replace("\r\n", "")+"&token="+token+"&url="+encoder.encodeBuffer(redirect_url.getBytes()).replace("\r\n", ""));
		}else if(!"".equals(redirect_url)){//跳转到其他页面
			if(redirect_url.startsWith("http")){
				response.sendRedirect(redirect_url);
			}else{
				response.sendRedirect("/sso/login.action?uid="+uid+"&expire="+encoder.encodeBuffer(expire.getBytes()).replace("\r\n", "")+"&token="+token+"&url="+encoder.encodeBuffer(redirect_url.getBytes()).replace("\r\n", ""));
			}
		}else{
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write("<script>try{window.close();}catch(ex){};window.location = 'about:blank';</script>");
		}
		
	}
	
	
	
	/**
	 * 保存会签控件数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/saveCtrlFeedback")
	@ResponseBody
	public TeeJson saveCtrlFeedback(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int frpSid=TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		String content = TeeStringUtil.getString(request.getParameter("content"));
		String signData = TeeStringUtil.getString(request.getParameter("signData"));
		String sealData = TeeStringUtil.getString(request.getParameter("sealData"));
		String picData = TeeStringUtil.getString(request.getParameter("picData"));
		String h5Data = TeeStringUtil.getString(request.getParameter("h5Data"));
		String mobiData = TeeStringUtil.getString(request.getParameter("mobiData"));
		String hwData = TeeStringUtil.getString(request.getParameter("hwData"));
		int itemId = TeeStringUtil.getInteger(request.getParameter("itemId"), 0);
		String rand = TeeStringUtil.getString(request.getParameter("rand"));
		
		
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json.setRtState(true);
		flowServiceContext.getWorkflowService().saveCtrlFeedback(runId,itemId,content,signData,rand,person,sealData,hwData,frpSid,picData,h5Data,mobiData);
		
		return json;
	}
	
	/**
	 * 清空流程数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/clearRunDatas")
	@ResponseBody
	public TeeJson clearRunDatas(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json.setRtState(true);
//		flowServiceContext.getWorkflowService().saveCtrlFeedback(runId,itemId,content,signData,rand,person);
		flowRunService.clearRunDatasService(flowId);
		return json;
	}
	
	/**
	 * 获取会签控件数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getCtrlFeedbacks")
	@ResponseBody
	public TeeJson getCtrlFeedbacks(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		int itemId = TeeStringUtil.getInteger(request.getParameter("itemId"), 0);
		
		json.setRtState(true);
		json.setRtData(flowServiceContext.getWorkflowService().getCtrlFeedbacks(runId, itemId,1,"asc"));
		
		return json;
	}
	
	@RequestMapping(value="/delCtrlFeedbacks")
	@ResponseBody
	public TeeJson delCtrlFeedbacks(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtState(true);
		flowServiceContext.getWorkflowService().delCtrlFeedbacks(sid);
		return json;
	}

	/**
	 * 获取转交数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getHandlerData")
	@ResponseBody
	public TeeJson getHandlerData(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		Map requestData = TeeServletUtility.getParamMap(request);
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
//		json = workflowHandler.getHandlerData(runId, frpSid, person);
		json.setRtState(true);
		json.setRtData(flowDataFactory.getHandlerData(requestData, person));
		
		return json;
	}
	
	/**
	 * 获取转交数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getEditHtmlModel")
	@ResponseBody
	public TeeJson getEditHtmlModel(HttpServletRequest request){
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		return workflowHandler.getEditHtmlModel(runId, person);
	}
	
	/**
	 * 获取打印表单预览数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getFormPrintExplore")
	@ResponseBody
	public TeeJson getFormPrintExplore(HttpServletRequest request){
		TeeJson json = null;
		
		int formId = TeeStringUtil.getInteger(request.getParameter("formId"), 0);
		
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json = workflowHandler.getFormPrintExplore(formId, person);
		
		return json;
	}
	
	
	
	/**
	 * 预览历史版本
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getHistoryFormPrintExplore")
	@ResponseBody
	public TeeJson getHistoryFormPrintExplore(HttpServletRequest request){
		TeeJson json = null;
		
		int formId = TeeStringUtil.getInteger(request.getParameter("formId"), 0);
		
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json = workflowHandler.getHistoryFormPrintExplore(formId, person);
		
		return json;
	}
	
	
	/**
	 * 为归档获取流程附件
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getFlowRunAttaches4Archives")
	@ResponseBody
	public TeeJson getFlowRunAttaches4Archives(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json.setRtData(simpleDataLoader.getFlowRunAttaches4Archives(runId));
		return json;
	}
	
	/**
	 * 获取表单打印HTML相关数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getFormPrintData")
	@ResponseBody
	public TeeJson getFormPrintData(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		Map requestData = TeeServletUtility.getParamMap(request);
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json.setRtState(true);
		json.setRtData(simpleDataLoader.getFormPrintData(requestData, person));
		
		return json;
	}
	
	/**
	 * 流程操作，流程结束
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/turnEnd")
	@ResponseBody
	public TeeJson turnEnd(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		flowServiceContext.getFlowRunPrcsService().turnEnd(frpSid);
		
		TeeFlowRunPrcs currentFrp=flowRunPrcsService.get(frpSid);
		TeeFlowType flowType=flowTypeService.get(flowId);
		
		//处理自动归档
		int autoArchive=flowType.getAutoArchive();
		int autoArchiveValue=flowType.getAutoArchiveValue();
		Map reqData=new HashMap();
		reqData.put("loginUser", currentFrp.getPrcsUser());
		reqData.put("runId", currentFrp.getFlowRun().getRunId());
		if(autoArchive==1){//自动归档
			if((autoArchiveValue&1)==1){//表单
				 reqData.put("hasBd", 1);
			}else{
				reqData.put("hasBd", 0);
			}
			if((autoArchiveValue&2)==2){//签批单
				 reqData.put("hasQpd", 1);
			}else{
				reqData.put("hasQpd", 0);
			}
			if((autoArchiveValue&4)==4){//正文
				 reqData.put("hasZw", 1);
			}else{
				reqData.put("hasZw", 0);
			}
			if((autoArchiveValue&8)==8){//版式正文
				 reqData.put("hasBszw", 1);
			}else{
				reqData.put("hasBszw", 0);
			}
			if((autoArchiveValue&16)==16){//附件
				 reqData.put("hasFj", 1);
			}else{
				reqData.put("hasFj", 0);
			}
			
			damFilesService.workFlowArchive(reqData);
		}
		
		
		json.setRtState(true);
		json.setRtMsg("流程已结束");
		
		return json;
	}
	
	/**
	 * 获取流程数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getFlowRunDatas")
	@ResponseBody
	public TeeJson getFlowRunDatas(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		json.setRtState(true);
		json.setRtData(helper.getFlowRunData(runId));
		return json;
	}
	
	
	/**
	 * 获取流程表单数据  以formItem的title为键值
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getFlowRunDatasOnTitle")
	@ResponseBody
	public TeeJson getFlowRunDatasOnTitle(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		json.setRtState(true);
		json.setRtData(helper.getFlowRunDatasOnTitle(runId));
		return json;
	}
	
	
	@RequestMapping(value="/getHandlerOptPrivData")
	@ResponseBody
	public TeeJson getHandlerOptPrivData(HttpServletRequest request){
		TeeJson json = null;
		
		
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json = workflowHandler.getHandlerOptPrivData(runId, frpSid, person);
		
		
		return json;
	}
	
	/**
	 * 获取该步骤的直属上一步骤的已走过的步骤
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getPreReachablePrcsList")
	@ResponseBody
	public TeeJson getPreReachablePrcsList(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		json.setRtState(true);
		json.setRtData(flowDataFactory.getPreReachablePrcsList(frpSid, person));
		
		return json;
	}
	
	/**
	 * 获取之前所有步骤信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getPrePrcsList")
	@ResponseBody
	public TeeJson getPrePrcsList(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		json.setRtState(true);
		json.setRtData(flowDataFactory.getPrePrcsList(frpSid, person));
		
		return json;
	}
	
	
	/**
	 * 获取指定回退步骤
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getBackPrcs")
	@ResponseBody
    public TeeJson getBackPrcs(HttpServletRequest request){
		TeeJson json = new TeeJson();
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//步骤主键
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		//获取工作主键
		int runId= TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		//获取流程的主键
		int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		//System.out.println("流程主键"+flowId);
		json.setRtState(true);
		json.setRtData(flowDataFactory.getBackPrcs(frpSid,flowId,runId, person));
    	return json;
    }
	
	/**
	 * 获取当前步骤经办人
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getCurPrcsUsers")
	@ResponseBody
	public TeeJson getCurPrcsUsers(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		json.setRtState(true);
		json.setRtData(flowDataFactory.getCurPrcsUsers(frpSid, person));
		
		return json;
	}
	
	/**
	 * 添加经办人
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/addPrcsUser")
	@ResponseBody
	public TeeJson addPrcsUser(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestData = TeeServletUtility.getParamMap(request);
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		json.setRtState(true);
		json.setRtData(flowRunnerFactory.addPrcsUser(requestData, person));
		
		return json;
	}
	
	/**
	 * 回退上一步骤
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/backTo")
	@ResponseBody
	public TeeJson backTo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		Map requestData = TeeServletUtility.getParamMap(request);
		
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json.setRtData(flowRunnerFactory.backTo(requestData, loginPerson));
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 回退其他步骤
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/backToOther")
	@ResponseBody
	public TeeJson backToOther(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		Map requestData = TeeServletUtility.getParamMap(request);
		
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json.setRtData(flowRunnerFactory.backToOther(requestData, loginPerson));
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 回退指定步骤
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/backToFixed")
	@ResponseBody
	public TeeJson backToFixed(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		Map requestData = TeeServletUtility.getParamMap(request);
		
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json.setRtData(flowRunnerFactory.backToFixed(requestData, loginPerson));
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 保存流程表单数据信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/saveFlowRunData")
	@ResponseBody
	public TeeJson saveFlowRunData(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		//处理列表控件（插入中间表）
		try{
			workflowHandler.saveXlistData(runId,flowId,workflowHelper.getHttpRequestFlowRunDataMaps(request));
		}catch(Exception ex){}
		
		workflowHandler.saveFlowRunData(runId,flowId,frpSid, workflowHelper.getHttpRequestFlowRunDataMaps(request),TeeServletUtility.getParamMap(request));
		
		
		//修改流程的保存状态
		workflowHandler.updateSaveStatus(runId);
		
		json.setRtState(true);
		json.setRtMsg("保存成功");
		return json;
	}
	
	/**
	 * 修改流程表单数据信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/editFlowRunData")
	@ResponseBody
	public TeeJson editFlowRunData(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		workflowHandler.editFlowRunData(runId,flowId,frpSid, workflowHelper.getHttpRequestFlowRunDataMaps(request));
		json.setRtState(true);
		json.setRtMsg("保存成功");
		return json;
	}
	
	/**
	 * 转交下一步
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/turnNextHandler")
	@ResponseBody
	public TeeJson turnNextHandler(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		TeePerson sessionPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeePerson loginPerson = personService.selectByUuid(sessionPerson.getUuid()+"");
		Map requestData = TeeServletUtility.getParamMap(request);
		
		json.setRtData(flowRunnerFactory.turnNext(requestData, loginPerson));
		json.setRtState(true);
		
		return json;
	}
	
	@RequestMapping(value="/countersignHandler")
	@ResponseBody
	public TeeJson countersignHandler(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);//此prcsId 是在转交页面 做的 +1
		int flowPrcs = TeeStringUtil.getInteger(request.getParameter("flowPrcs"), 0);
		
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		json = workflowHandler.countersignHandler(runId, flowId, prcsId, flowPrcs,person);
		return json;
	}
	
	@RequestMapping(value="/turnEndHandler")
	@ResponseBody
	public TeeJson turnEndHandler(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);//此prcsId 是在转交页面 做的 +1
		int flowPrcs = TeeStringUtil.getInteger(request.getParameter("flowPrcs"), 0);
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		json = workflowHandler.turnEndFreeFlow(runId, flowId, prcsId, flowPrcs,person);
		return json;
	}
	
	@RequestMapping(value="/getTurnHandlerData")
	@ResponseBody
	public TeeJson getTurnHandlerData(HttpServletRequest request){
		TeeJson json = new TeeJson();
		Map requestData = TeeServletUtility.getParamMap(request);
		
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		
		
		Map data = flowDataFactory.getTurnHandlerData(requestData, loginPerson);
		
		int frpSid = TeeStringUtil.getInteger(requestData.get("frpSid"), 0);
		TeeFlowRunPrcs flowRunPrcs = flowRunPrcsService.get(frpSid);
		final int runId=flowRunPrcs.getFlowRun().getRunId();
		final TeeForm form=flowRunPrcs.getFlowRun().getForm();
		
		
		final String tableName="tee_f_r_d_"+flowRunPrcs.getFlowRun().getFlowType().getSid();
	    TeeFlowProcess process=flowRunPrcs.getFlowPrcs();
	    if(process!=null){
	    	//判断短信模板是不是为空
	    	String smsTpl=process.getSmsTpl();
	    	if(!TeeUtility.isNullorEmpty(smsTpl)){
	    		//先处理{当前用户}  {流程标题}
	    		smsTpl=smsTpl.replace("{当前用户}",loginPerson.getUserName()).replace("{流程标题}",flowRunPrcs.getFlowRun().getRunName()).replace("{流程发起人}",flowRunPrcs.getFlowRun().getBeginPerson().getUserName() );
	    		
	    		 TeeRegexpAnalyser analyser = new TeeRegexpAnalyser(); 
				 analyser.setText(smsTpl);
	    		 String newSmsTpl = analyser.replace(new String[] { "\\{DATA_[a-zA-Z0-9\\W]+\\}" },
							new TeeExpFetcher() {
									@Override
									public String parse(String pattern) {
										//pattern是满足正则的字符串截取内容
										//在这里去掉{DATA_ 和 }
										String ctrlTitle = pattern.replace("{DATA_", "").replace("}", "");
//										//查询控件
										
										List<TeeFormItem> ctrlList= workflowService.getFormItemsByFormAndCtrTitle(form,ctrlTitle);
												
										String value="";
										if(ctrlList!=null&&ctrlList.size()>0){//控件名称存在
											   TeeFormItem ctrl=ctrlList.get(0);
											   //获取控件的name  例如：DATA_1
											   String ctrlName=ctrl.getName();
											   //查询控件值
											   List result=workflowService.getCtrlValue(ctrlName,tableName,runId);
											   value=TeeStringUtil.getString(result.get(0));
											  
										  }else{
											  value="";
										  }
										
										
										return value;//此处是返回最新的字符串片段  也就是将原先匹配的{DATA_xxxx}替换成指定的字符串
									}

								});
	    		data.put("msg", newSmsTpl);
	    	}
	    }
		
		List<Map> prcsNodeInfos = (List<Map>) data.get("prcsNodeInfos");
		for(Map prcsNodeInfo:prcsNodeInfos){
			int prcsId = TeeStringUtil.getInteger(prcsNodeInfo.get("prcsId"), 0);
			TeeFlowProcess flowProcess = flowServiceContext.getFlowProcessService().load(prcsId);
			if(flowProcess.getAutoSelectFirst()==1){//如果只选择出第一个人
				
				
				String prcsUser = TeeStringUtil.getString(prcsNodeInfo.get("prcsUser"));
				String prcsUserDesc = TeeStringUtil.getString(prcsNodeInfo.get("prcsUserDesc"));
				
				List<TeePerson> pList = personService.getPersonByUuids(prcsUser);
				prcsUser = "";
				prcsUserDesc = "";
				
				for(int i=0;i<pList.size();i++){
					prcsUser+=pList.get(i).getUuid();
					prcsUserDesc+=pList.get(i).getUserName();
					
					if(i!=pList.size()-1){
						prcsUser+=",";
						prcsUserDesc+=",";
					}
				}
				
				if(prcsUser.indexOf(",")!=-1){
					prcsUser = prcsUser.split(",")[0];
					prcsUserDesc = prcsUserDesc.split(",")[0];
				}
				
				if(flowProcess.getOpFlag()==1){
					prcsNodeInfo.put("opUser", prcsUser);
					prcsNodeInfo.put("opUserDesc", prcsUserDesc);
					prcsNodeInfo.put("prcsUser", "");
					prcsNodeInfo.put("prcsUserDesc", "");
				}else{
					prcsNodeInfo.put("opUser", "");
					prcsNodeInfo.put("opUserDesc", "");
					prcsNodeInfo.put("prcsUser", prcsUser);
					prcsNodeInfo.put("prcsUserDesc", prcsUserDesc);
				}
				
			
			}
			prcsNodeInfo.put("showSelect", 1);
		}
		
		json.setRtData(data);
		json.setRtMsg("");
		json.setRtState(true);
		
		return json;
	}
	
	/**
	 * 预转交请求数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/preTurnHandlerData")
	@ResponseBody
	public TeeJson preTurnHandlerData(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		//获取请求map参数
		Map requestData = TeeServletUtility.getParamMap(request);
		
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		Map params = flowRunnerFactory.preTurnNext(requestData, loginPerson);
		
		
		json.setRtState(true);
		json.setRtData(params);
		return json;
	}
	/**
	 * 自由流转交
	 * @author zhp
	 * @createTime 2013-10-31
	 * @editTime 下午08:36:07
	 * @desc
	 */
	@RequestMapping(value="/turnFreeNext")
	@ResponseBody
	public TeeJson turnFreeNext(HttpServletRequest request){
		
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		try {
			freeFlowRunner.turnNext(request, loginPerson);
			json.setRtState(true);
			json.setRtMsg("转交成功!");
		} catch (Exception e) {
			e.printStackTrace();
			json.setRtState(false);
			json.setRtMsg("转交失败!");
		}
	
		return json;
	}
	
	
	/**
	 * 获取流程表单流
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getFlowRunFormStream")
	@ResponseBody
	public TeeJson getFlowRunFormStream(HttpServletRequest request,HttpServletResponse response){
		TeeJson json = new TeeJson();
		json.setRtState(true);
		Map requestData = TeeServletUtility.getParamMap(request);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		requestData.put("total", "0");
		String sb = simpleDataLoader.getFormPrintDataStream(requestData, loginPerson);
		json.setRtData(sb);
		return json;
	}
	
	/**
	 * 导出表单
	 * @param formId
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportFlowRun")
	@ResponseBody
	public void exportFlowRun(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map requestData = TeeServletUtility.getParamMap(request);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		requestData.put("view", 1);
		String sb = simpleDataLoader.getFormPrintDataStream(requestData, loginPerson);
		String runId = (String) requestData.get("runId");
		requestData.put("view", 0);
		Map data = simpleDataLoader.getFormPrintData(requestData, loginPerson);
		
		response.setCharacterEncoding("UTF-8");
		
		//创建临时下载文件夹
		//'   "    <  >   ~!@#$%^&*
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String nameDesc = "["+data.get("runId")+"]"+data.get("runName").toString().replace(":", "_").replace("\'", "").replace("\"", "").replace("<", "").replace(">", "").replace("~", "") 
		                  .replace("!", "").replace("@", "").replace("#", "").replace("$", "").replace("%", "")
		                  .replace("^", "").replace("&", "").replace("*", "");
		//导出签章控件
//		TeeFileUtility.copy(TeeSysProps.getRootPath()+"/system/core/workflow/websign/WebSign.dll", TeeSysProps.getTmpPath()+"/"+uuid);
		
		//获取流程正文
		TeeFlowRunDoc doc = flowServiceContext.getFlowRunDocService().getFlowRunDocByRunId(Integer.parseInt(runId));
		if(doc!=null){
			TeeBaseStream baseStream = baseDownloadService.getTeeBaseStream(doc.getDocAttach());
			InputStream in = baseStream.getFileInputStream();
			TeeFileUtility.readStreamToFile(in, new File(TeeSysProps.getTmpPath()+"/"+uuid+"/正文.doc"));
			baseStream.close();
		}
		//获取公共附件
		List<TeeAttachment> attaches = attachmentService.getAttaches(TeeAttachmentModelKeys.workFlow, runId);
		for(TeeAttachment attachment:attaches){
			TeeBaseStream baseStream = baseDownloadService.getTeeBaseStream(attachment);
			InputStream in = baseStream.getFileInputStream();
			TeeFileUtility.readStreamToFile(in, new File(TeeSysProps.getTmpPath()+"/"+uuid+"/公共附件/"+attachment.getFileName()));
			baseStream.close();
		}
//		//获取会签附件
//		flowServiceContext.getFeedbackService().get
		
		//复制表单
		StringInputStream sis = new StringInputStream(sb.toString(),"UTF-8");
		TeeFileUtility.readStreamToFile(sis, new File(TeeSysProps.getTmpPath()+"/"+uuid+"/表单.html"));
		sis.close();
		
		//生成zip文件
		TeeZipUtil.zip(TeeSysProps.getTmpPath()+"/"+uuid+"_/"+nameDesc+".zip", TeeSysProps.getTmpPath()+"/"+uuid);
		
		File zipFile = new File(TeeSysProps.getTmpPath()+"/"+uuid+"_/"+nameDesc+".zip");
		
		//将临时文件夹删除
		TeeFileUtility.deleteAll(TeeSysProps.getTmpPath()+"/"+uuid);
		
		response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(nameDesc, "UTF-8")
				.replace("+", " ")+".zip");
		response.setHeader("Accept-Ranges", "bytes");
		response.setHeader("Cache-Control", "maxage=3600");
		response.setHeader("Pragma", "public");
		response.setHeader("Accept-Length", String.valueOf(zipFile.length()));
		response.setHeader("Content-Length", String.valueOf(zipFile.length()));
		
		OutputStream output = response.getOutputStream();
		
		InputStream zipInputStream = new FileInputStream(new File(TeeSysProps.getTmpPath()+"/"+uuid+"_/"+nameDesc+".zip"));
		byte b[] = new byte[8192];
		int length = 0;
		try{
			while((length=zipInputStream.read(b))!=-1){
				output.write(b, 0, length);
				output.flush();
			}
		}catch(Exception e){
			
		}finally{
			zipInputStream.close();
		}
		
	}
	
	
	/**
	 * 批量导出表单
	 * @param formId
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportFlowRunBatch")
	@ResponseBody
	public void exportFlowRunBatch(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		Map requestData = TeeServletUtility.getParamMap(request);
		String runIds[] = TeeStringUtil.parseStringArray(request.getParameter("runIds"));
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

		String topUuid = UUID.randomUUID().toString().replace("-", "");
		for(String runId:runIds){
			requestData.put("view", 1);
			requestData.put("runId", runId);
			requestData.put("frpSid", null);
			String sb = simpleDataLoader.getFormPrintDataStream(requestData, loginPerson);
			requestData.put("view", 0);
			Map data = simpleDataLoader.getFormPrintData(requestData, loginPerson);
			
			//创建临时下载文件夹
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String nameDesc = "["+data.get("runId")+"]"+data.get("runName").toString().replace(":", "_").replace("\'", "").replace("\"", "").replace("<", "").replace(">", "").replace("~", "") 
	                  .replace("!", "").replace("@", "").replace("#", "").replace("$", "").replace("%", "")
	                  .replace("^", "").replace("&", "").replace("*", "");
			
			//获取流程正文
			TeeFlowRunDoc doc = flowServiceContext.getFlowRunDocService().getFlowRunDocByRunId(Integer.parseInt(runId));
			if(doc!=null){
				TeeBaseStream baseStream = baseDownloadService.getTeeBaseStream(doc.getDocAttach());
				InputStream in = baseStream.getFileInputStream();
				TeeFileUtility.readStreamToFile(in, new File(TeeSysProps.getTmpPath()+"/"+uuid+"/正文.doc"));
				baseStream.close();
			}
			//获取公共附件
			List<TeeAttachment> attaches = attachmentService.getAttaches(TeeAttachmentModelKeys.workFlow, runId);
			for(TeeAttachment attachment:attaches){
				TeeBaseStream baseStream = baseDownloadService.getTeeBaseStream(attachment);
				InputStream in = baseStream.getFileInputStream();
				TeeFileUtility.readStreamToFile(in, new File(TeeSysProps.getTmpPath()+"/"+uuid+"/公共附件/"+attachment.getFileName()));
				baseStream.close();
			}
			
			//复制表单
			StringInputStream sis = new StringInputStream(sb.toString(),"UTF-8");
			TeeFileUtility.readStreamToFile(sis, new File(TeeSysProps.getTmpPath()+"/"+uuid+"/表单.html"));
			sis.close();
			
			//生成zip文件
			TeeZipUtil.zip(TeeSysProps.getTmpPath()+"/"+topUuid+"/"+nameDesc+".zip", TeeSysProps.getTmpPath()+"/"+uuid);
			
			//将临时文件夹删除
			TeeFileUtility.deleteAll(TeeSysProps.getTmpPath()+"/"+uuid);
		}
		
		String fileName = TeeDateUtil.format(new Date(), "yyyyMMddHHmmsssss")+"_Exp.zip";
		
		TeeZipUtil.zip(TeeSysProps.getTmpPath()+"/"+topUuid+"_/"+fileName, TeeSysProps.getTmpPath()+"/"+topUuid);
		File zipFile = new File(TeeSysProps.getTmpPath()+"/"+topUuid+"_/"+fileName);
		TeeFileUtility.deleteAll(TeeSysProps.getTmpPath()+"/"+topUuid);
		
		response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8")
				.replace("+", " "));
		response.setHeader("Accept-Ranges", "bytes");
		response.setHeader("Cache-Control", "maxage=3600");
		response.setHeader("Pragma", "public");
		response.setHeader("Accept-Length", String.valueOf(zipFile.length()));
		response.setHeader("Content-Length", String.valueOf(zipFile.length()));
		
		OutputStream output = response.getOutputStream();
		
		InputStream zipInputStream = new FileInputStream(new File(TeeSysProps.getTmpPath()+"/"+topUuid+"_/"+fileName));
		byte b[] = new byte[8192];
		int length = 0;
		try{
			while((length=zipInputStream.read(b))!=-1){
				output.write(b, 0, length);
				output.flush();
			}
		}catch(Exception e){
			
		}finally{
			zipInputStream.close();
			TeeFileUtility.deleteAll(TeeSysProps.getTmpPath()+"/"+topUuid);
		}
		
	}
	
	
	/**
	 * 流程查阅
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/flowRunLookup")
	@ResponseBody
	public TeeJson flowRunLookup(HttpServletRequest request){
		
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		flowRunViewService.viewLookup(runId, loginPerson.getUuid());
		
		json.setRtState(true);
		json.setRtMsg("已查阅");
		
		return json;
	}
	
	
	/**
	 * 根据步骤获取盖章规则
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getSealRulesByPrcs")
	@ResponseBody
	public TeeJson getSealRulesByPrcs(HttpServletRequest request){
		
		TeeJson json = new TeeJson();
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		int aipId = TeeStringUtil.getInteger(request.getParameter("aipId"), 0);
		TeeFlowRunPrcs flowRunPrcs = flowRunPrcsService.get(frpSid);
		TeeFlowProcess flowPrcs = flowRunPrcs.getFlowPrcs();
		
		List<Map<String,String>> maps = TeeJsonUtil.JsonStr2MapList(flowPrcs.getSealRules());
		Iterator it = maps.iterator();
		while(it.hasNext()){
			Map<String,String> m = (Map) it.next();
			if(!m.get("aipId").equals(aipId+"")){
				it.remove();
			}
		}
		
		json.setRtData(maps);
		return json;
	}
	
	
	/**
	 * 添加远程盖章，返回pdf文件格式
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/addRemoteSeal")
	@ResponseBody
	public TeeJson addRemoteSeal(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		String sealId = request.getParameter("sealId");//盖章规则id
		String attachId = request.getParameter("attachId");//需要盖章的附件id
		
		//获取附件文件
		TeeAttachment attach = attachmentService.getById(Integer.parseInt(attachId));
		attach.setAttachmentName(attach.getAttachmentName().replace(".aip", ".pdf"));
		attach.setFileName(attach.getFileName().replace(".aip", ".pdf"));
		attach.setExt("pdf");
		
		
		String uuid = UUID.randomUUID().toString().replace("-", "")+".pdf";
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		try {  
	        String result = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
	        		+ "<SealDocRequest>"
	        		+ "		<BASE_DATA>"
	        		+ "		        <!--应用系统id写死-->"
					+ "			<SYS_ID>sysId</SYS_ID>"
					+ "		        <!--用户id写死-->"
					+ "			<USER_ID>admin</USER_ID>"
	//						+ "		        <!--用户密码写死-->"
	//						+ "			<USER_PSD>admin</USER_PSD>"
					+ "		</BASE_DATA>"
					+ "		<META_DATA>"
					+ "		     <!--是否模板合并写死-->"
					+ "		    <IS_MERGER>false</IS_MERGER>"
					+ "		   </META_DATA>"
					+ "		<FILE_LIST>"
					+ "		   <TREE_NODE>"
					+ "		        <!--文档名称-->"
					+ "		        <FILE_NO>"+uuid+"</FILE_NO>"
					+ "		        <!--是否加二维码-->"
					+ "		        <IS_CODEBAR>false</IS_CODEBAR>"
					+ "		        <!--固定写死空-->"
					+ "		        <CERT_CODEBAR></CERT_CODEBAR>"
					+ "		        <!--二维码类型1:p417,0:QR-->"
					+ "		        <CODEBAR_TYPE>0</CODEBAR_TYPE>"
					+ "		        <!--二维码编辑信息-->"
					+ "			<CODEBAR_DATA>000000000000000000</CODEBAR_DATA>"
					+ "		        <!--二维码左右偏移-->"
					+ "		        <X_COORDINATE>30000</X_COORDINATE>"
					+ "		        <!--二维码上下偏移-->"
					+ "			<Y_COORDINATE>5000</Y_COORDINATE>"
					+ "		        <!--二维码大小-->"
					+ "		        <CODEBAR_SIZE>100</CODEBAR_SIZE>"
					+ "		        <!--二维码加盖页码1,2,3,-1是最后一页0是所有页-->"
					+ "		        <CODEBAR_PAGE>0</CODEBAR_PAGE>"
					+ "		        <SEAL_TYPE>0</SEAL_TYPE>"
					+ "		        <RULE_TYPE>0</RULE_TYPE>"
					+ "		        <!--规则号，多个规则用逗号隔开-->"
					+ "		        <RULE_NO>"+sealId+"</RULE_NO>"
					+ "		        <!--应用场景data是模板数据合成，file是读取FILEPATH文件写死-->"
					+ "		        <CJ_TYPE>file</CJ_TYPE>"
					+ "		        <!--固定写死空-->"
					+ "		        <REQUEST_TYPE>0</REQUEST_TYPE>"
					+ "		        <!--读取文件路径-->"
					+ "		        <FILE_PATH>"+TeeSysProps.getString("CURRENT_ADDRESS")+"/imAttachment/downFile.action?id="+attachId+"&vt="+c.getTimeInMillis()+"</FILE_PATH>"
					+ "		        <!--模板名称-->"
					+ "		        <MODEL_NAME>模板1</MODEL_NAME>"
					+ "		        <AREA_SEAL>false</AREA_SEAL>"
					+ "		        <!--模板数据-->"
//					+ "		        <APP_DATA>"
//					+ "		        <Info>"
//					+ "		         <name></name>"
//					+ "		        </Info>"
//					+ "		       </APP_DATA>"
					+ "		    </TREE_NODE>"
					+ "		</FILE_LIST>"
					+ "		</SealDocRequest>";
	        String wsdlUrl = TeeSysProps.getString("DJ_ADDRESS")+"/Seal/services/SealService?wsdl";
			String nameSpaceUri = "http://serv";
			Service service = new Service();
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(wsdlUrl));
			call.setOperationName(new QName(nameSpaceUri, "sealAutoPdf"));
			String s = (String) call.invoke(new Object[] {result});
			
			int start = s.indexOf("<FILE_MSG>")+"<FILE_MSG>".length();
			int end = s.indexOf("</FILE_MSG>");
			s = s.substring(start, end);
			if(s.contains("http")){
				//将文件下载并覆盖到原文件
				HttpClientUtil.download(null, null, s, attach.getFilePath());
				attachmentService.updateAttachment(attach);
			}
			
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
		
		
		return json;
	}
	
	/**
	 * 根据ID删除流程步骤实例
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delFlowRunPrcsById")
	@ResponseBody
	public TeeJson delFlowRunPrcsById(HttpServletRequest request){
		
		TeeJson json = new TeeJson();
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		flowRunPrcsService.delete(frpSid);
		json.setRtState(true);
		
		return json;
	}
	
	/**
	 * 流程催办
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/flowRunUrge")
	@ResponseBody
	public TeeJson flowRunUrge(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		flowRunViewService.flowRunUrge(runId, loginPerson);
		json.setRtState(true);
		json.setRtMsg("已催办");
		return json;
	}
	

	/**
	 * 获取经办人过滤字符串
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getPrcsUsersFilter")
	@ResponseBody
	public TeeJson getPrcsUsersFilter(HttpServletRequest request){
		
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		
		json.setRtState(true);
		json.setRtData(flowDataFactory.getPrcsUserFilter(frpSid, loginPerson));
		
		return json;
	}
	
	/**
	 * 获取当前办理人、当前流程下的历史数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getHistoryFlowRunDatas")
	@ResponseBody
	public TeeEasyuiDataGridJson getHistoryFlowRunDatas(HttpServletRequest request,TeeDataGridModel dm){
		
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		int itemId = TeeStringUtil.getInteger(request.getParameter("itemId"), 0);
		
		return workflowHelper.getHistoryFlowRunDatas(itemId,frpSid,loginPerson,dm);
	}
	
	/**
	 * 上传list控件对应的excel文件
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping("/uploadXlistDatas")
	public void commonUpload(HttpServletRequest request,HttpServletResponse response) throws IOException{
		/*设置字符集为'UTF-8'*/
        response.setCharacterEncoding("UTF-8"); 
        response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String itemId = multipartRequest.getParameter("itemId");
		MultipartFile file = multipartRequest.getFile("file");
		Map map = new HashMap();
		map.put("itemId", itemId);
		
		try {
			ArrayList<TeeDataRecord> dbL = TeeAOPExcleUtil.readExc(file.getInputStream(), true);
			List datas = new ArrayList();
			map.put("state", true);
			map.put("datas", datas);
			for(int i=0;i<dbL.size();i++){
				TeeDataRecord dr = dbL.get(i);
				Map m = new HashMap();
				for(int j=0;j<dr.getFieldCnt();j++){
					m.put(dr.getNameByIndex(j), dr.getValueByIndex(j));
				}
				datas.add(m);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("state", false);
		}finally{
			file.getInputStream().close();
		}
		out.print("<script>parent.doUploadXlistDataCallback("+TeeJsonUtil.mapToJson(map)+");</script>");
	}
	
	/**
	 * 下载列表控件对应的上传数据模版
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping("/downloadXlistTemplate")
	public void downloadXlistTemplate(HttpServletRequest request,HttpServletResponse response) throws IOException{
		int itemId = TeeStringUtil.getInteger(request.getParameter("itemId"), 0);
		TeeFormItem formItem = flowFormService.getFormItemById(itemId);
		String model = formItem.getModel();
		List<Map<String,String>> models = TeeJsonUtil.JsonStr2MapList(model);
		ArrayList<TeeDataRecord> dataArray = new ArrayList();
		TeeDataRecord dataRecord = new TeeDataRecord();
		for(Map<String,String> datas:models){
			dataRecord.addField(datas.get("title"), "");
		}
		
		dataArray.add(dataRecord);
		String fileName = formItem.getTitle()+"_导入模版.xls";
		response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/msexcel;charset=UTF-8");
		try {
			TeeAOPExcleUtil.writeExc(response.getOutputStream(), dataArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 检查会签意见必填
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/checkFbRequired")
	@ResponseBody
	public TeeJson checkFbRequired(HttpServletRequest request){
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		int frpSid=TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);

		return flowRunViewService.checkFbRequired(runId, loginPerson,frpSid);
	}
	
	
	
	/**
	 * 流程传阅   ----每个步骤都可以传阅
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/view")
	@ResponseBody
	public TeeJson view(HttpServletRequest request){
		return workflowHandler.view(request);
	}
	
	/**
	 * 根据frpSid获取runId和当前prcsId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getInfosByFrpSid")
	@ResponseBody
	public TeeJson getInfosByFrpSid(HttpServletRequest request){
		return workflowHandler.getInfosByFrpSid(request);
	}
	
	
	/**
	 * 根据流程主键  获取当前流程 我办理的所有的步骤信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getMyHistoryStepByRunId")
	@ResponseBody
	public TeeJson getMyHistoryStepByRunId(HttpServletRequest request){
		return flowRunPrcsService.getMyHistoryStepByRunId(request);
	}
	
	
	
	/**
	 * 工作监控  ----   流程干预
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/intervention")
	@ResponseBody
	public TeeJson intervention(HttpServletRequest request){
		return workflowHandler.intervention(request);
	}
	
	
	
	/**
	 * 根据主键获取flowRunPrcs详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getFlowRunPrcsBySid")
	@ResponseBody
	public TeeJson getFlowRunPrcsBySid(HttpServletRequest request){
		return flowRunPrcsService.getFlowRunPrcsBySid(request);
	}
	
	
	/**
	 * 新方法实现word打印模板(xml文件)    
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/freeMarker")
	@ResponseBody
	public void freeMarker(HttpServletRequest request,HttpServletResponse response) throws Exception{
		int attachId=TeeStringUtil.getInteger(request.getParameter("attachId"), 0);
		int runId=TeeStringUtil.getInteger(request.getParameter("runId"),0);
		

		TeeFlowRun flowRun=flowRunService.get(runId);
		TeeAttachment attachment=attachmentService.getById(attachId);
		//获取打印模板
		Configuration config = new Configuration();
		StringTemplateLoader templateLoader = new StringTemplateLoader();
		templateLoader.putTemplate(attachId+"", FileUtils.readFileToString(new File(attachment.getFilePath()),"UTF-8"));
		config.setTemplateLoader(templateLoader);
		config.setDefaultEncoding("UTF-8");
        Template template = config.getTemplate(attachId+"");
        //获取流程数据{DATA_1=XXX,DATA_2=XXX}
        Map dataMap=workflowHelper.getFlowRunData(runId);
        List<TeeFormItem>formItemList=flowFormService.listFormItems(flowRun.getForm());        
        Map data=new HashMap<String, String>();//需要映射到word中的数据
        for (Object key : dataMap.keySet()) {
        	key=TeeStringUtil.getString(key);
			for (TeeFormItem item:formItemList) {
				if((key).equals(item.getName())){//列表   
					if(("xlist").equals(item.getXtype())){
						data.put(item.getName(),TeeJsonUtil.JsonStr2MapList(dataMap.get(key).toString()));
					}else if(("xfeedback").equals(item.getXtype())){//会签控件
						//表单控件分析
						TeeHTMLTag tag = new TeeHTMLInputTag();
						tag.analyse(item.getContent());
						//获取控件属性
						Map<String,String> attrs = tag.getAttributes();
						String dateFormat=TeeStringUtil.getString(attrs.get("dateformat"));
						if(("").equals(dateFormat)){
							dateFormat="yyyy年MM月dd日  HH时mm分";
						}
						SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
						//获取该流程该控件的会签意见
						int sortField=TeeStringUtil.getInteger(attrs.get("sortfield"),1);//排序字段
						String order=attrs.get("order");//排序方式
						int itemId = item.getItemId();
						List<Map> list = workflowService.getCtrlFeedbacks(runId, itemId,sortField,order);
					    if(list!=null&&list.size()>0){
					    	for (Map map : list) {
								if(!TeeUtility.isNullorEmpty(map.get("createTime"))){
									map.put("createTime", sdf.format((Date)map.get("createTime")));
								}else{
									map.put("createTime","");
								}
							}
					    }
					    data.put(item.getName(),list);
					}else if(("xcheckbox").equals(item.getXtype())){
						int value=TeeStringUtil.getInteger(dataMap.get(key), 0);
						if(value==1){
							data.put(item.getTitle(),"☑");
						}else{
							data.put(item.getTitle(),"☒");
						}
						
					}else if(("ximg").equals(item.getXtype())){//图片上传控件
						int attId=TeeStringUtil.getInteger(dataMap.get(key), 0);
					    TeeAttachment image=attachmentService.getById(attId);
					    InputStream is = new FileInputStream(image.getFilePath());
				        BASE64Encoder encoder = new BASE64Encoder();
				        byte[] d = new byte[is.available()];
				        is.read(d);
				        is.close();
				        data.put(item.getTitle(),encoder.encode(d));
					    
					
					}else{
					
						data.put(item.getTitle(),dataMap.get(key));
					}
				}
			}
		}
        
        
        response.setContentType("application/octet-stream");
		response.setHeader("Cache-control", "private");
		response.setHeader("Accept-Ranges", "bytes");
		response.setHeader("Cache-Control", "maxage=3600");
		response.setHeader("Pragma", "public");
		
		response.setHeader("Content-disposition", "attachment; filename=\""
				+ URLEncoder.encode("template.doc", "UTF-8") + "\"");
        
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream,"UTF-8");
        template.process(data, outputStreamWriter);
        
        response.setHeader("Accept-Length", String.valueOf(byteArrayOutputStream.size()));
		response.setHeader("Content-Length", String.valueOf(byteArrayOutputStream.size()));
        
		response.getOutputStream().write(byteArrayOutputStream.toByteArray());
        
		/*baos = new FileOutputStream(target);
	    out = new OutputStreamWriter(baos,"UTF-8");
	    
	    
	    //输出参数
	    template.process(itemMap, out);
	    out.flush();
	    out.close();
*/
		
	}
	
	/**
	 * 根据流程排序号获取流程数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFlowRunList")
	@ResponseBody
	public TeeEasyuiDataGridJson getFlowRunListByFlowId(HttpServletRequest request,TeeDataGridModel dataGridModel,TeeFlowRunModel queryModel){
		TeeEasyuiDataGridJson dataGridJson = null;
		try {
			dataGridJson = flowRunService.getFlowRunListByFlowId(request,dataGridModel,queryModel);
		} catch (Exception e1) {
			e1.printStackTrace();
			dataGridJson = new TeeEasyuiDataGridJson();
			dataGridJson.setRows(new ArrayList());
			dataGridJson.setTotal(Long.parseLong("0"));
		}
		return dataGridJson;
	}
	
	@RequestMapping("/copyBodyAttachement")
	@ResponseBody
	public TeeJson copyBodyAttachement(HttpServletRequest request){
		return flowRunService.copyBodyAttachement(request);
	}
	
	
	/**
	 * 根据frpSid判断当前步骤是否允许归档
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkIsArchive")
	@ResponseBody
	public TeeJson checkIsArchive(HttpServletRequest request){
		return flowRunService.checkIsArchive(request);
	}
	
	
	/**
	 * 数据同步  发文管理
	 * @param request
	 * @return
	 */
	@RequestMapping("/dataSync")
	@ResponseBody
	public TeeJson dataSync(HttpServletRequest request){
		return flowRunService.dataSync(request);
	}
	
	
	
	/**
	 * 数据同步  签报管理
	 * @param request
	 * @return
	 */
	@RequestMapping("/dataSync1")
	@ResponseBody
	public TeeJson dataSync1(HttpServletRequest request){
		return flowRunService.dataSync1(request);
	}

	/**
	 * 获取步骤号
	 * */
	@ResponseBody
	@RequestMapping("/findPrcsId")
	public TeeJson findPrcsId(HttpServletRequest request){
		return flowRunService.findPrcsId(request);
	}
	
	//修改第一步
	@ResponseBody
	@RequestMapping("/updateEndTimeByPrcsId")
	public TeeJson updateEndTimeByPrcsId(HttpServletRequest request){
		return flowRunService.updateEndTimeByPrcsId(request);
	}

	
	
	
	/**
	 *根据runId  获取flowType的文档类型
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDocTypeByRunId")
	@ResponseBody
	public TeeJson getDocTypeByRunId(HttpServletRequest request){
		return flowRunService.getDocTypeByRunId(request);
	}
	
	/**
	 * 
	 * 克隆附件到指定流程
	 * */
	@ResponseBody
	@RequestMapping("/keLongAttachment")
	public TeeJson keLongAttachment(HttpServletRequest request){
		return workflowService.keLongAttachment(request);
	}

}
