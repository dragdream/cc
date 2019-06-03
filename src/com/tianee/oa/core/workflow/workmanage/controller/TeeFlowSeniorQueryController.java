package com.tianee.oa.core.workflow.workmanage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.HTMLDocument.HTMLReader.FormAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianee.oa.core.general.bean.TeePortlet;
import com.tianee.oa.core.general.bean.TeePortletPersonal;
import com.tianee.oa.core.general.model.TeePortletModel;
import com.tianee.oa.core.general.model.TeeSmsModel;
import com.tianee.oa.core.general.service.TeePersonalPortletService;
import com.tianee.oa.core.general.service.TeePortletService;
import com.tianee.oa.core.general.service.TeeSmsService;
import com.tianee.oa.core.general.service.TeeSysParaService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.model.TeePersonModel;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowSort;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowSortServiceInterface;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.core.workflow.formmanage.dao.TeeFlowFormDao;
import com.tianee.oa.core.workflow.formmanage.dao.TeeFormDao;
import com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormService;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowQueryTpl;
import com.tianee.oa.core.workflow.workmanage.model.TeeFlowTypeIModel;
import com.tianee.oa.core.workflow.workmanage.model.TeeSeniorQueryModel;
import com.tianee.oa.core.workflow.workmanage.model.queryTpl.TeeTplFormFieldModel;
import com.tianee.oa.core.workflow.workmanage.model.queryTpl.TeeTplRadioModel;
import com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrivServiveInterface;
import com.tianee.oa.core.workflow.workmanage.service.TeeFlowRuleService;
import com.tianee.oa.core.workflow.workmanage.service.TeeFlowSeniorQueryServiceInterface;
import com.tianee.oa.core.workflow.workmanage.service.TeeWorkQueryServiceInterface;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("seniorQuery")
/**
 * 工作流高级查询
 * @author CXT
 *
 */
public class TeeFlowSeniorQueryController {

	 @Autowired
	 TeeFlowSortServiceInterface flowSortService;
	 @Autowired
	 TeeFlowTypeServiceInterface flowTypeService;
	 @Autowired
	 TeeFlowSeniorQueryServiceInterface queryService;
	 @Autowired
	 TeeWorkQueryServiceInterface workQueryService;
	 @Autowired
	 TeeFlowPrivServiveInterface flowPrivService;
	 @Autowired
	 TeeFlowFormDao formDao;
	 
	 /**
	  * spring mvc 返回mav 用el表达式获取值
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/flowList")
	  public ModelAndView portlet(HttpServletRequest request) {
		  ModelAndView mav = new ModelAndView("/system/core/workflow/workmanage/workquery/seniorQuery/flowList.jsp");
		  TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		
		  /**
			 * 查询范围SQL语句组装
			 */

			// 我发起的
			String myBegin = "fr.beginPerson.uuid="+person.getUuid()+" ";

			// 我经办的
			String myPrcs = "exists (select 1 from TeeFlowRunPrcs frp where frp.flowRun.runId=fr.runId and frp.prcsUser.uuid="
					+ person.getUuid()+ ")";

			// 我管理的（这里需要拿到当前人所管理的几个流程和其对应的管理部门串
			StringBuffer myManage = new StringBuffer();
			// 管理权限
			List managePrivList = flowPrivService.getQueryPostDeptsByAllFlow(
					person, "1");
			// 监控权限
			List monitorPrivList = flowPrivService.getQueryPostDeptsByAllFlow(
					person, "2");
			// 查询权限
			List searchPrivList = flowPrivService.getQueryPostDeptsByAllFlow(
					person, "3");
			// 编辑权限
			List editPrivList = flowPrivService.getQueryPostDeptsByAllFlow(
					person, "4");

			Map queryData = null;
			// 所有权限汇总
			List allPrivList = new ArrayList();
			for (int i = 0; i < managePrivList.size(); i++) {
				queryData = (Map) managePrivList.get(i);
				allPrivList.add(queryData);
			}
			for (int i = 0; i < monitorPrivList.size(); i++) {
				queryData = (Map) monitorPrivList.get(i);
				allPrivList.add(queryData);
			}
			for (int i = 0; i < searchPrivList.size(); i++) {
				queryData = (Map) searchPrivList.get(i);
				allPrivList.add(queryData);
			}
			for (int i = 0; i < editPrivList.size(); i++) {
				queryData = (Map) editPrivList.get(i);
				allPrivList.add(queryData);
			}

