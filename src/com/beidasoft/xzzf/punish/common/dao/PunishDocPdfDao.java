package com.beidasoft.xzzf.punish.common.dao;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.common.bean.PunishDocPdf;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class PunishDocPdfDao extends TeeBaseDao<PunishDocPdf>{
	
	public void saveOrUpdate(PunishDocPdf pdf) {
		super.saveOrUpdate(pdf);
	}
	
}
