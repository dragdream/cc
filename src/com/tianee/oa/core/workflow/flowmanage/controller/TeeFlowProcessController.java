package com.tianee.oa.core.workflow.flowmanage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowSort;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowSortServiceInterface;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/flowProcess")
public class TeeFlowProcessController {
	@Autowired
	private TeeFlowProcessServiceInterface flowProcessService;
	
	@Autowired
	private TeeFlowTypeServiceInterface flowTypeService;
	
	@Autowired
	private TeeWorkflowServiceInterface workflowService;
	
	@Autowired
	private TeeFlowSortServiceInterface flowSortService;
	
	/**
	 * 获取步骤集合
	 * @param request
	 * @return
	 */
	@RequestMapping("/getProcessList")
	@ResponseBody
	public TeeJson getProcessList(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtData(flowProcessService.getProcessList(TeeStringUtil.getInteger(request.getParameter("flowId"), 0)));
		
		return json;
	}
	
	
	@RequestMapping("/toEnd")
	@ResponseBody
	public TeeJson toEnd(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		
		TeeFlowProcess endNode = flowProcessService.getEndNode(flowId);
		TeeFlowProcess currentNode = flowProcessService.get(prcsId);
		
		currentNode.getNextProcess().add(endNode);
		
		flowProcessService.update(currentNode);
		
		json.setRtState(TeeConst.RETURN_OK);
		
		return json;
	}
	
	@RequestMapping("/createProcess")
	@ResponseBody
	public TeeJson createProcess(HttpServletRequest request){
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		int prcsType = TeeStringUtil.getInteger(request.getParameter("prcsType"), 0);
		int parentId = TeeStringUtil.getInteger(request.getParameter("parentId"), 0);
		int x = TeeStringUtil.getInteger(request.getParameter("x"), 0);
		int y = TeeStringUtil.getInteger(request.getParameter("y"), 0);
		String name = TeeStringUtil.getString(request.getParameter("name"));
		
		//获取流程信息
		TeeFlowType flowType = flowTypeService.get(flowId);
		
		//获取当前步骤
		TeeFlowProcess fp = flowProcessService.get(parentId);
		
		int maxPrcsId = flowProcessService.getMaxPrcsId(flowId);
		String prcsName = null;
		//创建新步骤
		TeeFlowProcess process = new TeeFlowProcess();
		if(prcsType==3){//普通节点
			prcsName = "普通节点";
		}else if(prcsType==4){//并发节点
			prcsName = "并发节点";
		}else if(prcsType==5){//聚合节点
			prcsName = "聚合节点";
		}else if(prcsType==6){//子流程节点
			prcsName = "子流程节点";
		}
		
		if(!"".equals(name)){
			process.setPrcsName(name);
		}else{
			process.setPrcsName(prcsName+(maxPrcsId+1));
		}
		//默认不允许自动选人
		process.setAutoSelect(0);
		//超时允许继续办理
		process.setTimeoutHandable(1);
		process.setPrcsId(maxPrcsId+1);
		process.setSortNo(maxPrcsId+1);
		process.setPrcsType(prcsType);
		process.setFlowType(flowType);
		process.setNextPrcsAlert(1);
		process.setX(x);
		process.setY(y);
		
		flowProcessService.save(process);
		
		fp.getNextProcess().add(process);
		flowProcessService.update(fp);
		
		TeeJson json = new TeeJson();
		json.setRtState(TeeConst.RETURN_OK);
		
		Map params = new HashMap();
		params.put("sid", process.getSid());
		params.put("prcsId", process.getPrcsId());
		params.put("prcsName", process.getPrcsName());
		
		json.setRtData(params);
		
		return json;
	}
	
