package com.tianee.webframe.util.date;

import java.beans.PropertyEditorSupport;

public class CustomIntEditor  extends PropertyEditorSupport{


	/*
	 * 
	 * 空的构造方法
	 */
	public CustomIntEditor() {
	}



	@Override
	public void setAsText(String text) {
		if ( text != null && !text.equals("null") && !text.equals("")) {// 解决前台传过来null的BUG
			setValue(Integer.parseInt(text));
		}else{
			 setValue(0);
		}
	}

	/**
	 * 先执行get 在执行 set
	 * 
	 */
	@Override
	public String getAsText() {
		String  value =  (String)getValue();
		if ( value == null  || value.equals("null")   || value.equals("")) {// 解决前台传过来null的BUG
			return "0";
		}
		return value;
	}

}
