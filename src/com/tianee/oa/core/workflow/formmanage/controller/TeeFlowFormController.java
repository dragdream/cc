package com.tianee.oa.core.workflow.formmanage.controller;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormSort;
import com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormService;
import com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface;
import com.tianee.oa.core.workflow.formmanage.service.TeeFormSortServiceInterface;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.util.workflow.TeeWorkflowHelperInterface;
import com.tianee.oa.util.workflow.ctrl.TeeCtrl;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowFormItemModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowFormModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/flowForm")
public class TeeFlowFormController {
	/**
	 * 表单功能相关工具
	 */
	@Autowired
	private TeeWorkflowHelperInterface workflowHelper;
	/**
	 * 注册flowFormItemService服务
	 */
	@Autowired
	private TeeFlowFormServiceInterface flowFormService;
	
	@Autowired
	private TeeFlowFormService formService;
	
	@Autowired
	private TeeFormSortServiceInterface formSortService;
	
	@Autowired
	private TeeFlowTypeServiceInterface flowTypeService;
	
	@Autowired
	private TeeBaseUpload upload;
	
	
	@Autowired
	private TeeDeptService deptService;
	/**
	 * 提交表单
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/commitForm")
	@ResponseBody
	public TeeJson commitForm(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String content = TeeStringUtil.getString(request.getParameter("content"));
		int formId = TeeStringUtil.getInteger(request.getParameter("formId"), 0);
		
		TeeJson json = new TeeJson();
		
		//更新并保存表单元数据
		flowFormService.saveAndUpdateFormService(content, formId);
		json.setRtMsg("保存成功");
		json.setRtState(TeeConst.RETURN_OK);
		
		return json;
	}
	
	/**
	 * 获取表单树
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getTreeData")
	@ResponseBody
	public TeeJson getTreeData(HttpServletRequest request) throws Exception {
		//获取当前登录的用户
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		TeeJson json = new TeeJson();
		List<TeeZTreeModel> list = new ArrayList<TeeZTreeModel>();
		int id = TeeStringUtil.getInteger(request.getParameter("id"), 0);
		
		if(id==0){
			//添加默认节点
			TeeZTreeModel m = new TeeZTreeModel();
			m.setId("-1");
			m.setName("默认分类");
			m.setParent(true);
			m.getParams().put("type", 1);//分类节点
			list.add(m);
			m.setIconSkin("wfNode1");
			
			List<TeeFormSort> sortList = formSortService.list();
			for(TeeFormSort formSort:sortList){
				m = new TeeZTreeModel();
				m.setId(String.valueOf(formSort.getSid()));
				m.setName(formSort.getSortName());
				m.setParent(true);
				m.getParams().put("type", 1);//分类节点
				list.add(m);
				m.setIconSkin("wfNode1");
			}
		}else{
			
			List<TeeForm> formList = flowFormService.getFlowFormBySort1(id,loginUser);
			for(TeeForm form:formList){
				TeeZTreeModel m = new TeeZTreeModel();
				m.setId(String.valueOf(form.getSid()));
				m.setName(form.getFormName());
				m.setParent(false);
				m.getParams().put("type", 2);//表单节点
				list.add(m);
				m.setIconSkin("wfNode2");
			}
		}
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(list);
		
		return json;
	}
	
	/**
	 * 获取绑定表单的流程数量
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/countOfBundledFlowType")
	@ResponseBody
	public TeeJson countOfBundledFlowType(HttpServletRequest request) throws Exception {
		//获取当前登录的用户
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER); 
		TeeJson json = new TeeJson();
		List<TeeZTreeModel> list = new ArrayList<TeeZTreeModel>();
		int id = 0;
		
		if(id==0){
			List<TeeFormSort> sortList = formSortService.list();
			for(TeeFormSort formSort:sortList){
				TeeZTreeModel m = new TeeZTreeModel();
				m.setId(String.valueOf(formSort.getSid()));
				m.setName(formSort.getSortName());
				m.setParent(true);
				m.getParams().put("type", 1);//分类节点
				list.add(m);
			}
		}else{
			List<TeeForm> formList = flowFormService.getFlowFormBySort(id);
			for(TeeForm form:formList){
				TeeZTreeModel m = new TeeZTreeModel();
				m.setId(String.valueOf(form.getSid()));
				m.setName(form.getFormName());
				m.setParent(false);
				m.getParams().put("type", 2);//表单节点
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
	@RequestMapping("/getFormListBySort")
	@ResponseBody
	public TeeJson getFormListBySort(Integer sortId) throws Exception {
		TeeJson json = new TeeJson();
		List<TeeFlowFormModel> list = new ArrayList<TeeFlowFormModel>();
		
		List<TeeForm> formList = flowFormService.getFlowFormBySort(sortId);
		for(TeeForm form:formList){
			TeeFlowFormModel m = new TeeFlowFormModel();
			BeanUtils.copyProperties(form, m);
			m.setCss(null);
			m.setPrintModel(null);
			m.setPrintModelShort(null);
			list.add(m);
		}
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(list);
		
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
		//获取当前登录的用户名
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		List<TeeFlowFormModel> list = flowFormService.datagrid(m, sortId,loginUser);
		json.setTotal(flowFormService.getFlowFormCountBySort(sortId,loginUser));
		json.setRows(list);
		
		return json;
	}
	
	/**
	 * 加载一条表单记录，无表单内容数据，仅增加查询效率
	 * @param formId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPlain")
	@ResponseBody
	public TeeJson getPlain(Integer formId) throws Exception {
		TeeJson json = new TeeJson();
		
		TeeForm form = flowFormService.getForm(formId);
		TeeFlowFormModel m = new TeeFlowFormModel();
		BeanUtils.copyProperties(form, m);
		m.setFormGroup(form.getFormGroup());
		m.setFormSortId(form.getFormSort()==null?0:form.getFormSort().getSid());
		m.setCss("");
		m.setPrintModel("");
		m.setPrintModelShort("");
		if(form.getDept()!=null){
			m.setDeptId(form.getDept().getUuid());
			m.setDeptName(form.getDept().getDeptName());
		}
		
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(m);
		
		return json;
	}
	
	/**
	 * 获取表单数据（全数据）
	 * @param formId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/get")
	@ResponseBody
	public TeeJson get(Integer formId) throws Exception {
		TeeJson json = new TeeJson();
		
		TeeForm form = flowFormService.getForm(formId);
		TeeFlowFormModel m = new TeeFlowFormModel();
		BeanUtils.copyProperties(form, m);
		m.setFormGroup(form.getFormGroup());
		m.setFormSortId(form.getFormSort()==null?0:form.getFormSort().getSid());
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(m);
		
		return json;
	}
	
	@RequestMapping("/getStyle")
	@ResponseBody
	public TeeJson getStyle(Integer formId) throws Exception {
		TeeJson json = new TeeJson();
		
		TeeForm form = flowFormService.getForm(formId);
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(form.getCss());
		
		return json;
	}
	
	@RequestMapping("/updateStyle")
	@ResponseBody
	public TeeJson updateStyle(int formId,String css) throws Exception {
		TeeJson json = new TeeJson();
		
		TeeForm form = flowFormService.getForm(formId);
		form.setCss(css);
		flowFormService.updateService(form);
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtMsg("保存成功");
		
		return json;
	}
	
	@RequestMapping("/getScript")
	@ResponseBody
	public TeeJson getScript(Integer formId) throws Exception {
		TeeJson json = new TeeJson();
		
		TeeForm form = flowFormService.getForm(formId);
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(form.getScript());
		
		return json;
	}
	
	@RequestMapping("/updateScript")
	@ResponseBody
	public TeeJson updateScript(Integer formId,String script) throws Exception {
		TeeJson json = new TeeJson();
		
		TeeForm form = flowFormService.getForm(formId);
		form.setScript(script);
		flowFormService.updateService(form);
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtMsg("保存成功");
		
		return json;
	}
	
	/**
	 * 创建表单记录
	 * @param formName
	 * @param sortId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/createForm")
	@ResponseBody
	public TeeJson createForm(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		//获取当前登录的用户名
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String formName = TeeStringUtil.getString(request.getParameter("formName"));
		int sortId = TeeStringUtil.getInteger(request.getParameter("sortId"), 0);
		int deptId=TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
		//先获取表单分类
		TeeFormSort formSort = formSortService.get(sortId);
		TeeForm form = new TeeForm();
		form.setFormName(formName);
		form.setFormSort(formSort);
		
		//获取所属部门
		TeeDepartment dept=null;
		//判断部门主键是否为0
		if(deptId!=0){
			dept=deptService.get(deptId);
		}else{
			dept=loginUser.getDept();
		}
		
		form.setDept(dept);
		//保存表单
		flowFormService.saveService(form);
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtMsg("创建表单成功");
		
		
		return json;
	}
	
	@RequestMapping("/deleteForm")
	@ResponseBody
	public TeeJson deleteForm(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		int formId = TeeStringUtil.getInteger(request.getParameter("formId"), 0);
		
		flowFormService.deleteFormService(formId);
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtMsg("删除表单成功");
		
		
		return json;
	}
	
	
	@RequestMapping("/getFormItemsByFlowType")
	@ResponseBody
	public TeeJson getFormItemsByFlowType(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
		TeeFlowType flowType = flowTypeService.get(flowId);
		TeeForm form = flowType.getForm();
		List<TeeFormItem> items = flowFormService.getLatestFormItemsByOriginForm(form);
		List<TeeFlowFormItemModel> mList = new ArrayList<TeeFlowFormItemModel>(); 
		
		for(TeeFormItem formItem:items){
			TeeFlowFormItemModel m = new TeeFlowFormItemModel();
			BeanUtils.copyProperties(formItem, m);
			m.setXtypeDesc(TeeCtrl.getXTypeDesc(formItem.getXtype()));
			mList.add(m);
		}
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(mList);
		
		return json;
	}
	
	@RequestMapping("/getFormItemsByForm")
	@ResponseBody
	public TeeJson getFormItemsByForm(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		
		int formId = TeeStringUtil.getInteger(request.getParameter("formId"),0);
		TeeForm form = flowFormService.getForm(formId);
		List<TeeFormItem> items = flowFormService.getLatestFormItemsByOriginForm(form);
		List<TeeFlowFormItemModel> mList = new ArrayList<TeeFlowFormItemModel>(); 
		
		for(TeeFormItem formItem:items){
			TeeFlowFormItemModel m = new TeeFlowFormItemModel();
			BeanUtils.copyProperties(formItem, m);
			m.setXtypeDesc(TeeCtrl.getXTypeDesc(formItem.getXtype()));
			mList.add(m);
		}
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(mList);
		
		return json;
	}
	
	
	@RequestMapping("/getFormItemsByFormGroup")
	@ResponseBody
	public TeeJson getFormItemsByFormGroup(HttpServletRequest request) throws Exception {
		return flowFormService.getFormItemsByFormGroup(request);
	}
	
	@RequestMapping("/updateFormItemsByFormGroup")
	@ResponseBody
	public TeeJson updateFormItemsByFormGroup(HttpServletRequest request) throws Exception {
		return flowFormService.updateFormItemsByFormGroup(request);
	}
	
	/**
	 * 流程数据选择控件  映射字段
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getMappingFormItemsByFlowType")
	@ResponseBody
	public TeeJson getMappingFormItemsByFlowType(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		
		int flowId= TeeStringUtil.getInteger(request.getParameter("flowId"),0);
        String mappingStr=TeeStringUtil.getString(request.getParameter("mappingStr"));
		List<TeeFormItem> items = formService.getMappingFormItemsByFlowType(flowId,mappingStr);
		List<TeeFlowFormItemModel> mList = new ArrayList<TeeFlowFormItemModel>(); 
		if(items!=null){
			for(TeeFormItem formItem:items){
				TeeFlowFormItemModel m = new TeeFlowFormItemModel();
				BeanUtils.copyProperties(formItem, m);
				m.setXtypeDesc(TeeCtrl.getXTypeDesc(formItem.getXtype()));
				mList.add(m);
			}
		}
	
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(mList);
		
		return json;
	}
	
	
	
	@RequestMapping("/getMappingFormItemsByFlowId")
	@ResponseBody
	public TeeJson getMappingFormItemsByFlowId(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		
		int flowId= TeeStringUtil.getInteger(request.getParameter("flowId"),0);
        String mappingStr=TeeStringUtil.getString(request.getParameter("mappingStr"));
		List<TeeFormItem> items = formService.getMappingFormItemsByFlowType(flowId,mappingStr);
		Map<String,String> map=new HashMap<String, String>();
		if(items!=null){
			for(TeeFormItem formItem:items){
				map.put(formItem.getTitle(), formItem.getName());
			}
		}
	
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(map);
		
		return json;
	}
	/**
	 * 更新字段项排序号
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateItemsSortNo")
	@ResponseBody
	public TeeJson updateItemsSortNo(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		String sortFieldModel = TeeStringUtil.getString(request.getParameter("sortFieldModel"));
		List<Map<String,String>> params = TeeJsonUtil.JsonStr2MapList(sortFieldModel);
		flowFormService.updateItemsSortNo(params);
		return json;
	}
	
	/**
	 * 获取表单字段的控件类型描述
	 * @param xtype
	 * @return
	 */
	public String getXTypeDesc(String xtype){
		String desc = null;
		if("xinput".equals(xtype)){
			desc = "单行输入框";
		}else if("xtextarea".equals(xtype)){
			desc = "多行文本框";
		}else if("xselect".equals(xtype)){
			desc = "下拉菜单";
		}else if("xradio".equals(xtype)){
			desc = "单选控件";
		}else if("xcheckbox".equals(xtype)){
			desc = "多选控件";
		}else if("xcalculate".equals(xtype)){
			desc = "计算控件";
		}else if("xfetch".equals(xtype)){
			desc = "选择器";
		}else if("xlist".equals(xtype)){
			desc = "列表控件";
		}else if("xmacro".equals(xtype)){
			desc = "宏控件";
		}else if("xmacrotag".equals(xtype)){
			desc = "宏标记";
		}else if("xseal".equals(xtype)){
			desc = "签章控件";
		}
		return desc;
	}
	
