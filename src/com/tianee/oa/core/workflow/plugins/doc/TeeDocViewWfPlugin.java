package com.tianee.oa.core.workflow.plugins.doc;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.dbutils.DbUtils;

import com.tianee.oa.core.workflow.plugins.TeeWfPlugin;
import com.tianee.oa.core.workflow.proxy.TeeJsonProxy;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.util.db.TeeDbUtility;

public class TeeDocViewWfPlugin extends TeeWfPlugin{

	@Override
	public TeeJsonProxy beforeTurnnext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void afterTurnnext() {
		// TODO Auto-generated method stub
	}

	@Override
	public TeeJsonProxy beforeSave() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void afterSave() {
		//设置前台传递过来的数据
	}

	@Override
	public void preTurnNextFilter(Map preTurnNextData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String afterRendered() {
		// TODO Auto-generated method stub
		return "parent.$('#docViewBtn').show();";
	}

	@Override
	public void goBack(String prcsName, int prcsId, String content) {
		// TODO Auto-generated method stub
		
	}

}
