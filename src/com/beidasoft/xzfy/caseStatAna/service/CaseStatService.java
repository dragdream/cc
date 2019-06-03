package com.beidasoft.xzfy.caseStatAna.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzfy.caseStatAna.bean.CaseStatInfo;
import com.beidasoft.xzfy.caseStatAna.bean.ConstSql;
import com.beidasoft.xzfy.caseStatAna.dao.CaseStatDao;
import com.beidasoft.xzfy.utils.StringUtils;
import com.tianee.webframe.httpmodel.TeeJson;

@Service
public class CaseStatService {
	@Autowired
	private CaseStatDao dao;

	public TeeJson getCaseStatList(String beginTime,String endTime) {
		TeeJson data = new TeeJson();
		data.setRtMsg("success");
		data.setRtState(true);
//		String lastYear = StringUtils.getNYearsAgo(1);// 只查询2年内的数据
		data.setRtData(dao.list(ConstSql.CASE_STAT_INFO_SQL,
				CaseStatInfo.class,beginTime,endTime));
		return data;
	}
}
