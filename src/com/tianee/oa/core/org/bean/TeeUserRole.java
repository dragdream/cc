package com.tianee.oa.core.org.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="USER_ROLE")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class TeeUserRole implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="USER_ROLE_seq_gen")
	@SequenceGenerator(name="USER_ROLE_seq_gen", sequenceName="USER_ROLE_seq")
	private int uuid;
	
	@Column(name="ROLE_NAME",length=100)
	private String roleName;//角色名称
	
	@Column(name="ROLE_NO",length=11)
	private Integer roleNo;//角色编码 排序

	@OneToMany(mappedBy="userRole",fetch=FetchType.LAZY)
	private List<TeePerson> persons;//人员 属于该角色的人员

	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX9747fd002f5e485499af0b6751f")
	@JoinColumn(name="USER_ROLE_TYPE_ID")
	private TeeUserRoleType userRoleType;//角色分类
	
	@Column(name="SALARY_")
	private double salary;//岗位工资
	
	@Lob
	@Column(name="SAL_LEVEL_MODEL")
	private String salaryLevelModel = "[]";//薪资级别  [{a:'初级',b:'200'},{a:'中级',b:'300'},{a:'高级',b:'800'}]

	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX9747lk002f5e485499af206751f")
	@JoinColumn(name="DEPT_ID")
	private TeeDepartment dept;
	
	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public Integer getRoleNo() {
		return roleNo;
	}

	public void setRoleNo(Integer roleNo) {
		this.roleNo = roleNo;
	}

	public List<TeePerson> getPersons() {
		return persons;
	}

	public void setPersons(List<TeePerson> persons) {
		this.persons = persons;
	}

	public TeeUserRoleType getUserRoleType() {
		return userRoleType;
	}

	public void setUserRoleType(TeeUserRoleType userRoleType) {
		this.userRoleType = userRoleType;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getSalaryLevelModel() {
		return salaryLevelModel;
	}

	public void setSalaryLevelModel(String salaryLevelModel) {
		this.salaryLevelModel = salaryLevelModel;
	}

	public TeeDepartment getDept() {
		return dept;
	}

	public void setDept(TeeDepartment dept) {
		this.dept = dept;
	}
	
	
}
