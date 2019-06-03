package com.tianee.lucene.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public class DocumentRecords implements Serializable{
	private int totalHits;//记录总数
	private List<Map> recordList;//记录集合
	
	
	public int getTotalHits() {
		return totalHits;
	}
	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}
	public List<Map> getRecordList() {
		return recordList;
	}
	public void setRecordList(List<Map> recordList) {
		this.recordList = recordList;
	}
	
	
}
