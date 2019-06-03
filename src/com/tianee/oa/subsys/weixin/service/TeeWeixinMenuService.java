package com.tianee.oa.subsys.weixin.service;

import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.tianee.oa.subsys.weixin.ParamesAPI.AccessToken;
import com.tianee.oa.subsys.weixin.ParamesAPI.WeixinUtil;
import com.tianee.oa.subsys.weixin.menu.Button;
import com.tianee.oa.subsys.weixin.menu.Menu;
import com.tianee.oa.subsys.weixin.menu.ViewButton;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeWeixinMenuService {
	// 菜单创建（POST）
	public static String menu_create_url = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN&agentid=AGENTID";

	/**
	 * 创建菜单
	 * @param AGENTID
	 * @param menuJsonDate
	 * @return
	 */
	public String createMenu(String AGENTID , String menuJsonDate){
		// 先删除menu
		String url = "https://qyapi.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN&agentid=AGENTID".replace("ACCESS_TOKEN", AccessToken.getAccessTokenInstance().getAppToken().get(AGENTID)).replace("AGENTID", AGENTID);
		JSONObject obj = WeixinUtil.httpRequest(url, "GET", menuJsonDate);
		String errcode = "";
		
		// 拼装创建菜单的url
		url = menu_create_url.replace("ACCESS_TOKEN", AccessToken.getAccessTokenInstance().getAppToken().get(AGENTID)).replace("AGENTID", AGENTID);
		obj = WeixinUtil.httpRequest(url, "POST", menuJsonDate);
		errcode = "";
		if(obj != null){
			 errcode = TeeStringUtil.getString(obj.get("errcode"));
		}
		return errcode;
	}
	
	
	
	/**
	 * 初始化邮箱菜单
	 * @return
	 */
	public  String getCreateEmail(){
		Menu menu = new Menu();
		ViewButton receive = new ViewButton();
		receive.setName("收件箱");
		receive.setType("view");
		String url = getRedirectUri("email")[0];
		receive.setUrl(url);
		/*ViewButton sendEmail = new ViewButton();
		sendEmail.setName("写邮件");
		sendEmail.setType("view");
		sendEmail.setUrl(getRedirectUri("email_addOrUpdate")[0]);*/
		//menu.setButton(new Button[] { receive, sendEmail });
		menu.setButton(new Button[] { receive });
		JSONObject jsonb = JSONObject.fromObject(menu);
		String temp = TeeJsonUtil.toJson(jsonb);
		return temp;
	}
	
	/**
	 * 初始化公告菜单
	 * @return
	 */
	public  String getInitNotify(){
		Menu menu = new Menu();
		ViewButton obj = new ViewButton();
		obj.setName("公告通知");
		obj.setType("view");
		obj.setUrl(getRedirectUri("notify")[0]);
		menu.setButton(new Button[] { obj });
		JSONObject jsonb = JSONObject.fromObject(menu);
		String temp = TeeJsonUtil.toJson(jsonb);
		return temp;
	}
	
	/**
	 * 初始化新闻菜单
	 * @return
	 */
	public  String getInitNews(){
		Menu menu = new Menu();
		ViewButton obj = new ViewButton();
		obj.setName("新闻管理");
		obj.setType("view");
		obj.setUrl(getRedirectUri("news")[0]);
		menu.setButton(new Button[] { obj });
		JSONObject jsonb = JSONObject.fromObject(menu);
		String temp = TeeJsonUtil.toJson(jsonb);
		return temp;
	}
	
	/**
	 * 初始化工作流菜单
	 * @return
	 */
	public  String getInitWorkFlow(){
		Menu menu = new Menu();
		ViewButton obj = new ViewButton();
		obj.setName("我的工作");
		obj.setType("view");
		obj.setUrl(getRedirectUri("workflow")[0]);
		menu.setButton(new Button[] { obj });
		JSONObject jsonb = JSONObject.fromObject(menu);
		String temp = TeeJsonUtil.toJson(jsonb);
		return temp;
	}
	
	/**
	 * 初始化日程安排菜单
	 * @return
	 */
	public  String getInitCalendar(){
		Menu menu = new Menu();
		ViewButton obj = new ViewButton();
		obj.setName("日程安排");
		obj.setType("view");
		obj.setUrl(getRedirectUri("calendar")[0]);
		menu.setButton(new Button[] { obj });
		JSONObject jsonb = JSONObject.fromObject(menu);
		String temp = TeeJsonUtil.toJson(jsonb);
		return temp;
	}
	
	/**
	 * 初始化任务管理菜单
	 * @return
	 */
	public  String getInitTask(){
		Menu menu = new Menu();
		ViewButton obj = new ViewButton();
		obj.setName("我的任务");
		obj.setType("view");
		obj.setUrl(getRedirectUri("task")[0]);
		menu.setButton(new Button[] { obj });
		JSONObject jsonb = JSONObject.fromObject(menu);
		String temp = TeeJsonUtil.toJson(jsonb);
		return temp;
	}
	
	/**
	 * 初始化客户管理菜单
	 * @return
	 */
	public  String getInitCustomer(){
		Menu menu = new Menu();
		ViewButton obj = new ViewButton();
		obj.setName("客户管理");
		obj.setType("view");
		obj.setUrl(getRedirectUri("customer")[0]);
		menu.setButton(new Button[] { obj });
		JSONObject jsonb = JSONObject.fromObject(menu);
		String temp = TeeJsonUtil.toJson(jsonb);
		return temp;
	}
	
	/**
	 * 初始化客户管理菜单
	 * @return
	 */
	public  String getInitDiary(){
		Menu menu = new Menu();
		ViewButton obj = new ViewButton();
		obj.setName("日志管理");
		obj.setType("view");
		obj.setUrl(getRedirectUri("diary")[0]);
		menu.setButton(new Button[] { obj });
		JSONObject jsonb = JSONObject.fromObject(menu);
		String temp = TeeJsonUtil.toJson(jsonb);
		return temp;
	}
	
	
	/**
	 * 初始化计划管理菜单
	 * @return
	 */
	public  String getInitSchedule(){
		Menu menu = new Menu();
		ViewButton obj = new ViewButton();
		obj.setName("计划管理");
		obj.setType("view");
		obj.setUrl(getRedirectUri("schedule")[0]);
		menu.setButton(new Button[] { obj });
		JSONObject jsonb = JSONObject.fromObject(menu);
		String temp = TeeJsonUtil.toJson(jsonb);
		return temp;
	}
	

	/**
	 * 初始化讨论区菜单
	 * @return
	 */
	public  String getInitTopic(){
		Menu menu = new Menu();
		ViewButton obj = new ViewButton();
		obj.setName("讨论区");
		obj.setType("view");
		obj.setUrl(getRedirectUri("topic")[0]);
		menu.setButton(new Button[] { obj });
		JSONObject jsonb = JSONObject.fromObject(menu);
		String temp = TeeJsonUtil.toJson(jsonb);
		return temp;
	}
	
	/**
	 * 初始化公共网盘菜单
	 * @return
	 */
	public  String getInitPubdisk(){
		Menu menu = new Menu();
		ViewButton obj = new ViewButton();
		obj.setName("公共网盘");
		obj.setType("view");
		obj.setUrl(getRedirectUri("pubdisk")[0]);
		menu.setButton(new Button[] { obj });
		JSONObject jsonb = JSONObject.fromObject(menu);
		String temp = TeeJsonUtil.toJson(jsonb);
		return temp;
	}
	
	/**
	 * 初始化公共网盘菜单
	 * @return
	 */
	public  String getInitPersondisk(){
		Menu menu = new Menu();
		ViewButton obj = new ViewButton();
		obj.setName("个人网盘");
		obj.setType("view");
		obj.setUrl(getRedirectUri("persondisk")[0]);
		menu.setButton(new Button[] { obj });
		JSONObject jsonb = JSONObject.fromObject(menu);
		String temp = TeeJsonUtil.toJson(jsonb);
		return temp;
	}
	
	/**
	 * 获取去微信重定向信息
	 * @param appModel 应用模块，如email、notify、calendar
	 * @return
	 */
	public String[] getRedirectUri( String appModel){
		String[] url = new String[3];
		String redirect_uri = "";//与微信绑定跳转路径
		String response_url = "";//跳转真实页面路径
		String call_back_url = "";//回调校验路径
		if("workflow".equals(appModel)){
			redirect_uri= "/weixin/dosso.action?app=workflow";
			response_url = "/system/mobile/phone/workflow/index.jsp";
			call_back_url = "/weixin/callBackUrl.action?appName=workflow";
		}else if("address".equals(appModel)){
			redirect_uri = "/weixin/dosso.action?app=address";
			response_url  = "/system/mobile/phone/address/index.jsp";
			call_back_url = "/weixin/callBackUrl.action?appName=address";
		}else if("calendar".equals(appModel)){
			redirect_uri = "/weixin/dosso.action.action?app=calendar";
			response_url  = "/system/mobile/phone/calendar/index.jsp";
			call_back_url = "/weixin/callBackUrl.action?appName=calendar";
		}else if("customer".equals(appModel)){
			redirect_uri = "/weixin/dosso.action?app=customer";
			response_url  = "/system/mobile/phone/customer/index.jsp";
			call_back_url = "/weixin/callBackUrl.action?appName=customer";
		}else if("diary".equals(appModel)){
			redirect_uri = "/weixin/dosso.action?app=diary";
			response_url  = "/system/mobile/phone/diary/index.jsp";
			call_back_url = "/weixin/callBackUrl.action?appName=diary";
		}else if("email".startsWith(appModel)){
			//邮件箱、新建邮箱
			if(appModel.equals("email_addOrUpdate")){
				redirect_uri = "/weixin/dosso.action?app=email_addOrUpdate";
				response_url  = "/system/mobile/phone/email/addOrUpdate.jsp";
			}else{
				redirect_uri = "/weixin/dosso.action?app=email";
				response_url  = "/system/mobile/phone/email/index.jsp";
				call_back_url = "/weixin/callBackUrl.action?appName=email";
			}
		}else if("news".equals(appModel)){
			redirect_uri = "/weixin/dosso.action?app=news";
			response_url  = "/system/mobile/phone/news/index.jsp";
			call_back_url = "/weixin/callBackUrl.action?appName=news";
		}else if("notify".equals(appModel)){
			redirect_uri = "/weixin/dosso.action?app=notify";
			response_url  = "/system/mobile/phone/notify/index.jsp";
			call_back_url = "/weixin/callBackUrl.action?appName=notify";
		}else if("report".equals(appModel)){
			redirect_uri = "/weixin/dosso.action?app=report";
			response_url  = "/system/mobile/phone/report/index.jsp";
			call_back_url = "/weixin/callBackUrl.action?appName=report";
		}else if("schedule".equals(appModel)){
			redirect_uri = "/weixin/dosso.action?app=schedule";
			response_url  = "/system/mobile/phone/schedule/index.jsp";
			call_back_url = "/weixin/callBackUrl.action?appName=schedule";
		}else if("search".equals(appModel)){
			redirect_uri = "/weixin/dosso.action?app=search";
			response_url  = "/system/mobile/phone/search/index.jsp";
			call_back_url = "/weixin/callBackUrl.action?appName=search";
		}else if("task".equals(appModel)){
			redirect_uri = "/weixin/dosso.action?app=task";
			response_url  = "/system/mobile/phone/task/index.jsp";
			call_back_url = "/weixin/callBackUrl.action?appName=task";
		}else if("topic".equals(appModel)){
			redirect_uri = "/weixin/dosso.action?app=topic";
			response_url  = "/system/mobile/phone/topic/index.jsp";
			call_back_url = "/weixin/callBackUrl.action?appName=topic";
		}else if("report".equals(appModel)){
			redirect_uri = "/weixin/dosso.action?app=report";
			response_url  = "/system/mobile/phone/report/index.jsp";
			call_back_url = "/weixin/callBackUrl.action?appName=report";
		}else if("search".equals(appModel)){
			redirect_uri = "/weixin/dosso.action?app=search";
			response_url  = "/system/mobile/phone/search/index.jsp";
			call_back_url = "/weixin/callBackUrl.action?appName=search";
		}else if("pubdisk".equals(appModel)){
			redirect_uri = "/weixin/dosso.action?app=pubdisk";
			response_url  = "/system/mobile/phone/fileNetdisk/public/index.jsp";
			call_back_url = "/weixin/callBackUrl.action?appName=pubdisk";
		}else if("persondisk".equals(appModel)){
			redirect_uri = "/weixin/dosso.action?app=persondisk";
			response_url  = "/system/mobile/phone/fileNetdisk/person/index.jsp";
			call_back_url = "/weixin/callBackUrl.action?appName=persondisk";
		}
		
		redirect_uri = URLEncoder.encode(TeeSysProps.getString("WEIXIN_URL")+ redirect_uri);
		url[0] ="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+TeeSysProps.getString("WEIXIN_CORPID") + "&redirect_uri="+redirect_uri +"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
		url[1] = response_url;
		url[2] = call_back_url;
		return url;
	}
	public static void main(String[] args) {
	/*	TeeWeixinMenuService menu = new TeeWeixinMenuService();
		menu.getCreateEmail();*/
	}
	
}
