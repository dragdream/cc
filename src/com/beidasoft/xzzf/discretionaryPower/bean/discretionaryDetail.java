package com.beidasoft.xzzf.discretionaryPower.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FX_DISCRETIONARY_DETAIL")
public class discretionaryDetail {

	@Id
	@Column(name = "ID") 
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "FX_DISCRETIONARY_DETAIL_seq_gen")
	@SequenceGenerator(name = "FX_DISCRETIONARY_DETAIL_seq_gen", sequenceName = "FX_DISCRETIONARY_DETAIL_seq")
	private int id; //

	@Column(name = "POWERDETAIL")
	private String powerdetail; //编号
	
	@Column(name = "POWER")
	private String power; //职权编号
	
	@Column(name = "LOWPLOT")
	private String lowplot; //法律情节

	@Column(name = "LOWNORM")
	private String lownorm; //裁量基准
	
	@Column(name = "TIME")
	private Date time; //更新时间
	
	@Column(name = "PEOPLE")
	private String people; //更新人
	
	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	
	public String getPowerdetail() {
		return powerdetail;
	}

	public void setPowerdetail(String powerdetail) {
		this.powerdetail = powerdetail;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLowplot() {
		return lowplot;
	}

	public void setLowplot(String lowplot) {
		this.lowplot = lowplot;
	}

	public String getLownorm() {
		return lownorm;
	}

	public void setLownorm(String lownorm) {
		this.lownorm = lownorm;
	}


	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getPeople() {
		return people;
	}

	public void setPeople(String people) {
		this.people = people;
	}
	
}
