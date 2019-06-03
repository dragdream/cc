package com.beidasoft.xzfy.caseStatAna.bean;

import java.math.BigDecimal;


public class CaseStatInfo {
	
	private String category;//行政管理类型
	
	private BigDecimal lastPeriod;//上期结转

	private BigDecimal thisPeriod;//本期新收
	
	private BigDecimal accepted;// 已受理
	
	private BigDecimal applicantTotal;// 申请人总数
	
	private BigDecimal respondentTotal08;// 被申请人(乡镇政府)
	
	private BigDecimal respondentTotal07;// 被申请人(县政府部门)
	
	private BigDecimal respondentTotal06;// 被申请人(县级政府)
	
	private BigDecimal respondentTotal05;// 被申请人(市级政府部门)
	
	private BigDecimal respondentTotal04;// 被申请人(市级政府)
	
	private BigDecimal respondentTotal03;// 被申请人(省级政府部门)
	
	private BigDecimal respondentTotal02;// 被申请人(省级政府)
	
	private BigDecimal respondentTotal01;// 被申请人(国务院部门)
	
	private BigDecimal respondentTotal99;// 被申请人(其他)

	private BigDecimal fyOrgTotal07 ;// 复议机关(县级政府部门)
	private BigDecimal fyOrgTotal06 ;// 复议机关(县级政府)
	private BigDecimal fyOrgTotal05 ;// 复议机关(市级政府部门)
	private BigDecimal fyOrgTotal04 ;// 复议机关(市级政府)
	private BigDecimal fyOrgTotal03 ;// 复议机关(省级政府部门)
	private BigDecimal fyOrgTotal02 ;// 复议机关(省级政府)
	private BigDecimal fyOrgTotal01 ;// 复议机关(国务院部门)
	private BigDecimal fyOrgTotal00 ;// 复议机关(国务院)
	private BigDecimal itemTotal01;//申请复议事项(行政处罚)
	private BigDecimal itemTotal02;//申请复议事项(行政强制措施)
	private BigDecimal itemTotal03;//申请复议事项(行政征收)
	private BigDecimal itemTotal04;//申请复议事项(行政许可)
	private BigDecimal itemTotal05;//申请复议事项(行政确权)
	private BigDecimal itemTotal08;//申请复议事项(行政确认)
	private BigDecimal itemTotal09;//申请复议事项(信息公开)
	private BigDecimal itemTotal06;//申请复议事项(举报投诉处理)
	private BigDecimal itemTotal07;//申请复议事项(行政不作为)
	private BigDecimal itemTotal99;//申请复议事项(其他)
	private BigDecimal trialTotal;// 已审结(总计)
	private BigDecimal trialTotal01;// 已审结(驳回)
	private BigDecimal trialTotal02;// 已审结(维持)
	private BigDecimal trialTotal03;// 已审结(确认违法)
	private BigDecimal trialTotal04;// 已审结(撤销)
	private BigDecimal trialTotal05;// 已审结(变更)
	private BigDecimal trialTotal06;// 已审结(责令履行)
	private BigDecimal trialTotal07;// 已审结(中止-调解)
	private BigDecimal trialTotal08;// 已审结(中止-其他)
	private BigDecimal trialTotal99;// 已审结(其他)
	private BigDecimal notTrialTotal;// 未审结
	private BigDecimal fileCheckTotal;// 规范文件附带检查
	private BigDecimal compensationTotalJs;// 行政赔偿(件数)
	private BigDecimal compensationTotalJe;// 行政赔偿(金额)
	private BigDecimal fileTotal01;// 行政复议意见书(制发数)
	private BigDecimal fileTotal02;//行政复议意见书(落实数) 
//	private BigDecimal total;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public BigDecimal getLastPeriod() {
		return lastPeriod;
	}
	public void setLastPeriod(BigDecimal lastPeriod) {
		this.lastPeriod = lastPeriod;
	}
	public BigDecimal getThisPeriod() {
		return thisPeriod;
	}
	public void setThisPeriod(BigDecimal thisPeriod) {
		this.thisPeriod = thisPeriod;
	}
	public BigDecimal getAccepted() {
		return accepted;
	}
	public void setAccepted(BigDecimal accepted) {
		this.accepted = accepted;
	}
	public BigDecimal getApplicantTotal() {
		return applicantTotal;
	}
	public void setApplicantTotal(BigDecimal applicantTotal) {
		this.applicantTotal = applicantTotal;
	}
	public BigDecimal getRespondentTotal08() {
		return respondentTotal08;
	}
	public void setRespondentTotal08(BigDecimal respondentTotal08) {
		this.respondentTotal08 = respondentTotal08;
	}
	public BigDecimal getRespondentTotal07() {
		return respondentTotal07;
	}
	public void setRespondentTotal07(BigDecimal respondentTotal07) {
		this.respondentTotal07 = respondentTotal07;
	}
	public BigDecimal getRespondentTotal06() {
		return respondentTotal06;
	}
	public void setRespondentTotal06(BigDecimal respondentTotal06) {
		this.respondentTotal06 = respondentTotal06;
	}
	public BigDecimal getRespondentTotal05() {
		return respondentTotal05;
	}
	public void setRespondentTotal05(BigDecimal respondentTotal05) {
		this.respondentTotal05 = respondentTotal05;
	}
	public BigDecimal getRespondentTotal04() {
		return respondentTotal04;
	}
	public void setRespondentTotal04(BigDecimal respondentTotal04) {
		this.respondentTotal04 = respondentTotal04;
	}
	public BigDecimal getRespondentTotal03() {
		return respondentTotal03;
	}
	public void setRespondentTotal03(BigDecimal respondentTotal03) {
		this.respondentTotal03 = respondentTotal03;
	}
	public BigDecimal getRespondentTotal02() {
		return respondentTotal02;
	}
	public void setRespondentTotal02(BigDecimal respondentTotal02) {
		this.respondentTotal02 = respondentTotal02;
	}
	public BigDecimal getRespondentTotal01() {
		return respondentTotal01;
	}
	public void setRespondentTotal01(BigDecimal respondentTotal01) {
		this.respondentTotal01 = respondentTotal01;
	}
	public BigDecimal getRespondentTotal99() {
		return respondentTotal99;
	}
	public void setRespondentTotal99(BigDecimal respondentTotal99) {
		this.respondentTotal99 = respondentTotal99;
	}
	public BigDecimal getFyOrgTotal07() {
		return fyOrgTotal07;
	}
	public void setFyOrgTotal07(BigDecimal fyOrgTotal07) {
		this.fyOrgTotal07 = fyOrgTotal07;
	}
	public BigDecimal getFyOrgTotal06() {
		return fyOrgTotal06;
	}
	public void setFyOrgTotal06(BigDecimal fyOrgTotal06) {
		this.fyOrgTotal06 = fyOrgTotal06;
	}
	public BigDecimal getFyOrgTotal05() {
		return fyOrgTotal05;
	}
	public void setFyOrgTotal05(BigDecimal fyOrgTotal05) {
		this.fyOrgTotal05 = fyOrgTotal05;
	}
	public BigDecimal getFyOrgTotal04() {
		return fyOrgTotal04;
	}
	public void setFyOrgTotal04(BigDecimal fyOrgTotal04) {
		this.fyOrgTotal04 = fyOrgTotal04;
	}
	public BigDecimal getFyOrgTotal03() {
		return fyOrgTotal03;
	}
	public void setFyOrgTotal03(BigDecimal fyOrgTotal03) {
		this.fyOrgTotal03 = fyOrgTotal03;
	}
	public BigDecimal getFyOrgTotal02() {
		return fyOrgTotal02;
	}
	public void setFyOrgTotal02(BigDecimal fyOrgTotal02) {
		this.fyOrgTotal02 = fyOrgTotal02;
	}
	public BigDecimal getFyOrgTotal01() {
		return fyOrgTotal01;
	}
	public void setFyOrgTotal01(BigDecimal fyOrgTotal01) {
		this.fyOrgTotal01 = fyOrgTotal01;
	}

	public BigDecimal getFyOrgTotal00() {
		return fyOrgTotal00;
	}
	public void setFyOrgTotal00(BigDecimal fyOrgTotal00) {
		this.fyOrgTotal00 = fyOrgTotal00;
	}
	public BigDecimal getItemTotal01() {
		return itemTotal01;
	}
	public void setItemTotal01(BigDecimal itemTotal01) {
		this.itemTotal01 = itemTotal01;
	}
	public BigDecimal getItemTotal02() {
		return itemTotal02;
	}
	public void setItemTotal02(BigDecimal itemTotal02) {
		this.itemTotal02 = itemTotal02;
	}
	public BigDecimal getItemTotal03() {
		return itemTotal03;
	}
	public void setItemTotal03(BigDecimal itemTotal03) {
		this.itemTotal03 = itemTotal03;
	}
	public BigDecimal getItemTotal04() {
		return itemTotal04;
	}
	public void setItemTotal04(BigDecimal itemTotal04) {
		this.itemTotal04 = itemTotal04;
	}
	public BigDecimal getItemTotal05() {
		return itemTotal05;
	}
	public void setItemTotal05(BigDecimal itemTotal05) {
		this.itemTotal05 = itemTotal05;
	}
	public BigDecimal getItemTotal08() {
		return itemTotal08;
	}
	public void setItemTotal08(BigDecimal itemTotal08) {
		this.itemTotal08 = itemTotal08;
	}
	public BigDecimal getItemTotal09() {
		return itemTotal09;
	}
	public void setItemTotal09(BigDecimal itemTotal09) {
		this.itemTotal09 = itemTotal09;
	}
	public BigDecimal getItemTotal06() {
		return itemTotal06;
	}
	public void setItemTotal06(BigDecimal itemTotal06) {
		this.itemTotal06 = itemTotal06;
	}
	public BigDecimal getItemTotal07() {
		return itemTotal07;
	}
	public void setItemTotal07(BigDecimal itemTotal07) {
		this.itemTotal07 = itemTotal07;
	}
	public BigDecimal getItemTotal99() {
		return itemTotal99;
	}
	public void setItemTotal99(BigDecimal itemTotal99) {
		this.itemTotal99 = itemTotal99;
	}
	public BigDecimal getTrialTotal() {
		return trialTotal;
	}
	public void setTrialTotal(BigDecimal trialTotal) {
		this.trialTotal = trialTotal;
	}
	public BigDecimal getTrialTotal01() {
		return trialTotal01;
	}
	public void setTrialTotal01(BigDecimal trialTotal01) {
		this.trialTotal01 = trialTotal01;
	}
	public BigDecimal getTrialTotal02() {
		return trialTotal02;
	}
	public void setTrialTotal02(BigDecimal trialTotal02) {
		this.trialTotal02 = trialTotal02;
	}
	public BigDecimal getTrialTotal03() {
		return trialTotal03;
	}
	public void setTrialTotal03(BigDecimal trialTotal03) {
		this.trialTotal03 = trialTotal03;
	}
	public BigDecimal getTrialTotal04() {
		return trialTotal04;
	}
	public void setTrialTotal04(BigDecimal trialTotal04) {
		this.trialTotal04 = trialTotal04;
	}
	public BigDecimal getTrialTotal05() {
		return trialTotal05;
	}
	public void setTrialTotal05(BigDecimal trialTotal05) {
		this.trialTotal05 = trialTotal05;
	}
	public BigDecimal getTrialTotal06() {
		return trialTotal06;
	}
	public void setTrialTotal06(BigDecimal trialTotal06) {
		this.trialTotal06 = trialTotal06;
	}
	public BigDecimal getTrialTotal07() {
		return trialTotal07;
	}
	public void setTrialTotal07(BigDecimal trialTotal07) {
		this.trialTotal07 = trialTotal07;
	}
	public BigDecimal getTrialTotal08() {
		return trialTotal08;
	}
	public void setTrialTotal08(BigDecimal trialTotal08) {
		this.trialTotal08 = trialTotal08;
	}
	public BigDecimal getTrialTotal99() {
		return trialTotal99;
	}
	public void setTrialTotal99(BigDecimal trialTotal99) {
		this.trialTotal99 = trialTotal99;
	}
	public BigDecimal getNotTrialTotal() {
		return notTrialTotal;
	}
	public void setNotTrialTotal(BigDecimal notTrialTotal) {
		this.notTrialTotal = notTrialTotal;
	}
	public BigDecimal getFileCheckTotal() {
		return fileCheckTotal;
	}
	public void setFileCheckTotal(BigDecimal fileCheckTotal) {
		this.fileCheckTotal = fileCheckTotal;
	}

	public BigDecimal getCompensationTotalJs() {
		return compensationTotalJs;
	}
	public void setCompensationTotalJs(BigDecimal compensationTotalJs) {
		this.compensationTotalJs = compensationTotalJs;
	}
	public BigDecimal getCompensationTotalJe() {
		return compensationTotalJe;
	}
	public void setCompensationTotalJe(BigDecimal compensationTotalJe) {
		this.compensationTotalJe = compensationTotalJe;
	}
	public BigDecimal getFileTotal01() {
		return fileTotal01;
	}
	public void setFileTotal01(BigDecimal fileTotal01) {
		this.fileTotal01 = fileTotal01;
	}
	public BigDecimal getFileTotal02() {
		return fileTotal02;
	}
	public void setFileTotal02(BigDecimal fileTotal02) {
		this.fileTotal02 = fileTotal02;
	}

}
