package com.tianee.oa.core.base.vote.model;

import net.sf.json.JSONArray;

/**
 * 
 * @author CXT
 *
 */

public class TeeSaveVoteModel {
	
	private int itemNum;//子项数目
	private int voteId;//项目ID
	private JSONArray vote;//子项
	public int getItemNum() {
		return itemNum;
	}
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	public int getVoteId() {
		return voteId;
	}
	public void setVoteId(int voteId) {
		this.voteId = voteId;
	}
	public JSONArray getVote() {
		return vote;
	}
	public void setVote(JSONArray vote) {
		this.vote = vote;
	}

	
	
		
}



