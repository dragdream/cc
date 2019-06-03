package com.tianee.oa.subsys.crm.core.contract.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.crm.core.contract.bean.TeeCrmContract;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("teeCrmContractDao")
public class TeeCrmContractDao extends TeeBaseDao<TeeCrmContract>{
	/**
	 * 获取所有产品 合同
	 * @author syl
	 * @date 2014-3-1
	 * @return
	 */
	public List<TeeCrmContract> getCrmContract() {
	    String hql = " from TeeCrmContract ";
		List<TeeCrmContract> list = (List<TeeCrmContract>) executeQuery(hql,null);
		return list;
	}
	
	/**
	 * 删除 byIds
	 * @return
	 */
	public int deleteContract (String sid ){
		int count = 0;
		sid = TeeStringUtil.getString(sid);
		if(TeeUtility.isNullorEmpty(sid)){
			return count;
		}
		if(sid.endsWith(",")){
			sid = sid.substring(0, sid.length() - 1);
		}
		String hql = "delete from TeeCrmContract where sid in(" + sid + ")";
		count = deleteOrUpdateByQuery(hql, null);
		return count;
	}
	/**
	 * 根据Ids 查询客户合同	
	 * @param sid
	 * @return
	 */
	public List<TeeCrmContract> getContractByIds (String sid ){
		List<TeeCrmContract> list = new ArrayList<TeeCrmContract>();
		sid = TeeStringUtil.getString(sid);
		if(TeeUtility.isNullorEmpty(sid)){
			return list;
		}
		if(sid.endsWith(",")){
			sid = sid.substring(0, sid.length() - 1);
		}
		String hql = " from TeeCrmContract where sid in(" + sid + ")";
		list = executeQuery(hql, null);
		return list;
	}
		
	
	/*
	 * 合同年汇总统计 
	 */
	public List<TeeCrmContract> statisticsYearCollect(int year , TeePerson person){
		List<TeeCrmContract> list = new ArrayList<TeeCrmContract>();
		Object[] values = {year};
		String hql = "select * from TeeCrmContract where year(contractSignDate) = ? ";
		list = executeQuery(hql, values);
		return list;
	}

	
	/**
	 * 根据客户Ids  得到所有合同Ids
	 * @param customerIds
	 * @return
	 */
	public String getContractIdsByCustomerIds(String customerIds){
		StringBuffer sb = new StringBuffer();
		if(!TeeUtility.isNullorEmpty(customerIds)){
			if(customerIds.endsWith(",")){
				customerIds= customerIds.substring(0, customerIds.length() -1 );
			}
			String hql = " from TeeCrmContract where customerInfo.sid in (" + customerIds + ")"; 
			Object[] values = {};
			List<TeeCrmContract> list = executeQuery(hql, values);
			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i).getSid() + ","); 
			}
		}
		return sb.toString();
	}
}
