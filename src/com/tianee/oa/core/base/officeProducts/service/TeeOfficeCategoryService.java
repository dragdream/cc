package com.tianee.oa.core.base.officeProducts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeCategory;
import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeDepository;
import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeProduct;
import com.tianee.oa.core.base.officeProducts.model.TeeOfficeCategoryModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeOfficeCategoryService extends TeeBaseService{
	
	public TeeOfficeCategory addCategory(TeeOfficeCategory category){
		getSimpleDaoSupport().save(category);
		return category;
	}
	
	public TeeOfficeCategory addCategoryModel(TeeOfficeCategoryModel categoryModel){
		TeeOfficeCategory category = new TeeOfficeCategory();
		BeanUtils.copyProperties(categoryModel, category);
		
		TeeOfficeDepository depository = (TeeOfficeDepository) simpleDaoSupport.get(TeeOfficeDepository.class, categoryModel.getOfficeDepositoryId());
		category.setOfficeDepository(depository);
		
		TeePerson createUser = (TeePerson) simpleDaoSupport.get(TeePerson.class, categoryModel.getCreateUserId());
		category.setCreateUser(createUser);
		
		addCategory(category);
		return category;
	}
	
	public TeeOfficeCategory deleteCategory(TeeOfficeCategory category){
		//获取该分类下所有用品
		List<TeeOfficeProduct> products = simpleDaoSupport.find("from TeeOfficeProduct where category.sid="+category.getSid(), null);
		//清除该用品下的单据、库存信息
		for(TeeOfficeProduct product:products){
			simpleDaoSupport.executeUpdate("delete from TeeOfficeStock where product.sid="+product.getSid(), null);
			simpleDaoSupport.executeUpdate("delete from TeeOfficeStockBill where product.sid="+product.getSid(), null);
			simpleDaoSupport.deleteByObj(product);
		}
		getSimpleDaoSupport().deleteByObj(category);
		return category;
	}
	
	public void updateCategory(TeeOfficeCategory category){
		getSimpleDaoSupport().update(category);
	}
	
	public void updateCategoryModel(TeeOfficeCategoryModel categoryModel){
		TeeOfficeCategory category = (TeeOfficeCategory) simpleDaoSupport.get(TeeOfficeCategory.class, categoryModel.getSid());
		BeanUtils.copyProperties(categoryModel, category);
		
		TeeOfficeDepository depository = (TeeOfficeDepository) simpleDaoSupport.get(TeeOfficeDepository.class, categoryModel.getOfficeDepositoryId());
		category.setOfficeDepository(depository);
		
		updateCategory(category);
	}
	
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		String hql = "from TeeOfficeCategory oc where 1=1 ";
		TeePerson person = (TeePerson) requestDatas.get(TeeConst.LOGIN_USER);
		//如果不是管理员的话，则进行权限校验
		if(!person.getUserId().equals("admin")){
			hql+=" and exists (select 1 from oc.officeDepository.admins admins where admins.uuid="+person.getUuid()+")";
		}
		
		//根据depositoryId查找分类
		int depositoryId = TeeStringUtil.getInteger(requestDatas.get("depositoryId"), -1);
		if(depositoryId!=-1){//如果传了depositoryId的话，则进行条件查询
			hql+=" and oc.officeDepository.sid="+depositoryId;
		}
		
		
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		
		List rows = new ArrayList();
		List<TeeOfficeCategory> list = simpleDaoSupport.pageFind(hql+" order by oc.sid asc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		for(TeeOfficeCategory category:list){
			TeeOfficeCategoryModel model = new TeeOfficeCategoryModel();
			BeanUtils.copyProperties(category, model);
			model.setCreateUserId(category.getCreateUser().getUuid());
			model.setCreateUserDesc(category.getCreateUser().getUserName());
			if(category.getOfficeDepository()!=null){
				model.setOfficeDepositoryDesc(category.getOfficeDepository().getDeposName());
				model.setOfficeDepositoryId(category.getOfficeDepository().getSid());
			}
			rows.add(model);
		}
		
		datagird.setRows(rows);
		datagird.setTotal(total);
		
		return datagird;
	}
	
	public TeeOfficeCategory getById(int sid){
		TeeOfficeCategory category = (TeeOfficeCategory) simpleDaoSupport.get(TeeOfficeCategory.class, sid);
		return category;
	}
	
	public List<TeeOfficeCategoryModel> getCatListWithNoPriv(int deposId){
		String hql = "from TeeOfficeCategory oc where oc.officeDepository.sid="+deposId;
		List<TeeOfficeCategoryModel> models = new ArrayList<TeeOfficeCategoryModel>();
		List<TeeOfficeCategory> list = simpleDaoSupport.find(hql, null);
		for(TeeOfficeCategory oc:list){
			TeeOfficeCategoryModel m = new TeeOfficeCategoryModel();
			m.setSid(oc.getSid());
			m.setCatName(oc.getCatName());
			models.add(m);
		}
		return models;
	}
}
