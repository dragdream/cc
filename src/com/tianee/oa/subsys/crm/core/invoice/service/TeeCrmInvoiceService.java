package com.tianee.oa.subsys.crm.core.invoice.service;

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
import com.tianee.oa.subsys.crm.core.invoice.model.TeeCrmInvoiceModel;
import com.tianee.oa.subsys.crm.core.order.bean.TeeCrmOrder;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeCrmInvoiceService extends TeeBaseService{
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeAttachmentDao attachmentDao;
	
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private TeeSmsManager smsManager;

	/**
	 * 添加或编辑开票
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request,TeeCrmInvoiceModel model) {
		TeeJson json = new TeeJson();
		TeeCrmInvoice invoice = new TeeCrmInvoice();
		TeePerson person =(TeePerson)request.getSession().getAttribute("LOGIN_USER");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cl = Calendar.getInstance();
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
			invoice  = (TeeCrmInvoice) simpleDaoSupport.get(TeeCrmInvoice.class, model.getSid());
			if(invoice != null){
				BeanUtils.copyProperties(model, invoice);
				if(!TeeUtility.isNullorEmpty(model.getCustomerId())){//客户
					TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, model.getCustomerId());
					invoice.setCustomer(customer);
				}
				if(!TeeUtility.isNullorEmpty(model.getOrderId())){//订单
					TeeCrmOrder order = (TeeCrmOrder) simpleDaoSupport.get(TeeCrmOrder.class, model.getOrderId());
					invoice.setOrder(order);
				}
			
				invoice.setCreateUser(person); //创建人
				if(!TeeUtility.isNullorEmpty(model.getManagePersonId())){//负责人
					TeePerson responsibleUser = (TeePerson)personDao.get(model.getManagePersonId());
					invoice.setResponsibleUser(responsibleUser);
				}
				if(!TeeUtility.isNullorEmpty(model.getInvoiceFinancialId())){//开票财务
					TeePerson invoiceFinancial = (TeePerson)personDao.get(model.getInvoiceFinancialId());
					invoice.setInvoiceFinancial(invoiceFinancial);
				}	
				if(!TeeUtility.isNullorEmpty(model.getInvoiceTimeDesc())){//开票日期
					try {
						cl.setTime(sdf.parse(model.getInvoiceTimeDesc()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				invoice.setInvoiceTime(cl);
				invoice.setCreateTime(invoice.getCreateTime());//开票创建时间
				invoice.setLastEditTime(ncl);//开票的最后一次修改日期
				invoice.setInvoiceStatus(1);; //开票状态    1-未开票   2--已开票    3--已驳回
				simpleDaoSupport.update(invoice);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到相关开票信息！");
				return json;
			}
		}else{//新建开票
			BeanUtils.copyProperties(model, invoice);
			if(!TeeUtility.isNullorEmpty(model.getCustomerId())){//客户
				TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, model.getCustomerId());
				invoice.setCustomer(customer);
			}
			if(!TeeUtility.isNullorEmpty(model.getOrderId())){//订单
				TeeCrmOrder order = (TeeCrmOrder) simpleDaoSupport.get(TeeCrmOrder.class, model.getOrderId());
				invoice.setOrder(order);
			}
		
			invoice.setCreateUser(person); //创建人
			if(!TeeUtility.isNullorEmpty(model.getManagePersonId())){//负责人
				TeePerson responsibleUser = (TeePerson)personDao.get(model.getManagePersonId());
				invoice.setResponsibleUser(responsibleUser);
			}
			if(!TeeUtility.isNullorEmpty(model.getInvoiceFinancialId())){//开票财务
				TeePerson invoiceFinancial = (TeePerson)personDao.get(model.getInvoiceFinancialId());
				invoice.setInvoiceFinancial(invoiceFinancial);
			}	
			if(!TeeUtility.isNullorEmpty(model.getInvoiceTimeDesc())){//开票日期
				try {
					cl.setTime(sdf.parse(model.getInvoiceTimeDesc()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			invoice.setInvoiceTime(cl);
			invoice.setCreateTime(ncl);//回款创建时间
			invoice.setLastEditTime(ncl);//回款的最后一次修改日期
			invoice.setInvoiceStatus(1);; //开票状态    1-未开票   2--已开票    3--已驳回
			simpleDaoSupport.save(invoice);
		}
		
		//处理附件
		for(TeeAttachment attach:attachAll){
			attach.setModelId(String.valueOf(invoice.getSid()));
			simpleDaoSupport.update(attach);
		}
		
		//发送消息
		if(!TeeUtility.isNullorEmpty(model.getInvoiceFinancialId())){
			Map requestData1 = new HashMap();
			requestData1.put("content", "您有一个开票申请需要确认，开票申请编号为："+invoice.getInvoiceNo()+"，请及时办理！");
			requestData1.put("userListIds", model.getInvoiceFinancialId());
			requestData1.put("moduleNo", "037");
			requestData1.put("remindUrl","/system/subsys/crm/core/invoice/indexApprove.jsp");
			requestData1.put("remindUrl1","/system/mobile/phone/crm/invoice/indexApprove.jsp?invoiceStatus=1");
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
	 * 开票列表
	 * @param dm
	 * @param requestDatas
	 * @return
	 */
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm, Map requestDatas) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson person =(TeePerson)requestDatas.get("LOGIN_USER");
		String cusId = (String) requestDatas.get("cusId");
		String invoiceNo = (String) requestDatas.get("invoiceNo");//开票编号
		String customerName = (String) requestDatas.get("customerName");//客户名称
		String customerId = (String) requestDatas.get("customerId"); //客户id
		String orderNo = (String) requestDatas.get("orderNo");//订单编号
		String orderId = (String) requestDatas.get("orderId");//
		String invoiceTimeDesc =(String) requestDatas.get("invoiceTimeDesc"); //开票日期
		String invoiceType = (String) requestDatas.get("invoiceType");//开票类型
		String invoiceStatusDesc =(String) requestDatas.get("invoiceStatusDesc");  //状态
		String type = (String) requestDatas.get("type");
		String invoiceStatus = (String) requestDatas.get("invoiceStatus");//
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		List param = new ArrayList();
		String hql = " from TeeCrmInvoice invoice where 1=1";
		if(!TeeUtility.isNullorEmpty(invoiceNo)){//开票编号
			hql += " and invoice.invoiceNo like ?";
			param.add("%"+invoiceNo+"%");
		}
		if(!TeeUtility.isNullorEmpty(customerId)&& !"0".equals(customerId)){
			TeeCrmCustomer customer = (TeeCrmCustomer) simpleDaoSupport.get(TeeCrmCustomer.class, Integer.parseInt(customerId));
			if(!TeeUtility.isNullorEmpty(customer)){//客户
				hql+=" and invoice.customer= ?";
				param.add(customer);
			}
		}
		if(!TeeUtility.isNullorEmpty(orderId)&& !"0".equals(orderId)){
			TeeCrmOrder order = (TeeCrmOrder) simpleDaoSupport.get(TeeCrmOrder.class, Integer.parseInt(orderId));
			if(!TeeUtility.isNullorEmpty(order)){//订单
				hql+=" and invoice.order= ?";
				param.add(order);
			}
		}
		if(!TeeUtility.isNullorEmpty(invoiceType) && !"0".equals(invoiceType)){//开票类型
			hql += " and invoice.invoiceType= ?";
			param.add(invoiceType);
		}
		if(!TeeUtility.isNullorEmpty(invoiceTimeDesc)){//开票日期
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sf.parse(invoiceTimeDesc));
				hql+=" and invoice.invoiceTime = ?";
				param.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(!TeeUtility.isNullorEmpty(invoiceStatusDesc)){
			if(!"0".equals(invoiceStatusDesc)){
				hql+=" and invoice.invoiceStatus = ?";
				param.add(Integer.parseInt(invoiceStatusDesc));
			}else{
				hql +=" ";
			}
		}
		if(!TeeUtility.isNullorEmpty(type)){
			if("2".equals(type)){//type =2我负责的
				hql +=" and invoice.responsibleUser.uuid = "+person.getUuid();
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
			    		hql+=" and invoice.responsibleUser.uuid in ("+ underPersonIds+")";
			    		
			    	}else{
				    	hql +=" and invoice.responsibleUser.uuid is null";
				    }
			    }
			}
		}else{
			if(!TeeUtility.isNullorEmpty(invoiceStatus)&&!"0".equals(invoiceStatus)){
				hql =" from TeeCrmInvoice invoice where invoice.invoiceFinancial = ? and invoice.invoiceStatus = ? ";
				param.add(person);
				param.add(Integer.parseInt(invoiceStatus));
			}else{
			//  没有type代表所属客户下的退货单
				hql =" from TeeCrmInvoice invoice,TeeCrmCustomer customer where invoice.customer=customer and invoice.customer.sid=? and ( invoice.responsibleUser.uuid="+person.getUuid()+" or invoice.createUser.uuid="+person.getUuid()+") ";
				param.add(Integer.parseInt(cusId));
			}
		}
		List<TeeCrmInvoice> orderList = simpleDaoSupport.pageFindByList("select invoice "+hql+" order by invoice.lastEditTime desc", (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		long total = simpleDaoSupport.countByList("select count(*) "+hql, param);
		List<TeeCrmInvoiceModel> models = new ArrayList<TeeCrmInvoiceModel>();
		for (TeeCrmInvoice invoice : orderList) {
			TeeCrmInvoiceModel m = new TeeCrmInvoiceModel();
			m = parseModel(invoice);
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	public TeeCrmInvoiceModel parseModel(TeeCrmInvoice invoice){
		TeeCrmInvoiceModel model = new TeeCrmInvoiceModel();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(invoice == null){
			return null;
		}
		BeanUtils.copyProperties(invoice, model);
		if(invoice.getInvoiceTime()!=null){//开票日期
			model.setInvoiceTimeDesc(sf.format(invoice.getInvoiceTime().getTime()));
		}
		if(invoice.getInvoiceStatus()==1){//开票状态
			model.setInvoiceStatusDesc("待开票");
		}else if(invoice.getInvoiceStatus()==2){
			model.setInvoiceStatusDesc("已开票");
		}else if(invoice.getInvoiceStatus()==3){
			model.setInvoiceStatusDesc("已驳回");
		}
		if(invoice.getCreateTime()!=null){
			model.setCreateTimeDesc(sdf.format(invoice.getCreateTime().getTime())); //创建时间
		}
		if(invoice.getCustomer()!=null){//客户
			model.setCustomerId(invoice.getCustomer().getSid());
			model.setCustomerName(invoice.getCustomer().getCustomerName());
		}
		if(invoice.getOrder()!=null){//订单
			model.setOrderId(invoice.getOrder().getSid());
			model.setOrderNo(invoice.getOrder().getOrderNo());
		}
		if(invoice.getCreateUser()!=null){//创建人
			model.setAddPersonId(invoice.getCreateUser().getUuid());
			model.setAddPersonName(invoice.getCreateUser().getUserName());
		}
		if(invoice.getResponsibleUser()!=null){//负责人
			model.setManagePersonId(invoice.getResponsibleUser().getUuid());
			model.setManagePersonName(invoice.getResponsibleUser().getUserName());
		}
		if(invoice.getInvoiceFinancial()!=null){//开票财务
			model.setInvoiceFinancialId(invoice.getInvoiceFinancial().getUuid());
			model.setInvoiceFinancialName(invoice.getInvoiceFinancial().getUserName());
		}
		if(invoice.getLastEditTime()!=null){
			model.setLastEditTimeDesc(sdf.format(invoice.getLastEditTime().getTime()));  //最后修改日期
		}
		//开票类型
		String invoiceTypeDesc=TeeCrmCodeManager.getChildSysCodeNameCodeNo("INVOICE_TYPE", model.getInvoiceType());
		model.setInvoiceTypeDesc(invoiceTypeDesc);
		
		//抬头类型
		if(invoice.getHeaderType()==1){
				model.setHeaderTypeDesc("个人");
		}else if(invoice.getHeaderType()==2){
				model.setHeaderTypeDesc("单位");
		}else{
			model.setHeaderTypeDesc(null);
		}
		
		//驳回原因
		if(invoice.getRejectReason()!=null){
			model.setRejectReason(invoice.getRejectReason());
		}else{
			model.setRejectReason(null);
		}
		
		//附件
		List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.CRM_INVOICE, String.valueOf(invoice.getSid()));
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

	/**
	 * 获取开票信息详情
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request,TeeCrmInvoiceModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeCrmInvoice invoice = (TeeCrmInvoice) simpleDaoSupport.get(TeeCrmInvoice.class, model.getSid());
			if(invoice !=null){
				model = parseModel(invoice);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关开票信息！");
		return json;
	}

	/**
	 * 同意
	 * @param request
	 * @return
	 */
	public TeeJson agree(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson person =(TeePerson)request.getSession().getAttribute("LOGIN_USER");
		String uuid=TeeStringUtil.getString(request.getParameter("sid"),"0");
		if(!TeeUtility.isNullorEmpty(uuid)){
			TeeCrmInvoice invoice=(TeeCrmInvoice) simpleDaoSupport.get(TeeCrmInvoice.class, Integer.parseInt(uuid));
			int status=TeeStringUtil.getInteger(request.getParameter("invoiceStatus"), 0);
			Calendar cl = Calendar.getInstance();
			simpleDaoSupport.executeUpdate("update TeeCrmInvoice set invoiceStatus = ?, lastEditTime = ? where sid =? ", new Object[]{status,cl,Integer.parseInt(uuid)});
			
			//发送消息
			if(!TeeUtility.isNullorEmpty(invoice.getCreateUser())){
				Map requestData1 = new HashMap();
				requestData1.put("content", "您的开票申请被开票财务审核通过,开票编号为："+invoice.getInvoiceNo()+"，请及时查看！");
				requestData1.put("userListIds", invoice.getCreateUser().getUuid());
				requestData1.put("moduleNo", "037");
				requestData1.put("remindUrl","");
				requestData1.put("remindUrl1","/system/mobile/phone/crm/invoice/indexApprove.jsp?invoiceStatus=2");
				smsManager.sendSms(requestData1, person);
				
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
		TeePerson person =(TeePerson)request.getSession().getAttribute("LOGIN_USER");
		String uuid=TeeStringUtil.getString(request.getParameter("sid"),"0");
		String reason = TeeStringUtil.getString(request.getParameter("rejectReason"),  null);
		if(!TeeUtility.isNullorEmpty(uuid)){
			
			TeeCrmInvoice invoice=(TeeCrmInvoice) simpleDaoSupport.get(TeeCrmInvoice.class, Integer.parseInt(uuid));
			int status=TeeStringUtil.getInteger(request.getParameter("invoiceStatus"), 0);
			Calendar cl = Calendar.getInstance();
			simpleDaoSupport.executeUpdate("update TeeCrmInvoice set rejectReason = ?, invoiceStatus = ?, lastEditTime = ? where sid =? ", new Object[]{reason,status,cl,Integer.parseInt(uuid)});
			
			//发送消息
			if(!TeeUtility.isNullorEmpty(invoice.getCreateUser())){
				Map requestData1 = new HashMap();
				requestData1.put("content", "您的开票申请被开票财务驳回,开票编号为："+invoice.getInvoiceNo()+"，请及时查看！");
				requestData1.put("userListIds", invoice.getCreateUser().getUuid());
				requestData1.put("moduleNo", "037");
				requestData1.put("remindUrl","");
				requestData1.put("remindUrl1","/system/mobile/phone/crm/invoice/indexApprove.jsp?invoiceStatus=3");
				smsManager.sendSms(requestData1, person);
				
			}
			json.setRtState(true);
			json.setRtMsg("操作成功！");
		}
		return json;
	}

	/**
	 * 删除开票记录
	 * @param request
	 * @return
	 */
	public TeeJson deleteById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sid = TeeStringUtil.getString(request.getParameter("sid"),"0");
		if(!TeeUtility.isNullorEmpty(sid)){
			TeeCrmInvoice invoice = new TeeCrmInvoice();
			invoice.setSid(Integer.parseInt(sid));
			simpleDaoSupport.deleteByObj(invoice);
			json.setRtState(true);
			json.setRtMsg("删除成功！");
		}
		return json;
	
	}

	
	
}
