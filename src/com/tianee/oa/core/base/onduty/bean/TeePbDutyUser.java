package com.tianee.oa.core.base.onduty.bean;

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

@Entity
@Table(name="pb_user")
public class TeePbDutyUser {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="pb_user_seq_gen")
	@SequenceGenerator(name="pb_user_seq_gen", sequenceName="pb_user_seq")
	@Column(name = "SID")
	private int sid;
	
	@Column(name="PDUTY_ID")
	private int pdutyId;
	
	@Column(name="TYPE_CHILD_ID")
	private int typeChildId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID")
	private TeePerson user;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getPdutyId() {
		return pdutyId;
	}

	public void setPdutyId(int pdutyId) {
		this.pdutyId = pdutyId;
	}

	public int getTypeChildId() {
		return typeChildId;
	}

	public void setTypeChildId(int typeChildId) {
		this.typeChildId = typeChildId;
	}

	public TeePerson getUser() {
		return user;
	}

	public void setUser(TeePerson user) {
		this.user = user;
	}
	
}
