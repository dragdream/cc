package com.tianee.oa.core.base.attend.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "ATTEND_DUTY")
public class TeeAttendDuty{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="ATTEND_DUTY_seq_gen")
	@SequenceGenerator(name="ATTEND_DUTY_seq_gen", sequenceName="ATTEND_DUTY_seq")
	@Column(name="SID")
	private int sid;
	
	@Column(name="USER_ID")
	private int userId;
	
	@Column(name="REGISTER_TYPE")
	private String registerType;
	
	@Column(name="REGISTER_TIME")
	private Calendar registerTime;
	
	@Column(name="REGISTER_IP")
	private String registerIp;
	
	@Column(name="REMARK")
	@Lob()
	private String remark;
	
	@Column(name="POSITION_")
	private String position;//坐标系
	
	@Column(name="DUTY_TYPE")
	private int dutyType;//登记次数
	
	@Column(name="HOURS")
	private long hours;//上班时长
	
	@Column(name="phone_model")
	private String phoneModel;//手机型号
	
	

	public String getPhoneModel() {
		return phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public Calendar getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Calendar registerTime) {
		this.registerTime = registerTime;
	}

	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getDutyType() {
		return dutyType;
	}

	public void setDutyType(int dutyType) {
		this.dutyType = dutyType;
	}

	public long getHours() {
		return hours;
	}

	public void setHours(long hours) {
		this.hours = hours;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	
	
}
