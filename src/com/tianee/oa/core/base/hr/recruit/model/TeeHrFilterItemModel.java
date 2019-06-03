package com.tianee.oa.core.base.hr.recruit.model;

import java.util.Date;

public class TeeHrFilterItemModel {
	private int sid;// 自增id
	private int filterId;
	private String filterMethod;//筛选方式
	private String filterMethodDesc;//
	private String filterContent;//内容
	private String filterView;//意见
	private String transactorStepId;//筛选人
	private String transactorStepName;//筛选人
	private Date filterDatetime;////筛选时间
	private String filterDatetimeStr;//
	private String nextTransactorStepId;//下一次筛选人
	private String nextTransactorStepName;//
	private Date nextFilterDatetime;//下一次筛选时间
	private String nextFilterDatetimeStr;//
	
	private String filterState;// 1-通过 2-不通过
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getFilterId() {
		return filterId;
	}
	public void setFilterId(int filterId) {
		this.filterId = filterId;
	}
	public String getFilterMethod() {
		return filterMethod;
	}
	public void setFilterMethod(String filterMethod) {
		this.filterMethod = filterMethod;
	}
	
	public String getFilterMethodDesc() {
		return filterMethodDesc;
	}
	public void setFilterMethodDesc(String filterMethodDesc) {
		this.filterMethodDesc = filterMethodDesc;
	}
	public String getFilterContent() {
		return filterContent;
	}
	public void setFilterContent(String filterContent) {
		this.filterContent = filterContent;
	}
	public String getFilterView() {
		return filterView;
	}
	public void setFilterView(String filterView) {
		this.filterView = filterView;
	}
	public String getTransactorStepId() {
		return transactorStepId;
	}
	public void setTransactorStepId(String transactorStepId) {
		this.transactorStepId = transactorStepId;
	}
	public String getTransactorStepName() {
		return transactorStepName;
	}
	public void setTransactorStepName(String transactorStepName) {
		this.transactorStepName = transactorStepName;
	}
	public Date getFilterDatetime() {
		return filterDatetime;
	}
	public void setFilterDatetime(Date filterDatetime) {
		this.filterDatetime = filterDatetime;
	}
	public String getFilterDatetimeStr() {
		return filterDatetimeStr;
	}
	public void setFilterDatetimeStr(String filterDatetimeStr) {
		this.filterDatetimeStr = filterDatetimeStr;
	}
	public String getNextTransactorStepId() {
		return nextTransactorStepId;
	}
	public void setNextTransactorStepId(String nextTransactorStepId) {
		this.nextTransactorStepId = nextTransactorStepId;
	}
	public String getNextTransactorStepName() {
		return nextTransactorStepName;
	}
	public void setNextTransactorStepName(String nextTransactorStepName) {
		this.nextTransactorStepName = nextTransactorStepName;
	}
	public Date getNextFilterDatetime() {
		return nextFilterDatetime;
	}
	public void setNextFilterDatetime(Date nextFilterDatetime) {
		this.nextFilterDatetime = nextFilterDatetime;
	}
	public String getNextFilterDatetimeStr() {
		return nextFilterDatetimeStr;
	}
	public void setNextFilterDatetimeStr(String nextFilterDatetimeStr) {
		this.nextFilterDatetimeStr = nextFilterDatetimeStr;
	}
	public String getFilterState() {
		return filterState;
	}
	public void setFilterState(String filterState) {
		this.filterState = filterState;
	}
	
	

}
