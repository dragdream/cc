package com.beidasoft.xzzf.punish.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.AffiliatedPerson;
import com.beidasoft.xzzf.punish.common.dao.AffiliatedDao;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class AffiliatedService extends TeeBaseService {

	@Autowired
	private AffiliatedDao affiliDao;
	
	/**
	 * 保存参与人信息
	 * @param person
	 */
	public void save(AffiliatedPerson person){
		affiliDao.saveOrUpdate(person);
	}
	
	/**
	 * 获取参与人List
	 * @param person
	 */
	public List<AffiliatedPerson> getListByBaseId(String baseId){
		return affiliDao.getListByBaseId(baseId);
	}

	public void deletePersonByBaseId(String baseId) {
		affiliDao.deletePersonByBaseId(baseId);
	}
}
