package com.tianee.oa.core.workflow.workmanage.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;

@Entity
@Table(name="FLOW_RULE")
public class TeeFlowRule  implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FLOW_RULE_seq_gen")
	@SequenceGenerator(name="FLOW_RULE_seq_gen", sequenceName="FLOW_RULE_seq")
	@Column(name="SID")
	private int sid;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX077ed1af424342118d4b5fdbda9")
	@JoinColumn(name="FLOW_ID")
	private TeeFlowType flowType;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX81b95d59c0874ae48c70473be47")
	@JoinColumn(name="USER_ID")
	private TeePerson user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDXaef044d50ef74c2ca52042d9f7a")
	@JoinColumn(name="TO_USER")
	private TeePerson toUser;
	
	@Column(name="BEGIN_DATE")
	private Date beginDate;
	
	@Column(name="END_DATE")
	private Date endDate;
	
	@Column(name="STATUS")
	private int status;//1：一直有效   0：非一直有效

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeFlowType getFlowType() {
		return flowType;
	}

	public void setFlowType(TeeFlowType flowType) {
		this.flowType = flowType;
	}

	public TeePerson getUser() {
		return user;
	}

	public void setUser(TeePerson user) {
		this.user = user;
	}

	public TeePerson getToUser() {
		return toUser;
	}

	public void setToUser(TeePerson toUser) {
		this.toUser = toUser;
	}

	
	
	

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
