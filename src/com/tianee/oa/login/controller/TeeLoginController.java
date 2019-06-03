package com.tianee.oa.login.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.org.bjca.client.security.SecurityEngineDeal;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserOnline;
import com.tianee.oa.core.org.dao.TeeUserOnlineDao;
import com.tianee.oa.login.service.TeeLoginService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.servlet.TeeSessionListener;

@Controller
@RequestMapping("/CAlogin")
public class TeeLoginController {

	@Autowired
	private TeeLoginService service;
	@Autowired
	TeeUserOnlineDao  userOnlineDao;
	
	@ResponseBody
	@RequestMapping("/verifyCertUniqueId")
	public TeeJson verifyCertUniqueId(HttpServletRequest request) throws Exception, IOException{
		
		TeeJson json = new TeeJson();
		
		Properties properties = new Properties();
		FileInputStream is = new FileInputStream(request.getRealPath("/properties/webappName.properties"));
		properties.load(is);
		is.close();
		
		SecurityEngineDeal.setProfilePath(request.getRealPath("/BJCAROOT"));
		
		SecurityEngineDeal sed = null;
	  	sed = SecurityEngineDeal.getInstance(properties.getProperty("webappName"));
	  	
	  	HttpSession session = request.getSession(true);
	  	String ranStr = (String) session.getAttribute("Random");
		
		//获得登陆用户cert
		String clientCert = request.getParameter("UserCert");
		String UserSignedData = request.getParameter("UserSignedData");
		String ContainerName = request.getParameter("ContainerName");
		String certPub = sed.getCertInfo(clientCert, 8);
		String certType = sed.getCertInfo(clientCert, 31);
		session.setAttribute("UserCertType", certType);
		
		//验证客户端签名
		byte[] signedByte = sed.base64Decode(UserSignedData);
		try {
			if (sed.verifySignedData(clientCert, ranStr.getBytes(), signedByte)) {
				//out.println("<h3>验证客户端签名成功！</h3>");
			} else {
				json.setRtState(false);
				json.setRtMsg("登录失败:验证客户端签名失败！");
				return json;
			}
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("验证客户端签名错误！");
			return json;
		}
		//验证客户端证书
		try {

			int retValue = sed.validateCert(clientCert);
			if (retValue == 1) {

				session.setAttribute("ContainerName", ContainerName);
		
				String uniqueIdStr = "";
				try {
					uniqueIdStr = sed.getCertInfo(clientCert, 17);
				} catch (Exception e) {
					json.setRtState(false);
					json.setRtMsg("登录失败:验证证书错误！");
					return json;
				}
				session.setAttribute("UniqueID", uniqueIdStr);
				
				//组织机构代码或身份证号
				String uniqueId = "";
				try {
					//获得登陆用户ID
					uniqueId = sed.getCertInfoByOid(clientCert,
							"2.16.840.1.113732.2");
				} catch (Exception e) {
					uniqueId = "";
				}
				String uniqueId_sm2 = "";										
					try {
					//获得登陆用户ID
					uniqueId_sm2 = sed.getCertInfoByOid(clientCert,
							"1.2.156.112562.2.1.1.1");
					} catch (Exception e) {
						json.setRtState(false);
						json.setRtMsg("登录失败:证书无效！");
						return json;
					}

				
				String entity_id_rsa = "";
				String entity_id_sm2 = "";
				String xydm = "";
				//证书实体标识
				    try {
					//获得登陆用户ID
					entity_id_rsa = sed.getCertInfoByOid(clientCert,
							"1.2.86.11.7.1.8");//单位证书唯一标识ID 取这个 "1.2.86.11.7.1.8"
					} catch (Exception e) {
						entity_id_rsa = "";
					}
					try {
					//获得登陆用户ID
					entity_id_sm2 = sed.getCertInfoByOid(clientCert,
							"1.2.156.112562.2.1.1.23");
					} catch (Exception e) {
						entity_id_sm2 = "";
					}
				//企业信用代码
				try {
					//获得登陆用户ID
					xydm = sed.getCertInfoByOid(clientCert,
							"1.2.156.112562.2.1.1.17");
					} catch (Exception e) {
						xydm = "";
					}

				if((uniqueId == null || "".equals(uniqueId.trim())) || (entity_id_sm2 == null || "".equals(entity_id_sm2.trim()))) {
					//请在错误页面上提示。
					//out.println("登录失败:证书无效,证书唯一标示不存在!");
					//return;
				}else{
					//请处理您的业务登录。
				}
				
			} else {
				//客户端证书验证失败
				if (retValue == -1) {
					json.setRtState(false);
					json.setRtMsg("登录失败:证书的根不被信任！");
					return json;
				} else if (retValue == -2) {
					json.setRtState(false);
					json.setRtMsg("登录失败:证书超过有效期！");
					return json;
				} else if (retValue == -3) {
					json.setRtState(false);
					json.setRtMsg("登录失败:证书为作废证书！");
					return json;
				} else if (retValue == -4) {
					json.setRtState(false);
					json.setRtMsg("登录失败:证书被临时冻结！");
					return json;
				} else if (retValue == -5) {
					json.setRtState(false);
					json.setRtMsg("登录失败:证书未启用！");
					return json;
				}
				json.setRtState(false);
				json.setRtMsg("登录失败:证书无效！");
				return json;
			}
		} catch (Exception ex) {
			json.setRtState(false);
			json.setRtMsg("登录失败:证书无效！");
			return json;
		}
		
		String certUniqueId = request.getParameter("uniqueId");
		if(certUniqueId==null||certUniqueId.equals("")){
			json.setRtState(false);
			json.setRtMsg("登录失败:证书无效,证书唯一标识不存在!");
			return json;
		}
		List<TeePerson> list = service.verifyCertUniqueId(certUniqueId);
		
		if(list.size()>1){
			json.setRtState(false);
			json.setRtMsg("登录失败:有重复的用户标识");
		}else if(list.size()==1){
			TeePerson teePerson = list.get(0);
			TeeSessionListener.data.put(session.getId(), session);
			if(teePerson.getDept() != null){
				teePerson.getDept().getDeptName();
			}
			if(teePerson.getUserRole()!=null){
				teePerson.getUserRole().getRoleName();
			}
			try{
				teePerson.getUserRoleOther().size();
			}catch(Exception ex){}
			try{
				teePerson.getDeptIdOther().size();
			}catch(Exception ex){}
			
			session.setAttribute(TeeConst.LOGIN_USER, teePerson); 
			
			//往userOnline插入一条数据
			TeeUserOnline userOnline=new TeeUserOnline();
			userOnline.setSessionToken(session.getId());
			userOnline.setLoginTime(new Date());
			userOnline.setUserId(teePerson.getUuid());
			userOnline.setUserStatus(1);
			userOnline.setClient("1");
			userOnline.setIp(request.getRemoteAddr());							
			userOnlineDao.addUserOnline(userOnline);
			
			Map  data=new HashMap();
			data.put("userId",teePerson.getUserId());
			if(teePerson.getUserRole()!=null){
				data.put("roleId",teePerson.getUserRole().getUuid());
			}
			
			json.setRtData(data);
			json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("登录失败:无此用户");
		}
		
		return json;
	}
	
}
