package com.tianee.oa.core.workflow.flowrun.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomerInfo;

@Entity
@Table(name="RUN_CUSTOMER_REL")
public class TeeRunCustomerRel {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@ManyToOne
	@Index(name="IDXdb04e7d7aca8416fa5d3176cfbb")
	@JoinColumn(name="RUN_ID")
	private TeeFlowRun flowRun;
	
	@ManyToOne
	@Index(name="IDXdc916fbdcbd0445490519aece9b")
	@JoinColumn(name="CUSTOMER_ID")
	private TeeCrmCustomerInfo customerInfo;
	
	@Column(name="TIME_")
	private Calendar time;
	
	@Column(name="USER_ID")
	private int userId;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public TeeFlowRun getFlowRun() {
		return flowRun;
	}

	public void setFlowRun(TeeFlowRun flowRun) {
		this.flowRun = flowRun;
	}

	public TeeCrmCustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(TeeCrmCustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	public Calendar getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
