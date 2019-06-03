package com.tianee.oa.subsys.bisengin.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.bisengin.bean.BisCategory;
import com.tianee.oa.subsys.bisengin.dao.BisCatDao;
import com.tianee.oa.subsys.bisengin.model.BisCategoryModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class BisCatService extends TeeBaseService{
	@Autowired
	private BisCatDao bisCatDao;
	
	public void addBisCat(BisCategoryModel bisCategoryModel){
		BisCategory bisCategory = new BisCategory();
		BeanUtils.copyProperties(bisCategoryModel, bisCategory);
		bisCatDao.save(bisCategory);
	}
	
	public void updateBisCat(BisCategoryModel bisCategoryModel){
		BisCategory bisCategory = new BisCategory();
		BeanUtils.copyProperties(bisCategoryModel, bisCategory);
		bisCatDao.update(bisCategory);
	}
	
	/**
	 * 通过sid获取模型
	 * @param sid
	 * @return
	 */
	public BisCategoryModel getModelById(int sid){
		BisCategory bisCategory = bisCatDao.get(sid);
		BisCategoryModel m = new BisCategoryModel();
		BeanUtils.copyProperties(bisCategory, m);
		return m;
	}
	
	public BisCategory deleteBisCat(int sid){
		BisCategory bisCategory = bisCatDao.get(sid);
		bisCatDao.deleteByObj(bisCategory);
		return bisCategory;
	}
	
	/**
	 * 获取分类前台显示数据集合
	 * @param categoryModel
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson datagrid(BisCategoryModel categoryModel,TeeDataGridModel dm){
		return bisCatDao.datagrid(categoryModel, dm);
	}
}
