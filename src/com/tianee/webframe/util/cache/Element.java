package com.tianee.webframe.util.cache;

import java.io.Serializable;

public class Element implements Serializable{
	private String key;
	private Object value;
	
	public Element(String key,Object value){
		this.key = key;
		this.value = value;
	}
	
	public Element(int key,Object value){
		this.key = String.valueOf(key);
		this.value = value;
	}
	
	public Object getValue(){
		return value;
	}
	
	public String getKey(){
		return key;
	}
}
