package com.beidasoft.xzzf.punish.document.dao;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.document.bean.DocInorout;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class InoroutDao extends TeeBaseDao<DocInorout> {
	String strStart = " '";
	String strFina = "' ";
	String temp = "' and '";
}
