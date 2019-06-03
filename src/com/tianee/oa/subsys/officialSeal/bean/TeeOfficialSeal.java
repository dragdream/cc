package com.tianee.oa.subsys.officialSeal.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 公章
 * @author xsy
 *
 */
@Entity
@Table(name = "OFFICIAL_SEAL")
public class TeeOfficialSeal {
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="OFFICIAL_SEAL_seq_gen")
	@SequenceGenerator(name="OFFICIAL_SEAL_seq_gen", sequenceName="OFFICIAL_SEAL_seq")
	private int sid;
	
	@Column(name="KEY_WORD")
	private String keyWord;//关键字
	
	@Column(name="BUSS_RULENUM")
	private String bussRuleNum;//签章规则
	
	@Column(name="TEMPLATE_NUM")
	private String templateNum;//签章模板
	
	@Column(name="CREDIT_CODE")
	private String creditCode;//组织机构代码
	
	
	@Column(name="USER_NAME")
	private String userName;//企业用户名称


	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public String getKeyWord() {
		return keyWord;
	}


	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}


	public String getBussRuleNum() {
		return bussRuleNum;
	}


	public void setBussRuleNum(String bussRuleNum) {
		this.bussRuleNum = bussRuleNum;
	}


	public String getTemplateNum() {
		return templateNum;
	}


	public void setTemplateNum(String templateNum) {
		this.templateNum = templateNum;
	}


	public String getCreditCode() {
		return creditCode;
	}


	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
	
}
