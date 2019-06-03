package com.tianee.oa.core.base.officeProducts.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeCategory;
import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeProduct;
import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeRecord;
import com.tianee.oa.core.base.officeProducts.model.TeeOfficeProductModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeOfficeProductService extends TeeBaseService{
	
	public TeeOfficeProduct addProduct(TeeOfficeProduct product){
		getSimpleDaoSupport().save(product);
		return product;
	}
	
	public TeeOfficeProduct addProductModel(TeeOfficeProductModel productModel){
		TeeOfficeProduct product = new TeeOfficeProduct();
		modelToEntity(productModel,product);
		
		addProduct(product);
		return product;
	}
	
	public void entityToModel(TeeOfficeProduct officeProduct,TeeOfficeProductModel model){
		BeanUtils.copyProperties(officeProduct,model,new String[]{"price"});
		//设置价格
		model.setPrice(officeProduct.getPrice().toString());
		StringBuffer ids = new StringBuffer();
		StringBuffer names = new StringBuffer();
		
		//设置审批权限
		Set<TeePerson> auditors = officeProduct.getAuditors();
		for(TeePerson p : auditors){
			ids.append(p.getUuid()+",");
			names.append(p.getUserName()+",");
		}
		model.setAuditorsIds(ids.toString());
		model.setAuditorsNames(names.toString());
		ids.delete(0, ids.length());
		names.delete(0, names.length());
		
		//设置登记权限
		Set<TeePerson> regUsers = officeProduct.getRegUsers();
		for(TeePerson p : regUsers){
			ids.append(p.getUuid()+",");
			names.append(p.getUserName()+",");
		}
		model.setRegUsersIds(ids.toString());
		model.setRegUsersNames(names.toString());
		ids.delete(0, ids.length());
		names.delete(0, names.length());
		
		//设置登记权限（部门）
		Set<TeeDepartment> regDepts = officeProduct.getRegDepts();
		for(TeeDepartment d : regDepts){
			ids.append(d.getUuid()+",");
			names.append(d.getDeptName()+",");
		}
		model.setRegDeptsIds(ids.toString());
		model.setRegDeptsName(names.toString());
		
		//设置类型
		model.setCategoryId(officeProduct.getCategory().getSid());
		model.setCategoryName(officeProduct.getCategory().getCatName());
		
		//设置所属库
		model.setDepositoryId(officeProduct.getCategory().getOfficeDepository().getSid());
		model.setDepositoryName(officeProduct.getCategory().getOfficeDepository().getDeposName());
	}
	
	public void modelToEntity(TeeOfficeProductModel productModel,TeeOfficeProduct product){
		BeanUtils.copyProperties(productModel, product,new String[]{"price"});
		
		//设置price的BigDecimal类型
		product.setPrice(BigDecimal.valueOf(Double.parseDouble(productModel.getPrice())));
		
		//设置类型
		TeeOfficeCategory category = (TeeOfficeCategory) simpleDaoSupport.get(TeeOfficeCategory.class, productModel.getCategoryId());
		product.setCategory(category);
		
		//设置审批权限
		int auditorsIds [] = TeeStringUtil.parseIntegerArray(productModel.getAuditorsIds());
		for(int auditor:auditorsIds){
			if(auditor==0){
				continue;
			}
			TeePerson p = (TeePerson) simpleDaoSupport.load(TeePerson.class, auditor);
			product.getAuditors().add(p);
		}
		
		//设置登记权限
		int regUsersIds [] = TeeStringUtil.parseIntegerArray(productModel.getRegUsersIds());
		for(int regUserId:regUsersIds){
			if(regUserId==0){
				continue;
			}
			TeePerson p = (TeePerson) simpleDaoSupport.load(TeePerson.class, regUserId);
			product.getRegUsers().add(p);
		}
		
		//设置登记权限（部门）
		int regDeptIds [] = TeeStringUtil.parseIntegerArray(productModel.getRegDeptsIds());
		for(int regDeptId:regDeptIds){
			if(regDeptId==0){
				continue;
			}
			TeeDepartment d = (TeeDepartment) simpleDaoSupport.load(TeeDepartment.class, regDeptId);
			product.getRegDepts().add(d);
		}
	}
	
	public void delProduct(TeeOfficeProduct product){
		getSimpleDaoSupport().deleteByObj(product);
	}
	
	public void delProduct(TeePerson loginUser,int sid){
		TeeOfficeProduct product = (TeeOfficeProduct) simpleDaoSupport.get(TeeOfficeProduct.class, sid);
		
		//日志记录
		TeeOfficeRecord record = new TeeOfficeRecord();
		record.setActionDesc("删除用品");
		record.setRecordType(8);
		record.setActionTime(Calendar.getInstance());
		record.setActionTimeDesc(TeeDateUtil.format(Calendar.getInstance()));
		record.setCategoryName(product.getCategory().getCatName());
		record.setDepositoryName(product.getCategory().getOfficeDepository().getDeposName());
		record.setProCode(product.getProCode());
		record.setProName(product.getProName());
		record.setRegUserId(loginUser.getUuid());
		record.setRegUserName(loginUser.getUserName()+"");
		simpleDaoSupport.save(record);
		
		delProduct(product);
	}
	
	public void delProducts(TeePerson loginUser,String sids){
		if(sids.endsWith(",")){
			sids = sids.substring(0,sids.length()-1);
		}
		String[] ids = sids.split(",");
		for(String id:ids){
			TeeOfficeProduct product = (TeeOfficeProduct) simpleDaoSupport.get(TeeOfficeProduct.class, Integer.parseInt(id));
			//日志记录
			TeeOfficeRecord record = new TeeOfficeRecord();
			record.setActionDesc("删除用品");
			record.setRecordType(8);
			record.setActionTime(Calendar.getInstance());
			record.setActionTimeDesc(TeeDateUtil.format(Calendar.getInstance()));
			record.setCategoryName(product.getCategory().getCatName());
			record.setDepositoryName(product.getCategory().getOfficeDepository().getDeposName());
			record.setProCode(product.getProCode());
			record.setProName(product.getProName());
			record.setRegUserId(loginUser.getUuid());
			record.setRegUserName(loginUser.getUserName()+"");
			simpleDaoSupport.save(record);
			delProduct(product);
		}
	}
	
	public void updateProduct(TeeOfficeProduct product){
		getSimpleDaoSupport().update(product);
	}
	
	public void updateProductModel(TeeOfficeProductModel productModel){
		TeeOfficeProduct officeProduct = getById(productModel.getSid());
		officeProduct.getAuditors().clear();
		officeProduct.getRegUsers().clear();
		officeProduct.getRegDepts().clear();
		modelToEntity(productModel,officeProduct);
		updateProduct(officeProduct);
	}
	
	public TeeOfficeProductModel getModelById(int sid){
		TeeOfficeProduct officeProduct = getById(sid);
		TeeOfficeProductModel model = new TeeOfficeProductModel();
		entityToModel(officeProduct,model);
		return model;
	}
	
	public TeeOfficeProduct getById(int sid){
		TeeOfficeProduct officeProduct = (TeeOfficeProduct) simpleDaoSupport.get(TeeOfficeProduct.class, sid);
		return officeProduct;
	}
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = "from TeeOfficeProduct op where 1=1 ";
		
		TeePerson loginUser = (TeePerson) requestDatas.get(TeeConst.LOGIN_USER);
		loginUser = (TeePerson) simpleDaoSupport.get(TeePerson.class, loginUser.getUuid());
		if(!TeePersonService.checkIsAdminPriv(loginUser)){//如果不是管理员角色，则按照模块条件查询
			hql += " and exists (select 1 from op.category.officeDepository.admins admins where admins.uuid="+loginUser.getUuid()+")";
		}
		
		List<TeeOfficeProduct> products = simpleDaoSupport.pageFind(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		List<TeeOfficeProductModel> models = new ArrayList<TeeOfficeProductModel>();
		
		for(TeeOfficeProduct product:products){
			TeeOfficeProductModel m = new TeeOfficeProductModel();
			entityToModel(product, m);
			m.setDepositoryName(product.getCategory().getOfficeDepository().getDeposName());
			m.setCurStock(Integer.parseInt(""+simpleDaoSupport.count("select sum(os.regCount) from TeeOfficeStock os where os.product.sid="+product.getSid(), null)));
			models.add(m);
		}
		
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	/**
	 * 根据分类查询物品
	 * @param person
	 * @param catId
	 * @return
	 */
	public List<TeeOfficeProductModel> getProductWithPriv(TeePerson person,int catId,int regType){
		person = (TeePerson) simpleDaoSupport.get(TeePerson.class, person.getUuid());
		
		String hql = "from TeeOfficeProduct op where op.category.sid="+catId+" and " +
				"(exists (select 1 from op.regUsers regUsers where regUsers.uuid="+person.getUuid()+") or " +
				"exists (select 1 from op.regDepts regDepts where regDepts.uuid="+person.getDept().getUuid()+")) ";
		if(regType!=0){
			if(regType==3){
				hql+=" and op.regType="+2;
			}else{
				hql+=" and op.regType="+regType;
			}
		}
		
		List<TeeOfficeProductModel> models = new ArrayList<TeeOfficeProductModel>();
		List<TeeOfficeProduct> list = simpleDaoSupport.find(hql, null);
		for(TeeOfficeProduct op : list){
			TeeOfficeProductModel m = new TeeOfficeProductModel();
			m.setSid(op.getSid());
			m.setCurStock(Integer.parseInt(""+simpleDaoSupport.count("select sum(os.regCount) from TeeOfficeStock os where os.product.sid="+op.getSid(), null)));
			m.setProName(op.getProName());
			m.setRegType(op.getRegType());
			models.add(m);
		}
		
		return models;
	}
}
