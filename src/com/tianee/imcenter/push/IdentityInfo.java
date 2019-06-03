package com.tianee.imcenter.push;

import java.io.Serializable;
import java.net.InetAddress;

public class IdentityInfo implements Serializable{
	public String userId;
	public InetAddress address;
	public int port;
	public int terminal;//设备终端类型   1：android  2：iOS
	public String deviceId;//设备ID
	public long invalidTime;//失效时间
}