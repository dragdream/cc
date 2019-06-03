package com.tianee.oa.subsys.contract.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.contract.bean.TeeContractCategory;
import com.tianee.oa.subsys.contract.bean.TeeContractSort;
import com.tianee.oa.subsys.contract.model.TeeContractSortModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeContractSortService extends TeeBaseService{
	
	public void add(TeeContractSortModel contractSortModel){
		TeeContractSort contractSort = Model2Entity(contractSortModel);
		simpleDaoSupport.save(contractSort);
	}
	
	public void update(TeeContractSortModel contractSortModel){
		TeeContractSort contractSort = Model2Entity(contractSortModel);
		simpleDaoSupport.update(contractSort);
	}
	
	public void delete(int sid){
		simpleDaoSupport.executeUpdate("delete from TeeContract where contractSort.sid="+sid,null);
		simpleDaoSupport.delete(TeeContractSort.class, sid);
	}
	
	public TeeContractSortModel get(int sid){
		TeeContractSort contractSort = (TeeContractSort) simpleDaoSupport.get(TeeContractSort.class, sid);
		return Entity2Model(contractSort);
	}
	
	public List<TeeContractSortModel> getSortByCatId(int catId){
		List<TeeContractSortModel> mlist = new ArrayList();
		List<TeeContractSort> list = simpleDaoSupport.find("from TeeContractSort sort where sort.category.sid=?", new Object[]{catId});
		for(TeeContractSort sort:list){
			mlist.add(Entity2Model(sort));
		}
		return mlist;
	}
	
	public TeeEasyuiDataGridJson datagrid(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = "from TeeContractSort sort ";
		List<TeeContractSort> list = simpleDaoSupport.pageFind(hql+"order by sort.category.sid asc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		List<TeeContractSortModel> modelList = new ArrayList();
		for(TeeContractSort sort:list){
			TeeContractSortModel m = Entity2Model(sort);
			modelList.add(m);
		}
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	public List<TeeContractSortModel> getSortsByCatId(int catId){
		List<TeeContractSort> list = simpleDaoSupport.find("from TeeContractSort where category.sid=? order sid asc", new Object[]{catId});
		List<TeeContractSortModel> modelList = new ArrayList();
		for(TeeContractSort sort:list){
			TeeContractSortModel m = Entity2Model(sort);
			modelList.add(m);
		}
		
		return modelList;
	}
	
	public TeeContractSortModel Entity2Model(TeeContractSort sort){
		TeeContractSortModel contractSortModel = new TeeContractSortModel();
		BeanUtils.copyProperties(sort, contractSortModel);
		if(sort.getCategory()!=null){
			contractSortModel.setCategoryId(sort.getCategory().getSid());
			contractSortModel.setCategoryName(sort.getCategory().getName());
		}
		return contractSortModel;
	}
	
	public TeeContractSort Model2Entity(TeeContractSortModel sortModel){
		TeeContractSort contractSort = new TeeContractSort();
		BeanUtils.copyProperties(sortModel, contractSort);
		TeeContractCategory category = 
				(TeeContractCategory) simpleDaoSupport.get(TeeContractCategory.class, sortModel.getCategoryId());
		
		contractSort.setCategory(category);
		return contractSort;
	}
}
