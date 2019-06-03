package com.tianee.oa.core.workflow.flowmanage.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowProcessModel;
import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeFlowProcessServiceInterface {

	public void save(TeeFlowProcess flowProcess);

	public TeeFlowProcess get(int sid);

	public TeeFlowProcess load(int sid);

	public void delete(TeeFlowProcess flowProcess);

	public void deleteNotReal(int sid);

	/**
	 * 真实删除
	 * @param sid
	 */
	public void deleteReal(int sid);

	public void update(TeeFlowProcess flowProcess);

	public void updateByMap(Map updateItem, int id);

	/**
	 * 通过用户权限获取可办理流程步骤
	 * @param person
	 * @return
	 */
	public List<TeeFlowProcess> getStartNodeListByUserPriv(TeePerson person);

	public List<TeeFlowProcess> findFlowProcessByFlowType(int flowTypeId);

	/**
	 * 更新步骤布局
	 * @param jsonModel
	 * @param flowId
	 */
	public void updateProcessLayout(String jsonModel, int flowId);

	/**
	 * 获取流程步骤中最大步骤号
	 * @param flowId
	 * @return
	 */
	public int getMaxPrcsId(int flowId);

	/**
	 * 获取步骤json信息
	 * @param prcsSeqId
	 * @return
	 */
	public TeeFlowProcessModel getFlowProcessInfo2Json(int prcsSeqId);

	/**
	 * 添加步骤上经办权限及经办规则
	 * @param request
	 * @return
	 */
	public void addPrcsPriv(int prcsId, int prcsUser[], int prcsDept[],
			int prcsRole[], String prcsUserSelectRule[],
			String prcsAutoSelectUser[], String prcsAutoSelectUser1);

	/**
	 * 获取步骤经办权限相关信息
	 * @param prcsId
	 * @return
	 */
	public TeeJson getPrcsPriv(int prcsId);

	/**
	 * 获取步骤列表
	 * @param flowId
	 * @return
	 */
	public List<TeeFlowProcessModel> getProcessList(int flowId);

	/**
	 * 更新转交条件
	 * @param prcsId
	 * @param conditionModel
	 */
	public void updateCondition(int prcsId, String conditionModel);

	/**
	 * 更新表单校验
	 * @param prcsId
	 * @param conditionModel
	 */
	public void updateFormValid(int prcsId, String formValidModel);

	public TeeFlowProcess getEndNode(int flowId);

	public TeeFlowProcess getStartNode(int flowId);

	/**
	 * 删除步骤节点之前  判断该步骤是否有TeeFlowRunPrcs关联
	 * @param sid
	 * @return
	 */
	public TeeJson hasRelatedFlowRunPrcs(int sid);

	/**
	 * 获取全局表单的所有的表单项目
	 * @param request
	 * @return
	 */
	public TeeJson getBasicFormItems(HttpServletRequest request);

	/**
	 * 获取独立表单内容
	 * @param request
	 * @return
	 */
	public TeeJson getFormShortById(HttpServletRequest request);

	/**
	 * 更新独立表单内容
	 * @param request
	 * @return
	 */
	public TeeJson updateFormShort(HttpServletRequest request);

}