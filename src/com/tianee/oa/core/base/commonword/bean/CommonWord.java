package com.tianee.oa.core.base.commonword.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;


import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="common_word")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class CommonWord implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9142240391844838999L;
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "sid")
	private String sid;
	@Column(name="cyy")
	private String cyy;
	@Column(name="cis")
	private int cis;
	@ManyToOne
	@JoinColumn(name = "person_id")
	private TeePerson person;//部门表

	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getCyy() {
		return cyy;
	}
	public void setCyy(String cyy) {
		this.cyy = cyy;
	}
	public int getCis() {
		return cis;
	}
	public void setCis(int cis) {
		this.cis = cis;
	}
	public TeePerson getPerson() {
		return person;
	}
	public void setPerson(TeePerson person) {
		this.person = person;
	}
	

  
}
