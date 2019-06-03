package com.tianee.oa.subsys.salManage.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;
@Entity
@Table(name = "HR_SAL_ACCOUNT_PERSON")
public class TeeSalAccountPerson implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="HR_SAL_ACCOUNT_PERSON_seq_gen")
	@SequenceGenerator(name="HR_SAL_ACCOUNT_PERSON_seq_gen", sequenceName="HR_SAL_ACCOUNT_seq")
	private int sid;//自增id
	
	@OneToOne
	@JoinColumn(name = "USER_ID")
	private TeePerson user;//person
	
	
	@ManyToOne()
	@Index(name="IDXd5a8d6878f354464a6b64bc7cad")
	@JoinColumn(name="ACCOUNT_ID")
	private TeeSalAccount account;//账套


	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public TeePerson getUser() {
		return user;
	}


	public void setUser(TeePerson user) {
		this.user = user;
	}


	public TeeSalAccount getAccount() {
		return account;
	}


	public void setAccount(TeeSalAccount account) {
		this.account = account;
	}
	
	
}
