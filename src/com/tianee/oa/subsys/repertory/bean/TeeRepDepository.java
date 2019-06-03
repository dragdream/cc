package com.tianee.oa.subsys.repertory.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="REP_DEPOSITORY")
public class TeeRepDepository {
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="REP_DEPOSITORY_seq_gen")
	@SequenceGenerator(name="REP_DEPOSITORY_seq_gen", sequenceName="REP_DEPOSITORY_seq")
	private int sid;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="CODE")
	private String code;
	
	@ManyToOne
	@Index(name="IDX9ea1cf68799d446db6b53d1fe29")
	@JoinColumn(name="MANAGER_ID")
	private TeePerson manager;//库管员

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public TeePerson getManager() {
		return manager;
	}

	public void setManager(TeePerson manager) {
		this.manager = manager;
	}
	
}
