package com.beidasoft.xzfy.base.exception;

public class ValidateException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//返回码
	private String resultCode;
	
	//返回描述
	private String resultMsg;
	
	public ValidateException(String resultCode,String resultMsg){
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
	
}
