package com.tianee.oa.subsys.crm.core.order.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.crm.core.order.bean.TeeCrmOrder;
import com.tianee.oa.subsys.crm.core.order.bean.TeeOrderProducts;
import com.tianee.oa.subsys.crm.core.order.model.TeeOrderProductsModel;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeOrderProductsService extends TeeBaseService{
	
	/**
	 * 根据订单id获取产品详情
	 * @param sid
	 * @return
	 */
	public List<TeeOrderProductsModel> getProductItem(int sid) {
		List<TeeOrderProducts> list = new ArrayList<TeeOrderProducts>();
		String hql = "from TeeOrderProducts item where 1=1 and item.order.sid="+sid;
		list= simpleDaoSupport.executeQuery(hql,null);
		List<TeeOrderProductsModel> models = new ArrayList<TeeOrderProductsModel>();
		for(TeeOrderProducts productItem:list){
			TeeOrderProductsModel m = new TeeOrderProductsModel();
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
	public TeeOrderProductsModel parseModel(TeeOrderProducts productItem){
		TeeOrderProductsModel model = new TeeOrderProductsModel();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(productItem == null){
			return null;
		}
		BeanUtils.copyProperties(productItem, model);
		if(productItem.getOrder()!=null){
			model.setOrderId(productItem.getOrder().getSid());
		}
		String unitsDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("PRODUCTS_UNITS_TYPE",productItem.getUnits());
		model.setUnitsDesc(unitsDesc);
		model.setUnitsName(unitsDesc);
		model.setProductsTypeName(model.getProductsModel());
		return model;
	}
	
	/**
	 * 删除
	 * @param sid
	 */
	public void delByOrderId(int sid) {
		if(!TeeUtility.isNullorEmpty(sid)){
			String hql = "delete from TeeOrderProducts item where item.order.sid="+sid;
			simpleDaoSupport.deleteOrUpdateByQuery(hql, null);
		}
	}

	/**
	 * 获取订单下的产品信息
	 * @param dm
	 * @param requestDatas
	 * @return
	 */
	public TeeEasyuiDataGridJson getOrderProductsInfoList(TeeDataGridModel dm,Map requestDatas) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson person =(TeePerson)requestDatas.get("LOGIN_USER");
		String orderId = (String) requestDatas.get("sid");
		
		String productsName=TeeStringUtil.getString(requestDatas.get("productsName"));
		
		TeeCrmOrder order= null;
		if(!TeeUtility.isNullorEmpty(orderId)){
			order = (TeeCrmOrder) simpleDaoSupport.get(TeeCrmOrder.class, Integer.parseInt(orderId));
		}
		List param = new ArrayList();
		String hql = "from TeeOrderProducts item where 1=1 and item.order.sid= ?";
		param.add(Integer.parseInt(orderId));
		
		if(!TeeUtility.isNullorEmpty(productsName)){
			hql+=" and productsName like ? ";
			param.add("%"+productsName+"%");
		}
		
		List<TeeOrderProducts> infos = simpleDaoSupport.pageFindByList("select item "+hql+" order by item.sid desc", (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		long total = simpleDaoSupport.countByList("select count(*) "+hql, param);
		List<TeeOrderProductsModel> models = new ArrayList<TeeOrderProductsModel>();
		for(TeeOrderProducts op:infos){
			TeeOrderProductsModel m = new TeeOrderProductsModel();
			m=parseModel(op);	
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}


	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoById(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeOrderProducts p=(TeeOrderProducts) simpleDaoSupport.get(TeeOrderProducts.class,sid);
		if(p!=null){
			TeeOrderProductsModel model=parseModel(p);
			json.setRtState(true);
			json.setRtData(model);
		}
		return json;
	}


}
