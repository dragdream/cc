package com.tianee.thirdparty.sphy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.thirdparty.sphy.dao.TeeSphyBodyDao;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeSphyBodyService extends TeeBaseService{
	
	@Autowired
	private TeeSphyBodyDao teeSphyBodyDao;
	
	

}
