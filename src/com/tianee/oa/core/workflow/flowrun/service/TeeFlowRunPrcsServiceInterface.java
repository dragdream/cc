package com.tianee.oa.core.workflow.flowrun.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowRunPrcsModel;
import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeFlowRunPrcsServiceInterface {

	public abstract TeeFlowRunPrcs get(int sid);

	public abstract void delete(TeeFlowRunPrcs flowRunPrcs);

	public abstract void delete(int sid);

	public abstract void save(TeeFlowRunPrcs flowRunPrcs);

	public abstract void update(TeeFlowRunPrcs flowRunPrcs);

	public abstract TeeFlowRunPrcs findByComplex(int runId, int flowId,
			int prcsId, int flowPrcs, int userUuid);

	public abstract List<TeeFlowRunPrcs> findByComplex(int runId, int flowId,
			int prcsId, int flowPrcs);


	public abstract int update(String hql);

	public abstract int update(String hql, Object[] object);

	public abstract int updateCurrPrcsUserState2(String hql);

	public abstract int updateAllPrcsUserState4(String hql);

	public abstract int updatePrcsState(String hql, Object[] object);

	/**
	 * 获取流程监控数据
	 * flowTypeId 流程类型 sid
	 * runName 文号
	 * flagType 1为取主办人 2 为获取发起人
	 * pid 人员 
	 * deptids 人员所在部门的 sid字符串
	 * deptWhereList 全体部门权限条件 list
	 * @author zhp
	 * @createTime 2013-10-4
	 * @editTime 下午09:19:22
	 * @desc
	 */
	public abstract List<TeeFlowRunPrcs> JobMonitorAllTypeList(String runName,
			int runId, int flagType, String pid, List deptWhereList,
			int firstResult, int pageSize, TeeDataGridModel md);

	/**
	 * 获取流程监控数据 数量 全部 所有类型流程
	 * flowTypeId 流程类型 sid
	 * runName 文号
	 * flagType 1为取主办人 2 为获取发起人
	 * pid 人员 
	 * deptids 人员所在部门的 sid字符串
	 * deptWhereList 全体部门权限条件 list
	 * @author zhp
	 * @createTime 2013-10-4
	 * @editTime 下午09:19:22
	 * @desc
	 */
	public abstract Long JobMonitorAllTypeListCount(String runName, int runId,
			int flagType, String pid, List deptWhereList, TeeDataGridModel md);

	/**
	 * 获取流程监控数据
	 * flowTypeId 流程类型 sid
	 * runName 文号
	 * flagType 1为取主办人 2 为获取发起人
	 * pid 人员 
	 * deptids 人员所在部门的 sid字符串
	 * @author zhp
	 * @createTime 2013-10-4
	 * @editTime 下午09:19:22
	 * @desc
	 */
	public abstract List<TeeFlowRunPrcs> JobMonitorList(int flowTypeId,
			String runName, int runId, int flagType, String pid,
			String deptids, int firstResult, int pageSize, TeeDataGridModel md);

	/**
	 * 获取流程监控总数量 用于分页
	 * flowTypeId 流程类型 sid
	 * runName 文号
	 * flagType 1为取主办人 2 为获取发起人
	 * pid 人员 
	 * deptids 人员所在部门的 sid字符串
	 * @author zhp
	 * @createTime 2013-10-4
	 * @editTime 下午11:23:11
	 * @desc
	 */
	public abstract long JobMonitorListCount(int flowTypeId, String runName,
			int runId, int flagType, String pid, String deptids);

	/**
	 * @author syl
	 * 判断"先签收者为主办"是否已存在主办人会签
	 * @param runId
	 * @return
	 */
	public abstract boolean checkFlowRunPrcsIsExtisHand(int runId, int prcsId,
			int flowPrcsSid);

	/**
	 * 根据runId返回步骤实例模型集合
	 * @param runId
	 * @return
	 */
	public abstract List<TeeFlowRunPrcsModel> getFlowRunPrcsModelList(int runId);

	/**
	 * @author syl
	 * 判断“无主办会签”是否是最后一个人会签
	 * @param runId   流程实例Id
	 * @param runPrcsId 步骤实例Id
	 * @return
	 */
	public abstract boolean checkFlowRunPrcsIsLastHand(int runId, int prcsId,
			int flowPrcsSid);

	/**
	 * 获取流程实例最大步骤
	 * @author zhp
	 * @createTime 2013-11-2
	 * @editTime 上午10:23:56
	 * @desc
	 */
	public abstract int getMaxPrcsId(int runId);

	/**
	 * 获取自由流程 最大 prcsId
	 * @author zhp
	 * @createTime 2013-11-19
	 * @editTime 下午09:29:36
	 * @desc
	 */
	public abstract int geFreeFlowtMaxPrcsId(int runId);

	/**
	 * 获取自由流程预设步骤
	 * @author zhp
	 * @createTime 2013-11-5
	 * @editTime 上午09:52:10
	 * @desc
	 */
	public abstract List<TeeFlowRunPrcs> getFreeFlowPreFlowRunPrcs(
			int currentPrcsId, int runId);

	/**
	 * 获取下一步骤办理人 返回Map
	 * @author zhp
	 * @createTime 2013-11-5
	 * @editTime 下午01:50:43
	 * @desc
	 */
	public abstract Map getFreeFlowNextPrcs(List<TeeFlowRunPrcs> list,
			int nextPrcsId);

	/**
	 * 获取 除去下一步骤的 其他步骤的 预设步骤
	 * @author zhp
	 * @createTime 2013-11-5
	 * @editTime 下午02:36:26
	 * @desc
	 */
	public abstract List getFreeFlowOtherPrcs(List<TeeFlowRunPrcs> list,
			int currPrcsId, int maxPrc);

	/**
	 * 获取之前的步骤
	 * @author zhp
	 * @createTime 2013-11-7
	 * @editTime 上午03:31:05
	 * @desc
	 */
	public abstract List getFreeFlowOldPrcs(List<TeeFlowRunPrcs> list,
			int currPrcsId);

	/**
	 * 获取 指定 prcs
	 * @author zhp
	 * @createTime 2013-11-7
	 * @editTime 上午03:34:00
	 * @desc
	 */
	public abstract Map getFreeFlowOldPrcsByCPrcsId(List<TeeFlowRunPrcs> list,
			int currPrcsId);

	/**
	 * 转交结束流程，用于自由流程
	 * @param frpSid
	 */
	public abstract void turnEnd(int frpSid);

	/**
	 * 获取自由流程步骤中的字段控制列表
	 * @param runId
	 * @return
	 */
	public abstract List<String> getFreePrcsCtrlModelList(int runId);

	/**
	 * 获取当前未办理完成的工作流程节点
	 * @param runId
	 * @return
	 */
	public abstract List<Map> getUnhandledWorks(int runId, int userSid);

	/**
	 * 根据流程主键  获取当前流程  我已经办理的所有的步骤
	 * @param request
	 * @return
	 */
	public abstract TeeJson getMyHistoryStepByRunId(HttpServletRequest request);

	/**
	 * 根据主键获取flowRunPrcs详情
	 * @param request
	 * @return
	 */
	public abstract TeeJson getFlowRunPrcsBySid(HttpServletRequest request);

}