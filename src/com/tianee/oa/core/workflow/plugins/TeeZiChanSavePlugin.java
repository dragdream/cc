package com.tianee.oa.core.workflow.plugins;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.tianee.oa.core.base.fixedAssets.bean.TeeFixedAssetsCategory;
import com.tianee.oa.core.base.fixedAssets.model.TeeFixedAssetsInfoModel;
import com.tianee.oa.core.base.fixedAssets.service.TeeFixedAssetsInfoService;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.proxy.TeeJsonProxy;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.global.TeeBeanFactory;


public class TeeZiChanSavePlugin extends TeeWfPlugin {
	@Override
	public TeeJsonProxy beforeTurnnext() {
		
		return null;
	}

	@Override
	public void afterTurnnext() {
		///获取表单内容
		String name = this.flowRunDatas
				.get(formItemIdentities.get("物品名称"));
		//System.out.println(name);
		String types = this.flowRunDatas
				.get(formItemIdentities.get("物品类别"));
		String hao = this.flowRunDatas
				.get(formItemIdentities.get("规格型号"));
		String rnum = this.flowRunDatas
				.get(formItemIdentities.get("入库数量"));
		String home = this.flowRunDatas
				.get(formItemIdentities.get("生产厂家"));
		String status = this.flowRunDatas
				.get(formItemIdentities.get("物品状态"));
		String dept = this.flowRunDatas
				.get(formItemIdentities.get("申请部门"));
		String person = this.getFlowRunDatas().get("EXTRA_10");
		String time = this.flowRunDatas
				.get(formItemIdentities.get("入库时间"));
		String biao = this.flowRunDatas
				.get(formItemIdentities.get("物品表"));
		//获得物品表的数据
		TeeFixedAssetsInfoModel model=new TeeFixedAssetsInfoModel();
		if(JSONArray.fromObject(biao).size()>0){
			for(int i=0;i< JSONArray.fromObject(biao).size();i++){
				JSONObject js = JSONArray.fromObject(biao).getJSONObject(i);
				int num1 =Integer.parseInt(String.valueOf(js.get("数量")));
				String bhao = String.valueOf(js.get("物品编号"));
				String remark = String.valueOf(js.get("备注"));
				model.setAssetCode(bhao);
				//库存
				model.setAssetNum(num1);
				//可申请数量
				model.setAuseNum(num1);
				model.setAssetName(name);
				model.setAssetsVersion(hao);
				model.setUseState("0");
				model.setUseStateDesc("0");
				model.setMadein(home);
				model.setRemark(remark);
				model.setAssetNum(num1);
				Object [] as={dept};
				List<TeeDepartment> executeQuery1 = simpleDaoSupport.find(" from TeeDepartment where deptName=? ",as);
				model.setDeptId(executeQuery1.get(0).getUuid());
				model.setKeeperId(Integer.parseInt(person));
				Object [] as1={types};
				List<TeeFixedAssetsCategory> executeQuery = simpleDaoSupport.find(" from TeeFixedAssetsCategory where name=? ",as1);
				model.setTypeId(executeQuery.get(0).getSid());
				model.setValideTimeDesc(time);
				TeeFixedAssetsInfoService fixedAssetsInfoService = 
						(TeeFixedAssetsInfoService) TeeBeanFactory.getBean("teeFixedAssetsInfoService");
				try {
					fixedAssetsInfoService.addAssetsInfoModel(model);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}

	@Override
	public TeeJsonProxy beforeSave() {
		return null;
	}

	@Override
	public void afterSave() {
		
		
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
