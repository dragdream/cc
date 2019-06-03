package com.tianee.oa.core.workflow.flowrun.service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunVars;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.core.workflow.plugins.TeeDefaultWfPluginImpl;
import com.tianee.oa.core.workflow.plugins.TeeWfPlugin;
import com.tianee.oa.core.workflow.proxy.TeeFlowRunProxy;
import com.tianee.oa.subsys.bisengin.util.BisEngineUtil;
import com.tianee.webframe.util.global.TeeBeanFactory;

public class TeeWfPluginFactory {
	TeeFlowRunPrcs flowRunPrcs = null;
	Map flowRunDatas = null;
	Map requestParams = null;
	
	
	public TeeWfPluginFactory(TeeFlowRunPrcs flowRunPrcs,Map flowRunDatas){
		this.flowRunPrcs = flowRunPrcs;
		this.flowRunDatas = flowRunDatas;
	}
	
	public TeeWfPluginFactory(TeeFlowRunPrcs flowRunPrcs,Map flowRunDatas,Map requestParams){
		this.flowRunPrcs = flowRunPrcs;
		this.flowRunDatas = flowRunDatas;
		this.requestParams = requestParams;
	}
	
	public TeeWfPlugin newInstance() throws Exception{
		if(flowRunPrcs==null){
			return new TeeDefaultWfPluginImpl();
		}
		TeeFlowProcess fp = flowRunPrcs.getFlowPrcs();
		String pluginClass = fp==null?null:fp.getPluginClass();
		if(pluginClass==null || "".equals(pluginClass)){
			return new TeeDefaultWfPluginImpl();
		}
		
		Object pluginObj = null;
		Class clazz = Thread.currentThread().getContextClassLoader().loadClass(pluginClass);
		pluginObj = clazz.newInstance();
		
		//设置flowRunVars
		Method setFlowRunVarsMethod = clazz.getMethod("setFlowRunVars", Map.class);
		Map flowRunVars = new HashMap();
		List<TeeFlowRunVars> varsList = flowRunPrcs.getFlowRun().getFlowRunVars();
		for(TeeFlowRunVars runVars:varsList){
			flowRunVars.put(runVars.getVarKey(), runVars.getVarValue());
		}
		setFlowRunVarsMethod.invoke(pluginObj, flowRunVars);
		
		//设置flowRunDatas
		Method setFlowRunDatasMethod = clazz.getMethod("setFlowRunDatas", Map.class);
		setFlowRunDatasMethod.invoke(pluginObj, flowRunDatas);
		
		//设置flowRun代理
		TeeFlowRunProxy flowRunProxy = initFlowRunProxy();
		Method setFlowRunProxyMethod = clazz.getMethod("setFlowRunProxy", TeeFlowRunProxy.class);
		setFlowRunProxyMethod.invoke(pluginObj, flowRunProxy);
		
		//设置来自外部的请求
		Method setRequestParamsMethod = clazz.getMethod("setRequestParams", Map.class);
		setRequestParamsMethod.invoke(pluginObj, requestParams);
		
		//设置表单项对应关系
		TeeFlowRun fr = flowRunPrcs.getFlowRun();
		List<TeeFormItem> items = fr.getForm().getFormItems();
		Map formItemIdentities = new HashMap();
		for(TeeFormItem item:items){
			formItemIdentities.put(item.getTitle(), item.getName());
		}
		Method setFormItemIdentitiesMethod = clazz.getMethod("setFormItemIdentities", Map.class);
		setFormItemIdentitiesMethod.invoke(pluginObj, formItemIdentities);
		
		//设置业务引擎工具类
		BisEngineUtil bisEngineUtil = (BisEngineUtil) TeeBeanFactory.getBean("bisEngineUtil");
		Method setBisEngineUtilMethod = clazz.getMethod("setBisEngineUtil", BisEngineUtil.class);
		setBisEngineUtilMethod.invoke(pluginObj, bisEngineUtil);
		
		return (TeeWfPlugin) pluginObj;
	}
	
	private TeeFlowRunProxy initFlowRunProxy(){
		TeeFlowRunProxy flowRunProxy = new TeeFlowRunProxy();
		TeeFlowRun fr = flowRunPrcs.getFlowRun();
		BeanUtils.copyProperties(fr, flowRunProxy);
		TeePerson person = fr.getBeginPerson();
		if(person!=null){
			flowRunProxy.setBeginUserId(person.getUserId());
			flowRunProxy.setBeginUserName(person.getUserName());
			flowRunProxy.setBeginUserUuid(person.getUuid());
		}
		
		flowRunProxy.setFlowName(fr.getFlowType().getFlowName());
		flowRunProxy.setFlowPrcs(flowRunPrcs.getFlowPrcs().getSid());
		flowRunProxy.setPrcsId(flowRunPrcs.getPrcsId());
		flowRunProxy.frpSid = flowRunPrcs.getSid();
		
		person = flowRunPrcs.getPrcsUser();
		
		flowRunProxy.setPrcsBeginTime(flowRunPrcs.getBeginTime());
		flowRunProxy.setPrcsEndTime(flowRunPrcs.getEndTime());
		flowRunProxy.setPrcsName(flowRunPrcs.getFlowPrcs().getPrcsName());
		flowRunProxy.setPrcsUserId(person.getUserId());
		flowRunProxy.setPrcsUserName(person.getUserName());
		flowRunProxy.setPrcsUserUuId(person.getUuid());
		
		return flowRunProxy;
	}
	
}
