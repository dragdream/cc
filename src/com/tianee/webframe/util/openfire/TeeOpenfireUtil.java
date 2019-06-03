package com.tianee.webframe.util.openfire;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.servlet.HttpClientUtil;


/**
 * Openfire工具类
 * @author LiTeng
 *
 */
public class TeeOpenfireUtil {
	
	/**
	 * 创建用户
	 * @param userId
	 * @param userName
	 * @param pwd
	 */
	public static boolean createUser(String userId,String userName){
		String ip = TeeSysProps.getString("OF_IP");
		String port = TeeSysProps.getString("OF_PORT");
		String key = TeeSysProps.getString("OF_KEY");
		
		String url = "";
		try {
			url = "http://"+ip+":"+port+"/plugins/userService/userservice?type=add&secret="+key+"&username="+URLEncoder.encode(userId, "UTF-8")+"&password=trsadmin&name="+URLEncoder.encode(userName, "UTF-8");
			String c = HttpClientUtil.requestGet(url);
			if(c==null){
				return false;
			}
			//如果用户已存在，则更新用户
			if(c.contains("UserAlreadyExistsException")){
				url = "http://"+ip+":"+port+"/plugins/userService/userservice?type=update&secret="+key+"&username="+URLEncoder.encode(userId, "UTF-8")+"&password=trsadmin&name="+URLEncoder.encode(userName, "UTF-8");
				c = HttpClientUtil.requestGet(url);
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 * 删除用户
	 * @param userId
	 * @param userName
	 * @param pwd
	 */
	public static void delUser(String userId){
		if("admin".equals(userId)){
			return;
		}
		String ip = TeeSysProps.getString("OF_IP");
		String port = TeeSysProps.getString("OF_PORT");
		String key = TeeSysProps.getString("OF_KEY");
		
		String url = "";
		try {
			url = "http://"+ip+":"+port+"/plugins/userService/userservice?type=delete&secret="+key+"&username="+URLEncoder.encode(userId, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpClientUtil.requestGet(url);
	}
	
	/**
	 * 向服务器发送消息
	 * @param userId
	 * @param userName
	 * @param pwd
	 */
	public static void sendMessage(String content,String to,String apnsTitle,String apnsContent){
		String ip = TeeSysProps.getString("OF_IP");
		String port = TeeSysProps.getString("OF_PORT");
		String key = TeeSysProps.getString("OF_KEY");
		
		String url = "http://"+ip+":"+port+"/plugins/zatp?cmd=sendmsg";
		Map params = new HashMap();
		params.put("content", content);
		params.put("to", to);
		params.put("apnsTitle", apnsTitle);
		params.put("apnsContent", apnsContent);
		HttpClientUtil.requestPost(params,url);
	}
	
}
