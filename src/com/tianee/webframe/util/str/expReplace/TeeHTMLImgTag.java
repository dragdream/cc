package com.tianee.webframe.util.str.expReplace;

public class TeeHTMLImgTag extends TeeHTMLTag{
	
	public TeeHTMLImgTag(){
		tagHead="<";
		tagName="IMG";
		tagTail="";
		tagContent="";
		tagEnd="/>";
	}
	
	@Override
	public void analyse(String pattern) {
		// TODO Auto-generated method stub
		super.analyse(pattern);
	}

	@Override
	public String getRegExp() {
		// TODO Auto-generated method stub
		return "(<IMG"+TeeRegexpConst.ATTRIBUTES+"/\\s*>)";
	}
	
}
