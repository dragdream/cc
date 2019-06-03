package com.tianee.oa.subsys.crm.core.target.model;


public class TeeCrmCompanyTargetModel {

	private int sid;
	private int year;// 财年
	private double m1;// 1月销售目标
	private double m2;// 2月销售目标
	private double m3;
	private double m4;
	private double m5;
	private double m6;
	private double m7;
	private double m8;
	private double m9;
	private double m10;
	private double m11;
	private double m12;
	private double total;// 总额1-12月份相加
	private String createTimeStr;// 创建时间
	private int crUserId;// 创建人
	private String crUserName;// 创建人姓名

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getM1() {
		return m1;
	}

	public void setM1(double m1) {
		this.m1 = m1;
	}

	public double getM2() {
		return m2;
	}

	public void setM2(double m2) {
		this.m2 = m2;
	}

	public double getM3() {
		return m3;
	}

	public void setM3(double m3) {
		this.m3 = m3;
	}

	public double getM4() {
		return m4;
	}

	public void setM4(double m4) {
		this.m4 = m4;
	}

	public double getM5() {
		return m5;
	}

	public void setM5(double m5) {
		this.m5 = m5;
	}

	public double getM6() {
		return m6;
	}

	public void setM6(double m6) {
		this.m6 = m6;
	}

	public double getM7() {
		return m7;
	}

	public void setM7(double m7) {
		this.m7 = m7;
	}

	public double getM8() {
		return m8;
	}

	public void setM8(double m8) {
		this.m8 = m8;
	}

	public double getM9() {
		return m9;
	}

	public void setM9(double m9) {
		this.m9 = m9;
	}

	public double getM10() {
		return m10;
	}

	public void setM10(double m10) {
		this.m10 = m10;
	}

	public double getM11() {
		return m11;
	}

	public void setM11(double m11) {
		this.m11 = m11;
	}

	public double getM12() {
		return m12;
	}

	public void setM12(double m12) {
		this.m12 = m12;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getCrUserId() {
		return crUserId;
	}

	public void setCrUserId(int crUserId) {
		this.crUserId = crUserId;
	}

	public String getCrUserName() {
		return crUserName;
	}

	public void setCrUserName(String crUserName) {
		this.crUserName = crUserName;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
}
