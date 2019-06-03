package com.tianee.lucene.entity;

import java.io.Serializable;

public class SearchModel implements Serializable{

	private String space;//索引库路径
	private String[] defaultSearchField;//搜索的字段
	private String[] returnField;//返回的字段
	private int returnFieldWordCount[];//设置对应返回的字段最大字数
	private String[] lightedField;//设置需要被高亮显示摘要的字段
	private String term = "";//词条
	private int curPage = 1;//当前页数
	private int pageSize = 20;//每页的记录数
	
	public SearchModel() {
		defaultSearchField = new String[]{""};//搜索的字段
		returnField = new String[]{};//返回的字段
		lightedField = new String[]{};//设置需要被高亮显示摘要的字段
		setReturnFieldWordCount(new int[]{});//
	}
	
	
	public String[] getDefaultSearchField() {
		return defaultSearchField;
	}
	public void setDefaultSearchField(String[] defaultSearchField) {
		this.defaultSearchField = defaultSearchField;
	}
	public String[] getReturnField() {
		return returnField;
	}
	public void setReturnField(String[] returnField) {
		this.returnField = returnField;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String[] getLightedField() {
		return lightedField;
	}
	public void setLightedField(String[] lightedField) {
		this.lightedField = lightedField;
	}


	public String getSpace() {
		return space;
	}


	public void setSpace(String space) {
		this.space = space;
	}


	public int[] getReturnFieldWordCount() {
		return returnFieldWordCount;
	}


	public void setReturnFieldWordCount(int returnFieldWordCount[]) {
		this.returnFieldWordCount = returnFieldWordCount;
	}
	
}
