package com.beidasoft.xzzf.task.caseInterface.httpmodel;

public class CaseJson {

	private boolean rtState = false;//成功标记
	private String rtMsg = "";// 信息提示
	private Object rtData = null;// 返回
	public boolean isRtState() {
		return rtState;
	}
	public void setRtState(boolean rtState) {
		this.rtState = rtState;
	}
	public String getRtMsg() {
		return rtMsg;
	}
	public void setRtMsg(String rtMsg) {
		this.rtMsg = rtMsg;
	}
	public Object getRtData() {
		return rtData;
	}
	public void setRtData(Object rtData) {
		this.rtData = rtData;
	}
	
	
}
