package com.tianee.oa.subsys.crm.core.order.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomer;
import com.tianee.oa.subsys.crm.core.order.bean.TeeCrmOrder;
import com.tianee.oa.subsys.crm.core.order.bean.TeeCrmReturnOrder;
import com.tianee.oa.subsys.crm.core.order.bean.TeeOrderProducts;
import com.tianee.oa.subsys.crm.core.order.bean.TeeReturnOrderProducts;
import com.tianee.oa.subsys.crm.core.order.model.TeeReturnOrderModel;
import com.tianee.oa.subsys.crm.core.order.model.TeeReturnOrderProductsModel;
import com.tianee.oa.subsys.crm.core.product.bean.TeeCrmProducts;
import com.tianee.oa.subsys.crm.core.product.service.TeeCrmProductsService;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeCrmReturnOrderService extends TeeBaseService {
	
	@Autowired
	private TeePersonService personServise;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private TeeCrmProductsService productsService;
	
	@Autowired
	private TeeReturnOrderProductsService returnOrderProductsService;
	
	@Autowired
	private TeeSmsManager smsManager;

	/**
	 * 新建或编辑退货单
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request,TeeReturnOrderModel model) {
		TeeJson json = new TeeJson();
		TeeCrmReturnOrder returnOrder = new TeeCrmReturnOrder();
		TeePerson person =(TeePerson)request.getSession().getAttribute("LOGIN_USER");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cl = Calendar.getInstance();
		Calendar ncl = Calendar.getInstance();

		if(model.getSid() > 0){
		    returnOrder  = (TeeCrmReturnOrder) simpleDaoSupport.get(TeeCrmReturnOrder.class, model.getSid());
			if(returnOrder != null){
				BeanUtils.copyProperties(model, returnOrder);
				if(!TeeUtility.isNullorEmpty(model.getCustomerId())){//客户
					TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, model.getCustomerId());
					returnOrder.setCustomer(customer);
				}
				if(!TeeUtility.isNullorEmpty(model.getOrderId())){//订单
					TeeCrmOrder order = (TeeCrmOrder) simpleDaoSupport.get(TeeCrmOrder.class, model.getOrderId());
					returnOrder.setOrder(order);
				}
				returnOrder.setAddPerson(person); //创建人
				if(!TeeUtility.isNullorEmpty(model.getManagePersonId())){//负责人
					TeePerson managePerson = (TeePerson)personDao.get(model.getManagePersonId());
					returnOrder.setRetOrderManagePerson(managePerson);
				}
				if(!TeeUtility.isNullorEmpty(model.getOrderApprovalId())){//订单管理员
					TeePerson orderApproval = (TeePerson)personDao.get(model.getOrderApprovalId());
					returnOrder.setOrderApproval(orderApproval);
				}	
					
				if(!TeeUtility.isNullorEmpty(model.getReturnTimeDesc())){//退单日期
					try {
						cl.setTime(sdf.parse(model.getReturnTimeDesc()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				returnOrder.setReturnTime(cl);
				returnOrder.setCreateTime(returnOrder.getCreateTime());// 退货订单的创建时间
				returnOrder.setLastEditTime(ncl);//最后一次修改日期
				returnOrder.setReturnOrderStatus(1); //订单状态       1--确认中   /   2--已确认/   3--已驳回/  
				simpleDaoSupport.update(returnOrder);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关退货单信息！");
				return json;
			}
			
			
			/**
			 * 删除产品明细
			 */
			returnOrderProductsService.delByOrderId(returnOrder.getSid());
			
		}else{//新建退货单
			BeanUtils.copyProperties(model, returnOrder);
			if(!TeeUtility.isNullorEmpty(model.getCustomerId())){//客户
				TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, model.getCustomerId());
				returnOrder.setCustomer(customer);
			}
			if(!TeeUtility.isNullorEmpty(model.getOrderId())){//订单
				TeeCrmOrder order = (TeeCrmOrder) simpleDaoSupport.get(TeeCrmOrder.class, model.getOrderId());
				returnOrder.setOrder(order);
			}
			returnOrder.setAddPerson(person); //创建人
			if(!TeeUtility.isNullorEmpty(model.getManagePersonId())){//负责人
				TeePerson managePerson = (TeePerson)personDao.get(model.getManagePersonId());
				returnOrder.setRetOrderManagePerson(managePerson);
			}
			if(!TeeUtility.isNullorEmpty(model.getOrderApprovalId())){//订单管理员
				TeePerson orderApproval = (TeePerson)personDao.get(model.getOrderApprovalId());
				returnOrder.setOrderApproval(orderApproval);
			}	
				
			if(!TeeUtility.isNullorEmpty(model.getReturnTimeDesc())){//退单日期
				try {
					cl.setTime(sdf.parse(model.getReturnTimeDesc()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			returnOrder.setReturnTime(cl);
			returnOrder.setCreateTime(ncl);// 退货订单的创建时间
			returnOrder.setLastEditTime(ncl);//最后一次修改日期
			returnOrder.setReturnOrderStatus(1); //订单状态       1--确认中   /   2--已确认/   3--已驳回/  
			simpleDaoSupport.save(returnOrder);
		}
		
		/**
		 * 新建产品明细
		 */
		String productsListStr = request.getParameter("productsList");
		if(!("").equals(productsListStr)){
			List<TeeReturnOrderProducts>  productsList= (List<TeeReturnOrderProducts>)TeeJsonUtil.JsonStr2ObjectList(productsListStr , TeeReturnOrderProducts.class);
			for (int i = 0; i < productsList.size(); i++) {
				TeeReturnOrderProducts item = productsList.get(i);
				item.setReturnOrder(returnOrder);
				simpleDaoSupport.save(item);
			}
		}
		
	  	//发送消息
			if(!TeeUtility.isNullorEmpty(model.getOrderApprovalId())){
				Map requestData1 = new HashMap();
				requestData1.put("content", "您有一个退货单需要确认，退货单编号为："+returnOrder.getReturnOrderNo()+"，请及时办理！");
				requestData1.put("userListIds", model.getOrderApprovalId());
				requestData1.put("moduleNo", "030");
				requestData1.put("remindUrl","/system/subsys/crm/core/returnOrder/indexApprove.jsp");
				smsManager.sendSms(requestData1, person);
				
			}

		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
	}

	/**
	 * 退货单列表
	 * @param dm
	 * @param requestDatas
	 * @return
	 */
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm, Map requestDatas) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson person =(TeePerson)requestDatas.get("LOGIN_USER");
		String cusId = (String) requestDatas.get("cusId");
		String returnOrderNo = (String) requestDatas.get("returnOrderNo");//退货单号
		String customerName = (String) requestDatas.get("customerName");//客户名称
		String customerId = (String) requestDatas.get("customerId"); //客户id
		String orderNo = (String) requestDatas.get("orderNo");//订单编号
		String orderId = (String) requestDatas.get("orderId");//订单编号
		String returnTimeDesc =(String) requestDatas.get("returnTimeDesc"); //退货单日期
		String returnReason = (String) requestDatas.get("returnReason");//退货原因
		String returnOrderStatusDesc =(String) requestDatas.get("returnOrderStatusDesc");  //状态
		String type = (String) requestDatas.get("type");
		String returnOrderStatus = (String) requestDatas.get("returnOrderStatus");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		List param = new ArrayList();
		String hql = " from TeeCrmReturnOrder returnOrders where 1=1";
		if(!TeeUtility.isNullorEmpty(returnOrderNo)){//退货单编号
			hql += " and returnOrders.returnOrderNo like ?";
			param.add("%"+returnOrderNo+"%");
		}
		if(!TeeUtility.isNullorEmpty(customerId)&& !"0".equals(customerId)){
			TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, Integer.parseInt(customerId));
			if(!TeeUtility.isNullorEmpty(customer)){//客户
				hql+=" and returnOrders.customer= ?";
				param.add(customer);
			}
		}
		if(!TeeUtility.isNullorEmpty(orderId)&& !"0".equals(orderId)){
			TeeCrmOrder order = (TeeCrmOrder) simpleDaoSupport.get(TeeCrmOrder.class, Integer.parseInt(orderId));
			if(!TeeUtility.isNullorEmpty(order)){//订单
				hql+=" and returnOrders.order= ?";
				param.add(order);
			}
		}
		if(!TeeUtility.isNullorEmpty(returnReason) && !"0".equals(returnReason)){//退货原因
			hql += " and returnOrders.returnReason= ?";
			param.add(returnReason);
		}
		if(!TeeUtility.isNullorEmpty(returnTimeDesc)){//退单日期
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sf.parse(returnTimeDesc));
				hql+=" and returnOrders.returnTime = ?";
				param.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(!TeeUtility.isNullorEmpty(returnOrderStatusDesc)){
			if(!"0".equals(returnOrderStatusDesc)){
				hql+=" and returnOrders.returnOrderStatus = ?";
				param.add(Integer.parseInt(returnOrderStatusDesc));
			}else{
				hql +=" ";
			}
		}
		if(!TeeUtility.isNullorEmpty(type)){
			if("2".equals(type)){//type =2我负责的
				hql +=" and returnOrders.retOrderManagePerson.uuid = "+person.getUuid()+")";
			}else if("1".equals(type)){//type=1 代表全部
				hql +="";
			}else if("3".equals(type)){//type=3代表我下属负责的
			    List<TeePerson> underPersonList = personService.getUnderlines(person.getUuid());
			    String underPersonIds = "";
			    if(!TeeUtility.isNullorEmpty(underPersonList)){
			    	for (TeePerson teePerson : underPersonList) {
			    		underPersonIds+=teePerson.getUuid()+",";
					}
			    	if(!TeeUtility.isNullorEmpty(underPersonIds)){
			    		if(underPersonIds.endsWith(",")){
			    			underPersonIds=underPersonIds.substring(0, underPersonIds.length()-1);
			    		}
			    		hql+=" and returnOrders.retOrderManagePerson.uuid in ("+ underPersonIds+")";
			    		
			    	}else{
				    	hql +=" and returnOrders.retOrderManagePerson.uuid is null";
				    }
			    }
			}
		}else{//  没有type代表所属客户下的退货单
			if(!TeeUtility.isNullorEmpty(returnOrderStatus)&&!"0".equals(returnOrderStatus)){//查询订单管理员待确认  已确认   已驳回列表
				hql ="from TeeCrmReturnOrder returnOrders where returnOrders.orderApproval = ? and returnOrders.returnOrderStatus = ? order by returnOrders.lastEditTime desc";
				param.add(person);
				param.add(Integer.parseInt(returnOrderStatus));
			}else{
				hql ="from TeeCrmReturnOrder returnOrders,TeeCrmCustomer customer where returnOrders.customer=customer and returnOrders.customer.sid=? and ( returnOrders.retOrderManagePerson.uuid="+person.getUuid()+" or returnOrders.addPerson.uuid="+person.getUuid()+") order by returnOrders.lastEditTime desc";
				param.add(Integer.parseInt(cusId));
			}
			
		}
		List<TeeCrmReturnOrder> orderList = simpleDaoSupport.pageFindByList("select returnOrders "+hql+" order by returnOrders.lastEditTime desc", (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		long total = simpleDaoSupport.countByList("select count(*) "+hql, param);
		List<TeeReturnOrderModel> models = new ArrayList<TeeReturnOrderModel>();
		for (TeeCrmReturnOrder returnOrder : orderList) {
			TeeReturnOrderModel m = new TeeReturnOrderModel();
			m = parseModel(returnOrder);
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	/**
	 * 对象转换
	 * @author nieyi
	 * @param contactUser
	 * @return
	 */
	public TeeReturnOrderModel parseModel(TeeCrmReturnOrder returnOrder){
		TeeReturnOrderModel model = new TeeReturnOrderModel();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(returnOrder == null){
			return null;
		}
		BeanUtils.copyProperties(returnOrder, model);
		if(returnOrder.getReturnTime()!=null){//退单日期
			model.setReturnTimeDesc(sf.format(returnOrder.getReturnTime().getTime()));
		}
		if(returnOrder.getReturnOrderStatus()==1){//退货单状态
			model.setReturnOrderStatusDesc("确认中");
		}else if(returnOrder.getReturnOrderStatus()==2){
			model.setReturnOrderStatusDesc("已确认");
		}else if(returnOrder.getReturnOrderStatus()==3){
			model.setReturnOrderStatusDesc("已驳回");
		}
		if(returnOrder.getCreateTime()!=null){
			model.setCreateTimeDesc(sdf.format(returnOrder.getCreateTime().getTime())); //创建时间
		}
		if(returnOrder.getCustomer()!=null){//客户
			model.setCustomerId(returnOrder.getCustomer().getSid());
			model.setCustomerName(returnOrder.getCustomer().getCustomerName());
		}
		if(returnOrder.getOrder()!=null){//订单
			model.setOrderId(returnOrder.getOrder().getSid());
			model.setOrderNo(returnOrder.getOrder().getOrderNo());
		}
		if(returnOrder.getAddPerson()!=null){//创建人
			model.setAddPersonId(returnOrder.getAddPerson().getUuid());
			model.setAddPersonName(returnOrder.getAddPerson().getUserName());
		}
		if(returnOrder.getRetOrderManagePerson()!=null){//负责人
			model.setManagePersonId(returnOrder.getRetOrderManagePerson().getUuid());
			model.setManagePersonName(returnOrder.getRetOrderManagePerson().getUserName());
		}
		if(returnOrder.getOrderApproval()!=null){//订单管理员
			model.setOrderApprovalId(returnOrder.getOrderApproval().getUuid());
			model.setOrderApprovalName(returnOrder.getOrderApproval().getUserName());
		}
		if(returnOrder.getLastEditTime()!=null){
			model.setLastEditTimeDesc(sdf.format(returnOrder.getLastEditTime().getTime()));  //最后修改日期
		}
		//退货原因
		String returnReasonDesc=TeeCrmCodeManager.getChildSysCodeNameCodeNo("RETURN_REASON", model.getReturnReason());
		model.setReturnReasonDesc(returnReasonDesc);
		
		//驳回原因
		if(returnOrder.getRejectReason()!=null){
			model.setRejectReason(returnOrder.getRejectReason());
		}else{
			model.setRejectReason(null);
		}
		
		//获取相关联的退货产品信息
		List<TeeReturnOrderProducts> pList=simpleDaoSupport.executeQuery(" from TeeReturnOrderProducts where returnOrder.sid=? ", new Object[]{returnOrder.getSid()});
		String returnProductsIds="";
		if(pList!=null&&pList.size()>0){
			for (TeeReturnOrderProducts teeReturnOrderProducts : pList) {
				returnProductsIds+=teeReturnOrderProducts.getSid()+",";
			}
		}
		
		model.setReturnProductsIds(returnProductsIds);
		return model;
	}
	
	/**
	 * 获取产品详情
	 * @param request
	 * @return
	 */
	public TeeJson getProductItem(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		List<TeeReturnOrderProductsModel> itemList = returnOrderProductsService.getProductItem(sid);
		json.setRtData(itemList);
		json.setRtState(true);
		json.setRtMsg("查询成功!");
		return json;
	}

/*
	public TeeJson getProductItem(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		List<Map> list = new ArrayList();
		List productsList = new ArrayList();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//退货单id
		int orderId = TeeStringUtil.getInteger(request.getParameter("orderId"), 0);//订单id
		//获取某退货单下的退货产品
		String sql = "select CP.SID, from RETORDER_PRODUCTS RP,CRM_PRODUCTS CP where RP.PRODUCTS_ID=CP.SID and RP.RETURN_ORDER_ID= ?";
		list = simpleDaoSupport.executeNativeQuery(sql, new Object[]{sid}, 0, Integer.MAX_VALUE);
		//获取退货单所选的订单下的订单产品实体
		List<TeeOrderProductsModel> itemList = itemList = orderProductsService.getProductItem(orderId);
		if(list!=null){
			for (int i = 0; i < list.size(); i++) {
				int productsId = (Integer) list.get(i).get("SID");
				TeeCrmProducts products = (TeeCrmProducts) simpleDaoSupport.get(TeeCrmProducts.class,productsId);
				productsList.add(products);
			}
		}
		json.setRtData(itemList);
		json.setRtState(true);
		json.setRtMsg("查询成功!");
		return json;
	}
*/
	/**
	 * 获取退货单详情
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request,TeeReturnOrderModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeCrmReturnOrder returnOrder = (TeeCrmReturnOrder) simpleDaoSupport.get(TeeCrmReturnOrder.class, model.getSid());
			if(returnOrder !=null){
				model = parseModel(returnOrder);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关退货单！");
		return json;
	}

	/**
	 * 同意退货单
	 * @param request
	 * @return
	 */
	public TeeJson agree(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String uuid=TeeStringUtil.getString(request.getParameter("sid"));
		if(!TeeUtility.isNullorEmpty(uuid)){
			int status=TeeStringUtil.getInteger(request.getParameter("returnOrderStatus"), 0);
			Calendar cl = Calendar.getInstance();
			simpleDaoSupport.executeUpdate("update TeeCrmReturnOrder set returnOrderStatus = ?, lastEditTime = ? where sid =? ", new Object[]{status,cl,Integer.parseInt(uuid)});
			json.setRtState(true);
			json.setRtMsg("操作成功！");
		}
		return json;
	}

	/**
	 * 驳回
	 * @param request
	 * @return
	 */
	public TeeJson reject(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String uuid=TeeStringUtil.getString(request.getParameter("sid"));
		String reason = TeeStringUtil.getString(request.getParameter("rejectReason"),  null);
		if(!TeeUtility.isNullorEmpty(uuid)){
			int status=TeeStringUtil.getInteger(request.getParameter("returnOrderStatus"), 0);
			Calendar cl = Calendar.getInstance();
			simpleDaoSupport.executeUpdate("update TeeCrmReturnOrder set rejectReason = ?, returnOrderStatus = ?, lastEditTime = ? where sid =? ", new Object[]{reason,status,cl,Integer.parseInt(uuid)});
			json.setRtState(true);
			json.setRtMsg("操作成功！");
		}
		return json;
	}
	
	
	/**
	 * 删除退货单
	 * @param request
	 * @return
	 */
	public TeeJson delById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sid = TeeStringUtil.getString(request.getParameter("sid"),"0");
		if(!TeeUtility.isNullorEmpty(sid)){
			TeeCrmReturnOrder returnOrder = new TeeCrmReturnOrder();
			returnOrder.setSid(Integer.parseInt(sid));
			simpleDaoSupport.deleteByObj(returnOrder);
			//删除退货单产品中间表
			returnOrderProductsService.delByOrderId(returnOrder.getSid());
			json.setRtState(true);
			json.setRtMsg("删除成功！");
		}
		return json;
	
	}
	

}
