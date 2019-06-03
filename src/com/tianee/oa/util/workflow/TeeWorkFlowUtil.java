package com.tianee.oa.util.workflow;

import java.text.DecimalFormat;

public class TeeWorkFlowUtil {
	/**
	 * 数字转换为指定格式
	 * @param number
	 * @param format
	 * @return
	 */
	public static String number2Format(Double number,String format){
		DecimalFormat df = new DecimalFormat(format);
		return df.format(number);
	}
	
	/**
	 * 数字转换为指定格式（带四舍五入和结果加1）
	 * @param number
	 * @param format
	 * @param type
	 * @return
	 */
	public static String number2Format(Double number,String format,int type){
		DecimalFormat df = new DecimalFormat(format);
		return df.format(number);
	}
	
	/**
	 * 人民币转换
	 * @param value
	 * @return
	 */
	public static String toChineseCurrency(Number o) {  
        String s = new DecimalFormat("#.00").format(o);  
        s = s.replaceAll("\\.", "");  
        char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' };  
        String unit = "仟佰拾兆仟佰拾亿仟佰拾万仟佰拾元角分";  
        int l = unit.length();  
        StringBuffer sb = new StringBuffer(unit);  
        for (int i = s.length() - 1; i >= 0; i--)  
            sb = sb.insert(l - s.length() + i, digit[(s.charAt(i) - 0x30)]);  
        s = sb.substring(l - s.length(), l + s.length());  
        s = s.replaceAll("零[拾佰仟]", "零").replaceAll("零{2,}", "零").replaceAll("零([兆万元])", "$1").replaceAll("零[角分]", "");  
        if (s.endsWith("角"))  
            s += "零分";  
        if (!s.contains("角") && !s.contains("分") && s.contains("元"))  
            s += "整";  
        if (s.contains("分") && !s.contains("整") && !s.contains("角"))  
            s = s.replace("元", "元零");  
        return s;  
    }  
	
	public static void main(String[] args) {
		
	}

}
