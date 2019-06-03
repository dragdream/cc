package com.tianee.oa.core.general.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="IP_RULE")
public class TeeIpRule {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="IP_RULE_seq_gen")
	@SequenceGenerator(name="IP_RULE_seq_gen", sequenceName="IP_RULE_seq")
	@Column(name="SID")
	private int sid;//自增id

	@Column(name="BEGIN_IP",length=50)
	private String beginIp;
	
	@Column(name="END_IP",length=50)
	private String endIp;
	
	@Column(name="IP_TYPE",length=20)
	private String ipType;
	
	@Column(name="REMARK",length=500)
	private String remark;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getBeginIp() {
		return beginIp;
	}

	public void setBeginIp(String beginIp) {
		this.beginIp = beginIp;
	}

	public String getEndIp() {
		return endIp;
	}

	public void setEndIp(String endIp) {
		this.endIp = endIp;
	}

	public String getIpType() {
		return ipType;
	}

	public void setIpType(String ipType) {
		this.ipType = ipType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}

	
