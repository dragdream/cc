package com.tianee.webframe.httpmodel;
/**
 * Teejson 这个 主要是根据ajax 的习惯 来设计的
 *  基础类json 这个主要是根据T9的习惯 给大家设计的
 * @author 赵鹏
 *
 */
public class TeeJson {

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
