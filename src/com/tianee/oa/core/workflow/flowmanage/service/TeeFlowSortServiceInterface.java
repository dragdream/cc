package com.tianee.oa.core.workflow.flowmanage.service;

import java.util.List;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowSort;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowSortModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

public interface TeeFlowSortServiceInterface {

	/**
	 * 保存实体
	 * @param flowSort
	 */
	public abstract void save(TeeFlowSort flowSort);

	/**
	 * 获取FlowSort实体
	 * @param flowSortId
	 * @return
	 */
	public abstract TeeFlowSort get(int flowSortId);

	/**
	 * 获取FlowSortModel模型
	 * @param flowSortId
	 * @return
	 */
	public abstract TeeFlowSortModel getModel(int flowSortId);

	/**
	 * 获取FlowSortModel模型集合
	 * @param flowSortId
	 * @return
	 */
	public abstract List<TeeFlowSortModel> getModelList();

	/**
	 * 更新实体
	 * @param flowSort
	 */
	public abstract void update(TeeFlowSort flowSort);

	/**
	 * 删除实体，并不会级联删除该分类下的流程
	 * @param flowSort
	 */
	public abstract void delete(int sortId);

	/**
	 * 进行排序
	 * @param type  1-上移  2-下移
	 */
	public abstract void doSortOrder(int type, int flowSortId);

	/**
	 * 获取最大分类排序号
	 * @return
	 */
	public abstract int getMaxOrderNo();

	/**
	 * 获取流程分类集合
	 * @return
	 */
	public abstract List<TeeFlowSort> list();

	/**
	 * 获取流程分类集合，easyui的请求返回格式
	 * @param dm
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm);


}