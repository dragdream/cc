package com.beidasoft.xzzf.punish.document.dao;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.document.bean.DocEvidencedescription;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class EvidencedescriptionDao extends TeeBaseDao<DocEvidencedescription>{

	String strStart = " '";
	String strFina = "' ";
	String temp = "' and '";
	
}
