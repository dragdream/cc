package com.beidasoft.xzzf.power.model;

public class DiscretionModel {
	private String id;// 主键

	private String powerId; // 职权ID

	private String powerCode; // 自由裁量编号

	private String breaklow; // 违法行为

	private String legalbasis; // 法律依据

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPowerId() {
		return powerId;
	}

	public void setPowerId(String powerId) {
		this.powerId = powerId;
	}

	public String getPowerCode() {
		return powerCode;
	}

	public void setPowerCode(String powerCode) {
		this.powerCode = powerCode;
	}

	public String getBreaklow() {
		return breaklow;
	}

	public void setBreaklow(String breaklow) {
		this.breaklow = breaklow;
	}

	public String getLegalbasis() {
		return legalbasis;
	}

	public void setLegalbasis(String legalbasis) {
		this.legalbasis = legalbasis;
	}

}
