package com.beidasoft.xzzf.punish.document.dao;

import org.springframework.stereotype.Repository;
import com.beidasoft.xzzf.punish.document.bean.DocApprovaltable;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class ApprovaltableDao extends TeeBaseDao<DocApprovaltable>{
	String strStart = " '";
	String strFina = "' ";
	String temp = "' and '";

}
