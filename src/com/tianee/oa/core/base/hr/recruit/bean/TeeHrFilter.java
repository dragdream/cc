package com.tianee.oa.core.base.hr.recruit.bean;
import org.hibernate.annotations.Index;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tianee.oa.core.base.hr.recruit.plan.bean.TeeRecruitPlan;
import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 人才库
 * @author syl
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "HR_FILTER")
public class TeeHrFilter {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="HR_FILTER_seq_gen")
	@SequenceGenerator(name="HR_FILTER_seq_gen", sequenceName="HR_FILTER_seq")
	@Column(name = "SID")
	private int sid;// 自增id
	
	// 计划招聘
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDXe592b760ab60484c9f1c58b2d3f")
	@JoinColumn(name = "PLAN_ID")
	private TeeRecruitPlan plan;
	
	// 人才库 
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDX01dadbaf244b439db3d29f336dd")
	@JoinColumn(name = "POOL_ID")
	private TeeHrPool hrPool;

	@Column(name = "POSITION")
	private String position;	// 应聘岗位

	@Column(name = "EMPLOYEE_MAJOR" )
	private String employeeMajor;	// 所学专业

	@Column(name = "EMPLOYEE_PHONE" )
	private String employeePhone;	// 电话
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDX59881a0b59144b8d81d0faf9f5e")
	@JoinColumn(name = "SEND_PERSON_ID")
	private TeePerson sendPerson;//发起人
   
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDXc6eed23e3bfa4c70acbcb759ef0")
	@JoinColumn(name = "NEXT_TRANSACTOR_STEP_ID")
	private TeePerson nextTransactor;//下一次筛选人
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "NEXT_DATETIME")
	private Date nextDatetime;//下一次筛选时间
	
	
	@Column(name = "FILTER_STATE" , columnDefinition="char(1) default 0")
	private String filterState;//0-待筛选 1-通过 2-不通过

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;//创建时间
	
	@OneToMany(mappedBy="filter",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)//这是双向的 要写JoinColumn 对面要写 mappedBy  ,在对方的
	private List<TeeHrFilterItem> filterItem = new ArrayList();//
	

	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public List<TeeHrFilterItem> getFilterItem() {
		return filterItem;
	}


	public void setFilterItem(List<TeeHrFilterItem> filterItem) {
		this.filterItem = filterItem;
	}


	public TeeRecruitPlan getPlan() {
		return plan;
	}


	public void setPlan(TeeRecruitPlan plan) {
		this.plan = plan;
	}


	public TeeHrPool getHrPool() {
		return hrPool;
	}


	public void setHrPool(TeeHrPool hrPool) {
		this.hrPool = hrPool;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public String getEmployeeMajor() {
		return employeeMajor;
	}


	public void setEmployeeMajor(String employeeMajor) {
		this.employeeMajor = employeeMajor;
	}


	public String getEmployeePhone() {
		return employeePhone;
	}


	public void setEmployeePhone(String employeePhone) {
		this.employeePhone = employeePhone;
	}


	public TeePerson getSendPerson() {
		return sendPerson;
	}


	public void setSendPerson(TeePerson sendPerson) {
		this.sendPerson = sendPerson;
	}



	public TeePerson getNextTransactor() {
		return nextTransactor;
	}


	public void setNextTransactor(TeePerson nextTransactor) {
		this.nextTransactor = nextTransactor;
	}


	public Date getNextDatetime() {
		return nextDatetime;
	}


	public void setNextDatetime(Date nextDatetime) {
		this.nextDatetime = nextDatetime;
	}


	public String getFilterState() {
		return filterState;
	}


	public void setFilterState(String filterState) {
		this.filterState = filterState;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
