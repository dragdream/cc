package com.tianee.oa.core.base.officeProducts.bean;
import org.hibernate.annotations.Index;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.springframework.beans.factory.annotation.Autowired;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 办公用品库
 * @author kakalion
 *
 */
@Entity
@Table(name="Office_Depository")
public class TeeOfficeDepository{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="Office_Depository_seq_gen")
	@SequenceGenerator(name="Office_Depository_seq_gen", sequenceName="Office_Depository_seq")
	private int sid;
	
	@Column(name="DEPOS_NAME")
	private String deposName;//库名称
	
	@ManyToMany(fetch=FetchType.LAZY,cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name="officeDepos_depts")
	private Set<TeeDepartment> depts = new HashSet<TeeDepartment>(0);//所属部门
	
	@ManyToMany(fetch=FetchType.LAZY,cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name="officeDepos_admins")
	Set<TeePerson> admins = new HashSet<TeePerson>(0);//库管理员
	
	@ManyToMany(fetch=FetchType.LAZY,cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name="officeDepos_operators")
	Set<TeePerson> operators = new HashSet<TeePerson>(0);//库调度员

	public String getDeposName() {
		return deposName;
	}

	public void setDeposName(String deposName) {
		this.deposName = deposName;
	}

	public Set<TeePerson> getAdmins() {
		return admins;
	}

	public void setAdmins(Set<TeePerson> admins) {
		this.admins = admins;
	}

	public Set<TeePerson> getOperators() {
		return operators;
	}

	public void setOperators(Set<TeePerson> operators) {
		this.operators = operators;
	}

	public void setDepts(Set<TeeDepartment> depts) {
		this.depts = depts;
	}

	public Set<TeeDepartment> getDepts() {
		return depts;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getSid() {
		return sid;
	}
	
	
}