	/**
	 * 更新步骤布局信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateProcessLayout")
	@ResponseBody
	public TeeJson updateProcessLayout(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String jsonModelStr = TeeStringUtil.getString(request.getParameter("jsonModel"));
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		
		flowProcessService.updateProcessLayout(jsonModelStr, flowId);
		
		json.setRtState(TeeConst.RETURN_OK);
		return json;
	}
	
	/**
	 * 删除步骤节点
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteProcess")
	@ResponseBody
	public TeeJson deleteProcess(HttpServletRequest request){
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		flowProcessService.deleteNotReal(sid);
		TeeJson json = new TeeJson();
		json.setRtState(TeeConst.RETURN_OK);
		return json;
	}
	
	
	/**
	 * 删除步骤节点之前  判断该步骤是否有TeeFlowRunPrcs关联
	 * @param request
	 * @return
	 */
	@RequestMapping("/hasRelatedFlowRunPrcs")
	@ResponseBody
	public TeeJson hasRelatedFlowRunPrcs(HttpServletRequest request){
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeJson json=flowProcessService.hasRelatedFlowRunPrcs(sid);	
		return json;
	}
	/**
	 * 获取流程步骤信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getProcessInfo")
	@ResponseBody
	public TeeJson getProcessInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtState(TeeConst.RETURN_OK);
		
		int prcsSeqId = TeeStringUtil.getInteger(request.getParameter("prcsSeqId"), 0);
		json.setRtData(flowProcessService.getFlowProcessInfo2Json(prcsSeqId));
		
		return json;
	}
	
	/**
	 * 更新节点基础信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateBasicInfo")
	@ResponseBody
	public TeeJson updateBasicInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtState(TeeConst.RETURN_OK);
		int prcsSeqId = TeeStringUtil.getInteger(request.getParameter("prcsSeqId"), 0);
		String prcsName = TeeStringUtil.getString(request.getParameter("prcsName"));
		String prcsDesc = TeeStringUtil.getString(request.getParameter("prcsDesc"));
		String outerPage = TeeStringUtil.getString(request.getParameter("outerPage"));
		String innerPage = TeeStringUtil.getString(request.getParameter("innerPage"));
		int viewPriv = TeeStringUtil.getInteger(request.getParameter("viewPriv"),0);
		int forceTurn = TeeStringUtil.getInteger(request.getParameter("forceTurn"),0);
		int sortNo = TeeStringUtil.getInteger(request.getParameter("sortNo"),0);
		int forceParallel = TeeStringUtil.getInteger(request.getParameter("forceParallel"),0);
		int forceAggregation = TeeStringUtil.getInteger(request.getParameter("forceAggregation"),0);
		
		int formSelect=TeeStringUtil.getInteger(request.getParameter("formSelect"),1);
		//获取节点类型
	    int  prcsType=TeeStringUtil.getInteger(request.getParameter("prcsType"),0);
		
		TeeFlowProcess fp = flowProcessService.get(prcsSeqId);
		fp.setPrcsName(prcsName);
		fp.setForceTurn(forceTurn);
		fp.setSortNo(sortNo);
		fp.setPrcsDesc(prcsDesc);
		fp.setForceParallel(forceParallel);
		fp.setForceAggregation(forceAggregation);
		fp.setSortNo(sortNo);
		fp.setPrcsType(prcsType);
		fp.setOuterPage(outerPage);
		fp.setInnerPage(innerPage);
		fp.setFormSelect(formSelect);
		
		//处理下一步骤
		int nextPrcsArray[] = TeeStringUtil.parseIntegerArray(request.getParameter("nextPrcs"));
		fp.getNextProcess().clear();
		for(int prcsSid:nextPrcsArray){
			if(prcsSid==0){
				continue;
			}
			TeeFlowProcess tmp = flowProcessService.get(prcsSid);
			fp.getNextProcess().add(tmp);
		}
		
		flowProcessService.update(fp);
		json.setRtMsg("保存步骤信息成功");
		
		return json;
	}
	
	/**
	 * 更新流转条件
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateCondition")
	@ResponseBody
	public TeeJson updateCondition(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);
		String conditionModel = TeeStringUtil.getString(request.getParameter("requestDataModel"));
		
		flowProcessService.updateCondition(prcsId, conditionModel);
		
		json.setRtState(true);
		json.setRtMsg("保存成功");
		return json;
	}
	
	/**
	 * 更新表单校验
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateFormValid")
	@ResponseBody
	public TeeJson updateFormValid(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);
		String formValidModel = TeeStringUtil.getString(request.getParameter("requestDataModel"));
		
		flowProcessService.updateFormValid(prcsId, formValidModel);
		
		json.setRtState(true);
		json.setRtMsg("保存成功");
		return json;
	}
	
	/**
	 * 获取转交条件数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCondition")
	@ResponseBody
	public TeeJson getCondition(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);
		TeeFlowProcess fp = flowProcessService.get(prcsId);
		
		json.setRtData(fp.getConditionModel());
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 更新表单字段控制
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateFieldCtrlModel")
	@ResponseBody
	public TeeJson updateFieldCtrlModel(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String jsonStr = TeeStringUtil.getString(request.getParameter("jsonStr"));
		int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);
		//获取附件上传控件   控制模型
		String attachCtrModel=TeeStringUtil.getString(request.getParameter("attachCtrModel"));
		TeeFlowProcess process = flowProcessService.get(prcsId);
		process.setFieldCtrlModel(jsonStr);
		process.setAttachCtrlModel(attachCtrModel);
		flowProcessService.update(process);
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtMsg("更新表单字段成功");
		
		return json;
	}
	

	
	/**
	 * 更新扩展信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateExtInfo")
	@ResponseBody
	public TeeJson updateExtInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);
		String pluginClass = TeeStringUtil.getString(request.getParameter("pluginClass"));
		String triggerUrl = TeeStringUtil.getString(request.getParameter("triggerUrl"));
		
		TeeFlowProcess process = flowProcessService.get(prcsId);
		process.setPluginClass(pluginClass);
		process.setTriggerUrl(triggerUrl);
		flowProcessService.update(process);
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtMsg("保存成功！");
		
		return json;
	}
	
	/**
	 * 测试获取经办权限
	 * @param request
	 * @return
	 */
//	@RequestMapping("/testPrcsUser")
//	@ResponseBody
//	public String testPrcsUser(HttpServletRequest request){
//		int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);
//		TeeFlowProcess fp = flowProcessService.get(prcsId);
//		Set<TeePerson> p = workflowService.getPrcsUsersByProcess(fp, null, null);
//		String s = "";
//		for(TeePerson p1:p){
//			s+=p1.getUserId()+" ";
//		}
//		return s;
//	}
	
