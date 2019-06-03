package com.tianee.oa.subsys.bisengin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.bisengin.bean.BisForm;
import com.tianee.oa.subsys.bisengin.bean.BisTable;
import com.tianee.oa.subsys.bisengin.bean.BisTableField;
import com.tianee.oa.subsys.bisengin.model.BisFormModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

@Repository
public class BisFormDao extends TeeBaseDao<BisForm>{

	/**
	 * 获取所有bisForm的列表
	 * @param bisTableFieldModel
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson datagrid(BisFormModel model,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = "from BisForm bf ";
		long total = count("select count(*) "+hql,null);
		hql+=" order by bf.sid asc";
		List list = pageFind(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		dataGridJson.setRows(list);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}

	
}
