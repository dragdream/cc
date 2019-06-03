package com.tianee.oa.core.workflow.flowrun.service;

import java.util.List;
import java.util.Map;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.bean.TeeDocNum;
import com.tianee.oa.core.workflow.flowrun.bean.TeeDocNumRunner;
import com.tianee.oa.core.workflow.flowrun.model.TeeDocNumModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeDocNumServiceInterface {

	public abstract void addDocNumModel(TeeDocNumModel docNumModel);

	public abstract void deleteDocNumById(int sid);

	public abstract TeeDocNumModel getById(int sid);

	public abstract void updateDocNumModel(TeeDocNumModel docNumModel);

	/**
	 * 创建文号执行实例
	 * @return
	 */
	public abstract TeeDocNumRunner generateDocNumRunner(TeePerson loginUser,
			int sid, int runId, int flowId);

	/**
	 * 生成文号字样
	 * @param loginUser
	 * @param sid
	 * @param runId
	 * @param flowId
	 * @return
	 */
	public abstract String generateDocNum(TeePerson loginUser, int sid,
			int runId, int flowId);

	/**
	 * 动态修改文号
	 * @param loginUser
	 * @param sid
	 * @param docNumber
	 * @return
	 */
	public abstract String diynamicEditDocNum(TeePerson loginUser, int runId,
			int flowId, int editNum);

	/**
	 * 检查是否存在文号
	 * @param loginUser
	 * @param sid
	 * @param docNumber
	 * @return
	 */
	public abstract boolean checkExistsDocNum(int runId, int flowId);

	/**
	 * 判断某个历史文号是否已经清零过
	 * @return
	 */
	public abstract boolean checkIsReset(TeeDocNum docNum,
			TeeDocNumRunner docNumRunner);

	/**
	 * 获取当前文号和最大文号值
	 * @param loginUser
	 * @param sid
	 * @param docNumber
	 * @return
	 */
	public abstract Map getCurDocAndMaxDoc(int runId, int flowId);

	public abstract void modelToEntity(TeeDocNumModel docNumModel,
			TeeDocNum docNum);

	public abstract void entityToModel(TeeDocNum docNum,
			TeeDocNumModel docNumModel);

	public abstract TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,
			Map requestDatas);

	public abstract TeeEasyuiDataGridJson listHistory(TeeDataGridModel dm,
			Map requestDatas);

	public abstract List<TeeDocNumModel> getDocNumListByPriv(
			TeeDataGridModel dm, TeePerson loginPerson);

	/**
	 * 清除文号历史记录并重置
	 * @param sid
	 */
	public abstract void clear(int sid);

	/**
	 * 根据主键删除文号日志
	 * @param sid
	 * @return
	 */
	public abstract TeeJson delHistoryBySid(int sid);

}