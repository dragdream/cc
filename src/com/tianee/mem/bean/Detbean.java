package com.tianee.mem.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name="MEM_DETAILED")
public class Detbean {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="MEM_DETAILED_seq_gen")
	@SequenceGenerator(name="MEM_DETAILED_seq_gen", sequenceName="MEM_DETAILED_seq")
	@Column(name="ID")
	private int id;
	@Column(name="SID")
	private int sid;
	@Column(name="ITEM_NAME")
	private String item_name;
	@Column(name="UNIT")
	private int unit;
	@Column(name="NUMBER")
	private int number;
	@Column(name="FIRST")
	private String first;
	@Column(name="SECOND")
	private String second;
	@Column(name="LOCATION")
	private String location;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public int getUnit() {
		return unit;
	}
	public void setUnit(int unit) {
		this.unit = unit;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getSecond() {
		return second;
	}
	public void setSecond(String second) {
		this.second = second;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	

}
