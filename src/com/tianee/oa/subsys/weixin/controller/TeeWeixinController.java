package com.tianee.oa.subsys.weixin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.department.Department;
import com.alibaba.dingtalk.openapi.demo.department.DepartmentHelper;
import com.qq.weixin.util.WXpiException;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeModuleConst;
import com.tianee.oa.quartzjob.TeeAccessTokenTimer;
import com.tianee.oa.subsys.weixin.WeiXinConst;
import com.tianee.oa.subsys.weixin.ParamesAPI.AccessToken;
import com.tianee.oa.subsys.weixin.ParamesAPI.ParamesAPI;
import com.tianee.oa.subsys.weixin.ParamesAPI.WeixinUtil;
import com.tianee.oa.subsys.weixin.encryption.WXBizMsgCrypt;
import com.tianee.oa.subsys.weixin.menu.Button;
import com.tianee.oa.subsys.weixin.menu.Menu;
import com.tianee.oa.subsys.weixin.menu.ViewButton;
import com.tianee.oa.subsys.weixin.model.TeeWeixinDeptModel;
import com.tianee.oa.subsys.weixin.oauth2.OAuth2Core;
import com.tianee.oa.subsys.weixin.service.TeeWeixinMenuService;
import com.tianee.oa.subsys.weixin.service.TeeWeixinPersonService;
import com.tianee.oa.subsys.weixin.service.TeeWeixinService;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUrlToFile;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 微信Controller
 * @author syl
 *
 */
@Controller
@RequestMapping("/weixin")
public class TeeWeixinController {
	/*static{
		WeixinUtil.getAccessToken(ParamesAPI.corpId, ParamesAPI.secret);
		}*/
	@Autowired
	TeeWeixinService weixinService;
	
	@Autowired
	TeeWeixinPersonService weixinPersonService;
	
	@Autowired
	TeeWeixinMenuService menuService;
	
	@Autowired
	TeeBaseUpload baseUpload;
	
	
	AccessToken accessTokenObj;
	private static String url = "http://teneeoa.com";
	
