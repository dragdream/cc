package com.tianee.oa.subsys.weixin.menu;


/**
 * 复杂按钮（父按钮）
 * 
 * @author syl
 * @date 2015.9.19
 */
public class ComplexButton extends Button {
	private Button[] sub_button;

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
}