			 //获取真正有权限的集合
			List  realPrivList = new ArrayList();
			Map m=null;
			if (allPrivList.size() != 0) {
				for (int i = 0; i < allPrivList.size(); i++) {
					m = (Map) allPrivList.get(i);
					if (!String.valueOf(m.get("postDeptIds")).equals("")) {// 有权限
						realPrivList.add(m);
					}
				}
			}
			
			
			if (realPrivList.size() != 0) {// 有权限
				for (int i = 0; i < realPrivList.size(); i++) {
					queryData = (Map) realPrivList.get(i);
					if (!String.valueOf(queryData.get("postDeptIds")).equals("")) {// 有权限
						myManage.append("(fr.flowType.sid="
								+ queryData.get("flowTypeId"));
						String postDeptIds = String.valueOf(queryData
								.get("postDeptIds"));
						if (!postDeptIds.equals("0")) {// 加入限定部门条件
							if (postDeptIds.endsWith(",")) {
								postDeptIds = postDeptIds.substring(0,
										postDeptIds.length() - 1);
							}
							myManage.append(" and exists (select 1 from TeeDepartment dept where dept.uuid in ("
									+ postDeptIds + ") and dept.uuid="+person.getDept().getUuid()+")");
						}
						myManage.append(")");
						if (i != realPrivList.size() - 1) {
							myManage.append(" or ");
						}
					}
				}
			} else {
				myManage.append(" fr.flowType.sid=0 ");
			}

			// 我关注的
			String myConcern = "exists (select 1 from TeeFlowRunConcern frc where frc.flowRun=fr and frc.person.uuid="
					+ person.getUuid() + ")";

			// 我查阅的
			String myLookup = "exists (select 1 from TeeFlowRunView frv where frv.viewPerson.uuid="
					+ person.getUuid() + " and frv.flowRun=fr)";

		  
		  //拼接所有的管理范围
		StringBuffer manager = new StringBuffer();
		manager.append(" and ((" + myBegin + ") or (" + myPrcs + ") ");
		if (myManage.length() != 0) {
			manager.append(" or (" + myManage.toString() + ")");
		}
		manager.append(" or (" + myConcern + ")");
		manager.append(" or (" + myLookup + ")");
		manager.append(")");
		
		
		  
		  
		  
		  
		  List<TeeFlowSort> list = flowSortService.list();
		  List<TeeSeniorQueryModel> list1 = new ArrayList<TeeSeniorQueryModel>();
		  for(TeeFlowSort sort : list){
			  TeeSeniorQueryModel model = new TeeSeniorQueryModel();
			  BeanUtils.copyProperties(sort, model);
			  List<TeeFlowType> list2 = sort.getFlowTypeSet();
			  List<TeeFlowTypeIModel> list3 = new ArrayList<TeeFlowTypeIModel>();
			  for(TeeFlowType type : list2){
				  TeeFlowTypeIModel model1 = new TeeFlowTypeIModel();
				  BeanUtils.copyProperties(type,model1);
				  int DEAL_COUNT = flowTypeService.getDealCount(person.getUuid(),type.getSid(),manager);
				  int OVER_COUNT = flowTypeService.getOverCount(person.getUuid(),type.getSid(),manager);
				  model1.setDEAL_COUNT(DEAL_COUNT);
				  model1.setOVER_COUNT(OVER_COUNT);
				  list3.add(model1);
			  }
			  model.setFlowTypeModelSet(list3);
			  list1.add(model);
		  }
		  //返回流程列表
		  mav.addObject("flowList", list1);
		  //返回流程分类
		  mav.addObject("flowSortList", this.getFlowSortList(request));
		  
