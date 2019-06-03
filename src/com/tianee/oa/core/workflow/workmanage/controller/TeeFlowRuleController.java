package com.tianee.oa.core.workflow.workmanage.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowSort;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowSortServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowRule;
import com.tianee.oa.core.workflow.workmanage.service.TeeFlowRuleServiceInterface;
import com.tianee.oa.core.workflow.workmanage.service.TeeWorkQueryServiceInterface;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("flowRule")
public class TeeFlowRuleController {
	@Autowired
	TeeFlowRuleServiceInterface ruleService;
	
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	@Qualifier("teeWorkflowService")
	private TeeWorkflowServiceInterface workflowService;
	
	@Autowired
	private TeeFlowSortServiceInterface flowSortService;
	
	@Autowired
	private TeeWorkQueryServiceInterface workQueryService;
	
	/**
	 * 新建规则
	 * @param request
	 * @param rule
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addRule.action")
	@ResponseBody
	public TeeJson addRule(HttpServletRequest request){
		
		TeeJson json = new TeeJson();
		int user = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
		int toUser = TeeStringUtil.getInteger(request.getParameter("toUserId"), 0);
		String endDate = TeeStringUtil.getString(request.getParameter("endDate"));
		String beginDate = TeeStringUtil.getString(request.getParameter("beginDate"));
		String flowIdStr = TeeStringUtil.getString(request.getParameter("flowId"));
		String status = TeeStringUtil.getString(request.getParameter("status"));
//		System.out.println(status);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date1 = null;
		Date date2 = null;
		try {
			if(!TeeUtility.isNullorEmpty(beginDate)){
				date1 = sdf.parse(beginDate);
			}else{
				date1 = new Date();
			}
			if(!TeeUtility.isNullorEmpty(endDate)){
				date2 = sdf.parse(endDate);
			}else{
				date2 = sdf.parse("2155-01-01 00:00");
			}
			if(status.equals("1")){
				date1 = sdf.parse("1900-01-01 00:00");
				date2 = sdf.parse("1900-01-01 00:00");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(user==0){
			user = loginPerson.getUuid();
		}
		ruleService.addRule(user,toUser,date1,date2,flowIdStr,TeeStringUtil.getInteger(status, 0));
		
		json.setRtMsg("添加委托规则成功！");
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 编辑规则
	 * @param request
	 * @param rule
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/editRule.action")
	@ResponseBody
	public TeeJson editRule(HttpServletRequest request){
		
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		int user = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
		int toUser = TeeStringUtil.getInteger(request.getParameter("toUserId"), 0);
		String endDate = TeeStringUtil.getString(request.getParameter("endDate"));
		String beginDate = TeeStringUtil.getString(request.getParameter("beginDate"));
		String flowIdStr = TeeStringUtil.getString(request.getParameter("flowId"));
		String status = TeeStringUtil.getString(request.getParameter("status"));
//		System.out.println(status);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date1 = null;
		Date date2 = null;
		try {
			if(!TeeUtility.isNullorEmpty(beginDate)){
				date1 = sdf.parse(beginDate);
			}else{
				date1 = new Date();
			}
			if(!TeeUtility.isNullorEmpty(endDate)){
				date2 = sdf.parse(endDate);
			}else{
				date2 = sdf.parse("2155-01-01 00:00");
			}
			if(status.equals("1")){
				date1 = sdf.parse("1900-01-01 00:00");
				date2 = sdf.parse("1900-01-01 00:00");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(user==0){
			user = loginPerson.getUuid();
		}
		ruleService.editRule(sid,user,toUser,date1,date2,Integer.parseInt(flowIdStr),TeeStringUtil.getInteger(status, 0));
		
		json.setRtMsg("修改委托规则成功！");
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 得到规则
	 * @param request
	 * @return
	 */
	@RequestMapping("/getRule.action")
	@ResponseBody
	public TeeJson getRule(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtState(true);
		json.setRtData(ruleService.getRule(sid));
		return json;
	}
	
