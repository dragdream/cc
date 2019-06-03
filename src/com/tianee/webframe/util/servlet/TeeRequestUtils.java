package com.tianee.webframe.util.servlet;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.web.util.UrlPathHelper;

public class TeeRequestUtils
{
  private static Logger log = Logger.getLogger("com.tianee.util.TeeRequestUtils");
  public static String getQueryParam(HttpServletRequest request, String name)
  {
    if (StringUtils.isBlank(name)) {
      return null;
    }
    if (request.getMethod().equalsIgnoreCase("POST")) {
      return request.getParameter(name);
    }
    String s = request.getQueryString();
    if (StringUtils.isBlank(s))
      return null;
    try
    {
      s = URLDecoder.decode(s, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      log.error("encoding UTF-8 not support?", e);
    }
    String[] values = (String[])parseQueryString(s).get(name);
    if ((values != null) && (values.length > 0)) {
      return values[(values.length - 1)];
    }
    return null;
  }


  public static Map<String, String[]> parseQueryString(String s)
  {
    String[] valArray = (String[])null;
    if (s == null) {
      throw new IllegalArgumentException();
    }
    Map ht = new HashMap();
    StringTokenizer st = new StringTokenizer(s, "&");
    while (st.hasMoreTokens()) {
      String pair = st.nextToken();
      int pos = pair.indexOf('=');
      if (pos == -1) {
        continue;
      }
      String key = pair.substring(0, pos);
      String val = pair.substring(pos + 1, pair.length());
      if (ht.containsKey(key)) {
        String[] oldVals = (String[])ht.get(key);
        valArray = new String[oldVals.length + 1];
        for (int i = 0; i < oldVals.length; i++) {
          valArray[i] = oldVals[i];
        }
        valArray[oldVals.length] = val;
      } else {
        valArray = new String[1];
        valArray[0] = val;
      }
      ht.put(key, valArray);
    }
    return ht;
  }

  public static Map<String, String> getRequestMap(HttpServletRequest request, String prefix)
  {
    return getRequestMap(request, prefix, false);
  }

  public static Map<String, String> getRequestMapWithPrefix(HttpServletRequest request, String prefix)
  {
    return getRequestMap(request, prefix, true);
  }

  private static Map<String, String> getRequestMap(HttpServletRequest request, String prefix, boolean nameWithPrefix)
  {
    Map map = new HashMap();
    Enumeration names = request.getParameterNames();

    while (names.hasMoreElements()) {
      String name = (String)names.nextElement();
      if (name.startsWith(prefix)) {
        String key = nameWithPrefix ? name : name.substring(prefix.length());
        String value = StringUtils.join(request.getParameterValues(name), ',');
        map.put(key, value);
      }
    }
    return map;
  }

  public static String getIpAddr(HttpServletRequest request)
  {
    String ip = request.getHeader("X-Real-IP");
    if ((!StringUtils.isBlank(ip)) && (!"unknown".equalsIgnoreCase(ip))) {
      return ip;
    }
    ip = request.getHeader("X-Forwarded-For");
    if ((!StringUtils.isBlank(ip)) && (!"unknown".equalsIgnoreCase(ip)))
    {
      int index = ip.indexOf(',');
      if (index != -1) {
        return ip.substring(0, index);
      }
      return ip;
    }

    return request.getRemoteAddr();
  }

  public static String getLocation(HttpServletRequest request)
  {
    UrlPathHelper helper = new UrlPathHelper();
    StringBuffer buff = request.getRequestURL();
    String uri = request.getRequestURI();
    String origUri = helper.getOriginatingRequestUri(request);
    buff.replace(buff.length() - uri.length(), buff.length(), origUri);
    String queryString = helper.getOriginatingQueryString(request);
    if (queryString != null) {
      buff.append("?").append(queryString);
    }
    return buff.toString();
  }

  public static String getRequestedSessionId(HttpServletRequest request)
  {
    String sid = request.getRequestedSessionId();
    String ctx = request.getContextPath();

    if ((request.isRequestedSessionIdFromURL()) || (StringUtils.isBlank(ctx))) {
      return sid;
    }

    Cookie cookie = TeeCookieUtils.getCookie(request, 
      "JSESSIONID");
    if (cookie != null) {
      return cookie.getValue();
    }
    return null;
  }

  /**
   * 从request总获取 参数到 map
   * @author zhp
   * @createTime 2013-10-17
   * @editTime 上午04:30:07
   * @desc
   */
  private HashMap<String, String> getRequestParams(HttpServletRequest request) {
      Map srcParamMap = request.getParameterMap();
      HashMap<String, String> destParamMap = new HashMap<String, String>(srcParamMap.size());
      for (Object obj : srcParamMap.keySet()) {
          String[] values = (String[]) srcParamMap.get(obj);
          if (values != null && values.length > 0) {
              destParamMap.put((String) obj, values[0]);
          } else {
              destParamMap.put((String) obj, null);
          }
      }
      return destParamMap;
 }
  public static void main(String[] args)
  {
  }
}