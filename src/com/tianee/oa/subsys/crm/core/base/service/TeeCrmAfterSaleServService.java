package com.tianee.oa.subsys.crm.core.base.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.base.bean.TeeCrmAfterSaleServ;
import com.tianee.oa.subsys.crm.core.base.dao.TeeCrmAfterSaleServDao;
import com.tianee.oa.subsys.crm.core.base.model.TeeCrmAfterSaleServModel;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomerInfo;
import com.tianee.oa.subsys.crm.core.customer.dao.TeeCrmCustomerInfoDao;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeCrmAfterSaleServService extends TeeBaseService {
	@Autowired
	private TeeCrmAfterSaleServDao dao;

	@Autowired
	private TeeCrmCustomerInfoDao customerInfoDao;

	@Autowired
	private TeePersonDao personDao;

	@Autowired
	private TeeAttachmentDao attachmentDao;

	@Autowired
	private TeeAttachmentService attachmentService;

	/**
	 * @function: 新建或编辑
	 * @author:
	 * @data: 2014年8月29日
	 * @param request
	 * @param model
	 * @return TeeJson
	 */
	public TeeJson addOrUpdateService(HttpServletRequest request, TeeCrmAfterSaleServModel model) {
		TeeSysLog sysLog = TeeSysLog.newInstance();
		StringBuffer buffer = new StringBuffer();
		TeeJson json = new TeeJson();
		try {
			TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			List<TeeAttachment> attachmentsList = new ArrayList<TeeAttachment>();
			if (!TeeUtility.isNullorEmpty(model.getAttacheIds())) {
				attachmentsList = attachmentService.getAttachmentsByIds(model.getAttacheIds());
			}
			if (model.getSid() > 0) {
				TeeCrmAfterSaleServ obj = dao.get(model.getSid());
				if (obj != null) {
					this.parseObj(model, obj);
					dao.update(obj);
					if (attachmentsList != null && attachmentsList.size() > 0) {
						for (TeeAttachment attach : attachmentsList) {
							attach.setModelId(String.valueOf(obj.getSid()));
							simpleDaoSupport.update(attach);
						}
					}
					sysLog.setType("048B");
					buffer.append("编辑成功,{服务编号：" + obj.getServiceCode() + ",客户名称：" + obj.getCustomer().getCustomerName() + "}");
				}
			} else {
				TeeCrmAfterSaleServ obj = new TeeCrmAfterSaleServ();
				BeanUtils.copyProperties(model, obj);
				this.parseObj(model, obj);
				obj.setCreateUser(person);
				obj.setCreateTime(new Date());
				dao.save(obj);
				if (attachmentsList != null && attachmentsList.size() > 0) {
					for (TeeAttachment attach : attachmentsList) {
						attach.setModelId(String.valueOf(obj.getSid()));
						simpleDaoSupport.update(attach);
					}
				}
				sysLog.setType("048A");
				buffer.append("新建成功,{服务编号：" + obj.getServiceCode() + ",客户名称：" + obj.getCustomer().getCustomerName() + "}");
			}
			sysLog.setRemark(buffer.toString());
			TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
			json.setRtState(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * @function: 转换成保存对象
	 * @author: wyw
	 * @data: 2014年8月29日
	 * @param obj
	 * @return TeeCrmAfterSaleServModel
	 */
	public TeeCrmAfterSaleServ parseObj(TeeCrmAfterSaleServModel model, TeeCrmAfterSaleServ obj) {
		if (model == null) {
			return obj;
		}
		try {
			BeanUtils.copyProperties(model, obj);
			obj.setServiceCode(model.getServiceCode());

			TeeCrmCustomerInfo customer = customerInfoDao.get(model.getCustomerInfoId());
			obj.setCustomer(customer);
			obj.setContactUserId(model.getContactUserId());
			obj.setContactUserName(model.getContactUserName());
			obj.setServiceType(model.getServiceType());
			obj.setEmergencyDegree(model.getEmergencyDegree());

			TeePerson accteptUser = personDao.get(model.getAccteptUserId());
			obj.setAccteptUser(accteptUser);
			if (TeeUtility.isDayTime(model.getAcceptDatetimeStr())) {
				obj.setAcceptDatetime(TeeUtility.parseDate(model.getAcceptDatetimeStr()));
			}
			obj.setQuestionDesc(model.getQuestionDesc());
			obj.setHandleUser(personDao.get(model.getHandleUserId()));
			if (TeeUtility.isDayTime(model.getHandleDatetimeStr())) {
				obj.setHandleDatetime(TeeUtility.parseDate(model.getHandleDatetimeStr()));
			}
			obj.setHandleDesc(obj.getHandleDesc());
			obj.setFeedback(model.getFeedback());
			obj.setHandleStatus(model.getHandleStatus());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * @function: 管理列表
	 * @author: wyw
	 * @data: 2014年8月29日
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException
	 *             TeeEasyuiDataGridJson
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getManageInfoList(TeeDataGridModel dm, HttpServletRequest request, TeeCrmAfterSaleServModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

		Map requestDatas = TeeServletUtility.getParamMap(request);
		String serviceCode = (String) requestDatas.get("serviceCode");
		String handleStatus = (String) requestDatas.get("handleStatus");
		String customerInfoId = (String) requestDatas.get("customerInfoId");
		String contactUserId = (String) requestDatas.get("contactUserId");
		String serviceType = (String) requestDatas.get("serviceType");
		String emergencyDegree = (String) requestDatas.get("emergencyDegree");
		String createTimeStrMin = (String) requestDatas.get("createTimeStrMin");
		String createTimeStrMax = (String) requestDatas.get("createTimeStrMax");
		String shareFlag = (String) requestDatas.get("shareFlag");//共享标识；1-查看客户共享数据；2-综合查询

		
		String hql = "";
		if("1".equals(shareFlag)){//共享
			hql = " from TeeCrmAfterSaleServ require where require.createUser.uuid <> " + loginPerson.getUuid() +  " and exists(select 1 from require.customer.sharePerson sharePerson where sharePerson.uuid="+loginPerson.getUuid()+")";
		}else{
			if(TeePersonService.checkIsSuperAdmin(loginPerson, "") || "2".equals(shareFlag)){//管理员
				hql = " from TeeCrmAfterSaleServ require where 1=1 ";
			}else{//个人
				hql = " from TeeCrmAfterSaleServ require where require.createUser.uuid= " + loginPerson.getUuid();
			}
		}
		
		
		List param = new ArrayList();
		if (!TeeUtility.isNullorEmpty(serviceCode)) {
			hql += " and require.serviceCode like ?";
			param.add("%" + serviceCode + "%");
		}
		if (!TeeUtility.isNullorEmpty(handleStatus)) {
			hql += " and require.handleStatus =?";
			param.add(handleStatus);
		}
		if (!TeeUtility.isNullorEmpty(customerInfoId)) {
			hql += " and require.customer.sid =?";
			param.add(Integer.parseInt(customerInfoId));
		}
		if (!TeeUtility.isNullorEmpty(contactUserId)) {
			hql += " and require.contactUserId like ?";
			param.add(Integer.parseInt(contactUserId));
		}
		if (!TeeUtility.isNullorEmpty(serviceType)) {
			hql += " and require.serviceType like ?";
			param.add(serviceType);
		}
		if (!TeeUtility.isNullorEmpty(emergencyDegree)) {
			hql += " and require.emergencyDegree like ?";
			param.add(emergencyDegree);
		}

		if (!TeeUtility.isNullorEmpty(createTimeStrMin)) {
			hql += " and require.createTime >= ?";
			param.add(TeeDateUtil.parseDate("yyyy-MM-dd hh:mm:ss", createTimeStrMin + " 00:00:00"));
		}
		if (!TeeUtility.isNullorEmpty(createTimeStrMax)) {
			hql += " and require.createTime <= ?";
			param.add(TeeDateUtil.parseDate("yyyy-MM-dd hh:mm:ss", createTimeStrMax + " 23:59:59"));
		}

		j.setTotal(dao.countByList("select count(*) " + hql, param));// 设置总记录数

		hql += " order by require.createTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeCrmAfterSaleServ> list = dao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeCrmAfterSaleServModel> modelList = new ArrayList<TeeCrmAfterSaleServModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeCrmAfterSaleServModel modeltemp = parseReturnModel(list.get(i), false);
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * @function: 转换成返回对象
	 * @author: wyw
	 * @data: 2014年8月29日
	 * @param obj
	 * @return TeeCrmAfterSaleServModel
	 */
	public TeeCrmAfterSaleServModel parseReturnModel(TeeCrmAfterSaleServ obj, boolean isEdit) {
		TeeCrmAfterSaleServModel model = new TeeCrmAfterSaleServModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);
		model.setServiceCode(obj.getServiceCode());
		if (obj.getCustomer() != null) {
			model.setCustomerInfoId(obj.getCustomer().getSid());
			model.setCustomerInfoName(obj.getCustomer().getCustomerName());
		}
		model.setContactUserId(obj.getContactUserId());
		model.setContactUserName(model.getContactUserName());

		// 从缓存中获取
		String serviceTypeName = TeeCrmCodeManager.getChildSysCodeNameCodeNo("CUSTOMER_SERVICE_TYPE", model.getServiceType());// 紧急程度
		model.setServiceTypeName(serviceTypeName);
		// 从缓存中获取
		String companyScaleDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("CUSTOMER_SERVICE_EMERGENCY", model.getEmergencyDegree());// 紧急程度
		model.setEmergencyDegree(companyScaleDesc);
		if (obj.getAccteptUser() != null) {
			model.setAccteptUserId(obj.getAccteptUser().getUuid());
			model.setAccteptUserName(obj.getAccteptUser().getUserName());
		} else {
			model.setAccteptUserName("");
		}
		if (!TeeUtility.isNullorEmpty(obj.getAcceptDatetime())) {
			model.setAcceptDatetimeStr(TeeUtility.getDateStrByFormat(obj.getAcceptDatetime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
		} else {
			model.setAcceptDatetimeStr("");
		}
		model.setQuestionDesc(obj.getQuestionDesc());
		if (obj.getHandleUser() != null) {
			model.setHandleUserId(obj.getHandleUser().getUuid());
			model.setHandleUserName(obj.getHandleUser().getUserName());
		} else {
			model.setHandleUserName("");
		}
		if (!TeeUtility.isNullorEmpty(obj.getHandleDatetime())) {
			model.setHandleDatetimeStr(TeeUtility.getDateStrByFormat(obj.getHandleDatetime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
		} else {
			model.setHandleDatetimeStr("");
		}
		model.setHandleStatus(obj.getHandleStatus());
		model.setHandleDesc(obj.getHandleDesc());
		model.setFeedback(obj.getFeedback());
		if (obj.getCreateUser() != null) {
			model.setCreateUserId(obj.getCreateUser().getUuid());
			model.setCreateUserName(obj.getCreateUser().getUserName());
		}
		if (!TeeUtility.isNullorEmpty(obj.getCreateTime())) {
			model.setCreateTimeStr(TeeUtility.getDateStrByFormat(obj.getCreateTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
		}

		List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.CRM_AFTER_SALE_SERV, String.valueOf(obj.getSid()));
		List<TeeAttachmentModel> attacheModels = new ArrayList<TeeAttachmentModel>();
		StringBuffer attacheIdBuffer = new StringBuffer();
		for (TeeAttachment attach : attaches) {
			TeeAttachmentModel m = new TeeAttachmentModel();
			BeanUtils.copyProperties(attach, m);
			m.setUserId(attach.getUser().getUuid() + "");
			m.setUserName(attach.getUser().getUserName());
			int priv = 1 + 2;
			if (isEdit) {
				priv = 1 + 2 + 4;
			}
			m.setPriv(priv);// 一共五个权限好像
							// 1、2、4、8、16、32,具体权限值含义可以参考TeeAttachmentConst
			attacheModels.add(m);
			if (attacheIdBuffer.length() > 1) {
				attacheIdBuffer.append(",");
			}
			attacheIdBuffer.append(attach.getSid());
		}
		model.setAttacheModels(attacheModels);
		model.setAttacheIds(attacheIdBuffer.toString());
		return model;
	}

	/**
	 * @function: 根据sid查看详情
	 * @author: wyw
	 * @data: 2014年8月29日
	 * @param request
	 * @param model
	 * @return TeeJson
	 */
	public TeeJson getInfoById(HttpServletRequest request, TeeCrmAfterSaleServModel model) {
		TeeJson json = new TeeJson();
		String isEdit = request.getParameter("isEdit");
		boolean editFlag = false;
		if ("1".equals(isEdit)) {
			editFlag = true;
		}
		if (model.getSid() > 0) {
			TeeCrmAfterSaleServ obj = dao.get(model.getSid());
			if (obj != null) {
				model = parseReturnModel(obj, editFlag);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		return json;
	}

	/**
	 * @function: 删除
	 * @author: wyw
	 * @data: 2014年8月30日
	 * @param sids
	 * @return TeeJson
	 */
	public TeeJson deleteObjById(String sids) {
		if(TeeUtility.isNullorEmpty(sids)){
			sids = "0";
		}
		if(sids.endsWith(",")){
			sids = sids.substring(0,sids.length()-1);
		}
		TeeJson json = new TeeJson();
		TeeSysLog sysLog = TeeSysLog.newInstance();
		sysLog.setType("048C");
		StringBuffer buffer = new StringBuffer();
		Object values[]={};
		String hql = " from TeeCrmAfterSaleServ where sid in (" + sids + ")";
		List<TeeCrmAfterSaleServ> list = dao.executeQuery(hql, values);
		if(list!= null && list.size()>0){
			for(TeeCrmAfterSaleServ obj:list){
				dao.deleteByObj(obj);
				if(buffer.length()>0){
					buffer.append(",");
				}
				buffer.append("{服务编号：" + obj.getServiceCode() + ",客户名称：" + obj.getCustomer().getCustomerName() + "}");
			}
		}
		sysLog.setRemark("删除文件:[" +buffer.toString() + "]");
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}

}
