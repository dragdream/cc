package com.tianee.oa.subsys.footprint.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name = "foot_print")
public class TeeFootPrint {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="foot_print_seq_gen")
	@SequenceGenerator(name="foot_print_seq_gen", sequenceName="foot_print_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private TeePerson user;// 当前用户


	@Column(name = "cr_time")
	private Date crTime;//创建时间

	@Column(name = "coordinate_x")
	private double coordinateX;//X坐标

	@Column(name = "coordinate_y")
	private double coordinateY;//Y坐标

	
	@Column(name = "address_description")
	private String addressDescription;//地理位置描述
	
	
	@Column(name = "is_cross")
	private  int isCross;//记录是否越界
	
    
	
	
	
	public int getIsCross() {
		return isCross;
	}

	public void setIsCross(int isCross) {
		this.isCross = isCross;
	}

	public String getAddressDescription() {
		return addressDescription;
	}

	public void setAddressDescription(String addressDescription) {
		this.addressDescription = addressDescription;
	}

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

	public Date getCrTime() {
		return crTime;
	}

	public void setCrTime(Date crTime) {
		this.crTime = crTime;
	}

	public double getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(double coordinatesX) {
		this.coordinateX = coordinatesX;
	}

	public double getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(double coordinateY) {
		this.coordinateY = coordinateY;
	}

	




}
