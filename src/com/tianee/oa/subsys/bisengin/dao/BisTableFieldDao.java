package com.tianee.oa.subsys.bisengin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.bisengin.bean.BisTableField;
import com.tianee.oa.subsys.bisengin.model.BisTableFieldModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

@Repository
public class BisTableFieldDao extends TeeBaseDao<BisTableField>{
	
	public void addBisTableField(BisTableField bisTableField){
		super.save(bisTableField);
	}
	
	public void updateBisTableField(BisTableField bisTableField){
		super.update(bisTableField);
	}
	
	public void deleteBisTableField(BisTableField bisTableField){
		super.deleteByObj(bisTableField);
	}
	
	public TeeEasyuiDataGridJson datagrid(BisTableFieldModel bisTableFieldModel,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = "from BisTableField btf where btf.bisTable.sid="+bisTableFieldModel.getBisTableId();
		long total = count("select count(*) "+hql,null);
		hql+=" order by btf.sid asc";
		List list = pageFind(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		dataGridJson.setRows(list);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	/**
	 * 检查字段是否存在
	 * @param fieldName
	 * @param tableId
	 * @return
	 */
	public boolean checkFieldIsExist(String fieldName,int tableId){
		String hql = "select count(btf.sid) from BisTableField btf where btf.fieldName=? and btf.bisTable.sid=?";
		long total = count(hql,new Object[]{fieldName,tableId});
		return total!=0;
	}
}

