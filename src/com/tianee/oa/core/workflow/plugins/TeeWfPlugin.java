package com.tianee.oa.core.workflow.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tianee.oa.core.workflow.proxy.TeeFlowRunProxy;
import com.tianee.oa.core.workflow.proxy.TeeJsonProxy;
import com.tianee.oa.subsys.bisengin.util.BisEngineUtil;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;

public abstract class TeeWfPlugin {
	protected Map<String,String> flowRunVars = null;
	protected Map<String,String> flowRunDatas = null;
	protected Map<String,String> requestParams = null;
	protected Map<String,String> formItemIdentities = null;
	protected TeeFlowRunProxy flowRunProxy = null;
	protected TeeSimpleDaoSupport simpleDaoSupport = null;
	protected BisEngineUtil bisEngineUtil = null;
	protected List<Map> smsDataList = new ArrayList();//短消息数据集合
	
	/**
	 * 转交前处理
	 * @return
	 */
	public abstract TeeJsonProxy beforeTurnnext();
	
	/**
	 * 转交后处理
	 */
	public abstract void afterTurnnext();
	
	/**
	 * 保存前处理
	 * @return
	 */
	public abstract TeeJsonProxy beforeSave();
	
	/**
	 * 保存后处理
	 */
	public abstract void afterSave();
	
	/**
	 * 回退处理
	 * @param prcsName TODO
	 * @param prcsId TODO
	 * @param content TODO
	 * @return
	 */
	public abstract void goBack(String prcsName, int prcsId, String content);
	
	/**
	 * 预转交拦截方法
	 * @param preTurnNextData 预转交数据模型
	 */
	public abstract void preTurnNextFilter(Map preTurnNextData);
	
	/**
	 * 渲染表单数据之后，返回String，可以再次渲染前台
	 * @return
	 */
	public abstract String afterRendered();

	public void setFlowRunVars(Map<String, String> flowRunVars) {
		this.flowRunVars = flowRunVars;
	}

	public void setFlowRunDatas(Map<String, String> flowRunDatas) {
		this.flowRunDatas = flowRunDatas;
	}

	public void setFlowRunProxy(TeeFlowRunProxy flowRunProxy) {
		this.flowRunProxy = flowRunProxy;
	}

	public Map<String, String> getFlowRunVars() {
		return flowRunVars;
	}

	public Map<String, String> getFlowRunDatas() {
		return flowRunDatas;
	}

	public TeeFlowRunProxy getFlowRunProxy() {
		return flowRunProxy;
	}

	public Map<String, String> getFormItemIdentities() {
		return formItemIdentities;
	}

	public void setFormItemIdentities(Map<String, String> formItemIdentities) {
		this.formItemIdentities = formItemIdentities;
	}

	public TeeSimpleDaoSupport getSimpleDaoSupport() {
		return simpleDaoSupport;
	}

	public void setSimpleDaoSupport(TeeSimpleDaoSupport simpleDaoSupport) {
		this.simpleDaoSupport = simpleDaoSupport;
	}

	public BisEngineUtil getBisEngineUtil() {
		return bisEngineUtil;
	}

	public void setBisEngineUtil(BisEngineUtil bisEngineUtil) {
		this.bisEngineUtil = bisEngineUtil;
	}

	public Map<String, String> getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(Map<String, String> requestParams) {
		this.requestParams = requestParams;
	}

	public List<Map> getSmsDataList() {
		return smsDataList;
	}

	public void setSmsDataList(List<Map> smsDataList) {
		this.smsDataList = smsDataList;
	}
	
}
