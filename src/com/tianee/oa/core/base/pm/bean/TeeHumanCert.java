package com.tianee.oa.core.base.pm.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 证书管理
 * @author kakalion
 *
 */
@Entity
@Table(name="HUMAN_CERT")
public class TeeHumanCert {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="HUMAN_CERT_seq_gen")
	@SequenceGenerator(name="HUMAN_CERT_seq_gen", sequenceName="HUMAN_CERT_seq")
	private int sid;
	
	private String certCode;//证书编号
	
	/**
	 * 驾驶证
	 * 健康证
	 * 暂住证
	 * 技能证
	 * 其他
	 */
	private String certType;//证件类型
	
	private String certName;//证件名称
	
	private Calendar getTime;//取证日期
	
	private Calendar validTime;//生效日期
	
	private Calendar endTime;//结束日期
	/**
	 * 未生效
	 * 生效中
	 * 已到期
	 */
	private String certAttr;//证件属性
	
	private String certOrg;//发证机构
	@Lob()
	private String remark;//备注
	
	@ManyToOne
	@Index(name="IDX69b565873df2419bb5fde39aa66")
	private TeeHumanDoc humanDoc;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getCertCode() {
		return certCode;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}

	public String getCertName() {
		return certName;
	}

	public void setCertName(String certName) {
		this.certName = certName;
	}

	public Calendar getGetTime() {
		return getTime;
	}

	public void setGetTime(Calendar getTime) {
		this.getTime = getTime;
	}

	public Calendar getValidTime() {
		return validTime;
	}

	public void setValidTime(Calendar validTime) {
		this.validTime = validTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public String getCertOrg() {
		return certOrg;
	}

	public void setCertOrg(String certOrg) {
		this.certOrg = certOrg;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public TeeHumanDoc getHumanDoc() {
		return humanDoc;
	}

	public void setHumanDoc(TeeHumanDoc humanDoc) {
		this.humanDoc = humanDoc;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertAttr() {
		return certAttr;
	}

	public void setCertAttr(String certAttr) {
		this.certAttr = certAttr;
	}
	
	
}
