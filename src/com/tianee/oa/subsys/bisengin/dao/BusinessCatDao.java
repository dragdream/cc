package com.tianee.oa.subsys.bisengin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.bisengin.bean.BusinessCat;
import com.tianee.oa.subsys.bisengin.model.BisTableFieldModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
@Repository
public class BusinessCatDao extends TeeBaseDao<BusinessCat>{

	
	public TeeEasyuiDataGridJson datagrid(BusinessCat cat,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = "from BusinessCat cat ";
		long total = count("select count(*) "+hql,null);
		hql+=" order by cat.sortNo asc";
		List list = pageFind(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		dataGridJson.setRows(list);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
}
