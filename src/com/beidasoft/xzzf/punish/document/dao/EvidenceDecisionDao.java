package com.beidasoft.xzzf.punish.document.dao;

import org.springframework.stereotype.Repository;
import com.beidasoft.xzzf.punish.document.bean.DocEvidenceDecision;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class EvidenceDecisionDao extends TeeBaseDao<DocEvidenceDecision> {
	
	String strStart = " '";
	String strFina = "' ";
	String temp = "' and '";
}
