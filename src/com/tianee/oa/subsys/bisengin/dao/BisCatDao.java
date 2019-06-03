package com.tianee.oa.subsys.bisengin.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.bisengin.bean.BisCategory;
import com.tianee.oa.subsys.bisengin.model.BisCategoryModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

@Repository
public class BisCatDao extends TeeBaseDao<BisCategory>{
	
	public void addBisCat(BisCategory bisCategory){
		super.save(bisCategory);
	}
	
	public void updateBisCat(BisCategory bisCategory){
		super.update(bisCategory);
	}
	
	public void deleteBisCat(BisCategory bisCategory){
		super.deleteByObj(bisCategory);
	}
	
	/**
	 * 获取分类前台显示数据集合
	 * @param categoryModel
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson datagrid(BisCategoryModel categoryModel,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = "from BisCategory bc order by bc.sortNo asc";
		long total = count("select count(*) from BisCategory bc", null);
		List<BisCategory> list = pageFind(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		List<BisCategoryModel> mList = new ArrayList();
		for(BisCategory cat:list){
			BisCategoryModel m = new BisCategoryModel();
			BeanUtils.copyProperties(cat, m);
			mList.add(m);
		}
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(mList);
		
		return dataGridJson;
	}
}
