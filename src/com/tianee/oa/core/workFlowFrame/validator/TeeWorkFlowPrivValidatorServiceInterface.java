package com.tianee.oa.core.workFlowFrame.validator;

import java.util.List;
import java.util.Map;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeSelectUserRule;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowTypeDao;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunPrcsDao;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.core.workflow.workmanage.dao.TeeFlowPrivDao;

public interface TeeWorkFlowPrivValidatorServiceInterface {

	/**
	 * 自由流程发起权限校验---重查数据库方式
	 * @param person
	 * @param flowtype
	 * @return
	 */
	public boolean freeFlowCreatePrivValide(TeePerson person,
			TeeFlowType flowtype);

	/**
	 * 判断固定流是否存在发起权限 ---- 直接获取第一步骤   根据人员、部门、角色进行判断
	 * @param person
	 * @param flowtype
	 * @return
	 */
	public boolean fixedFlowCreatePrivValide(TeePerson person,
			TeeFlowType flowtype);

	/**
	 * 判断是否有监控权限
	 * @param person
	 * @param flowtype
	 * @return
	 */
	public boolean monitorPrivValidator(TeePerson person, TeeFlowType flowtype);

	/**
	 * 条件转交条件判断
	 * @param items  form_item表单项list
	 * @param data  实例data（run_data）
	 * @param frp  实际步骤对象
	 * @param fp  下一步流程步骤
	 * @return
	 */
	public boolean turnConditionAnalyse(List<TeeFormItem> items,
			Map<String, String> data, TeeFlowRunPrcs frp, TeeFlowProcess fp);

	/***
	 * 获取固定流程基本权限信息
	 * @param flowRunPrcs
	 * @return
	 */
	public Map getFixedFlowBasicPriv(TeeFlowType flowType,
			TeeFlowRunPrcs flowRunPrcs);

	/***
	 * 获取自由流程基本权限信息
	 * @param flowRunPrcs
	 * @return
	 */
	public Map getFreeFlowBasicPriv(TeeFlowRunPrcs flowRunPrcs);

	/**
	 * 获取过滤选授权选人人员信息
	 * @param flowType
	 * @param flowRunPrcs
	 * @return
	 */
	public String getFreeFlowFilterSelectPersonInfo(TeeFlowProcess flowProcess,
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

	public TeeFlowTypeDao getFlowTypeDao();

	public void setFlowTypeDao(TeeFlowTypeDao flowTypeDao);

	public TeeFlowPrivDao getFlowPrivDao();

	public void setFlowPrivDao(TeeFlowPrivDao flowPrivDao);

	public void setPersonDao(TeePersonDao personDao);

	public void setFlowRunPrcsDao(TeeFlowRunPrcsDao flowRunPrcsDao);

	public void setDeptDao(TeeDeptDao deptDao);

}