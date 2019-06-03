package com.beidasoft.xzfy.homePage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzfy.homePage.dao.CaseSummaryDao;
import com.beidasoft.xzfy.homePage.model.CaseClosedSummaryModel;
import com.beidasoft.xzfy.homePage.model.CaseAcceptedSummaryModel;
import com.beidasoft.xzfy.homePage.model.CaseStatusSummaryModel;
import com.beidasoft.xzfy.utils.StringUtils;

/**
 * 案件统计相关服务
 * @author Henry
 *
 */
@Service
public class CaseSummaryService {
	
	@Autowired
	private CaseSummaryDao caseSummaryDao;

	//获取案件状态统计
	public List<CaseStatusSummaryModel> listCaseStatusSummary(String userId){
		//@todo  根据userId 来过滤数据范围
		String lastYear = StringUtils.getNYearsAgo(1);//只查询2年内的数据
		return caseSummaryDao.list(SqlConstant.CASE_STATUS_STATS_SQL,
				CaseStatusSummaryModel.class,lastYear);
	}
	

	//获取案件受理情况统计
	public List<CaseAcceptedSummaryModel> listCaseAcceptedSummary(String userId){
		//@todo  根据userId 来过滤数据范围
		return caseSummaryDao.list(SqlConstant.CASE_ACCEPTED_STATS_SQL,
				CaseAcceptedSummaryModel.class);
	}
	
	//获取案件结案情况统计
	public List<CaseClosedSummaryModel> listCaseClosedSummary(String userId){
		//@todo  根据userId 来过滤数据范围
		String lastYear = StringUtils.getNYearsAgo(1);//只查询2年内的数据
		return caseSummaryDao.list(SqlConstant.CASE_CLOSED_STATS_SQL,
				CaseClosedSummaryModel.class,lastYear);
	}
}
