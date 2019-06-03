package com.tianee.oa.subsys.crm.core.customer.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.subsys.crm.core.base.dao.TeeCrmAfterSaleServDao;
import com.tianee.oa.subsys.crm.core.contract.bean.TeeCrmContract;
import com.tianee.oa.subsys.crm.core.contract.dao.TeeCrmContractDao;
import com.tianee.oa.subsys.crm.core.contract.service.TeeCrmContractService;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmContactUser;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomerInfo;
import com.tianee.oa.subsys.crm.core.customer.dao.TeeCrmContactUserDao;
import com.tianee.oa.subsys.crm.core.customer.dao.TeeCrmCustomerInfoDao;
import com.tianee.oa.subsys.crm.core.customer.dao.TeeCrmSaleFollowDao;
import com.tianee.oa.subsys.crm.core.customer.model.TeeCrmCustomerInfoModel;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.subsys.crm.setting.dao.TeeChinaCityDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;


@Service
public class TeeCrmCustomerInfoService extends TeeBaseService{
	@Autowired
	private TeeCrmCustomerInfoDao customerInfoDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeCrmContactUserDao contactUserDao;
	
	@Autowired
	private TeeChinaCityDao cityDao;
	
	@Autowired
	private TeeCrmSaleFollowDao crmSaleFollowDao;
	
	@Autowired
	private TeeCrmSaleFollowService crmSaleFollowService;
	
	@Autowired
	private TeeCrmContractService contractService;
	
	@Autowired
	private TeeCrmContractDao contractDao;
	
	@Autowired
	private TeeCrmAfterSaleServDao afterSaleServDao;

