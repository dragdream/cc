package com.tianee.oa.core.workflow.flowrun.service;

import java.util.Map;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface;
import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeRepeatToPubServiceInterface {

	public abstract TeeWorkFlowServiceContextInterface getFlowServiceContext();

	public abstract void setFlowServiceContext(
			TeeWorkFlowServiceContextInterface flowServiceContext);

	/**
	 * @function: 获取转存公共网盘目录树
	 * @author: wyw
	 * @data: 2014年9月14日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 */
	public abstract TeeJson getPublicFolderTree(Map requestMap,
			TeePerson loginPerson);

	/**
	 * @function: 转存至公共网盘
	 * @author: wyw
	 * @data: 2014年9月14日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 */
	public abstract TeeJson saveToPersonFolder(Map requestMap,
			TeePerson loginPerson);

}