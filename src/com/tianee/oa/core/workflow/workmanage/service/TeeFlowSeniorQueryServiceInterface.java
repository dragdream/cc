package com.tianee.oa.core.workflow.workmanage.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowQueryTpl;
import com.tianee.oa.core.workflow.workmanage.model.TeeSeniorQueryModel;

public interface TeeFlowSeniorQueryServiceInterface {

	public abstract List<TeeSeniorQueryModel> getQueryTplByFlowId(
			TeePerson person, int flowId);

	public abstract void saveTpl(TeeFlowQueryTpl queryTpl);

	public abstract void updateTpl(TeeFlowQueryTpl queryTpl);

	public abstract void deleteTpl(TeeFlowQueryTpl queryTpl);

	public abstract TeeForm getFormByFlowId(int flowId);

	public abstract TeeFlowQueryTpl getById(int tplId);

}