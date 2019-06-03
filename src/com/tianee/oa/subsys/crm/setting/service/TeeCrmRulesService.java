package com.tianee.oa.subsys.crm.setting.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.attend.bean.TeeAttendLeaderRule;
import com.tianee.oa.core.base.attend.model.TeeAttendLeaderRuleModel;
import com.tianee.oa.core.general.bean.TeeSysPara;
import com.tianee.oa.core.general.dao.TeeSysParaDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeCrmRulesService extends TeeBaseService{
	@Autowired
	TeeSysParaDao sysParaDao;

	/**
	 * 设置CRM相关审批规则人员
	 * @param requestDatas
	 */
	public void saveCrmRulePerson(Map requestDatas) {
		//获取参数
		String orderManagerIds = TeeStringUtil.getString(requestDatas.get("orderManagerIds"), "");
		String orderManagerNames = TeeStringUtil.getString(requestDatas.get("orderManagerNames"), "");
		String paymentPersonIds = TeeStringUtil.getString(requestDatas.get("paymentPersonIds"), "");
		String paymentPersonNames = TeeStringUtil.getString(requestDatas.get("paymentPersonNames"), "");
		String drawbackPersonIds = TeeStringUtil.getString(requestDatas.get("drawbackPersonIds"), "");
		String drawbackPersonNames = TeeStringUtil.getString(requestDatas.get("drawbackPersonNames"), "");
		String billingPersonIds = TeeStringUtil.getString(requestDatas.get("billingPersonIds"), "");
		String billingPersonNames = TeeStringUtil.getString(requestDatas.get("billingPersonNames"), "");
		
		TeeSysPara sysPara1 = new TeeSysPara();
		if (sysParaDao.getSysPara("ORDER_MANAGER_IDS") == null) {// 设置订单管理员IDS
			sysPara1.setParaName("ORDER_MANAGER_IDS");
			sysPara1.setParaValue(orderManagerIds);
			sysParaDao.save(sysPara1);
		} else {
			sysPara1 = sysParaDao.getSysPara("ORDER_MANAGER_IDS");
			sysPara1.setParaValue(orderManagerIds);
			sysParaDao.update(sysPara1);
		}
		TeeSysPara sysPara2 = new TeeSysPara();
		if (sysParaDao.getSysPara("ORDER_MANAGER_NAMES") == null) {//  设置订单管理员NAMES
			sysPara2.setParaName("ORDER_MANAGER_NAMES");
			sysPara2.setParaValue(orderManagerNames);
			sysParaDao.save(sysPara2);
		} else {
			sysPara2 = sysParaDao.getSysPara("ORDER_MANAGER_NAMES");
			sysPara2.setParaValue(orderManagerNames);
			sysParaDao.update(sysPara2);
		}
		TeeSysPara sysPara3 = new TeeSysPara();
		if (sysParaDao.getSysPara("PAYMENT_PERSON_IDS") == null) {// 设置回款财务IDS
			sysPara3.setParaName("PAYMENT_PERSON_IDS");
			sysPara3.setParaValue(paymentPersonIds);
			sysParaDao.save(sysPara3);
		} else {
			sysPara3 = sysParaDao.getSysPara("PAYMENT_PERSON_IDS");
			sysPara3.setParaValue(paymentPersonIds);
			sysParaDao.update(sysPara3);
		}
		TeeSysPara sysPara4 = new TeeSysPara();
		if (sysParaDao.getSysPara("PAYMENT_PERSON_NAMES") == null) {// 设置回款财务NAMES
			sysPara4.setParaName("PAYMENT_PERSON_NAMES");
			sysPara4.setParaValue(paymentPersonNames);
			sysParaDao.save(sysPara4);
		} else {
			sysPara4 = sysParaDao.getSysPara("PAYMENT_PERSON_NAMES");
			sysPara4.setParaValue(paymentPersonNames);
			sysParaDao.update(sysPara4);
		}
		TeeSysPara sysPara5 = new TeeSysPara();
		if (sysParaDao.getSysPara("DRAWBACK_PERSON_IDS") == null) {// 设置退款财务IDS
			sysPara5.setParaName("DRAWBACK_PERSON_IDS");
			sysPara5.setParaValue(drawbackPersonIds);
			sysParaDao.save(sysPara5);
		} else {
			sysPara5 = sysParaDao.getSysPara("DRAWBACK_PERSON_IDS");
			sysPara5.setParaValue(drawbackPersonIds);
			sysParaDao.update(sysPara5);
		}
		TeeSysPara sysPara6 = new TeeSysPara();
		if (sysParaDao.getSysPara("DRAWBACK_PERSON_NAMES") == null) {// 设置退款财务NAMES
			sysPara6.setParaName("DRAWBACK_PERSON_NAMES");
			sysPara6.setParaValue(drawbackPersonNames);
			sysParaDao.save(sysPara6);
		} else {
			sysPara6 = sysParaDao.getSysPara("DRAWBACK_PERSON_NAMES");
			sysPara6.setParaValue(drawbackPersonNames);
			sysParaDao.update(sysPara6);
		}
		TeeSysPara sysPara7 = new TeeSysPara();
		if (sysParaDao.getSysPara("BILLING_PERSON_IDS") == null) {// 设置开票财务IDS
			sysPara7.setParaName("BILLING_PERSON_IDS");
			sysPara7.setParaValue(billingPersonIds);
			sysParaDao.save(sysPara7);
		} else {
			sysPara7 = sysParaDao.getSysPara("BILLING_PERSON_IDS");
			sysPara7.setParaValue(billingPersonIds);
			sysParaDao.update(sysPara7);
		}
		TeeSysPara sysPara8 = new TeeSysPara();
		if (sysParaDao.getSysPara("BILLING_PERSON_NAMES") == null) {// 设置开票财务NAMES
			sysPara8.setParaName("BILLING_PERSON_NAMES");
			sysPara8.setParaValue(billingPersonNames);
			sysParaDao.save(sysPara8);
		} else {
			sysPara8 = sysParaDao.getSysPara("BILLING_PERSON_NAMES");
			sysPara8.setParaValue(billingPersonNames);
			sysParaDao.update(sysPara8);
		}

	}

}
