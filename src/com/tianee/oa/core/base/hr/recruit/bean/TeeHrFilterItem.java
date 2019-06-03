package com.tianee.oa.core.base.hr.recruit.bean;
import org.hibernate.annotations.Index;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tianee.oa.core.org.bean.TeePerson;

@SuppressWarnings("serial")
@Entity
@Table(name = "HR_FILTER_ITEM")
public class TeeHrFilterItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="HR_FILTER_ITEM_seq_gen")
	@SequenceGenerator(name="HR_FILTER_ITEM_seq_gen", sequenceName="HR_FILTER_ITEM_seq")
	@Column(name = "SID")
	private int sid;// 自增id
	// 筛选Id
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDX90721d79ff264b1a941b86e7ef5")
	@JoinColumn(name = "FILTER_ID")
	private TeeHrFilter filter;
	
	@Column(name = "FILTER_METHOD")
	private String filterMethod;//筛选方式
	
	@Column(name = "FILTER_CONTENT")
	private String filterContent;//内容
	
	@Column(name = "FILTER_VIEW")
	private String filterView;//意见
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDXf1aa9e1c418a4403a5e5c5fffd3")
	@JoinColumn(name = "TRANSACTOR_STEP_ID")
	private TeePerson transactorStep;//筛选人
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FILTER_DATETIME")
	private Date filterDatetime;////筛选时间
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDX9418dbbfd0eb456e91653175302")
	@JoinColumn(name = "NEXT_TRANSACTOR_STEP_ID")
	private TeePerson nextTransactorStep;//下一次筛选人
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "NEXT_FILTER_DATETIME")
	private Date nextFilterDatetime;//下一次筛选时间

	
	@Column(name = "FILTER_STATE", columnDefinition="char(1) default 0" )
	private String filterState;// 1-通过 2-不通过
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeHrFilter getFilter() {
		return filter;
	}

	public void setFilter(TeeHrFilter filter) {
		this.filter = filter;
	}

	public String getFilterMethod() {
		return filterMethod;
	}

	public void setFilterMethod(String filterMethod) {
		this.filterMethod = filterMethod;
	}

	public Date getFilterDatetime() {
		return filterDatetime;
	}

	public void setFilterDatetime(Date filterDatetime) {
		this.filterDatetime = filterDatetime;
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

	public TeePerson getTransactorStep() {
		return transactorStep;
	}

	public void setTransactorStep(TeePerson transactorStep) {
		this.transactorStep = transactorStep;
	}

	public Date getNextFilterDatetime() {
		return nextFilterDatetime;
	}

	public void setNextFilterDatetime(Date nextFilterDatetime) {
		this.nextFilterDatetime = nextFilterDatetime;
	}

	public TeePerson getNextTransactorStep() {
		return nextTransactorStep;
	}

	public void setNextTransactorStep(TeePerson nextTransactorStep) {
		this.nextTransactorStep = nextTransactorStep;
	}

	public String getFilterState() {
		return filterState;
	}

	public void setFilterState(String filterState) {
		this.filterState = filterState;
	}

	
	
}
