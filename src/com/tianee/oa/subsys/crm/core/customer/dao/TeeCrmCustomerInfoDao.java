package com.tianee.oa.subsys.crm.core.customer.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomerInfo;
import com.tianee.oa.subsys.crm.core.customer.model.TeeCrmCustomerInfoModel;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.str.TeeUtility;


@Repository("TeeCrmCustomerInfoDao")
public class TeeCrmCustomerInfoDao extends TeeBaseDao<TeeCrmCustomerInfo>{
	/**
	 * @author nieyi
	 * @param customerInfo
	 */
	public void addCustomerInfo(TeeCrmCustomerInfo customerInfo) {
		save(customerInfo);
	}
	
	/**
	 * @author nieyi
	 * @param customerInfo
	 */
	public void updateCustomerInfo(TeeCrmCustomerInfo customerInfo) {
		update(customerInfo);
	}
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeCrmCustomerInfo loadById(int id) {
		TeeCrmCustomerInfo intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeCrmCustomerInfo getById(int id) {
		TeeCrmCustomerInfo intf = get(id);
		return intf;
	}
	
	
	/**
	 * @author nieyi
	 * @param id
	 */
	public void delById(int id) {
		delete(id);
	}
	
	
	/**
	 * @author nieyi
	 * @param ids
	 */
	public void delByIds(String ids){
		
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids= ids.substring(0, ids.length() -1 );
			}
			String hql = "delete from TeeCrmCustomerInfo where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
			executeUpdate("delete from TeeRunCustomerRel where customerInfo.sid in (" + ids + ")", null);
		}
	}
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeePerson person = (TeePerson)requestDatas.get("LOGIN_USER");
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String industry=(String)requestDatas.get("industry");
		String customerName=(String)requestDatas.get("customerName");
		String customerType=(String)requestDatas.get("customerType");
		String customerSource=(String)requestDatas.get("customerSource");
		String cityCode=(String)requestDatas.get("cityCode");
		String type=(String)requestDatas.get("type");//type=2 代表所有的客户，type =1 共享客户  没有type代表我的客户
		String fromTime=(String)requestDatas.get("fromTime");
		String toTime=(String)requestDatas.get("toTime");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List param = new ArrayList();
		String hql = "from TeeCrmCustomerInfo customer where 1=1";
		
		if(!TeeUtility.isNullorEmpty(industry) && !"0".equals(industry)){
			hql+=" and customer.industry=?";
			param.add(industry);
		}
		if(!TeeUtility.isNullorEmpty(customerName)){
			hql+=" and customer.customerName like ?";
			param.add("%"+customerName+"%");
		}
		if(!TeeUtility.isNullorEmpty(customerType) && !"0".equals(customerType)){
			hql+=" and customer.customerType = ?";
			param.add(customerType);
		}
		if(!TeeUtility.isNullorEmpty(customerSource) && !"0".equals(customerSource)){
			hql+=" and customer.customerSource = ?";
			param.add(customerSource);
		}
		if(!TeeUtility.isNullorEmpty(cityCode)){
			if(cityCode.endsWith("0000")){
				hql+=" and customer.province = ?";
				param.add(cityCode);
			}else if(cityCode.endsWith("00") && !cityCode.endsWith("0000")){
				hql+=" and customer.city = ?";
				param.add(cityCode);
			}else{
				hql+=" and customer.county = ?";
				param.add(cityCode);
			}
		}
		if(!TeeUtility.isNullorEmpty(fromTime)){
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sf.parse(fromTime+" 00:00:00"));
				hql+=" and customer.addTime >= ?";
				param.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		if(!TeeUtility.isNullorEmpty(toTime)){
			Calendar t = Calendar.getInstance();
			try {
				t.setTime(sf.parse(toTime+" 23:59:59"));
				hql+=" and customer.addTime <= ?";
				param.add(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		
		if(!TeeUtility.isNullorEmpty(type)){
			if(type.equals("1")){//type =1 共享客户  
				hql +=" and exists (select 1 from customer.sharePerson sharePerson where sharePerson.uuid = "+person.getUuid()+"))";
			}else if(type.equals("2")){//type=2 代表所有的客户
				hql +="";
			}else if(type.equals("3")){//type=3代表公海客户
				hql +=" and customer.managePerson.uuid is null";
			}
		}else{//  没有type代表我的客户
			hql +=" and (customer.managePerson.uuid="+person.getUuid()+" or exists (select 1 from customer.sharePerson sharePerson where sharePerson.uuid = "+person.getUuid()+"))";
		}
		hql += " order by customer.addTime desc";
		List<TeeCrmCustomerInfo> infos = pageFindByList(hql, dm.getFirstResult(), dm.getRows(), param);
		long total = countByList("select count(*) "+hql, param);
		List<TeeCrmCustomerInfoModel> models = new ArrayList<TeeCrmCustomerInfoModel>();
		for(TeeCrmCustomerInfo customerInfo:infos){
			TeeCrmCustomerInfoModel m = new TeeCrmCustomerInfoModel();
			m=parseModel(customerInfo);	
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
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
		if(!TeeUtility.isNullorEmpty(customerInfo.getAddTime())){
			model.setAddTimeDesc(sf.format(customerInfo.getAddTime().getTime()));
		}
		String sharePersonIds="";
		String sharePersonNames="";
		if(!TeeUtility.isNullorEmpty(customerInfo.getSharePerson())){
			for(TeePerson person:customerInfo.getSharePerson()){
				sharePersonIds += person.getUuid()+",";
				sharePersonNames+=person.getUserName()+",";
			}
		}
		if(sharePersonIds.endsWith(",")){
			sharePersonIds=sharePersonIds.substring(0, sharePersonIds.length()-1);
		}
		if(sharePersonNames.endsWith(",")){
			sharePersonNames=sharePersonNames.substring(0, sharePersonNames.length()-1);
		}
		model.setSharePersonIds(sharePersonIds);
		model.setSharePersonNames(sharePersonNames);
		String typeDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("CUSTOMER_TYPE",model.getCustomerType());
		String industryDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("INDUSTRY_TYPE",model.getIndustry());
		String customerSourceDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("CUSTOMER_SOURCE",model.getCustomerSource());
		String importantLevelDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("IMPORTTANT_LEVEL",model.getImportantLevel());
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
	 * 根据条件查出所有数据
	 * @param requestDatas
	 * @return
	 */
	public List<TeeCrmCustomerInfoModel> getTotalByConditon(Map requestDatas) {
		TeePerson person = (TeePerson)requestDatas.get("LOGIN_USER");
		String industry=(String)requestDatas.get("industry");
		String customerName=(String)requestDatas.get("customerName");
		String customerType=(String)requestDatas.get("customerType");
		String customerSource=(String)requestDatas.get("customerSource");
		String cityCode=(String)requestDatas.get("cityCode");
		String type=(String)requestDatas.get("type");//type=2 代表所有的客户，type =1 共享客户  没有type代表我的客户
		List param = new ArrayList();
		String hql = "from TeeCrmCustomerInfo customer where 1=1";
		
		if(!TeeUtility.isNullorEmpty(industry) && !"0".equals(industry)){
			hql+=" and customer.industry=?";
			param.add(industry);
		}
		if(!TeeUtility.isNullorEmpty(customerName)){
			hql+=" and customer.customerName like ?";
			param.add("%"+customerName+"%");
		}
		if(!TeeUtility.isNullorEmpty(customerType) && !"0".equals(customerType)){
			hql+=" and customer.customerType = ?";
			param.add(customerType);
		}
		if(!TeeUtility.isNullorEmpty(customerSource) && !"0".equals(customerSource)){
			hql+=" and customer.customerSource = ?";
			param.add(customerSource);
		}
		if(!TeeUtility.isNullorEmpty(cityCode)){
			if(cityCode.endsWith("0000")){
				hql+=" and customer.province = ?";
				param.add(cityCode);
			}else if(cityCode.endsWith("00") && !cityCode.endsWith("0000")){
				hql+=" and customer.city = ?";
				param.add(cityCode);
			}else{
				hql+=" and customer.county = ?";
				param.add(cityCode);
			}
		}
		if(!TeeUtility.isNullorEmpty(type)){
			if(type.equals("1")){//type=3代表公海客户，type=2 代表所有的客户，type =1 共享客户  没有type代表我的客户
				hql +=" and exists (select 1 from customer.sharePerson sharePerson where sharePerson.uuid = "+person.getUuid()+"))";
			}else if(type.equals("2")){//type=3代表公海客户，type=2 代表所有的客户，type =1 共享客户  没有type代表我的客户
				hql +="";
			}else if(type.equals("3")){//type=3代表公海客户，type=2 代表所有的客户，type =1 共享客户  没有type代表我的客户
				hql +=" and customer.managePerson.uuid is null";
			}
		}else{//type=3代表公海客户，type=2 代表所有的客户，type =1 共享客户  没有type代表我的客户
			hql +=" and (customer.managePerson.uuid="+person.getUuid()+" or exists (select 1 from customer.sharePerson sharePerson where sharePerson.uuid = "+person.getUuid()+"))";
		}
		List<TeeCrmCustomerInfo> infos = super.executeQueryByList(hql, param);
		List<TeeCrmCustomerInfoModel> models = new ArrayList<TeeCrmCustomerInfoModel>();
		for(TeeCrmCustomerInfo customerInfo:infos){
			TeeCrmCustomerInfoModel m = new TeeCrmCustomerInfoModel();
			m=parseModel(customerInfo);	
			models.add(m);
		}
		return models;
	}
}