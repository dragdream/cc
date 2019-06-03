package com.tianee.webframe.util.str.expReplace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeeHTMLTextareaTag extends TeeHTMLTag{
	
	public TeeHTMLTextareaTag(){
		tagHead="<";
		tagName="TEXTAREA";
		tagTail=">";
		tagContent="";
		tagEnd="</TEXTAREA>";
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
		return "(<TEXTAREA"+TeeRegexpConst.ATTRIBUTES+">"+TeeRegexpConst.ANY+"</TEXTAREA>)";
	}
	
	public void setContent(String data){
		tagContent = data;
	}

}
