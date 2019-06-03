package com.tianee.oa.core.workFlowFrame.dataloader;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.org.bean.TeePerson;

public interface TeeSimpleDataLoaderInterface {

	/**
	 * 获取表单处理数据
	 */
	public Map getHandlerData(Map requestData, TeePerson loginPerson);

	/**
	 * 获取表单打印数据
	 * @param requestData
	 * @param loginPerson
	 * @return
	 */
	public Map getFormPrintData(Map requestData, TeePerson loginPerson);

	/**
	 * 获取表单打印数据字符串流
	 * @param requestData
	 * @param loginPerson
	 * @return
	 */
	public String getFormPrintDataStream(Map requestData, TeePerson loginPerson);

	/**
	 * 获取当前步骤经办人
	 * @param frpSid
	 * @param loginPerson
	 * @return
	 */
	public List<Map> getCurPrcsUsers(int frpSid, TeePerson loginPerson);

	public Map getTurnHandlerData(Map requestData, TeePerson loginPerson);

	public List<Map> getPrePrcsList(int frpSid, TeePerson loginPerson);

	public List<TeeAttachmentModel> getFlowRunAttaches4Archives(int runId);

	public List<Map> getPreReachablePrcsList(int frpSid, TeePerson loginPerson);

}