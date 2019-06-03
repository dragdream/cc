package com.tianee.oa.subsys.bisengin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.bisengin.bean.BisTable;
import com.tianee.oa.subsys.bisengin.model.BisTableModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

@Repository
public class BisTableDao extends TeeBaseDao<BisTable>{
	
	public void addBisTable(BisTable bisTable){
		super.save(bisTable);
	}
	
	public void updateBisTable(BisTable bisTable){
		super.update(bisTable);
	}
	
	public void deleteBisTable(BisTable bisTable){
		super.deleteByObj(bisTable);
	}
	
	/**
	 * @param bisTableModel
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson datagrid(BisTableModel bisTableModel,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = null;
		if(bisTableModel.getBisCatId()==0){
			hql = "from BisTable bt where bt.bisCat is null ";
		}else{
			hql = "from BisTable bt where bt.bisCat.sid="+bisTableModel.getBisCatId();
		}
		long total = count("select count(*) "+hql,null);
		hql+="order by bt.sid asc";
		List<BisTable> list = pageFind(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		dataGridJson.setTotal(total);
		dataGridJson.setRows(list);
		return dataGridJson;
	}
}
