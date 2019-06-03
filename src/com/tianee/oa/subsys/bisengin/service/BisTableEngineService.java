package com.tianee.oa.subsys.bisengin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.bisengin.bean.BisTable;
import com.tianee.oa.subsys.bisengin.bean.BisTableEngine;
import com.tianee.oa.subsys.bisengin.model.BisTableEngineModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class BisTableEngineService extends TeeBaseService{
	
	public void addBisTableEngine(BisTableEngineModel bisTableEngineModel){
		BisTableEngine bisTableEngine = new BisTableEngine();
		BisTable bisTable = (BisTable) simpleDaoSupport.get(BisTable.class, bisTableEngineModel.getBisTableId());
		bisTableEngine.setBisModel(bisTableEngineModel.getBisModel());
		bisTableEngine.setFlowId(bisTableEngineModel.getFlowId());
		bisTableEngine.setBisTable(bisTable);
		bisTableEngine.setType(1);
		bisTableEngine.setListTitle("");
		bisTableEngine.setStatus(0);
		bisTableEngine.setRemark("");
		simpleDaoSupport.save(bisTableEngine);
	}
	
	public void updateBisTableEngine(BisTableEngineModel bisTableEngineModel){
		BisTableEngine bisTableEngine = new BisTableEngine();
		BisTable bisTable = (BisTable) simpleDaoSupport.get(BisTable.class, bisTableEngineModel.getBisTableId());
		bisTableEngine.setBisModel(bisTableEngineModel.getBisModel());
		bisTableEngine.setFlowId(bisTableEngineModel.getFlowId());
		bisTableEngine.setBisTable(bisTable);
		bisTableEngine.setSid(bisTableEngineModel.getSid());
		bisTableEngine.setType(bisTableEngineModel.getType());
		bisTableEngine.setListTitle(bisTableEngineModel.getListTitle());
		bisTableEngine.setStatus(bisTableEngineModel.getStatus());
		bisTableEngine.setRemark(bisTableEngineModel.getRemark());
		simpleDaoSupport.update(bisTableEngine);
	}
	
	public void delBisTableEngine(int sid){
		simpleDaoSupport.delete(BisTableEngine.class, sid);
	}
	
	public BisTableEngineModel getBisTableEngine(int sid){
		BisTableEngine bisTableEngine = (BisTableEngine) simpleDaoSupport.get(BisTableEngine.class, sid);
		BisTableEngineModel bisTableEngineModel = new BisTableEngineModel();
		bisTableEngineModel.setBisModel(bisTableEngine.getBisModel());
		bisTableEngineModel.setBisTableId(bisTableEngine.getBisTable().getSid());
		bisTableEngineModel.setFlowId(bisTableEngine.getFlowId());
		bisTableEngineModel.setType(bisTableEngine.getType());
		bisTableEngineModel.setListTitle(bisTableEngine.getListTitle());
		bisTableEngineModel.setStatus(bisTableEngine.getStatus());
		bisTableEngineModel.setRemark(bisTableEngine.getRemark());
		return bisTableEngineModel;
	}
	
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,Map requestData){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		int bisTableId = TeeStringUtil.getInteger(requestData.get("bisTableId"), 0);
		String hql = "from BisTableEngine bte where bte.bisTable.sid="+bisTableId+" order by bte.sid asc";
		List<BisTableEngine> list = simpleDaoSupport.find(hql, null);
		List<BisTableEngineModel> modelList = new ArrayList<BisTableEngineModel>();
		
		Map data = null;
		
		for(BisTableEngine bte:list){
			BisTableEngineModel model = new BisTableEngineModel();
			model.setBisTableId(bte.getBisTable().getSid());
			model.setFlowId(bte.getFlowId());
			model.setRemark(bte.getRemark());
			model.setSid(bte.getSid());
			model.setStatus(bte.getStatus());
			data = simpleDaoSupport.getMap("select ft.flowName as flowName from TeeFlowType ft where ft.sid="+bte.getFlowId(), null);
			if(data!=null){
				model.setFlowName(TeeStringUtil.getString(data.get("flowName")));
			}else{
				model.setFlowName("[无绑定流程]");
			}
			modelList.add(model);
		}
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(Long.parseLong(list.size()+""));
		return dataGridJson;
	}
	
	/**
	 * 设置引擎状态
	 * @param sid
	 * @param status
	 */
	public void setStatus(int sid,int status){
		simpleDaoSupport.executeUpdate("update BisTableEngine set status="+status+" where sid="+sid, null);
	}
}
