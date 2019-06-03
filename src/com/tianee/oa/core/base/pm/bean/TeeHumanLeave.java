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
 * 离职管理
 * @author kakalion
 *
 */
@Entity
@Table(name="Human_Leave")
public class TeeHumanLeave {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="Human_Leave_seq_gen")
	@SequenceGenerator(name="Human_Leave_seq_gen", sequenceName="Human_Leave_seq")
	private int sid;
	
	private String pos;//担任职务
	
	/**
	 * 辞职
	 * 离休
	 * 退休
	 * 借调
	 */
	private String leaveType;//离职类型
	
	private Calendar regTime;//申请日期
	
	private Calendar planTime;//拟离职日期
	
	private Calendar realTime;//实际离职日期
	
	private Calendar payTime;//工资截止日期
	
	private String leaveDept;//离职部门
	
	private String forward;//去向
	
	private String leaveDetail;//离职手续办理
	@Lob()
	private String remark;//备注
	
	@ManyToOne
	@Index(name="IDXe639778b65aa4ea1b2e27a3e180")
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

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
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

	public String getLeaveDept() {
		return leaveDept;
	}

	public void setLeaveDept(String leaveDept) {
		this.leaveDept = leaveDept;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getLeaveDetail() {
		return leaveDetail;
	}

	public void setLeaveDetail(String leaveDetail) {
		this.leaveDetail = leaveDetail;
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