	/**
	 * 测试连接微信
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/testConnectionWeiXin")
	@ResponseBody
	public TeeJson testConnectionWeiXin(HttpServletRequest request,HttpServletResponse response) throws Exception{
		TeeJson json = new TeeJson();
		String corpID = TeeStringUtil.getString(request.getParameter("WEIXIN_CORPID"));
		String secret = TeeStringUtil.getString(request.getParameter("WEIXIN_SECRET"));
		boolean  useConnection  = WeixinUtil.testConnection(corpID, secret);
		json.setRtState(useConnection);
		if(useConnection != true){
			json.setRtMsg("连接失败，检查是否有效的CORPID和SECRET!");
		}else{
			String WEIXIN_CORPID = TeeSysProps.getString("WEIXIN_CORPID");
			String WEIXIN_SECRET = TeeSysProps.getString("WEIXIN_SECRET");
			String WEIXIN_URL = TeeSysProps.getString("WEIXIN_URL");
			if(!"".equals(WEIXIN_CORPID) && !"".equals(WEIXIN_SECRET) && !"".equals(WEIXIN_URL)){
				//获取微信accessToken，并存入参数中
				AccessToken accessToken = WeixinUtil.getAccessToken(WEIXIN_CORPID, WEIXIN_SECRET);
				if(accessToken != null){
					TeeSysProps.getProps().setProperty("WEIXIN_ACCESS_TOKEN",accessToken.getToken());
				}
				//获取JSTicket
				String jsTicket = WeixinUtil.getJsTicket(accessToken.getToken());
				if(jsTicket!=null){
					TeeSysProps.getProps().setProperty("WEIXIN_JSAPI_TICKET",jsTicket);
				}
			}
		}
		return json;
	}
	
	/**
	 * 获取基本参数
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getBasicParam")
	@ResponseBody
	public TeeJson getBasicParam(HttpServletRequest request,HttpServletResponse response) throws Exception{
		TeeJson json = new TeeJson();
		json.setRtData(weixinService.getBasicParam());
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 保存基本信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveBasicParam")
	@ResponseBody
	public TeeJson saveBasicParam(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String wechat = request.getParameter("wechat");
		weixinService.saveBasicParam(TeeServletUtility.getParamMap(request));
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 微信同步人员
	 * @param request
	 * @return
	 */
	@RequestMapping("/syncPerson")
	public void syncPerson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		TeeJson json = new TeeJson();
		String userIds = TeeStringUtil.getString(request.getParameter("ids"));
		String deptIds = TeeStringUtil.getString(request.getParameter("deptIds"));
		String oper = TeeStringUtil.getString(request.getParameter("oper"));
		weixinPersonService.syncPerson(userIds,deptIds, oper,response);
	}
	
	/**
	 * 获取微信模块应用设置
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAppParam")
	@ResponseBody
	public TeeJson getAppBasicParam(HttpServletRequest request) throws WXpiException{
		TeeJson json = new TeeJson();
		String modelType = TeeStringUtil.getString(request.getParameter("modelType"));//模块类型
		String WEIXIN_APPID = "";
		String WEIXIN_TOKEN = "";
		String WEIXIN_ENCODING_AESKEY = "";

		String WEIXIN_APPID_NAME = "";
		String WEIXIN_TOKEN_NAME = "";
		String WEIXIN_ENCODING_AESKEY_NAME = "";
		Map map = new HashMap();
		WEIXIN_APPID = TeeModuleConst.MODULE_SORT_WX_APP_ID.get(modelType);
		String WEIXIN_APPSECRET = TeeModuleConst.MODULE_SORT_WX_SECRET.get(modelType);
//		WEIXIN_TOKEN = TeeSysProps.getString(temp[1]);
//		WEIXIN_ENCODING_AESKEY = TeeSysProps.getString(temp[2]);
		map.put("WEIXIN_APPID", WEIXIN_APPID);
		map.put("WEIXIN_TOKEN", WEIXIN_TOKEN);
		map.put("WEIXIN_ENCODING_AESKEY", WEIXIN_ENCODING_AESKEY);
		String[] urlTemp = menuService.getRedirectUri(modelType);
		map.put("WEIXIN_APP_URL", TeeSysProps.getString("WEIXIN_URL") + "/weixin/callBackUrl.action?moduleId="+modelType);
		map.put("WEIXIN_APPSECRET", WEIXIN_APPSECRET);
		
		
		String agentId = TeeModuleConst.MODULE_SORT_WX_APP_ID.get(modelType);
		String appUrl = TeeStringUtil.getString(TeeModuleConst.MODULE_SORT_URL.get(modelType));
		BASE64Encoder base64Encoder = new BASE64Encoder();
		appUrl = base64Encoder.encode(appUrl.getBytes());
		
		if(!TeeUtility.isNullorEmpty(WEIXIN_APPID)){
			map.put("WEIXIN_MENU_URL", "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+TeeSysProps.getString("WEIXIN_CORPID") + "&redirect_uri="+URLEncoder.encode(TeeSysProps.getString("WEIXIN_URL")+ "/weixin/dosso.action?url="+appUrl+"&agent_id="+agentId) +"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
		}
		
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	/*
	 * 保存微信模块应用设置
	 * @param request
	 * @return
	 * @throws WXpiException
	 */
	@RequestMapping("/saveAppParam")
	@ResponseBody
	public TeeJson saveAppParam(HttpServletRequest request) throws WXpiException{
		TeeJson json = new TeeJson();
		weixinService.saveAppParam(TeeServletUtility.getParamMap(request));
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 初始化APP菜单
	 * @param request
	 * @return
	 * @throws WXpiException
	 */
	@RequestMapping("/initAppMenu")
	@ResponseBody
	public TeeJson initAppMenu(HttpServletRequest request) throws WXpiException{
		TeeJson json = new TeeJson();
		String modelType = TeeStringUtil.getString(request.getParameter("modelType"));//模块类型
		String WEIXIN_APPID = "";//应用ID
		String WEIXIN_TOKEN = "";//token
		String WEIXIN_ENCODING_AESKEY = "";//秘钥key
//		Map map = new HashMap();
		String data = "";
		String WEIXIN_APPID_NAME = "";
		String WEIXIN_TOKEN_NAME = "";
		String WEIXIN_ENCODING_AESKEY_NAME = "";
		//String[] temp = weixinService.getWeixinAppInfo(modelType, WEIXIN_APPID_NAME, WEIXIN_TOKEN_NAME, WEIXIN_ENCODING_AESKEY_NAME);
//		if(modelType.equals("email")){
//			data = menuService.getCreateEmail();//获取邮箱
//		}else if(modelType.equals("notify")){
//			data = menuService.getInitNotify();
//		}else if(modelType.equals("news")){
//			data = menuService.getInitNews();
//		}else if(modelType.equals("workflow")){
//			data = menuService.getInitWorkFlow();
//		}else if(modelType.equals("calendar")){
//			data = menuService.getInitCalendar();
//		}else if(modelType.equals("diary")){
//			data = menuService.getInitDiary();
//		}else if(modelType.equals("schedule")){
//			data = menuService.getInitSchedule();
//		}else if(modelType.equals("task")){
//			data = menuService.getInitTask();
//		}else if(modelType.equals("customer")){
//			data = menuService.getInitCustomer();
//		}else if(modelType.equals("topic")){
//			data = menuService.getInitTopic();
//		}else if(modelType.equals("report")){
////			data = menuService.getInitTopic();
//		}else if(modelType.equals("pubdisk")){
//			data = menuService.getInitPubdisk();
//		}else if(modelType.equals("persondisk")){
//			data = menuService.getInitPersondisk();
//		}
		String agentId = TeeModuleConst.MODULE_SORT_WX_APP_ID.get(modelType);
		String appUrl = TeeStringUtil.getString(TeeModuleConst.MODULE_SORT_URL.get(modelType));
		BASE64Encoder base64Encoder = new BASE64Encoder();
		appUrl = base64Encoder.encode(appUrl.getBytes());
		
		Menu menu = new Menu();
		ViewButton receive = new ViewButton();
		receive.setName(TeeModuleConst.MODULE_SORT_TYPE.get(modelType));
		receive.setType("view");
		receive.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+TeeSysProps.getString("WEIXIN_CORPID") + "&redirect_uri="+URLEncoder.encode(TeeSysProps.getString("WEIXIN_URL")+ "/weixin/dosso.action?url="+appUrl+"&agent_id="+agentId) +"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
		menu.setButton(new Button[] { receive });
		JSONObject jsonb = JSONObject.fromObject(menu);
		data = TeeJsonUtil.toJson(jsonb);
		
		
		WEIXIN_APPID = TeeModuleConst.MODULE_SORT_WX_APP_ID.get(modelType);
		WEIXIN_TOKEN = WeiXinConst.appToken;
		WEIXIN_ENCODING_AESKEY = WeiXinConst.appAesKey;
//		map.put("WEIXIN_APPID", WEIXIN_APPID);
//		map.put("WEIXIN_TOKEN", WEIXIN_TOKEN);
//		map.put("WEIXIN_ENCODING_AESKEY", "LjTTL-NNjy30qBMpaqv1WxmB0eOLouZ40BQe9zkYsMQ1dasdasd");
		//初始化菜单
		if(TeeUtility.isNullorEmpty(WEIXIN_APPID)){
			json.setRtMsg("应用ID不能为空！");
			return json;
		}
		String code = menuService.createMenu(WEIXIN_APPID, data);
		json.setRtState(true);
		if(!code.equals("0")){
			json.setRtState(false);
			json.setRtMsg(code);
		}
		return json;
	}
	
	/**
	 * 校验邮件回调URL方法  如 callBackUrl?appName=email
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/callBackUrl")
	public void callBackUrl(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String WEIXIN_TOKEN = "";
		String WEIXIN_ENCODING_AESKEY = "";
		String corpId = "";
		/*TeeSysProps.getProps().setProperty("WEIXIN_EMAIL_APPID", "3");
		TeeSysProps.getProps().setProperty("WEIXIN_EMAIL_TOKEN","PBgR22CsFRbxWxhzMPYcnvd3B8u");
		TeeSysProps.getProps().setProperty("WEIXIN_EMAIL_ENCODING_AESKEY","8wxFsxAXfAEC9Psd8rjfpRhTcfVWynjKR9TForbXtIp");
		TeeSysProps.getProps().setProperty("WEIXIN_CORPID","wxe71007977c76156f");*/
		String signature = request.getParameter("msg_signature"); // 微信加密签名
		String timestamp = request.getParameter("timestamp"); // 时间戳
		String nonce = request.getParameter("nonce"); // 随机数
		String echostr = TeeStringUtil.getString(request.getParameter("echostr")); // 随机字符串
		String WEIXIN_APPID_NAME = "";
		String WEIXIN_TOKEN_NAME = "";
		String WEIXIN_ENCODING_AESKEY_NAME = "";
		//根据应用模块类型，获取相应模块名称
		WEIXIN_TOKEN = WeiXinConst.appToken;
		WEIXIN_ENCODING_AESKEY = WeiXinConst.appAesKey;
		corpId = TeeSysProps.getString("WEIXIN_CORPID");
		
		WXBizMsgCrypt wxcpt;
		String sEchoStr = "";
		wxcpt = new WXBizMsgCrypt(WEIXIN_TOKEN, WEIXIN_ENCODING_AESKEY, corpId);
		if (!echostr.equals("")) {
			sEchoStr = wxcpt.VerifyURL(signature, timestamp, nonce, echostr);
			//System.out.println("verifyurl echostr: " + sEchoStr);
		}
		PrintWriter out = response.getWriter();
		out.print(sEchoStr);
		out.close();
	}
	
	/**
	 * 单点登录
	 * @param request
	 * @throws IOException 
	 */
	@RequestMapping("/dosso")
	public void dosso(HttpServletRequest request,HttpServletResponse response) throws WXpiException, IOException{
		String code = TeeStringUtil.getString(request.getParameter("code")) ;
		String url = TeeStringUtil.getString(request.getParameter("url"));
		String state =  TeeStringUtil.getString(request.getParameter("state"));
		String redirect_uri = TeeStringUtil.getString(request.getParameter("redirect_uri"));
		String agent_id = TeeStringUtil.getString(request.getParameter("agent_id"));
		// agentid 跳转链接时所在的企业应用ID
		// 管理员须拥有agent的使用权限；agentid必须和跳转链接时所在的企业应用ID相同
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String UserID = null;
		if(person == null){
			redirect_uri = URLEncoder.encode(TeeSysProps.getString("WEIXIN_URL")+ "/weixin/dosso.action?url="+url+"&agent_id="+agent_id);
			if(code.equals("")){
				response.addHeader("location", "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+TeeSysProps.getString("WEIXIN_CORPID")+"&redirect_uri="+redirect_uri+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
				response.setStatus(302);
				return;
			}else{
				UserID = OAuth2Core.getUserID(AccessToken.getAccessTokenInstance().getAppToken().get(agent_id), code, "4");
				//System.out.println(UserID + "-----------BBBBBBBBBBBBB----------" + code);
				if(!"".equals(UserID)){
					//根据userId进行登陆
					person = weixinPersonService.getPersonByUserId(UserID);
					if(person!=null){
						if(person.getDept() != null){
							person.getDept().getUuid();
						}
						person.getUserRole().getUuid();
						person.getUserRoleOther().size();
						person.getDeptIdOther().size();
						request.getSession().setAttribute(TeeConst.LOGIN_USER, person);
					}
				}
			}
		}
		
		if(person!=null){
			//System.out.println("userId:--------" + person.getUserId() +":APP" + app);
			BASE64Decoder base64Decoder = new BASE64Decoder();
			url = new String(base64Decoder.decodeBuffer(url));
			response.sendRedirect(url);
		}else{
//			response.setContentType("text/html;charset=utf-8");
//			response.setCharacterEncoding("UTF-8");
//			PrintWriter printWriter = response.getWriter();
//			printWriter.write("<script>alert(\"当前微信账号没有绑定OA内部账号，无法使用此功能，请联系管理员。\");</script>");
			response.sendRedirect("/system/mobile/wx_auth_error.jsp");
		}
		
	}
	
	/**
	 * 单点登录初始化  ---- 之前的 已被弃用
	 * @param request
	 * @throws IOException 
	 */
	@RequestMapping("/sso")
	public void sso(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String state =  TeeStringUtil.getString(request.getParameter("state"));
		String app = TeeStringUtil.getString(request.getParameter("app"));
		//https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe71007977c76156f&redirect_uri=http%3A%2F%2F182.92.187.50%2Fpda%2Femail%2Findex.php%3Fhash%3Dinbox&response_type=code&scope=snsapi_base&state=fromWX_3#wechat_redirect
		//String redirect_uri = URLEncoder.encode(TeeSysProps.getString("WEIXIN_URL")+ "/weixin/dosso.action?app="+app);
		String redirect_uri = URLEncoder.encode(url+ "/weixin/dosso.action?app="+app);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		if(loginPerson==null){
			response.addHeader("location", "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+TeeSysProps.getString("WEIXIN_CORPID")+"&redirect_uri="+redirect_uri+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
			response.setStatus(302);
		}else{
			if("workflow".equals(app)){
				response.sendRedirect(url+"/system/mobile/phone/workflow/index.jsp");
			}else if("address".equals(app)){
				response.sendRedirect(url+"/system/mobile/phone/address/index.jsp");
			}else if("calendar".equals(app)){
				response.sendRedirect(url+"/system/mobile/phone/calendar/index.jsp");
			}else if("customer".equals(app)){
				response.sendRedirect(url+"/system/mobile/phone/customer/index.jsp");
			}else if("diary".equals(app)){
				response.sendRedirect(url+"/system/mobile/phone/diary/index.jsp");
			}else if("email".equals(app)){
				response.sendRedirect(url+"/system/mobile/phone/email/index.jsp");
			}else if("news".equals(app)){
				response.sendRedirect(url+"/system/mobile/phone/news/index.jsp");
			}else if("notify".equals(app)){
				response.sendRedirect(url+"/system/mobile/phone/notify/index.jsp");
			}else if("report".equals(app)){
				response.sendRedirect(url+"/system/mobile/phone/report/index.jsp");
			}else if("schedule".equals(app)){
				response.sendRedirect(url+"/system/mobile/phone/schedule/index.jsp");
			}else if("search".equals(app)){
				response.sendRedirect(url+"/system/mobile/phone/search/index.jsp");
			}else if("task".equals(app)){
				response.sendRedirect(url+"/system/mobile/phone/task/index.jsp");
			}else if("topic".equals(app)){
				response.sendRedirect(url+"/system/mobile/phone/topic/index.jsp");
			}
		}
	}
	
	
	/**
	 * 获取部门树
	 * @param request
	 * @param response
	 * @return
	 * @throws OApiException 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getDeptInfos")
	public TeeJson getDeptInfos(HttpServletRequest request) throws OApiException{
		TeeJson json = new TeeJson();
		String accessToken = AccessToken.getAccessTokenInstance().getToken();
		List zTreeList = new ArrayList();
		
		List<TeeWeixinDeptModel> deptList = weixinPersonService.getDepartmentList("");
		
		for(TeeWeixinDeptModel dept:deptList){
			TeeZTreeModel model = new TeeZTreeModel();
			model.setName(dept.getDeptName());
			model.setId(dept.getId()+"");
			model.setIconSkin("deptNode");
			model.setpId(dept.getParentid()+"");
			model.setParent(false);
			
			zTreeList.add(model);
		}
		json.setRtData(zTreeList);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 通过部门获取人员账号
	 * @param request
	 * @param response
	 * @return
	 * @throws OApiException 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getPersonsByDept")
	public TeeJson getPersonsByDept(HttpServletRequest request) throws OApiException{
		TeeJson json = new TeeJson();
		json.setRtData(weixinPersonService.getPersonsByDept(request.getParameter("id")));
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 绑定人员
	 * @param request
	 * @param response
	 * @return
	 * @throws OApiException 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/bindUser")
	public TeeJson bindUser(HttpServletRequest request) throws OApiException{
		TeeJson json = new TeeJson();
		String dingId = request.getParameter("dingId");
		String userId = request.getParameter("userId");
		
		String type = request.getParameter("type");
		if("cancel".equals(type)){
			weixinPersonService.cancelBindUser(dingId, userId);
		}else{
			weixinPersonService.bindUser(dingId, userId);
		}
		return json;
	}
	
	
	/**
	 * 下载微信文件到本地服务器
	 * @param request
	 * @return
	 * @throws OApiException
	 */
	@ResponseBody
	@RequestMapping("/downloadWxFile")
	public TeeJson downloadWxFile(HttpServletRequest request) throws OApiException{
		TeeJson json = new TeeJson();
		String files = TeeStringUtil.getString(request.getParameter("files"));
		String model = TeeStringUtil.getString(request.getParameter("model"));
		String modelId = TeeStringUtil.getString(request.getParameter("modelId"));
		
		String accessToken = AccessToken.getAccessTokenInstance().getToken();
		
		String filesSp[] = files.split(",");
		TeeAttachment attach = null;
		String urls = null;
		String fileExt = null;
		String sp[] = null;
		List list = new ArrayList();
		Map map = null;
		String fileName;
		String sp1[] = null;
		for(String filePath:filesSp){
			map = new HashMap();
			sp = filePath.split("`");
			fileName = sp[1];
			sp1 = fileName.split("\\.");
			fileExt = sp1[sp1.length-1];
			InputStream inputStream = null;
			try{
				inputStream = TeeUrlToFile.download("https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token="+accessToken+"&media_id="+sp[0]);
				attach = baseUpload.singleAttachUpload(inputStream, inputStream.available(), fileName, "", model,modelId, null);
				urls=TeeSysProps.getString("contextPath")+"/attachmentController/downFile.action?id="+attach.getSid();
				map.put("id", attach.getSid());
				map.put("url", urls);
				list.add(map);
			}catch(Exception ex){
				
			}finally{
				
			}
			
		}
		json.setRtData(list);
		return json;
	}
	
}
