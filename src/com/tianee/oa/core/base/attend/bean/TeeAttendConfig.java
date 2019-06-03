package com.tianee.oa.core.base.attend.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@SuppressWarnings("serial")
@Entity
@Table(name = "ATTEND_CONFIG")
public class TeeAttendConfig{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="ATTEND_CONFIG_seq_gen")
	@SequenceGenerator(name="ATTEND_CONFIG_seq_gen", sequenceName="ATTEND_CONFIG_seq")
	@Column(name="SID")
	private int sid;
	
	@Column(name="DUTY_NAME")
	private String dutyName;
	
	@Column(name="GENERAL")
	private String general;
	
	@Column(name="DUTY_TIME1")
	private Calendar dutyTime1;
	
	@Column(name="DUTY_TIME2")
	private Calendar dutyTime2;
	
	@Column(name="DUTY_TIME3")
	private Calendar dutyTime3;
	
	@Column(name="DUTY_TIME4")
	private Calendar dutyTime4;
	
	@Column(name="DUTY_TIME5")
	private Calendar dutyTime5;
	
	@Column(name="DUTY_TIME6")
	private Calendar dutyTime6;
	
	@Column(name="DUTY_TYPE1",columnDefinition="char(1)")
	private char dutyType1;
	
	@Column(name="DUTY_TYPE2",columnDefinition="char(1)")
	private char dutyType2;
	
	@Column(name="DUTY_TYPE3",columnDefinition="char(1)")
	private char dutyType3;
	
	@Column(name="DUTY_TYPE4",columnDefinition="char(1)")
	private char dutyType4;
	
	@Column(name="DUTY_TYPE5",columnDefinition="char(1)")
	private char dutyType5;
	
	@Column(name="DUTY_TYPE6",columnDefinition="char(1)")
	private char dutyType6;

	
	@Column(name="center_Position")
	private String centerPosition;//移動端签到中心点坐标  格式：xxx,yyy
	
	
	@Column(name="center_Position_Desc")
	private String centerPositionDesc;//移動端签到中心点坐标 
	
	
	@Column(name="radius_")
	private double radius;//移动端签到区域半径大小
	
	
	@Column(name="addr_json")
	private String addrJson;//签到区域
	
	
	
	
	public String getAddrJson() {
		return addrJson;
	}

	public void setAddrJson(String addrJson) {
		this.addrJson = addrJson;
	}

	public String getCenterPosition() {
		return centerPosition;
	}

	public void setCenterPosition(String centerPosition) {
		this.centerPosition = centerPosition;
	}

	public String getCenterPositionDesc() {
		return centerPositionDesc;
	}

	public void setCenterPositionDesc(String centerPositionDesc) {
		this.centerPositionDesc = centerPositionDesc;
	}

	

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public String getGeneral() {
		return general;
	}

	public void setGeneral(String general) {
		this.general = general;
	}

	public Calendar getDutyTime1() {
		return dutyTime1;
	}

	public void setDutyTime1(Calendar dutyTime1) {
		this.dutyTime1 = dutyTime1;
	}

	public Calendar getDutyTime2() {
		return dutyTime2;
	}

	public void setDutyTime2(Calendar dutyTime2) {
		this.dutyTime2 = dutyTime2;
	}

	public Calendar getDutyTime3() {
		return dutyTime3;
	}

	public void setDutyTime3(Calendar dutyTime3) {
		this.dutyTime3 = dutyTime3;
	}

	public Calendar getDutyTime4() {
		return dutyTime4;
	}

	public void setDutyTime4(Calendar dutyTime4) {
		this.dutyTime4 = dutyTime4;
	}

	public Calendar getDutyTime5() {
		return dutyTime5;
	}

	public void setDutyTime5(Calendar dutyTime5) {
		this.dutyTime5 = dutyTime5;
	}

	public Calendar getDutyTime6() {
		return dutyTime6;
	}

	public void setDutyTime6(Calendar dutyTime6) {
		this.dutyTime6 = dutyTime6;
	}

	public char getDutyType1() {
		return dutyType1;
	}

	public void setDutyType1(char dutyType1) {
		this.dutyType1 = dutyType1;
	}

	public char getDutyType2() {
		return dutyType2;
	}

	public void setDutyType2(char dutyType2) {
		this.dutyType2 = dutyType2;
	}

	public char getDutyType3() {
		return dutyType3;
	}

	public void setDutyType3(char dutyType3) {
		this.dutyType3 = dutyType3;
	}

	public char getDutyType4() {
		return dutyType4;
	}

	public void setDutyType4(char dutyType4) {
		this.dutyType4 = dutyType4;
	}

	public char getDutyType5() {
		return dutyType5;
	}

	public void setDutyType5(char dutyType5) {
		this.dutyType5 = dutyType5;
	}

	public char getDutyType6() {
		return dutyType6;
	}

	public void setDutyType6(char dutyType6) {
		this.dutyType6 = dutyType6;
	}
	
}
