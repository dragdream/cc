package com.tianee.oa.subsys.contract.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.contract.bean.TeeContractCategory;
import com.tianee.oa.subsys.contract.model.TeeContractCategoryModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeContractCategoryService extends TeeBaseService{
	
	public void add(TeeContractCategoryModel categoryModel){
		TeeContractCategory contractCategory = Model2Entity(categoryModel);
		simpleDaoSupport.save(contractCategory);
	}
	
	public void update(TeeContractCategoryModel categoryModel){
		TeeContractCategory contractCategory = Model2Entity(categoryModel);
		simpleDaoSupport.update(contractCategory);
	}
	
	public void delete(int sid){
		simpleDaoSupport.executeUpdate("delete from TeeContractSort where category.sid="+sid,null);
		simpleDaoSupport.executeUpdate("delete from TeeContract where category.sid="+sid,null);
		simpleDaoSupport.delete(TeeContractCategory.class, sid);
	}
	
	public TeeContractCategoryModel get(int sid){
		TeeContractCategory category = (TeeContractCategory) simpleDaoSupport.get(TeeContractCategory.class, sid);
		return Entity2Model(category);
	}
	
	public TeeEasyuiDataGridJson datagrid(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		String hql = "from TeeContractCategory category ";
		
		List<TeeContractCategory> categoryList = simpleDaoSupport.pageFind(hql+"order by category.sid asc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		List<TeeContractCategoryModel> modelList = new ArrayList();
		for(TeeContractCategory category:categoryList){
			TeeContractCategoryModel model = Entity2Model(category);
			modelList.add(model);
		}
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	public List<TeeZTreeModel> getCategoryTreeByViewPriv(TeePerson loginUser){
		List<TeeZTreeModel> ztreelist = new ArrayList<TeeZTreeModel>();
		String hql = "from TeeContractCategory category where exists (select 1 from category.viewPriv viewPriv where viewPriv.uuid="+loginUser.getUuid()+") order by category.sid asc";
		List<TeeContractCategory> list = simpleDaoSupport.find(hql, null);
		
		for(TeeContractCategory category:list){
			TeeZTreeModel m = new TeeZTreeModel();
			m.setParent(true);
			m.setTitle(category.getName());
			m.setId(String.valueOf(category.getSid()));
			ztreelist.add(m);
		}
		
		return ztreelist;
	}
	
	public List<TeeZTreeModel> getCategoryTreeByManagePriv(TeePerson loginUser){
		List<TeeZTreeModel> ztreelist = new ArrayList<TeeZTreeModel>();
		String hql = "from TeeContractCategory category where exists (select 1 from category.managePriv managePriv where managePriv.uuid="+loginUser.getUuid()+") order by category.sid asc";
		List<TeeContractCategory> list = simpleDaoSupport.find(hql, null);
		
		for(TeeContractCategory category:list){
			TeeZTreeModel m = new TeeZTreeModel();
			m.setParent(true);
			m.setTitle(category.getName());
			m.setId(String.valueOf(category.getSid()));
			ztreelist.add(m);
		}
		
		return ztreelist;
	}
	
	public TeeContractCategoryModel Entity2Model(TeeContractCategory category){
		TeeContractCategoryModel categoryModel = new TeeContractCategoryModel();
		BeanUtils.copyProperties(category, categoryModel);
		
		StringBuffer ids = new StringBuffer();
		StringBuffer names = new StringBuffer();
		
		Set<TeePerson> persons = category.getViewPriv();
		for(TeePerson p:persons){
			ids.append(p.getUuid()+",");
			names.append(p.getUserName()+",");
		}
		if(ids.length()!=0){
			ids.deleteCharAt(ids.length()-1);
			names.deleteCharAt(names.length()-1);
		}
		
		categoryModel.setViewPrivIds(ids.toString());
		categoryModel.setViewPrivNames(names.toString());
		
		
		ids.delete(0, ids.length());
		names.delete(0, names.length());
		
		persons = category.getManagePriv();
		for(TeePerson p:persons){
			ids.append(p.getUuid()+",");
			names.append(p.getUserName()+",");
		}
		if(ids.length()!=0){
			ids.deleteCharAt(ids.length()-1);
			names.deleteCharAt(names.length()-1);
		}
		categoryModel.setManagePrivIds(ids.toString());
		categoryModel.setManagePrivNames(names.toString());
		
		return categoryModel;
	}
	
	public TeeContractCategory Model2Entity(TeeContractCategoryModel categoryModel){
		TeeContractCategory category = new TeeContractCategory();
		BeanUtils.copyProperties(categoryModel, category);
		
		String ids[] = TeeStringUtil.parseStringArray(categoryModel.getViewPrivIds());
		TeePerson p = null;
		for(String uuid:ids){
			if("".equals(uuid)){
				continue;
			}
			p = new TeePerson();
			p.setUuid(TeeStringUtil.getInteger(uuid, 0));
			category.getViewPriv().add(p);
		}
		
		ids = TeeStringUtil.parseStringArray(categoryModel.getManagePrivIds());
		for(String uuid:ids){
			if("".equals(uuid)){
				continue;
			}
			p = new TeePerson();
			p.setUuid(TeeStringUtil.getInteger(uuid, 0));
			category.getManagePriv().add(p);
		}
		
		return category;
	}
}
