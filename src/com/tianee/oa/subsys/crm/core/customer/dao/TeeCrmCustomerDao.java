package com.tianee.oa.subsys.crm.core.customer.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomer;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeExtendFiled;
import com.tianee.oa.subsys.crm.core.customer.model.TeeCrmCustomerModel;
import com.tianee.oa.subsys.crm.core.customer.model.TeeExtendFiledModel;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("TeeCrmCustomerDao")
public class TeeCrmCustomerDao extends TeeBaseDao<TeeCrmCustomer>{
	/**
	 * @author nieyi
	 * @param customerInfo
	 */
	public void addCustomerInfo(TeeCrmCustomer customer) {
		save(customer);
	}
	
	/**
	 * @author nieyi
	 * @param customerInfo
	 */
	public void updateCustomerInfo(TeeCrmCustomer customerInfo) {
		update(customerInfo);
	}
	
	/**
	 * 对象转换
	 * @author nieyi
	 * @param customerInfo
	 * @return
	 */
	public TeeCrmCustomerModel parseModel(TeeCrmCustomer customerInfo){
		TeeCrmCustomerModel model = new TeeCrmCustomerModel();
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
		String companyScaleDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("COMPANY_SCALE",model.getCompanyScale());
		model.setUnitTypeDesc(TeeCrmCodeManager.getChildSysCodeNameCodeNo("UNIT_TYPE",model.getUnitType()));
		model.setCustomerTypeDesc(typeDesc);
		model.setIndustryDesc(industryDesc);
		model.setCustomerSourceDesc(customerSourceDesc);
		model.setCompanyScaleDesc(companyScaleDesc);
		model.setAddressDesc(customerInfo.getLocateInformation());
		model.setAddPersonId(customerInfo.getAddPerson().getUuid());
		model.setAddPersonName(customerInfo.getAddPerson().getUserName());
		return model;
	}


	

	
}