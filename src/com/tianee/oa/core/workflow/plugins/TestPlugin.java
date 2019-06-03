package com.tianee.oa.core.workflow.plugins;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.tianee.oa.core.workflow.proxy.TeeJsonProxy;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.util.db.TeeDbUtility;

public class TestPlugin extends TeeWfPlugin{

	@Override
	public TeeJsonProxy beforeTurnnext() {
		// TODO Auto-generated method stub
		TeeJsonProxy jsonProxy = new TeeJsonProxy();
		Connection conn = this.bisEngineUtil.getConnection("bis_car_info");
		try{
			System.out.println(conn);
			//创建业务表关联键
			Map insertItem = new HashMap();
			insertItem.put("CAR_NAME", "车辆测试");
			insertItem.put("DRIVER", "李师傅");
			insertItem.put("CAR_NO", "京PB1T95");
			Object bisId = bisEngineUtil.executeSave(conn, insertItem, "bis_car_info");
			bisEngineUtil.createBisRunRelation(this.flowRunProxy.getRunId(), "bis_car_info", bisId.toString());
			jsonProxy.setRtState(true);
			conn.commit();
		}catch(Exception e){
			TeeDbUtility.rollback(conn);
			throw new TeeOperationException(e.getMessage());
		}finally{
			TeeDbUtility.closeConn(conn);
		}
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
