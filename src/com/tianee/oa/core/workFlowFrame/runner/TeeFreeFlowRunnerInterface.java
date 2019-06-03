package com.tianee.oa.core.workFlowFrame.runner;

import javax.servlet.http.HttpServletRequest;

import com.tianee.oa.core.org.bean.TeePerson;

public interface TeeFreeFlowRunnerInterface {
	public void turnNext(HttpServletRequest request, TeePerson loginPerson);
}
