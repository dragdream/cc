package com.tianee.webframe.util.str.expReplace;

public class TeeHTMLInputTag extends TeeHTMLTag{
	
	public TeeHTMLInputTag(){
		tagHead="<";
		tagName="INPUT";
		tagTail="";
		tagContent="";
		tagEnd="/>";
	}
	
	@Override
	public String getRegExp() {
		// TODO Auto-generated method stub
		return "(<INPUT"+TeeRegexpConst.ATTRIBUTES+"/\\s*>)";
	}

}
