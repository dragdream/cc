package com.tianee.oa.subsys.budget.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 部门费用计划
 * @author kakalion
 *
 */
@Entity
@Table(name="BUDGET_DEPT")
public class TeeDeptBudget {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//主键
	
	@ManyToOne
	@Index(name="IDX519c25ee4ebe437c969597f3833")
	@JoinColumn(name="DEPT_ID")
	private TeeDepartment dept;
	
	@Column(name="YEAR")
	private String year;//年度   例如  2014
	
	@Column(name="MONTH")
	private String month;//月份  例如 01 02 03 11 12 等
	
	@Column(name="AMOUNT")
	private double amount;
	
	@ManyToOne
	@Index(name="IDX74e4f05aa599405eb1daf8b765e")
	@JoinColumn(name="CR_USER_ID")
	private TeePerson crUser;

	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public TeeDepartment getDept() {
		return dept;
	}

	public void setDept(TeeDepartment dept) {
		this.dept = dept;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public TeePerson getCrUser() {
		return crUser;
	}

	public void setCrUser(TeePerson crUser) {
		this.crUser = crUser;
	}
	
	
}