	/**
	 * 保存或更新
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeCrmCustomerInfoModel model) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)personDao.get(model.getManagePersonId());
		TeeCrmCustomerInfo customerInfo = new TeeCrmCustomerInfo();
		int[] sharePersonIds = TeeStringUtil.parseIntegerArray(model.getSharePersonIds());
		List<TeePerson> personList = personDao.getPersonByUuids(TeeStringUtil.getString(model.getSharePersonIds(), "0"));
		if(model.getSid() > 0){
		    customerInfo  = customerInfoDao.getById(model.getSid());
			if(customerInfo != null){
				BeanUtils.copyProperties(model, customerInfo);
				customerInfo.setManagePerson(person);
				if(personList != null && personList.size()>0){
					customerInfo.getSharePerson().clear();
					customerInfo.getSharePerson().addAll(personList);
				}else{
					customerInfo.setSharePerson(new HashSet<TeePerson>());
				}
				
				
				/*
				for(int uuid:sharePersonIds){
					if(uuid==0){
						continue;
					}
					customerInfo.getSharePerson().add((TeePerson)personDao.get(uuid));
				}
				*/
				customerInfoDao.updateCustomerInfo(customerInfo);
				//记录日志
				StringBuffer remark=new StringBuffer();
				remark.append("客户名称：\""+customerInfo.getCustomerName()+"\"");
				TeeSysLog sysLog = TeeSysLog.newInstance();
		        sysLog.setType("044B");
		  	  	sysLog.setRemark(remark.toString());
		        TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
				String ids = request.getParameter("idsTemp");
				contactUserDao.delByNotInSids(ids,model.getSid());
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关外出信息！");
				return json;
			}
		}else{
			Calendar cl = Calendar.getInstance();
			BeanUtils.copyProperties(model, customerInfo);
			customerInfo.setAddTime(cl);
			customerInfo.setManagePerson(person);
			if(personList != null && personList.size()>0){
				customerInfo.getSharePerson().clear();
				customerInfo.getSharePerson().addAll(personList);
			}else{
				customerInfo.setSharePerson(new HashSet<TeePerson>());
			}
			/*
			for(int uuid:sharePersonIds){
				if(uuid==0){
					continue;
				}
				customerInfo.getSharePerson().add((TeePerson)personDao.get(uuid));
			}
			*/
			customerInfoDao.addCustomerInfo(customerInfo);
			//记录日志
			StringBuffer remark=new StringBuffer();
			remark.append("客户名称：\""+customerInfo.getCustomerName()+"\"");
			TeeSysLog sysLog = TeeSysLog.newInstance();
	        sysLog.setType("044A");
	  	  	sysLog.setRemark(remark.toString());
	        TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		}
		
		/**
		 * 处理联系人
		 */
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		String contactUserListStr = request.getParameter("contactUserList");
		if(!contactUserListStr.equals("")){
			List<TeeCrmContactUser>  contactUserList= (List<TeeCrmContactUser>)TeeJsonUtil.JsonStr2ObjectList(contactUserListStr , TeeCrmContactUser.class);
			for (int i = 0; i < contactUserList.size(); i++) {
				TeeCrmContactUser item = contactUserList.get(i);
				item.setCustomer(customerInfo);
				item.setAddPerson(loginPerson);
				if(item.getSid()>0){
					contactUserDao.update(item);
					//记录日志
					StringBuffer remark=new StringBuffer();
					remark.append("客户名称：\""+customerInfo.getCustomerName()+"\",联系人姓名：\""+item.getName()+"\"");
					TeeSysLog sysLog = TeeSysLog.newInstance();
			        sysLog.setType("045B");
			  	  	sysLog.setRemark(remark.toString());
			        TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
				}else{
					contactUserDao.save(item);
					//记录日志
					StringBuffer remark=new StringBuffer();
					remark.append("客户名称：\""+customerInfo.getCustomerName()+"\",联系人姓名：\""+item.getName()+"\"");
					TeeSysLog sysLog = TeeSysLog.newInstance();
			        sysLog.setType("045A");
			  	  	sysLog.setRemark(remark.toString());
			        TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
				}
			}
		}
		
		
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
	}

	/**
	 * 对象转换
	 * @author nieyi
	 * @param customerInfo
	 * @return
	 */
	public TeeCrmCustomerInfoModel parseModel(TeeCrmCustomerInfo customerInfo){
		TeeCrmCustomerInfoModel model = new TeeCrmCustomerInfoModel();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(customerInfo == null){
			return null;
		}
		BeanUtils.copyProperties(customerInfo, model);
		if(!TeeUtility.isNullorEmpty(customerInfo.getManagePerson())){
			model.setManagePersonId(customerInfo.getManagePerson().getUuid());
			model.setManagePersonName(customerInfo.getManagePerson().getUserName());
		}
		String sharePersonIds="";
		String sharePersonNames="";
		if(!TeeUtility.isNullorEmpty(customerInfo.getSharePerson())){
			for(TeePerson person:customerInfo.getSharePerson()){
				sharePersonIds += person.getUuid()+",";
				sharePersonNames+=person.getUserName()+",";
			}
		}
		
		if(!TeeUtility.isNullorEmpty(customerInfo.getProvince())){
			String provinceName = cityDao.getNameByCityCode(customerInfo.getProvince());
			model.setProvinceName(provinceName);
		}
		
		if(!TeeUtility.isNullorEmpty(customerInfo.getCity())){
			String cityName = cityDao.getNameByCityCode(customerInfo.getCity());
			model.setCityName(cityName);
		}
		
		if(!TeeUtility.isNullorEmpty(customerInfo.getCounty())){
			String countyName = cityDao.getNameByCityCode(customerInfo.getCounty());
			model.setCountyName(countyName);
		}
		if(sharePersonIds.endsWith(",")){
			sharePersonIds=sharePersonIds.substring(0, sharePersonIds.length()-1);
		}
		if(sharePersonNames.endsWith(",")){
			sharePersonNames=sharePersonNames.substring(0, sharePersonNames.length()-1);
		}
		if(!TeeUtility.isNullorEmpty(customerInfo.getAddTime())){
			model.setAddTimeDesc(sf.format(customerInfo.getAddTime().getTime()));
		}
		model.setSharePersonIds(sharePersonIds);
		model.setSharePersonNames(sharePersonNames);
		String typeDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("CUSTOMER_TYPE",model.getCustomerType());
		String industryDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("INDUSTRY_TYPE",model.getIndustry());
		String customerSourceDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("CUSTOMER_SOURCE",model.getCustomerSource());
		String importantLevelDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("IMPORTANT_LEVEL",model.getImportantLevel());
		String relationLevelDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("RELATION_LEVEL",model.getRelationLevel());
		String salesMarketDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("SALES_MARKET",model.getSalesMarket());
		String sourcesOfInvestmentDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("SOURCE_OF_INVESTMENT",model.getSourcesOfInvestment());
		String trustLevelDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("TRUST_LEVEL",model.getTrustLevel());
		String companyScaleDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("COMPANY_SCALE",model.getCompanyScale());
		model.setUnitTypeDesc(TeeCrmCodeManager.getChildSysCodeNameCodeNo("UNIT_TYPE",model.getUnitType()));
		model.setCustomerTypeDesc(typeDesc);
		model.setIndustryDesc(industryDesc);
		model.setCustomerSourceDesc(customerSourceDesc);
		model.setImportantLevelDesc(importantLevelDesc);
		model.setRelationLevelDesc(relationLevelDesc);
		model.setSourcesOfInvestmentDesc(sourcesOfInvestmentDesc);
		model.setSalesMarketDesc(salesMarketDesc);
		model.setTrustLevelDesc(trustLevelDesc);
		model.setCompanyScaleDesc(companyScaleDesc);
		return model;
	}
	
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson deleteByIdService(HttpServletRequest request,String sids) {
		TeeJson json = new TeeJson();
		
		
		//删除客户跟单记录
		String saleFollowIds = crmSaleFollowDao.getSaleFollowIdsByCustomerIds(sids);
		if(!TeeUtility.isNullorEmpty(saleFollowIds)){
			crmSaleFollowService.deleteByIdService(request, saleFollowIds);
		}
		
		//删除联系人
		contactUserDao.deleteByCustomerInfoId(sids);
		
		//删除合同
		String contractIds = contractDao.getContractIdsByCustomerIds(sids);
		if(!TeeUtility.isNullorEmpty(contractIds)){
			contractService.deleteByIds(request, contractIds);
		}
		
		//删除售后服务
		afterSaleServDao.delByCoustomerIds(sids);
		
		//删除客户
		json.setRtState(true);
		//记录日志
		StringBuffer remark=new StringBuffer("[");
		String[] ids = sids.split(",");
		if(ids.length>0){
			for (int i = 0; i < ids.length; i++) {
				TeeCrmCustomerInfo customerInfo = customerInfoDao.getById(Integer.parseInt(ids[i]));
				remark.append("{客户名称：\""+customerInfo.getCustomerName()+"\"},");
			}
		}
		if(remark.toString().endsWith(",")){
			remark.deleteCharAt(remark.length()-1);
		}
		remark.append("]");
		customerInfoDao.delByIds(sids);
		TeeSysLog sysLog = TeeSysLog.newInstance();
        sysLog.setType("044C");
  	  	sysLog.setRemark(remark.toString());
        TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		json.setRtMsg("删除成功!");
		return json;
	}
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request, TeeCrmCustomerInfoModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeCrmCustomerInfo customerInfo = customerInfoDao.getById(model.getSid());
			if(customerInfo !=null){
				model = parseModel(customerInfo);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关客户记录！");
		return json;
	}

	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm, Map requestDatas) {
		return customerInfoDao.datagird(dm, requestDatas);
	}

	/**
	 * 领用公海客户
	 * @param sid
	 * @return
	 */
	public TeeJson collarCustomer(HttpServletRequest request,int sid) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		TeeCrmCustomerInfo customer = (TeeCrmCustomerInfo)customerInfoDao.get(sid);
		if(customer!=null){
			customer.setManagePerson(person);
			customerInfoDao.update(customer);
			json.setRtState(true);
			json.setRtMsg("领用成功！");
			return json;
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关客户记录！");
		return json;
	}

	public List<TeeCrmCustomerInfoModel> getTotalByConditon(Map requestDatas) {
		return customerInfoDao.getTotalByConditon(requestDatas);
	}
}