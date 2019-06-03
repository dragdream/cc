package com.tianee.oa.subsys.report.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SENIOR_RE_CAT")
public class TeeSeniorReportCat {
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy=GenerationType.AUTO,generator="SENIOR_RE_CAT_seq_gen")
	@SequenceGenerator(name="SENIOR_RE_CAT_seq_gen", sequenceName="SENIOR_RE_CAT_seq")
	private int sid;
	
	@Column(name="CAT_NAME")
	private String name;
	
	@Column(name="SORT_NO")
	private int sortNo;
	
	@Column(name="COLOR")
	private String color;

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

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
