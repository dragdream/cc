package com.tianee.oa.core.system.service;

import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tianee.oa.core.general.bean.TeeInterface;
import com.tianee.oa.core.general.dao.TeeInterfaceDao;
import com.tianee.oa.core.general.dao.TeeSysParaDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeeUserOnlineService;
import com.tianee.oa.core.system.filters.TeeIpRuleValidator;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeSystemServiceInterface {

	/**
	 * 登录方法
	 * @author syl
	 * @date 2013-11-13
	 * @param request
	 * @param response
	 * @return
	 */
	public abstract TeeJson doLoginInService(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 
	 * @author syl
	 * @date 2013-11-13
	 * @param cookies
	 * @param response
	 * @param SEC_BAN_TIME 有效时长  分钟
	 * @param person
	 */
	public abstract void addCookie(Cookie[] cookies,
			HttpServletResponse response, int SEC_BAN_TIME, TeePerson person);

	/**
	 * 销毁cookie
	 * @param cookies
	 */
	public abstract void deleteCookie(Cookie[] cookies,
			HttpServletResponse response, int personId);

	/**
	 * 登录成功设置系统参数放在session中
	 */
	public abstract void setSysParaSession(Map sysParaMap, HttpSession session);

	/**
	 * 登录成功设置can放在session中 ---
	 */
	public abstract void setIntefaceSession(TeeInterface inte,
			HttpSession session);

	/**
	 * 登录成功的处理
	 * @param conn
	 * @param person
	 * @param request
	 * @throws Exception
	 */
	public abstract void loginSuccess(TeePerson person,
			HttpServletRequest request, HttpServletResponse response,
			String loginType) throws Exception;

	/**
	 * 在session中设置用户信息
	 * @author syl
	 * @date 2013-11-19
	 * @param person
	 * @param session
	 * @param ip
	 * @param request
	 * @throws Exception
	 */
	public abstract void setUserInfoInSession(TeePerson person,
			HttpSession session, String ip, HttpServletRequest request,
			String sessionToken) throws Exception;

	/**
	 * 用户退出系统
	 * @author syl
	 * @date 2013-11-21
	 * @param request
	 * @param response
	 * @return
	 */
	public abstract TeeJson doLogoutService(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 获取当前登录人 有权限的菜单  且为选中的 --- 第二套风格
	 * @author syl
	 * @date 2014-3-23
	 * @param request
	 * @return
	 */
	public abstract TeeJson getPostSelectChildMenuInfoPerson(
			HttpServletRequest request);

	/**
	 * 获取当前登录人 有权限的菜单 --- 第二套风格
	 * @author syl
	 * @date 2014-3-17
	 * @param request
	 * @return
	 */
	public abstract TeeJson getPostChildMenuInfoByPerson(
			HttpServletRequest request);

	/**
	 * 获取各模块待办记录数
	 * @author syl
	 * @date 2014-6-15
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	public abstract TeeJson getModelHandCount(HttpServletRequest request)
			throws ParseException;

	public abstract void refreshCache(int cacheFlag);

	public abstract void updateUnitName(String unitName);

	public abstract void setPersonDao(TeePersonDao personDao);

	public abstract void setSysParaDao(TeeSysParaDao sysParaDao);

	public abstract void setInterfaceDao(TeeInterfaceDao interfaceDao);

	public abstract void setIpRuleValidator(TeeIpRuleValidator ipRuleValidator);

	public abstract void setOnlineService(TeeUserOnlineService onlineService);

	/**
	 * 登录方法
	 * @author syl
	 * @date 2013-11-13
	 * @param request
	 * @param response
	 * @return
	 */
	public abstract TeeJson doLoginInByUserIdService(
			HttpServletRequest request, HttpServletResponse response);

	
	/**
	 * 单点登录
	 * @param request
	 * @param response
	 * @return
	 */
	public abstract TeeJson doSSOLoginIn(HttpServletRequest request,
			HttpServletResponse response);

}