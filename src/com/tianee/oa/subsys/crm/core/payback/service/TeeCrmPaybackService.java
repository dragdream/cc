package com.tianee.oa.subsys.crm.core.payback.service;

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

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomer;
import com.tianee.oa.subsys.crm.core.invoice.bean.TeeCrmInvoice;
import com.tianee.oa.subsys.crm.core.order.bean.TeeCrmOrder;
import com.tianee.oa.subsys.crm.core.payback.bean.TeeCrmPayback;
import com.tianee.oa.subsys.crm.core.payback.model.TeeCrmPaybackModel;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeCrmPaybackService extends TeeBaseService {
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeAttachmentDao attachmentDao;
	
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private TeeSmsManager smsManager;

	public TeeJson addOrUpdate(HttpServletRequest request,TeeCrmPaybackModel model) {
		TeeJson json = new TeeJson();
		TeeCrmPayback payback = new TeeCrmPayback();
		TeePerson person =(TeePerson)request.getSession().getAttribute("LOGIN_USER");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cl = Calendar.getInstance();
		Calendar cl2 = Calendar.getInstance();
		Calendar ncl = Calendar.getInstance();
		//附件
		String dbAttachSid = TeeStringUtil.getString(request.getParameter("dbAttachSid"), "0");
		String attachmentSidStr = TeeStringUtil.getString(request.getParameter("attachmentSidStr") ,"0");
		List<TeeAttachment> attachAll = new ArrayList<TeeAttachment>();
		List<TeeAttachment> dbAttachSidList = getAttachmentsByIds(dbAttachSid);
		List<TeeAttachment> attachments = getAttachmentsByIds(attachmentSidStr);
		attachAll.addAll(dbAttachSidList);
		attachAll.addAll(attachments);
		if(model.getSid() > 0){
			payback  = (TeeCrmPayback) simpleDaoSupport.get(TeeCrmPayback.class, model.getSid());
			if(payback != null){
				BeanUtils.copyProperties(model, payback);
				if(!TeeUtility.isNullorEmpty(model.getCustomerId())){//客户
					TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, model.getCustomerId());
					payback.setCustomer(customer);
				}
				if(!TeeUtility.isNullorEmpty(model.getOrderId())){//订单
					TeeCrmOrder order = (TeeCrmOrder) simpleDaoSupport.get(TeeCrmOrder.class, model.getOrderId());
					payback.setOrder(order);
				}
			
				payback.setCreateUser(person); //创建人
				if(!TeeUtility.isNullorEmpty(model.getManagePersonId())){//负责人
					TeePerson responsibleUser = (TeePerson)personDao.get(model.getManagePersonId());
					payback.setResponsibleUser(responsibleUser);
				}
				if(!TeeUtility.isNullorEmpty(model.getPaymentFinancialId())){//回款财务
					TeePerson paymentFinancial = (TeePerson)personDao.get(model.getPaymentFinancialId());
					payback.setPaymentFinancial(paymentFinancial);
				}	
					
				if(!TeeUtility.isNullorEmpty(model.getRemindTimeDesc())){//提醒时间
					try {
						cl.setTime(sdf.parse(model.getRemindTimeDesc()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				payback.setRemindTime(cl);
				if(!TeeUtility.isNullorEmpty(model.getPaybackTimeDesc())){//回款日期
					try {
						cl2.setTime(sdf.parse(model.getPaybackTimeDesc()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				payback.setPaybackTime(cl2);
				payback.setCreateTime(payback.getCreateTime());//回款创建时间
				payback.setLastEditTime(ncl);//回款的最后一次修改日期
				payback.setPaybackStatus(1); //回款状态    1-未回款   2--已回款    3--已驳回
				simpleDaoSupport.update(payback);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到相关回款信息！");
				return json;
			}
		}else{//新建回款
			BeanUtils.copyProperties(model, payback);
			if(!TeeUtility.isNullorEmpty(model.getCustomerId())){//客户
				TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, model.getCustomerId());
				payback.setCustomer(customer);
			}
			if(!TeeUtility.isNullorEmpty(model.getOrderId())){//订单
				TeeCrmOrder order = (TeeCrmOrder) simpleDaoSupport.get(TeeCrmOrder.class, model.getOrderId());
				payback.setOrder(order);
			}
		
			payback.setCreateUser(person); //创建人
			if(!TeeUtility.isNullorEmpty(model.getManagePersonId())){//负责人
				TeePerson responsibleUser = (TeePerson)personDao.get(model.getManagePersonId());
				payback.setResponsibleUser(responsibleUser);
			}
			if(!TeeUtility.isNullorEmpty(model.getPaymentFinancialId())){//回款财务
				TeePerson paymentFinancial = (TeePerson)personDao.get(model.getPaymentFinancialId());
				payback.setPaymentFinancial(paymentFinancial);
			}	
				
			if(!TeeUtility.isNullorEmpty(model.getRemindTimeDesc())){//提醒时间
				try {
					cl.setTime(sdf.parse(model.getRemindTimeDesc()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			payback.setRemindTime(cl);
			if(!TeeUtility.isNullorEmpty(model.getPaybackTimeDesc())){//回款日期
				try {
					cl2.setTime(sdf.parse(model.getPaybackTimeDesc()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			payback.setPaybackTime(cl2);
			payback.setCreateTime(ncl);//回款创建时间
			payback.setLastEditTime(ncl);//回款的最后一次修改日期
			payback.setPaybackStatus(1); //回款状态    1-未回款   2--已回款    3--已驳回
			simpleDaoSupport.save(payback);
		}
		
		//处理附件
		for(TeeAttachment attach:attachAll){
			attach.setModelId(String.valueOf(payback.getSid()));
			simpleDaoSupport.update(attach);
			
		}
		
		//发送消息
		if(!TeeUtility.isNullorEmpty(model.getPaymentFinancialId())){
			Map requestData1 = new HashMap();
			requestData1.put("content", "您有一个回款需要确认，回款编号为："+payback.getPaybackNo()+"，请及时办理！");
			requestData1.put("userListIds", model.getPaymentFinancialId());
			requestData1.put("moduleNo", "037");
			requestData1.put("remindUrl","/system/subsys/crm/core/payback/indexApprove.jsp");
			requestData1.put("remindUrl1","/system/mobile/phone/crm/payback/indexApprove.jsp?paybackStatus=1");
			smsManager.sendSms(requestData1, person);
			
		}

		json.setRtState(true);
		json.setRtMsg("保存成功！");
		return json;
	}
	
	/**
	 * 根据附件sid获取附件
	 * 
	 * @date 2014年6月10日
	 * @author
	 * @param attachIds
	 * @return
	 */
	public List<TeeAttachment> getAttachmentsByIds(String attachIds) {
		return attachmentDao.getAttachmentsByIds(attachIds);
	}

	/**
	 * 回款列表
	 * @param dm
	 * @param requestDatas
	 * @return
	 */
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm, Map requestDatas) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson person =(TeePerson)requestDatas.get("LOGIN_USER");
		String cusId = (String) requestDatas.get("cusId");
		String paybackNo = (String) requestDatas.get("paybackNo");//汇款编号
		String customerName = (String) requestDatas.get("customerName");//客户名称
		String customerId = (String) requestDatas.get("customerId"); //客户id
		String orderNo = (String) requestDatas.get("orderNo");//订单编号
		String orderId = (String) requestDatas.get("orderId");//
		String paybackTimeDesc =(String) requestDatas.get("paybackTimeDesc"); //回款日期
		String paybackStyle = (String) requestDatas.get("paybackStyle");//回款方式
		String paybackStatusDesc =(String) requestDatas.get("paybackStatusDesc");  //状态
		String paybackStatus = (String) requestDatas.get("paybackStatus");//
		String type = (String) requestDatas.get("type");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		List param = new ArrayList();
		String hql = " from TeeCrmPayback payback where 1=1";
		if(!TeeUtility.isNullorEmpty(paybackNo)){//回款编号
			hql += " and payback.paybackNo like ?";
			param.add("%"+paybackNo+"%");
		}
		if(!TeeUtility.isNullorEmpty(customerId)&& !"0".equals(customerId)){
			TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, Integer.parseInt(customerId));
			if(!TeeUtility.isNullorEmpty(customer)){//客户
				hql+=" and payback.customer= ?";
				param.add(customer);
			}
		}
		if(!TeeUtility.isNullorEmpty(orderId)&& !"0".equals(orderId)){
			TeeCrmOrder order = (TeeCrmOrder) simpleDaoSupport.get(TeeCrmOrder.class, Integer.parseInt(orderId));
			if(!TeeUtility.isNullorEmpty(order)){//订单
				hql+=" and payback.order= ?";
				param.add(order);
			}
		}
		if(!TeeUtility.isNullorEmpty(paybackStyle) && !"0".equals(paybackStyle)){//回款方式
			hql += " and payback.paybackStyle= ?";
			param.add(paybackStyle);
		}
		if(!TeeUtility.isNullorEmpty(paybackTimeDesc)){//回款日期
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sf.parse(paybackTimeDesc));
				hql+=" and payback.paybackTime = ?";
				param.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(!TeeUtility.isNullorEmpty(paybackStatusDesc)){
			if(!"0".equals(paybackStatusDesc)){
				hql+=" and payback.paybackStatus = ?";
				param.add(Integer.parseInt(paybackStatusDesc));
			}else{
				hql +=" ";
			}
		}
		if(!TeeUtility.isNullorEmpty(type)){
			if("2".equals(type)){//type =2我负责的
				hql +=" and payback.responsibleUser.uuid = "+person.getUuid() ;
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
			    		hql+=" and payback.responsibleUser.uuid in ("+ underPersonIds+")";
			    		
			    	}else{
				    	hql +=" and payback.responsibleUser.uuid is null";
				    }
			    }
			}
		}else{//回款财务查询不同状态下的数据
			if(!TeeUtility.isNullorEmpty(paybackStatus)&&!"0".equals(paybackStatus)){
				hql =" from TeeCrmPayback payback where payback.paymentFinancial = ? and payback.paybackStatus = ? ";
				param.add(person);
				param.add(Integer.parseInt(paybackStatus));
			}else{
				//  没有type代表所属客户下的退货单
				hql =" from TeeCrmPayback payback,TeeCrmCustomer customer where payback.customer=customer and payback.customer.sid=? and ( payback.responsibleUser.uuid="+person.getUuid()+" or payback.createUser.uuid="+person.getUuid()+") ";
				param.add(Integer.parseInt(cusId));
				
				
			}
		}
		List<TeeCrmPayback> orderList = simpleDaoSupport.pageFindByList("select payback "+hql+" order by payback.lastEditTime desc", (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		long total = simpleDaoSupport.countByList("select count(*) "+hql, param);
		List<TeeCrmPaybackModel> models = new ArrayList<TeeCrmPaybackModel>();
		for (TeeCrmPayback payback : orderList) {
			TeeCrmPaybackModel m = new TeeCrmPaybackModel();
			m = parseModel(payback);
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
	public TeeCrmPaybackModel parseModel(TeeCrmPayback payback){
		TeeCrmPaybackModel model = new TeeCrmPaybackModel();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(payback == null){
			return null;
		}
		BeanUtils.copyProperties(payback, model);
		if(payback.getPaybackTime()!=null){//回款日期
			model.setPaybackTimeDesc(sf.format(payback.getPaybackTime().getTime()));
		}
		if(payback.getRemindTime()!=null){//提醒时间
			model.setRemindTimeDesc(sf.format(payback.getRemindTime().getTime()));
		}
		if(payback.getPaybackStatus()==1){//回款状态
			model.setPaybackStatusDesc("待回款");
		}else if(payback.getPaybackStatus()==2){
			model.setPaybackStatusDesc("已回款");
		}else if(payback.getPaybackStatus()==3){
			model.setPaybackStatusDesc("已驳回");
		}
		if(payback.getCreateTime()!=null){
			model.setCreateTimeDesc(sdf.format(payback.getCreateTime().getTime())); //创建时间
		}
		if(payback.getCustomer()!=null){//客户
			model.setCustomerId(payback.getCustomer().getSid());
			model.setCustomerName(payback.getCustomer().getCustomerName());
		}
		if(payback.getOrder()!=null){//订单
			model.setOrderId(payback.getOrder().getSid());
			model.setOrderNo(payback.getOrder().getOrderNo());
		}
		if(payback.getCreateUser()!=null){//创建人
			model.setAddPersonId(payback.getCreateUser().getUuid());
			model.setAddPersonName(payback.getCreateUser().getUserName());
		}
		if(payback.getResponsibleUser()!=null){//负责人
			model.setManagePersonId(payback.getResponsibleUser().getUuid());
			model.setManagePersonName(payback.getResponsibleUser().getUserName());
		}
		if(payback.getPaymentFinancial()!=null){//财务回款
			model.setPaymentFinancialId(payback.getPaymentFinancial().getUuid());
			model.setPaymentFinancialName(payback.getPaymentFinancial().getUserName());
		}
		if(payback.getLastEditTime()!=null){
			model.setLastEditTimeDesc(sdf.format(payback.getLastEditTime().getTime()));  //最后修改日期
		}
		//回款方式
		String paybackStyleDesc=TeeCrmCodeManager.getChildSysCodeNameCodeNo("PAYBACK_STYLE", model.getPaybackStyle());
		model.setPaybackStyleDesc(paybackStyleDesc);
		
		//驳回原因
		if(payback.getRejectReason()!=null){
			model.setRejectReason(payback.getRejectReason());
		}else{
			model.setRejectReason(null);
		}
		
		//附件
		List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.CRM_PAYBACK, String.valueOf(payback.getSid()));
		List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
		for(TeeAttachment attach:attaches){
			TeeAttachmentModel m = new TeeAttachmentModel();
			BeanUtils.copyProperties(attach, m);
			m.setUserId(attach.getUser().getUuid()+"");
			m.setUserName(attach.getUser().getUserName());
			m.setPriv(1+2+4+8+16+32);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
			attachmodels.add(m);
		}
		model.setAttachmodels(attachmodels);
		
		return model;
	}

	public TeeJson getInfoBySid(HttpServletRequest request,TeeCrmPaybackModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeCrmPayback payback = (TeeCrmPayback) simpleDaoSupport.get(TeeCrmPayback.class, model.getSid());
			if(payback !=null){
				model = parseModel(payback);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关回款信息！");
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
			TeeCrmPayback payback=(TeeCrmPayback) simpleDaoSupport.get(TeeCrmPayback.class, Integer.parseInt(uuid));
			int status=TeeStringUtil.getInteger(request.getParameter("paybackStatus"), 0);
			Calendar cl = Calendar.getInstance();
			simpleDaoSupport.executeUpdate("update TeeCrmPayback set paybackStatus = ?, lastEditTime = ? where sid =? ", new Object[]{status,cl,Integer.parseInt(uuid)});
			
			//发送消息
			if(!TeeUtility.isNullorEmpty(payback.getCreateUser())){
				Map requestData1 = new HashMap();
				requestData1.put("content", "您的回款信息被回款财务审核通过,回款编号为："+payback.getPaybackNo()+"，请及时查看！");
				requestData1.put("userListIds", payback.getCreateUser().getUuid());
				requestData1.put("moduleNo", "037");
				requestData1.put("remindUrl","");
				requestData1.put("remindUrl1","/system/mobile/phone/crm/payback/indexApprove.jsp?paybackStatus=2");
				smsManager.sendSms(requestData1, payback.getPaymentFinancial());
				
			}
			
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
			TeeCrmPayback payback=(TeeCrmPayback) simpleDaoSupport.get(TeeCrmPayback.class, Integer.parseInt(uuid));
			int status=TeeStringUtil.getInteger(request.getParameter("paybackStatus"), 0);
			Calendar cl = Calendar.getInstance();
			simpleDaoSupport.executeUpdate("update TeeCrmPayback set rejectReason = ?, paybackStatus = ?, lastEditTime = ? where sid =? ", new Object[]{reason,status,cl,Integer.parseInt(uuid)});
			
			//发送消息
			if(!TeeUtility.isNullorEmpty(payback.getCreateUser())){
				Map requestData1 = new HashMap();
				requestData1.put("content", "您的回款信息被回款财务驳回,回款编号为："+payback.getPaybackNo()+"，请及时查看！");
				requestData1.put("userListIds", payback.getCreateUser().getUuid());
				requestData1.put("moduleNo", "037");
				requestData1.put("remindUrl","");
				requestData1.put("remindUrl1","/system/mobile/phone/crm/payback/indexApprove.jsp?paybackStatus=3");
				smsManager.sendSms(requestData1, payback.getPaymentFinancial());
				
			}
			
			json.setRtState(true);
			json.setRtMsg("操作成功！");
		}
		return json;
	}

	/**
	 * 删除回款信息
	 * @param request
	 * @return
	 */
	public TeeJson deleteById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sid = TeeStringUtil.getString(request.getParameter("sid"),"0");
		if(!TeeUtility.isNullorEmpty(sid)){
			TeeCrmPayback payback = new TeeCrmPayback();
			payback.setSid(Integer.parseInt(sid));
			simpleDaoSupport.deleteByObj(payback);
			json.setRtState(true);
			json.setRtMsg("删除成功！");
		}
		return json;
	
	}
	

}
