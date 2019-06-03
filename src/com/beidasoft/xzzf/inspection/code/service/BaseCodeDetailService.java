package com.beidasoft.xzzf.inspection.code.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.inspection.code.bean.BaseCodeDetail;
import com.beidasoft.xzzf.inspection.code.dao.BaseCodeDetailDao;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class BaseCodeDetailService extends TeeBaseService {
	
	@Autowired
	private BaseCodeDetailDao baseCodeDetailDao;
	
	/**
	 * 查询代码表（根据MainKey）
	 * @param mainKey
	 * @return
	 */
	public List<BaseCodeDetail> getBaseCodeDetails(String mainKey) {
		return baseCodeDetailDao.getBaseCodeDetails(mainKey);
	}
	
	/**
	 * 查询代码表（根据MainKey）
	 * @param mainKey
	 * @return
	 */
	public Map<String, String> getCodeMap(String mainKey) {
		Map<String, String> codeMap = new HashMap<String, String>();
		List<BaseCodeDetail> codeDetails =  baseCodeDetailDao.getBaseCodeDetails(mainKey);
		for(BaseCodeDetail row : codeDetails) {
			codeMap.put(row.getDetailKey(), row.getDetailValue());
		}
		
		return codeMap;
	}
}
