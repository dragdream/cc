package com.tianee.oa.core.base.pm.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 合同管理
 * @author kakalion
 *
 */
@Entity
@Table(name="HUMAN_CONTRACT")
public class TeeHumanContract {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="HUMAN_CONTRACT_seq_gen")
	@SequenceGenerator(name="HUMAN_CONTRACT_seq_gen", sequenceName="HUMAN_CONTRACT_seq")
	private int sid;
	
	@Column(name="CON_Title")
	private String conTitle;//合同标题
	
	private String conCode;//合同编号
	
	/**
	 * 劳务合同
	 * 保密合同
	 * 其他
	 */
	private String conType;//合同类型
	
	/**
	 * 无固定期限
	 * 有固定期限
	 */
	private String conAttr;//合同属性
	
	/**
	 * 使用中
	 * 已转正
	 * 已解除
	 */
	private String conStatus;//合同状态
	
	private Calendar validTime;//生效时间
	
	private Calendar invalidTime;//解除时间
	
	private Calendar endTime;//结束时间
	
	private int signCount;//签约次数
	
	private Calendar renewDate;
	
	private Calendar lastRemindDate;
	
	@Lob
	private String remark;//备注
	
	@ManyToOne
	@Index(name="IDX64fcd5937e804a45abe8b29dfa4")
	private TeeHumanDoc humanDoc;//关联档案信息

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getConTitle() {
		return conTitle;
	}

	public void setConTitle(String conTitle) {
		this.conTitle = conTitle;
	}

	public String getConCode() {
		return conCode;
	}

	public void setConCode(String conCode) {
		this.conCode = conCode;
	}

	public Calendar getValidTime() {
		return validTime;
	}

	public void setValidTime(Calendar validTime) {
		this.validTime = validTime;
	}

	public Calendar getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Calendar invalidTime) {
		this.invalidTime = invalidTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public int getSignCount() {
		return signCount;
	}

	public void setSignCount(int signCount) {
		this.signCount = signCount;
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

	public String getConType() {
		return conType;
	}

	public void setConType(String conType) {
		this.conType = conType;
	}

	public String getConAttr() {
		return conAttr;
	}

	public void setConAttr(String conAttr) {
		this.conAttr = conAttr;
	}

	public String getConStatus() {
		return conStatus;
	}

	public void setConStatus(String conStatus) {
		this.conStatus = conStatus;
	}

	public Calendar getRenewDate() {
		return renewDate;
	}

	public void setRenewDate(Calendar renewDate) {
		this.renewDate = renewDate;
	}

	public Calendar getLastRemindDate() {
		return lastRemindDate;
	}

	public void setLastRemindDate(Calendar lastRemindDate) {
		this.lastRemindDate = lastRemindDate;
	}
	
	
}
