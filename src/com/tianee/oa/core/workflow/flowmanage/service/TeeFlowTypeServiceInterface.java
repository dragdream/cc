package com.tianee.oa.core.workflow.flowmanage.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jdom.JDOMException;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowSort;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeFlowTypeServiceInterface {

	public abstract void createFlowTypeService(TeeFlowType flowType);

	public abstract TeeFlowType get(int flowTypeId);

	public abstract TeeFlowType load(int flowTypeId);

	public abstract TeeEasyuiDataGridJson datagrid(TeeDataGridModel m,
			Integer sortId, TeePerson loginUser);

	/**
	 * 保存流程经办权限（自由流程）
	 * @param flowId
	 * @param prcsUser
	 * @param prcsDept
	 * @param prcsRole
	 */
	public abstract void savePrcsPriv(int flowId, int prcsUser[],
			int prcsDept[], int prcsRole[]);

	/**
	 * 获取经办权限集合模型
	 * @param flowId
	 * @return
	 */
	public abstract Map getPrcsPrivModel(int flowId);

	public abstract int getDealCount(int userId, int flowId,
			StringBuffer managerHql);

	public abstract int getOverCount(int userId, int flowId,
			StringBuffer managerHql);

	/**
	 * 导出XML数据
	 * @param output
	 * @param flowId
	 */
	public abstract String exportXml(int flowId);

	/**
	 * 删除流程下的所有步骤
	 * @param flowId
	 */
	public abstract void deleteAllProcess(int flowId);

	public abstract void flowCopy(int flowId) throws JDOMException;

	/**
	 * 导入XML数据
	 * @param output
	 * @param flowId
	 * @throws JDOMException 
	 */
	public abstract void importXml(InputStream in, int flowId, int importOrg)
			throws JDOMException;

	/**
	 * 更新流程变量模型
	 * @param model
	 * @param flowTypeId
	 */
	public abstract void updateFlowRunVarsModel(String model, int flowTypeId);

	/**
	 * 获取已发起指定流程的工作数
	 * @param flowId
	 * @return
	 */
	public abstract int getTheTotleOfFlowRunByFlowId(int flowId);

	/**
	 * 获取流程变量模型
	 * @param flowId
	 * @return
	 */
	public abstract String getFlowRunVarsModel(int flowId);

	public abstract void updateFlowTypeService(TeeFlowType flowType);

	public abstract TeeFlowType deleteService(int id);

	public abstract List<TeeFlowType> findByFlowSort(TeeFlowSort flowSort);

	public abstract List<TeeFlowType> findByFlowSort(int id);



	/**
	 * 获取当前前用户可以编辑的流程列表
	 * @param id
	 * @param loginUser
	 * @return
	 */
	public abstract List<TeeFlowType> findByFlowSort1(int id,
			TeePerson loginUser);

	/**
	 * 判断该flowType下是否有关联的flowRun
	 * @param sid
	 * @return
	 */
	public abstract TeeJson hasRelatedFlowRun(int sid);

	/**
	 * 归档映射
	 * @param request
	 * @return
	 */
	public abstract TeeJson updateArchiveMapping(HttpServletRequest request);

	/**
	 * 根据主键  获取归档映射
	 * @param request
	 * @return
	 */
	public abstract TeeJson getArchiveMappingById(HttpServletRequest request);

	/**
	 * 获取所有的流程类型和流程分类
	 * @param request
	 * @return
	 */
	public abstract TeeJson getAllFlowTypesAndFlowSorts(
			HttpServletRequest request);

}