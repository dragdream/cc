package com.tianee.oa.core.workFlowFrame.runner;

import java.util.Map;

import com.tianee.oa.core.org.bean.TeePerson;

public interface TeeWorkFlowRunnerFactoryInterface {

	public Map preTurnNext(Map requestData, TeePerson loginPerson);

	public Map turnNext(Map requestData, TeePerson loginPerson);

	/**
	 * 添加经办人员
	 * @param requestData
	 * @param loginPerson
	 * @return
	 */
	public Map addPrcsUser(Map requestData, TeePerson loginPerson);

	/**
	 * 回退上一步
	 * @param requestData
	 * @param loginPerson
	 * @return
	 */
	public Map backTo(Map requestData, TeePerson loginPerson);

	/**
	 * 回退之前步骤
	 * @param requestData
	 * @param loginPerson
	 * @return
	 */
	public Map backToOther(Map requestData, TeePerson loginPerson);

	/**
	 * 回退到指定的步骤
	 * @param requestData
	 * @param loginPerson
	 * @return
	 */
	public Map backToFixed(Map requestData, TeePerson loginPerson);

}