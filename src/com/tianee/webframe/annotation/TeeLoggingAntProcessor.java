package com.tianee.webframe.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.multiaction.InternalPathMethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.MethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import com.tianee.webframe.util.str.TeeELExpressionParser;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.expReplace.TeeExpFetcher;
import com.tianee.webframe.util.str.expReplace.TeeRegexpAnalyser;
import com.tianee.webframe.util.thread.TeeRequestInfo;

public class TeeLoggingAntProcessor{
	
	/**
	 * 渲染日志模板
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 */
	public String renderTemplate(final TeeRequestInfo requestInfo,Method method,final Object arguments[],final Object returned) {
		String rendered = null;
		TeeLoggingAnt annotation = method.getAnnotation(TeeLoggingAnt.class);
		if(annotation!=null){
			String template = annotation.template();
			TeeRegexpAnalyser analyser = new TeeRegexpAnalyser();
			analyser.setText(template);
			rendered = analyser.replace(new String[]{"\\{[a-zA-Z0-9\\._#$]+\\}"}, new TeeExpFetcher(){
				
				@Override
				public String parse(String pattern) {
					// TODO Auto-generated method stub
					return renderByVariables(pattern,requestInfo,arguments,returned);
				}
				
			});
		}
		
		return rendered;
	}
	
	/**
	 * 根据变量渲染
	 * @param pattern
	 * @param request
	 * @param response
	 * @return
	 */
	public static String renderByVariables(String pattern,TeeRequestInfo requestInfo,Object arguments[],Object returned){
		String patternPlain = pattern.substring(1,pattern.length()-1);
		String renderedField = null;
		TeeELExpressionParser el = new TeeELExpressionParser();
		
		Object contextObject = requestInfo;
		
		String sp[] = patternPlain.split("\\.");
		
		if(sp[0].startsWith("$")){//$1为第一个参数，$2为第二个，以此类推
			patternPlain = "";
			int index = Integer.parseInt(sp[0].substring(1,sp[0].length()));
			contextObject = arguments[index-1];
		}else if(sp[0].startsWith("#")){//#为返回值
			patternPlain = "";
			contextObject = returned;
		}
		
		if("".equals(patternPlain)){
			for(int i=1;i<sp.length;i++){
				patternPlain+=sp[i];
				if(i!=sp.length-1){
					patternPlain+=".";
				}
			}
		}
		
		renderedField = TeeStringUtil.getString(el.evaluate(contextObject, patternPlain));
		return renderedField;
	}
	
}
