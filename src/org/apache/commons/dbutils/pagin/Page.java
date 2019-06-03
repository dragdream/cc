package org.apache.commons.dbutils.pagin;

import java.util.ArrayList;
import java.util.List;

public class Page<T>{
	private long rowCount=0l;
	private List<T> list=new ArrayList<T>();
	public Page(long rowNo,List<T> t){
		this.rowCount=rowNo;
		this.list=t;
	}
	public long getRowCount() {
		return rowCount;
	}
	public List<T> getList() {
		return list;
	}
}
