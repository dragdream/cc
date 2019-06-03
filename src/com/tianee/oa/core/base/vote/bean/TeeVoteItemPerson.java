package com.tianee.oa.core.base.vote.bean;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 投票详情表（记录人员的投投票选项）
 * @author 
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "VOTE_ITEM_PERSON")
public class TeeVoteItemPerson {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="VOTE_ITEM_PERSON_seq_gen")
	@SequenceGenerator(name="VOTE_ITEM_PERSON_seq_gen", sequenceName="VOTE_ITEM_PERSON_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@ManyToOne
	@Index(name="IDXa35fd65d729744ad9b406cdeba5")
	@JoinColumn(name="VOTE_ID")//这是双向的 要写JoinColumn 对面要写 mappedBy
	private TeeVote vote;
	
	@ManyToOne
	@Index(name="IDX48a8d9e36c584f53a8f21734b81")
	@JoinColumn(name="VOTE_ITEM_ID")//这是双向的 要写JoinColumn 对面要写 mappedBy
	private TeeVoteItem voteItem;
	
	@ManyToOne
	@Index(name="IDXb06cdd91a9a141778fcc420b838")
	@JoinColumn(name="USER_ID")//这是双向的 要写JoinColumn 对面要写 mappedBy
	private TeePerson user;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_DATE")
	private Date createDate;//创建时间
	
	@Lob
	@Column(name="VOTE_DATE")
	private String voteData;//文本框所选内容

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeVoteItem getVoteItem() {
		return voteItem;
	}

	public void setVoteItem(TeeVoteItem voteItem) {
		this.voteItem = voteItem;
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

	public String getVoteData() {
		return voteData;
	}

	public void setVoteData(String voteData) {
		this.voteData = voteData;
	}

	public TeeVote getVote() {
		return vote;
	}

	public void setVote(TeeVote vote) {
		this.vote = vote;
	}
	
	
}
