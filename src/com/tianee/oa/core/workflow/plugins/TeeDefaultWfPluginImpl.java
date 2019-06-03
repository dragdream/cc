package com.tianee.oa.core.workflow.plugins;

import java.util.Map;

import com.tianee.oa.core.workflow.proxy.TeeJsonProxy;

public class TeeDefaultWfPluginImpl extends TeeWfPlugin{


	@Override
	public TeeJsonProxy beforeTurnnext() {
		// TODO Auto-generated method stub
		TeeJsonProxy jsonProxy = new TeeJsonProxy();
		jsonProxy.setRtState(true);
		return jsonProxy;
	}

	@Override
	public void afterTurnnext() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TeeJsonProxy beforeSave() {
		// TODO Auto-generated method stub
		TeeJsonProxy jsonProxy = new TeeJsonProxy();
		jsonProxy.setRtState(true);
		return jsonProxy;
	}

	@Override
	public void afterSave() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preTurnNextFilter(Map preTurnNextData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String afterRendered() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void goBack(String prcsName, int prcsId, String content) {
		// TODO Auto-generated method stub
		
	}
	
}
