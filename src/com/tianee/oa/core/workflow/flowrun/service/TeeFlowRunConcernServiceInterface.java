package com.tianee.oa.core.workflow.flowrun.service;

import java.util.List;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunConcern;

public interface TeeFlowRunConcernServiceInterface {

	/**
	 * 流程关注
	 * @param flowRun
	 * @param person
	 */
	public abstract void concern(int runId, int personId);

	/**
	 * 根据流程获取所关注的人
	 * @param runId
	 * @return
	 */
	public abstract List<TeePerson> getConcernUsersByRunId(int runId);

	/**
	 * 取消关注
	 * @param runId
	 * @param personUuid
	 */
	public abstract TeeFlowRunConcern cancelConcern(int runId, int personUuid);


	public abstract void setPersonDao(TeePersonDao personDao);


}