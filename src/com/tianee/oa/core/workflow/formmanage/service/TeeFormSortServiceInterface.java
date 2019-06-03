package com.tianee.oa.core.workflow.formmanage.service;

import java.util.List;

import com.tianee.oa.core.workflow.formmanage.bean.TeeFormSort;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFormSortModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

public interface TeeFormSortServiceInterface {

	/**
	 * 保存实体
	 * @param flowSort
	 */
	public abstract void save(TeeFormSort formSort);

	/**
	 * 获取FlowSort实体
	 * @param formSortId
	 * @return
	 */
	public abstract TeeFormSort get(int formSortId);

	/**
	 * 获取FormwSortModel模型
	 * @param formSortId
	 * @return
	 */
	public abstract TeeFormSortModel getModel(int formSortId);

	/**
	 * 获取FormSortModel模型集合
	 * @param formSortId
	 * @return
	 */
	public abstract List<TeeFormSortModel> getModelList();

	/**
	 * 更新实体
	 * @param flowSort
	 */
	public abstract void update(TeeFormSort formSort);

	/**
	 * 删除实体，并不会级联删除该分类下的流程
	 * @param flowSort
	 */
	public abstract void delete(TeeFormSort formSort);

	public abstract void delete(int formSordId);

	/**
	 * 进行排序
	 * @param type  1-上移  2-下移
	 */
	public abstract void doSortOrder(int type, int formSortId);

	/**
	 * 获取最大分类排序号
	 * @return
	 */
	public abstract int getMaxOrderNo();

	/**
	 * 获取流程分类集合
	 * @return
	 */
	public abstract List<TeeFormSort> list();

	/**
	 * 获取流程分类集合，easyui的请求返回格式
	 * @param dm
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm);


}