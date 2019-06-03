package com.tianee.mem.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name="MEM_RECORD")
public class Recbean {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="MEM_RECORD_seq_gen")
	@SequenceGenerator(name="MEM_RECORD_seq_gen", sequenceName="MEM_RECORD_seq")
	@Column(name="ID")
	private int id;
	@Column(name="SID")
	private int sid;
	@Column(name="HANDLE_TIME")
	private Date handle_time;
	@Column(name="HANDLE_ACTION")
	private String handle_action;
	@Column(name="HANDLE_NUMBER")
	private int handle_number;
	@Column(name="HANDLE_PERSON")
	private String handle_person;
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
	public Date getHandle_time() {
		return handle_time;
	}
	public void setHandle_time(Date handle_time) {
		this.handle_time = handle_time;
	}
	public String getHandle_action() {
		return handle_action;
	}
	public void setHandle_action(String handle_action) {
		this.handle_action = handle_action;
	}
	public int getHandle_number() {
		return handle_number;
	}
	public void setHandle_number(int handle_number) {
		this.handle_number = handle_number;
	}
	public String getHandle_person() {
		return handle_person;
	}
	public void setHandle_person(String handle_person) {
		this.handle_person = handle_person;
	}
	
}
