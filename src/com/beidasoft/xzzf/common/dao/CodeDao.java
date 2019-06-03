package com.beidasoft.xzzf.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.common.bean.CodeDetail;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class CodeDao extends TeeBaseDao<CodeDetail>{
	
	public List<CodeDetail> getCodeDetails(String mainKey) {
		return super.find("from CodeDetail where mainKey = '" + mainKey + "'", null);
	}

	public List<CodeDetail> getCodeByDetailKey(String mainKey, String detailKey) {
		String hql = "from CodeDetail where mainKey = '" + mainKey + "'" + "and detailKey like '" + detailKey + "%'";
		return super.find(hql, null);
	}
}
