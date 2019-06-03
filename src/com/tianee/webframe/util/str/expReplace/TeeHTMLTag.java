package com.tianee.webframe.util.str.expReplace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class TeeHTMLTag {
	protected String tagHead="";
	protected String tagName="";
	protected String tagTail="";
	protected String tagContent="";
	protected String tagEnd="";
	
	protected Map<String,String> attributes = new HashMap<String,String>();
	
	/**
	 * 获取dom正则表达式
	 * @return
	 */
	public abstract String getRegExp();
	
	public static TeeHTMLTag parse2TagEntity(TeeHTMLTag HTMLTags[],String pattern){
		for(TeeHTMLTag tag:HTMLTags){
			if(pattern.toUpperCase().startsWith(tag.tagHead+tag.tagName)){
				try {
					return tag.getClass().newInstance();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public void analyse(String pattern){
		Pattern p = Pattern.compile(TeeRegexpConst.HTML_ATTRIBUTE,Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(pattern);
		while(m.find()){
			String s = m.group();
			String kv[] = getKeyValue(s);
			attributes.put(kv[0].toLowerCase(), kv[1]);
		}
	}
	
	protected String[] getKeyValue(String str){
		str = str.replace("\"", "").trim();
		int index = str.indexOf("=");
		
		if(index!=-1){
			String left = str.substring(0, index);
			String right = str.substring(index+1, str.length());
			return new String[]{left,right};
		}else{
			return new String[]{null,null};
		}
		
//		String sp[] = str.replace("\"", "").trim().split("=",1);
//		System.out.println(sp.length);
//		if(sp.length==0){
//			return new String[]{null,null};
//		}else if(sp.length==1){
//			return new String[]{sp[0],null};
//		}else{
//			return sp;
//		}
	}
	
	public String toString(){
		StringBuffer s = new StringBuffer(tagHead+tagName+" ");
		Iterator it = attributes.entrySet().iterator();
		while(it.hasNext()){
			Entry<String,String> en = (Entry<String, String>) it.next();
			String val = en.getValue();
			if(val!=null){
				s.append(en.getKey()+"="+"\""+val+"\" ");
			}
		}
		s.append(tagTail+tagContent+tagEnd);
		return s.toString();
	}
	
	public Map<String,String> getAttributes(){
		return attributes;
	}
	
	public String getTagName(){
		return tagName;
	}
	
	public String getTagHead(){
		return tagHead;
	}
	
	public String getTagTail(){
		return tagTail;
	}
	
	public String getTagContent(){
		return tagContent;
	}
	
	public String getTagEnd(){
		return tagEnd;
	}
}
