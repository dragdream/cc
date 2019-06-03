package com.tianee.oa.subsys.contract.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="CONTRACT_REMIND")
public class TeeContractRemind {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CONTRACT_REMIND_seq_gen")
	@SequenceGenerator(name="CONTRACT_REMIND_seq_gen", sequenceName="CONTRACT_REMIND_seq")
	@Column(name="SID")
	private int sid;
	
	@Lob
	@Column(name="REMIND_CONTENT")
	private String remindContent;
	
	@Column(name="REMIND_TIME")
	private Calendar remindTime;
	
	@Lob
	@Column(name="TO_USER_IDS")
	private String toUserIds;
	
	@Lob
	@Column(name="TO_USER_UIDS")
	private String toUserUids;
	
	@Lob
	@Column(name="TO_USER_NAMES")
	private String toUserNames;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDXad5b595c0a044a5ebaf88d9602b")
	@JoinColumn(name="CONTRACT_ID")
	private TeeContract contract;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX8d38316a26834eac904bc0701d9")
	@JoinColumn(name="CR_USER")
	private TeePerson crUser;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getRemindContent() {
		return remindContent;
	}

	public void setRemindContent(String remindContent) {
		this.remindContent = remindContent;
	}

	public Calendar getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(Calendar remindTime) {
		this.remindTime = remindTime;
	}

	public String getToUserIds() {
		return toUserIds;
	}

	public void setToUserIds(String toUserIds) {
		this.toUserIds = toUserIds;
	}

	public TeeContract getContract() {
		return contract;
	}

	public void setContract(TeeContract contract) {
		this.contract = contract;
	}

	public TeePerson getCrUser() {
		return crUser;
	}

	public void setCrUser(TeePerson crUser) {
		this.crUser = crUser;
	}

	public String getToUserUids() {
		return toUserUids;
	}

	public void setToUserUids(String toUserUids) {
		this.toUserUids = toUserUids;
	}

	public String getToUserNames() {
		return toUserNames;
	}

	public void setToUserNames(String toUserNames) {
		this.toUserNames = toUserNames;
	}
	
}
