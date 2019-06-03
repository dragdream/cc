package com.tianee.oa.subsys.crm.core.contract.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonManagerI;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeModelIdConst;
import com.tianee.oa.subsys.crm.core.contract.bean.TeeCrmContract;
import com.tianee.oa.subsys.crm.core.contract.bean.TeeCrmContractProductItem;
import com.tianee.oa.subsys.crm.core.contract.bean.TeeCrmContractRecvPayments;
import com.tianee.oa.subsys.crm.core.contract.dao.TeeCrmContractDao;
import com.tianee.oa.subsys.crm.core.contract.dao.TeeCrmContractProductItemDao;
import com.tianee.oa.subsys.crm.core.contract.dao.TeeCrmContractRecvPaymentsDao;
import com.tianee.oa.subsys.crm.core.contract.model.TeeCrmContractModel;
import com.tianee.oa.subsys.crm.core.contract.model.TeeCrmContractProductItemModel;
import com.tianee.oa.subsys.crm.core.contract.model.TeeCrmContractRecvPaymentsModel;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomerInfo;
import com.tianee.oa.subsys.crm.core.customer.dao.TeeCrmCustomerInfoDao;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeManagerPostPersonDataPrivModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Service
public class TeeCrmContractService  extends TeeBaseService{
	@Autowired
	TeeCrmContractDao crmContractDao;
	
	@Autowired
	TeePersonDao personDao;
	
	@Autowired
	TeeCrmContractProductItemDao contractProductItemDao;
	
	@Autowired
	TeeCrmContractProductItemService contractProductItemService;
	
	@Autowired
	TeeCrmContractRecvPaymentsDao contractRecvPaymentsDao;
	
	@Autowired
	TeeCrmContractRecvPaymentsService contractRecvPaymentsService;
	
	@Autowired
	private TeeAttachmentDao attachmentDao;
	
	@Autowired
	private TeeCrmCustomerInfoDao crmCustomerInfoDao;
	
	@Autowired
	private TeePersonManagerI personManagerI;
	/**
	 * 新建或者更新
	 * @param request 
	 * @param object 模型
	 * @return
	 * @throws ParseException 
	 */

