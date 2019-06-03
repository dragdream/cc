package com.tianee.webframe.util.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sun.misc.BASE64Decoder;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.util.cache.Cache;
import com.tianee.webframe.util.cache.Element;
import com.tianee.webframe.util.cache.TeeCacheManager;
import com.tianee.webframe.util.global.TeeBeanFactory;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;


public class TeeServletUtility {
  private static List<String> needNoCheckUriList = new ArrayList<String>();
  private static List<String> gbkCodeUriList = new ArrayList<String>();
  
  /**
   * 设置不需要检查的URI列表
   * @param uriStr
   */
  public static void setNeedNoCheckUriList(String uriStr) {
    if (TeeUtility.isNullorEmpty(uriStr)) {
      return;
    }
    String[] uriList = uriStr.split(",");
    for (int i = 0; i < uriList.length; i++) {
      String uri = uriList[i].trim();
      if (!needNoCheckUriList.contains(uri)) {
        needNoCheckUriList.add(uri);
      }
    }
  }
  
  public static void requestParamsCopyToObject(HttpServletRequest request,Object target){
	  Map params = TeeServletUtility.getParamMap(request);
	  try {
		org.apache.commons.beanutils.BeanUtils.copyProperties(target, params);
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InvocationTargetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  public static Object request2Object(HttpServletRequest request,Class clazz){
	  Object obj = null;
	  try {
		obj = clazz.newInstance();
	} catch (InstantiationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  requestParamsCopyToObject(request,obj);
	  return obj;
	  
  }
  
  /**
   * 设置需要GBK编码的URI列表
   * @param uriStr
   */
  public static void setNeedGbkCodeUriList(String uriStr) {
    if (TeeUtility.isNullorEmpty(uriStr)) {
      return;
    }
    String[] uriList = uriStr.split(",");
    for (int i = 0; i < uriList.length; i++) {
      String uri = uriList[i].trim();
      if (!gbkCodeUriList.contains(uri)) {
        gbkCodeUriList.add(uri);
      }
    }
  }
  
  /**
   * 设置不需要检查的URI列表
   * @param uriStr
   */
  public static void resetNeedNoCheckUriList(String uriStr) {
    needNoCheckUriList.clear();
    setNeedNoCheckUriList(uriStr);
  }
  
  /**
   * 设置需要GBK编码的URI列表
   * @param uriStr
   */
  public static void resetNeedGBKCodeUriList(String uriStr) {
    gbkCodeUriList.clear();
    setNeedGbkCodeUriList(uriStr);
  }
  
  /**
   * 传递请求
   * @param toUrl                        传递的URL地址
   * @param request                      请求
   * @param response                     回复
   * @throws ServletException
   * @throws IOException
   */
  public static void forward(String toUrl, HttpServletRequest request,
      HttpServletResponse response) throws ServletException,
      IOException {
    RequestDispatcher requestDispatcher =
        request.getRequestDispatcher(toUrl);
    requestDispatcher.forward(request, response);
  }
  
  /**
   * 判断是否不需要登录校验的URL地址
   * @param requestUri
   * @return
   */
  public static boolean isLoginAction(HttpServletRequest request) {
    if (request == null) {
      return false;
    }
    String qUri = request.getRequestURI();
    String contextPath = request.getContextPath();
//    if (contextPath.equals("")) {
//      contextPath = "/oaop";
//    }
    
   if (qUri.endsWith("/")) {
      qUri += "index.jsp";
    }
    if (!qUri.startsWith(contextPath)) {
      qUri = contextPath + qUri;
    }
    if (qUri.equals(contextPath + "/login.jsp")
        || qUri.equals(contextPath + "/index.jsp")
        || qUri.equals(contextPath + "/logincheck.jsp")
        || qUri.equals(contextPath + "/inc/sessionerror.jsp")
        || qUri.equals(contextPath + "/systemAction/doLoginIn.action")
        || qUri.equals(contextPath + "/mobileSystemAction/doLoginIn.action")
        || qUri.equals(contextPath + "/verifyCode/generate.action")
        || qUri.equals(contextPath + "/book/book1.action")
        || qUri.equals(contextPath + "/saveInterface/save.action")
        || qUri.equals(contextPath + "/saveInterface/update.action")
        || qUri.equals(contextPath + "/saveInterface/delete.action")
        || qUri.equals(contextPath + "/dataMigrationController/topic.action")
        || qUri.equals(contextPath + "/dataMigrationController/addFileNetdisk.action")
        || qUri.equals(contextPath + "/teeFileInterfaceController/addOrUpdateFileNetdisk.action")
        || qUri.equals(contextPath + "/teeFileInterfaceController/uploadNetdiskFile.action")
    		){
      
      return true;
    }
    if (!TeeUtility.isNullorEmpty(TeeSysProps.getString("NEED_NOT_CHECKED_URI"))) {
      if (needNoCheckUriList.size() < 1) {
        setNeedNoCheckUriList(TeeSysProps.getString("NEED_NOT_CHECKED_URI"));
      }
      for (int i = 0; i < needNoCheckUriList.size(); i++) {
        String uri = needNoCheckUriList.get(i);
        if (qUri.startsWith(contextPath + uri)) {
          return true;
        }
      }
    }
   
    return false;
  }
  
  
  	/**
  	 * 检查是否存在访问权限
  	 * @author syl
  	 * @date 2014-2-26
  	 * @param request
  	 * @param session
  	 * @return
  	 */
    public static boolean isUseSysMenuPriv(HttpServletRequest request , HttpSession session) {
		if (request == null) {
			return true;
		}
		String qUri = request.getRequestURI();
		String contextPath = request.getContextPath();
//		if (contextPath.equals("")) {
//			contextPath = "/oaop";
//		}
		TeePerson person = (TeePerson) session.getAttribute(TeeConst.LOGIN_USER);
		if(TeePersonService.checkIsAdminPriv(person)){
			return true;
		}
    	//获取所有菜单权限缓存对象
		Cache menuPrivCache = TeeCacheManager.getCache("menuPriv");
		
		//是否需要校验，配置文件总开关
		String IS_CHECK_VALID_SYS_MENU_PRIV = TeeStringUtil.getString(TeeSysProps.getString("IS_CHECK_VALID_SYS_MENU_PRIV") , "0");
		if(menuPrivCache != null  && IS_CHECK_VALID_SYS_MENU_PRIV.equals("1")){
			//处理多个//斜杠
			Pattern p = Pattern.compile("\\/+");
			Matcher m = p.matcher(qUri);
			qUri = m.replaceAll("/");
			if(qUri.startsWith(contextPath)){
				qUri = qUri.substring(contextPath.length());
			}
			
			Element urlElement =  menuPrivCache.get(qUri);
			if(urlElement != null){
				
				//获取菜单权限个人缓存对象
				Cache menuPrivPersonal = TeeCacheManager.getCache("menuPrivPersonal");
				if(menuPrivPersonal != null){
					//获取个人是否需要判断此路径元素
					Element personElement =  menuPrivPersonal.get(person.getUuid());
					if(personElement != null){
						Map<String , Object> map = (Map<String , Object>)personElement.getValue();
						if(map != null && !TeeUtility.isNullorEmpty(map.get(qUri))){//存在权限不等于空
							return true;
						}else{
							return false;
						}
					}else{
						return false;
					}
				}
				
			}
			
		}
		return true;
  }
  
  /**
   * 是否需要GBK编码
   * @param request
   * @return
   */
  public static boolean isGbkCode(HttpServletRequest request) {
    if (request == null) {
      return false;
    }
    
    if (gbkCodeUriList.size() > 0 || !TeeUtility.isNullorEmpty(TeeSysProps.getString("NEED_GBKCODE_URI"))) {
      if (gbkCodeUriList.size() < 1) {
        setNeedGbkCodeUriList(TeeSysProps.getString("NEED_GBKCODE_URI"));
      }
      
      String qUri = request.getRequestURI();
      String contextPath = request.getContextPath();
      
      if (qUri.endsWith("/")) {
        qUri += "index.jsp";
      }
      if (!qUri.startsWith(contextPath)) {
        qUri = contextPath + qUri;
      }
      for (int i = 0; i < gbkCodeUriList.size(); i++) {
        String uri = gbkCodeUriList.get(i);
        if (qUri.startsWith(contextPath + uri)) {
          return true;
        }
      }
    }
    
    return false;
  }
  
  /**
   * 验证Session的有效性
   * @param session        用户上下文会话
   * @param key            检查参数的键名
   * @return               true=在上下文会话中存在以key为名称的对象
   *                       false=在上下文会话中不存在以key为名称的对象
   */
  public static boolean isValidSession(ServletRequest request, String key) {
	  HttpServletRequest httpRequest = (HttpServletRequest)request;
    if (httpRequest.getSession() == null || key == null) {
      return false;
    }
    
    if (httpRequest.getSession().getAttribute(key) == null) {
    	//获取cookie值，用于app的session保持
        Cookie cookies[] = httpRequest.getCookies();
        if(cookies==null){
        	return false;
        }
        boolean valid = false;
        for(Cookie cookie:cookies){
        	if("TOKEN".equals(cookie.getName())){
        		String value = cookie.getValue();
        		value = value.replace("%3D", "=").replace("%2F", "/");
        		BASE64Decoder base64Decoder = new BASE64Decoder();
        		try {
    				value = new String(base64Decoder.decodeBuffer(value));
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
        		TeePersonService personService = 
        				(TeePersonService) TeeBeanFactory.getBean("teePersonService");
        		TeePerson person = personService.getPersonByUserId(value);
        		if(person!=null){
        			if(person.getDept() != null){
    					person.getDept().getDeptName();
    				}
        			if(person.getUserRole()!=null){
        				person.getUserRole().getRoleName();
        			}
        			try{
        				person.getUserRoleOther().size();
        			}catch(Exception ex){}
        			try{
        				person.getDeptIdOther().size();
        			}catch(Exception ex){}
    				
    				httpRequest.getSession().setAttribute(TeeConst.LOGIN_USER, person);
        		}
        		break;
        	}
        }
        
        if(valid){
        	return true;
        }else{
        	return false;
        }
    }
    return true;
  }

  
  /**
   * 取得cookie的值
   * @param request
   * @param cookieName
   * @param defaultValue
   * @return
   */
  public static String getCookieValue(HttpServletRequest request,
      String cookieName) throws Exception {   
    return getCookieValue(request, cookieName, "");
  }
  
  /**
   * 取得cookie的值
   * @param request
   * @param cookieName
   * @param defaultValue
   * @return
   */
  public static String getCookieValue(HttpServletRequest request,
      String cookieName,
      String defaultValue) throws Exception {

    Cookie[] cookies = request.getCookies();    
    return getCookieValue(cookies, cookieName, defaultValue);
  }
  
  /**
   * 取得cookie的值
   * @param cookies
   * @param cookieName
   * @param defaultValue
   * @return
   */
  public static String getCookieValue(Cookie[] cookies,
      String cookieName,
      String defaultValue) throws Exception {
    
    if (cookies == null || cookieName == null) {
      return null;
    }
    
    for (int i = 0; i < cookies.length; i++) {
      Cookie cookie = cookies[i];
      if (cookieName.equals(cookie.getName())) {
        return URLDecoder.decode(cookie.getValue(), "GBK");
      }
    }
    return defaultValue;
  }
  
  /**
   * 添加Cookie
   * @param name
   * @param value
   * @param leavSeconds
   * @param response
   */
  public static void addCookie(String name,
      String value,
      int leafSeconds,
      HttpServletResponse response) throws Exception {
    
    Cookie cookie = new Cookie(name, URLEncoder.encode(value, "GBK"));
    cookie.setMaxAge(leafSeconds);
    response.addCookie(cookie);
  }
  
  /**
   * 取得参数哈希表
   * @param request
   * @return
   */
  public static Map<String, String> getParamMap(HttpServletRequest request) {
    Map<String, String> rtMap = new HashMap<String, String>();
    Map<String, String[]> paramMap = request.getParameterMap();
    
    Iterator<String> iKeys = paramMap.keySet().iterator();
    while (iKeys.hasNext()) {
      String key = iKeys.next();
      String[] value = paramMap.get(key);
      if (value != null && value.length > 0) {
        rtMap.put(key, value[0]);
      }else {
        rtMap.put(key, "");
      }
    }
    
    return rtMap;
  }
  
  /**
   * 取得请求中的参数，构成Url参数串
   * @param request     Http请求对象
   * @return
   */
  public static String getUrlParams(HttpServletRequest request) throws Exception {    
    return getUrlParams(request, null);
  }
  
  /**
   * 取得请求中的参数，构成Url参数串
   * @param request     Http请求对象
   * @return
   */
  public static String getUrlParams(HttpServletRequest request,
      ArrayList exKeyList) throws Exception {
    
    if (exKeyList == null) {
      exKeyList = new ArrayList();
    }
    
    StringBuffer params = new StringBuffer();
    Enumeration eParamName = request.getParameterNames();
    while (eParamName.hasMoreElements()) {
      String paramName = (String)eParamName.nextElement();
      if (exKeyList.contains(paramName)) {
        continue;
      }
      String paramValue = TeeUtility.iso88591ToGbk(
          request.getParameter(paramName));
      params.append(paramName);
      params.append("=");
      params.append(paramValue);
      params.append("&");
    }
    if (params.length() > 1) {
      params.delete(params.length() - 1, params.length());
    }
    return params.toString();
  }
  
  /**
   * 创建一个实例
   * @param className
   * @return
   * @throws ClassNotFoundException
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  public static Object applicationInstance(String className)
    throws ClassNotFoundException, IllegalAccessException, InstantiationException {
  
    return (applicationClass(className).newInstance());
  
  }
  
  /**
   * 创建一个类
   * @param className
   * @return
   * @throws ClassNotFoundException
   */
  public static Class applicationClass(String className) throws ClassNotFoundException {

      // Look up the class loader to be used
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      if (classLoader == null) {
        classLoader = TeeServletUtility.class.getClassLoader();
      }

      // Attempt to load the specified class
      return (classLoader.loadClass(className));

  }
  
  /**
   * 取得Web应用的安装目录
   * @param context
   * @return
   */
  public static String getWebAppDir(ServletContext context) {
    return context.getRealPath("/");
  }
  
  /**
   * 取得Web应用所在的盘符
   * @param context
   * @return
   */
  public static String getWebAppDiskPart(ServletContext context) {
    return getWebAppDir(context).substring(0, 3);
  }
  
  /**
   * 取得参数
   * @param request
   * @param paramName
   * @param defalutValue
   * @return
   */
  public static String getParam(HttpServletRequest request, String paramName, String defalutValue) {
    String paramValue = null;
    //新传递的参数优先级最高，目的是防止参数覆盖
  /*  Map paramMapInner = (Map)request.getAttribute(TeeBeanKeys.PARAM_MAP_INNER);
    if (paramMapInner != null) {
      paramValue = (String)paramMapInner.get(paramName);
      if (!TeeUtility.isNullorEmpty(paramValue)) {
        return paramValue;
      }
    }
    paramValue = request.getParameter(paramName);
    if (!TeeUtility.isNullorEmpty(paramValue)) {
      return paramValue;
    }        
    paramValue = (String)request.getAttribute(paramName);
    if (!TeeUtility.isNullorEmpty(paramValue)) {
      return paramValue;
    }*/
    return defalutValue;
  }
  /**
   * 取得参数
   * @param request
   * @param paramName
   * @return
   */
  public static String getParam(HttpServletRequest request, String paramName) {
	  return getParam(request, paramName, "");
  }
}
