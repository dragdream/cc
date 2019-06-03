package com.tianee.oa.subsys.crm.core.contract.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.crm.core.contract.bean.TeeCrmContractProductItem;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("crmContractProductItemDao")
public class TeeCrmContractProductItemDao extends TeeBaseDao<TeeCrmContractProductItem> {
	
	/**
	 * 根据合同 ById
	 * @author syl
	 * @date 2014-3-1
	 * @return
	 */
	public List<TeeCrmContractProductItem> getCrmProductsItemByContractId( int contractId) {
		Object[] values = {contractId};
	    String hql = " from TeeCrmContractProductItem where contract.sid = ?";
		List<TeeCrmContractProductItem> list = (List<TeeCrmContractProductItem>) executeQuery(hql,values);
		return list;
	}
	
	/**
	 * 删除 根据合同Id
	 * @param contractId
	 * @return
	 */
	public int delByContractId(int contractId){
		int count = 0;
		String hql = "delete from TeeCrmContractProductItem where contract.sid = ?";
		Object[] values = {contractId};
		count = deleteOrUpdateByQuery(hql, values);
		return count;
	}
	
	/**
	 * 根据合同Ids  删除所有产品明细表
	 * @param contractIds
	 * @return
	 */
	public int delByContractIds(String contractIds){
		int count = 0;
		if(!TeeUtility.isNullorEmpty(contractIds)){
			contractIds = contractIds.substring(0, contractIds.length() - 1);
			String hql = "delete from TeeCrmContractProductItem where contract.sid in (" + contractIds + ")" ;
			count = deleteOrUpdateByQuery(hql, null);
		}
		return count;
	}
	
	
}
