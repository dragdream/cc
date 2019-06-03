package com.tianee.oa.subsys.bisengin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.bisengin.bean.BusinessModel;
import com.tianee.oa.subsys.bisengin.model.BusModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

@Repository
public class BusinessModelDao extends TeeBaseDao<BusinessModel>{

	public TeeEasyuiDataGridJson datagrid(BusModel model,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = "from BusinessModel model ";
		long total = count("select count(*) "+hql,null);
		hql+=" order by model.createTime asc";
		List<BusinessModel> list = pageFind(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		dataGridJson.setRows(list);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
}
