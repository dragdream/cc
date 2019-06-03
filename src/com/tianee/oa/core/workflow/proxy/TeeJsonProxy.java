package com.tianee.oa.core.workflow.proxy;

public class TeeJsonProxy {
	private boolean rtState;
	private String rtMsg;
	private String rtData;
	
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
	public String getRtData() {
		return rtData;
	}
	public void setRtData(String rtData) {
		this.rtData = rtData;
	}
	
}
