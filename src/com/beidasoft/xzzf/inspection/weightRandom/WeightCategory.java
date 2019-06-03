package com.beidasoft.xzzf.inspection.weightRandom;

public class WeightCategory {
	//需要设置权重的对象
	private Object category;
	//对应权重
	private Integer weight;
	
	public WeightCategory() {
		super();
	}
	public WeightCategory(Object category, Integer weight) {
		super();
		this.category = category;
		this.weight = weight;
	}
	
	public Object getCategory() {
		return category;
	}
	public void setCategory(Object category) {
		this.category = category;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
}