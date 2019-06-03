package com.tianee.oa.mobile.system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeMobileSystemServiceInterface {

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
	 * 获取菜单权限字符串   type=1 :OA类别     type=2 :执法类
	 * @param person
	 * @return
	 */
	public abstract List getMenuFuncs(TeePerson person,int type);

	/**
	 * 获取菜单权限
	 * @param person
	 * @return
	 */
	public abstract Set getMenuList(TeePerson person);

	public abstract Map getNewPush(TeePerson loginUser, String module);

	/**
	 * 绑定设备号
	 * @param loginUser
	 * @param deviceId
	 */
	public abstract TeeJson bindDeviceId(TeePerson loginUser, String deviceId,
			String serialNo);

}