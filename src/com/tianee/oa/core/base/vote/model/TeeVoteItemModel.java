package com.tianee.oa.core.base.vote.model;

/**
 * 
 * @author syl
 *
 */

public class TeeVoteItemModel {
	
	private int sid;//自增id
	private int voteId;//VOTE;//投标项目
	private String voteName;//
	private String itemName;//	ITEM_NAME	CLOB	项目名称			
	private int voteCount;//INT	投票记录数			
	private int voteNo;//INT	排序号
	
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
	public int getVoteId() {
		return voteId;
	}
	public void setVoteId(int voteId) {
		this.voteId = voteId;
	}
	public String getVoteName() {
		return voteName;
	}
	public void setVoteName(String voteName) {
		this.voteName = voteName;
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

	
		
}



