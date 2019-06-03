package com.tianee.oa.mobile.system.service;

import java.util.HashMap;
import java.util.Map;

import com.tianee.webframe.util.global.TeeSysProps;

public class TeeMobileConfigService {

	/**
	 * 获取系统配置
	 * @return
	 */
	public static Map getMobileConfig(){
		Map map = new HashMap();
		map.put("EMAIL", "25");
		map.put("NEWS", "27");
		map.put("NOTIFY", "29");
		map.put("CALENDAR", "36");
		map.put("DIARY", "33");
		map.put("WORK_FLOW", "12");
		map.put("PERSONAL_NETDISK", "23");
		map.put("PUBLIC_NETDISK", "96");
		map.put("REPORTSHOP", "233");
		map.put("DOCREC", "274");
		map.put("DOCVIEW", "275");
		return map;
	}
	
	/**
	 * 获取android当前版本
	 * @return
	 */
	public static String getAndroidCurrVersion(){
		return TeeSysProps.getString("ANDROID_CURR_VERSION");
	}
	
	/**
	 * 获取IOS当前版本
	 * @return
	 */
	public static String getIosCurrVersion(){
		return TeeSysProps.getString("IOS_CURR_VERSION");
	}
}
