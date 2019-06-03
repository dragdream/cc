package com.tianee.test.service;

import org.springframework.stereotype.Service;

import com.tianee.webframe.service.TeeBaseService;

/**
 * 
 * @author zhp 用户服务层 对用户增删改查功能实现
 */
@Service("teeUserService")
public class TeeUserService extends TeeBaseService{

	public void test() {
		String hql = "from TeePerson where find_in_set('2',MSN)='2'";
		simpleDaoSupport.find(hql, null);
	}

}
