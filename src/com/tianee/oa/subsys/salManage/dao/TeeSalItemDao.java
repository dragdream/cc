package com.tianee.oa.subsys.salManage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.salManage.bean.TeeHrSalData;
import com.tianee.oa.subsys.salManage.bean.TeeSalDataPerson;
import com.tianee.oa.subsys.salManage.bean.TeeSalItem;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 
 * @author CXT
 *
 */
@Repository("salItemDao")
public class TeeSalItemDao extends TeeBaseDao<TeeSalItem>{


	/**
	 * 获取所有去掉不显示的项目
	 * @return
	 */
	public List<TeeSalItem> salItemListByAccountId(int accountId ){
		Object[] values = {accountId};
		String hql = "from TeeSalItem where itemFlag = '0' and accountId = ?  order by sortNo asc"; 
		List<TeeSalItem> list = executeQuery(hql, values);
		return list;
	}
	/**
	 * 获取所有的项目
	 * @return
	 */
	public List<TeeSalItem> salAllItemListByAccountId(int accountId){
		Object[] values = {accountId};
		String hql = "from TeeSalItem where  accountId = ? order by sortNo asc"; 
		List<TeeSalItem> list = executeQuery(hql, values);
		return list;
	}
	
	/**
	 * 查询 by 类型
	 * @param type
	 * @return
	 */
	public List<TeeSalItem> salItemListByAccountIdAndType(int type , int accountId){
		Object[] values = {accountId , type};
		String hql = " from TeeSalItem  where accountId = ? and itemType = ? order by sortNo asc";
	
		List<TeeSalItem> list = executeQuery(hql, values);
		return list;
	}
	
	
	/**
	 * 查询 by 类型
	 * @param type
	 * @return
	 */
	public List<TeeSalItem> salItemListByType(int accountId , int type){
		Object[] values = {accountId};
		String hql = "from TeeSalItem item where  accountId = ? and  item.itemFlag = '0' and item.itemType = " + type+"  order by sortNo asc"; 
		List<TeeSalItem> list = executeQuery(hql, values);
		return list;
	}
	
	public void update(TeeSalItem item){
		Session session = this.getSession();
		session.merge(item);
	}
	
	

	/**
	 * 获取数据
	 * @return
	 */
	public long getCount(int accountId){
		String hql = "select count(sid) from TeeSalItem where accountId = " + accountId;
		long count = count(hql, null);
		return count;
	}
	
	/**
	 * 检查是否存在重复工资项
	 * @return
	 */
	public long checkExtisItemName(int accountId , TeeSalItem salItem){
		String hql = "select count(sid) from TeeSalItem where accountId = " + accountId;
		if(salItem.getSid() > 0){
			hql = hql + " and sid <> " + salItem.getSid();
		}
		hql = hql + " and  itemName = ?";
		long count = count(hql, new Object[]{salItem.getItemName()});
		return count;
	}
	/**
	 * 从salData中取itemColumn对应的值
	 * @param itemColumn
	 * @param salData
	 * @return
	 */
	public String getItemValue(String itemColumn, TeeSalDataPerson salData) {
		String itemValue="";
		try {
			String get = (itemColumn.charAt(0)+"").toUpperCase()+itemColumn.substring(1);
			itemValue = TeeJsonUtil.getValueByKey(salData.getClass().getDeclaredField(itemColumn), salData.getClass().getMethod("get"+get), salData, TeeSalDataPerson.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return itemValue;
	}

	/**
	 * 批量删除人员薪资
	 * @param sids
	 */
	public void delSalaryInfo(String sids) {
		if(!TeeUtility.isNullorEmpty(sids)){
			if(sids.endsWith(",")){
				sids= sids.substring(0, sids.length() -1 );
			}
			String hql = "delete from TeeSalDataPerson where sid in (" + sids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
}
