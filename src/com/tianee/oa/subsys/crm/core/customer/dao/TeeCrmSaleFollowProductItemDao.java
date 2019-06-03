package com.tianee.oa.subsys.crm.core.customer.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmContactUser;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmSaleFollowProductItem;
import com.tianee.oa.subsys.crm.core.customer.model.TeeCrmContactUserModel;
import com.tianee.oa.subsys.crm.core.customer.model.TeeCrmSaleFollowProductItemModel;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("TeeCrmSaleFollowProductItemDao")
public class TeeCrmSaleFollowProductItemDao extends TeeBaseDao<TeeCrmSaleFollowProductItem>{
	/**
	 * @author nieyi
	 * @param productItem
	 */
	public void addSaleFollow(TeeCrmSaleFollowProductItem productItem) {
		save(productItem);
	}
	
	/**
	 * @author nieyi
	 * @param productItem
	 */
	public void updateSaleFollow(TeeCrmSaleFollowProductItem productItem) {
		update(productItem);
	}
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeCrmSaleFollowProductItem loadById(int id) {
		TeeCrmSaleFollowProductItem intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeCrmSaleFollowProductItem getById(int id) {
		TeeCrmSaleFollowProductItem intf = get(id);
		return intf;
	}
	
	
	/**
	 * @author nieyi
	 * @param id
	 */
	public void delById(int id) {
		delete(id);
	}
	
	/**
	 * @author nieyi
	 * @param ids
	 */
	public void delByIds(String ids){
		
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids= ids.substring(0, ids.length() -1 );
			}
			String hql = "delete from TeeCrmSaleFollowProductItem where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	
	/**
	 * 对象转换
	 * @author nieyi
	 * @param productItem
	 * @return
	 */
	public TeeCrmSaleFollowProductItemModel parseModel(TeeCrmSaleFollowProductItem productItem){
		TeeCrmSaleFollowProductItemModel model = new TeeCrmSaleFollowProductItemModel();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(productItem == null){
			return null;
		}
		BeanUtils.copyProperties(productItem, model);
		if(productItem.getSaleFollow()!=null){
			model.setSaleFollowId(productItem.getSaleFollow().getSid());
		}
		String unitsDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("PRODUCTS_UNITS_TYPE",productItem.getUnits());
		model.setUnitsDesc(unitsDesc);
		
		return model;
	}

	public List<TeeCrmSaleFollowProductItemModel> getProductItem(int saleFollowId) {
		List<TeeCrmSaleFollowProductItem> list = new ArrayList<TeeCrmSaleFollowProductItem>();
		String hql = "from TeeCrmSaleFollowProductItem item where 1=1 and item.saleFollow.sid="+saleFollowId;
		list=executeQuery(hql,null);
		List<TeeCrmSaleFollowProductItemModel> models = new ArrayList<TeeCrmSaleFollowProductItemModel>();
		for(TeeCrmSaleFollowProductItem productItem:list){
			TeeCrmSaleFollowProductItemModel m = new TeeCrmSaleFollowProductItemModel();
			m=parseModel(productItem);	
			models.add(m);
		}
		return models;
	}
	
	public void delBySaleFollowId(int saleFollowSid) {
		if(!TeeUtility.isNullorEmpty(saleFollowSid)){
			String hql = "delete from TeeCrmSaleFollowProductItem item where item.saleFollow.sid="+saleFollowSid;
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	
	
	/**
	 * 根据订单Id  删除相关订单产品明细
	 * @param customerInfo
	 * @return
	 */
	public long deleteBySaleFollowIds(String saleFollowIds){
		if(!TeeUtility.isNullorEmpty(saleFollowIds)){
			if(saleFollowIds.endsWith(",")){
				saleFollowIds= saleFollowIds.substring(0, saleFollowIds.length() -1 );
			}
			String hql = "delete from TeeCrmSaleFollowProductItem where saleFollow.sid in (" + saleFollowIds + ")"; 
			Object[] values = {};
			return deleteOrUpdateByQuery(hql, values);
		}
		return 0;
	}
}