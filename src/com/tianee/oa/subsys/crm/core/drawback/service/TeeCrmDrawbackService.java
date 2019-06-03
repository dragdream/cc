package com.tianee.oa.subsys.crm.core.drawback.service;

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

import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomer;
import com.tianee.oa.subsys.crm.core.drawback.bean.TeeCrmDrawback;
import com.tianee.oa.subsys.crm.core.drawback.model.TeeCrmDrawbackModel;
import com.tianee.oa.subsys.crm.core.order.bean.TeeCrmOrder;
import com.tianee.oa.subsys.crm.core.payback.bean.TeeCrmPayback;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeCrmDrawbackService extends TeeBaseService{
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeAttachmentDao attachmentDao;
	
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private TeeSmsManager smsManager;

	/**
	 * 添加或编辑退款
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeCrmDrawbackModel model) {
		TeeJson json = new TeeJson();
		TeeCrmDrawback drawback = new TeeCrmDrawback();
		TeePerson person =(TeePerson)request.getSession().getAttribute("LOGIN_USER");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cl = Calendar.getInstance();
		Calendar ncl = Calendar.getInstance();

		if(model.getSid() > 0){
			drawback  = (TeeCrmDrawback) simpleDaoSupport.get(TeeCrmDrawback.class, model.getSid());
			if(drawback != null){
				BeanUtils.copyProperties(model, drawback);
				if(!TeeUtility.isNullorEmpty(model.getCustomerId())){//客户
					TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, model.getCustomerId());
					drawback.setCustomer(customer);
				}
				if(!TeeUtility.isNullorEmpty(model.getOrderId())){//订单
					TeeCrmOrder order = (TeeCrmOrder) simpleDaoSupport.get(TeeCrmOrder.class, model.getOrderId());
					drawback.setOrder(order);
				}
				drawback.setCreateUser(person); //创建人
				if(!TeeUtility.isNullorEmpty(model.getManagePersonId())){//负责人
					TeePerson responsibleUser = (TeePerson)personDao.get(model.getManagePersonId());
					drawback.setResponsibleUser(responsibleUser);
				}
				if(!TeeUtility.isNullorEmpty(model.getRefundFinancialId())){//退款财务
					TeePerson RefundFinancial = (TeePerson)personDao.get(model.getRefundFinancialId());
					drawback.setRefundFinance(RefundFinancial);
				}	
				if(!TeeUtility.isNullorEmpty(model.getDrawbackTimeDesc())){//退款日期
					try {
						cl.setTime(sdf.parse(model.getDrawbackTimeDesc()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				drawback.setDrawbackTime(cl);
				drawback.setCreateTime(drawback.getCreateTime());//退款创建时间
				drawback.setLastEditTime(ncl);//退款的最后一次修改日期
				drawback.setDrawbackStatus(1); //退款状态    1-未退款   2--已退款    3--已驳回
				simpleDaoSupport.update(drawback);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到相关退款信息！");
				return json;
			}
		}else{//新建退款
			BeanUtils.copyProperties(model, drawback);
			if(!TeeUtility.isNullorEmpty(model.getCustomerId())){//客户
				TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, model.getCustomerId());
				drawback.setCustomer(customer);
			}
			if(!TeeUtility.isNullorEmpty(model.getOrderId())){//订单
				TeeCrmOrder order = (TeeCrmOrder) simpleDaoSupport.get(TeeCrmOrder.class, model.getOrderId());
				drawback.setOrder(order);
			}
			drawback.setCreateUser(person); //创建人
			if(!TeeUtility.isNullorEmpty(model.getManagePersonId())){//负责人
				TeePerson responsibleUser = (TeePerson)personDao.get(model.getManagePersonId());
				drawback.setResponsibleUser(responsibleUser);
			}
			if(!TeeUtility.isNullorEmpty(model.getRefundFinancialId())){//退款财务
				TeePerson RefundFinancial = (TeePerson)personDao.get(model.getRefundFinancialId());
				drawback.setRefundFinance(RefundFinancial);
			}	
			if(!TeeUtility.isNullorEmpty(model.getDrawbackTimeDesc())){//退款日期
				try {
					cl.setTime(sdf.parse(model.getDrawbackTimeDesc()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			drawback.setDrawbackTime(cl);
			drawback.setCreateTime(ncl);//退款创建时间
			drawback.setLastEditTime(ncl);//退款的最后一次修改日期
			drawback.setDrawbackStatus(1); //退款状态    1-未退款   2--已退款    3--已驳回
			simpleDaoSupport.save(drawback);
			
			
			//发送消息
			if(!TeeUtility.isNullorEmpty(model.getRefundFinancialId())){
				Map requestData1 = new HashMap();
				requestData1.put("content", "您有一个退款需要确认，退款编号为："+drawback.getDrawbackNo()+"，请及时办理！");
				requestData1.put("userListIds", model.getRefundFinancialId());
				requestData1.put("moduleNo", "037");
				requestData1.put("remindUrl","/system/subsys/crm/core/drawback/indexApprove.jsp");
				requestData1.put("remindUrl1","/system/mobile/phone/crm/drawback/indexApprove.jsp?drawbackStatus=1");
				smsManager.sendSms(requestData1, person);
				
			}
		}

		json.setRtState(true);
		json.setRtMsg("保存成功！");
		return json;
	}

	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm, Map requestDatas) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson person =(TeePerson)requestDatas.get("LOGIN_USER");
		String cusId = (String) requestDatas.get("cusId");
		String drawbackNo = (String) requestDatas.get("drawbackNo");//退款编号
		String customerName = (String) requestDatas.get("customerName");//客户名称
		String customerId = (String) requestDatas.get("customerId"); //客户id
		String orderNo = (String) requestDatas.get("orderNo");//订单编号
		String orderId = (String) requestDatas.get("orderId");//
		String drawbackTimeDesc =(String) requestDatas.get("drawbackTimeDesc"); //退款日期
		String drawbackStyle = (String) requestDatas.get("drawbackStyle");//退款方式
		String drawbackStatusDesc =(String) requestDatas.get("drawbackStatusDesc");  //状态
		String type = (String) requestDatas.get("type");
		String drawbackStatus =(String) requestDatas.get("drawbackStatus");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		List param = new ArrayList();
		String hql = " from TeeCrmDrawback drawback where 1=1";
		if(!TeeUtility.isNullorEmpty(drawbackNo)){//退款编号
			hql += " and drawback.drawbackNo like ?";
			param.add("%"+drawbackNo+"%");
		}
		if(!TeeUtility.isNullorEmpty(customerId)&& !"0".equals(customerId)){
			TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, Integer.parseInt(customerId));
			if(!TeeUtility.isNullorEmpty(customer)){//客户
				hql+=" and drawback.customer= ?";
				param.add(customer);
			}
		}
		if(!TeeUtility.isNullorEmpty(orderId)&& !"0".equals(orderId)){
			TeeCrmOrder order = (TeeCrmOrder) simpleDaoSupport.get(TeeCrmOrder.class, Integer.parseInt(orderId));
			if(!TeeUtility.isNullorEmpty(order)){//订单
				hql+=" and drawback.order= ?";
				param.add(order);
			}
		}
		if(!TeeUtility.isNullorEmpty(drawbackStyle) && !"0".equals(drawbackStyle)){//退款方式
			hql += " and drawback.drawbackStyle= ?";
			param.add(drawbackStyle);
		}
		if(!TeeUtility.isNullorEmpty(drawbackTimeDesc)){//退款日期
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sf.parse(drawbackTimeDesc));
				hql+=" and drawback.drawbackTime = ?";
				param.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(!TeeUtility.isNullorEmpty(drawbackStatusDesc)){
			if(!"0".equals(drawbackStatusDesc)){
				hql+=" and drawback.drawbackStatus = ?";
				param.add(Integer.parseInt(drawbackStatusDesc));
			}else{
				hql +=" ";
			}
		}
		if(!TeeUtility.isNullorEmpty(type)){
			if("2".equals(type)){//type =2我负责的
				hql +=" and drawback.responsibleUser.uuid = "+person.getUuid();
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
			    		hql+=" and drawback.responsibleUser.uuid in ("+ underPersonIds+")";
			    		
			    	}else{
				    	hql +=" and drawback.responsibleUser.uuid is null";
				    }
			    }
			}
		}else{
			if(!TeeUtility.isNullorEmpty(drawbackStatus)&&!"0".equals(drawbackStatus)){
				hql =" from TeeCrmDrawback drawback where drawback.refundFinance = ? and drawback.drawbackStatus = ? ";
				param.add(person);
				param.add(Integer.parseInt(drawbackStatus));
			}else{
				//  没有type代表所属客户下的退货单
				hql =" from TeeCrmDrawback drawback,TeeCrmCustomer customer where drawback.customer=customer and drawback.customer.sid=? and ( drawback.responsibleUser.uuid="+person.getUuid()+" or drawback.createUser.uuid="+person.getUuid()+") ";
				param.add(Integer.parseInt(cusId));
			}
			
		}
		List<TeeCrmDrawback> orderList = simpleDaoSupport.pageFindByList("select drawback "+hql+" order by drawback.lastEditTime desc", (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		long total = simpleDaoSupport.countByList("select count(*) "+hql, param);
		List<TeeCrmDrawbackModel> models = new ArrayList<TeeCrmDrawbackModel>();
		for (TeeCrmDrawback drawback : orderList) {
			TeeCrmDrawbackModel m = new TeeCrmDrawbackModel();
			m = parseModel(drawback);
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	/**
	 * 对象转换
	 * @param drawback
	 * @return
	 */
	public TeeCrmDrawbackModel parseModel(TeeCrmDrawback drawback){
		TeeCrmDrawbackModel model = new TeeCrmDrawbackModel();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(drawback == null){
			return null;
		}
		BeanUtils.copyProperties(drawback, model);
		if(drawback.getDrawbackTime()!=null){//退款日期
			model.setDrawbackTimeDesc(sf.format(drawback.getDrawbackTime().getTime()));
		}
		if(drawback.getDrawbackStatus()==1){//退款状态
			model.setDrawbackStatusDesc("待退款");
		}else if(drawback.getDrawbackStatus()==2){
			model.setDrawbackStatusDesc("已退款");
		}else if(drawback.getDrawbackStatus()==3){
			model.setDrawbackStatusDesc("已驳回");
		}
		if(drawback.getCreateTime()!=null){
			model.setCreateTimeDesc(sdf.format(drawback.getCreateTime().getTime())); //创建时间
		}
		if(drawback.getCustomer()!=null){//客户
			model.setCustomerId(drawback.getCustomer().getSid());
			model.setCustomerName(drawback.getCustomer().getCustomerName());
		}
		if(drawback.getOrder()!=null){//订单
			model.setOrderId(drawback.getOrder().getSid());
			model.setOrderNo(drawback.getOrder().getOrderNo());
		}
		if(drawback.getCreateUser()!=null){//创建人
			model.setAddPersonId(drawback.getCreateUser().getUuid());
			model.setAddPersonName(drawback.getCreateUser().getUserName());
		}
		if(drawback.getResponsibleUser()!=null){//负责人
			model.setManagePersonId(drawback.getResponsibleUser().getUuid());
			model.setManagePersonName(drawback.getResponsibleUser().getUserName());
		}
		if(drawback.getRefundFinance()!=null){//退款财务
			model.setRefundFinancialId(drawback.getRefundFinance().getUuid());
			model.setRefundFinancialName(drawback.getRefundFinance().getUserName());
		}
		if(drawback.getLastEditTime()!=null){
			model.setLastEditTimeDesc(sdf.format(drawback.getLastEditTime().getTime()));  //最后修改日期
		}
		//退款方式
		String drawbackStyleDesc=TeeCrmCodeManager.getChildSysCodeNameCodeNo("DRAWBACK_STYLE", model.getDrawbackStyle());
		model.setDrawbackStyleDesc(drawbackStyleDesc);
		
		//驳回原因
		if(drawback.getRejectReason()!=null){
			model.setRejectReason(drawback.getRejectReason());
		}else{
			model.setRejectReason(null);
		}
		
		return model;
	}

	public TeeJson getInfoBySid(HttpServletRequest request,TeeCrmDrawbackModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeCrmDrawback drawback = (TeeCrmDrawback) simpleDaoSupport.get(TeeCrmDrawback.class, model.getSid());
			if(drawback !=null){
				model = parseModel(drawback);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关退款信息！");
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
			TeeCrmDrawback drawback=(TeeCrmDrawback) simpleDaoSupport.get(TeeCrmDrawback.class, Integer.parseInt(uuid));
			int status=TeeStringUtil.getInteger(request.getParameter("drawbackStatus"), 0);
			Calendar cl = Calendar.getInstance();
			simpleDaoSupport.executeUpdate("update TeeCrmDrawback set drawbackStatus = ?, lastEditTime = ? where sid =? ", new Object[]{status,cl,Integer.parseInt(uuid)});
			
			//发送消息
			if(!TeeUtility.isNullorEmpty(drawback.getCreateUser())){
				Map requestData1 = new HashMap();
				requestData1.put("content", "您的退款信息被退款财务审核通过,退款编号为："+drawback.getDrawbackNo()+"，请及时查看！");
				requestData1.put("userListIds", drawback.getCreateUser().getUuid());
				requestData1.put("moduleNo", "037");
				requestData1.put("remindUrl","");
				requestData1.put("remindUrl1","/system/mobile/phone/crm/drawback/indexApprove.jsp?drawbackStatus=2");
				smsManager.sendSms(requestData1, drawback.getRefundFinance());
				
			}
			
			json.setRtState(true);
			json.setRtMsg("操作成功！");
		}
		return json;
	}

	public TeeJson reject(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String uuid=TeeStringUtil.getString(request.getParameter("sid"));
		String reason = TeeStringUtil.getString(request.getParameter("rejectReason"),  null);
		if(!TeeUtility.isNullorEmpty(uuid)){
			TeeCrmDrawback drawback=(TeeCrmDrawback) simpleDaoSupport.get(TeeCrmDrawback.class, Integer.parseInt(uuid));
			int status=TeeStringUtil.getInteger(request.getParameter("drawbackStatus"), 0);
			Calendar cl = Calendar.getInstance();
			simpleDaoSupport.executeUpdate("update TeeCrmDrawback set rejectReason = ?, drawbackStatus = ?, lastEditTime = ? where sid =? ", new Object[]{reason,status,cl,Integer.parseInt(uuid)});
			
			//发送消息
			if(!TeeUtility.isNullorEmpty(drawback.getCreateUser())){
				Map requestData1 = new HashMap();
				requestData1.put("content", "您的退款信息被退款财务驳回,退款编号为："+drawback.getDrawbackNo()+"，请及时查看！");
				requestData1.put("userListIds", drawback.getCreateUser().getUuid());
				requestData1.put("moduleNo", "037");
				requestData1.put("remindUrl","");
				requestData1.put("remindUrl1","/system/mobile/phone/crm/drawback/indexApprove.jsp?drawbackStatus=3");
				smsManager.sendSms(requestData1, drawback.getRefundFinance());
				
			}
			json.setRtState(true);
			json.setRtMsg("操作成功！");
		}
		return json;
	}

	public TeeJson deleteById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sid = TeeStringUtil.getString(request.getParameter("sid"),"0");
		if(!TeeUtility.isNullorEmpty(sid)){
			TeeCrmDrawback drawback = new TeeCrmDrawback();
			drawback.setSid(Integer.parseInt(sid));
			simpleDaoSupport.deleteByObj(drawback);
			json.setRtState(true);
			json.setRtMsg("删除成功！");
		}
		return json;
	
	}

}
