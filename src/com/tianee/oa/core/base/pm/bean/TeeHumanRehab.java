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
 * 复职管理
 * @author kakalion
 *
 */
@Entity
@Table(name="Human_Rehab")
public class TeeHumanRehab {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="Human_Rehab_seq_gen")
	@SequenceGenerator(name="Human_Rehab_seq_gen", sequenceName="Human_Rehab_seq")
	private int sid;
	
	private String pos;//担任职务
	
	/**
	 * 调回
	 * 复原
	 */
	private String rehabType;//离职类型
	
	private Calendar regTime;//申请日期
	
	private Calendar planTime;//拟复职日期
	
	private Calendar realTime;//实际复职日期
	
	private Calendar payTime;//工资恢复日期
	
	private String rehabDept;//复职部门
	
	private String rehabDetail;//复职手续办理
	@Lob()
	private String remark;//备注
	
	@ManyToOne
	@Index(name="IDXc95c5986a0214646b68af94b7ae")
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

	public String getRehabType() {
		return rehabType;
	}

	public void setRehabType(String rehabType) {
		this.rehabType = rehabType;
	}

	public Calendar getRegTime() {
		return regTime;
	}

	public void setRegTime(Calendar regTime) {
		this.regTime = regTime;
	}

	public Calendar getPlanTime() {
		return planTime;
	}

	public void setPlanTime(Calendar planTime) {
		this.planTime = planTime;
	}

	public Calendar getRealTime() {
		return realTime;
	}

	public void setRealTime(Calendar realTime) {
		this.realTime = realTime;
	}

	public Calendar getPayTime() {
		return payTime;
	}

	public void setPayTime(Calendar payTime) {
		this.payTime = payTime;
	}

	public String getRehabDept() {
		return rehabDept;
	}

	public void setRehabDept(String rehabDept) {
		this.rehabDept = rehabDept;
	}

	public String getRehabDetail() {
		return rehabDetail;
	}

	public void setRehabDetail(String rehabDetail) {
		this.rehabDetail = rehabDetail;
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
