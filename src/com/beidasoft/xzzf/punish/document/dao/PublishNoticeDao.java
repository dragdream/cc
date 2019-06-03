package com.beidasoft.xzzf.punish.document.dao;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.document.bean.DocPublishNotice;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class PublishNoticeDao extends TeeBaseDao<DocPublishNotice>{	
	String strStart = " '";
	String strFina = "' ";
	String temp = "' and '";
	
}
