package com.tianee.webframe.util.str.expReplace;

public class TeeHTMLOptionTag extends TeeHTMLTag{
	public TeeHTMLOptionTag(){
		tagHead="<";
		tagName="OPTION";
		tagTail=">";
		tagContent="";
		tagEnd="</OPTION>";
	}
	
	public void analyse(String pattern){
		int start = pattern.indexOf(tagTail);
		int end = pattern.indexOf(tagEnd.toLowerCase());
		if(start+1!=end){
			tagContent = pattern.substring(start+1,end);
		}
		super.analyse(pattern.substring(0,start+1));
	}

	@Override
	public String getRegExp() {
		// TODO Auto-generated method stub
		return "(<OPTION"+TeeRegexpConst.ATTRIBUTES+">"+TeeRegexpConst.ANY+"</OPTION>)";
	}
	
}
