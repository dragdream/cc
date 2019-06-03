package com.tianee.oa.core.workFlowFrame.validator;

import java.util.List;
import java.util.Map;

import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;

public interface TeeWorkFlowFormValidatorServiceInterface {

	/**
	 * 获取流程步骤字段控制 -- 固定流程 
	 * @param form
	 * @return
	 */
	public Map getFixedFormItemControlInfo(TeeForm form,
			TeeFlowRunPrcs flowRunPrcs);

	/**
	 * 获取表单项控制信息
	 * @param item
	 * @param fieldCtrlModelList
	 * @return
	 */
	public Map getFormItemControlInfo(TeeFormItem item,
			List<Map<String, String>> fieldCtrlModelList);

	/**
	 * 获取 流程步骤字段控制 --- 自由流程
	 * @param form
	 * @return
	 */
	public Map getFreeFormItemControlInfo(TeeForm form);

}