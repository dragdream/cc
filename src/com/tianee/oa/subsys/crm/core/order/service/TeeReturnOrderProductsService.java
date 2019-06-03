package com.tianee.oa.subsys.crm.core.order.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.crm.core.order.bean.TeeOrderProducts;
import com.tianee.oa.subsys.crm.core.order.bean.TeeReturnOrderProducts;
import com.tianee.oa.subsys.crm.core.order.model.TeeReturnOrderProductsModel;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeReturnOrderProductsService extends TeeBaseService{
	/**
	 * 根据退货单id获取产品详情
	 * @param sid
	 * @return
	 */
	public List<TeeReturnOrderProductsModel> getProductItem(int sid) {
		List<TeeReturnOrderProducts> list = new ArrayList<TeeReturnOrderProducts>();
		String hql = "from TeeReturnOrderProducts item where 1=1 and item.returnOrder.sid="+sid;
		list= simpleDaoSupport.executeQuery(hql,null);
		List<TeeReturnOrderProductsModel> models = new ArrayList<TeeReturnOrderProductsModel>();
		for(TeeReturnOrderProducts productItem:list){
			TeeReturnOrderProductsModel m = new TeeReturnOrderProductsModel();
			m=parseModel(productItem);	
			models.add(m);
		}
		return models;
	}
	
	
	/**
	 * 对象转换
	 * @author nieyi
	 * @param productItem
	 * @return
	 */
	public TeeReturnOrderProductsModel parseModel(TeeReturnOrderProducts productItem){
		TeeReturnOrderProductsModel model = new TeeReturnOrderProductsModel();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(productItem == null){
			return null;
		}
		BeanUtils.copyProperties(productItem, model);
		if(productItem.getReturnOrder()!=null){
			model.setReturnOrderId(productItem.getReturnOrder().getSid());
		}
		String unitsDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("PRODUCTS_UNITS_TYPE",productItem.getUnits());
		model.setUnitsDesc(unitsDesc);
		
		model.setUnitsName(unitsDesc);
		model.setProductsTypeName(model.getProductsModel());
		
		
		//取maxNum
		List<TeeOrderProducts> oList=simpleDaoSupport.executeQuery(" from  TeeOrderProducts  where order.sid=? and productsId=?  ", new Object[]{productItem.getReturnOrder().getOrder().getSid(),productItem.getProductsId()});
		if(oList!=null&&oList.size()>0){
			TeeOrderProducts p=oList.get(0);
			model.setMaxNum(p.getProductsNumber());
		}
		return model;
	}
	
	
	/**
	 * 删除
	 * @param sid
	 */
	public void delByOrderId(int sid) {
		if(!TeeUtility.isNullorEmpty(sid)){
			String hql = "delete from TeeReturnOrderProducts item where item.returnOrder.sid="+sid;
			simpleDaoSupport.deleteOrUpdateByQuery(hql, null);
		}
	}

	
	
    /**
     * 根据id主键获取详情
     * @param request
     * @return
     */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeReturnOrderProducts p=(TeeReturnOrderProducts) simpleDaoSupport.get(TeeReturnOrderProducts.class,sid);
		if(p!=null){
			TeeReturnOrderProductsModel model=parseModel(p);
			json.setRtState(true);
			json.setRtData(model);
		}
		return json;
	}

}
