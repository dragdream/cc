package com.tianee.oa.core.org.service;

import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeeOrgCache;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeOrgCacheService extends TeeBaseService{

	public byte[] getBytes(String type){
		TeeOrgCache orgCache = (TeeOrgCache) simpleDaoSupport.unique("from TeeOrgCache where key='"+type+"'", null);
		if(orgCache!=null){
			return orgCache.getValue();
		}
		return new byte[0];
	}
	
}
