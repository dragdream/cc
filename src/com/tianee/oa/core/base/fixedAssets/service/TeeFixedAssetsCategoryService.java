package com.tianee.oa.core.base.fixedAssets.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.tianee.oa.core.base.fixedAssets.bean.TeeFixedAssetsCategory;
import com.tianee.oa.core.base.fixedAssets.bean.TeeFixedAssetsInfo;
import com.tianee.oa.core.base.fixedAssets.model.TeeFixedAssetsCategoryModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeFixedAssetsCategoryService extends TeeBaseService{
	
	public TeeFixedAssetsCategory addAssetsType(TeeFixedAssetsCategory assetsType){
		getSimpleDaoSupport().save(assetsType);
		return assetsType;
	}
	
	public TeeFixedAssetsCategory addAssetsTypeModel(TeeFixedAssetsCategoryModel assetsTypeModel){
		TeeFixedAssetsCategory assetsType = new TeeFixedAssetsCategory();
		BeanUtils.copyProperties(assetsTypeModel, assetsType);
		addAssetsType(assetsType);
		return assetsType;
	}
	
	public TeeFixedAssetsCategory delAssetsType(TeeFixedAssetsCategory assetsType){
		setAssetsTypeEmpty(assetsType.getSid());
		getSimpleDaoSupport().deleteByObj(assetsType);
		return assetsType;
	}
	
	public void updateAssetsType(TeeFixedAssetsCategory assetsType){
		getSimpleDaoSupport().update(assetsType);
	}
	
	public void updateAssetsTypeModel(TeeFixedAssetsCategoryModel assetsTypeModel){
		TeeFixedAssetsCategory assetsType = (TeeFixedAssetsCategory) simpleDaoSupport.get(TeeFixedAssetsCategory.class, assetsTypeModel.getSid());
		BeanUtils.copyProperties(assetsTypeModel, assetsType);
		updateAssetsType(assetsType);
	}
	
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		String hql = "from TeeFixedAssetsCategory oc where 1=1 ";
		
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		
		List rows = new ArrayList();
		List<TeeFixedAssetsCategory> list = simpleDaoSupport.pageFind(hql+" order by oc.sortNo asc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		for(TeeFixedAssetsCategory assetsType:list){
			TeeFixedAssetsCategoryModel model = new TeeFixedAssetsCategoryModel();
			BeanUtils.copyProperties(assetsType, model);
			rows.add(model);
		}
		
		datagird.setRows(rows);
		datagird.setTotal(total);
		
		return datagird;
	}
	
	public TeeFixedAssetsCategory getById(int sid){
		TeeFixedAssetsCategory assetsType = (TeeFixedAssetsCategory) simpleDaoSupport.get(TeeFixedAssetsCategory.class, sid);
		return assetsType;
	}
	
	public void setAssetsTypeEmpty(int typeId){
		List<TeeFixedAssetsInfo> assetsInfo =simpleDaoSupport.executeQuery("from TeeFixedAssetsInfo info where info.category.sid="+typeId, null);
		for(TeeFixedAssetsInfo info:assetsInfo){
			info.setCategory(null);
			simpleDaoSupport.update(info);
		}
	}
}
