package com.tianee.oa.core.phoneSms.sender;

public interface SmsSender {
	public String send(String fromUser,String phone,String content);
}
