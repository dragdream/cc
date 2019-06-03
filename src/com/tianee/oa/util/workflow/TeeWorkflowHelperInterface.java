package com.tianee.oa.util.workflow;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

public interface TeeWorkflowHelperInterface {

	/**
	 * 变更表单字段类型
	 * @param removedField
	 * @param extraField
	 * @param alteredField
	 * @param tableName
	 */
	public void updateColumns(List<TeeFormItem> items, String tableName);

	/**
	 * 判断是否存在流程数据表tee_f_r_d_[flowId]_[flowVersion]_[formId]_[formVersion]
	 * @param flowId
	 * @param formId
	 * @return
	 */
	public boolean hasExistFormDataTable(String tableName);

	/**
	 * 获取表的元数据信息key(DATA_1)=>value(int)
	 * @param flowId
	 * @param formId
	 * @return
	 */
	public Map<String, Integer> getTableMetaData(String tableName);

	/**
	 * 按流水号与流程类型获取工作流表单数据
	 * @param runId
	 * @param flowId
	 * @return
	 */
	public Map getFlowRunData(int runId, int flowId);

	/**
	 * 按流水号与流程类型获取工作流表单数据
	 * @param runId
	 * @return
	 */
	public Map getFlowRunData(int runId);

	/**
	 * 删除流程数据表
	 * @param flowId
	 */
	public void deleteFlowRunDataTable(int flowId);

	/**
	 * 创建流程数据的表单
	 * @param ft
	 * @return
	 */
	public void createFlowRunDataTable(TeeFlowType ft);

	/**
	 * 初始化流程数据库表
	 */
	public boolean initFlowRunDataFormTable(TeeFlowType ft);

	/**
	 * 初始化流程数据
	 * @param runId
	 * @param flowId
	 */
	public void initFlowRunData(int runId, int flowId);

	public SessionFactory getSessionFactory();

	public void setSessionFactory(SessionFactory sessionFactory);

	/**
	 * 保存或更新流程数据
	 * @param flowId
	 * @param runId
	 * @param datas
	 * @throws SQLException 
	 */
	public void saveOrUpdateFlowRunData(int runId, int flowId, int frpSid,
			Map<String, String> datas, Map<String, String> requestParam);

	/**
	 * 获取http请求中的表单流程数据
	 * @param request
	 * @return
	 */
	public Map getHttpRequestFlowRunDataMaps(HttpServletRequest request);

	/**
	 * 基础宏标记过滤器，适用于普通系统级宏标记
	 * @param pattern
	 * @return
	 */
	public String basicMacroFiltering(String pattern);

	/**
	 * 流程宏标记过滤器，专门用于流程标记过滤的
	 * @param pattern
	 * @return
	 */
	public String flowMacroFiltering(String pattern, TeeFlowRun flowRun,
			TeePerson loginUser);

	/**
	 * 字段映射解释专用！
	 * @param mappingMap 映射规则
	 * @param dataMap 数据map
	 * @param keyItems 键
	 * @param valueItems 值
	 * @return
	 */
	public Map getFieldMappingTranslate(Map mappingMap,
			Map<String, String> dataMap, List<TeeFormItem> keyItems,
			List<TeeFormItem> valueItems);

	/**
	 * 获取历史流程数据，用于双击表单控件后快速回填
	 * @param itemId
	 * @param frpSid
	 * @return
	 */
	public TeeEasyuiDataGridJson getHistoryFlowRunDatas(int itemId, int frpSid,
			TeePerson loginPerson, TeeDataGridModel dm);

	/**
	 * 条件查询
	 * @param datas
	 * @param model
	 * @return
	 */
	public boolean conditionResult(Map<String, String> datas, String model);


	/**
	 * 父流程的会签控件映射到子流程
	 * @param fieldMapping
	 * @param runId
	 * @param parentItemList
	 * @param childItemList
	 * @return
	 */
	public void getFeedBackFieldMappingTranslate(
			Map<String, String> fieldMapping, int runId,
			List<TeeFormItem> keyItems, List<TeeFormItem> valueItems,
			TeeFlowRun childFlowRun, TeeFlowRunPrcs childFlowRunPrcs);

	/**
	 * 反向映射会签控件  子流程---父流程  
	 * @param fieldReverseMapping
	 * @param parentItemList
	 * @param childItemList
	 */
	public void getFeedBackFieldReverseMappingTranslate(
			Map<String, String> fieldReverseMapping,
			List<TeeFormItem> valueItems, List<TeeFormItem> keyItems,
			int childRunId, int pRunId, int pFlowPrcsId);

	/**
	 * 获取流程表单数据  以formItem的title为键值
	 * @param request
	 * @return
	 */
	public Object getFlowRunDatasOnTitle(int runId);

	
	/**
	 * 父流程的附件上传控件映射到子流程
	 * @param fieldMapping
	 * @param runId
	 * @param parentItemList
	 * @param childItemList
	 * @param childFlowRun
	 * @param childFlowRunPrcs
	 */
	public void getAttachUploadCtrlFieldMappingTranslate(
			Map<String, String> fieldMapping, int runId,
			List<TeeFormItem> parentItemList, List<TeeFormItem> childItemList,
			TeeFlowRun childFlowRun, TeeFlowRunPrcs childFlowRunPrcs);

	
	/**
	 * 子流程的附件上传控件映射到父流程
	 * @param fieldReverseMapping
	 * @param parentItemList
	 * @param childItemList
	 * @param runId
	 * @param pRunId
	 * @param pFlowPrcsId
	 */
	public void getAttachUploadCtrlFieldReverseMappingTranslate(
			Map<String, String> fieldReverseMapping,
			List<TeeFormItem> parentItemList, List<TeeFormItem> childItemList,
			int runId, int pRunId, int pFlowPrcsId);

	
	/**
	 * 父流程到子流程  图片上传控件的映射
	 * @param fieldMapping
	 * @param runId
	 * @param parentItemList
	 * @param childItemList
	 * @param childFlowRun
	 * @param childFlowRunPrcs
	 */
	public void getImgCtrlFieldMappingTranslate(
			Map<String, String> fieldMapping, int runId,
			List<TeeFormItem> parentItemList, List<TeeFormItem> childItemList,
			TeeFlowRun childFlowRun, TeeFlowRunPrcs childFlowRunPrcs,Map<String,String> parentData);

	
	/**
	 * 子流程的图片上传控件映射到父流程
	 * @param fieldReverseMapping
	 * @param parentItemList
	 * @param childItemList
	 * @param runId
	 * @param pRunId
	 * @param pFlowPrcsId
	 * @param datas
	 */
	public void getImgUploadCtrlFieldReverseMappingTranslate(
			Map<String, String> fieldReverseMapping,
			List<TeeFormItem> parentItemList, List<TeeFormItem> childItemList,
			int runId, int pRunId, int pFlowPrcsId, Map datas);

}