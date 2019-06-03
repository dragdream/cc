package com.tianee.oa.core.base.vote.bean;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

/**
 * 投票数据项
 * @author syl
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "VOTE_ITEM")
public class TeeVoteItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="VOTE_ITEM_seq_gen")
	@SequenceGenerator(name="VOTE_ITEM_seq_gen", sequenceName="VOTE_ITEM_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@ManyToOne()
	@Index(name="IDX1f38724ce6e247de80e8cb3ccdf")
	@JoinColumn(name="VOTE_ID")
	private TeeVote vote;//投标项目
	
	@Column(name="ITEM_NAME" , length=500)
	private String itemName;//	ITEM_NAME	CLOB	项目名称			
	
	
	@Column(name="VOTE_COUNT" ,columnDefinition="INT default 0" ,nullable=false)
	private int voteCount;//INT	投票记录数
	
	@Column(name="VOTE_NO" ,columnDefinition="INT default 0" ,nullable=false)
	private int voteNo;//INT	排序号

	@OneToMany(mappedBy="voteItem",fetch=FetchType.LAZY,cascade=CascadeType.ALL)//因为这里是双向的 一对多 所以要写mappedBy	
	private List<TeeVoteItemPerson> voteItemPersons;////投票人员

	public int getVoteNo() {
		return voteNo;
	}

	public void setVoteNo(int voteNo) {
		this.voteNo = voteNo;
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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

	public List<TeeVoteItemPerson> getVoteItemPersons() {
		return voteItemPersons;
	}

	public void setVoteItemPersons(List<TeeVoteItemPerson> voteItemPersons) {
		this.voteItemPersons = voteItemPersons;
	}


	
		
}



