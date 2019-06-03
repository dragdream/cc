package com.tianee.oa.core.workflow.flowmanage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.JDOMException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowSort;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowSortServiceInterface;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowSeqServiceInterface;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.util.workflow.TeeWorkflowHelperInterface;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowTypeModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/flowType")
public class TeeFlowTypeController {
	/**
	 * 注册flowFormItemService服务
	 */
	@Autowired
	private TeeFlowTypeServiceInterface flowTypeService;
	
	@Autowired
	private TeeFlowSortServiceInterface flowSortService;
	
	@Autowired
	private TeeFlowFormServiceInterface flowFormService;
	
	@Autowired
	private TeeDeptService deptService;
	
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private TeeBaseUpload upload;
	
	@Autowired
	private TeeWorkflowHelperInterface workflowHelper;
	
	@Autowired
	private TeeFlowSeqServiceInterface flowSeqService;
	
	/**
	 * 获取表单树
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getTreeData")
	@ResponseBody
	public TeeJson getTreeData(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		
		//获取当前登录的用户
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		List<TeeZTreeModel> list = new ArrayList<TeeZTreeModel>();
		int id = TeeStringUtil.getInteger(request.getParameter("id"), 0);
		
		if(id==0){//获取分类节点
			//添加默认节点
			TeeZTreeModel m = new TeeZTreeModel();
			m.setId("-1");
			m.setName("默认分类");
			m.setParent(true);
			m.getParams().put("type", 1);//分类节点
			m.setIconSkin("wfNode1");
			m.setNocheck(true);
			list.add(m);
			
			List<TeeFlowSort> sortList = flowSortService.list();
			for(TeeFlowSort flowSort:sortList){
				m = new TeeZTreeModel();
				m.setId(String.valueOf(flowSort.getSid()));
				m.setName(flowSort.getSortName());
				m.setParent(true);
				m.getParams().put("type", 1);//分类节点
				m.setIconSkin("wfNode1");
				m.setNocheck(true);
				list.add(m);
			}
		}else{
			List<TeeFlowType> flowList = flowTypeService.findByFlowSort1(id,loginUser);
			for(TeeFlowType flowType:flowList){
				TeeZTreeModel m = new TeeZTreeModel();
				m.setId(String.valueOf(flowType.getSid()));
				m.setName(flowType.getFlowName());
				m.setParent(false);
				m.getParams().put("type", 2);//表单节点
				m.setIconSkin("wfNode2");
				list.add(m);
			}
		}
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(list);
		
		return json;
	}
	
	/**
	 * 通过表单分类获取表单
	 * @param sortId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getFlowListBySort")
	@ResponseBody
	public TeeJson getFlowListBySort(Integer sortId) throws Exception {
		TeeJson json = new TeeJson();
		List<TeeFlowTypeModel> list = new ArrayList<TeeFlowTypeModel>();
		
		List<TeeFlowType> flowList = flowTypeService.findByFlowSort(sortId);
		for(TeeFlowType flowType:flowList){
			TeeFlowTypeModel m = new TeeFlowTypeModel();
			BeanUtils.copyProperties(flowType, m);
			m.setFlowSortId(flowType.getFlowSort()==null?0:flowType.getFlowSort().getSid());
			m.setFormId(flowType.getForm()==null?0:flowType.getForm().getSid());
			list.add(m);
		}
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(list);
		
		return json;
	}
	
	/**
	 * 通过表单分类获取表单
	 * @param sortId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/flowCopy")
	@ResponseBody
	public TeeJson flowCopy(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		List<TeeFlowTypeModel> list = new ArrayList<TeeFlowTypeModel>();
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		flowTypeService.flowCopy(flowId);
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData("复制成功");
		
		return json;
	}
	
	/**
	 * 数据列表专用方法
	 * @param m
	 * @param sortId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel m,Integer sortId,HttpServletRequest request) throws Exception {
		//获取当前登录的用户
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson json = flowTypeService.datagrid(m, sortId,loginUser);
		return json;
	}
	
	
	/**
	 * 获取流程数据
	 * @param flowTypeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/get")
	@ResponseBody
	public TeeJson get(@RequestParam(value="flowTypeId") Integer flowTypeId) throws Exception {
		TeeJson json = new TeeJson();
		TeeFlowType flowType = flowTypeService.get(flowTypeId);
		TeeFlowTypeModel m = new TeeFlowTypeModel();
		BeanUtils.copyProperties(flowType, m);
		m.setFlowSortId(flowType.getFlowSort()==null?0:flowType.getFlowSort().getSid());
		m.setFormId(flowType.getForm()==null?0:flowType.getForm().getSid());
		
		//封装所属部门信息
		if(flowType.getDept()!=null){
			m.setDeptId(flowType.getDept().getUuid());
			m.setDeptName(flowType.getDept().getDeptName());
		}
		//获取已发起工作数
		int count = flowTypeService.getTheTotleOfFlowRunByFlowId(flowTypeId);
		m.setTotalWorkNum(count);
		
		String formName = flowType.getForm()==null?"":flowType.getForm().getFormName();
		//获取流程绑定表单的名称
		m.setFormName(formName);
		
		//获取类型定义
		m.setTypeDesc((flowType.getType()==1?"固定流程":"自由流程"));
		
		//获取流程序列编号
		m.setNumbering(flowSeqService.getFlowTypeNumbering(flowType.getSid()));
		
		//保存传阅人
		if(flowType.getViewPriv() == 1){//允许传阅
			String viewPersonNames = "";
			String viewPersonIds = "";
			Set<TeePerson> viewPersons = flowType.getViewPersons();
			Iterator<TeePerson> it = viewPersons.iterator();
			while(it.hasNext()){
				TeePerson person = it.next();
				viewPersonIds =  viewPersonIds + person.getUuid() + ",";
				viewPersonNames = viewPersonNames + person.getUserName() + ",";
			}
			m.setViewPersonIds(viewPersonIds);
			m.setViewPersonNames(viewPersonNames);
		
		}
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(m);
		
		return json;
	}
	
	/**
	 * 创建流程记录
	 * @param formName
	 * @param sortId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@ResponseBody
	public TeeJson save(TeeFlowType flowType,HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		//获取当前登录的用户
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//接收额外参数
		int formId = TeeStringUtil.getInteger(request.getParameter("formId"),0);
		int flowSortId = TeeStringUtil.getInteger(request.getParameter("flowSortId"),0);
		String viewPersonsIds [] = TeeStringUtil.parseStringArray(request.getParameter("viewPersonsIds"));
		String deptId = TeeStringUtil.getString(request.getParameter("deptId"));
		String outerPage = TeeStringUtil.getString(request.getParameter("outerPage"));
		int hasDoc = TeeStringUtil.getInteger(request.getParameter("hasDoc"),0);
		int runNamePriv = TeeStringUtil.getInteger(request.getParameter("runNamePriv"),0);
		
		//int deptId=TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
		
		TeeForm form = flowFormService.getForm(formId);
		TeeFlowSort flowSort = flowSortId==0?null:flowSortService.get(flowSortId);
		
		flowType.setForm(form);
		flowType.setFlowSort(flowSort);
		flowType.setHasDoc(hasDoc);
		flowType.setOuterPage(outerPage);
		flowType.setRunNamePriv(runNamePriv);
		
		//获取所属部门
		TeeDepartment dept =null;
		if(TeeUtility.isNullorEmpty(deptId)){//如果为空  则获取当前登录用户的部门
			dept=loginUser.getDept();
		}else{
			dept = deptService.selectDeptByUuid(deptId);
		}
		
		flowType.setDept(dept);
		//保存传阅人
		if(flowType.getViewPriv() == 1){//允许传阅
			String viewPersonNames = request.getParameter("viewPersonNames");
			String viewPersonIds = request.getParameter("viewPersonIds");
			if(!TeeUtility.isNullorEmpty(viewPersonIds)){
				List<TeePerson> viewPerson = personService.getPersonByUuids(viewPersonIds);
				Set<TeePerson >viewPersons = new HashSet<TeePerson>(viewPerson);
				flowType.setViewPersons(viewPersons);
			}
		}
		
		//保存流程
		flowTypeService.createFlowTypeService(flowType);
		
		//创建实体表
		workflowHelper.createFlowRunDataTable(flowType);
		
		//创建流程编号序列
		flowSeqService.createFlowTypeNumbering(flowType.getSid());
		
		json.setRtState(TeeConst.RETURN_OK);
		
		return json;
	}
	
	/**
	 * 更新流程信息
	 * @param flowName
	 * @param sortId
	 * @param formId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update")
	@ResponseBody
	public TeeJson update(TeeFlowType flowType,HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		
		//接收额外参数
		int formId = TeeStringUtil.getInteger(request.getParameter("formId"),0);
		int flowSortId = TeeStringUtil.getInteger(request.getParameter("flowSortId"),0);
		TeeForm form = flowFormService.getForm(formId);
		TeeFlowSort flowSort = flowSortId==0?null:flowSortService.get(flowSortId);
		String outerPage = TeeStringUtil.getString(request.getParameter("outerPage"));
		int hasDoc = TeeStringUtil.getInteger(request.getParameter("hasDoc"),0);
		int runNamePriv = TeeStringUtil.getInteger(request.getParameter("runNamePriv"),0);
		//封装部门信息
		int deptId=TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
		TeeDepartment dept=null;
		if(deptId!=0){
			dept=deptService.get(deptId);
		}
		
		//获取原流程信息
		TeeFlowType oFlowType = flowTypeService.get(flowType.getSid());
		//封装部门信息
		oFlowType.setDept(dept);
		
		oFlowType.setComment(flowType.getComment());
		oFlowType.setDelegate(flowType.getDelegate());
		oFlowType.setEditTitle(flowType.getEditTitle());
		oFlowType.setFileCodeExpression(flowType.getFileCodeExpression());
		oFlowType.setFlowName(flowType.getFlowName());
		oFlowType.setFlowSort(flowSort);
		oFlowType.setForm(form);
		oFlowType.setNumbering(flowType.getNumbering());
		oFlowType.setNumberingLength(flowType.getNumberingLength());
		oFlowType.setOrderNo(flowType.getOrderNo());
		oFlowType.setType(flowType.getType());
		oFlowType.setViewPriv(flowType.getViewPriv());
		oFlowType.setAttachPriv(flowType.getAttachPriv());
		oFlowType.setFeedbackPriv(flowType.getFeedbackPriv());
		oFlowType.setHasDoc(hasDoc);
		oFlowType.setFreePreset(flowType.getFreePreset());
		oFlowType.setRunNamePriv(runNamePriv);
		oFlowType.setOuterPage(outerPage);
		oFlowType.setAutoArchive(flowType.getAutoArchive());
		oFlowType.setAutoArchiveValue(flowType.getAutoArchiveValue());
		
		Set<TeePerson >viewPersons = new HashSet<TeePerson>();
		//保存传阅人
		if(flowType.getViewPriv() == 1){//允许传阅
			String viewPersonIds = request.getParameter("viewPersonIds");
			if(!TeeUtility.isNullorEmpty(viewPersonIds)){
				List<TeePerson> viewPerson = personService.getPersonByUuids(viewPersonIds);
				viewPersons = new HashSet<TeePerson>(viewPerson);
			}
		}
		oFlowType.setViewPersons(viewPersons);
		
		flowTypeService.updateFlowTypeService(oFlowType);
		//生成表结构
		workflowHelper.createFlowRunDataTable(oFlowType);
		//创建流程编号序列
		flowSeqService.createFlowTypeNumbering(oFlowType.getSid());
				
		json.setRtState(TeeConst.RETURN_OK);
		
		return json;
	}
	
	/**
	 * 获取自由流程发起经办权限
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPrcsPriv")
	@ResponseBody
	public TeeJson getPrcsPriv(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		json.setRtState(true);
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
		
		json.setRtData(flowTypeService.getPrcsPrivModel(flowId));
		
		return json;
	}
	
	/**
	 * 移除流程定义
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delFlowType")
	@ResponseBody
	public TeeJson delFlowType(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
		
		//删除流程定义
		flowTypeService.deleteService(flowId);
		
		//删除对应流程表
		try{
			workflowHelper.deleteFlowRunDataTable(flowId);
		}catch(Exception e){
			
		}
		
		//删除流程编号序列
		flowSeqService.deleteFlowTypeNumbering(flowId);
		
		json.setRtState(true);
		json.setRtMsg("删除流程定义成功。");
		return json;
	}
	
	/**
	 * 保存经办权限
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/savePrcsPriv")
	@ResponseBody
	public TeeJson savePrcsPriv(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
		int prcsUser [] = TeeStringUtil.parseIntegerArray(request.getParameter("prcsUser"));
		int prcsDept [] = TeeStringUtil.parseIntegerArray(request.getParameter("prcsDept"));
		int prcsRole [] = TeeStringUtil.parseIntegerArray(request.getParameter("prcsRole"));
		
		flowTypeService.savePrcsPriv(flowId, prcsUser, prcsDept, prcsRole);
		
		json.setRtState(true);
		json.setRtMsg("保存成功");
		
		return json;
	}
	
	/**
	 * 导出流程xml文件
	 * @param flowId
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportXml")
	@ResponseBody
	public void exportXml(@RequestParam(value="flowId") int flowId,HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		TeeFlowType ft = flowTypeService.load(flowId);
		response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(ft.getFlowName(),"UTF-8")+".xml");
		OutputStream output = response.getOutputStream();
		String sb = flowTypeService.exportXml(flowId);
		output.write(sb.getBytes("UTF-8"));
	}
	
	/**
	 * 导出流程xml文件
	 * @param flowId
	 * @param response
	 * @throws JDOMException 
	 * @throws Exception
	 */
	@RequestMapping("/importXml")
	public void importXml(HttpServletRequest request,HttpServletResponse response) throws IOException, JDOMException {
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		int flowId = TeeStringUtil.getInteger(multiRequest.getParameter("flowId"), 0);
		int importOrg = TeeStringUtil.getInteger(multiRequest.getParameter("importOrg"), 0);
		TeeAttachment attach = upload.singleAttachUpload(multiRequest, TeeAttachmentModelKeys.TMP);
		File file = new File(attach.getFilePath());
		flowTypeService.deleteAllProcess(flowId);
		flowTypeService.importXml(new FileInputStream(file), flowId,importOrg);
		
		PrintWriter pw = response.getWriter();
		pw.write("<script>parent.uploadSuccess();</script>");
	}
	
