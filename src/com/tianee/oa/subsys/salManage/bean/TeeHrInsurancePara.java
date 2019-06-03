package com.tianee.oa.subsys.salManage.bean;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 基本参数设置
 * @author think
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "HR_INSURANCE_PARA")
public class TeeHrInsurancePara implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="HR_INSU_PA_seq_gen")
	@SequenceGenerator(name="HR_INSU_PA_seq_gen", sequenceName="HR_INSU_PA_seq")
	private int sid;//自增id
	
	@Column(name="INSURANCE_NAME")
	private String insuranceName;
	
	@Column(name="PENSION_U_PAY")
	private double  pensionUPay;//单位养老系数
	
	@Column(name="PENSION_U_PAY_ADD")
	private double  pensionUPayAdd;//单位养老额外钱
	
	@Column(name="PENSION_P_PAY")
	private double  pensionPPay;//个人养老系数
	
	@Column(name="PENSION_P_PAY_ADD")
	private double  pensionPPayAdd;//个人养老额外钱
	
	@Column(name="HEALTH_U_PAY")
	private double  healthUPay;//单位医疗系数
	
	@Column(name="HEALTH_U_PAY_ADD")
	private double  healthUPayAdd;//单位医疗额外钱
	
	@Column(name="HEALTH_P_PAY")
	private double  healthPPay;//个人医疗系数
	
	@Column(name="HEALTH_P_PAY_ADD")
	private double  healthPPayAdd;//个人医疗额外钱

	@Column(name="UNEMPLOYMENT_U_PAY")
	private double  unemploymentUPay;//单位失业系数
	
	@Column(name="UNEMPLOYMENT_U_PAY_ADD")
	private double  unemploymentUPayAdd;//单位失业额外钱
	
	@Column(name="UNEMPLOYMENT_P_PAY")
	private double  unemploymentPPay;//个人失业系数
	
	@Column(name="UNEMPLOYMENT_P_PAY_ADD")
	private double  unemploymentPPayAdd;//个人失业额外钱
	
	@Column(name="INJURY_U_PAY")
	private double  injuryUPay;//单位工伤系数
	
	@Column(name="INJURY_U_PAY_ADD")
	private double  injuryUPayAdd;//单位工伤额外钱
	
	@Column(name="INJURY_P_PAY")
	private double  injuryPPay;//个人工伤系数
	
	@Column(name="INJURY_P_PAY_ADD")
	private double  injuryPPayAdd;//个人工伤额外钱
	
	@Column(name="HOUSING_U_PAY")
	private double  housingUPay;//单位住房系数
	
	@Column(name="HOUSING_U_PAY_ADD")
	private double  housingUPayAdd;//单位住房额外钱
	
	@Column(name="HOUSING_P_PAY")
	private double  housingPPay;//个人住房系数
	
	@Column(name="HOUSING_P_PAY_ADD")
	private double  housingPPayAdd;//个人住房额外钱
	
	@Column(name="MATERNITY_U_PAY")
	private double  maternityUPay;//生育单位支付百分比
	
	@Column(name="MATERNITY_U_PAY_ADD")
	private double  maternityUPayAdd;//生育单位支付额外钱
	
	@Column(name="MATERNITY_P_PAY")
	private double  maternityPPay;//生育个人支付百分比
	
	@Column(name="MATERNITY_P_PAY_ADD")
	private double  maternityPPayAdd;//生育单位个人支付额外钱
	
	@Column(name="YES_OTHER" ,columnDefinition="INT default 0" ,nullable=false)
	private int  yesOther;//是否显示在工资项目中（0 否 1 是）
	
	@Column(name="SB_BASE")
	private double  sbBase;//社保基数
	
	@Column(name="SB_MIN")
	private double  sbMin;//社保基数最低
	
	@Column(name="SB_MAX")
	private double  sbMax;//社保基数最低
	
	@Column(name="GJJ_BASE")
	private double  gjjBase;//公积金基数
	
	@Column(name="GJJ_MIN")
	private double  gjjMin;//公积金基数最低
	
	@Column(name="GJJ_MAX")
	private double  gjjMax;//公积金基数最高

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public double getPensionUPay() {
		return pensionUPay;
	}

	public void setPensionUPay(double pensionUPay) {
		this.pensionUPay = pensionUPay;
	}

	public double getPensionUPayAdd() {
		return pensionUPayAdd;
	}

	public void setPensionUPayAdd(double pensionUPayAdd) {
		this.pensionUPayAdd = pensionUPayAdd;
	}

	public double getPensionPPay() {
		return pensionPPay;
	}

	public void setPensionPPay(double pensionPPay) {
		this.pensionPPay = pensionPPay;
	}

	public double getPensionPPayAdd() {
		return pensionPPayAdd;
	}

	public void setPensionPPayAdd(double pensionPPayAdd) {
		this.pensionPPayAdd = pensionPPayAdd;
	}

	public double getHealthUPay() {
		return healthUPay;
	}

	public void setHealthUPay(double healthUPay) {
		this.healthUPay = healthUPay;
	}

	public double getHealthUPayAdd() {
		return healthUPayAdd;
	}

	public void setHealthUPayAdd(double healthUPayAdd) {
		this.healthUPayAdd = healthUPayAdd;
	}

	public double getHealthPPay() {
		return healthPPay;
	}

	public void setHealthPPay(double healthPPay) {
		this.healthPPay = healthPPay;
	}

	public double getHealthPPayAdd() {
		return healthPPayAdd;
	}

	public void setHealthPPayAdd(double healthPPayAdd) {
		this.healthPPayAdd = healthPPayAdd;
	}

	public double getUnemploymentUPay() {
		return unemploymentUPay;
	}

	public void setUnemploymentUPay(double unemploymentUPay) {
		this.unemploymentUPay = unemploymentUPay;
	}

	public double getUnemploymentUPayAdd() {
		return unemploymentUPayAdd;
	}

	public void setUnemploymentUPayAdd(double unemploymentUPayAdd) {
		this.unemploymentUPayAdd = unemploymentUPayAdd;
	}

	public double getUnemploymentPPay() {
		return unemploymentPPay;
	}

	public void setUnemploymentPPay(double unemploymentPPay) {
		this.unemploymentPPay = unemploymentPPay;
	}

	public double getUnemploymentPPayAdd() {
		return unemploymentPPayAdd;
	}

	public void setUnemploymentPPayAdd(double unemploymentPPayAdd) {
		this.unemploymentPPayAdd = unemploymentPPayAdd;
	}

	public double getInjuryUPay() {
		return injuryUPay;
	}

	public void setInjuryUPay(double injuryUPay) {
		this.injuryUPay = injuryUPay;
	}

	public double getInjuryUPayAdd() {
		return injuryUPayAdd;
	}

	public void setInjuryUPayAdd(double injuryUPayAdd) {
		this.injuryUPayAdd = injuryUPayAdd;
	}

	public double getInjuryPPay() {
		return injuryPPay;
	}

	public void setInjuryPPay(double injuryPPay) {
		this.injuryPPay = injuryPPay;
	}

	public double getInjuryPPayAdd() {
		return injuryPPayAdd;
	}

	public void setInjuryPPayAdd(double injuryPPayAdd) {
		this.injuryPPayAdd = injuryPPayAdd;
	}

	public double getHousingUPay() {
		return housingUPay;
	}

	public void setHousingUPay(double housingUPay) {
		this.housingUPay = housingUPay;
	}

	public double getHousingUPayAdd() {
		return housingUPayAdd;
	}

	public void setHousingUPayAdd(double housingUPayAdd) {
		this.housingUPayAdd = housingUPayAdd;
	}

	public double getHousingPPay() {
		return housingPPay;
	}

	public void setHousingPPay(double housingPPay) {
		this.housingPPay = housingPPay;
	}

	public double getHousingPPayAdd() {
		return housingPPayAdd;
	}

	public void setHousingPPayAdd(double housingPPayAdd) {
		this.housingPPayAdd = housingPPayAdd;
	}

	public double getMaternityUPay() {
		return maternityUPay;
	}

	public void setMaternityUPay(double maternityUPay) {
		this.maternityUPay = maternityUPay;
	}

	public double getMaternityUPayAdd() {
		return maternityUPayAdd;
	}

	public void setMaternityUPayAdd(double maternityUPayAdd) {
		this.maternityUPayAdd = maternityUPayAdd;
	}

	public double getMaternityPPay() {
		return maternityPPay;
	}

	public void setMaternityPPay(double maternityPPay) {
		this.maternityPPay = maternityPPay;
	}

	public double getMaternityPPayAdd() {
		return maternityPPayAdd;
	}

	public void setMaternityPPayAdd(double maternityPPayAdd) {
		this.maternityPPayAdd = maternityPPayAdd;
	}

	public int getYesOther() {
		return yesOther;
	}

	public void setYesOther(int yesOther) {
		this.yesOther = yesOther;
	}

	public String getInsuranceName() {
		return insuranceName;
	}

	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}

	public double getSbBase() {
		return sbBase;
	}

	public void setSbBase(double sbBase) {
		this.sbBase = sbBase;
	}

	public double getGjjBase() {
		return gjjBase;
	}

	public void setGjjBase(double gjjBase) {
		this.gjjBase = gjjBase;
	}

	public double getSbMin() {
		return sbMin;
	}

	public void setSbMin(double sbMin) {
		this.sbMin = sbMin;
	}

	public double getSbMax() {
		return sbMax;
	}

	public void setSbMax(double sbMax) {
		this.sbMax = sbMax;
	}

	public double getGjjMin() {
		return gjjMin;
	}

	public void setGjjMin(double gjjMin) {
		this.gjjMin = gjjMin;
	}

	public double getGjjMax() {
		return gjjMax;
	}

	public void setGjjMax(double gjjMax) {
		this.gjjMax = gjjMax;
	}
	
	
}
