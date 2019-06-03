package com.tianee.oa.subsys.crm.core.target.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;
@Entity
@Table(name = "CRM_COMPANY_TARGET")
public class TeeCrmCompanyTarget {
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO, generator="CRM_COMPANY_TARGET_seq_gen")
	  @SequenceGenerator(name="CRM_COMPANY_TARGET_seq_gen", sequenceName="CRM_COMPANY_TARGET_seq")
	  @Column(name = "SID")
	  private int sid;
      
	  @Column(name = "year")
	  private int year;//财年
      
	  @Column(name = "m1")
	  private double m1;//1月销售目标
	  
	  @Column(name = "m2")
	  private double m2;//2月销售目标
      
	  @Column(name = "m3")
	  private double m3;
      
	  @Column(name = "m4")
	  private double m4;

	  @Column(name = "m5")
	  private double m5;
      
	  @Column(name = "m6")
	  private double m6;
      
	  @Column(name = "m7")
	  private double m7;
      
	  @Column(name = "m8")
	  private double m8;
      
	  @Column(name = "m9")
	  private double m9;
      
	  @Column(name = "m10")
	  private double m10;
      
	  @Column(name = "m11")
	  private double m11;

	  @Column(name = "m12")
	  private double m12;

	  @Column(name = "total")
	  private double total;//总额1-12月份相加

	  @Column(name = "create_time")
	  private Calendar createTime;//创建时间

	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "cr_user_id")
	  private TeePerson crUser;//创建人

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

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public TeePerson getCrUser() {
		return crUser;
	}

	public void setCrUser(TeePerson crUser) {
		this.crUser = crUser;
	}

}
