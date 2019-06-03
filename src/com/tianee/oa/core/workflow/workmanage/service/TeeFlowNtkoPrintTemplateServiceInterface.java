package com.tianee.oa.core.workflow.workmanage.service;

import java.util.List;
import java.util.Map;

public interface TeeFlowNtkoPrintTemplateServiceInterface {

	public abstract void addOrUpdate(Map requestData);

	public abstract void delete(int sid);

	public abstract List<Map> list(int flowId, int runId);

}