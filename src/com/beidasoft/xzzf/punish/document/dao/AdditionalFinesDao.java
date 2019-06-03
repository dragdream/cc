package com.beidasoft.xzzf.punish.document.dao;

import org.springframework.stereotype.Repository;
import com.beidasoft.xzzf.punish.document.bean.DocAdditionalFines;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class AdditionalFinesDao extends TeeBaseDao<DocAdditionalFines> {
	String strStart = " '";
	String strFina = "' ";
	String temp = "' and '";
}
