package com.tianee.oa.core.base.officeProducts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeCategory;
import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeDepository;
import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeProduct;
import com.tianee.oa.core.base.officeProducts.model.TeeOfficeDepositoryModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeOfficeDepositoryService extends TeeBaseService{
	
	public TeeOfficeDepository addDepositoryModel(TeeOfficeDepositoryModel depositoryModel){
		TeeOfficeDepository officeDepository = new TeeOfficeDepository();
		
		int admins [] = TeeStringUtil.parseIntegerArray(depositoryModel.getAdminsIds());
		int depts [] = TeeStringUtil.parseIntegerArray(depositoryModel.getDeptsIds());
		int operators [] = TeeStringUtil.parseIntegerArray(depositoryModel.getOperatorsIds());
		
		officeDepository.setDeposName(depositoryModel.getDeposName());
		//执行保存
		getSimpleDaoSupport().save(officeDepository);
		
		for(int adminsId:admins){
			officeDepository.getAdmins().add((TeePerson)simpleDaoSupport.load(TeePerson.class, adminsId));
		}
		
		for(int deptsId:depts){
			officeDepository.getDepts().add((TeeDepartment)simpleDaoSupport.load(TeeDepartment.class, deptsId));
		}
		
		for(int operatorsId:operators){
			officeDepository.getOperators().add((TeePerson)simpleDaoSupport.load(TeePerson.class, operatorsId));
		}
		
		
		return officeDepository;
	}
	
	public TeeOfficeDepository saveDepository(TeeOfficeDepository depository){
		getSimpleDaoSupport().save(depository);
		return depository;
	}
	
	public void delDepository(TeeOfficeDepository depository){
		//获取该库下的所有分类
		List<TeeOfficeCategory> categories = simpleDaoSupport.find("from TeeOfficeCategory where officeDepository.sid="+depository.getSid(), null);
		List<TeeOfficeProduct> products = null;
		for(TeeOfficeCategory category:categories){
			products = simpleDaoSupport.find("from TeeOfficeProduct where category.sid="+category.getSid(), null);
			//清除该用品下的单据、库存信息
			for(TeeOfficeProduct product:products){
				simpleDaoSupport.executeUpdate("delete from TeeOfficeStock where product.sid="+product.getSid(), null);
				simpleDaoSupport.executeUpdate("delete from TeeOfficeStockBill where product.sid="+product.getSid(), null);
				simpleDaoSupport.deleteByObj(product);
			}
			simpleDaoSupport.deleteByObj(category);
		}
		simpleDaoSupport.deleteByObj(depository);
	}
	
	public void updateDepositoryModel(TeeOfficeDepositoryModel depositoryModel){
		TeeOfficeDepository depository = (TeeOfficeDepository) simpleDaoSupport.get(TeeOfficeDepository.class, depositoryModel.getSid());
		
		int admins [] = TeeStringUtil.parseIntegerArray(depositoryModel.getAdminsIds());
		int depts [] = TeeStringUtil.parseIntegerArray(depositoryModel.getDeptsIds());
		int operators [] = TeeStringUtil.parseIntegerArray(depositoryModel.getOperatorsIds());
		
		depository.setDeposName(depositoryModel.getDeposName());
		depository.getAdmins().clear();
		depository.getDepts().clear();
		depository.getOperators().clear();
		
		for(int adminsId:admins){
			depository.getAdmins().add((TeePerson)simpleDaoSupport.load(TeePerson.class, adminsId));
		}
		
		for(int deptsId:depts){
			depository.getDepts().add((TeeDepartment)simpleDaoSupport.load(TeeDepartment.class, deptsId));
		}
		
		for(int operatorsId:operators){
			depository.getOperators().add((TeePerson)simpleDaoSupport.load(TeePerson.class, operatorsId));
		}
		
		updateDepository(depository);
	}
	
	public void updateDepository(TeeOfficeDepository depository){
		getSimpleDaoSupport().update(depository);
	}
	
	
	/**
	 * datagrid，而且根据人员权限来查询
	 * @param dm
	 * @param requestDatas
	 * @return
	 */
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		String hql = "from TeeOfficeDepository od";
		
		TeePerson person = (TeePerson) requestDatas.get(TeeConst.LOGIN_USER);
		//如果不是管理员的话，则进行权限校验
		if(!TeePersonService.checkIsAdminPriv(person)){
			hql += " where exists (select 1 from od.admins admins where admins.uuid="+person.getUuid()+")";
		}
		
		
		List<TeeOfficeDepository> list = simpleDaoSupport.pageFind(hql+" order by od.sid asc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		long count = simpleDaoSupport.count("select count(*) "+hql, null);
		
		List<TeeOfficeDepositoryModel> modelList = new ArrayList<TeeOfficeDepositoryModel>();
		
		Set<TeeDepartment> depts;
		Set<TeePerson> admins;
		Set<TeePerson> operators;
		StringBuffer sbIds = new StringBuffer("");
		StringBuffer sbNames = new StringBuffer("");
		
		for(TeeOfficeDepository od:list){
			TeeOfficeDepositoryModel model = new TeeOfficeDepositoryModel();
			BeanUtils.copyProperties(od, model);
			
			depts = od.getDepts();
			admins = od.getAdmins();
			operators = od.getOperators();
			
			//处理所属部门
			for(TeeDepartment dept:depts){
				sbIds.append(dept.getUuid()+",");
				sbNames.append(dept.getDeptName()+",");
			}
			model.setDeptsIds(sbIds.toString());
			model.setDeptsNames(sbNames.toString());
			
			sbIds.delete(0, sbIds.length());
			sbNames.delete(0, sbNames.length());
			
			//处理仓库管理员
			for(TeePerson admin:admins){
				sbIds.append(admin.getUuid()+",");
				sbNames.append(admin.getUserName()+",");
			}
			model.setAdminsIds(sbIds.toString());
			model.setAdminsNames(sbNames.toString());
			
			sbIds.delete(0, sbIds.length());
			sbNames.delete(0, sbNames.length());
			
			//处理物品调度员
			for(TeePerson operator:operators){
				sbIds.append(operator.getUuid()+",");
				sbNames.append(operator.getUserName()+",");
			}
			model.setOperatorsIds(sbIds.toString());
			model.setOperatorsNames(sbNames.toString());
			
			sbIds.delete(0, sbIds.length());
			sbNames.delete(0, sbNames.length());
			
			modelList.add(model);
		}
		
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		datagrid.setTotal(count);
		datagrid.setRows(modelList);
		
		return datagrid;
	}
	
	public TeeOfficeDepository getBySid(int sid){
		return (TeeOfficeDepository) simpleDaoSupport.get(TeeOfficeDepository.class, sid);
	}
	
	public TeeOfficeDepositoryModel getModelBySid(int sid){
		TeeOfficeDepositoryModel model = new TeeOfficeDepositoryModel();
		TeeOfficeDepository depository = (TeeOfficeDepository) simpleDaoSupport.get(TeeOfficeDepository.class, sid);
		if(depository==null){
			throw new TeeOperationException("不存在的办公用品库信息");
		}
		
		StringBuffer sbIds = new StringBuffer("");
		StringBuffer sbNames = new StringBuffer("");
	
		BeanUtils.copyProperties(depository, model);
		
		Set<TeeDepartment> depts = depository.getDepts();
		Set<TeePerson> admins = depository.getAdmins();
		Set<TeePerson> operators = depository.getOperators();
		
		//处理所属部门
		for(TeeDepartment dept:depts){
			sbIds.append(dept.getUuid()+",");
			sbNames.append(dept.getDeptName()+",");
		}
		model.setDeptsIds(sbIds.toString());
		model.setDeptsNames(sbNames.toString());
		
		sbIds.delete(0, sbIds.length());
		sbNames.delete(0, sbNames.length());
		
		//处理仓库管理员
		for(TeePerson admin:admins){
			sbIds.append(admin.getUuid()+",");
			sbNames.append(admin.getUserName()+",");
		}
		model.setAdminsIds(sbIds.toString());
		model.setAdminsNames(sbNames.toString());
		
		sbIds.delete(0, sbIds.length());
		sbNames.delete(0, sbNames.length());
		
		//处理物品调度员
		for(TeePerson operator:operators){
			sbIds.append(operator.getUuid()+",");
			sbNames.append(operator.getUserName()+",");
		}
		model.setOperatorsIds(sbIds.toString());
		model.setOperatorsNames(sbNames.toString());
		
		sbIds.delete(0, sbIds.length());
		sbNames.delete(0, sbNames.length());
		
		return model;
	}
	
	/**
	 * 获取库集合
	 * @param person
	 * @return
	 */
	public List<TeeOfficeDepositoryModel> getDeposListWithNoPriv(){
		String hql = "from TeeOfficeDepository od ";
		List<TeeOfficeDepository> list = simpleDaoSupport.find(hql, null);
		List<TeeOfficeDepositoryModel> models = new ArrayList<TeeOfficeDepositoryModel>();
		for(TeeOfficeDepository od:list){
			TeeOfficeDepositoryModel m = new TeeOfficeDepositoryModel();
			m.setSid(od.getSid());
			m.setDeposName(od.getDeposName());
			models.add(m);
		}
		
		return models;
	}
	
	/**
	 * 根据调度员权限获取库集合
	 * @param loginUser
	 * @return
	 */
	public List<TeeOfficeDepositoryModel> getDeposListByOperatePriv(TeePerson loginUser){
		String hql = "from TeeOfficeDepository od ";
		if(!TeePersonService.checkIsAdminPriv(loginUser)){
			hql += " where exists (select 1 from od.operators operators where operators.uuid="+loginUser.getUuid()+")";
		}
		List<TeeOfficeDepository> list = simpleDaoSupport.find(hql, null);
		List<TeeOfficeDepositoryModel> models = new ArrayList<TeeOfficeDepositoryModel>();
		for(TeeOfficeDepository od:list){
			TeeOfficeDepositoryModel m = new TeeOfficeDepositoryModel();
			m.setSid(od.getSid());
			m.setDeposName(od.getDeposName());
			models.add(m);
		}
		
		return models;
	}
	
}
