package com.beidasoft.xzfy.homePage.model;

import java.math.BigDecimal;

/**
 * 案件结案情况统计
 * @author Henry
 *
 */
public class CaseClosedSummaryModel{
	private BigDecimal total01;//驳回
	private BigDecimal total02;//维持
	private BigDecimal total03;//确认违法
	private BigDecimal total04;//撤销
	private BigDecimal total05;//变更
	private BigDecimal total06;//责令履行
	private BigDecimal total07;//终止-调解(和解)
	private BigDecimal total08;//终止-其他
	private BigDecimal total99;//其他
	public BigDecimal getTotal01() {
		return total01;
	}
	public void setTotal01(BigDecimal total01) {
		this.total01 = total01;
	}
	public BigDecimal getTotal02() {
		return total02;
	}
	public void setTotal02(BigDecimal total02) {
		this.total02 = total02;
	}
	public BigDecimal getTotal03() {
		return total03;
	}
	public void setTotal03(BigDecimal total03) {
		this.total03 = total03;
	}
	public BigDecimal getTotal04() {
		return total04;
	}
	public void setTotal04(BigDecimal total04) {
		this.total04 = total04;
	}
	public BigDecimal getTotal05() {
		return total05;
	}
	public void setTotal05(BigDecimal total05) {
		this.total05 = total05;
	}
	public BigDecimal getTotal06() {
		return total06;
	}
	public void setTotal06(BigDecimal total06) {
		this.total06 = total06;
	}
	public BigDecimal getTotal07() {
		return total07;
	}
	public void setTotal07(BigDecimal total07) {
		this.total07 = total07;
	}
	public BigDecimal getTotal08() {
		return total08;
	}
	public void setTotal08(BigDecimal total08) {
		this.total08 = total08;
	}
	public BigDecimal getTotal99() {
		return total99;
	}
	public void setTotal99(BigDecimal total99) {
		this.total99 = total99;
	}
	
	
}
