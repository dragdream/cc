package com.tianee.oa.subsys.weixin.oauth2;

import org.apache.http.HttpRequest;

import net.sf.json.JSONObject;

import com.tianee.oa.subsys.weixin.ParamesAPI.HttpRequestUtil;
import com.tianee.oa.subsys.weixin.ParamesAPI.ParamesAPI;
import com.tianee.oa.subsys.weixin.ParamesAPI.WeixinUtil;

/**
 * OAuth2类
 * 
 * @author syl
 * @date 2015.9.20
 */
public class OAuth2Core {
	public final static String GET_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=CORPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=a123#wechat_redirect";

	/**
	 * 企业获取code地址处理
	 * 
	 * @param appid
	 *            企业的CorpID
	 * @param redirect_uri
	 *            授权后重定向的回调链接地址，请使用urlencode对链接进行处理
	 * @param response_type
	 *            返回类型，此时固定为：code
	 * @param scope
	 *            应用授权作用域，此时固定为：snsapi_base
	 * @param state
	 *            重定向后会带上state参数，企业可以填写a-zA-Z0-9的参数值
	 * @param #wechat_redirect 微信终端使用此参数判断是否需要带上身份信息 员工点击后，页面将跳转至 redirect_uri/?code=CODE&state=STATE，企业可根据code参数获得员工的userid
	 * */
	public static String getCode() {
		String get_code_url = "";
		get_code_url = GET_CODE.replace("CORPID", ParamesAPI.corpId).replace("REDIRECT_URI", WeixinUtil.URLEncoder(ParamesAPI.REDIRECT_URI));
		return get_code_url;
	}

	//public static String CODE_TO_USERINFO = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE&agentid=AGENTID";
	public final static String CODE_TO_USERINFO = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";

	/**
	 * 根据code获取成员信息
	 * 
	 * @param access_token
	 *            调用接口凭证
	 * @param code
	 *            通过员工授权获取到的code，每次员工授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
	 * @param agentid
	 *            跳转链接时所在的企业应用ID 管理员须拥有agent的使用权限；agentid必须和跳转链接时所在的企业应用ID相同
	 * */
	public static String getUserID(String access_token, String code, String agentid) {
		String UserId = "";
		String CODE_TO_USERINFO_NEW = CODE_TO_USERINFO.replace("ACCESS_TOKEN", access_token).replace("CODE", code).replace("AGENTID", agentid);
		JSONObject jsonobject = WeixinUtil.httpRequest(CODE_TO_USERINFO_NEW, "GET", null);
		System.out.println(jsonobject+"-----------------------------第一次,code:"+ code);
		if (null != jsonobject) {
			try{
				UserId = jsonobject.getString("UserId");
			}catch(Exception e){
				int errorrcode = jsonobject.getInt("errcode");
				String errmsg = jsonobject.getString("errmsg");
				System.out.println("错误码：" + errorrcode + "————" + "错误信息：" + errmsg);
			}
		} else {
			System.out.println("获取授权失败了");
		}
		return UserId;
	}

}