	public TeeJson addOrUpdate(HttpServletRequest request , TeeCrmContractModel model ) throws ParseException{
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	
		StringBuffer desc = new StringBuffer();
		String logType = "040A";
		String productsListStr = request.getParameter("productsList");
		TeeCrmContract contract = new TeeCrmContract();
		//责任人
		int userId = TeeStringUtil.getInteger(model.getResponsibleUserId() , 0);
		TeePerson  responsibleUser = null;
		if(userId > 0 ){
			responsibleUser = personDao.get(userId);
		}
		
		TeeCrmCustomerInfo customerInfo = null;
		int  customerInfoId = TeeStringUtil.getInteger(model.getCustomerInfoId(), 0);
		if(customerInfoId > 0){
			customerInfo = crmCustomerInfoDao.get(customerInfoId);
		}
		String recvPaymentIds = TeeStringUtil.getString(request.getParameter("recvPaymentIds"));//分期付款ID字符串，更新，之前数据保存的sid字符串，以逗号分割
		
		//附件

		String dbAttachSid = TeeStringUtil.getString(request.getParameter("dbAttachSid"), "0");
		String attachmentSidStr = TeeStringUtil.getString(request.getParameter("attachmentSidStr") ,"0");
		List<TeeAttachment> attachAll = new ArrayList<TeeAttachment>();
		List<TeeAttachment> dbAttachSidList = getAttachmentsByIds(dbAttachSid);
		List<TeeAttachment> attachments = getAttachmentsByIds(attachmentSidStr);
		attachAll.addAll(dbAttachSidList);
		attachAll.addAll(attachments);
		
		if(model.getSid() > 0){
			TeeCrmContract oldContract = crmContractDao.get(model.getSid());
			Date createaTime = oldContract.getCreateTime();
			BeanUtils.copyProperties(model, oldContract);
			oldContract.setResponsibleUser(responsibleUser);
			oldContract.setCustomerInfo(customerInfo);
			oldContract.setCreateTime(createaTime);
			crmContractDao.update(oldContract);
			json.setRtMsg("更新成功！");
			contract = oldContract;
			/**
			 * 删除产品明细资料
			 */
			contractProductItemDao.delByContractId(oldContract.getSid());
			
			/**
			 * 删除分期付款信息
			 */
			contractRecvPaymentsService.delByContractAndIds(oldContract.getSid(), recvPaymentIds);
			logType = "040B";
		}else{
			BeanUtils.copyProperties(model, contract);
			contract.setResponsibleUser(responsibleUser);
			contract.setCreateTime(new Date());
			contract.setCreateUser(person);
			contract.setCustomerInfo(customerInfo);
			crmContractDao.save(contract);
			json.setRtMsg("新建成功！");
		}
		
		//处理邮件附件
		for(TeeAttachment attach:attachAll){
			attach.setModelId(String.valueOf(contract.getSid()));
			simpleDaoSupport.update(attach);
			
			
		}
		/**
		 * 新建产品明细
		 */
		if(!productsListStr.equals("")){
			List<TeeCrmContractProductItem>  productsList= (List<TeeCrmContractProductItem>)TeeJsonUtil.JsonStr2ObjectList(productsListStr , TeeCrmContractProductItem.class);
			for (int i = 0; i < productsList.size(); i++) {
				TeeCrmContractProductItem item = productsList.get(i);
				item.setContract(contract);
				item.setContractSignDate(contract.getContractSignDate());//合同的签约时间
				contractProductItemDao.save(item);
			}
		}
		
		/**
		 * 新建/更新回款信息
		 */
		String recvPaymentListStr = TeeStringUtil.getString(request.getParameter("recvPaymentListStr"));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if(!recvPaymentListStr.equals("")){
			List<Map<String, String>>  productsList= TeeJsonUtil.JsonStr2MapList(recvPaymentListStr);
			for (int i = 0; i < productsList.size(); i++) {
				Map<String, String> item = productsList.get(i);
				TeeCrmContractRecvPayments recvPayments = null;
				int recvPaymentSid = TeeStringUtil.getInteger(item.get("recvPaymentSid"), 0);
				if(recvPaymentSid > 0){
					recvPayments = contractRecvPaymentsDao.get(recvPaymentSid);
				}
				if(recvPayments == null){
					recvPayments = new TeeCrmContractRecvPayments();
				}
				recvPayments.setContract(contract);
				
				if(!TeeUtility.isNullorEmpty(item.get("inviceDate"))){//发票日期
					Date inviceDate = format.parse(item.get("inviceDate"));
					recvPayments.setInviceDate(inviceDate);
				}
				if(!TeeUtility.isNullorEmpty(item.get("planRecvDate"))){//计划签订日期
					Date planRecvDate = format.parse(item.get("planRecvDate"));
					recvPayments.setPlanRecvDate(planRecvDate);
				}
				if(!TeeUtility.isNullorEmpty(item.get("recvDate"))){//实际签订日期
					Date recvDate = format.parse(item.get("recvDate"));
					recvPayments.setRecvDate(recvDate);
				}
				recvPayments.setMakeInvice(item.get("makeInvice"));//是否要发票
				recvPayments.setInviceNumber(item.get("inviceNumber"));//发票编号
				recvPayments.setRemark(item.get("remark"));//备注
				Double recvPayAmount = 0d;
				if(!TeeUtility.isNullorEmpty(item.get("recvPayAmount"))){
					recvPayAmount = Double.parseDouble(item.get("recvPayAmount"));
				}
				recvPayments.setRecvPayAmount(recvPayAmount);//回款金额
				Double recvPayParcent = 0d;
				if(!TeeUtility.isNullorEmpty(item.get("recvPayParcent"))){
					recvPayParcent = Double.parseDouble(item.get("recvPayParcent"));
				}
				recvPayments.setRecvPayParcent(recvPayParcent);//回款百分比
				recvPayments.setRemark(item.get("remark"));//备注
				recvPayments.setRecvPayPerson(item.get("recvPayPerson"));//回款人
				if(recvPayments.getSid() > 0){
					contractRecvPaymentsDao.update(recvPayments);
				}else{
					recvPayments.setCreateTime(new Date());
					recvPayments.setRecvPaymentFlag("0");
					recvPayments.setManagerUser(responsibleUser);
					contractRecvPaymentsDao.save(recvPayments);
				}
				//发送回款消息
				contractRecvPaymentsService.sendQuartzTask(recvPayments);
			}
		}
		json.setRtState(true);
		
		//记录日志
		StringBuffer logModel = parseLogModel(contract);
		desc.append(logModel);
		TeeSysLog sysLog = TeeSysLog.newInstance();
        sysLog.setType(logType);
        sysLog.setRemark(desc.toString());
        TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
	
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
	 * 合同管理 通用列表
	 * @param dm
	 * @param request
	 * @param person
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson manager(TeeDataGridModel dm,HttpServletRequest request,TeePerson person) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeeCrmContractModel model = new TeeCrmContractModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		String hql = "from TeeCrmContract where 1 =1 ";
		List<TeeCrmContractModel> modelList = new ArrayList<TeeCrmContractModel>();
		
		Map map = TeeServletUtility.getParamMap(request);
		map.put(TeeConst.LOGIN_USER, person);
		String shareFlag = (String)map.get("shareFlag");
		List paraList = new ArrayList();
		if("1".equals(shareFlag)){
			hql = " from TeeCrmContract contract where 1 =1 and createUser.uuid <> " + person.getUuid() +  " and exists(select 1 from contract.customerInfo.sharePerson sharePerson where sharePerson.uuid="+person.getUuid()+")";
		}else{
			if(TeePersonService.checkIsSuperAdmin(person, "")){//管理员
				hql = " from TeeCrmContract contract  where 1=1 ";
			}else{
				//数据管理权限
				TeeManagerPostPersonDataPrivModel dataPrivModel = personManagerI.getManagerPostPersonIdsPriv(map, TeeModelIdConst.CRM_CUSTOMER_CONTRACT_MANAGER_POST_PRIV, "0");
				if(dataPrivModel.getPrivType().equals("0")){//空
					paraList.add(person.getUuid());
					hql = hql + " and createUser.uuid = ?";//加上自己创建的
				}else if(dataPrivModel.getPrivType().equals("ALL")){//所有
					// hql = "from TeeNews n where  1 = 1";
				}else{
					List<Integer> pIdList = dataPrivModel.getPersonIds();//获取权限
					pIdList.add(person.getUuid());
					if(pIdList.size() > 0){
						String personIdsSql =  TeeDbUtility.IN("createUser.uuid", pIdList);
						hql = hql + " and " + personIdsSql;
					}else{
						j.setTotal(0L);
						j.setRows(modelList);// 设置返回的行
						return j;
					}
				}
			}
		}
		
		

		if(!TeeUtility.isNullorEmpty(model.getContractNo())){
			paraList.add("%" +  model.getContractNo() + "%" );
			hql = hql + " and contractNo like ?";
		}
		if(!TeeUtility.isNullorEmpty(model.getContractName())){
			paraList.add("%" + model.getContractName() + "%");
			hql = hql + " and contractName like ?";
		}
		if(!TeeUtility.isNullorEmpty(model.getContractCode())){//合同类型
			paraList.add( model.getContractCode() );
			hql = hql + " and contractCode = ?";
		}
		if(!TeeUtility.isNullorEmpty(model.getContractStatus())){//合同状态
			paraList.add( model.getContractStatus() );
			hql = hql + " and contractStatus = ?";
		}
		
		String totalHql = " select count(*) " + hql;
		j.setTotal(crmContractDao.countByList(totalHql, paraList));// 设置总记录数
		if (dm.getSort() != null) {// 设置排序
			hql += " order by " + dm.getSort() + " "+dm.getOrder();
		}else{
			hql += " order by createTime desc" ;
		}
		
		List<TeeCrmContract> contactList = crmContractDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(),paraList);// 查询
		if (contactList != null && contactList.size() > 0) {
			for (TeeCrmContract contract : contactList) {
				TeeCrmContractModel tempModel = parseModel(contract , true);
				modelList.add(tempModel);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	/**
	 * 将对象转换日志模型字符串
	 * @param contract
	 * @return
	 */
	public StringBuffer parseLogModel(TeeCrmContract contract ){
		StringBuffer sb = new StringBuffer("{");
		sb.append("产品名称：" + contract.getContractName());
		sb.append(",产品编号：" + contract.getContractNo());
		
		//客户
		TeeCrmCustomerInfo customerInfo = contract.getCustomerInfo();
		String customerInfoName = "";
		if(customerInfo != null ){
			customerInfoName = customerInfo.getCustomerName();
		}
		sb.append(",客户名称：" + customerInfoName);
		sb.append("}" );
		return sb;
	}

	/**
	 * 转换模型
	 * @param contract
	 * @return
	 */
	public TeeCrmContractModel parseModel(TeeCrmContract contract , boolean isSimple){
		TeeCrmContractModel model = new TeeCrmContractModel();
		if(contract ==null){
			return null;
		}

		BeanUtils.copyProperties(contract, model);
		TeePerson responsibleUser = contract.getResponsibleUser();//负责人
		String responsibleUserId = "";
		String responsibleUserName = "";
		if(responsibleUser != null){
			responsibleUserId = responsibleUser.getUuid() + "";
			responsibleUserName = responsibleUser.getUserName();
		}
		
		if(contract.getCreateUser() != null){
			model.setCreateUserId(contract.getCreateUser().getUuid() + "");
			model.setCreateUserName(contract.getCreateUser().getUserName());
		}
		
		
		model.setResponsibleUserId(responsibleUserId);
		model.setResponsibleUserName(responsibleUserName);
		String CURRENCY_TYPE = TeeCrmCodeManager.getChildSysCodeNameCodeNo("CURRENCY_TYPE",contract.getCurrencyType());//货币类型
		model.setCurrencyTypeDesc(CURRENCY_TYPE);
		String CONTRACT_STATUS = TeeCrmCodeManager.getChildSysCodeNameCodeNo("CONTRACT_STATUS",contract.getContractStatus());//合同状态
		model.setContractStatusDesc(CONTRACT_STATUS);
		String CONTRACT_CODE = TeeCrmCodeManager.getChildSysCodeNameCodeNo("CONTRACT_CODE",contract.getContractCode());//合同类型 
		model.setContractCodeDesc(CONTRACT_CODE);
		
		String ACCOUNTS_METHOD = TeeCrmCodeManager.getChildSysCodeNameCodeNo("ACCOUNTS_METHOD",contract.getAccountsMethod());//结算方式
		model.setAccountsMethodDesc(ACCOUNTS_METHOD);
		
		String paymentTypeDes  = "一次性支付";
		if(contract.getPaymentMethod().equals("1")){
			paymentTypeDes = "多次支付";
		}
		model.setPaymentMethodDesc(paymentTypeDes);
		
		
		if(!TeeUtility.isNullorEmpty(contract.getContractSignDate())){
			model.setContractSignDateStr(TeeUtility.getDateTimeStr(contract.getContractSignDate(), new SimpleDateFormat("yyyy-MM-dd")));
		}
		if(!TeeUtility.isNullorEmpty(contract.getContractStartDate())){
			model.setContractStartDateStr(TeeUtility.getDateTimeStr(contract.getContractStartDate(), new SimpleDateFormat("yyyy-MM-dd")));
		}
		if(!TeeUtility.isNullorEmpty(contract.getContractEndDate())){
			model.setContractEndDateStr(TeeUtility.getDateTimeStr(contract.getContractEndDate(), new SimpleDateFormat("yyyy-MM-dd")));
		}

		//客户
		TeeCrmCustomerInfo customerInfo = contract.getCustomerInfo();
		String customerInfoId = "";
		String customerInfoName = "";
		if(customerInfo != null ){
			customerInfoId = customerInfo.getSid() + "";
			customerInfoName = customerInfo.getCustomerName();
		}
		model.setCustomerInfoId(customerInfoId);
		model.setCustomerInfoName(customerInfoName);
	
		
		//产品明细
		if(!isSimple){
			List<TeeCrmContractProductItemModel> productItemModel = contractProductItemService.getProductItemModelList(contract.getSid());
			model.setProductItemModel(productItemModel);
			
			
			List<TeeCrmContractRecvPaymentsModel> recvPaymentModel = contractRecvPaymentsService.getRecvMentsModelList(contract.getSid());
			model.setRecvPaymentModel(recvPaymentModel);
			
			
			List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.CRM_CUSTOMER_CANTRACT, String.valueOf(contract.getSid()));
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
		}
		return model;
	}
	
	/**
	 * 删除byIds
	 * @param request
	 * @return
	 */
	public TeeJson deleteByIds(HttpServletRequest request  ,String ids){
		TeeJson json = new TeeJson();
		//删除产品明细
		contractProductItemDao.delByContractIds(ids);
		
		//删除回款明细
		contractRecvPaymentsDao.delByContractIds(ids);
		
		//删除合同
		List<TeeCrmContract> contractList = crmContractDao.getContractByIds(ids);
		StringBuffer logSb = new StringBuffer("[");
		for (int i = 0; i < contractList.size(); i++) {
			StringBuffer temp = parseLogModel(contractList.get(i));
			logSb.append(temp);
			logSb.append(",");
		}
		crmContractDao.deleteContract(ids);
		//记录日志
		TeeSysLog sysLog = TeeSysLog.newInstance();
		logSb.append("]");
        sysLog.setType("040C");
  	  	sysLog.setRemark(logSb.toString());
        TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		json.setRtState(true);
		return json;
	}
	
	/**
	 *  getById
	 * @param request
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int ids = TeeStringUtil.getInteger(request.getParameter("sid") ,0 );
		TeeCrmContract contract = crmContractDao.get(ids);
		if(contract == null){
			json.setRtMsg("该数据已被删除！");
		}else{
			TeeCrmContractModel model = parseModel(contract ,false);
			json.setRtState(true);
			json.setRtData(model);
		}
		return json;
	}
	

	
	/**
	 * 合同年汇总表
	 * @param map
	 * @param person
	 * @return
	 */
	public TeeJson statisticsYearCollect(Map map , TeePerson person){
		TeeJson json = new TeeJson();
		Calendar cal = Calendar.getInstance();
		int year = TeeStringUtil.getInteger(map.get("year"), cal.get(Calendar.YEAR));
		List<TeeCrmContract> list = crmContractDao.statisticsYearCollect(year, person);
		List<Map> modelList = new ArrayList<Map>();
		Map map1 = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			TeeCrmContract contract = list.get(i);
			Date temp = contract.getContractSignDate();//合同签订日期
			
		}
		return json;
	}
}
