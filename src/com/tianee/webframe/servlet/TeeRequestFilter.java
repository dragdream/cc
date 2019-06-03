package com.tianee.webframe.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;





import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeSysPropKeys;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.secure.TeePassBase64;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 处理Request的过滤器
 * 
 * @author syl
 * 
 */
public class TeeRequestFilter implements Filter {
	// 编码
	protected String encoding = null;
	// 过滤器配置

	protected FilterConfig filterConfig = null;

	/**
	 * 释放资源
	 */
	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}

	/**
	 * 执行过滤器
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();
		String qUri = ((HttpServletRequest) request).getRequestURI();
		if (qUri.endsWith("/")) {
			qUri += "index.jsp";
		}
		
		/**
		 * 验证是否mWebView已经登录
		 */
		if ((qUri.endsWith(".jsp") || qUri.endsWith(".action"))
				&& !TeeServletUtility.isLoginAction((HttpServletRequest) request)) {
			
//			String sessionId = request.getParameter("sessionid");
	
//			if (!TeeUtility.isNullorEmpty(sessionId)) {
//				session = (HttpSession) TeeSessionListener.getSessionContext().get(sessionId);
//				TeePerson person = (TeePerson) session.getAttribute(TeeConst.LOGIN_USER);
//				((HttpServletRequest) request).getSession().setAttribute(TeeConst.LOGIN_USER, person);
//			}
			/*
			//session 是否有效
			if (!TeeServletUtility.isValidSession(request, TeeConst.LOGIN_USER) && !qUri.endsWith(".action")) {
				//TeeServletUtility.forward(url,(HttpServletRequest) request,(HttpServletResponse) response);
				//当前请求的路径
				String qUrl = ((HttpServletRequest) request).getRequestURL().toString()+"?";
				
				
				Enumeration enumers = ((HttpServletRequest) request).getParameterNames();
				while(enumers.hasMoreElements()){
					String key = (String) enumers.nextElement();
					qUrl+="&"+key+"="+((HttpServletRequest) request).getParameter(key);
				}
				
				String url="/login.jsp?url="+TeePassBase64.encodeStr(request.getProtocol().toLowerCase().contains("https")?"https":"http"+"://"+request.getServerName()+":"+request.getServerPort()+"/systemAction/doSSOLoginIn.action?url="+TeePassBase64.encodeStr(qUrl));
				((HttpServletResponse) response).sendRedirect(url);
				return;
			}else if(!TeeServletUtility.isValidSession(request, TeeConst.LOGIN_USER) && qUri.endsWith(".action")){
				response.getWriter().write("{\"timeout\":1}");
				return;
			}
			//菜单权限路径校验
			if (!TeeServletUtility.isUseSysMenuPriv((HttpServletRequest)request,session)) {
				TeeServletUtility.forward("/inc/sessionerror.jsp?checkValidType=1",(HttpServletRequest) request,(HttpServletResponse) response);
				return;
			}
			*/
		
		}

//		if (true) {
//			Long lastOptTime = (Long) session.getAttribute("LAST_OPT_TIME");
//			Long lockSec = (Long) session.getAttribute("OFFLINE_TIME_MIN");

			/*
			 * //设置了空闲强制自动离线时间并且上次操作时间不为空时判断是否过期 if (lockSec > 0 && lastOptTime
			 * != null && lastOptTime + lockSec < new Date().getTime()) {
			 * 
			 * request.setAttribute(TeeActionKeys.RET_STATE,
			 * TeeConst.RETURN_OK); request.setAttribute(TeeActionKeys.RET_MSRG,
			 * "空闲自动离线"); request.setAttribute(TeeActionKeys.RET_DATA, "-1");
			 * TeeServletUtility.forward("/inc/rtjson.jsp",
			 * (HttpServletRequest)request, (HttpServletResponse)response);
			 * return; }
			 */
//		} else {
//			session.setAttribute("LAST_OPT_TIME", new Date().getTime());
//		}

		// request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}

	/**
	 * 过滤器初始化
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
	}

	/**
	 * 取得编码
	 * 
	 * @return
	 */
	protected String getEncoding() {
		return (this.encoding);
	}
}
