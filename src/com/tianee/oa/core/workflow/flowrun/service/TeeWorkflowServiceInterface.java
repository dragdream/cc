package com.tianee.oa.core.workflow.flowrun.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeSelectUserRule;
import com.tianee.oa.core.workflow.flowmanage.service.TeeSelectUserRuleServiceInterface;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunVars;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeWorkFlowInfoModel;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeWorkflowServiceInterface {

	public abstract int createNewWork(TeeFlowType ft, TeePerson beginUser);

	/**
	 * 获取未接收的待办工作
	 * 
	 * @param person
	 * @param firstResult
	 * @param pageSize
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson getNoReceivedWorks(Map queryParams,
			TeePerson loginUser, TeeDataGridModel dataGridModel);

	/**
	 * 获取已接受的工作
	 * 
	 * @param person
	 * @param firstResult
	 * @param pageSize
	 * @return
	 * @throws ParseException 
	 */
	public abstract TeeEasyuiDataGridJson getReceivedWorks(Map queryParams,
			TeePerson loginUser, TeeDataGridModel dataGridModel);

	/**
	 * 获取关注工作
	 * 
	 * @param person
	 * @param firstResult
	 * @param pageSize
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson getConcernedWorks(Map queryParams,
			TeePerson loginUser, TeeDataGridModel dataGridModel);

	/**
	 * 获取待查阅的工作
	 * 
	 * @param queryParams
	 * @param loginUser
	 * @param dataGridModel
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson getViewsWorks(Map queryParams,
			TeePerson loginUser, TeeDataGridModel dataGridModel);

	/**
	 * 获取挂起工作
	 * 
	 * @param person
	 * @param firstResult
	 * @param pageSize
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson getSuspendedWorks(Map queryParams,
			TeePerson loginUser, TeeDataGridModel dataGridModel);

	/**
	 * 获取未办理的工作
	 * 
	 * @param person
	 * @param firstResult
	 * @param pageSize
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson getNoHandledWorks(TeePerson person,
			TeeDataGridModel m);

	/**
	 * 根据runId获取当前流程走到了哪一步
	 * @return
	 */
	public abstract String getCurrStepDescByRunId(int runId, Object endTime);

	/**
	 * 获取已办结的工作
	 * 
	 * @param person
	 * @param firstResult
	 * @param pageSize
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson getHandledWorks(Map queryParams,
			TeePerson loginUser, TeeDataGridModel dataGridModel);

	/**
	 * 获取已办结信息
	 * 
	 * @param runId
	 * @param uuid
	 * @return
	 */
	public abstract List<TeeWorkFlowInfoModel> getExtraWorksByFlowRun(
			int runId, String uuid);


	public abstract List<TeePerson> getPrcsUsersByProcess(
			TeeFlowProcess flowProcess, TeeDepartment deptFilter,
			TeeUserRole roleFilter);

	public abstract boolean hasPrcsPrivByDefault(TeePerson person,
			TeeFlowProcess flowProcess);

	public abstract boolean isInDepartment(TeePerson person,
			TeeDepartment deptFilter);

	public abstract boolean isInUserRole(TeePerson person,
			TeeUserRole roleFilter);

	public abstract boolean isInDepartment(TeePerson person,
			Set<TeeDepartment> deptFilterList);

	public abstract boolean isInUserRole(TeePerson person,
			Set<TeeUserRole> roleFilterList);

	public abstract Set<TeePerson> getPrcsUsersBySelectRule(TeeFlowRun flowRun,
			TeeFlowRunPrcs frp, TeeFlowProcess nextPrcs, TeePerson person);

	public abstract Set<TeePerson> getPrcsUsersByRule(TeeFlowRun flowRun,
			TeeFlowRunPrcs frp, TeeFlowProcess nextPrcs, TeePerson person,
			TeeSelectUserRule rule);

	public abstract void setSimpleDaoSupport(
			TeeSimpleDaoSupport simpleDaoSupport);

	public abstract void setPersonService(TeePersonService personService);

	public abstract void setSelectUserRuleService(
			TeeSelectUserRuleServiceInterface selectUserRuleService);

	/**
	 * 获取当前人有创建权限的流程
	 * 
	 * @param person
	 * @return
	 */
	public abstract List<TeeFlowType> getCreatablePrivFlowListByPerson(
			TeePerson person);

	/**
	 * 获取当前人有创建权限的流程
	 * 
	 * @param person
	 * @return
	 */
	public abstract List<TeeZTreeModel> getHandableFlowType2SelectCtrl(
			TeePerson person, Map requestMap);

	/**
	 * 获取当前人有创建权限的流程
	 * 
	 * @param person
	 * @return
	 */
	public abstract List<TeeZTreeModel> getFlowType2SelectCtrl();

	/**
	 * 获取当前人有创建权限的流程
	 * 
	 * @param person
	 * @return
	 */
	public abstract List<Map> getCreatablePrivFlowListModelsByPerson(
			TeePerson person);

	public abstract boolean isInPersons(TeePerson person, Set<TeePerson> persons);

	/**
	 * 快速查询，用于桌面上的查询
	 * 
	 * @param person
	 * @return
	 */
	public abstract List<Map> quickSearch(String psnName, TeePerson person);

	/**
	 * 保存会签控件数据
	 * 
	 * @param runId
	 * @param itemId
	 * @param signData
	 * @param loginUser
	 */
	public abstract void saveCtrlFeedback(int runId, int itemId,
			String content, String signData, String rand, TeePerson loginUser,
			String sealData, String hwData, int frpSid, String picData,
			String h5Data, String mobiData);

	/**
	 * 获取指定流程的目标控件的会签意见
	 * 
	 * @param runId
	 * @param itemId
	 * @param signData
	 * @param loginUser
	 */
	public abstract List<Map> getCtrlFeedbacks(int runId, int itemId,
			int sortField, String order);

	public abstract void delCtrlFeedbacks(int sid);

	public abstract void suspend(int frpSid);

	public abstract void unsuspend(int frpSid);


	/**
	 * 导出excel表格
	 * 
	 * @param params
	 * @param loginUser
	 * @param response
	 */
	public abstract void exportExcel(Map params, TeePerson loginUser,
			HttpServletResponse response);

	//获取挂起工作列表
	public abstract List getSuspendedWorksList(Map queryParams,
			TeePerson loginUser);

	//获取我已办结的流程列表信息
	public abstract List getHandledWorksList(Map queryParams,
			TeePerson loginUser);

	//获取已关注的流程列表
	public abstract List getConcernedWorksList(Map queryParams,
			TeePerson loginUser);

	//我的查阅
	public abstract List getViewsWorksList(Map queryParams, TeePerson loginUser);

	// 获取我的待办的流程列表
	public abstract List getReceivedWorksList(Map queryParams,
			TeePerson loginUser);

	/**
	 * 获取我发起的并且未删除的流程数据
	 * @param person
	 * @param dm
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson getMyBeginRunList(
			HttpServletRequest request, TeePerson person, TeeDataGridModel dm);

	public abstract List<TeeFlowRunVars> getFlowRunVars(int runId);

	public abstract void view(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * 创建流程数据的表单
	 * @param ft
	 * @return
	 * @throws IOException 
	 */
	public abstract void toView(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * 创建流程数据的表单
	 * @param ft
	 * @return
	 * @throws IOException 
	 */
	public abstract void toFlowRun(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * type=1  代表我的待办   type=2 代表我已办结
	 * 获取相关流程类型的ids  flowTypeIds
	 * @param request
	 * @return
	 */
	public abstract TeeJson getPrivFlowTypeIds(HttpServletRequest request);

	/**
	 * 我的待办  我已办结  选择所属流程   获取相关的流程类型   不相关的不显示
	 * @return
	 */
	public abstract List<TeeZTreeModel> getPrivFlowTypes(
			HttpServletRequest request);

	/**
	 * 固定流程
	 * @return
	 */
	public abstract List<TeeZTreeModel> getFixedFlowType2SelectCtrl();

	/**
	 * 撤销
	 * */
	public abstract TeeJson doCheXiao(HttpServletRequest request);

	/**
	 * 克隆附件到指定流程中
	 * */
	public abstract TeeJson keLongAttachment(HttpServletRequest request);

	
	/**
	 * 手动创建流程实例
	 * @param request
	 * @return
	 */
	public abstract TeeJson addFlowRunPrcs(HttpServletRequest request);
	
	
    /**
     * 手动修改流程步骤实例的状态
     * @param request
     * @return
     */
	public abstract TeeJson updateFlowRunPrcs(HttpServletRequest request);
	
	
	public List<TeeFormItem> getFormItemsByFormAndCtrTitle(TeeForm form,String ctrlTitle);

	//获取中间表中某个控件的值
	public abstract List getCtrlValue(String ctrlName, String tableName,
			int runId);

}