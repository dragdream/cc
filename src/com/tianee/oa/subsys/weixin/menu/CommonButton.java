package com.tianee.oa.subsys.weixin.menu;


/**
 * 普通按钮（子按钮）
 * 
 * @author syl
 * @date 2015.9.19
 */
public class CommonButton extends Button {
	private String type;
	private String key;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}