	/**
	 * 清空规则
	 * @param request
	 * @return
	 */
	@RequestMapping("/delAll.action")
	@ResponseBody
	public TeeJson delAll(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtState(true);
		int targetUserId = TeeStringUtil.getInteger(request.getParameter("toUserId"), 0);
		int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		ruleService.delAll(targetUserId,flowId);
		return json;
	}
	
	/**
	 * 删除规则
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteRule")
	@ResponseBody
	public TeeJson deleteRule(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		int ruleId = TeeStringUtil.getInteger(request.getParameter("ruleId"), 0);
		ruleService.deleteRule(ruleId);
		
		json.setRtState(true);
		json.setRtMsg("删除委托规则成功");
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
		person = personService.selectByUuid(person.getUuid()+"");
		
		//创建全部流程节点
		TeeZTreeModel alls = new TeeZTreeModel();
		alls.setName("全部流程");
		alls.setId("0");
		alls.setTitle("全部流程");
		alls.setDisabled(false);
		alls.setOpen(true);
		list.add(alls);
		
		//创建全部流程节点
		TeeZTreeModel def = new TeeZTreeModel();
		def.setName("默认分类");
		def.setId("sort0");
		def.setTitle("默认分类");
		def.setDisabled(true);
		def.setOpen(true);
		list.add(def);
		
		//获取当前委托人所委托的流程
		List<TeeFlowRule> rules = ruleService.getDeligatorRules(person.getUuid());
		
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
			list.add(m);
		}
		boolean ctn = false;
		for(TeeFlowType ft:typeList){
			ctn = false;
			//如果已经有委托的规则，则过滤掉
			for(TeeFlowRule rule:rules){
				if(rule.getFlowType()==ft){
					ctn = true;
				}
			}
			if(ctn){
				continue;
			}
			
			TeeZTreeModel m = new TeeZTreeModel();
			m.setId(String.valueOf(ft.getSid()));
			m.setName(ft.getFlowName());
			m.setTitle(ft.getFlowName());
			m.setpId("sort"+(ft.getFlowSort()==null?0:ft.getFlowSort().getSid()));
			list.add(m);
		}
		
		json.setRtData(list);
		return json;
	}
	
	/**
	 * 获取流程委托列表
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getFlowList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getFlowList(TeeDataGridModel dm,HttpServletRequest request){
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int fromId = person.getUuid();
		String user = request.getParameter("toUserId");
		String entrustStatus = request.getParameter("status");
		int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"),0);
		if(TeeUtility.isNullorEmpty(entrustStatus)){
			entrustStatus = "0";
		}
		if(!TeeUtility.isNullorEmpty(user)){
			fromId = Integer.parseInt(user);
		}
		return ruleService.datagrid(dm,fromId,entrustStatus,flowId);
	}
	
	/**
	 * 获取已委托记录列表
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getEntrustRecordList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getEntrustRecordList(TeeDataGridModel dm,HttpServletRequest request){
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int fromId = person.getUuid();
		String user = request.getParameter("toUserId");
		String entrustStatus = request.getParameter("workName");
		String qs = TeeStringUtil.getString(request.getParameter("qs"));
		if(TeeUtility.isNullorEmpty(entrustStatus)){
			entrustStatus = "0";
		}
		if(!TeeUtility.isNullorEmpty(user)){
			fromId = Integer.parseInt(user);
		}
		
		int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		return ruleService.entrustDatagrid(dm,fromId,qs,entrustStatus,flowId);
	}
	
	/**
	 * 获取被委托记录列表
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getEntrustedRecordList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getEntrustedRecordList(TeeDataGridModel dm,HttpServletRequest request){
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int fromId = person.getUuid();
		String user = request.getParameter("toUserId");
		String entrustStatus = request.getParameter("workName");
		String qs = TeeStringUtil.getString(request.getParameter("qs"));
		if(TeeUtility.isNullorEmpty(entrustStatus)){
			entrustStatus = "0";
		}
		if(!TeeUtility.isNullorEmpty(user)){
			fromId = Integer.parseInt(user);
		}
		
		
		int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"),0);
		return ruleService.entrustedDatagrid(dm,fromId,qs,entrustStatus,flowId);
	}


}
