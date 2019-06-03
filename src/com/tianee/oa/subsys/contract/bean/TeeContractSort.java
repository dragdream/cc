package com.tianee.oa.subsys.contract.bean;
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

@Entity
@Table(name="CONTRACT_SORT")
public class TeeContractSort {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CONTRACT_SORT_seq_gen")
	@SequenceGenerator(name="CONTRACT_SORT_seq_gen", sequenceName="CONTRACT_SORT_seq")
	private int sid;
	
	@Column(name="SORT_NAME")
	private String name;
	
	@ManyToOne
	@Index(name="IDXf0d2182d7a604d82bb9593f07d8")
	@JoinColumn(name="CAT_ID")
	private TeeContractCategory category;

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

	public TeeContractCategory getCategory() {
		return category;
	}

	public void setCategory(TeeContractCategory category) {
		this.category = category;
	}
	
	
}
