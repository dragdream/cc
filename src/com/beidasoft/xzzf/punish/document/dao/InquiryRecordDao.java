package com.beidasoft.xzzf.punish.document.dao;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.document.bean.DocInquiryRecord;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class InquiryRecordDao extends TeeBaseDao<DocInquiryRecord>{
	String strStart = " '";
	String strFina = "' ";
	String temp = "' and '";
}
