package com.tianee.oa.core.workflow.plugins;

import java.util.List;
import java.util.Map;

import com.tianee.oa.core.base.fixedAssets.bean.TeeFixedAssetsCategory;
import com.tianee.oa.core.base.fixedAssets.bean.TeeFixedAssetsInfo;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.proxy.TeeJsonProxy;

public class TeeUpdatFFPlugin extends TeeWfPlugin{

	@Override
	public TeeJsonProxy beforeTurnnext() {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterTurnnext() {
		String per = this.flowRunDatas
				.get(formItemIdentities.get("申请人"));
		String num1 = this.flowRunDatas
				.get(formItemIdentities.get("可申请的数量"));
		int numk=Integer.parseInt(num1);
		//System.out.println(name);
		String num2 = this.flowRunDatas
				.get(formItemIdentities.get("申请数量"));
		int num=Integer.parseInt(num2);
		String hao = this.flowRunDatas
				.get("DATA_20");
		
		Object [] as1={hao};
		List<TeeFixedAssetsInfo> executeQuery = simpleDaoSupport.find(" from TeeFixedAssetsInfo where assetCode=? ",as1);
		TeeFixedAssetsInfo as=executeQuery.get(0);
		int cc=as.getAuseNum()-num;
		as.setAuseNum(cc);
		as.setUseNum(num);
		Object [] as2={per};
		List<TeePerson> users= simpleDaoSupport.find(" from TeePerson where userName=? ",as2);
		TeePerson user=users.get(0);
		as.setUseUser(user);
		simpleDaoSupport.update(as);
		
	}

	@Override
	public TeeJsonProxy beforeSave() {
				
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
