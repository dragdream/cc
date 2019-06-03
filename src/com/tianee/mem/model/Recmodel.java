package com.tianee.mem.model;

import java.util.Date;

import javax.persistence.Column;

public class Recmodel {
	private int id;
	private int sid;
	private String handle_timestr;
	private String handle_action;
	private int handle_number;
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
	public String getHandle_timestr() {
		return handle_timestr;
	}
	public void setHandle_timestr(String handle_timestr) {
		this.handle_timestr = handle_timestr;
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
