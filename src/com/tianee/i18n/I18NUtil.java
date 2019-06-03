package com.tianee.i18n;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 国际化工具
 * @author kakalion
 *
 */
public class I18NUtil {
	private static Map<String,Properties> props = new HashMap();
	
	
	public static String get(String key,HttpSession session){
		String lang = TeeStringUtil.getString(session.getAttribute("LANG"),"cn");
		Properties language = props.get(lang);
		String value = language.getProperty(key);
		if(value==null){
			value = "NoLang";
		}
		try {
			return new String(value.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "NoLang";
	}
	
	public static String get(String key){
		
		return "NoLang";
	}
	
	public static void init(){
		String langs[] = {"cn"};
		for(String lang:langs){
			try{
				Properties properties = new Properties();
				properties.load(I18NUtil.class.getResourceAsStream(lang+".properties"));
				props.put(lang, properties);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
