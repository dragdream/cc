package com.beidasoft.xzfy.base.model.response;

public class Response {

	//返回码
	private String resultCode;
	
	//返回描述
	private String resultMsg;

	public Response(String resultCode,String resultMsg){
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
