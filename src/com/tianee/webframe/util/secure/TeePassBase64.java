package com.tianee.webframe.util.secure;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public class TeePassBase64 {
	
	 final static  String encode = "UTF-8";
	/**
	 * 明文字符串base64 加密
	 * 作者：syl 
	 * 
	 * plainText : 明文
	 * @throws UnsupportedEncodingException 
	 */
	public static String encodeStr(String plainText) {
		byte[] b = null;
		try {
			b = plainText.getBytes(encode);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Base64 base64 = new Base64();
		b = base64.encode(b);
		String s = new String(b);
		return s;
	}

	/**
	 * 
	 * 
	 * 修改日期 作者：syl
	 * encodeStr:base64加密字符串
	 *  TODO  return ： 明文
	 */
	public static String decodeStr(String encodeStr) {
		byte[] b = null;
		try {
			b = encodeStr.getBytes(encode);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Base64 base64 = new Base64();
		b = base64.decode(b);
		String s = new String(b);
		return s;
	}

	public static void main(String[] args) {
		String name = "111";
		
		String ss = encodeStr(name);
		System.out.println(ss);
	}
}
