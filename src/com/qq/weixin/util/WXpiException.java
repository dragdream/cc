package com.qq.weixin.util;

public class WXpiException extends Exception {

	public WXpiException(int errCode, String errMsg) {
		super("error code: " + errCode + ", error message: " + errMsg);
	}
}
