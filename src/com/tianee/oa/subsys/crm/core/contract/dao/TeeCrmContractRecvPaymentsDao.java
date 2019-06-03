package com.tianee.oa.subsys.crm.core.contract.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.crm.core.contract.bean.TeeCrmContractRecvPayments;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;
@Repository("crmContractRecvPaymentsDao")
public class TeeCrmContractRecvPaymentsDao  extends TeeBaseDao<TeeCrmContractRecvPayments> {
	/**
	 * 根据合同 ById
	 * @author syl
	 * @date 2014-3-1
	 * @return
	 */
	public List<TeeCrmContractRecvPayments> getCrmProductsItemByContractId( int contractId) {
		Object[] values = {contractId};
	    String hql = " from TeeCrmContractRecvPayments where contract.sid = ?";
		List<TeeCrmContractRecvPayments> list = (List<TeeCrmContractRecvPayments>) executeQuery(hql,values);
		return list;
	}
		
	/**
	 * 删除 根据合同Id
	 * @param contractId
	 * @return
	 */
	public int delByContractId(int contractId){
		int count = 0;
		String hql = "delete from TeeCrmContractRecvPayments where contract.sid = ?";
		Object[] values = {contractId};
		count = deleteOrUpdateByQuery(hql, values);
		return count;
	}
	
	/**
	 * 根据合同Id 且  ids
	 * @param contractId
	 * @param ids
	 * @return
	 */
	public List<TeeCrmContractRecvPayments> getByContractIdAndIds(int contractId , String ids){
		int count = 0;
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length() -1);
		}
		String hql = " from TeeCrmContractRecvPayments where contract.sid = ? and sid not in (" + ids + ")";
		if(ids.equals("")){
			hql = " from TeeCrmContractRecvPayments where contract.sid = ?";
		}
		Object[] values = {contractId};
		return executeQuery(hql, values);
		//return count;
	}
	
	/**
	 * 根据 ids 删除
	 * @param contractId
	 * @param ids
	 * @return
	 */
	public int delByIds(String ids){
		int count = 0;
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length() -1);
		}
		if(ids.equals("")){
			return count;
		}
		String hql = "delete from TeeCrmContractRecvPayments where  sid in (" + ids + ")";
		Object[] values = {};
		count = deleteOrUpdateByQuery(hql, values);
		return count;
	}
	
	
	/**
	 * 批量设置回款
	 * @param ids
	 * @return
	 */
	public int batchRecvPaymentByIds(String ids){
		int count = 0;
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length() -1);
		}
		if(ids.equals("")){
			return count;
		}
		String hql = "update TeeCrmContractRecvPayments set recvPaymentFlag = '1' , recvDate= ?  where  sid in (" + ids + ")";
		Object[] values = {new Date()};
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
			String hql = "delete from TeeCrmContractRecvPayments where contract.sid in (" + contractIds + ")" ;
			count = deleteOrUpdateByQuery(hql, null);
		}
		return count;
	}
}
