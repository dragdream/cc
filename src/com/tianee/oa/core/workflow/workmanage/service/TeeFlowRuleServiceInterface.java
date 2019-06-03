package com.tianee.oa.core.workflow.workmanage.service;

import java.util.Date;
import java.util.List;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowRule;
import com.tianee.oa.core.workflow.workmanage.model.TeeFlowRuleModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

public interface TeeFlowRuleServiceInterface {

	/**
	 * 删除规则
	 * @param ruleId
	 * @return
	 */
	public abstract TeeFlowRule deleteRule(int ruleId);

	/**
	 * 保存规则
	 * @param flowRule
	 * @return
	 */
	public abstract void addRule(int user, int toUser, Date startDate,
			Date endDate, String flowIdStr, int status);

	/**
	 * 编辑规则
	 * @return
	 */
	public abstract void editRule(int sid, int user, int toUser,
			Date startDate, Date endDate, int flowId, int status);

	/**
	 * 获取规则
	 * @return
	 */
	public abstract TeeFlowRuleModel getRule(int sid);

	/**
	 * 清空所有规则
	 * @return
	 */
	public abstract void delAll(int targetUserId, int flowId);

	/**
	 * 获得流程树
	 * @param flowRuleTree
	 * @return
	 */
	public abstract String getFlowTree();

	/**
	 * 获取委托人的规则
	 * @param personId
	 * @return
	 */
	public abstract List<TeeFlowRule> getDeligatorRules(int personId);

	public abstract TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,
			int userId, String entrustStatus, int flowId);

	/**
	 * 根据person和flowTypeId返回真实办理人
	 * @param person
	 * @param flowTypeId
	 * @return personList
	 */
	public abstract List<TeePerson> returnRealPerson(TeePerson person,
			int flowTypeId);

	public abstract TeeEasyuiDataGridJson entrustDatagrid(TeeDataGridModel dm,
			int userId, String qs, String entrustStatus, int flowId);

	public abstract TeeEasyuiDataGridJson entrustedDatagrid(
			TeeDataGridModel dm, int userId, String qs, String entrustStatus,
			int flowId);


	public abstract void setPersonDao(TeePersonDao personDao);

}