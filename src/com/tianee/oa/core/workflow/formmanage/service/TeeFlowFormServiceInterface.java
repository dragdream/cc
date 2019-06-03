package com.tianee.oa.core.workflow.formmanage.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeListCtrlExtend;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowFormModel;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeFlowFormServiceInterface {

	/**
	 * 返回datagird形式的数据
	 * @param m
	 * @return
	 */
	public List<TeeFlowFormModel> datagrid(TeeDataGridModel m, int sortId,
			TeePerson loginUser);

	/**
	 * 删除表单
	 * @param formId
	 */
	public TeeForm deleteFormService(int formId);

	/**
	 * 获取某分类下的表单数量
	 * @param sortId
	 * @return
	 */
	public long getFlowFormCountBySort(int sortId, TeePerson loginUser);

	/**
	 * 获取该表单的历史版本
	 * @param form
	 * @return
	 */
	public List<TeeForm> listHistoryVersion(int formId);

	/**
	 * 保存表单，第一版本
	 * @param form
	 */
	public void saveService(TeeForm form);

	/**
	 * 更新实体
	 * @param form
	 */
	public void updateService(TeeForm form);

	/**
	 * 获取表单
	 * @param formId
	 * @return
	 */
	public TeeForm getForm(int formId);

	/**
	 * 获取表单模型
	 * @param formId
	 * @return
	 */
	public TeeFlowFormModel getFormModel(int formId);

	/**
	 * 按分类获取表单模型集合
	 * @param formId
	 * @return
	 */
	public List<TeeFlowFormModel> getFormModelList(int sortId);

	/**
	 * 保存表单字段实体
	 * @param formItem
	 */
	public void saveFormItem(TeeFormItem formItem);

	/**
	 * 根据流程类型获取最新版本表单数据项
	 * @param flowId
	 * @return
	 */
	public List<TeeFormItem> getLatestFormItemsByOriginForm(TeeForm form);

	/**
	 * 更新项排序号
	 * @param fieldMap
	 */
	public void updateItemsSortNo(List<Map<String, String>> fieldMapList);

	/**
	 * 获取最新表单版本
	 * @param form
	 * @return
	 */
	public TeeForm getLatestVersion(TeeForm form);

	public TeeForm getLatestVersion(int formId);

	/**
	 * 获取指定表单中的表单字段集合
	 * @param form
	 * @return
	 */
	public List<TeeFormItem> listFormItems(TeeForm form);

	/**
	 * 获取指定表单中的表单字段集合
	 * @param form
	 * @return
	 */
	public List<TeeFormItem> listFormItems(int formId);

	/**
	 * 打印预览模板
	 * @param formId
	 * @return
	 */
	public String printExplore(int formId, TeePerson loginUser);

	/**
	 * 更新并保存表单元数据
	 * @param html
	 * @param formId
	 */
	public TeeForm saveAndUpdateFormService(String content, int formId);

	/**
	 * 通过表单分类获取表单实体集合
	 * @param sortId
	 * @return
	 */
	public List<TeeForm> getFlowFormBySort1(int sortId, TeePerson loginUser);

	/**
	 * 通过表单分类获取表单实体集合
	 * @param sortId
	 * @return
	 */
	public List<TeeForm> getFlowFormBySort(int sortId);

	public List<TeeForm> getFlowFormBySort(TeePerson loginUser, int sortId,
			int firstResult, int pageSize);

	public TeeForm getVersionForm(int formGroup, int versionNo);

	public void saveOrUpdateListCtrlExtend(TeeListCtrlExtend lce);

	public void setSimpleDaoSupport(TeeSimpleDaoSupport simpleDaoSupport);

	public TeeListCtrlExtend getListCtrlExtend(int flowPrcsId, int formItemId);

	/**
	 * 生成版本
	 * @param formId
	 * @return
	 */
	public TeeForm createVersionFormService(int formId);

	/**
	 * 获取版本列表json模型
	 * @param formId
	 * @return
	 */
	public TeeJson listVersions2Json(int formId);

	/**
	 * 导入表单
	 * @param formId
	 * @param file
	 * @throws IOException 
	 */
	public void importForm(int formId, File file) throws IOException;

	/**
	 * 判断是否存在绑定表单的流程
	 * @param formId
	 * @return
	 */
	public boolean checkExistFlow(int formId);

	public TeeFormItem getFormItemById(int sid);

	public List<TeeForm> list();

	/**
	 * 根据表单获取所绑定流程的数量
	 * @param formId
	 * @return
	 */
	public long getCountOfBundledFlowType(int formId);

	/**
	 * 根据表单获取所绑定流程集合
	 * @param formId
	 * @return
	 */
	public List<TeeFlowType> getBundledFlowTypes(int formId);

	/**
	 * 获取指定表单中所有控件
	 * 
	 * */
	public TeeJson getFormItemsByFormGroup(HttpServletRequest request);

	/**
	 * 给控件分类
	 * */
	public TeeJson updateFormItemsByFormGroup(HttpServletRequest request);

}