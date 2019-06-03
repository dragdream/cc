package com.beidasoft.gzpt.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class PrivIfTag extends SimpleTagSupport {

	private boolean test;

	public void setTest(boolean test) {
		this.test = test;
	}

	@Override
	public void doTag() throws JspException, IOException {
		if (test) {
			// 如果条件为真，则输出标签体的内容
			JspFragment jspBody = this.getJspBody();
			jspBody.invoke(null);
		}
	}

}
