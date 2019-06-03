package com.tianee.oa.subsys.crm.core.base.dao;

import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.crm.core.base.bean.TeeCrmAfterSaleServ;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("crmAfterSaleServDao")
public class TeeCrmAfterSaleServDao extends TeeBaseDao<TeeCrmAfterSaleServ> {

	/**
	 * @function: 删除数据
	 * @author: wyw
	 * @data: 2014年8月30日
	 * @param ids
	 *            void
	 */
	public void delByIds(String ids) {

		if (!TeeUtility.isNullorEmpty(ids)) {
			if (ids.endsWith(",")) {
				ids = ids.substring(0, ids.length() - 1);
			}
			String hql = "delete from TeeCrmAfterSaleServ where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	
	/**
	 * 根据客户Ids  删除相关客户售后服务表
	 * @param contractIds
	 * @return
	 */
	public int delByCoustomerIds(String coustomerIds){
		int count = 0;
		if(!TeeUtility.isNullorEmpty(coustomerIds)){
			coustomerIds = coustomerIds.substring(0, coustomerIds.length() - 1);
			String hql = "delete from TeeCrmAfterSaleServ where customer.sid in (" + coustomerIds + ")" ;
			count = deleteOrUpdateByQuery(hql, null);
		}
		return count;
	}

}
