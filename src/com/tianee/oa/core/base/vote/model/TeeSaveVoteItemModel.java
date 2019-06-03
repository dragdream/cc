package com.tianee.oa.core.base.vote.model;

import net.sf.json.JSONArray;

/**
 * 
 * @author CXT
 *
 */

public class TeeSaveVoteItemModel {
	
	private int minNum;//最小
	private int maxNum;//最大
	private String content;//说明内容
	private int ifContent;//是否有说明
	private int required;//是否必填
	private int type;//类型
	private int voteNo;//序号
	private String voteSubject;//子项标题
	private String textHtml;//html
	private JSONArray item;//每个选项标题
	
	
	public int getVoteNo() {
		return voteNo;
	}
	public void setVoteNo(int voteNo) {
		this.voteNo = voteNo;
	}
	public String getTextHtml() {
		return textHtml;
	}
	public void setTextHtml(String textHtml) {
		this.textHtml = textHtml;
	}
	public int getMinNum() {
		return minNum;
	}
	public void setMinNum(int minNum) {
		this.minNum = minNum;
	}
	public int getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getIfContent() {
		return ifContent;
	}
	public void setIfContent(int ifContent) {
		this.ifContent = ifContent;
	}
	public int getRequired() {
		return required;
	}
	public void setRequired(int required) {
		this.required = required;
	}
	public String getVoteSubject() {
		return voteSubject;
	}
	public void setVoteSubject(String voteSubject) {
		this.voteSubject = voteSubject;
	}
	public JSONArray getItem() {
		return item;
	}
	public void setItem(JSONArray item) {
		this.item = item;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

		
}