	/**
	 * 更新表单名称及分类
	 * @param formName
	 * @param sortId
	 * @param formId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateFormPlain")
	@ResponseBody
	public TeeJson updateFormPlain(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		
		String formName = TeeStringUtil.getString(request.getParameter("formName"));
		int sortId = TeeStringUtil.getInteger(request.getParameter("sortId"), 0);
		int formId = TeeStringUtil.getInteger(request.getParameter("formId"), 0);
		int deptId=TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
		
		//获取表单所属部门
		TeeDepartment dept=null;
		if(deptId!=0){
			dept=deptService.get(deptId);
		}
		//先获取表单分类
		TeeFormSort formSort = formSortService.get(sortId);
		//获取表单实体
		List<TeeForm> formList = flowFormService.listHistoryVersion(formId);
		for(TeeForm f:formList){
			f.setFormName(formName);
			f.setFormSort(formSort);
			f.setDept(dept);
			flowFormService.updateService(f);
		}
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtMsg("更新表单成功");
		
		
		return json;
	}
	
	/**
	 * 导出表单
	 * @param formId
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/export")
	@ResponseBody
	public void export(@RequestParam(value="formId") int formId,HttpServletResponse response) throws Exception {
		TeeForm form = flowFormService.getForm(formId);
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(form.getFormName()+"_Ver"+form.getVersionNo(),"UTF-8")+".html");
		OutputStream output = response.getOutputStream();
		output.write(form.getPrintModel()==null?"".getBytes():form.getPrintModel().getBytes("UTF-8"));
	}
	
	/**
	 * 导入表单
	 * @param formId
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/importForm")
	@ResponseBody
	public void importForm(@RequestParam(value="formId") int formId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		TeeAttachment attach = upload.singleAttachUpload(multipartRequest, TeeAttachmentModelKeys.TMP);
		File file = new File(attach.getFilePath());
		flowFormService.importForm(formId, file);
		
		PrintWriter pw = response.getWriter();
		pw.write("<script>parent.uploadSuccess();</script>");
	}
	
	@RequestMapping("/createVersionForm")
	@ResponseBody
	public TeeJson createVersionForm(int formId) throws Exception {
		TeeForm form = flowFormService.createVersionFormService(formId);
		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtData(form.getSid());
		json.setRtMsg("已生成表单版本，版本号为"+form.getVersionNo());
		return json;
	}
	
	/**
	 * 获取最新版本表单ID
	 * @param formId
	 * @return
	 */
	@RequestMapping("/getLatestVersionFormId")
	@ResponseBody
	public TeeJson getLatestVersionFormId(int formId){
		TeeJson json = new TeeJson();
		TeeForm form = flowFormService.getLatestVersion(formId);
		json.setRtState(true);
		json.setRtData(form.getSid());
		return json;
	}
	
	/**
	 * 判断是否存在绑定表单的流程
	 * @param formId
	 * @return
	 */
	@RequestMapping("/checkExistFlow")
	@ResponseBody
	public TeeJson checkExistFlow(int formId){
		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtData(flowFormService.checkExistFlow(formId));
		return json;
	}
	
	/**
	 * 返回版本列表集合
	 * @param formId
	 * @return
	 */
	@RequestMapping("/listVersions")
	@ResponseBody
	public TeeJson listVersions(int formId){
		return flowFormService.listVersions2Json(formId);
	}

	public void setWorkflowHelper(TeeWorkflowHelperInterface workflowHelper) {
		this.workflowHelper = workflowHelper;
	}

	public void setFlowFormService(TeeFlowFormServiceInterface flowFormService) {
		this.flowFormService = flowFormService;
	}

	public void setFormSortService(TeeFormSortServiceInterface formSortService) {
		this.formSortService = formSortService;
	}

	public void setFlowTypeService(TeeFlowTypeServiceInterface flowTypeService) {
		this.flowTypeService = flowTypeService;
	}

	public void setUpload(TeeBaseUpload upload) {
		this.upload = upload;
	}

	public TeeBaseUpload getUpload() {
		return upload;
	}
	
	
}
