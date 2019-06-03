package com.tianee.oa.subsys.salManage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.subsys.salManage.bean.TeeSalAccount;
import com.tianee.oa.subsys.salManage.bean.TeeSalItem;
import com.tianee.oa.subsys.salManage.dao.TeeSalAccountDao;
import com.tianee.oa.subsys.salManage.dao.TeeSalAccountPersonDao;
import com.tianee.oa.subsys.salManage.model.TeeSalAccountModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeSalAccountService  extends TeeBaseService{

	@Autowired
	TeeSalAccountDao accountDao;
	@Autowired
	TeeSalAccountPersonDao accountPersonDao;
	@Autowired
	TeePersonDao personDao;
	/**
	 * 新增或者更新
	 * @param map
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(Map map ,  TeeSalAccountModel model){
		TeeJson json = new TeeJson();
	
		if(model.getSid() > 0){
			TeeSalAccount account = accountDao.get(model.getSid());
			account.setRemark(model.getRemark());
			account.setAccountSort(model.getAccountSort());
			account.setAccountName(model.getAccountName());
			account.setAccountNo(model.getAccountNo());
			accountDao.update(account);
		}else{
			TeeSalAccount account = new TeeSalAccount();
			BeanUtils.copyProperties(model, account);
			accountDao.save(account);
			
			//创建实发工资项
			TeeSalItem item = new TeeSalItem();
			item.setAccountId(account.getSid());
			item.setSortNo(1000000);
			item.setItemColumn("finalPayAmount");
			item.setItemName("实发工资");
			item.setItemType(1);
			item.setNumberPoint(2);
			simpleDaoSupport.save(item);
		}
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据Id  获取对象
	 * @param model
	 * @return
	 */
	public TeeJson getById(TeeSalAccountModel model){
		TeeJson json = new TeeJson();
		TeeSalAccount account = accountDao.get(model.getSid());
		model = parseModel(account);
		
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 删除Id  获取对象
	 * @param model
	 * @return
	 */
	public TeeJson deleteById(TeeSalAccountModel model){
		TeeJson json = new TeeJson();
		TeeSalAccount account = accountDao.get(model.getSid());
		if(account != null){
			//删除账套人员
			accountPersonDao.delByAccountId(account.getSid());
			
			//删除账套项;
			simpleDaoSupport.executeUpdate("delete from TeeSalItem where accountId="+account.getSid(), null);
			
			//删除帐套下的工资数据
			simpleDaoSupport.executeUpdate("delete from TeeSalDataPerson where accountId="+account.getSid(), null);
			
			//删除帐套下的工资基础数据
			simpleDaoSupport.executeUpdate("delete from TeeHrSalData where accountId="+account.getSid(), null);
			
			//删除账套;
			accountDao.deleteByObj(account);
		}
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取所有账套
	 * @return
	 */
	public TeeJson getAllAccount(){
		TeeJson json = new TeeJson();
		List<TeeSalAccount> list = accountDao.getAllList();
		List<TeeSalAccountModel> modelList= new ArrayList<TeeSalAccountModel>();
		for (int i = 0; i < list.size(); i++) {
			TeeSalAccountModel model = new TeeSalAccountModel();
			model = parseModel(list.get(i));
			modelList.add(model);
			
		}
		json.setRtData(modelList);
		json.setRtState(true);
		return json;
	}
	/**
	 * 转换对象
	 * @param account
	 * @return
	 */
	public TeeSalAccountModel parseModel(TeeSalAccount account){
		TeeSalAccountModel model = new TeeSalAccountModel();
		if(account != null){
			BeanUtils.copyProperties(account, model);
		}
		return model;
	}
}
