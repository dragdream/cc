package com.beidasoft.xzzf.discretionaryPower.model;

public class discretionaryDetailModel {

	private int id;
	
	private String lowplot; //法律情节
	
	private String lownorm; //裁量基准
	
	private String timeStr; //更新时间
	
	private String people; //更新人
	
	private String powerdetail; //编号
	
	private String power; //职权编号

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


	public String getPeople() {
		return people;
	}

	public void setPeople(String people) {
		this.people = people;
	}

	public String getPowerdetail() {
		return powerdetail;
	}

	public void setPowerdetail(String powerdetail) {
		this.powerdetail = powerdetail;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}
}
