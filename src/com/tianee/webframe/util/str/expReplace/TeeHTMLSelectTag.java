package com.tianee.webframe.util.str.expReplace;

public class TeeHTMLSelectTag extends TeeHTMLTag{
	
	public TeeHTMLSelectTag(){
		tagHead="<";
		tagName="SELECT";
		tagTail=">";
		tagContent="";
		tagEnd="</SELECT>";
	}
	
	public void analyse(String pattern){
		int start = pattern.indexOf(tagTail);
		int end = pattern.indexOf(tagEnd.toLowerCase());
		if(start+1!=end){
			tagContent = pattern.substring(start+1,end);
		}
		super.analyse(pattern.substring(0,start+1));
	}
	
	/**
	 * 设置默认值
	 * @param value
	 */
	public void setDefaultValue(final String value){
		TeeRegexpAnalyser analyser = new TeeRegexpAnalyser();
		String content = tagContent;
		analyser.setText(content);
		content = analyser.replace(new String[]{new TeeHTMLOptionTag().getRegExp()}, new TeeExpFetcher(){
			@Override
			public String parse(String pattern) {
				// TODO Auto-generated method stub
				TeeHTMLOptionTag tag = new TeeHTMLOptionTag();
				tag.analyse(pattern);
				if(!"".equals(value) && value!=null){
					if(value.equals(tag.getAttributes().get("value"))){
						tag.getAttributes().put("selected","selected");
					}else{
						tag.getAttributes().remove("selected");
					}
				}
				return tag.toString();
			}
		});
		tagContent = content;
	}
	
	@Override
	public String getRegExp() {
		// TODO Auto-generated method stub
		return "(<SELECT"+TeeRegexpConst.ATTRIBUTES+">"+TeeRegexpConst.ANY+"</SELECT>)";
	}
	
}