	/**
	 * 测试选人规则
	 * @param request
	 * @return
	 */
//	@RequestMapping("/testSelectUserRule")
//	@ResponseBody
//	public String testSelectUserRule(HttpServletRequest request){
//		int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);
//		TeeFlowProcess fp = flowProcessService.get(prcsId);
//		Set<TeePerson> p = workflowService.getPrcsUsersBySelectRule(flowRun, frp, nextPrcs, person);
//		String s = "";
//		for(TeePerson p1:p){
//			s+=p1.getUserId()+" ";
//		}
//		return s;
//	}
	
	/**
	 * 获取字段控制模型
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFieldCtrlModel")
	@ResponseBody
	public TeeJson getFieldCtrlModel(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);
		TeeFlowProcess fp = flowProcessService.get(prcsId);
		Map data = new HashMap();
		data.put("fieldCtrlModel", fp.getFieldCtrlModel());
		data.put("attachCtrlModel", fp.getAttachCtrlModel());
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(data);
		
		return json;
	}
	
	/**
	 * 更新流转设置
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatePrcsSet")
	@ResponseBody
	public TeeJson updatePrcsSet(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int prcsSeqId = TeeStringUtil.getInteger(request.getParameter("prcsSeqId"), 0);
		int opFlag = TeeStringUtil.getInteger(request.getParameter("opFlag"), 0);
		int userLock = TeeStringUtil.getInteger(request.getParameter("userLock"),0);
		int feedback = TeeStringUtil.getInteger(request.getParameter("feedback"),0);
		int feedbackViewType = TeeStringUtil.getInteger(request.getParameter("feedbackViewType"),0);
		int forceTurn = TeeStringUtil.getInteger(request.getParameter("forceTurn"),0);
		int goBack = TeeStringUtil.getInteger(request.getParameter("goBack"),0);
		int backTo = TeeStringUtil.getInteger(request.getParameter("backTo"),0);
		int nextPrcsAlert = TeeStringUtil.getInteger(request.getParameter("nextPrcsAlert"),0);
		int beginUserAlert = TeeStringUtil.getInteger(request.getParameter("beginUserAlert"),0);
		int allPrcsUserAlert = TeeStringUtil.getInteger(request.getParameter("allPrcsUserAlert"),0);
		String emailToUsers = TeeStringUtil.getString(request.getParameter("emailToUsers"));
		String prcsEventDef = TeeStringUtil.getString(request.getParameter("prcsEventDef"));
		double timeout = TeeStringUtil.getDouble(request.getParameter("timeout"), 0);
		int timeoutFlag = TeeStringUtil.getInteger(request.getParameter("timeoutFlag"),0);
		int timeoutType = TeeStringUtil.getInteger(request.getParameter("timeoutType"),0);
		int timeoutHandable = TeeStringUtil.getInteger(request.getParameter("timeoutHandable"),0);
		int timeoutAlarm = TeeStringUtil.getInteger(request.getParameter("timeoutAlarm"),0);
		int ignoreType = TeeStringUtil.getInteger(request.getParameter("ignoreType"),0);
		int attachPriv = TeeStringUtil.getInteger(request.getParameter("attachPriv"),0);
		int attachOtherPriv = TeeStringUtil.getInteger(request.getParameter("attachOtherPriv"),0);
		int runNamePriv = TeeStringUtil.getInteger(request.getParameter("runNamePriv"),0);
		int officePriv = TeeStringUtil.getInteger(request.getParameter("officePriv"),0);
		int archivesPriv = TeeStringUtil.getInteger(request.getParameter("archivesPriv"),0);
		int autoTurn = TeeStringUtil.getInteger(request.getParameter("autoTurn"),0);
		int autoSelect = TeeStringUtil.getInteger(request.getParameter("autoSelect"),0);
		String alarmUserIds = TeeStringUtil.getString(request.getParameter("alarmUserIds"));
		String alarmDeptIds = TeeStringUtil.getString(request.getParameter("alarmDeptIds"));
		String alarmRoleIds = TeeStringUtil.getString(request.getParameter("alarmRoleIds"));
		String sealRules = TeeStringUtil.getString(request.getParameter("sealRules"));
		//串签
		int seqOper = TeeStringUtil.getInteger(request.getParameter("seqOper"),0);
		
		//加签字权限
		int addPrcsUserPriv=TeeStringUtil.getInteger(request.getParameter("addPrcsUserPriv"),0);
		int autoSelectFirst=TeeStringUtil.getInteger(request.getParameter("autoSelectFirst"),0);
		
		//获取前台传来的短信提醒模板内容
		String smsTpl = TeeStringUtil.getString(request.getParameter("smsTpl"));
		
		
		TeeFlowProcess fp = flowProcessService.get(prcsSeqId);
		fp.setOpFlag(opFlag);
		fp.setUserLock(userLock);
		fp.setFeedback(feedback);
		fp.setFeedbackViewType(feedbackViewType);
		fp.setForceTurn(forceTurn);
		fp.setGoBack(goBack);
		fp.setBackTo(backTo);
		fp.setNextPrcsAlert(nextPrcsAlert);
		fp.setAllPrcsUserAlert(allPrcsUserAlert);
		fp.setBeginUserAlert(beginUserAlert);
		fp.setTimeout(timeout);
		fp.setTimeoutFlag(timeoutFlag);
		fp.setTimeoutType(timeoutType);
		fp.setIgnoreType(ignoreType);
		fp.setRunNamePriv(runNamePriv);
		fp.setAttachPriv(attachPriv);
		fp.setOfficePriv(officePriv);
		fp.setPrcsEventDef(prcsEventDef);
		fp.setTimeoutAlarm(timeoutAlarm);
		fp.setTimeoutHandable(timeoutHandable);
		fp.setArchivesPriv(archivesPriv);
		fp.setAttachOtherPriv(attachOtherPriv);
		fp.setAlarmUserIds(alarmUserIds);
		fp.setAlarmRoleIds(alarmRoleIds);
		fp.setAlarmDeptIds(alarmDeptIds);
		fp.setAutoTurn(autoTurn);
		fp.setAutoSelect(autoSelect);
		fp.setSeqOper(seqOper);
		fp.setAddPrcsUserPriv(addPrcsUserPriv);
		fp.setSealRules(sealRules);
		fp.setAutoSelectFirst(autoSelectFirst);
		fp.setSmsTpl(smsTpl);
		
		flowProcessService.update(fp);
		json.setRtMsg("保存步骤信息成功");
		json.setRtState(TeeConst.RETURN_OK);
		
		return json;
	}
	
	/**
	 * 添加步骤上经办权限及经办规则
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPrcsPriv")
	@ResponseBody
	public TeeJson addPrcsPriv(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtState(TeeConst.RETURN_OK);
		
		int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);
		//经办人员
		int prcsUser[] = TeeStringUtil.parseIntegerArray(request.getParameter("prcsUser"));
		//经办部门
		int prcsDept[] = TeeStringUtil.parseIntegerArray(request.getParameter("prcsDept"));
		//经办角色
		int prcsRole[] = TeeStringUtil.parseIntegerArray(request.getParameter("prcsRole"));
		//选人规则
		String prcsUserSelectRule [] = TeeStringUtil.parseStringArray(request.getParameter("prcsUserSelectRule"));
		
		//自动选人规则
		String prcsAutoSelectUser [] = TeeStringUtil.parseStringArray(request.getParameter("prcsAutoSelectUser"));
		
		//高级自动选人规则
		String prcsAutoSelectUser1 = TeeStringUtil.getString(request.getParameter("prcsAutoSelectUser1"));
//		List<Map<String,String>> list = TeeJsonUtil.JsonStr2MapList(prcsAutoSelectUser1);
		
		//表单字段规则
		String formItemRule [] = TeeStringUtil.parseStringArray(request.getParameter("formItemRule"));
		
		flowProcessService.addPrcsPriv(prcsId, prcsUser, prcsDept, prcsRole, prcsUserSelectRule, prcsAutoSelectUser,prcsAutoSelectUser1);
		
		return json;
	}
	
	/**
	 * 更新数据映射
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateFieldMapping")
	@ResponseBody
	public TeeJson updateFieldMapping(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		int prcsSeqId = TeeStringUtil.getInteger(request.getParameter("prcsSeqId"), 0);
		String fieldMapping = TeeStringUtil.getString(request.getParameter("fieldMapping"));
		String fieldReverseMapping = TeeStringUtil.getString(request.getParameter("fieldReverseMapping"));
		int shareAttaches = TeeStringUtil.getInteger(request.getParameter("shareAttaches"), 0);
		int childFlowId = TeeStringUtil.getInteger(request.getParameter("childFlowId"), 0);
		int multiInst = TeeStringUtil.getInteger(request.getParameter("multiInst"), 0);
		int userLock = TeeStringUtil.getInteger(request.getParameter("userLock"), 0);
		int shareDoc = TeeStringUtil.getInteger(request.getParameter("shareDoc"), 0);
		int prcsWait = TeeStringUtil.getInteger(request.getParameter("prcsWait"), 0);
		
		//更新部分字段
		Map map = new HashMap();
		map.put("fieldMapping", fieldMapping);
		map.put("fieldReverseMapping", fieldReverseMapping);
		map.put("shareAttaches", shareAttaches);
		map.put("childFlowId", childFlowId);
		map.put("multiInst", multiInst);
		map.put("userLock", userLock);
		map.put("shareDoc", shareDoc);
		map.put("prcsWait", prcsWait);
		
		flowProcessService.updateByMap(map,prcsSeqId);
		
		json.setRtMsg("更新成功");
		return json;
	}
	
	/**
	 * 获取流程树
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getFlowTree.action")
	@ResponseBody
	public TeeJson getFlowTree(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtState(TeeConst.RETURN_OK);
		
		List<TeeZTreeModel> list = new ArrayList<TeeZTreeModel>();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		//创建全部流程节点
		TeeZTreeModel def = new TeeZTreeModel();
		def.setName("默认分类");
		def.setId("sort0");
		def.setTitle("默认分类");
		def.setDisabled(true);
		def.setOpen(true);
		def.setIconSkin("wfNode1");
		list.add(def);
		
		
		//获取流程分类与流程类型
		List<TeeFlowType> typeList = workflowService.getCreatablePrivFlowListByPerson(person);
		List<TeeFlowSort> sortList = flowSortService.list();
		
		for(TeeFlowSort fs:sortList){
			TeeZTreeModel m = new TeeZTreeModel();
			m.setId("sort"+fs.getSid());
			m.setName(fs.getSortName());
			m.setTitle(fs.getSortName());
			m.setDisabled(true);
			m.setOpen(true);
			m.setIconSkin("wfNode1");
			list.add(m);
		}
		for(TeeFlowType ft:typeList){
			TeeZTreeModel m = new TeeZTreeModel();
			m.setId(String.valueOf(ft.getSid()));
			m.setName(ft.getFlowName());
			m.setTitle(ft.getFlowName());
			m.setpId("sort"+(ft.getFlowSort()==null?0:ft.getFlowSort().getSid()));
			m.setIconSkin("wfNode2");
			list.add(m);
		}
		
		json.setRtData(list);
		return json;
	}
	
	/**
	 * 获取步骤经办权限相关信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPrcsPriv")
	@ResponseBody
	public TeeJson getPrcsPriv(HttpServletRequest request){
		TeeJson json = null;
		int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);
		json = flowProcessService.getPrcsPriv(prcsId);
		
		return json;
	}
	
	
	/**
	 * 手机字段显示控制模型
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateMobileFieldCtrlModel")
	@ResponseBody
	public TeeJson updateMobileFieldCtrlModel(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String mobileFiledCtrModel = TeeStringUtil.getString(request.getParameter("mobileFieldContrlModel"));
		int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);
		//获取是否开启移动控制模板
		int isOpenMobileCtr=TeeStringUtil.getInteger(request.getParameter("isOpenMobileCtr"), 0);
		//获取流程信息控制模型
		String workFlowCtrlModel=TeeStringUtil.getString(request.getParameter("workFlowCtrlModel"));
		
		
		
		TeeFlowProcess process = flowProcessService.get(prcsId);
		process.setMobileFieldCtrlModel(mobileFiledCtrModel);
		process.setIsOpenMobileCtr(isOpenMobileCtr);
		//设置流程信息控制模型的值
		process.setWorkFlowCtrlModel(workFlowCtrlModel);
		
		flowProcessService.update(process);	
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtMsg("移动表单模板更新成功！");
		
		return json;
	}

	
	
	
	/**
	 * 手机字段显示控制模型
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFlowProcess")
	@ResponseBody
	public TeeJson getFlowProcess(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);	
		TeeFlowProcess process = flowProcessService.get(prcsId);
		List<Object> result=new ArrayList<Object>();
		if(process!=null){
			result.add(process.getMobileFieldCtrlModel());
			result.add(process.getIsOpenMobileCtr());
			result.add(process.getWorkFlowCtrlModel());
		}
		json.setRtData(result);
		json.setRtState(TeeConst.RETURN_OK);
		
		return json;
	}
	
	
	/**
	 * 获取全局表单的所有表单项
	 * @param request
	 * @return
	 */
	@RequestMapping("/getBasicFormItems")
	@ResponseBody
	public TeeJson getBasicFormItems(HttpServletRequest request){
		return flowProcessService.getBasicFormItems(request);
	}
	
	
	/**
	 * 获取独立表单内容
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFormShortById")
	@ResponseBody
	public TeeJson getFormShortById(HttpServletRequest request){
		return flowProcessService.getFormShortById(request);
	}
	
	
	
	/**
	 * 更新独立表单内容
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateFormShort")
	@ResponseBody
	public TeeJson updateFormShort(HttpServletRequest request){
		return flowProcessService.updateFormShort(request);
	}
	
	
	public void setFlowProcessService(TeeFlowProcessServiceInterface flowProcessService) {
		this.flowProcessService = flowProcessService;
	}


	public void setFlowTypeService(TeeFlowTypeServiceInterface flowTypeService) {
		this.flowTypeService = flowTypeService;
	}


	public void setWorkflowService(TeeWorkflowServiceInterface workflowService) {
		this.workflowService = workflowService;
	}
}
