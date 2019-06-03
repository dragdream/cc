package com.tianee.oa.subsys.salManage.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "HR_SAL_ACCOUNT")
public class TeeSalAccount implements Serializable{	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="HR_SAL_ACCOUNT_seq_gen")
	@SequenceGenerator(name="HR_SAL_ACCOUNT_seq_gen", sequenceName="HR_SAL_ACCOUNT_seq")
	private int sid;//自增id
	
	@Column(name="ACCOUNT_NAME")
	private String accountName;//名称
	
	@Column(name="ACCOUNT_NO")
	private String accountNo;//账套编号
	
	@Column(name="ACCOUNT_SORT" , columnDefinition = ("int default 0"))
	private int accountSort;// 排序
	
	@Column(name="REMARK" , length = 5000)
	private String remark;//备注

	@OneToMany(mappedBy="account",fetch=FetchType.LAZY,cascade=CascadeType.ALL)//因为这里是双向的 一对多 所以要写mappedBy
	private List<TeeSalAccountPerson> accountPerson;//账套权限人员

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getAccountSort() {
		return accountSort;
	}

	public void setAccountSort(int accountSort) {
		this.accountSort = accountSort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<TeeSalAccountPerson> getAccountPerson() {
		return accountPerson;
	}

	public void setAccountPerson(List<TeeSalAccountPerson> accountPerson) {
		this.accountPerson = accountPerson;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	
}
