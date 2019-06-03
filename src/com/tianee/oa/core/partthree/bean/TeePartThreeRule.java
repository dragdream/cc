package com.tianee.oa.core.partthree.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "part_three_rule")
public class TeePartThreeRule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="part_three_rule_seq_gen")
	@SequenceGenerator(name="part_three_rule_seq_gen", sequenceName="part_three_rule_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@Column(name="RULE_CODE")
	private String ruleCode;//规则代码
	
	
	@Column(name="RULE_DESC")
	private String ruleDesc;//规则描述
	
	
	@Column(name="OPER_PRIV")
	private int operPriv;//操作权限
	
	@Column(name="IS_OPEN")
	private int isOpen;//开启状态

	public int getSid() {
		return sid;
	}

	public String getRuleCode() {
		return ruleCode;
	}

	public String getRuleDesc() {
		return ruleDesc;
	}

	public int getOperPriv() {
		return operPriv;
	}

	public int getIsOpen() {
		return isOpen;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	

	public void setOperPriv(int operPriv) {
		this.operPriv = operPriv;
	}

	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}
	
	
	
}
