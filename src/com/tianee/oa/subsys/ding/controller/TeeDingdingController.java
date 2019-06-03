package com.tianee.oa.subsys.ding.controller;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.auth.AuthHelper;
import com.alibaba.dingtalk.openapi.demo.department.Department;
import com.alibaba.dingtalk.openapi.demo.department.DepartmentHelper;
import com.alibaba.dingtalk.openapi.demo.user.UserHelper;
import com.alibaba.dingtalk.openapi.demo.utils.HttpHelper;
import com.alibaba.fastjson.JSONObject;
import com.qq.weixin.util.WXpiException;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeModuleConst;
import com.tianee.oa.subsys.ding.service.TeeDingdingService;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUrlToFile;

/**
 * @author kakalion
 *
 */
@Controller
@RequestMapping("/dingding")
public class TeeDingdingController {
	
	@Autowired
	TeeDingdingService dingdingService;
	
	@Autowired
	TeePersonService personService;
	
	@Autowired
	TeeBaseUpload baseUpload;
	
	/**
	 * 单点登录初始化
	 * @param request
	 * @throws IOException 
	 */
	@RequestMapping("/sso")
	public void sso(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String url = TeeSysProps.getString("DD_URL");
		String appid = TeeSysProps.getString("DD_CORPID");
		String targetUrl = TeeStringUtil.getString(request.getParameter("url"));
		BASE64Decoder base64Decoder = new BASE64Decoder();
		targetUrl = new String(base64Decoder.decodeBuffer(targetUrl)); 
		String redirect_uri = URLEncoder.encode(url+"/dingding/dosso.action?url="+TeeStringUtil.getString(request.getParameter("url")));
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		if(loginPerson==null){
			response.addHeader("location", "https://oapi.dingtalk.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+redirect_uri+"&response_type=code&scope=snsapi_base&state=abcd1234");
			response.setStatus(302);
		}else{
//			if("workflow".equals(app)){
//				response.sendRedirect(url+"/system/mobile/phone/workflow/index.jsp");
//			}else if("address".equals(app)){
//				response.sendRedirect(url+"/system/mobile/phone/address/index.jsp");
//			}else if("calendar".equals(app)){
//				response.sendRedirect(url+"/system/mobile/phone/calendar/index.jsp");
//			}else if("customer".equals(app)){
//				response.sendRedirect(url+"/system/mobile/phone/customer/index.jsp");
//			}else if("diary".equals(app)){
//				response.sendRedirect(url+"/system/mobile/phone/diary/index.jsp");
//			}else if("email".equals(app)){
//				response.sendRedirect(url+"/system/mobile/phone/email/index.jsp");
//			}else if("news".equals(app)){
//				response.sendRedirect(url+"/system/mobile/phone/news/index.jsp");
//			}else if("notify".equals(app)){
//				response.sendRedirect(url+"/system/mobile/phone/notify/index.jsp");
//			}else if("report".equals(app)){
//				response.sendRedirect(url+"/system/mobile/phone/report/index.jsp");
//			}else if("schedule".equals(app)){
//				response.sendRedirect(url+"/system/mobile/phone/schedule/index.jsp");
//			}else if("search".equals(app)){
//				response.sendRedirect(url+"/system/mobile/phone/search/index.jsp");
//			}else if("task".equals(app)){
//				response.sendRedirect(url+"/system/mobile/phone/task/index.jsp");
//			}else if("topic".equals(app)){
//				response.sendRedirect(url+"/system/mobile/phone/topic/index.jsp");
//			}else if("pubdisk".equals(app)){
//				response.sendRedirect(url+"/system/mobile/phone/fileNetdisk/public/index.jsp");
//			}else if("persondisk".equals(app)){
//				response.sendRedirect(url+"/system/mobile/phone/fileNetdisk/person/index.jsp");
//			}else{
				response.sendRedirect(targetUrl);
//			}
		}
	}
	
	/**
	 * 单点登录
	 * @param request
	 * @throws OApiException 
	 * @throws IOException 
	 */
	@RequestMapping("/dosso")
	public void dosso(HttpServletRequest request,HttpServletResponse response) throws OApiException, IOException{
		String code = request.getParameter("code");
		String url = TeeSysProps.getString("DD_URL");
		String targetUrl = TeeStringUtil.getString(request.getParameter("url"));
		BASE64Decoder base64Decoder = new BASE64Decoder();
		targetUrl = new String(base64Decoder.decodeBuffer(targetUrl));
//		String DD_CORPID = TeeSysProps.getString("DD_CORPID");
//		String DD_CORPSECRET = TeeSysProps.getString("DD_CORPSECRET");
		String accessToken = TeeSysProps.getString("DING_ACCESS_TOKEN");//通行证
//		String jsApiTicket = TeeSysProps.getString("DING_JSAPI_TICKET");//js票据
		
		if(!"".equals(accessToken)){
			JSONObject jsonData = UserHelper.getUserInfo(accessToken, code);
			String userId = jsonData.getString("userid");
			
			//根据userId进行登陆
			TeePerson person = personService.getPersonByDingUserId(userId);
			if(person!=null){
				person.getDept().getUuid();
				person.getUserRole().getUuid();
				person.getUserRoleOther().size();
				person.getDeptIdOther().size();
				request.getSession().setAttribute(TeeConst.LOGIN_USER, person);
				
//				if("workflow".equals(app)){
//					response.sendRedirect(url+"/system/mobile/phone/workflow/index.jsp");
//				}else if("address".equals(app)){
//					response.sendRedirect(url+"/system/mobile/phone/address/index.jsp");
//				}else if("calendar".equals(app)){
//					response.sendRedirect(url+"/system/mobile/phone/calendar/index.jsp");
//				}else if("customer".equals(app)){
//					response.sendRedirect(url+"/system/mobile/phone/customer/index.jsp");
//				}else if("diary".equals(app)){
//					response.sendRedirect(url+"/system/mobile/phone/diary/index.jsp");
//				}else if("email".equals(app)){
//					response.sendRedirect(url+"/system/mobile/phone/email/index.jsp");
//				}else if("news".equals(app)){
//					response.sendRedirect(url+"/system/mobile/phone/news/index.jsp");
//				}else if("notify".equals(app)){
//					response.sendRedirect(url+"/system/mobile/phone/notify/index.jsp");
//				}else if("report".equals(app)){
//					response.sendRedirect(url+"/system/mobile/phone/report/index.jsp");
//				}else if("schedule".equals(app)){
//					response.sendRedirect(url+"/system/mobile/phone/schedule/index.jsp");
//				}else if("search".equals(app)){
//					response.sendRedirect(url+"/system/mobile/phone/search/index.jsp");
//				}else if("task".equals(app)){
//					response.sendRedirect(url+"/system/mobile/phone/task/index.jsp");
//				}else if("topic".equals(app)){
//					response.sendRedirect(url+"/system/mobile/phone/topic/index.jsp");
//				}else if("pubdisk".equals(app)){
//					response.sendRedirect(url+"/system/mobile/phone/fileNetdisk/public/index.jsp");
//				}else if("persondisk".equals(app)){
//					response.sendRedirect(url+"/system/mobile/phone/fileNetdisk/person/index.jsp");
//				}else{
					response.sendRedirect(targetUrl);
//				}
			}else{
//				PrintWriter pw = response.getWriter();
//				pw.write("<script>alert(\"没有在OA中找到与钉钉UserID["+userId+"]绑定的账号\");</script>");
				response.sendRedirect("/system/mobile/ding_auth_error.jsp");
			}
		}
	}
	
	/**
	 * 获取基本信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getBasicParam")
	public TeeJson getBasicParam(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtData(dingdingService.getBasicParam());
		return json;
	}
	
	/**
	 * 保存基本信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/saveBasicParam")
	public TeeJson saveBasicParam(HttpServletRequest request){
		TeeJson json = new TeeJson();
		dingdingService.saveBasicParam(TeeServletUtility.getParamMap(request));
		return json;
	}
	
	/**
	 * 保存应用信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/saveAppParam")
	public TeeJson saveAppParam(HttpServletRequest request){
		TeeJson json = new TeeJson();
		dingdingService.saveAppParam(TeeServletUtility.getParamMap(request));
		return json;
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
		WEIXIN_APPID = TeeModuleConst.MODULE_SORT_DD_APP_ID.get(modelType);
//		WEIXIN_TOKEN = TeeSysProps.getString(temp[1]);
//		WEIXIN_ENCODING_AESKEY = TeeSysProps.getString(temp[2]);
		map.put("WEIXIN_APPID", WEIXIN_APPID);
		map.put("WEIXIN_TOKEN", WEIXIN_TOKEN);
		map.put("WEIXIN_ENCODING_AESKEY", WEIXIN_ENCODING_AESKEY);
		
		String url = TeeStringUtil.getString(TeeModuleConst.MODULE_SORT_URL.get(modelType),"");
		BASE64Encoder base64Encoder = new BASE64Encoder();
		url = base64Encoder.encode(url.getBytes());
		map.put("WEIXIN_APP_URL", TeeSysProps.getString("DD_URL") + "/dingding/sso.action?url="+url);
		
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 测试连接状态
	 * @param request
	 * @param response
	 * @return
	 * @throws OApiException 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/testConnections")
	public TeeJson testConnections(HttpServletRequest request) throws OApiException{
		TeeJson json = new TeeJson();
		
		String DD_CORPID = TeeStringUtil.getString(request.getParameter("DD_CORPID"));
		String DD_CORPSECRET = TeeStringUtil.getString(request.getParameter("DD_CORPSECRET"));
		JSONObject jsonData = HttpHelper.httpGet("https://oapi.dingtalk.com/gettoken?corpid="+DD_CORPID+"&corpsecret="+DD_CORPSECRET+"");
		if("ok".equals(jsonData.getString("errmsg"))){
			json.setRtState(true);
			TeeSysProps.getProps().setProperty("DING_ACCESS_TOKEN", jsonData.getString("access_token"));
			String ticket = AuthHelper.getJsapiTicket(jsonData.getString("access_token"));
			TeeSysProps.getProps().setProperty("DING_JSAPI_TICKET", ticket);
		}else{
			json.setRtMsg(jsonData.getString("errmsg"));
			json.setRtState(false);
		}
		return json;
	}
	
	/**
	 * 同步组织机构
	 * @param request
	 * @param response
	 * @return
	 * @throws OApiException 
	 * @throws Exception
	 */
	@RequestMapping("/syncOrg")
	public void syncOrg(HttpServletResponse response) throws Exception{
		dingdingService.syncOrg(response);
	}
	
	
	
	
	/**
	 * 同步指定部门
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/syncDept")
	public void syncDept(HttpServletResponse response,HttpServletRequest request) throws Exception{
		dingdingService.syncDept(response,request);
	}
	
	
	/**
	 * 从钉钉上删除某个部门
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/delDingDept")
	public void delDingDept(HttpServletResponse response,HttpServletRequest request) throws Exception{
		dingdingService.delDingDept(response,request);
	}
	
	/**
	 * 同步人员
	 * @param request
	 * @param response
	 * @return
	 * @throws OApiException 
	 * @throws Exception
	 */
	@RequestMapping("/syncPerson")
	public void syncPerson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String userIds = TeeStringUtil.getString(request.getParameter("ids"));
		String deptIds = TeeStringUtil.getString(request.getParameter("deptIds"));
		String oper = TeeStringUtil.getString(request.getParameter("oper"));
		dingdingService.syncPerson(userIds,deptIds,oper,response);
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
		String accessToken = TeeSysProps.getString("DING_ACCESS_TOKEN");
		List zTreeList = new ArrayList();
		List<Department> deptList = DepartmentHelper.listDepartments(accessToken, "");
		for(Department dept:deptList){
			TeeZTreeModel model = new TeeZTreeModel();
			model.setName(dept.name);
			model.setId(dept.id);
			model.setIconSkin("deptNode");
			model.setpId((dept.parentid==null?"0":dept.parentid));
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
		json.setRtData(dingdingService.getPersonsByDept(request.getParameter("id")));
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
			dingdingService.cancelBindUser(dingId, userId);
		}else{
			dingdingService.bindUser(dingId, userId);
		}
		return json;
	}
	
	
	/**
	 * 下载钉钉文件到本地服务器
	 * @param request
	 * @return
	 * @throws OApiException
	 */
	@ResponseBody
	@RequestMapping("/downloadDingFile")
	public TeeJson downloadDingFile(HttpServletRequest request) throws OApiException{
		TeeJson json = new TeeJson();
		String files = TeeStringUtil.getString(request.getParameter("files"));
		String model = TeeStringUtil.getString(request.getParameter("model"));
		String modelId = TeeStringUtil.getString(request.getParameter("modelId"));
		
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		String filesSp[] = files.split(",");
		TeeAttachment attach = null;
		String urls = null;
		String fileExt = null;
		String sp[] = null;
		List list = new ArrayList();
		Map map = null;
		for(String filePath:filesSp){
			map = new HashMap();
			sp = filePath.split("\\.");
			fileExt = sp[sp.length-1];
			InputStream inputStream = null;
			try{
				inputStream = TeeUrlToFile.download(filePath);
				attach = baseUpload.singleAttachUpload(inputStream, inputStream.available(), "图片"+System.currentTimeMillis()+"."+fileExt, "", model,modelId, loginUser);
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
