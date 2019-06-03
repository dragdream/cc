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
 * 工作经历管理
 * @author kakalion
 *
 */
@Entity
@Table(name="Human_Experience")
public class TeeHumanExperience {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="Human_Experience_seq_gen")
	@SequenceGenerator(name="Human_Experience_seq_gen", sequenceName="Human_Experience_seq")
	private int sid;
	
	private String pos;//担任职务
	
	private String dept;//所在部门
	
	private String prover;//证明人
	
	private String tradeType;//行业类别
	
	private Calendar startTime;//开始日期
	
	private Calendar endTime;//结束日期
	
	private String workAt;//工作单位
	
	private String contact;//联系方式
	@Lob()
	private String content;//工作内容
	
	private String leaveCause;//离职原因
	@Lob()
	private String remark;//备注
	
	@ManyToOne
	@Index(name="IDXf164ef507d4449b6abe3c92655b")
	private TeeHumanDoc humanDoc;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getProver() {
		return prover;
	}

	public void setProver(String prover) {
		this.prover = prover;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public String getWorkAt() {
		return workAt;
	}

	public void setWorkAt(String workAt) {
		this.workAt = workAt;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLeaveCause() {
		return leaveCause;
	}

	public void setLeaveCause(String leaveCause) {
		this.leaveCause = leaveCause;
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
	
	
}
