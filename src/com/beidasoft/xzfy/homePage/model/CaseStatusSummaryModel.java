package com.beidasoft.xzfy.homePage.model;

import java.math.BigDecimal;

/**
 * 案件状态统计
 * @author Henry
 *
 */
public class CaseStatusSummaryModel{
	private String caseStatusCode;
	private String caseStatus;
	//total不能定义为int，而是BigDecimal的原因是：hibernate执行sql语句返回结果中，sql的int类型
	//会返回成为BigDecimal,为了保证利用 StringUtils.arrayToObject方法执行成功，否则反射中
	//字段类型与值类型不匹配，会报错：java.lang.IllegalArgumentException: argument type mismatch
	private BigDecimal total;
	
	public String getCaseStatusCode() {
		return caseStatusCode;
	}
	public void setCaseStatusCode(String caseStatusCode) {
		this.caseStatusCode = caseStatusCode;
	}
	public String getCaseStatus() {
		return caseStatus;
	}
	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	
	
}
