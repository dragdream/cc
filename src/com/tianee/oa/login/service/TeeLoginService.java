package com.tianee.oa.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.login.dao.TeeLoginDao;

@Service
public class TeeLoginService {

	@Autowired
	private TeeLoginDao dao;
	
	public List<TeePerson> verifyCertUniqueId(String certUniqueId){
		return dao.verifyCertUniqueId(certUniqueId);
	}
	
}
