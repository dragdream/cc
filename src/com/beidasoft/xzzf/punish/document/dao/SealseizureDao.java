package com.beidasoft.xzzf.punish.document.dao;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.document.bean.DocSealseizure;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class SealseizureDao extends TeeBaseDao<DocSealseizure> {

	String strStart = " '";
	String strFina = "' ";
	String temp = "' and '";
	/**
	 * 根据id查询
	 */
	public DocSealseizure loadById(String id) {
		DocSealseizure docSealseizure = super.get(id);
		return docSealseizure;
	}
}
