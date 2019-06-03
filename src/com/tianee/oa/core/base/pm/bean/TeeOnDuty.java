package com.tianee.oa.core.base.pm.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="on_duty")
public class TeeOnDuty implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="on_duty_seq_gen")
	@SequenceGenerator(name="on_duty_seq_gen", sequenceName="on_duty_seq")
	private int uuid;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_UUID")
	@Index(name="on_duty_USER_UUID")
	private TeePerson user;//值班人员
	
	@Column(name="PB_TYPE",length=30)
	private String pbType;//排班类型
	
	@Column(name="ZB_TYPE",length=30)
	private String zbType;//值班类型
	
	
	@Column(name="begin_time")
	private Date beginTime;//开始日期
	
	@Column(name="end_time")
	private Date endTime;//结束日期
	
	
	@Lob
	@Column(name = "DEMAND") 
	private String demand;//值班要求
	
	@Lob
	@Column(name = "REMARK_")
	private String remark;//备注

	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public TeePerson getUser() {
		return user;
	}

	public void setUser(TeePerson user) {
		this.user = user;
	}

	public String getPbType() {
		return pbType;
	}

	public void setPbType(String pbType) {
		this.pbType = pbType;
	}

	public String getZbType() {
		return zbType;
	}

	public void setZbType(String zbType) {
		this.zbType = zbType;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDemand() {
		return demand;
	}

	public void setDemand(String demand) {
		this.demand = demand;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
