package com.tianee.oa.subsys.contract.bean;
import org.hibernate.annotations.Index;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;


@Entity
@Table(name="CONTRACT_CATEGORY")
public class TeeContractCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CONTRACT_CATEGORY_seq_gen")
	@SequenceGenerator(name="CONTRACT_CATEGORY_seq_gen", sequenceName="CONTRACT_CATEGORY_seq")
	private int sid;
	
	@Column(name="CAT_NAME")
	private String name;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="CONTRACT_CAT_VIEWS",
			joinColumns={@JoinColumn(name="CAT_ID")},       
			inverseJoinColumns={@JoinColumn(name="USER_ID")}  ) 	
	private Set<TeePerson> viewPriv = new HashSet(0);
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="CONTRACT_CAT_MANAG",
			joinColumns={@JoinColumn(name="CAT_ID")},       
			inverseJoinColumns={@JoinColumn(name="USER_ID")}  ) 	
	private Set<TeePerson> managePriv = new HashSet(0);

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

	public Set<TeePerson> getViewPriv() {
		return viewPriv;
	}

	public void setViewPriv(Set<TeePerson> viewPriv) {
		this.viewPriv = viewPriv;
	}

	public Set<TeePerson> getManagePriv() {
		return managePriv;
	}

	public void setManagePriv(Set<TeePerson> managePriv) {
		this.managePriv = managePriv;
	}
	
	
}
