package com.beidasoft.xzzf.punish.document.dao;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.document.bean.DocPaymentinstallments;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class PaymentinstallmentsDao extends TeeBaseDao<DocPaymentinstallments> {
	String strStart = " '";
	String strFina = "' ";
	String temp = "' and '";
}
