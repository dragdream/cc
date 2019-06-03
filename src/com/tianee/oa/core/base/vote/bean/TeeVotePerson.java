package com.tianee.oa.core.base.vote.bean;
import org.hibernate.annotations.Index;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 人员已投票表（记录人员是否已经投票）
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "VOTE_PERSON")
public class TeeVotePerson {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="VOTE_PERSON_seq_gen")
	@SequenceGenerator(name="VOTE_PERSON_seq_gen", sequenceName="VOTE_PERSON_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@ManyToOne
	@Index(name="IDX90b02ca332d54817bc2c75d1cd5")
	@JoinColumn(name="VOTE_ID")//这是双向的 要写JoinColumn 对面要写 mappedBy
	private TeeVote vote;
	
	@ManyToOne
	@Index(name="IDX9b8c8ce3a51a474fa3f92ded24a")
	@JoinColumn(name="USER_ID")//这是双向的 要写JoinColumn 对面要写 mappedBy
	private TeePerson user;
	
	@Column(name="ANONYMITY",columnDefinition="char(1) default 0" )
	private String  anonymity;//	ANONYMITY	Varchar（10）	是否匿名投票0：否  1：是
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_DATE")
	private Date createDate;//创建时间
	
	

	public String getAnonymity() {
		return anonymity;
	}

	public void setAnonymity(String anonymity) {
		this.anonymity = anonymity;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeVote getVote() {
		return vote;
	}

	public void setVote(TeeVote vote) {
		this.vote = vote;
	}

	public TeePerson getUser() {
		return user;
	}

	public void setUser(TeePerson user) {
		this.user = user;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
