package com.tianee.oa.subsys.crm.core.order.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.util.logging.resources.logging;

import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.chances.bean.TeeCrmChances;
import com.tianee.oa.subsys.crm.core.chances.model.TeeCrmChancesModel;
import com.tianee.oa.subsys.crm.core.contacts.bean.TeeCrmContacts;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomer;
import com.tianee.oa.subsys.crm.core.order.bean.TeeCrmOrder;
import com.tianee.oa.subsys.crm.core.order.bean.TeeOrderProducts;
import com.tianee.oa.subsys.crm.core.order.model.TeeCrmOrderModel;
import com.tianee.oa.subsys.crm.core.order.model.TeeOrderProductsModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeCrmOrderService extends TeeBaseService {
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private TeeOrderProductsService orderProductsService;
	
	@Autowired
	private TeeSmsManager smsManager;

	/**
	 * 添加或编辑销售订单
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request,TeeCrmOrderModel model) {
		TeeJson json = new TeeJson();
		TeeCrmOrder order = new TeeCrmOrder();
		TeePerson person =(TeePerson)request.getSession().getAttribute("LOGIN_USER");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cl = Calendar.getInstance();
		Calendar cl2 = Calendar.getInstance();
		Calendar ncl = Calendar.getInstance();
		if(model.getSid() > 0){
		    order  = (TeeCrmOrder) simpleDaoSupport.get(TeeCrmOrder.class, model.getSid());
			if(order != null){
				BeanUtils.copyProperties(model, order);
				if(!TeeUtility.isNullorEmpty(model.getCustomerId())){//客户
					TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, model.getCustomerId());
					order.setCustomer(customer);
				}
				if(!TeeUtility.isNullorEmpty(model.getChanceId())){//商机
					TeeCrmChances chances = (TeeCrmChances) simpleDaoSupport.get(TeeCrmChances.class, model.getChanceId());
					order.setChance(chances);
				}
				if(!TeeUtility.isNullorEmpty(model.getReceiverId())){//收货人
					TeeCrmContacts receivePerson = (TeeCrmContacts) simpleDaoSupport.get(TeeCrmContacts.class, model.getReceiverId());
					order.setReceivePerson(receivePerson);
				}
				order.setAddPerson(person); //创建人
				if(!TeeUtility.isNullorEmpty(model.getManagePersonId())){//负责人
					TeePerson managePerson = (TeePerson)personDao.get(model.getManagePersonId());
					order.setOrderManagePerson(managePerson);
				}
				if(!TeeUtility.isNullorEmpty(model.getOrderApprovalId())){//订单管理员
					TeePerson orderApproval = (TeePerson)personDao.get(model.getOrderApprovalId());
					order.setOrderApproval(orderApproval);
				}	
					
				if(!TeeUtility.isNullorEmpty(model.getOrderTimeDesc())){//下单日期
					try {
						cl.setTime(sdf.parse(model.getOrderTimeDesc()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				order.setOrderedTime(cl);
				if(!TeeUtility.isNullorEmpty(model.getTransactionsTimeDesc())){//交货日期
					try {
						cl2.setTime(sdf.parse(model.getTransactionsTimeDesc()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				order.setTransactionsTime(cl2);
				order.setCreateTime(order.getCreateTime());// 订单的创建时间
				order.setLastEditTime(ncl);//订单的最后一次修改日期
				order.setOrderStatus(1); //订单状态       1--确认中   /   2--已确认/   3--已驳回/  4--已作废
				simpleDaoSupport.update(order);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关信息！");
				return json;
			}
			
			/**
			 * 删除产品明细
			 */
			orderProductsService.delByOrderId(order.getSid());
			
		}else{//新建订单
			BeanUtils.copyProperties(model, order);
			if(!TeeUtility.isNullorEmpty(model.getCustomerId())){//客户
				TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, model.getCustomerId());
				order.setCustomer(customer);
			}
			if(!TeeUtility.isNullorEmpty(model.getChanceId())){//商机
				TeeCrmChances chances = (TeeCrmChances) simpleDaoSupport.get(TeeCrmChances.class, model.getChanceId());
				order.setChance(chances);
			}
			if(!TeeUtility.isNullorEmpty(model.getReceiverId())){//收货人
				TeeCrmContacts receivePerson = (TeeCrmContacts) simpleDaoSupport.get(TeeCrmContacts.class, model.getReceiverId());
				order.setReceivePerson(receivePerson);
			}
			order.setAddPerson(person); //创建人
			if(!TeeUtility.isNullorEmpty(model.getManagePersonId())){//负责人
				TeePerson managePerson = (TeePerson)personDao.get(model.getManagePersonId());
				order.setOrderManagePerson(managePerson);
			}
			if(!TeeUtility.isNullorEmpty(model.getOrderApprovalId())){//订单管理员
				TeePerson orderApproval = (TeePerson)personDao.get(model.getOrderApprovalId());
				order.setOrderApproval(orderApproval);
			}	
				
			if(!TeeUtility.isNullorEmpty(model.getOrderTimeDesc())){//下单日期
				try {
					cl.setTime(sdf.parse(model.getOrderTimeDesc()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			order.setOrderedTime(cl);
			if(!TeeUtility.isNullorEmpty(model.getTransactionsTimeDesc())){//交货日期
				try {
					cl2.setTime(sdf.parse(model.getTransactionsTimeDesc()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			order.setTransactionsTime(cl2);
			order.setCreateTime(ncl);// 订单的创建时间
			order.setLastEditTime(ncl);//订单的最后一次修改日期
			order.setOrderStatus(1); //订单状态       1--确认中   /   2--已确认/   3--已驳回/  4--已作废
			simpleDaoSupport.save(order);
		}
		
		/**
		 * 新建产品明细
		 */
		String productsListStr = request.getParameter("productsList");
		if(!("").equals(productsListStr)){
			List<TeeOrderProducts>  productsList= (List<TeeOrderProducts>)TeeJsonUtil.JsonStr2ObjectList(productsListStr , TeeOrderProducts.class);
			for (int i = 0; i < productsList.size(); i++) {
				TeeOrderProducts item = productsList.get(i);
				item.setOrder(order);
				simpleDaoSupport.save(item);
			}
		}
		
    	//发送消息
		if(!TeeUtility.isNullorEmpty(model.getOrderApprovalId())){
			Map requestData1 = new HashMap();
			requestData1.put("content", "您有一个订单需要确认，订单编号为："+order.getOrderNo()+"，请及时办理！");
			requestData1.put("userListIds", model.getOrderApprovalId());
			requestData1.put("moduleNo", "030");
			requestData1.put("remindUrl","/system/subsys/crm/core/order/indexApprove.jsp");
			smsManager.sendSms(requestData1, person);
			
		}

		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
	}

	/**
	 * 订单列表
	 * @param dm
	 * @param requestDatas
	 * @return
	 */
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm, Map requestDatas) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson person =(TeePerson)requestDatas.get("LOGIN_USER");
		String cusId = (String) requestDatas.get("cusId");
		String customerName = (String) requestDatas.get("customerName");//客户名称
		String customerId = (String) requestDatas.get("customerId"); //客户id
		String orderNo = (String) requestDatas.get("orderNo");//订单编号
		String orderTimeDesc =(String) requestDatas.get("orderTimeDesc"); //下单日期
		String orderStatusDesc =(String) requestDatas.get("orderStatusDesc");  //状态
		String orderStatus = (String) requestDatas.get("orderStatus");//订单管理员查看待确认订单列表订单状态
		String type = (String) requestDatas.get("type");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		List param = new ArrayList();
		String hql = " from TeeCrmOrder orders where 1=1";
		if(!TeeUtility.isNullorEmpty(customerId)&& !"0".equals(customerId)){
			TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, Integer.parseInt(customerId));
			if(!TeeUtility.isNullorEmpty(customer)){//客户
				hql+=" and orders.customer= ?";
				param.add(customer);
			}
		}
		if(!TeeUtility.isNullorEmpty(orderNo)){//订单编号
			hql += " and orders.orderNo like ?";
			param.add("%"+orderNo+"%");
		}
		if(!TeeUtility.isNullorEmpty(orderTimeDesc)){//
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sf.parse(orderTimeDesc));
				hql+=" and orders.orderedTime = ?";
				param.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(!TeeUtility.isNullorEmpty(orderStatusDesc)){
			if(!"0".equals(orderStatusDesc)){
				hql+=" and orders.orderStatus = ?";
				param.add(Integer.parseInt(orderStatusDesc));
			}else{
				hql +=" ";
			}
		}
		if(!TeeUtility.isNullorEmpty(type)){
			if("2".equals(type)){//type =2我负责的
				hql +=" and orders.orderManagePerson.uuid = "+person.getUuid();
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
			    		hql+=" and orders.orderManagePerson.uuid in ("+ underPersonIds+")";
			    		
			    	}else{
				    	hql +=" and orders.orderManagePerson.uuid is null";
				    }
			    }
			}
		}else{//  没有type代表所属客户下的订单
			if(!TeeUtility.isNullorEmpty(cusId)){
				hql =" from TeeCrmOrder orders,TeeCrmCustomer customer where orders.customer=customer and orders.customer.sid=? and ( orders.orderManagePerson.uuid="+person.getUuid()+" or orders.addPerson.uuid="+person.getUuid()+") ";
				param.add(Integer.parseInt(cusId));
			}else if(!TeeUtility.isNullorEmpty(orderStatus)&&!"0".equals(orderStatus)){//查询订单管理员待确认  已确认   已驳回列表
				hql =" from TeeCrmOrder orders where orders.orderApproval = ? and orders.orderStatus = ? ";
				param.add(person);
				param.add(Integer.parseInt(orderStatus));
			}else{//高级查询处查询所有订单
				hql =" from TeeCrmOrder orders where 1=1 ";
			}
		}
		List<TeeCrmOrder> orderList = simpleDaoSupport.pageFindByList("select orders "+hql+" order by orders.lastEditTime desc", (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		long total = simpleDaoSupport.countByList("select count(*) "+hql, param);
		List<TeeCrmOrderModel> models = new ArrayList<TeeCrmOrderModel>();
		for (TeeCrmOrder order : orderList) {
			TeeCrmOrderModel m = new TeeCrmOrderModel();
			m = parseModel(order);
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
	public TeeCrmOrderModel parseModel(TeeCrmOrder order){
		TeeCrmOrderModel model = new TeeCrmOrderModel();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(order == null){
			return null;
		}
		BeanUtils.copyProperties(order, model);
		if(order.getOrderedTime()!=null){
			model.setOrderTimeDesc(sf.format(order.getOrderedTime().getTime())); //下单日期
		} 
		if(order.getTransactionsTime()!=null){
			model.setTransactionsTimeDesc(sf.format(order.getTransactionsTime().getTime())); //交货日期
		} 
		if(order.getOrderStatus()==1){//订单状态
			model.setOrderStatusDesc("确认中");
		}else if(order.getOrderStatus()==2){
			model.setOrderStatusDesc("已确认");
		}else if(order.getOrderStatus()==3){
			model.setOrderStatusDesc("已驳回");
		}else if(order.getOrderStatus()==4){
			model.setOrderStatusDesc("已作废");
		}
		if(order.getCreateTime()!=null){
			model.setCreateTimeDesc(sdf.format(order.getCreateTime().getTime())); //创建时间
		}
		if(order.getCustomer()!=null){//客户
			model.setCustomerId(order.getCustomer().getSid());
			model.setCustomerName(order.getCustomer().getCustomerName());
		}
		if(order.getChance()!=null){//商机
			model.setChanceId(order.getChance().getSid());
			model.setChanceName(order.getChance().getChanceName());
		}
		if(order.getAddPerson()!=null){//创建人
			model.setAddPersonId(order.getAddPerson().getUuid());
			model.setAddPersonName(order.getAddPerson().getUserName());
		}
		if(order.getOrderManagePerson()!=null){//负责人
			model.setManagePersonId(order.getOrderManagePerson().getUuid());
			model.setManagePersonName(order.getOrderManagePerson().getUserName());
		}
		if(order.getReceivePerson()!=null){//收货人
			model.setReceiverId(order.getReceivePerson().getSid());
			model.setReceiverName(order.getReceivePerson().getContactName());
		}
		if(order.getOrderApproval()!=null){//订单管理员
			model.setOrderApprovalId(order.getOrderApproval().getUuid());
			model.setOrderApprovalName(order.getOrderApproval().getUserName());
		}
		if(order.getLastEditTime()!=null){
			model.setLastEditTimeDesc(sdf.format(order.getLastEditTime().getTime()));  //最后修改日期
		}
		
		//驳回原因
		if(order.getRejectReason()!=null){
			model.setRejectReason(order.getRejectReason());
		}else{
			model.setRejectReason(null);
		}
		
		
		return model;
	}

	/**
	 * 获取订单详情
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request,TeeCrmOrderModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeCrmOrder order = (TeeCrmOrder) simpleDaoSupport.get(TeeCrmOrder.class, model.getSid());
			if(order !=null){
				model = parseModel(order);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关订单！");
		return json;
	}

	/**
	 * 获取产品详情
	 * @param request
	 * @return
	 */
	public TeeJson getProductItem(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		List<TeeOrderProductsModel> itemList = orderProductsService.getProductItem(sid);
		json.setRtData(itemList);
		json.setRtState(true);
		json.setRtMsg("查询成功!");
		return json;
	}

	/**
	 * 删除订单
	 * @param request
	 * @return
	 */
	public TeeJson delById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sid = TeeStringUtil.getString(request.getParameter("sid"),"0");
		if(!TeeUtility.isNullorEmpty(sid)){
			TeeCrmOrder order = new TeeCrmOrder();
			order.setSid(Integer.parseInt(sid));
			//删除产品订单产品中间表
			orderProductsService.delByOrderId(order.getSid());
			//删除该订单关联的退货单
			simpleDaoSupport.executeNativeUpdate(" delete CRO.*,ROP.* from CRM_RETURN_ORDER CRO INNER JOIN RETURN_ORDER_PRODUCTS ROP ON CRO.SID = ROP.RETURN_ORDER_ID where CRO.ORDER_ID = ?", new Object[]{order.getSid()});
			//删除对应订单下的合同数据
			simpleDaoSupport.executeNativeUpdate(" delete from CRM_CONTRACTS where ORDER_ID = ?",  new Object[]{order.getSid()});
			//删除对应订单下的回款数据
			simpleDaoSupport.executeNativeUpdate(" delete from PAYBACK  where ORDER_ID = ? ", new Object[]{order.getSid()});
			//删除对应订单下的退款数据 
			simpleDaoSupport.executeNativeUpdate(" delete from DRAWBACK  where ORDER_ID = ? ", new Object[]{order.getSid()});
			//删除对应订单下的开票数据
			simpleDaoSupport.executeNativeUpdate(" delete from INVOICE  where ORDER_ID = ? ", new Object[]{order.getSid()});
			//删除订单
			simpleDaoSupport.deleteByObj(order);
			json.setRtState(true);
			json.setRtMsg("删除成功！");
		}
		return json;
	
	}

	/**
	 * 同意
	 * @param request
	 * @return
	 */
	public TeeJson agree(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String uuid=TeeStringUtil.getString(request.getParameter("sid"));
		if(!TeeUtility.isNullorEmpty(uuid)){
			int status=TeeStringUtil.getInteger(request.getParameter("orderStatus"), 0);
			Calendar cl = Calendar.getInstance();
			simpleDaoSupport.executeUpdate("update TeeCrmOrder set orderStatus = ?, lastEditTime = ? where sid =? ", new Object[]{status,cl,Integer.parseInt(uuid)});
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
			int status=TeeStringUtil.getInteger(request.getParameter("orderStatus"), 0);
			Calendar cl = Calendar.getInstance();
			simpleDaoSupport.executeUpdate("update TeeCrmOrder set rejectReason = ?, orderStatus = ?, lastEditTime = ? where sid =? ", new Object[]{reason,status,cl,Integer.parseInt(uuid)});
			json.setRtState(true);
			json.setRtMsg("操作成功！");
		}
		return json;
	}

	/**
	 * 查询商机下的所属订单
	 * @param sid
	 * @return
	 */
	public List<TeeCrmOrder> selectListByChanceId(int sid) {
		List<TeeCrmOrder> list = new ArrayList<TeeCrmOrder>();
	    if(!TeeUtility.isNullorEmpty(sid)&&0!=sid){
			String hql = "from TeeCrmOrder order where 1=1 and order.chance.sid="+sid;
			list= simpleDaoSupport.executeQuery(hql,null);
	    }
		return list;
	}

	/**
	 * 删除所属客户下的订单及关联数据
	 * @param parseInt
	 */
	public void deleteByCustomerId(int customerId) {
		List<TeeCrmOrder> list = new ArrayList<TeeCrmOrder>();
		String ids = "";
	    if(!TeeUtility.isNullorEmpty(customerId)&&0!=customerId){
			String hql = "from TeeCrmOrder order where 1=1 and order.customer.sid="+customerId;
			list= simpleDaoSupport.executeQuery(hql,null);
	    }
		if(!TeeUtility.isNullorEmpty(list)){
			for (TeeCrmOrder orders : list) {
				ids = orders.getSid()+",";
			}
		}
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids= ids.substring(0, ids.length() -1 );
			}
			
			//删除订单下的关联的退货单
			simpleDaoSupport.executeNativeUpdate(" delete CRO.*,ROP.* from CRM_RETURN_ORDER CRO INNER JOIN RETURN_ORDER_PRODUCTS ROP ON CRO.SID = ROP.RETURN_ORDER_ID where CRO.ORDER_ID in ("+ids+")", null);
			//删除订单下的关联合同
			simpleDaoSupport.executeUpdate("delete from TeeCrmContracts where order.sid in (" + ids + ")", null);
			//删除订单下的关联回款
			simpleDaoSupport.executeUpdate("delete from TeeCrmPayback where order.sid in (" + ids + ")", null);
			//删除订单下的关联退款
			simpleDaoSupport.executeUpdate("delete from TeeCrmDrawback where order.sid in (" + ids + ")", null);
			//删除订单下的关联开票信息
			simpleDaoSupport.executeUpdate("delete from TeeCrmInvoice where order.sid in (" + ids + ")", null);
			//删除订单
			simpleDaoSupport.executeUpdate("delete from TeeOrderProducts item where item.order.sid in (" + ids + ")", null);
			simpleDaoSupport.executeUpdate("delete from TeeCrmOrder where sid in (" + ids + ")", null);
		}
		
	}

	
	public List<TeeCrmOrderModel> selectOrders(HttpServletRequest request) {

		String cusId = TeeStringUtil.getString(request.getParameter("cusId"), "0");
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String hql ="select orders from TeeCrmOrder orders,TeeCrmCustomer customer where orders.customer=customer and orders.customer.sid=? and ( orders.orderManagePerson.uuid="+person.getUuid()+" or orders.addPerson.uuid="+person.getUuid()+") ";
		List<TeeCrmOrder> list = simpleDaoSupport.executeQuery(hql, new Object[]{Integer.parseInt(cusId)});
		List<TeeCrmOrderModel> models = new ArrayList<TeeCrmOrderModel>();
		for(TeeCrmOrder order:list){
			TeeCrmOrderModel m = new TeeCrmOrderModel();
			m=parseModel(order);	
			models.add(m);
		}

		return models;
	}

}
