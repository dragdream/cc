package com.tianee.oa.core.workflow.plugins;

import java.util.List;
import java.util.Map;

import com.tianee.oa.core.base.fixedAssets.bean.TeeFixedAssetsInfo;
import com.tianee.oa.core.workflow.proxy.TeeJsonProxy;

public class TeeUpdateFCPlugin extends TeeWfPlugin{

	@Override
	public TeeJsonProxy beforeTurnnext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void afterTurnnext() {
		String num2 = this.flowRunDatas
				.get(formItemIdentities.get("报废数量"));
		int num=Integer.parseInt(num2);
		String hao = this.flowRunDatas
				.get("DATA_25");
		Object [] as1={hao};
		List<TeeFixedAssetsInfo> executeQuery = simpleDaoSupport.find(" from TeeFixedAssetsInfo where assetCode=? ",as1);
		TeeFixedAssetsInfo as=executeQuery.get(0);
		int cc=as.getAuseNum()-num;
		as.setAuseNum(cc);
		as.setScrapNum(num);
		simpleDaoSupport.update(as);
		
	}

	@Override
	public TeeJsonProxy beforeSave() {
		// TODO Auto-generated method stub
		return null;
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
