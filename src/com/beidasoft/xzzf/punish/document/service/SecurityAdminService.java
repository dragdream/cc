package com.beidasoft.xzzf.punish.document.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.document.bean.DocSecurityAdmin;
import com.beidasoft.xzzf.punish.document.dao.SecurityAdminDao;
import com.tianee.webframe.service.TeeBaseService;


@Service
public class SecurityAdminService extends TeeBaseService {
	
	@Autowired
	private SecurityAdminDao securityAdminDao;
	
	/**
	 * 保存行政处罚决定书
	 * @param docSecurityAdmin
	 */
	public void saveDocInfo(DocSecurityAdmin docSecurityAdmin) {
		securityAdminDao.saveOrUpdate(docSecurityAdmin);
	}
	
	/**
	 * 查询行政处罚决定书
	 * @param id
	 * @return
	 */
	public DocSecurityAdmin getDocInfo(String pId) {
		return securityAdminDao.get(pId);
	}

}
