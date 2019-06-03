package com.beidasoft.xzfy.base.controller;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 所有controller的基础类，包含IP,Session,Request等信息 
 * @author fyj
 *
 */
public class FyBaseController implements Serializable{
    
    /**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 获取request中的Attribute值
     * @param attributeName 
     * @return
     */
	public Object getAttribute(String attributeName) {
        return this.getRequest().getAttribute(attributeName);
    }

	/**
	 * 设置request中的Attribute值
	 * @param attributeName
	 * @param object
	 */
    public void setAttribute(String attributeName, Object object) {
        this.getRequest().setAttribute(attributeName, object);
    }

    /**
     * 获取session中的Attribute值
     * @param attributeName
     * @return
     */
    public Object getSessionAttr(String attributeName) {
        return this.getRequest().getSession(true).getAttribute(attributeName);
    }

    /**
     * 设置session中的Attribute值
     * @param attributeName
     * @param object
     */
    public void setSessionAttr(String attributeName, Object object) {
        this.getRequest().getSession(true).setAttribute(attributeName, object);
    }
    
    /**
     * 获取session
     * @return
     */
    public HttpSession getSession() {
        return this.getRequest().getSession(true);
    }
    
    /**
     * 获取request对象
     * @return
     */
    public HttpServletRequest getRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        return ((ServletRequestAttributes) ra).getRequest();
    }
    
    /**
     * 获取response对象
     * @return
     */
    public HttpServletResponse getResponse(){
        //RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        //HttpServletResponse r = ((ServletRequestAttributes) ra).getResponse();
        HttpServletResponse response = ((ServletWebRequest)RequestContextHolder
        		.getRequestAttributes()).getResponse();
        return response;
    }
    
    /**
     * 获取request中Parameter的值
     * @param paraName
     * @return
     */
    public String getParameter(String paraName) {
        return this.getRequest().getParameter(paraName);
    }
    
    /**
     * 获取表单格式数据ParameterMap值(或url拼接参数)
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Map getParameterMap(){
        return this.getRequest().getParameterMap();
    }
    
    /**
     * 获取IP地址
     * @return
     */
    public String getIpAddress(){
        String ip =  this.getRequest().getRemoteAddr();
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }
    
    /**
     * 获取服务器ip地址
     * @return
     */
    public String getServerIpAddress(){
        InetAddress address;
        String serverIpAddress = null;
        try {
        	//获取的是本地的IP地址 //PC-20140317PXKX/192.168.0.121
            address = InetAddress.getLocalHost(); 
            //192.168.0.121
            serverIpAddress = address.getHostAddress();
        } 
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return serverIpAddress;
    }
 
    
    /**
     * 允许跨域访问
     */
    public void allowCrossDomainAccess(){
        HttpServletResponse servletResponse = getResponse();
        servletResponse.setHeader("Access-Control-Allow-Origin", "*");
        servletResponse.setHeader("Access-Control-Allow-Methods", "POST,GET");
        servletResponse.setHeader("Access-Control-Allow-Headers:x-requested-with", "content-type");
    }
}
