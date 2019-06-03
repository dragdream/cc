package com.tianee.oa.mobile.workflow.service;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

public interface TeeMobileWorkflowServiceInterface {

	/**
	 * 获取我的工作代办
	 * @param loginUser
	 * @param dm
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson getMyWorkList(TeePerson loginUser,
			TeeDataGridModel dm, Map<String, String> requestData);

	/**
	 * 获取我的工作已办结
	 * @param loginUser
	 * @param dm
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson getMyWorkHandledList(
			TeePerson loginUser, TeeDataGridModel dm,
			Map<String, String> requestData);

	/**
	 * 获取表单信息
	 * @return
	 */
	public abstract Map getFormHanderData(Map requestData);

	/**
	 * 获取表单信息（预览）
	 * @return
	 */
	public abstract Map getFormHanderData4Print(Map requestData);

	public abstract void addFeedBack(Map requestData, TeePerson loginUser);

	public abstract String formListView(Map requestData);

	/**
	 * 根据发起权限获取流程分类
	 * @param loginUser
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson getFlowSortByPriv(TeePerson loginUser);

	/**
	 * 根据发起权限获取流程分类
	 * @param loginUser
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson getFlowTypeBySortAndPriv(
			TeePerson loginUser, int sortId);

}