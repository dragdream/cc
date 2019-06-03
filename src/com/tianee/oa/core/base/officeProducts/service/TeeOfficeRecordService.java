package com.tianee.oa.core.base.officeProducts.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeRecord;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeOfficeRecordService extends TeeBaseService{
	
	public void delRecord(int recordIds[]){
		for(int id:recordIds){
			simpleDaoSupport.delete(TeeOfficeRecord.class, id);
		}
	}
	
	/**
	 * 入库记录列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagrid1")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		String hql = "from TeeOfficeRecord r where 1=1 and r.recordType=1 ";
		List<TeeOfficeRecord> records = simpleDaoSupport.pageFind(hql+"order by r.sid desc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		dataGridJson.setRows(records);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	/**
	 * 报废记录列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagrid2")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid2(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		String hql = "from TeeOfficeRecord r where 1=1 and r.recordType=2 ";
		List<TeeOfficeRecord> records = simpleDaoSupport.pageFind(hql+"order by r.sid desc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		dataGridJson.setRows(records);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	/**
	 * 领用记录列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagrid3")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid3(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		String hql = "from TeeOfficeRecord r where 1=1 and r.recordType=3 ";
		List<TeeOfficeRecord> records = simpleDaoSupport.pageFind(hql+"order by r.sid desc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		dataGridJson.setRows(records);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	/**
	 * 借用记录列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagrid4")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid4(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		String hql = "from TeeOfficeRecord r where 1=1 and r.recordType=4 ";
		List<TeeOfficeRecord> records = simpleDaoSupport.pageFind(hql+"order by r.sid desc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		dataGridJson.setRows(records);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	/**
	 * 归还记录列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagrid5")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid5(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		String hql = "from TeeOfficeRecord r where 1=1 and r.recordType=5 ";
		List<TeeOfficeRecord> records = simpleDaoSupport.pageFind(hql+"order by r.sid desc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		dataGridJson.setRows(records);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	/**
	 * 维护记录列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagrid6")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid6(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		String hql = "from TeeOfficeRecord r where 1=1 and r.recordType=6 ";
		List<TeeOfficeRecord> records = simpleDaoSupport.pageFind(hql+"order by r.sid desc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		dataGridJson.setRows(records);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	/**
	 * 库存记录列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagrid7")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid7(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		String hql = "from TeeOfficeRecord r where 1=1 and r.recordType=7 ";
		List<TeeOfficeRecord> records = simpleDaoSupport.pageFind(hql+"order by r.sid desc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		dataGridJson.setRows(records);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	/**
	 * 删除记录列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagrid8")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid8(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		String hql = "from TeeOfficeRecord r where 1=1 and r.recordType=8 ";
		List<TeeOfficeRecord> records = simpleDaoSupport.pageFind(hql+"order by r.sid desc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		dataGridJson.setRows(records);                
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	
	public TeeJson getOfficeRecords(String proCode,String recordType){
		TeeJson json = new TeeJson();
		String hql = "from TeeOfficeRecord r where 1=1 and r.recordType= "+recordType+" and r.proCode='"+proCode+"'";
		List<TeeOfficeRecord> records = simpleDaoSupport.executeQuery(hql, null);
		if(null!=records && records.size()>0){
			json.setRtState(true);
			json.setRtData(records);
		}else{
			json.setRtState(false);
			json.setRtMsg("没有相关记录！");
		}
		return json;
	}
	
	
}
