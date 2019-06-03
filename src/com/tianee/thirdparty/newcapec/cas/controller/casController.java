package com.tianee.thirdparty.newcapec.cas.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sun.misc.BASE64Decoder;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.newcapec.cas.service.casService;
import com.tianee.thirdparty.newcapec.supwisdom.CASUtils;
import com.tianee.thirdparty.newcapec.supwisdom.LoginUser;
import com.tianee.webframe.util.global.TeeBeanFactory;
import com.tianee.webframe.util.global.TeeSysCustomerProps;
import com.tianee.webframe.util.secure.TeePassBase64;

@Controller
@RequestMapping("/cassso")
public class casController{
	@Autowired
	private casService casservice;

@RequestMapping("/tbUser")
	public void tbUser(){
		try {
			casservice.userTimer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping("/tbDept")
	public void tbDept(){
		try {
			casservice.deptTimer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/login")
	public void dologin( HttpServletResponse response,HttpServletRequest request,HttpSession session) throws IOException {
		String targetUrl = CASUtils.getTargetUrl(request);
		if (targetUrl.indexOf("http") == -1) {//判断是不是地址已被加密
			targetUrl = TeePassBase64.decodeStr(targetUrl);
			if (CASUtils.isLogin(session)) {
				if (targetUrl == TeeSysCustomerProps.getString("SSO_ADDR_")) {
					response.sendRedirect(TeeSysCustomerProps
							.getString("SSO_ADDR_"));// 直接跳到系统
				} else {
					response.sendRedirect(targetUrl);
				}

			} else {
				if (CASUtils.hasTicket(request)) {
					LoginUser loginUser = CASUtils.getLoginUser(request);
					if (loginUser.isLogin() && doLogin(loginUser, request,response)) {
						CASUtils.login(loginUser, session);
						response.sendRedirect(targetUrl);// 参数地址
					} else {
						//response.sendRedirect(CASUtils.getLogoutUrl(request));
						response.setContentType("text/html");
						response.setCharacterEncoding("gb2312");
						try {
							PrintWriter out = response.getWriter();
							out.print("<script>");
							out.print("alert('登录失败，请您重新登录');window.close();");
							out.print("</script>");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					String loginUrl = CASUtils.getLoginUrl(request);
					response.sendRedirect(loginUrl);
				}
			}
		} else {

			if (CASUtils.isLogin(session)) {
				if (targetUrl == TeeSysCustomerProps.getString("SSO_ADDR_")) {
					response.sendRedirect(TeeSysCustomerProps
							.getString("SSO_ADDR_"));// 直接跳到系统
				} else {
					response.sendRedirect(targetUrl);
				}

			} else {
				if (CASUtils.hasTicket(request)) {
					LoginUser loginUser = CASUtils.getLoginUser(request);
					if (loginUser.isLogin() && doLogin(loginUser, request,response)) {
						CASUtils.login(loginUser, session);
						response.sendRedirect(targetUrl);// 参数地址
					} else {
						//response.sendRedirect(CASUtils.getLogoutUrl(request));
						response.setContentType("text/html");
						response.setCharacterEncoding("gb2312");
						try {
							PrintWriter out = response.getWriter();
							out.print("<script>");
							out.print("alert('登录失败，请您重新登录');window.close();");
							out.print("</script>");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					String loginUrl = CASUtils.getLoginUrl(request);
					response.sendRedirect(loginUrl);
				}
			}
		}
}
		
		public boolean doLogin(LoginUser loginUser, HttpServletRequest request,HttpServletResponse response ) {
		HttpSession session = request.getSession();
		String username = loginUser.getAccount();// 工号
        boolean panduan;
		casService casservice = (casService) TeeBeanFactory
				.getBean("casService");// 调用框架内部Service
		// 存入本地session
		TeePerson tp = casservice.selectUser(username);// 根据username查相关数据
		if(tp!=null){
		session.setAttribute(TeeConst.LOGIN_USER, tp);
		session.setAttribute("cuo", "110");
		panduan=true;
		}else{
			
			panduan=false;
		}
		// ServletContext application = session.getServletContext();
		// 如果使用了Spring可以用下面的方法获取spring的context对象
		// WebApplicationContext appContext =
		// WebApplicationContextUtils.getWebApplicationContext(application);
		// 如果需要使用SpringMVC上下文、可以用下面的方法获取springMVC的context对象
		// WebApplicationContext mvcContext =
		// RequestContextUtils.getWebApplicationContext(request);
	//	return true;
		return panduan;

		}
	
		
	

	

}




