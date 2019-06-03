package com.tianee.oa.core.workFlowFrame.dataloader;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeSelectAutoUserRule;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeSelectUserRule;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;

public interface TeeFixedFlowDataLoaderInterface {

	/**
	 * 固定流程转交步骤返回信息
	 * 通用实现在<TeeSimpleDataLoader>
	 * @param person
	 * @param flowRunPrcs
	 * @return
	 */
	public Map getHandlerData(Map requestData, TeePerson loginPerson);

	/**
	 * 获取固定流程的之前步骤
	 * @param frpSid
	 * @param loginPerson
	 * @return
	 */
	public List<Map> getPrePrcsList(int frpSid, TeePerson loginPerson);

	/**
	 * 获取该步骤的直属上一步骤的已走过的步骤
	 * @param request
	 * @return
	 */
	public List<Map> getPreReachablePrcsList(int frpSid, TeePerson loginPerson);

	/***
	 *  获取流程转交界面信息 ---固定流程
	 *  @param requestData 前台传入参数 一把为 frpSid runId flowId 
	 *  @param loginPerson 系统登录人员
	 *  
	 */
	public Map getTurnHandlerData(Map requestData, TeePerson loginPerson);

	/**
	 * 获取固定流程所有隶属当前步骤的下一步骤节点
	 * @param flowRunPrcs  流程实例步骤
	 * @param flowtype  流程
	 * @return
	 */
	public Map getFixedFlowTurnInfo(TeeFlowType flowType,
			TeeFlowRunPrcs flowRunPrcs, TeePerson loginPerson, Map requestData);

	/**
	 * 转交页面，获取流程步骤信息  --- 固定流程步骤---转换为JSONMap
	 * @param flowProcess
	 * @return
	 */
	public Map getFixedFlowProcessInfo(TeeFlowProcess flowProcess,
			TeeFlowRunPrcs flowRunPrcs, TeePerson loginPerson);

	/**
	 * 获取过滤选授权选人人员信息
	 * @param flowType
	 * @param flowRunPrcs
	 * @return
	 */
	public Map getFreeFlowFilterSelectPersonInfo(TeeFlowProcess flowProcess,
			TeeFlowRunPrcs flowRunPrcs, TeePerson loginPerson);

	/**
	 * 获取过滤人员hql语句
	 * @param rule   过滤条件人员
	 * @param flowRunPrcs   实例步骤 
	 * @param flowRun 实例
	 * @param loginPerson  系统当前登录人
	 * @return 
	 */
	public String getFilterRuleHql(TeeSelectUserRule rule,
			TeeFlowRunPrcs flowRunPrcs, TeeFlowRun flowRun,
			TeePerson loginPerson);

	/**
	 * 获取自动选人过滤人员Id字符串
	 * @param rule   过滤条件人员
	 * @param flowRunPrcs   实例步骤 
	 * @param flowRun 实例
	 * @param loginPerson  系统当前登录人
	 * 
	 * @return  数组 0- 经办人     1- 主办人
	 */
	public Map getAutoSelectUserRulePersonId(TeeSelectAutoUserRule autoRule,
			TeeFlowRunPrcs flowRunPrcs, TeeFlowRun flowRun,
			TeePerson loginPerson, Set<TeePerson> prcsPrvUserIds,
			Map<String, String> formDatas, List<TeeFormItem> items);

	/**
	 * 高级自动选人规则
	 * @param flowRunPrcs
	 * @param flowRun
	 * @param loginPerson
	 * @param prcsPrvUserIds
	 * @return
	 */
	public Map getSeniorAutoSelectUserRulePersonId(TeeFlowRunPrcs flowRunPrcs,
			TeeFlowRun flowRun, TeePerson loginPerson,
			Set<TeePerson> prcsPrvUserIds, String selectAutoUserSenior,
			Map<String, String> formDatas, List<TeeFormItem> items);

	public String[] turnConditionText(String exp,
			List<Map<String, String>> conditionList2, TeeFlowRunPrcs frp,
			TeeFlowProcess fp, TeeFlowProcess curFp, List<TeeFormItem> items,
			Map<String, String> data, Map requestData);

	/**
	 * 条件转交条件判断
	 * @param items  form_item表单项list
	 * @param data  实例data（run_data）
	 * @param frp  实际步骤对象
	 * @param fp  下一步流程步骤
	 * @return
	 */
	public String[] turnConditionAnalyse(List<TeeFormItem> items,
			Map<String, String> data, TeeFlowRunPrcs frp, TeeFlowProcess curFp,
			TeeFlowProcess fp, Map requestData);



}