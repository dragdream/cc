package com.tianee.oa.subsys.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tianee.webframe.service.TeeBaseService;

@Service
public class CmsDocumentCategoryService extends TeeBaseService{
	public List listCats(){
		return simpleDaoSupport.find("from DocumentCategory order by sid asc", null);
	}
}
