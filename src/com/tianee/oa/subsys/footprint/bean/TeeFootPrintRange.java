package com.tianee.oa.subsys.footprint.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name = "foot_print_range")
public class TeeFootPrintRange {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="foot_print_range_seq_gen")
	@SequenceGenerator(name="foot_print_range_seq_gen", sequenceName="foot_print_range_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	
	@Column(name = "RANGE_NAME")
	private String rangeName;//围栏名称
	
	
	
	@Column(name = "RANGE_STR")
	private String rangeStr;//围栏范围
	
	
	@ManyToMany() 
	@JoinTable( name="foot_print_range_user",        
			joinColumns={@JoinColumn(name="range_id")},       
			inverseJoinColumns={@JoinColumn(name="user_id")}  )  	
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)	
	private List<TeePerson> users;//管理范围


	public int getSid() {
		return sid;
	}


	public String getRangeName() {
		return rangeName;
	}


	public String getRangeStr() {
		return rangeStr;
	}


	public List<TeePerson> getUsers() {
		return users;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public void setRangeName(String rangeName) {
		this.rangeName = rangeName;
	}


	public void setRangeStr(String rangeStr) {
		this.rangeStr = rangeStr;
	}


	public void setUsers(List<TeePerson> users) {
		this.users = users;
	}
	
	
	
	
	
	
}
