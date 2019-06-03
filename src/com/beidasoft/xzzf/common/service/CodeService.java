package com.beidasoft.xzzf.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.common.bean.CodeDetail;
import com.beidasoft.xzzf.common.dao.CodeDao;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class CodeService extends TeeBaseService{

	@Autowired
	private CodeDao codeDao;
	
	public List<CodeDetail> getCodeDetails(String mainkey) {
		return codeDao.getCodeDetails(mainkey);
	}
	
	public List<CodeDetail> getCodeByDetailKey(String mainKey, String detailKey) {
		return codeDao.getCodeByDetailKey(mainKey, detailKey);
	}
}
