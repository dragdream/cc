package com.tianee.webframe.util.str.expReplace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeeRegexpAnalyser {
	private String text;
	
	public TeeRegexpAnalyser(){
		this("");
	}
	
	public TeeRegexpAnalyser(String text){
		this.text = text;
	}
	
	public String replace(String patterns[],TeeExpFetcher fetcher){
		if(patterns==null || fetcher==null || text==null){
			return this.text;
		}
		
		StringBuffer sb = new StringBuffer();
		int i=0;
		for(String pattern:patterns){
			sb.append(pattern);
			if(i!=patterns.length-1){
				sb.append("|");
			}
			i++;
		}
		
		Pattern pattern = Pattern.compile(sb.toString(),Pattern.CASE_INSENSITIVE);
		Matcher m = pattern.matcher(text);
		
		sb.delete(0, sb.length());
		
		int index = 0;
		while(m.find()){
			String group = m.group();
			int start = m.start();
			int end = m.end();
			
			sb.append(text.substring(index, start));
			index = start;
			index +=group.length();
			group = fetcher.parse(group);
			sb.append(group);
		}
		if(index==0){
			sb.append(text);
			return sb.toString();
		}
//		System.out.println(index);
		if(index!=text.length()){
			sb.append(text.substring(index, text.length()));
		}
		return sb.toString();
	}
	
	public String getText(){
		return text;
	}
	
	public void setText(String text){
		this.text = text;
	}
}
