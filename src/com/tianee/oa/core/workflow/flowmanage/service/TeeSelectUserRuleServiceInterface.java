package com.tianee.oa.core.workflow.flowmanage.service;

import java.util.Set;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeSelectUserRule;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;

public interface TeeSelectUserRuleServiceInterface {

	/**
	 * 以流程发起人为引导
	 * @param flowRun
	 * @param frp
	 * @param nextPrcs
	 * @param person
	 * @return
	 */
	public abstract Set<TeePerson> leadByBeginUser(TeeFlowRun flowRun,
			TeeFlowRunPrcs frp, TeeFlowProcess nextPrcs, TeePerson person,
			TeeSelectUserRule rule);

	/**
	 * 以当前办理人为引导
	 * @param flowRun
	 * @param frp
	 * @param nextPrcs
	 * @param person
	 * @param rule
	 * @return
	 */
	public abstract Set<TeePerson> leadByCurrentPrcsUser(TeeFlowRun flowRun,
			TeeFlowRunPrcs frp, TeeFlowProcess nextPrcs, TeePerson person,
			TeeSelectUserRule rule);

	/**
	 * 以该流程所有经办人为引导
	 * @param flowRun
	 * @param frp
	 * @param nextPrcs
	 * @param person
	 * @param rule
	 * @return
	 */
	public abstract Set<TeePerson> leadByAllPrcsUser(TeeFlowRun flowRun,
			TeeFlowRunPrcs frp, TeeFlowProcess nextPrcs, TeePerson person,
			TeeSelectUserRule rule);

	/**
	 * 以当前步骤经办人为引导
	 * @param flowRun
	 * @param frp
	 * @param nextPrcs
	 * @param person
	 * @param rule
	 * @return
	 */
	public abstract Set<TeePerson> leadByThisPrcsUsers(TeeFlowRun flowRun,
			TeeFlowRunPrcs frp, TeeFlowProcess nextPrcs, TeePerson person,
			TeeSelectUserRule rule);

	/**
	 * 以上一步经办人为引导
	 * @param flowRun
	 * @param frp
	 * @param nextPrcs
	 * @param person
	 * @param rule
	 * @return
	 */
	public abstract Set<TeePerson> leadByPrePrcsUsers(TeeFlowRun flowRun,
			TeeFlowRunPrcs frp, TeeFlowProcess nextPrcs, TeePerson person,
			TeeSelectUserRule rule);

	/**
	 * 按照部门过滤选择
	 * @param flowRun
	 * @param frp
	 * @param nextPrcs
	 * @param person
	 * @param rule
	 * @return
	 */
	public abstract Set<TeePerson> leadByDeptFilter(TeeFlowRun flowRun,
			TeeFlowRunPrcs frp, TeeFlowProcess nextPrcs, TeePerson person,
			TeeSelectUserRule rule, Set<TeePerson> personSet);

	/**
	 * 通过所属部门查找
	 * @param persons
	 * @return
	 */
	public abstract Set<TeePerson> findByDirectDept(Set<TeePerson> persons);

	/**
	 * 通过所属部门主管领导查询
	 * @param persons
	 * @return
	 */
	public abstract Set<TeePerson> findByDirectDeptLeader1(
			Set<TeePerson> persons);

	/**
	 * 通过所属部门分管领导查询
	 * @param persons
	 * @return
	 */
	public abstract Set<TeePerson> findByDirectDeptLeader2(
			Set<TeePerson> persons);

	/**
	 * 通过上级部门选择
	 * @param persons
	 * @return
	 */
	public abstract Set<TeePerson> findByParentDept(Set<TeePerson> persons);

	/**
	 * 根据上级部门主管领导选择
	 * @param persons
	 * @return
	 */
	public abstract Set<TeePerson> findByParentDeptLeader1(
			Set<TeePerson> persons);

	/**
	 * 根据上级部门分管领导选择
	 * @param persons
	 * @return
	 */
	public abstract Set<TeePerson> findByParentDeptLeader2(
			Set<TeePerson> persons);

	/**
	 * 通过下级部门查询
	 * @param persons
	 * @return
	 */
	public abstract Set<TeePerson> findByChildDept(Set<TeePerson> persons);

	/**
	 * 通过下级部门主管领导选择
	 * @param persons
	 * @return
	 */
	public abstract Set<TeePerson> findByChildDeptLeader1(Set<TeePerson> persons);

	/**
	 * 通过下级部门分管领导选择
	 * @param persons
	 * @return
	 */
	public abstract Set<TeePerson> findByChildDeptLeader2(Set<TeePerson> persons);

	public abstract void setPersonDao(TeePersonDao personDao);

}