	/**
	 * 获取流程变量模型
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws JDOMException
	 */
	@RequestMapping("/getFlowRunVarsModel")
	@ResponseBody
	public TeeJson getFlowRunVarsModel(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		json.setRtData(flowTypeService.getFlowRunVarsModel(flowId));
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 更新流程变量初始模型
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws JDOMException
	 */
	@RequestMapping("/updateFlowRunVarsModel")
	@ResponseBody
	public TeeJson updateFlowRunVarsModel(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		String model = TeeStringUtil.getString(request.getParameter("model"));
		flowTypeService.updateFlowRunVarsModel(model, flowId);
		
		json.setRtState(true);
		json.setRtMsg("更新成功");
		return json;
	}

	
	/**
	 * 归档映射
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateArchiveMapping")
	@ResponseBody
	public TeeJson updateArchiveMapping(HttpServletRequest request){
		TeeJson json = new TeeJson();
		return flowTypeService.updateArchiveMapping(request);
	}
	
	/**
	 * 判断该flowType下是否有关联的flowRun
	 * @param request
	 * @return
	 */
	@RequestMapping("/hasRelatedFlowRun")
	@ResponseBody
	public TeeJson hasRelatedFlowRun(HttpServletRequest request){
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		String model = TeeStringUtil.getString(request.getParameter("model"));
		return flowTypeService.hasRelatedFlowRun(sid);
	}
	
	
	/**
	 * 根据主键  获取归档映射
	 * @param request
	 * @return
	 */
	@RequestMapping("/getArchiveMappingById")
	@ResponseBody
	public TeeJson getArchiveMappingById(HttpServletRequest request){
		return flowTypeService.getArchiveMappingById(request);
	}
	
	
	/**
	 * 获取所有的流程类型和流程分类
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllFlowTypesAndFlowSorts")
	@ResponseBody
	public TeeJson getAllFlowTypesAndFlowSorts(HttpServletRequest request){
		return flowTypeService.getAllFlowTypesAndFlowSorts(request);
	}
	
	
	public void setFlowTypeService(TeeFlowTypeServiceInterface flowTypeService) {
		this.flowTypeService = flowTypeService;
	}

	public void setFlowSortService(TeeFlowSortServiceInterface flowSortService) {
		this.flowSortService = flowSortService;
	}

	public void setFlowFormService(TeeFlowFormServiceInterface flowFormService) {
		this.flowFormService = flowFormService;
	}

	public void setDeptService(TeeDeptService deptService) {
		this.deptService = deptService;
	}

	public void setPersonService(TeePersonService personService) {
		this.personService = personService;
	}

	public void setWorkflowHelper(TeeWorkflowHelperInterface workflowHelper) {
		this.workflowHelper = workflowHelper;
	}

	public TeeWorkflowHelperInterface getWorkflowHelper() {
		return workflowHelper;
	}

	
	
}
