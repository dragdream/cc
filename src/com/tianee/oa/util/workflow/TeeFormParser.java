package com.tianee.oa.util.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.util.workflow.ctrl.TeeCtrl;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUnicodeUtil;
import com.tianee.webframe.util.str.expReplace.TeeExpFetcher;
import com.tianee.webframe.util.str.expReplace.TeeHTMLImgTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLInputTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLSelectTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLTextareaTag;
import com.tianee.webframe.util.str.expReplace.TeeRegexpAnalyser;

public class TeeFormParser {
	
	/**
	 * 从html表单中分析出List<TeeFormItem>
	 * @param html
	 * @return
	 */
	public List<TeeFormItem> getFormItemsFromHtml(String html){
		final List<TeeFormItem> formItems = new ArrayList<TeeFormItem>();
		TeeRegexpAnalyser ra = new TeeRegexpAnalyser();
		ra.setText(html);
		String text = ra.replace(new String[]{new TeeHTMLImgTag().getRegExp(),
				new TeeHTMLInputTag().getRegExp(),new TeeHTMLSelectTag().getRegExp()
				,new TeeHTMLTextareaTag().getRegExp()}, new TeeExpFetcher(){

			@Override
			public String parse(String pattern) {
				TeeHTMLTag tag = TeeHTMLTag.parse2TagEntity(new TeeHTMLTag[]{new TeeHTMLImgTag()
				,new TeeHTMLInputTag(),new TeeHTMLSelectTag(),new TeeHTMLTextareaTag()}, pattern);
				
				//分析标签内属性
				tag.analyse(pattern);
				//获取属性集合
				Map<String,String> attributes = tag.getAttributes();
				
				//实例化声明一个formItem
				TeeFormItem formItem = new TeeFormItem();
				//将属性注入到formItem中
				formItem.setContent(pattern);
				formItem.setTag(tag.getTagName());
				formItem.setTitle(attributes.get("title"));
				formItem.setModel(attributes.get("model"));
				formItem.setXtype(attributes.get("xtype"));
				if("xtextarea".equals(formItem.getXtype())){
					formItem.setDefaultValue(TeeUnicodeUtil.unicode2String(attributes.get("defaultvalue")));
				}else{
					formItem.setDefaultValue(attributes.get("defaultvalue"));
				}
				
				
				String xType = attributes.get("xtype");
				TeeCtrl ctrl = TeeCtrl.getInstanceOf(xType);
				
				
				if(ctrl==null){
					return pattern;
				}
				//获取控件列类型
				ctrl.getCtrlColumnType(formItem);
				
				
				String id = TeeStringUtil.getString(attributes.get("id"));
				String name = TeeStringUtil.getString(attributes.get("name"));
				
				if("".equals(id) || "".equals(name) || !name.equals(id)){
					return pattern;
				}
				formItem.setItemId(Integer.parseInt(id.split("_")[1]));
				formItem.setName(name);
				
				formItems.add(formItem);
				
				return pattern;
			}
			
		});
		return formItems;
	}
	
	/**
	 * 从html中分析出快速模板，一般用于较快速度替换对应的控件值
	 * @param html
	 * @return
	 */
	public String getShortModelFromHtml(String html){
		TeeRegexpAnalyser ra = new TeeRegexpAnalyser();
		ra.setText(html);
		String text = ra.replace(new String[]{new TeeHTMLImgTag().getRegExp(),
				new TeeHTMLInputTag().getRegExp(),new TeeHTMLSelectTag().getRegExp()
				,new TeeHTMLTextareaTag().getRegExp()}, new TeeExpFetcher(){

			@Override
			public String parse(String pattern) {
				TeeHTMLTag tag = TeeHTMLTag.parse2TagEntity(new TeeHTMLTag[]{new TeeHTMLImgTag()
				,new TeeHTMLInputTag(),new TeeHTMLSelectTag(),new TeeHTMLTextareaTag()}, pattern);
				//分析标签内属性
				tag.analyse(pattern);
				//获取属性集合
				Map<String,String> attributes = tag.getAttributes();
				
				String id = TeeStringUtil.getString(attributes.get("id"));
				String name = TeeStringUtil.getString(attributes.get("name"));
				
				if("".equals(id) || "".equals(name) || !name.equals(id)){
					return pattern;
				}
				
				
				return "{"+name+"}";//返回空字符串即可
			}
			
		});
		return text;
	}
}
