package com.tianee.webframe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;

/**
 * 默认事务控制
 * @author lt
 *
 */
@Service
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class TeeBaseService {
	
	@Autowired
	protected TeeSimpleDaoSupport simpleDaoSupport;

	public void setSimpleDaoSupport(TeeSimpleDaoSupport simpleDaoSupport) {
		this.simpleDaoSupport = simpleDaoSupport;
	}

	public TeeSimpleDaoSupport getSimpleDaoSupport() {
		return simpleDaoSupport;
	}
	
	
}
