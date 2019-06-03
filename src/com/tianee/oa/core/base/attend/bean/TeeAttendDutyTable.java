package com.tianee.oa.core.base.attend.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name = "ATTEND_DUTY_TABLE")
public class TeeAttendDutyTable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="ATTEND_DUTY_TABLE_seq_gen")
	@SequenceGenerator(name="ATTEND_DUTY_TABLE_seq_gen", sequenceName="ATTEND_DUTY_TABLE_seq")
	@Column(name="SID")
	private int sid;
	
	@ManyToOne()
	@JoinColumn(name="USER_ID")
	private TeePerson user;//用戶
	
	@Column(name="PB_DATE")
	private String pbDate;//排班时间  格式 yyyy-MM
	
	@Column(name="JSON_DATA")
	private String jsonData;//排班数据

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

	public String getPbDate() {
		return pbDate;
	}

	public void setPbDate(String pbDate) {
		this.pbDate = pbDate;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	
}