		  return mav;
	  }
	 
	 /**
	  * 返回流程分类
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/getFlowSortList")
	 @ResponseBody
	  public String getFlowSortList(HttpServletRequest request) {
		  TeeJson json = new TeeJson();
		  TeeJsonUtil ju = new TeeJsonUtil();
		  TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		  int uuid = person.getUuid();
		  //获取流程分类列表
		  List<TeeFlowSort> sortList = flowSortService.list();
		  String data = "";

		  String flowList = "{sortable: false,columns: [";

		  List<TeePortletModel> list = new ArrayList<TeePortletModel>();

		  //计算桌面布局，按左中右，和modellist进行循环，读取桌面模块位置
		  for(int i = 1;i<=2;i++){
			  int j = 0;
			  int width = 0;
			  flowList+="{ width:'50%' ,portlets: [";
			  for(TeeFlowSort m : sortList){	  
				  j++;
				  if((j+i)%2==0){
					  flowList+="{ title:'"+m.getSortName()+"',attrs:{id:"+m.getSid()+"},id:"+m.getSid()+",content: {style: {maxHeight:'100%'},type:'text',text: function() {return $('#"+m.getSortName()+"').html();}}},";
				  }
			  }
			  if(flowList.endsWith(",")){
				  flowList = flowList.substring(0, flowList.length()-1);
			  }
			  flowList+="]},";
		  }
		  if(flowList.endsWith(",")){
			  flowList = flowList.substring(0, flowList.length()-1);
		  }
		  flowList +="]}";
		  String str = ju.toJson(list);
		  data = flowList;
		  //System.out.println("flowList:"+data);
		  return data;
	  }
	 
	 /**
	  * spring mvc 返回mav 用el表达式获取值
	  * 根据flowId、登录人返回flow_query_tpl
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/getQueryTplByFlowId")
	 public ModelAndView getQueryTplByFlowId(HttpServletRequest request) {
		  ModelAndView mav = new ModelAndView("/system/core/workflow/workmanage/workquery/seniorQuery/flowQueryTpl/index.jsp");
		  TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		  int uuid = person.getUuid();
		  String flowId = request.getParameter("flowId");
		  List<TeeSeniorQueryModel> list = queryService.getQueryTplByFlowId(person,Integer.parseInt(flowId));

		  mav.addObject("queryList", list);
		  mav.addObject("flow", flowTypeService.get(Integer.parseInt(flowId)));
		  return mav;
	  }
	 
	 /**
	  * spring mvc 返回mav 用el表达式获取值
	  * 根据flowId、登录人返回flow_query_tpl
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/sysQueryTplByFlowId")
	 public ModelAndView sysQueryTplByFlowId(HttpServletRequest request) {
		  ModelAndView mav = new ModelAndView("/system/core/workflow/flowtype/manage/tplManager.jsp");
		  TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		  int uuid = person.getUuid();
		  String flowId = request.getParameter("flowId");
		  List<TeeSeniorQueryModel> list = queryService.getQueryTplByFlowId(person,Integer.parseInt(flowId));

		  mav.addObject("queryList", list);
		  mav.addObject("flow", flowTypeService.get(Integer.parseInt(flowId)));
		  return mav;
	  }
	 
	 
	 /**
	  * spring mvc 返回mav 用el表达式获取值
	  * 根据flowId返回flowSeniorQueryTplModelList
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/toFlowQuery")
	 public ModelAndView toFlowQuery(HttpServletRequest request) {
		 //用来判断是否是自定义菜单过来的
		  int isCustom=TeeStringUtil.getInteger(request.getParameter("isCustom"),0);
		  
		  ModelAndView mav = new ModelAndView("/system/core/workflow/workmanage/workquery/seniorQuery/flowQuery.jsp?isCustom="+isCustom);
		  TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		  int uuid = person.getUuid();
		  String flowId = request.getParameter("flowId");
		  String tplId = request.getParameter("tplId");
		  List<TeeSeniorQueryModel> list = queryService.getQueryTplByFlowId(person,Integer.parseInt(flowId));
		  if(!TeeUtility.isNullorEmpty(tplId)){
			  TeeFlowQueryTpl queryTpl = queryService.getById(Integer.parseInt(tplId));
			  String data = queryTpl.getDataJson();
			  mav.addObject("queryEdit",data);
			  
		  }
		  TeeForm form = queryService.getFormByFlowId(Integer.parseInt(flowId));
		  form = formDao.getLatestVersion(form);
		  List<TeeTplFormFieldModel> modelList = new ArrayList<TeeTplFormFieldModel>();
		  for(TeeFormItem item : form.getFormItems()){
			  TeeTplFormFieldModel model = new TeeTplFormFieldModel();
			  BeanUtils.copyProperties(item, model);
			  String radioModel = item.getModel();
			  List<TeeTplRadioModel> radioList = new ArrayList<TeeTplRadioModel>();
			  if(item.getXtype().equals("xradio")){
				  JSONArray array = JSONArray.fromObject(radioModel);
				  for (int i = 0; i < array.size(); i++) {
					  JSONObject obj = array.getJSONObject(i);
					  String checked = "";
					  boolean str = (Boolean) obj.get("default");
					  if(str==true){
						  checked = "checked";
					  }
					  TeeTplRadioModel radio = new TeeTplRadioModel();
					  radio.setChecked(checked);
					  radio.setData(obj.get("data").toString());
					  radioList.add(radio);
				  }
			  }
			  model.setRadioList(radioList);
			  modelList.add(model);
		  }
		  mav.addObject("formItem",modelList);
		  mav.addObject("queryList", list);
		  mav.addObject("flow", flowTypeService.get(Integer.parseInt(flowId)));
		  mav.addObject("tplId",tplId);
		  return mav;
	  }
	 
	 
	 public void getFlowCount(HttpServletRequest request){
		 TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		 
		 request.setAttribute("text", person);
	 }
	 
	 /**
	  * spring mvc 返回mav 用el表达式获取值
	  * 跳转toOrderTpl.jsp
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/toOrderTpl")
	 public ModelAndView toOrderTpl(HttpServletRequest request) {
		  TeeJsonUtil ju = new TeeJsonUtil();
		  ModelAndView mav = new ModelAndView("/system/core/workflow/workmanage/workquery/seniorQuery/flowQueryTpl/orderTpl.jsp");
		  TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		  int uuid = person.getUuid();
		  String flowId = request.getParameter("flowId");
		  String tplId = request.getParameter("tplId");
		  if(!TeeUtility.isNullorEmpty(tplId)){
			  mav.addObject("type","编辑查询模板");
			  TeeFlowQueryTpl queryTpl = queryService.getById(Integer.parseInt(tplId));
			  String data = queryTpl.getDataJson();
			  mav.addObject("queryEdit",data);
		  }else{
			  mav.addObject("type","新建查询模板");
		  }
		  TeeForm form = queryService.getFormByFlowId(Integer.parseInt(flowId));
		  List<TeeTplFormFieldModel> modelList = new ArrayList<TeeTplFormFieldModel>();
		  for(TeeFormItem item : form.getFormItems()){
			  TeeTplFormFieldModel model = new TeeTplFormFieldModel();
			  BeanUtils.copyProperties(item, model);
			  String radioModel = item.getModel();
			  List<TeeTplRadioModel> radioList = new ArrayList<TeeTplRadioModel>();
			  if(item.getXtype().equals("xradio")){
				  JSONArray array = JSONArray.fromObject(radioModel);
				  for (int i = 0; i < array.size(); i++) {
					  JSONObject obj = array.getJSONObject(i);
					  String checked = "";
					  boolean str = (Boolean) obj.get("default");
					  if(str==true){
						  checked = "checked";
					  }
					  TeeTplRadioModel radio = new TeeTplRadioModel();
					  radio.setChecked(checked);
					  radio.setData(obj.get("data").toString());
					  radioList.add(radio);
				  }
			  }
			  model.setRadioList(radioList);
			  modelList.add(model);
		  }
		  mav.addObject("formItem",modelList);
		  mav.addObject("tplId",tplId);
		  mav.addObject("flow", flowTypeService.get(Integer.parseInt(flowId)));
		  return mav;
	  }
	 
	 
	 
	 /**
	  * 保存/更新高级查询模板
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/saveOrUpdateSeniorTpl")
	 @ResponseBody
	  public TeeJson saveOrUpdateSeniorTpl(HttpServletRequest request) {
		  TeeJson json = new TeeJson();
		  TeeJsonUtil ju = new TeeJsonUtil();
		  String tplId = request.getParameter("tplId");
		  String tplName = request.getParameter("tplName");
		  String condFormula = request.getParameter("condFormula");
		  String flowId = request.getParameter("flowId");
		  Map map = new HashMap();
		  map = request.getParameterMap();
		  String data = "{"; 
		  for(Iterator iter = map.entrySet().iterator();iter.hasNext();){ 
		       Map.Entry element = (Map.Entry)iter.next(); 
		       Object strKey = element.getKey(); 
		       String[] value=(String[])element.getValue();
		       String key = strKey.toString();
		       //System.out.println(key);
		       String val = value[0];
		       if(key.equals("dispFld")){
		    	   if(val.endsWith(",")){
		    		   val = val.substring(0, val.length()-1);
		    	   }
		       }
		       data+="'"+key+"':'"+val+"',"; 
		  }

		  if(data.endsWith(",")){
			  data = data.substring(0, data.length()-1);
		  }
		  data+="}";
		  TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		  int uuid = person.getUuid();
		  if(!TeeUtility.isNullorEmpty(tplId)){
			  TeeFlowQueryTpl queryTpl = queryService.getById(Integer.parseInt(tplId));
			  queryTpl.setTplName(tplName);
			  queryTpl.setCondFormula(condFormula);
			  queryTpl.setUserId(uuid);
			  queryTpl.setFlowType(flowTypeService.get(Integer.parseInt(flowId)));
			  queryTpl.setDataJson(data);
			  queryService.updateTpl(queryTpl);
			  json.setRtMsg("更新成功！");
			  json.setRtState(true);
		  }else{
			  TeeFlowQueryTpl queryTpl = new TeeFlowQueryTpl();
			  queryTpl.setTplName(tplName);
			  queryTpl.setCondFormula(condFormula);
			  queryTpl.setUserId(uuid);
			  queryTpl.setFlowType(flowTypeService.get(Integer.parseInt(flowId)));
			  queryTpl.setDataJson(data);
			  queryService.saveTpl(queryTpl);
			  json.setRtMsg("保存成功！");
			  json.setRtState(true);
		  }
		  return json;
	  }
	 
	 /**
	  * 删除模板
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/deleteTpl")
	 @ResponseBody
	 public TeeJson deleteTpl(HttpServletRequest request){
		 TeeJson json = new TeeJson();
		 String tplId = request.getParameter("tplId");
		 if(!TeeUtility.isNullorEmpty(tplId)){
			 TeeFlowQueryTpl queryTpl = queryService.getById(Integer.parseInt(tplId));
			 queryService.deleteTpl(queryTpl);
			 json.setRtMsg("删除成功！");
			 json.setRtState(true);
		 }else{
			 json.setRtMsg("删除失败！");
			 json.setRtState(true);
		 }
		 return json;
	 }
	 
	 /**
	  * 高级查询
	  * @author CXT
	  * @return
	  */
	 @SuppressWarnings("unchecked")
	 @RequestMapping(value = "/toSeniorQuery")
	 @ResponseBody
	  public TeeEasyuiDataGridJson toSeniorQuery(HttpServletRequest request,TeeDataGridModel dataGridModel) {

		  TeeEasyuiDataGridJson dataGrid = this.toSeniorMiddleQuery(request, dataGridModel,0);
		 
		  return dataGrid;
	  }
	 
	 /**
	  * spring mvc 返回mav 用el表达式获取值
	  * 根据flowId返回flow_query_tpl
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/sysSeniorTpl")
	 public ModelAndView sysSeniorTpl(HttpServletRequest request) {
		  ModelAndView mav = new ModelAndView("/system/core/workflow/flowtype/manage/seniorIndex.jsp");
		  TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		  int uuid = person.getUuid();
		  String flowId = request.getParameter("flowId");
		  TeeForm form = queryService.getFormByFlowId(Integer.parseInt(flowId));
		  List<TeeFormItem> list = new ArrayList<TeeFormItem>();
		  for(TeeFormItem item : form.getFormItems()){
			  if(!item.getTag().equals("IMG")){
				  list.add(item);
			  }
		  }
		  mav.addObject("queryClumn",flowTypeService.get(Integer.parseInt(flowId)).getQueryFieldModel());
		  mav.addObject("formItem", list);
		  mav.addObject("flowId", Integer.parseInt(flowId));
		  return mav;
	  }
	 
	 /**
	  * 保存/更新高级查询列表字段显示
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/saveQueryClumn")
	 @ResponseBody
	  public TeeJson saveQueryClumn(HttpServletRequest request) {
		  TeeJson json = new TeeJson();
		  TeeJsonUtil ju = new TeeJsonUtil();
		  String dispFld = request.getParameter("dispFld");
		  String flowId = request.getParameter("flowId");
		  TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		  int uuid = person.getUuid();
		  if(dispFld.endsWith(",")){
			  dispFld = dispFld.substring(0,dispFld.length()-1);
		  }
		  try{
			  TeeFlowType flow = flowTypeService.get(Integer.parseInt(flowId));
			  flow.setQueryFieldModel("["+dispFld+"]");
			  flowTypeService.updateFlowTypeService(flow);
			  json.setRtMsg("保存成功！");
			  json.setRtState(true);
		  }catch(Exception ex){
			  ex.printStackTrace();
		  }

		  return json;
	  }
	 
	 /**
	  * 高级查询中间模板
	  * @param flowId 流程ID，flowStatus 流程状态 ，flowQueryType查询范围：0全部 1我发起的 2我经办的 3我管理的，
	  * @param beginUserId 流程发起人ID，runName 名称/文号，beginTime1 流程开始时间起点，  beginTime2 流程开始时间终点
	  * @param endTime1 流程结束时间起点，  endTime2 流程结束时间终点，attachmentName 公共附件名称，DATA_（表单值）
	  * @author CXT
	  * @return
	  */
	  public TeeEasyuiDataGridJson toSeniorMiddleQuery(HttpServletRequest request,TeeDataGridModel dataGridModel,int opType) {
		  Map map = new HashMap();
		  Map map1 = new HashMap();
		  map = request.getParameterMap();
		  TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		  int uuid = person.getUuid();
		  for(Iterator iter = map.entrySet().iterator();iter.hasNext();){ 
		       Map.Entry element = (Map.Entry)iter.next(); 
		       Object strKey = element.getKey(); 
		       String[] value=(String[])element.getValue();
		       String key = strKey.toString();
		      
		       String val = value[0];
		       if(key.contains("relation_")){
		    	   String num = key.replace("relation_", "");
		    	   String operator = "";
		    	   String name = ((String[])map.get("DATA_"+num))[0];
		    	   if("".equals(name)){
		    		   continue;
		    	   }
		    	   if(name.equals("SELECT_ALL_VALUE")){
		    		   continue;
		    	   }
		    	   
		    	   switch (Integer.parseInt(val)) {
					case 1:
						operator = "=";
						if(!TeeUtility.isNullorEmpty(name)){
							map1.put("DATA_"+num, name);
							map1.put("DATA_"+num+"_OPER", operator);
						}
						break;
					case 2:
						operator = ">";
						if(!TeeUtility.isNullorEmpty(name)){
							map1.put("DATA_"+num, name);
							map1.put("DATA_"+num+"_OPER", operator);
						}
						break;
					case 3:
						operator = "<";
						if(!TeeUtility.isNullorEmpty(name)){
							map1.put("DATA_"+num, name);
							map1.put("DATA_"+num+"_OPER", operator);
						}
						break;
					case 4:
						operator = ">=";
						if(!TeeUtility.isNullorEmpty(name)){
							map1.put("DATA_"+num, name);
							map1.put("DATA_"+num+"_OPER", operator);
						}
						break;
					case 5:
						operator = "<=";
						if(!TeeUtility.isNullorEmpty(name)){
							map1.put("DATA_"+num, name);
							map1.put("DATA_"+num+"_OPER", operator);
						}
						break;
					case 6:
						operator = "<>";
						if(!TeeUtility.isNullorEmpty(name)){
							map1.put("DATA_"+num, name);
							map1.put("DATA_"+num+"_OPER", operator);
						}
						break;
					case 7:
						operator = "开始为";
						if(!TeeUtility.isNullorEmpty(name)){
							map1.put("DATA_"+num, name);
							map1.put("DATA_"+num+"_OPER", "like1");
						}
						break;
					case 8:
						operator = "包含";
						if(!TeeUtility.isNullorEmpty(name)){
							map1.put("DATA_"+num, name);
							map1.put("DATA_"+num+"_OPER", "like2");
						}
						break;
					case 9:
						operator = "结束为";
						if(!TeeUtility.isNullorEmpty(name)){
							map1.put("DATA_"+num, name);
							map1.put("DATA_"+num+"_OPER", "like3");
						}
						break;
					case 10:
						operator = "=";
						map1.put("DATA_"+num, "");
						map1.put("DATA_"+num+"_OPER", operator);
						break;
				   } 
		       }
		       if(key.equals("flowStatus")){
		    	   if(!TeeUtility.isNullorEmpty(val)){
		    		   map1.put("status",Integer.parseInt(val));
		    	   }
		       }
		       if(key.equals("flowQueryType")){ 
	    		   map1.put("type",Integer.parseInt(val));
		       }
		       if(key.equals("beginUserId")){
		    	   if(!TeeUtility.isNullorEmpty(val)){
	    		   		map1.put("beginUser", Integer.parseInt(val));
		    	   }
		       }
		       if(key.equals("showFields")){
		    	   map1.put("showFields", val);
		       }
		       if(key.equals("runNameRelation")){
		    	   String operator = "";
		    	   String rName = ((String[])map.get("runName"))[0];
		    	   if(!TeeUtility.isNullorEmpty(rName)){
		    		   switch (Integer.parseInt(val)) {
						case 1:
							operator = "=";
							map1.put("runName", rName);
							map1.put("runNameOper", operator);
							break;
						case 2:
							operator = ">";
							map1.put("runName", rName);
							map1.put("runNameOper", operator);
							break;
						case 3:
							operator = "<";
							map1.put("runName", rName);
							map1.put("runNameOper", operator);
							break;
						case 4:
							operator = ">=";
							map1.put("runName", rName);
							map1.put("runNameOper", operator);
							break;
						case 5:
							operator = "<=";
							map1.put("runName", rName);
							map1.put("runNameOper", operator);
							break;
						case 6:
							operator = "<>";
							map1.put("runName", rName);
							map1.put("runNameOper", operator);
							break;
						case 7:
							operator = "开始为";
							map1.put("runName", rName);
							map1.put("runNameOper", "like1");
							//map1.put("runName", " like '"+rName + "%' ");
							break;
						case 8:
							operator = "包含";
							map1.put("runName", rName);
							map1.put("runNameOper", "like2");
							//map1.put("runName", " like '%"+rName + "%' ");
							break;
						case 9:
							operator = "结束为";
							map1.put("runName", rName);
							map1.put("runNameOper", "like3");
							//map1.put("runName", " like '%"+rName + "' ");
							break;
						case 10:
							operator = "is null";
							map1.put("runName", rName);
							map1.put("runNameOper", operator);
							break;
					   } 
		    	   }
		       }
		       if(key.equals("prcsDate1")){
		    	   if(!TeeUtility.isNullorEmpty(val)){
		    		   map1.put("start1", val + " 00:00:00");
		    	   }
		       }
		       if(key.equals("prcsDate2")){
		    	   if(!TeeUtility.isNullorEmpty(val)){
		    		   map1.put("start2", val + " 23:59:59");
		    	   }
		       }
		       if(key.equals("endDate1")){
		    	   if(!TeeUtility.isNullorEmpty(val)){
		    		   map1.put("endTime1", val + " 00:00:00") ;
		    	   }
		       }
		       if(key.equals("endDate2")){
		    	   if(!TeeUtility.isNullorEmpty(val)){
		    		   map1.put("endTime2", val + " 23:59:59");
		    	   }
		       }
		       if(key.equals("attachmentName")){
		    	   map1.put("attachmentName", val);
		    	   map1.put("attachmentNameOper", "like2");
		       }
		       if(key.equals("flowId")){
		    	   map1.put("flowId", Integer.parseInt(val));
		       }
		       if(key.equals("condFormula")){
		    	   map1.put("condFormula", val);
		       }
		  }
		  map1.put("opType", opType);
		  TeeEasyuiDataGridJson dataGrid = workQueryService.query(map1, person, dataGridModel);
		 
		  return dataGrid;
	  }
	 
	 
	 /**
	  * spring mvc 返回mav 用el表达式获取值
	  * 输出html报表
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/flowQueryToHtml")
	  public ModelAndView flowQueryToHtml(HttpServletRequest request,TeeDataGridModel dataGridModel) {
		  ModelAndView mav = new ModelAndView("/system/core/workflow/workmanage/workquery/seniorQuery/flowQueryToHtml.jsp");
		  TeeEasyuiDataGridJson dataGrid = this.toSeniorMiddleQuery(request, dataGridModel,1);
		  List<Map> list = dataGrid.getRows();
		  mav.addObject("flowList", list);
		  return mav;
	  }
	
	
}
